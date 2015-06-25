<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
    private String[] tab1TableColumns = {"Confirm", "Field Name", "Old Value", "New Value", "Editable at Local", "Reason"};
%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Modification Summary</title>

<link href="../css/RequestModificationDetail.css" rel="stylesheet" type="text/css" />
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function MM_confirmMsg(msg) { //v3.0
  window.confirm(msg);
}
function MM_openBrWindow(theURL,winName,features) { //v3.0
  window.open(theURL,winName,features);
}

function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}

function to_old_win(url) { //v3.0
    opener.location.href = url;
    window.close();
}
//-->
</script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
</head>

<body>
 <div id="pageTitle" class="pageTitle">
	Modification Summary for Product: Risperidone 2 mg TAB</div>
<form action="" method="get">
	<div class="mainBody">
    
        <div id="TabbedPanels1" class="TabbedPanels">
            <ul class="TabbedPanelsTabGroup">
              <li class="TabbedPanelsTab" tabindex="0">All Modifications</li>
          </ul>
        	<div class="TabbedPanelsContentGroup">
       		  	<div class="TabbedPanelsContent">
                    <div class="resultsList">
						<table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <tr bgcolor="#FFFFFF">
                            	<td align="center"><input name="" type="checkbox" value="" checked="checked"/></td>
                            	<td>Days Supply Limit</td>
                            	<td>30</td>
                            	<td>30</td>
                            	<td>No</td>
                            	<td width="20%"align="justify"><input name="reason" type="text" size="50" /></td>
                           	</tr>
                           	<tr bgcolor="#DEEFFF">
                            	<td align="center"><input name="" type="checkbox" value="" checked="checked"/></td>
                            	<td>Non-Renewable</td>
                            	<td>No</td>
                            	<td>Yes</td>
                            	<td>Yes</td>
                            	<td width="20%"align="justify"><input name="reason" type="text" size="50" /></td>
                           	</tr>
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/><br/>
              </div tab1>
                
            </div>
      </div>
  <div class="button">	
	        <input name="close" type="button" value="Confirm Changes" class="button" onClick="to_old_win('05_Confirmation.jsp')"/>
            <input name="close" type="button" value="Cancel" class="button"/>
        </div bottomRight>
    </div mainBody>
</form>

<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html> 