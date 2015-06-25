/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;


/**
 * Perform CRUD operations on Managed Data domains
 * 
 * @param <T> type of {@link ManagedDataVo}
 * @param <DO> type of {@link DataObject}
 */
public interface ManagedDataDomainCapability<T extends ManagedDataVo, DO extends DataObject> extends
    ManagedItemDomainCapability<T, DO> {

}
