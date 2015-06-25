<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldAuthorizationVo"%>
<%@page import="java.util.Collection"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo"%>
<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	Collection<ModDifferenceVo> modDifferences = (Collection<ModDifferenceVo>) request
			.getAttribute("modDifferences");
	FieldAuthorizationVo fieldAuthorization = (FieldAuthorizationVo) request
			.getAttribute("fieldAuthorization");
	if (fieldAuthorization == null)
		fieldAuthorization = new FieldAuthorizationVo();
%>

<c:set scope="page" var="itemId" value="${item.id}" />
<c:set scope="page" var="itemTypeString" value="${item.entityType.name}" />

<peps:warnings id="warnings" warnings="${warnings}" />
<br />

<h3>
	&nbsp;&nbsp;Summary for
	<c:choose>
		<c:when test="${item.entityType.product}">
			<peps:label key="product" />:<peps:text item="${item}"
				key="product.long.name" label="false" />
		</c:when>
		<c:when test="${item.entityType.ndc}">
			<peps:label key="ndc" />:<peps:text item="${item}" key="ndc"
				label="false" />
		</c:when>
		<c:when test="${item.entityType.orderableItem}">
			<peps:label key="orderable.item" />:<peps:text item="${item}"
				key="oi.name" label="false" />
		</c:when>
		<c:otherwise>
			<peps:label key="${itemTypeString}" />:<peps:text item="${item}"
				key="value" label="false" />
		</c:otherwise>
	</c:choose>
</h3>


