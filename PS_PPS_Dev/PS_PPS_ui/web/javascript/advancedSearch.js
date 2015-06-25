/**
 * Copyright 2007, Southwest Research Institute
 *
 * advancedSearch.js is used to hold JavaScript functions to check 
 * for changes related to the advancedSearch functionality
 */


/**	
 * Prompts the user about the change to the entityType. If accepted, re-runs search, otherwise resets the dropdown
 *
 * @param url String value of URL to redirect to if there were no changes
 */
function reloadOncePrompted() {

	var shouldRedirect = true;

	//commented out for now - is there a usability requirement for this?
//	var message = window.document.getElementById("search.type.changed").innerHTML;
//
//	shouldRedirect = confirm(message);
		
	if (shouldRedirect) {
		document.getElementById("hasEntityChanged").value=true;
		document.getElementById("button.search.button").click();
	}
	else {
		revertSelectBox();
	}
}
/**
 * 
 *
 */
function runSearch(url) {
	document.advancedsearch.action = url;
    document.advancedsearch.submit();
}


/**
 * Reverts the entityType dropdown list to the previous value (default)
 *
 */
function revertSelectBox() {
	var type = document.getElementById("entityType");

		for (var i = 0; i < type.options.length; i++) {
			if ( type.options[i].defaultSelected ) {
				type.selectedIndex = i;
			}
		}
}

function retrieveTemplates() {
	window.location = '/PRE/manageSearchTemplate.go';
}
function add() {
	document.getElementById("addTerm").value = true;
}
function setAsDefault() {
	document.getElementById("setDefault").value = true;
}
function saveTemplate() {
	document.getElementById("saveSearch").value = true;
	if (document.getElementById("prevTemplateName").value == document.getElementById("template").value){
		confirm("A search template already exists by the name. Are you sure you wish to overwrite the existing template?");
		
	}
}
function prepareDisplayed(){
	var dis = document.getElementById("displayedItems");
	var box = document.getElementById("selectedOptions");
	var displayedArray = new Array();
	
	for(var count2=0; count2 < box.options.length; count2++) {   
		displayedArray.push(box.options[count2].value);
	}
	
	dis.value = displayedArray;
	if (dis.value == null || dis.value == "") {
		alert("The Selected Fields box must contain at least one item.");
	}
}

function listbox_moveacross(sourceID, destID, selectedID, addID, displayedID, direction) {     
	var src = document.getElementById(sourceID);    
	var dest = document.getElementById(destID);
	
	var sel = document.getElementById(selectedID);
	var box = document.getElementById("selectedOptions");
	var dis = document.getElementById(displayedID);
	var ad = document.getElementById(addID);
	
	var selectedArray = new Array();
	var addArray = new Array();
	var displayedArray = new Array();
	
	for(var count=0; count < src.options.length; count++) {           
		if(src.options[count].selected == true) {                 
			var option = src.options[count];                   
			var newOption = document.createElement("option");                 
			newOption.value = option.value;                 
			newOption.text = option.text;                 
			newOption.selected = true;                 
			try {                          
				dest.add(newOption, null);                        
				src.remove(count, null);
				selectedArray.push(option.value);
				if(direction == 'add'){
					addArray.push(newOption.value);
				}
			} catch (error) {                          
				dest.add(newOption);                         
				src.remove(count); 
				
			}                 
			count--;        
		}
	} 
	prepareDisplayed();
	for(var count2=0; count2 < box.options.length; count2++) {   
		displayedArray.push(box.options[count2].value);
	}
	
	dis.value = displayedArray;
	sel.value = selectedArray;
	ad.value = addArray;
}

function listbox_move(listID, direction) {       
	var listbox = document.getElementById(listID);     
	var selIndex = listbox.selectedIndex;       
	if(-1 == selIndex) {         
		return;     
	}       
	var increment = -1;     
	if(direction == 'up')         
		increment = -1;     
	else        
		increment = 1;       
	if((selIndex + increment) < 0 ||(selIndex + increment) > (listbox.options.length-1)) {         
		return;     
	}       
	var selValue = listbox.options[selIndex].value;     
	var selText = listbox.options[selIndex].text;     
	listbox.options[selIndex].value = listbox.options[selIndex + increment].value     
	listbox.options[selIndex].text = listbox.options[selIndex + increment].text       
	listbox.options[selIndex + increment].value = selValue;     
	listbox.options[selIndex + increment].text = selText;       
	listbox.selectedIndex = selIndex + increment; 
} 

function listSelections(){ 
	var cid = document.getElementById("availableList").selectedIndex; 
	var val = document.getElementById("availableList").options[cid].text 
}

function checkDisplayed(){
	var dis = document.getElementById("displayedItems");
	var displayedArray = new Array();
	
	for(var count2=0; count2 < box.options.length; count2++) {   
		displayedArray.push(box.options[count2].value);
	}
	
	dis.value = displayedArray;
	if (dis.value == null || dis.value == "") {
		alert("you must have one item");
	}
}