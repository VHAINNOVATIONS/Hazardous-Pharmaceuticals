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
	
	private String[] mfgList = {"Merck & CO.", "Novartis"};
	private String[] doseList = {"Capsule", "Tablet"};
	private String[] unitList = {"mg", "ml"};
	private String[] drugClassList = {"ACE Inhibitors-Calcium Blockers Combinations", "Antipsychotic - Atypical Dopamine-Serotonin Antag"};
	private String[] routeList = {"Oral", "IV"};
	private String[] dispUnitList = {"CAP", "TAB"};
	private String[] appPkgList = {"Inpatient", "Outpatient"};
	private String[] splittableList = {"0.25", "0.33", "0.5"};
%>

<%
	String searchText = (String)application.getAttribute("searchText");
	String fileName = searchText + "_detail.csv";
	URL source = new URL("http://localhost:8080/PRE/data/"+fileName);
	BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
	
	br.readLine();
	String line = br.readLine();
	StringTokenizer token = new StringTokenizer(line, ",");
	genericName = token.nextToken();
	tradeName = token.nextToken();
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
//-->
</script>
</head>

<body>
<div id="pageTitle" class="pageTitle">
    Item Details
</div>  
<div class="pageBody">
<form id="itemDetail" name="itemDetail" method="post" action="ItemDetail.jsp">
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
                <input type="text" name="tradeName" id="tradeName" value="<%=tradeName%>"/>
                <br />
                <input type="text" name="genericName" id="genericName" value="<%=genericName%>"/>
               	<br />
                <input type="text" name="strength" id="strength" size="4" value="<%=strength%>"/>
              	<br />
              	<select name="route" id="route">
                    <% 
					for (int i=0; i<routeList.length; i++) {
						String rt = routeList[i];
						if (rt.equalsIgnoreCase(route)) {%>
                  			<option selected><%=rt%></option>
		                <%
						} else { %>
							<option><%=rt%></option>
                  		<%
                  		}
					}
					%>
              </select>
            <select name="unit" id="unit" >
                    <% 
					for (int i=0; i<unitList.length; i++) {
						String u = unitList[i];
						if (u.equalsIgnoreCase(unit)) {%>
                  			<option selected><%=u%></option>
		                <%
						} else { %>
							<option><%=u%></option>
                  		<%
                  		}
					}
					%>
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
                <input name="ndc" type="text" id="ndc" value="<%=ndc%>" size="12" />
                <br />
                <select name="manufacturer" id="manufacturer">
               	  <% 
					for (int i=0; i<mfgList.length; i++) {
						String mf = mfgList[i];
						if (mf.equalsIgnoreCase(mfg)) {%>
                  			<option selected><%=mf%></option>
		                <%
						} else { %>
							<option><%=mf%></option>
           		  		<%
                  		}
					}
					%>
                </select>
          		<br />
          		<select name="doseForm" id="doseForm">
                    <% 
					for (int i=0; i<doseList.length; i++) {
						String dose = doseList[i];
						if (dose.equalsIgnoreCase(doseForm)) {%>
                  			<option selected><%=dose%></option>
		                <%
						} else { %>
							<option><%=dose%></option>
       					<%
                  		}
					}
					%>
          		</select>
                <br />
                 <select name="DrugClassComboBox" id="DrugClassComboBox" >
                  <% 
					for (int i=0; i<drugClassList.length; i++) {
						String dc = drugClassList[i];
						if (dc.equalsIgnoreCase(drugClass)) {%>
                  			<option selected><%=dc%></option>
                 		 <%
						} else { %>
                        <option><%=dc%></option>
                        <%
                  		}
					}
					%>
                </select>
			</div col4>
        </div collapsiblePanelContent>
    </div collapsiblePanel>
    <div id="CollapsiblePanel2" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
        	Complex Data Fields
        </div>
        <div class="CollapsiblePanelContent">
            <div class="text_column1">
                Dispense Unit:
                <br />
                Orderable Item:
                <br />
                Splittable:
            </div col1>
            <div class="text_column2">
                <select name="DispenseUnit" id="DispenseUnit" >
                    <% 
					for (int i=0; i<dispUnitList.length; i++) {
						String disp = dispUnitList[i];
						if (disp.equalsIgnoreCase(dispenseUnit)) {%>
                  			<option selected><%=disp%></option>
		                <%
						} else { %>
							<option><%=disp%></option>
                  		<%
                  		}
					}
					%>
                </select>
                <br />
                <input type="text" name="OrderableItem2" id="OrderableItem2" value="<%=orderable%>"/>
                <br />
                <select name="splittable" size="3" id="splittable" >
                    <% 
					for (int i=0; i<splittableList.length; i++) {
						String split = splittableList[i];
						if (split.equalsIgnoreCase(splittable)) {%>
                  			<option selected><%=split%></option>
		                <%
						} else { %>
							<option><%=split%></option>
                  		<%
                  		}
					}
					%>
                </select>
            </div col2>
            <div class="text_column3">
                Dispense Quantity Limit:
                <br />
                Days Supply Limit:
                <br />
                Application Package Use:
            </div col3>
            <div class="text_column4">
                <input type="text" name="DQL1" id="DQL1" size="3" value="<%=dql1%>"/>
                <input type="text" name="DQL2" id="DQL2" size="3" value="<%=dql2%>"/>
                days
                <br />
                <select name="DSL" id="DSL" value="<%=dsl%>">
                    <option>days</option>
                </select>
                days
                <br />
                <select name="ApplicationPackage" size="3" multiple="multiple" id="ApplicationPackage">
                    <% 
					for (int i=0; i<appPkgList.length; i++) {
						String ap = appPkgList[i];
						if (ap.equalsIgnoreCase(appPkg)) {%>
                  			<option selected><%=ap%></option>
		                <%
						} else { %>
							<option><%=ap%></option>
                  		<%
                  		}
					}
					%>
                </select>
            </div col4>
        </div CollapsiblePanelContent>
    </div CollapsiblePanel2>
    <div id="CollapsiblePanel3" class="CollapsiblePanel">
    	<div class="CollapsiblePanelTab" tabindex="0">
        	Boolean Data Fields
        </div>
        <div class="CollapsiblePanelContent">
        	<div class="checkbox_column1">
                <input type="checkbox" name="Witness" id="Witness" />
                Witness For Administration <br/>
                <input type="checkbox" name="Mail" id="Mail" />
                Do Not Mail <br/>
                <input type="checkbox" name="Label" id="Label" />
                Patient Specific Label <br/>
                <input type="checkbox" name="Refrige" id="Refrige" />
                Refrigeration <br />
            </div>
            <div class="checkbox_column4">
                <input type="checkbox" name="Risk" id="Risk" />
                High-Risk<br/>
                <input type="checkbox" name="Light" id="Light" />
                Protect From Light <br/>
                <input type="checkbox" name="CMOP" id="CMOP" />
                CMOP Transmission  <br/>
                <input type="checkbox" name="Local" id="Local" />
                Mark For Local Use <br /><br />
            </div>
        </div CollapsiblePanelContent>
    </div CollapsiblePanel3>
    <div id="CollapsiblePanel4" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
            Labs
        </div>
        <div class="CollapsiblePanelContent">
            <div class="checkbox_column1">
                <BR/>
                INR/Plasma: <br/>
                Creatinine/Serum: <br/>
                Hemoglobin Alc/Blood: <br/>
                Gentamicin - Peak/Serum: <br/>
                Gentamicin - Trough/Serum: <br/>
                Gentamicin - Random/Serum: <br/>
            </div col1>
            <div class="checkbox_column2">
                Entry <BR />
                <input type="checkbox" name="inrEntry" id="inrEntry" /><br />
                <input type="checkbox" name="creatinineEntry" id="creatinineEntry" /><br />
                <input type="checkbox" name="hemoglobinEntry" id="hemoglobinEntry" /><br/>
                <input type="checkbox" name="peakEntry" id="peakEntry" /><br />
                <input type="checkbox" name="troughEntry" id="troughEntry" /><br/>
                <input type="checkbox" name="randomEntry" id="randomEntry" /><br />
            </div col2>
            <div class="checkbox_column3">
                Finishing<br />
                <input type="checkbox" name="inrFinishing" id="inrFinishing" /><br />
                <input type="checkbox" name="creatinineFinishing" id="creatinineFinishing" /><br />
                <input type="checkbox" name="hemoglobinFinishing" id="hemoglobinFinishing" /><br/>
                <input type="checkbox" name="peakFinishing" id="peakFinishing" /><br />
                <input type="checkbox" name="troughFinishing" id="troughFinishing" /><br/>
                <input type="checkbox" name="randomFinishing" id="randomFinishing" /><br />
            </div col3>
            <div class="checkbox_column4">
                Admin <BR />
                <input type="checkbox" name="inrAdmin" id="inrAdmin" /><br />
                <input type="checkbox" name="creatinineAdmin" id="creatinineAdmin" /><br />
                <input type="checkbox" name="hemoglobinAdmin" id="hemoglobinAdmin" /><br/>
                <input type="checkbox" name="peakAdmin" id="peakAdmin" /><br />
                <input type="checkbox" name="troughAdmin" id="troughAdmin" /><br/>
                <input type="checkbox" name="randomAdmin" id="randomAdmin" /><br />
            </div col4>
      </div CollapsiblePanelContent4>
    </div CollapsiblePanel4>
    <div id="CollapsiblePanel5" class="CollapsiblePanel">
        <div class="CollapsiblePanelTab" tabindex="0">
        	Vitals
        </div>
        <div class="CollapsiblePanelContent">
            <div class="checkbox_column1">
                <BR />
                Pulse (Heart Rate): <BR />
                Blood Pressure: <BR />
                Pain Level: <BR />
                Temperature: <BR />
            </div col1>
            <div class="checkbox_column2">
                Entry <BR />
                <input type="checkbox" name="heartEntry" id="heartEntry" /> <BR />
                <input type="checkbox" name="bpEntry" id="bpEntry" /> <BR />
                <input type="checkbox" name="painEntry" id="painEntry" /> <BR />
                <input type="checkbox" name="tempEntry" id="tempEntry" /> <BR />
            </div col2>
            <div class="checkbox_column3">
                Finishing <BR />
                <input type="checkbox" name="heartFinish" id=="heartFinish" /> <BR />
                <input type="checkbox" name="bpFinish" id="bpFinish" /> <BR />
                <input type="checkbox" name="painFinish" id="painFinish" /> <BR />
                <input type="checkbox" name="tempFinish" id="tempFinish" /> <BR />
            </div col3>
            <div class="checkbox_column4">
                Admin <BR />
                <input type="checkbox" name="heartAdmin" id="heartAdmin" /> <BR />
                <input type="checkbox" name="bpAdmin" id="bpAdmin" /> <BR />
                <input type="checkbox" name="painAdmin" id="painAdmin" /> <BR />
                <input type="checkbox" name="tempAdmin" id="tempAdmin" /> <BR />
            </div col4>
      </div collapsablePanelContent>
    </div collapsablePanel>
    <input name="save" type="submit" value="Save" />

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
