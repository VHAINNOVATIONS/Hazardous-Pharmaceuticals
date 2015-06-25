<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.RxConsultVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
            Collection<RxConsultVo> localSelected = OgnlUtility.findValue(request, "item.localWarningLabels");
            Collection<RxConsultVo> nationalSelected = OgnlUtility.findValue(request, "item.nationalWarningLabels");

            Collection<RxConsultVo> nationalDomain = OgnlUtility.findValue(request,
                "domainUtility.getDomain('national.warning.labels')");
            pageContext
                .setAttribute("nationalLabel", FieldKey.NATIONAL_WARNING_LABELS.getLocalizedName(request.getLocale()));

            Collection<RxConsultVo> localDomain = OgnlUtility.findValue(request,
                "domainUtility.getDomain('local.warning.labels')");
            Collection<RxConsultVo> localAvailable = new LinkedHashSet<RxConsultVo>(localDomain);
            localAvailable.removeAll(localSelected);
            localAvailable.removeAll(nationalSelected);
            pageContext.setAttribute("localAvailable", localAvailable);
            pageContext.setAttribute("localLabel", FieldKey.LOCAL_WARNING_LABELS.getLocalizedName(request.getLocale()));
%>
<s:set
    name="availableLabels"
    value="getText('available.warning.labels')"
    scope="page" />
<s:set
    name="selectedLabels"
    value="getText('selected.warning.labels')"
    scope="page" />
<div class="panel"><s:if test="item.newInstance">
    <s:optiontransferselect
        id="allWarningLabels"
        label="%{#attr.nationalLabel}"
        leftTitle="%{#attr.availableLabels}"
        name="allWarningLabels"
        list="#attr.nationlDomain"
        listKey="id"
        listValue="value"
        cssStyle="width: 550px"
        size="20"
        allowAddAllToLeft="false"
        allowSelectAll="false"
        allowAddAllToRight="false"
        allowUpDownOnLeft="false"
        allowUpDownOnRight="true"
        rightTitle="%{#attr.selectedLabels}"
        doubleId="selectedWarningLabels"
        doubleName="item.nationalWarningLabels"
        doubleList="item.nationalWarningLabels"
        doubleListKey="id"
        doubleListValue="value"
        doubleCssStyle="width: 550px"
        doubleSize="20"
        doubleOnchange="displayWarningLabelText(this.id)"
        onchange="displayWarningLabelText(this.id)"
        doubleOnclick="displayWarningLabelText(this.id)"
        onclick="displayWarningLabelText(this.id)" />
</s:if><s:else>
    ${nationalLabel}:
    <peps:table
        tableId="national_warning_labels"
        dataOgnl="nationalWarningLabels"
        fieldsOgnl="@gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory@defaultRxConsult()" 
        paged="false" />
</s:else><s:optiontransferselect
    id="allWarningLabels"
    label="%{#attr.localLabel}"
    leftTitle="%{#attr.availableLabels}"
    name="allWarningLabels"
    list="#attr.localAvailable"
    listKey="id"
    listValue="value"
    cssStyle="width: 550px"
    size="20"
    allowAddAllToLeft="false"
    allowSelectAll="false"
    allowAddAllToRight="false"
    allowUpDownOnLeft="false"
    allowUpDownOnRight="true"
    rightTitle="%{#attr.selectedLabels}"
    doubleId="selectedWarningLabels"
    doubleName="item.localWarningLabels"
    doubleList="item.localWarningLabels"
    doubleListKey="id"
    doubleListValue="value"
    doubleCssStyle="width: 550px"
    doubleSize="20"
    doubleOnchange="displayWarningLabelText(this.id)"
    onchange="displayWarningLabelText(this.id)"
    doubleOnclick="displayWarningLabelText(this.id)"
    onclick="displayWarningLabelText(this.id)" />
<fieldset><legend>Selected Warning Label Text</legend><span id="displayed.warning.label.text">No warning label
selected.</span></fieldset>
</div>
<div class="hidden"><span id="no.warning.label.text">No warning label selected.</span><%
 for (RxConsultVo rxConsult : nationalDomain) {
 %><span id="<%= rxConsult.getId() %>.text"><%=rxConsult.getConsultText()%></span><%
            }

            for (RxConsultVo rxConsult : localDomain) {
%><span id="<%= rxConsult.getId() %>.text"><%=rxConsult.getConsultText()%></span><%
 }
 %></div>