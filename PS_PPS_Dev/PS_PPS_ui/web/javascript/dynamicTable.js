/**
 * Copyright 2007, Southwest Research Institute
 *
 * dynamicTable.js is used to hold JavaScript functions to add and remove
 * rows from a table.
 */

var TABLE_ID;
var INDEXABLE_OGNL;


/**	
 * Remove a row from a table. 
 */
function removeRow(obj, indexableOgnl, removedRowsId, removedIndex) {
	var table = getTable(obj);	
	var tableIndex = getRowNumber(obj);
	var collectionIndex = tableIndex - 1;
			
	var oldRemoveRowValue = window.document.getElementById(removedRowsId).value;
	
	if (oldRemoveRowValue.length == 0) { 
		window.document.getElementById(removedRowsId).value = indexableOgnl + '_' + removedIndex;
	}
	else {
		window.document.getElementById(removedRowsId).value = oldRemoveRowValue + "," + indexableOgnl + '_' + removedIndex;		
	}
	
	table.deleteRow(tableIndex);
	
	resetRowCss(table);
	resetIndices(table, indexableOgnl);
	
	// IE has problems submitting the table without having the wrong indices, so save off the table's ID and OGNL to
	// reset them again just before submitting the form
	if (isInternetExplorer()) {
		TABLE_ID = table.id;
		INDEXABLE_OGNL = indexableOgnl;
	}
	
	if (isPopup()) {
		resizePopupHeight();
	}
	
	// if a row is removed and we're using IE, we'll need to to set a work-around where the indices are reset on form submit
	if (isInternetExplorer()) {
		
	}
}

/**
 * Add a row to a table.
 *
 * @param obj the button clicked
 * @param indexableOgnl OGNL prefix that is directly followed by the index to set and followed by the rest of the OGNL path
 */
function addRow(obj, indexableOgnl) {
	var table = getTable(obj);
	var invisibleRow = table.rows[table.rows.length - 1];	
	var newRow = invisibleRow.cloneNode(true);
	invisibleRow.parentNode.insertBefore(newRow, invisibleRow.nextSibling);
		
	resetDisabled(invisibleRow);
	resetRowCss(table);
	resetIndices(table, indexableOgnl);
	
	resetSelectMinWidth(table);
	
	// IE has problems submitting the table without having the wrong indices, so save off the table's ID and OGNL to
	// reset them again just before submitting the form
	if (isInternetExplorer()) {
		TABLE_ID = table.id;
		INDEXABLE_OGNL = indexableOgnl;
	}
	
	if (isPopup()) {
		resizePopupHeight();
	}
	
	var element = returnFocusElement(table.rows[table.rows.length - 2]);	
	element.focus();
	
	return element;
}

/**
 * When a row is added to a table, the select boxes have an incorrect width. Reset the widths of the select boxes for the
 * new row to be equal to the widths of the previous row.
 *
 * @param table current table with row added
 */
function resetSelectMinWidth(table) {
	// The if else block wasn't working for the newly modified group datafield select boxes, so the else has been commented out.
	// This doesn't seem to have a negative impact on any other select boxes. SGW - 05/30/12
	// A table will have only three rows if the newly added row is the first row (there is a header row, a row displayed, and a hidden row)
//	if (table.rows.length == 3) {
		var visibleRow = table.rows[table.rows.length - 2];
		
		var visibleSelectBoxes = visibleRow.getElementsByTagName("select");
	 	
		for (var i = 0; i < visibleSelectBoxes.length; i++) {
			visibleSelectBoxes[i].style.width = "auto";			
		}
//	}
//	else {
//		var secondToLastVisibleRow = table.rows[table.rows.length - 3];
//		var lastVisibleRow = table.rows[table.rows.length - 2];
//		
//		var secondToLastVisibleSelectBoxes = secondToLastVisibleRow.getElementsByTagName("select");
//		var lastVisibleSelectBoxes = lastVisibleRow.getElementsByTagName("select");
//	 	
//		for (var i = 0; i < lastVisibleSelectBoxes.length; i++) {
//			lastVisibleSelectBoxes[i].style.width = secondToLastVisibleSelectBoxes[i].style.width; 		
//		}
//	}
}

/**
 * Set the new row as visible and not disabled.
 *
 * @param row New table row
 */
function resetDisabled(row) {
	row.style.visibility = "";
	row.style.display = "";

	var controlElements = row.getElementsByTagName("*");

	for (var j = 0; j < controlElements.length; j++) {
		if (controlElements[j].tagName == "INPUT" || controlElements[j].tagName == "SELECT") {
			var unauthorizedIndex = controlElements[j].className.indexOf("unauthorized");
			
			if (unauthorizedIndex == -1) {
				controlElements[j].disabled = false;
			}
		}
	}	
}

/**
 * Returns the first input or select to put focus in the newly created row
 *
 * @param row New table row
 */
