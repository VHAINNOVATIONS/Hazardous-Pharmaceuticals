<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.OrderableItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.RequestVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
	
<%
      UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
      boolean canEdit = userContext != null && userContext.getUser().isEitherManager();
      
      boolean canApprove = userContext != null && userContext.getUser().canApprove();    
      ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
      EntityType itemType = item.getEntityType();
      RequestVo mainRequest = (RequestVo)request.getAttribute("mainRequest");
      FieldAuthorizationVo fieldAuthorization = (FieldAuthorizationVo)request.getAttribute("fieldAuthorization");
      boolean canActivate = itemType.isDomainType() && FieldKey.INACTIVATION_DATE.getEntityTypes().contains(itemType) && !fieldAuthorization.isDisabled(FieldKey.ITEM_STATUS);
     
    
%>

<div class="panel">
	<c:choose>
		<c:when test="${item.entityType.domainType}">
			<div class="floatLeft">
			<c:set var="editEvent" value="manageDomainRequest.go"/>
		</c:when><c:otherwise>
		    <div class="floatRight">
		    <c:set var="editEvent" value="manageRequest.go"/>
		</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${!item.requestItemStatus.rejected}">
	<%
	if (canEdit) {
	%>
	<c:choose>
	<c:when test="${mainRequest == null}">
	    <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/editCancel.go" var="cancelUrl"/>
	</c:when>
	<c:otherwise>
		<c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/requestCancel.go" var="cancelUrl"/>        
    </c:otherwise>
    </c:choose>
	<c:choose>
		<c:when test="${item.entityType.pharmacySystem}">
		    <span class="column"><peps:submit key="button.undo" action="${cancelUrl}" /></span>
		</c:when>
		<c:otherwise>
		    <span class="column">
                <peps:submit key="button.cancel" action="${cancelUrl}" />
            </span>
		</c:otherwise>
	</c:choose>	
	<c:if test="${mainRequest != null && mainRequest.requestType != null && mainRequest.requestType.problemReport}">
	    <span class="column">
	    <c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/rejectProblemReport.go" var="rejectProblemUrl"/>
	    <peps:submit key="button.rejectProblemReport" action="${rejectProblemUrl}"/></span>
	</c:if>  
	
	<c:if test="${item.entityType.entityType}">
         <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/autoPartialSaveLogout.go" var="autoPartialUrl"/>
            <span class="column"><peps:submit
            key="button.autosave" action="${autoPartialUrl}" cssStyle="display: none; visibility: hidden" /></span>
	     <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/startPartialSave.go" var="partialUrl"/>
            <span class="column"><peps:submit
	        key="button.bookmark" action="${partialUrl}"/></span>
	</c:if>
			
	
	<c:if test="${flowScope.multipleSelectItemBean ne null and flowScope.multipleSelectItemBean.currentIndex ne 0}">
		<c:choose>
		<c:when test="${mainRequest == null}">
			<c:url value="/${fn:toLowerCase(item.entityType)}/${flowScope.multipleSelectItemBean.itemIds[flowScope.multipleSelectItemBean.currentIndex - 1]}/edit.go" var="previousUrl"/> 
		</c:when>
		<c:otherwise>
			<c:url value="/${fn:toLowerCase(item.entityType)}/${flowScope.multipleSelectItemBean.itemIds[flowScope.multipleSelectItemBean.currentIndex - 1]}/request/${flowScope.multipleSelectItemBean.requestIds[flowScope.multipleSelectItemBean.currentIndex - 1]}/${editEvent}" var="previousUrl"/>
		</c:otherwise>
		</c:choose>
        <span class="column">
	       <peps:submit key="button.previousItem" onclick="window.location.href='${previousUrl}'; return false;" />
	    </span>
	</c:if>	
	
	<c:if test="${flowScope.multipleSelectItemBean ne null and fn:length(flowScope.multipleSelectItemBean.itemIds) > flowScope.multipleSelectItemBean.currentIndex + 1}">
	    <c:choose>
		<c:when test="${mainRequest == null}">
			<c:url value="/${fn:toLowerCase(item.entityType)}/${flowScope.multipleSelectItemBean.itemIds[flowScope.multipleSelectItemBean.currentIndex + 1]}/edit.go" var="nextUrl"/> 
		</c:when>
		<c:otherwise>
			<c:url value="/${fn:toLowerCase(item.entityType)}/${flowScope.multipleSelectItemBean.itemIds[flowScope.multipleSelectItemBean.currentIndex + 1]}/request/${flowScope.multipleSelectItemBean.requestIds[flowScope.multipleSelectItemBean.currentIndex + 1]}/${editEvent}" var="nextUrl"/>
		</c:otherwise>
		</c:choose>	    
	    <span class="column">
	       <peps:submit key="button.nextItem" onclick="window.location.href='${nextUrl}'; return false;" /></span>
	</c:if>
	
	<c:if test="<%=canActivate%>">
	<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/activateField.go" var="activateUrl"/>
    <input type="hidden" id="activate" name="activate" value="${!item.itemStatus.active}"/>	
    <span class="column">
	    <c:choose>
	     <c:when test="${item.itemStatus.active}">	     	     
	         <peps:submit
	             key="button.inactivate" action="${activateUrl}"/>
	     </c:when>
	     <c:otherwise>
	         <peps:submit
	             key="button.activate" action="${activateUrl}"/>
	     </c:otherwise>
	    </c:choose>
    </span>
	</c:if>
	
	<c:if test="${item.entityType.deleteable}">
		<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/deleteDomain.go" var="deleteUrl"/>
		<span class="column"> 
			<peps:submit key="button.delete" action="${deleteUrl}" />
		</span>
	</c:if>	
	
	<c:if test="<%=itemType.isProduct() || itemType.isNdc() || (itemType.isOrderableItem() && ((gov.va.med.pharmacy.peps.common.vo.OrderableItemVo)item).isLocal())%>">
	    <span class="column"> 
	    <c:if test="${item.entityType.entityType}">	    
	        <c:choose>
		        <c:when test="${item.entityType.orderableItem}">
		            <spring:message
		                var="parentButtonKey" 
		                code="button.change.loi.parent"
		                scope="page" />
		        </c:when>
		        <c:when test="${item.entityType.product}">
		            <spring:message
		                var="parentButtonKey"
		                code="button.change.product.parent"
		                scope="page" />
		        </c:when>
		        <c:when test="${item.entityType.ndc}">
		            <spring:message
		                var="parentButtonKey"
		                code="button.change.ndc.parent"
		                scope="page" />
		        </c:when>
		        <c:otherwise>
		            <spring:message
		                var="parentButtonKey"
		                code="button.change.parent"
		                scope="page" />
		        </c:otherwise>		        
	    	</c:choose>
	    	<c:url value="/changeParent.go?entityType=${fn:toLowerCase(item.parent.entityType)}" var="actionUrl"/>
	    	<peps:submit key="${parentButtonKey}" action="${actionUrl}" />
         </c:if>
	    </span>
	</c:if>
	 <c:if test="${mainRequest == null}">
	    <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/submitModifications.go?tab=${tab}" var="actionUrl"/>
        <span class="column">
            <peps:submit key="button.submit" action="${actionUrl}" />
        </span>
	</c:if> 

	
<%
    }
