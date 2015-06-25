<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Configure Notifications</title>

<link href="../css/NTFPLM.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="mainBody">
	<div id="highestLevel" class="heading">
        <div class="generalInfo">
            <table width="60%" border="0" cellspacing="1" cellpadding="0">
              <tr> 
              	<td valign="top" align="left" height="60"><div class="title">Configure Notifications</div></td>
                <td>&nbsp;</td>
              </tr>
              <tr height="30">
                <td width="30%" align="right" valign="top">New VA Specific Items<input name="chkNewVASpecificItems" type="checkbox" value="" /></td>
				<td width="20%" align="right" valign="top">Modified COTS Items<input name="chkModifiedCOTSItems" type="checkbox" value="" /></td>
                              
               </tr>
               
              <tr height="30">
                <td width="20%" align="right" valign="top">New COTS Items<input name="chkNewCOTSItems" type="checkbox" value="" /></td>
				<td width="50%" align="right" valign="top">Rejected VA Specific Items<input name="chkRejectedVASpecificItems" type="checkbox" value="" /></td>
              </tr>
              <tr height="30">
                <td width="20%" align="right" valign="top">Modified VA Specific Items<input name="chkModifiedVASpecificItems" type="checkbox" value="" /></td>
				<td width="50%" align="right" valign="top">Deleted COTS Items<input name="chkDeletedCOTSItems" type="checkbox" value="" /></td>
              </tr>
              <tr height="60">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </table>
      </div generalinfo>
    </div highestLevel>
        
    <div id="buttons" class="buttonPanel">
        <input class="buttonCenter" name="Ok" type="button" value="OK" />
        <input class="adjbuttonCenter" name="Cancel" type="button" value="Cancel" />
    </div buttonPanel>
</div mainBody>

</body>
</html>
