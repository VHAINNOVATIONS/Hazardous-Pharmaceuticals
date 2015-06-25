/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Retrieve possible values for various domain attributes.
 */
public interface DomainUtility {

    /**
     * Get all available values for the given FieldKey.
     * 
     * @param <T>
     *            Type to return
     * @param key
     *            FieldKey to retrieve values for
     * @return Instance of data field for given FieldKey with all of the
     *         available values set
     */
    <T> T getDomain(FieldKey key);
}
