<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
		
<peps:form id="notificationSearch">
<script language="javascript">
var otherOnLoad = window.onload;
window.onload = function() {
    otherOnLoad();
    setDateRangeVisible(document.getElementById('notification.date.use').checked);
}
function setDateRangeVisible(show) {
    if (show) {
        document.getElementById('dateDiv').className = 'threeColumn';
    }
    else {
        document.getElementById('dateDiv').className = 'threeColumn hidden';
    }
}
</script>
    <div class="panel">
        <div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>  
        <div>           
            <span class="column">
                <peps:dataField key="notification.is.unread" labelPosition="right"/>
                <peps:dataField key="notification.is.viewed" labelPosition="right" />
                <peps:dataField key="notification.is.hidden" labelPosition="right" />
            </span>
        </div>
        <div class="column">        
        <span class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>   
            <span class="column">
            <peps:dataField key="notification.date.use" labelPosition="right" jsOnClick="setDateRangeVisible(this.checked)"/>
            <peps:dataField key="notification.only.local" labelPosition="right"/>
            </span>
        </div>

        <div class="threeColumn hidden" id="dateDiv">
            <peps:dataField key="search.notification.start.date" labelPosition="left" cssClass="iahdate" />
            <peps:dataField key="search.notification.end.date" labelPosition="left" cssClass="iahdate" />
            <s:if test="%{criteria.wasDateChanged}"> <s:text name="notification.date.reset"/></s:if>
            <%--  <c:if test="%{criteria.wasDateChanged}"> <spring:message code="${notification.date.reset}"/></c:if> --%>
            
        </div>
        
        <div class="bodyheaderspacer"></div>
    <div class="column spacer"></div> 
      <div class="fourColumn">      
            <div class="fourColumn"><peps:submit
                key="button.search"
                event="search" /></div>
        </div>     
        </div>   

</peps:form>