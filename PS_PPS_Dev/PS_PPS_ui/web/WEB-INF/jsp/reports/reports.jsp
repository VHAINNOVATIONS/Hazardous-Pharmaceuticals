<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>

<script src="/PRE/javascript/reportScripts.js" type="text/javascript"></script>

<form:form id="performReportsSearchOption"
	name="performReportsSearchOption" modelAttribute="reportVo"
	action="/PRE/reports.go" method="get">
	<input type="hidden" id="isFirstRun" name="isFirstRun" value="false" />
	<input type="hidden" id="hasEntityChanged" name="hasEntityChanged"
		value="false" />
	<div class="row">
		<div class="panel">
			<div class="controlGroup">
				<table cellpadding="5">
					<tr>
						<td>
							<div>
								<div class="column">
									<spring:message text="Select a Report" />
									<span class="required"></span><span>:</span>
									<c:choose>
										<c:when test="${empty reportType}">
											<form:select id="reportTypeOptions" path="reportType"
												cssClass="comboBox"
												onchange="document.performReportsSearchOption.submit();"
												style="width:350px;">
												<form:options items="${reportTypeMap}" />
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="reportType"
												id="report.reportType_disabled" cssClass="comboBox"
												onchange="refreshForm();" disabled="true">
												<form:options items="${reportTypeMap}" />
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="column"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td><c:if test="${reportVo.hasStart}">
								<peps:dataField id="start.date" item="${reportVo}"
									key="start.date" labelPosition="left" cssClass="iahdate" />
							</c:if> <c:if test="${reportVo.hasStop}">
								<peps:dataField id="stop.date" item="${reportVo}"
									key="stop.date" labelPosition="left" cssClass="iahdate" />
							</c:if> <c:if test="${reportVo.VUIDResults}">
								<strong>Synchronization messaging was turned off on default date.</strong>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<div>
								<spring:message code="button.view.report" var="viewButton" />
								<spring:message code="button.status.report" var="statusButton" />
								<spring:message code="button.generate.csv" var="generateButton" />
								<spring:message code="button.download.csv" var="downloadButton" />

								<div>
									<c:choose>
										<c:when test="${reportVo.printable}">
											<input id="button.search.button" class="button"
												style="display: inline;" type="button"
												onclick="runReport('/PRE/reports.update.go');"
												value="${viewButton}" name="">
										</c:when>
										<c:otherwise>
											<input id="button.status" class="button"
												style="display: inline;" type="button"
												onclick="runReport('/PRE/reports.update.go');"
												value="${statusButton}" name="">
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${!reportVo.printable}">
											<c:choose>
												<c:when test="${reportVo.generateOn}">
													<input id="button.generate.button" class="submit"
														style="display: inline;" type="button"
														onclick="generateCSV('/PRE/reports.generate.go');"
														value="${generateButton}" name="genButton">
													<input id="button.download.button" class="submit"
														type="button" onclick="downloadCSV();"
														value="${downloadButton}" name="downloadButton">
												</c:when>
												<c:otherwise>
													<input id="button.generate.button" class="ignoreEnable"
														style="display: inline;" type="button" disabled="disabled"
														value="${generateButton}" name="disGenButton" />
													<input id="button.download.button" class="ignoreEnable"
														type="button" disabled="disabled"
														value="${downloadButton}" name="disDownloadButton" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<input id="button.download.button" class="submit"
												type="button" onclick="downloadCSV();"
												value="${downloadButton}" name="">
										</c:otherwise>
									</c:choose>
									<c:if test="${reportVo.hasDesc}">
										<div id="chDescription" style="display: inline;">
											<form:checkbox path="description"></form:checkbox>
											with Description <br>
										</div>
									</c:if>
								</div>
								<c:choose>
									<c:when test="not empty ${reportType}">
										<peps:submit key="button.view.report" />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${isPrintable}">
											</c:when>
											<c:otherwise>

											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<div class="horizontalspacer"></div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form:form>
<div class="bodyheaderspacer"></div>
