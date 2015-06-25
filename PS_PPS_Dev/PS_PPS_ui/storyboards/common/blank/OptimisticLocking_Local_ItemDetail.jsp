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
	String fileName = searchText + "_detail.csv";
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
<link href="../css/Content.css" rel="stylesheet" type="text/css" />
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
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
function MM_popupMsg(msg) { //v1.0
  alert(msg);
}
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</head>

<body>
<div class="pageBody">
<table width="100%" border="0">
  <tr>
    <th bgcolor="#630C21" scope="col"><div align="left"><span class="style1">Item Detail</span></div></th>
  </tr>
</table>
<form id="localItemDetail" name="localItemDetail" method="post">
    <div id="CollapsiblePanel1" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
        	External Data Fields
        </div collapsiblePanelTab>
        <div class="CollapsiblePanelContent">
            <div class="text_column1">
                Trade Name:
                <br />
                Generic Name:
                <br />
                Strength/Unit:
                <br />
                Route:
            </div col1>
<div class="text_column2">
                <input name="tradeName" type="text" disabled="disabled" id="tradeName" value="<%=tradeName%>"/>
                <br />
                <input name="genericName" type="text" disabled="disabled" id="genericName" value="<%=genericName%>"/>
<br />
                <input name="strength" type="text"  disabled="disabled" id="strength" value="<%=strength%>" size="4"/>
                <select name="unit"  disabled="disabled" id="unit" w="w"  value="<%=unit%>">
                <option>mg</option>
                    <option>ml</option>
                </select>
                <br />
                <select name="route"  disabled="disabled" id="route"  value="<%=route%>">
                <option>Oral</option>
                    <option>IV</option>
                </select>
            </div col2>
            <div class="text_column3">
                NDC:
                <br />
                Manufacturer:
                <br />
                Dosage Form:
                <br />
                Drug Class:
            </div col3>
            <div class="text_column4">
                <input name="ndc" type="text"   disabled="disabled" id="ndc" value="<%=ndc%>" size="12" />
                <br />
                <select name="manufacturer"   disabled="disabled" id="manufacturer"  value="<%=mfg%>">
                <option>MERCK &amp; CO.</option>
                    <option>JANSSEN PHARM.</option>
                </select>
                <br />
                <select name="doseForm"   disabled="disabled" id="doseForm"  value="<%=doseForm%>">
           	    <option>Tablet Rapid Dissolve</option>
                    <option>Tablet</option>
                    <option>Capsule</option>
                </select>
                <br />
                <select name="DrugClassComboBox"   disabled="disabled" id="DrugClassComboBox"  value="<%=drugClass%>">
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
        <div class="collapsiblePanelHeight">
            <div class="text_column1">
                Dispense Unit:
                <br />
                Orderable Item:
                <br />
                Approved for Splitting:
                <br/>
                Splittable:
            </div col1>
            <div class="text_column2">
                <select name="DispenseUnit"   disabled="disabled" id="DispenseUnit"  value="<%=dispenseUnit%>">
              <option>Not Selected</option>
                    <option>CAP</option>
                    <option selected = "selected">TAB</option>
                </select>
                <br />
                <input type="text" name="OrderableItem2" id="OrderableItem2" value="<%=orderable%>"/>
                <br />
                <select name="ApprovedSplitting" id="ApprovedSplitting" >
              <option>Not Selected</option>
                </select>
                <br/>
                <select name="splittable" size="3" id="splittable"  value="<%=splittable%>">
                <option>0.33</option>
                    <option>0.5</option>
                    <option>0.25</option>
                </select>
            </div col2>
            <div class="text_column3">
                Dispense Quantity Limit:
                <br />
                Days Supply Limit:
                <br />
                Recall Level:
                <br />
                Application Package Use:
            </div col3>
            <div class="text_column2">
                <input type="text" name="DQL1" id="DQL1" size="3" value="<%=dql1%>"/>
                /
                <input type="text" name="DQL2" id="DQL2" size="3" value="<%=dql2%>"/>
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
            <div class="text_column1">
            	Default Mail Service:
                <br />
                DEA Schedule:
                <br />
                Formulary:
                <br />
                Number of Doses:
                <br />
                Product Type:
            </div col5>
            <div class="text_column4">
            <select name="DefaultMailSvc" id="DefaultMailSvc" >
              <option>Not Selected</option>
              </select>
                <br/>
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
                <div class="checkbox_column1">
                    <td><input name="chkBox" type="checkbox" ><%=token.nextToken()%></td>
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
        </div CollapsiblePanelContent>
    </div CollapsiblePanel3>
    <div id="CollapsiblePanel4" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
            Labs and Vitals
        </div>
        <div class="CollapsiblePanelContent">
            <div class="labVital_column1">
                <BR/>
                Labs: <br/>
                Vitals: <br/>
            </div col1>
            <div class="labVital_column2">
                Entry <BR />
                <select name="LabEntry" id="LabEntry" >
              <option>INR/Plasma</option>
                </select><br/>
                <select name="VitalsEntry" id="VitalsEntry" >
              <option>Pulse (Heart Rate)</option>
                </select>
            </div col2>
            <div class="labVital_column2">
                Finishing<br />
                <select name="LabFinishing" id="LabFinishing" >
              <option>INR/Plasma</option>
                </select><br/>
                <select name="VitalsFinishing" id="VitalsFinishing" >
              <option>Pulse (Heart Rate)</option>
                </select>
            </div col3>
            <div class="labVital_column2">
                Admin <BR />
                <select name="LabAdmin" id="LabAdmin" >
              <option>INR/Plasma</option>
                </select><br/>
                <select name="VitalsAdmin" id="VitalsAdmin" >
              <option>Pulse (Heart Rate)</option>
                </select>
            </div col4>
      </div CollapsiblePanelContent4>
    </div CollapsiblePanel4>
    <br/>
  
<input name="save"  type="button" onClick="MM_openBrWindow('OptimisticLocking.jsp','Item Locked','status=yes,width=600,height=200')" value="Save and Submit">
</form>
</div>
<script type="text/javascript">
<!--
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1");
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2");
var CollapsiblePanel3 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel3");
var CollapsiblePanel4 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel4");
//-->
</script>
</body>
</html>