%>
	</c:when>
	<c:otherwise>
		<c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/editCancel.go" var="cancelUrl"/>
		<span class="column">
                <peps:submit key="button.cancel" action="${cancelUrl}" />
        </span>
        <c:url value="/${fn:toLowerCase(item.entityType)}/${item.id}/rescindRejection.go" var="rescindRejectionUrl"/>
		<span class="column">
                <peps:submit key="button.rescind.rejection" action="${rescindRejectionUrl}" />
        </span>
	</c:otherwise>
	</c:choose>
<%
    if (canApprove) {
%> 
<c:if test="${mainRequest != null}" >
	<c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/approveRequest.go" var="approveUrl"/> 
    <c:if test="${mainRequest.requestType.addition}">	    
    	<span class="column">         		
    		<c:choose>	    	
		    	<c:when test="${mainRequestSize > 0}">	    		
	            	<peps:submit key="button.accept.actions" action="${approveUrl}"/>	        	
		        </c:when>
	        	<c:otherwise>
	        		<c:choose>
	        			<c:when test="${item.entityType.entityType}">
		        			<peps:submit key="button.approve.${item.entityType}" action="${approveUrl}"/>		       		
	       				</c:when>
	       				<c:otherwise>		       		
		            		<peps:submit key="button.approve" action="${approveUrl}"/>		       		
	       				</c:otherwise>
	       			</c:choose>
	        	</c:otherwise>
        	</c:choose>
        </span>
        <c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/rejectRequest.go" var="rejectUrl"/>	        
        <span class="column">
			<c:choose>
	        	<c:when test="${item.entityType.entityType}">
		            <peps:submit key="button.reject.${item.entityType}" action="${rejectUrl}"/>
	        	</c:when>
		        <c:otherwise>
	        	    <peps:submit key="button.reject" action="${rejectUrl}"/>
	        	</c:otherwise>
        	</c:choose>
        </span>     
    </c:if>
    <span class="column">
    	<c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/markRequestUnderReview.go" var="underReviewUrl"/>
       	<peps:submit key="button.under.review" action="${underReviewUrl}"/>
    </span>
    <c:if test="${!mainRequest.requestType.addition}">    	
       	<span class="column">
           	<peps:submit key="button.accept.actions" action="${approveUrl}" />
        </span>		    
    </c:if>
</c:if>		
<%
}
%></div>