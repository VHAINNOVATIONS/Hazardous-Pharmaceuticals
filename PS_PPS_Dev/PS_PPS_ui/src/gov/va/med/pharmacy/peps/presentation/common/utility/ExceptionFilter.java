/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.servlet.context.ServletUtil;
import org.springframework.web.util.NestedServletException;

import gov.va.med.pharmacy.peps.common.exception.PresentationSecurityException;
import gov.va.med.pharmacy.peps.common.utility.ConfigFileUtility;




/**
 * Catch all exceptions that don't get caught by Struts or Spring Web Flow and display a nicer page to the user.
 */
public class ExceptionFilter implements Filter {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ExceptionFilter.class);
    private static final String EXCEPTION_TILES_DEFINITION = "exception";
    private static final String EXCEPTION_REQUEST_ATTRIBUTE = "exception";
    private static final String RUNNING_AT_SWRI = "isSwRI";

    private FilterConfig filterConfig;

    /**
     * This filter doesn't store any state, so there is nothing to destroy.
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * Catch all exceptions thrown by {@link FilterChain#doFilter(ServletRequest, ServletResponse)}. If an exception is
     * caught, display a user friendly page.
     * 
     * @param request ServletRequest
     * @param response ServletResponse
     * @param filterChain FilterChain
     * @throws IOException if error
     * @throws ServletException if error
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
        ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(e, EXCEPTION_TILES_DEFINITION, (HttpServletRequest) request, (HttpServletResponse) response);
        }
    }

    /**
     * If Struts it not yet initialized, create a new instance of the Struts Dispatcher so that Struts tags will work in the
     * rendered Tiles definition. Then set a request attribute named {@value #EXCEPTION_REQUEST_ATTRIBUTE} with the value of
     * the given Exception for use in the rendered Tiles definition. Finally, render the given Tiles definition name.
     * 
     * @param e caught Exception
     * @param tilesDefinition Apache Tiles definition to render
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if error
     */
    private void handleException(Exception e, String tilesDefinition, HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException {

        ConfigFileUtility configFileUtility = new ConfigFileUtility();

        Throwable cause;

        if (e.getCause() instanceof NestedServletException) {
            cause = e.getCause().getCause();
        } else {
            cause = e.getCause();
        }

        if (cause instanceof SecurityException
            || (cause instanceof UndeclaredThrowableException && ((UndeclaredThrowableException) cause)
                .getUndeclaredThrowable() instanceof PresentationSecurityException)) {
            renderTilesDefinition("exception.security", request, response);
        } else {
            if (configFileUtility.isSwri()) {
                request.setAttribute(EXCEPTION_REQUEST_ATTRIBUTE, e);
                request.setAttribute(RUNNING_AT_SWRI, true);
            } else {
                request.setAttribute(RUNNING_AT_SWRI, false);
            }

            renderTilesDefinition(tilesDefinition, request, response);
            LOG.error("Exception caught while executing request", e);
        }



    }

    /**
     * Render the given Apache Tiles definition.
     * 
     * @param tilesDefinition String definition name
     * @param request current HttpServletRequest
     * @param response current HttpServletResponse
     * @throws ServletException if error rendering the given Tiles definition
     */
    private void renderTilesDefinition(String tilesDefinition, HttpServletRequest request, HttpServletResponse response)
        throws ServletException {

        try {
            TilesContainer container = ServletUtil.getContainer(getServletContext());
            container.render(tilesDefinition, new Object[] { request, response });
        } catch (TilesException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Set the FilterConfig
     * 
     * @param config FilterConfig
     * @throws ServletException if error
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }

    /**
     * Return the ServletContext found in the FilterConfig at {@link FilterConfig#getServletContext()}
     * 
     * @return ServletContext
     */
    private ServletContext getServletContext() {
        return filterConfig.getServletContext();
    }
}
