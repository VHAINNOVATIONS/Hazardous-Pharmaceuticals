

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
    <td height="22" width="30%"><strong>Name:</strong> RISPERIDONE</td>
    <td width="9%"><strong>Ingredients:</strong></td>
    <td width="61%" rowspan="2" valign="middle"><label>
      <textarea name="textfield" cols="40" rows="1.5" id="textfield">Ingredient 1, Ingredient 2, Ingredient 3</textarea>
    </label></td>
  </tr>
  <tr>
    <td height="22"><strong>Strength:</strong> 2 MG</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="22"><strong>Form:</strong> TABLET</td>
    <td><strong>Route:</strong></td>
    <td><label>
      <select name="select" id="select">
        <option selected="selected">Oral</option>
        <option>Longer Value</option>
      </select>
    </label></td>
  </tr>
  <tr>
    <td height="22"><strong>Product Type:</strong> Medication</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="22"><strong>Product Status:</strong> Active</td>
    <td colspan="2"><strong>VA Print Name:</strong> Risperidone 2 MG TABLET</td>
  </tr>
  <tr>
    <td height="22"><strong>Formulary Indicator:</strong> Formulary</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="22"><strong>Formulary Alternative: </strong>N/A</td>
    <td><strong>Synonym:</strong></td>
    <td rowspan="2"><label>
      <textarea name="textfield2" cols="40" rows="1.5" id="textfield2">Risperidal</textarea>
    </label></td>
  </tr>
  <tr>
    <td height="22"><strong>Approved for CMOP:</strong> Approved</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="22"><strong>CMOP ID:</strong> R0023</td>
    <td colspan="2"><strong>Approved for Splitting:</strong> No</td>
  </tr>
</table>  <label></label></td>
  </tr>
</table>
<fieldset class="fieldset"><legend class="borderTitle">Application Package Use</legend>
<table width="100%" border="0" cellspacing="1" cellpadding="0"  class="tableFont">
  <tr>
    <td width="18%" height="20"><label>
      <input type="checkbox" name="checkbox" id="checkbox" />
    Outpatient</label></td>
    <td width="22%"><label>
      <input type="checkbox" name="checkbox3" id="checkbox3" />
    Unit Dose</label></td>
    <td width="59%"><label>
      <input type="checkbox" name="checkbox5" id="checkbox5" />
    Non VA Med</label></td>
  </tr>
  <tr>
    <td height="20"><label>
      <input type="checkbox" name="checkbox2" id="checkbox2" />
    IV</label></td>
    <td><label>
      <input type="checkbox" name="checkbox4" id="checkbox4" />
    Controlled Substances</label></td>
    <td>&nbsp;</td>
  </tr>
</table>
</fieldset>
<fieldset class="fieldset"><legend class="borderTitle">Local VA Data</legend>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="tableFont">
  <tr>
    <td width="22%"><label>
      <input type="checkbox" name="checkbox6" id="checkbox6" />
    Refrigeration</label></td>
    <td width="28%"><label>
       <input type="checkbox" name="checkbox10" id="checkbox10" />
    Quantity Dispense Message</label></td>
    <td width="49%"><label>
      <input type="checkbox" name="checkbox11" id="checkbox11" />
    Warning Label</label></td>
  </tr>
  <tr>
    <td><label>
      <input type="checkbox" name="checkbox7" id="checkbox7" />
    Protect from light</label></td>
    <td rowspan="3"><label>
      <textarea class="textarea" name="textfield4" cols="25" rows="3" disabled="disabled" id="textfield4" >Enter Text</textarea>
    </label></td>
    <td rowspan="3"><label>
      <textarea class="textarea" name="textfield5" cols="25" rows="3" disabled="disabled" id="textfield5">Enter Text</textarea>
    </label></td>
  </tr>
  <tr>
    <td><label>
      <input type="checkbox" name="checkbox8" id="checkbox8" />
    CMOP Dispense</label></td>
  </tr>
  <tr>
    <td><label>
      <input type="checkbox" name="checkbox9" id="checkbox9" />
    Do not Mail</label></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>VA Dispense Unit: 
      <label>
      <input name="textfield3" type="text" id="textfield3" value="TAB" size="4"/>
    </label></td>
    <td>Dispense Unit per dose: 
      <label>
      <input name="textfield6" type="text" id="textfield6" value="1" size="2" />
    </label></td>
    <td>&nbsp;</td>
  </tr>
</table>
</fieldset>
<fieldset class="fieldset">
      <div class="botRight">
            <div class="modBttn">
                Non-editable data:
                <br/>
                <input name="" type="button" value="Request Modification" class="button"/>
            </div>
            <div class="templateBttn">
                Add New Product:
                <br/>
                <input name="" type="button" value="Open as a Template" class="button"/>
            </div>
        </div bottomRight>
        </fieldset>
</div>
</body>
</html>
