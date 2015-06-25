/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;


/**
 * SecurityInterceptor's brief summary
 * 
 * Details of SecurityInterceptor's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class SecurityInterceptor extends PepsSpringInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        RoleNeeded roleNeeded = AnnotationUtils.findAnnotation(handler.getClass(), RoleNeeded.class);

        UserVo user = getUser(request.getSession());

        if (roleNeeded != null && (user != null && !user.hasAnyRole(roleNeeded.roles()))) {
            throw new SecurityException();
        }

        return true;
    }


}
