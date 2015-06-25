<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/ProductDetail.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="tableFont"><tr height="50"><td width="61%" rowspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="0" class="tableFont">
  <tr>
    <td height="30" width="30%"><strong>Product Status:</strong> <select name="cmbProductStatus" id="cmbProductStatus">
      <option>Active</option>
      <option>Inactive</option>
    </select></td>
    <td width="9%"><strong>&nbsp;</strong></td>
    <td width="61%">&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>Trade Name:</strong> <input name="txtTradeName"  id="txtTradeName" type="text" value="RISPERIDONE" /></td>
    <td><strong>Route:</strong></td>
   <td> <select name="cmbRoute" id="cmbRoute">
      <option>Oral</option>
    </select></td>
  </tr>
  <tr>
    <td height="30"><strong>Generic Name:</strong> <input name="txtGenericname"  id="txtGenericName" type="text"  value="RISPERIDAL M-TAB"/></td>
    <td><strong>Dosage Form:</strong></td>
    <td><select name="cmbForm" id="cmbForm">
      <option>TABLET RAPID DISSOLVE</option>
      <option>TAB</option>
    </select></td>
  </tr>
  <tr>
    <td height="30"><strong>Strength/Unit:</strong> <input name="txtStrength" type="text"  id="txtStrength" value="2"  size="10"/><select name="cmbUnit" id="cmbUnit">
      <option>mg</option>
      <option>ml</option>
    </select></td>
    <td><strong>Drug Class:</strong></td>
    <td><select name="cmbDrugClass" id="cmbDrugClass">
      <option>AntiiiPsychotic-Atypical Dopamine-Serotonin Antag-Benzisoxaole Deriv</option>
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
