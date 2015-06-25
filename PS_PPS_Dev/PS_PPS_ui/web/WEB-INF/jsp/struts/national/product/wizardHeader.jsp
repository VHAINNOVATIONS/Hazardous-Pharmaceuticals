<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib prefix="peps" tagdir="/WEB-INF/tags"%>
<div align="center" class="addproducttitle">
    (Sample Header)Add New FDB National Product
        
</div>
<div class="threeColumn" align="center">
Generic Name: ACETAMINOPHEN</div>
<div class="threeColumn" align="center">
VA Print Name: ACETAMINOPHEN 325MG TAB</div>
<div class="threeColumn" align="center">
Trade Name: TYLENOL
</div>
<div class="horizontalspacer"></div>

<peps:model ognlPrefix="item"> 
<div class="twoColumn">
    <peps:text
        key="product.long.name"
        label="true" 
        cssClass="editheader" />
</div>
<div class="twoColumn">
    <s:set scope="page" name="oiId" value="item.orderableItem.id"/>
    <s:set scope="page" name="oiType" value="item.orderableItem.type"/>
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