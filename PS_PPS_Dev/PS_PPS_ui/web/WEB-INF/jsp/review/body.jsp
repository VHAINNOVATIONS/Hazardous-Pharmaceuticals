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
<c:when test="${requests != null}">
    <div
        id="TabbedPanels1"
        class="TabbedPanels">
	    <ul class="TabbedPanelsTabGroup">
	
	        <!-- Setting tab index to 0 provides for keyboard navigation -->
	        <li class="TabbedPanelsTab"><a href="#pending_requests"><peps:localizeName key="pending.requests" /></a></li>
	    </ul>
	    <div class="TabbedPanelsContentGroup">
		    <div class="TabbedPanelsContent">
			    <a name="pending_requests" /></a>
			    <peps:table
			        tableId="searchTable"
			        dataModel="${requests}"
			        printTemplate="${printTemplate}"
			        paged="false" />
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