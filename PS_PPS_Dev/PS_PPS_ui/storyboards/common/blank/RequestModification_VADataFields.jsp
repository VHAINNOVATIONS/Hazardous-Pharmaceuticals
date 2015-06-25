<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>VA Data Fields</title>
<link href="../css/RequestModification.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	color: #FFFFFF;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function MM_popupMsg(msg) { //v3.0
  alert(msg);
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
//-->
</script>
</head>

<body>
<div class="highestLevel">
<fieldset><legend>Item Details</legend>
		<div class="new_text_column1">
                
                Orderable Item:
                <br />
                CMOP ID:
                <br />
                DEA Schedule:
				<br />
                Product Type: 
				<br/>
                Formulary:
                <br />
                Approved for Splitting:
                <br/>
                Scored:            </div col1>
            <div class="text_column2">
                <input name="OrderableItem2" type="text" id="OrderableItem2" value="orderable"/>
          <br />
          <input name="CMOPID" type="text" id="CMOPID" value="R0023"/>
          <br />
          <select name="DefaultMailSvc" id="DefaultMailSvc"  >
              <option>Not Selected</option>
              </select>
                <br/>
                 <select name="DEASchedule" id="DEASchedule" >
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="ProductType" id="ProductType">
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="ApprovedSplitting" id="ApprovedSplitting">
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="splittable" size="3" id="splittable">
                <option>0.25</option>
                    <option>0.33</option>
                    <option>0.5</option>
                </select>
            </div col2>
            <div class="text_column3">
            	Dispense Quantity Limit:
                <br />
                Quantity Dispense Msg:
                <br/>
                Days Supply Limit:
                <br />
                Number of Doses: 
                <br/>
				Default Mail Service: 
				<br/>
                Recall Level:
            <br />
                Application Package Use:            </div col3>
          <div class="text_column4">
          <input name="DQL1" type="text" id="DQL1" value="2"  style="text-align:right" size="2"/>
            <select name="DispenseUnit"  id="DispenseUnit">
              <option>Not Selected</option>
                    <option>CAP</option>
                    <option selected>TAB</option>
                </select>
                per
	        	<input type="text" name="DQL2" id="DQL2" size="2"  style="text-align:right" value="14"/>
                days
                <br />
                <input name="QtyDispMsg" type="text" class="greentextField" id="QtyDispMsg" size="30"  value="Dispensed for 30 days at a time"/>
                <br/>
                <input type="text" name="DSL" id="DSL" size="3" class="greentextField" style="text-align:right" value="30"/>
                days
                <br />
                <select name="RecallLevel" id="RecallLevel">
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="Formulary" id="Formulary">
              <option>Not Selected</option>
                </select>
                <br/>
                 <select name="NumDoses" id="NumDoses">
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="ApplicationPackage" size="3" class="greentextField" multiple="multiple" id="ApplicationPackage">
                    <option>Inpatient</option>
                    <option>Outpatient</option>
                </select>
  </div col4>
  </fieldset>
          <fieldset><legend>Item Specifications</legend>
           <div id="booleanDataFields">
            <%
                String fileName = "http://localhost:8080/PRE/data/booleanDFList.csv";
               URL source = new URL(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                
                String s1 = "";
            %>
            <table width="100%" border="0" cellpadding="3" cellspacing="0">
            <%
				br.readLine(); //first line of file has no data values
                String line = "";
                while ((line = br.readLine()) != null)
                {
                    StringTokenizer token = new StringTokenizer(line, ",");
            %>
                <tr>
                <div class="checkbox_column1">
                    <td><input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                    </div>
                    <div class="checkbox_column4">
                    <td><input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                    </div>
                    <div class="checkbox_column4">
                    <td><input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                    </div>
                    
              </tr>
            <%
                }
				br.close();
            %>
            </table>
            </div booleanDataFields>
          </fieldset>
          <fieldset><legend>Labs and Vitals</legend>
          <div class="labVital_column1">
                <BR/>
                Labs: <br/>
                Vitals: <br/>
            </div col1>
            <div class="labVital_column2">
                Order Entry<BR />
                <select name="LabEntry" id="LabEntry">
                  <option selected="selected">INR/Plasma</option>
                  <option>Pulse (Heart Rate)</option>
                </select>
            <br/>
                <select name="VitalsEntry" id="VitalsEntry">
              <option selected="selected">Pulse (Heart Rate)</option>
              </select>
            </div col1>

	<div class="labVital_column2">
                Order Finish<BR />
                <select name="LabFinish" id="LabFinish">
                  <option selected="selected">INR/Plasma</option>
                  <option>Pulse (Heart Rate)</option>
                </select>
            <br/>
                <select name="VitalsEntry" id="VitalsEntry">
              <option selected="selected">Pulse (Heart Rate)</option>
              </select>
            </div col2>
            
            <div class="labVital_column2">
                Administration <BR />
                <select name="LabAdmin" id="LabAdmin">
              <option selected="selected">INR/Plasma</option>
              <option>Pulse (Heart Rate)</option>
              </select><br/>
                <select name="VitalsAdmin" id="VitalsAdmin">
              <option>Pulse (Heart Rate)</option>
                </select>
            </div col3>
            </fieldset>
            <!--<input name="btnRequestModification" type="button" value="Request Modification" onClick="MM_confirmMsg('Are you sure you want to create a new modification request?')"/>-->
            <input name="btnRequestModification" type="button" class="buttonRequestMod" value="Request Modification" onClick="MM_openBrWindow('RequestModification_Dialog.html','Request Modification','width=490,height=220')"/>
            </div highest level>
</body>
</html>
