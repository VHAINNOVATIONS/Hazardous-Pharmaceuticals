<%@page import="gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.SearchDomain"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>  
<%@taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>        
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>  
	      
<%
boolean isDomainSearch = ((SearchRequestCriteriaVo)request.getAttribute("searchRequestCriteriaVo")).getSearchDomain().isDomainSearch();
%>

<script language="javascript">
	var otherOnLoad = window.onload;
	window.onload = function() {
	    otherOnLoad();
	    setDateRangeVisible(document.getElementById('request.date.use').unchecked);
	}
	function setDateRangeVisible(show) {
	    if (show) {
	        document.getElementById('dateDiv').className = 'threeColumn'; 
	        document.getElementById('all.requests').checked = false; 
	    }
	    else {
	        document.getElementById('dateDiv').className = 'threeColumn hidden';
// 	        if (!document.getElementById('local.requests').checked) {
// 	            document.getElementById('all.requests').checked = true;
	        
// 	        }
	    }
	}
</script>


<form:form id="reviewSearch" onsubmit="return disableSubmit(reviewSearch);" modelAttribute="searchRequestCriteriaVo">
<form:hidden path="searchDomain" />

    <div class="panel">
    
	<c:choose>
        <c:when test="${user.onlySecondReviewer}">

				<spring:message code="marked.for.peps.second.review.name" var="markedSecondReview"/>
				
			    <peps:controlheader 
			   		labelposition="right"
			        label="${markedSecondReview}"
			        id="marked.for.peps.second.review"
			        forattrib="marked.for.peps.second.review"/>    
			    <form:checkbox
			        path="markedForPepsSecondReview"
			        id="marked.for.peps.second.review" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${markedSecondReview}"
			        id="marked.for.peps.second.review"
			        forattrib="marked.for.peps.second.review"/> 
        </c:when>        
        <c:otherwise>
	        <div class="fourColumn">

				<spring:message code="pending.addition.name" var="pendingAddName"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${pendingAddName}"
			        id="pending.addition"
			        forattrib="pending.addition"/>    
			    <form:checkbox
			        path="pendingSecondApprovalAddition"
			        id="pending.addition" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${pendingAddName}"
			        id="pending.addition"
			        forattrib="pending.addition"/> 			        

<%-- 		        <peps:dataField key="pending.second.approval.addition" labelPosition="right" /> --%>
				
				<spring:message code="pending.second.approval.addition.name" var="pendingSecondApproval"/>
				
			    <peps:controlheader 
			   		labelposition="right"
			        label="${pendingSecondApproval}"
			        id="pending.second.approval.addition"
			        forattrib="pending.second.approval.addition"/>    
			    <form:checkbox
			        path="pendingSecondApprovalAddition"
			        id="pending.second.approval.addition" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${pendingSecondApproval}"
			        id="pending.second.approval.addition"
			        forattrib="pending.second.approval.addition"/> 

        	</div>
        	
	        <div class="fourColumn">
		        <c:if test="<%=!isDomainSearch%>">
				<spring:message code="pending.modification.name" var="pendMod"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${pendMod}"
			        id="pending.modification"
			        forattrib="pending.modification"/>    
			    <form:checkbox
			        path="pendingModification"
			        id="pending.modification" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${pendMod}"
			        id="pending.modification"
			        forattrib="pending.modification"/> 
						
		        </c:if>
<%-- 	        	<peps:dataField key="pending.second.approval.modification" labelPosition="right" /> --%>

				<spring:message code="pending.second.approval.modification.name" var="pend2ndApprovalMod"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${pend2ndApprovalMod}"
			        id="pending.second.approval.modification"
			        forattrib="pending.second.approval.modification"/>    
			    <form:checkbox
			        path="pendingSecondApprovalModification"
			        id="pending.second.approval.modification" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${pend2ndApprovalMod}"
			        id="pending.second.approval.modification"
			        forattrib="pending.second.approval.modification"/> 

        	</div>
	        <div class="eightColumn">
<%-- 	        	<peps:dataField key="all.requests" labelPosition="right" /> --%>

				<spring:message code="all.requests.name" var="allRequests"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${allRequests}"
			        id="all.requests"
			        forattrib="all.requests"/>    
			    <form:checkbox
			        path="allRequests"
			        id="all.requests" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${allRequests}"
			        id="all.requests"
			        forattrib="all.requests"/> 
			        
<%-- 	        	<peps:dataField key="under.review" labelPosition="right" /> --%>

				<spring:message code="under.review.name" var="underReview"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${underReview}"
			        id="under.review"
			        forattrib="under.review"/>    
			    <form:checkbox
			        path="underReview"
			        value="enabled"
			        id="under.review" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${underReview}"
			        id="under.review"
			        forattrib="under.review"/> 
			        
        	</div>
	        <div class="eightColumn">
<%-- 	           <peps:dataField key="marked.for.peps.second.review" labelPosition="right" /> --%>

				<spring:message code="marked.for.peps.second.review.name" var="markedPeps2ndReview"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${markedPeps2ndReview}"
			        id="marked.for.peps.second.review"
			        forattrib="marked.for.peps.second.review"/>    
			    <form:checkbox
			        path="markedForPepsSecondReview"
			        value="enabled"
			        id="marked.for.peps.second.review" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${markedPeps2ndReview}"
			        id="marked.for.peps.second.review"
			        forattrib="marked.for.peps.second.review"/> 
			        
<%--          	   <peps:dataField key="problem.reports" labelPosition="right" /> --%>

				<spring:message code="problem.reports.name" var="problemReport"/>
								
			    <peps:controlheader 
			   		labelposition="right"
			        label="${problemReport}"
			        id="problem.reports"
			        forattrib="problem.reports"/>    
			    <form:checkbox
			        path="problemReports"
			        value="enabled"
			        id="problem.reports" />
			    <peps:controlfooter 
			    	labelposition="right"
			        label="${problemReport}"
			        id="problem.reports"
			        forattrib="problem.reports"/> 
			        
            </div>
        </c:otherwise>
    </c:choose>    

    <div class="bodyheaderspacer"></div>
    <div class="column spacer"></div> 
    <div class="fourColumn">
	    <peps:submit
	        key="button.search"
	        event="search"
	        isFlowButton="false" />
    </div>
    </div>
    

    
</form:form>    