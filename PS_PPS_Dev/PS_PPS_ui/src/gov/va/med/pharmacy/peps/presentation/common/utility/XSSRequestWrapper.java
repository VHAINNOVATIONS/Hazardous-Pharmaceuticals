/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.utility;


import java.text.Normalizer;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * XSSRequestWrapper's brief summary
 * 
 * Details of XSSRequestWrapper's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 * borrowed from http://ricardozuasti.com/2012/stronger-anti-cross-site-scripting-xss-filter-for-java-web-apps/
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(XSSRequestWrapper.class);

    private static Pattern[] patterns = new Pattern[] {

        // Script fragments
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),

        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

        // lonely script tags
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

        // eval(...)
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

        // expression(...)
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

        // javascript:...
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),

        // vbscript:...
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),

        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    /**
     * constructor
     *
     * @param servletRequest request
     */
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        LOG.debug("checking request of parameter values '" + parameter + "'.");
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];

        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(parameter, values[i]);
        }

        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        LOG.debug("checking request of parameter '" + parameter + "'.");
        String value = super.getParameter(parameter);

        return stripXSS(parameter, value);
    }

    @Override
    public String getHeader(String name) {
        LOG.debug("checking request of header '" + name + "'.");
        String value = super.getHeader(name);

        return stripXSS(name, value);
    }

    /**
     * override the getCookies method
     * @see javax.servlet.http.HttpServletRequestWrapper#getCookies()
     * 
     * @return Cookies
     */
    @Override
    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();

        for (Cookie c : cookies) {
            String name = c.getName();
            String value = c.getValue();

            stripXSS(name, value);
        }
        
        return cookies;
    }

    /**
     * stripXSS
     * @param key string key string of parameter
     * @param value string value string of parameter
     * @return string
     */
    public String stripXSS(String key, String value) {
        String rv = value;

        if (rv != null) {

            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            LOG.debug("stripping value(s) if needed: <<" + value + ">>");

            // Avoid null characters
            rv = rv.replaceAll("\0", "");

            // Remove all sections that match a pattern
            for (Pattern scriptPattern : patterns) {
                String test = this.canonicalize(rv);

                if (scriptPattern.matcher(test).find()) {
                    LOG.error("XSS attack attempt found on key '" + key + "': " + rv);
                    LOG.error("Dumping user session as a precaution.");

                    // As a precaution, since we detected Cross Site Scripting (XSS), we dump the user's session.
                    this.getSession().invalidate();
                    rv = "";
                }

//                rv = scriptPattern.matcher(rv).replaceAll("");
            }
        }

        return rv;
    }

    /**
     * canonicalize
     * Simplifies input to its simplest form to make encoding tricks more difficult
     * 
     * @param input string
     * @return string
     */
    private String canonicalize(String input) {

        //String canonical = sun.text.Normalizer.normalize( input, Normalizer.DECOMP, 0 );
        String canonical = Normalizer.normalize(input, Normalizer.Form.NFD);
        canonical = StringEscapeUtils.unescapeHtml(input);
        canonical = StringEscapeUtils.unescapeJavaScript(input);

        return canonical;
    }
}
