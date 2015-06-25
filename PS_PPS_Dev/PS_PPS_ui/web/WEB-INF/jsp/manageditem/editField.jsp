<%@page import="gov.va.med.pharmacy.peps.common.vo.Environment"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>

<%
FieldAuthorizationVo fieldAuthorization = (FieldAuthorizationVo)request.getAttribute("fieldAuthorization");
ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
FieldKey fieldKey = (FieldKey)request.getAttribute("fieldKey");
%>


<c:if
    test="<%=fieldAuthorization.isDisabled(fieldKey) || fieldAuthorization.isNonEditable(fieldKey) || fieldAuthorization.isRequired(fieldKey) || fieldAuthorization.isRequiresSecondApproval(fieldKey) || fieldAuthorization.isLocalOnly(fieldKey)%>">
    <div class="panel"><spring:message code="field.restrictions" />
    <ul>
        <c:if test="<%=fieldAuthorization.isDisabled(fieldKey) %>">
            <li><spring:message code="field.restrictions.read.only" /></li>
        </c:if>
        <c:if test="<%=fieldAuthorization.isNonEditable(fieldKey) %>">
            <li><spring:message code="field.restrictions.locked" /></li>
        </c:if>
        <c:if test="<%=fieldAuthorization.isRequired(fieldKey) %>">
            <li><spring:message code="field.restrictions.required" /></li>
        </c:if>
        <c:if test="<%=fieldAuthorization.isRequiresSecondApproval(fieldKey) %>">
            <li><spring:message code="field.restrictions.second.approval" /></li>
        </c:if>
        <c:if test="<%=fieldAuthorization.isLocalOnly(fieldKey) %>">
            <li><spring:message code="field.restrictions.local.only" /></li>
        </c:if>
<%--         <c:if test="<%=isLocal() && fieldAuthorization.isNationalDataField(fieldKey) %>"> --%>
<%--             <li><spring:message code="field.restrictions.national.data.field" /></li> --%>
<%--         </c:if> --%>
    </ul>
    </div>
</c:if>

<div id="editFieldForm">
<peps:form id="editField">
    
        <peps:dataField
            key="${fieldKey.key}"
            item="${item}"
            managedDataAddLink="false" />
        <div class="hidden">
        <input type="hidden" name="fieldKey" id="fieldKey" value="${fieldKey.key}"/>
        <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/saveDomainField.go" var="actionUrl"/>
        <peps:submit
            id="submit.field"
            key="button.submit"
            action="${actionUrl}"
            disabled="true" />
</div>
    
</peps:form>
</div>
<div
    id="buttons"
    class="panel"><span class="column">
<button
    id="closewindow"
    onclick="window.close();"><spring:message code="button.cancel" /></button>
</span>



<c:if test="<%=!fieldAuthorization.isDisabled(fieldKey) || fieldAuthorization.isLocalOnly(fieldKey) %>">
    <span class="column">
    <button
        id="updateField"
        onclick="submitButton = 'submitPopup'; disableSubmit('editField'); submitPopup('editField','submit.field');"
        class="floatRight"><spring:message code="button.saveChanges" /></button>
    </span>
</c:if></div>

<peps:javascriptLocalization />

