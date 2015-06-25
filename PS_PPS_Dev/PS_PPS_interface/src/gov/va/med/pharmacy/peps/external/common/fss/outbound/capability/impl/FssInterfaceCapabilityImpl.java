/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.impl;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.external.common.fss.outbound.capability.FssInterfaceCapability;



/**
 * FssInterfaceCapabilityImpl
 *
 */
public class FssInterfaceCapabilityImpl implements FssInterfaceCapability {
   
    private static final Logger LOG = Logger.getLogger(FssInterfaceCapabilityImpl.class);
    private boolean isWindows = false;


    /**
     * FssInterfaceCapabilityImpl
     */
    public FssInterfaceCapabilityImpl() {
        super();
        
        String osName = System.getProperty("os.name");

        if (osName.contains("win") || osName.contains("Win")) {
            isWindows = true;
        } else {
            isWindows = false;
        }
    }
    

    /**
     * This method 
     * @param lastRun lastRun
     * @return map
     */
    public Map<String, String> getNdcsToUpdate(String lastRun) {
       
        Map<String, String> ndcList = new HashMap<String, String>();
        
        Connection con = getConnection();

        if (con == null) {
            LOG.error("Exception getting connection for FSS!");
        }

        // Declare the JDBC objects.
        Statement stmt = null;
        ResultSet rs = null;

        try {
            
            // CHECK the DI_DRUGITEMNDC table
            StringBuffer query = new StringBuffer();
            query.append("select FDAASSIGNEDLABELERCODE, PRODUCTCODE, PACKAGECODE FROM DI_DRUGITEMNDC ").append(
                " WHERE LastModificationDate > '").append(lastRun).append("' OR CREATIONDATE > '").append(lastRun).append("'"); 

            LOG.debug(query);
            String sql = query.toString();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            
            // Now we need to Iterate through the data in the result set and display it.
            while (rs.next()) {
                String str = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(PPSConstants.I3);
                ndcList.put(str, str);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception c) {
                LOG.error("getNdcsToUpdate: Exception closing rs or statement " + c.toString());
            }
        }
            
        try {
            
            // CHECK THE DI_CONTRACT table.
            StringBuffer query = new StringBuffer();
            query.append("SELECT NDC.FDAASSIGNEDLABELERCODE, NDC.PRODUCTCODE, NDC.PACKAGECODE DI_DRUGITEMNDC ")
                .append(" FROM DI_CONTRACTS CONTRACTS, DI_DRUGITEMS ITEM, DI_DRUGITEMNDC NDC")
                .append(" WHERE ( CONTRACTS.LASTMODIFICATIONDATE > '" + lastRun + "' OR CONTRACTS.CREATIONDATE > '")
                .append(lastRun).append("')").append(" AND CONTRACTS.DRUGITEMID = ITEM.DRUGITEMID AND ")
                .append(" ITEM.DRUGITEMNDCID = NDC.DRUGITEMNDCID ");

            LOG.debug("Query for DI CONTRACT is " + query);
            String sql = query.toString();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
    
            // Iterate through the data in the result set and display it for the contract solution
            while (rs.next()) {
                String str = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(PPSConstants.I3);
                ndcList.put(str, str);
                LOG.debug("NDC found to update: " + str);
            }
        } catch (Exception e) {
            
            // Log the error for the DI_Contract exception
            LOG.error(e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception c) {
                LOG.error("Exception closing rs or statement " + c.toString());
            }
        }

        // Retrieve from the E DI_DRUGITEMNDC table.
        try {
            StringBuffer query = new StringBuffer();
            query.append("select NDC.FDAASSIGNEDLABELERCODE, NDC.PRODUCTCODE, NDC.PACKAGECODE ")
                .append(" FROM DI_DRUGITEMPRICE  PRICE, DI_DRUGITEMS ITEM, DI_DRUGITEMNDC NDC")
                .append(" WHERE ( PRICE.LASTMODIFICATIONDATE > '").append(lastRun).append("' OR PRICE.CREATIONDATE > '")
                .append(lastRun).append("')").append(" AND PRICE.DRUGITEMID = ITEM.DRUGITEMID AND ")
                .append(" ITEM.DRUGITEMNDCID = NDC.DRUGITEMNDCID ");

            // query for DI_DRUGITEMPRICE
            LOG.debug(query);
            String sql = query.toString();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
    
            // Iterate through the data in the result set and display it for DI_DRUGITEMPRICE.
            while (rs.next()) {
                String str = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(PPSConstants.I3);
                ndcList.put(str, str);
                LOG.debug("NDC found to update: " + str);
            }
        } catch (Exception e) {
            
            //for DI_DRUGITEMPRICE
            LOG.error(e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception c) {
                LOG.error("Exception closing rs or statement " + c.toString());
            }
        }
        
        try {
            
            // CHECK THE DI_DRUGITEM table.
            StringBuffer query = new StringBuffer();
            query.append("select NDC.FDAASSIGNEDLABELERCODE, NDC.PRODUCTCODE, NDC.PACKAGECODE DI_DRUGITEMNDC "
                + " FROM DI_DRUGITEMS item, DI_DRUGITEMNDC ndc"
                + " WHERE ( ITEM.LastModificationDate > '" + lastRun + "' OR ITEM.CREATIONDATE > '" + lastRun + "')" 
                + " AND item.DRUGITEMNDCID = ndc.DRUGITEMNDCID "); 

            LOG.debug(query);
            String sql = query.toString();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
    
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                String str = rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(PPSConstants.I3);
                ndcList.put(str, str);
                LOG.debug("NDC found to update: " + str);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception c) {
                LOG.error("Exception closing rs or statement " + c.toString());
            }
        }
       
