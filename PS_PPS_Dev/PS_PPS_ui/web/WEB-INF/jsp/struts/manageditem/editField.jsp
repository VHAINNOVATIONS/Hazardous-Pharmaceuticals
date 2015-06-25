<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<s:if
    test="fieldAuthorization.isDisabled(#attr.fieldKey) || fieldAuthorization.isNonEditable(#attr.fieldKey) || fieldAuthorization.isRequired(#attr.fieldKey) || fieldAuthorization.isRequiresSecondApproval(#attr.fieldKey) || (isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)) || (isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey))">
    <div class="panel"><s:text name="field.restrictions" />
    <ul>
        <s:if test="fieldAuthorization.isDisabled(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.read.only" /> --%><spring:message code="field.restrictions.read.only" /></li>
        </s:if>
        <s:if test="fieldAuthorization.isNonEditable(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.locked" /> --%><spring:message code="field.restrictions.locked" /></li>
        </s:if>
        <s:if test="fieldAuthorization.isRequired(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.required" /> --%><spring:message code="field.restrictions.required" /></li>
        </s:if>
        <s:if test="fieldAuthorization.isRequiresSecondApproval(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.second.approval" /> --%><spring:message code="field.restrictions.second.approval" /></li>
        </s:if>
        <s:if test="isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.local.only" /> --%><spring:message code="field.restrictions.local.only" /></li>
        </s:if>
        <s:if test="isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey)">
            <li><%-- <s:text name="field.restrictions.national.data.field" /> --%><spring:message code="field.restrictions.national.data.field" /></li>
        </s:if>
    </ul>
    </div>
</s:if>


<%-- SPRING/JSTL Converstions
<c:if
    test="fieldAuthorization.isDisabled(#attr.fieldKey) || fieldAuthorization.isNonEditable(#attr.fieldKey) || fieldAuthorization.isRequired(#attr.fieldKey) || fieldAuthorization.isRequiresSecondApproval(#attr.fieldKey) || (isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)) || (isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey))">
    <div class="panel"><s:text name="field.restrictions" />
    <ul>
        <c:if test="fieldAuthorization.isDisabled(#attr.fieldKey)">
            <li><s:text name="field.restrictions.read.only" /></li>
        </c:if>
        <c:if test="fieldAuthorization.isNonEditable(#attr.fieldKey)">
            <li><s:text name="field.restrictions.locked" /></li>
        </c:if>
        <c:if test="fieldAuthorization.isRequired(#attr.fieldKey)">
            <li><s:text name="field.restrictions.required" /></li>
        </c:if>
        <c:if test="fieldAuthorization.isRequiresSecondApproval(#attr.fieldKey)">
            <li><s:text name="field.restrictions.second.approval" /></li>
        </c:if>
        <c:if test="isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)">
            <li><s:text name="field.restrictions.local.only" /></li>
        </c:if>
        <c:if test="isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey)">
            <li><s:text name="field.restrictions.national.data.field" /></li>
        </c:if>
    </ul>
    </div>
</c:if>
 --%>
<div id="editFieldForm"><peps:form id="editField">
    <peps:model ognlPrefix="item">
        <peps:dataField
            key="${fieldKey}"
            ognl="${ognlKey}"
            managedDataAddLink="false" />
        <div class="hidden"><peps:submit
            id="submit.field"
            key="button.submit"
            event="submitPopup"
            disabled="true" /></div>
    </peps:model>
</peps:form></div>
<div
    id="buttons"
    class="panel"><span class="column">
<button
    id="closewindow"
    onclick="window.close();"><%-- <s:text name="button.cancel" /> --%><spring:message code="button.cancel" /></button>
</span>
<s:if test="!(fieldAuthorization.isDisabled(#attr.fieldKey) || (isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)) || (isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey)))">
    <span class="column">
    <button
        id="updateField"
        onclick="submitButton = 'submitPopup'; disableSubmit('editField'); submitPopup('editField','submit.field');"
        class="floatRight"><%-- <s:text name="button.saveChanges" /> --%><spring:message code="button.saveChanges" /></button>
    </span>
</s:if></div>

<%-- 
<c:if test="!(fieldAuthorization.isDisabled(#attr.fieldKey) || (isNational() && fieldAuthorization.isLocalOnly(#attr.fieldKey)) || (isLocal() && fieldAuthorization.isNationalDataField(#attr.fieldKey)))">
    <span class="column">
    <button
        id="updateField"
        onclick="submitButton = 'submitPopup'; disableSubmit('editField'); submitPopup('editField','submit.field');"
        class="floatRight"><s:text name="button.saveChanges" /></button>
    </span>
</c:if></div>
 --%>
 </div>
<peps:javascriptLocalization />
</body>
</html>