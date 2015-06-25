<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@page
	import="gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo"%>


<%@page import="gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<peps:warnings id="warnings" warnings="${warnings}"/>


<link rel="stylesheet" type="text/css" href="css/fdbupdate.css">
<script src="/PRE/javascript/fdbUpdate.js" type="text/javascript"></script>
<form:form modelAttribute="fdbSearchOption" id="searchForm"
	name="searchForm" action="/PRE/fdbSearch.go" method="GET"
	onsubmit="return disableSubmit(this.id);">
	<div class="horizontalspacer"></div>
	<c:if test="${!empty errorMessages}">
		<div id="errorMessages">
			<font size="4" color="Red"><c:out value="${errorMessages}" />
			</font>
		</div>
	</c:if>
	<div class="body-content">
		<c:if test="${flowScope.fdbAddStateBean.fdbSearchMode == true}">
			<div class="horizontalspacer"></div>
			<div class="panel" style="width: 600px">
			<div class="controlGroup">
					<spring:message code="fdb.search.option.type.date"
						var="fdbTypeDate" />
					<label for="fdb.search.option.type.date" class="label left comboBox">${fdbTypeDate}:</label>
					${flowScope.fdbUpdateDate}
				</div>
				<div class="controlGroup">
					<spring:message code="fdb.search.option.type.name"
						var="fdbTypeText" />
					<label for="fdb.search.option.type" class="label left comboBox">${fdbTypeText}:</label>
					<form:select id="fdb.search.option.type" path="fdbSearchOptionType"
						title="${fdbTypeText}" cssClass="comboBox"
						items="${fdbSearchOptionType}" />
				</div>
				<div class="controlGroup">
					<spring:message code="fdb.search.string.name" var="fdbSearchText" />
					<label for="fdb.search.option.string" class="label left">${fdbSearchText}:</label>

					<form:input id="fdb.search.option.string" path="fdbSearchString"
						size="19" title="${fdbSearchText}" />

				</div>
				<div class="horizontalspacer"></div>

				<div class="panel">
					<span class="column">
					<spring:message var="fdbSearchButtonText" code="button.performFDBSearchOption"/>
					<input id="submitSearch" type="button"
						name="submitSearch" value="${fdbSearchButtonText}"
						onclick="busyProcess(this, performSearch());" />
					<input type="hidden" id="resetSearch" name="resetSearch" value="true"/>	
					</span>
				</div>
			</div>
			<br />
		</c:if>
	</div>
</form:form>
<div class="horizontalspacer"></div>
<c:if test="${flowScope.fdbAddStateBean.displayTable == true}">

	<fieldset>
		<legend>
			<b>RESULTS</b>
		</legend>
		<table id="fdbPendingTable1" width="100%">
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td align="left"><a href="/PRE/fdbAddPrint.go?type=LIST"
								onclick="return popupWithOptions(this.href, 'print_details', 'resizable=yes,scrollbars=yes,toolbar=no, width=1200,height=500');">
									Print </a>
							</td>
							<td align="right">
							<c:if test="${flowScope.fdbAddStateBean.fdbSearchMode == true}">
									<a href="#" id="downloadCSV" name="downloadCSV"
										onclick="downloadCSVFile('search');">Export to CSV</a> &nbsp;&nbsp;
                                  </c:if> <%--Record Count: <c:out value="${fn:length(pendingList)}"/> --%>
                            	<c:if test="${flowScope.fdbAddStateBean.fdbSearchMode == false}">
									<a href="#" id="downloadCSV" name="downloadCSV"
										onclick="downloadCSVFile('add');">Export to CSV</a> &nbsp;&nbsp;
                                  </c:if>
							</td>
						</tr>
					</table>
					<table border="0" width="100%">
						<tr>
							<td><peps:table tableId="fdbAddTable"
									dataModel="${flowScope.fdbAddStateBean.pendingList}" printTemplate="${flowScope.fdbAddStateBean.printTemplate}"
									disableMultiselectButton="true"									
									multiselectButtonKeys="${flowScope.fdbAddStateBean.buttonKeys}"
									multiselectButtonActions="${flowScope.fdbAddStateBean.buttonActions}"
									multiselectButtonMethods="${flowScope.fdbAddStateBean.buttonMethods}"
									nonMultiselectButtonKeys="${flowScope.fdbAddStateBean.addlButtonKeys}"
									nonMultiselectButtonActions="${flowScope.fdbAddStateBean.addlButtonActions}"
									nonMultiselectButtonMethods="${flowScope.fdbAddStateBean.addlButtonMethods}" />
							 </td>
						</tr>
					</table> 
				</td>
			</tr>
		</table>
	</fieldset>
</c:if>

