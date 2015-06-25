<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>  
	
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%> 


<%-- <c:if test="${item ne null and item2 ne null}"> --%>  
<s:if test="{item != null && item2 != null}">      
	<peps:form id="moveNDCs" 
	    onsubmit="selectAllOptions(document.getElementById('itemNDCs'));selectAllOptions(document.getElementById('item2NDCs'));" >
		
		<fieldset>
			<legend>Move NDCs to the Correct Product</legend>
			<s:set
			    name="firstProductName"
			    value="getText(item.getVaProductName())"
			    scope="page" />
			<s:set
			    name="secondProductName"
			    value="getText(item2.getVaProductName())"
			    scope="page" />
			<%-- 
			<c:set
			    var="firstProductName"
			    value="getText(item.getVaProductName())"
			    scope="page" />
			<c:set
			    var="secondProductName"
			    value="getText(item2.getVaProductName())"
			    scope="page" /> --%>
			    
			<s:optiontransferselect
			     name="item.ndcs"
			     id="itemNDCs"
			     cssStyle="width: 115px"
			     leftTitle="%{#attr.firstProductName}"
			     rightTitle="%{#attr.secondProductName}"
			     size="20"
			     list="item.ndcs"
			     listValue="ndc"
			     listKey="id"
			     multiple="true"
			     emptyOption="false"
			     doubleList="item2.ndcs"
			     doubleListValue="ndc"
			     doubleListKey="id"
			     doubleName="item2.ndcs"
			     doubleSize="20"
			     doubleId="item2NDCs"
			     doubleEmptyOption="false"
			     doubleMultiple="true"
			     doubleCssStyle="width: 115px"
			     allowUpDownOnLeft="false"
			     allowUpDownOnRight="false"
			     allowAddToLeft="true"
			     allowAddToRight="true"
			     allowSelectAll="false"
			     buttonCssClass="optionTransferSelectButton"
			 />
			 <%--TODO FIND EQUIV OF OPTION SELECT TRANSFER --%>
			 
		</fieldset>
	 </div>
	
		<div style="margin-top: 5px">
			<s:if test="item == null || item2 == null">
				<peps:submit key="button.submit" event="updateChildren" disabled="true" />
			</s:if>
			<s:else>
				<peps:submit key="button.submit" event="updateChildren" disabled="false" />
			</s:else>
			<%--
			<c:choose>
				<c:when test="item == null || item2 == null">
					<peps:submit key="button.submit" event="updateChildren" disabled="true" />
				</c:when>
				<c:otherwise>
					<peps:submit key="button.submit" event="updateChildren" disabled="false" />
				</c:otherwise> 
			</c:choose>--%>
		
		</div>
	</peps:form>
<%-- </c:if> --%>
</s:if>