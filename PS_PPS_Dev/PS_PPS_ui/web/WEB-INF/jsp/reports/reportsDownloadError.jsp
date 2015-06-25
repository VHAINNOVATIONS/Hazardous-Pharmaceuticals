<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<%@ page 
	import="gov.va.med.pharmacy.peps.service.common.reports.ReportExportType" %>


<div class="panel">
	<form id="reportDownloadForm" name="reportDownloadForm" enctype="multipart/form-data" action="reports.download.go" method="POST">
		<table width="1250" border="1" cellspacing="10" cellpadding="10">
			<tr>
				<td width="550" valign="top">
					<div style="border: 1px solid black;">
						<table  cellpadding="5">
							<tr>
								<td>
									<big>
										<strong><c:out value="${selectedReportType}"/> Download Error</strong>	
									</big>
								</td>
							</tr>
							<tr>
								<td>
									The requested file is not available.  Please check the report status or generate new files to download.
								</td>
							</tr>
							
						</table>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>