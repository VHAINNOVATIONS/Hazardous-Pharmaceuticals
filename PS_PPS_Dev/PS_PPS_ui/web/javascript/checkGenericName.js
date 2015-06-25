/**
 * Copyright 2008, Southwest Research Institute
 *
 * Check to see that Generic Name and Dosage Form are selected before continuing
 * to generate the OI Names (with generate button)
 */


/**	
 * Read the items on the form. 
 * If changed = false (required fields are empty), do not allow the button action to complete.
 */

function checkGenericName() {

	for (var i = 0; i < document.forms.length; i++) {
		filledin = genfilledin(document.forms[i]);
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
 * Check if Generic Name or Dosage Form are empty.
 * If either field is empty, pop a warning window. 
 * Warning states that values must be select to use the generate button.
 */
function genfilledin(form) {
	var filledin = false;
	var message = "You must select a";
	var messagebody = "";
	var messagebody1 = "";
	var messagetail = " to use this button";
	count = 0;
	
	for (var i = 0; i < form.elements.length && !filledin; i++) {

		if ((form.elements[i].name == "genericName") && (form.elements[i].value == "")) {
			messagebody = " Generic Name ";
			count++;
		}
		else if ((form.elements[i].name == "dosageForm") && (form.elements[i].value === "")) {
			count++;
			if (count == 1) {
				messagebody1 = " Dosage Form ";
			}
			else {
				messagebody1 = "and Dosage Form ";
			}
		}
	} // end for loop
	
	if (count > 0) {
		alert(message + messagebody + messagebody1 + messagetail);
	}
	else {
		filledin = true;
	}
	
	return filledin;
}