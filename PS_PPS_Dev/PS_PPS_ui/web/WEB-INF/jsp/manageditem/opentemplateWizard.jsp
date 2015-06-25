<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.OrderableItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="pepsOld" tagdir="/WEB-INF/tags"%>
<%
    UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
    boolean notReadOnly = userContext != null && userContext.getUser().isEitherManager();
    ManagedItemVo item = null;
    EntityType itemType = null;
    String itemId = (String)request.getAttribute("itemId");
    if(request.getAttribute("item") != null) {
        item = (ManagedItemVo) request.getAttribute("item");
        itemType = item.getEntityType();
    } else /*if(request.getAttribute("itemType") != null)*/ {
        itemType = (EntityType)request.getAttribute("itemType");
    }
    
    pageContext.setAttribute("itemTypeString", itemType.toString());
    pageContext.setAttribute("itemTypeKey", itemType.toFieldKey());

    if (notReadOnly) {
%>
<div style="text-align: center;">
    <c:if test="${item.id != null}">
        <c:if test="<%=itemType.hasLocalNationalConcept() && !itemType.isOrderableItem()%>">
            <div>
            <c:if test="${environment.local}">
                <spring:message scope="page" var="openLocalItemTemplate"
                    code="button.open.local.${itemTypeKey}.template" /> 
                <span style="padding: 0 10px 0 10px;"><peps:link
                        item="${item}"
                        text="${openLocalItemTemplate}" 
                        itemId="${item.id}"
                        itemType="${itemTypeString}"
                        event="openLocalTemplate/add"/></span> 
             </c:if>
            <spring:message scope="page" var="openNationalItemTemplate"
                code="button.open.national.${itemTypeKey}.template" />
            <span style="padding: 0 10px 0 10px;"><peps:link
                    item="${item}"
                    text="${openNationalItemTemplate}" 
                    itemId="${item.id}"
                    itemType="${itemTypeString}"                    
                    event="${openTemplate}/add" /></span>
            </div>
        </c:if> 
    </c:if>
    <c:choose>
        <c:when test="<%=itemType.isOrderableItem()%>">
            <div>
            <c:choose>
                <c:when test="<%=((gov.va.med.pharmacy.peps.common.vo.OrderableItemVo)item).isLocal()%>">
                <spring:message scope="page" var="openLocalOrderableItemTemplate"
                    code="button.open.orderable.item.template" />
                <span style="padding: 0 10px 0 10px;"> 
                  <peps:link
                        item="${item}"
                        text="${openLocalOrderableItemTemplate}" 
                        itemId="${itemId}"
                        itemType="${itemTypeString}"
                        event="openLocalTemplate/add" />
                </span>
               </c:when>
               <c:otherwise>
                  <spring:message scope="page" var="openNationalOrderableItemTemplate"
                    code="button.open.orderable.item.template" />
                    <span style="padding: 0 10px 0 10px;"> 
                      <peps:link
                            item="${item}"
                            text="${openNationalOrderableItemTemplate}" 
                            itemId="${item.id}"
                        	itemType="${itemTypeString}"                        	
                            event="openTemplate/add"/> 
                    </span>
               </c:otherwise>
            </c:choose>
            </div>
        </c:when>
        <c:otherwise>
            <span style="padding: 0 10px 0 10px;"> 
                <c:if test="<%=!itemType.isDomainType() %>">
                    <spring:message scope="page" var="openAsTemplate"
                        code="button.open.as.${itemTypeKey}.template" />
                    <peps:link 
                        item="${item}"
                        text="${openAsTemplate}"
                        itemId="${item.id}"
                        itemType="${itemTypeString}"
                        event="openTemplate/add" />
               </c:if> 
            </span>
            <span style="padding: 0 10px 0 10px;"> 
                <c:choose>
                    <c:when test="<%=itemType.hasParent() && (item != null && item.getParent() != null)%>">
                    
                    </c:when>
                    <c:otherwise>
                        <c:if test="<%=itemType.isDomainType() %>">
                            <%-- <spring:message scope="page" var="openBlankItemTemplate"
                                code="button.open.blank.${itemTypeKey}.template" /> --%>
                            <spring:message scope="page" var="openBlankTemplate"
                                code="button.open.blank.template" />
                            <peps:link 
                                item="${item}"
                                text="${openBlankTemplate}"
        		                itemType="${itemTypeString}"
                                itemId="${item.id}"		                        
                                event="openBlankTemplate/add"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </span>
        </c:otherwise>
    </c:choose>
    <c:if test="<%=itemType.hasChild() && !itemType.isProduct() && !itemType.isOrderableItem()%>">
        <c:set scope="page" var="child" value="<%=itemType.getChild().toFieldKey()%>" />
        <spring:message scope="page" var="openBlankChildTemplate"
            code="button.open.blank.${child}.template" />
        <span style="padding: 0 10px 0 10px;">
           <peps:link
                item="${item}"
                text="${openBlankChildTemplate}" 
                itemType="${itemTypeString}"
                itemId="${item.id}"
                event="openBlankChildTemplate/add"/> 
        </span>
    </c:if>
</div>
<%
    }
%>