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
function MM_confirmMsg(msg) { //v3.0
  window.confirm(msg);
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
        </table>
        <br/><br/><br/>
        <input name="approve" type="button" class="buttonRight" onClick="MM_confirmMsg('You must save your changes to the product before creating new NDCs for the product. Click OK to save the product now or Cancel to return to the product details.')" value="Open Blank NDC Template">
        <!--input type="button" class="buttonRight" onclick="MM_goToURL('parent','AddNew_NDC_WithoutTemplate.jsp');return document.MM_returnValue" value="Open Blank NDC Template" /-->
    </div searchTable>
</body>

</html>
