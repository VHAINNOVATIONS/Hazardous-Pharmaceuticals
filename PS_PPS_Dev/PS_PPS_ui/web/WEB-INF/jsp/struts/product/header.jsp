<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
<peps:model ognlPrefix="item">
    <div class="threeColumn"><b><font size="3"><peps:text
        key="product.long.name"
        label="true" cssClass="producteditheader" /></font></b>
        <peps:text
        key="primary.drug.class"
        label="true" abbreviation="true" cssClass="producteditheader" />
        <!-- clicking Local use label activated check box - changed label to false and added Local Use text -->
        Local Use:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<peps:text
        key="local.use"
        label="false" cssClass="producteditheader" />
    </div>
    <div class="fourColumn">
        <s:if test="item.itemStatus.inactive">
        <span class="columnnopadding"><peps:text
        key="item.status"
        label="true" cssClass="editheader redText boldText" dataCssClass="redText boldText" /></span><span class="column">(<peps:text 
        key="inactivation.date"
        label="false" cssClass="producteditheader" />)</span>
        </s:if>
        <s:else>
        <peps:text
        key="item.status"
        label="true" cssClass="editheader" />
        </s:else> 
        <peps:text
        key="request.item.status"
        label="true" cssClass="editheader" />
    </div>
    <div class="threeColumn2"><s:set
        scope="page"
        name="oiId"
        value="item.orderableItem.id" /><s:set scope="page" name="oiLinkAltText" value="getText('click.to.edit.field') + ' ' + getText('ORDERABLE_ITEM')" /><span class="columnnopadding"><peps:redirectFlowLink
        id="oiParentLink"
        flowId="manageItem2Flow"
        key="oi.name"
        label="true"
        abbreviation="true"
        tooltip="${oiLinkAltText}"
        itemId="${oiId}"
        itemType="ORDERABLE_ITEM" cssClass="editheader" /></span>
        <span class="column">(<peps:text
        key="orderable.item.type"
        label="false" />)&nbsp;
<%--         <s:if test="item.orderableItem.localUse"><img src="images/mflu.gif" alt="Marked for Local Use"/></s:if> --%>
<%--         <s:else><img src="images/nmflu.gif" alt="Not marked for Local Use"/></s:else> --%>
        </span><br />
        <peps:text
        key="application.package.use"
        label="true" cssClass="editheader" />
    </div>
    <div class="horizontalspacer"></div>
    <s:set
        scope="page"
        name="itemTypeString"
        value="itemType.toFieldKey()" />
    <div class="panel">
    <center><b><font size="3">
    <s:if test="%{mainRequest.requestType.addition}">
        <peps:label key="${itemTypeString}" /> Addition Request
    </s:if>
    <s:if test="%{mainRequest.requestType.modification}">
        <peps:label key="${itemTypeString}" /> Modification Request
    </s:if>
    </font></b></center>
</peps:model>

</div>
<div class="horizontalspacer"></div>