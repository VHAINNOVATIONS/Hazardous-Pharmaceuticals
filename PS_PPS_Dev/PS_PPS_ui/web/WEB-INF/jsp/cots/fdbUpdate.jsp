<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

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

<script src="/PRE/javascript/fdbUpdate.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="css/fdbupdate.css" />
<c:if test="${!empty errorMessages}">
	<div id="errorMessages">
		<font size="4" color="Red"><c:out value="${errorMessages}" />
		</font>
	</div>
</c:if>

<fieldset>
	<legend>
		<b>RESULTS</b>
	</legend>
	<table border="0" width="100%">
		<tr>
			<td align="right"><a href="#" id="downloadCSV" name="downloadCSV"
				onclick="downloadCSVFile('update');">Export to CSV</a> &nbsp;&nbsp;</td>
		</tr>
		<tr>
			<spring:message code="button.delete" var="deleteButtonText"/>
			<td><peps:table tableId="fdbUpdateTable"
					dataModel="${flowScope.fdbAddStateBean.updateList}" printTemplate="${printTemplate}"
					multiselectButtonKey="${deleteButtonText}"
					multiselectButtonAction="fdbUpdateDelete.go"
					multiselectButtonMethod="POST" />
			</td>
		</tr>		
	</table>
</fieldset>