/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.nddfloader;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.StringUtils;


/**
 * Command line tool to load NDDF DDL and data.
 */
public class NddfLoader {

    private static final String CONNECTION_PROPERTIES_PATH = "connection.properties";
    private static final String DRIVER_PROPERTY = "driver";
    private static final String URL_PROPERTY = "url";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    private static final String DDL_FILE = "NDDF PLUS DDL.ZIP";
    private static final String DATA_LOAD_FILE = "NDDF PLUS DB.ZIP";

    private static final String ANSI_SQL_DDL_FILE = "ANSI.SQL";
    private static final String SQL_COMMENT_PREFIX = "--";
    private static final String DATA_FILE_DELIMITER = "\\|";

    private static Map<String, Integer> SQL_TYPES = new HashMap<String, Integer>();

    private JdbcTemplate jdbcTemplate;
    private PrintStream log;
    private Map<String, int[]> tableNameToSqlTypes = new HashMap<String, int[]>();


    /**
     * Initialize the DataSource for the Spring JDBC template.
     * @throws FileNotFoundException 
     */
    public NddfLoader() throws FileNotFoundException {
        super();

        this.log = new PrintStream(new FileOutputStream("NddfLoader.log"), true);

        SQL_TYPES.put("CHAR", new Integer(Types.CHAR));
        SQL_TYPES.put("VARCHAR", new Integer(Types.VARCHAR));
        SQL_TYPES.put("LONGVARCHAR", new Integer(Types.LONGVARCHAR));
        SQL_TYPES.put("NUMERIC", new Integer(Types.NUMERIC));
        SQL_TYPES.put("DECIMAL", new Integer(Types.DECIMAL));
        SQL_TYPES.put("BIT", new Integer(Types.BIT));
        SQL_TYPES.put("TINYINT", new Integer(Types.TINYINT));
        SQL_TYPES.put("SMALLINT", new Integer(Types.SMALLINT));
        SQL_TYPES.put("INTEGER", new Integer(Types.INTEGER));
        SQL_TYPES.put("BIGINT", new Integer(Types.BIGINT));
        SQL_TYPES.put("REAL", new Integer(Types.REAL));
        SQL_TYPES.put("FLOAT", new Integer(Types.FLOAT));
        SQL_TYPES.put("DOUBLE", new Integer(Types.DOUBLE));
        SQL_TYPES.put("BINARY", new Integer(Types.BINARY));
        SQL_TYPES.put("VARBINARY", new Integer(Types.VARBINARY));
        SQL_TYPES.put("LONGVARBINARY", new Integer(Types.LONGVARBINARY));
        SQL_TYPES.put("DATE", new Integer(Types.DATE));
        SQL_TYPES.put("TIME", new Integer(Types.TIME));
        SQL_TYPES.put("TIMESTAMP", new Integer(Types.TIMESTAMP));
    }

    /**
     * Run the NDDF Loader application from the command line.
     * 
     * @param args command line arguments
     * @throws Exception if an error occurs while processing the data load
     */
    public static void main(String[] args) throws Exception {
        NddfLoader nddfLoader = new NddfLoader();
        nddfLoader.setJdbcTemplate(new JdbcTemplate(nddfLoader.initializeDataSource()));

        nddfLoader.executeDdl();
        nddfLoader.loadData();

        nddfLoader.log.close();
    }

