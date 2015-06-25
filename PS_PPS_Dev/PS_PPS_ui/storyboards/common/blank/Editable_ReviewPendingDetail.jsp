<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Risperidone 2 mg TAB</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<script type="text/javascript">
function MM_popupMsg(msg) { //v3.0
  alert(msg);
}
function MM_confirmMsg(msg) { //v3.0
  window.confirm(msg);
}
function MM_openBrWindow(theURL,winName,features) { //v3.0
  window.open(theURL,winName,features);
}
</script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Product.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="mainBody">
	<div id="highestLevel" class="heading">
        <div class="productId">
            <b>Product:</b> Atomoxetine 80 MG TAB
        </div colLeft>
        <br/>
        <div class="generalInfo">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td width="572" height="21"><strong>VUID</strong>: 021154-3256</td>
                <td width="572"><strong>Orderable Item:</strong> <a href="#">Atomoxetine TAB</a></td>
              </tr>
              <tr>
                <td height="21"><label id="gcn"><strong>GCN Seq Number:</strong> 021154</label></td>
                <td><strong>Drug Class:</strong> Ace inhibitor</td>
              </tr>
              <tr>
                <td height="21"><label id="genName"><strong>Generic Name:</strong> Atomoxetine</label></td>
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
                <jsp:include page="Editable_ExternalDataFields.jsp"/>
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
        <!--input name="approve" type="submit" class="buttonCenter" onClick="MM_confirmMsg('Are you sure you want to approve this item?')" value="Approve" />
        <input name="approve" type="button" class="buttonCenter" onClick="MM_popupMsg('This item already exists in the system.')" value="Approve"-->
   	  	<input name="reject" type="button" class="rejectButton" onClick="MM_confirmMsg('Are you sure you want to reject this item ?')" value="Reject"-->
        <!--input name="reject" type="submit" class="rejectButton" value="Reject" onClick="MM_openBrWindow('RejectReason.html','RejectReason','width=490,height=270')" />
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
