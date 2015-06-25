/**
 * Copyright 2007, Southwest Research Institute
 *
 * formDiff.js is used to hold JavaScript functions to check 
 * for changes in forms within the current document.
 */


/**
 * Search for changes in forms found within the current document.
 *
 * If a form was submitted, there (should) be a change anyway.
 * In this case the method returns no message so that no pop-up
 * is displayed -- hopefully because the form submit was deliberate. 
 *
 * @return message to display if there are changes, otherwise null
 */
function checkForChanges() {
	
	// Use the submitButton global attribute from disableSubmit.js to know if a form is submitting.
	// If one is, ignore checking for changes, since the page has to post anyway to submit the form.
	if (submitButton) {
		return;
	}

	var dirtyFlag = window.document.getElementById("dirty.flag");
	var changed = false;
		
	if (dirtyFlag) {
		changed = dirtyFlag.innerHTML == "true" ? true : false;
	}
	
	for (var i = 0; i < window.document.forms.length && !changed; i++) {
		changed = formChanged(window.document.forms[i]);
	}
	
	var multiSelectFlag = window.document.getElementById("multi.select.flag");
	var multi = false;
		
	if (multiSelectFlag) {
		multi = multiSelectFlag.innerHTML == "true" ? true : false;
	}
	
	var partialSave = window.document.getElementById("partial.save.flag");
	var partialSaveFlag = false;
	
	if (partialSave) {
		partialSaveFlag = partialSave.innerHTML == "true" ? true : false;
	}

	var message;

	if (changed) {
		message = window.document.getElementById("page.changed").innerHTML;
	}

	if (multi) {
		message = window.document.getElementById("multi.changed").innerHTML;
	}

	if (partialSaveFlag) {
		message = window.document.getElementById("page.partialsave.changed").innerHTML;
	}

	return message;	
}

/**	
 * Search for changes in forms found within the current document. 
 *
 * @return boolean true if no changes are found, allowing to redirect the page to the given URL, else false
 */
function promptForChanges() {
	var message = checkForChanges();
	var shouldRedirect = true;
	
	if (message) {
		shouldRedirect = confirm(message);
	}

	if (shouldRedirect) {
		if (window.event) {
   			window.event.returnValue = true;
  		}     
  		
		return true;
	}
	else {
		if (window.event) {
			window.event.returnValue = false;
		}
		
		return false;
	}
} 

/**
 * Test if the form with the given ID has changed since it was loaded.
 *
 * If the form has changed, submit it before moving to the next tab.
 *
 * This method assumes that the anchor's href attribute intcludes a "hash" that signifies the 
 * tab element ID to display.
 *
 * @param anchor tag that makes this call
 * @param formId HTML ID for the form to test and submit
 */
function submitOnFormChange(formId, tab) {
	var form = window.document.getElementById(formId);
	
	var input = window.document.getElementById("post_tab");
	
	if(input == null || input == undefined) {		
		input = window.document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "post_tab");
		input.setAttribute("id", "post_tab");
		form.appendChild(input);
	}
	
	input.setAttribute("value", tab);
	
	
	if (form.onsubmit()) {
		
		// setting global variable from disableSubmit.js to prevent redirect confirmation pop-up from displaying
		submitButton = formId;
		
		form.submit();
	}
}

/**
 * Check if the given form has an element within it that has a changed value.
 * Return true for the first element found to be different; do not check the whole form.
 *
 * @param form Form DOM to check for changes in
 * @return boolean true if there was an element with a change, else false
 */
function formChanged(form) {
	var changed = false;
	
	for (var i = 0; i < form.elements.length && !changed; i++) {
		changed = elementChanged(form.elements[i]);
	}
	
	return changed;
}

/**
 * Check if the given element's value has changed since the page was loaded.
 * 
 * @param element Element DOM to check for changes in
 * @return boolean true if there was a change in the given element, else false
 */
function elementChanged(element) {
	var changed = false;
	
	if (element.type == 'checkbox' || element.type == 'radio') {
		changed = element.checked != element.defaultChecked;
	}
	else if (element.type == 'file' || element.type == 'password' || element.type == 'text' || element.type == 'textarea') {
		changed = element.value != element.defaultValue;
	}
	else if (element.type == 'select-one' || element.type == 'select-multiple') {
		var hasDefault = false;
		
		for (var i = 0; i < element.options.length && !hasDefault; i++) {
			hasDefault = element.options[i].defaultSelected;
		}
			
		if (hasDefault) {
			for (var i = 0; i < element.options.length && !changed; i++) {
				changed = element.options[i].selected != element.options[i].defaultSelected;
			}
		}
		else {
			if (element.type == 'select-one') {
				changed = !element.options[0].selected;
			}
			else {
				for (var i = 0; i < element.options.length && !changed; i++) {
					changed = element.options[i].selected;
				}
			}
		}
	}
	
	return changed;
}