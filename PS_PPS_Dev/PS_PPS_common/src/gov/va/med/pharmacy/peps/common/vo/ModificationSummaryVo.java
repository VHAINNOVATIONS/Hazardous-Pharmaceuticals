/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Contains data returned when a modification is submitted.
 */
public class ModificationSummaryVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>(0);
    private Errors warnings = new Errors();

    /**
     * Empty Constructor
     */
    public ModificationSummaryVo() {
        super();
    }

    /**
     * Set the differences and warnings.
     * 
     * @param modDifferences Collection of {@link ModDifferenceVo}
     * @param warnings {@link Errors} object representing warnings
     */
    public ModificationSummaryVo(Collection<ModDifferenceVo> modDifferences, Errors warnings) {
        this.modDifferences = modDifferences;
        this.warnings = warnings;
    }

    /**
     * getModDifferences
     * 
     * @return modDifferences property
     */
    public Collection<ModDifferenceVo> getModDifferences() {
        return modDifferences;
    }

    /**
     * setModDifferences
     * 
     * @param modDifferences modDifferences property
     */
    public void setModDifferences(Collection<ModDifferenceVo> modDifferences) {
        this.modDifferences = new ArrayList<ModDifferenceVo>();

        if (modDifferences != null && !modDifferences.isEmpty()) {
            this.modDifferences.addAll(modDifferences);
        }
    }

    /**
     * getWarnings for ModificationSummaryVO.
     * 
     * @return warnings property
     */
    public Errors getWarnings() {
        return warnings;
    }

    /**
     * setWarnings for ModificationSummaryVo.
     * 
     * @param warnings warnings property
     */
    public void setWarnings(Errors warnings) {
        this.warnings = warnings;
    }

    /**
     * Return true if the warnings is not null and if there are warnings for ModificationSummaryVO.
     * 
     * @return boolean
     */
    public boolean hasWarnings() {
        return warnings != null && !warnings.hasErrors();
    }
}
