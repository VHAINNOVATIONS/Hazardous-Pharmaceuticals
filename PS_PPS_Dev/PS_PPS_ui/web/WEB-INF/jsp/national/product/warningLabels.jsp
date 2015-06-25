<%@page import="gov.va.med.pharmacy.peps.common.vo.ManagedItemVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.ApplicationContextUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib 
	prefix="spring" 
	uri="http://www.springframework.org/tags" %>    
<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.RxConsultVo"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility"%>

<%
            
DomainUtility domainUtility = ApplicationContextUtility.getSpringBean(config.getServletContext(), DomainUtility.class);
Collection<RxConsultVo> domain = domainUtility.getDomain(FieldKey.NATIONAL_WARNING_LABELS);
ManagedItemVo item = (ManagedItemVo)request.getAttribute("item");
Collection<RxConsultVo> selected = (Collection<RxConsultVo>)JspTagUtility.getFieldValue(item, FieldKey.NATIONAL_WARNING_LABELS);
Collection<RxConsultVo> available = new ArrayList<RxConsultVo>(domain);
available.removeAll(selected);
pageContext.setAttribute("available", available);

pageContext.setAttribute("nationalLabel", FieldKey.NATIONAL_WARNING_LABELS.getLocalizedName(request.getLocale()));
%>
<c:set var="availableLabelsStr" value="available.warning.labels" />
<spring:message var="availableLabels" scope="page" code="${availableLabelsStr}" />

<c:set var="selectedLabelsStr" value="selected.warning.labels" />
<spring:message var="selectedLabels" scope="page" code="${selectedLabelsStr}" />
<div class="panel">

<!-- <s:optiontransferselect
    id="allWarningLabels"
    label="%{#attr.nationalLabel}"
    leftTitle="%{#attr.availableLabels}"
    name="allWarningLabels"
    list="#attr.available"
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
    onclick="displayWarningLabelText(this.id)" /> -->
    
<fieldset><legend>Selected Warning Label Text</legend><span id="displayed.warning.label.text">No warning label
selected.</span></fieldset>
</div>
<div class="hidden"><span id="no.warning.label.text">No warning label selected.</span>
<%
for (RxConsultVo rxConsult : domain) {
%><span id="<%= rxConsult.getId() %>.text"><%=rxConsult.getConsultText()%></span>
<%
}
%>
</div>