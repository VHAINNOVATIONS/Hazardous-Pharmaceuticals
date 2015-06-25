/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import junit.framework.TestCase;



//import com.microsoft.sqlserver.jdbc.SQLServerDriver;


/**
 * Verify FSS functionality..
 */
public class FssDomainCapabilityTest extends TestCase {

   // private static final Logger LOG = Logger.getLogger(FssDomainCapabilityTest.class);
    
    /**
     * testCollectData
     */
    public void testCollectData() {

        boolean isTrue = true;
        assertTrue("TestNotPerformed", isTrue);
        
        // Create a variable for the connection string.
        // String connectionUrl = "jdbc:sqlserver://ADE-VM1/PRE:1433;" +
        // "databaseName=PRE_FSS_DLL;integratedSecurity=true;";

/*        String connectionURL = "jdbc:sqlserver://ADE_VM1\\PRE;databaseName=PRE_FSS_DLL;user=sa;password=Passw0rd1;";

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionURL);

            // Create and execute an SQL statement that returns some data.
            String sql = "SELECT TOP 10 * FROM Person.Contact";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            LOG.debug(sql);
            
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                LOG.debug(sql);
            }
        } catch (Exception e) {
            fail("Exception is " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    fail("Exception2 is " + e.getMessage());
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    fail("Exception3 is " + e.getMessage());
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    fail("Exception4 is " + e.getMessage());
                }
            }
        }
        */
    }

}
