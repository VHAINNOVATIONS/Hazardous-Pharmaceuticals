<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<peps:form id="moveNDCsProductSelect">
<div class="instructionalText">This page is used to move NDCs from one product to another product. 
<p>First select two Products and then transfer the NDCs between them.</div>

<fieldset>
    <LEGEND>Search for First Product</LEGEND>
    
    <div>
        First Product:
        <peps:text key="va.product.name" label="false" ognlPrefix="item" /> 
    </div>
    <div>
        &nbsp;
    </div>
    <div>
        <peps:submit key="button.search" event="searchProduct1" />
    </div>

</fieldset>

<fieldset>
    <LEGEND>Search for Second Product</LEGEND>
    <div>
        Second Product:
        <peps:text key="va.product.name" label="false" ognlPrefix="item2" /> 
    </div>
    <div>
        &nbsp;
    </div>
    <div>
        <peps:submit key="button.search" event="searchProduct2" />
    </div>

</fieldset>

</peps:form>