<form:form id="modificationSummary" commandName="modificatonSummary"
	method="post" action=""
	onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
	<div class="panel">

		<c:if test="${mainRequest.requestType.addition}">
			<div class="threeColumn2left">
				<peps:dataField item="${mainRequest}" key="new.note" rows="5"
					cols="10" />
			</div>
			<div class="threeColumn2left">
				<peps:dataField item="${mainRequest}" key="note" readonly="true"
					rows="5" cols="10" cssClass="readonlydisabled"/>
			</div>
		</c:if>

		<c:if test="${mainRequest.requestState.pendingFirstReview}">
			<peps:dataField item="${mainRequest}"
				key="marked.for.peps.second.review" labelPosition="left" />
		</c:if>
		<c:if test="${psrName != user.username}">
			<peps:dataField item="${mainRequest}" key="psr.name" />
		</c:if>
	</div>
	<div class="panel">
		<c:if
			test="${mainRequest != null 
              && mainRequest.requestState.pendingFirstReview
              && mainRequest.requestType.problemReport}">
			<fieldset>
				<legend>Change Requests</legend>
				<peps:textarea value="${mainRequest.note}" id="mainRequest.note"
					rows="10" cols="70" />
			</fieldset>
			<br>
		</c:if>
		<div class="topspacer">
			<spring:message code="has.conflicts" />
		</div>
		<c:choose>
			<c:when test="${!empty mainRequest.requestDetails}">
				<fieldset>
					<legend>
						<spring:message code="request.summary" />
					</legend>
					<table id="modTable" frame="box" rules="cols" class="pepsTable">
						<thead>
							<tr>
								<th><peps:label key="column.action" abbreviation="true" />
								</th>
								<th><peps:label key="field.name" abbreviation="true" />
								</th>
								<th><peps:label key="current.value" abbreviation="true" />
								</th>
								<th><peps:label key="modified.value" abbreviation="true" />
								</th>
								<th><peps:label key="modification.reason"
										abbreviation="true" />
								</th>
								<th><peps:label key="request.to.modify.value"
										abbreviation="true" />
								</th>
								<th><peps:label key="request.to.make.editable"
										abbreviation="true" />
								</th>
								<th><peps:label key="requested.by" abbreviation="true" />
								</th>
								<th><peps:label key="requires.second.approval"
										abbreviation="true" />
								</th>
								<th><peps:label key="reviewed.by" abbreviation="true" />
								</th>
								<th><peps:label key="request.comments" abbreviation="true" />
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="reqDetail" items="${mainRequest.requestDetails}"
								varStatus="details">
								<%
									ModDifferenceVo requestDetail = (ModDifferenceVo) pageContext
															.getAttribute("reqDetail");
								%>
								<c:set scope="page" var="index" value="${details.index}" />
								<c:choose>
									<c:when test="<%=requestDetail.hasConflict()%>">
										<tr class="hasconflict">
									</c:when>
									<c:when test="${index % 2 == 0}">
										<tr class="odd">
									</c:when>
									<c:otherwise>
										<tr class="even">
									</c:otherwise>
								</c:choose>
								<td align="center"><peps:dataField item="${reqDetail}"
										key="mod.request.item.status"
										id="mod.request.item.status_${index}" labelPosition="none" />
								</td>
								<td><c:if test="<%=requestDetail.hasConflict()%>">
										<spring:message code="conflict.symbol"/>&nbsp;</c:if><peps:text
										item="<%=requestDetail.getDifference().getFieldKey().getLocalizedName(request.getLocale())%>"
										key="field.name" />
								</td>
								<td><peps:text
										item="${reqDetail.difference.oldValueShortString}"
										key="current.value" />
								</td>
								<td><peps:text
										item="${reqDetail.difference.newValueShortString}"
										key="modified.value" />
								</td>
								<td><peps:text item="${reqDetail}"
										key="modification.reason" />
								</td>
								<td align="center"><peps:dataField item="${reqDetail}"
										key="request.to.modify.value"
										id="request.to.modify.value_${index}" labelPosition="none"
										disabled="true" cssClass="checkbox2 center" />
								</td>
								<td align="center"><peps:dataField item="${reqDetail}"
										key="request.to.make.editable"
										id="request.to.make.editable_${index}" labelPosition="none"
										disabled="true" cssClass="checkbox2 center" />
								</td>
								<td align="center"><c:choose>
										<c:when test="${reqDetail.requestor ne null}">
											<c:set value="${reqDetail.requestor.displayName}"
												var="requestorName" />
										</c:when>
										<c:otherwise>
											<c:set value="" var="requestorName" />
										</c:otherwise>
									</c:choose> <peps:text item="${requestorName}" key="requested.by" />
								</td>
								<td align="center"><peps:dataField item="${reqDetail}"
										key="requires.second.approval"
										id="requires.second.approval_${index}" 
										value="${reqDetail.difference.fieldKey.requiresSecondApproval}"
										labelPosition="none"
										cssClass="center" disabled="true" />
								</td>
								<td align="center"><c:choose>
										<c:when test="${reqDetail.reviewer ne null}">
											<c:set value="${reqDetail.reviewer.displayName}"
												var="reviewerName" />
										</c:when>
										<c:otherwise>
											<c:set value="" var="reviewerName" />
										</c:otherwise>
									</c:choose> <peps:text item="${reviewerName}" key="reviewed.by" />
								</td>
								<td align="center"><peps:dataField item="${reqDetail}"
										key="comments" id="note_${index}" labelPosition="none"
										rows="2" cols="10" />
								</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</c:when>
			<c:otherwise>
				<p>
					<spring:message code="request.no.pending.mods" />
				<p>
			</c:otherwise>
		</c:choose>
		<br>
		<c:if test="${!empty mainRequest.dependentRequests}">
			<fieldset>
				<legend>
					<spring:message code="dependent.requests" />
				</legend>
				<p>
					<spring:message code="request.approve.dependents.text" />
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
		<c:if
			test="<%=DifferenceUtility.hasEditableDifference(modDifferences) %>">
			<fieldset>
				<legend>
					<spring:message code="mod.summary.text" />
				</legend>
				<table id="modTable" frame="box" rules="cols" class="pepsTable">
					<thead>
						<tr>
							<th><peps:label key="field.name" abbreviation="true" />
							</th>
							<th><peps:label key="current.value" abbreviation="true" />
							</th>
							<th><peps:label key="modified.value" abbreviation="true" />
							</th>
							<th><peps:label key="modification.reason"
									abbreviation="true" />
							</th>
							<th><peps:label key="accept.change" abbreviation="true" />
							</th>
							<th><peps:label key="requires.second.approval"
									abbreviation="true" />
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="modDifference" items="${modDifferences}"
							varStatus="modStatus">
							<%
								ModDifferenceVo modDifference = (ModDifferenceVo) pageContext
													.getAttribute("modDifference");
							%>
							<c:set scope="page" var="index" value="${modStatus.index}" />
							<c:choose>
								<c:when test="<%=modDifference.hasConflict()%>">
									<tr class="hasconflict">
								</c:when>
								<c:when test="${index % 2 == 0}">
									<tr class="odd">
								</c:when>
								<c:otherwise>
									<tr class="even">
								</c:otherwise>
							</c:choose>
							<td><c:if test="<%=modDifference.hasConflict()%>">
									<spring:message code="conflict.symbol" />&nbsp;</c:if> <peps:text
									item="<%=modDifference.getDifference().getFieldKey().getLocalizedName(request.getLocale())%>"
									key="field.name" /></td>
							<td><peps:text
									item="${modDifference.difference.oldValueShortString}"
									key="current.value" /></td>
							<td><peps:text
									item="${modDifference.difference.newValueShortString}"
									key="modified.value" />
							</td>
							<td><c:if
									test="${modDifference.difference.fieldKey.key eq 'item.status' and modDifference.difference.oldValue eq 'ACTIVE'}">
									<span class="required">*</span>
								</c:if> <form:textarea id="modification.reason_${index}"
									cssClass="textarea" path="modifications[${index}].reason"
									rows="2" cols="10" /></td>
							<td align="center"><form:checkbox
									id="accept.change_${index}"
									path="modifications[${index}].acceptChange"
									cssClass="checkbox2 center" /></td>
							<td align="center"><peps:dataField item="${modDifference}"
									key="requires.second.approval"
									id="requires.second.approval_${index}"
									value="<%=Boolean.valueOf(fieldAuthorization.isRequiresSecondApproval(modDifference.getDifference().getFieldKey())).toString() %>"
									labelPosition="none" cssClass="checkbox2 center"
									disabled="true" />
							</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</c:if>
		<p>
			<b>${requestStateStatusMessage}</b>
		</p>
		<div>
			<div class="fourColumn">
				<c:url
					value="/${fn:toLowerCase(mainRequest.entityType)}/${mainRequest.id}/request/${mainRequest.id}/returnToRequest.go"
					var="actionUrl" />
				<peps:submit key="button.cancel" action="${actionUrl}" />
			</div>
			<c:choose>
				<c:when test="${!empty mainRequest.requestDetails}">
					<div class="fourColumn">
						<peps:submit key="button.accept.actions" />
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${item.entityType.product || item.entityType.ndc || item.entityType.orderableItem}">
				   			<div class="fourColumn"><peps:submit
	        					key="button.approve.${item.entityType}" /></div>
	        			</c:when>
	        			<c:otherwise>
	        				<div class="fourColumn"><peps:submit key="button.approve" /></div>
	        			</c:otherwise>
        			</c:choose>
				</c:otherwise>
			</c:choose>
    	</div>
    </div>
</form:form>