/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;


/**
 * Perform operations on and with DataFields
 */
public interface DataFieldsCapability {

    /**
     * Retrieve all data fields for the given {@link EntityType} with the default values set
     * 
     * @param itemType EntityType to retrieve default values for
     * @return DataFields
     */
    DataFields<DataField> retrieveDefaultDomain(EntityType itemType);

    /**
     * Retrieve a List of possible values to select from
     * 
     * @return DataFields
     */
    DataFields retrieveDomain();
}
