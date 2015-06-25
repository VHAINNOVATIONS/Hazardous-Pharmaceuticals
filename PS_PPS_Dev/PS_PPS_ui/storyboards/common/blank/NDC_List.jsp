<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"NDC", "Trade Name", "Dosage Form", "Pack Size", "Pack Type", "Unit Price", "Manufacturer", "Image"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/ndcList.css" rel="stylesheet" type="text/css" />
<title>Untitled Document</title>
<script type="text/javascript">
<!--
function to_old_win(url) { //v3.0
  	opener.location.href = url;
	window.close();
}
//-->
</script>
</head>

<body>
	<div id="highestLevel" class="heading">
        <div class="generalInfo">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
              <tr> 
              	<td valign="bottom"><div class="productId"><b>Product:</b> Risperidone 2 mg TAB</div colLeft></td>
                <td valign="bottom"><strong>Orderable Item:</strong> <a href="#">Risperidone TAB</a></td>
              </tr>
              <tr>
                <td width="572" valign="bottom" height="21"><strong>VUID</strong>: 021154-3256</td>
				<td valign="bottom" height="21"><label id="genName"><strong>Generic Name:</strong> Risperidone</label></td>
              </tr>
              <tr>
                <td valign="bottom" height="21"><label id="gcn"><strong>GCN Seq Number:</strong> 021154</label></td>
                <td valign="bottom"><strong>Drug Class:</strong> Anti-Psychotic</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table>
        </div generalInfo>
    </div highestLevel>
	
    <jsp:include page="ndcTable.jsp"/>

</body>
</html>
