/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.aspects;


import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.exception.PresentationSecurityException;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;


/**
 * Checks the execution of every public method on the classes in the controller 
 * packages and sub-packages for the @RoleNeeded annotation. If the user does not
 * have the required role, an error is thrown.
 *
 */
@Component
@Aspect
public class SecurityAspect {


    /**
     * The HttpSession
     */
    @Autowired
    private HttpSession session;

    /**
     * 
     * The advice executed before all controller methods with the @RoleNeeded annotation
     *
     * @param roleNeeded the annotation containing the roles need to perform the action
     * @throws PresentationSecurityException 
     */
    @Before(
        value = "execution(public * gov.va.med.pharmacy.peps.presentation.common.controller..*(..)) && @annotation(roleNeeded)",
        argNames = "roleNeeded")
    public void checkRole(RoleNeeded roleNeeded) throws PresentationSecurityException {

        UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);

        UserVo user = userContext.getUser();

        if (user != null && !userContext.getUser().hasAnyRole(roleNeeded.roles())) {
            throw new PresentationSecurityException(PresentationSecurityException.ROLE_REQUIRED);
        }


    }

}
