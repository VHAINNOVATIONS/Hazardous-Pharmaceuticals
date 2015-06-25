<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div align="center" class="addproducttitle">
    Add New NDC
</div>
<peps:model ognlPrefix="item"> 
<div class="twoColumn">
    <peps:text 
        key="ndc"
        label="true" cssClass="editheader" />
    <peps:text 
        key="request.state"
        label="true" cssClass="editheader" /> 
</div>
<div class="twoColumn">
    <peps:text 
        key="product"
        label="true" cssClass="editheader" />
    <peps:text 
        key="generic.name"
        label="true" cssClass="editheader" /> 
</div>
</peps:model>