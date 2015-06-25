<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
    private String[] dataFields = {"Dispense Via CMOP,Hazardous to Patient,Patient Specific Label", "Do Not Handle if Pregnant,High-Risk,Protect From Light", "Do Not Mail,Long Term Out of Stock,Refrigeration", "Follow-Up Time,Low Safety Margin,Transmit to CMOP", "Hazardous to Dispense,Narcotic,Witness to Administration", "Hazardous to Handle,Non-Renewable,stop"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>VA Data Fields</title>
<link href="../css/ProductDetail.css" rel="stylesheet" type="text/css" />
<link href="../css/AddItemContent.css" rel="stylesheet" type="text/css" />
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
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
//-->
</script>
</head>

<body onload="MM_preloadImages('../images/nonEditable.gif')">
<div class="new_highestLevel">
<fieldset class="set"><legend>Item Details</legend>
		<div class="new_text_column1">
                <img src="../images/editable.gif" alt="editable" id="Image1" onClick="MM_swapImage('Image1','','../images/nonEditable.gif',1)" />
                Orderable Item:
                <br />
                <img src="../images/editable.gif" alt="editable" id="Image2" onClick="MM_swapImage('Image2','','../images/nonEditable.gif',1)" />
                CMOP ID:
                <br />
                <img src="../images/editable.gif" alt="editable" id="Image3" onClick="MM_swapImage('Image3','','../images/nonEditable.gif',1)" />
                DEA Schedule:
				<br />
                <img src="../images/editable.gif" alt="editable" id="Image4" onClick="MM_swapImage('Image4','','../images/nonEditable.gif',1)" />
                Product Type: 
				<br/>
                <img src="../images/editable.gif" alt="editable" id="Image5" onClick="MM_swapImage('Image5','','../images/nonEditable.gif',1)" />
                Formulary:
                <br />
                <img src="../images/editable.gif" alt="editable" id="Image6" onClick="MM_swapImage('Image6','','../images/nonEditable.gif',1)" />
                Approved for Splitting:
                <br/>
                <img src="../images/editable.gif" alt="editable" id="Image7" onClick="MM_swapImage('Image7','','../images/nonEditable.gif',1)" />
                Application Package Use:            </div col1>
            <div class="new_text_column2">
                <input name="OrderableItem2" type="text" id="OrderableItem2" value="Orderable"/>
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
                <select name="AppPkgUse" size="3" id="AppPkgUse">
                <option>Inpatient</option>
                    <option>Outpatient</option>
                </select>
            </div col2>
            <div class="nationaltext_column3">
            <img src="../images/editable.gif" alt="editable" id="Image8" onClick="MM_swapImage('Image8','','../images/nonEditable.gif',1)" />
            	Dispense Quantity Limit:
                <br />
                <img src="../images/editable.gif" alt="editable" id="Image9" onClick="MM_swapImage('Image9','','../images/nonEditable.gif',1)" />
                Quantity Dispense Msg:
                <br/>
                <img src="../images/editable.gif" alt="editable" id="Image10" onClick="MM_swapImage('Image10','','../images/nonEditable.gif',1)" />
                Days Supply Limit:
                <br />
                <img src="../images/editable.gif" alt="editable" id="Image11" onClick="MM_swapImage('Image11','','../images/nonEditable.gif',1)" />
                Number of Doses: 
                <br/>
                <img src="../images/editable.gif" alt="editable" id="Image12" onClick="MM_swapImage('Image12','','../images/nonEditable.gif',1)" />
				Default Mail Service: 
				<br/>
                <img src="../images/editable.gif" alt="editable" id="Image13" onClick="MM_swapImage('Image13','','../images/nonEditable.gif',1)" />
                Recall Level:
  </div col3>
          <div class="nationaltext_column4">
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
                <input name="QtyDispMsg" type="text" id="QtyDispMsg" size="35" value="Dispensed for 30 days at a time"/>
            <br/>
                <input type="text" name="DSL" id="DSL" size="3" style="text-align:right" value="30"/>
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
  </div col4>
  </fieldset>
    <fieldset><legend>Item Specifications</legend>
        <div id="booleanDataFields">
            <table width="100%" border="0" cellpadding="3" cellspacing="0">
            <%
                int k=0;
                for (int i=0; i<dataFields.length; i++)
                {
                    StringTokenizer token = new StringTokenizer(dataFields[i], ",");
            %>
                    <tr>
                        <div class="checkbox_column1">
                            <td><input name="input" type="image" src="../images/editable.gif" ><input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                        </div>
                        <div class="checkbox_column4">
                            <td><input name="input" type="image" src="../images/editable.gif" ><input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                        </div>
                        <div class="checkbox_column4">
                        <%  String temp = token.nextToken();
                            if(!(temp.trim().equals("stop")))
                            {
                        %>
                                <td><input name="input" type="image" src="../images/editable.gif" ><input name="chkBox" type="checkbox"><%=temp%></td>
                        <%
                            } else
                            {
                        %>
                                <td>&nbsp;</td>
                        <%
                            }
                        %>
                    </tr>
            <%
                }
            %>
            </table>
        </div booleanDataFields>
    </fieldset>
    <fieldset><legend>Labs and Vitals</legend>
          <div class="labVital_column1">
                Labs: <br/>
                Vitals: <br/>
          </div col1>
            <div class="labVital_column2">
            <input name="input" type="image" src="../images/editable.gif" >
                Order Entry<BR />
                <select name="LabEntry" id="LabEntry">
                <option selected="selected">Not Selected</option>
                  <option>INR/Plasma</option>
                  <option>Pulse (Heart Rate)</option>
                </select>
            <br/>
                <select name="VitalsEntry" id="VitalsEntry">
                <option selected="selected">Not Selected</option>
              <option>Pulse (Heart Rate)</option>
              </select>
            </div col1>

			<div class="labVital_column2">
            <input name="input" type="image" src="../images/editable.gif" >
                Order Finish<BR />
                <select name="LabFinish" id="LabFinish">
                <option selected="selected">Not Selected</option>
                  <option>INR/Plasma</option>
                  <option>Pulse (Heart Rate)</option>
                </select>
            <br/>
                <select name="VitalsEntry" id="VitalsEntry">
                <option selected="selected">Not Selected</option>
              <option>Pulse (Heart Rate)</option>
              </select>
            </div col2>
            
            <div class="labVital_column2">
            <input name="input" type="image" src="../images/editable.gif" >
                Administration <BR />
                <select name="LabAdmin" id="LabAdmin">
                <option selected="selected">Not Selected</option>
              <option>INR/Plasma</option>
              <option>Pulse (Heart Rate)</option>
              </select><br/>
                <select name="VitalsAdmin" id="VitalsAdmin">
                <option selected="selected">Not Selected</option>
              <option>Pulse (Heart Rate)</option>
                </select>
            </div col3>
  </fieldset>
</div highest level>
</body>
</html>
