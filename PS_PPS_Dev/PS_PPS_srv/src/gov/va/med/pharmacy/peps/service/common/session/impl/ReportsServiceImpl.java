/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.session.ReportExportManager;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * Search and retrieve requests.
 */
public class ReportsServiceImpl implements ReportsService {

    private ReportDomainCapability reportDomainCapability;
    private NationalSettingDomainCapability nationalSettingDomainCapability;
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ReportExportManager reportExportManager;

    @Override
    public ReportExportState startProcess(ReportType reportType) {
        return reportExportManager.startProcess(reportType);
    }

    @Override
    public ReportExportState getStatus(ReportType reportType) {
        return reportExportManager.getStatus(reportType);
    }

    @Override
    public ReportExportState stopProcess() {
        return reportExportManager.stopProcess();
    }

    /**
     * getProductExclusionData().
     *
     * @param pstartDate Start Date
     * @param pendDate End Date
     * @return ReportProductVo
     * 
     * @throws ItemNotFoundException if cannot find Request with given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getProductExclusionData()
     */
    public List<ReportProductVo> getProductExclusionData(Date pstartDate, Date pendDate) throws ItemNotFoundException {
        return reportDomainCapability.getProductExclusionData(pstartDate, pendDate);
    }

    /**
     * getProductNoActiveNdcData
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportsService#getProductNoActiveNdcData()
     * @return reportDomainCapability.getProductNoActiveNdcData
     * @throws ItemNotFoundException item not found
     */
    public List<ReportProductVo> getProductNoActiveNdcData() throws ItemNotFoundException {
        return reportDomainCapability.getProductNoActiveNdcData();
    }

    /**
     * getProductProposedInactivationDate
     * @param pstartDate start date
     * @param pendDate end date
     * @throws ItemNotFoundException item not found
     * @return list
     */
    public List<ReportProductVo> getProductProposedInactivationDate(Date pstartDate, Date pendDate)
        throws ItemNotFoundException {
        return reportDomainCapability.getProductProposedInactivationDate(pstartDate, pendDate);
    }

    /**
     * getReportDomainCapability
     * @return reportDomainCapability
     */
    public ReportDomainCapability getReportDomainCapability() {
        return reportDomainCapability;
    }

    /**
     * getVuidApprovalReportIngredient
     * @param pstartDate Start Date
     * @return VuidApprovalReportIngredient list
     */
    public ReportVuidApprovalVo getVuidApprovalReportIngredient(Date pstartDate) {
        return reportDomainCapability.getVuidApprovalReportIngredient(pstartDate);
    }

    /**
     * getVuidApprovalReportModifiedIngredient
     * @param pstartDate Start Date
     * @return Vuid Mod Ingredient list
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedIngredient(Date pstartDate) {
        return reportDomainCapability.getVuidModifiedReportIngredient(pstartDate);
    }

    /**
     * getVuidApprovalReportProducts
     * @param pstartDate Start Date
     * @return Vuid New Product List
     */
    public ReportVuidApprovalVo getVuidApprovalReportProducts(Date pstartDate) {
        return reportDomainCapability.getVuidApprovalReportProducts(pstartDate);
    }

    /**
     * getVuidApprovalReportGeneric
     * @param pstartDate Start Date
     * @return Vuid New Generic List
     */
    public ReportVuidApprovalVo getVuidApprovalReportGeneric(Date pstartDate) {
        return reportDomainCapability.getVuidApprovalReportGeneric(pstartDate);
    }

    /**
     * getVuidApprovalReportModifiedGeneric
     * @param pstartDate Start Date
     * @return Vuid Mod Generic List
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedGeneric(Date pstartDate) {
        return reportDomainCapability.getVuidModifiedReportGeneric(pstartDate);
    }

    /**
     * getVuidApprovalReportDrugClasses
     * @param pstartDate Start Date
     * @return Drug class list
     */
    public ReportVuidApprovalVo getVuidApprovalReportDrugClasses(Date pstartDate) {
        return reportDomainCapability.getVuidApprovalReportDrugClasses(pstartDate);
    }

    /**
     * getDrugClassData
     * @return Drug class data
     */
    public List<ReportDrugClassVo> getDrugClassData() {
        return reportDomainCapability.getDrugClassData();
    }

    /**
     * setReportDomainCapability
     * @param reportDomainCapability reportDomainCapability
     */
    public void setReportDomainCapability(ReportDomainCapability reportDomainCapability) {
        this.reportDomainCapability = reportDomainCapability;
    }

    /**
     * getVuidApprovalReportModifiedProducts
     * @param pstartDate Start Date
     * @return Vuid Mod Product List
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedProducts(Date pstartDate) {
        return reportDomainCapability.getVuidModifiedReportProducts(pstartDate);
    }

    /**
     * getIds
     * @param pReportType report type of selected report
     * @return reportDomainCapability
     */
    public List<Long> getIds(ReportType pReportType) {
        return reportDomainCapability.getIds(pReportType);
    }

    /**
     * ReportExportManager manages generate process
     * @return the reportExportManager
     */
    public ReportExportManager getReportExportManager() {
        return reportExportManager;
    }

    /**
     * setReportExportManager
     * @param reportExportManager the reportExportManager to set
     */
    public void setReportExportManager(ReportExportManager reportExportManager) {
        this.reportExportManager = reportExportManager;
    }

    /** Retrieves a list which contain Start and stop time stamps for generated reports.
     * @return National Setting list
     */
    public List<NationalSettingVo> getGenerateDates() {
        return nationalSettingDomainCapability.retrieve();
    }

    /**
     * NationalSettingDomainCapability
     * @return nationalSettingDomainCapability
     */
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return nationalSettingDomainCapability;
    }

    /**
     * setNationalSettingDomainCapability
     * @param nationalSettingDomainCapability Stores date values of generated report times
     */
    public void setNationalSettingDomainCapability(NationalSettingDomainCapability nationalSettingDomainCapability) {
        this.nationalSettingDomainCapability = nationalSettingDomainCapability;
    }

    /**
     * PlatformTransactionManager
     * @return transactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * setTransactionManager
     * @param transactionManager transactionManager
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
