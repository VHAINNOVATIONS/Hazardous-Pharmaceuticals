/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Represent data returned from a drug information request
 */
public class DrugInfoResultsVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Collection<DrugInfoVo> drugsNotFound = new ArrayList<DrugInfoVo>();
    private Collection<DrugInfoVo> drugs = new ArrayList<DrugInfoVo>();

    /**
     * description
     * @return drugsNotFound property
     */
    public Collection<DrugInfoVo> getDrugsNotFound() {
        return drugsNotFound;
    }

    /**
     * description
     * @param drugsNotFound drugsNotFound property
     */
    public void setDrugsNotFound(Collection<DrugInfoVo> drugsNotFound) {
        this.drugsNotFound = drugsNotFound;
    }

    /**
     * description
     * @return drugs property
     */
    public Collection<DrugInfoVo> getDrugs() {
        return drugs;
    }

    /**
     * description
     * @param drugs drugs property
     */
    public void setDrugs(Collection<DrugInfoVo> drugs) {
        this.drugs = drugs;
    }
}
