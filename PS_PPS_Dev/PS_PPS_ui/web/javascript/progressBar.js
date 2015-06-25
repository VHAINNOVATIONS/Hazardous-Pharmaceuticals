//creates a progress bar with specified ID
function makeBar(elemId, height){
	document.write('<td width="400" height="'+height+'"><div style="border: 2px solid black; height: 100%;">');
	document.write('<div id="'+elemId+'" style="background-color: lightblue; width: 0%; height: 100%;">&nbsp;</div></div></td>');
	document.write('<td align="right" width="35" id="'+elemId+'per">0</td>');
}

//sets the progress bar to a given percent
function setProgressWidth(elemId, percent)
{

	var divEl = document.getElementById(elemId);
	divEl.style.width = percent;
	var perId = elemId + "per";
	var tdPer = document.getElementById(perId);
	tdPer.innerHTML = percent;
}
