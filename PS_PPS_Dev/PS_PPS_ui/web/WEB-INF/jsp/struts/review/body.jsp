<%@ taglib
    uri="http://tiles.apache.org/tags-tiles"
    prefix="tiles"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ taglib 	
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %> 
    	
<c:choose>	    
<c:when test="${not empty requests}">
    <div
        id="TabbedPanels1"
        class="TabbedPanels">
	    <ul class="TabbedPanelsTabGroup">
	
	        <!-- Setting tab index to 0 provides for keyboard navigation -->
	        <li class="TabbedPanelsTab"><a href="#pending_requests"><peps:localizeName key="pending.requests" /></a></li>
	        <li class="TabbedPanelsTab"><a href="#cots_update"><peps:localizeName key="cots.update" /></a></li>
	    </ul>
	    <div class="TabbedPanelsContentGroup">
		    <div class="TabbedPanelsContent">
			    <a name="pending_requests" />
			    <peps:table
			        tableId="searchTable"
			        dataModel="${requests}"
			        printTemplate="${requestSearchPrintTemplate}"
			        paged="false" />
		    </div>
		    <div class="TabbedPanelsContent">
		   		<a name="cots_update" />COTS UPDATE -- COMING EVENTUALLY!
		    </div>
	    </div>
    </div>
    <script type="text/javascript">
	<!--
	var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
	//-->
 
</script>
</c:when>
<c:when test="${empty requests && !isFirstRun}">
    <div
        id="TabbedPanels1"
        class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
    
            <!-- Setting tab index to 0 provides for keyboard navigation -->
            <li class="TabbedPanelsTab"><a href="#pending_requests"><peps:localizeName key="pending.requests" /></a></li>
            <li class="TabbedPanelsTab"><a href="#cots_update"><peps:localizeName key="cots.update" /></a></li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <a name="pending_requests" />
                <peps:table
                    tableId="searchTable"
                    dataModel="${requests}"
                    printTemplate="${requestSearchPrintTemplate}"
                    paged="false" />
            </div>
            <div class="TabbedPanelsContent">
                <a name="cots_update" />COTS UPDATE -- COMING EVENTUALLY!
            </div>
        </div>
    </div>
    <script type="text/javascript">
    <!--
    var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
    //-->
 
</script>
</c:when>
</c:choose>