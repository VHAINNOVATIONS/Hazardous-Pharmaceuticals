<%@ include file="../HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Generic Name", "STR", "Unit", "Dose", "VA Drug Class", "Local Use", "Request Status", "Item State", "Formulary", "NDCs"};
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PEPS Search</title>
<link href="../css/Content.css" rel="stylesheet" type="text/css" />
<script src="../javascript/CollapsiblePanel.js" type="text/javascript"></script>
<script src="../javascript/DynamicComponents.js" type="text/javascript"></script>
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
    	<div id="searchCriteria" class="searchCriteria">
    	  	<div class="column" id="searchBy">
                Search Fields: <BR/>
                <select name="By">
                    <option selected>All</option>
                    <% 
                    for (int i=0; i<tableColumns.length; i++) {
                        String col = tableColumns[i];
                        %>
                            <option><%=col%></option>
                        <%
                    }
                    %>	
                </select>
                <BR/><BR/>
            </div col1>
            <div class="column" id="searchFor">
              For: <BR/>
                <input type="text" name="searchFor" id="searchFor"/>
                <BR/><BR/>
            </div col2>
            <div class="column" id="strength">
                Strength: <BR/>
              <input type="text" size="10" name="strength" id="strength"/>
                <BR/><BR/>                
            </div col3>
            <div class="column" id="form">
                Form: <BR/>
              <input type="text" size="10" name="form" id="form"/>
                <br/>
            </div col4>
            <div class="column" id="submit">
                <br/><BR/>
                <input name="Search" type="submit" id="Search" onClick="MM_goToURL('parent','Search_Results.jsp');return document.MM_returnValue" value="Search"/>
            </div col5>
<div class="columnRight" id="requestState">
             	Request Status:
                <br/>
                <label>
                    <input name="Approved" type="checkbox" value="Approved" checked>
                    Approved
                </label>
                <br/>
                <label>
                    <input name="Pending" type="checkbox" value="Pending">
                    Pending
                </label>
              	<br/>
                <label>
                    <input name="Rejected" type="checkbox" value="Rejected">
                    Rejected
                </label>
        	</div col8>
			<div class="columnRight" id="status">
             	Item Status:
                <br/>
                <label>
                    <input name="Active" type="checkbox" value="Active" checked>
                    Active
                </label>
                <br/>
                <label>
                    <input name="Inactive" type="checkbox" value="Inactive">
                    Inactive
                </label>
            </div col7>
			<div class="columnRight" id="display">
                Display Only:
                <br/>
                
                  <label>
                    <input type="radio" name="RadioGroup1" value="radio" id="RadioGroup1_0">                    Local Use</label>
                  <br>
                  <label>
                    <input name="RadioGroup1" type="radio" id="RadioGroup1_1" checked>
                    All Items</label>
                  <br>
                
          	</div col6>
   	  	</div searchCritiera>
        
        <div id="CollapsiblePanel1" class="CollapsiblePanel">
          <div class="CollapsiblePanelTab" tabindex="0">
          	&nbsp;&nbsp;Advanced Search
          </div>
          <div class="CollapsiblePanelContent">
          <div align="left" id="advSearchLeftTop"><div align="center"><font size="3">&nbsp;</font></div>
               &nbsp;<font size="2">Set as Default Criteria</font>&nbsp;<input type="checkbox" id="defaultCheckbox" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">Save Search</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">Retrieve Search</a>               
          </div>
<div class="columnRight" id="requestState">
             	Request Status:
                <br/>
                <label>
                    <input name="Approved" type="checkbox" value="Approved" checked>
                    Approved
                </label>
                <br/>
                <label>
                    <input name="Pending" type="checkbox" value="Pending">
                    Pending
                </label>
              	<br/>
                <label>
                    <input name="Rejected" type="checkbox" value="Rejected">
                    Rejected
                </label>
        	</div col8>
			<div class="columnRight" id="status">
             	Item Status:
                <br/>
                <label>
                    <input name="Active" type="checkbox" value="Active" checked>
                    Active
                </label>
                <br/>
                <label>
                    <input name="Inactive" type="checkbox" value="Inactive">
                    Inactive
                </label>
            </div col7>
			<div class="columnRight" id="display">
                Display Only:
                <br/>
                
                  <label>
                    <input type="radio" name="RadioGroup1" value="radio" id="RadioGroup1_0">                    Local Use</label>
                  <br>
                  <label>
                    <input name="RadioGroup1" type="radio" id="RadioGroup1_1" checked>
                    All Items</label>
                  <br>
                
          	</div col6>                                               
		  <div class="advCriteria" id="ac1">
              &nbsp;<label>Select field:<select onChange="nextComponent()">
                	<option>enter field</option>
    		    	<option selected="selected">None</option>
                    <option>All Fields</option>
  		        	<option>Generic Name</option>
                    <option>Manufacturer</option>
                    <option>Drug Class</option>
                    <option>Local Use</option>
                    <option>Dosage Form</option>
                    <option>Do Not Mail</option>
        		    </select></label>           
                </div>                                                    
<!--                <center><input type="button" value="Save Search"  />&nbsp;<input type="button" value="Retrieve Search" /></center>
//-->
				<br />                                
                <br />
                <center><input type="button" value="Search" onClick="note()"/></center>
                <br />
          </div>
        </div>
<fieldset style="border-color:#000000" class="searchFieldset">
            <div id="searchResults" class="searchResults">
                <div class="left">
                    Search Results &nbsp;<a href="#"><font size="-1">Manage Print Template</font></a>
                </div left>
                <div class="right">
                    Page Count: 
                    <select name="pgCount">
                        <option>-</option>
                    </select>
                    Page Lines:
                    <select name="pgCount">
                        <option>-</option>
                    </select>
                </div right>
            </div searchResults>
	  		<div id="searchTable" class="searchTable">
                <table width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                                       <tr align="center">
                    	<th align="center">&nbsp;</th>
                        <th>Generic Name</th>
                        <th>STR</th>
                        <th>Unit</th>
                        <th>Dose</th>
                        <th>VA Drug Class</th>
                        <th>Local Use</th>
                        <th>Request Status</th>
                        <th>Item Status</th>
                        <th>Formulary</th>
                        <th>NDCs</th>                               
                    </tr>
                </table>
            </div searchTable>
			<div class="bottomLeft">
                <label>
                    <input name="SelectAll" type="checkbox" value="SelectAll">
                    Select All
                </label>
                <BR />
                <input name="OpenMultipleProducts" type="button" value="Open Multiple Products">
				</div>
                <div align="right" >
                	<input type="button" value="Edit Product" align="right"/>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>                
        </fieldset>
        <div class="searchBottom">
            <div class="bottomLeft">
            Search Results:
            <br/>
            <input name="Print" type="button" value="Print" >
            <input name="Export" type="button" value="Export">
            </div>
            <div class="bottomRight">
            	Add New Orderable Item:
                <br/>
                <input name="Open Blank Product Template" type="button" value="Open Product Blank Template">
            </div>
        </div searchBottom>
    </form>
</div pageBody>
<script type="text/javascript">
<!--
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1");
//-->
</script>
</body>
</html>