/**
 * Copyright 20012, Southwest Research Institute
 *
 * Uncheck the All Requests checkbox when others are checked, and 
 * uncheck all others when All Requests is rechecked.
 * 
 */


/**	
 * Read each form on the page. 
 * 
 */

function checkAllRequests(id) {

	for (var i = 0; i < document.forms.length; i++) {
		genfilledin(id, document.forms[i]);
	}
		
}

/**
 * Uncheck the All Requests checkbox when others are checked, and 
 * uncheck all others when All Requests is rechecked.
 * 
 */
function genfilledin(id, form) {
		
	if (id == "all.requests" && document.getElementById(id).checked) {

		for (var x=0; x< form.elements.length; x++) {
			if (form.elements[x].name != "allRequests") {
				form.elements[x].checked = false;
			}
		}
		
	} 
		
	if ((id != "all.requests") && (document.getElementById(id).checked)) {
			document.getElementById("all.requests").checked = false;
	}

}