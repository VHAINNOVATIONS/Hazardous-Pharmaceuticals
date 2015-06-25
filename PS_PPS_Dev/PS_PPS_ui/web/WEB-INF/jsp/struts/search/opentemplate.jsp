<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>    
    
<%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
    boolean notReadOnly = userContext != null && userContext.getUser().isEitherManager();
    if (notReadOnly) {
        %>
        

        
<s:if test="%{items != null}">



    <s:if test="%{searchMode == null}">

        <div style="text-align: center;"><s:if test="%{searchTemplate.searchCriteria.entityType.orderableItem}">




            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.orderable.item.template')}" />
                
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${linkText}"
                flowId="manageTemplate.struts"
                itemType="ORDERABLE_ITEM"
                templateMethod="openBlankTemplate" /></span>

        </s:if><s:elseif test="%{searchTemplate.searchCriteria.entityType.product}">
        
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.product.template')}" />
                        
                
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${linkText}"
                flowId="manageTemplate.struts"
                itemType="PRODUCT"
                templateMethod="openBlankTemplate" /></span>
                
        </s:elseif>       
        
        <s:elseif test="%{searchTemplate.searchCriteria.entityType.ndc}">
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.ndc.template')}" />
                       
                
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${linkText}"
                flowId="manageTemplate.struts"
                itemType="NDC"
                templateMethod="openBlankTemplate" /></span>
                
        </s:elseif><s:else>
        
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.template')}" />
                
            <s:set
                scope="page"
                name="itemType"
                value="%{searchTemplate.searchCriteria.entityType}" />              
                
            <span style="padding: 0 10px 0 10px;"><peps:redirectFlowLink
                text="${linkText}"
                flowId="manageTemplate.struts"
                itemType="${itemType}"
                templateMethod="openBlankTemplate" /></span>

        </s:else></div>

    </s:if>

    <s:else>
 
        <div style="text-align: center;"><s:if test="%{searchTemplate.searchCriteria.entityType.orderableItem}">
        
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.orderable.item.template')}" />                     
                
            <span style="padding: 0 10px 0 10px;"><peps:resumeFlowLink
                text="${linkText}"
                eventId="blankTemplate"
                itemType="ORDERABLE_ITEM" /></span>
                
        </s:if><s:elseif test="%{searchTemplate.searchCriteria.entityType.product}">
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.product.template')}" />        
                
            <span style="padding: 0 10px 0 10px;"><peps:resumeFlowLink
                text="${linkText}"
                eventId="blankTemplate"
                itemType="PRODUCT" /></span>
                
        </s:elseif><s:elseif test="%{searchTemplate.searchCriteria.entityType.ndc}">
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.ndc.template')}" />
                                
            <span style="padding: 0 10px 0 10px;"><peps:resumeFlowLink
                text="${linkText}"
                eventId="blankTemplate"
                itemType="NDC" /></span>
                
        </s:elseif><s:else>
            <s:set
                scope="page"
                name="linkText"
                value="%{getText('button.open.blank.template')}" />			              
                
            <span style="padding: 0 10px 0 10px;"><peps:resumeFlowLink
                text="${linkText}"
                eventId="blankTemplate"
                itemType="${itemType}" /></span>



        </s:else></div>

        
    </s:else>

</s:if>

<% 
}   
%>

