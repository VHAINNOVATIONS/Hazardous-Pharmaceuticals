/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.impl;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugReferenceCapability;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;


/**
 * Retrieve drug reference information.
 */
public class DrugReferenceServiceImpl implements DrugReferenceService {

    private DrugReferenceCapability drugReferenceCapability;

    /**
     * Retrieve PMI data.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    public String retrievePatientMedicationInformation(String request) {
        return drugReferenceCapability.retrievePatientMedicationInformation(request);
    }

    /**
     * retrieveWarningLabels
     * @param vo vo
     * @return ReportProductVo
     */
    public ReportProductVo retrieveWarningLabels(ReportProductVo vo) {
        return drugReferenceCapability.retrieveWarningLabels(vo);
    }
    
    /**
     * Retrieve PMI data.
     * 
     * @param gcnSeqNo GcnSeqNo
     * @param spanish True if for spanish text
     * @return response PatientMedicationInstructionVo
     * 
     * @throws ValidationException if error loading FDB monograph
     */
    public PatientMedicationInstructionVo retrievePatientMedicationInformation(long gcnSeqNo, boolean spanish)
        throws ValidationException {
        return drugReferenceCapability.retrievePatientMedicationInformation(gcnSeqNo, spanish);
    }

    /**
     * Processes the FDB Search Option
     * 
     * @param fdbSearchOptionVo This is the search object with the criteria embedded
     * @param user UserVo
     * @return FDBSearchOptionVo This is the search option return object
     */
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo fdbSearchOptionVo, UserVo user) {
        return drugReferenceCapability.performFDBSearchOption(fdbSearchOptionVo, user);
    }

    /**
     * Set capability.
     * 
     * @param drugReferenceCapability capability
     */
    public void setDrugReferenceCapability(DrugReferenceCapability drugReferenceCapability) {
        this.drugReferenceCapability = drugReferenceCapability;
    }

    /**
     * performFDBSearchOption
     * @param pFdbSearchOption pFdbSearchOption
     * @param fdbSearchOptionType fdbSearchOptionType
     * @param user user
     * @return FDBSearchOptionVo
     */
    @Override
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo pFdbSearchOption, 
            FDBSearchOptionType fdbSearchOptionType, UserVo user) {
        return drugReferenceCapability.performFDBSearchOption(pFdbSearchOption, fdbSearchOptionType, user);
    }
}
