<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard1">
<peps:model ognlPrefix="item">
<div class="twoColumnRight">
<s:if test="(item.entityType.hasParent() && item.parent != null ) ">
    <peps:submit key="button.change.product.parent" event="selectParent" />
</s:if>
<s:else>
    <peps:submit key="button.select.product.parent" event="selectParent" />
</s:else>
</div>
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">

    <fieldset class="background1" ><legend>Define the VA Product</legend>
    <div class="uniquenesspanel">
    <div class="panel"><span class="column"><peps:dataField key="active.ingredients" /></span></div>
    <div class="horizontalspacer"></div>
    <peps:submit key="button.compute" event="computeFields" onclick="checkIngredients();" />
    <peps:dataField key="va.product.name" cssClass="vafieldswidth" maxlength="64" />
    <peps:dataField key="va.print.name" cssClass="vafieldswidth" maxlength="40" />
    <peps:dataField key="dispense.unit" />
    <peps:dataField key="generic.name" />
    </div>
    </fieldset>
    <div class="horizontalspacer"></div>
    
    <fieldset class="background2" ><legend>Define the VA Drug Classes</legend>
    <div class="panel">
       <span class="column"><peps:dataField key="drug.classes" jsOnClick="selectPrimaryDrugClass(this);" /></span>
    </div>
    </fieldset>
    <fieldset class="background3" ><legend>Define the Schedule and Handling</legend>
    <div class="panel">
        <div class="twoColumn">
            <peps:dataField key="cs.fed.schedule" jsOnChange="processChangedCsFedSchedule()"/>
            <peps:dataField key="single.multi.source.product" /> 
            <peps:dataField key="gcn.sequence.number" />
        </div>
        <div class="twoColumn">
            <peps:dataField key="dea.schedule" />
            <peps:dataField key="ncpdp.dispense.unit" />
            <peps:dataField key="ncpdp.quantity.multiplier" />
        </div>
        <span class="column"><peps:dataField key="categories" /></span>
        <span class="column"><peps:dataField key="special.handlings" /> </span>
    </div>
    </fieldset>
    <div class="horizontalspacer"></div>

    <fieldset class="background1" ><legend>Miscellaneous</legend>
    <div class="twoColumn">
		<peps:dataField key="national.formulary.indicator" cssClass="aligncheckbox216" />
		<peps:dataField key="formulary"  /> 
		<peps:dataField key="national.formulary.name"  cssClass="formulary" />
		<peps:dataField key="cmop.id" /> 
        <peps:dataField key="cmop.dispense" cssClass="aligncheckbox216 labelreadonly" />
        <peps:dataField key="max.dose.per.day" cssClass="vafieldswidth" /> 
		</div>
    <div class="twoColumn">
        <peps:dataField key="create.possible.dosage" cssClass="aligncheckbox5 labelreadonly" /> 
        <peps:dataField key="possible.dosages.auto.create" />
        <peps:dataField key="exclude.drg.drg.interaction.check" cssClass="aligncheckbox5 labelreadonly" /> 
        <peps:dataField key="override.df.dose.chk.exclusn" cssClass="aligncheckbox5 labelreadonly" /> 
        <peps:dataField key="copay" /> 
        <peps:dataField key="reduced.copay" />       
    </div>

    </fieldset>
</div>
    <div class="horizontalspacer"></div>

    <div id="buttons" class="panel">
        <span class="floatRight">
        <s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
            <span class="column"><peps:submit
                key="button.cancel"
                event="cancel" /></span>
        </s:if>
        <s:else>
            <span class="column"><peps:cancel /></span>
        </s:else>    
        <span class="column">
            <peps:submit
                key="button.submit"
                event="next" 
                />




        </span>
        </span>
    </div>
    
    </peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>