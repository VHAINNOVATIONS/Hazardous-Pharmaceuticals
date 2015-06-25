<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Location", "First DataBank", "PEPS", "VistA", "Date Received"};
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PEPS Search</title>
<link href="../css/Content.css" rel="stylesheet" type="text/css" />
<script src="../javascript/CollapsiblePanel.js" type="text/javascript"></script>
<script src="../javascript/SpryCollapsiblePanel.js" type="text/javascript"></script>
<link href="../css/CollapsiblePanel.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
//-->
</script>
<link href="../javascript/SpryCollapsiblePanel.css" rel="stylesheet" type="text/css">
</head>

<body>
<div id="mainpage" class="pageBody">
	<form action="" method="post">

<fieldset style="border-color:#000000" class="searchFieldset">
            <div id="searchResults" class="searchResults">
                <div class="left">
                    Search Templates
                </div left>
                <div class="right">
                </div right>
            </div searchResults>
	  		<div id="searchTable" class="searchTable">

                <table align="left" width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                    <tr align="center">
                    	<th width="8%">Selection</th>
                    	<th width="30%" align="left">&nbsp;&nbsp;Template</th>
                        <th>Level
                        <th>Notes</th>
                    </tr>
                    <tr align="left" bgcolor="#DeeeFF">
                    	<td align="center"><input type="checkbox"/></td>
	                    <td>&nbsp;&nbsp;<a href="#"><b>Inhibitor Drug Class</b></a>
                    <td align="center">user</td>
                    <td>Drug classification - Inhibitors</td>
                    </tr>
                    <tr align="left">
                    	<td align="center"><input type="checkbox"/></td>
	                    <td>&nbsp;&nbsp;<a href="#">sample</a></td>
                        <td align="center">system</td>
                        <td>this is a sample search template</td>
                    </tr>
                    <tr align="left" bgcolor="#DeeeFF">
                    	<td align="center"><input type="checkbox"/></td>
	                    <td>&nbsp;&nbsp;<a href="#">IVAX_Manufacturer</a>
                    <td align="center">user</td>
                    <td>products by IVAX</td>
                    </tr>                                                          
                </table><br /><br /><br /><br /><br />
                <div align="center"><input type="button" value="Delete Selections" /></div>
            </div searchTable><br />

                
        </fieldset>

    </form>
</div pageBody>
<script type="text/javascript">
<!--
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1");
//-->
</script>
</body>
</html>
