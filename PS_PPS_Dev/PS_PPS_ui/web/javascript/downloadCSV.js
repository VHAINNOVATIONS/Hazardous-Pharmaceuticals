/**
 * Copyright 2011, Southwest Research Institute
 *
 * Alert the user that they should download CSV files before migration 
 *
 */


/**	
 * Alert the user
 */

function downloadCSV() {

	if(window.event.returnValue==true){
		var choice = confirm("             Starting Migration will reset the database.\n\n"  + "If you have not downloaded the CSV files click cancel, otherwise click ok.");
		if(choice==false){
			window.event.returnValue = false;
		}
		window.navigate('/PRE/startMigration.go');
	}
	
}