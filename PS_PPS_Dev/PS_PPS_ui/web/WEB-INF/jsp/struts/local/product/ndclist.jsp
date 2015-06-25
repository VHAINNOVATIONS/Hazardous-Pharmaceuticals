<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<div class="panel">
<fieldset style="width: 998px;">
<div class="twoColumn"><peps:dataField key="normal.amount.to.order" /><peps:dataField key="reorder.level" /><peps:dataField
    key="source" /></div>
<div class="twoColumn"><peps:text
    key="single.multi.source.product"
    label="true"
    cssClass="readonly" /> <peps:dataField
    key="long.term.out.of.stock"
    cssClass="aligncheckbox720" /></div>
</fieldset>
</div>
<div class="horizontalspacer"></div>
<div class="panel"><peps:text
    key="dispense.unit"
    label="true"
    cssClass="readonly" /></div>
<%
            java.util.Map parameters = new java.util.HashMap();
            parameters.put(gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction.TAB_ELEMENT_ID,
            gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction.CHILDREN_TAB);
%>
<peps:table
    tableId="<%= gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction.NDC_LIST_TABLE %>"
    dataOgnl="ndcs"
    fieldsOgnl="ndcListPrintTemplate"
    export="false"
    sortExportLinkParameters="<%=parameters%>" />
<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="openBlankChildTemplate"
    value="%{getText('button.open.blank.ndc.template')}" />
<peps:redirectFlowLink
    text="${openBlankChildTemplate}"
    flowId="manageTemplate"
    itemId="${itemId}"
    itemType="NDC"
    templateMethod="openBlankChildTemplate" />