/**
 * Copyright 2008, Southwest Research Institute
 *
 * Open JavaScript pop-up windows.
 */
 
var POP_UPS = new Array(0);

/**
 * Close all open pop-ups stored in the POP_UPS array.
 *
 * @param confirm (optional) boolean value stating if a confirmation dialog box should appear before closing all pop-ups. Defaults to false.
 */
function closeAllPopups(confirm) {
	cleanupPopUps();
	var length = POP_UPS.length;

	for (var i = 0; i < length; i++) {	
		var popup = POP_UPS.pop();
		
		if (popup && !popup.closed) {
			popup.close();
		}
	}
}

/**
 * Return true if the JavaScript is currently executing inside of a pop-up window. (i.e., it has a window.opener property)
 */
function isPopup() {
	if (window.opener) {
		return true;
	}
	else {
		return false;
	}
}

/**
 * Prompt if there are any pop-ups still open.
 */
function checkForPopups() {
	cleanupPopUps();
	
	if (POP_UPS.length != 0) {
		return window.document.getElementById("confirm.close.popups").innerHTML;
	}
}

/**
 * Reset the POP_UPS Array so that it only contains open pop-ups. It may contain closed pop-ups if a used manually closed
 * a pop-up window.
 */
function cleanupPopUps() {
	var popups = POP_UPS;
	POP_UPS = new Array(0);
	
	for (var i = 0; i < popups.length; i++) {	
		if (popups[i] && !popups[i].closed) {
			POP_UPS.push(popups[i]);
		}
	}
}

function removePopup(popup) {
	for (var i = 0; i < POP_UPS.length; i++) {
		if (POP_UPS[i] == popup) {
			POP_UPS[i] = null;
		}
	}
}

/**
 * Open a pop-up window at the given URL. Set the new window's name, height, and width. 
 * All other attributes for the window are defaulted by JavaScipt.
 *
 * @param url String URL to open in pop-up
 * @param name String unique name to give the new window NOTE: unless the same window is shared across browsers/tabs, this name must be unique
 * @param height int height of the new window
 * @param width int width of the new window
 * @param top int distance from the top of the browser 
 * @param left int distance from the left of the browser 
 */
function popup(url, name, height, width, top, left) {
	return popupWithOptions(url, name, 'height=' + height + ',width=' + width + ',top=' + top + ',left=' + left + ',resizable=yes,location=no,menubar=no,toolbar=no,status=no');
}

/**
 * Open a pop-up window at the URL with the given name and options.
 *
 * @param url String URL to open in pop-up
 * @param name String unique name to give the new window NOTE: unless the same window is shared across browsers/tabs, this name must be unique
 * @param options options to pass to the window.open call controlling the size, etc. of the new window
 */
function popupWithOptions(url, name, options) {
	var popup;
	
	if (options) {
		popup = window.open(url, name, options);
	}
	else {
		popup = window.open(url, name);
	}
	
	if (window.focus) {
		popup.focus();
	}
	
	POP_UPS.push(popup);
	
	return false;
}

/**
 * Resize the pop-up to fit the content.
 */
function resizePopup() {
	resizePopupWidth();
	resizePopupHeight();
}

/**
 * Determine the width of the body content in the pop-up. If it is wider or narrower than the pop-up window itself, resize
 * the pop-up to fit the content.
 */
function resizePopupWidth() {
	window.self.resizeBy(window.self.document.documentElement.scrollWidth - window.self.document.documentElement.clientWidth, 0);
}

/**
 * Determine the height of the body content in the pop-up. If it is taller or shorter than the pop-up window itself, resize
 * the pop-up to fit the content.
 */
function resizePopupHeight() {	
	window.self.resizeBy(0, window.self.document.documentElement.scrollHeight - window.self.document.documentElement.clientHeight);
}

/**
 * Open a pop-up window in which to edit a single data field. This method is called by the read-only managed item edit
 * page by links whose IDs are the FieldKey to edit.
 *
 * @param url URL to open in the pop-up window
 * @param fieldKey FieldKey string to use as the pop-up window's name
 * @param height int height of the new window
 * @param width int width of the new window
 * @param top int distance from the top of the browser 
 * @param left int distance from the left of the browser 
 *
 * @see popup(url, name, height, width, top, left)
 */
function popupField(url, fieldKey, height, width, top, left) {	
	cleanupPopUps();
	
	if (POP_UPS.length != 0 && !POP_UPS[0].closed) {	
		var message = window.document.getElementById("popup.already.open").innerHTML;
	
		if (window.confirm(message)) {		
			closeAllPopups();
		}
		else {
			return false;
		}		
	}

	var t = top;
	var l = left;
	if (top == null || top == "") {
		t = 350;
		}
	if (left == null || left == "") {
		l = 200;
		}

	return popup(url, toWindowName(fieldKey), 250, 500, t, l);
}

/**
 * Convert the given FieldKey into a window name. Removes "." charaters and replaces them with "_".
 *
 * Makes the window name unique by suffixing the FieldKey with the current time in milliseconds.
 *
 * @param fieldKey FieldKey string to convert
 *
 * @see toFieldKey(windowName)
 */
