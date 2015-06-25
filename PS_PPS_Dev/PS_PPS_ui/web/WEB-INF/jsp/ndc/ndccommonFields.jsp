<%@ taglib 
    prefix="peps" 
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
    uri="http://tiles.apache.org/tags-tiles" 
    prefix="tiles"%>  
<%@ taglib 
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib 
    prefix="spring"
    uri="http://www.springframework.org/tags"%>

<peps:warnings id="warnings" warnings="${warnings}"/>
<br />

<peps:form id="NDCWizard2">
	<h2>&nbsp;&nbsp;Confirming New NDC Information</h2>
	<div class="panel">

        <peps:text item="${item}" key="ndc" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="upc.upn" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="trade.name" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="order.unit" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="package.type" cssClass="readonlynational" label="true"/>
        <div class="rowspacer"></div>
        <peps:text item="${item}" key="package.size" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="manufacturer" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="otc.rx.indicator" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="single.multi.source.ndc" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="product.number" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="scored" cssClass="readonlynational" label="true"/>
        <div class="rowspacer"></div>
        <peps:text item="${item}" key="color" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="shape" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="imprint" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="imprint2" cssClass="readonlynational" label="true"/>
        <peps:text item="${item}" key="inactivation.date" cssClass="readonlynational" label="true"/>
        <div class="rowspacer"></div>
        <peps:text item="${item}" key="protect.from.light" cssClass="readonlynational"  label="true"/>
        <peps:text item="${item}" key="refrigeration" cssClass="readonlynational" label="true"/>
        <div class="buttonspacer"></div>
        
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/returnToAdd.go" var="returnUrl"/>
            <peps:submit key="button.return" action="${returnUrl}"/>
        </span>
        <c:if test="${flowScope.multipleSelectItemBean eq null}">
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/submitNewItem.go" var="submitUrl"/>
            <peps:submit key="button.accept.changes" action="${submitUrl}"/>
        </span>
        </c:if>
        <c:if test="${flowScope.multipleSelectItemBean eq null}">
        <span class="column">
            <c:url value="/${fn:toLowerCase(item.entityType)}/addMoreDetails.go" var="actionUrl"/>
            <peps:submit key="button.add.more.details" action="${actionUrl}"/>
        </span>
        </c:if>
        <c:if test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) > flowScope.multipleSelectItemBean.currentIndex + 1}">
	    	<span class="column">
	        	<c:url value="/${fn:toLowerCase(item.entityType)}/saveNDCTemplate.go" var="nextUrl"/>
				<peps:submit key="button.next.ndc" action="${nextUrl}" />
			</span>
		</c:if>
		<c:if test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) == flowScope.multipleSelectItemBean.currentIndex + 1}">
	    	<span class="column">
	        	<c:url value="/${fn:toLowerCase(item.entityType)}/submitNewItem.go" var="submitUrl"/>
				<peps:submit key="button.final.ndc" action="${submitUrl}" />
			</span>
		</c:if>
	</div>
</peps:form>