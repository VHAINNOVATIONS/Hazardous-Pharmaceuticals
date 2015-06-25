<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib 
	prefix="peps" 
	tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib
    prefix="fmt" 
    uri="http://java.sun.com/jsp/jstl/fmt"  %>   
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>  

<link rel="stylesheet" type="text/css" href="css/reports.css" />
<c:choose>
	<c:when test="${items eq null}">
		<div class="row">
			<div class="panel">
				<div class="controlGroup">
					<table  cellpadding="5" width="100%">
						<tr>
							<td>	
								<c:if test="${isPrintable}">
									<div class="verticalspacer"></div>
			 						<div class="instructionalText">
								    	<spring:message 
								    		scope="page" 
								    		var="printFriendlyVersion" 
								    		code="print.friendly.version" />
								    	<peps:link
								                id="pmiPopup"
								                event="pmi"
								                requestParameter="true" 
								                isFirstRun="false"                                                        
								                text="${printFriendlyVersion}"
								                onclick="return popupWithOptions(this.href, 'print_pmi', 'resizable=yes,scrollbars=yes');" />
								    </div>
								</c:if>
								<table width="100%" border="1" cellspacing="10" cellpadding="10">
									<tr>
										<td width="100%" valign="top">
											<table  cellpadding="5" width="100%">
												<tr>
													<td>
														<big>	
															<strong><spring:message code="ReportsType.PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE"/> Report</strong></strong>
														</big>
													</td>
												</tr>
												<tr>
													<td>
														<strong>Date Range:</strong>&nbsp;&nbsp;
														<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${startDate}" /> - 
														<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${endDate}" />
													</td>
												</tr>
												<tr>
													<td>
														<div class="horizontalspacer"></div>
														<div style="overflow:auto;width:100%;">
															<table id="reportsTable" frame="box" style="width:100%;cellpadding:5;"  rules="cols" class="reportsTable">
																<c:choose>
																	<c:when test="${empty resultsList}">
																	     <tr>
																	        <td>
																	            <font size="1" color="black"><b>No Results Found</b></font>
																	        </td>
																	     </tr>
																	</c:when>
																	<c:otherwise> 
																		<thead>
																	 		<tr>
																		         <td style="width: 55px"><strong>Product Name</strong></td>
																		         <td style="width: 80px;"><strong>Excluded</strong></td>
																		     </tr>
																		 </thead> 
																		<c:forEach var="result" items="${resultsList}" begin="0" varStatus="status">
																	       <tr>
																	            <td style="width:55px"><c:out value="${result.productName}" /></td>    
																		        <td  style="width: 80px"><c:out value="${result.excludeInteractionCheck}" />
																		        </td>
																		   </tr>
																        </c:forEach>
																	</c:otherwise>
																</c:choose>
									    					</table>
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</c:when>
</c:choose>