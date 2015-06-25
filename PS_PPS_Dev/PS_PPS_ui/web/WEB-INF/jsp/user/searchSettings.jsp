<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">

 function refreshpage()
 {
 	window.location.reload(true);
 }
 
</script>

<body onload="document.refresh();">
</body>
<div class="body-content">
	<div class="horizontalspacer"></div>
	<form:form id="searchPreferences"  
		modelAttribute="searchManager"
		onsubmit="return disableSubmit(this.id);" method="GET" >
		<spring:message code="label.select.template" />:
			<form:select title="${currentPrintTemplateDesc}"
			path="currentPrintTemplateId" onChange="this.form.submit();" >
			<form:options items="${availablePrintTemplates}" />
		</form:select>
		
		<br />
		<br />
		<br />
		
		<fieldset>
			<legend><spring:message code="header.personalize" /></legend>
				<div class="row">
					<div class="panel">
						<div class="controlGroup">
							<table cellpadding="5" width="100%" >
								<tr>
									<td>
										<div class="row">
											<div class="horizontalspacer"></div>
											<div class="panel">
												<div class="column">
													<spring:message code="label.available.fields" var="availableFields"/>
													<strong>${availableFields}<span>:</span>
													</strong><br> <br>
													<div>
														<!-- Building the Available Fields box -->
														<form:select title="${availableFields}"
															id="availableOptions" multiple="true"
															cssStyle="height:300px;" path="" cssClass="comboBox">
															<c:choose>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_PRODUCT_SEARCH'}">
																	<form:options items="${availableProductColumns}" />
																</c:when>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_ORDERABLE_ITEM_SEARCH'}">
																	<form:options items="${availableOrderableItemColumns}" />
																</c:when>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_NDC_SEARCH'}">
																	<form:options items="${availableNdcColumns}" />
																</c:when>
															</c:choose>
														</form:select>
													</div>
												</div>
												<div class="column">
													<div class="horizontalspacer"></div>
													<br>
													<div>
														<spring:message code="button.right.arrow"
															var="rightButton" />
														<spring:message code="button.left.arrow" var="leftButton" />

														<input id="button.right.arrow"
															class="optionTransferSelectButton" type="button"
															onclick="listbox_moveacross('availableOptions','selectedOptions','selectedItems','addedItems','displayedItems','add');"
															value="${rightButton}" /> <br> <br> <input
															id="button.left.arrow" class="optionTransferSelectButton"
															type="button"
															onclick="listbox_moveacross('selectedOptions','availableOptions','selectedItems','addedItems','displayedItems','remove');"
															value="${leftButton}" />
													</div>
												</div>
												<div class="column">
													<spring:message code="label.selected.fields" var="selectedFields"/>
													<strong>${selectedFields}<span>:</span>
													</strong><br> <br>
													<form:input type="hidden" path="displayedList"
														id="displayedItems" value="" />
													<form:input type="hidden" path="selectedKeys" id="selectedItems" value="" />
													<form:input type="hidden" path="addedKeys" id="addedItems" value="" />												
													<div>
														<!-- Building the Selected Fields box -->
														<form:select title="${selectedFields}" id="selectedOptions"
															multiple="true" cssStyle="height:300px;" path=""
															cssClass="comboBox">
															<c:choose>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_ORDERABLE_ITEM_SEARCH'}">
																	<form:options items="${orderableItemFieldsMap}" />
																</c:when>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_NDC_SEARCH'}">
																	<form:options items="${ndcFieldsMap}" />
																</c:when>
																<c:when
																	test="${searchPreferences.currentPrintTemplateId == 'PERSONAL_PRODUCT_SEARCH'}">
																	<form:options items="${productFieldsMap}" />
																</c:when>
															</c:choose>
														</form:select>
													</div>
												</div>

												<div class="column">
													<div class="horizontalspacer"></div>
													<br>
													<div>
														<spring:message code="button.up.arrow" var="upButton" />
														<spring:message code="button.down.arrow" var="downButton" />
														<input title="Move Up" id="button.up.arrow"
															class="optionTransferMoveButton" type="button"
															onclick="listbox_move('selectedOptions','up');"
															value="${upButton}" name=""><br> <br> 
														<input
															title="Move Down" id="button.down.arrow"
															class="optionTransferMoveButton" type="button"
															onclick="listbox_move('selectedOptions','down');"
															value="${downButton}" name="">
													</div>
												</div>
												<div class="horizontalspacer"></div>
												<div class="row">
													<br> <br>
													<spring:message code="search.prefs.note"/><br /><br />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<peps:submit key="button.submit" 
														onclick="prepareDisplayed(); refreshpage(); " />
												</div>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
		</fieldset>	
	</form:form>
</div>