/**
 * Copyright 2007, Southwest Research Institute
 *
 * editable.js is used to hold JavaScript functions to manipulate
 * the editable link at local and national.
 */

// Global attribute to hold original innerHTML for dynamic tables (MultitextDataField and GroupListDataField)
var originalInnerHtml = new Object();

/**	
 * Toggles a component's disabled attribute and sets the associated
 * requestToEdit hidden field to post the state back to the 
 * server. 
 *
 * @param fieldKey String value of FieldKey for component, used as the HTML component's ID 
 */
function toggle(fieldKey) {
	var controlGroup = document.getElementById(fieldKey + ".controlGroup");
	var requestToEdit = document.getElementById(fieldKey + ".requestToEdit");
	
	// if the control group is in the "map", we're re-disabling, otherwise we're disabling
	if (originalInnerHtml[fieldKey]) {	
		controlGroup.innerHTML = originalInnerHtml[fieldKey];
		originalInnerHtml[fieldKey] = null;
		
		if (requestToEdit != null) {
			requestToEdit.value = "false";
		}
	}
	else { // store the innerHTML in the "map" and enable the fields
		originalInnerHtml[fieldKey] = controlGroup.innerHTML;
		
		if (requestToEdit != null) {
			requestToEdit.value = "true";
		}
		
		var controlElements = controlGroup.getElementsByTagName("*");

		// We assume that all IDs of fields grouped by any DataField contain its key.
		// We also ignore any non-input fields and the requestToEdit field (which was reset above).
		for (var i = 0; i < controlElements.length; i++) {
			if (controlElements[i].id.indexOf(fieldKey) != -1 
				&& controlElements[i].id.indexOf(".requestToEdit") == -1
				&& controlElements[i].id.indexOf("control") == -1) {
				
				controlElements[i].disabled = false;
			}
		}
	}
}

function swapImage(fieldKey) {
	return swapImage(fieldKey, '');
}
/**	
 * Swaps the editable/nonEditable image and toggles the value of the 
 * associated hidden field to post the state back to the 
 * server. 
 *
 * @param fieldKey String value of FieldKey for a component.
 */
function swapImage(fieldKey, imagesLocation) {
	var editable = window.document.getElementById(fieldKey + ".editable");
	var img = window.document.getElementById(fieldKey + ".editable.image");
	
	if (editable.value == "true") {
		editable.value = "false";
		img.src = imagesLocation + "images/nonEditable.gif";
		img.alt = "locked";
		img.title = window.document.getElementById("click.to.unlock").innerHTML;
	}
	else {
		editable.value = "true";
		img.src = imagesLocation + "images/editable.gif";
		img.alt = "unlocked";
		img.title = window.document.getElementById("click.to.lock").innerHTML;
	}
}