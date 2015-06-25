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
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
	
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>      

<peps:form id="moveProducts"
    onsubmit="selectAllOptions(document.getElementById('itemProducts'));selectAllOptions(document.getElementById('item2Products'));" >
<div class="twoColumn">
<fieldset>
<legend>Move Products to the Correct Orderable Items</legend>
<s:set
    name="firstOIName"
    value="getText(item.getOiName())"
    scope="page" />
<s:set
    name="secondOIName"
    value="getText(item2.getOiName())"
    scope="page" />
<s:optiontransferselect
     name="item.products"
     id="itemProducts"
     leftTitle="%{#attr.firstOIName}"
     rightTitle="%{#attr.secondOIName}"
     cssStyle="width: 170px"
     size="20"
     list="item.products"
     listValue="vaProductName"
     listKey="id"
     multiple="true"
     emptyOption="false"
     doubleList="item2.products"
     doubleListValue="vaProductName"
     doubleListKey="id"
     doubleName="item2.products"
     doubleId="item2Products"
     doubleEmptyOption="false"
     doubleMultiple="true"
     doubleCssStyle="width: 170px"
     doubleSize="20"
     allowUpDownOnLeft="false"
     allowUpDownOnRight="false"
     allowAddToLeft="true"
     allowAddToRight="true"
     allowSelectAll="false"
     buttonCssClass="optionTransferSelectButton"
 />
 </fieldset>
 </div>
<div class="column" style="margin-top: 200px">
<c:choose>
	<c:when test="item == null || item2 == null">
	<peps:submit key="button.submit" event="updateChildren" disabled="true" />
	</c:when>
	<c:otherwise>
	<peps:submit key="button.submit" event="updateChildren" disabled="false" />
	</c:otherwise>
</c:choose>
<%-- 
<s:if test="item == null || item2 == null">
<peps:submit key="button.submit" event="updateChildren" disabled="true" />
</s:if>
<s:else>
<peps:submit key="button.submit" event="updateChildren" disabled="false" />
</s:else>
--%>
</peps:form>