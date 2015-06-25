<div class="panel">
   <div class="twoColumn">
   <fieldset><legend class="legend">Local Pricing Information</legend>
        <peps:text item="${item}" key="order.unit" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="unit.price" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="ndc.disp.units.per.ord.unit" />
        <peps:dataField item="${item}" key="ndc.price.per.dispense.unit" />
        <peps:dataField item="${item}" key="package.size" />   
        <peps:dataField item="${item}" key="package.type" cssClass="matchwidth" /> 
        <peps:dataField item="${item}" key="vendor" /> 
        <peps:dataField item="${item}" key="vendor.stock.number" /> 
    </fieldset>  
    </div>
    <div>
    <fieldset><legend class="legend">National Pricing Information</legend>
        <peps:text item="${item}" key="fss.i" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="fss.pv" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="fss.cnt.no" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="fss.fss.end" label="true" cssClass="readonlynational" />
     	<peps:text item="${item}" key="fss.fss.price" label="true" cssClass="readonlynational" />
     	<peps:text item="${item}" key="fss.va.price" label="true" cssClass="readonlynational" /> 
        <peps:text item="${item}" key="fss.big4.price" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="fss.bpa.price" label="true" cssClass="readonlynational" /> 
        <peps:text item="${item}" key="fss.bpa.avail" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="fss.nc.price" label="true" cssClass="readonlynational" /> 
    </fieldset>   
    </div>
</div>