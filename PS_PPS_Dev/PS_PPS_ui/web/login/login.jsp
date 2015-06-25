<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" session="false"%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page
    import="gov.va.med.authentication.kernel.ConfigurationVO,gov.va.med.authentication.kernel.LoginController,weblogic.servlet.security.ServletAuthentication"%>

<%
    // Turn off cache so that a user cannot navigate back to the login page after post-login
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    //Logger LOG = Logger.getLogger("VistALinkLogin.jsp");
    //LOG.debug("Loading login.jsp");
    
    // this should force a logout or force-clear the session object to ensure it doesn't have a bad targetURL 
    // from ServletAuthentication.getTargetURLForFormAuthentication
    //ServletAuthentication.logout(request);
    
%>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<meta http-equiv="PRAGMA" content="NO-CACHE" />
<meta http-equiv="EXPIRES" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />

<title>Login Page</title>
<!--
 * 
 * @author VHIT Security and Other Common Services (S&OCS)
 * @version 1.1.0.007
 * -->

<style type="text/css">
.scrollPane {
	height: 18em;
	overflow: auto;
}
.inputLabel {
    font-weight: bold;
}
.inputLabelSort {
    font-weight: normal;
}
</style>

<script type="text/javascript"
    src="<%=request.getContextPath()%>/login/javascript/login.js"></script>
