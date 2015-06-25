<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>VA Data Fields</title>
<link href="../css/ProductDetail.css" rel="stylesheet" type="text/css" />
<link href="../css/NonEditableVADF.css" rel="stylesheet" type="text/css" />
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
<fieldset class="set"><legend>Item Details</legend>
		<div class="text_column1">
                
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
                Application Package Use:            </div col1>
            <div class="text_column2">
                orderable
          <br />
          R0023
          <br />
          Not Selected
                <br/>
                Not Selected
                <br/>
                Not Selected
                <br/>
                Not Selected
                <br/>
              Not Selected
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
                </div col3>
          <div class="text_column4">
         2 TAB per 14 days
            <br />
            Dispensed for 30 days at a time
        <br/>
                30 days
                <br />
               Not Selected
                <br/>
               Not Selected
                <br/>
              Not Selected
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
          		&nbsp;<br/>
                Labs: <br/>
                Vitals: <br/>
            </div col1>
            <div class="labVital_column2">
                Order Entry<BR />
                INR/Plasma
            <br/>
              Pulse (Heart Rate)
            </div col1>

			<div class="labVital_column2">
                Order Finish<BR />
                INR/Plasma
            <br/>
              Pulse (Heart Rate)
            </div col2>
            
            <div class="labVital_column2">
                Administration <BR />
                INR/Plasma<br/>
              Pulse (Heart Rate)
            </div col3>
            </fieldset>
           </div highest level>
</body>
</html>
