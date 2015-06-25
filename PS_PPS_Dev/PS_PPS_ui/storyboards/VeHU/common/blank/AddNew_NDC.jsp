<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>NDC:50458-0320-50</title>
<script src="../javascript/TabbedPanels.js" type="text/javascript"></script>
<link href="../css/TabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="../css/Product.css" rel="stylesheet" type="text/css" />
<link href="../css/ProductDetail.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="ndcBody">
	<div id="highestLevel" class="heading">
        <div class="productId">
            RISPERIDONE 2 MG TAB 500
        </div colLeft>
        <br/>
        <div class="generalInfo">
            <table width="95%" border="0" cellspacing="1" cellpadding="0" class="topTable">
              <tr>
                <td width="60%" height="23">&nbsp;</td>
                <td width="40%"><strong>Product:</strong> <a href="#">Risperidal 2 MG TAB</a></td>
              </tr>
              <tr>
                <td height="23">&nbsp;</td>
                <td><strong>Generic Name:</strong> RISPERIDONE</td>
              </tr>
            </table>
      </div>
    </div highestLevel>

<fieldset>
<table width="100%" border="0" cellspacing="1" cellpadding="0">
<tr>
    <td><strong>NDC:</strong></td>
    <td><input type="text" name="txtNDC" id="txtNDC" /></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="15%"><strong>Manufacturer:</strong></td>
    <td width="33%"><input type="text" name="txtManufacturer" id="txtManufacturer" value="JOM PHARMACEUTICAL" /></td>
    <td width="8%"><strong>Shape:</strong></td>
    <td width="20%"><input type="text" name="txtShape" id="txtShape" value="Oblong" /></td>
    <td width="24%" rowspan="3"><label style="border:dotted"><strong>No Image Available</strong></label></td>
  </tr>
  <tr>
    <td><strong>Package Type:</strong></td>
    <td><input type="text" name="txtPkgType" id="txtPkgType" value="OblongBottle" />
      </td>
    <td><strong>Color:</strong></td>
    <td><input type="text" name="txtColor" id="txtColor" value="Orange" /></td>
    </tr>
  <tr>
    <td><strong>Package Size:</strong></td>
    <td><input type="text" name="txtPkgSize" id="txtPkgSize" value="500" /></td>
    <td><strong>Imprint:</strong></td>
    <td><input type="text" name="txtImprint" id="txtImprint" value="JANSSEN" /></td>
    </tr>
  
</table>
<fieldset class="fieldset"><legend class="borderTitle">Local VA Data</legend>
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
  <tr>
    <td width="23%" align="right">Unit Price: </td>
    <td width="27%"><label>
      <input name="textfield" type="text" id="textfield" value="$ 3.0217" size="8" />
    </label></td>
    <td width="50%" rowspan="4"><label>Scored
        <select name="select" id="select">
          <option>Yes</option>
          <option selected="selected">No</option>
        </select>
    </label>
      <p>
        <label>
        <input type="checkbox" name="checkbox" id="checkbox" />
        Protect from light</label>
      </p>
      <p>
        <label>
        <input type="checkbox" name="checkbox2" id="checkbox2" />
        Refrigeration</label>
      </p>
       <p>
        <label>
        <input type="checkbox" name="MFLUcheckbox" id="MFLUcheckbox" />
        Mark for Local Use</label>
      </p></td>
  </tr>
  <tr>
    <td align="right">Dispense Unit per Order Unit:</td>
    <td><label>
      <input name="textfield2" type="text" id="textfield2" value="1" size="8" />
    </label></td>
    </tr>
  <tr>
    <td align="right">Price per Order Unit:</td>
    <td><label>
      <input name="textfield3" type="text" id="textfield3" value="$ 3.0217" size="8" />
    </label></td>
    </tr>
  <tr>
    <td align="right">Reorder Level:</td>
    <td><label>
      <input name="textfield4" type="text" id="textfield4" value="1" size="8" />
    </label></td>
    </tr>
</table>
    </fieldset>
    
</fieldset>
        
    <div id="buttons" class="buttonPanel">
        <input class="buttonLeft" name="Print" type="button" value="Print" />
        <input class="buttonLeft" name="Export" type="button" value="Export" />
        <input name="Save" type="submit"  class="buttonCenter" value="Save" />
        <input class="buttonRight" name="Close" type="button" value="Close" />
    </div buttonPanel>
</div mainBody>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
