<%@ include file="HeaderFrame.html"%>
<%@ include file="LeftFrame.html"%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String tradeName="";
    private String genericName="";
    private String ndc="";
	private String route="";
	private String mfg="";
	private String strength="";
	private String unit="";
	private String doseForm="";
	private String drugClass="";
	private String dispenseUnit="";
	private String dql1="";
	private String dql2="";
	private String orderable="";
	private String dsl="";
	private String splittable="";
	private String appPkg="";
	private boolean witness=false;
%>

<%
	String searchText = (String)application.getAttribute("searchText");
	String fileName = "Atomoxetine_detail.csv";
	URL source = new URL("http://localhost:8080/PRE/data/"+fileName);
	BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
	
	br.readLine();
	String line = br.readLine();
	StringTokenizer token = new StringTokenizer(line, ",");
	tradeName = token.nextToken();
	genericName = token.nextToken();
	ndc = token.nextToken();
	route = token.nextToken();
	mfg = token.nextToken();
	strength = token.nextToken();
	unit = token.nextToken();
	doseForm = token.nextToken();
	drugClass = token.nextToken();
	dispenseUnit = token.nextToken();
	dql1 = token.nextToken();
	dql2 = token.nextToken();
	orderable = token.nextToken();
	dsl = token.nextToken();
	splittable = token.nextToken();
	appPkg = token.nextToken();
	String temp = token.nextToken();
	witness = new Boolean(temp).booleanValue();
	
	String filePath = "C:\\My Tools\\Tomcat\\apache-tomcat-5.5.23\\webapps\\ROOT\\PRE\\data\\"+searchText+"_search.csv";
	if (request.getParameter("genericName") != null) {
		File file = new File(filePath);
		RandomAccessFile raf =  new RandomAccessFile(file, "rw");
		try {
			raf.seek(file.length());
			raf.writeBytes(request.getParameter("genericName")+",");
			raf.writeBytes(request.getParameter("strength")+",");
			raf.writeBytes(request.getParameter("unit")+",");
			raf.writeBytes(request.getParameter("doseForm")+",");
			raf.writeBytes(request.getParameter("drugClass")+",");
			raf.writeBytes("Y,");
			raf.writeBytes("Pending,");
			raf.writeBytes("????,");
			raf.writeBytes("????,");
			raf.writeBytes("\r\n");
		} finally { 
			raf.close(); 
		}
	}
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Alternate Item Detail View</title>
<script src="../javascript/CollapsiblePanel.js" type="text/javascript"></script>
<link href="../css/CollapsiblePanel.css" rel="stylesheet" type="text/css" />
<link href="../css/RPNM.css" rel="stylesheet" type="text/css" />
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

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
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

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body onLoad="MM_preloadImages('../images/nonEditable.gif')">
<div class="pageTitle">
	Item Detail
