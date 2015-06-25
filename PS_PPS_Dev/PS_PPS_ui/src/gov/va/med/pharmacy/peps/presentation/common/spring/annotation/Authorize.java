/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Call the {@link UserService#authorizeFields(gov.va.med.pharmacy.peps.common.vo.UserVo, Object)} method if this annotation
 * is on a field.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Authorize {

}
