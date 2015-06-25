<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div align="center" class="addproducttitle">
    Add New Local Product
</div>
<peps:model ognlPrefix="item"> 
<div class="twoColumn">
    <peps:text
        key="product.long.name"
        label="true" 
        cssClass="editheader" />
</div>
<div class="twoColumn">
    <s:set scope="page" name="oiId" value="item.orderableItem.id"/>
    <span class="column"><peps:redirectFlowLink flowId="manageItem2Flow"
                key="oi.name"
                label="true"
                itemId="${oiId}"
                itemType="ORDERABLE_ITEM" 
                cssClass="editheader" /></span>
<s:if test="(item.entityType.hasParent() && item.parent != null)">
    <span class="column">(<peps:text
        key="orderable.item.type"
        label="false" />)</span>
</s:if>
</div>
</peps:model>
