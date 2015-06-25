/**
 * Copyright 2011, Southwest Research Institute
 *
 * hiddenTabs.js is used to hold JavaScript functions to
 * dynamically show or hide tabs on the Product edit page
 */

/**
 * Unhides all tabs if tab are hidden, and hides all tabs if tabs
 * are visible
 */
function switchVisability(selectedTab){
	if(document.getElementById("hideLink").style.display=="none"){
		document.getElementById("hideLink").style.display="inline";
		document.getElementById("showLink").style.display="none";
		showAll();
	}else{
		document.getElementById("hideLink").style.display="none";
		document.getElementById("showLink").style.display="inline";		
		hideAll(selectedTab);
	}
	return false;
}

/**
 * Hides all tabs not currently selected
 */
function hideAll(selectedTab, showTabsCookieValue){

//	showTabsCookieValue = (showTabsCookieValue == null)
	
	if(showTabsCookieValue == 'false' || showTabsCookieValue == null){
		
		if(selectedTab != "drug_data_tab")
			document.getElementById("drug_data_tab").style.display = "none";
		else 
			document.getElementById("drug_data_tab").style.display = "inline";
			
		if(selectedTab!="options_tab")
			document.getElementById("options_tab").style.display = "none";
		else
			document.getElementById("options_tab").style.display = "inline";
		
		if(selectedTab!="application_data_tab")
			document.getElementById("application_data_tab").style.display = "none";
		else
			document.getElementById("application_data_tab").style.display = "inline";
		
		if(selectedTab!="dispense_data_tab")
			document.getElementById("dispense_data_tab").style.display = "none";
		else
			document.getElementById("dispense_data_tab").style.display = "inline";
		
		if(selectedTab!="administration_data_tab")
			document.getElementById("administration_data_tab").style.display = "none";
		else
			document.getElementById("administration_data_tab").style.display = "inline";
		
		if(selectedTab!="children_tab")
			document.getElementById("children_tab").style.display = "none";
		else
			document.getElementById("children_tab").style.display = "inline";
		
		if(selectedTab!="history_tab")
			document.getElementById("history_tab").style.display = "none";
		else
			document.getElementById("history_tab").style.display = "inline";
		
		if(selectedTab!="alpha_tab")
			document.getElementById("alpha_tab").style.display = "none";
		else
			document.getElementById("alpha_tab").style.display = "inline";
	
	}

}

/**
 * Shows all tabs for the page
 */
function showAll(){
	//alert("inside showAll");
		document.getElementById("drug_data_tab").style.display = "inline";
		document.getElementById("options_tab").style.display = "inline";
		document.getElementById("application_data_tab").style.display = "inline";
		document.getElementById("dispense_data_tab").style.display = "inline";
		document.getElementById("administration_data_tab").style.display = "inline";
		document.getElementById("children_tab").style.display = "inline";
		document.getElementById("history_tab").style.display = "inline";
		document.getElementById("alpha_tab").style.display = "inline";
		
		
}