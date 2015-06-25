/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassGroupVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;


/**
 * This class is used to retrieve data from the database for the migration
 * effort.
 * 
 */
public class ResetNationalDatabaseDomainCapabilityImpl implements ResetNationalDatabaseDomainCapability {

    private static final Logger LOG = Logger.getLogger(ResetNationalDatabaseDomainCapabilityImpl.class);
    private static final String MSG_CONN_NULL = "Connection is null.";
    private static final String MSG_IS = " message is ";
    private static final String TRUE = "true";
    private static final String YES = "Y";
    private static final String OR = " OR ";

    private static final String DDSL = "3";
    private static final String DQTYLD = "4";
    private static final String DQTYLT = "5";
    private static final String DONOTMAIL = "7";
    private static final String HRM = "8";
    private static final String PATSPECLBL = "12";
    private static final String PTFROMLIGHT = "13";
    private static final String REFRIG = "14";
    private static final String WITREQ = "19";
    private static final String FORM = "24";
    private static final String AFS = "25";
    private static final String QDM = "27";
    private static final String NUMDOS = "28";
    private static final String DMS = "29";
    private static final String RL = "30";
    private static final String NOTIFPREG = "32";
    private static final String FUT = "33";
    private static final String HAZDIS = "34";
    private static final String HAZHAN = "35";
    private static final String HAZPAT = "36";
    private static final String LTOOS = "38";
    private static final String LSM = "39";
    private static final String NO_RENEW = "41";
    private static final String LIFETIME_CUM_DOSE = "45"; // Lifetime Cumulative Dosages
    private static final String OTHER_LANG_INST = "53"; // Other Language instructions
    private static final String PATIENT_INST = "54"; // Patient instructions
    private static final String PRODTYPE = "57";
    private static final String DDL = "59";
    private static final String PPOPINACT = "60";
    private static final String PREVNDC = "67"; // previous.ndc
    private static final String PREUPN = "68"; // previous.upc.upn
    private static final String AWACAT = "71";
    private static final String AWACON = "72";
    private static final String DLFO = "75";
    private static final String DLS = "76";
    private static final String DOR = "77";
    private static final String DORR = "78";
    private static final String DQTYO = "79";
    private static final String DQTYOR = "80";
    private static final String TDQ = "83";
    private static final String DORB = "84";
    private static final String DAW = "87";
    private static final String MDL = "93";
    private static final String RXMESSAGE = "94";
    private static final String MMD = "95";
    private static final String DEAS = "96";
    private static final String HRFU = "97";
    private static final String HRFUT = "98";
    private static final String IOMAXT = "99";
    private static final String IOMINT = "100";
    private static final String OI_SYN = "119"; // Orderable Item synonym
    private static final String UDS = "121";
    private static final String UDST = "122";
    private static final String FSN = "124";
    private static final String PDUPOU = "128";
    private static final String FORMALT = "130";
    private static final String IV_FLAG = "131"; // IV flag
    private static final String MONROUT = "140";
    private static final String LABMM = "141";
    private static final String NCPDU = "142";
    private static final String NCPQM = "143";
    private static final String PRODNUM = "144";
    private static final String LOCALSPECHAND = "146";
    private static final String SPECHANLDING = "147";
    private static final String DISPPERORDER = "148";
    private static final String NFHU = "149";

    private boolean isWindows = false;

    /**
     * Default Constructor
     */
    public ResetNationalDatabaseDomainCapabilityImpl() {
        String osName = System.getProperty("os.name");

        if (osName.contains("win") || osName.contains("Win")) {
            isWindows = true;
        } else {
            isWindows = false;
        }
    }

    /**
     * Setup and return a connection
     *
     * @return Connection
     */
    protected Connection setupConnection() {
        Connection con = null; //NOPMD

        // get the depending upon the operating system type.  Windows is for the local dev boxes.
        if (isWindows) {
            con = getSimpleConnection();
        } else {
            con = getJNDIConnection();
        }

        return con;
    }

    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param longVal
     *            this is the EPL_ID from the NDC table.
     * @param ndcVo
     *            This is the NDC that was created using the blank template.
     * @return A fully filled out NdcVo
     */
    @Override
    @SuppressWarnings("rawtypes")
    public NdcVo getNdcs(Long longVal, NdcVo ndcVo) {

        Connection con = setupConnection();

        // Return a null if neither connection worked.
        if (con == null) {
            LOG.error(MSG_CONN_NULL);

            return null;
        }

        String ndfId = longVal.toString();
        String query =
            "select NDC.NDC_NUMBER, P.VA_PRODUCT_NAME, P.VUID, P.GCN_SEQNO, NDC.INACTIVATION_DATE, M.NAME, "
                + "NDC.NDC_DISP_UNITS_PER_ORD_UNIT, NDC.OTC_RX, NDC.PACKAGE_SIZE, PT.PACKAGE_TYPE_NAME, NDC.TRADE_NAME, "
                + "OU.ORDER_UNIT_ABBREVIATION, NDC.SOURCE"
                + " from EPL_NDCS NDC, EPL_PRODUCTS P, EPL_ORDER_UNITS OU, EPL_MANUFACTURERS M, EPL_PACKAGE_TYPES PT"
                + " where NDC.EPL_ID = " 
                + ndfId + " AND NDC.EPL_ID_PRODUCT_FK = P.EPL_ID AND NDC.MANUFACTURER_ID_FK = M.EPL_ID AND "
                + "NDC.PACKAGE_TYPE_ID_FK = PT.EPL_ID AND NDC.ORDER_UNIT_ID_FK = OU.EPL_ID";

        try {

            ProductVo productVo = new ProductVo();
            ManufacturerVo manVo = new ManufacturerVo();
            PackageTypeVo ptVo = new PackageTypeVo();
            OtcRxVo otcVo = new OtcRxVo();
            OrderUnitVo ouVo = new OrderUnitVo();
            ResultSet rs1 = null; //NOPMD
            PreparedStatement pstmt1 = null;

            pstmt1 = con.prepareStatement(query); // create a statement
            rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                extractNdc(rs1, ndcVo, productVo, manVo, otcVo, ptVo, ouVo);

                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                String vadfOwnerId = "";

                try {

                    // Get the Owner Id
                    String query2 =
                        "select ID"
                            + " from EPL_VADF_OWNERS"
                            + " where VADF_OWNER_TYPE = 'NDC' AND VADF_OWNER_ID = " 
                            + ndfId;
                    pstmt2 = con.prepareStatement(query2); // create a statement
                    rs2 = pstmt2.executeQuery();

                    // extract data from the ResultSet
                    while (rs2.next()) {
                        Long l = rs2.getLong(1);
                        vadfOwnerId = l.toString();
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                } finally {
                    rs2.close();
                    pstmt2.close();
                }

                try {

                    String queryNonList =
                        "select  VADF_ID_FK, VA_DF_VALUE"
                            + " from EPL_VADF_NONLIST_VALUES"
                            + " where VADF_OWNER_ID_FK = "
                            + vadfOwnerId + "  AND ( VADF_ID_FK = " + PTFROMLIGHT + OR + "VADF_ID_FK = " + PPOPINACT 
                            + OR + "VADF_ID_FK = " + PRODNUM + " )";

                    ResultSet rsNonList = null; //NOPMD
                    PreparedStatement pstmtNonList = null;
                    pstmtNonList = con.prepareStatement(queryNonList);
                    rsNonList = pstmtNonList.executeQuery();

                    // extract data from the ResultSet
                    DataFields<DataField> ndcVadfNonList = ndcVo.getVaDataFields();
                    extractNdcVadfNonList(rsNonList, ndcVo, ndcVadfNonList);
                    ndcVo.setVaDataFields(ndcVadfNonList);

                    rsNonList.close();
                    pstmtNonList.close();
                } catch (Exception e) {
                    LOG.error("SQL EXCEPTION IN Process NDC NonList datafields : " + longVal + ":" + e.getMessage());
                }

                try {
                    String queryAssocs =
                        " select VADF_ID_FK, LIST_VALUE"
                            + " from EPL_VADF_ASSOC_VALUES"
                            + " where VADF_OWNER_ID_FK = " 
                            + vadfOwnerId + " AND ( VADF_ID_FK = " + REFRIG + " )";

                    ResultSet rsAssocs = null; //NOPMD
                    PreparedStatement pstmtAssocs = null;
                    pstmtAssocs = con.prepareStatement(queryAssocs);
                    rsAssocs = pstmtAssocs.executeQuery();

                    // extract data from the ResultSet
                    DataFields<DataField> ndcVadfAssocs = ndcVo.getVaDataFields();
                    extractNdcVadfAssocs(rsAssocs, ndcVo, ndcVadfAssocs);
                    ndcVo.setVaDataFields(ndcVadfAssocs);

                    rsAssocs.close();
                    pstmtAssocs.close();
                } catch (Exception e) {
                    LOG.error("SQL EXCEPTION IN Process NDC Assocs datafields : " + longVal + MSG_IS + e.getMessage());
                }

                try {
                    String queryMulti =
                        "select VADF_ID_FK, TEXT"
                            + " from EPL_MULTI_TEXT"
                            + " where VADF_OWNER_ID_FK = " 
                            + vadfOwnerId + " AND ( VADF_ID_FK = " + PREVNDC + OR + "VADF_ID_FK = " + PREUPN + " )";

                    ResultSet rsMulti = null; //NOPMD
                    PreparedStatement pstmtMulti = null;
                    pstmtMulti = con.prepareStatement(queryMulti);
                    rsMulti = pstmtMulti.executeQuery();

                    // extract data from the ResultSet
                    DataFields<DataField> ndcVadfMulti = ndcVo.getVaDataFields();
                    extractNdcVadfMulti(rsMulti, ndcVo, ndcVadfMulti);
                    ndcVo.setVaDataFields(ndcVadfMulti);

                    rsMulti.close();
                    pstmtMulti.close();
                } catch (Exception e) {
                    LOG.error("SQL EXCEPTION IN Process NDC MULTI datafields : " + longVal + MSG_IS + e.getMessage());
                }
            } // end while.rs.next

            rs1.close();
            pstmt1.close();
        } catch (Exception e) {
            LOG.error("Exception occured Processing the NDCS. Error is " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Exception occured Closing the connection. Error is " + e.getMessage());
            }
        }

        return ndcVo;
    }