    /**
     * Method called by the command line implementation which
     * loads a data source for use by the Spring JDBC Template.
     * 
     * @return DataSource
     * @throws IOException if the properties file containing the JDBC connection info cannot be loaded
     */
    private DataSource initializeDataSource() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONNECTION_PROPERTIES_PATH));

        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(properties.getProperty(DRIVER_PROPERTY));
        dataSource.setUrl(properties.getProperty(URL_PROPERTY));
        dataSource.setUsername(properties.getProperty(USERNAME_PROPERTY));
        dataSource.setPassword(properties.getProperty(PASSWORD_PROPERTY));

        return dataSource;
    }

    /**
     * Load the NDDF data using the default path to the data ZIP file.
     * 
     * @throws IOException if error reading data ZIP file
     */
    public void loadData() throws IOException {
        loadData(DATA_LOAD_FILE);
    }

    /**
     * Load the NDDF data using the provided path to the data ZIP file.
     * 
     * @param dataFile String path on the application's class path to the data ZIP file
     * @throws IOException if error reading data ZIP file
     */
    public void loadData(String dataFile) throws IOException {
        ZipFile file = new ZipFile(dataFile);

        Enumeration entries = file.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            if (!entry.isDirectory()) {
                int startIndex = entry.getName().lastIndexOf("\\");

                if (startIndex == -1) {
                    startIndex = entry.getName().lastIndexOf("/");
                }

                String tableName = entry.getName().substring(startIndex + 1, entry.getName().indexOf("_"));

                long start = System.currentTimeMillis();

                BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream(entry)));

                String line = in.readLine();
                long rows = 0;

                while (line != null) {
                    Object[] fields = line.split(DATA_FILE_DELIMITER, StringUtils.countOccurrencesOf(line, "|") + 1);
                    String sql = createInsertSql(tableName, fields.length);
                    int[] types = getTypes(tableName);

                    try {
                        rows += jdbcTemplate.update(sql, formatDates(fields, types));
                    } catch (Throwable t) {
                        log.println("**********************************************************************");
                        log.println("Error inserting data, but I'll carry on anyway with the next table...");
                        t.printStackTrace(log);
                        log.println("*********************************************************************");
                        log.println();

                        break;
                    }
                    
                    line = in.readLine();
                }
                
                in.close();
            }
        }

        // close the file so no open file pointers are left dangling. 
        file.close();
    }

    /**
     * Find date fields and format them into java.sql.Date from Strings.
     * 
     * @param fields Object array containing data to insert
     * @param types int array containing data types
     * @return Object array containing data to insert with formatted dates
     */
    private Object[] formatDates(Object[] fields, int[] types) {
        Object[] data = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            if (Types.DATE == types[i]) {
                String field = (String) fields[i];
                Date date = null;

                // In case a date is null, is zero length, or is FDB's default date (all zeroes), don't convert
                if (field != null && field.trim().length() > 0 && !"00000000".equals(field)) {
                    date = new Date(new SimpleDateFormat("yyyyMMdd", Locale.US).parse(
                        field, new ParsePosition(0)).getTime());
                }

                data[i] = date;
            } else {
                data[i] = fields[i];
            }
        }

        return data;
    }

    /**
     * Return the types mapped in TYPES as an array of ints.
     * 
     * @param tableName String name of the current table, used as the key into the TYPES Map
     * @return int[] array of java.sql.Types for the given table's columns
     */
    private int[] getTypes(String tableName) {
        return tableNameToSqlTypes.get(tableName);
    }

    /**
     * Create an insert SQL statement with the same number of parameters as there are fields.
     * 
     * @param tableName String name of the current file being processed, which contains the name of the table
     * @param numberOfFields int number of fields for this record
     * @return insert SQL String
     */
    private String createInsertSql(String tableName, int numberOfFields) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append(" values(");

        for (int i = 1; i <= numberOfFields; i++) {
            sql.append("?");

            if (i < numberOfFields) {
                sql.append(",");
            }
        }

        sql.append(")");

        return sql.toString();
    }

    /**
     * Execute the NDDF DDL using the default path for the DDL files.
     * 
     * @throws IOException if error reading DDL ZIP file
     */
    public void executeDdl() throws IOException {
        executeDdl(DDL_FILE);
    }

    /**
     * Execute the NDDF DDL using the provided path for the DDL files.
     * Only parse ANSI SQL files (for now).
     * Do not execute a line in the DDL if it is blank or if it is an SQL comment.
     * 
     * @param ddlFile String path on the application's class path to the DDL ZIP file
     * @throws IOException if error reading DDL ZIP file
     */
    public void executeDdl(String ddlFile) throws IOException {
        ZipFile file = new ZipFile(ddlFile);

        Enumeration entries = file.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            if (entry.getName().endsWith(ANSI_SQL_DDL_FILE)) {
                BufferedReader in = new BufferedReader(new InputStreamReader(file.getInputStream(entry)));

                String line = in.readLine();

                while (line != null) {
                    if (line.trim().length() > 0 && !line.startsWith(SQL_COMMENT_PREFIX)) {
                        line = line.replace(';', ' ');

                        parseSqlTypes(line);

                        try {
                            jdbcTemplate.execute(line);
                        } catch (Throwable t) {
                            log.println("*******************************************************************");
                            log.println("Error executing DDL, but I'll carry on anyway with the next table...");
                            t.printStackTrace(log);
                            log.println("********************************************************************");
                            log.println();
                        }
                    }

                    line = in.readLine();
                }

                in.close();
            }
        }

        file.close();
    }

    /**
     * Create an array of Integers (later to be converted to an array of int) that stores
     * the java.sql.Types for each column in a particular table.
     * 
     * @param line String current DDL line
     */
    private void parseSqlTypes(String line) {
        if (line.trim().startsWith("CREATE TABLE")) {
            String table = line.trim().split(" ")[2];
            String[] columns = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"))
                .replaceAll("\\(\\d*,\\d*\\)", "").split(",");
            int[] types = new int[columns.length];

            for (int i = 0; i < columns.length; i++) {
                String type = columns[i].split(" ")[1];

                if (type.indexOf("(") != -1) {
                    type = type.substring(0, type.indexOf("("));
                }

                types[i] = SQL_TYPES.get(type);
            }

            tableNameToSqlTypes.put(table, types);
        }
    }

    /**
     * Setter available if Spring is used to configure the NddfLoader class such that 
     * the NDDF data can be loaded from the PEPS application rather than from the command line.
     * 
     * @param jdbcTemplate jdbcTemplate property
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
