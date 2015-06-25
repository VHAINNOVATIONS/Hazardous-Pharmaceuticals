<%@ page 
	contentType="text/html;charset=UTF-8" 
	language="java" %>    
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
	
<div class="panel"><peps:form id="homepage">
    <table
        bgcolor="#F8F3E2"
        width="1250"
        border="0"
        cellspacing="10"
        cellpadding="10">
        <tr valign="top">
            <!--LOCAL OFFICE MESSAGE-->
            <td
                height="300"
                width="500"
                valign="top"><img
                class="news-img"
                src="images/GoldenChalice.jpg"
                alt="Apothecary Chalice" />
            <!-- <div class="office-title"><s:text name="home.localoffice.label" /></div > -->                
            <div class="office-title"><spring:message code="home.localoffice.label" /></div>
            <p><strong>Some Drugs Increase Risk Of Falling</strong> <br />
            Researchers at the University of North Carolina at Chapel Hill have created a list of prescription drugs that
            increase the risk of falling for patients aged 65 and older who take four or more medications on a regular basis.

            "Falls are the leading cause of both fatal and non-fatal injuries for adults 65 and older, and research suggests
            that those taking four or more medications are at an even greater risk than those who don't perhaps two to three
            times greater," said <a
                href="#"
                title="More">Full Story...</a></p>
            </td>
            <!--PBM OFFICE MESSAGE-->
            <td
                valign="top"
                width="500"><img
                class="news-img"
                src="images/VeteransPoster.jpg"
                alt="Honoring Those Who Served"
                width="180" />
			<!--<div class="office-title"><s:text name="home.pbmoffice.label" /></div>    -->             
            <div class="office-title"><spring:message code="home.pbmoffice.label" /></div>  
            <p><strong>Don't Return Controlled Substance</strong> <br />
            No provisions in Controlled Substances Act or Code of Federal Regulations for pharmacy take back of controlled
            substances from patients. Pharmacists should be aware that no provisions exist in the Controlled Substances Act
            or Code of Federal Regulations (CFR) for a DEA registrant, such as a community pharmacy, to take back controlled
            substances from a non-registrant (i.e., individual patient). However, patients may return an unused controlled
            substance medication to the pharmacy in the event that the controlled substance is recalled or a dispensing error
            has occurred, according to <a
                href="#"
                title="More">Full Story...</a><br />
            </p>
            </td>
            <!--SEARCH-->
            <td><!-- <input type="hidden" name="_flowExecutionId" value="${flowExecutionKey}" />--> 
                <a href="manageItem"
                   title="Perform Simple Search">
                   Perform Simple Search</a></td>
        </tr>
    </table>
    <!--HORIZONTAL DIVIDING LINE-->
    <table
        width="100%"
        border="0"
        cellspacing="0"
        cellpadding="0">
        <tr bgcolor="#000066">
            <td>&nbsp;</td>
        </tr>
    </table>
    <table
        bgcolor="#F6E5CA"
        width="1250"
        border="1">
        <tr valign="top">
            <!--    NOTIFICATIONS     -->
            
<%--  N/A PREVIOUS STRUTS LOGIC FOR LOCAL

             <s:if test="%{environment.local}">
             <td
                width="50%"
                height="250">
            <table
                width="94%"
                border="0">
                <tr valign="top">
                    <td ><a
                        href="notifications"
                        title="Notifications"
                        onclick="setTabIndex('Notifications');">Notifications:</a> 
                        <s:if test="%{notificationCount == 1}">One notification found.</s:if>
                        <s:elseif test="%{notificationCount > 1 && notificationCount < 6}">
                            <s:property value="notificationCount" /> notifications found.
                        </s:elseif>
                        <s:elseif test="%{notificationCount > 5}">
                            <s:property value="notificationCount" /> notifications found, 
                            only displaying the most recent five below.
                        </s:elseif>
                        <s:else>
                            No notifications found.
                        </s:else>
                    </td>
                
                <tr>
                    <td >
                         <s:if test="%{notifications != null}">
                            <peps:table
                                tableId="homeNotificationsTable"
                                dataOgnl="notifications"
                                fieldsOgnl="notificationsPrintTemplate"
                                multiselectButtonKey="button.delete"
                                multiselectButtonEvent="delete"
                                paged="false" />
                             </s:if>   
                            
                            <s:if test="%{notifications == null}">
                                Testing only:   NOTIFICATIONS IS NULL  IN home.jsp !!! 
                            </s:if>

                    </td>
                </tr>
            </table>
            </td>
            </s:if>
 --%>
            <!--    PARTIAL SAVES     -->
            <td>
            <table
                width="94%"
                border="0">
                <tr valign="top">
                    <td ><a
                        href="managePartialItem"
                        title="Saved Work in Progress">Saved Work in Progress:</a>
			<c:choose>
                        <c:when test="${partialItemsCount == 1}"> One saved item found.</c:when>
                        <c:when test="${partialItemsCount > 1 && partialItemsCount <= 5}">
                            <!-- <s:property value="partialItemsCount" /> saved items found. -->                         
                            <spring:message code="partialItemsCount" /> saved items found. 
                        </c:when>
                        <c:when test="${partialItemsCount > 5}">
                            <!-- <s:property value="partialItemsCount" /> saved items found,    -->                      
                            <spring:message code="partialItemsCount" /> saved items found, 
                            only displaying the most recent five below.
                        </c:when>  
                        <c:otherwise>
                            No saved items found.
                        </c:otherwise>   
             </c:choose>                             
                    </td>
                </tr>
          
                <tr>
                
                    <td >
                    
