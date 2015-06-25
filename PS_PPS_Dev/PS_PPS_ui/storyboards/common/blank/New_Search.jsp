<%@ include file="HeaderFrame.html"%>
<%@ include file="LeftFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PEPS Search</title>
<link href="../css/AddItemContent.css" rel="stylesheet" type="text/css" />
<script src="../javascript/CollapsiblePanel.js" type="text/javascript"></script>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/CollapsiblePanel.css" rel="stylesheet" type="text/css" />
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
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
</head>

<body>
<div id="pageTitle" class="pageTitle">
        Search
	</div>   
<div id="mainpage" class="pageBody">
     
    <form action="" method="get">
        <div class="newSearchcolumn1" id="productSearch">
        Product:
          <input type="text" name="searchProduct" id="searchProduct"/>
          </div>
          <div class="newSearchcolumn1" id="genericNameSearch">
          Generic Name:
          <input type="text" name="searchGenericName" id="searchGenericName"/>
          </div>
          <div class="newSearchcolumn1" id="allSearch">
          All:
          <input type="text" name="searchAll" id="searchAll"/>
          </div>
          <input type="submit" name="Search" id="Search" value="Search" />
    <br/>
        <br/>
        
        <div id="CollapsiblePanel2" class="CollapsiblePanel">
            <div class="CollapsiblePanelTab" tabindex="0">
                Search Results Filter
            </div>
            <div class="CollapsiblePanelContent">
                <div class="column1" id="filter1">
                    <label>
                        <input type="checkbox" name="StandardDrugs" id="StandardDrugs" />
                        Standard Drugs
                    </label>
                    <BR />
                    <label>
                        <input type="checkbox" name="Compounds" id="Compounds" />
                        Compounds Drugs
                    </label>
                    <BR />
                    <label>
                        <input type="checkbox" name="Investigationals" id="Investigationals" />
                        Investigationals
                    </label>
                    <BR />
                    <label>
                        <input type="checkbox" name="Supplies" id="Supplies" />
                        Supplies
                    </label>
                </div col1>
                
                
                <div class="column2" id="filter2">
                    <label>
                        <input type="checkbox" name="MFLU" id="MFLU" />
                        Marked for Local Use
                    </label>
                    <BR/>
                    <label>
                        <input type="checkbox" name="Active" id="Active" />
                        Active
                    </label>
                    <BR/>
                    <label>
                        <input type="checkbox" name="Formulary" id="Formulary" />
                        Formulary
                    </label>
                    <BR/>
                </div col2>
                    
                <div class="column3" id="filter3">
                    <label>
                        <input type="checkbox" name="ApprovedRequests" id="ApprovedRequests" />
                        Approved Requests
                    </label>
                    <BR/>
                    <label>
                        <input type="checkbox" name="PendingRequests" id="PendingRequests" />
                        Pending Requests
                    </label>
                    <BR/>
                    <label>
                        <input type="checkbox" name="RejectedRequests" id="RejectedRequests" />
                        Rejected Requests
                    </label>
                    <BR/>
                </div col3>
                <BR /> <BR />
          </div collapsiblePanelContent>
       
        </div - collapsbile panel>
      
        <div id="searchResults" class="searchTable">
            <%
                String fileName = "http://localhost:8080/PRE/data/" + request.getParameter("searchGenericName") + "_search.csv";
                if (application.getAttribute("searchText") != null)
				{
					application.removeAttribute("searchGenericName");
				}
				application.setAttribute("searchText", request.getParameter("searchGenericName"));
                URL source = new URL(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                
                String s = "";
            %>
            <table width="100%" border="1" cellpadding="3" cellspacing="0">
                <tr>
                    <th>Generic Name</th>
                    <th>Strength</th>
                    <th>Unit</th>
                    <th>Dose</th>
                    <th>Item Type</th>
                    <th>Local Use</th>
                    <th>Request Status</th>
                    <th>Formulary</th>
                    <th>State</th>
                    <th></th>
              </tr>
            <%
				br.readLine(); //first line of file has no data values
                String line = "";
                while ((line = br.readLine()) != null)
                {
                    StringTokenizer token = new StringTokenizer(line, ",");
            %>
                <tr>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><%=token.nextToken()%></td>
                    <td><input name="NDC" type="button" onClick="MM_openBrWindow('NDC.jsp','ViewNDC','status=yes,width=600,height=400')"	value="NDC" /></td>
              </tr>
            <%
                }
				br.close();
            %>
            </table>
            
            <div align="right">
                  <input name="EditProduct2" type="button" onClick="MM_goToURL('parent','ItemDetail.jsp');
                  	return document.MM_returnValue" value="Edit Product" align="right"/>
	              <p>
                      <label >Add New Products</label>
                      <input name="OpenDrugTemplate" type="button" value="Open Drug Template" />
                      <input name="OpenBlankTemplate" type="button" value="Open Blank Template" />
                  </p>
            </div>
        </div searchResults>
    </form>
</div>

<script type="text/javascript">
<!--
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2");
//-->
  </script>
</body>
</html>
