/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.CharUtils;

import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplCmopIdGeneratorDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSeqNumDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplCmopIdGeneratorDo;


/**
 * Perform CRUD operations on SeqNums
 */
public class SeqNumDomainCapabilityImpl implements SeqNumDomainCapability {

    private static final Map<EntityType, String> ENTITY_TYPE_TO_TABLE = new HashMap<EntityType, String>();
    private static final String ZERO_SUFFIX = "000";

    private EplSeqNumDao eplSeqNumDao;
    private EplCmopIdGeneratorDao eplCmopIdGeneratorDao;
    private EnvironmentUtility environmentUtility;

    static {
        ENTITY_TYPE_TO_TABLE.put(EntityType.ADMINISTRATION_SCHEDULE, "EPL_ADMIN_SCHEDULES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.CATEGORY, "EPL_CATEGORIES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DISPENSE_UNIT, "EPL_VA_DISPENSE_UNITS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DOSAGE_FORM, "EPL_DOSAGE_FORMS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DOSE_UNIT, "EPL_DOSE_UNITS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DRUG_CLASS, "EPL_VA_DRUG_CLASSES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DRUG_TEXT, "EPL_DRUG_TEXT");
        ENTITY_TYPE_TO_TABLE.put(EntityType.DRUG_UNIT, "EPL_DRUG_UNITS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.GENERIC_NAME, "EPL_VA_GEN_NAMES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.INGREDIENT, "EPL_INGREDIENTS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.LOCAL_MEDICATION_ROUTE, "EPL_LOCAL_MED_ROUTES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.MANUFACTURER, "EPL_MANUFACTURERS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.MEDICATION_INSTRUCTION, "EPL_MEDICATION_INSTRUCTIONS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.NDC, "EPL_NDCS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.NDF_SYNCH_QUEUE, "EPL_NDF_SYNCH_QUEUE");
        ENTITY_TYPE_TO_TABLE.put(EntityType.ORDER_UNIT, "EPL_ORDER_UNITS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.ORDERABLE_ITEM, "EPL_ORDERABLE_ITEMS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.PACKAGE_TYPE, "EPL_PACKAGE_TYPES");
        ENTITY_TYPE_TO_TABLE.put(EntityType.PHARMACY_SYSTEM, "EPL_PHARMACY_SYSTEM");
        ENTITY_TYPE_TO_TABLE.put(EntityType.PRODUCT, "EPL_PRODUCTS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.REDUCED_COPAY, "EPL_REDUCED_COPAY");
        ENTITY_TYPE_TO_TABLE.put(EntityType.RX_CONSULT, "EPL_WARN_LABELS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.SPECIAL_HANDLING, "EPL_SPECIAL_HANDLING");
        ENTITY_TYPE_TO_TABLE.put(EntityType.STANDARD_MED_ROUTE, "EPL_STANDARD_MED_ROUTE");
        ENTITY_TYPE_TO_TABLE.put(EntityType.USER, "EPL_USERS");
        ENTITY_TYPE_TO_TABLE.put(EntityType.FDB_ADD, "EPL_FDB_ADD");
        ENTITY_TYPE_TO_TABLE.put(EntityType.FDB_UPDATE, "EPL_FDB_UPDATE");
        ENTITY_TYPE_TO_TABLE.put(EntityType.FDB_AUTO_UPDATE, "EPL_FDB_AUTO_UPDATE");
        ENTITY_TYPE_TO_TABLE.put(EntityType.VUID_STATUS_HISTORY, "EPL_VUID_STATUS_HISTORY");

    }

    /**
     * Get next available ID for the given {@link EntityType}.
     * <p>
     * Maps the {@link EntityType} to the appropriate table name.
     * <p>
     * Assumes that the given {@link EntityType} is not null!
     * 
     * @param entityType {@link EntityType} for which to generate an ID
     * @param user {@link UserVo} performing the action
     * @return String ID
     */
    public String generateId(EntityType entityType, UserVo user) {
        String tableName = ENTITY_TYPE_TO_TABLE.get(entityType);
        String generatedId = environmentUtility.getSiteNumber() + getId(tableName, user);

        return generatedId;
    }

    /**
     * generate fed schedule id
     * 
     * @param user {@link UserVo} performing the action
     * @return String generated FedSchedule id
     */
    public String generateFedScheduleId(UserVo user) {
        String generatedId = environmentUtility.getSiteNumber() + getId("EPL_CS_FED_SCHEDULES", user);

        return (generatedId);
    }

    /**
     * generate notification id
     * 
     * @param user {@link UserVo} performing the action
     * @return String generated epl_notifications id
     */
    public String generateNotificationId(UserVo user) {
        String generatedId = environmentUtility.getSiteNumber() + getId("EPL_NOTIFICATIONS", user);

        return (generatedId);
    }

    /**
     * generate item audit history id
     * 
     * @param user {@link UserVo} performing the action
     * @return String generated epl_item_audit_history table
     */
    public String generateItemAuditHistoryId(UserVo user) {
        String generatedId = environmentUtility.getSiteNumber() + getId("EPL_ITEM_AUDIT_HISTORY", user);

        return (generatedId);
    }

    /**
     * Generates the owner id using site and unique key
     * 
     * @param user {@link UserVo} performing the action
     * @return String with generated ownerId
     */
    public String generatedOwnerId(UserVo user) {
        String generatedId = environmentUtility.getSiteNumber() + getId("EPL_VADF_OWNERS", user);

        return (generatedId);
    }

    /**
     * generate searach template id
     * 
     * @param user {@link UserVo} performing the action
     * @return Long
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability#generateSearchTemplateId()
     */
    public Long generateSearchTemplateId(UserVo user) {
        return getId("EPL_SEARCH_TEMPLATES", user);
    }

    /**
     * generate print template id
     * 
     * @param user {@link UserVo} performing the action
     * @return Long
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability#generateSearchTemplateId()
     */
    public Long generatePrintTemplateId(UserVo user) {
        return getId("EPL_PRINT_TEMPLATES", user);
    }

    /**
     * description
     * 
     * @param seed String
     * @param value String
     * @param user UserVo
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability#setCmopIdGeneratorValue(
     *      java.lang.String, java.lang.String, gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    public void setCmopIdGeneratorValue(String seed, String value, UserVo user) {

        EplCmopIdGeneratorDo cmop = eplCmopIdGeneratorDao.retrieve(seed);
        cmop.setLastUsedCmopId(value);
        eplCmopIdGeneratorDao.updateCreatedAndLastModified(cmop, user);
        eplCmopIdGeneratorDao.update(cmop, user);
    }

    /**
     * Generate CMOP ID
     * 
     * @param seed String
     * @param user {@link UserVo} performing the action
     * @return String
     */
    public String generateCmopId(StringBuffer seed, UserVo user) {

        EplCmopIdGeneratorDo cmop = eplCmopIdGeneratorDao.retrieve(seed.toString());
        String idtoBeReturned = cmop.getLastUsedCmopId();

        // increment it and save it
        String incremented = cmop.getLastUsedCmopId().substring(1);

        StringBuffer alphaString = new StringBuffer();
        StringBuffer numericString = new StringBuffer();

        for (int i = 0; i < incremented.length(); i++) {
            char c = incremented.charAt(i);

            if (Character.isDigit(c)) {
                numericString.append(c);
            } else {
                alphaString.append(c);
            }
        }

        // all numbers

        if (alphaString.length() == 0) {
            incremented = alpha0(incremented, numericString);
        }

        // first is alpha
        if (alphaString.length() == 1) {
            Integer value = new Integer(numericString.toString());

            if (value.intValue() == PPSConstants.I999) {
                incremented = alpha1(incremented, numericString, alphaString, seed);
            } else {
                int x = value.intValue() + 1;

                if (x <= PPSConstants.I9) {
                    incremented = alphaString + numericString.substring(0, 2) + Integer.valueOf(x).toString();
                } else if (x <= PPSConstants.NINETYNINE) {
                    incremented = alphaString + numericString.substring(0, 1) + Integer.valueOf(x).toString();
                } else if (x <= PPSConstants.I999) {
                    incremented = alphaString + Integer.valueOf(x).toString();
                } else {
                    incremented = Integer.valueOf(x).toString();
                }
            }
        }

        idtoBeReturned = seed + incremented;
        cmop.setLastUsedCmopId(idtoBeReturned);
        eplCmopIdGeneratorDao.update(cmop, user);

        return idtoBeReturned;
    }

    /**
     * alpha0
     * @param incremented incremented
     * @param numericString numericString
     * @return incremented value
     */
    private String alpha0(String incremented, StringBuffer numericString) {
        Integer value = new Integer(numericString.toString());
        String returnValue = incremented;

        if (value.intValue() == PPSConstants.I9999) {
            returnValue = "A000";
        } else {
            int x = value.intValue() + 1;

            // the first 3 were padded with 0's

            if (x <= PPSConstants.I9) {
                returnValue = numericString.substring(0, PPSConstants.I3) + Integer.valueOf(x).toString();
            } else if (x <= PPSConstants.NINETYNINE) {
                returnValue = numericString.substring(0, 2) + Integer.valueOf(x).toString();
            } else if (x <= PPSConstants.I999) {
                returnValue = numericString.substring(0, 1) + Integer.valueOf(x).toString();
            } else {
                returnValue = Integer.valueOf(x).toString();
            }
        }

        return returnValue;
    }

    /**
     * alpha1
     * @param incremented incremented
     * @param numericString numericString
     * @param alphaString alphaString
     * @param seed seed
     * @return value
     */
    private String alpha1(String incremented, StringBuffer numericString, StringBuffer alphaString, StringBuffer seed) {

        String returnVal = "";

        char test = alphaString.charAt(0);
        char charA = 'A';
        char charZ = 'Z';

        if (test >= charA && test < charZ) {
            returnVal = ++test + ZERO_SUFFIX;
        } else if (test == charZ) {
            seed.replace(0, seed.length(), getNextAlphaChar(seed.charAt(0)));
            returnVal = "0000";
        }

        return returnVal;
    }

    /**
     * Retrieve id for a table from ep_seq_num
     * 
     * @param tableName String
     * @param user {@link UserVo} performing the action
     * @return long
     */
    private synchronized long getId(final String tableName, final UserVo user) {
        long value = System.currentTimeMillis();

        // long value2 = System.nanoTime();
        // LOG.debug("ID generated for " + tableName + " is " + value);

        long t0;
        long t1;
        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < 1);

        return value;

//        EplSeqNumDo retreivedDo = eplSeqNumDao.retrieve(tableName);
//
//        long id = retreivedDo.getNextSeqNum().longValue();
//        retreivedDo.setNextSeqNum(retreivedDo.getNextSeqNum() + 1);
//        eplSeqNumDao.update(retreivedDo, user);
//
//        return id;

        // We really should use identity columns in database in combination with site number, or if we continue to use the
        // EPL_SEQ_NUMS table, increment the ID in its own transaction like follows:
        //
        // TransactionHelper idGenerator = new TransactionHelper() {
        // private static final long DEFAULT_ID = 50000;
        // private static final String ALIAS = "A";
        // private static final String NEXT_SEQ_NUM = "NEXT_SEQ_NUM";
        // private static final String SELECT_QUERY = "SELECT " + ALIAS + "." + NEXT_SEQ_NUM + " FROM EPL_SEQ_NUMS "
        // + ALIAS + " WHERE TABLE_NAME=?";
        // private static final String UPDATE_QUERY = "UPDATE EPL_SEQ_NUMS SET NEXT_SEQ_NUM=?, LAST_MODIFIED_BY=?, "
        // + "LAST_MODIFIED_DTM=? WHERE TABLE_NAME=?";
        // private static final String INSERT_QUERY = "INSERT INTO EPL_SEQ_NUMS (TABLE_NAME, NEXT_SEQ_NUM, CREATED_BY, "
        // + "CREATED_DTM) VALUES (?, ?, ?, ?)";
        //
        // /**
        // * Execute SQL in a separate transaction with row level locking to generate a new ID.
        // *
        // * @param conn JDBC Connection
        // * @param sql String (unused)
        // * @return Long ID generated
        // * @throws SQLException if error
        // *
        // * @see org.hibernate.engine.TransactionHelper#doWorkInCurrentTransaction(java.sql.Connection, java.lang.String)
        // */
        // @Override
        // protected Serializable doWorkInCurrentTransaction(Connection conn, String sql) throws SQLException {
        // SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) eplSeqNumDao.getCurrentSession()
        // .getSessionFactory();
        // Dialect dialect = sessionFactory.getDialect();
        //
        // Map<String, LockMode> lockMap = new HashMap<String, LockMode>();
        // lockMap.put(ALIAS, LockMode.UPGRADE);
        // Map<String, String[]> updateTargetColumnsMap = Collections.singletonMap(ALIAS, new String[] {NEXT_SEQ_NUM});
        // String selectSql = dialect.applyLocksToSql(SELECT_QUERY, lockMap, updateTargetColumnsMap);
        //
        // long currentValue = DEFAULT_ID;
        // PreparedStatement select = conn.prepareStatement(selectSql);
        //
        // try {
        // select.setString(1, tableName);
        // ResultSet selectResultSet = select.executeQuery();
        //
        // if (selectResultSet.next()) {
        // currentValue = selectResultSet.getLong(1);
        // }
        // else {
        // PreparedStatement insert = conn.prepareStatement(INSERT_QUERY);
        //
        // try {
        // insert.setString(1, tableName);
        // insert.setLong(2, currentValue);
        // insert.setString(3, user.getUsername());
        // insert.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        // insert.execute();
        // }
        // finally {
        // insert.close();
        // }
        // }
        //
        // selectResultSet.close();
        // }
        // finally {
        // select.close();
        // }
        //
        // PreparedStatement update = conn.prepareStatement(UPDATE_QUERY);
        //
        // try {
        // update.setLong(1, currentValue + 1);
        // update.setString(2, user.getUsername());
        // update.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        // update.setString(4, tableName);
        // update.executeUpdate();
        // }
        // finally {
        // update.close();
        // }
        //
        // return Long.valueOf(currentValue + 1);
        // }
        // };
        //
        // Long id = (Long) idGenerator.doWorkInNewTransaction((SessionImplementor) eplSeqNumDao.getCurrentSession());
        //
        // return id.longValue();
    }

    /**
     * getNextAlphaChar
     *
     * @param a char
     * @return String
     */
    private String getNextAlphaChar(char a) {
        if (a == 'Z') {
            throw new IllegalArgumentException("The is no alpha character after Z");
        }

        return CharUtils.toString((char) (a + 1));
    }

    /**
     * getEplSeqNumDao
     * @return EplSeqNumDao
     */
    public EplSeqNumDao getEplSeqNumDao() {
        return this.eplSeqNumDao;
    }
    
    /**
     * setEplSeqNumDao
     * @param eplSeqNumDao eplSeqNumDao property
     */
    public void setEplSeqNumDao(EplSeqNumDao eplSeqNumDao) {
        this.eplSeqNumDao = eplSeqNumDao;
    }

    /**
     * setEnvironmentUtility
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * setEplCmopIdGeneratorDao
     * @param eplCmopIdGeneratorDao eplCmopIdGeneratorDao property
     */
    public void setEplCmopIdGeneratorDao(EplCmopIdGeneratorDao eplCmopIdGeneratorDao) {
        this.eplCmopIdGeneratorDao = eplCmopIdGeneratorDao;
    }
}