    /**
     * extractNdc
     *
     * @param rs1 ResultSet
     * @param ndcVo NdcVo
     * @param productVo ProductVo
     * @param manVo ManufacturerVo
     * @param otcVo OtcRxVo
     * @param ptVo PackageTypeVo
     * @param ouVo OrderUnitVo
     * @throws SQLException exception
     */
    private void extractNdc(ResultSet rs1, NdcVo ndcVo, ProductVo productVo, ManufacturerVo manVo, OtcRxVo otcVo,
        PackageTypeVo ptVo, OrderUnitVo ouVo) throws SQLException {

        ndcVo.setNdc(rs1.getString(1)); // NDC_NUMBER
        productVo.setVaProductName(rs1.getString(2)); // VAProductName
        productVo.setVuid(rs1.getString(PPSConstants.I3)); // VUID
        productVo.setGcnSequenceNumber(rs1.getString(PPSConstants.I4)); // GCNSeqNo
        ndcVo.setProduct(productVo);
        ndcVo.setInactivationDate(rs1.getDate(PPSConstants.I5)); // Inactivation Date
        manVo.setValue(rs1.getString(PPSConstants.I6)); // manufacturerName
        ndcVo.setManufacturer(manVo);
        ndcVo.setNdcDispUnitsPerOrdUnit(rs1.getDouble(PPSConstants.I7));
        otcVo.setValue(rs1.getString(PPSConstants.I8)); // OTC_RX
        ndcVo.setOtcRxIndicator(otcVo);
        ndcVo.setPackageSize(rs1.getDouble(PPSConstants.I9)); // Package Size
        ptVo.setValue(rs1.getString(PPSConstants.I10)); // Package Type
        ndcVo.setPackageType(ptVo);
        ndcVo.setTradeName(rs1.getString(PPSConstants.I11)); // Trade Name
        ouVo.setValue(rs1.getString(PPSConstants.I12)); // Order Unit
        ndcVo.setOrderUnit(ouVo);
        String source = rs1.getString(PPSConstants.I13);

        if ("FDA".equals(source)) {
            ndcVo.setSource(NdcSourceType.FDA);
        } else if ("COTS".equals(source)) {
            ndcVo.setSource(NdcSourceType.COTS);
        } else {
            ndcVo.setSource(NdcSourceType.VA);
        }

    }

    /**
     * extractNdcVadfAssocs
     *
     * @param rsNonList ResultSet
     * @param ndcVo NdcVo
     * @param ndcVadfNonList DataFields
     * @throws SQLException exception
     */
    @SuppressWarnings("rawtypes")
    private void extractNdcVadfNonList(ResultSet rsNonList, NdcVo ndcVo, DataFields<DataField> ndcVadfNonList)
        throws SQLException {

        while (rsNonList.next()) {
            Long val1 = rsNonList.getLong(1);

            if (PTFROMLIGHT.equals(val1.toString())) {
                String val2 = rsNonList.getString(2);

                if (TRUE.equals(val2)) {
                    ndcVadfNonList.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(true);
                } else {
                    ndcVadfNonList.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(false);
                }
            } else if (PPOPINACT.equals(val1.toString())) {
                String tVal = rsNonList.getString(2);
                Long proLong = Long.valueOf(tVal);
                Date dt = new Date(proLong.longValue());
                ndcVadfNonList.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(dt);
            } else if (PRODNUM.equals(val1.toString())) {
                String val2 = rsNonList.getString(2);
                ndcVadfNonList.getDataField(FieldKey.PRODUCT_NUMBER).selectStringValue(val2);
            }
        } // end while next
    }

    /**
     * extractNdcVadfAssocs
     *
     * @param rsAssocs ResultSet
     * @param ndcVo NdcVo
     * @param ndcVadfAssocs DataFields
     * @throws SQLException exception
     */
    @SuppressWarnings("rawtypes")
    private void extractNdcVadfAssocs(ResultSet rsAssocs, NdcVo ndcVo, DataFields<DataField> ndcVadfAssocs)
        throws SQLException {

        while (rsAssocs.next()) {
            Long val1 = rsAssocs.getLong(1);

            if (REFRIG.equals(val1.toString())) {
                ndcVadfAssocs.getDataField(FieldKey.REFRIGERATION).addStringSelection("REFRIGERATE");

            }
        } // end while next
    }