</head>
<body
    onload="javascript:doTimer(document.LoginForm,document.LoginForm.kjMaxIntObjHF);checkHF(document.LoginForm,document.LoginForm.institution,document.LoginForm.sortInstBy,document.LoginForm.disableInstitutionObjHF,document.LoginForm.disableSortByObjHF,document.LoginForm.sortByObjHF);document.LoginForm.access.focus();">
    <a href="#skip" title="Skip to login fields"></a>

    <%
        /*
        It could be argued that using code within JSP is not a good thing, but for such a small app it's 
        completely acceptable.  If we were working with an HTML designer, the separation would be good. But then
        JSTL would be needed and with that we'd introduce some version of that as a dependency, which is preferably
        avoided given because we're embedded, we force these dependencies on the containing application as well.
        */
        ConfigurationVO kaajeeEnv = ConfigurationVO.getInstance();

        /*
        If this is a relogin attempt, check for session timeout.
        */
        if (request.getParameter("relogin") != null) {
            HttpSession hSess = request.getSession(false);
            if (hSess == null) {
                StringBuffer sbsession = new StringBuffer(request.getContextPath());
                sbsession.append("/login/SessionTimeout.jsp");
                response.sendRedirect(sbsession.toString());
                return;
            }
        }

        HttpSession hSess = request.getSession(true);

        int kjMaxInt = hSess.getMaxInactiveInterval();
    %>

    <form name="LoginForm" method="post"
        action="<%=request.getContextPath()%>/LoginController"
        onsubmit="javascript:enableInstitutionObj(document.LoginForm,document.LoginForm.institution,document.LoginForm.sortInstBy,document.LoginForm.disableInstitutionObjHF,document.LoginForm.disableSortByObjHF)">
        <div style="width: 75%; margin-left: auto; margin-right: auto;">
            <%
                String text = kaajeeEnv.getIntroductoryText();
                String fixed = text.replaceAll("<br>", "<br />");
                text = fixed;
            %>
            <div style="clear: both; text-align: center;">
                <div style="text-align: left;"><strong>System Announcements:</strong></div>
                <div style="border: 0px; text-align: left; padding: 4px; margin: 4px; background-color: #eeeeee;">
                    <div class="scrollPane"><%=text%></div>
                </div>
            </div>

            <div>
                <div style="white-space: nowrap; text-align: center;"><h3>Login: <%= kaajeeEnv.getHostApplicationName() %></h3></div>
    
                <div style="clear: both; text-align: center;">
                    <div>
                        <div style="width: 400px; margin-left: auto; margin-right: auto;">
                            <img src="<%=request.getContextPath()%>/login/images/HealtheVetVistaSmallBlue.jpg"
                                 alt="Health e Vet Vista logo" style="width: 120px; height: 60px;" />
                            <div style="float: right;">
                                <div style="white-space: nowrap; text-align: right;">
                                    <a name="skip"></a>
                                    <label for="access" class="inputLabel">Access Code:</label>
                                    <input type="password" id="access" name="access" value="" size="16" maxlength="20" />
                                </div>
                                <div style="white-space: nowrap; text-align: right; margin-top: 5px;">
                                    <label for="verify" class="inputLabel">Verify Code:</label>
                                    <input type="password" id="verify" name="verify" value="" size="16" maxlength="20" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        //1. Initialize values
                        String kaajeeDefaultInstitutionValue = "";
                        String kaajeeDisableInstitutionComponentsValue = "";
                        String kaajeeSortStationByValue = "";
                        String kaajeeDisableSortStationByValue = "";
                        boolean kaajeeDefaultInstitutionParamFound = false;
                        boolean kaajeeDisableInstitutionParamFound = false;
                        boolean kaajeeSortStationByParamFound = false;
                        boolean kaajeeDisableSortStationByParamFound = false;
                        
                        //2. Get targetURL and parse out parameters
                        
                        String targetURL = ServletAuthentication.getTargetURLForFormAuthentication(request.getSession());
                        
                        if (targetURL == null) {
                            StringBuffer sbNavErrorPg = new StringBuffer(request.getContextPath());
                            sbNavErrorPg.append("/login/navigationerror.jsp");
                            response.sendRedirect(sbNavErrorPg.toString());
                            return;
                        }
                        
                        int paramPos = targetURL.indexOf('?');
                        String paramList = (paramPos > -1) ? targetURL.substring(paramPos + 1, targetURL.length()) : "";
                        String[] paramArray = paramList.split("\u0026"); //split on '&' char
                        for (int i = 0; i < paramArray.length; i++) {
                            String[] tempArray = paramArray[i].split("\u003D"); //split on '=' char
                            if (tempArray[0].indexOf("kaajeeDefaultInstitution") != -1) {
                                kaajeeDefaultInstitutionValue = tempArray[1];
                                kaajeeDefaultInstitutionParamFound = true;
                            } else {
                                if (tempArray[0].indexOf("kaajeeDisableInstitutionComponents") != -1) {
                                    kaajeeDisableInstitutionComponentsValue = tempArray[1];
                                    kaajeeDisableInstitutionParamFound = true;
                                } else {
                                    if (tempArray[0].indexOf("kaajeeSortStationBy") != -1) {
                                        kaajeeSortStationByValue = tempArray[1];
                                        kaajeeSortStationByParamFound = true;
                                    } else {
                                        if (tempArray[0].indexOf("kaajeeDisableSortStationBy") != -1) {
                                            kaajeeDisableSortStationByValue = tempArray[1];
                                            kaajeeDisableSortStationByParamFound = true;
                                        }
                                    }
                                }
                            }
                            if ((kaajeeDefaultInstitutionParamFound) && (kaajeeDisableInstitutionParamFound)
                                && (kaajeeSortStationByParamFound) && (kaajeeDisableSortStationByParamFound)) {
                                                break;
                            }
                        }
                                        
                        //3. Get default login institution and sorting preference if any from cookies
                        boolean sortCookieFound = false;
                        boolean instCookieFound = false;
                        String defaultSortInstBy = "";
                        String defaultInst = "";
                        String cookieDefaultSortInstitutionByString = LoginController.COOKIE_DEFAULT_SORT_INSTITUTION_BY_STRING;
                        String cookieDefaultDivisionString = LoginController.COOKIE_DEFAULT_DIVISION_STRING;
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            for (int i = 0; i < cookies.length; i++) {
                                if (cookies[i].getName().equals(cookieDefaultSortInstitutionByString)) {
                                    defaultSortInstBy = cookies[i].getValue();
                                    // store in session for LoginController, post-submit
                                    hSess.setAttribute(LoginController.SESSION_KEY_COOKIE_DEFAULT_SORT_INSTITUTION_BY, defaultSortInstBy);
                                    sortCookieFound = true;
                                } else {
                                    if (cookies[i].getName().equals(cookieDefaultDivisionString)) {
                                        defaultInst = cookies[i].getValue();
                                        // store in session for LoginController, post-submit
                                        hSess.setAttribute(LoginController.SESSION_KEY_COOKIE_DEFAULT_DIVISION, defaultInst);
                                        instCookieFound = true;
                                    }
                                }
                                
                                if ((sortCookieFound) && (instCookieFound)) {
                                    break;
                                }
                            }
                        }
        
                        String radioButton1CheckValue = 
                            (
                                (kaajeeSortStationByValue.trim().length() > 0) 
                                    ? (kaajeeSortStationByValue.equals("number") 
                                            ? "checked=\"checked\"" 
                                            : "")
                                    : ( (defaultSortInstBy.equals("") || defaultSortInstBy.equals("number")) 
                                            ? "checked=\"checked\""
                                            : ""
                                      )
                            );
                        String radioButton2CheckValue =
                            (
                                (kaajeeSortStationByValue.trim().length() > 0) 
                                    ? (kaajeeSortStationByValue.equals("name") 
                                            ? "checked=\"checked\""
                                            : "")
                                    : (defaultSortInstBy.equals("name") 
                                            ? "checked=\"checked\""
                                            : ""
                                      )
                            );
                    %>
                    <div style="clear: both; margin-top: 10px;">
                        <div>
                            <input id="sortInstByNumber" type="radio" <%=radioButton1CheckValue%>
                                name="sortInstBy" value="number"
                                onclick="reSortSelectOptions(this.form,this.form.institution,'value')" />
                            <label for="sortInstByNumber" class="inputLabelSort">Sort by Station Number <sup>*</sup></label>
                            <input id="sortInstByName" type="radio" <%=radioButton2CheckValue%>
                                name="sortInstBy" value="name"
                                onclick="reSortSelectOptions(this.form,this.form.institution,'text')" />
                            <label for="sortInstByName" class="inputLabelSort">Sort by Station Name <sup>*</sup></label>
                            
                            <input type="hidden" name="disableInstitutionObjHF"
                                   value="<%=kaajeeDisableInstitutionComponentsValue%>" />
                            <input type="hidden" name="disableSortByObjHF"
                                   value="<%=kaajeeDisableSortStationByValue%>" /> 
                            <input type="hidden" name="sortByObjHF"
                                   value="<%=kaajeeSortStationByValue%>" />
                            <input type="hidden" name="kjMaxIntObjHF"
                                   value="<%=kjMaxInt%>" />
                        </div>
                    </div>
        
                    <div style="text-align: center; clear: both;">
                        <div style="white-space: nowrap; margin-top: 10px;">
                            <label for="institution" class="inputLabel">Institution:</label> 
                            <%
                                //4. Initialize and Create Option List
                                String imOptionList = "";
                                if (defaultSortInstBy.equals("name")) {
                                    imOptionList = kaajeeEnv.getJspDropDownListLoginOptionsByName();
                                } else {
                                    imOptionList = kaajeeEnv.getJspDropDownListLoginOptions();
                                }
        
                                //5. Set the selected institution in the list based on the cookie value
                                if ((defaultInst != null) 
                                    || ((kaajeeDefaultInstitutionValue != null) 
                                        && (kaajeeDefaultInstitutionValue.trim().length() > 0))) {
                                    // int indexDefaultInst = imOptionList.indexOf("value="+defaultInst+">");
                                    StringBuffer sbInst = new StringBuffer();
                                    sbInst.append(((kaajeeDefaultInstitutionValue != null) && (kaajeeDefaultInstitutionValue.trim()
                                                            .length() > 0)) ? kaajeeDefaultInstitutionValue : defaultInst);
                                    int indexDefaultInst = imOptionList.indexOf("value=" + sbInst.toString() + ">");
                                    
                                    if (indexDefaultInst > -1) {
                                        StringBuffer sb = new StringBuffer();
                                        sb.append(imOptionList.substring(0, indexDefaultInst - 1));
                                        sb.append(" selected=\"selected\" ");
                                        sb.append(imOptionList.substring(indexDefaultInst, imOptionList.length() - 1));
                                        imOptionList = sb.toString();
                                    }
                                }
        
                                imOptionList = imOptionList.replaceAll("<(\\/?)OPTION", "<$1option");
                                imOptionList = imOptionList.replaceAll(" value=(\\d+)>", " value=\"$1\">");
                               
                            %>
                            <select id="institution" name="institution" size="1"><%=imOptionList%></select> <sup>*</sup>
                        </div>
                    </div>
                </div>
                
                <!-- <td><input type="checkbox" name="changeverify" value="yes" /><strong>Change Verify Code (Not yet implemented)</strong></td> -->
    
                <div style="text-align: center; margin-top: 20px;">
                    <input id="loginSubmit" name="loginSubmit" type="submit" value="Login" />
                </div>
                <div style="text-align: center; margin-top: 30px;">
                    <div style="white-space: nowrap">
                        * Persistent Cookie Used
                        (<a href="<%=request.getContextPath()%>/login/loginCookieInfo.htm"
                            onclick='window.open("<%=request.getContextPath()%>/login/loginCookieInfo.htm","new_win","status=no,scrollbars=yes,toolbar=no,location=no,width=400,height=450,resizable"); return false;'>more
                            information</a>).
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
