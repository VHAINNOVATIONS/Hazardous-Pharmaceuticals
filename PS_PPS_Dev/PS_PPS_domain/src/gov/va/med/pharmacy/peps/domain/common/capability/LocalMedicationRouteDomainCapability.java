/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;


/**
 * Perform CRUD operations on local medication routes
 */
public interface LocalMedicationRouteDomainCapability extends
    ManagedDataDomainCapability<LocalMedicationRouteVo, EplLocalMedRouteDo> {

}
