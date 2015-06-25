<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<br/>	

	<table width="900" border="0">
			<tr>
				<td colspan="4" align="center">
					<h1 style="font-size: x-large">
						<strong>Migration Report</strong>
					</h1>
				</td>
			</tr>
			<!--  start  -->
			<c:choose>
				<c:when test="${singleReport == true}">
						<tr>
							<td></td>
							<td align="center">
								<table width="1000" border="1" cellspacing="0" cellpadding="3">
									<tr>
										<td colspan="4" align="center">
											<h1 style="font-size: large">
												<strong>Migration Details for ${domain.name}</strong>
											</h1>
										</td>
									</tr>
									<tr>
										<td align="center">
											<strong>Total Records Processed</strong>
										</td>
										<td align="center">
											<strong>Total Errors</strong>
										</td>
										<td align="center">
											<strong>Duplicates Not Migrated</strong>
										</td>
										<td align="center">
											<strong>Total Migrated</strong>
										</td>
									</tr>
									<tr>
										<td align="center">${domain.count}</td>
										<td align="center">${domain.totalErrors}</td>
										<td align="center">${domain.duplicatesNoMigrated}</td>
										<td align="center">${domain.totalMigrated}</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--  Error details -->
						<tr>
							<td>
							</td>
							<td align="center">
								<table width="1000" border="1" cellspacing="0" cellpadding="3">
											<tr>
												<td colspan="7" align="center">
													<h1	style="font-size: large">
														<strong>Error Details</strong>
													</h1>
												</td>
											</tr>
											<tr>
												<td align="center">
													<strong>
														<c:out value="IEN/Row" />
													</strong>
												</td>
												<td align="center">
													<strong>Item Name</strong>
												</td>
												<td align="center">
													<strong>Field</strong>
												</td>
												<!--  show these 2 columns if Drug Dosage Form or Va Product  -->
												<c:if test="${domain.name eq 'Dosage Form' or domain.name eq 'VA Product'}">												
														<td align="center">
															<strong>Multiple IEN</strong>
														</td>
														<td align="center">
															<strong>Multiple field</strong>
														</td>
												</c:if>
																							
												<td align="center">
													<strong>Field Value</strong>
												</td>
												<td align="center">
													<strong>Reason for Error</strong>
												</td>
											</tr>
											<c:forEach var="error" items="${domain.errorDetailList}">
											<tr>
												<td align="center">
													<c:choose>
														<c:when test="${error.ien == ''}">
															N/A
														</c:when>
														<c:otherwise>
														   <c:out value="${error.ien}" />
														</c:otherwise>
													</c:choose>
												</td>
												<td align="center">
													   <c:out value="${error.itemName}" /> 
												</td>
												<td align="center">
													   <c:out value="${error.field}" /> 
												</td>
												<!--  show these 2 columns if Drug Dosage Form or Va Product  -->
												<c:if test="${domain.name eq 'Dosage Form' or domain.name eq 'VA Product'}">												
														<td align="center">
															  <c:out value="${error.multipleIen}" /> 
														</td>
														<td align="center">
															  <c:out value="${error.multipleField}" /> 
														</td>
												</c:if>
															
												
												<td align="center">
													   <c:out value="${error.fieldValue}" /> 
												</td>
												<td align="center">
													   <c:out value="${error.reasonForError}" /> 
												</td>
												
											</tr>
											</c:forEach>
											<tr>
										</tr>
								</table>
							</td>
						</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="migratedDomains" items="${migrationState.migratedDomainList}">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td></td>
					<td align="center">
						<table width="1000" border="1" cellspacing="0" cellpadding="3">
							<tr>
								<td colspan="4" align="center"><h1
										style="font-size: large">
										<strong>Migration Details for <c:out
												value="${migratedDomains.name}" />
										</strong>
									</h1>
								</td>
							</tr>
							<tr>
								<td align="center"><strong>Total Records
										Processed</strong>
								</td>
								<td align="center"><strong>Total Errors</strong>
								</td>
								<td align="center"><strong>Duplicates Not
										Migrated</strong>
								</td>
								<td align="center"><strong>Total Migrated</strong>
								</td>
							</tr>
							<tr>
								<td align="center">${migratedDomains.count}</td>
								<td align="center">${migratedDomains.totalErrors}</td>
								<td align="center">${migratedDomains.duplicatesNoMigrated}</td>
								<td align="center">${migratedDomains.totalMigrated}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="center">
						<table width="1000" border="1" cellspacing="0" cellpadding="3">
							<tr>
								<td colspan="7" align="center"><h1
										style="font-size: large">
										<strong>Error Details</strong>
									</h1>
								</td>
							</tr>
							<tr>
								<td align="center"><strong><c:out
											value="IEN/Row" />
								</strong>
								</td>
								<td align="center"><strong>Item Name</strong>
								</td>
								<td align="center"><strong>Field</strong>
								</td>
								<!--  show these 2 columns if Drug Dosage Form or Va Product  -->
								<c:if test="${migratedDomains.name eq 'Dosage Form' or migratedDomains.name eq 'VA Product'}">												
									<td align="center">
										<strong>Multiple IEN</strong>
									</td>
									<td align="center">
										<strong>Multiple field</strong>
									</td>
								</c:if>
								<td align="center"><strong>Field Value</strong>
								</td>
								<td align="center"><strong>Reason for Error</strong>
								</td>
							</tr>
							<c:forEach var="error" items="${migratedDomains.errorDetailList}">
							<tr>
								<td align="center">
									<c:choose>
										<c:when test="${error.ien == ''}">
											N/A
										</c:when>
										<c:otherwise>
										   <c:out value="${error.ien}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td align="center">
									   <c:out value="${error.itemName}" /> 
								</td>
								<td align="center">
									   <c:out value="${error.field}" /> 
								</td>
								<!--  show these 2 columns if Drug Dosage Form or Va Product  -->
								<c:if test="${migratedDomains.name eq 'Dosage Form' or migratedDomains.name eq 'VA Product'}">												
										<td align="center">
											  <c:out value="${error.multipleIen}" /> 
										</td>
										<td align="center">
											  <c:out value="${error.multipleField}" /> 
										</td>
								</c:if>
								<td align="center">
									   <c:out value="${error.fieldValue}" /> 
								</td>
								<td align="center">
									   <c:out value="${error.reasonForError}" /> 
								</td>
								
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			 </c:forEach>
		</c:otherwise>
	</c:choose>
			
	</table>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	