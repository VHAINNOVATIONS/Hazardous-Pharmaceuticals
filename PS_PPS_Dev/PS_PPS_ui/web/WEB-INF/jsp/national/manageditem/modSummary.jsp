<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"%>
<%@page import="java.util.Collection"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
    prefix="form"
    uri="http://www.springframework.org/tags/form"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%
ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
EntityType entityType = item.getEntityType();
Collection<ModDifferenceVo> modDifferences = (Collection<ModDifferenceVo>)request.getAttribute("modDifferences");
FieldAuthorizationVo fieldAuthorization = (FieldAuthorizationVo) request.getAttribute("fieldAuthorization");
if (fieldAuthorization == null)
    fieldAuthorization = new FieldAuthorizationVo();
%>
<peps:warnings id="warnings" warnings="${warnings}"/>
<br />
<div class="panel">
<center><h3>
	<span class="modSummaryTitle">Modification Summary for</span> 
	<span class=""> 
		<c:choose>
	    	<c:when test="<%=entityType.isProduct()%>">
	            Product: <peps:text item="${item}" key="product.long.name" label="false" />
			</c:when>
			<c:when test="<%=entityType.isNdc()%>">
	            NDC: <peps:text item="${item}" key="ndc" label="false" />
			</c:when>
			<c:when test="<%=entityType.isOrderableItem()%>">
	            Orderable Item: <peps:text item="${item}" key="oi.name" label="false" />
			</c:when>
			<c:when test="<%=entityType.isDomainType()%>">
	            Name: <peps:text item="${item}" key="value" label="false" />
			</c:when>
			<c:otherwise>
			  <peps:text item="${item}" key="value" label="true" />
			</c:otherwise>
		</c:choose>
		
	</span>
</h3></center></div>

<form:form id="modificationSummary" commandName="modificatonSummary"
    method="post" action="" 
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">


    <c:if test="${showPsrName}">
	    <peps:dataField item="${item}" key="psr.name" />
		<div class="buttonspacer"></div>
	</c:if>
     
     <c:choose> 
	     <c:when test="<%=DifferenceUtility.hasEditableDifference(modDifferences) %>" >     
	        <fieldset>
	           <legend>Modification Summary for Data Fields</legend>
	        <table
	            id="modTable"
	            frame="box"
	            rules="cols"
	            class="pepsTable">
	            <thead>
	                <tr>
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
	                        key="accept.change"
	                        abbreviation="true" /></th>
	                    <th><peps:label
	                        key="requires.second.approval"
	                        abbreviation="true" /></th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:set var="row" scope="page" value="0" />
	                <c:forEach
	                    var="modDifference"
	                    items="${modDifferences}"                    
	                    varStatus="modStatus">     
	                    <%
	                    ModDifferenceVo modDifference = (ModDifferenceVo)pageContext.getAttribute("modDifference");        
	                    %>               
	                    <c:set
	                        scope="page"
	                        var="index"
	                        value="${modStatus.index}" />
	                    <c:choose>
	                    	<c:when test="${row mod 2 == 0}">
	                        	<tr class="odd">
	                    	</c:when>
	                    	<c:otherwise>
	                        	<tr class="even">
	                    	</c:otherwise>
	                    </c:choose>
	                    
	                    
	                   	<c:set var="row" scope="page" value="${row + 1}" />                    	
	                    
	                    <c:choose>                    
		                    <c:when test="<%=entityType.isDomainType()%>">
		                        <td>	                        
		                        <peps:text
		                            item="${modDifference}"
		                            key="field.name"	                            
		                            value="<%=modDifference.getDifference().getFieldKey().getLocalizedName(request.getLocale(), entityType) %>" /></td>
		                    </c:when>
		                    <c:otherwise>
		                        <td><peps:text
		                            item="${modDifference}"
		                            key="field.name"
		                            value="<%=modDifference.getDifference().getFieldKey().getLocalizedName(request.getLocale()) %>" /></td>
		                    </c:otherwise>
	                    </c:choose>
	                    
	                    <td><peps:text
	                        item="${modDifference}"
	                        key="current.value"
	                        value="<%=modDifference.getDifference().getOldValueShortString()%>" /></td>
	                    <td><peps:text
	                        item="${modDifference}"
	                        key="modified.value"
	                        value="<%=modDifference.getDifference().getNewValueShortString()%>" /></td>
	                    <td>
	                       <c:if test="${modDifference.difference.fieldKey.key eq 'item.status' and modDifference.difference.oldValue eq 'ACTIVE'}"><span class="required">*</span> </c:if>
	                       <form:textarea id="modification.reason_${index}" 
	                                      cssClass="textarea" 
	                                      path="modifications[${index}].reason" 
	                                      rows="2" cols="10" />
	                    </td>
	                    <td align="center">
	                       <form:checkbox id="accept.change_${index}" 
	                                      path="modifications[${index}].acceptChange" 
	                                      cssClass="checkbox2 center" />
	                    </td>
	                    <td align="center">
	                    <c:choose>
	                    	<c:when test="${modDifference.difference.fieldKey.key eq 'item.status'}">
	                    		<c:set value="true" var="requiresSecondApprovalChecked"/>
	                    	</c:when>
		                    <c:otherwise>
		                    	<c:set value="${modDifference.difference.fieldKey.requiresSecondApproval}" var="requiresSecondApprovalChecked"/>
		                    </c:otherwise>
                        </c:choose>
                        
                        <peps:dataField
		                        item="${modDifference}"
		                        key="requires.second.approval"
		                        id="requires.second.approval_${index}"
		                        value="${requiresSecondApprovalChecked}"
		                        labelPosition="none"
		                        cssClass="checkbox2 center"
		                        disabled="true" />
                        </td>
	                    </tr>
	                </c:forEach>
	               
	            </tbody>
	        </table>
	        </fieldset>
	    
	    </c:when>
	    
	    <c:otherwise>
	        <p>No Data Fields were modified!</p>     
	    </c:otherwise> 
    </c:choose> 
    <div>
    <div class="threeColumn">    
        <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/modificationSummaryCancel.go?tab=${tab}" var="actionUrl"/>
        <peps:submit
        key="button.cancel"
        action="${actionUrl}" />
    </div>
    <div class="threeColumn">
	    <c:if test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) > 1}">
	        <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/startApplyToAll.go" var="applyToAllUrl"/>
	        <peps:submit key="button.accept.changeAll" action="${applyToAllUrl}" />    
	    </c:if>
    </div>
    
    <c:if test="<%=DifferenceUtility.hasEditableDifference(modDifferences) %>" >
        <div class="threeColumn">
        
        <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/commitModifications.go" var="actionUrl"/>
        <c:choose>
            <c:when test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) > 1}">
                <peps:submit
                    key="button.accept.changes2"
                    action="${actionUrl}" />
        
            </c:when>
            <c:otherwise>
               <peps:submit
                   key="button.accept.changes"
                   action="${actionUrl}" />
        
            </c:otherwise>
        </c:choose>
        
        </div>
    
    </c:if>
    </div>
    </div>
</form:form>