    /**
      * extractNdcVadfMulti
      *
      * @param rsMulti ResultSet
      * @param ndcVo NdcVo
      * @param ndcVadfMulti DataFields
      * @throws SQLException exception
      */
    @SuppressWarnings("rawtypes")
    private void extractNdcVadfMulti(ResultSet rsMulti, NdcVo ndcVo, DataFields<DataField> ndcVadfMulti) throws SQLException {
        while (rsMulti.next()) {
            Long val1 = rsMulti.getLong(1);

            if (PREVNDC.equals(val1.toString())) {
                String val2 = rsMulti.getString(2);
                ndcVadfMulti.getDataField(FieldKey.PREVIOUS_NDC).selectStringValue(val2);
            } else if (PREUPN.equals(val1.toString())) {
                String val2 = rsMulti.getString(2);
                ndcVadfMulti.getDataField(FieldKey.PREVIOUS_UPC_UPN).selectStringValue(val2);
            }
        } // end while next
    }

    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param longVal
     *            This is the EPL_ID for the product
     * @param productVo
     *            The generically created productVo from a blank template
     * @return the fully formed productVo
     */
    @Override
    @SuppressWarnings("rawtypes")
    public ProductVo getProduct(Long longVal, ProductVo productVo) {
        String productId = longVal.toString();
        Connection con = setupConnection();

        if (con == null) {
            LOG.error(MSG_CONN_NULL);

            return null;
        }

        try {
            String query1 =
                "select P.VA_PRODUCT_NAME,  P.REJECT_REASON_TEXT, P.MAX_DOSE_PER_DAY, P.TALLMAN_LETTERING, " 
                    + "P.REQUEST_REJECT_REASON, OI.OI_NAME"
                    + " FROM EPL_PRODUCTS P, EPL_ORDERABLE_ITEMS OI"
                    + " WHERE P.EPL_ID = "
                    + productId + " AND P.EPL_ID_OI_FK = OI.EPL_ID";

            try {
                OrderableItemVo oiVo = new OrderableItemVo();
                PreparedStatement pstmt1 = con.prepareStatement(query1);
                ResultSet rs1 = pstmt1.executeQuery(); //NOPMD
                extractProduct(rs1, productVo, oiVo);
                pstmt1.close();
                rs1.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN ProcessProduct query1 : " + longVal + MSG_IS + e.getMessage());
            }

            try {
                OrderUnitVo ouVo = new OrderUnitVo();
                String query2A =
                    "SELECT OU.ORDER_UNIT_ABBREVIATION"
                        + " FROM EPL_ORDER_UNITS OU, EPL_PRODUCTS p" 
                        + " WHERE OU.EPL_ID = P.ORDER_UNIT_ID_FK AND P.EPL_ID = "
                        + productId;

                PreparedStatement pstmt2A = con.prepareStatement(query2A);
                ResultSet rs2A = pstmt2A.executeQuery(); //NOPMD
                extractProductOrderUnit(rs2A, ouVo);
                productVo.setOrderUnit(ouVo);
                pstmt2A.close();
                rs2A.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN ProcessProduct query2A : " + longVal + MSG_IS + e.getMessage());
            }

            try {
                String query2 =
                    "SELECT DT.DRUG_TEXT_NAME" 
                        + " FROM EPL_DRUG_TEXT DT, EPL_PROD_DRUG_TEXT_N_ASSOCS pdt, EPL_PRODUCTS p"
                        + " WHERE DT.EPL_ID = pdt.DRUG_TEXT_ID_FK AND P.EPL_ID = pdt.EPL_ID_PROD_FK AND P.EPL_ID = "
                        + productId;

                PreparedStatement pstmt2 = con.prepareStatement(query2);
                ResultSet rs2 = pstmt2.executeQuery(); //NOPMD
                List<DrugTextVo> drugTextList = new ArrayList<DrugTextVo>();
                extractProductDrugText(rs2, drugTextList);
                productVo.setNationalDrugTexts(drugTextList);

                pstmt2.close();
                rs2.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN ProcessProduct query2 : " + longVal + MSG_IS + e.getMessage());
            }

            try {
                String query4 =
                    "SELECT DC.CODE, DC.CLASSIFICATION_NAME"
                    + " FROM EPL_VA_DRUG_CLASSES DC, EPL_PROD_DRUG_CLASS_ASSOCS DCA, EPL_PRODUCTS P"
                    + " WHERE DC.EPL_ID = DCA.DRUG_CLASS_ID_FK AND P.EPL_ID = DCA.EPL_ID_PRODUCT_FK AND DCA.PRIMARY_YN = 'N'"
                    + " AND P.EPL_ID = " 
                    + productId;

                PreparedStatement pstmt4 = con.prepareStatement(query4);
                ResultSet rs4 = pstmt4.executeQuery(); //NOPMD
                List<DrugClassGroupVo> drugClassGroupVo = new ArrayList<DrugClassGroupVo>();
                extractProductDrugClassGroups(rs4, drugClassGroupVo);
                productVo.setDrugClasses(drugClassGroupVo);

                pstmt4.close();
                rs4.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN ProcessProduct query4 : " + longVal + MSG_IS + e.getMessage());
            }

            extractSynonyms(con, longVal, productId, productVo);

            String vadfOwnerId = "";

            try {
                String query6 =
                    "select ID" 
                        + " from EPL_VADF_OWNERS" 
                        + " where VADF_OWNER_TYPE = 'PRODUCT' AND VADF_OWNER_ID = " 
                        + productId;
                PreparedStatement pstmt6 = con.prepareStatement(query6);
                ResultSet rs6 = pstmt6.executeQuery(); //NOPMD

                while (rs6.next()) {
                    Long l = rs6.getLong(1);
                    vadfOwnerId = l.toString();
                }

                rs6.close();
                pstmt6.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN ProcessProduct query6 : " + longVal + MSG_IS + e.getMessage());
            }

            extractProductVadf(con, longVal, vadfOwnerId, productVo);

            try {
                String queryAssocs =
                    "select VADF_ID_FK, LIST_VALUE"
                        + " from EPL_VADF_ASSOC_VALUES" 
                        + " where VADF_OWNER_ID_FK = " 
                        + vadfOwnerId
                        + " AND ( VADF_ID_FK = " + REFRIG + OR + "VADF_ID_FK = " + NCPDU + OR + "VADF_ID_FK = " + DAW + OR
                        + "VADF_ID_FK = " + FORM + OR + "VADF_ID_FK = " + AWACAT + OR + "VADF_ID_FK = " + PRODTYPE + OR
                        + "VADF_ID_FK = " + RL + OR + "VADF_ID_FK = " + DEAS + OR + "VADF_ID_FK = " + UDST + OR
                        + "VADF_ID_FK = " + AFS + " )";

                ResultSet rsAssocs = null; //NOPMD
                PreparedStatement pstmtAssocs = null;
                pstmtAssocs = con.prepareStatement(queryAssocs);
                rsAssocs = pstmtAssocs.executeQuery();

                DataFields<DataField> prodVadfAssocs = productVo.getVaDataFields();
                extractProductVadfAssociations(rsAssocs, productVo, prodVadfAssocs);
                productVo.setVaDataFields(prodVadfAssocs);

                rsAssocs.close();
                pstmtAssocs.close();
            } catch (Exception e) {
                LOG.error("SQL EXCEPTION IN Process Product Assocs datafields : " + longVal + ":" + e.getMessage());
            }

        } catch (Exception e) {
            LOG.error("SQL EXCEPTION IN ProcessProduct : " + longVal + MSG_IS + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("SQL EXCEPTION IN closing connection: " + e.getMessage());
            }
        }

        return productVo;
    }

