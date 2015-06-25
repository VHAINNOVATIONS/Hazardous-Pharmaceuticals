<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="body-content">
	<div class="horizontalspacer"></div>
	<form:form id="userPreferences" action="/PRE/displaySettings.go"
		modelAttribute="preferences" onsubmit="return disableSubmit(this.id);">
		<fieldset>
			<legend>Date/Time Format</legend>
			<div class="row">
				<div class="fourColumn">
					<div class="controlGroup">
						<div class="controlLabel">
							<label id="label.user.sessionPreferences.LONG_DATE_FORMAT"
								class="label"><spring:message code="date.format" />:</label>
						</div>
						<span class="control"> <form:radiobuttons
								id="user.sessionPreferences.LONG_DATE_FORMAT_" path="dateFormat"
								items="${dateFormatMap}" element="div" /> </span>
					</div>
				</div>
				<div class="fourColumn">
					<div class="controlGroup">
						<div class="controlLabel">
							<label id="label.user.sessionPreferences.TIME_FORMAT"
								class="label"><spring:message code="time.format" />:</label>
						</div>
						<span class="control"> <form:radiobuttons
								id="user.sessionPreferences.TIME_FORMAT_" path="timeFormat"
								items="${timeFormatMap}" element="div" /> </span>
					</div>
				</div>
			</div>
		</fieldset>
		<br />
		<fieldset>
			<legend>Default Login Page</legend>
			<div class="fourColumn">
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.HOME_PAGE_FORMAT"
							path="homePageFormat" items="${homePageFormatMap}" element="div" />
					</span>
				</div>
			</div>
		</fieldset>
		<br />
		
		<fieldset>
			<legend>Table Display Format</legend>
			<div class="column">
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.TABLE_ROW_COUNT"
							class="label"> <%=FieldKey.getLocalizedDescription("table.row.count", request.getLocale())%>:</label>
					</div>
					<span class="control"> <form:input
							id="user.sessionPreferences.TABLE_ROW_COUNT" path="tableRowCount"
							size="15" /> &nbsp;(10-100) </span>
				</div>
			</div>
		</fieldset>
		
		<br />
		
		<fieldset>
			<legend>FDB Filter Settings</legend>
			<br />
			<div class="column">
			<fieldset>
			<legend>FDB Status Code Format</legend>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_STATUS_CODE_ACTIVE" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.status.code.active", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_STATUS_CODE_ACTIVE" path="fdbStatusCodeActive" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_STATUS_CODE_RETIRED" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.status.code.retired", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_STATUS_CODE_RETIRED" path="fdbStatusCodeRetired" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_STATUS_CODE_REPLACED" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.status.code.replaced", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_STATUS_CODE_REPLACED" path="fdbStatusCodeReplaced" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_STATUS_CODE_INACTIVE" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.status.code.inactive", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_STATUS_CODE_INACTIVE" path="fdbStatusCodeInactive" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_STATUS_CODE_UNASSOCIATED" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.status.code.unassociated", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_STATUS_CODE_UNASSOCIATED" path="fdbStatusCodeUnassociated" cssClass="" />  
					</span>
				</div>
			</fieldset>
			</div>
			
			<div class="column">
			<fieldset>
			<legend>Other Settings</legend>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_DEVICES" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.devices", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_DEVICES" path="fdbDevices" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_SINGLE_INGREDIENT" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.single.ingredient", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_SINGLE_INGREDIENT" path="fdbSingleIngredient" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_OBSOLETE_DRUGS" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.obsolete.drugs", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_OBSOLETE_DRUGS" path="fdbObsoleteDrugs" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_PRIVATE_LABELERS" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.private.labelers", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_PRIVATE_LABELERS" path="fdbPrivateLabelers" cssClass="" />  
					</span>
				</div>
				<div class="controlGroup">
					<div class="controlLabel">
						<label id="label.user.sessionPreferences.FDB_REPACKAGERS" class="label left checkbox"
							class="label"> <%=FieldKey.getLocalizedAbbreviation("fdb.repackagers", request.getLocale())%>:</label>
					</div>
					<span class="control"> 
						<form:checkbox id="user.sessionPreferences.FDB_REPACKAGERS" path="fdbRepackagers" cssClass="" />  
					</span>
				</div>
			</fieldset>
			</div>
		</fieldset>
		<br />
		<fieldset>
			<div class="column">
			<fieldset>
			<legend>FDB RX OTC Format</legend>
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.FDB_RX_OTC"
							path="fdbRxOtc" items="${fdbRxOtcMap}" element="div" />
					</span>
				</div>
			</fieldset>
			</div>
			<div class="column">
			<fieldset>
			<legend>FDB Name Type Format</legend>
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.FDB_NAME_TYPE"
							path="fdbNameType" items="${fdbNameTypeMap}" element="div" />
					</span>
				</div>
			</fieldset>
			</div>
			<div class="column">
			<fieldset>
			<legend>FDB Packaged Drug Format</legend>
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.FDB_PACKAGED_DRUG"
							path="fdbPackagedDrug" items="${fdbPackagedDrugMap}" element="div" />
					</span>
				</div>
			</fieldset>
			</div>
			<div class="column">
			<fieldset>
			<legend>FDB Phonetic Search Format</legend>
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.FDB_PHONETIC_SEARCH"
							path="fdbPhoneticSearch" items="${fdbPhoneticSearchMap}" element="div" />
					</span>
				</div>
			</fieldset>
			</div>
			<div class="column">
			<fieldset>
			<legend>FDB Search Methods Format</legend>
				<div class="controlGroup">
					<span class="control"> <form:radiobuttons
							id="user.sessionPreferences.FDB_SEARCH_METHODS"
							path="fdbSearchMethods" items="${fdbSearchMethodsMap}" element="div" />
					</span>
				</div>
			</fieldset>
			</div>
			<br /><br />
			<div class="column">&nbsp;
			</div>
			<div class="horizontalspacer"></div>



	</fieldset>

<!--  Please inspect the clear case history for the drug class sort preference area -->
<br>
<span class="column"> <peps:cancelMvc key="button.undo"
		name="cancel" /> </span>
<span class="column"> <peps:submit key="button.setPreferences" />
</span>
	</form:form>
</div>