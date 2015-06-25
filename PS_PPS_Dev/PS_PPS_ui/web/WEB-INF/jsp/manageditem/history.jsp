<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>	
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>

<div class="panel">
	<fieldset style="width:25%;"><legend class="legend">Request Details</legend>
		<div class="controlLabel">
            <peps:text key="request.item.status" cssClass="comboBox" label="true" item="${item}"/>       
            <peps:text key="request.rejection.reason" cssClass="comboBox" label="true" item="${item}"/>            
            <peps:text key="rejection.reason.text" cssClass="comboBox" label="true"  item="${item}"/>
       </div>
	</fieldset>
</div>
<div class="panel">

	<fieldset>
		<legend class="legend">
			<peps:label key="EntityType.${item.entityType}" />&nbsp;<spring:message code="title.iah" />
	    </legend>		   
	    <peps:table
			tableId="${iahTable}"
			dataModel="${item.itemAuditHistory}"
			printTemplate="<%=gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants.ITEM_AUDIT_HISTORY_PRINT_TEMPLATE%>"
			paged="false" /> 
	</fieldset>
</div>
