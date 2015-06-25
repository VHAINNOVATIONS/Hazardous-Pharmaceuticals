<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
    private String[] tab1TableColumns = {"Confirm", "Field Name", "Old Value", "New Value", "Reason"};
	private String[] tab2TableColumns = {"Confirm", "Field Name", "Old Value", "New Value", "Request to Make<br/>Editable", "Reason"};
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
	Modification Summary for Product:  Risperidone 2 mg TAB <span class="inputColumn">50458-0320-0</span>1</div>
<form action="" method="get">
	<div class="mainBody">
    
        <div id="TabbedPanels1" class="TabbedPanels">
            <ul class="TabbedPanelsTabGroup">
                <li class="TabbedPanelsTab" tabindex="0">Editable VA Data Modifications</li>
              <li class="TabbedPanelsTab" tabindex="1">Non-Editable VA Data Modifications</li>
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
       
                                <tr bgcolor="#FFFFFF";>
                                <td align="center"><input name="" type="checkbox" value="" checked="checked"/></td>
                                <td align="left">Dispense Quantity Limit</td>
                                <td align="left">2 TAB per 14 days</td>
                                <td align="left">3 TAB per 14 days</td>
                                    <td width="20%" align="justify"><input name="input" type="text" size="50" /></td>
                              </tr>
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/>
                </div tab1>
                <div class="TabbedPanelsContent">
                    <div class="resultsList">
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab2TableColumns.length; i++) {
                                    String col = tab2TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                                <tr bgcolor="#FFFFFF";>
                                <td align="center"><input name="" type="checkbox" value=""/></td>
                                <td align="left"></td>
                                <td align="left"></td>
                                <td align="left"></td>
                                <td align="center"><input name="makeEditable" type="checkbox" value="" /></td>
                                <td width="20%" align="justify"><input name="input" type="text" size="50" value="" /></td>         	
                              </tr>
                            
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/><br/>
                </div>
            </div>
        </div>
        <div class="button">	
		  <input name="" type="button" value="Confirm Changes" onClick="to_old_win('04_Confirmation.jsp')"/>
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