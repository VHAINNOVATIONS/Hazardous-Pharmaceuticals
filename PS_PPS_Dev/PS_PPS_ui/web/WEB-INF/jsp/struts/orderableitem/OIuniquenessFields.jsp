<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<peps:form id="OIWizard1">
	<peps:model ognlPrefix="item">
		<s:if test="%{environment.local and item.isLocal()}">
			<div id="loiParentDiv" class="twoColumn floatRight">
				<peps:submit key="button.select.loi.parent" event="selectParent" />
			</div>
		</s:if>
		<s:else>
			<div id="loiParentDiv" class="twoColumn floatRight hidden">
				<peps:submit key="button.select.loi.parent" event="selectParent" />
			</div>
		</s:else>
		<script language="javascript" type="text/javascript">
function toggleOIParentSelect()
{
    var buttonDiv = document.getElementById('loiParentDiv');
    var labelDiv = document.getElementById('parentNameDiv');
    var oiType = document.getElementById('item.orderableItemType');
    if (oiType.value == "NATIONAL") {
     	buttonDiv.className = 'twoColumn floatRight hidden';
        labelDiv.className = 'controlGroup hidden';
    } else {
        buttonDiv.className = 'twoColumn floatRight';
        labelDiv.className = 'controlGroup';
    }
}
</script>
		<div class="topspacer">
			&nbsp;&nbsp;Fields marked with <font color="red">*</font> are
			required
		</div>

		<div class="topspacer"></div>
		<div class="clearLeft">
			<fieldset>
				<legend>OI Uniqueness Fields</legend>

				<div class="panel">
					<s:if test="%{environment.national}">
						<peps:text key="orderable.item.type" label="true"
							cssClass="readonlynational" />
					</s:if>
					<s:else>
						<peps:dataField key="orderable.item.type"
							jsOnChange="toggleOIParentSelect()" />
					</s:else>
					<peps:dataField key="generic.name" />
					<peps:dataField key="dosage.form" />
					<peps:submit key="button.generate.oi.names" event="generateOINames"
						onclick="checkGenericName();" />
					<peps:dataField key="vista.oi.name" />
					<peps:dataField key="national.formulary.indicator"
						cssClass="aligncheckbox1" />
					<peps:dataField key="categories" />


				</div>
				<div class="clearLeft twoColumn">
					<peps:dataField key="standard.med.route" />
				</div>
			</fieldset>
		</div>

		<div class="buttonspacer"></div>

		<div id="buttons" class="panel">
			<span class="floatRight"> <s:if
					test="%{getConversationScopeAttribute('templateSubFlow')}">
					<span class="column"><peps:submit key="button.cancel"
							event="cancel" />
					</span>
				</s:if> <s:else>
					<span class="column"><peps:cancel />
					</span>
				</s:else> <span class="column"> <peps:submit key="button.submit"
						event="next" /> </span> </span>
		</div>

	</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden">
	<s:property value="item.partialSave" />
</div>