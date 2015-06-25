/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;


/**
 * ReportDomainCapability
 * 
 */
public interface ReportDomainCapability {

    
    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param searchCriteria SearchCriteriaVo
     * @return The list of Ids
     */
    List<Long> getSimpleSearchIds(SearchCriteriaVo searchCriteria);
    
    
    /**
     * getIds gets just the EPL_IDs from the EPL table.
     * 
     * @param searchCriteria SearchCriteriaVo
     * @return The list of Ids
     */
    List<Long> getSimpleSynonymSearchIds(SearchCriteriaVo searchCriteria);
    
    /**
     * } getCaptureNdfData
     * 
     * @param eplId
     *            List of eplids
     * @return ReportCaptureNdfVo
     */
    List<ReportCaptureNdfVo> getCaptureNdfData(List<Long> eplId);

    /**
     * getProductWarningLabelData
     * 
     * @param eplId
     *            eplID
     * @return ReportProductVo
     */
    List<ReportProductVo> getProductIngredientData(List<Long> eplId);

    /**
     * getProductWarningLabelData
     * 
     * @return ReportProductVo
     */
    List<ReportProductVo> getProductWarningLabelData();

    /**
     * getProductExclusionData
     * 
     * @param startDate startDate
     * @param endDate endDate
     * @return ReportProductVo
     */
    List<ReportProductVo> getProductExclusionData(Date startDate, Date endDate);

    /**
     * getProductNoActiveNdcData
     * 
     * @return ReportProductVo
     */
    List<ReportProductVo> getProductNoActiveNdcData();

    /**
     * getProductProposedInactivationData
     * 
     * @param startDate
     *            The startDate
     * @param stopDate
     *            The stopDate
     * @return ReportProductVo
     */
    List<ReportProductVo> getProductProposedInactivationDate(Date startDate,
            Date stopDate);

    /**
     * getVuidApprovalReportIngredient
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportIngredient(Date startDate);
    
    /**
     * getVuidModifiedReportIngredient
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidModifiedReportIngredient(Date startDate);

    /**
     * getVuidApprovalReportDrugClasses
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportDrugClasses(Date startDate);
    
    /**
     * getVuidModifiedReportDrugClasses
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidModifiedReportDrugClasses(Date startDate);


    /**
     * getVuidApprovalReportGeneric
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportGeneric(Date startDate);
    
    /**
     * getVuidModifiedReportGeneric
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidModifiedReportGeneric(Date startDate);

    /**
     * getVuidApprovalReportVo
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportProducts(Date startDate);
    
    /**
     * getVuidModifiedReportVo
     * 
     * @param startDate startDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidModifiedReportProducts(Date startDate);

    /**
     * getDrugClassData
     * 
     * @return List<ReportDrugClassVo>
     */
    List<ReportDrugClassVo> getDrugClassData();

    /**
     * getIds for reporting
     * 
     * @param reportType
     *            reportType
     * @return list of EPL_IDS
     */
    List<Long> getIds(ReportType reportType);

}
