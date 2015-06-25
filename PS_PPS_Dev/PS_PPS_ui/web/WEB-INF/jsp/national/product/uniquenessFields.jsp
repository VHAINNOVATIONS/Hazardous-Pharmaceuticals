<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@ page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ page import="java.util.List"%>
<br />

<peps:form id="ProductWizard1">

	<div class="twoColumnRight">
		<c:url value="/changeParent.go" var="actionUrl" />
		<input type="hidden" id="entityType" name="entityType"
			value="${fn:toLowerCase(item.entityType.parent)}" />
		<c:choose>
			<c:when
				test="${item.orderableItem.id != null}">
				<peps:submit key="button.change.product.parent"
					action="${actionUrl}"  disabled="true"/>
			</c:when>
			<c:otherwise>
				<peps:submit key="button.select.product.parent"
					action="${actionUrl}" disabled="true"/>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="topspacer">
		&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required
	</div>

	<div class="clearLeft">

		<fieldset class="background1">
			<legend>Define the VA Product</legend>
			<div class="uniquenesspanel">
				<div class="panel">
					<span class="column"> <peps:dataField
							key="active.ingredients" item="${item}" />
					</span>
				</div>
				<peps:dataField key="generic.name" item="${item}" />
				<div class="controlGroup">
					<c:url value="/${fn:toLowerCase(item.entityType)}/generateNames.go"
						var="generateNamesUrl" />
					<peps:submit key="button.compute" onclick="checkIngredients();"
						action="${generateNamesUrl}" />
				</div>

				<peps:dataField key="va.product.name" cssClass="vafieldswidth"
					maxlength="64" item="${item}" />
				<peps:dataField key="va.print.name" cssClass="maxlen40" item="${item}" maxlength="40"/>
				<peps:dataField key="national.formulary.name" item="${item}" cssClass="maxlen94" maxlength="94" />
				<peps:dataField key="product.strength" item="${item}" cssClass="maxlen45" maxlength="45"/>
				<peps:dataField key="product.unit" item="${item}"  cssClass="matchwidth" />
				<peps:dataField key="dispense.unit" item="${item}"  cssClass="matchwidth" />

			</div>
		</fieldset>

		<div class="horizontalspacer"></div>

		<fieldset class="background2">
			<legend>Define the VA Drug Classes</legend>
			<div class="panel">
				<span class="column"> <peps:dataField key="drug.classes"
						item="${item}" jsOnClick="selectPrimaryDrugClass(this, ${item.supply}, true);" jsOnChange="checkDrugClassForSupplyType(this, ${item.supply});"/> </span>
			</div>
		</fieldset>

		<fieldset class="background3">
			<legend>Define the Schedule and Handling</legend>
			<div class="panel">

				<div class="twoColumn">
					<peps:dataField key="cs.fed.schedule" item="${item}"
						jsOnChange="processChangedCsFedSchedule()" />
					<peps:dataField key="single.multi.source.product" item="${item}"
						cssClass="matchwidth" />
					<peps:dataField key="gcn.sequence.number" item="${item}" cssClass="maxlen6" maxlength="6" />
				</div>

				<div class="twoColumn">
					<peps:dataField key="dea.schedule" item="${item}"
						cssClass="matchwidth" />
					<peps:dataField key="ncpdp.dispense.unit" item="${item}"
						cssClass="matchwidth" />
					<peps:dataField key="ncpdp.quantity.multiplier" item="${item}" cssClass="maxlen9" maxlength="9" />
				</div>

				<div class="twoColumn">
					<fieldset class="subcat">
						<legend>Sub-Category</legend>
						<peps:dataField item="${item}" key="sub.categories"
							labelPosition="none" cssClass="aligncheckbox1 labelreadonly" />
					</fieldset>
				</div>

				<div class="twoColumn">
					<peps:dataField key="special.handlings" item="${item}" />
				</div>
			</div>
		</fieldset>

		<div class="horizontalspacer"></div>

		<fieldset class="background1">
			<legend>Miscellaneous</legend>

			<div class="twoColumn">
				<peps:dataField key="national.formulary.indicator" item="${item}"
					cssClass=" labelreadonly" />
				<peps:dataField key="cmop.dispense"
					cssClass=" labelreadonly" item="${item}" />
				<peps:dataField key="override.df.dose.chk.exclusn"
					cssClass=" labelreadonly" item="${item}"
					abbreviation="true" />
				<peps:dataField key="exclude.drg.drg.interaction.check"
					cssClass=" labelreadonly " abbreviation="true" item="${item}" />

			</div>

			<div class="twoColumn">
				<peps:dataField key="create.possible.dosage"
					cssClass=" labelreadonly " item="${item}" />
				<peps:dataField key="possible.dosages.auto.create" item="${item}" />
				<peps:dataField key="product.package" item="${item}" />

			</div>
			
			<div class="panel">
				<peps:dataField key="fda.med.guide" cssClass="fdamedguidewidth"
					item="${item}" maxlength="100" />
			</div>

		</fieldset>

	</div>
	
	<input type="hidden" id="domainResult" value="ddd" />

	<div class="buttonspacer"></div>
		<div id="buttons" class="panel">
			<span class="floatRight">			
				<span class="column">
	                <c:url value="/${fn:toLowerCase(item.entityType)}/addProductCancel.go" var="cancelUrl"/>
	                <peps:submit key="button.cancel" action="${cancelUrl}" /> 
                </span>
				<span class="column">
					<peps:submit key="button.submit" />
				</span>
			</span>
		</div>
</peps:form>