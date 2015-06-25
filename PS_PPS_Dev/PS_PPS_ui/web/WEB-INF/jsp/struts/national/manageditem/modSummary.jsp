<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %> 
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
	
<peps:warnings />
<br />
<peps:model ognlPrefix="item">
    <h3>
    <span class="columnpadded">&nbsp;&nbsp;Modification Summary for&nbsp;</span>
    <span class="columnpadded">
	    <s:if test="%{item.entityType.product}">
	        Product:<peps:text
	            key="product.long.name"
	            label="false" />
	    </s:if> 
	    <s:elseif test="%{item.entityType.Ndc}">
	        NDC:<peps:text
	            key="ndc"
	            label="false" />
	    </s:elseif> 
	    <s:elseif test="%{item.entityType.orderableItem}">
	        Orderable Item:<peps:text
	            key="oi.name"
	            label="false" />
	    </s:elseif> 
	    <s:elseif test="%{item.entityType.isDomainType()}">
	        Name: <peps:text 
	            key="value"
	            label="false" />
	    </s:elseif>
		<s:else>
	        <peps:text
	            key="value"
	            label="true" />
	    </s:else> 
	    <%--
	    <c:choose>	    
		    <c:when test="${item.entityType.product}">
		        Product:<peps:text
		            key="product.long.name"
		            label="false" />
		    </c:when> 
		    <c:when test="${item.entityType.Ndc}">
		        NDC:<peps:text
		            key="ndc"
		            label="false" />
		    </c:when> 
		    <c:when test="${item.entityType.orderableItem}">
		        Orderable Item:<peps:text
		            key="oi.name"
		            label="false" />
		    </c:when> 
		  	<c:when test="${item.entityType.isDomainType()}">
		        Name: <peps:text 
		            key="value"
		            label="false" />
		    </c:when> 
		    <c:otherwise>
		        <peps:text
		            key="value"
		            label="true" />
		    </c:otherwise> 
	    </c:choose>
	    --%>
    </span>
    </h3>
</peps:model>


<peps:form id="modificationSummary">
    <div class="panel">

     <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" >
     <%-- <c:choose> 
     <c:when test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" >--%>
     
        <fieldset><legend>Modification Summary for Data Fields</legend>
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
               <%-- <s:set
                    scope="page"
                    name="row"
                    value="0" />--%>
                <c:set var="row" scope="page">
                    	<c:out value="0" />
                </c:set>
                <s:iterator
                    value="modDifferences"
                    status="modStatus">
                <%--<c:forEach
                    value="modDifferences"
                    status="modStatus">--%>
                    <%--<s:set
                        scope="page"
                        name="index"
                        value="#modStatus.index" /> --%>
                    <c:set
                        scope="page"
                        var="index"
                        value="#modStatus.index" />
                    <%--<s:if test="#attr.row % 2 == 0">
                    	<tr class="odd">
                    </s:if>
                    <s:else>
                        <tr class="even">
                    </s:else>
                    --%>
                    <c:choose>
                    	<c:when test="${row mod 2 == 0}">
                        	<tr class="odd">
                    	</c:when>
                    	<c:otherwise>
                        	<tr class="even">
                    	</c:otherwise>
                    </c:choose>
                    
                    <%-- <s:set
                        scope="page"
                        name="row"
                        value="#attr.row + 1" />--%>
                   	<c:set var="row" scope="page" >
                    	<c:out value="${row + 1}" />
                    </c:set>
                    
                    <s:if test="%{item.entityType.isDomainType()}">
                        <td><peps:text
                            key="field.name"
                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale, item.entityType)" /></td>
                    </s:if>
                    <s:else>
                        <td><peps:text
                            key="field.name"
                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                    </s:else>
                    
                    <%--<c:choose>                    
	                    <c:when test="%{item.entityType.isDomainType()}">
	                        <td><peps:text
	                            key="field.name"
	                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale, item.entityType)" /></td>
	                    </c:when>
	                    <c:otherwise>
	                        <td><peps:text
	                            key="field.name"
	                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
	                    </c:otherwise>
                    </c:choose> --%>
                    
                    <td><peps:text
                        key="current.value"
                        ognl="modDifferences[${index}].difference.oldValueShortString" /></td>
                    <td><peps:text
                        key="modified.value"
                        ognl="modDifferences[${index}].difference.newValueShortString" /></td>
                    <td><peps:dataField
                        key="modification.reason"
                        id="modification.reason_${index}"
                        ognl="modDifferences[${index}].modificationReason"
                        labelPosition="none" rows="2" cols="10"  /></td>
                    <td align="center"><peps:dataField
                        key="accept.change"
                        id="accept.change_${index}"
                        ognl="modDifferences[${index}].acceptChange"
                        labelPosition="none"
                        cssClass="checkbox2 center" /></td>
                    <td align="center"><peps:dataField
                        key="requires.second.approval"
                        id="requires.second.approval_${index}"
                        ognl="fieldAuthorization.isRequiresSecondApproval(modDifferences[${index}].difference.fieldKey)"
                        labelPosition="none"
                        cssClass="checkbox2 center"
                        disabled="true" /></td>
                    </tr>
                <%--</c:forEach>--%>
               </s:iterator> 
            </tbody>
        </table>
        </fieldset>
    </s:if>
    <%-- </c:when></c:choose> --%>
    <%-- <c:otherwise> --%>
    <s:else>
        <p>No Data Fields were modified!</p>
    </s:else> 
    <%--</c:otherwise> --%>  
    <div>
    <div class="fourColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></div>
    <div class="threeColumn">
    
    <s:if test="%{tableSelections != null && tableSelections.size() > 0}">
    <%-- <c:if test="%{tableSelections != null && tableSelections.size() > 0}"> --%>
        <peps:submit
            key="button.accept.changeAll"
            event="reviewAll" />
    </s:if>
    <%-- </c:if> --%>
    </div>
    <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" >
    <%-- <c:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" > --%>
        <div class="fourColumn">
        <s:if test="%{tableSelections != null && tableSelections.size() > 0}">
        <%-- <c:choose> --%>
        <%-- <c:when test="%{tableSelections != null && tableSelections.size() > 0}"> --%>
            <peps:submit
                key="button.accept.changes2"
                event="commitModifications" />
        </s:if>
        <%-- </c:when></c:choose> --%>
        <s:else>
        <%-- <c:otherwise> --%>
        <peps:submit
            key="button.accept.changes"
            event="commitModifications" />
        </s:else>
        <%-- </c:otherwise> --%>
        </div>
    </s:if>
    <%-- </c:if> --%>
    </div>
    </div>
</peps:form>