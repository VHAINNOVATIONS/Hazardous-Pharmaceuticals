<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Lisinopril 80 mg TAB</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<script src="../javascript/DynamicComponents.js" type="text/javascript"></script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Product.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
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
              	<td valign="top"><div class="productId"><b>Product:</b> Refampin 300 mg CAP</div colLeft></td>
                <td><strong><div class="orderableItem"><strong>Orderable Item:</strong> <a href="#">Refampin CAP</a></div></td>
              </tr>
              <tr>
                <td width="572" height="22" valign="top"><strong>VUID</strong>: 4008596</td>
				<td height="22" valign="top"><label id="genName"><strong>Generic Name:</strong> Refampin</label></td>
              </tr>
              <tr>
                <td height="21" valign="top"><label id="gcn"><strong>GCN Seq Number:</strong> 021161</label></td>
                <td height="21" valign="top"><strong>Drug Class:</strong> Antitubercular-Rifamycin and Derivatives
                <p><a href="#">Previous</a>  &nbsp;<a href="#">Last</a></p></td>
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
                4 NDCs</li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <jsp:include page="ExternalDataFields_Refampin.jsp"/>
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
        <input name="Save" type="submit" class="buttonLeft" onclick="comment()" value="Bookmark" />
        <input name="Submit" type="button" class="buttonCenter" onclick="MM_popupMsg('This is a Duplicate Item')" value="Approve" />
        <input name="Next" type="button" class="adjbuttonCenter" onclick="MM_openBrWindow('RejectReason.html','RejectItem','width=520,height=200')"value="Reject" />
    </div buttonPanel>
</div mainBody>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
