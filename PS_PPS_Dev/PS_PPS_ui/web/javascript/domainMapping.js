var dataChanged = false;

function scriptControlledExit(message) {
	if (dataChanged) {
		if (window.confirm(message)) {
			
			var searchForm = document.getElementById("domainMappingSearch");
			var displayForm = document.getElementById("domainMappingDisplay");
			
			var selectedChoiceList = searchForm.getElementsByTagName("select");			

			var choiceFromUser = selectedChoiceList[0].value;
			
			var choiceForSubmit = document.forms["domainMappingDisplay"].domainChoiceForSubmit.value;
			
			// "domainChoiceForSubmit" used below is from the hidden field 'name' argument.
			var choiceForSubmit = displayForm.domainChoiceForSubmit.value;	
			var eplTable = document.getElementById('epl.term');
			var selectedEplRow = getElementsByClassName(eplTable, 'selected')[0];
			associateEpl(selectedEplRow);


			
			// Configure and submit old user choice.
			document.getElementById("domainChoiceFromUser").value = choiceFromUser;
			document.getElementById("searchAfterSave").value = true;			
			document.getElementById("domainMappingDisplay").submit();
		}

		else {
			document.getElementById("domainMappingSearch").submit();
		}
	}
	else {
		document.getElementById("domainMappingSearch").submit();
	}
	dataChanged = false;
}

function selectEpl(fdbRowClick, eplTableLength) {
	if (fdbRowClick != null) {
		var fdbTable = fdbRowClick.parentNode;
		clearRows(fdbTable);
		selectRow(fdbTable, fdbRowClick);		// Highlight clicked row.
	}
	
	var eplTable = document.getElementById('epl.term');
	clearRows(eplTable);

	// Get EPL table geometry to calculate selected scroll position.
	var stepHeight = 0;
	if (eplScrollDiv == undefined)
	{
		var eplScrollDiv = document.getElementById('epl.scroll.div');
		//var eplScrollDivHeight = eplScrollDiv.scrollHeight;
		var eplScrollDivHeight = 20;
		stepHeight = eplScrollDivHeight / eplTableLength;
	}
	var selectedFdbRow = getElementsByClassName(fdbTable, 'selected')[0];

	if (selectedFdbRow != undefined && fdbRowClick.id.length > 6) {
		selectedFdbRow.getElementsByTagName('input')[0].checked = true;
		var tableRows = eplTable.getElementsByTagName('tr');
		for ( var i = 0; i < tableRows.length; i++) {
			var eplTrId = tableRows[i].id;
			if(('epl.term' + fdbRowClick.id.substring(6)) == eplTrId){
				document.getElementById(eplTrId).getElementsByTagName('input')[0].checked = true;
				if (eplScrollDiv != undefined)
				{
					eplScrollDiv.scrollTop = stepHeight * i;
				}
			}
		}
		// Highlight row with checked radio button.
		var eplTr = document.getElementById('epl.term' + fdbRowClick.id.substring(6));
		selectRow(eplTable, eplTr);
	}
}

function associateEpl(eplTr) {
	var eplTermKey = eplTr.getElementsByTagName('input')[0].value;
	var eplTermValue = eplTr.getElementsByTagName('label')[0].innerHTML;
	var eplTable = document.getElementById('epl.term');
	eplTable = eplTr.parentNode;
	var tableRows = eplTable.getElementsByTagName('tr');
	var eplLength = tableRows.length;
	clearRows(eplTable);
	addClass(eplTr, "selected");
	document.getElementById(eplTermKey).checked = true;
	
	var fdbTable = document.getElementById('fdb.term');
	var fdbRowSelected = getElementsByClassName(fdbTable, 'selected')[0];
	fdbRowSelected.id = "fdbEpl" + eplTermKey;
	fdbRowSelected.onclick = function() {selectEpl(this, eplLength)};
	var selectedEpl = getElementsByClassName(fdbRowSelected, 'eplLabel')[0];	
	selectedEpl.innerHTML = eplTermValue;
	var hiddenInput =getElementsByClassName(fdbRowSelected, 'eplLeft')[0].getElementsByTagName('input')[0];
	hiddenInput.value = eplTermKey;
	
	dataChanged = true;
}

function clearEpl(){
	var fdbTable = document.getElementById('fdb.term');
	var fdbRowSelected = getElementsByClassName(fdbTable, 'selected')[0];
	fdbRowSelected.id = "fdbEpl";
	
	var selectedEpl = getElementsByClassName(fdbRowSelected, 'eplLabel')[0];	
	selectedEpl.innerHTML = '';
	var hiddenInput =getElementsByClassName(fdbRowSelected, 'eplLeft')[0].getElementsByTagName('input')[0];
	hiddenInput.value = '';

	dataChanged = true;
	
	clearRows(document.getElementById('epl.term'));

}

function setCurrentFdbRowId(trId){
	var currentFdbRowId;
	currentFdbRowId = trId;	
}
function getCurrentFdbRowId(){
	return currentFdbRowId;	
}
function setCurrentEplRowId(trId){
	var currentEplRowId;
	currentEplRowId = trId;	
}
function getCurrentEplRowId(tr){
	return currentEplRowId;	
}
function selectRow(table, tr) {
	addClass(tr, 'selected');
}
function clearRows(table) {
	var selectedRows = getElementsByClassName(table, 'selected');
	for ( var i = 0; i < selectedRows.length; i++) {
		var currentRow = selectedRows[i];
		if (currentRow != null) {
			removeClass(currentRow, 'selected');
		}
		var tableTrId = currentRow.id;
		if (tableTrId != null) {
			document.getElementById(tableTrId).getElementsByTagName('input')[i].checked = false;
		}
	}
}
function hasClass(ele, cls) {
	return ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}
function addClass(ele, cls) {
	if (!this.hasClass(ele, cls))
		ele.className += " " + cls;
}
function removeClass(ele, cls) {
	if (hasClass(ele, cls)) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		ele.className = ele.className.replace(reg, ' ');
	}
}
function replaceClass(ele, oldClass, newClass) {
	if (hasClass(ele, oldClass)) {
		removeClass(ele, oldClass);
		addClass(ele, newClass);
	}
	return;
}
function getElementsByClassName(node, classname) {
		return (function getElementsByClass(searchClass, node) {
			if (node == null)
				node = document;
			var classElements = [], els = node.getElementsByTagName("*"), elsLen = els.length, pattern = new RegExp(
					"(^|\\s)" + searchClass + "(\\s|$)"), i, j;

			for (i = 0, j = 0; i < elsLen; i++) {
				if (pattern.test(els[i].className)) {
					classElements[j] = els[i];
					j++;
				}
			}
			return classElements;
		})(classname, node);
}

function getLayer(td){
	var tDIV = document.getElementById('hoverText')
	tDIV.style.cursor = "hand"
	tDIV.style.visibility = "visible"
}

function floatLayer(td){
	var tDIV = document.getElementById('hoverText')
	tDIV.style.top = window.event.clientY + 20
	
	if(window.event.clientX+200>document.body.clientWidth)
	{
		tDIV.style.left = document.body.clientWidth - 200	
	}
	else
	{
		tDIV.style.left = window.event.clientX  // + 15
	}
}

function hideLayer(td){
	var tDIV = document.getElementById('hoverText')
	tDIV.style.visibility='hidden'
}