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
                    Partially Saved Items
                </div left>
                <div class="right">
                </div right>
            </div searchResults>
	  		<div id="searchTable" class="searchTable">
				<%
                    String fileName = "http://localhost:8080/PRE/data/" + request.getParameter("searchFor") + "_search.csv";
                    if (application.getAttribute("searchText") != null)
                    {
                        application.removeAttribute("searchFor");
                    }
                    application.setAttribute("searchText", request.getParameter("searchFor"));
                    URL source = new URL(fileName);
                    BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                    
                    String s = "";
                %>
                <table align="center" width="90%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                    <tr align="center">
                    	<th>&nbsp;</th>
                    	<th align="center">Partial Save Action Category</th>
                        <th align="center">Comment</th>
                        <th align="center">Generic Name</th>
                        <th align="center">Strength</th>
                        <th align="center">Unit</th>
                        <th align="center">Dosage Form</th>
                        <th align="center">Date Modified</th>
                    </tr>
                    <tr align="left">
                    	<td><input type="checkbox"/></td>
	                    <td>New Product Request</td>
                   		<td>Initiated a new item request for Risperidone 3mg.</td>
                        <td align="center">Risperidone</td>
                        <td align="center">3</td>
                        <td align="center">mg</td>
                        <td align="center">Tablet</td>
                        <td align="center">08/10/2007</td>
                    </tr>
                    <tr align="left" bgcolor="#DeeeFF">
                    	<td><input type="checkbox"/></td>
	                    <td>Product Item Modification</td>
                   		<td>Edited the Product item.</td>
                        <td align="center">Amlodipine-Benazepril</td>
                        <td align="center">10-20</td>
                        <td align="center">mg</td>
                        <td align="center">Capsule</td>
                        <td align="center">08/15/2007</td>                        
                    </tr>                                                          
                </table>
            </div searchTable>
			<div align="center">

                <BR />
                <input name="EditItem" type="button" value="Edit Item">
				</div>
                
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
