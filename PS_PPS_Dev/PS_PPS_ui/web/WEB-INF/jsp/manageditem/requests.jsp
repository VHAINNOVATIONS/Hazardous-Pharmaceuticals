<%@page import="gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.RequestVo"%>
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
	
<%
RequestVo mainRequest = (RequestVo)request.getAttribute("mainRequest");
%> 
	
<table
    id="requests"
    frame="box"
    rules="cols"
    class="pepsTable">
    <thead>
        <tr>
            
            <th><peps:label 
                key="column.action" /></th>
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
    	<c:forEach
    	    var="detail"
            items="${mainRequest.requestDetails}"
            varStatus="details">            
            <c:set
                scope="page"
                var="index"
                value="${details.index}" />
            <%
            int i = (Integer)pageContext.getAttribute("index");
            ModDifferenceVo detail = (ModDifferenceVo)pageContext.getAttribute("detail");
            %>    
            <c:set
                scope="page"
                var="href"
                value="${details.index}"  />
			<c:choose>
	            <c:when test="${index mod 2 == 0}">
	                <tr class="odd">
	            </c:when>
	            <c:otherwise>
	                <tr class="even">
	            </c:otherwise>
            </c:choose>
            <tr>
<%--             <c:choose> --%>
<%--             	<c:when test="%{environment.local}"> --%>
<%--                 <td><peps:text --%>
<!--                     key="field.name" -->
<%--                     ognl="mainRequest.requestDetails[${index}].difference.fieldKey.getLocalizedName(locale)" /></td> --%>
<%--                 <td><peps:text --%>
<!--                     key="current.value" -->
<%--                     ognl="mainRequest.requestDetails[${index}].difference.oldValueShortString" /></td> --%>
<%--                 <td><peps:text --%>
<!--                     key="modified.value" -->
<%--                     ognl="mainRequest.requestDetails[${index}].difference.newValueShortString" /></td> --%>
<%--                 <td><peps:text --%>
<!--                     key="modification.reason" -->
<%--                     ognl="mainRequest.requestDetails[${index}].modificationReason" /></td> --%>
<%--                 <td align="center"><peps:dataField --%>
<!--                     key="request.to.modify.value" -->
<%--                     ognl="mainRequest.requestDetails[${index}].requestToModifyValue" --%>
<!--                     labelPosition="none" -->
<!--                     disabled="true"  -->
<!--                     cssClass="center" /></td> -->
<%--                 <td align="center"><peps:dataField --%>
<!--                     key="request.to.make.editable" -->
<%--                     ognl="mainRequest.requestDetails[${index}].requestToMakeEditable" --%>
<!--                     labelPosition="none" -->
<!--                     disabled="true"  -->
<!--                     cssClass="center" /></td> -->
<%--                 <td align="center"><peps:text --%>
<!--                     key="requested.by" -->
<%--                     ognl="mainRequest.requestDetails[${index}].requestor.toDisplayName()" /></td> --%>
<%--                 <td align="center"><peps:text --%>
<!--                     key="reviewed.by" -->
<%--                     ognl="mainRequest.requestDetails[${index}].reviewer.toDisplayName()" /></td> --%>
<%--                 <td align="center"><peps:text --%>
<!--                     key="note" -->
<%--                     ognl="mainRequest.requestDetails[${index}].comments" /></td>             --%>
<%--             </c:when> --%>
<%--             <c:otherwise> --%>
                <td align="center">
                    <peps:dataField                    
	                    item="${detail}"
	                    key="mod.request.item.status"
	                    id="mod.request.item.status_${index}"	                    
	                    labelPosition="none" /></td>
                <td><peps:text
                    item="<%=detail.getDifference().getFieldKey().getLocalizedName(request.getLocale())%>" 
                    key="field.name"
                     /></td>
                <td><peps:text
                    item="${detail.difference.oldValueShortString}" 
                    key="current.value"/></td>
                <td><peps:text
                    item="${detail.difference.newValueShortString}"
                    key="modified.value" /></td>
                <td><peps:text
                    item="${detail}"
                    key="modification.reason"/></td>                    
                <td align="center">
                <peps:dataField
                    item="${detail}"
                    key="request.to.modify.value"
                    id="request.to.modify.value_${index}"                     
                    labelPosition="none"
                    disabled="true"
                    cssClass="center" /></td>
                <td align="center">
                <peps:dataField
                    item="${detail}" 
                    key="request.to.make.editable"
                    id="request.to.make.editable_${index}"                     
                    labelPosition="none"
                    disabled="true"
                    cssClass="center" /></td>
                <td align="center">
                <c:choose>
                	<c:when test="${detail.requestor ne null}">
	                	<c:set value="${detail.requestor.displayName}" var="requestorName"/>
	                </c:when>
	                <c:otherwise>
	                	<c:set value="" var="requestorName"/>
	                </c:otherwise>
                </c:choose>
                <peps:text
                    item="${requestorName}"
                    key="requested.by"/></td>
                <td align="center">
                <c:choose>
                	<c:when test="${detail.reviewer ne null}">
	                	<c:set value="${detail.reviewer.displayName}" var="reviewerName"/>
	                </c:when>
	                <c:otherwise>
	                	<c:set value="" var="reviewerName"/>
	                </c:otherwise>
                </c:choose>
                <peps:text
                    item="${reviewerName}"
                    key="reviewed.by" /></td>                   
                <td align="center">
	                <peps:dataField
	                    item="${detail}"
	                    key="comments"
	                    id="note_${index}"                     
	                    labelPosition="none"
	                    rows="2"
	                    cols="10" /></td>
<%--             </c:otherwise> --%>
<%--             </c:choose> --%>
            </tr>
        
        </c:forEach>
    </tbody>
</table>
