/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;


/**
 * Search and retrieve requests.
 */
public interface ReportsService {

    /**
     * startProcess
     * @param reportType type of report to export
     * @return ReportExportState
     */
    ReportExportState startProcess(ReportType reportType);

    /**
     * getStatus
     * @param reportType type of report to export
     * @return ReportExportState
     */
    ReportExportState getStatus(ReportType reportType);

    /**
     * stopProcess
     * @return ReportExportState
     */
    ReportExportState stopProcess();

    /**
     * Retrieve request detail by ID.
     * 
     * @param pstartDate RequestVo ID to retrieve
     * @param pendDate Date the End
     * @return RequestVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     */
    List<ReportProductVo> getProductExclusionData(Date pstartDate, Date pendDate) throws ItemNotFoundException;

    /**
     * getProductNoActiveNdcData
     * @return List of ReportProductVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    List<ReportProductVo> getProductNoActiveNdcData() throws ItemNotFoundException;

    /**
     * getProductProposedInactivationDate
     * @param pstartDate pstartDate
     * @param pendDate pendDate
     * @return List of ReportProductVos
     * @throws ItemNotFoundException ItemNotFoundException
     */
    List<ReportProductVo> getProductProposedInactivationDate(Date pstartDate, Date pendDate) throws ItemNotFoundException;

    /**
     * getReportDomainCapability
     * @return ReportDomainCapability
     */
    ReportDomainCapability getReportDomainCapability();

    /**
     * getNationalSettingDomainCapability
     * @return NationalSettingDomainCapability
     */
    NationalSettingDomainCapability getNationalSettingDomainCapability();

    /**
     * getVuidApprovalReportIngredient
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportIngredient(Date pstartDate);

    /**
     * getVuidApprovalReportModifiedIngredient
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportModifiedIngredient(Date pstartDate);

    /**
     * getVuidApprovalReportProducts
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportProducts(Date pstartDate);

    /**
     * getVuidApprovalReportModifiedProducts
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportModifiedProducts(Date pstartDate);

    /**
     * getVuidApprovalReportGeneric
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportGeneric(Date pstartDate);

    /**
     * getVuidApprovalReportModifiedGeneric
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportModifiedGeneric(Date pstartDate);

    /**
     * getVuidApprovalReportDrugClasses
     * @param pstartDate pstartDate
     * @return ReportVuidApprovalVo
     */
    ReportVuidApprovalVo getVuidApprovalReportDrugClasses(Date pstartDate);

    /**
     * getDrugClassData
     * @return List of ReportDrugClassVo
     */
    List<ReportDrugClassVo> getDrugClassData();

    /**
     * getIds
     * @param pReportType pReportType
     * @return List of Longs
     */
    List<Long> getIds(ReportType pReportType);

    /**
     * getGenerateDates
     * @return List of NatonalSettingVo
     */
    List<NationalSettingVo> getGenerateDates();

//    /**
//     * getDateValue
//     * @param type type
//     * @return Date
//     */
//    Date getDateValue(NationalSetting type);

}
