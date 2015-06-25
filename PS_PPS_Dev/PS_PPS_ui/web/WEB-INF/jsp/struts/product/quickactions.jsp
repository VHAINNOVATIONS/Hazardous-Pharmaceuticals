<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<!--Create the Vertical Tabbed Panel for the Quick Actions Tab -->
<div class="horizontalspacer"></div>
    <div class="VTabbedPanels" id="TabbedPanels3" style="width: 1240px;">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab">CMOP Mark / Unmark</li> 
            <li class="TabbedPanelsTab">Enter / Edit Dosages</li> 
            <li class="TabbedPanelsTab">Print PMI</li> 
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent"
            id="cmop_MarkUnmark_tab"><a name="cmop_MarkUnmark_tab_link"></a>
                <peps:model ognlPrefix="item">
                <tiles:insertAttribute name="cmop.mark" />
                </peps:model>
            </div>
            <div class="TabbedPanelsContent"
            id="enteredit_Dosages_tab"><a name="enteredit_Dosages_tab_link"></a>
                <peps:model ognlPrefix="item">
                <tiles:insertAttribute name="enter.edit.dosages" />
                </peps:model>
            </div>
            <div class="TabbedPanelsContent"
            id="print_PMI_tab"><a name="print_PMI_tab_link"></a>
                <peps:model ognlPrefix="item">
                <tiles:insertAttribute name="view.pmi" />
                </peps:model>
            </div>
        </div> 
    </div>
<!--Initialize the Tabbed Panel widget object-->
<script type="text/javascript">
    var retrievePMI = getCookie("retrievePMI");
    var TabbedPanels3;
    if (retrievePMI) {
        TabbedPanels3 = new Spry.Widget.TabbedPanels("TabbedPanels3",{defaultTab:3});
        deleteCookie("retrievePMI");
    } 
    else {
        TabbedPanels3 = new Spry.Widget.TabbedPanels("TabbedPanels3");
    }
</script> 