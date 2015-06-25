<%@page import="java.util.Collection"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo"%>
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
<peps:warnings id="warnings" warnings="${warnings}"/>
<br />
<form id="modificationSummary"
    method="post" action="" 
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
     <c:forEach
        var="modDiff"
        items="${itemModDifferences}"
        varStatus="itemStatus">
		
		<c:set var="itemIndex" scope="page" value="${itemStatus.index}"/>
   		    
            <c:set
                var="itemTypeKey"
                value="${modDiff.item.entityType.fieldKey}"
                scope="page" />
            <h3>
            <div class="columnpadded">Modification Summary for <peps:localizeName key="${itemTypeKey}" />: </div>
            <div class="columnpadded">
            	<c:choose>
	            	<c:when test="${modDiff.item.entityType.product}">
                	<peps:text
                	    item="${modDiff.item}"
                    	key="product.long.name"
                    	label="false" />
	            	</c:when>
	            	<c:when test="${modDiff.item.entityType.ndc}">
	                	<peps:text
	                	    item="${modDiff.item}"
	                    	key="ndc"
	                    	label="false" />
	            	</c:when>
	            	<c:when test="${modDiff.item.entityType.orderableItem}">
	                	<peps:text
	                	    item="${modDiff.item}"
	                    	key="oi.name"
	                    	label="false" />
	            	</c:when>
	                <c:otherwise>
	                    <peps:text
	                        item="${modDiff.item}"
	                        key="value"
	                        label="true" />
	                </c:otherwise>
            	</c:choose>
	        </div>
            </h3>        
        <br />
        <br />

        <table
            id="editableModTable"
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
                </tr>
            </thead>
            <tbody> 
                 <c:forEach
                   	var="diff"   
                   	items="${modDiff.differences}"
                   	varStatus="differenceStatus">
                   	<%
                    ModDifferenceVo diff = ((ModDifferenceVo)pageContext.getAttribute("diff"));
                    %>  
                    <c:set var="differenceIndex" scope="page" value="${differenceStatus.index}"/>
                    <c:choose>
                        <c:when test="${!diff.changeable}">
	                        <tr class="nochange">
	                    </c:when>
	                    <c:when test="${!diff.editable}">
	                        <tr class="noteditable">
	                    </c:when>
	                    <c:when test="${differenceIndex % 2 eq 0}">
	                        <tr class="odd">
	                    </c:when>
	                    <c:otherwise>
	                        <tr class="even">                    
	                    </c:otherwise>
                    </c:choose>
                    <td><peps:text
                        key="field.name"
                        item="<%=diff.getDifference().getFieldKey().getLocalizedName(request.getLocale()) %>" /></td>
                    <td><peps:text
                        key="current.value"
                        item="<%=diff.getDifference().getOldValueShortString() %>" /></td>

                    <c:choose>
                    	<c:when test="${!diff.changeable}">
                        	<td align=center>-</td>
                    	</c:when>
                    	<c:when test="${!diff.editable}">
                        	<td align=center>-</td>
                    	</c:when>                    	
	                    <c:otherwise>
	                        <td><peps:text
	                            key="modified.value"
	                            item="<%=diff.getDifference().getNewValueShortString() %>" /></td>
	                    </c:otherwise>
                    </c:choose>
                    <c:choose>
                    	<c:when test="${!diff.changeable}">
                        	<td>No Change Required</td>
                    	</c:when>
                    	<c:when test="${!diff.editable}">
                        	<td>Field Locked</td>
	                    </c:when>
	                    <c:otherwise>
	                        <td>
	                           <c:if test="${diff.difference.fieldKey.key eq 'item.status' and diff.difference.oldValue eq 'ACTIVE'}">
	                               <span class="required">*</span> 
	                           </c:if>
                             <textarea id="modification.reason_${modDiff.item.id}_${differenceIndex}" 
                                              class="textarea" 
                                              name="${modDiff.item.id}-${differenceIndex}-reason" 
                                              rows="2" 
                                              cols="10" >${diff.modificationReason}</textarea>
	                        </td>
	                    </c:otherwise>
                    </c:choose>
                    <c:choose>
                    	<c:when test="${!diff.changeable}">
                        	<td align="center"></td>
                    	</c:when>
                    	<c:when test="${!diff.editable}">
                        	<td align="center"></td>
                        </c:when>
	                    <c:otherwise>
	                        <td align="center">
	                           <%-- had to manually build the input here due to 3.0.5 of spring now working right with map values --%>
	                            <input 
	                               name="${modDiff.item.id}-${differenceIndex}-acceptChange" 
	                               type="checkbox"
	                               value="true"
	                               <c:if test="${diff.acceptChange}">checked="checked"</c:if>
	                               class="checkbox2 center" />
	                            <input 
	                               type="hidden" 
	                               name="${modDiff.item.id}-${differenceIndex}-acceptChange" 
	                               value="false" />
                            </td>
	                    </c:otherwise>
                   	</c:choose>
                   	</tr>
            	</c:forEach>
                 
            </tbody>
        </table>    
   </c:forEach>
    <div>
        <span class="twoColumn">
            <c:url value="/${fn:toLowerCase(itemModDifferences[0].item.entityType)}/${item.id}/cancelCommitMultiModifications.go" var="cancelUrl"/>
            <peps:submit key="button.cancel" action="${cancelUrl}" />
		</span>
        <span class="twoColumn">
            <c:url value="/${fn:toLowerCase(itemModDifferences[0].item.entityType)}/${item.id}/commitMultiModifications.go" var="actionUrl"/>
            <peps:submit key="button.accept.changes" action="${actionUrl}"/>
		</span>
	</div>
</form>