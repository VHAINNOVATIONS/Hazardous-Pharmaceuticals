<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib
    prefix="spring"
    uri="http://www.springframework.org/tags"%>

<c:if test="${sessionScope.PPSNUserContext != null && sessionScope.PPSNUserContext.user != null}">
	<c:set value="${sessionScope.PPSNUserContext.user}" var="user"/>
</c:if>
<ul class="menuList">	    	
 	<li>
 		<spring:message code="nav.home" var="home"/>
   		<a tabIndex="1" title="${home}" href="<c:url value="/home.go"/>" onclick="setTabIndex('${home}')">${home}</a>
  	</li>  	
  	<c:if test="${user != null}">
  	<li>
  		<spring:message code="nav.itemManagement" var="itemManagement"/>
   		<a tabIndex="1" title="${itemManagement}" href="<c:url value="/searchItems.go"/>" onclick="setTabIndex('${itemManagement}')">${itemManagement}</a>
   		<ul class="submenu">
    		<li>
    			<spring:message code="nav.search" var="search"/>
     			<a tabIndex="1" title="${search}" href="<c:url value="/searchItems.go"/>" onclick="setTabIndex('${search}')">${search}</a>
    		</li>
		    <li>
		    	<spring:message code="nav.reviewRequests" var="requests"/>
		     	<a tabIndex="1" title="${requests}" href="<c:url value="/searchRequests.go"/>" onclick="setTabIndex('${requests}')">${requests}</a>
		    </li>	
		    <li>
		    	<spring:message code="nav.partialSaveItems" var="partialSave"/>
		     	<a tabIndex="1" title="${partialSave}" href="<c:url value="/managePartialItem.go"/>" onclick="setTabIndex('${partialSave}')">${partialSave}</a>
		    </li>
		    <li>
		    	<spring:message code="nav.manageDomains" var="domains"/>
		    	<a tabIndex="1" title="${domains}" href="<c:url value="/searchDataElements.go"/>" onclick="setTabIndex('${domains}')">${domains}</a>
		    </li>
		    <li>
		    	<spring:message code="nav.reviewDomainRequests" var="domainRequests"/>
     			<a tabIndex="1" title="${domainRequests}" href="<c:url value="/searchDomainRequests.go"/>" onclick="setTabIndex('${domainRequests}')">${domainRequests}</a>
   			</li>
		</ul>
   	</li>
  	<li>
  		<spring:message code="nav.reports" var="reports"/>
  		<a tabIndex="1" title="${reports}" href="<c:url value="/reports.go"/>" onclick="setTabIndex('${reports}')">${reports}</a>
  	</li>
  	<li>
  		<spring:message code="nav.cotsServices" var="cots"/>
  		<a tabIndex="1" title="${cots}" href="<c:url value="/fdbSearch.go"/>" onclick="setTabIndex('${cots}')">${cots}</a>
   		<ul class="submenu">
	   		<li>
	   			<spring:message code="nav.fdbSearch" var="fdbSearch"/>
	     		<a tabIndex="1" title="${fdbSearch}" href="<c:url value="/fdbSearch.go"/>" onclick="setTabIndex('${fdbSearch}')">${fdbSearch}</a>
	    	</li>
	    	<li>
	    		<spring:message code="nav.fdbAdd" var="fdbAdd"/>
	     		<a tabIndex="1" title="${fdbAdd}" href="<c:url value="/fdbAdd.go"/>" onclick="setTabIndex('${fdbAdd}')">${fdbAdd}</a>
	    	</li>	
		    <li>
		    	<spring:message code="nav.fdbUpdate" var="fdbUpdate"/>
		    	<a tabIndex="1" title="${fdbUpdate}" href="<c:url value="/fdbUpdate.go"/>" onclick="setTabIndex('${fdbUpdate}')">${fdbUpdate}</a>
		    </li>	
		    <li>	
		    	<spring:message code="nav.fdbAddReport" var="fdbAddReport"/>
		    	<a tabIndex="1" title="${fdbAddReport}" href="<c:url value="/fdbAddReport.go"/>" onclick="setTabIndex('${fdbAddReport}')">${fdbAddReport}</a>
		    </li>
		    <li>
		    	<spring:message code="nav.fdbUpdateReport" var="fdbUpdateReport"/>
	     		<a tabIndex="1" title="${fdbUpdateReport}" href="<c:url value="/fdbUpdateReport.go"/>" onclick="setTabIndex('${fdbUpdateReport}')">${fdbUpdateReport}</a>
	    	</li>
    	</ul>
  	</li>
 <c:if test="${user.rolePnsFlag && user.rolePnsApvdFlag}"> 
 
  	<li>
  		<spring:message code="nav.administration" var="administration"/>
   		<a tabIndex="1" title="${administration}" href="<c:url value="/domainMapping.go"/>" onclick="setTabIndex('${administration}')">${administration}</a>
   		<ul class="submenu">
	 		<li>
	 			<spring:message code="nav.domainMapping" var="domainMapping"/>
	     		<a tabIndex="1" title="${domainMapping}" href="<c:url value="/domainMapping.go"/>" onclick="setTabIndex('${domainMapping}')">${domainMapping}</a>
	    	</li>
	    	<li>
	    		<spring:message code="nav.schduler" var="scheduler"/>
	     		<a tabIndex="1" title="${scheduler}" href="<c:url value="/schedulerHome.go"/>" onclick="setTabIndex('${scheduler}')">${scheduler}</a>
	    	</li>
            <li>
                <spring:message code="nav.userRoles" var="userRoles"/>
                <a tabIndex="1" title="${userRoles}" href="<c:url value="/userRoles.go"/>" onclick="setTabIndex('${userRoles}')">${userRoles}</a>
            </li>           
            <li>
                <spring:message code="nav.editHomePage" var="editHomePage"/>
                <a tabIndex="1" title="${editHomePage}" href="<c:url value="/editHomePage.go"/>" onclick="setTabIndex('${editHomePage}')">${editHomePage}</a>
            </li>           
    	</ul>
  	</li>
  </c:if>
  	<li>
  		<spring:message code="nav.accountManagement" var="displaySettings"/>
  		<a tabIndex="1" title="${displaySettings}" href="<c:url value="/displaySettings.go"/>" onclick="setTabIndex('${displaySettings}')">${displaySettings}</a>
   		<ul class="submenu">
	   		<li>
	     		<a tabIndex="1" title="${displaySettings}" href="<c:url value="/displaySettings.go"/>" onclick="setTabIndex('${displaySettings}')">${displaySettings}</a>
	    	</li>
	    	<li>
	    		<spring:message code="nav.searchManagement" var="searchManagement"/>
	     		<a tabIndex="1" title="${searchManagement}" href="<c:url value="/searchSettings.go"/>" onclick="setTabIndex('${searchManagement}')">${searchManagement}</a>
	    	</li>
		</ul>	
  	</li>
  	<c:if test="${user.roleMigFlag && user.roleMigApvdFlag}">
  	<li>
  		<spring:message code="nav.migration" var="migration"/>
   		<a tabIndex="1" title="${migration}" href="<c:url value="/migrationHome.go"/>" onclick="setTabIndex('${migration}')">${migration}</a>
	   	<ul class="submenu">
	    	<li>
	    		<spring:message code="nav.migrationControl" var="migrationControl"/>
	     		<a tabIndex="1" title="${migrationControl}" href="<c:url value="/migrationControl.go"/>" onclick="setTabIndex('${migrationControl}')">${migrationControl}</a>
	    	</li>
		    <li>
		    	<spring:message code="nav.migrationStart" var="migrationStart"/>
		    	<a tabIndex="1" title="${migrationStart}" href="<c:url value="/migrationHome.go"/>" onclick="setTabIndex('${migrationStart}')">${migrationStart}</a>
		    </li>
		    <li>
		    	<spring:message code="nav.migrationReport" var="migrationReport"/>
		    	<a tabIndex="1" title="${migrationReport}" href="<c:url value="/migrationSummary.go"/>" onclick="setTabIndex('${migrationReport}')">${migrationReport}</a>
		    </li>
	    </ul>    
    </li>
    </c:if>
    <%-- <li>
    	<spring:message code="nav.about" var="about"/>
  		<a tabIndex="1" title="${about}" href="<c:url value="/about.go"/>" onclick="setTabIndex('${about}')">${about}</a>
   	</li> --%>
   	</c:if>
   	<li>
    	<spring:message code="nav.mainHelp" var="mainHelp"/>
    	<c:url value="/help/index.htm" var="helpIndex"/>
  		<a tabindex="1" href='javascript:RH_ShowHelp(0, "${helpIndex}", HH_HELP_CONTEXT, 0)'>${mainHelp}</a>
  	</li>
</ul>

<input type="hidden" id="menuSelected" value="${sessionScope.menuItemSelected}"/>
