<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Risperdal TAB</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Details.css" rel="stylesheet" type="text/css" />

</head>

<body>
	<div id="highestLevel">
        <div class="productId">
            <b>Orderable Item:</b> RISPERDAL TAB
        </div colLeft>
        <br/>
        <div class="generalInfo">
            <b> Item Type: </b> Medication
        </div>

		<div id="TabbedPanels1" class="TabbedPanels">
            <ul class="TabbedPanelsTabGroup">
                <li class="TabbedPanelsTab" tabindex="0">
                	Item Details                </li>
                <li class="TabbedPanelsTab" tabindex="1">
                	<b> 6 </b>Products                </li>
            </ul>
            <div class="TabbedPanelsContentGroup">
                <div id="ItemDetails" class="TabbedPanelsContent">
                	<jsp:include page="ItemDetails.jsp"/>
                </div>
                <div id="Products" class="TabbedPanelsContent">
                	<jsp:include page="Products.jsp"/>  
                </div>
            </div>
        </div>
        
        <div id="buttons" class="buttonPanel">
        	<div class="buttonLeft">
	            <input name="Print" type="button" value="Print" class="button"/>
    	        <input class="button" name="Export" type="button" value="Export" />
            </div>
			<div class="buttonCenter">
	            <input name="Save" type="submit" disabled="disabled" class="button" value="Save" />
			</div>
            <div class="buttonRight">
	            <input class="button" name="Close" type="button" value="Close" />
            </div>
        </div>
        
    </div highestLevel>

<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
