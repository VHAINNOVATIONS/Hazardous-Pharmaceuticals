<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<peps:form id="NDCWizard1">
<peps:model ognlPrefix="item">
<div class="twoColumn floatRight">
<s:if test="(item.entityType.hasParent() && item.parent != null ) ">
    <peps:submit key="button.change.ndc.parent" event="selectParent" />
</s:if>
<s:else>
    <peps:submit key="button.select.ndc.parent" event="selectParent" />
</s:else>
</div>
<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>
    <div class="clearLeft">
    <fieldset class="background1" ><legend>NDC Information</legend>
    <div class="twoColumn">
      <peps:dataField key="ndc" cssClass="uniqueness" />
      <peps:dataField key="upc.upn" cssClass="uniqueness" />
      <peps:dataField key="trade.name" cssClass="uniqueness" />
      <peps:dataField key="package.type" cssClass="uniqueness" />           
      <peps:dataField key="package.size" cssClass="uniqueness" />
      <peps:dataField key="manufacturer" cssClass="uniqueness" />
             <peps:dataField  key="otc.rx.indicator" />
    </div>
    </fieldset>
        <div class="horizontalspacer"></div>
    
      <fieldset class="background2"><legend>Pill Information</legend>
     <div class="threeColumn2left">
       <peps:dataField  key="product.number" />  

       <peps:dataField  key="scored" />
       <peps:dataField  key="color" />
       <peps:dataField  key="shape" /> 
       <peps:dataField  key="imprint" />
       <peps:dataField  key="imprint2" />
       
    </div>
    <div class="twoColumnwide">

       <peps:dataField  key="inactivation.date" />       
       <peps:dataField  key="protect.from.light" cssClass="aligncheckbox6" />
       <peps:dataField  key="refrigeration" />

       <peps:dataField  key="image" />

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
                key="button.submit"
                event="next" />
        </span>      

        </span>
    </div>

    </peps:model>
</peps:form>
<div id="partial.save.flag" class="hidden"><s:property value="item.partialSave" /></div>