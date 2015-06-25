	<%@ include file="../HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Risperidone 2 mg TAB</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
//-->
</script>
</head>

<body>
<div class="mainBody">
	<div id="highestLevel" class="heading">
        <div class="generalInfo">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr> 
              	<td valign="top"><div class="productId"><b>Product:</b> </div colLeft></td>
                <td><strong><div class="orderableItem"><strong>Orderable Item:</strong> <a href="#">Choose Orderable Item</a></div></td>
              </tr>
              <tr>
                <td width="572" height="21" valign="top"><strong>VUID:</strong> </td>
				<td height="21" valign="top"><label id="genName"><strong>Generic Name:</strong> </label></td>
              </tr>
              <tr>
                <td height="21" valign="top"><label id="gcn"><strong>GCN Seq Number: </strong></label></td>
                <td height="21" valign="top"><strong>Drug Class: </strong></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </table>
      </div generalinfo>
    </div highestLevel>

    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab" tabindex="0">
                External Data Fields            </li>
            <li class="TabbedPanelsTab" tabindex="1">
                VA Data Fields</li>
            <li class="TabbedPanelsTab" tabindex="2">
                0 NDCs</li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <jsp:include page="03_Editable_ExternalDataFields.jsp"/>
            </div tab1>
            <div class="TabbedPanelsContent">
                <jsp:include page="National_VADataFields.jsp"/>
            </div tab2>
            
            <div class="TabbedPanelsContent">
                <jsp:include page="NDCs.jsp"/>
            </div tab3>
        </div tabbedPanelsContentGroup>
    </div tabbedPanels>
        
    <div id="buttons" class="buttonPanel">
        <input class="buttonLeft" name="Print" type="button" value="Print" />
        <input class="buttonLeft" name="Export" type="button" value="Export" />
        <input name="Save" type="submit" class="buttonLeft" value="Bookmark" />
        <input name="SaveAndSubmit" type="submit" class="buttonCenter" value="Submit" onClick="MM_goToURL('parent','04_Confirmation.jsp');return document.MM_returnValue"/>
    </div buttonPanel>
</div mainBody>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
