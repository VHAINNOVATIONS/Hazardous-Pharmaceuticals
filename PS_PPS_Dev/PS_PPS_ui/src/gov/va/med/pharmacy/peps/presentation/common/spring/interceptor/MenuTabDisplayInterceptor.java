/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.interceptor;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;


/**
 * MenuTabDisplayInterceptor
 * 
 * Stores the user's menu selection from the AJAX call to a session variable used by the navigation.jsp page.
 *
 */
public class MenuTabDisplayInterceptor extends PepsSpringInterceptor {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MenuTabDisplayInterceptor.class);
    private static String MENU_ITEM_SELECTED = "menuItemSelected";
    private static Pattern PAT = Pattern.compile("[^a-zA-Z0-9_\\-\\s]+");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String val = request.getParameter(MENU_ITEM_SELECTED);

        if (StringUtils.isNotEmpty(val)) {
            Matcher m = PAT.matcher(val);

            if (m.matches()) {
                LOG.warn("XSS attack attempt: " + val);
            } else {
                session.setAttribute(MENU_ITEM_SELECTED, val);
            }
        }

        return true;
    }

}
