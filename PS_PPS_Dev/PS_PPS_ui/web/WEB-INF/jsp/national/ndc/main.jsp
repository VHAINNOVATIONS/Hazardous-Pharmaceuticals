
<div class="panel" style="height: 600px;">
    <div class="twoColumn">
    
    <fieldset style="width: 496px;"><legend class="legend">NDC Information</legend>
		<div id="ndc.controlGroup" style="width: 99%; padding-top:0.12em; padding-right: 0.12em; padding-bottom: 0.12em; padding-left: 0.12em; clear: both;">
		    <span>
		        <label id="label_ndc" title="NDC" style="padding-bottom: 8px; padding-left: 16px; margin-right: 5px; text-align: left; padding-right: 3px;">NDC:</label>
		    </span>
		    <span>
		        <label style="padding-bottom: 8px; padding-left: 1px; margin-left: 162px;  padding-right: 3px;  padding-bottom: 5px;">
		              <a href="/PRE/fdbDetails.go?ndc=${item.ndc}&popup=true&ndcDetails=true"
                        onclick="return popupWithOptions(this.href, 'print', 'resizable=yes,scrollbars=no,toolbar=no, width=900,height=650');">${item.ndc}</a>
		        </label>
		    </span>
		</div>

        <!-- <peps:text item="${item}" key="ndc" label="true" cssClass="readonlynational" /> -->
        <peps:dataField item="${item}" key="trade.name" />
        <peps:dataField item="${item}" key="manufacturer"  cssClass="minwidth"/>
        <peps:dataField item="${item}" key="upc.upn" />
    </fieldset> 
    </div>
    <div class="twoColumn">
    <fieldset style="width: 440px;"><legend class="legend">Physical Characteristics</legend>
        <peps:dataField item="${item}" key="color"  cssClass="matchwidth" />
        <peps:dataField item="${item}" key="imprint" cssClass="matchwidth" />
        <peps:dataField item="${item}" key="imprint2" cssClass="matchwidth" />
        <peps:dataField item="${item}" key="scored" cssClass="scored" cssClass="matchwidth" />
        <peps:dataField item="${item}" key="shape" cssClass="matchwidth" />
    </fieldset>   
    </div>
    <div class="horizontalspacer"></div>
    <div class="twoColumn">
    <fieldset style="width: 496px;"><legend class="legend">Local Pricing Information</legend>
        <peps:text item="${item}" key="order.unit" label="true" cssClass="readonlynational" />
        <peps:text item="${item}" key="unit.price" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="ndc.disp.units.per.ord.unit" abbreviation="true" />
        <peps:dataField item="${item}" key="ndc.price.per.dispense.unit" />
        <peps:dataField item="${item}" key="package.size" />   
        <peps:dataField item="${item}" key="package.type"  cssClass="minwidth" />  
        <peps:dataField item="${item}" key="vendor" /> 
        <peps:dataField item="${item}" key="vendor.stock.number" /> 
    </fieldset>  
    </div>
    <div class="twoColumn">
     <fieldset style="width: 540px;"><legend class="legend">Other Information</legend>
        <peps:dataField item="${item}" key="otc.rx.indicator" cssClass="matchwidth" /> 
        <peps:dataField item="${item}" key="protect.from.light" /> 
        <peps:dataField item="${item}" key="refrigeration" cssClass="matchwidth" /> 
        <peps:text item="${item}" key="application.package.use" label="true" cssClass="readonlynational" /> 
        <peps:dataField item="${item}" key="item.status" cssClass="matchwidth" /> 
        <peps:dataField item="${item}" key="proposed.inactivation.date" /> 
        <peps:text item="${item}" key="inactivation.date" label="true" cssClass="readonlynational" /> 
        <peps:text item="${item}" key="categories" label="true" cssClass="readonlynational" />
        <peps:dataField item="${item}" key="single.multi.source.ndc" cssClass="matchwidth" />
        <peps:dataField item="${item}" key="source" cssClass="matchwidth" />
        <peps:dataField item="${item}" key="product.number" cssClass="matchwidth" />
     </fieldset> 
     </div> 
     <div class="twoColumn">
     <fieldset style="width: 540px;"><legend class="legend">Sub-Category</legend>
                <div class="twoColumn">
					<fieldset class="subcat">
						<legend>Sub-Category</legend>
				   			<peps:dataField item="${item}"  key="sub.categories" labelPosition="none" cssClass="aligncheckbox1" />
					</fieldset>
				</div>
     </fieldset>  
    </div>
    <div>
    <fieldset style="width: 550px;"><legend class="legend">Previous Entries</legend>
        <peps:dataField item="${item}" key="previous.ndc" /> 
        <peps:dataField item="${item}" key="previous.upc.upn" /> 
    </fieldset>   
    </div>

</div>