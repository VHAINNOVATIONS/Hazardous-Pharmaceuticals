<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<div class="panel">
<fieldset style="width: 296px;"><legend class="legend">Request Details</legend><peps:text
    key="request.item.status"
    label="true" /><peps:text
    key="request.rejection.reason"
    label="true" /><peps:text
    key="rejection.reason.text"
    label="true" /></fieldset>
    </div>
    <div class="panel">
    
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
    
<fieldset><legend class="legend"><peps:label
    key="${itemTypeString}" />&nbsp;<%--<s:text name="title.iah" /> --%><spring:message code="title.iah" /></legend><peps:table
    tableId="iahTable"
    dataOgnl="itemAuditHistory"
    fieldsOgnl="iahPrintTemplate"
    paged="false" /></fieldset>
    </div>