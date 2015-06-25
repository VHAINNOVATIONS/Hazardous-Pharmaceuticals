<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.RxConsultType"%>
<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.struts.OgnlUtility"%>
<%
RxConsultType type = OgnlUtility.findValue(request, "item.rxConsultType");
%>
<h1>Add <%=OgnlUtility.getText(request, type)%> <peps:localizeName key="rx.consult" /></h1>

<div class="topspacer">&nbsp;&nbsp;Fields marked with <font color="red">*</font> are required</div>

<peps:form id="rxConsultForm">
    <peps:model ognlPrefix="item">
        <peps:dataField key="value" />
        <peps:dataField
            key="consult.text"
            rows="4"
            cols="10" />
        <peps:dataField
            key="spanish.translation"
            rows="4"
            cols="10" />
        <peps:dataField
            key="warning.mapping"
            managedDataAddLink="false" />
    </peps:model>

    <br />
    <span class="column"><peps:submit
        key="button.cancel"
        event="cancel" /></span>
    <span class="column"> <peps:submit
        key="button.submit"
        event="submit" /> </span>


</peps:form>