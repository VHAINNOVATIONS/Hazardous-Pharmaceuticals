<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags" %>
<div class="panel">
    <fieldset>
        <peps:dataField key="generic.name" />
    <div class="twoColumnwide">
        <peps:text key="va.product.name" label="true" cssClass="readonlynational" />
        <peps:dataField key="va.print.name" />   
        <peps:dataField key="national.formulary.indicator" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="formulary" />  
        <peps:dataField key="strength" />  
        <peps:dataField key="dispense.unit" />  
        <peps:dataField key="cmop.id" /> 
        <peps:dataField key="cmop.dispense" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField key="create.possible.dosage" cssClass="aligncheckbox225 labelreadonly" /> 
        <peps:dataField key="possible.dosages.auto.create" />

    </div> 
    <div class="threeColumn2">
        <peps:dataField key="gcn.sequence.number" />
        <peps:text key="vuid" label="true" cssClass="readonlynational" />
        <peps:dataField key="cs.fed.schedule" jsOnChange="processChangedCsFedSchedule()"/>
        <peps:dataField key="national.formulary.name"/>
        <peps:dataField key="proposed.inactivation.date" /> 
        <peps:dataField key="inactivation.date" />  
        <peps:dataField key="exclude.drg.drg.interaction.check" cssClass="aligncheckbox4 labelreadonly" /> 
        <peps:dataField key="override.df.dose.chk.exclusn" cssClass="aligncheckbox4 labelreadonly" /> 
        <peps:dataField key="copay" /> 
        <peps:dataField key="reduced.copay" cssClass="aligncheckbox4 labelreadonly" />
    </div>
        <peps:dataField key="fda.med.guide" cssClass="fdamedguidewidth" /> 
    </fieldset>        
    <fieldset>
    <div class="fourColumn">
        <peps:dataField key="categories" />
    </div> 
    <div class="twoColumnwidest">
        <peps:dataField key="drug.classes" />
    </div> 
    </fieldset>         
    <fieldset>
    <div class="column">
        <peps:dataField key="active.ingredients"/>
        <peps:dataField key="synonyms" /> 
    </div> 
    </fieldset>                                           
</div>