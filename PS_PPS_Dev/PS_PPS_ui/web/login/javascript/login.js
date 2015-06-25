/**
 * This JavaScript file contains functions that are needed for login.
 * @author VHIT Security and Other Common Services (S&OCS)
 * @version 1.1.0.007
 */

function preserveRadioGroup (evt) {
  this.checked = this.storedChecked;
  var rgb = this.form[this.name];
  if (!rgb.length && rgb.storedChecked)
    rgb.checked = true;
  else 
    for (var b = 0; b < rgb.length; b++)
      rgb[b].checked = rgb[b].storedChecked ? true : false;
}

function disableRadioGroup (radioGroup) {
  if (!radioGroup.disabled) {
    radioGroup.disabled = true;
    if (document.all || document.getElementById) {
      if (!radioGroup.length)
        radioGroup.disabled = true;
      else
        for (var b = 0; b < radioGroup.length; b++)
          radioGroup[b].disabled = true;
     }
    else {
      if (!radioGroup.length) {
        radioGroup.storedChecked = radioGroup.checked;
        radioGroup.oldOnClick = radioGroup.onclick;
        radioGroup.onclick = preserveRadioGroup;
      }
      else
        for (var b = 0; b < radioGroup.length; b++) {
          radioGroup[b].storedChecked = radioGroup[b].checked;
          radioGroup[b].oldOnClick = radioGroup[b].onclick;
          radioGroup[b].onclick = preserveRadioGroup;
        }
    }
  }
}

function enableRadioGroup (radioGroup) {
  if (radioGroup.disabled) {
    radioGroup.disabled = false;
    if (document.all || document.getElementById) {
      if (!radioGroup.length)
        radioGroup.disabled = false;
      else
        for (var b = 0; b < radioGroup.length; b++)
          radioGroup[b].disabled = false;
     }
    else {
      if (!radioGroup.length) {
        radioGroup.onclick = radioGroup.oldOnClick;
      }
      else
        for (var b = 0; b < radioGroup.length; b++) {
          radioGroup[b].onclick = radioGroup[b].oldOnClick;
        }
    }
  }
}

function isblank(s) {
	slen = s.length;
	for (var i = 0; i < slen; i++) {
		var c = s.charAt(i);
		if ((c != ' ') && (c != '\n') && (c != '')) return false;
	}
	return true;
}

function disableObj(obj) {
	obj.disabled = true;
}

function enableObj(obj) {
	obj.disabled = false;
}

function checkHF(myForm,mySelect,myRadioButtonGroup,myHiddenObj1,myHiddenObj2,myHiddenObj3) {
	disableInstitutionObj(myForm,mySelect,myRadioButtonGroup,myHiddenObj1,myHiddenObj2)
	if (myHiddenObj3.value == "number") {
		reSortSelectOptions(myForm,mySelect,"value");
	}
	else if (myHiddenObj3.value == "name") {
		reSortSelectOptions(myForm,mySelect,"text");
	}
}

function disableInstitutionObj(myForm,mySelect,myRadioButtonGroup,myHiddenObj1,myHiddenObj2) {
	if (myHiddenObj1.value == "true") disableObj(mySelect);
	if ((myHiddenObj1.value == "true") || (myHiddenObj2.value == "true")) {
		disableRadioGroup(myRadioButtonGroup);
	}
}

function enableInstitutionObj(myForm,mySelect,myRadioButtonGroup,myHiddenObj1,myHiddenObj2) {
	myHiddenObj1.value = "false";
	myHiddenObj2.value = "false";
	enableObj(mySelect);
	enableRadioGroup(myRadioButtonGroup);
}

function hasOptions(obj) {
  if (obj!=null && obj.options!=null) { return true; }
  return false;
}

function sortSelect(obj,sortBy) {
  var o = new Array();
  if (!hasOptions(obj)) { return false; }
  for (var i=0; i<obj.options.length; i++) {
    o[o.length] = new Option( obj.options[i].text, obj.options[i].value, obj.options[i].defaultSelected, obj.options[i].selected) ;
  }
  if (o.length==0) { return true; }
  if (sortBy == "text") {
  	o = o.sort( 
    	  function(a,b) { 
      	  if ((a.text+"") < (b.text+"")) { return -1; }
      	  if ((a.text+"") > (b.text+"")) { return 1; }
      	  return 0;
    	  } 
  	);
  }
  else if (sortBy == "value") {
    	o = o.sort( 
    	  function(a,b) { 
      	  if ((a.value+"") < (b.value+"")) { return -1; }
      	  if ((a.value+"") > (b.value+"")) { return 1; }
      	  return 0;
    	  } 
  	);
  }
  else {
  	alert("\nError encountered while sorting institutions!!!");
  }

  for (var i=0; i<o.length; i++) {
    obj.options[i] = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
  }
  return true;
}

function reSortSelectOptions(formRef,selectRef,sortBy) {
	if (!formRef) {
	 alert("\nForm reference unknown!!!");
	}
	else if (selectRef.length <1) {
	 alert("\n" + selectRef.name + " drop down component is empty!!!");
	}
	else {
	 var result = sortSelect(selectRef,sortBy);
	 if (!result) {
	  alert("\nSorting Select Object failed!!!");
	 }
	}
}

function doInitialSortOnDivisions(myForm,mySelect,mySortPref,mycookie) {
    var allcookies = mycookie;
    var cookieArry = allcookies.split(";");
    for (var i=0; i < cookieArry.length; i++) {
        cookieArry[i]=cookieArry[i].split("=");
        if ((cookieArry[i][0].indexOf("gov.va.med.authentication.kernel.defaultSortDivisionBy") != -1) &&
            (cookieArry[i][1] == "name")) {
                //Now call function to sort divisions by name.
                reSortSelectOptions(myForm,mySelect,mySortPref)
                break;
        }
    }
}

function doTimer(myForm,myTimer) {
		var t2 = (myTimer.value - 30) * 1000;
		var t3 = (t2 / 1000);
		var t = window.setTimeout('alert("Your session will expire in 30 seconds!")', t2);
}