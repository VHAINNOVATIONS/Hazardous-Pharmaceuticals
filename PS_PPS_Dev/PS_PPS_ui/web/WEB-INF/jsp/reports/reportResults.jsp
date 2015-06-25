<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" type="text/css" href="css/reports.css" />
<c:choose>
	<c:when test="${items eq null}">
		<div class="row">
			<div class="panel">
				<div class="controlGroup">
					<table cellpadding="5" width="100%">
						<tr>
							<td>
								<div class="verticalspacer"></div> <c:if
									test="${reportVo.printable}">
									<div class="instructionalText">
										<spring:message scope="page" var="printFriendlyVersion"
											code="print.friendly.version" />
										<peps:link id="reportPopup" event="reports.update"
											requestParameter="true" isFirstRun="false"
											hasEntityChanged="false" text="${printFriendlyVersion}"
											onclick="return popupWithOptions(this.href, 'print_report', 'resizable=yes,scrollbars=yes');" />
									</div>
								</c:if> <c:if test="${showPrint}">
									<a href="javascript:window.print()"><spring:message
											code="print" /> </a>
								</c:if>
								<table cellpadding="5" width="100%">
									<tr>
										<td><big> <strong><c:out
														value="${selectedReportType}" /> </strong> </big>
										</td>
									</tr>
									<tr>
										<td><c:if test="${ reportVo.hasStart }">
												<strong>Start Date:</strong>&nbsp;&nbsp; <fmt:formatDate
													type="date" dateStyle="short" pattern="MM/dd/yyyy"
													value="${reportVo.startDate}" />
											</c:if> <c:if test="${ reportVo.hasStop }">&nbsp;&nbsp;<strong>Stop
													Date:</strong>&nbsp;&nbsp; <fmt:formatDate type="both"
													dateStyle="short" pattern="MM/dd/yyyy"
													value="${reportVo.stopDate}" />
											</c:if>
										</td>
									</tr>

									<c:choose>

										<c:when test="${isVUIDResults}">
											<tr>
												<td>
											<tr>
												<td><strong>New Products</strong> <peps:table
														tableId="tableNewProduct"
														dataModel="${reportVo.reportVuidProductList}"
														printTemplate="<%=ControllerConstants.VUID_NEW_PRODUCT_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>Inactivated/Reactivated Products</strong> <peps:table
														tableId="tableModifiedProduct"
														dataModel="${reportVo.reportVuidModifiedList}"
														printTemplate="<%=ControllerConstants.VUID_INACTIVE_PRODUCT_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>New Ingredients</strong> <peps:table
														tableId="tableNewIngredient"
														dataModel="${reportVo.reportVuidIngredientList}"
														printTemplate="<%=ControllerConstants.VUID_NEW_INGREDIENT_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>Inactivated/Reactivated
														Ingredients</strong> <peps:table tableId="tableModifiedIngredients"
														dataModel="${reportVo.reportVuidModifiedIngredientList}"
														printTemplate="<%=ControllerConstants.VUID_INACTIVE_INGREDIENT_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>New Generic Names</strong> <peps:table
														tableId="tableGenericName"
														dataModel="${reportVo.reportVuidGenericList}"
														printTemplate="<%=ControllerConstants.VUID_GENERIC_NAME_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>Inactivated/Reactivated Generic
														Items</strong> <peps:table tableId="tableModifiedGeneric"
														dataModel="${reportVo.reportVuidModifiedGenericList}"
														printTemplate="<%=ControllerConstants.VUID_MODIFIED_GENERIC_NAME_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>
											<tr>
												<td><strong>New Drug Classes</strong> <peps:table
														tableId="tableDrugClass"
														dataModel="${reportVo.reportVuidDrugClassList}"
														printTemplate="<%=ControllerConstants.VUID_DRUG_CLASS_PRINT_TEMPLATE%>"
														paged="false" /></td>
											</tr>

										</c:when>
										<c:otherwise>
											<c:if test="${isGeneralResults}">
												<tr>
													<td><peps:table tableId="reportTable"
															dataModel="${reportVo.reportProductList}"
															printTemplate="${printTemplate}" paged="false" />
													</td>
												</tr>
											</c:if>
											<c:if test="${isDrugClassResults}">
												<tr>
													<td>
														<table id="reportsTable" frame="box"
															style="width: 100%; cellpadding: 5;" rules="groups"
															class="reportsTable">
															<c:choose>
																<c:when test="${empty reportVo.reportDrugClassList}">
																	<tr>
																		<td><font size="1" color="black"><b>No
																					Results Found</b> </font>
																		</td>
																	</tr>
																</c:when>
																<c:otherwise>
																
																	<c:forEach var="result"
																		items="${reportVo.reportDrugClassList}" begin="0"
																		varStatus="status">
																		<tr>
																			<td style="width: 55px"><strong><c:out
																				value="${result.code}" /></strong></td>
																			<td colspan="5"><strong><c:out
																				value="${result.classification}" /> </strong></td>
																		</tr>
																    	
																		<c:if test="${reportVo.description}">

																			<tr>
																				<td style="width: 55px; valign: top;"></td>
																				<td colspan="5"><c:out
																						value="${result.description}" /></td>
																			</tr>
																		</c:if>
																		<c:forEach var="resultSecondary"
																			items="${result.secondaryDrugClasses}" begin="0"
																			varStatus="secondaryStatus">
																			<c:set scope="page" var="index"
																				value="${secondaryStatus.index}" />
																			<tr>
																				<td style="width: 55px"></td>
																				<td style="width: 55px"><strong><c:out
																							value="${resultSecondary.code}" /> </strong></td>
																				<td colspan="4"><strong><c:out
																							value="${resultSecondary.classification}" /> </strong></td>
																			</tr>
																			<c:forEach var="resultTertiary"
																				items="${resultSecondary.tertiaryDrugClasses}"
																				begin="0" varStatus="tertiaryStatus">
																				<tr>
																					<td style="width: 55px"></td>
																					<td></td>
																					<td style="width: 55px"><c:out
																							value="${resultTertiary.code}" /></td>
																					<td><c:out
																							value="${resultTertiary.classification}" /></td>
																				</tr>
																			</c:forEach>
																		</c:forEach>
																	</c:forEach>
																</c:otherwise>
															</c:choose>
														</table>
													</td>
												</tr>
											</c:if>
										</c:otherwise>
									</c:choose>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</c:when>
</c:choose>