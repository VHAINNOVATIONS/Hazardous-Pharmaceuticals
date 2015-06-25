/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplAdminScheduleDo;


/**
 * perform CRUD operation on administration schedule
 */
public interface AdministrationScheduleDomainCapability extends
    ManagedDataDomainCapability<AdministrationScheduleVo, EplAdminScheduleDo> {

}
