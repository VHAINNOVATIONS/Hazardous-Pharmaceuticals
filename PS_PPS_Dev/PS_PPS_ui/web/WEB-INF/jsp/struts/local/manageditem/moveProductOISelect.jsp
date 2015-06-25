<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<peps:form id="moveProductOISelect">
<div class="instructionalText">This functionality enables the user move a product or products from one orderable item to another orderable item. 
<p>You may only move products from a National Orderable Item to one of its Local Orderable Items.  You may <b>NOT</b> move a product from one National Orderable Item to another National Orderable Item.</div>
<fieldset>
    <LEGEND>Search for First Orderable Item.</LEGEND>
    <p class="instructionalText">Search for the first orderable item. This may be either a local or a national orderable item.</p>
    
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
<s:if test="item != null">
<fieldset>
    <LEGEND>Search for Second Orderable Item.</LEGEND>
    <p class="instructionalText">Search for the second orderable item. If a local orderable item was selected above, search results are limited to local orderable items with the same parent as the selected local orderable item. If a national orderable item was selected first, search results will be limited to any local orderable items that have the selected national orderable item as its parent.</p>
    
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
</s:if>
</peps:form>