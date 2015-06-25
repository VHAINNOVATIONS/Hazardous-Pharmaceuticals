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
 * Get and set a field from and to Spring Web Flow's flow scope.
 * 
 * Flow scope gets allocated when a flow starts and destroyed when the flow ends.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FlowScope {

    /**
     * Key to use in flow scope for the given field
     * 
     * Default to an empty string that the interceptor will interpret as an instruction to use the field name as the key.
     */
    String key() default "";

    /**
     * The "direction" the flow scoped field should be set. Direction.IN will only be set incoming into the Action,
     * Direction.OUT will be set into flow scope only on the way out of an Action, and Direction.BOTH sets the Action on the
     * way in and the flow scope on the way out. The default is Direction.BOTH.
     */
    Direction direction() default Direction.BOTH;
}
