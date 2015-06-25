<%@page import="gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@page import="java.util.List"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.common.utility.FieldKeyUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.action.ManagedItemAction"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility"%>gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtilitystruts.OgnlUtility"%>gov.va.med.pharmacy.peps.presentation.common.context.UserContextontext.UserContext"%>
<form
    id="managedItemForm"
    method="post"
    action="<%=UrlUtility.createMvcExecutionUrl(request)%>"
    onsubmit="selectAllTheOptions('selectedWarningLabels'); return disableSubmit(this.id);">
<%
            ManagedItemVo managedItem = OgnlUtility.findValue(request, "item");            
            String tabToDisplay = (String) request.getAttribute(ManagedItemAction.TAB_ELEMENT_ID);
%>
<s:set
    scope="page"
    name="itemId"
    value="item.id" />
<s:set
    scope="page"
    name="itemTypeString"
    value="itemType.toFieldKey()" />
<H1><s:text name="title.edit" />&nbsp;<s:text name="item.value" /><peps:label key="${itemTypeString}" /></H1>
<div class="panel">
  <peps:model ognlPrefix="item">
      <div>
        <peps:label key="primary.drug.class" />:&nbsp;
        <peps:text
        key="primary.drug.class"
        label="false" />
      </div>
      <div>
        <peps:label key="local.use" />:&nbsp;
        <peps:text
        key="local.use"
        label="false" />
      </div>
      <div>
        <peps:label key="application.package.use" />:&nbsp;
        <peps:text
        key="application.package.use"
        label="false" />
      </div>
  </peps:model>
</div>
<div class="horizontalspacer"></div>
<fieldset>
<div>
    <peps:model ognlPrefix="item">
        <%@ include file="inpatient_unitdose.jsp" %>
    </peps:model> 
</div>
</fieldset>
<div class="horizontalspacer"></div>
<%
            UserContext userContext = (UserContext) session.getAttribute(ControllerConstants.USER_CONTEXT_KEY);
            boolean canEdit = userContext != null && userContext.getUser().isEitherManager();
            if (canEdit) {
%>
<div class="panel">
<div class="floatRight">
    <span class="column"> <peps:submit
        key="button.submit"
        event="submitModifications" />
    </span>
</form>
</div>
</div>
<%
}
%>
<div id="dirty.flag" class="hidden"><s:property value="oldItem.diff(item).size() > 0"/></div>
<div id="partial.save.flag" class="hidden"><s:property value="oldItem.partialSave" /></div>