    /**
     * extractProduct
     *
     * @param rs1 ResultSet
     * @param productVo ProductVo
     * @param oiVo OrderableItemVo
     * @throws SQLException exception
     */
    private void extractProduct(ResultSet rs1, ProductVo productVo, OrderableItemVo oiVo) throws SQLException {
        while (rs1.next()) {
            productVo.setVaProductName(rs1.getString(1)); // VAProductName
            productVo.setRejectionReasonText(rs1.getString(2));
            productVo.setMaxDosePerDay(rs1.getLong(PPSConstants.I3));
            productVo.setTallManLettering(rs1.getString(PPSConstants.I4));

            // Get Request Rejection Reason
            String rrs = rs1.getString(PPSConstants.I5);

            if (StringUtils.isNotBlank(rrs)) {
                if (RequestRejectionReason.INAPPROPRIATE_REQUEST.toString().equalsIgnoreCase(rrs)) {
                    productVo.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
                } else if (RequestRejectionReason.INCOMPLETE_INFORMATION.toString().equalsIgnoreCase(rrs)) {
                    productVo.setRequestRejectionReason(RequestRejectionReason.INCOMPLETE_INFORMATION);
                } else if (RequestRejectionReason.ITEM_EXISTS.toString().equalsIgnoreCase(rrs)) {
                    productVo.setRequestRejectionReason(RequestRejectionReason.ITEM_EXISTS);
                }
            }

            oiVo.setOiName(rs1.getString(PPSConstants.I6));
            productVo.setParent(oiVo);
        }
    }

    /**
     * extractProductDrugClassGroups
     *
     * @param rs4 ResultSet
     * @param drugClassGroupVo List<DrugClassGroupVo>
     * @throws SQLException exception
     */
    private void extractProductDrugClassGroups(ResultSet rs4, List<DrugClassGroupVo> drugClassGroupVo) throws SQLException {
        while (rs4.next()) {
            DrugClassGroupVo dcgVo = new DrugClassGroupVo();
            dcgVo.setPrimary(false);
            DrugClassVo dcVo = new DrugClassVo();
            dcVo.setCode(rs4.getString(1));
            dcVo.setClassification(rs4.getString(2));
            dcgVo.setDrugClass(dcVo);
            drugClassGroupVo.add(dcgVo);
        }
    }

    /**
     * extractProductOrderUnit
     *
     * @param rs2A ResultSet
     * @param ouVo OrderUnitVo
     * @throws SQLException exception
     */
    private void extractProductOrderUnit(ResultSet rs2A, OrderUnitVo ouVo) throws SQLException {
        while (rs2A.next()) {
            ouVo.setAbbrev(rs2A.getString(1));
        }
    }

    /**
     * extractProductDrugText
     *
     * @param rs2 ResultSet
     * @param drugTextList List<DrugTextVo> 
     * @throws SQLException exception
     */
    private void extractProductDrugText(ResultSet rs2, List<DrugTextVo> drugTextList) throws SQLException {
        while (rs2.next()) {
            DrugTextVo dtVo = new DrugTextVo();
            dtVo.setValue(rs2.getString(1));
            drugTextList.add(dtVo);
        }
    }

    /**
     * extractSynonyms
     *
     * @param con Connection
     * @param longVal Long
     * @param productId String
     * @param productVo ProductVo
     * @throws SQLException exception
     */
    private void extractSynonyms(Connection con, Long longVal, String productId, ProductVo productVo) throws SQLException {
        try {
            String query5 =
                "SELECT SYN.SYNONYM_NAME, SYN.NDC_CODE, SYN.VSN, SYN.DISPENSE_UNITS_PER_ORDER_UNIT, "
                + "SYN.PRICE_PER_DISPENSE_UNIT, SYN.VENDOR, SYN.PRICE_PER_ORDER_UNIT, OU.ORDER_UNIT_ABBREVIATION, " 
                + "IU.INTENDED_USE"
                + " FROM EPL_SYNONYMS SYN, EPL_ORDER_UNITS OU, EPL_INTENDED_USES IU"
                + " WHERE SYN.EPL_ID_PRODUCT_FK = "
                + productId 
                + " AND SYN.ORDER_UNIT_ID_FK = OU.EPL_ID AND SYN.INTENDED_USE_ID_FK = IU.EPL_ID";

            PreparedStatement pstmt5 = con.prepareStatement(query5);
            ResultSet rs5 = pstmt5.executeQuery(); //NOPMD
            List<SynonymVo> synonyms = new ArrayList<SynonymVo>();

            while (rs5.next()) {
                SynonymVo synonym = new SynonymVo();
                synonym.setSynonymName(rs5.getString(1));
                synonym.setNdcCode(rs5.getString(2));
                synonym.setSynonymVsn(rs5.getString(PPSConstants.I3));
                synonym.setSynonymDispenseUnitPerOrderUnit(rs5.getDouble(PPSConstants.I4));
                synonym.setSynonymPricePerDispenseUnit(rs5.getDouble(PPSConstants.I5));
                synonym.setSynonymVendor(rs5.getString(PPSConstants.I6));
                synonym.setSynonymPricePerOrderUnit(rs5.getDouble(PPSConstants.I7));

                OrderUnitVo synOrderUnitVo = new OrderUnitVo();
                synOrderUnitVo.setAbbrev(rs5.getString(PPSConstants.I8));
                synonym.setSynonymOrderUnit(synOrderUnitVo);

                IntendedUseVo synIUVo = new IntendedUseVo();
                synIUVo.setIntendedUse(rs5.getString(PPSConstants.I9));
                synonym.setSynonymIntendedUse(synIUVo);
                synonyms.add(synonym);
            }

            productVo.setSynonyms(synonyms);

            pstmt5.close();
            rs5.close();
        } catch (Exception e) {
            LOG.error("SQL EXCEPTION IN ProcessProduct query5 : " + longVal + MSG_IS + e.getMessage());
        }

    }

