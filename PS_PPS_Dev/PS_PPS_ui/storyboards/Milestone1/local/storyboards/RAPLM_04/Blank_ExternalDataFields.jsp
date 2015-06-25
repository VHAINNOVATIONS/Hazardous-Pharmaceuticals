<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/ProductDetail.css" rel="stylesheet" type="text/css" /></head>

<body>
<div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="tableFont"><tr height="50"><td width="61%" rowspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="0" class="tableFont">
  <tr>
    <td height="30" width="10%">Product Status:</td>
    <td width="15%"><select name="cmbProductStatus">
      <option>Active</option>
      <option>Inactive</option>
    </select></td>
    <td width="12%">&nbsp;</td>
    <td width="61%">&nbsp;</td>
  </tr>
  <tr>
    <td height="30">Trade Name: </td>
    <td><input name="tradeName" type="text" id="tradeName"  class="text_leftalign" value=""/></td>
    <td align="right">Route:</td>
    <td><select name="cmbRoute">
      <option>Not Selected</option>
      <option>Oral</option>
    </select></td>
  </tr>
  <tr>
    <td height="30">Generic Name:</td>
    <td><input name="genericName" type="text" id="genericName" class="text_leftalign" value=""/></td>
    <td align="right">Dosage Form:</td>
    <td><select name="cmbDosageForm">
    <option selected="selected">Not Selected</option>
    <option>Tablet</option>
      <option>Tablet Rapid Dissolve</option>
    </select></td>
  </tr>
  <tr>
    <td height="30">Strength/Unit:</td>
    <td><input name="strength" type="text" id="strength" class="text_rightalign" size="2" value=""/>
      <select name="cmbUnit">
      <option>Not Selected</option>
      <option>mg</option>
      <option>ml</option>
    </select></td>
    <td align="right">Drug Class:</td>
    <td><select name="cmbDrugClass">
    <option selected="selected">Not Selected</option>
    <option>Anti-Psychotic</option>
      <option>ACE Inhibitors-Calcium Blockers Combinations</option>
    </select></td>
  </tr> 
</table>  
<label></label></td>
  </tr>
</table>
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
</div>
</body>
</html>

