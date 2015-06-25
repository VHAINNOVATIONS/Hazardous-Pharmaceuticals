<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<div class="panel">
	<peps:dataField item="${item}" key="approved.for.splitting"
		cssClass="matchwidth" />
	<peps:dataField item="${item}" key="displayable.ingredient.strength" />
	<div class="column">
		&nbsp;&nbsp;&nbsp;&nbsp;National Possible Dosages:<br />
	</div>
	<div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div class="column">
		<peps:text item="${item}" key="national.possible.dosages"
			label="false" cssClass="possibleDosage" />
	</div>
	<div class="horizontalspacer"></div>
	<div class="horizontalspacer"></div>
	<div class="clearLeft column">&nbsp;&nbsp;&nbsp;&nbsp;Local
		Possible Dosages:</div>
	<div class="column">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div class="column">
		<peps:text item="${item}" key="local.possible.dosages" label="false"
			cssClass="possibleDosage" />
	</div>
</div>