<%@page import="gov.va.med.pharmacy.peps.common.vo.MergeDifferenceVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.DateFormatUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
    prefix="spring"
    uri="http://www.springframework.org/tags"%> 
<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%> 
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core"%>
<%
ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
%> 
<peps:warnings id="warnings" warnings="${warnings}"/>
<br />
    <h3>
    <div class="columnpadded">
    <c:choose>
    <c:when test="${item.entityType.product}">
        PRODUCT: <peps:text
            item="${item}"
            key="product.long.name"
            label="false" />
    </c:when>
    <c:when test="${item.entityType.ndc}">
        NDC: <peps:text
            item="${item}"
            key="ndc"
            label="false" />
    </c:when>
    <c:when test="${item.entityType.orderableItem}">
        OI: <peps:text
            item="${item}"
            key="oi.name"
            label="false" />
    </c:when>
    <c:otherwise>
        <peps:text
            item="${item}"
            key="value"
            label="true"/>
    </c:otherwise>
    </c:choose>
    </div>
    <div class="columnpadded">&nbsp; has been modified by another user since you retrieved it</div>
    </h3>

<form:form id="mergeSummary" commandName="modificatonSummary" onsubmit="return disableSubmit(this.id);">
    <div class="panel">
	    <fieldset>
		    <legend>Merge Summary for VA Data Fields</legend>
		    <table
		        id="mergeTable"
		        frame="box"
		        rules="cols"
		        class="pepsTable">
		        <thead>
		            <tr>
		                <th><peps:label key="field.name" abbreviation="true" /></th>
		                <th><peps:label key="original.value" abbreviation="true" /></th>
		                <th><peps:label key="modifiers.value" abbreviation="true" /></th>
		                <th><peps:label key="your.value" abbreviation="true" /></th>
		                <th><peps:label key="modified.by" abbreviation="true" /></th>
		                <th><peps:label key="modified.date" abbreviation="true" /></th>
		                <th><peps:label key="accept.your.change" abbreviation="true" /></th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:set
		                scope="page"
		                var="row"
		                value="0" />
		            <c:forEach
		                var="mItem"
		                items="${mergeVo.mergeRequestDetails}"
		                varStatus="modStatus">
		                <%
		                MergeDifferenceVo mergeItem = (MergeDifferenceVo)pageContext.getAttribute("mItem");        
                        %> 
		                <c:set
		                    scope="page"
		                    var="index"
		                    value="${modStatus.index}" />
		                <c:choose>
			                <c:when test="${row % 2 == 0}">
			                    <tr class="odd">
			                </c:when>
			                <c:otherwise>
			                    <tr class="even">
			                </c:otherwise>
		                </c:choose>
		                <c:set
		                    scope="page"
		                    var="row"
		                    value="${row + 1}" />
		                <c:choose>
		                <c:when test="${item.entityType.domainType}">
		                    <td> 
		                    <peps:text
		                            item="<%=mergeItem.getDifference().getFieldKey().getLocalizedName(request.getLocale(), item.getEntityType())%>"
		                            key="field.name"/>
		                    </td>    
		                </c:when>
		                <c:otherwise>
		                    <td> <peps:text
		                            item="<%=mergeItem.getDifference().getFieldKey().getLocalizedName(request.getLocale())%>"
		                            key="field.name"/>
		                    </td>
		                </c:otherwise>
		                </c:choose>		                
		                <td><peps:text
		                    item="${mItem.difference.oldValueShortString}"
		                    key="current.value"/></td>
		                <td><peps:text
		                    item="${mItem.difference.modifiersValueShortString}"
		                    key="current.value"/></td>
		                <td><peps:text
		                    item="${mItem.difference.newValueShortString}"
		                    key="modified.value" /></td>
		                <td><peps:text
		                    item="${mItem}"
		                    key="modified.by" /></td>
		                <td><peps:text
		                    item="${mItem}"
		                    key="modified.date" /></td>		                    
		                <td align="center">
		                  <form:checkbox id="accept.change_${index}" 
                                          path="modifications[${index}].acceptChange" 
                                          cssClass="checkbox2 center" />
                        </td>
		                </tr>
		            </c:forEach>
		        </tbody>
		    </table>
	    </fieldset>
	    <div>
		    <div class="twoColumn">
		     <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/mergeSummaryCancel.go" var="actionUrl"/>
		        <peps:submit key="button.cancel" action="${actionUrl}"/>
		    </div>
		    <div class="twoColumn">
		        <peps:submit key="button.accept.changes" />
		    </div>
	    </div>
    </div>
</form:form>