/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.presentation.common.context.UserContext;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.Authorize;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Sets up the fieldAuthorizationVo for use on the GUI
 * 
 */
public class FieldAuthorizationSpringInterceptor extends PepsSpringInterceptor {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
        ModelAndView modelAndView) {

        UserContext userContext = getUserContext(request.getSession());

        if (userContext.getUser() != null) {

            // check to see if modelAndView is not null
            if (modelAndView != null) {
                authorizeFromItem(request, modelAndView, userContext);
            }
        }
    }


    /**
     * 
     * Authorize the fields given a Managed Item
     *
     * @param request HttpServletRequest
     * @param modelAndView ModelAndView
     * @param userContext UserContext
     */
    private void authorizeFromItem(HttpServletRequest request, ModelAndView modelAndView, UserContext userContext) {
        ManagedItemVo item = (ManagedItemVo) modelAndView.getModelMap().get(ControllerConstants.ITEM_KEY);

        if (item != null) {
            FieldAuthorizationVo fieldAuthorization = userService.authorizeFields(userContext.getUser(), item);
            modelAndView.getModel().put("fieldAuthorization", fieldAuthorization);
        }

    }

    /**
     * Find the fields on the given action class that are annotated by
     * {@link Authorize}.
     * 
     * @param clazz Class of the current action
     * @return List<Field> of {@link Authorize} annotated fields
     */
    public static List<Field> getAuthorizeFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();
        addAllAnnotatedFields(Authorize.class, clazz, fields);

        return fields;
    }

    /**
     *  setUserService
     * @param userService userService property
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
