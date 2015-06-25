<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<peps:warnings />
<br />

<peps:model ognlPrefix="item"> 

<peps:form id="ProductWizard2">
<div class="columnpadded"><h2>Confirmation for Creating New FDB Product</h2></div><br /><br />

    	<peps:text key="va.product.name" label="true" cssClass="readonlynational" />
    	<peps:text key="va.print.name" label="true" cssClass="readonlynational" />
    	<peps:text key="dispense.unit" label="true" cssClass="readonlynational" />
    	<peps:text key="generic.name" label="true" cssClass="readonlynational" />
		<peps:text key="oi.name" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>
		<peps:text key="cs.fed.schedule" label="true" cssClass="readonlynational"/>
		<peps:text key="single.multi.source.product" label="true" cssClass="readonlynational"/> 
		<peps:text key="gcn.sequence.number" label="true" cssClass="readonlynational" />
		<peps:text key="dea.schedule" label="true" cssClass="readonlynational" />
		<peps:text key="ncpdp.dispense.unit" label="true" cssClass="readonlynational" />
		<div class="rowspacer"></div>
		<peps:text key="ncpdp.quantity.multiplier" label="true" cssClass="readonlynational" />
		<peps:text key="categories" label="true" cssClass="readonlynational" />
		<peps:text key="special.handlings" label="true" cssClass="readonlynational" /> 
		<peps:text key="national.formulary.indicator" label="true" cssClass="readonlynational" />
		<peps:text key="formulary" label="true" cssClass="readonlynational"  /> 
		<div class="rowspacer"></div>
		<peps:text key="national.formulary.name" label="true" cssClass="readonlynational" />
		<peps:text key="cmop.id" label="true" cssClass="readonlynational" /> 
        <peps:text key="cmop.dispense"  label="true" cssClass="readonlynational" />
        <peps:text key="max.dose.per.day" label="true" cssClass="readonlynational" /> 
        <peps:text key="create.possible.dosage"  label="true" cssClass="readonlynational" /> 
		<div class="rowspacer"></div>
        <peps:text key="possible.dosages.auto.create" label="true" cssClass="readonlynational" />
        <peps:text key="exclude.drg.drg.interaction.check" label="true" cssClass="readonlynational" /> 
        <peps:text key="override.df.dose.chk.exclusn" label="true" cssClass="readonlynational" /> 
        <peps:text key="copay" label="true" cssClass="readonlynational" /> 
        <peps:text key="reduced.copay" label="true" cssClass="readonlynational" />  
        <div class="rowspacer"></div>
   <span class="column"><peps:submit
        key="button.return"
        event="previous" /></span>
    
    <span class="column"><peps:submit
        key="button.accept.changes"
        event="confirm" /></span>
    </div>
</peps:form>
</peps:model>