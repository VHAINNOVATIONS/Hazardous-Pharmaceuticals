<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<script src="/PRE/javascript/fdbUpdateReports.js" type="text/javascript"></script>
<script src="/PRE/javascript/fdbUpdate.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="css/fdbupdate.css" />

<form:form modelAttribute="fdbUpdateBean" id="addFdbForm"
	name="addFdbForm" action="fdbAdd.go" method="GET"
	onsubmit="return disableSubmit(this.id);">
	<div class="horizontalspacer"></div>
	<c:if test="${!empty errorMessages}">
		<div id="errorMessages">
			<font size="4" color="Red"><c:out value="${errorMessages}" />
			</font>
		</div>
	</c:if>

	<c:choose>
		<c:when test="${!empty flowScope.fdbAddStateBean.autoAddList}">
			<table style="width: 950px;">
				<tr>
					<td align="right"><a href="#" id="downloadCSV"
						name="downloadCSV" onclick="downloadCSVFile('autoAdd');">Export
							to CSV</a> &nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td align="right">Record Count: <c:out
							value="${fn:length(flowScope.fdbAddStateBean.autoAddList)}" /></td>
				</tr>
			</table>
			<spring:message code="button.delete" var="deleteButtonText"/>
			<table border="0" width="100%">
				<tr>
					<td><peps:table 
							tableId="fdbAddReportTable"
							dataModel="${flowScope.fdbAddStateBean.autoAddList}" 
							printTemplate="${printTemplate}"							
							multiselectButtonKey="${deleteButtonText}"
							multiselectButtonAction="fdbAutoAddDelete.go"
							multiselectButtonMethod="POST" />
					</td>
				</tr>
			</table>			
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td align="center"><font size="4" color="red"><b>No
								Results Found</b>
					</font></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</form:form>