    /**
     * extractProductVadf
     *
     * @param con Connection
     * @param longVal Long
     * @param vadfOwnerId String
     * @param productVo ProductVo
     */
    @SuppressWarnings("rawtypes")
    private void extractProductVadf(Connection con, Long longVal, String vadfOwnerId, ProductVo productVo) {
        try {
            String queryVadf =
                "select VADF_ID_FK, VA_DF_VALUE" 
                    + " from EPL_VADF_NONLIST_VALUES"
                    + " where VADF_OWNER_ID_FK = " 
                    + vadfOwnerId
                    + " AND ( VADF_ID_FK = " + SPECHANLDING + OR + "VADF_ID_FK = " + PATSPECLBL + OR + "VADF_ID_FK = "
                    + PTFROMLIGHT + OR + "VADF_ID_FK = " + NOTIFPREG + OR + "VADF_ID_FK = " + HAZDIS + OR + "VADF_ID_FK = "
                    + HAZHAN + OR + "VADF_ID_FK = " + HAZPAT + OR + "VADF_ID_FK = " + LTOOS + OR + "VADF_ID_FK = " + LSM
                    + OR + "VADF_ID_FK = " + NO_RENEW + OR + "VADF_ID_FK = " + LABMM + OR + "VADF_ID_FK = " + NFHU + OR
                    + "VADF_ID_FK = " + NCPQM + OR + "VADF_ID_FK = " + DONOTMAIL + OR + "VADF_ID_FK = " + AWACON + OR
                    + "VADF_ID_FK = " + DDL + OR + "VADF_ID_FK = " + DDSL + OR + "VADF_ID_FK = " + DLFO + OR
                    + "VADF_ID_FK = " + DLS + OR + "VADF_ID_FK = " + DOR + OR + "VADF_ID_FK = " + DORR + OR
                    + "VADF_ID_FK = " + DQTYLD + OR + "VADF_ID_FK = " + DQTYLT + OR + "VADF_ID_FK = " + DQTYO + OR
                    + "VADF_ID_FK = " + DQTYOR + OR + "VADF_ID_FK = " + FSN + OR + "VADF_ID_FK = " + FORMALT + OR
                    + "VADF_ID_FK = " + HRM + OR + "VADF_ID_FK = " + HRFU + OR + "VADF_ID_FK = " + HRFUT + OR
                    + "VADF_ID_FK = " + FUT + OR + "VADF_ID_FK = " + IOMAXT + OR + "VADF_ID_FK = " + IOMINT + OR
                    + "VADF_ID_FK = " + MDL + OR + "VADF_ID_FK = " + RXMESSAGE + OR + "VADF_ID_FK = " + MMD + OR
                    + "VADF_ID_FK = " + DORB + OR + "VADF_ID_FK = " + QDM + OR + "VADF_ID_FK = " + TDQ + OR + "VADF_ID_FK = " 
                    + UDS + OR + "VADF_ID_FK = " + DMS + OR + "VADF_ID_FK = " + MONROUT + OR + "VADF_ID_FK = " + NUMDOS + OR 
                    + "VADF_ID_FK = " + PPOPINACT + OR + "VADF_ID_FK = " + DISPPERORDER + OR + "VADF_ID_FK = " + WITREQ + OR 
                    + "VADF_ID_FK = " + LOCALSPECHAND + OR + "VADF_ID_FK = " + PDUPOU + " )";

            ResultSet rsVadf = null; //NOPMD
            PreparedStatement pstmtVadf = null;
            pstmtVadf = con.prepareStatement(queryVadf);
            rsVadf = pstmtVadf.executeQuery();

            DataFields<DataField> prodVadf = productVo.getVaDataFields();
            extractProductVadf2(rsVadf, productVo, prodVadf);
            productVo.setVaDataFields(prodVadf);

            rsVadf.close();
            pstmtVadf.close();
        } catch (Exception e) {
            LOG.error("SQL EXCEPTION IN ProcessProduct queryVADF : " + longVal + MSG_IS + e.getMessage());
        }
    }

