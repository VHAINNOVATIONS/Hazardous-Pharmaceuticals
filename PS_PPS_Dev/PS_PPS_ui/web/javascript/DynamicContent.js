/**
 * Copyright 2007, Southwest Research Institute
 *
 * DynamicContent.js is used to hold JavaScript functions to manipulate
 * page elements and form components.
 */

/* Dynamically creates a component. */
function createComponent() {

	var searchField = document.getElementById('searchFields').value;
	var key = searchField.getFieldKey().getKey();
	document.getElementById('searchValues').setAttribute("value", key);
}

/* Indicates that the search has changed */
function changed() {
	//document.getElementById('templateName').value = "";
	//document.getElementById('templateNotes').value = "";
	;//document.getElementById('changed').value = "true";
}

/**
 * If the given ID is found, selects all the options in a multi-select control.
 *
 * @param selectId HTML ID of select box to find
 */
function selectAllTheOptions(selectId) {
  var select = document.getElementById(selectId);
  
  if (select) {
  	for (var i = 0; i < select.options.length; i++) {
    	select.options[i].selected = true;
 	 }
  }
}