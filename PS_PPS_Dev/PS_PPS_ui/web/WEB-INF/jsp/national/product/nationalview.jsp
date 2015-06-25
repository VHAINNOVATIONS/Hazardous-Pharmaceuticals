<%@ page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="panel">
	<fieldset>

		<div class="twoColumn">
			<peps:text item="${item}" key="va.product.name" label="true"
				cssClass="readonlynational" />
		</div>
		<div class="twoColumn">
			<peps:dataField item="${item}" key="gcn.sequence.number" />
		</div>
		<div class="twoColumnwidest">
			<peps:dataField item="${item}" key="generic.name" />
			<peps:dataField item="${item}" key="product.strength" cssClass="maxlen45" maxlength="45" />
		</div>
		<div class="twoColumn">
			<peps:dataField item="${item}" key="product.unit" cssClass="matchwidth" />
			<peps:dataField item="${item}" key="dispense.unit" cssClass="matchwidth" />
			<peps:dataField item="${item}" key="va.print.name" cssClass="maxlen40" maxlength="40" />
			<peps:text item="${item}" key="cmop.id" label="true" cssClass="readonlynational" />
			<peps:dataField item="${item}" key="cmop.dispense"
				cssClass="aligncheckbox1 labelreadonly" />
			<peps:dataField item="${item}"
				key="exclude.drg.drg.interaction.check" abbreviation="true"
				cssClass="aligncheckbox1 labelreadonly" />
			<peps:dataField item="${item}" key="override.df.dose.chk.exclusn"
				abbreviation="true" cssClass="aligncheckbox1 labelreadonly" />
			<peps:dataField item="${item}" key="create.possible.dosage"
				cssClass="aligncheckbox1 labelreadonly" />
			<peps:dataField item="${item}" key="possible.dosages.auto.create" />
			<peps:dataField item="${item}" key="product.package" cssClass="matchwidth" />

		</div>
		<div class="twoColumn editheader2">
			<peps:text item="${item}" key="vuid" label="true"
				cssClass="readonlynational" />
			<peps:dataField item="${item}" key="cs.fed.schedule" cssClass="matchwidth"
				jsOnChange="processChangedCsFedSchedule()" />
			<peps:dataField item="${item}" key="national.formulary.indicator"
				cssClass="aligncheckbox225 labelreadonly" />
			<peps:text item="${item}" key="national.formulary.name" label="true" cssClass="readonlynational" />
			<peps:dataField item="${item}" key="item.status" cssClass="matchwidth" />
			<c:choose>
		    	<c:when test="${item.inactivationDate == null}">
		          	<peps:dataField item="${item}" key="proposed.inactivation.date" />
		        </c:when>
		        <c:otherwise>
		        	<peps:text item="${item}" key="inactivation.date" label="true" cssClass="readonlynational" />
		        </c:otherwise>
		    </c:choose>
			<peps:dataField item="${item}" key="reduced.copay" />

		</div>
		<div class="panel">
			<peps:dataField item="${item}" key="fda.med.guide"
				cssClass="fdamedguidewidth" maxlength="100" />
		</div>
	</fieldset>
	<fieldset>
		<div class="fourColumn">
			<fieldset class="subcat">
				<legend>Sub-Category</legend>
				   <peps:dataField item="${item}"  key="sub.categories" labelPosition="none" cssClass="aligncheckbox1" />
			</fieldset>
		</div>
		<div class="twoColumnwidest">
			<peps:dataField item="${item}" key="drug.classes" jsOnClick="selectPrimaryDrugClass(this, ${item.supply}, false);"/>
		</div>
	</fieldset>
	<fieldset>
		<div class="column">
			<peps:dataField item="${item}" key="active.ingredients" />
			<peps:dataField item="${item}" key="synonyms" />
		</div>
	</fieldset>
</div>