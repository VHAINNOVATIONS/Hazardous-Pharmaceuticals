/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ReportDomainCapability;
import gov.va.med.pharmacy.peps.service.common.reports.ReportExportState;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * Search and retrieve reports.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 *
 */
public class ReportsServiceBean extends AbstractPepsStatelessSessionBean<ReportsService> implements ReportsService {

    private static final long serialVersionUID = 1L;

    /**
     * Retrieve reports detail by ID.
     * @param pstartDate Start Date
     * @param pendDate End Date
     * 
     * @return ReportProductVo
     * 
     * @throws ItemNotFoundException if cannot find Report with given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getProductExclusionData()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ReportProductVo> getProductExclusionData(Date pstartDate, Date pendDate) throws ItemNotFoundException {
        return this.getService().getProductExclusionData(pstartDate, pendDate);
    }

    /**
     * Retrieve reports detail by ID.
     * 
     * @return ReportProductVo
     * 
     * @throws ItemNotFoundException if cannot find Report with given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getProductNoActiveNdcData()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ReportProductVo> getProductNoActiveNdcData() throws ItemNotFoundException {
        return this.getService().getProductNoActiveNdcData();
    }

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate Date
     * @param pendDate Date
     * 
     * @return ReportProductVo
     * 
     * @throws ItemNotFoundException if cannot find Report with given ID
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getProductProposedInactivationDate()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ReportProductVo> getProductProposedInactivationDate(Date pstartDate, Date pendDate)
        throws ItemNotFoundException {
        return this.getService().getProductProposedInactivationDate(pstartDate, pendDate);
    }

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate The StartDate for the Ingredient VuidApprovalReport
     * @return ReportProductVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportIngredient()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportIngredient(Date pstartDate) {
        return this.getService().getVuidApprovalReportIngredient(pstartDate);
    }

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate Start Date
     * @return ReportProductVo 
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportIngredient()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedIngredient(Date pstartDate) {
        return this.getService().getVuidApprovalReportIngredient(pstartDate);
    }

    /**
     * Retrieve reports detail by ID.
     * @param pstartDate Start Date
     * @return ReportProductVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportProducts()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportProducts(Date pstartDate) {
        return this.getService().getVuidApprovalReportProducts(pstartDate);
    };

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate Start Date
     * @return ReportProductVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportGeneric()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportGeneric(Date pstartDate) {
        return this.getService().getVuidApprovalReportGeneric(pstartDate);
    };

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate Start Date
     * @return ReportProductVo
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportModifiedProducts()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedGeneric(Date pstartDate) {
        return this.getService().getVuidApprovalReportModifiedGeneric(pstartDate);
    };

    /**
     * Retrieve reports detail by ID.
     * @param pstartDate Start Date
     * @return ReportProductVo
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportDrugClasses()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportDrugClasses(Date pstartDate) {
        return this.getService().getVuidApprovalReportDrugClasses(pstartDate);
    };

    /**
     * Retrieve reports detail by ID.
     * 
     * @param pstartDate Start Date
     * @return ReportProductVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getVuidApprovalReportModifiedProducts()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public ReportVuidApprovalVo getVuidApprovalReportModifiedProducts(Date pstartDate) {
        return this.getService().getVuidApprovalReportModifiedProducts(pstartDate);
    };

    /**
     * Retrieve reports detail by ID.
     * 
     * @return ReportProductVo
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportService#getDrugClassData()
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public List<ReportDrugClassVo> getDrugClassData() {
        return this.getService().getDrugClassData();
    }

    /**
     * getReportDomainCapability
     * 
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportsService#getReportDomainCapability()
     * 
     * @return ReportDomainCapability
     * 
     * @ejb.interface-method
     *  
     * @ejb.transaction type = "Required"
     */
    public ReportDomainCapability getReportDomainCapability() {
        return this.getService().getReportDomainCapability();
    }

    /**
     * Get Ids
     * @see gov.va.med.pharmacy.peps.service.common.session.ReportsService#getIds(gov.va.med.pharmacy.peps.common.vo.ReportType)
     * @param pReportType ReportType
     * @return ReportProductVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public List<Long> getIds(ReportType pReportType) {
        return this.getService().getIds(pReportType);
    }

    /**
     * startProcess
     * @param reportType Type of report to generate
     * @return reportService 
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public ReportExportState startProcess(ReportType reportType) {
        return this.getService().startProcess(reportType);
    }

    /**
     * getStatus 
     * @param reportType Type of report
     * @return reportService.getStatus()
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public ReportExportState getStatus(ReportType reportType) {
        return this.getService().getStatus(reportType);
    }

    /**
     * stopProcess 
     * @return reportsService
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public ReportExportState stopProcess() {
        return this.getService().stopProcess();
    }

//    /** 
//     * @ejb.interface-method
//     * 
//     * @ejb.transaction type= "Required"
//     */
//    @Override
//    public Date getDateValue(NationalSetting ptype) {
//        return this.getService().getDateValue(ptype);
//    }

    /**
     * getNationalSettingDomainCapability
     * @return National Setting List
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public NationalSettingDomainCapability getNationalSettingDomainCapability() {
        return this.getService().getNationalSettingDomainCapability();

    }

    /** 
     * getGenerateDates
     * @return List<NationalSettingVo>
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type= "Required"
     */
    @Override
    public List<NationalSettingVo> getGenerateDates() {
        return this.getService().getGenerateDates();
    }

}
