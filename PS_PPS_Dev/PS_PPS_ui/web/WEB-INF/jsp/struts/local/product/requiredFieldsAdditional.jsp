<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard12b">
<peps:model ognlPrefix="item">
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">
    <fieldset><legend>Drug Classes and Synonyms/NDCs (Page 3 of 9)</legend>
    <div class="panel">
       <peps:dataField key="drug.classes" jsOnClick="selectPrimaryDrugClass(this);" />
    </div>
    <div class="panel">
        <peps:dataField key="synonyms" abbreviation="true" />
    </div>
    </fieldset>
    </div>
    
    <div class="buttonspacer"></div>
   
    <div id="buttons" class="panel">
        <span class="floatRight">
        <s:if test="%{getConversationScopeAttribute('templateSubFlow')}">
            <span class="column"><peps:submit
                key="button.cancel"
                event="cancel" /></span>
        </s:if>
        <s:else>
            <span class="column"><peps:cancel /></span>
        </s:else>
        <span class="column">
            <peps:submit
                key="button.bookmark"
                event="savePartial" />
        </span>        
        <span class="column">
            <peps:submit
                key="button.previous" 
                event="previous" />
        </span>       
        <span class="column">
            <peps:submit
                key="button.submit"
                event="submit" />
        </span>
        <span class="column">
            <peps:submit
                key="button.next"
                event="next" />
        </span>
        </span>
    </div>
        
</peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>
