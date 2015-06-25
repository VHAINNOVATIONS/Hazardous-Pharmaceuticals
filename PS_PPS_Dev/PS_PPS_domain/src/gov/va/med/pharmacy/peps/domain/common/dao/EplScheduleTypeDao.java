/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.dao;


import gov.va.med.pharmacy.peps.domain.common.model.EplScheduleTypeDo;


/**
 * This Schedule provides a set of operation that may be performed on all the Schedulees that inherit from this Schedule.
 * This interface should never be used directly, but it will be inherited by all DAOs. It provides abstract methods to access
 * and modify the EplScheduleTypeDo.
 * 
 */
public interface EplScheduleTypeDao extends DataAccessObject<EplScheduleTypeDo, Long> {

}
