<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PEPS Search</title>
<link href="../css/RPI.css" rel="stylesheet" type="text/css" />
<script src="../javascript/CollapsiblePanel.js" type="text/javascript"></script>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/CollapsiblePanel.css" rel="stylesheet" type="text/css" />
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
</script>
</head>

<body>
<!--div id="pageTitle" class="pageTitle">
    Review Pending Items
</div-->
<div id="mainpage" class="pageBody">
    <form action="" method="get">
    <div class="tabHolder">
    <div id="TabbedPanels1" class="TabbedPanels">
      <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab" tabindex="0">Pending Requests</li>
        <li class="TabbedPanelsTab" tabindex="0">COTS Updates</li>
      </ul>
      <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent"><div id="pendingItems" class="pendingItemsTable">
            <div id="filter" class="pendingFilter">
           	<table cellpadding="5" cellspacing="3">
              <tr>
              	<td valign="top:"> View:<br/><br/> </td>
                <td>
                    <label>
                        <input type="checkbox" name="PendingFilter" value="radio" id="PendingFilter_0" />
                        Pending Addition
                    </label>
                    <br/>
                    <label>
                        <input type="checkbox" name="PendingFilter" value="radio" id="PendingFilter_1" />
                        Pending 2nd Approval Addition
                    </label>
                </td>
                <td>
                    <label>
                        <input type="checkbox" name="PendingFilter" value="radio" id="PendingFilter_2" />
                        Pending Modification
                    </label>
                    <br/>
                    <label>
                        <input type="checkbox" name="PendingFilter" value="radio" id="PendingFilter_2" />
                        Pending 2nd Approval Modification
                    </label>
                </td>
                <td>
                    <label>
                        <input type="checkbox" name="PendingFilter" value="radio" id="PendingFilter_2" />
                        All
                    </label>
                    <br/><br/>
                </td>
				<td>
	                <input name="Search" type="button" value="Search">
                </td>
              </tr>
            </table>
            
    	</div filter>
			<%
                String fileName = "http://localhost:8080/PRE/data/pendingItemsList.csv";
                URL source = new URL(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                
                String s = "";
            %>

          <table width="100%" border="1" cellpadding="3" cellspacing="0" RULES="COLS" FRAME="box">
                <tr>
                	<th>&nbsp; </th>
                    <th>VA Product Name</th>
                    <th>Generic Name</th>
                    <th>Str</th>
                    <th>Unit</th>
                    <th>Dosage Form</th>
                    <th>Level</th>
                    <th>Item Type</th>
                    <th>Request Type</th>
                    <th>Item Status</th>
                    <th>Request Status</th>
                    <th>Requested By</th>
              </tr>
            <%
				br.readLine(); //first line of file has no data values
                String line = "";
				int row=0;
                while ((line = br.readLine()) != null)
                {
					row=row+1;
					String rowColor = "#FFFFFF";
					if (row%2 == 0) {
						rowColor="#DeeeFF";
					}
                    StringTokenizer token = new StringTokenizer(line, ",");
            %>
                <tr bgcolor="<%=rowColor%>">
                    <td><input name="" type="checkbox" value=""></td>
               	      
                    <td><a href="National_Product.jsp"><%=token.nextToken()%></a></td>
                    <td><%=token.nextToken()%></td>
                    <td align="right"><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
              </tr>
            <%
                }
				br.close();
            %>
            </table>
            
            
        </div searchResults></div>
        <div class="TabbedPanelsContent">
        <div id="filter" class="pendingFilter">
           	<table cellpadding="5" cellspacing="3">
              <tr>
              	<td> View: </td>
                <td><label>
                  <input type="radio" name="PendingFilter" value="radio" id="PendingFilter_0" />
                  Pending Addition</label></td>
                <td><label>
                  <input type="radio" name="PendingFilter" value="radio" id="PendingFilter_2" />
                  Pending Modification</label></td>
                <td><label>
                  <input type="radio" name="PendingFilter" value="radio" id="PendingFilter_2" />
                  All</label></td>
				<td>
	                <input name="Search" type="button" value="Search">
                </td>
              </tr>
            </table>
            
    	</div filter>
        	<%
                fileName = "http://localhost:8080/PRE/data/cotsPendingItemsList.csv";
                source = new URL(fileName);
                br = new BufferedReader(new InputStreamReader(source.openStream()));
                
                s = "";
            %>
        	<table width="100%" border="1" cellpadding="3" cellspacing="0" RULES="COLS" FRAME="box">
                <tr>
                    <th>&nbsp; </th>
                    <th>Generic Name</th>
                    <th>Str</th>
                    <th>Unit</th>
                    <th>Dosage Form</th>
                    <th>Level</th>
                    <th>Item Type</th>
                    <th>Route</th>
                    <th>Drug Class</th>
                    <th>Update Type</th>
              </tr>
            <%
				br.readLine(); //first line of file has no data values
                line = "";
				row=0;
                while ((line = br.readLine()) != null)
                {
					row=row+1;
					String rowColor = "#FFFFFF";
					if (row%2 == 0) {
						rowColor="#DeeeFF";
					}
                    StringTokenizer token = new StringTokenizer(line, ",");
            %>
                <tr bgcolor="<%=rowColor%>">
                    <td><input name="" type="checkbox" value=""></td>
               	      
                    <td><a href="National_Product.jsp"><%=token.nextToken()%></a></td>
                    
                    <td align="right"><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
              </tr>
            <%
                }
				br.close();
            %>
            </table>
          </div>
      </div>
    </div tabbedPanels>
    </div tabHolder>
    <div class="leftBottomButtons">
    	<!--input name="" type="button" value="Reject Selected">
        <input name="" type="button" value="Approve Selected"-->
    </div>
	<div align="center">
        <input name="Edit" type="button" value="Review" class="printButton" onClick="MM_goToURL('parent','National_Product.jsp');
                  	return document.MM_returnValue"/>
	</div>
    </form>
</div>

<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
