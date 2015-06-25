<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>  
<div class="panel">
    <div class="twoColumn">
        <peps:text item="${item}" key="va.print.name" label="true" cssClass="fortychars highrisk" />
        <peps:text item="${item}" key="drug.classes" label="true" cssClass="fortychars highrisk" />
        <peps:text item="${item}" key="dispense.unit" label="true" cssClass="fortychars highrisk" />
        <peps:text item="${item}" key="cmop.id" label="true" cssClass="fortychars highrisk" />
        <peps:text item="${item}" key="transmit.to.cmop" label="true" cssClass="fortychars highrisk" />
    </div>
    <div class="twoColumn">
        <peps:text item="${item}" key="order.unit" label="true" cssClass="highrisk" />
        <peps:text item="${item}" key="product.dispense.unit.per.ord" label="true" cssClass="highrisk" />
        <peps:text item="${item}" key="application.package.use" label="true" cssClass="highrisk" />
        <peps:text item="${item}" key="product.price.per.dispense.unit" label="true" cssClass="highrisk" />
    </div>
</div>
<div class="panel">
    <div class="twoColumn">
    	<peps:dataField item="${item}" key="quantity.dispense.message" cssClass="highrisk" />
    	<peps:text item="${item}" key="product.price.per.order.unit" label="true" cssClass="fortychars highrisk" />
    	<peps:dataField item="${item}" key="product.dispense.unit.per.ord" cssClass="highrisk" />
    	<peps:text item="${item}" key="local.print.name" label="true" cssClass="fortychars highrisk" />
    </div>
</div>