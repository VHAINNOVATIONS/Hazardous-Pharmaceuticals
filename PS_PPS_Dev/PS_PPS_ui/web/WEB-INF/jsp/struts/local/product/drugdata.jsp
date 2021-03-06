<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <fieldset>
    <div class="twoColumn">
        <peps:text key="generic.name" label="true" cssClass="readonly" />
        <peps:text key="va.product.name" label="true" cssClass="readonly" />
        <peps:text key="va.print.name" label="true" cssClass="readonly" />
        <peps:dataField key="local.print.name" />   
    </div> 
    <div class="twoColumn">
        <peps:resumeFlowLink
                eventId="editField"
                fieldKey="active.ingredients" 
                ognl="item.createActiveIngredientsLinkText(locale)"
                onclick="return popup(this.href, this.id, 300, 800, 200, 240);" 
                cssClass="linkpadding" />
        <peps:text key="national.formulary.name" label="true" cssClass="readonly"/>
        <peps:dataField key="proposed.inactivation.date" disabled="true" />       
    </div>
    </fieldset>        
    <div class="horizontalspacer"></div>
    <fieldset>
    <div class="twoColumn">
        <peps:dataField key="local.use" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="categories" />
        <peps:dataField key="special.handlings" />  
        <peps:dataField key="cs.fed.schedule" />
        <peps:dataField key="dea.schedule" />    
    </div> 
    <div class="twoColumn">
        <peps:resumeFlowLink
                eventId="editField"
                fieldKey="drug.classes" 
                ognl="item.createDrugClassesLinkText(locale)"
                onclick="return popup(this.href, this.id, 300, 800, 200, 240);" 
                cssClass="linkpadding" />

        <peps:text key="gcn.sequence.number" label="true" cssClass="readonly" />
        <peps:text key="vuid" label="true" cssClass="readonly" />
    </div> 
    </fieldset>                            
    <div class="horizontalspacer"></div>
    <fieldset>
    <div class="twoColumn">
        <peps:dataField key="local.non.formulary" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="national.formulary.indicator" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="formulary" /> 
        <peps:dataField key="formulary.alternative" rows="4" cols="10" />    
        <peps:dataField key="local.drug.texts" />   
        <peps:dataField key="message" rows="4" cols="10" />  
    </div> 
    <div class="twoColumn">
        <peps:dataField key="product.ndc" />
        <peps:resumeFlowLink
                eventId="editField"
                fieldKey="synonyms" 
                ognl="item.createSynonymsLinkText(locale)"
                onclick="return popup(this.href, this.id, 280, 1020, 200, 0);" 
                cssClass="linkpadding" />
        <peps:dataField key="order.unit" /> 
        <peps:dataField key="product.price.per.order.unit" />  
        <peps:dataField key="dispense.unit" />       
        <peps:dataField key="product.dispense.unit.per.ord" />  
        <peps:dataField key="product.price.per.dispense.unit"  /> 
        <peps:dataField key="ifcap.item.number" cssClass="itemnumber" />  
        <peps:dataField key="fsn" />  
        <peps:dataField key="refrigeration" />  
        <peps:dataField key="protect.from.light" cssClass="aligncheckbox962 labelreadonly" />  
    </div>
    <div class="panel">
        <peps:dataField key="daw.code" />       
    </div>
    </fieldset>                      
</div>