<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div align="center" class="addproducttitle">
    Add New NDC
</div>

<div class="twoColumn">
    <peps:text 
        key="ndc"
        item="${item}" label="true" cssClass="editheader" />
    <peps:text 
        key="request.state"
        item="${item}" label="true" cssClass="editheader" /> 
</div>
<div class="twoColumn">
    <peps:text 
        key="product"
        item="${item}" label="true" cssClass="editheader" />
    <peps:text 
        key="generic.name"
        item="${item}" label="true" cssClass="editheader" /> 
</div>
