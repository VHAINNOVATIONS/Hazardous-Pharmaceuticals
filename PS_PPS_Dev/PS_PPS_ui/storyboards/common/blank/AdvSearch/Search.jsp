<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Generic Name", "STR", "Unit", "Dose", "VA Drug Class", "Local Use", "Request Status", "Item State", "Formulary", "Orderable Item", "NDCs"};
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
                <a href="#">Save Search</a>
            </div col1>
            <div class="column" id="searchFor">
              For: <BR/>
                <input type="text" name="searchFor" id="searchFor"/>
                <BR/><BR/>
                <a href="#">Retrieve Search</a>
            </div col2>
            <div class="column" id="strength">
                Strength: <BR/>
              <input type="text" size="10" name="strength" id="strength"/>
                <BR/><BR/>
                <a href="#">Print Template</a>
            </div col3>
            <div class="column" id="form">
                Form: <BR/>
              <input type="text" size="10" name="form" id="form"/>
                <br/>
            </div col4>
            <div class="column" id="submit">
                <br/><BR/>
                <input type="submit" name="Search" id="Search" value="Search" />
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
                    <input type="radio" name="RadioGroup1" value="radio" id="RadioGroup1_0">
                    Marked for Local Use</label>
                  <br>
                  <label>
                    <input name="RadioGroup1" type="radio" id="RadioGroup1_1" checked >
                    All Items</label>
                  <br>
                
          	</div col6>
   	  	</div searchCritiera>
        
        <div id="CollapsiblePanel1" class="CollapsiblePanel">
          <div class="CollapsiblePanelTab" tabindex="0">
          	Advanced Search
          </div>
          <div class="CollapsiblePanelContent">
		  <div class="advCriteria" id="ac1">
              <label>Select field:<select onchange="nextComponent(document.getElementById('ac1').value, 'ac1')">
                	<option>enter field</option>
    		    	<option>None</option>
                    <option>All Fields</option>
  		        	<option>Generic Name</option>
                    <option>Manufacturer</option>
                    <option>Drug Class</option>
                    <option>Local Use</option>
                    <option>Dosage Form</option>
        		    </select></label>&nbsp;
                    <select><option>Tablet</option>
                    <option>Capsule</option>
                    </select>                    
                </div>    
		  <div class="advCriteria" id="ac2">
              <label>Select field:<select onchange="nextComponent(document.getElementById('ac2').value, 'ac2')">
                	<option>enter field</option>
    		    	<option>None</option>
                    <option>All Fields</option>
  		        	<option>Generic Name</option>
                    <option>Manufacturer</option>
                    <option>Drug Class</option>
                    <option>Do Not Mail</option>
                    <option>Dosage Form</option>
        		    </select></label>&nbsp;
                </div>     
		  <div class="advCriteria" id="ac3">
              <label>Select field:<select onchange="nextComponent(document.getElementById('ac2').value, 'ac2')">
                	<option>enter field</option>
    		    	<option>None</option>
                    <option>All Fields</option>
  		        	<option>Generic Name</option>
                    <option>Manufacturer</option>
                    <option>Drug Class</option>
                    <option>Do Not Mail</option>
                    <option>Dosage Form</option>
        		    </select></label>&nbsp;
                </div>                                                                            
          </div>	
          </div>
<fieldset style="border-color:#000000" class="searchFieldset">
            <div id="searchResults" class="searchResults">
                <div class="left">
                    Search Results
                </div left>
                <div class="right">
                    Page Count: 
                    <select name="pgCount">
                        <option>1 of 5</option>
                    </select>
                    Page Lines:
                    <select name="pgCount">
                        <option>20</option>
                    </select>
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
                <table width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                    <tr align="center">
                    	<th align="center">&nbsp;</th>
                        <% 
                        for (int i=0; i<tableColumns.length; i++) {
                            String col = tableColumns[i];
                            %>
                                <th><%=col%></th>
                      <%
                        }
                        %>
                    </tr>
                    <%
                    br.readLine(); //first line of file has no data values
                    String line = "";
					int row=0;
                    while ((line = br.readLine()) != null)
                    {
						row=row+1;
                        StringTokenizer token = new StringTokenizer(line, ",");
						String rowColor = "#FFFFFF";
						if (row%2 == 0) {
							rowColor="#DeeeFF";
						}
                    %>
                    <tr align="left" bgcolor=<%=rowColor%>>
	                    <td><input name="" type="checkbox" value="" /></td>
                    	<% 
						String t="";
						int col=0;
						while (token.hasMoreTokens()) {
							col=col+1;
							t=token.nextToken();
							if (t.equals(" ")) {
								t = "&nbsp;";
							} 
							if (col==1 || col==10) {
							%>
								<td><a href="ItemDetail.jsp" target="_self"><%=t%></a></td>
                            <%
							} else if (col==11) {
							%>
                            	<td align="right"><a href="ItemDetail.jsp" target="_self"><%=t%></a></td>
                            <%
							} else if (col==2) {
							%>
                            	<td align="right"><%=t%></td>
                            <%
							}else {
                            %>
                            <td><%=t%></td>
					  <%
							}
						}
						%>
                  </tr>
                <%
                    }
                    br.close();
                %>
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
                <input name="Open Blank Template" type="button" value="Open Blank Template">
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
