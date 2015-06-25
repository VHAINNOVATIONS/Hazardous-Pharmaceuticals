<%@page import="gov.va.med.pharmacy.peps.common.vo.ProductVo"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%> 
    
<div class="panel">
    <fieldset>
    <div class="twoColumn">
        <peps:text item="${item}" key="generic.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="va.product.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="va.print.name" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="local.print.name" label="true" cssClass="readonlynational" />    
    </div> 
    <div class="twoColumn">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <spring:message code="active.ingredients.name" var="localizedActiveIngredient"/>
        <peps:link
                event="editField"
                fieldKey="active.ingredients"
                itemType="${item.entityType}"
                itemId="${item.id}" 
                text="${localizedActiveIngredient} ${item.activeIngredientsLinkText}"
                onclick="return popup(this.href, this.id, 300, 800, 200, 240);" 
                cssClass="linkpadding" />
        <peps:text item="${item}" key="national.formulary.name" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="proposed.inactivation.date" />       
    </div>
    </fieldset>        
    <div class="horizontalspacer"></div>
    <fieldset>
    <div class="twoColumn">
        <peps:dataField item="${item}" key="local.use" cssClass="aligncheckbox225 labelreadonly" />
		<peps:dataField item="${item}" key="special.handlings" />
        <peps:dataField item="${item}" key="cs.fed.schedule" jsOnChange="processChangedCsFedSchedule()"/>
        <peps:dataField item="${item}" key="dea.schedule" /> 

    </div> 
    <div class="twoColumn">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <spring:message code="drug.classes.name" var="localizedDrugClasses"/>
        <peps:link
                event="editField"
                fieldKey="drug.classes" 
                itemType="${item.entityType}"
                itemId="${item.id}"
                text="${localizedDrugClasses} ${item.drugClassesLinkText}"
                onclick="return popup(this.href, this.id, 300, 800, 200, 240);" 
                cssClass="linkpadding" />

        <peps:text item="${item}" key="gcn.sequence.number" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="vuid" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}"  key="sub.categories" labelPosition="top" cssClass="aligncheckbox5" /> 
    </div> 

    </fieldset>                            
    <div class="horizontalspacer"></div>
    <fieldset>
    <div class="twoColumn">
        <peps:text item="${item}" key="local.non.formulary" label="true" cssClass="readonlynational"  />
        <peps:dataField item="${item}" key="national.formulary.indicator" cssClass="aligncheckbox225 labelreadonly" />
		<peps:text item="${item}" key="formulary" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="formulary.alternative" /> 
        <peps:dataField item="${item}" key="national.drug.texts" />   
        <peps:dataField item="${item}" key="message" rows="4" cols="10" />  
    </div> 
    <div class="twoColumn">
        
        &nbsp;&nbsp;&nbsp;&nbsp;
        <spring:message code="synonyms.name" var="localizedSynonyms"/>
        <peps:link
                event="editField"
                fieldKey="synonyms"
                itemType="${item.entityType}"
                itemId="${item.id}" 
                text="${localizedSynonyms} ${item.synonymsLinkText}"
                onclick="return popup(this.href, this.id, 280, 1020, 200, 0);" 
                cssClass="linkpadding" />                
        <peps:text item="${item}" key="order.unit" label="true" cssClass="readonlynational" /> 
        <peps:text item="${item}" key="product.price.per.order.unit" label="true" cssClass="readonlynational" />  
        <peps:dataField item="${item}" key="dispense.unit" />       
        <peps:dataField item="${item}" key="product.dispense.unit.per.ord" />  
        <peps:text item="${item}" key="product.price.per.dispense.unit" label="true" cssClass="readonlynational" /> 
        <peps:text item="${item}" key="ifcap.item.number" label="true" cssClass="readonlynational"  />  
        <peps:dataField item="${item}" key="fsn" />  
        <peps:dataField item="${item}" key="refrigeration" />  
        <peps:dataField item="${item}" key="protect.from.light" cssClass="aligncheckbox5 labelreadonly" />  
    </div>
    <div class="panel">
        <peps:dataField item="${item}" key="daw.code" />       
    </div>
    </fieldset>                      
</div>