        try {
            con.close();
        } catch (Exception c) {
            LOG.error("Exception closing connection " + c.toString());
        }
        
        return ndcList;
    }
    
    /**
     * getFssData.
     * @param ndcVo NdcVo
     */
    @Override
    public void getFssData(NdcVo ndcVo) {
        
        Connection con = getConnection();
      
        // Declare the JDBC objects.
        Statement stmt = null;
        ResultSet rs = null;
        Statement stmt2 = null;
        ResultSet rs2 = null;
        
        try {
            String ndc = ndcVo.getNdc().replaceAll("-", "");
            
            // QUERY 1
            StringBuffer query = new StringBuffer();
            query.append("select  DRUGITEMNDCID FROM DI_DRUGITEMNDC WHERE FdaAssignedLabelerCode LIKE '" 
                         + ndc.substring(0, PPSConstants.I5)
                         + "' AND ProductCode LIKE '" 
                         + ndc.substring(PPSConstants.I5, PPSConstants.I9)
                         + "' AND PackageCode LIKE '"
                         + ndc.substring(PPSConstants.I9, PPSConstants.I11) + "'");

            LOG.debug(query);
            String sql = query.toString();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
    
            Integer drugItemNdcId = null;
            
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                drugItemNdcId = rs.getInt(1);
    
                if (drugItemNdcId == null) {
                    LOG.debug("drugItemNdcId is null");
                } else {
                
                    //QUERY2
                    StringBuffer query2 = new StringBuffer();
                    query2.append("select DRUGITEMID, CONTRACTID, COVERED, PRIMEVENDOR FROM DI_DRUGITEMS "
                                  + "WHERE DrugItemNDCID = "
                                  + drugItemNdcId);

                    String sql2 = query2.toString();
                    LOG.debug("Query2 is " + sql2);
                    stmt2 = con.createStatement();
                    rs2 = stmt2.executeQuery(sql2);
                    Integer drugItemId = null;
                    Integer contractId = null;

                    // Iterate through the data in the result set and display it.
                    while (rs2.next()) {
                    
                        drugItemId = rs2.getInt(1);
                        contractId = rs2.getInt(2);
                        ndcVo.setFssI(rs2.getString(PPSConstants.I3));

                        if ("T".equals(rs2.getString(PPSConstants.I4))) {
                            ndcVo.setFssPv(true);
                        } else {
                            ndcVo.setFssPv(false);
                        }
                    }
                    
                    //QUERY3
                    //  //private String fssCntNo;
                    StringBuffer query3 = new StringBuffer();
                    query3.append("select ContractNumber from DI_Contracts where ContractId = " + contractId);

                    String sql3 = query3.toString();
                    rs2 = stmt2.executeQuery(sql3);

                    // Iterate through the data in the result set and display it.
                    while (rs2.next()) {
                        ndcVo.setFssCntNo(rs2.getString(1));
                    }
                    
                    //QUERY4
                    // private Date fssFssEnd;
                    // private Double fssVaPrice;
                    // private Double fssBig4Price;
                    // private Double fssFssPrice;
                    StringBuffer query4 = new StringBuffer();
                    query4
                        .append("select isFss, isBig4, isVa, pricestopdate, price from DI_DRUGITEMPRICE where DrugItemId = "
                                      + drugItemId);

                    String sql4 = query4.toString();
                    rs2 = stmt2.executeQuery(sql4);

                    // Iterate through the data in the result set and display it.
                    while (rs2.next()) {
                        if (rs2.getBoolean(1)) {
                            ndcVo.setFssFssPrice(rs2.getDouble(PPSConstants.I5));
                            ndcVo.setFssFssEnd(rs2.getDate(PPSConstants.I4));
                        }

                        if (rs2.getBoolean(2)) {
                            ndcVo.setFssBig4Price(rs2.getDouble(PPSConstants.I5));
                        }

                        if (rs2.getBoolean(PPSConstants.I3)) {
                            ndcVo.setFssVaPrice(rs2.getDouble(PPSConstants.I5));
                        }
                        
                        if ("F".equalsIgnoreCase(ndcVo.getFssI())) {
                            ndcVo.setFssNcPrice(rs2.getDouble(PPSConstants.I5));
                        }
                        
                        if (ndcVo.getFssCntNo() != null) {
                            if (ndcVo.getFssCntNo().startsWith("797-FSSBPA-")) {
                                ndcVo.setFssBpaAvail(true);
                                ndcVo.setFssBpaPrice(rs2.getDouble(PPSConstants.I5));
                            }
                        }
                    }
                    
                 
                }
            } // end while

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                con.close();
                rs.close();
                stmt.close();

                if (stmt2 != null) {
                    stmt2.close();
                }
                
                if (rs2 != null) {
                    rs2.close();
                }
                
               
            } catch (Exception c) {
                LOG.error("Exception closing fsstest cases " + c.toString());

            }
        }
    }
    
    /**
     * getConnection
     * @return Connection
     */
    private Connection getConnection() {
        
        Connection con;
        
        if (isWindows) {
            con = getSimpleConnection();
        } else {
            con = getJNDIConnection();
        }

        if (con == null) {
            LOG.error("Connection is null");
        }

        return con;
        
    }
    
    /**
     * Uses JNDI and Datasource (preferred style).
     * 
     * @return Connection
     */
    private Connection getJNDIConnection() {

        // Obtain our environment naming context for the FssInterfaceCapablitytest
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
            DataSource datasource = (DataSource) initCtx.lookup("datasource.PRE-FSS");

            conn = datasource.getConnection();

        } catch (NamingException ex) {
            LOG.error("Cannot get connection:  " + ex);
        } catch (SQLException ex) {
            LOG.error("Cannot get connection: " + ex);
        }

        return conn;
    }

    /**
     * Uses DriverManager for FssInterfaceCapablityImpl.
     * 
     * @return connection
     */
    private Connection getSimpleConnection() {

        Connection con = null;
        
        // Create a variable for the connection string.
        String connectionUrl = 
            "jdbc:sqlserver://ADE-VM1\\PRE;" 
                + "DatabaseName=PRE_FSS_DLL;user=sa;password=Passw0rd1";

        try {
            
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            LOG.error("Driver loaded, but cannot connect to db: " + connectionUrl);
        } catch (ClassNotFoundException e) {
            LOG.error(e.toString());
        }

        return con;
    }

    
}

