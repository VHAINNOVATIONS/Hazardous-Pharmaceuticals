/**
 * Copyright 2008, Southwest Research Institute
 *
 * Disables use of the Enter key from within a text box
 */

function checkEnter(e){ //e is event object passed from function invocation
	var characterCode //literal character code will be stored in this variable

	if (e && e.which){ //if which property of event object is supported (NN4)
		e = e
		characterCode = e.which //character code is contained in NN4's which property
	}
	else {
		e = e
		characterCode = e.keyCode //character code is contained in IE's keyCode property
	}

	if (characterCode == 13){ //if generated character code is equal to ascii 13 (if enter key)
		return false //return false to the event handler
	}
	else {
		return true //return true to the event handler
	}
}