</div>
<div class="pageBody">
<form id="itemDetail" name="itemDetail" method="post">
    <div id="CollapsiblePanel1" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
        	External Data Fields
        </div collapsiblePanelTab>
        <div class="CollapsiblePanelContent">
            <div class="labels_column1">
                Trade Name:
                <br />
                Generic Name:
                <br />
                Strength/Unit:
                <br />
                Route:
            </div col1>
            <div class="text_column2">
              <input name="tradeName" type="text" id="tradeName" value="<%=tradeName%>"/>
              <br />
                <input name="genericName" type="text" id="genericName" value="<%=genericName%>"/>
              <br />
                <input name="strength" type="text"  id="strength" value="<%=strength%>" size="4"/>
                <select name="unit"  id="unit" w="w"  value="<%=unit%>">
                <option>mg</option>
                    <option>ml</option>
                </select>
                <br />
                <select name="route" id="route"  value="<%=route%>">
                <option>Oral</option>
                    <option>IV</option>
                </select>
          </div col2>
            <div class="labels_column1">
                NDC:
                <br />
                Manufacturer:
                <br />
                Dosage Form:
                <br />
                Drug Class:
            </div col3>
            <div class="text_column4">
                <input name="ndc" type="text"  id="ndc" value="<%=ndc%>" size="12" />
                <br />
                <select name="manufacturer"  id="manufacturer"  value="<%=mfg%>">
                <option>MERCK &amp; CO.</option>
                    <option>JANSSEN PHARM.</option>
                </select>
                <br />
                <select name="doseForm"  id="doseForm"  value="<%=doseForm%>">
           	    <option>Tablet Rapid Dissolve</option>
                    <option>Tablet</option>
                    <option>Capsule</option>
                </select>
                <br />
                <select name="DrugClassComboBox"  id="DrugClassComboBox"  value="<%=drugClass%>">
                <option>Ace Inhibitors</option>
                    <option>Antipsychotic-Atypical Dopamine-Serotonin Antag-Benzisoxaole Deriv</option>
                </select>
            </div col4>
        </div collapsiblePanelContent>
    </div collapsiblePanel>
    <div id="CollapsiblePanel2" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
        	Complex Data Fields
        </div>
        <div class="CollapsiblePanelContent">
            <div class="complexDF">
                <div class="icon_column1">
                    <input name="editable" type="image" onClick="MM_swapImage('Image1','','../images/nonEditable.gif',1)" src="../images/editable.gif" alt="edit" >
                    Dispense Unit:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Orderable Item:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Default Mail Service:
                    <br/>
                    <input name="input" type="image" src="../images/editable.gif" >
                    Splittable:
                </div col1>
              <div class="df_fields">
                    <select name="DispenseUnit" id="DispenseUnit"  value="<%=dispenseUnit%>">
                  <option>Not Selected</option>
                        <option>CAP</option>
                        <option selected = "selected">TAB</option>
                    </select>
                    
                    <br />
              <input type="text" name="OrderableItem2" id="OrderableItem2" value="<%=orderable%>"/>
                    <br />
                    <select name="DefaultMailSvc" id="DefaultMailSvc" >
                        <option>Not Selected</option>
                    </select>
                  <br/>
                  <select name="splittable" size="3" id="splittable"  value="<%=splittable%>">
                    <option>0.33</option>
                    <option>0.5</option>
                    <option>0.25</option>
                  </select>
              </div col2>
                <div class="icon_column2">
                    <input name="input" type="image" src="../images/editable.gif" >
                    Dispense Quantity Limit:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Days Supply Limit:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Recall Level:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Application Package Use:
                </div col3>
              <div class="df_fields">
                    <input type="text" name="DQL1" id="DQL1" size="1" value="<%=dql1%>"/>
                    /
                  <input type="text" name="DQL2" id="DQL2" size="1" value="<%=dql2%>"/>
                    days
                    <br />
                    <input type="text" name="DSL" id="DSL" size="3" value="<%=dsl%>"/>
                    days
                    <br />
                    <select name="RecallLevel" id="RecallLevel" >
                  <option>Not Selected</option>
                    </select>
                  <br/>
                    <select name="ApplicationPackage" size="3" multiple="multiple" id="ApplicationPackage" value="<%=appPkg%>">
                        <option>Inpatient</option>
                        <option>Outpatient</option>
                    </select>
                </div col4>
                <div class="icon_column3">
                    <input name="input" type="image" src="../images/editable.gif" >
                    DEA Schedule:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Formulary:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Number of Doses:
                    <br />
                    <input name="input" type="image" src="../images/editable.gif" >
                    Product Type:
                </div col5>
              <div class="last_df_fields">
                     <select name="DEASchedule" id="DEASchedule" >
                  <option>Not Selected</option>
                    </select>
                  <br/>
                     <select name="Formulary" id="Formulary" >
                  <option>Not Selected</option>
                    </select>
                  <br/>
                     <select name="NumDoses" id="NumDoses" >
                  <option>Not Selected</option>
                    </select>
                  <br/>
                     <select name="ProductType" id="ProductType" >
                  <option>Not Selected</option>
                    </select>
              </div col6>
          </div>
      </div CollapsiblePanelContent>
    </div CollapsiblePanel2>
