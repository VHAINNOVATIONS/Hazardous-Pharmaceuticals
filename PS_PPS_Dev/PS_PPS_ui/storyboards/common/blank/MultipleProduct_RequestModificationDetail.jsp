<%@ include file="HeaderFrame.html"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
    private String[] tab1TableColumns = {"Field Name", "Current Value", "Modified Value", "Reason<br/>(optional)", "Confirm"};
	private String[] tab2TableColumns = {"Field Name", "Current Value", "Requested Value","Reason<br/>(required)", "Request to Modify<br/>Field Value","Request to Make<br/>Field Editable"};
%>

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Modification Summary for Multiple Products</title>

<link href="../css/RequestModificationDetail.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
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
<form action="" method="get">


<div class="heading">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="60%" valign="top"align="left"><h2>Modification Summary for Multiple Products</h2></td>
    <td valign="top" align="right"><a href="#">Comments</a></td>
  </tr>
</table>
</div>
	<div class="new_mainBody">

    <fieldset>
    <fieldset class="fieldset">
    <legend><strong>Modification Summmay for Editable VA Data Fields</strong></legend>
    <div>
        <p>
        Note: Clicking the "Accept" button will immediately save the confirmed changes to the local database.
        </p>
        </div>
        <div align="center">
        <p><strong>Modifications for Product: Amlodipine-Benazepril 2.5-10 mg CAP</strong></p>
        </div>
                        <div class="resultsList">
						<%
                        String fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_Amlodipine_2_5_10_mg.csv";
                        URL source1 = new URL(fileName1);
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                String line = "";
                                int row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
                  
                  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<div align="center">
        <p><strong>Modifications for Product: Amlodipine-Benazepril 5-40 mg CAP</strong></p>
         </div
                  ><div class="resultsList">
						<%
                        fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_Amlodipine_5_40_mg.csv";
                        source1 = new URL(fileName1);
                        br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                  <div align="center">
        <p><strong>Modifications for Product: Amlodipine-Benazepril 10-20 mg CAP</strong></p>
         </div><div class="resultsList">
         <%
					                        fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_Amlodipine_10_20_mg.csv";
                        source1 = new URL(fileName1);
                        br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                  <div align="center">
        <p><strong>Modifications for Product: Amlodipine-Benazepril 10-40 mg CAP</strong></p>
         </div><div class="resultsList">
         <%
					                        fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_Amlodipine_10_20_mg.csv";
                        source1 = new URL(fileName1);
                        br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
                  <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                  <div align="center">
        <p><strong>Modifications for Product: Metoprolol Tartarate 50 mg TAB</strong></p>
         </div><div class="resultsList">
         <%
					                        fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_metoprolol_mg.csv";
                        source1 = new URL(fileName1);
                        br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/>
                  <div align="center">
        <p><strong>Modifications for Product: Metoprolol Tartarate 100 mg TAB</strong></p>
         </div><div class="resultsList">
         <%
					                        fileName1 = "http://localhost:8080/PRE/data/requestModification/editableRequestModificationDetail_MP_metoprolol_mg.csv";
                        source1 = new URL(fileName1);
                        br1 = new BufferedReader(new InputStreamReader(source1.openStream()));
                        
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab1TableColumns.length; i++) {
                                    String col = tab1TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br1.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br1.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
                                        
                                        if (col==4) {
                                        %>
                                         <td width="15%"><input name="input1" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if (col==5) {
										%>
										<td align="center"><input name="makeEditable1" type="checkbox" value="" /></td>
                                        
                                        
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br1.close();
                            %>
                        </table>
                  </div resultsList>
                  </fieldset>
                  <br/><br/><br/><br/>
                  <fieldset class="fieldset">
                  <legend><strong>Modification Request Summary for Non-Editable VA Data Fields</strong></legend>
                  <div>
        <p>
        Note: Clicking the "Accept" button will not save non-editable changes to the local database; it will send the modification request(s) to National for review.
        </p>
        <div align="center">
        <p><strong>Modifications for Product: Metoprolol Tartarate 50 mg TAB</strong></p>
        </div>
                    <div class="resultsList">
						<%
                        String fileName = "http://localhost:8080/PRE/data/requestModification/RequestModificationDetail_MultipleProduct_Metoprolol_50mg.csv";
                        URL source = new URL(fileName);
                        BufferedReader br = new BufferedReader(new InputStreamReader(source.openStream()));
                        
                        String s = "";
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab2TableColumns.length; i++) {
                                    String col = tab2TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
									int num=0;
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
										try{
										num=Integer.parseInt(t);
										}
										catch(Exception e){
										}
                                        if((col==2)||(col==3)) {
										{if(num>0){
										%>
                                        <td align="right" width="10%"><%=t%></td>
										<%
										}
										else {
										%>
                                        <td align="left" width="10%"><%=t%></td>
                                        <%
										}
										}
										}
                                        else if (col==4) {
                                        %>
                                        <td><input name="input" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if ((col==5)||(col==6)) {
                                        %>
                                        <td align="center"><input name="makeEditable" type="checkbox" value="" /></td>
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br.close();
                            %>
                        </table>
                  </div resultsList>
                  
                  <br/><br/><br/><br/><br/>
                  <div align="center">
        <p><strong>Modifications for Product: Metoprolol Tartarate 100 mg TAB</strong></p>
        </div>
                    <div class="resultsList">
						<%
                        fileName = "http://localhost:8080/PRE/data/requestModification/RequestModificationDetail_MultipleProduct_Metoprolol_100mg.csv";
                        source = new URL(fileName);
                        br = new BufferedReader(new InputStreamReader(source.openStream()));
                        
                        s = "";
                    	%>
                        <table border="1" cellpadding="2" cellspacing="0" bordercolor="#000000" RULES="COLS" FRAME="box" >
                            <tr>
                                <% 
                                for (int i=0; i<tab2TableColumns.length; i++) {
                                    String col = tab2TableColumns[i];
                                    %>
                                        <th><%=col%></th>
                                    <%
                                }
                                %>
                            </tr>
                            <%
                                br.readLine(); //first line of file has no data values
                                line = "";
                                row=0;
                                while ((line = br.readLine()) != null)
                                {
                                    row=row+1;
                                    StringTokenizer token = new StringTokenizer(line, ",");
                                    String rowColor = "#FFFFFF";
                                    if (row%2 == 0) {
                                        rowColor="#DeeFFF";
                                    }
                                %>
                                <tr bgcolor=<%=rowColor%>>
                                    <% 
                                    String t="";
									int num=0;
                                    int col=0;
                                    while (token.hasMoreTokens()) {
                                        col=col+1;
                                        
                                        t=token.nextToken();
										try{
										num=Integer.parseInt(t);
										}
										catch(Exception e){
										}
                                        if((col==2)||(col==3)) {
										{if(num>0){
										%>
                                        <td align="right" width="10%"><%=t%></td>
										<%
										}
										else {
										%>
                                        <td align="left" width="10%"><%=t%></td>
                                        <%
										}
										}
										}
                                        else if (col==4) {
                                        %>
                                        <td><input name="input" type="text" size="50" value="<%=t%>" /></td>
                                        <%
                                        } else if ((col==5)||(col==6)) {
                                        %>
                                        <td align="center"><input name="makeEditable" type="checkbox" value="" /></td>
                                        <%
                                        } else {
                                        %>
                                  <td><%=t%></td>
                                    <%
                                        }
                                    }
                                    %>
                              </tr>
                            <%
                                }
                                br.close();
                            %>
                        </table>
                  </div resultsList>
                  </fieldset>
                  </fieldset highestlevel>
                  
                   <div class="button">
        <input name="Accept" type="button" value="Accept" class="buttonCenter"/>	
            <input name="Cancel" type="button" value="Cancel" class="adjbuttonCenter"/>
        </div bottomRight>
        
    </div mainBody>
</form>

</body>
</html>