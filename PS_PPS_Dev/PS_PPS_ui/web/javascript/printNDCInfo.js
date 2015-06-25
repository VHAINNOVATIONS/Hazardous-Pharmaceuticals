/**
 * Copyright 2009, Southwest Research Institute
 *
 * Submit the performFDBSearchOption and return the NDC results
 */
 
 function printNDCInfo(NDC) {

		var inputNDC = NDC;
		
		var searchType = "NDC_SEARCH";
		var fdbSearchOptionType = document.getElementById("fdbSearchOptionType");
			fdbSearchOptionType.value = searchType;
		var fdbSearchString = document.getElementById("fdbSearchString");
	 		fdbSearchString.value = inputNDC;
		var button = document.getElementById("button.performFDBSearchOption.button");
		
		button.click(); 

 }