<div id="CollapsiblePanel3" class="CollapsiblePanel">
    	<div class="CollapsiblePanelTab" tabindex="0">
        	Boolean Data Fields
        </div>
        <div class="CollapsiblePanelContent">
        	 <div id="booleanDataFields">
            <%
                fileName = "http://localhost:8080/PRE/data/booleanDFList.csv";
                source = new URL(fileName);
                br = new BufferedReader(new InputStreamReader(source.openStream()));
                
                String s1 = "";
            %>
            <table width="100%" border="0" cellpadding="3" cellspacing="0">
            <%
				br.readLine(); //first line of file has no data values
                line = "";
                while ((line = br.readLine()) != null)
                {
                    token = new StringTokenizer(line, ",");
            	%>
                <tr>
                    <td width="33%"><input name="input" type="image" src="../images/editable.gif" >
                    	<input name="chkBox" type="checkbox" ><%=token.nextToken()%></td>
                    <td width="33%"><input name="input" type="image" src="../images/editable.gif" >
	                    <input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
                    <td width="33%"><input name="input" type="image" src="../images/editable.gif" >
	                    <input name="chkBox" type="checkbox"><%=token.nextToken()%></td>
</tr>
	            <%
                }
				br.close();
            %>
            </table>
            </div booleanDataFields>
        </div CollapsiblePanelContent>
    </div CollapsiblePanel3>
    <div id="CollapsiblePanel4" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
            Labs and Vitals
        </div>
        <div class="CollapsiblePanelContent">
        	<div class="labsVitals">
          <div class="labVital_column1">
                    <BR/>
                    Labs: <br/>
                    Vitals: <br/>
                </div col1>
                <div class="labVital_column2">
                    
                    Entry
                    <BR />
                    <select name="LabEntry" id="LabEntry" >
                          <option>INR/Plasma</option>
                          <option>Pulse (Heart Rate)</option>
                    </select><input name="input2" type="image" src="../images/editable.gif" ><br/>
                    <select name="VitalsEntry" id="VitalsEntry" >
                  <option>Pulse (Heart Rate)</option>
                    </select><input name="input3" type="image" src="../images/editable.gif" >
                </div col2>
                <div class="labVital_column2">
                    
                    Finishing<br />
                    <select name="LabFinishing" id="LabFinishing" >
                  		<option>INR/Plasma</option>
                        <option>Pulse (Heart Rate)</option>
                    </select><input name="input2" type="image" src="../images/editable.gif" ><br/>
                    <select name="VitalsFinishing" id="VitalsFinishing" >
                  		<option>Pulse (Heart Rate)</option>
                    </select><input name="input2" type="image" src="../images/editable.gif" >
                </div col3>
<div class="labVital_column2">
                    
                    Admin <BR />
                    <select name="LabAdmin" id="LabAdmin" >
                  		<option>INR/Plasma</option>
                        <option>Pulse (Heart Rate)</option>
                    </select><input name="input2" type="image" src="../images/editable.gif" ><br/>
                    <select name="VitalsAdmin" id="VitalsAdmin" >
                      	<option>Pulse (Heart Rate)</option>
                    </select><input name="input2" type="image" src="../images/editable.gif" >
                </div col4>
            </div>
      </div CollapsiblePanelContent4>
    </div CollapsiblePanel4>
	<div class="buttons">
   	  <input name="approve" type="button" onClick="MM_confirmMsg('Are you sure you want to approve this item?')" value="Approve">
      <!--input name="approve" type="button" onClick="MM_popupMsg('This item already exists in the system.')" value="Approve"-->
          <input name="reject" type="button" value="Reject" onClick="MM_openBrWindow('RejectReason.html','RejectReason','width=490,height=200')">
   	  <!--input name="reject" type="button" onClick="MM_confirmMsg('Are you sure you want to reject this item ?')" value="Reject"-->
        <input name="cancel" type="button" value="Cancel">
	</div>
</form>
</div>
<script type="text/javascript">
<!--
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1");
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2");
var CollapsiblePanel3 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel3");
var CollapsiblePanel4 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel4");
var CollapsiblePanel5 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel5");
//-->
</script>
</body>
</html>
