<%@page import="gov.va.med.pharmacy.peps.common.vo.FieldKey"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.datafield.DataField"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>

<%@page import="gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility"%>
<%@page import="gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField"%>
<%@page import="java.util.List"%>
<%
            // NOTE: The checkbox list implementation for Application Package Use is tightly coupled with the values stored in the database!
            // In other words, the database values are hard coded below! If the database changes, this implementation must change too!

            ListDataField<String> applicationPackageUse = DataField.newInstance(FieldKey.APPLICATION_PACKAGE_USE);
            List<String> values = applicationPackageUse.getValue();
%>
<!-- Correct https://issues.apache.org/struts/browse/WW-2802 -->
<input
    type="hidden"
    id="__multiselect_application.package.use"
    name="__multiselect_item.dataFields['application.package.use']"
    value="" />

<!--Create the Vertical Tabbed Panel for the Application Tab -->
<div class="horizontalspacer"></div>
<div
    class="VTabbedPanels"
    id="TabbedPanels2"
    style="width: 1240px; height: 500px;">
<ul class="TabbedPanelsTabGroup">
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="O-OUTPATIENT"
        id="application.package.use.outpatient"
        <%=values.contains("O-OUTPATIENT") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Outpatient</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="U-UNIT DOSE"
        id="application.package.use.unit.dose"
        <%=values.contains("U-UNIT DOSE") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Inpatient - Unit Dose</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="I-IV"
        id="application.package.use.iv"
        <%=values.contains("I-IV") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;IV</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="S-SURGERY"
        id="application.package.use.surgery"
        disabled="disabled"
        <%=values.contains("S-SURGERY") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Surgery</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="W-WARD STOCK"
        id="application.package.use.ward.stock"
        <%=values.contains("W-WARD STOCK") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Ward Stock</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="D-DRUG ACCOUNTABILITY"
        id="application.package.use.drug.accountability"
        <%=values.contains("D-DRUG ACCOUNTABILITY") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Drug Accountability</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="N-CONTROLLED SUBSTANCES"
        id="application.package.use.controlled.substances"
        <%=values.contains("N-CONTROLLED SUBSTANCES") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Controlled Substance</label></li>
    <li class="TabbedPanelsTab"><input
        type="checkbox"
        name="item.dataFields['application.package.use']"
        value="X-NON-VA MED"
        id="application.package.use.non.va.med"
        <%=values.contains("X-NON-VA MED") ? "checked=\"checked\"" : ""%> /><label
        class="label">&nbsp;&nbsp;&nbsp;&nbsp;Non-VA Med</label></li>
</ul>
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent"
    id="outpatient_tab"><a name="outpatient_tab_link"></a> 
<tiles:insertAttribute name="outpatient" />
</div>
<div
    class="TabbedPanelsContent"
    id="inpatient_tab"><a name="inpatient_tab_link"></a>
    <tiles:insertAttribute name="unit.dose" />
</div>
<div class="TabbedPanelsContent">The details for this option have not yet been defined</div>
<div class="TabbedPanelsContent"></div>
<div
    class="TabbedPanelsContent"
    id="ward_stock_tab"><a name="ward_stock_tab_link"></a>
    <tiles:insertAttribute name="ward.stock" />
</div>
<div
    class="TabbedPanelsContent"
    id="drug_accountability_tab"><a name="drug_accountability_tab_link"></a> 
    <tiles:insertAttribute name="drug.accountability" />
</div>
<div
    class="TabbedPanelsContent"
    id="controlled_substance_tab"><a name="controlled_substance_tab_link"></a> 
    <tiles:insertAttribute name="controlled.substance.jsp" />
</div>
<div
    class="TabbedPanelsContent"
    id="nonva_med_tab"><a name="nonva_med_tab_link"></a>
    <tiles:insertAttribute name="non.va.med" />
</div>
</div>
</div>

<!--Initialize the Tabbed Panel widget object-->
<script type="text/javascript">
    var atcChoice = getCookie("atcChoice");
    var TabbedPanels2; 
    if (atcChoice) {
        TabbedPanels2 = new Spry.Widget.TabbedPanels("TabbedPanels2",{defaultTab:1});
        deleteCookie("atcChoice");
    } 
    else {
        TabbedPanels2 = new Spry.Widget.TabbedPanels("TabbedPanels2");
    }
</script> 