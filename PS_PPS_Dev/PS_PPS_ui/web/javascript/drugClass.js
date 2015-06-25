/**
 * Copyright 2009, Southwest Research Institute
 *
 * deaSchedule.js is used to hold JavaScript functions to handle rules related to VA Drug Classes
 */
 
/** 
 * If only one row is in the Drug Classes table, select it as primary. Otherwise, unselect all rows as primary.
 * This also calls helper methods to determine if the special handling field needs to have the supply instruction added 
 * to it based on primary drug class and category type
 *
 * Uses functions from dynamicTable.js to retrieve the table and components from the DOM.
 *
 * @param checkbox the primary checkbox
 * @param isSupply does the category of the product contain supply
 * @param isAdd is method being called on the Add Product page
 */
function selectPrimaryDrugClass(checkbox, isSupply, isAdd) {
	
	if(checkbox.type=="checkbox") {	
		var table = document.getElementById("drug.classes_table");
		
		// A table has 1 "row" in it if it has a length of three -- one for the header, one for the data, and one for the hidden row
		if (table.rows.length == 3) {
			var primary = document.getElementById("primary_"+0);
			primary.checked = true;
		}
		else if (table.rows.length > 3) {
			for (var i = 0; i < table.rows.length - 1; i++) {
				var primary = document.getElementById("primary_"+i);
				var hiddenCheckbox = document.getElementById(primary.name+".hidden");
				if(primary.name == checkbox.name) {
					primary.checked = checkbox.checked
					hiddenCheckbox.value = checkbox.checked
					
					if(checkbox.checked == true && isAdd) {
						checkDrugClassRowForSupplyTypeHelper(i, isSupply)
					}
					
				} else {
					primary.checked = false;
					hiddenCheckbox.value = false;
				}
				
				
			}
		}
	}
}

/**
 * Helper method for selectPrimaryDrugClass. Grabs the selected value text and
 * passes it to the addOrRemoveSupplySpecialHandling method.
 * @param rowNum
 * @param isSupply
 */
function checkDrugClassRowForSupplyTypeHelper(rowNum, isSupply) {
	var select = document.getElementById("drug.class_"+rowNum);
	var value = select.options[select.selectedIndex].text;
	
	addOrRemoveSupplySpecialHandling(value, isSupply);
}

/**
 * The method which actually adds the Supply field to the Special Handlings table
 */
function addSupplyToSpecialHandling() {
	var table = document.getElementById("special.handlings_table");	
	
	// A table has 1 "row" in it if it has a length of three -- one for the header, one for the data, and one for the hidden row
	if (table.rows.length == 2) {		
		setSupplyAsSelectedValue(0);		
	} else if (table.rows.length == 3) {
		var specialHandling = getComponent("special.handlings", 0);
		var selectedOption = specialHandling.options[specialHandling.selectedIndex];
		if(!selectedOption.text.startsWith('S')) {			
			setSupplyAsSelectedValue(1);
		}
	} else if (table.rows.length > 3) {
		var supplyFound = false;
		for (var i = 0; i < table.rows.length - 1; i++) {
			var specialHandling = getComponent("special.handlings", i);
			var selectedOption = specialHandling.options[specialHandling.selectedIndex];
			
			if(selectedOption.text.startsWith('S')) {
				supplyFound = true;
			}			
		}
		
		if(!supplyFound) {
			setSupplyAsSelectedValue(table.rows.length-1);
		}
	}
}

/**
 * Helper method for addSupplyToSpecialHandling, it sets the Special Handling as the selected value
 * @param selectPosition
 */
function setSupplyAsSelectedValue(selectPosition) {
	var addButton = document.getElementById("special.handlings_add");
	var select = addRow(addButton, "specialHandlings");

	
	var index;		
	for (i = 0; i<select.length; i++) {
		var text = select[i].text;
		if (text.startsWith('S')) {								
			index = i;
			break;
		}							
	}
	
	select.selectedIndex = index;
}

/**
 * Removes the supply field from the special handlings table
 */
function removeSupplyFromSpecialHandling() {
	var table = document.getElementById("special.handlings_table");	
	
	// A table has 1 "row" in it if it has a length of three -- one for the header, one for the data, and one for the hidden row
	if (table.rows.length == 3) {
		var specialHandling = document.getElementById("special.handlings_0");
		var selectedOption = specialHandling.options[specialHandling.selectedIndex];
		if(selectedOption.text.startsWith('S')) {			
			var removeButton = document.getElementById("special.handlings_remove_0");
			removeRow(removeButton,'specialHandlings', 'special.handlings_removedRows');
		}
	} else if (table.rows.length > 3) {
		
		for (var i = 0; i < table.rows.length - 1; i++) {
			var specialHandling = document.getElementById("special.handlings_"+i);
			var selectedOption = specialHandling.options[specialHandling.selectedIndex];
			
			if(selectedOption.text.startsWith('S')) {
				var removeButton = document.getElementById("special.handlings_remove_"+i);
				removeRow(removeButton,'specialHandlings', 'special.handlings_removedRows'); 
			}			
		}
		
		
	}
}

/**
 * Used on the selectboxes on the add product page. This is called on 
 * change and passes the heavy lifting onto addOrRemoveSupplySpecialHandling after
 * a primary drug class check is made
 * @param select
 * @param isSupply
 */
function checkDrugClassForSupplyType(select, isSupply) {
	if(select.type=="select-one") {
		var position = select.id[select.id.length-1];	
		var primary = document.getElementById("primary_"+position);
		
		if(primary.checked == true) {
			addOrRemoveSupplySpecialHandling(select.options[select.selectedIndex].text, isSupply);
		}
	}
	
}

/**
 * Determines if the selected drug class value is a supply type and calls add or remove appropriately.
 * @param selectValue
 * @param isSupply
 */
function addOrRemoveSupplySpecialHandling(selectValue, isSupply) {
	
	if(selectValue.startsWith('X')) {
		addSupplyToSpecialHandling();
	} else if(!isSupply){
		removeSupplyFromSpecialHandling();
	}
}