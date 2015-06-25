/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportIngredientsVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;


/**
* Performs Report data retrievals
*/
public class ReportDomainCapabilityImpl implements ReportDomainCapability {

    private static final Logger LOG = Logger.getLogger(ReportDomainCapabilityImpl.class);
    private static final String CREATED_DTM_GE = " where CREATED_DTM >= '";
    private static final String INACT_DTM_GE = " WHERE INACTIVATION_DATE >= '";
    private boolean isNational = false;

    //Local format is used when in Derby, National Format when in Oracle
    private SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.US);
    private SimpleDateFormat nationalFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);

    /**
     * ReportDomainCapability
     */
    public ReportDomainCapabilityImpl() {
        super();
    }

    /**
     * getCaptureNdfData
     * 
     * @param eplId eplId
     * @return ReportCaptureNdfVo
     */
    @Override
    public List<ReportCaptureNdfVo> getCaptureNdfData(List<Long> eplId) {

        Connection con = null;
        List<ReportCaptureNdfVo> ndcList = new ArrayList<ReportCaptureNdfVo>();
        
        try {
            con = getConnection();
    
            if (con == null) {
                LOG.error("getCaptureNdfData.getConnection is null");
    
                return ndcList;
            }
    
            for (Long id : eplId) {
                ReportCaptureNdfVo vo = new ReportCaptureNdfVo();
                Long productEplId = getNDCInfo(con, id, vo);
                getProductInfo(con, productEplId, vo);
                getDrugClass(con, productEplId, vo);
                getDrugUnit(con, productEplId, vo);
                getDispenseUnit(con, productEplId, vo);
                getVadfFields(con, id, vo);
                ndcList.add(vo);
            }
        } catch (Exception e) {
            LOG.error("Exception in getCaptureNdfData! " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("error closing conneciton! " + e.getMessage());
            }
        }

        return ndcList;
    }

    /**
     * getProductInfo
     * 
     * @param conn Connection
     * @param eplId Id for the Ndc
     * @param ndfVo ReportCaptureNdfVo
     * @return productEplId
     */
    private Long getNDCInfo(Connection conn, Long eplId, ReportCaptureNdfVo ndfVo) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long productEplId = 0L;

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT P.EPL_ID, NDC.NDC_NUMBER, NDC.UPC_UPN, NDC.INACTIVATION_DATE, NDC.TRADE_NAME,"
                         + "NDC.PACKAGE_SIZE , M.NAME, PT.PACKAGE_TYPE_NAME, NDC.OTC_RX "
                         + "from epl_ndcs NDC, EPL_Manufacturers M, epl_package_types pt, epl_products P "
                         + "where NDC.EPL_id = ");
            query.append(eplId.toString());
            query.append(" and NDC.MANUFACTURER_ID_FK = M.EPL_ID and " + "NDC.PACKAGE_TYPE_ID_FK = PT.EPL_ID and "
                         + "NDC.EPL_ID_PRODUCT_FK = P.EPL_ID");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                productEplId = rs.getLong(1);
                ndfVo.setNdc(rs.getString(2));

                if (ndfVo.getNdc().length() >= PPSConstants.I13) {
                    ndfVo.setNdc2to6(ndfVo.getNdc().substring(0, PPSConstants.I5));
                    ndfVo.setNdc7to10(ndfVo.getNdc().substring(PPSConstants.I6, PPSConstants.I10));
                    ndfVo.setNdc11to12(ndfVo.getNdc().substring(PPSConstants.I11, PPSConstants.I13));
                } else {
                    LOG.error("NDCREPORT EPL_ID is " + eplId + "  NDC is " + ndfVo.getNdc());
                }

                ndfVo.setUpn(rs.getString(PPSConstants.I3));
                ndfVo.setNdcInactivationDate(rs.getDate(PPSConstants.I4));
                ndfVo.setTradeName(rs.getString(PPSConstants.I5));
                ndfVo.setPackageSize(rs.getDouble(PPSConstants.I6));
                ndfVo.setManufacturer(rs.getString(PPSConstants.I7));
                ndfVo.setPackageType(rs.getString(PPSConstants.I8));
                ndfVo.setRxOtcIndicator(rs.getString(PPSConstants.I9));
            }
        } catch (Exception e) {
            LOG.error("Error getting getNDCInfo: " + e.toString());
        } finally {
            try {
                pstmt.close();
                rs.close();
            } catch (Exception e) {
                LOG.error("Exception closing connections in getNDCInfo: " + e.toString());
            }
        }

        return productEplId;
    }

    /**
     * getProductInfo
     * 
     * @param conn Connection
     * @param eplId Id for the Product
     * @param ndfVo ReportCaptureNdfVo
     */
    private void getProductInfo(Connection conn, Long eplId, ReportCaptureNdfVo ndfVo) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long oiId = null;

        try {

            StringBuffer query = new StringBuffer();
            query.append("SELECT P.VA_PRODUCT_NAME, P.INACTIVATION_DATE, P.STRENGTH, P.NATIONAL_FORMULARY_NAME, "
                         + "P.NATIONAL_FORMULARY_INDICATOR, P.VA_PRINT_NAME, P.CMOP_ID, P.CMOP_DISPENSE_YN, "
                         + "G.GENERIC_NAME, DF.DF_NAME, CS.SCHEDULE_NAME, P.NDF_PRODUCT_IEN, O.EPL_ID "
                         + "from epl_products p , epl_va_gen_names G, epl_orderable_items O, epl_dosage_forms DF, "
                         + "epl_cs_fed_schedules CS " + "where P.EPL_id = ");
            query.append(eplId);
            query.append(" and P.VA_GEN_NAME_ID_FK = G.EPL_ID and " + "O.EPL_ID = P.EPL_ID_OI_FK and "
                         + "O.DOSAGE_FORM_ID_FK = DF.EPL_ID and " + "P.CS_FED_SCHED_ID_FK = CS.EPL_ID ");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setVaProductName(rs.getString(1));
                ndfVo.setProductInactivationDate(rs.getDate(2));
                ndfVo.setStrength(rs.getString(PPSConstants.I3));
                ndfVo.setNationalFormularyName(rs.getString(PPSConstants.I4));
                ndfVo.setNationalFormularyIndicator(rs.getString(PPSConstants.I5));
                ndfVo.setVaPrintName(rs.getString(PPSConstants.I6));
                ndfVo.setCmopId(rs.getString(PPSConstants.I7));
                ndfVo.setMarkForCmop(rs.getString(PPSConstants.I8));
                ndfVo.setVaGenericName(rs.getString(PPSConstants.I9));
                ndfVo.setDosageForm(rs.getString(PPSConstants.I10));
                ndfVo.setCsFederalSchedule(rs.getString(PPSConstants.I11));
                Long ien = rs.getLong(PPSConstants.I12);
                ndfVo.setDssFeederKey(String.format("%05d", ien));
                oiId = rs.getLong(PPSConstants.I13);

            }

