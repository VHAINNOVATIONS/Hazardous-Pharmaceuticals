<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.PackageTypeVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.NdcVo"%>

<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form:form id="search" modelAttribute="searchCriteria" method="GET"
	onsubmit="return disableSubmit(this.id);">

	<input type="hidden" id="isFirstRun" name="isFirstRun" value="false">
	<input type="hidden" id="entityType" name="entityType"
		value="${searchCriteria.entityType}">
	<div class="row" style="height: auto">
		<h1>
			${searchCriteria.entityType.name} Search
			<c:if test="${not empty addTitle}">
		${addTitle}</c:if>
		</h1>
	</div>

	<div class="row">
		<fieldset>
			<legend>Search Criteria for
				${searchCriteria.entityType.name} Search</legend>
			<div class="horizontalspacer"></div>
			<div class="row">
				<div class="column">
					<spring:message code="search.for.name" var="searchFor" />
					<spring:message code="search.for.description" var="searchForDesc" />
					<label title="${searchFor}">${searchFor} <span
						class="required"> *</span><span>:</span> </label>
					<div>
						<form:input path="searchTerms[0].value" size="15"
							title="${searchForDesc}  (use % as wildcard)" />
					</div>

				</div>
				<div class="column">
					<spring:message code="search.fields.abbreviation" />
					<span>:</span>
					<div>
						<form:select cssClass="comboBox"
							path="searchTerms[0].searchField.key"
							onchange="reloadOncePrompted();">
							<form:options items="${searchCriteria.searchFieldsMap}" />
						</form:select>
					</div>
				</div>
				<div id="strengthField" class="column" style="display: none;">
					<spring:message code="strength.abbreviation" var="strAbbr" />
					<label title="${strAbbr}">${strAbbr} <span>:</span> </label>
					<div style="width: auto">
						<spring:message code="strength.description"
							var="strengthDescription" />
						<form:input title="${strengthDescription} (number only)"
							path="strength" size="8" />

					</div>
				</div>
				<div id="dosageFormField" class="column" style="display: none;">
					<spring:message code="dosage.form.abbreviation" />
					<span>:</span>
					<div>
						<spring:message code="dosage.form.description"
							var="dosageFormDesc" />
						<form:input title="${dosageFormDesc}" path="dosageForm" size="7" />
					</div>
				</div>

				<c:choose>
					<c:when test="${searchCriteria.entityType.product}">
						<script type="text/javascript">
							document.getElementById("strengthField").style.display = "inline";
							document.getElementById("dosageFormField").style.display = "none";
						</script>
					</c:when>
					<c:when test="${searchCriteria.entityType.orderableItem}">
						<script type="text/javascript">
							document.getElementById("strengthField").style.display = "none";
							document.getElementById("dosageFormField").style.display = "inline";
						</script>
					</c:when>
				</c:choose>

				<%-- <c:if test="${mainRequest == null}">
		                    <div class="column">
		                        <spring:message code="local.use.abbreviation" /><span>:</span>
		                        <div>
		                            <form:radiobuttons
		                                path="searchTemplate.searchCriteria.localUse"
		                                element="div" items="${localUseMap}" />
		                        </div>
		                    </div>
		                    <div class="column">&nbsp;&nbsp;</div>
		                </c:if> --%>


				<div class="column">
					<fieldset>
						<legend>
							<spring:message code="item.status.name" />
						</legend>
						<form:checkboxes cssClass="checkbox" element="div"
							items="${itemStatusTypeMap}" path="itemStatus" />
					</fieldset>
				</div>
				<div class="column">
					<fieldset>
						<legend>
							<spring:message code="request.item.status.name" />
						</legend>
						<form:checkboxes cssClass="checkbox" element="div"
							items="${requestStatusTypeMap}" path="requestStatus" />
					</fieldset>
				</div>
				<c:if test="${not empty item.vaProductName}">
					<div class="column">
						<table style="width: 450px;" class="pepsTable">
							<thead>
								<tr>
									<th colspan="7" align="center">
										<h1 align="center">Moving NDC(s) from:</h1></th>
								</tr>
							</thead>
								<tr class="odd">
									<th align="left" width="150px">Current VA Product:</th>
									<td><c:out value="${item.vaProductName}"></c:out>

									</td>
								</tr>

							<c:forEach var="packageList" items="${packageTypes}" begin="0"
								varStatus="status">
								<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
									<th align="left" width="150px">Package Type:</th>
									<td align="left" valign="middle"><c:out
											value="${packageList.value}" /></td>

								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<c:if test="${not empty product && !searchCriteria.entityType.orderableItem}">
					<div class="column">
						<table style="width: 450px;" class="pepsTable">
							<thead>
								<tr>
									<th colspan="7" align="center">
										<h1 align="center">Move NDC from:</h1>
										</th>
								</tr>
							</thead>
								<tr class="odd">
									<th align="left" width="150px">Current VA Product:</th>
									<td><peps:text item="${product}"
											key="va.product.name" label="false" />
									</td>
								</tr>
								<tr class="even">
									<th align="left" width="150px">Package Type:</th>
									<td><peps:text key="package.type" item="${product}" />
									</td>
								</tr>

						</table>
					</div>
				</c:if>
				<c:if test="${not empty ndcList}">
					<div class="column">
						<table style="width: 550px;" class="pepsTable">
							<thead>
								<tr>
									<th colspan="7" align="center">
										<h1 align="center">Matching NDCs:</h1></th>
								</tr>
							</thead>
							<c:forEach var="result" items="${ndcList}" begin="0"
								varStatus="status">
								<tr class="${status.index % 2 == 0 ? 'odd' : 'even'}">
									<th align="left" width="165px">Label Name / Package Type:</th>
									<td><c:out value="${result}" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
			</div>
			<div class="row">
				<div class="eightColumn">
					<peps:submit key="button.search" />
				</div>
				<div class="column">
					<c:url value="${cancelUrl}" var="fullCancelUrl" />
					<peps:submit key="button.cancel" action="${fullCancelUrl}" />
				</div>

			</div>
		</fieldset>
	</div>

</form:form>

