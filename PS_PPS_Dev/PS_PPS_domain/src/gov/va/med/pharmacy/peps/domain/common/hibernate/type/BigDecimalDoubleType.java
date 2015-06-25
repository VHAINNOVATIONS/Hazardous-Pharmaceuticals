/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.hibernate.type;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.type.DoubleType;


/**
 * Uses the {@link ResultSet#getBigDecimal(String)} to get the {@link BigDecimal} representation of the column and then
 * converts that into a {@link Double}.
 * <p>
 * In InterSystems Cache, using the {@link ResultSet#getDouble(String)} method at times returns incorrect {@link Double}
 * values. For instance, if 11.03 were stored in a NUMERIC(10,4) column in the database, 11.0300000000001 could be returned
 * instead.
 */
public class BigDecimalDoubleType extends DoubleType {
    private static final long serialVersionUID = 1L;

    /**
     * Get the value from {@link ResultSet#getBigDecimal(String)} and convert the {@link BigDecimal} into a {@link Double}.
     * 
     * @param rs {@link ResultSet}
     * @param name String column alias
     * @return Double
     * @throws SQLException if error
     * 
     * @see org.hibernate.type.DoubleType#get(java.sql.ResultSet, java.lang.String)
     */
    public Object get(ResultSet rs, String name) throws SQLException {
        BigDecimal bigDecimal = rs.getBigDecimal(name);
        double result = 0;

        if (bigDecimal != null) {
            result = bigDecimal.doubleValue();
        }

        return new Double(result);
    }

    /**
     * getName
     * @return String
     * 
     * @see org.hibernate.type.DoubleType#getName()
     */
    public String getName() {
        return "bigdecimal_double";
    }
}
