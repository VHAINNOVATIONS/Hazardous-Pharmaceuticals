/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data representing a report.
 * 
 */
public class ReportVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private ReportType reportType;

    private boolean generateOn;
    private boolean isPrintable;
    private boolean isDateRange;
    private boolean hasStart;
    private boolean hasStop;
    private boolean hasDesc;

    private boolean description;
    private Date startDate;
    private Date stopDate;
    private boolean reportTypeChanged;
    private boolean isVUIDResults;
    private boolean isGeneralResults;
    private boolean isDrugClassResults;
    private Date csvStart;
    private Date csvComplete;
    private int recordCount;
    private int recordTotal;

    private List<ReportDrugClassVo> reportDrugClassList = new ArrayList<ReportDrugClassVo>();
    private List<ReportProductVo> reportProductList = new ArrayList<ReportProductVo>();

    private List<ReportVuidVo> reportVuidIngredientList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidModifiedIngredientList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidProductList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidModifiedList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidGenericList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidModifiedGenericList = new ArrayList<ReportVuidVo>();
    private List<ReportVuidVo> reportVuidDrugClassList = new ArrayList<ReportVuidVo>();

    /**
    * constructor
    */
    public ReportVo() {

    }

    /**
     * Description
     *
     */
    public void clearAll() {

        reportDrugClassList.clear();
        reportProductList.clear();
        reportVuidIngredientList.clear();
        reportVuidModifiedIngredientList.clear();
        reportVuidProductList.clear();
        reportVuidModifiedList.clear();
        reportVuidGenericList.clear();
        reportVuidModifiedGenericList.clear();
        reportVuidDrugClassList.clear();
        isDrugClassResults = false;
        isGeneralResults = false;
        isVUIDResults = false;
        setStartDate(null);
        setStopDate(null);
        setDescription(false);
        setGenerateOn(false);

    }

    /**
     * getReportType
     * 
     * @return ReportsType
     */
    public ReportType getReportType() {

        return reportType;
    }

    /**
     * setReportType
     * @param reportType reportType
     */
    public void setReportType(ReportType reportType) {

        setReportTypeChanged(this.reportType != reportType);
        this.reportType = reportType;
    }

    /**
     * isReportTypeChanged
     * 
     * @return reportTypeChanged property
     */
    public boolean isReportTypeChanged() {

        return reportTypeChanged;
    }

    /**
     * setReportTypeChanged
     * 
     * @param reportTypeChanged reportTypeChanged property
     */
    public void setReportTypeChanged(boolean reportTypeChanged) {

        this.reportTypeChanged = reportTypeChanged;
    }

    /**
     * setPrintable
     * 
     * @param printable the isPrintable to set
     */
    public void setPrintable(boolean printable) {

        this.isPrintable = printable;
    }

    /**
     * isPrintable
     * 
     * @return the isPrintable
     */
    public boolean isPrintable() {

        return isPrintable;
    }

    /**
     * setDateRange
     * 
     * @param dtRange the isDateRange to set
     */
    public void setDateRange(boolean dtRange) {

        this.isDateRange = dtRange;
    }

    /**
     * isDateRange
     * 
     * @return the isDateRange
     */
    public boolean isDateRange() {

        return isDateRange;
    }

    /**
     * setStartDate
     * 
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }

    /**
     * getStartDate
     * 
     * @return the startDate
     */
    public Date getStartDate() {

        return startDate;
    }

    /**
     * setReportDrugClassList
     * 
     * @param reportDrugClassList the reportDrugClassList to set
     */
    public void setReportDrugClassList(List<ReportDrugClassVo> reportDrugClassList) {

        this.reportDrugClassList = reportDrugClassList;
    }

    /**
     * getReportDrugClassList
     * 
     * @return the reportDrugClassesList
     */
    public List<ReportDrugClassVo> getReportDrugClassList() {

        return reportDrugClassList;
    }

    /**
     * setReportProductList
     * 
     * @param reportProductList the reportProductList to set
     */
    public void setReportProductList(List<ReportProductVo> reportProductList) {

        this.reportProductList = reportProductList;
    }

    /**
     * getReportProductList
     * 
     * @return the reportProductsList
     */
    public List<ReportProductVo> getReportProductList() {

        return reportProductList;
    }

    /**
     * setReportVuidProductList
     * 
     * @param reportVuidProductList the reportVuidProductList to set
     */
    public void setReportVuidProductList(List<ReportVuidVo> reportVuidProductList) {

        this.reportVuidProductList = reportVuidProductList;
    }

    /**
     * getReportVuidProductList
     * 
     * @return the reportVuidProductsList
     */
    public List<ReportVuidVo> getReportVuidProductList() {

        return reportVuidProductList;
    }

    /**
     * setReportVuidModifiedList
     * 
     * @param reportVuidModifiedList the reportVuidModifiedList to set
     */
    public void setReportVuidModifiedList(List<ReportVuidVo> reportVuidModifiedList) {

        this.reportVuidModifiedList = reportVuidModifiedList;
    }

    /**
     * getReportVuidModifiedList
     * 
     * @return the reportVuidModifiedList
     */
    public List<ReportVuidVo> getReportVuidModifiedList() {

        return reportVuidModifiedList;
    }

    /**
     * reportVuidGenericList
     * 
     * @param list the reportVuidGenericList to set
     */
    public void setReportVuidGenericList(List<ReportVuidVo> list) {

        this.reportVuidGenericList = list;
    }

    /**
     * getReportVuidGenericList
     * 
     * @return the reportVuidGenericList
     */
    public List<ReportVuidVo> getReportVuidGenericList() {

        return reportVuidGenericList;
    }

    /**
     * setReportVuidDrugClassList
     * 
     * @param reportVuidDrugClassList the reportVuidDrugClassList to set
     */
    public void setReportVuidDrugClassList(List<ReportVuidVo> reportVuidDrugClassList) {

        this.reportVuidDrugClassList = reportVuidDrugClassList;
    }

    /**
     * getReportVuidDrugClassList
     * 
     * @return the reportVuidDrugClassList
     */
    public List<ReportVuidVo> getReportVuidDrugClassList() {

        return reportVuidDrugClassList;
    }

    /**
     * setVUIDResults
     * 
     * @param vUIDResults the isVUIDResults to set
     */
    public void setVUIDResults(boolean vUIDResults) {

        this.isVUIDResults = vUIDResults;
    }

    /**
     * isVUIDResults
     * 
     * @return the isVUIDResults
     */
    public boolean isVUIDResults() {

        return isVUIDResults;
    }

    /**
     * Description
     *
     * @param generalResults generalResults
     */
    public void setGeneralResults(boolean generalResults) {

        this.isGeneralResults = generalResults;
    }

    /**
     * Description
     *
     * @return boolean
     */
    public boolean isGeneralResults() {

        return isGeneralResults;
    }

    /**
     * Description
     *
     * @param drugClassResults drugClassResults
     */
    public void setDrugClassResults(boolean drugClassResults) {

        this.isDrugClassResults = drugClassResults;
    }

    /**
     * Description
     *
     * @return boolean
     */
    public boolean isDrugClassResults() {

        return isDrugClassResults;
    }

    /**
     * setReportVuidIngredientList
     * 
     * @param reportVuidIngredientList the reportVuidIngredientList to set
     */
    public void setReportVuidIngredientList(List<ReportVuidVo> reportVuidIngredientList) {

        this.reportVuidIngredientList = reportVuidIngredientList;
    }

    /**
     * getReportVuidIngredientList
     * 
     * @return the reportVuidIngredientsList
     */
    public List<ReportVuidVo> getReportVuidIngredientList() {

        return reportVuidIngredientList;
    }

    /**
     * setReportVuidModifiedGenericList
     * 
     * @param reportVuidModifiedGenericList the reportVuidModifiedGenericList to set
     */
    public void setReportVuidModifiedGenericList(List<ReportVuidVo> reportVuidModifiedGenericList) {

        this.reportVuidModifiedGenericList = reportVuidModifiedGenericList;
    }

    /**
     * getReportVuidModifiedGenericList
     * 
     * @return the reportVuidModifiedGenericList
     */
    public List<ReportVuidVo> getReportVuidModifiedGenericList() {

        return reportVuidModifiedGenericList;
    }

    /**
     * setReportVuidModifiedIngredientList
     * 
     * @param reportVuidModifiedIngredientList the reportVuidModifiedIngredientList to set
     */
    public void setReportVuidModifiedIngredientList(List<ReportVuidVo> reportVuidModifiedIngredientList) {

        this.reportVuidModifiedIngredientList = reportVuidModifiedIngredientList;
    }

    /**
     * getReportVuidModifiedIngredientList
     * 
     * @return the reportVuidIngredientsList
     */
    public List<ReportVuidVo> getReportVuidModifiedIngredientList() {

        return reportVuidModifiedIngredientList;
    }

    /**
     * setStopDate
     * 
     * @param stopDate the stopDate to set
     */
    public void setStopDate(Date stopDate) {

        this.stopDate = stopDate;
    }

    /**
     * getStopDate
     * 
     * @return the stopDate
     */
    public Date getStopDate() {

        return stopDate;
    }

    /**
     * setDescription
     * 
     * @param description the description to set
     */
    public void setDescription(boolean description) {

        this.description = description;
    }

    /**
     * isDescription
     * 
     * @return the description
     */
    public boolean isDescription() {

        return description;
    }

    /**
     * getCsvStart
     *
     * @return Date
     */
    public Date getCsvStart() {

        return csvStart;
    }

    /**
     * setCsvStart
     *
     * @param csvStart Date
     */
    public void setCsvStart(Date csvStart) {

        this.csvStart = csvStart;
    }

    /**
     * getCsvComplete
     *
     * @return Date
     */
    public Date getCsvComplete() {

        return csvComplete;
    }

    /**
     * setCsvComplete
     *
     * @param csvStop Date
     */
    public void setCsvComplete(Date csvStop) {

        this.csvComplete = csvStop;
    }

    /**
     * setHasStart
     *
     * @param hasStart the hasStart to set
     */
    public void setHasStart(boolean hasStart) {

        this.hasStart = hasStart;
    }

    /**
     * isHasStart
     *
     * @return the hasStart
     */
    public boolean isHasStart() {

        return hasStart;
    }

    /**
     * setHasStop
     *
     * @param hasStop the hasStop to set
     */
    public void setHasStop(boolean hasStop) {

        this.hasStop = hasStop;
    }

    /**
     * isHasStop
     *
     * @return the hasStop
     */
    public boolean isHasStop() {

        return hasStop;
    }

    /**
     * isHasDesc
     *
     * @return boolean
     */
    public boolean isHasDesc() {

        return hasDesc;
    }

    /**
     * setHasDesc
     *
     * @param hasDesc boolean
     */
    public void setHasDesc(boolean hasDesc) {

        this.hasDesc = hasDesc;
    }

    /**
     * setGenerateOn
     * @param generateOn the generateOn to set
     */
    public void setGenerateOn(boolean generateOn) {

        this.generateOn = generateOn;
    }

    /**
     * isGenerateOn
     * @return the generateOn
     */
    public boolean isGenerateOn() {

        return generateOn;
    }

    /**
     * setRecordCount
     * @param recordCount the recordCount to set
     */
    public void setRecordCount(int recordCount) {

        this.recordCount = recordCount;
    }

    /**
     * getRecordCount
     * @return the recordCount
     */
    public int getRecordCount() {

        return recordCount;
    }

    /**
     * setRecordTotal
     * @param recordTotal the recordTotal to set
     */
    public void setRecordTotal(int recordTotal) {

        this.recordTotal = recordTotal;
    }

    /**
     * getRecordTotal
     * @return the recordTotal
     */
    public int getRecordTotal() {

        return recordTotal;
    }

}
