<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%> 
<!--Create the Vertical Tabbed Panel for the Quick Actions Tab -->
<div class="horizontalspacer"></div>
    <div class="VTabbedPanels" id="TabbedPanels3" style="width: 1240px; min-height: 500px;">
        <ul class="TabbedPanelsTabGroup" >
            <li class="TabbedPanelsTab"><a href="#">CMOP Mark / Unmark</a></li> 
            <li class="TabbedPanelsTab"><a href="#">Enter / Edit Dosages</a></li> 
            <li class="TabbedPanelsTab"><a href="#">Print PMI</a></li> 
            <li class="TabbedPanelsTab"><a href="#">Print Warning Labels</a></li> 
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent"
            id="cmop_MarkUnmark_tab"><a name="cmop_MarkUnmark_tab_link"></a>
                <tiles:insertAttribute name="cmop.mark" />                
            </div>
            <div class="TabbedPanelsContent"
            id="enteredit_Dosages_tab"><a name="enteredit_Dosages_tab_link"></a>                
                <tiles:insertAttribute name="enter.edit.dosages" />                
            </div>
            <div class="TabbedPanelsContent" 
            id="print_PMI_tab"><a tabindex="3" name="print_PMI_tab_link"></a>                
                <tiles:insertAttribute name="view.pmi" />                
            </div>
            <div class="TabbedPanelsContent"
            id="print_warninglabels_tab"><a tabindex="4" name="print_warninglabels_tab_link"></a>                
                <tiles:insertAttribute name="view.warninglabels" />                
            </div>
        </div> 
    </div>

<!--Initialize the Tabbed Panel widget object-->
<script type="text/javascript">
    var retrievePMI = getCookie("retrievePMI");
    var warningLabels = getCookie("warningLabels");
    var TabbedPanels3;
    if (retrievePMI) {
        TabbedPanels3 = new Spry.Widget.TabbedPanels("TabbedPanels3",{defaultTab:2});
        deleteCookie("retrievePMI");
    } else if (warningLabels) {
        TabbedPanels3 = new Spry.Widget.TabbedPanels("TabbedPanels3",{defaultTab:3});
        deleteCookie("warningLabels");
    } else {
        TabbedPanels3 = new Spry.Widget.TabbedPanels("TabbedPanels3");
    }
</script> 