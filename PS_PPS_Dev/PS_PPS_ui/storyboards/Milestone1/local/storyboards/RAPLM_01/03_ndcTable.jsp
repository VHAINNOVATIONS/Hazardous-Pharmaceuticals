<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"NDC", "Trade Name", "Dosage Form", "Package Size", "Package Type", "Unit Price", "Manufacturer", "Image"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/ndcList.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
//-->
</script>
</head>

<body>
    <div id="table" class="searchTable">
        <table width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
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
            <tr bgcolor="#FFFFFF";>
            	<td align="right"><a href="#" target="_parent">50458-0320-01</a></td>
            	<td align="left">Risperdal</td>
            	<td align="left">TABLET</td>
            	<td align="right">100</td>
            	<td align="left">Unit Dose</td>
            	<td align="right">$3.0217</td>
            	<td align="left">JOM PHARMACEUTICAL</td>
                <td align="center"><img src="../images/pill_sm.jpg" /></td>
            </tr>
            <tr bgcolor="#DEEFFF";>
            	<td align="right"><a href="#" target="_parent">50458-0320-06</a></td>
            	<td align="left">Risperdal</td>
            	<td align="left">TABLET</td>
            	<td align="right">60</td>
            	<td align="left">Bottle</td>
            	<td align="right">$3.0217</td>
            	<td align="left">JOM PHARMACEUTICAL</td>
                <td align="center"><img src="../images/pill_sm.jpg" /></td>
            </tr>
            <tr bgcolor="#FFFFFF";>
            	<td align="right"><a href="#" target="_parent">50458-0320-50</a></td>
            	<td align="left">Risperdal</td>
            	<td align="left">TABLET</td>
            	<td align="right">500</td>
            	<td align="left">Bottle</td>
            	<td align="right">$3.0217</td>
            	<td align="left">JOM PHARMACEUTICAL</td>
                <td align="center"><img src="../images/pill_sm.jpg" /></td>
            </tr>
            <tr bgcolor="#DEEFFF";>
            	<td align="right"><a href="04_NDC.jsp" target="_parent">50458-0320-28</a></td>
            	<td align="left">Risperdal</td>
            	<td align="left">TABLET</td>
            	<td align="right">28</td>
            	<td align="left">Package</td>
            	<td align="right">$3.0217</td>
            	<td align="left">JOM PHARMACEUTICAL</td>
                <td align="center"><img src="../images/pill_sm.jpg" /></td>
            </tr>
        </table>
        <br/>
        <input type="button" class="buttonRight" value="Open Blank NDC Template" />
    </div searchTable>
</body>

</html>
