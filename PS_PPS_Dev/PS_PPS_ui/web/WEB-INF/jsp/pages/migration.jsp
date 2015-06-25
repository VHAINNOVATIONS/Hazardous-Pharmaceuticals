<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib
    prefix="tiles"
    uri="http://tiles.apache.org/tags-tiles"%>
<html>
<head>
<title>Migration Home Page</title>
<script
    src="/PRE/javascript/progressBar.js"
    type="text/javascript"></script>
    
<script type="text/javascript">
	var t;
	var processRunning = "<c:out value="${migrationState.running}"/>";
	var timer_is_on=0;
	
	function timedCount()
	{
		t=setTimeout("timedCount()",1000);
		document.migrationForm.submit();
	}
	function doTimer()
	{
		if (!timer_is_on)
		{
		 	timer_is_on=1;
			timedCount();
		}
	}
	//doTimer();
</script>

</head>
<body >
	<h1>Migration Home Page</h1>
	<form name="migrationForm" enctype="multipart/form-data" action="uploadCSVFiles.go" method="post">
		<table>
			<tr>
				<td>
					<ol>
						<li> Menu</li>
						<li><a href="startLouMigration.go">Start Migration</a></li> 
						<li><a href="stopLouMigration.go">Stop Migration</a></li>
						<li><a href="pauseLouMigration.go">Pause Migration</a></li>
						<li><a href="resumeLouMigration.go">Resume Migration</a></li>
						<li><a href="statusLouMigration.go">Migration Status (ajax)</a></li>
						<li><a href="getMigrationState.go">get Migration State</a></li>
					</ol>
				</td> 
			</tr>
			<tr> 				
				<td> 
					NDC File*: <input type="file" name="ndcFile" size="10"/>
				</td>
			</tr>
				
			<tr> 
				<td> 
					Orderable Item File: <input type="file" name="oiFile" size="10"/>
				</td>
			</tr>
			<tr>
				<td> 
					Product File: <input type="file" name="productFile" size="10"/> 
				</td>
			</tr>	
		 	<tr>
				<td>
 
					<div id="results">
						<tiles:insertTemplate template="/WEB-INF/jsp/pages/migration.frag.jsp"/>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<ol>
						<hr>
						<li>NDC file name: <c:out value="${model.file1.name}"/></li>
						<li>NDC file full name: <c:out value="${model.file1.originalFilename}"/></li>
						<li>NDC file upload size: <c:out value="${model.file1.size}"/></li>
						<li>NDC file Type: <c:out value="${model.file1.contentType}"/></li>
						<hr>
						<li>OI file upload status: <c:out value="${model.file2.name}"/></li>
						<li>OI file full name: <c:out value="${model.file2.originalFilename}"/></li>
						<li>OI file upload size: <c:out value="${model.file2.size}"/></li>
						<li>OI file Type: <c:out value="${model.file2.contentType}"/></li>
						<hr>
						<li>Product file upload status: <c:out value="${model.file3.name}"/></li>
						<li>Product file full name: <c:out value="${model.file3.originalFilename}"/></li>
						<li>Product file upload size: <c:out value="${model.file3.size}"/></li>
						<li>Product file Type: <c:out value="${model.file3.contentType}"/></li>
						<hr>
						<li>param3: <c:out value="${model.param3}"/></li>
						<li>errors: <c:out value="${error.message}"/></li>
					</ol>
				</td>
			</tr> 
			
		</table>
		<input type="submit" value="Submit" />
	</form>
</body>
</html>
 
