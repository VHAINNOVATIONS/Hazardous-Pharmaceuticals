/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import gov.va.med.pharmacy.peps.common.vo.Role;


/**
 * RoleNeeded's brief summary
 * 
 * Details of RoleNeeded's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface RoleNeeded {
    
    /**
     * 
     * Description
     *
     * @return
     */
    Role[] roles();

}
