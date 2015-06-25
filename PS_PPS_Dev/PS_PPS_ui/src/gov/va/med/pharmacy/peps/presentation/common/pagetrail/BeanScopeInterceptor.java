/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * BeanScopeInterceptor's brief summary
 * 
 * Details of BeanScopeInterceptor's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class BeanScopeInterceptor implements HandlerInterceptor {

    private static final String FLASH_SCOPE_KEY = FlashScope.class.getName();

    /** flashScope */
    @Autowired
    private FlashScope flashScope;

    /** flowScope */
    @Autowired
    private FlowScope flowScope;

    /** flowInputScope */
    @Autowired
    private FlowInputScope flowInputScope;

    /**
     * get the flashScope
     * 
     * @return the flashScope
     */
    public FlashScope getFlashScope() {
        return flashScope;
    }

    /**
     * sets flashScope field
     * 
     * @param flashScope the flashScope to set
     */
    public void setFlashScope(FlashScope flashScope) {
        this.flashScope = flashScope;
    }

    /**
     * get the flowScope
     * 
     * @return the flowScope
     */
    public FlowScope getFlowScope() {
        return flowScope;
    }

    /**
     * sets flowScope field
     * 
     * @param flowScope the flowScope to set
     */
    public void setFlowScope(FlowScope flowScope) {
        this.flowScope = flowScope;
    }

    /**
     * get the flowInputScope
     * 
     * @return the flowInputScope
     */
    public FlowInputScope getFlowInputScope() {
        return flowInputScope;
    }

    /**
     * sets flowInputScope field
     * 
     * @param flowInputScope the flowInputScope to set
     */
    public void setFlowInputScope(FlowInputScope flowInputScope) {
        this.flowInputScope = flowInputScope;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // put the flash scope backing map from the session into the request and clear it from the session
        HttpSession session = request.getSession(false);

        if (session != null) {
            Map<String, Object> flashScopeBackingMap = (Map<String, Object>) session.getAttribute(FLASH_SCOPE_KEY);

            if (flashScopeBackingMap != null) {

                for (String key : flashScopeBackingMap.keySet()) {
                    request.setAttribute(key, flashScopeBackingMap.get(key));
                }
            }

            session.removeAttribute(FLASH_SCOPE_KEY);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

        // remove the stagnant flash scope backing map from the session
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(FLASH_SCOPE_KEY);

            // put the flash scope backing map from the request into the session so it will survive a redirect
            Map<String, Object> flashScopeBackingMap = (Map<String, Object>) request.getAttribute(FLASH_SCOPE_KEY);

            session.setAttribute(FLASH_SCOPE_KEY, flashScopeBackingMap);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView)
        throws Exception {

        request.setAttribute("flashScope", flashScope);
        request.setAttribute("flowScope", flowScope);
    }

}
