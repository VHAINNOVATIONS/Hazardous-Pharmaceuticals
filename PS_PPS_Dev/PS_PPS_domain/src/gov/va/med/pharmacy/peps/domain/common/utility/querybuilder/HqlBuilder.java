/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.querybuilder;


/**
 * HqlBuilder's brief summary
 * 
 * Details of HqlBuilder's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class HqlBuilder {

    private StringBuffer buffer;

    /**
     * description
     * @param str String 
     */
    public HqlBuilder(String str) {
        this.buffer = new StringBuffer(str);
    }

    /**
     * description
     * @param str String
     * @return HqlBuilder
     */
    public static HqlBuilder create(String str) {
        return new HqlBuilder(str);
    }

    /**
     * description
     * @param str String 
     * @return HqlBuilder
     */
    public HqlBuilder append(String str) {
        buffer.append(' ').append(str);

        return this;
    }

    /**
     * description
     * @param str String 
     * @return HqlBuilder
     */
    public HqlBuilder appendNoSpace(String str) {
        buffer.append(str);

        return this;
    }

    /**
     * description
     * @param c Class
     * @return HqlBuilder
     */
    public HqlBuilder append(Class c) {
        return append(c.getName());
    }

    /**
     * description
     * @param variable String
     * @param property String 
     * @return HqlBuilder
     */
    public HqlBuilder append(String variable, String property) {
        buffer.append(' ').append(variable).append('.').append(property);

        return this;
    }

    /**
     * Converts to a String representation of the object.
     * 
     * @return A string representation of the object.
     */
    public String toString() {
        return buffer.toString();
    }

    /**
     * Inserts "WHERE" and "AND" keywords into a query, as required.
     * 
     * @return HqlBuilder
     */
    public HqlBuilder insertConjunctions() {
        String where = "WHERE";
        String and = "AND";
        
        if (buffer.indexOf(where) == -1) {
            append(where);
        } else if (!buffer.toString().trim().endsWith(and)) {
            append(and);
        }

        return this;
    }

}
