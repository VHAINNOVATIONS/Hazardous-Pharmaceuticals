<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>

<div class="panel" style="height: 600px;">
	<div class="twoColumn">
		<fieldset style="width: 550px;">
			<legend class="legend">Setup</legend>
			<peps:text item="${item}" key="application.package.use" label="true"
				cssClass="readonlynational" />
			<peps:dataField item="${item}" key="non.va.med"
				cssClass="aligncheckbox225" />
			<peps:text item="${item}" key="oi.iv.flag" label="true"
				cssClass="readonlynational" />
			<peps:dataField item="${item}" key="item.status"
				cssClass="aligncheckbox225" />
		</fieldset>
		<fieldset style="width: 550px;">
			<legend class="legend">Category / Sub-Category</legend>
			<div class="column">
				<fieldset class="subcat" style="width: 250px;">
					<legend>
						Category <span class="required">*</span>
					</legend>
					<peps:dataField item="${item}" key="categories"
						labelPosition="none" cssClass="aligncheckbox1" />
				</fieldset>
			</div>
			<div class="column">
				<fieldset class="subcat" style="width: 250px;">
					<legend>Sub-Category</legend>
					<peps:dataField item="${item}" key="sub.categories"
						labelPosition="none" cssClass="aligncheckbox11" />
				</fieldset>
			</div>
		</fieldset>
		<fieldset style="width: 550px;">
			<legend class="legend">OI Synonyms</legend>
			<peps:dataField item="${item}" key="orderable.item.synonym" />
		</fieldset>
	</div>
	<div class="twoColumn">
		<fieldset style="width: 550px;">
			<legend class="legend">Schedule / Duration</legend>
			<peps:text item="${item}" key="oi.schedule.type" label="true"
				cssClass="readonlynational" />
			<peps:text item="${item}" key="admin.schedules" label="true"
				cssClass="readonlynational" />
			<peps:dataField item="${item}" key="day.nd.or.dose.nl.limit" />
			<peps:dataField item="${item}" key="standard.med.route" />
		</fieldset>
	</div>
	<div class="horizontalspacer"></div>
	<div class="twoColumn">
		<fieldset style="width: 550px;">
			<legend class="legend">Formulary</legend>
			<peps:dataField item="${item}" key="national.formulary.indicator" />
		</fieldset>
	</div>
</div>