<div class="panel">
    <div class="twoColumn">
    <fieldset style="width: 496px;"><legend class="legend">NDC Information</legend>
        <peps:text key="ndc" label="true" cssClass="readonly" />
        <peps:text key="trade.name" label="true" cssClass="readonly" />
        <peps:text key="manufacturer" label="true" cssClass="readonly" />
        <peps:text key="upc.upn" label="true" cssClass="readonly" />
             
    </fieldset> 
    </div>
    <div class="twoColumn">
    <fieldset style="width: 440px;"><legend class="legend">Physical Characteristics</legend>
        <peps:dataField key="color"  />
        <peps:dataField key="imprint" />
        <peps:dataField key="imprint2" />
        <peps:dataField key="scored" cssClass="scored" />
        <peps:dataField key="shape" />
    </fieldset>   
    </div>
    <div class="horizontalspacer"></div>
    <div class="twoColumn">
    <fieldset style="width: 496px;"><legend class="legend">Local Pricing Information</legend>
        <peps:dataField key="order.unit" />
        <peps:dataField key="unit.price" />
        <peps:dataField key="ndc.disp.units.per.ord.unit" />
        <peps:dataField key="ndc.price.per.dispense.unit" />
        <peps:dataField key="package.size" />   
        <peps:dataField key="package.type" /> 
        <peps:dataField key="vendor" /> 
        <peps:dataField key="vendor.stock.number" /> 
    </fieldset>  
    </div>
    <div class="twoColumn">
     <fieldset style="width: 440px;"><legend class="legend">Other Information</legend>
        <peps:dataField key="otc.rx.indicator" /> 
        <peps:dataField key="protect.from.light" cssClass="aligncheckboxndcmain" /> 
        <peps:dataField key="refrigeration" /> 
        <peps:text key="application.package.use" label="true" cssClass="readonly" /> 
        <peps:text key="proposed.inactivation.date" label="true" cssClass="readonly" /> 
        <peps:text key="inactivation.date" label="true" cssClass="readonly" />  
        <peps:text key="categories" label="true" cssClass="readonly" />
        <peps:text key="source" label="true" cssClass="readonly" /> 
     </fieldset>  
    </div>
    <div style="twoColumnwide">
    <fieldset style="width: 550px;"><legend class="legend">Previous NDC</legend>
                <peps:resumeFlowLink
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
                eventId="editField"
                fieldKey="previous.upc.upn"
                text="Previous UPC/UPN" 
                onclick="return popup(this.href, this.id, 280, 400, 260, 400);" 
                cssClass="linkpadding" />
    </fieldset>   
    </div>
</div>