function toWindowName(fieldKey) {
	var name = fieldKey;

	while (name.indexOf("\.") > -1) {
		name = name.replace("\.", "_");
	}
	
	name = name + "__" + new Date().getTime();
	
	return name;
}

/**
 * Convert the given window name into a FieldKey. Removes "_" characters and replaces them with ".".
 *
 * @param windowName pop-up window name string to convert
 *
 * @see toWindowName(fieldKey)
 */
function toFieldKey(windowName) {
	var name = windowName.split("__")[0];

	while (name.indexOf("_") > -1) {
		name = name.replace("_", "\.");
	}
	
	return name;
}

/**
 * Add the pop-up's form to the opener's DOM in a hidden div and then submit it. 
 * 
 * @param formId Form to submit
 * @param submitId ID of submit button to click
 */
function submitPopup(formId, submitId) {
	var popupForm = window.self.document.getElementById(formId);
	var href = window.opener.location.href;
	
	// find the current active tab
	// should we get by CSS class TabbedPanelsContentGroup? (but this wouldn't be unique)
	var tabs = window.opener.document.getElementById("tabs");
	
	if (tabs != null) {
		var tabDivs = tabs.getElementsByTagName("div");
		var visibleTabId;
		
		for (var i = 0; i < tabDivs.length; i++) {
			if (tabDivs[i].className.indexOf("TabbedPanelsContentVisible") >= 0) {
				visibleTabId = tabDivs[i].id;
			}
		}
		
		var hashIndex = href.indexOf(window.opener.location.hash);
		
		if (hashIndex > 0) {
			href = href.substring(0, hashIndex);
		}
		
		href = href + "&tab=" + visibleTabId;
	}
	
	popupForm.action = href;
	
	var hiddenDiv = window.opener.document.getElementById("submitPopupForm");
	
	if (hiddenDiv == null) {
		hiddenDiv = window.opener.document.createElement("div");
		hiddenDiv.className = "hidden";
		hiddenDiv.id = "submitPopupForm";
		window.opener.document.body.appendChild(hiddenDiv);
		hiddenDiv = window.opener.document.getElementById("submitPopupForm");
	}
	
	if (isInternetExplorer()) {
		hiddenDiv.innerHTML = popupForm.outerHTML;
		resetOpenerIndices();			
		ieCopyFormElementHack('input');
		ieCopyFormElementHack('textarea');
		ieCopyFormElementHack('select');
		ieMultiSelectHack();	
	} else {
		hiddenDiv.appendChild(popupForm);
	}
	
	dirtyFlag = true;
		
	var openerFormButton = window.opener.document.getElementById(submitId);
	
	if (openerFormButton.disabled) {
		openerFormButton.disabled = false;	
	}
	
	window.opener.removePopup(window.self);
	openerFormButton.click();

	//window.opener.document.getElementById(toFieldKey(window.self.name)).focus();
	
	window.self.close();
}

/**
 * Copies the multiselect values int a hidden input because ie wont submit more than one for some reason.
 * 
 * @param oldDocument
 */
function ieMultiSelectHack() {
	var popupElements = window.self.document.getElementsByTagName('select');	
	var value = '';
	for(var i=0; i<popupElements.length; i++){
		if(popupElements[i].multiple != undefined && popupElements[i].multiple == true) {
			var popupOptions = popupElements[i].getElementsByTagName("option");
			for(var oi=0; oi<popupOptions.length; oi++){
				if(popupOptions[oi].selected == true){
					var multiselectHackHiddenInput = window.opener.document.createElement('input');
					multiselectHackHiddenInput.type = 'hidden';
					multiselectHackHiddenInput.name = popupElements[i].name;
					multiselectHackHiddenInput.value = popupOptions[oi].value;
					window.opener.document.forms['editField'].appendChild(multiselectHackHiddenInput);
				}
			}
		}
	}
}

/**
 * Copies popup form elements to the original form because IE doesn't seem to do so correctly
 */
function ieCopyFormElementHack(type){
	var popupElements = window.self.document.getElementsByTagName(type);
	var openerElements = window.opener.document.getElementsByTagName(type);
	for(var i=0; i<popupElements.length; i++){
		for(var x=0; x<openerElements.length; x++){
			if(openerElements[x].name == popupElements[i].name && openerElements[x].id == popupElements[i].id){
				if(type == 'input' && popupElements[i].type == 'checkbox') {
					if(popupElements[i].checked != "") {
						openerElements[x].checked = 'checked';
					} else {
						openerElements[x].checked = '';
					}
				}
				if(popupElements[i].multiple == undefined || popupElements[i].multiple == false) {
					openerElements[x].value = popupElements[i].value;
				} else {
					openerElements[x].removeNode(true);
				}
				break;
			}
		}
	}
}

/**
 * Return true if this window currently has an 'opener'. In other words if (window.opener) would return true.
 */
function isPopup() {
	return window.opener ? true : false;
}