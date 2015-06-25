/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.List;


/**
 * ModificationSummary's brief summary
 * 
 * Details of ModificationSummary's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ModificationSummary {
    
    private List<Modification> modifications;

    /**
     * getModifications
     *
     * @return List
     */
    public List<Modification> getModifications() {
        return modifications;
    }

    /**
     * setModifications
     *
     * @param modifications List
     */
    public void setModifications(List<Modification> modifications) {
        this.modifications = modifications;
    }
}
