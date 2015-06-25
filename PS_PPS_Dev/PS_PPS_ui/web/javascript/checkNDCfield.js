/**
 * Copyright 2011, Southwest Research Institute
 *
 * Check to see that the user has selected an NDC file that is required for migration
 *
 */


/**	
 * Read the NDC field on the form. 
 * If filledin = false (required NDC field is empty), do not allow the button action to complete.
 */

function checkNDCfield() {

	for (var i = 0; i < document.forms.length; i++) {
		filledin = ingfilledin(document.forms[i]);
	}
	
	var shouldNotRedirect = false;
	
	if (!filledin) {
		shouldNotRedirect = true;
	}
	
	if (shouldNotRedirect) {
		window.event.returnValue = false;
	}
	else {
		window.event.returnValue = true;
	}
}

/**
 * Check if NDC field is empty.  If it is, pop a warning window.
 * Warning states that a file must be selected to perform migration.
 */
function ingfilledin(form) {
	var filledin = false;
	var migrationmessage = "You must select an NDC file to perform a migration";
	var fieldname;
	count = 0;
	for (var i = 0; i < form.elements.length; i++) {
		//alert("file name = " + form.elements[i].name + " value= " + form.elements[i].value );
		if ((form.elements[i].name == "ndcFile") && (form.elements[i].value == "")) {
			count++;
		}
	} // end for loop
	
	if (count > 0) {
		alert(migrationmessage);
	}
	else {
		filledin = true;
	}
	
	return filledin;
}