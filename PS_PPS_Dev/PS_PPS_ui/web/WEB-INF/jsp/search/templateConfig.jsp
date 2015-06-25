<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags"%>

<%@ taglib 
	prefix="form" 
	uri="http://www.springframework.org/tags/form"%>

<%@ taglib 
    prefix="fn" 
    uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.SearchTermVo"%>

<%@page 
	import="java.util.List"%>

<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.ProductVo"%>

<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>

<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.EntityType"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>

<%@page
	import="gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField"%>

<%@page
	import="gov.va.med.pharmacy.peps.presentation.common.utility.ApplicationContextUtility"%>
   
<%@page 
	import="gov.va.med.pharmacy.peps.common.vo.ValueObject"%>
	

<div class="row">
		<div class="panel">
			<div class="controlGroup">
				<table cellpadding="5" width="100%">
					<tr>
						<td>
							<div class="row">
							<c:url value="/manageprinttemplate.submit.go" var="submitUrl"/>
							<c:url value="/manageprinttemplate.cancel.go" var="cancelUrl"/>
							<form:form id="manageSearchTemplates" name="manageSearchTemplates"  
								modelAttribute="manageTemplates" 
								onsubmit="return disableSubmit(this.id);"
								action="${submitUrl}" method="POST">
								
								<div class="warnings" >
									<span class="warningMessage">
										<font class="errorMessage3"><spring:message code="print.template.message"/></font>
									</span>
								</div>
								<div class="horizontalspacer"></div>								
								<div class="panel"> 
									<div class="column">
										<spring:message code="label.available.fields" var="availableFields"/>
										<strong>${availableFields}<span>:</span></strong><br><br>
										<div>
											<form:select 
												title="${availableFields}"
												id="availableOptions"  
										    	multiple="true" 
										    	cssStyle="height:300px;" 
										    	path="" 
										    	cssClass="comboBox">
												<form:options items="${availableFieldsMap}" />
											</form:select>			
										</div>
									</div>
									<div class="column">
										<div class="horizontalspacer"></div><br>
										<div>
										<spring:message code="button.right.arrow" var="rightButton" />
										<spring:message code="button.left.arrow" var="leftButton" />
										
										    <input
							        			id="button.right.arrow" 
							        			class="optionTransferSelectButton"
							        			type="button"										
												onclick="listbox_moveacross('availableOptions','selectedOptions','selectedItems','addedItems','displayedItems','add');"
												value="${rightButton}" 
												/>
											<br><br>
											<input
							        			id="button.left.arrow" 
							        			class="optionTransferSelectButton"	
							        			type="button"											
												onclick="listbox_moveacross('selectedOptions','availableOptions','selectedItems','addedItems','displayedItems','remove');"
												value="${leftButton}"
												/>
										</div>
									</div>
									<div class="column">
										<spring:message code="label.selected.fields" var="selectedFields"/>
										<strong>${selectedFields}<span>:</span></strong><br><br>										
										<form:input type="hidden" path="displayedList" id="displayedItems" />
										<form:input type="hidden" path="selectedKeys" id="selectedItems"/>
										<form:input type="hidden" path="addedKeys" id="addedItems" />
										
										<div>
											<form:select
												title="${selectedFields}"
												id="selectedOptions"
											 	multiple="true"  
										    	cssStyle="height:300px;" 
										    	path="" 
										    	cssClass="comboBox">
												<form:options items="${templateFieldsMap}" />
											</form:select>		
										</div>
									</div>
									<div class="column">
										<div class="horizontalspacer"></div> <br>
										<div>
										
										<spring:message code="button.up.arrow" var="upButton" />
										<spring:message code="button.down.arrow" var="downButton" />
											
											<input 
												title="<spring:message code='button.move.up'/>"
												id="button.up.arrow"
										    	class="optionTransferMoveButton"
												type="button"
												onclick="listbox_move('selectedOptions','up');"
												value="${upButton}" name=""><br><br>
											<input 
												title="<spring:message code='button.move.up'/>"
												id="button.down.arrow"
										    	class="optionTransferMoveButton"
												type="button"
												onclick="listbox_move('selectedOptions','down');"
												value="${downButton}" name="">
										</div>
									</div>
									<div class="horizontalspacer"></div>
									<div class="row">
									<br><br>
										<peps:submit
								        	key="button.submit" 
								        	onclick="prepareDisplayed();" />
							        	<peps:submit key="button.cancel" action="${cancelUrl}" />
									</div>
								</div>
							</form:form> 
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
