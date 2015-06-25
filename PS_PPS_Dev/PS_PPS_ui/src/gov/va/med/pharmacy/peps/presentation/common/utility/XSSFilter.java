/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * XSSFilter's brief summary
 * 
 * Details of XSSFilter's operations, special dependencies
 * or protocols developers shall know about when using the class.
 * 
 * borrowed from http://ricardozuasti.com/2012/stronger-anti-cross-site-scripting-xss-filter-for-java-web-apps/
 */
public class XSSFilter implements Filter {

    /**
     * empty destroy method
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * filter method
     * 
     * @param request request
     * @param response response
     * @param chain filter chain
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, 
     * javax.servlet.FilterChain)
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }

    /**
     * empty init method
     * 
     * @param config param
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * @throws ServletException exception
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