    /**
     * extractProductVadf2
     *
     * @param rsVadf ResultSet
     * @param productVo ProductVo
     * @param prodVadf DataFields
     * @throws SQLException exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void extractProductVadf2(ResultSet rsVadf, ProductVo productVo, DataFields<DataField> prodVadf)
        throws SQLException {

        Map<String, FieldKey> mapBool = new HashMap<String, FieldKey>();
        mapBool.put(PTFROMLIGHT, FieldKey.PROTECT_FROM_LIGHT);
        mapBool.put(WITREQ, FieldKey.WITNESS_TO_ADMINISTRATION);
        mapBool.put(PATSPECLBL, FieldKey.PATIENT_SPECIFIC_LABEL);
        mapBool.put(NOTIFPREG, FieldKey.DO_NOT_HANDLE_IF_PREGNANT);
        mapBool.put(FUT, FieldKey.FOLLOW_UP_TIME);
        mapBool.put(HAZDIS, FieldKey.HAZARDOUS_TO_DISPOSE);
        mapBool.put(HAZHAN, FieldKey.HAZARDOUS_TO_HANDLE);
        mapBool.put(HAZPAT, FieldKey.HAZARDOUS_TO_PATIENT);
        mapBool.put(LTOOS, FieldKey.LONG_TERM_OUT_OF_STOCK);
        mapBool.put(LSM, FieldKey.LOW_SAFETY_MARGIN);
        mapBool.put(NO_RENEW, FieldKey.NON_RENEWABLE);
        mapBool.put(LABMM, FieldKey.LAB_MONITOR_MARK);
        mapBool.put(DONOTMAIL, FieldKey.DO_NOT_MAIL);
        mapBool.put(HRFU, FieldKey.HIGH_RISK_FOLLOWUP);
        mapBool.put(HRM, FieldKey.HIGH_RISK);

        Map<String, FieldKey> mapValue = new HashMap<String, FieldKey>();
        mapValue.put(NCPQM, FieldKey.NDCDP_QUANTITY_MULTIPLIER);
        mapValue.put(LOCALSPECHAND, FieldKey.LOCAL_SPECIAL_HANDLING);
        mapValue.put(DDL, FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
        mapValue.put(DLFO, FieldKey.DISPENSE_LIMIT_FOR_ORDER);
        mapValue.put(DLS, FieldKey.DISPENSE_LIMIT_FOR_SCHEDULE);
        mapValue.put(DOR, FieldKey.DISPENSE_OVERRIDE);
        mapValue.put(DORR, FieldKey.DISPENSE_OVERRIDE_REASON);

        Map<String, FieldKey> mapStringValue = new HashMap<String, FieldKey>();
        mapStringValue.put(AWACON, FieldKey.AR_WS_CONVERSION_NUMBER);
        mapStringValue.put(DDSL, FieldKey.DISPENSE_DAYS_SUPPLY_LIMIT);
        mapStringValue.put(DQTYO, FieldKey.DISPENSE_QUANTITY_OVERRIDE);
        mapStringValue.put(DQTYOR, FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON);
        mapStringValue.put(PDUPOU, FieldKey.DISPENSE_QUANTITY_OVERRIDE_REASON); // yes this was set in original code to this
        mapStringValue.put(FSN, FieldKey.FSN);
        mapStringValue.put(HRFUT, FieldKey.HIGH_RISK_FOLLOWUP_TIME_PERIOD);
        mapStringValue.put(IOMAXT, FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME);
        mapStringValue.put(IOMINT, FieldKey.INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME);
        mapStringValue.put(MDL, FieldKey.MAX_DISPENSE_LIMIT);
        mapStringValue.put(RXMESSAGE, FieldKey.MESSAGE);
        mapStringValue.put(MMD, FieldKey.MONITOR_MAX_DAYS);
        mapStringValue.put(DORB, FieldKey.OVERRIDE_REASON_ENTERER);
        mapStringValue.put(QDM, FieldKey.QUANTITY_DISPENSE_MESSAGE);
        mapStringValue.put(TDQ, FieldKey.TOTAL_DISPENSE_QUANTITY);
        mapStringValue.put(UDS, FieldKey.UNIT_DOSE_SCHEDULE);
        mapStringValue.put(DMS, FieldKey.DEFAULT_MAIL_SERVICE);
        mapStringValue.put(MONROUT, FieldKey.MONITOR_ROUTINE);
        mapStringValue.put(NUMDOS, FieldKey.NUMBER_OF_DOSES);
        mapStringValue.put(DISPPERORDER, FieldKey.PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT);

        while (rsVadf.next()) {
            Long v = rsVadf.getLong(1);
            String val1 = v.toString();
            String val2 = rsVadf.getString(2);

            if (PPOPINACT.equals(val1)) {
                String tVal = rsVadf.getString(2);
                Long proLong = Long.valueOf(tVal);
                Date dt = new Date(proLong.longValue());
                prodVadf.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(dt);
            } else if (DQTYLD.equals(val1)) {
                prodVadf.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT)
                    .getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_DOSE).selectStringValue(val2);
            } else if (DQTYLT.equals(val1)) {
                prodVadf.getDataField(FieldKey.DISPENSE_QUANTITY_LIMIT)
                    .getGroupedField(FieldKey.DISPENSE_QUANTITY_LIMIT_TIME).selectStringValue(val2);
            } else if (mapBool.containsKey(val1)) {
                prodVadf.getDataField(mapBool.get(val1)).selectValue(TRUE.equals(val2));
            } else if (mapValue.containsKey(val1)) {
                prodVadf.getDataField(mapValue.get(val1)).selectValue(val2);
            } else if (mapStringValue.containsKey(val1)) {
                prodVadf.getDataField(mapStringValue.get(val1)).selectStringValue(val2);
            }
        } // end of while
    }

    /**
     * extractProductVadf
     *
     * @param rsAssocs ResultSet
     * @param productVo ProductVo
     * @param prodVadfAssocs DataFields
     * @throws SQLException exception
     */
    @SuppressWarnings("rawtypes")
    private void extractProductVadfAssociations(ResultSet rsAssocs, ProductVo productVo,
        DataFields<DataField> prodVadfAssocs) throws SQLException {

        while (rsAssocs.next()) {
            Long val1 = rsAssocs.getLong(1);

            if (REFRIG.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.REFRIGERATION).addStringSelection(val2);
            } else if (FORM.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.FORMULARY).addStringSelection(val2);
            } else if (UDST.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.UNIT_DOSE_SCHEDULE_TYPE).addStringSelection(val2);
            } else if (RL.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.RECALL_LEVEL).addStringSelection(val2);
            } else if (DEAS.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.DEA_SCHEDULE).addStringSelection(val2);
            } else if (DAW.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.DAW_CODE).removeSelection("0-NO PRODUCT SELECTION INDICATED");
                prodVadfAssocs.getDataField(FieldKey.DAW_CODE).addStringSelection(val2);
            } else if (AWACAT.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.AR_WS_AMIS_CATEGORY).addStringSelection(val2);
            } else if (NCPDU.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT).removeSelection("EA-EACH");
                prodVadfAssocs.getDataField(FieldKey.NCPDP_DISPENSE_UNIT).addStringSelection(val2);
            } else if (AFS.equals(val1.toString())) {
                String val2 = rsAssocs.getString(2);
                prodVadfAssocs.getDataField(FieldKey.APPROVED_FOR_SPLITTING).addStringSelection(val2);
            }
        } // end while next
    }

    /**
     * getIds gets the orderable item from the database.
     * 
     * @param longVal This is the entity type for the ids
     * @param orderableItemVo oiVo
     * @return Orderable Item Value Object.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public OrderableItemVo getOrderableItem(Long longVal, OrderableItemVo orderableItemVo) {
        Connection con = setupConnection(); //NOPMD

        if (con == null) {
            LOG.error(MSG_CONN_NULL);

            return null;
        }

        String orderableItemId = longVal.toString();
        String query1 =
            "SELECT OI.OI_NAME, OI.VISTA_OI_NAME, OI.NATIONAL_FORMULARY_INDICATOR, OI.INACTIVATION_DATE, OI.NON_VA_MED, "
                + "OI.REJECT_REASON_TEXT, OI.REQUEST_REJECT_REASON, DF.DF_NAME, OI.CAT_COMPOUND_FLAG, OI.CAT_INVEST_FLAG, " 
                + "OI.CAT_MEDIC_FLAG, OI.CAT_SUPPLY_FLAG" 
                + " FROM EPL_ORDERABLE_ITEMS OI, EPL_DOSAGE_FORMS DF" 
                + " WHERE OI.EPL_ID = "
                + orderableItemId 
                + " AND OI.DOSAGE_FORM_ID_FK = DF.EPL_ID ";

        try {
            PreparedStatement pstmt1 = con.prepareStatement(query1);
            ResultSet rs1 = pstmt1.executeQuery(); //NOPMD
            extractOrderableItem(rs1, orderableItemVo);
            rs1.close();
            pstmt1.close();
        } catch (Exception e) {
            logQueryError(longVal, "1", e);
        }

        String queryMedRoute =
            "SELECT STDMED.STANDARD_MED_ROUTE_NAME" 
                + " FROM EPL_STANDARD_MED_ROUTES STDMED, EPL_ORDERABLE_ITEMS OI" 
                + " WHERE OI.EPL_ID = "
                + orderableItemId 
                + " AND STDMED.EPL_ID = OI.STANDARD_MED_ROUTE_ID_FK ";

        try {
            PreparedStatement pstmtMedRoute = con.prepareStatement(queryMedRoute);
            ResultSet rsMedRoute = pstmtMedRoute.executeQuery(); //NOPMD
            extractOiMedRoute(rsMedRoute, orderableItemVo);
            rsMedRoute.close();
            pstmtMedRoute.close();
        } catch (Exception e) {
            logQueryError(longVal, "1", e);
        }

        String vadfOwnerId = "";

        try {
            String query2 =
                "select ID" 
                    + " from EPL_VADF_OWNERS" 
                    + " where VADF_OWNER_TYPE = 'ORDERABLE_ITEM' AND VADF_OWNER_ID = "
                    + orderableItemId;
            PreparedStatement pstmt2 = con.prepareStatement(query2);
            ResultSet rs2 = pstmt2.executeQuery(); //NOPMD

            // extract data from the ResultSet
            while (rs2.next()) {
                Long l = rs2.getLong(1);
                vadfOwnerId = l.toString();
            }

            rs2.close();
            pstmt2.close();
        } catch (Exception e) {
            logQueryError(longVal, "2", e);
        }

        String query3 =
            "select VADF_ID_FK, VA_DF_VALUE" 
                + " from EPL_VADF_NONLIST_VALUES" 
                + " where VADF_OWNER_ID_FK = " 
                + vadfOwnerId
                + " AND ( VADF_ID_FK = " + LIFETIME_CUM_DOSE + OR + "VADF_ID_FK = " + OTHER_LANG_INST + OR + "VADF_ID_FK = "
                + PATIENT_INST + OR + "VADF_ID_FK = " + DDL + OR + "VADF_ID_FK = " + PPOPINACT + OR + "VADF_ID_FK = " + IV_FLAG
                + " )";

        try {
            PreparedStatement pstmt3 = con.prepareStatement(query3);
            ResultSet rs3 = pstmt3.executeQuery(); //NOPMD
            DataFields<DataField> oiVadf = orderableItemVo.getVaDataFields();
            extractOiVadf(rs3, orderableItemVo, oiVadf);
            orderableItemVo.setVaDataFields(oiVadf);
            rs3.close();
            pstmt3.close();
        } catch (Exception e) {
            logQueryError(longVal, "3", e);
        }

        String query5 =
            "select VADF_ID_FK, TEXT" 
                + " from EPL_MULTI_TEXT" 
                + " where VADF_OWNER_ID_FK = " 
                + vadfOwnerId + " AND ( VADF_ID_FK = " + OI_SYN + " )";

        try {
            PreparedStatement pstmt5 = con.prepareStatement(query5);
            ResultSet rs5 = pstmt5.executeQuery(); //NOPMD
            DataFields<DataField> oiVadf = orderableItemVo.getVaDataFields();

            while (rs5.next()) {
                Long val1 = rs5.getLong(1);

                if (OI_SYN.equals(val1.toString())) {
                    String val2 = rs5.getString(2);
                    oiVadf.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM).addStringSelection(val2);
                }

            } // end while next

            orderableItemVo.setVaDataFields(oiVadf);
            rs5.close();
            pstmt5.close();
        } catch (Exception e) {
            logQueryError(longVal, "5", e);
        }

        try {
            String query6 =
                "SELECT DT.DRUG_TEXT_NAME" 
                    + " FROM EPL_DRUG_TEXT DT, EPL_OI_DRUG_TEXT_N_ASSOCS ODT, EPL_ORDERABLE_ITEMS OI" 
                    + " WHERE DT.EPL_ID = ODT.DRUG_TEXT_ID_FK AND OI.EPL_ID = ODT.EPL_ID_OI_FK AND OI.EPL_ID = "
                    + orderableItemId;

            PreparedStatement pstmt6 = con.prepareStatement(query6);
            ResultSet rs6 = pstmt6.executeQuery(); //NOPMD
            List<DrugTextVo> drugTextList = new ArrayList<DrugTextVo>();

            while (rs6.next()) {
                DrugTextVo dtVo = new DrugTextVo();
                dtVo.setValue(rs6.getString(1));
                drugTextList.add(dtVo);
            }

            orderableItemVo.setNationalDrugTexts(drugTextList);
            pstmt6.close();
            rs6.close();
        } catch (Exception e) {
            logQueryError(longVal, "6", e);
        }

        try {
            con.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        return orderableItemVo;
    }

    /**
     * logQueryError
     *
     * @param longVal Long
     * @param queryNum String
     * @param e Exception
     */
    private void logQueryError(Long longVal, String queryNum, Exception e) {
        StringBuffer errorMsg =
            new StringBuffer("Error for ").append(longVal).append(" Process Query").append(queryNum).append(": ")
                .append(e.getMessage());

        if (e.getCause() != null) {
            errorMsg.append(". Cause is ").append(e.getCause().getMessage());
        }

        LOG.error(errorMsg);
    }

    /**
     * extractOiMedRoute
     *
     * @param rsMedRoute ResultSet
     * @param orderableItemVo OrderableItemVo
     * @throws SQLException exception
     */
    private void extractOiMedRoute(ResultSet rsMedRoute, OrderableItemVo orderableItemVo) throws SQLException {

        //  extract data from the ResultSet
        while (rsMedRoute.next()) {
            StandardMedRouteVo vo = new StandardMedRouteVo();
            vo.setValue(rsMedRoute.getString(1));
            orderableItemVo.setStandardMedRoute(vo);
        }
    }

    /**
     * extractOrderableItem
     *
     * @param rs1 ResultSet
     * @param orderableItemVo OrderableItemVo
     * @throws SQLException exception
     */
    private void extractOrderableItem(ResultSet rs1, OrderableItemVo orderableItemVo) throws SQLException {
        while (rs1.next()) {
            orderableItemVo.setOiName(rs1.getString(1));
            orderableItemVo.setVistaOiName(rs1.getString(2));

            if (YES.equals(rs1.getString(PPSConstants.I3))) {
                orderableItemVo.setNationalFormularyIndicator(true);
            } else {
                orderableItemVo.setNationalFormularyIndicator(false);
            }

            orderableItemVo.setInactivationDate(rs1.getDate(PPSConstants.I4));

            if (YES.equals(rs1.getString(PPSConstants.I5))) {
                orderableItemVo.setNonVaMed(true);
            } else {
                orderableItemVo.setNonVaMed(false);
            }

            orderableItemVo.setRejectionReasonText(rs1.getString(PPSConstants.I6));

            String rrs = rs1.getString(PPSConstants.I7);

            if (StringUtils.isNotBlank(rrs)) {
                if ("INAPPROPRIATE_REQUEST".equals(rrs)) {
                    orderableItemVo.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
                } else if ("INCOMPLETE_INFORMATION".equals(rrs)) {
                    orderableItemVo.setRequestRejectionReason(RequestRejectionReason.INCOMPLETE_INFORMATION);
                } else if ("ITEM_EXISTS".equals(rrs)) {
                    orderableItemVo.setRequestRejectionReason(RequestRejectionReason.ITEM_EXISTS);
                }
            }

            DosageFormVo dosageForm = new DosageFormVo();
            dosageForm.setDosageFormName(rs1.getString(PPSConstants.I8));
            orderableItemVo.setDosageForm(dosageForm);

            List<Category> cat = new ArrayList<Category>();

            if (YES.equals(rs1.getString(PPSConstants.I9))) {
                cat.add(Category.COMPOUND);
            }

            if (YES.equals(rs1.getString(PPSConstants.I10))) {
                cat.add(Category.INVESTIGATIONAL);
            }

            if (YES.equals(rs1.getString(PPSConstants.I11))) {
                cat.add(Category.MEDICATION);
            }

            if (YES.equals(rs1.getString(PPSConstants.I12))) {
                cat.add(Category.SUPPLY);
            }

            orderableItemVo.setCategories(cat);

        } // end while
    }

    /**
     * extractOiVadf
     *
     * @param rs3 ResultSet
     * @param orderableItemVo OrderableItemVo
     * @param oiVadf DataFields
     * @throws SQLException exception
     */
    @SuppressWarnings("rawtypes")
    private void extractOiVadf(ResultSet rs3, OrderableItemVo orderableItemVo, DataFields<DataField> oiVadf)
        throws SQLException {

        while (rs3.next()) {
            Long val1 = rs3.getLong(1);

            if (LIFETIME_CUM_DOSE.equals(val1.toString())) {
                String val2 = rs3.getString(2);

                if (TRUE.equals(val2)) {
                    oiVadf.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE).selectValue(true);
                } else if ("false".equals(val2)) {
                    oiVadf.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE).selectValue(false);
                }
            } else if (OTHER_LANG_INST.equals(val1.toString())) {
                String val2 = rs3.getString(2);
                oiVadf.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS).selectValue(val2);
            } else if (PATIENT_INST.equals(val1.toString())) {
                String val2 = rs3.getString(2);
                oiVadf.getDataField(FieldKey.PATIENT_INSTRUCTIONS).selectValue(val2);
            } else if (DDL.equals(val1.toString())) {
                String val2 = rs3.getString(2);
                oiVadf.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT).selectValue(val2);
            } else if (PPOPINACT.equals(val1.toString())) {
                String val2 = rs3.getString(2);
                Long proLong = Long.valueOf(val2);
                Date dt = new Date(proLong.longValue());
                oiVadf.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(dt);
            } else if (IV_FLAG.equals(val1.toString())) {
                String val2 = rs3.getString(2);

                if (TRUE.equals(val2)) {
                    oiVadf.getDataField(FieldKey.OI_IV_FLAG).selectValue(true);
                } else {
                    oiVadf.getDataField(FieldKey.OI_IV_FLAG).selectValue(false);
                }
            }

        } // end while next
    }

    /**
     * getIdsPropInact gets just the EPL_IDs from the EPL table.
     * 
     * @param type is the entity type for the ids
     * @return The list of Ids
     */
    @Override
    public List<Long> getIdsPropInact(EntityType type) {

        Connection con = null;
        List<Long> list = new ArrayList<Long>();
        PreparedStatement pstmt = null;

        con = getJNDIConnection();

        // create an instance of the connection for getIdsPropInact
        if (con == null) {
            con = getSimpleConnection();
        }

        if (con == null) {
            LOG.error(MSG_CONN_NULL);
        } else {

            String query = "";

            if (EntityType.NDC.equals(type)) {
                query =
                    "select NDC.EPL_ID" 
                        + " from EPL_NDCS NDC, EPL_VADF_OWNERS OWNER, EPL_VADF_NONLIST_VALUES NON_LIST" 
                        + " WHERE OWNER.EPL_ID_NDC_FK = NDC.EPL_ID AND NDC.ITEM_STATUS = 'ACTIVE'" 
                        + " AND OWNER.ID = NON_LIST.VADF_OWNER_ID_FK AND VADF_ID_FK = " + PPOPINACT;
            } else if (EntityType.PRODUCT.equals(type)) {
                query =
                    "select PRODUCT.EPL_ID" 
                        + " from EPL_PRODUCTS PRODUCT, EPL_VADF_OWNERS OWNER, EPL_VADF_NONLIST_VALUES NON_LIST" 
                        + " WHERE OWNER.EPL_ID_PRODUCT_FK = PRODUCT.EPL_ID AND PRODUCT.ITEM_STATUS = 'ACTIVE'" 
                        + " AND OWNER.ID = NON_LIST.VADF_OWNER_ID_FK AND VADF_ID_FK = " + PPOPINACT;
            } else if (EntityType.ORDERABLE_ITEM.equals(type)) {
                query =
                    "select OI.EPL_ID" 
                        + " from EPL_ORDERABLE_ITEMS OI, EPL_VADF_OWNERS OWNER, EPL_VADF_NONLIST_VALUES NON_LIST" 
                        + " WHERE OWNER.EPL_ID_OI_FK = OI.EPL_ID AND OI.ITEM_STATUS = 'ACTIVE'" 
                        + " AND OWNER.ID = NON_LIST.VADF_OWNER_ID_FK AND VADF_ID_FK = " + PPOPINACT;
            } else {
                return null;
            }

            ResultSet rs = null;

            try {
                pstmt = con.prepareStatement(query); // create a statement
                rs = pstmt.executeQuery();

                // extract data from the ResultSet
                while (rs.next()) {
                    try {
                        list.add(rs.getLong(1));
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {
                    LOG.debug("Exception closing RS " + e.toString());
                }

                try {
                    pstmt.close();
                } catch (Exception e) {
                    LOG.debug("Exception closing prepared statement " + e.toString());
                }

                try {
                    con.close();
                } catch (Exception e) {
                    LOG.debug("Exception closing Connection " + e.toString());
                }
            }
        }

        return list;

    }

    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param type
     *            is the entity type for the ids
     * @param activeOnly true if only active records are requested.
     * @return The list of Ids
     */
    @Override
    public List<Long> getIds(EntityType type, boolean activeOnly) {
        Connection con = null;
        List<Long> list = new ArrayList<Long>();
        PreparedStatement pstmt = null;

        con = getJNDIConnection();

        if (con == null) {
            con = getSimpleConnection();
        }

        if (con == null) {
            LOG.error(MSG_CONN_NULL);
        } else {
            StringBuffer query = new StringBuffer("Select EPL_ID from ");

            if (EntityType.PRODUCT.equals(type)) {
                query.append("EPL_PRODUCTS");
            } else if (EntityType.NDC.equals(type)) {
                query.append("EPL_NDCS");
            } else if (EntityType.ORDERABLE_ITEM.equals(type)) {
                query.append("EPL_ORDERABLE_ITEMS");
            } else {
                return null;
            }

            if (activeOnly) {
                query.append(" WHERE  ITEM_STATUS LIKE 'ACTIVE'");
            }

            ResultSet rs = null;

            try {
                pstmt = con.prepareStatement(query.toString()); // create a statement
                rs = pstmt.executeQuery();

                // extract data from the ResultSet
                while (rs.next()) {
                    long value = rs.getLong(1);
                    list.add(Long.valueOf(value));
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            } finally {
                try {
                    rs.close();
                    pstmt.close();
                    con.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

        return list;
    }

    /**
     * reset the database runs the refresh database script to reset all the data
     * to the time before the migration process started
     * @param iRun the database number
     * @return true if successful
     * 
     */
    @Override
    public boolean resetDatabase(int iRun) {
        boolean success = false;

        if (iRun < 0 || iRun > PPSConstants.I15) {
            LOG.error("in the resetbase method the run value is " + iRun + " and it needs to be between 1 and 15");

            return false;
        }

        Connection con = null; //NOPMD
        String[] inst;

        // Get the sql statements from the sql log
        inst = getData(iRun);

        if (isWindows) {
            con = getSimpleConnection();
        } else {
            con = getJNDIConnection();
        }

        if (con == null) {
            LOG.error("Connection is null");

            return false;
        }

        if (inst == null) {
            LOG.error("inst is null");

            return false;
        }

        success = loadData(con, inst);

        return success;
    }

    /**
     * Uses JNDI and Datasource (preferred style).
     * 
     * @return Connection
     */
    private Connection getJNDIConnection() {

        // Obtain our environment naming context
        Context initCtx;
        Connection conn = null; //NOPMD

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
            LOG.error("Cannot get connection: " + ex);
        } catch (SQLException ex) {
            LOG.error("Cannot get connection: " + ex);
        }

        return conn;
    }

    /**
     * Uses DriverManager.
     * 
     * @return connection
     */
    private Connection getSimpleConnection() {

        // See your driver documentation for the proper format of this string :
        String dbConn = "jdbc:derby://localhost:1527/NationalEPL;create=true";

        // Provided by your driver documentation. In this case, a MySql driver
        // is used :
        String driverClassName = "org.apache.derby.jdbc.ClientDriver";
        String userName = "ppsnepl";
        String pass = "pharmacy";

        Connection result = null; //NOPMD

        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception ex) {
            LOG.error("Check classpath. Cannot load db driver: " + driverClassName);
        }

        try {
            result = DriverManager.getConnection(dbConn, userName, pass);
        } catch (SQLException e) {
            LOG.error("Driver loaded, but cannot connect to db: " + dbConn);
        }

        return result;
    }

    /**
     * retrieve the SQK commands from the sql file
     * @param iRun the number of the sql file
     * @return String array
     */
    private String[] getData(int iRun) {

        String s = new String();
        StringBuffer sb = new StringBuffer();

        try {
            String currentDir = System.getProperty("user.dir");
            String fileName = "";
            boolean devEnvironment = false;

            if (isWindows) {
                devEnvironment = true;
            }

            if (devEnvironment) {
                fileName =
                    currentDir + "\\..\\PS_PPS_domain\\etc\\database\\data\\migration\\PPSNEPLRefreshDB" + iRun + ".sql";
            } else {
                fileName = currentDir + "/config/PPSNEPLRefreshDB" + iRun + ".sql";
            }

            LOG.debug("filename is " + fileName);

            FileReader fr = new FileReader(new File(fileName));
            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {

                // Remove the empty rows are the ones that are comment lines
                if (!((s.contains("/*") || s.contains("<!--") || StringUtils.isEmpty(s)))) {
                    sb.append(s);
                }
            }

            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            return inst;
        } catch (Exception e) {
            LOG.error("*** Error : " + e.toString());
            LOG.error("*** ");
            LOG.error("*** Error : ");
            LOG.error("################################################");
            LOG.error(sb.toString());

            return null;
        }
    }

    /**
     * loadDAta
     * @param c Connaction
     * @param inst sql commands
     * @return true if successful
     */
    private boolean loadData(Connection c, String[] inst) {

        if (isWindows) {
            LOG.debug("Bypassing load: first line is " + inst[0]);

            return true; // don't actually run the commands in the dev box.
        }

        String lastStatement = "";

        try {
            Statement st = c.createStatement(); //NOPMD

            for (int i = 0; i < inst.length; i++) {

                // we ensure that there is no spaces before or after the request string in order to not execute empty statements
                if (!inst[i].trim().equals("")) {
                    lastStatement = inst[i];
                    st.executeUpdate(inst[i]);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception processing statement: " + lastStatement);
            LOG.error("*** Error : " + e.toString());
            LOG.error("################################################");

            return false;
        }

        return true;
    }
}
