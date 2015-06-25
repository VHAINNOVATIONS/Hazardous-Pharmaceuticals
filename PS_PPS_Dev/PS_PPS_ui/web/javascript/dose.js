/**
 * Copyright 2008, Southwest Research Institute
 *
 * Calculates the Dose field in National Possible Dosages
 */
 
 function getFieldIndexFromId (fieldObj) {
 	return fieldObj.id.substring(fieldObj.id.lastIndexOf("_")+1);
 }
 
 function populateDose(unitsPerDoseObj, strength) {
	try{
		if (unitsPerDoseObj.value < 1) { 
			alert("The value for Dispense Units per Dose must be at least 1"); 
		}
		else { 
	 		var fieldIndex = getFieldIndexFromId(unitsPerDoseObj);
	 		var doseObj = document.getElementById("dose_" + fieldIndex);
	 		doseObj.value = unitsPerDoseObj.value * strength;
	 		if (doseObj.value == 0) {
	 			alert("The Dose is 0 because there are multiple ingredient strengths. Please update the Dose field");
	 		}
	 		//alert(unitsPerDoseObj.id + ", " + doseObj.id + " = " + doseObj.value);
		}
 	} catch (e) {
 		alert(e);
 	}
 }