//            , O.STANDARD_MED_ROUTE
//            ndfVo.setRouteOfAdministration(rs.getString(13));
        } catch (Exception e) {
            LOG.error("Error getting NDC Report Product information: " + e.toString());
        } finally {
            try {
                pstmt.close();
                rs.close();
            } catch (Exception e) {
                LOG.error("Error closing statements in Product information: " + e.toString());
            }
        }

        pstmt = null;
        rs = null;

        try {

            StringBuffer query = new StringBuffer();
            query.append("SELECT STD.STANDARD_MED_ROUTE_NAME "
                         + "from EPL_STANDARD_MED_ROUTES STD, epl_orderable_items O Where O.EPL_id = ");
            query.append(oiId);
            query.append(" and O.STANDARD_MED_ROUTE_ID_FK = STD.EPL_ID");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setRouteOfAdministration(rs.getString(1));
            }

            pstmt.close();
            rs.close();
        } catch (Exception e) {
            LOG.error("Error getting NDCReport Product information: " + e.toString());
        }

    }

    /**
     * ReportCaptureNdfVo
     * 
     * @param conn Connection
     * @param eplId Id for the product
     * @param ndfVo ReportCaptureNdfVo
     */
    private void getDrugClass(Connection conn, Long eplId, ReportCaptureNdfVo ndfVo) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT DC.CODE, DC.CLASSIFICATION_NAME from epl_products p , epl_prod_drug_class_assocs a, "
                         + " epl_va_drug_classes dc where P.EPL_id = ");
            query.append(eplId);
            query.append(" AND P.EPL_ID = a.epl_id_product_fk and a.primary_YN = 'Y' and a.drug_class_id_fk = dc.EPL_ID");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setVaDrugClassCode(rs.getString(1));
                ndfVo.setVaDrugClassClassification(rs.getString(2));
            }

            pstmt.close();
            rs.close();
        } catch (Exception e) {
            LOG.error("Error getting NDC information: " + e.toString());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing rs in getDrugClass() " + e.getMessage());
            }
        }
    }

    /**
     * getDrugUnit()
     * 
     * @param conn Connection
     * @param eplId Id for the product
     * @param ndfVo ReportCaptureNdfVo
     */
    private void getDrugUnit(Connection conn, Long eplId, ReportCaptureNdfVo ndfVo) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT DU.NAME from epl_products p , epl_drug_units du  where P.EPL_id = ");
            query.append(eplId);
            query.append(" AND P.DRUG_UNIT_ID_FK = du.EPL_ID");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setUnit(rs.getString(1));
            }

            pstmt.close();
            rs.close();
        } catch (Exception e) {
            LOG.error("Error getting Drug Unit information: " + e.toString());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing rs in getDrugUnit() " + e.getMessage());
            }
        }
    }

    /**
     * getDispenseUnit()
     * 
     * @param conn Connection
     * @param eplId Id for the product
     * @param ndfVo ReportCaptureNdfVo
     */
    private void getDispenseUnit(Connection conn, Long eplId, ReportCaptureNdfVo ndfVo) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // Dispense Unit
        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT DU.DISPENSE_UNIT_NAME from epl_products p , epl_va_dispense_units du where P.EPL_id = ");
            query.append(eplId);
            query.append(" AND P.DISPENSE_UNIT_ID_FK = DU.EPL_ID");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setVaDispenseUnit(rs.getString(1));
            }

            pstmt.close();
            rs.close();
        } catch (Exception e) {
            LOG.error("Error getting DispenseUnit information: " + e.toString());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing rs in getDispenseUnit() " + e.getMessage());
            }
        }
    }

    /**
     * getVadfFields()
     * 
     * @param conn Connection
     * @param ndcId Id for the NDC
     * @param ndfVo ReportCaptureNdfVo
     */
    private void getVadfFields(Connection conn, Long ndcId, ReportCaptureNdfVo ndfVo) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT NV.VA_DF_VALUE " 
                + "from EPL_VADF_NONLIST_VALUES NV, epl_vadf_owners owners where owners.EPL_ID_NDC_FK = ")
                .append(ndcId).append(" AND NV.VADF_OWNER_ID_FK = OWNERS.ID AND NV.VADF_ID_FK = 144");

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ndfVo.setProductNumber(rs.getString(1));
            }

            pstmt.close();
            rs.close();
        } catch (Exception e) {
            LOG.error("Error getting ProductId information: " + e.toString());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing rs in getVadfFields() " + e.getMessage());
            }
        }

    }

    /**
     * getProductIngredientData This method works different for the development
     * database hibernate and the production database oracle. On oracle, it
     * works correctly because we can use the outer join. In hibernate, any
     * records in the product ingredient assocs table that have a null drug unit
     * will not be returned.
     * 
     * @param eplId eplId
     * @return ReportCaptureNdfVo
     */
    @Override
    public List<ReportProductVo> getProductIngredientData(List<Long> eplId) {

        Connection con = null;
        List<ReportProductVo> reportProductList = new ArrayList<ReportProductVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = getConnection();
            
            if (con == null) {
                LOG.error("getProductIngredientData.getConnection is null");

                return reportProductList;
            }
            
            for (Long id : eplId) {
                StringBuffer query = new StringBuffer();

                ReportProductVo reportProductVo = new ReportProductVo();
                query.append("select DU.NAME, DF.DF_NAME, i.name, io.strength, p.va_product_name  "
                             + "from epl_products p, epl_prod_ingredient_assocs io, epl_ingredients i, "
                             + "epl_orderable_items o, epl_dosage_forms df, epl_drug_units du " 
                             + "where p.epl_id = ");
                query.append(id.longValue());
                query.append(" and p.epl_id = IO.EPL_ID_PRODUCT_FK and io.ingredient_id_fk = i.epl_id and ");

                if (isNational) {
                    query.append("IO.DRUG_UNIT_ID_FK = DU.EPL_ID(+) and ");
                } else {
                    query.append("IO.DRUG_UNIT_ID_FK = DU.EPL_ID and ");
                }

                query.append("P.EPL_ID_OI_FK = O.EPL_ID and o.dosage_form_id_fk = df.epl_id");

                pstmt = con.prepareStatement(query.toString());
                rs = pstmt.executeQuery();

                List<ReportIngredientsVo> list = new ArrayList<ReportIngredientsVo>();

                // extract data from the ResultSet
                while (rs.next()) {
                    reportProductVo.setVaProductName(rs.getString(PPSConstants.I5));

                    ReportIngredientsVo vo = new ReportIngredientsVo();
                    vo.setUnit(rs.getString(1));
                    vo.setDosageForm(rs.getString(2));
                    vo.setIngredient(rs.getString(PPSConstants.I3));
                    vo.setStrength(rs.getString(PPSConstants.I4));
                    list.add(vo);
                }

                reportProductVo.setIngredients(list);
                reportProductList.add(reportProductVo);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            try {
                pstmt.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            try {
                con.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }

        return reportProductList;
    }

    /**
     * getProductWarningLabelData
     * 
     * @return ReportProductVo
     */
    @Override
    public List<ReportProductVo> getProductWarningLabelData() {

        Connection con = null;
        List<ReportProductVo> list = new ArrayList<ReportProductVo>();
        PreparedStatement pstmt = null;
        String query =
                "select VA_PRODUCT_NAME, GCN_SEQNO from EPL_PRODUCTS where GCN_SEQNO IS NOT NULL and item_status = 'ACTIVE'";
        ResultSet rs = null;

        try {
            con = getConnection();
            
            if (con == null) {
                LOG.error("getProductWarningLabelData connection is null");

                return list;
            }
            
            pstmt = con.prepareStatement(query); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportProductVo vo = new ReportProductVo();
                vo.setVaProductName(rs.getString(1));
                vo.setGcnSeqNo(rs.getString(2));
                list.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Error in getProductWarningLabelData: " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing connection: " + e.getMessage());
            }
        }

        return list;
    }

    /**
     * getProductExclusionData
     * 
     * @param startDate startDate
     * @param endDate endDate
     * @return ReportProductVo
     */
    @Override
    public List<ReportProductVo> getProductExclusionData(Date startDate, Date endDate) {
        Connection con = null;
        List<ReportProductVo> list = new ArrayList<ReportProductVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
        
            if (con == null) {
                LOG.error("The getProductExclusionData connection is null");

                return list;
            }

            StringBuffer query = new StringBuffer();
            query.append("select va_product_name, exclude_interaction_check from epl_products ");
    
            if (startDate != null || endDate != null) {
                if (isNational) {
                    if (startDate == null) {
                        if (endDate != null) {
                            query.append(" where CREATED_DTM <= '" + nationalFormat.format(endDate) + "'");
                        }
                    } else {
                        query.append(" where  CREATED_DTM >= '" + nationalFormat.format(startDate) + "'");
    
                        if (endDate != null) {
                            query.append(" and  CREATED_DTM <= '" + nationalFormat.format(endDate) + "'");
                        }
                    }
                } else {
                    if (startDate == null) {
                        if (endDate != null) {
                            query.append("  where CREATED_DTM <= '" + localFormat.format(endDate) + "'");
                        }
                    } else {
                        query.append("  where CREATED_DTM >= '" + localFormat.format(startDate) + "'");
    
                        if (endDate != null) {
                            query.append("  and CREATED_DTM <= '" + localFormat.format(endDate) + "'");
                        }
                    }
                }
            }
      
            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReportProductVo vo = new ReportProductVo();
                vo.setVaProductName(rs.getString(1));
                vo.setExcluded(rs.getString(2));
                list.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Exception getting data " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing this connection!" + e.getMessage());
            }
        }

        return list;
    }

    /**
     * getProductNoActiveNdcData
     * 
     * @return ReportProductVo
     */
    @Override
    public List<ReportProductVo> getProductNoActiveNdcData() {

        Connection con = null;
        List<ReportProductVo> list = new ArrayList<ReportProductVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            
            if (con == null) {
                LOG.error("getProductExclusionData connection is null");

                return list;
            }

            StringBuffer query = new StringBuffer();
            query.append("select p.va_product_name, p.epl_id from epl_products p"
                + " where ( p.epl_id not in (select epl_id_product_fk from epl_ndcs where item_status = 'ACTIVE') ) "
                + "AND p.ITEM_STATUS = 'ACTIVE'");
            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportProductVo vo = new ReportProductVo();
                vo.setVaProductName(rs.getString(1));
                vo.setId(rs.getString(2));
                list.add(vo);
            }
        } catch (Exception e) {
            LOG.error("Error in getProductNoActiveNdcData " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.error("Error closing connection in GetProductNotActive Ndc Data: " + e.getMessage());
            }
        }

        return list;
    }

    /**
     * getProductProposedInactivationData
     * 
     * @param startDate The startDate
     * @param stopDate The stopDate
     * @return ReportProductVo
     */
    @Override
    public List<ReportProductVo> getProductProposedInactivationDate(Date startDate, Date stopDate) {

        LOG.error("Entered getProductProposedInactivationDate");
        Connection con = null;
        List<ReportProductVo> list = new ArrayList<ReportProductVo>();
        PreparedStatement pstmt = null;

        con = getConnection();
        
        if (con == null) {
            LOG.error("getProductProposedInactivationDate connection is null");

            return list;
        }

        // need to setup start and stopTimes 
        long startTime = 0;
        long stopTime = 0;

        if (startDate == null) {
            startTime = 0;
        } else {
            startTime = startDate.getTime();
        }

        if (stopDate == null) {
            stopTime = Long.MAX_VALUE;
        } else {
            stopTime = stopDate.getTime();
        }

        String query =
                "select p.va_product_name, V.VA_DF_VALUE "
                        + "from epl_products p, epl_vadf_owners o, epl_vadf_nonlist_values v "
                        + "where p.epl_id = o.epl_id_product_fk and o.id = v.vadf_owner_id_fk and v.vadf_id_fk = 60";

        LOG.debug("The query for getProductProposedInactivationDate is " + query);
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(query); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportProductVo vo = new ReportProductVo();
                vo.setVaProductName(rs.getString(1));

                String tVal = rs.getString(2);
                Long proLong = Long.valueOf(tVal);

                // if the proposed date is within the date range.
                if (proLong.longValue() > startTime && proLong.longValue() < stopTime) {
                    Date dt = new Date(proLong.longValue());
                    vo.setProposedInactionDate(dt);
                    list.add(vo);
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception excuting getProductProposedInactivationDate " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error closing connection for getProductProposedInactivationDate " + e.getMessage());
            }
        }

        if (list.isEmpty()) {
            LOG.debug("List is empty.");
        } else {
            LOG.debug("List is has a size of :" + list.size());
        }

        return list;
    }

    /**
     * getVuidApprovalReportIngredient
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidApprovalReportIngredient(Date startDate) {

        Connection con = getConnection();
        ReportVuidApprovalVo reportVuidApprovalVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> approvalList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidApprovalVo;
        }

        if (con == null) {
            LOG.error("getVuidApprovalReportIngredient:GetConnection is null");

            return reportVuidApprovalVo;
        }

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            StringBuffer query = new StringBuffer();
            query.append(" SELECT NAME, VUID, NDF_INGREDIENT_IEN, INACTIVATION_DATE, CREATED_DTM FROM EPL_INGREDIENTS ");

            if (isNational) {
                query.append(CREATED_DTM_GE + nationalFormat.format(startDate) + "'");
            } else {
                query.append(CREATED_DTM_GE + localFormat.format(startDate) + "'");
            }
            
            query.append("  AND NDF_INGREDIENT_IEN > 0");
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            LOG.debug("The query for getVuidApprovalReportIngredient is " + query);

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getDate(PPSConstants.I4));
                vo.setCreatedDate(rs.getDate(PPSConstants.I5));
                approvalList.add(vo);
            }

            reportVuidApprovalVo.setNewIngredientList(approvalList);
        } catch (Exception e) {
            LOG.debug("Exception while excution getVuidApprovalReportIngredient: " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error while closing connection for getVuidApprovalReportIngredient:" + e.getMessage());
            }
        }

        return reportVuidApprovalVo;
    }

    /**
     * getVuidModifiedReportIngredient
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidModifiedReportIngredient(Date startDate) {

        Connection con = getConnection();
        ReportVuidApprovalVo reportVuidModifiedVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> modifiedList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidModifiedVo;
        }

        if (con == null) {
            LOG.error("getVuidModifiedReportIngredient is null");

            return reportVuidModifiedVo;
        }

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        // Get the ingredients that have an inactivation date of >= start date
        try {
            StringBuffer query = new StringBuffer();
            query.append("select NAME, VUID, NDF_INGREDIENT_IEN, INACTIVATION_DATE, CREATED_DTM FROM EPL_INGREDIENTS ");

            if (isNational) {
                query.append(INACT_DTM_GE + nationalFormat.format(startDate) + "'");
            } else {
                query.append(INACT_DTM_GE + localFormat.format(startDate) + "'");
            }
            
            query.append(" AND NDF_INGREDIENT_IEN > 0");

            LOG.debug("The query for getVuidModifiedReportIngredient:Inactive is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getTimestamp(PPSConstants.I4));
                vo.setCreatedDate(rs.getTimestamp(PPSConstants.I5));
                vo.setVuid(rs.getString(2));
                
                modifiedList.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Exception while excution getVuidModifiedReportIngredient : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                LOG.debug("Error while closing connection for getVuidModifiedReportIngredient:" + e.getMessage());
            }
        }

        // Get the re-activated ingredients
        try {
            StringBuffer query = new StringBuffer();
            query.append("select ING.NAME, ING.VUID, ING.NDF_INGREDIENT_IEN, IAH.CREATED_DTM"
                         + " FROM EPL_INGREDIENTS ING, EPL_ITEM_AUDIT_HISTORY IAH, EPL_ITEM_AUDIT_HISTORY_DETAILS IAHD"
                         + "  WHERE IAH.CREATED_DTM >= '");

            if (isNational) {
                query.append(nationalFormat.format(startDate) + "'");
            } else {
                query.append(localFormat.format(startDate) + "'");
            }

            query.append(" AND IAH.AUDIT_ITEM_TYPE LIKE 'INGREDIENT' AND IAH.AUDIT_ITEM_ID = ING.EPL_ID"
                         + "  AND IAH.EPL_ID = IAHD.IAH_ID_FK AND IAH.EVENT_CATEGORY LIKE 'COMPLETED_MODIFICATION_REQUEST'"
                         + "  AND IAHD.COL_NM LIKE 'item.status'" + "  AND IAHD.NEW_VALUE LIKE 'ACTIVE'");

            LOG.debug("The query for getVuidModifiedReportIngredient is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet for the Item Audit History retrieval.
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setCreatedDate(rs.getDate(PPSConstants.I4));

                modifiedList.add(vo);
            }

            reportVuidModifiedVo.setModifiedIngredientList(modifiedList);
        } catch (Exception e) {
            LOG.debug("Excpetion while excution getVuidModifiedReportIngredient : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error in closing the connection for getVuidModifiedReportIngredient:" + e.getMessage());
            }
        }

        return reportVuidModifiedVo;
    }

    /**
     * getVuidApprovalReportDrugClasses
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidApprovalReportDrugClasses(Date startDate) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // create the vuidApprovalReport for DrugClasses
        ReportVuidApprovalVo reportVuidApprovalVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> approvalList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidApprovalVo;
        }

        if (con == null) {
            LOG.error("getVuidApprovalReportDrugClass:GetConnection is null");

            return reportVuidApprovalVo;
        }

        StringBuffer query = new StringBuffer();
        query.append("select CODE, CLASSIFICATION_NAME, VUID, NDF_CLASS_IEN FROM EPL_VA_DRUG_CLASSES ");

        if (isNational) {
            query.append(CREATED_DTM_GE + nationalFormat.format(startDate) + "'");
        } else {
            query.append(CREATED_DTM_GE + localFormat.format(startDate) + "'");
        }
        
        query.append(" AND NDF_CLASS_IEN > 0");

        LOG.debug("query for getVuidApprovalReportDrugClasses is " + query);

        try {
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1) + ":" + rs.getString(2));
                vo.setVuid(rs.getString(PPSConstants.I3));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I4)));
                approvalList.add(vo);
            }

            reportVuidApprovalVo.setNewDrugClassList(approvalList);
        } catch (Exception e) {
            LOG.error("Exception getting Drug Class VUID Approval report information: " + e.toString());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("error closing connection for DrugClassVuidApprovalReport: " + e.getMessage());
            }
        }

        return reportVuidApprovalVo;
    }

    /**
     * getVuidModifiedReportDrugClasses
     * 
     * @param startDate The startDate
     * @return ReportVuidApprovalVo the value object being returned
     */
    @Override
    public ReportVuidApprovalVo getVuidModifiedReportDrugClasses(Date startDate) {

        // You can't inactivate or re-activate drug classes so nothing to do
        ReportVuidApprovalVo reportVuidModifiedVo = new ReportVuidApprovalVo();

        return reportVuidModifiedVo;
    }

    /**
     * getVuidApprovalReportGeneric
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidApprovalReportGeneric(Date startDate) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // creqte the VuidApproval Report for Generics
        ReportVuidApprovalVo reportVuidApprovalVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> approvalList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidApprovalVo;
        }

        if (con == null) {
            LOG.error("getVuidApprovalReportGeneric:GetConnection is null");

            return reportVuidApprovalVo;
        }

        StringBuffer query = new StringBuffer();
        query.append("select GENERIC_NAME, VUID, NDF_GENERIC_IEN, INACTIVATION_DATE FROM EPL_VA_GEN_NAMES ");

        if (isNational) {
            query.append(CREATED_DTM_GE + nationalFormat.format(startDate) + "'");
        } else {
            query.append(CREATED_DTM_GE + localFormat.format(startDate) + "'");
        }
        
        query.append("  AND NDF_GENERIC_IEN > 0");

        LOG.debug("query for getVuidApprovalReportGeneric is " + query);

        try {
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                
                // set the ReportVuid VO for the Generic Report
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getDate(PPSConstants.I4));
                approvalList.add(vo);
            }

            reportVuidApprovalVo.setNewGenericList(approvalList);
        } catch (Exception e) {
            LOG.debug("Exception excuting getVuidApprovalReportGeneric: " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Exception closing connection for: " + e.getMessage());
            }
        }

        return reportVuidApprovalVo;
    }

    /**
     * getVuidModifiedReportGeneric
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidModifiedReportGeneric(Date startDate) {

        Connection con = getConnection();
        ReportVuidApprovalVo reportVuidModifiedVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> modifiedList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidModifiedVo;
        }

        if (con == null) {
            LOG.error("getVuidModifiedReportGeneric connection is null");

            return reportVuidModifiedVo;
        }

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        // Get the ingredients that have an inactivation date of >= start date
        try {
            StringBuffer query = new StringBuffer();
            query
                .append("select GENERIC_NAME, VUID, NDF_GENERIC_IEN, INACTIVATION_DATE, CREATED_DTM FROM EPL_VA_GEN_NAMES ");

            if (isNational) {
                query.append(INACT_DTM_GE + nationalFormat.format(startDate) + "'");
            } else {
                query.append(INACT_DTM_GE + localFormat.format(startDate) + "'");
            }
            
            query.append(" AND NDF_GENERIC_IEN > 0");

            LOG.debug("The query for getVuidModifiedReportGeneric:Inactive is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                
                // set the ReportVuidVo for the modifiedGenericReport.
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getTimestamp(PPSConstants.I4));
                vo.setCreatedDate(rs.getTimestamp(PPSConstants.I5));

                modifiedList.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Excpetion while excution getVuidModifiedReportGeneric : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                LOG.debug("error closing connection for getVuidModifiedReportGeneric:" + e.getMessage());
            }
        }

        // Get the re-activated ingredients
        try {
            StringBuffer query = new StringBuffer();
            query.append("select GEN.GENERIC_NAME, GEN.VUID, GEN.NDF_GENERIC_IEN, IAH.CREATED_DTM"
                         + " FROM EPL_VA_GEN_NAMES GEN, EPL_ITEM_AUDIT_HISTORY IAH, EPL_ITEM_AUDIT_HISTORY_DETAILS IAHD"
                         + " WHERE  IAH.CREATED_DTM >= '");

            if (isNational) {
                query.append(nationalFormat.format(startDate) + "'");
            } else {
                query.append(localFormat.format(startDate) + "'");
            }

            query.append(" AND IAH.AUDIT_ITEM_TYPE LIKE 'GENERIC_NAME' AND IAH.AUDIT_ITEM_ID = GEN.EPL_ID"
                         + " AND IAH.EPL_ID = IAHD.IAH_ID_FK AND IAH.EVENT_CATEGORY LIKE 'COMPLETED_MODIFICATION_REQUEST' "
                         + " AND IAHD.COL_NM LIKE 'item.status' " + " AND IAHD.NEW_VALUE LIKE 'ACTIVE' ");

            LOG.debug("The query for getVuidModifiedReportGeneric:ReAactive is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract GenericDrug data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setCreatedDate(rs.getDate(PPSConstants.I4));

                modifiedList.add(vo);
            }

            reportVuidModifiedVo.setModifiedGenericList(modifiedList);
        } catch (Exception e) {
            LOG.debug("Excpetion while excution getVuidModifiedReportGeneric:ReAactive : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("error closing connection for getVuidModifiedReportGeneric:ReAactive:" + e.getMessage());
            }
        }

        return reportVuidModifiedVo;
    }

    /**
     * getVuidApprovalReportVo
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidApprovalReportProducts(Date startDate) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ReportVuidApprovalVo reportVuidApprovalVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> approvalList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidApprovalVo;
        }

        if (con == null) {
            LOG.error("getVuidApprovalReportProduct:GetConnection is null");

            return reportVuidApprovalVo;
        }

        StringBuffer query = new StringBuffer();
        query.append("select VA_PRODUCT_NAME, VUID, NDF_PRODUCT_IEN, INACTIVATION_DATE FROM EPL_PRODUCTS ");

        if (isNational) {
            query.append(CREATED_DTM_GE + nationalFormat.format(startDate) + "'");
        } else {
            query.append(CREATED_DTM_GE + localFormat.format(startDate) + "'");
        }

        LOG.debug("query for getVuidApprovalReportProducts is " + query);

        try {
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getDate(PPSConstants.I4));
                approvalList.add(vo);
            }

            reportVuidApprovalVo.setNewProductList(approvalList);
        } catch (Exception e) {
            LOG.debug("Error executing getVuidApprovalReportProducts:" + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error closing connection for getVuidApprovalReportProducts " + e.getMessage());
            }
        }

        return reportVuidApprovalVo;
    }

    /**
     * getVuidModifiedReportProduct
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    @Override
    public ReportVuidApprovalVo getVuidModifiedReportProducts(Date startDate) {

        Connection con = getConnection();
        ReportVuidApprovalVo reportVuidModifiedVo = new ReportVuidApprovalVo();
        List<ReportVuidVo> modifiedList = new ArrayList<ReportVuidVo>();

        if (startDate == null) {
            return reportVuidModifiedVo;
        }

        if (con == null) {
            LOG.error("getVuidModifiedReportGeneric is null");

            return reportVuidModifiedVo;
        }

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        // Get the products that have an inactivation date of >= start date
        try {
            StringBuffer query = new StringBuffer();
            query.append("select VA_PRODUCT_NAME, VUID, NDF_PRODUCT_IEN, INACTIVATION_DATE, CREATED_DTM FROM EPL_PRODUCTS ");

            if (isNational) {
                query.append(INACT_DTM_GE + nationalFormat.format(startDate) + "'");
            } else {
                query.append(INACT_DTM_GE + localFormat.format(startDate) + "'");
            }
            
            query.append(" AND NDF_PRODUCT_IEN > 0");

            LOG.debug("The query for getVuidModifiedReportProduct:Inactive is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setInactivationDate(rs.getTimestamp(PPSConstants.I4));
                vo.setCreatedDate(rs.getTimestamp(PPSConstants.I5));

                modifiedList.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Excpetion while excution getVuidModifiedReportProduct : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                LOG.debug("error closing connection for getVuidModifiedReportProduct:" + e.getMessage());
            }
        }

        // Get the re-activated ingredients
        try {
            StringBuffer query = new StringBuffer();
            query.append("select PROD.VA_PRODUCT_NAME, PROD.VUID, PROD.NDF_PRODUCT_IEN, IAH.CREATED_DTM"
                         + " FROM EPL_PRODUCTS PROD, EPL_ITEM_AUDIT_HISTORY IAH, EPL_ITEM_AUDIT_HISTORY_DETAILS IAHD"
                         + " WHERE IAH.CREATED_DTM >= '");

            if (isNational) {
                query.append(nationalFormat.format(startDate) + "'");
            } else {
                query.append(localFormat.format(startDate) + "'");
            }

            query.append(" AND IAH.AUDIT_ITEM_TYPE LIKE 'PRODUCT' AND IAH.AUDIT_ITEM_ID = PROD.EPL_ID"
                         + " AND IAH.EPL_ID = IAHD.IAH_ID_FK AND IAH.EVENT_CATEGORY LIKE 'COMPLETED_MODIFICATION_REQUEST'"
                         + " AND IAHD.COL_NM LIKE 'item.status'" + " AND IAHD.NEW_VALUE LIKE 'ACTIVE'");

            LOG.debug("The query for getVuidModifiedReportProduct:ReAactive is " + query);
            pstmt = con.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportVuidVo vo = new ReportVuidVo();
                vo.setName(rs.getString(1));
                vo.setVuid(rs.getString(2));
                vo.setIen(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setCreatedDate(rs.getDate(PPSConstants.I4));

                modifiedList.add(vo);
            }

            reportVuidModifiedVo.setModifedProductList(modifiedList);
        } catch (Exception e) {
            LOG.debug("Excpetion while excution getVuidModifiedReportProduct:ReAactive : " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("error closing connection for getVuidModifiedReportProduct:ReAactive:" + e.getMessage());
            }
        }

        return reportVuidModifiedVo;
    }
    
    /**
     * getDrugClassData
     * @return List<ReportDrugClassVo> 
     */
    @Override
    public List<ReportDrugClassVo> getDrugClassData() {

        Connection con = null;
        List<ReportDrugClassVo> drugClassificationList = new ArrayList<ReportDrugClassVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            
            if (con == null) {
                LOG.error("drugClassificationList is null");

                return drugClassificationList;
            }

            StringBuffer query = new StringBuffer();
            query
                .append("SELECT code, classification_name, description, parent_class_id_fk, epl_id "
                    + "FROM epl_va_drug_classes WHERE parent_class_id_fk IS NULL ORDER BY code");

            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportDrugClassVo vo = new ReportDrugClassVo();

                vo.setCode(rs.getString(1));
                vo.setClassification(rs.getString(2));
                vo.setDescription(rs.getString(PPSConstants.I3));
                vo.setParentClassType(rs.getInt(PPSConstants.I4));
                vo.setId(rs.getLong(PPSConstants.I5));
                vo.setSecondaryDrugClasses(getDrugSecondaryClass(con, vo.getId()));
                drugClassificationList.add(vo);
            }
        } catch (Exception e) {
            LOG.debug("Exception excutiong getDrugClass " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error Closing connection in getDrugClass " + e.getMessage());
            }
        }

        return drugClassificationList;
    }

    /**
     * getDrugSecondaryClass
     * @param conn connection
     * @param eplId eplId
     * @return List<ReportDrugClassVo>
     */
    private List<ReportDrugClassVo> getDrugSecondaryClass(Connection conn, Long eplId) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReportDrugClassVo> tmpList = new ArrayList<ReportDrugClassVo>();

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT code, classification_name, epl_id  FROM epl_va_drug_classes "
                + "where parent_class_id_fk = ");
            query.append(eplId);
            pstmt = conn.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportDrugClassVo tmpVo = new ReportDrugClassVo();
                tmpVo.setCode(rs.getString(1));
                tmpVo.setClassification(rs.getString(2));
                tmpVo.setId(rs.getLong(PPSConstants.I3));
                tmpVo.setTertiaryDrugClasses(getDrugTertiaryClass(conn, tmpVo.getId()));
                tmpList.add(tmpVo);
            }
        } catch (Exception e) {
            LOG.error("Error getting Secondary Drug Class information: " + e.toString());
        } finally {
            try {
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Error closing connection in getSecondaryDrugClass " + e.getMessage());
            }

        }

        return tmpList;
    }

    /**
     * getDrugTertiaryClass
     * @param conn connection
     * @param eplId eplId
     * @return List<ReportDrugClassVo>
     */
    private List<ReportDrugClassVo> getDrugTertiaryClass(Connection conn, Long eplId) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReportDrugClassVo> tmpList = new ArrayList<ReportDrugClassVo>();

        try {
            StringBuffer query = new StringBuffer();
            query.append("SELECT code, classification_name FROM epl_va_drug_classes where parent_class_id_fk = ");
            query.append(eplId);
            pstmt = conn.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                ReportDrugClassVo tmpVo = new ReportDrugClassVo();
                tmpVo.setCode(rs.getString(1));
                tmpVo.setClassification(rs.getString(2));
                tmpList.add(tmpVo);
            }

        } catch (Exception e) {
            LOG.error("Error getting Tertiary Drug Class information: " + e.toString());
        } finally {
            try {
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                LOG.debug("Error closing connection in Tertiary " + e.getMessage());
            }

        }

        return tmpList;
    }

    
    /**
     * searchSettings
     * @param searchCriteria searchCriteria
     * @param table table
     * @return the query settings
     */
    private String searchSettings(SearchCriteriaVo searchCriteria, String table) {
        String reqStatus = "REQUEST_STATUS";
        String query = "";
        
        if (searchCriteria.getItemStatus().size() == 1) {
            query = PPSConstants.AND + table + "ITEM_STATUS" + PPSConstants.LIKE 
                + searchCriteria.getItemStatus().get(0).toString() + "'";
        }

        if (searchCriteria.getRequestStatus().size() == 1) {
            query +=
                PPSConstants.AND + table + reqStatus + PPSConstants.LIKE + searchCriteria.getRequestStatus().get(0).toString() 
                    + "'";
        } else if (searchCriteria.getRequestStatus().size() == 2) {
            query +=
                PPSConstants.AND + "( " + table + reqStatus + PPSConstants.LIKE 
                    + searchCriteria.getRequestStatus().get(0).toString() + "'" + PPSConstants.OR + table + reqStatus 
                    + PPSConstants.LIKE + searchCriteria.getRequestStatus().get(1).toString() + "' ) ";
        }

        if (searchCriteria.getCategories() != null && searchCriteria.getCategories().size() > 0) {
            query += PPSConstants.AND + "( ";

            for (Category cat : searchCriteria.getCategories()) {
                if (cat.equals(Category.COMPOUND)) {
                    query += " " + table + "CAT_COMPOUND_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(Category.INVESTIGATIONAL)) {
                    query += " " + table + "CAT_INVEST_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(Category.MEDICATION)) {
                    query += " " + table + "CAT_MEDIC_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(Category.SUPPLY)) {
                    query += " " + table + "CAT_SUPPLY_FLAG LIKE 'Y'" + PPSConstants.OR;
                }
            }

            // remove the last OR
            query = query.substring(0, query.length() - PPSConstants.I4);
            query += " ) ";
        }

        if (searchCriteria.getSubCategories() != null && searchCriteria.getSubCategories().size() > 0) {
            query += PPSConstants.AND + "( ";

            for (SubCategory cat : searchCriteria.getSubCategories()) {
                if (cat.equals(SubCategory.CHEMOTHERAPY)) {
                    query += " " + table + "SUBCAT_CHEMO_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(SubCategory.HERBAL)) {
                    query += " " + table + "SUBCAT_HERBAL_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(SubCategory.OTC)) {
                    query += " " + table + "SUBCAT_OTC_FLAG LIKE 'Y'" + PPSConstants.OR;
                }

                if (cat.equals(SubCategory.VETERINARY)) {
                    query += " " + table + "SUBCAT_VETER_FLAG LIKE 'Y'" + PPSConstants.OR;
                }
            }

            // remove the last OR
            query = query.substring(0, query.length() - PPSConstants.I4);
            query += " ) ";
        }
        
        return query;
    }
    
    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param searchCriteria searchCriteriaVo
     * @return The list of Ids
     */
    @Override
    public List<Long> getSimpleSearchIds(SearchCriteriaVo searchCriteria) {

        Connection con = getConnection();
        List<Long> list = new ArrayList<Long>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String val = "";
        String dosageForm = "";
        String strength = "";
        String table = "";

        EntityType entityType = searchCriteria.getEntityType();
        SearchType searchType = searchCriteria.getSearchTerms().get(0).getSearchType();

        if (EntityType.NDC.equals(entityType)) {
            table = "NDC.";
        } else if (EntityType.PRODUCT.equals(entityType)) {
            table = "PRODUCT.";
        } else if (EntityType.ORDERABLE_ITEM.equals(entityType)) {
            table = "OI.";
        }

        if (searchType.equals(SearchType.CONTAINS)) {
            val = "'%" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "%'";
   
            if (StringUtils.isNotBlank(searchCriteria.getStrength())) {
                strength = "'%" + searchCriteria.getStrength().toUpperCase(Locale.US) + "%'";
            }
            
            if (StringUtils.isNotBlank(searchCriteria.getDosageForm())) {
                dosageForm = "'%" + searchCriteria.getDosageForm().toUpperCase(Locale.US) + "%'";
            }            
            
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            val = "'" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "%'";
         
            if (StringUtils.isNotBlank(searchCriteria.getStrength())) {
                strength = "'" + searchCriteria.getStrength().toUpperCase(Locale.US) + "%'";
            }
            
            if (StringUtils.isNotBlank(searchCriteria.getDosageForm())) {
                dosageForm = "'" + searchCriteria.getDosageForm().toUpperCase(Locale.US) + "%'";
            }

        } else if (searchType.equals(SearchType.EQUALS)) {
            val = "'" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "'";
          
            if (StringUtils.isNotBlank(searchCriteria.getStrength())) {
                strength = "'" + searchCriteria.getStrength().toUpperCase(Locale.US) + "'";
            }
            
            if (StringUtils.isNotBlank(searchCriteria.getDosageForm())) {
                dosageForm = "'" + searchCriteria.getDosageForm().toUpperCase(Locale.US) + "'";
            }

        } else {
            return list;
        }

        // set the searchSettings.
        String querySettings = this.searchSettings(searchCriteria, table);
       
        if (con == null) {
            LOG.error("This connection for getIds is null");

            return list;
        }

        try {
            StringBuffer query = new StringBuffer(" ");

            if (EntityType.NDC.equals(entityType)) {
                query.append("Select DISTINCT " + table + "EPL_ID from EPL_NDCS NDC WHERE ( NDC.TRADE_NAME LIKE ").append(
                    val);
                query.append(" OR NDC.NDC_NUMBER LIKE ").append(val);
                query.append(" OR NDC.NDC_NO_DASHES LIKE ").append(val);
                query.append(" OR NDC.UPC_UPN LIKE ").append(val);
                query.append(" ) ");

            } else if (EntityType.ORDERABLE_ITEM.equals(entityType)) {
                query.append("Select  DISTINCT OI.EPL_ID from EPL_ORDERABLE_ITEMS OI ")
                    .append(" LEFT JOIN EPL_DOSAGE_FORMS DF ON OI.DOSAGE_FORM_ID_FK = DF.EPL_ID ")
                    .append("WHERE OI.OI_NAME LIKE ").append(val);
                
                if (StringUtils.isNotEmpty(dosageForm)) {
                    query.append(" AND DF.DF_NAME LIKE ").append(dosageForm);       
                }             
            } else {
                query.append("SELECT  DISTINCT  PRODUCT.EPL_ID FROM EPL_PRODUCTS PRODUCT ");
                query.append("LEFT JOIN EPL_SYNONYMS SYN ON PRODUCT.EPL_ID = SYN.EPL_ID_PRODUCT_FK ");
                query.append("LEFT JOIN EPL_VA_GEN_NAMES GEN ON PRODUCT.VA_GEN_NAME_ID_FK = GEN.EPL_ID ");
                query
                    .append("LEFT JOIN EPL_PROD_INGREDIENT_ASSOCS PIASSOCS ON PIASSOCS.EPL_ID_PRODUCT_FK = PRODUCT.EPL_ID ");
                query.append("LEFT JOIN EPL_INGREDIENTS ING ON PIASSOCS.INGREDIENT_ID_FK = ING.EPL_ID ");
                query
                    .append("LEFT JOIN EPL_PROD_DRUG_CLASS_ASSOCS PDCASSOCS ON PDCASSOCS.EPL_ID_PRODUCT_FK = PRODUCT.EPL_ID ");
                query.append("LEFT JOIN EPL_VA_DRUG_CLASSES DRUGCLASS ON PDCASSOCS.DRUG_CLASS_ID_FK = DRUGCLASS.EPL_ID ");

                query.append("WHERE ( PRODUCT.VA_PRODUCT_NAME LIKE ").append(val);
                query.append(" OR PRODUCT.VA_PRINT_NAME LIKE ").append(val);
                query.append(" OR PRODUCT.CMOP_ID LIKE ").append(val);
                query.append(" OR GEN.GENERIC_NAME LIKE ").append(val);
                query.append(" OR ING.NAME LIKE ").append(val);
                query.append(" OR SYN.SYNONYM_NAME LIKE ").append(val);
                query.append(" OR DRUGCLASS.CODE LIKE ").append(val);
                query.append(" OR DRUGCLASS.CLASSIFICATION_NAME LIKE ").append(val).append(" ) ");
                
                if (StringUtils.isNotEmpty(strength)) {
                    query.append(" AND PRODUCT.STRENGTH LIKE ").append(strength);
                }
            }

            query.append(querySettings);

            LOG.debug("ReportDomainCapability.getSimpleSearchIds The Query for getIds is " + query);
            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            int i = 0;

            // extract data from the ResultSet
            // Up to 1000 items
            while (rs.next()) {
                long value = rs.getLong(1);
                list.add(Long.valueOf(value));

                if (++i >= PPSConstants.I1000) {
                    
                    // only pull back 1000 items.
                    break;
                }
            }
        } catch (Exception e) {
            LOG.debug("ReportDomainCapability.getSimpleSearchIds Exception in executing getIds " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("ReportDomainCapability.getSimpleSearchIds Error closing connection for getIds " + e.getMessage());
            }
        }

        LOG.debug("ReportDomainCapability.getSimpleSearchIds Found " + list.size() + " items.");

        return list;
    }

    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param searchCriteria searchCriteriaVo
     * @return The list of Ids
     */
    @Override
    public List<Long> getSimpleSynonymSearchIds(SearchCriteriaVo searchCriteria) {

        Connection con = getConnection();
        List<Long> list = new ArrayList<Long>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String val = "";
        String table = "";

        SearchType searchType = searchCriteria.getSearchTerms().get(0).getSearchType();

        table = "PRODUCT.";
        
        if (searchType.equals(SearchType.CONTAINS)) {
            val = "'%" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "%'";
        } else if (searchType.equals(SearchType.BEGINS_WITH)) {
            val = "'" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "%'";
        } else if (searchType.equals(SearchType.EQUALS)) {
            val = "'" + searchCriteria.getSearchTerms().get(0).getValue().toUpperCase(Locale.US) + "'";
        } else {

            return list;
        }
        
        String querySettings = this.searchSettings(searchCriteria, table);
       
        if (con == null) {
            LOG.error("This connection for getIds is null");

            return list;
        }

        try {
            StringBuffer query = new StringBuffer(" ");

            query.append("SELECT  DISTINCT  PRODUCT.EPL_ID FROM EPL_PRODUCTS PRODUCT ")
                .append("LEFT JOIN EPL_SYNONYMS SYN ON PRODUCT.EPL_ID = SYN.EPL_ID_PRODUCT_FK ")
                .append("WHERE SYN.SYNONYM_NAME LIKE ").append(val).append(querySettings);
            
            LOG.debug("Query for getIds is " + query);
            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            int i = 0;

            // extract data from the ResultSet
            while (rs.next()) {
                long value = rs.getLong(1);
                list.add(Long.valueOf(value));

                if (++i >= PPSConstants.I1000) {
                    break;
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in getSimpleSynonymSearchIds getIds " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error closing getSimpleSynonymSearchIds for getIds " + e.getMessage());
            }
        }

        LOG.debug("Found " + list.size() + " items.");

        return list;
    }
    
    
    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param reportType is the entity type for the ids
     * @return The list of Ids
     */
    @Override
    public List<Long> getIds(ReportType reportType) {
        Connection con = getConnection();
        List<Long> list = new ArrayList<Long>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (con == null) {
            LOG.error("This connection for getIds is null");

            return list;
        }

        try {
            StringBuffer query = new StringBuffer("Select EPL_ID from ");

            if (ReportType.LIST_INGREDIENTS.equals(reportType)) {
                query.append("EPL_PRODUCTS WHERE ITEM_STATUS = 'ACTIVE'");
            } else if (ReportType.NDC_LIST_PRINT_TEMPLATE.equals(reportType)) {
                query.append("EPL_NDCS");
            } else {
                return list;
            }

            LOG.debug("Query for getIds is " + query);
            pstmt = con.prepareStatement(query.toString()); // create a statement
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                long value = rs.getLong(1);
                list.add(Long.valueOf(value));
            }
        } catch (Exception e) {
            LOG.debug("Exception in executing getIds " + e.getMessage());
        } finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.debug("Error closing connection for getIds " + e.getMessage());
            }
        }

        LOG.debug("Found " + list.size() + " items.");

        return list;
    }

    /**
     * getConnection
     * 
     * @return Connection;
     */
    private Connection getConnection() {

        Connection conn = getJNDIConnection();

        if (conn == null) {
            isNational = false;
            conn = getSimpleConnection();
        } else {
            isNational = true;
        }

        return conn;
    }

    /**
     * Uses JNDI and Datasource (preferred style) for retrieving the JNDI Connection.
     * 
     * @return Connection
     */
    private Connection getJNDIConnection() {

        // Obtain our environment getJNDIConnection
        Context initCtx;
        Connection conn = null;

        try {
            ConfigFileUtility cfu = new ConfigFileUtility();
            initCtx = new InitialContext();
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            ht.put(Context.PROVIDER_URL, "t3://localhost:" + cfu.getPort());
            initCtx = new InitialContext(ht);

            // Lookup this DataSouce at the top level of the WebLogic JNDI tree
            DataSource datasource = (DataSource) initCtx.lookup("datasource.NationalEPL");
            conn = datasource.getConnection();
        } catch (NamingException ex) {
            LOG.error("NamingException in getting connection: " + ex);
        } catch (SQLException ex) {
            LOG.error("SQL Exception in getting connection: " + ex);
        }

        return conn;
    }

    /**
     * getSimpleConnection for ReportDomainCapability.
     * 
     * @return connection
     */
    private Connection getSimpleConnection() {

        // See your driver documentation for the proper format of this string :
        String dbConn = "jdbc:derby://localhost:1527/NationalEPL;create=true";

        // Provided by your driver documentation. In this case, a MySql driver
        // is used for ReportDomainCapability:
        String driverClassName = "org.apache.derby.jdbc.ClientDriver";
        String userName = "ppsnepl";
        String pass = "pharmacy";

        Connection result = null;

        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception ex) {
            LOG.error("ReportDomainCapability: Check classpath. Cannot load db driver: " + driverClassName);
        }

        try {
            result = DriverManager.getConnection(dbConn, userName, pass);
        } catch (SQLException e) {
            LOG.error("Driver loaded, but cannot connect to db: " + dbConn);
        }

        return result;
    }

}
