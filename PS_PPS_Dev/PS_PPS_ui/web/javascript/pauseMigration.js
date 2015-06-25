/**
 * Copyright 2011, Southwest Research Institute
 *
 * Change the button name from Pause to Restart when selected
 */
 
var checkflag = true;

/**
 * Change button name, Pause to Restart, and Restart to Pause
 *
 */
function pauseMigration(form) {
	for (var i = 0; i < document.forms.length; i++) {
		setting = changesetting(document.forms[i]);
	}
}

function changesetting(form) {
    var setting = false;
    for (var i = 0; i < form.elements.length; i++) {
          if (form.elements[i].value == "Pause") {
                form.elements[i].value = "Resume";
                checkflag = false;
                window.navigate('/PRE/pauseMigration.go');
                return form.elements[i].value;
                }
          if (form.elements[i].value == "Resume") {
                form.elements[i].value = "Pause";
                checkflag = true;
                window.navigate('/PRE/resumeMigration.go');
                return form.elements[i].value;
          }
    }
}

