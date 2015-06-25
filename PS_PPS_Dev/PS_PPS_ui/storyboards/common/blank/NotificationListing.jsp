<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Select", "Type", "User", "Date", "Read"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Item Audit History</title>

<link href="../css/Notification.css" rel="stylesheet" type="text/css" />
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
        Select Filter: 
        </div>    
     <div class="column1">
                <input name="New" type="checkbox" value="" />
                New
                <br/>
                <input name="Modified" type="checkbox" value="" />
                Modified
                <br/>
                <input name="Rejected" type="checkbox" value="" />
                Rejected
                </div>

        </div filters>
        <div class="resultsList">
        	<%
				String fileName = "http://localhost:8080/PRE/data/Notification.csv";
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
							if(col==3) { 
							%>
								<td align="right"><%=t%></td>
							<%
						}
						else {
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
        <div id="buttons" class="buttonPanel">
        
        <input name="Search" type="button" class="buttonCenter" value="Search" />
        <input name="Delete" type="button" class="adjbuttonCenter" value="Delete" />
        <input name="Read" type="button" class="adjbuttonCenter" value="Read" />
        <input name="Cancel" type="button" class="adjbuttonCenter" value="Cancel" />
        
    </div buttonPanel>
        </div mainBOdy>
</form>
</body>
</html>