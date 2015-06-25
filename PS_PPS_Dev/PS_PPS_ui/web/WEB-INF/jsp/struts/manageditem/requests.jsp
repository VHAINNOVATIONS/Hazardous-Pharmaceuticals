<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%> 
	
<table
    id="requests"
    frame="box"
    rules="cols"
    class="pepsTable">
    <thead>
        <tr>
        	<s:if test="%{environment.national}">
                <th><peps:label key="column.action" /></th>
            </s:if>
            <%-- 
            <c:if test="${environment.national}">
                <th><peps:label key="column.action" /></th>
            </c:if> --%>
            <th><peps:label
                key="field.name"
                abbreviation="true" /></th>
            <th><peps:label
                key="current.value"
                abbreviation="true" /></th>
            <th><peps:label
                key="modified.value"
                abbreviation="true" /></th>
            <th><peps:label
                key="modification.reason"
                abbreviation="true" /></th>
            <th><peps:label
                key="request.to.modify.value"
                abbreviation="true" /></th>
            <th><peps:label
                key="request.to.make.editable"
                abbreviation="true" /></th>
            <th><peps:label
                key="requested.by"
                abbreviation="true" /></th>
            <th><peps:label
                key="reviewed.by"
                abbreviation="true" /></th>
            <th><peps:label
                key="request.comments"
                abbreviation="true" /></th>
        </tr>
    </thead>
    <tbody>
    	<s:iterator
            value="mainRequest.requestDetails"
            status="details">
        <%--<c:forEach
            items="mainRequest.requestDetails"
            varStatus="details"> --%>
            <s:set
                scope="page"
                name="index"
                value="#details.index" />
            <%--<c:set
                scope="page"
                var="index"
                value="details.index" /> --%>
            <s:set
                scope="page"
                name="href"
                value="#details.index" />
			<%--<c:set
                scope="page"
                var="href"
                value="details.index" />
			 --%>
            <%-- <s:if test="#attr.index % 2 == 0">
                <tr class="odd">
            </s:if>
            <s:else>
                <tr class="even">
            </s:else> --%>
            
            <c:choose>
	            <c:when test="${index mod 2 == 0}">
	                <tr class="odd">
	            </c:when>
	            <c:otherwise>
	                <tr class="even">
	            </c:otherwise>
            </c:choose>
            
            
            <s:if test="%{environment.local}">
            <%--<c:choose>
            	<c:when test="%{environment.local}"> --%>

                <td><peps:text
                    key="field.name"
                    ognl="mainRequest.requestDetails[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                <td><peps:text
                    key="current.value"
                    ognl="mainRequest.requestDetails[${index}].difference.oldValueShortString" /></td>
                <td><peps:text
                    key="modified.value"
                    ognl="mainRequest.requestDetails[${index}].difference.newValueShortString" /></td>
                <td><peps:text
                    key="modification.reason"
                    ognl="mainRequest.requestDetails[${index}].modificationReason" /></td>
                <td align="center"><peps:dataField
                    key="request.to.modify.value"
                    ognl="mainRequest.requestDetails[${index}].requestToModifyValue"
                    labelPosition="none"
                    disabled="true" 
                    cssClass="center" /></td>
                <td align="center"><peps:dataField
                    key="request.to.make.editable"
                    ognl="mainRequest.requestDetails[${index}].requestToMakeEditable"
                    labelPosition="none"
                    disabled="true" 
                    cssClass="center" /></td>
                <td align="center"><peps:text
                    key="requested.by"
                    ognl="mainRequest.requestDetails[${index}].requestor.toDisplayName()" /></td>
                <td align="center"><peps:text
                    key="reviewed.by"
                    ognl="mainRequest.requestDetails[${index}].reviewer.toDisplayName()" /></td>
                <td align="center"><peps:text
                    key="note"
                    ognl="mainRequest.requestDetails[${index}].comments" /></td>
            </s:if>
            <s:else>
            <%--</c:when>
            <c:otherwise> --%>
                <td align="center"><peps:dataField
                    key="mod.request.item.status"
                    id="mod.request.item.status_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestItemStatus"
                    labelPosition="none" /></td>
                <td><peps:text
                    key="field.name"
                    ognl="mainRequest.requestDetails[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                <td><peps:text
                    key="current.value"
                    ognl="mainRequest.requestDetails[${index}].difference.oldValueShortString" /></td>
                <td><peps:text
                    key="modified.value"
                    ognl="mainRequest.requestDetails[${index}].difference.newValueShortString" /></td>
                <td><peps:text
                    key="modification.reason"
                    ognl="mainRequest.requestDetails[${index}].modificationReason" /></td>
                <td align="center"><peps:dataField
                    key="request.to.modify.value"
                    id="request.to.modify.value_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestToModifyValue"
                    labelPosition="none"
                    disabled="true"
                    cssClass="center" /></td>
                <td align="center"><peps:dataField
                    key="request.to.make.editable"
                    id="request.to.make.editable_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestToMakeEditable"
                    labelPosition="none"
                    disabled="true"
                    cssClass="center" /></td>
                <td align="center"><peps:text
                    key="requested.by"
                    ognl="mainRequest.requestDetails[${index}].requestor.toDisplayName()" /></td>
                <td align="center"><peps:text
                    key="reviewed.by"
                    ognl="mainRequest.requestDetails[${index}].reviewer.toDisplayName()" /></td>
                <td align="center"><peps:dataField
                    key="note"
                    id="note_${index}"
                    ognl="mainRequest.requestDetails[${index}].comments"
                    labelPosition="none"
                    rows="2"
                    cols="10" /></td>
            </s:else>
            <%--</c:otherwise>
            	</c:choose> --%>
            </tr>
        </s:iterator>
        <%--</c:forEach> --%>
    </tbody>
</table>
