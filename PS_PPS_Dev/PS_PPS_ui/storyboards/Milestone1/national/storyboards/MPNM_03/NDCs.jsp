<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*, java.net.*, java.util.*"%>

<%!
	private String[] tableColumns = {"NDC", "Trade Name", "Pack Size", "Pack Type", "Unit Price", "Manufacturer", "Image"};
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/NDC.css" rel="stylesheet" type="text/css" />
<title>Untitled Document</title>
</head>

<body>
	<div id="ndcList" class="ndcList">
		<div class="searchResults">
            <div class="right">
                Page Count: 
                <select name="pgCount">
                    <option>1 of 1</option>
                </select>
                Page Lines:
                <select name="pgCount">
                    <option>20</option>
                </select>
	        </div right>
        </div searchResults>
		<jsp:include page="ndcTable.jsp"/>
        <br/><br/>
        </fieldset>
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    </div ndcList>
</body>
</html>
