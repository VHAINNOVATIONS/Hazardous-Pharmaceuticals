/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.displaytag;


import org.displaytag.model.Cell;
import org.displaytag.model.DefaultComparator;


/**
 * Check if the given fields to compare are actually numeric to compare them as {@link Number} instances instead of
 * {@link String}.
 */
public class NumericComparator extends DefaultComparator {

    /**
     * Compares two given objects. Not comparable objects are compared using their string representation. String comparisons
     * are done using a Collator.
     * 
     * @param object1 first parameter
     * @param object2 second parameter
     * @return the value
     * 
     * @see org.displaytag.model.DefaultComparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object object1, Object object2) {
        Object one = object1;
        Object two = object2;

        if (one != null && two != null) {
            String firstValue = ((Cell) one).getStaticValue().toString().trim();
            String secondValue = ((Cell) two).getStaticValue().toString().trim();

            // try to convert to Double instances to sort numerically
            try {
                one = Double.valueOf(firstValue);
                two = Double.valueOf(secondValue);
            } catch (NumberFormatException e) {
                one = firstValue;
                two = secondValue;
            }
        }

        return super.compare(one, two);
    }
}
