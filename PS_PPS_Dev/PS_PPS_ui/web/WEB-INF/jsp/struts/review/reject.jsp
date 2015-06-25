<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	
    
<div class="twoColumn">
<center>
<h3>Provide Details for the Rejection</h3>
</center>
</div>
<div class="horizontalspacer"></div>
<peps:form id="confirmReject">
    <div class="twoColumnWide"><peps:dataField
        key="request.rejection.reason"
        ognl="mainRequest.requestRejectReason"
        disabled="false" /><br />
    <c:if test="${mainRequest.requestType.addition}">
        <div class="twoColumn"><peps:dataField
            key="note"
            ognl="mainRequest.note"
            disabled="true"
            rows="5"
            cols="10" /></div>
    </c:if> <label style="vertical-align: top;">Enter any optional comments
    here:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><peps:dataField
        key="new.note"
        ognl="mainRequest.newNote"
        labelPosition="none"
        cols="20"
        rows="4"
        cssStyle="width: 395px;" />
    </div>
    <div class="buttonspacer"></div>
    <div class="twoColumn">
    <div class="center"><span class="column"><peps:submit
        key="button.cancel"
        event="cancel" /></span><span class="column"></span><span class="column"><peps:submit
        key="button.submit"
        event="confirm" /></span></div>
    </div>
</peps:form>