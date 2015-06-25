/**
 * Copyright 2008, Southwest Research Institute
 *
 * Allow the user to select or unselect all checkboxes in a form
 */
 
var checkflag = true;

/**
 * Select all checkboxes in the given list for editing.
 *
 * @param checkboxlistName HTML name attribute of checkbox list
 */
function checkall(checkboxlistName) {
	var checkboxlist = window.document.getElementsByName(checkboxlistName);
	
	if (checkflag) {
		for (i=0; i < checkboxlist.length; i++) {
			checkboxlist[i].checked = true; 
		}
			
		checkflag = false;
		
		window.document.getElementById("unselect.all.button").style.display="inline";
		window.document.getElementById("select.all.button").style.display="none";
	}
	else {
		for (i = 0; i < checkboxlist.length; i++) {
			checkboxlist[i].checked = false;
		}
			
		checkflag = true;
		
		window.document.getElementById("unselect.all.button").style.display="none";
		window.document.getElementById("select.all.button").style.display="inline";
	}
}

/**
 * Verifies at least one item in the selected checkbox is selected. 
 *
 */
function verifySelection(checkboxlistName) {
	var checkboxlist = window.document.getElementsByName(checkboxlistName);
	
	var oneFlagged = false;
	
	for (i=0; i < checkboxlist.length; i++) {
		oneFlagged |= checkboxlist[i].checked; 
	}
		
	if (!oneFlagged) {
		alert("There is nothing selected");
		return false;
	}

	return true;		
}

