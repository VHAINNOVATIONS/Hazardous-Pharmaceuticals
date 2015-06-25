<%@ include file="HeaderFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PEPS Search</title>
<link href="../css/LVPPLM.css" rel="stylesheet" type="text/css" />

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
        
            <div id="filter" class="pendingFilter">
           	<table cellpadding="5" cellspacing="3" width="75%">
              <tr>
              	
                <td> <div class="date">
                View:
                <select name="datePicker">
                	<option>Tuesday, November 20, 2007</option>
                 </select>
                 &nbsp;to&nbsp;
                 <select name="datePicker">
                	<option>Monday, December 03, 2007</option>
                 </select>
            </div></td>
                  <td  width="20%" align="left"><input name="Search" type="button" value="Search"></td>
              </tr>
            </table>
    	</div filter>
	
    </div tabHolder>
    <div class="leftBottomButtons">
    	<!--input name="" type="button" value="Reject Selected">
        <input name="" type="button" value="Approve Selected"-->
    </div>
    </form>
</div>

</body>
</html>
