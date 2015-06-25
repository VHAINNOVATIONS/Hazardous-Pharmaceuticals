/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.Collection;
import java.util.HashSet;

import gov.va.med.pharmacy.peps.common.vo.DrugDataVendorVersionVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Results of requested order checks
 */
public class OrderCheckResultsVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private Collection<DrugCheckVo> drugsNotChecked = new HashSet<DrugCheckVo>(); // we want no duplicates
    private DrugCheckResultsVo<DrugDrugCheckResultVo> drugDrugCheckResults;
    private DrugCheckResultsVo<DrugDoseCheckResultVo> drugDoseCheckResults;
    private DrugCheckResultsVo<DrugTherapyCheckResultVo> drugTherapyCheckResults;

    private OrderCheckHeaderVo header;
    private DrugDataVendorVersionVo drugDataVendorVersion;

    /**
     * description
     */
    public OrderCheckResultsVo() {
        super();
    }

    /**
     * description
     * @return doseCheckResults property
     */
    public DrugCheckResultsVo<DrugDoseCheckResultVo> getDrugDoseCheckResults() {
        return drugDoseCheckResults;
    }

    /**
     * description
     * @param doseCheckResults doseCheckResults property
     */
    public void setDrugDoseCheckResults(DrugCheckResultsVo<DrugDoseCheckResultVo> doseCheckResults) {
        this.drugDoseCheckResults = doseCheckResults;
    }

    /**
     * description
     * @return drugCheckResults property
     */
    public DrugCheckResultsVo<DrugDrugCheckResultVo> getDrugDrugCheckResults() {
        return drugDrugCheckResults;
    }

    /**
     * description
     * @param drugCheckResults drugCheckResults property
     */
    public void setDrugDrugCheckResults(DrugCheckResultsVo<DrugDrugCheckResultVo> drugCheckResults) {
        this.drugDrugCheckResults = drugCheckResults;
    }

    /**
     * description
     * @return drugsNotChecked property
     */
    public Collection<DrugCheckVo> getDrugsNotChecked() {
        return drugsNotChecked;
    }

    /**
     * description
     * @param drugsNotChecked drugsNotChecked property
     */
    public void setDrugsNotChecked(Collection<DrugCheckVo> drugsNotChecked) {
        this.drugsNotChecked = drugsNotChecked;
    }

    /**
     * description
     * @return therapyCheckResults property
     */
    public DrugCheckResultsVo<DrugTherapyCheckResultVo> getDrugTherapyCheckResults() {
        return drugTherapyCheckResults;
    }

    /**
     * description
     * @param therapyCheckResults therapyCheckResults property
     */
    public void setDrugTherapyCheckResults(DrugCheckResultsVo<DrugTherapyCheckResultVo> therapyCheckResults) {
        this.drugTherapyCheckResults = therapyCheckResults;
    }

    /**
     * description
     * @return drugDataVendorVersion property
     */
    public DrugDataVendorVersionVo getDrugDataVendorVersion() {
        return drugDataVendorVersion;
    }

    /**
     * description
     * @param drugDataVendorVersion drugDataVendorVersion property
     */
    public void setDrugDataVendorVersion(DrugDataVendorVersionVo drugDataVendorVersion) {
        this.drugDataVendorVersion = drugDataVendorVersion;
    }

    /**
     * description
     * @return orderCheckHeader property
     */
    public OrderCheckHeaderVo getHeader() {
        return header;
    }

    /**
     * description
     * @param orderCheckHeader orderCheckHeader property
     */
    public void setHeader(OrderCheckHeaderVo orderCheckHeader) {
        this.header = orderCheckHeader;
    }
}
