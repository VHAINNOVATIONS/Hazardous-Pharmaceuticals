<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<peps:form id="NDCWizard2">
	<peps:model ognlPrefix="item">
		<div class="columnpadded">
			<h2>Confirmation for Creating New NDC</h2>
		</div>
		<br />
		<br />

		<peps:text key="ndc" label="true" cssClass="readonlynational" />
		<peps:text key="upc.upn" label="true" cssClass="readonlynational" />
		<peps:text key="trade.name" label="true" cssClass="readonlynational" />
		<peps:text key="package.type" label="true" cssClass="readonlynational" />
		<peps:text key="package.size" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>
		<peps:text key="manufacturer" label="true" cssClass="readonlynational" />
		<peps:text key="otc.rx.indicator" label="true" cssClass="readonlynational" />
		<peps:text key="product.number" label="true" cssClass="readonlynational" />
		<peps:text key="scored" label="true" cssClass="readonlynational" />
		<peps:text key="color" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>
		<peps:text key="shape" label="true" cssClass="readonlynational" />
		<peps:text key="imprint" label="true" cssClass="readonlynational" />
		<peps:text key="inactivation.date" label="true" cssClass="readonlynational" />
		<peps:text key="protect.from.light" cssClass="aligncheckbox6" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>
		<peps:text key="refrigeration" label="true" cssClass="readonlynational" />
		<peps:text key="image" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>

		<span class="column"><peps:submit key="button.return"
				event="previous" />
		</span>

		<span class="column"><peps:submit key="button.accept.changes"
				event="confirm" />
		</span>
		<div></div>
	</peps:model>
</peps:form>

<div id="partial.save.flag" class="hidden">
	<s:property value="item.partialSave" />
</div>
