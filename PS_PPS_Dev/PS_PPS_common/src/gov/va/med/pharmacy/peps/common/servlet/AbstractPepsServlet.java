/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gov.va.med.pharmacy.peps.common.utility.ClassUtility;


/**
 * Receive a request from VistA, retrieve the XML request parameter, and forward it on to a subclass. Then write the String
 * return value to the response.
 * 
 * @param <T> Class type of Service this Servlet uses for requests
 */
public abstract class AbstractPepsServlet<T> extends HttpServlet {
    private static final long serialVersionUID = 1l;
    private static final String TEXT_XML_CONTENT_TYPE = "text/xml";
    private static final String XML_REQUEST_PARAMETER = "xmlRequest";

    private T service;

    /**
     * Delegate DELETE requests to {@link #doService(ServletRequest, ServletResponse)}.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if error
     * @throws IOException if error
     * 
     * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doService(request, response);
    }

    /**
     * Delegate GET requests to {@link #doService(ServletRequest, ServletResponse)}.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if error
     * @throws IOException if error
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doService(request, response);
    }

    /**
     * Delegate POST requests to {@link #doService(ServletRequest, ServletResponse)}.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if error
     * @throws IOException if error
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doService(request, response);
    }

    /**
     * Delegate PUT requests to {@link #doService(ServletRequest, ServletResponse)}.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if error
     * @throws IOException if error
     * 
     * @see javax.servlet.http.HttpServlet#doPut(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        doService(request, response);
    }

    /**
     * Get the XML request parameter from the HttpServletRequest and forward it to the subclass via the
     * {@link #getResponse(String)} method.
     * 
     * Then write the String return value to the response.
     * 
     * @param request HttpServletRequest from VistA
     * @param response HttpServletResponse with XML response for VistA
     * @throws IOException If error processing request or writing response
     * 
     * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    private void doService(ServletRequest request, ServletResponse response) throws IOException {
        response.setContentType(TEXT_XML_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println(getResponse(request.getParameter(XML_REQUEST_PARAMETER)));
        out.flush();
        out.close();
    }

    /**
     * Handle the XML Request from VistA.
     * 
     * @param xmlRequest String request from VistA
     * @return String response for VistA
     */
    protected abstract String getResponse(String xmlRequest);

    /**
     * Get the service for this class as a Spring bean. The Spring bean must be named after the class name.
     * 
     * I.e., gov.va.med.pharmacy.peps.service.common.session.bean.DomainServiceBean must have a Spring bean defined as
     * "domainService".
     * 
     * @return Object service implementation class from Spring. If no Spring bean was found, return null.
     */
    protected final T getService() {
        return service;
    }

    /**
     * Set the ServletConfig and find the service in the Spring ApplicationContext. The Spring bean for the service must be
     * named after the class name.
     * 
     * I.e., gov.va.med.pharmacy.peps.service.common.session.bean.DomainServiceBean must have a Spring bean defined as
     * "domainService".
     * 
     * This method will be invoked after any bean properties have been set and the WebApplicationContext has been loaded. The
     * default implementation is empty; subclasses may override this method to perform any initialization they require.
     * 
     * @throws ServletException if error
     * 
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public final void init() throws ServletException {
        super.init();

        Class generic = ClassUtility.getGenericType(getClass());
        String className = ClassUtility.getSpringBeanId(generic);

        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        if (applicationContext.containsBean(className)) {
            this.service = (T) applicationContext.getBean(className);
        }
    }
}
