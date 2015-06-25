/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;


/**
 * 
 * Helper interceptor
 *
 */
public class PepsSpringInterceptor extends HandlerInterceptorAdapter {

    /**
      * Get the UserContext object from the HttpSession.
      * 
      * @param session HttpSession containing the UserContext
      * @return UserContext
      */
    public static UserContext getUserContext(HttpSession session) {
        UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);

        if (userContext == null) {
            userContext = new UserContext();
            session.setAttribute(ControllerConstants.USER_CONTEXT_KEY, new UserContext());
        }

        return userContext;
    }

    /**
     * Sets the UserContext on the session
     * @param request HttpRequest
     * @param session HttpSession
     * @param userContext User context
     */
    public static void setUserContext(HttpServletRequest request, HttpSession session, UserContext userContext) {

        // reset the UserContext in case changes were made
        if (request.isRequestedSessionIdValid()) {
            session.setAttribute(ControllerConstants.USER_CONTEXT_KEY, userContext);
        }
    }

    /**
     * 
     * Gets the user out of the user context
     *
     * @param session HttpSession
     * @return User
     */
    public static UserVo getUser(HttpSession session) {
        return getUserContext(session).getUser();
    }

    /**
     * Adds all fields with the specified Annotation of class clazz and its superclasses to allFields     
     *
     * 
     * @param annotationClass annotated class
     * @param clazz the class
     * @param <T> t
     * @param allFields list of all fields in POJO
     * 
     */
    public static <T> void addAllAnnotatedFields(Class<? extends Annotation> annotationClass, Class<T> clazz,
        List<Field> allFields) {
        if (clazz == null) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Annotation ann = field.getAnnotation(annotationClass);

            if (ann != null) {
                allFields.add(field);
            }
        }

        addAllAnnotatedFields(annotationClass, clazz.getSuperclass(), allFields);
    }
}
