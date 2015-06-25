/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.diff;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Denotes a field that should be ignored during difference testing.
 * <p>
 * Class member variables annotated will not be tested for equality and will not produce a {@link Difference} object.
 * 
 * @see Diff#checkForDifferences(gov.va.med.pharmacy.peps.common.vo.ValueObject,
 *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IgnoreDifference {

}
