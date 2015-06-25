/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.session;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Retrieve drug reference information.
 */
public interface DrugReferenceService {

    /**
     * Retrieve the PMI data.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    String retrievePatientMedicationInformation(String request);

    /**
     * retrieveWarningLabels
     * @param vo vo
     * @return ReportProductVo
     */
    ReportProductVo retrieveWarningLabels(ReportProductVo vo);
    
    /**
     * Retrieve the PMI data.
     * 
     * @param gcnSeqNo GcnSeqNo
     * @param spanish true if this if for a spanish PMI
     * @return response XML
     * 
     * @throws ValidationException if error loading FDB monograph
     */
    PatientMedicationInstructionVo retrievePatientMedicationInformation(long gcnSeqNo, boolean spanish)
        throws ValidationException;

    /**
     * This performs the FDB Search Option
     * 
     * @param fdbSearchOptionVo {@link FDBSearchOptionVo}
     * @param user UserVo
     * @return FDBSearchOptionVo
     */
    FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo fdbSearchOptionVo, UserVo user);
    
    /**
     * overloaded method
     * performFDBSearchOption
     *
     * @param pFdbSearchOption pFdbSearchOption
     * @param user UserVo
     * @param fdbSearchOptionType fdbSearchOptionType
     * @return FDBSearchOptionVo
     */
    FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo pFdbSearchOption, 
        FDBSearchOptionType fdbSearchOptionType, UserVo user);
}
