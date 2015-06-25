<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"
    %>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>
<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form"%>
<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>
<script type="text/javascript">	

function downloadCSV()
{
     window.location = '/PRE/downloadCSV.go';
}

function refreshForm()
{
	 //document.getElementById("hasEntityChanged").value=true;
     //window.location = '/PRE/refreshForm.go';
}
 </script>     
	<form:form id="performReportsSearchOption" 
	           modelAttribute="reportsSearch" 
	           action="/PRE/reports.go" 
	           method="GET">
	    <input type="hidden" id="isFirstRun" name="isFirstRun" value="false"/>
		<input type="hidden" id="hasEntityChanged" name="hasEntityChanged" value="false"/>
	    <div class="row">
			<div class="panel">
				<div class="controlGroup">					
					 <table  cellpadding="5">
						<tr>
							<td>
								<div>
									<div class="column">
										<spring:message text="Select a Report" /><span class="required"></span><span>:</span>
										<c:choose>
											<c:when test="${empty reportType}">
												<form:select 
						                            path="reportType" 
													cssClass="comboBox" 
													onchange="refreshForm();">
													<form:options 
														items="${reportTypeMap}"/>
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select
						                            path="reportType"	
													id="report.reportType_disabled"
													cssClass="comboBox" 
													onchange="refreshForm();">
													disabled="true">
													<form:options 
														items="${reportTypeMap}"/>
												</form:select>	
											</c:otherwise>
										</c:choose>
									</div>
									<div class="column">
										<c:if test="${reportType == 'ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE' || reportVo.reportType == 'PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE' || reportVo.reportType == 'ITEM_AUDIT_HISTORY_PRINT_TEMPLATE'}">
												<spring:message code="start.date.name" /><span>:</span>
													<form:input 
														path="" 
														size="20"/>
												<spring:message code="stop.date.name" /><span>:</span>
													<form:input 
														path="" 
														size="20"/>
										</c:if>
										<c:if test="${!isPrintable}">	
											<spring:message code="report.download.only" var="downloadMessage"/>
<%-- 											<c:out value="${downloadMessage}"/> --%>
										</c:if>
									</div>
									
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<spring:message code="button.view.report" var="viewButton"/>
									<spring:message code="button.generate.csv" var="generateButton"/>
									<spring:message code="button.download.csv" var="downloadButton"/>
									
									<c:choose>
										<c:when test="not empty ${reportType}">
											<peps:submit key="button.view.report" />
										</c:when>
										<c:otherwise>
												<c:choose>
												<c:when test="${isPrintable}">
												<input id="button.search.button" 
													class="button " 
													type="submit" 
													onclick="submitButton = this.name;" 
													style="" 
													value="${viewButton}" 
													name="">
												<input id="button.download.button" 
													class="button " 
													type="submit" 
													onclick="downloadCSV();" 
													style="width:100px;" 
													value="${downloadButton}" 
													name="">
<%-- 												<peps:submit key="button.view.report" id="button.view.report" /> --%>
	<%-- 											<peps:submit  --%>
	<!-- 												key="button.generate.report" /> -->
											</c:when>
											<c:otherwise>
											<input id="button.download.button" 
													class="button " 
													type="submit" 
													onclick="downloadCSV();" 
													style="width:100px;" 
													value="${generateButton}" 
													name="">
											<input id="button.download.button" 
													class="button " 
													type="submit" 
													onclick="downloadCSV();" 
													style="width:100px;" 
													value="${downloadButton}" 
													name="">
											</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
										<div class="horizontalspacer"></div>
	<%-- 								<c:out value="${reportVo.reportType}"/> --%>
								</div>
	<%-- 							<c:out value="<%= pageContext.getAttribute("option.selected") %>"/> --%>
								
							</td>
						</tr>
					</table>
<!-- 					</fieldset> -->
				</div>
			</div>
		</div>
	</form:form>
<%-- 	<tiles:insertAttribute name="drugClass" /> --%>
<div class="bodyheaderspacer"></div>