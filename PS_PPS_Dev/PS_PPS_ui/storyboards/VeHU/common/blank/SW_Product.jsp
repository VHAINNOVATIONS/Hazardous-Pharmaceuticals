<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Risperidone 2 mg TAB</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Product.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
.style1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	color: #FFFFFF;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function MM_popupMsg(msg) { //v3.0
  alert(msg);
}
function MM_confirmMsg(msg) { //v3.0
  window.confirm(msg);
}

function MM_openBrWindow(theURL,winName,features) { //v3.0
  window.open(theURL,winName,features);
}

function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
//-->
</script>
</head>

<body>
<div class="mainBody">
	<div id="highestLevel" class="heading">
        <div class="productId">
            <b>Product:</b> LOTREL 10-20 MG CAP
        </div colLeft>
        <br/>
        <div class="generalInfo">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td width="572" height="21"><strong>VUID</strong>: 021154-3256</td>
                <td width="572"><strong>Orderable Item:</strong> <a href="#">Lotrel CAP</a></td>
              </tr>
              <tr>
                <td height="21"><label id="gcn"><strong>GCN Seq Number:</strong> 021154</label></td>
                <td><strong>Drug Class:</strong> ACE Inhibitors-Calcium Blockers Combinations</td>
              </tr>
              <tr>
                <td height="21"><label id="genName"><strong>Generic Name:</strong> AMLODIPINE-BENAZEPRIL</label></td>
                <td>&nbsp;</td>
              </tr>
            </table>
      </div>
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
                <jsp:include page="SW_ExternalDataFields.jsp"/>
            </div tab1>
            <div class="TabbedPanelsContent">
                <jsp:include page="Editable_VADataFields.jsp"/>
            </div tab2>
            
            <div class="TabbedPanelsContent">
                <jsp:include page="NDCs.jsp"/>
            </div tab3>
        </div tabbedPanelsContentGroup>
    </div tabbedPanels>
        
    <div id="buttons" class="buttonPanel">
        <input class="buttonLeft" name="Print" type="button" value="Print" />
        <input class="buttonLeft" name="Export" type="button" value="Export" />
        <input name="Save" type="submit" class="buttonCenter" onClick="MM_openBrWindow('SaveReason.html','Save Comment','width=490,height=200')"value="Save" />
        <input name="SaveAndSubmit" type="submit" class="adjbuttonCenter" value="Save and Submit" />
        <input class="buttonRight" name="Close" type="button" value="Close" />
    </div buttonPanel>
</div mainBody>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
