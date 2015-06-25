<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
	<peps:text item="${item}" key="local.non.formulary" label="true" cssClass="readonlynational" />
	<peps:dataField item="${item}"  key="lab.monitor.mark" cssClass="aligncheckboxoptions2" />
	<peps:text item="${item}"  key="lab.test.monitor" label="true" cssClass="readonlynational" />
	<peps:text item="${item}"  key="corresponding.inpatient.drug" label="true" cssClass="readonlynational" />
	<peps:dataField item="${item}"  key="max.dose.per.day" />
	<peps:dataField item="${item}"  key="max.dispense.limit" />
	<peps:dataField item="${item}"  key="total.dispense.quantity" />
	<peps:text item="${item}"  key="op.external.dispense" label="true" cssClass="readonlynational" />
	<peps:dataField item="${item}"  key="quantity.dispense.message" />
	<peps:text item="${item}"  key="specimen.type" label="true" cssClass="readonlynational" />      
	<peps:text item="${item}"  key="action.profile.message" label="true" cssClass="readonlynational" /> 
	<peps:text item="${item}"  key="normal.amount.to.order" label="true" cssClass="readonlynational" />     
</div>