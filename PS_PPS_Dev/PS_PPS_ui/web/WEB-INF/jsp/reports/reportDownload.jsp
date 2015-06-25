<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>
<%@ page
	import="gov.va.med.pharmacy.peps.service.common.reports.ReportExportType"%>


<div class="panel">
	<table width="1250" border="1" cellspacing="10" cellpadding="10">
		<tr>
			<td width="550" valign="top">
				<div style="border: 1px solid black;">
					<table cellpadding="5">
						<tr>
							<td><big> <strong><c:out
											value="${selectedReportType}" /> Download Status</strong> </big>
							</td>
						</tr>
						<tr>
							<td>Once reports are generated, they will be available for
								download.</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${reportVo.generateOn}">
									<td><strong>Process Status: </strong>&nbsp;&nbsp;Not
										Running</td>
								</c:when>
								<c:otherwise>
									<td><strong>Process Status: </strong>&nbsp;&nbsp;Running (${reportVo.recordCount} of ${reportVo.recordTotal})</td>
								</c:otherwise>
							</c:choose>
<%-- 							<td><strong>Records: </strong>&nbsp;&nbsp; ${reportVo.recordCount} of ${reportVo.recordTotal}</td> --%>
						</tr>
						<tr>

							<td><strong>Started: </strong>&nbsp;&nbsp; <fmt:formatDate
									type="date" dateStyle="short" pattern="yyyy-MM-dd hh:mm:ss"
									value="${reportVo.csvStart}" /></td>
							<c:if test="${ reportVo.generateOn }">
								<td><strong>Completed: </strong>&nbsp;&nbsp;<fmt:formatDate
										type="date" dateStyle="short" pattern="yyyy-MM-dd hh:mm:ss"
										value="${reportVo.csvComplete}" /></td>
							</c:if>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>