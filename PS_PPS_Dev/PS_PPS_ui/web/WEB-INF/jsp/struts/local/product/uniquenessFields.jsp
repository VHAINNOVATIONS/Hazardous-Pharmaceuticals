<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="ProductWizard1">
<peps:model ognlPrefix="item">
<div class="twoColumn floatRight">
<s:if test="(item.entityType.hasParent() && item.parent != null ) ">
    <peps:submit key="button.change.product.parent" event="selectParent" />
</s:if>
<s:else>
    <peps:submit key="button.select.product.parent" event="selectParent" />
</s:else>
</div>
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">
    <fieldset><legend>Unique Identification Fields (Page 1 of 9)</legend>
    <div class="uniquenesspanel">
    <div class="panel"><peps:dataField key="active.ingredients" /></div>
        <peps:dataField key="generic.name" cssClass="uniqueness" />
    <div class="twoColumnwide"> 
        <peps:dataField key="dispense.unit" cssClass="uniqueness" />
        <peps:submit key="button.compute" event="computeFields" onclick="checkIngredients();" />
        <peps:dataField key="va.product.name" cssClass="uniqueness vafieldswidth" maxlength="64" />
        <peps:dataField key="va.print.name" cssClass="uniqueness vafieldswidth" maxlength="40" />
    </div>
    <div class="threeColumn2">    
        <peps:dataField key="product.ndc" />       
        <peps:dataField key="local.use" cssClass="aligncheckbox5" />
        <peps:dataField key="application.package.use" cssClass="apppackageuse" />
    </div>
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
                key="button.next"
                event="next" />
        </span>
        </span>
    </div>
    
    </peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>