<%--                     <peps:table --%>
<!--                         tableId="partialSaveTable" -->
<!--                         dataOgnl="partialItems" -->
<!--                         fieldsOgnl="partialSavesPrintTemplate" -->
<!--                         multiselectButtonKey="button.delete" -->
<!--                         multiselectButtonEvent="delete" -->
<!--                         paged="false"  -->
<!--                      /> -->
                        
                        </td>
                        
                </tr>
            </table>
            </td>
        </tr>
    </table>
    <table
        border="0"
        cellpadding="0"
        cellspacing="0"
        width="100%"
        summary="table is used for layout purposes">
        <tbody>
            <tr>
                <td id="nav-footer"><br />
                <a
                    href="http://www.vba.va.gov/VBA/espanol/factsheets/" target="_blank" 
                    title="Links to information in Spanish concerning Veterans Affairs benefits"> Espa&ntilde;ol</a> | <a
                    href="http://www.va.gov/vaforms/" target="_blank"  
                    title="Forms used by Veterans Affairs">VA Forms</a> | <a
                    href="http://www.va.gov/directory/" target="_blank" 
                    title="Find a VA Facility">Facilities Locator</a> | <a
                    href="https://iris.va.gov/" target="_blank" 
                    title="Contact Veterans Affairs">Contact the VA</a> | <a
                    href="https://iris.va.gov/scripts/iris.cfg/php.exe/enduser/std_alp.php" target="_blank" 
                    class="verynarrowtop parenttop"
                    title="Frequently asked questions of Veterans Affairs">Frequently Asked Questions (FAQs)</a> | <a
                    href="http://www.va.gov/privacy/" target="_blank" 
                    title="The Privacy Policy for Veterans Affairs">Privacy Policy</a> | <a
                    href="http://www.va.gov/webpolicylinks.asp" target="_blank" 
                    title="Information on Veterans Affairs web policies">Web Policies &amp; Important Links</a> <br />
                <a
                    href="http://www.va.gov/budget/report/" target="_blank" 
                    title="Link to the annual performance and accountability report">Annual Performance and
                Accountability Report</a> | <a
                    href="http://www.va.gov/foia/" target="_blank" 
                    title="Information on making a Freedom of Information Act request">Freedom of Information Act</a> | <a
                    href="http://www.va.gov/osdbu/contacts/contacts.htm" target="_blank" 
                    title="Information on small business contacts with Veterans Affairs">Small Business Contacts</a> | <a
                    href="http://www.va.gov/orm/NOFEAR_Select.asp" target="_blank" 
                    title="No Fear Act data">No FEAR Act Data</a><br />
                <a
                    href="http://www.va.gov/orpm/" target="_blank" 
                    title="Regulations and Guidance documents">Regulations &amp; Guidance Documents</a> | <a
                    href="http://www.va.gov/oit/CIO/Directives/default.asp" target="_blank" 
                    title="Directives">Directives</a> | <a
                    href="http://www.va.gov/oig/default.asp" target="_blank" 
                    title="Inspector General">Inspector General</a> | <a
                    href="http://www.usa.gov/" target="_blank" 
                    title="The U.S. government&#39;s official web portal.">USA.gov</a> | <a
                    href="http://www.whitehouse.gov/" target="_blank" 
                    title="Link to the White House web site">White House</a> | <a
                    href="http://www.freedomcorps.gov/" target="_blank" 
                    title="Link to the USA Freedom Corps">USA Freedom Corps</a> | <a
                    href="http://www.expectmore.gov/" target="_blank" 
                    title="ExpectMore Web site">ExpectMore</a> | <a
                    href="http://www.govbenefits.gov" target="_blank" 
                    title="GovBenefits Web site">GovBenefits</a></td>
            </tr>
            <tr>
                <td
                    class="style1"
                    id="nav-review"><br />
                Reviewed/Updated Date: 6 April 2009</td>
            </tr>
        </tbody>
    </table>
</peps:form></div>
<!--end <div class="body-content"> -->
