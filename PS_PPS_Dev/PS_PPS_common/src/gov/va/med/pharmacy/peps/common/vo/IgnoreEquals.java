/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Denotes a field that should be ignored during equals testing or hash code generation.
 * <p>
 * Class member variables annotated will not be tested for equality.
 * 
 * @see ValueObject#equals(Object)
 * @see ValueObject#hashCode()
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IgnoreEquals {

}
