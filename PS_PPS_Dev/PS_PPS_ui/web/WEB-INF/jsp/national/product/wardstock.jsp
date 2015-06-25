<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div class="panel">
	<peps:text item="${item}" key="inpatient.pharm.locations" label="true" cssClass="readonlynational" />
	<peps:dataField item="${item}" key="ar.ws.amis.category" />
	<peps:dataField item="${item}" key="ar.ws.amis.conversion.number" />

</div>