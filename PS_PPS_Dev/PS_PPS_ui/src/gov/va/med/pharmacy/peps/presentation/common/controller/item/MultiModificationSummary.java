/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.Collection;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo;


/**
 * MultiModificationSummary's brief summary
 * 
 * Details of MultiModificationSummary's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class MultiModificationSummary {

    private Map<String, ModificationSummary> modificationSummaries;
    private Collection<ItemModDifferenceVo> differences;

    /**
     * getModificationSummaries
     *
     * @return Map<String, ModificationSummary>
     */
    public Map<String, ModificationSummary> getModificationSummaries() {
        return modificationSummaries;
    }

    /**
     * setModificationSummaries
     *
     * @param modificationSummaries Map<String, ModificationSummary>
     */
    public void setModificationSummaries(Map<String, ModificationSummary> modificationSummaries) {
        this.modificationSummaries = modificationSummaries;
    }

    /**
     * getDifferences
     *
     * @return Collection<ItemModDifferenceVo>
     */
    public Collection<ItemModDifferenceVo> getDifferences() {
        return differences;
    }

    /**
     * setDifferences
     *
     * @param differences Collection<ItemModDifferenceVo>
     */
    public void setDifferences(Collection<ItemModDifferenceVo> differences) {
        this.differences = differences;
    }

}
