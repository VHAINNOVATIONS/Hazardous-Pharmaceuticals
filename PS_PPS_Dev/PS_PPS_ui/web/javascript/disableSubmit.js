/**
 * Copyright 2008, Southwest Research Institute
 *
 * Disable all buttons on the page when a form is submitted. 
 */

var submitButton; // set by peps:submit onclick event!

/**
 * Disable all buttons on the page when a form is submitted.
 *
 * Since disabling all buttons includes disabling the one that was clicked, add a hidden input field for that button
 * such that it's name/value pair still gets posted back. 
 */
function disableSubmit(formId) {
	// disable all submit buttons
	disableButtons(window.document.getElementsByTagName("input"));
	disableButtons(window.document.getElementsByTagName("button"));
	
	// set the form action the same as the button name if it exists
	if (submitButton && formId && submitButton.indexOf(':') >= 0) {
		
		var result = submitButton.split(':');
		
		var method = result[0].toUpperCase();
		if(method == 'POST' || method == 'GET') {
			window.document.getElementById(formId).method = method;
		}
		
		var action = result[1];
		if(result[1].length > 0) {
			window.document.getElementById(formId).action = action;
		}
	}
	
	// change the cursor to an hourglass
	window.document.body.style.cursor = "wait";
	
	return true;
}

/**
 * Disable all submit, button, and reset fields in the given buttons array.
 *
 * @param buttons input field element array
 */
function disableButtons(buttons) {
	if (buttons) {
		for (var i = 0; i < buttons.length; i++) {
			if (buttons[i].type == "submit" || buttons[i].type == "button" || buttons[i].type == "reset" || buttons[i].type == "image") {
				buttons[i].disabled = true;
			}
		}
	}
}


/**
 * Enable all submit, button, and reset fields in the given buttons array.
 *
 * @param buttons input field element array
 */
function enableButtons(buttons) {
	if (buttons) {
		for (var i = 0; i < buttons.length; i++) {
			if ((buttons[i].type == "submit" || buttons[i].type == "button" || buttons[i].type == "reset" || buttons[i].type == "image") && (buttons[i].className != 'ignoreEnable')) {
                buttons[i].disabled = false;
            }
		}
	}
}


	