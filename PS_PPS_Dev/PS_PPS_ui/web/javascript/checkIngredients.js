/**
 * Copyright 2008, Southwest Research Institute
 *
 * Check to see that the Ingredients fields are filled in before continuing
 * to generate the VA Product and Print Names (with generate button)
 */


/**	
 * Read the items on the form. 
 * If filledin = false (required fields are empty), do not allow the button action to complete.
 */

function checkIngredients() {

	for (var i = 0; i < document.forms.length; i++) {
		//alert("in checkIngredients ");
		filledin = ingfilledin(document.forms[i]);
	}
	
	var shouldNotRedirect = false;
	
	if (!filledin) {
		shouldNotRedirect = true;
	}
	
	if (shouldNotRedirect) {
		window.event.returnValue = false;
	}
	else {
		window.event.returnValue = true;
	}
}

/**
 * Check if PEPS OI Name or Active Ingredient fields are empty.
 * If any field is empty, pop a warning window. 
 * Warning states that values must be completed to use the button.
 */
function ingfilledin(form) {
	//alert("inside ingfilled");
	var filledin = false;
	var message = "The PPS OI Name, Active Ingredient, and Generic Name must be filled in to use this button";
	var fieldname;
	count = 0;
	
	for (var i = 0; i < form.elements.length; i++) {
		//alert("number name and value = " + i + " - " + form.elements[i].name + " - " + form.elements[i].value);

		if ((form.elements[i].id == "ingredient_0")  && (form.elements[i].value == "")) {
			//alert("ingredient is null");
			count++;
			break;
		}
		else if ((form.elements[i].id == "strength_0") && (form.elements[i].value == "")) {
			//alert("strength is null");
			count++;
			break;
		}
		else if ((form.elements[i].id == "drug.unit_0") && (form.elements[i].value == "")) {
			//alert("drug unit is null");
			count++;
			break;
		}
		else if ((form.elements[i].name == "genericName") && (form.elements[i].value == "")) {
			count++;
			break;
		}
		else if (document.getElementById("productParentLink") == null) {
			//alert("parent link is null");
			count++;
			break;
		}

		
	} // end for loop
	
	if (count > 0) {
		alert(message);
	}
	else {
		filledin = true;
	}
	
	return filledin;
}