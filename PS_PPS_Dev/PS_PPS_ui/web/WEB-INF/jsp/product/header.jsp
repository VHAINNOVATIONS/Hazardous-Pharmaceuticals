<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>    
<%@ taglib  
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
    prefix="spring"
    uri="http://www.springframework.org/tags"%>    

<div class="panel">
    <div class="twoColumnwide">
        <b><font size="3"><peps:text
        key="product.long.name"
        label="true" cssClass="ndceditheader" item="${item}"/></font></b>
        <peps:text
        key="primary.drug.class"
        label="true" abbreviation="true" cssClass="ndceditheader" item="${item.primaryDrugClass.value}"
        inputType="label"/>
        <peps:text
        key="categories"
        label="true" cssClass="ndceditheader" item="${item}" abbreviation="false" />
    </div>
    <div class="threeColumn2left">
        <c:set
        scope="page"
        var="oiId"
        value="${item.orderableItem.id}" />
        <spring:message var="clickToEditField" scope="page" code="click.to.edit.field" />
        <c:set var="orderableItemCTEF" scope="page" value="<%=gov.va.med.pharmacy.peps.common.vo.EntityType.ORDERABLE_ITEM%>" /> 
        <peps:link
            item="${item}"
            id="oiParentLink"
            event="edit"
            key="oi.name"
            label="true"
            abbreviation="true"
            tooltip="${clickToEditField} ${orderableItemCTEF}"
            cssClass="editheader2"
            itemId="${oiId}"
            itemType="ORDERABLE_ITEM" />
        <peps:text
        key="application.package.use"
        label="true" cssClass="editheader2" item="${item}" />
        <c:choose>
            <c:when test="${item.itemStatus.inactive}">
                <span class="columnnopadding">
                <b><font color="red"><peps:text
                    item="${item}"
                    key="item.status"
                    label="true" cssClass="editheader2" /></font></b></span> 
                 <span class="column">(<peps:text
                    item="${item}" 
			        key="inactivation.date"
			        label="false"/>)</span>    
            </c:when>
            <c:otherwise>
                <peps:text
                key="item.status"
                label="true" cssClass="editheader2" item="${item}" />
            </c:otherwise> 
        </c:choose>
        <peps:text
        key="request.item.status"
        label="true" cssClass="editheader2" item="${item}" />
    </div>

    <c:set
        scope="page"
        var="itemTypeString"
        value="<%=EntityType.PRODUCT.toFieldKey() %>" />
    <div class="panel">
    <center><b><font size="3">
     <c:if test="${mainRequest.requestType.addition}">
        <peps:label key="${itemTypeString}" /> Addition Request
    </c:if>

    <c:if test="${mainRequest.requestType.modification}">
        <peps:label key="${itemTypeString}" /> Modification Request
    </c:if>

    </font></b></center>


</div>
<div class="horizontalspacer"></div>