<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 
    prefix="fn"
    uri="http://java.sun.com/jsp/jstl/functions"%>	
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>	
    
<div class="twoColumn">
<center>
<h3>Provide Details for the Rejection</h3>

</center>
</div>
<div class="horizontalspacer"></div>
<peps:form id="confirmReject">
	<div class="topspacer" style="padding-left: 17px">
	Fields marked with <font color="red">*</font> are required
	</div>	
    <div class="twoColumnWide">    
	
    <peps:dataField
        item="${mainRequest}"
        key="request.rejection.reason"        
        disabled="false" /><br />
    <peps:dataField
        item="${mainRequest}"
        key="rejection.reason.text"        
        disabled="false" required="true"/><br />
    <c:if test="${mainRequest.requestType.addition}">
        <div class="twoColumn"><peps:dataField
            item="${mainRequest}"
            key="note"            
            disabled="true"
            rows="5"
            cols="10" /></div>    
    <label style="vertical-align: top;">Enter any optional comments
    here: </label><peps:dataField
        item="${mainRequest}"
        key="new.note"
        labelPosition="none"
        cols="20"
        rows="4"
        cssStyle="width: 395px;" />
    </c:if>
    </div> 
    <br>
    <p/>
     <div class="twoColumnWide">
		<c:if test="${!empty mainRequest.dependentRequests}">
			<fieldset>
				<legend>
					<spring:message code="dependent.requests" />
				</legend>
				<p>
					<spring:message code="request.reject.dependents.text" />
				<p>
				<table id="dependentRequestTable" frame="box" rules="cols"
					class="pepsTable">
					<thead>
						<tr>
							<th><spring:message code="item.type" />
							</th>
							<th><spring:message code="item.name" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${mainRequest.dependentRequests}"
							varStatus="itemStatus">
							<c:choose>
								<c:when test="${itemStatus.index % 2 == 0}">
									<c:set var="evenOdd" value="odd" />
								</c:when>
								<c:otherwise>
									<c:set var="evenOdd" value="even" />
								</c:otherwise>
							</c:choose>
							<tr>
								<td align="center" class="${evenOdd}">
									${item.key.entityType}</td>
								<td align="center" class="${evenOdd}">${item.key.value} <c:if
										test="${item.key.entityType.ingredient && item.key.primaryIngredient != null}">
		                        (${item.key.primaryIngredient.value})
		                       </c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</c:if>
    </div>
    <p><b><c:out value="${requestStateStatusMessage}" /></b></p>
    <div class="buttonspacer"></div>
    <div class="twoColumn">
    <div class="center">
    <span class="column">
	    <c:url value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/returnToRequest.go" var="actionUrl"/>
        <peps:submit
        key="button.cancel"
        action="${actionUrl}" />
    </span>
    <span class="column"></span>
    <span class="column">
        <peps:submit
        key="button.submit" />
    </span>
    </div>
    </div>
</peps:form>