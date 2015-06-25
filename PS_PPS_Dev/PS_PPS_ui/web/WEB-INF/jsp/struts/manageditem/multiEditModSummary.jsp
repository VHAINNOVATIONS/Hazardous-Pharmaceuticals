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

<peps:form id="modificationSummary">
    
    <s:iterator
        value="itemModDifferences"
        status="itemStatus">
     <%--<c:forEach
        items="#itemModDifferences"
        varStatus="itemStatus"> --%>
		<%--
        <s:set
            name="itemIndex"
            value="#itemStatus.index"
            scope="page" />
        --%>
		<c:set var="itemIndex" scope="page">
   			<c:out value="#itemStatus.index"/>
		</c:set>
            
        <peps:model ognlPrefix="itemModDifferences[${itemIndex}].item">
            
            <s:set
                name="itemTypeKey"
                value="itemModDifferences[#itemStatus.index].item.entityType.toFieldKey()"
                scope="page" />
            <%--<c:set var="itemTypeKey" scope="page">
   				<c:out value="itemModDifferences[#itemStatus.index].item.entityType.toFieldKey()"/>
			</c:set>--%>
            
            <h3>
            <div class="columnpadded">Modification Summary for&nbsp;<peps:localizeName key="${itemTypeKey}" />:&nbsp;</div>
            <div class="columnpadded">
            	<%--
            	<s:if test="%{itemModDifferences[#itemStatus.index].item.entityType.product}">
                	<peps:text
                    	key="product.long.name"
                    	label="false" />
            	</s:if>
            	<s:elseif test="%{itemModDifferences[#itemStatus.index].item.entityType.ndc}">
                <peps:text
                    key="ndc"
                    label="false" />
            	</s:elseif>
            	<s:elseif test="%{itemModDifferences[#itemStatus.index].item.entityType.orderableItem}">
                	<peps:text
                    	key="oi.name"
                    	label="false" />
            	</s:elseif>
            	<s:else>
                	<peps:text
                    	key="value"
                    	label="true" />
            	</s:else>
            	--%>
            	<c:choose>
	            	<s:when test="${itemModDifferences[#itemStatus.index].item.entityType.product}">
                	<peps:text
                    	key="product.long.name"
                    	label="false" />
	            	</s:when>
	            	<c:when test="${itemModDifferences[#itemStatus.index].item.entityType.ndc}">
	                	<peps:text
	                    	key="ndc"
	                    	label="false" />
	            	</c:when>
	            	<c:when test="${itemModDifferences[#itemStatus.index].item.entityType.orderableItem}">
	                	<peps:text
	                    	key="oi.name"
	                    	label="false" />
	            	</c:when>
            	</c:choose>
            	<c:otherwise>
            		<peps:text
                    	key="value"
                    	label="true" />
            	</c:otherwise>
	        </div>
            </h3>
        </peps:model>
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
            	<%-- <s:iterator
                    value="itemModDifferences[#itemStatus.index].differences"
                    status="differenceStatus">
                    <c:set var="differenceIndex" scope="page">
	                    	<c:out value="${#differenceStatus.index}"/>
	               </c:set>
	              
	                <s:set
                        name="differenceIndex"
                        value="#differenceStatus.index"
                        scope="page" />
                    <s:if test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        <tr class="nochange">
                    </s:if>
                    <s:elseif test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        <tr class="noteditable">
                    </s:elseif>
                    <s:elseif test="#differenceStatus.odd">
                        <tr class="odd">
                    </s:elseif>
                    <s:else>
                        <tr class="even">
                    </s:else>
                    <td><peps:text
                        key="field.name"
                        ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.fieldKey.getLocalizedName(locale)" /></td>
                    <td><peps:text
                        key="current.value"
                        ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.oldValueShortString" /></td>

                    <s:if test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        <td align=center>-</td>
                    </s:if>
                    <s:elseif test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        <td align=center>-</td>
                    </s:elseif>
                    <s:else>
                        <td><peps:text
                            key="modified.value"
                            ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.newValueShortString" /></td>
                    </s:else>

                    <s:if test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        <td>No Change Required</td>
                    </s:if>
                    <s:elseif test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        <td>Field Locked</td>
                    </s:elseif>
                    <s:else>
                        <td><peps:dataField
                            key="modification.reason"
                            ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].modificationReason"
                            labelPosition="none" rows="2" cols="10" /></td>
                    </s:else>

                    <s:if test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        <td align="center"></td>
                    </s:if>
                    <s:elseif test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        <td align="center"></td>
                    </s:elseif>
                    <s:else>
                        <td align="center"><peps:dataField
                            key="accept.change"
                            ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].acceptChange"
                            labelPosition="none"
                            cssClass="center" /></td>
                    </s:else>
                    
                    </tr>
                 </s:iterator>--%>
                 
                 <c:forEach
                   	items="itemModDifferences[#itemStatus.index].differences"
                   	varStatus="differenceStatus">
                    <c:set var"differenceIndex" scope="page">
                    	<c:out value="#differenceStatus.index"/>
                    </c:set>
                    <c:choose>
                        <c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
	                        <tr class="nochange">
	                    </c:when>
	                    <c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
	                        <tr class="noteditable">
	                    </c:when>
	                    <c:when test="#differenceStatus.odd">
	                        <tr class="odd">
	                    </c:when>
                    </c:choose>
                    <c:otherwise>
                    	<tr class="even">                    
                    </c:otherwise>
                    <td><peps:text
                        key="field.name"
                        ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.fieldKey.getLocalizedName(locale)" /></td>
                    <td><peps:text
                        key="current.value"
                        ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.oldValueShortString" /></td>

                    <c:choose>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        	<td align=center>-</td>
                    	</c:when>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        	<td align=center>-</td>
                    	</c:when>
                    </c:choose>
                    <c:otherwise>
                        <td><peps:text
                            key="modified.value"
                            ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].difference.newValueShortString" /></td>
                    </c:otherwise>

                    <c:choose>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        	<td>No Change Required</td>
                    	</c:when>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        	<td>Field Locked</td>
                    	</c:when>
                    </c:choose>
                    <c:otherwise>
                        <td><peps:dataField
                            key="modification.reason"
                            ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].modificationReason"
                            labelPosition="none" rows="2" cols="10" /></td>
                    </c:otherwise>

                    <c:choose>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].changeable">
                        	<td align="center"></td>
                    	</c:when>
                    	<c:when test="!itemModDifferences[#itemStatus.index].differences[#differenceStatus.index].editable">
                        	<td align="center"></td>
                        </c:when>
                   	</c:choose>
                   	<c:otherwise>
                       	<td align="center"><peps:dataField
                           	key="accept.change"
                           	ognl="itemModDifferences[${itemIndex}].differences[${differenceIndex}].acceptChange"
                           	labelPosition="none"
                           	cssClass="center" /></td>
                   	</c:otherwise>
                   	</tr>
            	</c:forEach>
                 
            </tbody>
        </table>
    <%-- </s:iterator>  --%>
   </c:forEach>
    <div><span class="twoColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></span><span class="twoColumn"><peps:submit
        key="button.accept.changes"
        event="commitModifications" /></span></div>
</peps:form>