function returnFocusElement(row) {
	
	var controlElements = row.getElementsByTagName("*");

	for (var j = 0; j < controlElements.length; j++) {
		var element = controlElements[j];
		if (element.tagName == "INPUT" || element.tagName == "SELECT") {
			return element;
		}
	}	
}

/**
 * Reset the CSS classes used in the table's rows.
 *
 * @param table Table with rows to reset
 */
function resetRowCss(table) {
	for (var i = 1; i < table.rows.length; i++) {
		if (i % 2 == 0) {
			table.rows[i].className = "even";
		}
		else {
		 	table.rows[i].className = "odd";
		}
	}
}

/**
 * Reset the opener window's table indices prior to submitting a the table's form from a pop-up.
 */
function resetOpenerIndices() {
	if (TABLE_ID && INDEXABLE_OGNL) {
		var table = window.opener.document.getElementById(TABLE_ID);
		window.opener.resetIndices(table, INDEXABLE_OGNL);
	}
}

/**
 * Reset the indices into the collection of groups within the input and select element name attributes.
 *
 * @param table Table with indices to reset
 * @param indexableOgnl OGNL prefix that is directly followed by the index to set and followed by the rest of the OGNL path
 */
function resetIndices(table, indexableOgnl) {
	for (var rowIndex = 1; rowIndex < table.rows.length; rowIndex++) {
		var row = table.rows[rowIndex];
		var controlElements = row.getElementsByTagName("*");

		for (var j = 0; j < controlElements.length; j++) {
			if (controlElements[j].id != "") {
				var idIndex = controlElements[j].id.lastIndexOf("_") + 1;				
				if (controlElements[j].tagName == "SPAN") {
					controlElements[j].id = controlElements[j].id.substring(0, idIndex) + (rowIndex - 1) + ".controlGroup";
				}
				else if (controlElements[j].type=="hidden") {
					controlElements[j].id = controlElements[j].id.substring(0, idIndex) + (rowIndex - 1)+".hidden";
				} else {
					controlElements[j].id = controlElements[j].id.substring(0, idIndex) + (rowIndex - 1);
				}
			}
					
			if (controlElements[j].tagName == "INPUT" || controlElements[j].tagName == "SELECT") {
				var oldName = controlElements[j].name;
				controlElements[j].name = oldName.substring(0, (oldName.indexOf('_') + 1)) + (rowIndex - 1);
			}	
		}		
	}
}

/**	
 * Derive the row number to remove from the button ID.
 *
 * @param obj the button clicked
 */
function getRowNumber(obj) {
	var parent = obj.parentNode;
	
	while (parent.tagName != "TR") {
		parent = parent.parentNode;
	}	
	
	return parent.rowIndex;
}

/**	
 * Derive the FieldKey from the button ID.
 *
 * @param obj the button clicked
 */
function getFieldKey(obj) {
	var end = obj.id.indexOf("_remove");
	
	if (end == -1) {
		end = obj.id.indexOf("_add");
	}
	
	return obj.id.substring(0, end);		
}

/**	
 * Derive the table ID from the FieldKey.
 *
 * @param obj the button clicked
 */
function getTableId(obj) {
	return getFieldKey(obj) + "_table";
}

/**
 * Return the table object the current button is in.
 *
 * @param obj the button clicked
 */
function getTable(obj) {
	return window.document.getElementById(getTableId(obj));
}

/**
 * Return the HTML ID for the given FieldKey's component in the given row index. 
 * 
 * @param fieldKey FieldKey of component to create ID for
 * @param row number
 */
function getComponentId(fieldKey, row) {
	return fieldKey + "_" + row;
}

/**
 * Return the input component for the given FieldKey's component in the given row index.
 * 
 * @param fieldKey FieldKey of component to create ID for
 * @param row number
 */
function getComponent(fieldKey, row) {
	return window.document.getElementById(getComponentId(fieldKey, row));
}

/**
 * Reset the input fields in a table row to nothing.
 *
 * @param row Table row to reset values on
 */
function resetRowValues(row) {
	var controlElements = row.getElementsByTagName("*");

	for (var i = 0; i < controlElements.length; i++) {
		if (controlElements[i].tagName == "INPUT" || controlElements[i].tagName == "SELECT") {
			var componentType = controlElements[i].type;
	
			if (componentType == "checkbox" || componentType == "radio") {
				controlElements[i].checked = false;
				controlElements[i].defaultChecked = false;
			}
			else if (componentType == "file" || componentType == "password" || componentType == "text" || componentType == "textarea" || componentType == "hidden") {
				controlElements[i].value = "";
				controlElements[i].defaultValue = "";
			}
			else if (componentType == "select-one" || componentType == "select-multiple") {			
				for (var j = 0; j < controlElements[i].options.length; j++) {
					controlElements[i].options[j].selected = false;
					controlElements[i].options[j].defaultSelected = false;
				}
				
				// force the first selection to selected so that if there are no selections the browser correctly resets the select box 
				if (componentType == "select-one") {
					controlElements[i].options[0].selected = true;
				}
			}
		}
	}
}