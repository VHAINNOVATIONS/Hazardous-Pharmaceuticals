/**
 * Copyright 2009, Southwest Research Institute
 *
 * This file contains methods that are used to perform client-side data field validation
 */
 
/**
 * This method is used to remove leading zeros from the data field (x)
 */
function trimLeadingZeros(x) {
	while (x.length > 0 && x.charAt(0) == "0") {
    	x = x.substring(1, x.length);
	}
	
	// if the leading character is ., add a 0 in front of it
	if (x.charAt(0) == ".") { 
		x = "0" + x;
	}
	
	return x;
}
 
/**
 * This method is used to remove trailing zeros from the data field (x)
 */
function trimTrailingZeros(x) {
	var decimalPos = x.indexOf(".");
	if (decimalPos > -1) {
		first = x.substring(0, decimalPos);
		second = x.substring(decimalPos, x.length);
			
		while (second.charAt(second.length-1) == "0") {			
			second = second.substring(0, second.length - 1);
		}
		
		if (second.length > 1) {
			return first + second;
		}
		else {	
			return first;
		}
	}
	
	
	return x;
}
 
/**
 * This method is used to remove leading and trailing zeroes from data field y
 * to support JCAHO compliance.
 * 
 */
function stripLeadingTrailingZeros(id) {
	var component = window.document.getElementById(id);
	
	var value = component.value;
	value = trimLeadingZeros(value);
	value = trimTrailingZeros(value);
	
	component.value = value;
}

/**
 * This method is used to remove leading and trailing zeroes from data field y
 * to support JCAHO compliance. Use this method when data field y should return
 * a Long or Short (basically, no decimal or anything after it). If you try and
 * return a Long with a decimal, Struts will throw an "invalid field error for
 * field"
 * 
 */
function stripLeadingTrailingZerosForLong(id) {
	var component = window.document.getElementById(id);
	
	var value = component.value;
	value = trimLeadingZeros(value);
	value = trimTrailingZeros(value);
	
	var decimalPos = value.indexOf(".");
	
// if (decimalPos > -1) {
// value = value.substring(0, decimalPos);
// }
	
	component.value = value;
}

/**
 * This method is used to upperCase dataField values
 * 
 */
function upperCase(id) {
	var component = window.document.getElementById(id);
	
	var value = component.value;
	value = value.toUpperCase();

	component.value = value;
}