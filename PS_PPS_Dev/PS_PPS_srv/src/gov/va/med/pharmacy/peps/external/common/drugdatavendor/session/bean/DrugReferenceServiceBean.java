/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.bean;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;


/**
 * Retrieve drug reference information.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class DrugReferenceServiceBean extends AbstractPepsStatelessSessionBean<DrugReferenceService> implements
    DrugReferenceService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve PMI data.
     * 
     * @param request request XML from VistA
     * @return response XML
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public String retrievePatientMedicationInformation(String request) {
        return getService().retrievePatientMedicationInformation(request);
    }

    /**
     * retrieveWarningLabels
     * 
     * @param vo ReportProductVo
     * @return ReportProductVo
     *            
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     */
    @Override
    public ReportProductVo retrieveWarningLabels(ReportProductVo vo) {
        return getService().retrieveWarningLabels(vo);
    }
   
    /**
     * Retrieve PMI data.
     * 
     * @param gcnSeqNo GcnSeqNo
     * @param spanish True if this request is for spanish text.
     * @return response XML
     * 
     * @throws ValidationException if error loading FDB monograph
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public PatientMedicationInstructionVo retrievePatientMedicationInformation(long gcnSeqNo, boolean spanish)
        throws ValidationException {
        return getService().retrievePatientMedicationInformation(gcnSeqNo, spanish);
    }

    /**
     * performFDBSearchOption
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param fdbSearchOptionVo FDBSearchOptionVo
     * @param user UserVo
     * @return FDBSearchOptionVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.ConsoleService#performFDBSearchOptoin()
     */
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo fdbSearchOptionVo, UserVo user) {
        return getService().performFDBSearchOption(fdbSearchOptionVo, user);
    }

    /**
     * FDBSearchOptionVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @param pFdbSearchOption pFdbSearchOption
     * @param fdbSearchOptionType fdbSearchOptionType
     * @param user UserVo
     * @return FDBSearchOptionVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.ConsoleService#performFDBSearchOptoin()
     */
    @Override
    public FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo pFdbSearchOption,
            FDBSearchOptionType fdbSearchOptionType, UserVo user) {
        return getService().performFDBSearchOption(pFdbSearchOption, fdbSearchOptionType, user);
    }
}
