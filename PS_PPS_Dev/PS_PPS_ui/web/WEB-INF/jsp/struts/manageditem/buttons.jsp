<%@page import="gov.va.med.pharmacy.peps.presentation.common.context.UserContext"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
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
           
          
%>
<div class="panel">
	<s:if test="%{itemType.isDomainType()}">
		<div class="floatLeft">
	</s:if>
	<s:else>
	    <div class="floatRight">
	</s:else>

<%-- SPRING CONVERSION 
<div class="panel">
	<c:choose>
		<c:when test="%{itemType.isDomainType()}">
			<div class="floatLeft">
		</c:when><c:otherwise>
		    <div class="floatRight">
		</c:otherwise>
	</c:choose>
--%>
	<%
	if (canEdit) {
	%>

	<s:if test="%{itemType.pharmacySystem}">
	    <span class="column"><peps:submit
	        key="button.undo"
	        event="cancel" /></span>
	</s:if>
	<s:else>
	    <span class="column"><peps:cancel /></span>
	</s:else> 
	
<%-- SPRING CONVERSION 
	<c:choose>
		<c:when test="%{itemType.pharmacySystem}">
		    <span class="column"><peps:submit
		        key="button.undo"
		        event="cancel" /></span>
		</c:when>
		<c:otherwise>
		    <span class="column"><peps:cancel /></span>
		</c:otherwise>
	</c:choose>
--%>	
	<s:if test="%{environment.local && !itemType.pharmacySystem}">
	    <span class="column"><peps:submit
	        key="button.problemReport"
	        event="submitProblemReport" /></span>
	</s:if>
	<%-- SPRING/JSTL 
	<c:if test="%{environment.local && !itemType.pharmacySystem}">
	    <span class="column"><peps:submit
	        key="button.problemReport"
	        event="submitProblemReport" /></span>
	</c:if>
	--%> 
	<s:if test="%{environment.national && mainRequest.requestType.isProblemReport()}">
	    <span class="column"><peps:submit
	        key="button.rejectProblemReport"
	        event="rejectProblemReport" /></span>
	</s:if>
	<%-- SPRING/JSTL
	<c:if test="%{environment.national && mainRequest.requestType.isProblemReport()}">
	    <span class="column"><peps:submit
	        key="button.rejectProblemReport"
	        event="rejectProblemReport" /></span>
	</c:if>  
	--%>
	<s:if test="%{itemType.isEntityType()}">
	    <span class="column"><peps:submit
	        key="button.bookmark"
	        event="savePartial" /></span>
	</s:if>
	<%-- SPRING/JSTL
	<c:choose>
		<c:when test="itemType.isEntityType()">
		    <span class="column"><peps:submit
		        key="button.bookmark"
		        event="savePartial" /></span>
		</c:when>
		<c:otherwise>
		 <span class="column"><peps:submit
		        key="button.rejectProblemReport"
		        event="savePartial" /></span>
		</c:otherwise>
	</c:choose>
	--%>
	<s:if test="%{tableSelections != null && tableSelectionsIndex > 0}">
	    <span class="column"><peps:submit
	        key="button.previousItem"
	        event="previousItem" /></span>
	</s:if>
	<%-- SPRING/JSTL
	<c:if test="%{tableSelections != null && tableSelectionsIndex > 0}">
	    <span class="column"><peps:submit
	        key="button.previousItem"
	        event="previousItem" /></span>
	</c:if>--%>
	<s:if test="%{tableSelections != null && tableSelections.size() > tableSelectionsIndex + 1}">
	    <span class="column"><peps:submit
	        key="button.nextItem"
	        event="nextItem" /></span>
	</s:if>
	<%-- SPRING/JSTL
	<c:if test="%{tableSelections != null && tableSelections.size() > tableSelectionsIndex + 1}">
	    <span class="column"><peps:submit
	        key="button.nextItem"
	        event="nextItem" /></span>
	</c:if>
	--%>
	<s:if
	    test="%{itemType.isDomainType() && @gov.va.med.pharmacy.peps.common.vo.FieldKey@INACTIVATION_DATE.getEntityTypes().contains(itemType) && !fieldAuthorization.isDisabled(@gov.va.med.pharmacy.peps.common.vo.FieldKey@ITEM_STATUS)}">
	    <s:if test="%{environment.national || itemType.isLocalOnlyData()}">
	        <span class="column">
		        <s:if test="%{item.itemStatus.isActive()}">
		            <peps:submit
		                key="button.inactivate"
		                event="inactivate" />
		        </s:if>
		        <s:else>
		            <peps:submit
		                key="button.activate"
		                event="activate" />
		        </s:else>
	        </span>
	    </s:if>
	    <s:elseif
	        test="%{environment.local && itemType.isDrugText() && item.drugTextType.isLocal()}">
	        <s:if test="%{item.itemStatus.isActive()}">
	            <span class="column"> <peps:submit
	                key="button.inactivate"
	                event="inactivate" /></span>
	        </s:if>
	    </s:elseif>
	</s:if>
	<%--<c:if test="%{itemType.isDomainType() && @gov.va.med.pharmacy.peps.common.vo.FieldKey@INACTIVATION_DATE.getEntityTypes().contains(itemType) && !fieldAuthorization.isDisabled(@gov.va.med.pharmacy.peps.common.vo.FieldKey@ITEM_STATUS)}">
	    <c:choose>
		    <c:when test="%{environment.national || itemType.isLocalOnlyData()}">
		        <span class="column">
			        <c:choose>
				        <c:when test="%{item.itemStatus.isActive()}">
				            <peps:submit
				                key="button.inactivate"
				                event="inactivate" />
				        </c:when>
				        <c:otherwise>
				            <peps:submit
				                key="button.activate"
				                event="activate" />
				        </c:otherwise>
			        </c:choose>
		        </span>
		    </c:when>
		    <c:otherwise>
			    <c:if test="%{environment.local && itemType.isDrugText() && item.drugTextType.isLocal()}">
			         <c:if test="%{item.itemStatus.isActive()}">
			            <span class="column"> <peps:submit
			                key="button.inactivate"
			                event="inactivate" /> </span>
			        </c:if>
			    </c:if>
		    </c:otherwise>
	   	</c:choose>
	</c:if> --%>
	
	
	<s:if test="%{itemType.isProduct() || itemType.isNdc() || (itemType.isOrderableItem() && item.isLocal())}">
	    <span class="column"> <s:if test="%{itemType.isEntityType()}">
	        <s:if test="%{itemType.isOrderableItem()}">
	            <s:set
	                name="parentButtonKey"
	                value="'button.change.loi.parent'"
	                scope="page" />
	        </s:if>
	        <s:elseif test="%{itemType.isProduct()}">
	            <s:set
	                name="parentButtonKey"
	                value="'button.change.product.parent'"
	                scope="page" />
	        </s:elseif>
	        <s:elseif test="%{itemType.isNdc()}">
	            <s:set
	                name="parentButtonKey"
	                value="'button.change.ndc.parent'"
	                scope="page" />
	        </s:elseif>
	        <s:else>
	            <s:set
	                name="parentButtonKey"
	                value="'button.change.parent'"
	                scope="page" />
	        </s:else>
	        <peps:submit
	            key="${parentButtonKey}"
	            event="selectParent" />
	    </s:if></span>
	</s:if>
	<s:if test="%{environment.local}">
	    <span class="column"><peps:submit
	        key="button.submit"
	        event="submitModifications" /></span>
	</s:if>
	<s:else>
	    <s:if test="%{mainRequest == null}">
	        <span class="column"><peps:submit
	            key="button.submit"
	            event="submitModifications" /></span>
	</s:if> 
	</s:else>
	
	<%-- SPRING/JSTL	
	<c:if test="%{itemType.isProduct() || itemType.isNdc() || (itemType.isOrderableItem() && item.isLocal())}">
	    <span class="column"> <s:if test="%{itemType.isEntityType()}">
	        <c:choose>
		        <c:when test="%{itemType.isOrderableItem()}">
		            <s:set
		                name="parentButtonKey"
		                value="'button.change.loi.parent'"
		                scope="page" />
		        </c:when>
		        <c:when test="%{itemType.isProduct()}">
		            <s:set
		                name="parentButtonKey"
		                value="'button.change.product.parent'"
		                scope="page" />
		        </c:when>
		        <c:when test="%{itemType.isNdc()}">
		            <s:set
		                name="parentButtonKey"
		                value="'button.change.ndc.parent'"
		                scope="page" />
		        </c:when>
		        <c:otherwise>
		            <s:set
		                name="parentButtonKey"
		                value="'button.change.parent'"
		                scope="page" />
		        </c:otherwise>
		        <peps:submit
		            key="${parentButtonKey}"
		            event="selectParent" />
	    	</c:choose>
	    </span>
	</c:if>
	<c:choose>
		<c:when test="%{environment.local}">
		    <span class="column"><peps:submit
		        key="button.submit"
		        event="submitModifications" /></span>
		</c:when>
		<c:otherwise>
		    <c:if test="%{mainRequest == null}">
		        <span class="column"><peps:submit
		            key="button.submit"
		            event="submitModifications" /></span>
			</c:if> 
		</c:otherwise>
	</c:choose>	
	--%>
<%
    }
    if (canApprove) {
%>
    
    <s:if test="%{mainRequest != null && mainRequest.requestType.addition}">
        <span class="column"><peps:submit
            key="button.approve"
            event="approveRequest" /></span>
        <span class="column"><peps:submit
            key="button.reject"
            event="rejectRequest" /></span>
        <span class="column"><peps:submit
            key="button.under.review"
            event="markUnderReview" /></span>
    </s:if>
    <s:elseif test="%{mainRequest != null && itemType}">
        <span class="column"><peps:submit
            key="button.under.review"
            event="markUnderReview" /></span>
        <span class="column"><peps:submit
            key="button.submit"
            event="submitModRequests" /></span>
    </s:elseif>
    
    <%-- SPRING/JSTL
    <c:choose>
	    <c:when test="%{mainRequest != null && mainRequest.requestType.addition}">
	        <span class="column"><peps:submit
	            key="button.approve"
	            event="approveRequest" /></span>
	        <span class="column"><peps:submit
	            key="button.reject"
	            event="rejectRequest" /></span>
	        <span class="column"><peps:submit
	            key="button.under.review"
	            event="markUnderReview" /></span>
	    </c:when>
	    <c:otherwise>
		    <c:if test="%{mainRequest != null && itemType}">
		        <span class="column"><peps:submit
		            key="button.under.review"
		            event="markUnderReview" /></span>
		        <span class="column"><peps:submit
		            key="button.submit"
		            event="submitModRequests" /></span>
		    </c:if>
	    </c:otherwise>
	</c:choose>
	 --%>
<%
}
%></div>
</div>