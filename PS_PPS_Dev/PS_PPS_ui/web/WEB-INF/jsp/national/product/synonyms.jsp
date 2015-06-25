<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
	
<peps:form id="ProductWizard4">
<%-- <peps:model ognlPrefix="item"> --%>
<div class="topspacer"></div>
    <div class="clearLeft">
    <fieldset><legend>Synonyms/NDC Fields (Page 5 of 10)</legend>
    <div class="twoColumn"><peps:dataField item="${item}" key="reorder.level" /></div>
    <div class="twoColumn"><peps:dataField item="${item}" key="recall.level" /></div>

    <div class="panel">
        <peps:dataField item="${item}" key="synonyms" abbreviation="true" />
    </div>
    </fieldset>
    </div>

    <div class="buttonspacer"></div>
   
    <div id="buttons" class="panel">
        <span class="floatRight">
        
	            <span class="column"><peps:submit
	                key="button.cancel" /></span>
        
        <span class="column">
            <peps:submit
                key="button.bookmark" />
        </span>        
        <span class="column">
            <peps:submit
                key="button.previous" />
        </span>       
        <span class="column">
            <peps:submit
                key="button.submit" />
        </span>
        <span class="column">
            <peps:submit
                key="button.next" />
        </span>
        </span>
    </div>
    
</peps:form>