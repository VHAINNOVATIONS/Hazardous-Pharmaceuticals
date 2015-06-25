<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
    boolean notReadOnly = userContext != null && userContext.getUser().isEitherManager();
    if (notReadOnly) {
%>

<div style="text-align: center;"><s:set
    scope="page"
    name="itemId"
    value="item.id" /><s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toString()" /><s:set
    scope="page"
    name="itemTypeKey"
    value="itemType.toFieldKey()" /> 
    <s:if test="%{item.id != null}">
    <s:if test="%{itemType.hasLocalNationalConcept() && !itemType.isOrderableItem()}">
        <s:if test="%{environment.local}">
            <div>
            <s:set
                scope="page"
                name="openLocalItemTemplate"
                value="%{getText('button.open.local.' + #attr.itemTypeKey + '.template')}" />
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${openLocalItemTemplate}"
                flowId="manageTemplate.struts"
                itemId="${itemId}"
                itemType="${itemTypeString}"
                templateMethod="openLocalTemplate" /></span>
        </s:if>
            <s:set
                scope="page"
                name="openNationalItemTemplate"
                value="%{getText('button.open.national.' + #attr.itemTypeKey + '.template')}" />
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${openNationalItemTemplate}"
                flowId="manageTemplate.struts"
                itemId="${itemId}"
                itemType="${itemTypeString}" 
                templateMethod="openTemplate"/></span>
            </div>
    </s:if> 
    <s:if test="%{itemType.isOrderableItem()}">
        <s:if test="%{item.isLocal()}">
           	<s:set
                scope="page"
                name="openLocalItemTemplate"
                value="%{getText('button.open.orderable.item.template')}" />
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${openLocalItemTemplate}"
                flowId="manageTemplate.struts"
                itemId="${itemId}"
                itemType="${itemTypeString}" 
                templateMethod="openLocalTemplate"/></span>
        </s:if>
        <s:else>
            <s:set
                scope="page"
                name="openNationalItemTemplate"
                value="%{getText('button.open.orderable.item.template')}" />
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${openNationalItemTemplate}"
                flowId="manageTemplate.struts"
                itemId="${itemId}"
                itemType="${itemTypeString}" 
                templateMethod="openTemplate"/></span>
            </div>
        </s:else>
    </s:if>
    <s:else>
        <s:set
            scope="page"
            name="openAsTemplate"
            value="%{getText('button.open.as.' + #attr.itemTypeKey + '.template')}" />
        <span style="padding: 0 10px 0 10px;">
            <s:if test="!item.entityType.isDomainType()"><peps:redirectFlowLink
            text="${openAsTemplate}"
            flowId="manageTemplate.struts"
            itemId="${itemId}"
            itemType="${itemTypeString}"
            templateMethod="openTemplate" />
            </s:if>
        </span>
        <s:set
            scope="page"
            name="openBlankItemTemplate"
            value="%{getText('button.open.blank.' + #attr.itemTypeKey + '.template')}" />
        <s:set
            scope="page"
            name="openBlankTemplate"
            value="%{getText('button.open.blank.template')}" />
        <span style="padding: 0 10px 0 10px;">
        <s:if test="(item.entityType.hasParent() && item.parent != null ) ">

        </s:if>
        <s:else>
            <s:if test="item.entityType.isDomainType()">
            <peps:redirectFlowLink
                text="${openBlankTemplate}"
                flowId="manageTemplate.struts"
                itemType="${itemTypeString}"
                templateMethod="openBlankTemplate"/></s:if>
        </s:else>
        </span>
    </s:else>
    </s:if> 
    <s:if test="%{itemType.hasChild() && !itemType.isProduct() && !itemType.isOrderableItem() }">
    <s:set
        scope="page"
        name="child"
        value="itemType.child.toFieldKey()" />
    <s:set
        scope="page"
        name="openBlankChildTemplate"
        value="%{getText('button.open.blank.' + #attr.child + '.template')}" />
    <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
        text="${openBlankChildTemplate}"
        flowId="manageTemplate.struts"
        itemId="${itemId}"
        itemType="${itemTypeString}" 
        templateMethod="openBlankChildTemplate"/></span>
</s:if>
</div>
<%
}
%>