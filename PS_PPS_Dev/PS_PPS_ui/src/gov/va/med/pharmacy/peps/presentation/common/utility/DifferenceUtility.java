/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;


/**
 * Handle common {@link ModDifferenceVo} logic.
 */
public class DifferenceUtility {

    /**
     * Cannot instantiate.
     */
    private DifferenceUtility() {
        super();
    }

    /**
     * Iterates through the modDifferences to see if any are Editable data fields.
     * <p>
     * If any VA Data Field's {@link DataField#isRequestToEdit()} is false, the Collection is considered to have an editable
     * difference.
     * <p>
     * If any non-VA Data Field was changed, the Collection is considered to have an editable difference.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * @return boolean
     */
    public static boolean hasEditableDifference(Collection<ModDifferenceVo> differences) {

        if (differences != null) {
            for (ModDifferenceVo difference : differences) {
                Object newValue = difference.getDifference().getNewValue();

                if (newValue instanceof DataField) {
                    DataField dataField = (DataField) newValue;

                    if (!dataField.isRequestToEdit()) {
                        return true;
                    }
                } else {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * Iterates through the modDifferences to see if any are Editable data fields.
     * <p>
     * If any VA Data Field's {@link DataField#isRequestToEdit()} is true, the Collection is considered to have an editable
     * difference.
     * 
     * @param differences Collection of {@link ModDifferenceVo}
     * @return boolean
     */
    public static boolean hasNonEditableDifference(Collection<ModDifferenceVo> differences) {

        if (differences != null) {
            for (ModDifferenceVo difference : differences) {
                Object newValue = difference.getDifference().getNewValue();

                if (newValue instanceof DataField) {
                    DataField dataField = (DataField) newValue;

                    if (dataField.isRequestToEdit()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
