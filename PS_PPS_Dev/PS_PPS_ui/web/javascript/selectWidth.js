/**
 * Copyright 2008, Southwest Research Institute
 *
 * IE does not support CSS min-width and max-width attributes. This JavaScript works around that deficiency on select boxes.
 */
 
/**
 * Fix IE's lack of min-width CSS support by resizing select boxes. 
 */
function selectMinWidth() {
	var selectBoxes = window.document.getElementsByTagName("select");
 	
	for (var i = 0; i < selectBoxes.length; i++) {
 		selectWidth(selectBoxes[i]);
	}
}

/**
 * Resize the given selectBox such that the text does not get cut off.
 *
 * @param selectBox Select input box to resize
 */
function selectWidth(selectBox) {
	var pad = 25;
	
	// scrollWidth and offsetWidth will be zero if the select box is hidden from view (display: none)
	if (selectBox.scrollWidth != 0 && selectBox.offsetWidth != 0 && selectBox.scrollWidth + pad > selectBox.offsetWidth) {		
		selectBox.style.width = selectBox.scrollWidth + pad + "px";
 	}	
}