/**
 * Copyright 2012, Southwest Research Institute
 *
 * Open JavaScript pop-up windows.
 */
 
var clicked = false;

/**
 * Change the window location to the url passed.  If the onClick returns false, stop navigation.
 *
 * @param localThis the anchor
 * @param url (optional) if populated, the url to navigate to.
 * @param onClick (optional) if populated, the onClick to perform before navigation.
 */
function navigateTo(localThis, url, onClick) {
	var continueNavigation = true;
	if(!clicked) {
		if(typeof onClick !== 'undefined' && onClick != null && onClick.replace(/^\s\s*/, '').replace(/\s\s*$/, '') != '') {
			onClick = onClick.replace(/this/g,'localThis');
			var onClickObjectDec = "var onClickObject = {'localThis': null,'setLocalThis':function(theLocalThis) {localThis = theLocalThis;},'onClickFunction':function() {" + onClick + "}}"; 
			eval(onClickObjectDec);
			onClickObject.setLocalThis(localThis);
			continueNavigation = onClickObject.onClickFunction();
			if(typeof continueNavigation == 'undefined'){
				continueNavigation = true;
			}
		} 

		if(continueNavigation){
			clicked = true;
			window.location = url;
		}
	} 
	return false;
}