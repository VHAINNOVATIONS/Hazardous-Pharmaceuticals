<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
    <div class="twoColumn">
        <peps:text key="va.print.name" label="true" cssClass="editheader"/>
        <peps:text key="drug.classes" label="true" cssClass="editheader"/>
        <peps:text key="dispense.unit" label="true" cssClass="editheader"/>
        <peps:text key="cmop.id" label="true" cssClass="editheader"/>
    </div>
    <div class="twoColumn">
        <peps:text key="order.unit" label="true" cssClass="editheader"/>
        Dispense Units/Order Unit:
        <peps:text key="application.package.use" label="true" cssClass="editheader"/>
        <peps:text key="product.price.per.dispense.unit" label="true" cssClass="editheader"/>
    </div>
</div>
<div class="horizontalspacer"></div>
<div class="panel">
    <peps:dataField key="transmit.to.cmop" description="true" cssClass="aligncheckboxoptions2" />
    <peps:dataField key="quantity.dispense.message" />
    <peps:dataField key="product.price.per.order.unit" />
    <peps:dataField key="product.dispense.unit.per.ord" />
    <peps:dataField key="local.print.name" />
</div>