/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;


/**
 * Perform CRUD operations on medication instructions
 */
public interface MedicationInstructionDomainCapability extends
    ManagedDataDomainCapability<MedicationInstructionVo, EplMedicationInstructionDo> {
}
