/**
 * Copyright 2009, Southwest Research Institute
 *
 * Methods called when a warning label is selected to display its consult text. 
 */

/**
 * Called when the selection changes in the multi-select warning label component.
 *
 * Since the warning label select boxes are multiselect, this code will always display
 * the first selection if a multiple options are selected. 
 *
 * @param id String ID for the selected warning label, used to find the consult text
 */ 
function displayWarningLabelText(id) {
	var select = window.document.getElementById(id);
	
	if (select && select.selectedIndex != -1) {
		var selected = select.options[select.selectedIndex];
		
		if (selected) {
			var selectedText = window.document.getElementById(selected.value + ".text");
	
			if (selectedText) {
				var displayedText = window.document.getElementById("displayed.warning.label.text");
				displayedText.innerHTML = selectedText.innerHTML;
			}		
		}
	}
	else {
		var selectedText = window.document.getElementById("no.warning.label.text");
		var displayedText = window.document.getElementById("displayed.warning.label.text");
		displayedText.innerHTML = selectedText.innerHTML;
	}
}