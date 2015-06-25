/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Result from duplicate therapy check
 */
public class DrugTherapyCheckResultVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private Collection<DrugCheckVo> drugs = new ArrayList<DrugCheckVo>();
    private String duplicateClass = "";
    private String shortText = "";
    private long allowance;

    /**
     * description
     */
    public DrugTherapyCheckResultVo() {
        super();
    }

    /**
     * description
     * @return allowance property
     */
    public long getAllowance() {
        return allowance;
    }

    /**
     * description
     * @param allowance allowance property
     */
    public void setAllowance(long allowance) {
        this.allowance = allowance;
    }

    /**
     * getDrugs for DrugTherapyCheckResultsVo.
     * @return drugs property
     */
    public Collection<DrugCheckVo> getDrugs() {
        return drugs;
    }

    /**
     * setDrugs  for DrugTherapyCheckResultsVo.
     * @param drugs drugs property
     */
    public void setDrugs(Collection<DrugCheckVo> drugs) {
        this.drugs = drugs;
    }

    /**
     * getDuplicateClass  for DrugTherapyCheckResultsVo
     * @return duplicateClass property
     */
    public String getDuplicateClass() {
        return duplicateClass;
    }

    /**
     * setDuplicateClass  for DrugTherapyCheckResultsVo
     * @param duplicateClass duplicateClass property
     */
    public void setDuplicateClass(String duplicateClass) {
        this.duplicateClass = duplicateClass;
    }

    /**
     * getShortText for DrugTherapyCheckResultVo
     * @return shortText property
     */
    public String getShortText() {
        return shortText;
    }

    /**
     * setShortText for DrugTherapyCheckResultVo
     * @param shortTextIn shortText property
     */
    public void setShortText(String shortTextIn) {
        this.shortText = shortTextIn;
    }
}
