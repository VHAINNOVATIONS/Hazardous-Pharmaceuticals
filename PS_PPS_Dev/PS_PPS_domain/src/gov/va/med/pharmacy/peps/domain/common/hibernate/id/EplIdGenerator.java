/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.hibernate.id;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.TransactionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGeneratorHelper.BigIntegerHolder;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.AccessCallback;
import org.hibernate.id.enhanced.Optimizer;
import org.hibernate.id.enhanced.OptimizerFactory;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Generate EPL IDs.
 * <p>
 * Code copied from {@link org.hibernate.id.enhanced.TableGenerator} and
 * modified to use Long everywhere (instead of some int some long), to use our
 * pre-existing EPL_SEQ_NUMS table, and to prefix the IDs with the PEPS Site
 * Number.
 */
public class EplIdGenerator extends TransactionHelper implements
        PersistentIdentifierGenerator, Configurable {

    /** CONFIG_PREFER_SEGMENT_PER_ENTITY */
    public static final String CONFIG_PREFER_SEGMENT_PER_ENTITY = "prefer_entity_table_as_segment_value";

    /** DEF_CONFIG_PREFER_SEGMENT_PER_ENTITY */
    public static final boolean DEF_CONFIG_PREFER_SEGMENT_PER_ENTITY = true;

    /** TABLE_PARAM */
    public static final String TABLE_PARAM = "table_name";

    /** DEF_TABLE */
    public static final String DEF_TABLE = "EPL_SEQ_NUMS";

    /** VALUE_COLUMN_PARAM */
    public static final String VALUE_COLUMN_PARAM = "value_column_name";

    /** DEF_VALUE_COLUMN */
    public static final String DEF_VALUE_COLUMN = "NEXT_SEQ_NUM";

    /** SEGMENT_COLUMN_PARAM */
    public static final String SEGMENT_COLUMN_PARAM = "segment_column_name";

    /** DEF_SEGMENT_COLUMN */
    public static final String DEF_SEGMENT_COLUMN = "TABLE_NAME";

    /** SEGMENT_VALUE_PARAM */
    public static final String SEGMENT_VALUE_PARAM = "segment_value";

    /** DEF_SEGMENT_VALUE */
    public static final String DEF_SEGMENT_VALUE = "default";

    /** SEGMENT_LENGTH_PARAM */
    public static final String SEGMENT_LENGTH_PARAM = "segment_value_length";

    /** DEF_SEGMENT_LENGTH */
    public static final int DEF_SEGMENT_LENGTH = 256;

    /** INITIAL_PARAM */
    public static final String INITIAL_PARAM = "initial_value";

    /** DEFAULT_INITIAL_VALUE */
    public static final int DEFAULT_INITIAL_VALUE = 50000;

    /** INCREMENT_PARAM */
    public static final String INCREMENT_PARAM = "increment_size";

    /** DEFAULT_INCREMENT_SIZE */
    public static final int DEFAULT_INCREMENT_SIZE = 1;

    /** OPT_PARAM */
    public static final String OPT_PARAM = "optimizer";

    private static final Logger LOG = LoggerFactory.getLogger(EplIdGenerator.class);

    private Type identifierType;

    private String tableName;

    private String segmentColumnName;
    private String segmentValue;
    private int segmentValueLength;

    private String valueColumnName;
    private int initialValue;
    private int incrementSize;

    private String selectQuery;
    private String insertQuery;
    private String updateQuery;

    private Optimizer optimizer;
    private long accessCount = 0;
    private Integer siteNumber = null;

    /**
     * Type mapping for the identifier.
     * 
     * @return The identifier type mapping.
     */
    public final Type getIdentifierType() {
        return identifierType;
    }

    /**
     * The name of the table in which we store this generator's persistent
     * state.
     * 
     * @return The table name.
     */
    public final String getTableName() {
        return tableName;
    }

    /**
     * The name of the column in which we store the segment to which each row
     * belongs. The value here acts as PK.
     * 
     * @return The segment column name
     */
    public final String getSegmentColumnName() {
        return segmentColumnName;
    }

    /**
     * The value in {@link #getSegmentColumnName segment column} which
     * corresponding to this generator instance. In other words this value
     * indicates the row in which this generator instance will store values.
     * 
     * @return The segment value for this generator instance.
     */
    public final String getSegmentValue() {
        return segmentValue;
    }

    /**
     * The size of the {@link #getSegmentColumnName segment column} in the
     * underlying table.
     * <p/>
     * <b>NOTE</b> : should really have been called 'segmentColumnLength' or
     * even better 'segmentColumnSize'
     * 
     * @return the column size.
     */
    public final int getSegmentValueLength() {
        return segmentValueLength;
    }

    /**
     * The name of the column in which we store our persistent generator value.
     * 
     * @return The name of the value column.
     */
    public final String getValueColumnName() {
        return valueColumnName;
    }

    /**
     * The initial value to use when we find no previous state in the generator
     * table corresponding to our sequence.
     * 
     * @return The initial value to use.
     */
    public final int getInitialValue() {
        return initialValue;
    }

    /**
     * The amount of increment to use. The exact implications of this depends on
     * the {@link #getOptimizer() optimizer} being used.
     * 
     * @return The increment amount.
     */
    public final int getIncrementSize() {
        return incrementSize;
    }

    /**
     * The optimizer being used by this generator.
     * 
     * @return Out optimizer.
     */
    public final Optimizer getOptimizer() {
        return optimizer;
    }

    /**
     * Getter for property 'tableAccessCount'. Only really useful for unit test
     * assertions.
     * 
     * @return Value for property 'tableAccessCount'.
     */
    public final long getTableAccessCount() {
        return accessCount;
    }

    /**
     * Set the configurable parameters.
     * 
     * @param type
     *            Type of ID
     * @param params
     *            configurable parameters
     * @param dialect
     *            database Dialect
     * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type,
     *      java.util.Properties, org.hibernate.dialect.Dialect)
     */
    @Override
    public void configure(Type type, Properties params, Dialect dialect) {
        this.identifierType = type;

        this.tableName = determneGeneratorTableName(params);
        this.segmentColumnName = determineSegmentColumnName(params);
        this.valueColumnName = determineValueColumnName(params);

        this.segmentValue = determineSegmentValue(params);

        this.segmentValueLength = determineSegmentColumnSize(params);
        this.initialValue = determineInitialValue(params);
        this.incrementSize = determineIncrementSize(params);

        this.selectQuery = buildSelectQuery(dialect);
        this.updateQuery = buildUpdateQuery();
        this.insertQuery = buildInsertQuery();

        String defOptStrategy = incrementSize <= 1 ? OptimizerFactory.NONE
                                                  : OptimizerFactory.POOL;
        String optimizationStrategy = PropertiesHelper.getString(OPT_PARAM, params, defOptStrategy);
        this.optimizer =
            OptimizerFactory.buildOptimizer(optimizationStrategy, identifierType.getReturnedClass(), incrementSize);
    }

    /**
     * Determine the table name to use for the generator values.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getTableName()
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The table name to use.
     */
    protected String determneGeneratorTableName(Properties params) {
        String name = PropertiesHelper.getString(TABLE_PARAM, params, DEF_TABLE);
        boolean isGivenNameUnqualified = name.indexOf('.') < 0;

        // if the given name is un-qualified we may need to qualify it
        if (isGivenNameUnqualified) {
            String schemaName = params.getProperty(SCHEMA);
            String catalogName = params.getProperty(CATALOG);
            name = Table.qualify(catalogName, schemaName, name);
        }

        return name;
    }

    /**
     * Determine the name of the column used to indicate the segment for each
     * row. This column acts as the primary key.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentColumnName()
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The name of the segment column
     */
    protected String determineSegmentColumnName(Properties params) {
        return PropertiesHelper.getString(SEGMENT_COLUMN_PARAM, params, DEF_SEGMENT_COLUMN);
    }

    /**
     * Determine the name of the column in which we will store the generator
     * persistent value.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getValueColumnName()
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The name of the value column
     */
    protected String determineValueColumnName(Properties params) {
        return PropertiesHelper.getString(VALUE_COLUMN_PARAM, params, DEF_VALUE_COLUMN);
    }

    /**
     * Determine the segment value corresponding to this generator instance.
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentValue()
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The name of the value column
     */
    protected String determineSegmentValue(Properties params) {
        String segmentValue2 = params.getProperty(SEGMENT_VALUE_PARAM);

        if (StringHelper.isEmpty(segmentValue2)) {
            segmentValue2 = determineDefaultSegmentValue(params);
        }

        return segmentValue2;
    }

    /**
     * Used in the cases where {@link #determineSegmentValue} is unable to
     * determine the value to use.
     * 
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The default segment value to use.
     */
    protected String determineDefaultSegmentValue(Properties params) {
        boolean preferSegmentPerEntity =
            PropertiesHelper.getBoolean(CONFIG_PREFER_SEGMENT_PER_ENTITY, params, DEF_CONFIG_PREFER_SEGMENT_PER_ENTITY);
        String defaultToUse = preferSegmentPerEntity ? params.getProperty(TABLE) : DEF_SEGMENT_VALUE;

        LOG.trace("explicit segment value for id generator [" + tableName + '.' + segmentColumnName
            + "] suggested; using default [" + defaultToUse + "]");

        return defaultToUse;
    }

    /**
     * Determine the size of the {@link #getSegmentColumnName segment column}
     * <p/>
     * Called during {@link #configure configuration}.
     * 
     * @see #getSegmentValueLength()
     * @param params
     *            The params supplied in the generator config (plus some
     *            standard useful extras).
     * @return The size of the segment column
     */
    protected int determineSegmentColumnSize(Properties params) {
        return PropertiesHelper.getInt(SEGMENT_LENGTH_PARAM, params, DEF_SEGMENT_LENGTH);
    }

    /**
     * Either use the configured or default initial value.
     * 
     * @param params
     *            configurable parameters
     * @return int initial value
     */
    protected int determineInitialValue(Properties params) {
        return PropertiesHelper.getInt(INITIAL_PARAM, params, DEFAULT_INITIAL_VALUE);
    }

    /**
     * Either use the configured or default increment size.
     * 
     * @param params
     *            configurable parameters
     * @return int increment size
     */
    protected int determineIncrementSize(Properties params) {
        return PropertiesHelper.getInt(INCREMENT_PARAM, params, DEFAULT_INCREMENT_SIZE);
    }

    /**
     * Create an SQL String for use as a prepared statement.
     * 
     * @param dialect
     *            database Dialect
     * @return SQL select query
     */
    protected String buildSelectQuery(Dialect dialect) {
        final String alias = "tbl";
        String query =
            "select " + StringHelper.qualify(alias, valueColumnName) + " from " + tableName + ' ' + alias + " where  "
                + StringHelper.qualify(alias, segmentColumnName) + "=?";

        LockOptions alo = new LockOptions();
        alo.setAliasSpecificLockMode(alias, LockMode.UPGRADE_NOWAIT);

        Map updateTargetColumnsMap = Collections.singletonMap(alias, new String[] { valueColumnName });

        return dialect.applyLocksToSql(query, alo, updateTargetColumnsMap);
    }

    /**
     * Create an SQL String for use as a prepared statement.
     * 
     * @return SQL update query
     */
    protected String buildUpdateQuery() {
        return "update " + tableName + " set " + valueColumnName + "=? " + " where " + valueColumnName + "=? and "
            + segmentColumnName + "=?";
    }

    /**
     * Create an SQL String for use as a prepared statement.
     * 
     * @return SQL insert query
     */
    protected String buildInsertQuery() {
        return "insert into " + tableName + " (" + segmentColumnName + ", " + valueColumnName + ") " + " values (?,?)";
    }

    /**
     * Generate a new, unique ID.
     * 
     * @param session
     *            {@link SessionImplementor}
     * @param obj
     *            Object
     * @return ID
     * 
     * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor,
     *      java.lang.Object)
     */
    @Override
    public synchronized Serializable generate(final SessionImplementor session, Object obj) {

        return optimizer.generate(new AccessCallback() {

            @Override
            public IntegralDataTypeHolder getNextValue() {

                BigIntegerHolder bih = new BigIntegerHolder();
                bih.initialize(((Number) doWorkInNewTransaction(session)).longValue());

                return bih;
            }
        });
    }

    /**
     * Generate a new, unique ID.
     * 
     * @param conn
     *            Connection
     * @param sql
     *            String SQL query
     * @return ID
     * @throws SQLException
     *             if error
     * 
     * @see org.hibernate.engine.TransactionHelper#doWorkInCurrentTransaction(java.sql.Connection,
     *      java.lang.String)
     */
    @Override
    public Serializable doWorkInCurrentTransaction(Connection conn, String sql)
        throws SQLException {
        long result;
        int rows;

        do {
            SQL_STATEMENT_LOGGER.logStatement(selectQuery, FormatStyle.BASIC);
            PreparedStatement selectPS = conn.prepareStatement(selectQuery);

            try {
                selectPS.setString(1, segmentValue);
                ResultSet selectRS = selectPS.executeQuery();

                if (selectRS.next()) {
                    result = selectRS.getLong(1);
                } else {
                    PreparedStatement insertPS = null;

                    try {
                        result = initialValue;
                        SQL_STATEMENT_LOGGER.logStatement(insertQuery, FormatStyle.BASIC);
                        insertPS = conn.prepareStatement(insertQuery);
                        insertPS.setString(1, segmentValue);
                        insertPS.setLong(2, result);
                        insertPS.execute();
                    } finally {
                        if (insertPS != null) {
                            insertPS.close();
                        }
                    }
                }

                selectRS.close();
            } catch (SQLException sqle) {
                LOG.error("could not read or init a hi value", sqle);
                throw sqle;
            } finally {
                selectPS.close();
            }

            SQL_STATEMENT_LOGGER.logStatement(updateQuery, FormatStyle.BASIC);
            PreparedStatement updatePS = conn.prepareStatement(updateQuery);

            try {
                long newValue = optimizer.applyIncrementSizeToSourceValues() ? result + incrementSize : result + 1;
                updatePS.setLong(1, newValue);
                updatePS.setLong(2, result);
                updatePS.setString(PPSConstants.I3, segmentValue);
                rows = updatePS.executeUpdate();
            } catch (SQLException sqle) {
                LOG.error("could not updateQuery hi value in: " + tableName, sqle);
                throw sqle;
            } finally {
                updatePS.close();
            }
        } while (rows == 0);

        accessCount++;

        return Long.valueOf(String.valueOf(getSiteNumber(conn)) + String.valueOf(result));
    }

    /**
     * Lookup the site number from the EPL_PHARMACY_SYSTEM table.
     * 
     * @param conn
     *            Connection
     * @return site number
     * @throws SQLException
     *             if error
     */
    private synchronized int getSiteNumber(Connection conn) throws SQLException {
        if (siteNumber == null) {
            String sql = "SELECT SITE_NUMBER FROM EPL_PHARMACY_SYSTEM";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            this.siteNumber = resultSet.getInt(1);
        }

        return siteNumber;
    }

    /**
     * The SQL required to create the underlying database objects. Since the
     * database table already exists, this method should really never get
     * called.
     * 
     * @param dialect
     *            The dialect against which to generate the create command(s)
     * @return The create command(s)
     * 
     * @see org.hibernate.id.PersistentIdentifierGenerator#sqlCreateStrings(org.hibernate.dialect.Dialect)
     */
    @Override
    public String[] sqlCreateStrings(Dialect dialect) {
        StringBuffer create = new StringBuffer();
        create.append(dialect.getCreateTableString());
        create.append(' ');
        create.append(tableName);
        create.append(" ( ");
        create.append(segmentColumnName);
        create.append(' ');
        create.append(dialect.getTypeName(Types.VARCHAR, segmentValueLength, 0, 0));
        create.append(",  ");
        create.append(valueColumnName);
        create.append(' ');
        create.append(dialect.getTypeName(Types.BIGINT));
        create.append(", primary key ( ");
        create.append(segmentColumnName);
        create.append(" ) ) ");

        return new String[] { create.toString() };
    }

    /**
     * The SQL required to remove the underlying database objects.
     * 
     * @param dialect
     *            The dialect against which to generate the drop command(s)
     * @return The drop command(s)
     * 
     * @see org.hibernate.id.PersistentIdentifierGenerator#sqlDropStrings(org.hibernate.dialect.Dialect)
     */
    @Override
    public String[] sqlDropStrings(Dialect dialect) {
        StringBuffer sqlDropString = new StringBuffer().append("drop table ");

        if (dialect.supportsIfExistsBeforeTableName()) {
            sqlDropString.append("if exists ");
        }

        sqlDropString.append(tableName).append(
                dialect.getCascadeConstraintsString());

        if (dialect.supportsIfExistsAfterTableName()) {
            sqlDropString.append(" if exists");
        }

        return new String[] { sqlDropString.toString() };
    }

    /**
     * Return a key unique to the underlying database objects. Prevents us from
     * trying to create/remove them multiple times.
     * 
     * @return Object an identifying key for this generator
     */
    @Override
    public Object generatorKey() {
        return tableName;
    }
}
