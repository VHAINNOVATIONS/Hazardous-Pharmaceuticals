/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbProductVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ShapeVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;

import firstdatabank.database.FDBException;



/**
 * Provides drug reference information.
 */
public interface DrugReferenceCapability {
   
    /**
     * getColors
     * @return List<ColorVo>
     */
    List<ColorVo> getColors();
    
    
    /**
     * getShapes
     * @return List<ShapeVo>
     */
    List<ShapeVo> getShapes();
    
    /**
     * Retrieve the PMI data.
     * 
     * @param request XML request
     * @return XML response
     */
    String retrievePatientMedicationInformation(String request);

    /**
     * Retrieve the warning labels
     * 
     * @param vo XML request
     * @return ReportProductVo
     */
    ReportProductVo retrieveWarningLabels(ReportProductVo vo);
    
    
    /**
     * Retrieve the Multi-Source Code
     * 
     * @param vo ProductVo
     * @return ProductVo
     */
    ProductVo retrieveMultiSource(ProductVo vo);
    
    
    /**
     * Retrieve the PMI data.
     * 
     * @param gcnSeqNo GcnSeqNo
     * @param spanish True if spanish is requested.
     * @return PatientMedicationInstructionVo
     * 
     * @throws ValidationException if error loading FDB monograph
     */
    PatientMedicationInstructionVo retrievePatientMedicationInformation(long gcnSeqNo, boolean spanish)
        throws ValidationException;

    /**
     * Performs an FDB Search based on Search Criteria.
     * 
     * @param fdbSearchOptionVo The search options
     * @param user User
     * @return XML response
     */
    FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo fdbSearchOptionVo, UserVo user);
 
    /**
     * Performs an FDB Search based on a single NDC.
     * 
     * @param fdbSearchOptionVo The search options
     * @return FDBSearchOptionVo
     */
    FDBSearchOptionVo getOneNdc(FDBSearchOptionVo fdbSearchOptionVo);

    
    /**
     * Performs an FDB Search based on Search Criteria.
     * 
     * @param gcnSeqNo The GCNSeqNo
     * @return ProductVo
     */
    ProductVo retrieveProductByGcn(String gcnSeqNo);

    /**
     * Performs an FDB Search based on Search Criteria.
     * 
     * @param ndcvo The ndcvo
     * @return ProductVo
     * @throws FDBException FDBException
     */
    NdcVo populateFdbNdcFields(NdcVo ndcvo) throws FDBException;


    /**
     * This method is used to populate the FdbAdVo with details for the fdb add queue
     * 
     * @param ndc ndcVO
     * @return FdbAddVo 
     * @throws FDBException 
     */
    FdbAddVo populateFdbAddVoFields(String ndc) throws FDBException;
    
    /**
     * populateFdbProductFields
     * @param gcnSeqNo gcnSeqNo
     * @return FdbProductVo
     * @throws FDBException FDBException
     */
    FdbProductVo populateFdbProductFields(String gcnSeqNo) throws FDBException;

    /**
     * performFDBSearchOption
     *
     * @param pFdbSearchOption pFdbSearchOption
     * @param fdbSearchOptionType fdbSearchOptionType
     * @param user UserVo
     * @return FDBSearchOptionVo
     */
    FDBSearchOptionVo performFDBSearchOption(FDBSearchOptionVo pFdbSearchOption, 
        FDBSearchOptionType fdbSearchOptionType, UserVo user);



}
