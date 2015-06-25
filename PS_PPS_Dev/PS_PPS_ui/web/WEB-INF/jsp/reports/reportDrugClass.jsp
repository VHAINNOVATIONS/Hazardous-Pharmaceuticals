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
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
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
															<strong>WALKEr<c:out value="${selectedReportType}"/></strong>	
														</big>
													</td>
												</tr>
												<c:if test="${showDateCriteria}">
													<tr>
														<td>
															<strong>Date Range:</strong>&nbsp;&nbsp;
															<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${startDate}" /> - 
															<fmt:formatDate type="both" dateStyle="short" pattern="MM/dd/yyyy" value="${endDate}" />
														</td>
													</tr>
												</c:if>
												<tr>
													<td>
														<div class="horizontalspacer"></div>
														<div style="overflow:auto;width:100%;">
															<table id="reportsTable" frame="box" style="width:100%;cellpadding:5;" rules="groups" class="reportsTable">
																<c:choose>
																	<c:when test="${empty drugClassResultsList}">
																	     <tr>
																	        <td>
																	            <font size="1" color="black"><b>No Results Found</b></font>
																	        </td>
																	     </tr>
																	</c:when>
																	<c:otherwise> 
																		<c:forEach var="result" items="${drugClassResultsList}" begin="0" varStatus="status">
																			<thead> 
																		    <tr>
																	            <td style="width: 55px"><strong><c:out value="${result.code}" /></strong></td>
																	            <td colspan="5"><strong><c:out value="${result.classification}" /></strong></td>  
<%-- 																	            <td><strong><c:out value="${result.classType}" />, <c:out value="${result.parentClassType}" />, <c:out value="${result.secondaryDrugClasses}" /></strong></td>     --%>
																		   </tr>
																		   </thead>
																		   <tr>
																	            <td style="width: 55px;valign:top;"></td>
																	            <td colspan="5"><c:out value="${result.description}" /></td>    
																		        
																		   </tr>
															
																		   <c:forEach var="resultSecondary" items="${result.secondaryDrugClasses}" begin="0" varStatus="secondaryStatus">
																			   <c:set
																			       scope="page"
																			       var="index"
																			       value="${secondaryStatus.index}" />
																			   <tr>
																		            <td style="width: 55px"></td>
																		            <td style="width: 55px"><strong><c:out value="${resultSecondary.code}"/></strong></td>    
																			        <td colspan="4"><strong><c:out value="${resultSecondary.classification}"/></strong></td> 
																			   </tr>
																			    <c:forEach var="resultTertiary" items="${resultSecondary.tertiaryDrugClasses}" begin="0" varStatus="tertiaryStatus">
																				   <tr>
																			            <td style="width: 55px"></td>
																			            <td></td>    
																				        <td style="width: 55px"><c:out value="${resultTertiary.code}"/></td> 
																				        <td><c:out value="${resultTertiary.classification}"/></td> 
																				   </tr>
																				</c:forEach>
																		   </c:forEach>

																		   
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