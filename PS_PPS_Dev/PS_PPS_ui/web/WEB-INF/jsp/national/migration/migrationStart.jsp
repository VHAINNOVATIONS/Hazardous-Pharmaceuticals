<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page
	import="gov.va.med.pharmacy.peps.service.common.migration.ExportCSVFileData"%>
<!-- TODO look into breadcrum -->
<script src="/PRE/javascript/migrationScripts.js" type="text/javascript"></script>
<script src="/PRE/javascript/progressBar.js" type="text/javascript"></script>

<script type="text/javascript">
	var record = document.getElementById("recordId");
	if(record != null && record != undefined) {
		record.style.visibility = 'hidden';
	}
</script>

<body onload="enableDownloadButton();">

	<div class="panel">
		<form id="migrationForm" name="migrationForm"
			enctype="multipart/form-data" action="validateFiles.go" method="POST">
			<table width="1250" border="1" cellspacing="10" cellpadding="10">
				<tr>
					<td colspan="2">
						<h1 style="font-size: x-large" align="center">Migration Start</h1>
					</td>
				</tr>
				<tr>
					<td width="550" valign="top">
						<div style="border: 1px solid black;">
							<table cellpadding="5">
								<tr>
									<td><big> <strong>Back-Up Current Database</strong>
									</big>
									</td>
								</tr>
								<tr>
									<td>After a migration has been completed, you may save the
										current database in the form of CSV files. These files can be
										used to start future migrations.</td>
								</tr>
								<tr>
									<td>
										<!-- ${migrationState.migrationComplete == true} --> <c:choose>
											<c:when test="true">
												<c:choose>
													<c:when
														test="${exportState.exportComplete == true && !exportState.running}">
														<input id="filebutton" type="button"
															value="Start Download" onclick="startDownload();">
														<input disabled id="status" type="button" value="Status"
															onclick="getExportStatus();">
														<c:if test="${!empty exportFiles}">
															<select id="csvFile">
																<c:forEach var="exportFile" items="${exportFiles}">
																	<option value="${exportFile}">${exportFile.fileDescription}</option>
																</c:forEach>
															</select>
															<input id="save" type="button" value="Save Exports"
																onclick="saveDownloadFile();">
														</c:if>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${exportState.running}">
																<input disabled id="filebutton" type="button"
																	value="Start Download" onclick="startDownload();">
																<input id="status" type="button" value="Status"
																	onclick="getExportStatus();">
																<br />
																<br />
																<div id="recordId"
																	style="border: 1px solid black; padding: 3px">
																	Reading NDC:
																	<fmt:formatNumber
																		value="${exportState.ndcRecordCounter / exportState.ndcMaxRecords}"
																		type="percent" />
																	<br /> Reading Orderable Items:
																	<fmt:formatNumber
																		value="${exportState.oiRecordCounter / exportState.oiMaxRecords}"
																		type="percent" />
																	<br /> Reading Products:
																	<fmt:formatNumber
																		value="${exportState.productRecordCounter / exportState.productMaxRecords}"
																		type="percent" />
																</div>
																<br />
															</c:when>
															<c:otherwise>
																<input id="filebutton" type="button"
																	value="Start Download" onclick="startDownload();">
																<input disabled id="status" type="button" value="Status"
																	onclick="getExportStatus();">
																<c:if test="${!empty exportFiles}">
																	<select id="csvFile">
																		<c:forEach var="exportFile" items="${exportFiles}">
																			<option value="${exportFile}">${exportFile.fileDescription}</option>
																		</c:forEach>
																	</select>
																	<input id="save" type="button" value="Save Exports"
																		onclick="saveDownloadFile();">
																</c:if>
															</c:otherwise>
														</c:choose>

													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<input disabled id="filebutton" type="button"
													value="download" onclick="startDownload();">
												<input disabled id="status" type="button" value="Status"
													onclick="getExportStatus();">
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div> <br>
						<div style="border: 1px solid black;">
							<table cellpadding="5">
								<tr>
									<td width="550" valign="top"><big> <strong>Domain
												Mapping</strong> </big><br> <br> <input type="button"
										value="Export Domain Mapping" onclick="exportMapping();">
									</td>
								</tr>
								<tr>
									<td>Domain Mapping File: 
									<input id="domainMappingFile" type="file" name="domainMappingFile"> 
									<input type="button" name="importDomainMapping" value="Start Import"
										onclick="importMapping(); " /><br> 
<%-- 										<c:if test="${processRunning}"> --%>
<!-- 											<strong>The import process is currently running.</strong> -->
<%-- 										</c:if> --%>
									</td>
								</tr>
							</table>
						</div>
					<td><div style="border: 1px solid black;">
							<table border="0" cellspacing="5" cellpadding="5" width="500">
								<tr>
									<td colspan="2" style="color: red"><c:if
											test="${responseMessage ne ''}">
											<c:out value="${responseMessage.responseMessage}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td colspan="2"><big> <strong>Configure
												Migration</strong> </big>
									</td>
								</tr>

								<tr>
									<td align="right"><label style="color: red">* </label><label>NDC
											File:</label></td>
									<td><input id="ndcFile" type="file" name="ndcFile" />
									</td>
								</tr>
								<tr>
									<td align="right">Orderable Item File:</td>
									<td><input id="oiFile" type="file" name="oiFile" />
									</td>
								</tr>
								<tr>
									<td align="right">Product File:</td>
									<td><input id="productFile" type="file" name="productFile" />
									</td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2"><input type="button" name="Start"
										value="Start Migration" onclick="startMigration(); " />
									</td>

								</tr>
								<tr>
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="right"><label style="color: red">*
									</label><label>Required Fields</label>
									</td>
								</tr>
							</table>
						</div> <input type="hidden" name="runStatus" value="true" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>