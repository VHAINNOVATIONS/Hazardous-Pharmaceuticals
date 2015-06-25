/**
 * Copyright 2008, Southwest Research Institute
 *
 * parameterizeSubmit.js is used to hold JavaScript functions to change the action
 * attribute of a form to the supplied URL containing parameters.
 */


/**
 * Submit the form after changing the form's action to the given URL.
 * 
 * @param event String 
 * @param formElementName the name of the form element the button is associated with
 * @param form optional parameter of form to submit. If not supplied, the form is taken from the window's current event.
 */
function parameterizeSubmit(event, domainEntityType, formElementName, form) {
	if (form == null) {
		if (window.event.target) { 
			form = window.event.target.form;
		}
		else if (window.event.srcElement) {
			form = window.event.srcElement.form;
		}
	}
	
	var domainEntityTypeInput = document.createElement("input");
	 
    //Assign different attributes to the element.
	domainEntityTypeInput.setAttribute("type", "hidden");
	domainEntityTypeInput.setAttribute("value", domainEntityType);
	domainEntityTypeInput.setAttribute("name", "domainEntityType");
	
	form.appendChild(domainEntityTypeInput);
	
	var formElementInput = document.createElement("input");
	 
    //Assign different attributes to the element.
	formElementInput.setAttribute("type", "hidden");
	formElementInput.setAttribute("value", formElementName);
	formElementInput.setAttribute("name", "formElementName");
	
	form.appendChild(formElementInput);
	
	var url = form.action;
	
	// Need to handle the case of adding from an add from an add (e.g. Product->Drug Class->Parent Drug Class)
	if(url.indexOf("addManagedDomain.go") != -1) {
		var lastPosOfSlash = url.lastIndexOf('/');
		url = url.substring(0, lastPosOfSlash+1) + "add/" +event;
	} else {
		var lastPosOfDot = url.lastIndexOf('.');
		url = url.substring(0, lastPosOfDot) + "/" + event;
	}
	
	form.action = url;	
	
	if (form.onsubmit()) {
		form.submit();
	}
}