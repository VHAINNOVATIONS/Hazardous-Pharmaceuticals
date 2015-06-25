<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"Product Name", "Strength", "Status", "Local Use", "FORM INDC", "CMOP ID", "Request Status", "Generic Local Name"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/Content.css" rel="stylesheet" type="text/css" />
<link href="../css/ItemDetails.css" rel="stylesheet" type="text/css" />
<title>Untitled Document</title>
</head>

<body>
	<div id="ndcList" class="ndcList">
		<div class="searchResults">
            <div class="left">
				<b>RISPERDAL 2 MG TAB</b>
            </div left>
            <div class="right">
                Page Count: 
                <select name="pgCount">
                    <option>1 of 1</option>
                </select>
                Page Lines:
                <select name="pgCount">
                    <option>20</option>
                </select>
	        </div right>
        </div searchResults>
        <div id="table" class="searchTable">
        	<%
                    String fileName = "http://localhost:8080/VeHU/data/products/risperidone.csv";
                    URL source = new URL(fileName);
                    BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                    
                    String s = "";
                %>
                <table width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                    <tr>
                    	<th>&nbsp;</th>
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
							rowColor="#DfFFFF";
						}
                    %>
                    <tr bgcolor=<%=rowColor%>>
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
							if (col==1) {
							%>
								<td><a href="ItemDetail.jsp" target="_self"><%=t%></a></td>
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
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                    <TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                	<TR><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></TR>
                </table>
        	</div searchTable>
            <div class="ndcBottom">
                <div class="blankTempButton">
                    <label>
                        <input name="SelectAll" type="checkbox" value="SelectAll">
                        Select All
                    </label>
                    <BR />
                    <input name="OpenMultipleProducts" type="button" value="Open Multiple Products">
                    </div>
                <div class="openTempButton">
                    Add New Item:
                    <br/>
                    <input class="templateButon" name="Open Blank Template" type="button" value="Open Blank Template">
                    <input class="templateButon" name="Open as Template" type="button" value="Open as Template">
                </div>
            </div searchBottom>
        </fieldset>
    </div ndcList>
</body>
</html>
