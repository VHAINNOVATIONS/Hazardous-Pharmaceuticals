<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<peps:form id="moveNDCsProductSelect">
<div class="instructionalText">This page is used to move a product or products from one orderable item to another orderable item. 
<p>First select two orderable items and then transfer the products between them.</div>
<fieldset>
    <LEGEND>Search for First Orderable Item.</LEGEND>
    
    <div>
        Orderable Item One: 
        <peps:text key="oi.name" label="false" ognlPrefix="item" />        
    </div>
    <div>
        &nbsp;
    </div>
    <div>
        <peps:submit key="button.search" event="searchOI1" />
    </div>
</fieldset>

<fieldset>
    <LEGEND>Search for Second Orderable Item.</LEGEND>
    
    <div>
        Orderable Item Two: 
        <peps:text key="oi.name" label="false" ognlPrefix="item2" /> 
    </div>
    <div>
        &nbsp;
    </div>
    <div>
        <peps:submit key="button.search" event="searchOI2" />
    </div>
</fieldset>
</peps:form>