/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Define methods for value objects that represent a data field that is selected from a list of options.
 */
public interface Selectable {

    /**
     * Get the String value of whatever type the ID is.
     * 
     * @return String value of the ID
     */
    String getId();

    /**
     * Get the String value of whatever the displayed value is.
     * 
     * @return String value
     */
    String getValue();
}
