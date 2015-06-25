<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
	
<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form" %>	

<div class="horizontalspacer"></div>
<%-- <peps:form id="sendEntireDrugFile"> --%>
<div class="panel" style="width: 400px"> 

<%--     <s:if test="status == success">
        <s:property value="fileSent" /><br /><br />
    </s:if>
    <s:else>&nbsp;
    </s:else> --%>

    <c:out value="${status}" /><br /><br />      

    <span class="column">
        <peps:submit
            key="button.sendEntireDrugFile"
            event="sendEntireDrugFile"
            isFlowButton="false"
             />
            
<!-- <input type="submit" value="Send Entire Drug File" /> -->

    </span>

</div>
<%-- </peps:form> --%>