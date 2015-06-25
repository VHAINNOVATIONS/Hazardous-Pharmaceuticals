/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * ContentCacheInterceptor's brief summary
 * 
 * Details of ContentCacheInterceptor's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ContentCacheInterceptor extends HandlerInterceptorAdapter {

    /**
     * postHandle
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @param modelAndView ModelAndView
     * 
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, 
     *      java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
        
        String noCache = "no-cache";
        response.setHeader("Cache-Control", noCache);
        response.setHeader("Pragma", noCache);
        response.setDateHeader("Expires", 0);
    }

}
