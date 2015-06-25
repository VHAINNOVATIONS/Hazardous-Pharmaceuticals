<div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 496px;"><legend class="legend">NDC Information</legend>
        <peps:text item="${item}" key="ndc" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="trade.name" />
        <peps:dataField item="${item}" key="manufacturer" />
        <peps:dataField item="${item}" key="upc.upn" />
             
    </fieldset> 
    </div>
    <div class="twoColumn">
    <fieldset style="width: 440px;"><legend class="legend">Physical Characteristics</legend>
        <peps:dataField item="${item}" key="color"  />
        <peps:dataField item="${item}" key="imprint" />
        <peps:dataField item="${item}" key="imprint2" />
        <peps:dataField item="${item}" key="scored" cssClass="scored" />
        <peps:dataField item="${item}" key="shape" />
    </fieldset>   
    </div>
    <div class="horizontalspacer"></div>
    <div class="twoColumn">
    <fieldset style="width: 496px;"><legend class="legend">Local Pricing Information</legend>
        <peps:dataField item="${item}" key="order.unit" />
        <peps:dataField item="${item}" key="unit.price" />
        <peps:dataField item="${item}" key="ndc.disp.units.per.ord.unit" />
        <peps:dataField item="${item}" key="ndc.price.per.dispense.unit" />
        <peps:dataField item="${item}" key="package.size" />   
        <peps:dataField item="${item}" key="package.type" /> 
        <peps:dataField item="${item}" key="vendor" /> 
        <peps:dataField item="${item}" key="vendor.stock.number" /> 
    </fieldset>  
    </div>
    <div class="twoColumn">
     <fieldset style="width: 440px;"><legend class="legend">Other Information</legend>
        <peps:dataField item="${item}" key="otc.rx.indicator" /> 
        <peps:dataField item="${item}" key="protect.from.light" cssClass="aligncheckboxndcmain" /> 
        <peps:dataField item="${item}" key="refrigeration" /> 
        <peps:text item="${item}" key="application.package.use" label="true" cssClass="readonlynational" /> 
        <peps:dataField item="${item}" key="proposed.inactivation.date" /> 
        <peps:dataField item="${item}" key="inactivation.date" /> 
        <peps:text item="${item}" key="categories" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="source" label="true" cssClass="readonlynational" /> 
     </fieldset>  
    </div>
    <div style="twoColumnwide">
    <fieldset style="width: 550px;"><legend class="legend">Previous NDC</legend>
                <peps:resumeFlowLink
	                item="${item}"
	                eventId="editField"
	                fieldKey="previous.ndc" 
	                text="Previous NDC"
	                onclick="return popup(this.href, this.id, 280, 400, 260, 400);" 
	                cssClass="linkpadding" />
    </fieldset>   
    </div>
    <div style="twoColumnwide">
    <fieldset style="width: 550px;"><legend class="legend">Previous UPC/UPN</legend>
                <peps:resumeFlowLink
	                item="${item}"
	                eventId="editField"
	                fieldKey="previous.upc.upn" 
	                text="Previous UPC/UPN" 
	                onclick="return popup(this.href, this.id, 280, 400, 260, 400);" 
	                cssClass="linkpadding" />
    </fieldset>   
    </div>
</div>