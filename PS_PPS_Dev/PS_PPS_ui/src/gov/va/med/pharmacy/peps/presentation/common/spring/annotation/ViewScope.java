/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Get and set a field from and to Spring Web Flow's view scope.
 * 
 * View scope gets allocated when a view-state enters and destroyed when the state exits. View scope is only referenceable
 * from within a view-state.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ViewScope {

    /**
     * Key to use in view scope for the given field
     * 
     * Default to an empty string that the interceptor will interpret as an instruction to use the field name as the key.
     */
    String key() default "";

    /**
     * The "direction" the view scoped field should be set. Direction.IN will only be set incoming into the Action,
     * Direction.OUT will be set into view scope only on the way out of an Action, and Direction.BOTH sets the Action on the
     * way in and the view scope on the way out. The default is Direction.BOTH.
     */
    Direction direction() default Direction.BOTH;
}
