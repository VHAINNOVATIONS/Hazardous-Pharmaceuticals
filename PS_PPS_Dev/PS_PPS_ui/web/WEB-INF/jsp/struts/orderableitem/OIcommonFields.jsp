<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<peps:form id="OIWizard2">
	<peps:model ognlPrefix="item">
		<div class="columnpadded">
			<h2>Confirmation for Creating New OI</h2>
		</div>
		<br />
		<br />

		<peps:text key="oi.name" label="true" cssClass="readonlynational" />
		<peps:text key="orderable.item.type" label="true"
			cssClass="readonlynational" />
		<peps:text key="generic.name" label="true" cssClass="readonlynational" />
		<peps:text key="dosage.form" label="true" cssClass="readonlynational" />
		<peps:text key="vista.oi.name" label="true"
			cssClass="readonlynational" />
		<div class="rowspacer"></div>

		<peps:text key="national.formulary.indicator" label="true"
			cssClass="readonlynational" />
		<peps:text key="categories" label="true" cssClass="readonlynational" />
		<peps:text key="standard.med.route" label="true" cssClass="readonlynational" />

		<div class="rowspacer"></div>

		<span class="column"><peps:submit key="button.return"
				event="previous" />
		</span>

		<span class="column"><peps:submit key="button.accept.changes"
				event="confirm" />
		</span>


	</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden">
	<s:property value="item.partialSave" />
</div>
