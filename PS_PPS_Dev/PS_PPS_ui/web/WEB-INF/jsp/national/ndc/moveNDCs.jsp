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
	
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.OgnlUtility"%> 


<c:if test="${item ne null and item2 ne null}">  
      
	<peps:form id="moveNDCs" 
	    onsubmit="selectAllOptions(document.getElementById('itemNDCs'));selectAllOptions(document.getElementById('item2NDCs'));" >
		
		<fieldset>
			<legend>Move NDCs to the Correct Product</legend>
			
			<c:set
			    var="firstProductName"
			    value="${item.vaProductName}"
			    scope="page" />
			<c:set
			    var="secondProductName"
			    value="${item2.vaProductName})"
			    scope="page" />
			    
<!-- 			<s:optiontransferselect
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
			 /> -->
			 <%--TODO FIND EQUIV OF OPTION SELECT TRANSFER --%>			 
		</fieldset>
	
		<div style="margin-top: 5px">
			
			<c:choose>
				<c:when test="item == null || item2 == null">
					<peps:submit key="button.submit" disabled="true" />
				</c:when>
				<c:otherwise>
					<peps:submit key="button.submit" disabled="false" />
				</c:otherwise> 
			</c:choose>
		
		</div>
	</peps:form>
</c:if>
