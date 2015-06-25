/**
 * Copyright 2008, Southwest Research Institute
 *
 * Methods called when the page loads/unloads. 
 */


/**
 * Call JavaScript functions when a page loads.
 *
 * Sets the session timeout, resets the width of drop-down boxes, and initialize the menu component.
 */
function onLoad() {
	deleteCookiesOnLogin();
	setSessionTimeoutAlert();
	selectMinWidth();
	initMenu();
	enableButtons(window.document.getElementsByTagName("input"));
	enableButtons(window.document.getElementsByTagName("button"));
	
	snapFocusToErrorList();
}
 
/**
 * Call JavaScript functions before the page begins to unload.
 *
 * These functions can return a message for the browser to display. The user has an option then to cancel the unload event.
 */
function onBeforeUnload() {
	var popupsMessage = checkForPopups();
	var changesMessage;
	
	if (typeof checkForChanges =='function')
	{
		changesMessage = checkForChanges();
	}
	var message;
	
	if (popupsMessage && changesMessage) {
		message = popupsMessage + " " + changesMessage;
	}
	else if (popupsMessage) {
		message = popupsMessage;
	}
	else if (changesMessage) {
		message = changesMessage;
	}
	
	return message;
}

/**
 * Call JavaScript functions when a page unloads.
 *
 * Clear any timeouts or intervals previously set.
 */
function onUnload() {
	clearTimeouts();
	clearIntervals();
	closeAllPopups();
}

/**
 * Snaps the focus to the error list if there happen to be any errors on the page.
 */
function snapFocusToErrorList(){
	var element = document.getElementById("errorAnchor");
	
	if(element !=null || element != undefined) {
		element.focus();
	}
}