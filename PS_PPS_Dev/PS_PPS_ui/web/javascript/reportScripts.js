	function downloadCSV() {
		window.location = '/PRE/reports.download.go';
	}

	function generateCSV(url) {
		alert("A new report will be generated. \nThis process will take 3 - 20 minutes.");
//		setTimeout("location.reload(true);", 200);
		document.performReportsSearchOption.action = url;
        document.performReportsSearchOption.submit();
//		window.location = '/PRE/reports.generate.go';	
//		setTimeout("location.href = '/PRE/reports.update.go';", 500);		
	}
	function runReport(url) {
		document.performReportsSearchOption.action = url;
        document.performReportsSearchOption.submit();
	}
