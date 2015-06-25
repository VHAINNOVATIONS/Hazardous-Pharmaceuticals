/**
 * Copyright 2011, Southwest Research Institute
 *
 * Javascript helper methods for forms
 */
 
/**
 * Change the hidden values associated with each checkbox
 */
function changeCheckboxHiddenValue(checkbox) {
	
	var hiddenCheckbox = document.getElementById(checkbox.name+".hidden");
	
	if(checkbox.checked) {
		checkbox.value=true;
		hiddenCheckbox.value = true;
	}
	else{
		checkbox.value=false;
		hiddenCheckbox.value = false;
		
	}
}

/**
 * Uses AJAX to look up a list of domain values associate with a fieldKey, add them
 * to a select box and then display that select box
 * @param id - of select box to manipulate
 * @param name - of select box to manipulate
 * @param value - of selected item
 * @param fieldKey - of domain to lookup
 */
function showHiddenDropdown(id, name, value, fieldKey){
	
	var textbox = document.getElementById("text_"+id);
	textbox.disabled = true;
	document.body.style.cursor = "wait";
	
	var request = new PPSNAjax('retrieveDomains.go', 'fieldKey='+fieldKey, false, true).get();
	
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {	
			if (request.responseText) {
				
				var domainOptions = {};				
				domainOptions = JSON.parse(request.responseText);
				
				var select = document.getElementById(id);
				select.options.length=0;
				var option = new Option();
				select.add(option, null);
				
				for(var i=0; i < domainOptions.size(); i++) {
					select.style.cursor = "wait";
					var option = new Option();
					option.text = domainOptions[i][i][0].display;
					option.value = domainOptions[i][i][0].value;
					
					if(domainOptions[i][i][0].value == value) {
						option.selected = true;
					} 						
					select.add(option, null);
				}
				
				var textbox = document.getElementById("text_"+id);
				textbox.style.display="none";
				textbox.style.visibility="hidden";
				
				select.style.display="inline";				
				select.style.cursor = "auto";				
				select.style.width="auto";		
				select.focus();
				
				document.body.style.cursor = "auto";
			}
		}
	}
	
}

