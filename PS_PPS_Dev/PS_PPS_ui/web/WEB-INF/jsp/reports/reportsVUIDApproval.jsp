<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" type="text/css" href="css/reports.css" />

<div class="row">
	<div class="panel">
		<div class="controlGroup">
			<table cellpadding="5" width="100%">
				<tr>
					<td>
						<div class="verticalspacer"></div>
						<div class="instructionalText">
							<spring:message scope="page" var="printFriendlyVersion"
								code="print.friendly.version" />
							<peps:link
					                id="reportPopup"
					                event="reports"
					                requestParameter="true" 
					                isFirstRun="false"                                                        
					                text="${printFriendlyVersion}"
					                onclick="return popupWithOptions(this.href, 'print_report', 'resizable=yes,scrollbars=yes');" />
						</div>


						<table width="100%" border="1" cellspacing="10" cellpadding="10">
							<tr>
								<td width="100%" valign="top">
									<table cellpadding="5" width="100%">
										<tr>
											<td><big> <strong><c:out
															value="${selectedReportType}" /> </strong> </big></td>
										</tr>
										<tr>
											<td><strong>Date Range:</strong>&nbsp;&nbsp; <fmt:formatDate
													type="both" dateStyle="short" pattern="MM/dd/yyyy"
													value="${startDate}" /> - <fmt:formatDate type="both"
													dateStyle="short" pattern="MM/dd/yyyy" value="${endDate}" />
											</td>
										</tr>
										<tr>
											<td>
												<div class="horizontalspacer"></div>
												<div style="width: 100%;">
													<table id="reportsTable" frame="box"
														style="width: 100%; cellpadding: 5;" rules="cols"
														class="reportsTable">
														<c:choose>
															<c:when test="${empty productList}">
																<tr>
																	<td><font size="1" color="black"><b>No
																				New Products Found</b> </font></td>
																</tr>
															</c:when>
															<c:otherwise>
																<strong>New Products</strong>
																<peps:table tableId="tableNewProduct"
																	dataModel="${productList}"
																	printTemplate="<%=ControllerConstants.VUID_NEW_PRODUCT_PRINT_TEMPLATE%>"
																	paged="false" />
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${empty modifiedProductList}">
																<tr>
																	<td><font size="1" color="black"><b>No
																				Product Inactivations/Reactivations Found</b> </font>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>

																<strong>Inactivated/Reactivated Products</strong>
																<peps:table tableId="tableModifiedProduct"
																	dataModel="${modifiedProductList}"
																	printTemplate="<%=ControllerConstants.VUID_MODIFIED_PRODUCT_PRINT_TEMPLATE%>"
																	paged="false" />

															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${empty ingredientList}">
																<tr>
																	<td><font size="1" color="black"><b>No
																				New Ingredients Found</b> </font></td>
																</tr>
															</c:when>
															<c:otherwise>
																<tr>
																	<td><strong>New Ingredients</strong> <peps:table
																			tableId="tableNewIngredient"
																			dataModel="${ingredientList}"
																			printTemplate="<%=ControllerConstants.VUID_NEW_INGREDIENT_PRINT_TEMPLATE%>"
																			paged="false" /></td>
																</tr>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${empty genericList}">
																<tr>
																	<td><font size="1" color="black"><b>No
																				New Generic Names Found</b> </font></td>
																</tr>
															</c:when>
															<c:otherwise>
																<tr>
																	<td><strong>New Generic Names</strong> <peps:table
																			tableId="tableGenericName" dataModel="${genericList}"
																			printTemplate="<%=ControllerConstants.VUID_GENERIC_NAME_PRINT_TEMPLATE%>"
																			paged="false" /></td>
																</tr>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${empty drugList}">
																<tr>
																	<td><font size="1" color="black"><b>No
																				New Drug Classes Found</b> </font></td>
																</tr>
															</c:when>
															<c:otherwise>
																<tr>
																	<td><strong>New Drug Classes</strong> <peps:table
																			tableId="tableDrugClass" dataModel="${drugList}"
																			printTemplate="<%=ControllerConstants.VUID_DRUG_CLASS_PRINT_TEMPLATE%>"
																			paged="false" /></td>
																</tr>
															</c:otherwise>
														</c:choose>
													</table>
												</div></td>
										</tr>
									</table></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>
	</div>
</div>
