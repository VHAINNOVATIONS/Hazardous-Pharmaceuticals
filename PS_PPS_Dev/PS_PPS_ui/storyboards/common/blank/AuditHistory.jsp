<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Product", "VUID", "Item Status", "Request Status", "Date/Time", "User", "NDC"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Item Audit History</title>

<link href="../css/Audit.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</head>

<body>
<!--div id="pageTitle" class="pageTitle">
	Item Audit History
</div--> 
<form action="" method="get">
	<div class="mainBody">
    	<div class="filters">
            <div class="date">
                Date Range:
                <select name="datePicker">
                	<option>Wednesday, July 29, 2007</option>
                 </select>
                 &nbsp;to&nbsp;
                 <select name="datePicker">
                	<option>Tuesday, August 28, 2007</option>
                 </select>
            </div>
            <div class="column1">
                Product Name:
                <br/>
                Strength:
                <br/>
                Dosage Form:
            </div col1>
<div class="textfield1">
                <input name="productItem" type="text" value="Risperidone" />
                <br/>
                <input name="strength" type="text" value="2" size="4" />
<select name="unitCombo">
				  <option selected="selected">mg</option>
				  <option>ml</option>
                
        </select>
                <br/>
                <select name="doseForm">
                  <option selected="selected">Tablet</option>
                  <option>Capsule</option>
                
              </select>
          </div col1>
			<div class="column">
                <input name="Active" type="checkbox" value="" />
                Active
                <br/>
                <input name="Inactive" type="checkbox" value="" />
                Inactive
	            <br/>
                <input name="LocalUse" type="checkbox" value="" />
                Local Use
                <br/>
                
                <input align="left" name="Search" type="button" value="Search" class="button"/>
            </div col4>
            <div class="column">
                <input name="NewItemRequest" type="checkbox" value="" />
                New Product Item Request
                <br/>
                <input name="ModRequest" type="checkbox" value="" />
                Product Item Modification Request
                <br/>
                <input name="ProductItem" type="checkbox" value="" />
                Product Item
                <br/>
                
            </div col2>
      <div class="lastColumn">
                <input name="Approved" type="checkbox" value="" />
                Approved
                <br/>
                <input name="Pending" type="checkbox" value="" />
                Pending
                <br/>
                <input name="Rejected" type="checkbox" value="" />
                Rejected
          </div col3>

        </div filters>
        <div class="resultsList">
        	<%
				String fileName = "http://localhost:8080/PRE/data/Audit.csv";
				URL source = new URL(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
				
				String s = "";
			%>
        	<table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
            	<tr>
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
						if (row%2==0) {
							rowColor="#DeeFFF";
						}
                    %>
                    <tr align="left" bgcolor=<%=rowColor%>>
                    	<% 
						String t="";
						int col=0;
						while (token.hasMoreTokens()) {
							col=col+1;
							t=token.nextToken();
							if (t.equals(" ")) {
								t = "&nbsp;";
							} 
							if (col==1) {
							%>
								<td><a href="ItemAuditHistoryDetail.jsp" target="_self"><%=t%></a></td>
                            <%
							} else if (col==7) {
									application.setAttribute("searchText", "risperidone");
							%>
								<td align="right"><a href="#" target="_parent" onclick="MM_openBrWindow('Audit_NDCs.jsp','','width=750,height=300')" ><%=t%></a></td>
							<%
							} else if ((col==2)) {
							%>
								<td align="right"><%=t%></td>
							<%
							} else if (col==5) {
							//String date = t.replace('/', ',');
							%>
								<td align="right"><%=t%></td>
							<%
							} else {
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
        </div resultsList>
        </div mainBOdy>
</form>
</body>
</html>
