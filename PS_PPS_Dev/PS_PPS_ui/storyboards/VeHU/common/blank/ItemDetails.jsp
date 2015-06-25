<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="../css/ItemDetails.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>

<body>
	<div class="body">
	<div class="top">
    	<div class="left">
        	<div class="text">
            	<p>
                	<b>Name:</b> RISPERDAL
                </p>
                <p>
                	<b>Form:</b> TABLET
                </p>
          </div>
        </div>
        <div class="left">
        	<div class="righttext">
                <p>
                	<b>VA Drug Class:</b> Anti-psychotic
				</p>
                <p>
                	<b>Formulary Status:</b> Formulary</p>
            </div>
        </div>
    </div top>
    <div class="clinicalData">
    	<fieldset class="fieldset"><legend class="borderTitle">Local VA Data
            <div class="left">
            	<div class="checkboxes">
                        <label>
                            <input name="HighRisk" type="checkbox" />
                            High Risk
                        </label>
                    <p>
                        <label>
                            <input name="Witness" type="checkbox" />
                            Witness for Administration
                        </label>
                    </p>

                        <label>
                            <input name="HighRisk" type="checkbox" />
                            Patient Specific Label
                        </label>
						<br/>
                    	<textarea name="" rows="3" disabled="disabled">Enter Text</textarea>
                    <br/><br/>
                        <label>
                            <input name="drugMsg" type="checkbox" />
                            Drug Message
                        </label>
                    <br/>
                        <textarea name="" rows="3" disabled="disabled">Enter Text</textarea>
                </div checkboxes>
            </div left>
            
            <div class="left">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
  <tr>
    <td colspan="3">
    	<label>
          <input type="checkbox" name="checkbox" id="checkbox" />
          Required Labs          </label>    </td>
    </tr>
  <tr>
    <td width="25">&nbsp;</td>
    <td width="120" align="right" class="labelColumn"><br/>Labs Test Name:</td>
    <td><br/> <select name="labs" disabled="disabled">
                              <option selected="selected">None specified</option>
                              <option>Longer List of Values</option>
                              </select></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="right" class="labelColumn"><br/>Specimen:</td>
    <td><br/><select name="labs" disabled="disabled">
                              <option selected="selected">None specified</option>
                              <option>Longer List of Values</option>
                              </select></td>
  </tr>
  <tr >
    <td colspan="3">
    	<br/><label>
          <input type="checkbox" name="checkbox2" id="checkbox2" />
          Required Vitals</label>    </td>
    </tr>
  <tr height="16px">
    <td>&nbsp;</td>
    <td align="right" class="labelColumn">Vitals:</td>
    <td><select name="labs" disabled="disabled">
                              <option selected="selected">None specified</option>
                              <option>Longer List of Values</option>
                            </select></td>
  </tr>
</table>
               		</div right>
            <div class="bottomRt">
            <br/><br/><br/><br/>
            	<div class="modReqButton">
                	Non-editable data:
                    <br/>
                    <input name="" type="button" value="Request Modification" class="button"/>
               	</div>
                <div class="templtButton">
                	Add New Orderable Item:
                    <br/>
                    <input name="" type="button" value="Open as a Template" class="button"/>
               	</div>
            </div bottomRight>

    	</legend></fieldset>
    </div clinicalData>
</div body>
</body>
</html>
