/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceAutoCapability;




/**
 * DrugReferenceAutoCapability encapsulates the FDB behavior for the auto update code.
 * It has a method for the newly added entries in FDB and a method for the newly updated entries.
 *
 */
public class DrugReferenceAutoCapabilityImpl implements DrugReferenceAutoCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    .getLogger(DrugReferenceAutoCapabilityImpl.class);
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyyMMdd", Locale.US);
    
    /**
     * getFdbAddedItems
     * @param startDate startDate
     * @return List<String>
     */
    @Override
    public List<FdbAutoAddVo> getFdbAddedItems(Date startDate) {

        Connection con = getConnection();
        List<FdbAutoAddVo> ndcList = new ArrayList<FdbAutoAddVo>();
        PreparedStatement pstmt = null;
        ResultSet rsAdd = null;

        if (con == null) {
            LOG.error("getFdbAddedItems: get Connection is null");

            return ndcList;
        }

        try {

            //  StringBuffer query = new StringBuffer();

            String query = "select PMID, GCNSEQNO, "
                    + "ADDDATE FROM FDB_PACKAGEDDRUG WHERE to_date (ADDDATE,'YYYYMMDD') between to_date("
                    + dateFormat.format(startDate)
                    + ",'YYYYMMDD') and to_date("
                    + dateFormat.format(new Date())
                    + ",'YYYYMMDD')";

            LOG.error("Logging at Error Level: " + query);
            pstmt = con.prepareStatement(query);
            rsAdd = pstmt.executeQuery();

            while (rsAdd.next()) {
                FdbAutoAddVo vo = new FdbAutoAddVo();
                vo.setNdc(rsAdd.getString(1));
                vo.setGcnSeqno(rsAdd.getLong(2));
                vo.setAddDate(rsAdd.getString(PPSConstants.I3));
                ndcList.add(vo);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                rsAdd.close();
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }

        LOG.error("Logging Debut statement at Error Level: Returning " + ndcList.size() + " items.");
        
        return ndcList;
    }

    /**
     * getFdbUpdatedItems
     * @param startDate startDate
     * @return List<String>
     */
    @Override
    public List<FdbAutoUpdateVo> getFdbUpdatedItems(Date startDate) {

        Connection con = getConnection();
        List<FdbAutoUpdateVo> ndcList = new ArrayList<FdbAutoUpdateVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (con == null) {
            LOG.error("getFdbUpdatedItems: get Connection is null");

            return ndcList;
        }

        try {

            String query = "select PMID, FORMATCODE, GCNSEQNO, UPDATEDATE FROM FDB_PACKAGEDDRUG WHERE to_date "
                + "(UPDATEDATE,'YYYYMMDD') between to_date("
                + dateFormat.format(startDate) + ",'YYYYMMDD')  and to_date("
                + dateFormat.format(new Date()) + ",'YYYYMMDD') ";

            LOG.debug(query);
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                FdbAutoUpdateVo vo = new FdbAutoUpdateVo();
                vo.setNdc(rs.getString(1));
                vo.setFormatIndicator(rs.getString(PPSConstants.I2));
                vo.setGcnSequenceNumber(String.valueOf(rs.getLong(PPSConstants.I3)));
                vo.setUpdateDate(rs.getString(PPSConstants.I4));
                ndcList.add(vo);
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

        return ndcList;
    }
    
    /**
     * getFdbUpdateDate
     * @return String UpdateDate
     */
    @Override
    public String getFdbUpdateDate() {

        Connection con = getConnection();
        List<FdbAutoUpdateVo> ndcList = new ArrayList<FdbAutoUpdateVo>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (con == null) {
            LOG.error("getFdbUpdatedItems: get Connection is null");

            return "";
        }

        String version = "";
        String issueDate = "";
        
        try {

            String query = "select DBVERSION, ISSUEDATE FROM FDB_VERSION";
            
            LOG.debug(query);
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            // extract data from the ResultSet
            while (rs.next()) {
                version = rs.getString(1);
                issueDate = rs.getString(2);
                
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

        try {
            Date date = dateFormat.parse(issueDate);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US);
            issueDate = formatter.format(date);
          
        } catch (ParseException e) {
            LOG.debug(e.getMessage());
        }
        
        return "FDB Version " + version + " - Issue Date: " + issueDate;
    }
    
    
    /**
     * getConnection
     * 
     * @return Connection;
     */
    private Connection getConnection() {

        Connection conn = getJNDIConnection();

        if (conn == null) {
            conn = getSimpleConnection();
        }

        return conn;
    }

    /**
     * Uses JNDI and Datasource (preferred style).
     * 
     * @return Connection
     */
    private Connection getJNDIConnection() {

        // Obtain our environment naming context
        Context initCtx;
        Connection conn = null;

        try {
            ConfigFileUtility cfu = new ConfigFileUtility();
            initCtx = new InitialContext();
            Hashtable<String, String> ht = new Hashtable<String, String>();

            ht.put(Context.INITIAL_CONTEXT_FACTORY,
                    "weblogic.jndi.WLInitialContextFactory");

            ht.put(Context.PROVIDER_URL, "t3://localhost:" + cfu.getPort());

            initCtx = new InitialContext(ht);

            // Lookup this DataSouce at the top level of the WebLogic JNDI tree
            DataSource datasource = (DataSource) initCtx
                    .lookup("datasource.FDB-DIF");

            conn = datasource.getConnection();

        } catch (NamingException ex) {
            LOG.error("NamingException in getting connection: " + ex);
        } catch (SQLException ex) {
            LOG.error("SQL Exception in getting connection: " + ex);
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
        String dbConn = "jdbc:oracle:thin:@//PRE-EPL-DB:1522/eplnat.dyn.datasys.swri.edu";

        // Provided by your driver documentation. In this case, a MySql driver
        // is used :
        String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String userName = "FDB_DIF";
        String pass = "fdb_dif123";

        Connection result = null;

        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception ex) {
            LOG.error("Check classpath. Cannot load db driver: "
                    + driverClassName);
        }

        try {
            result = DriverManager.getConnection(dbConn, userName, pass);
        } catch (SQLException e) {
            LOG.error("Driver loaded, but cannot connect to db: " + dbConn);
        }

        return result;
    }
}
