/**
 * Copyright 2008, Southwest Research Institute
 *
 * Methods called to handle the menu component. 
 */
 
 
/**
 * Select the menu that matches the URL when the page loads
 */
if (!document.getElementById) {
    document.getElementById = function() { return null; }
}
var tabMenuIndexes = new Object;
var tabMenuDefaultSubMenu = {
								"Home":"Home",
								"Manage PPS":"Enter / Edit Items",
								"Reports":"Reports",
								"COTS Services":"FDB Search",								
								"Manage Application":"Domain Mapping",
								"User Preferences":"User Preferences",
								"Migration":"Migration Start"	
							};
function initMenu() {
    var uls = document.getElementsByTagName("ul");
    for (i = 0; i < uls.length; i++) {
        if (uls[i].className == "menuList") {
            decorateMenu(uls[i]);
        }
    }
}

function decorateMenu(menu) {
    var items = menu.getElementsByTagName("li");
    
    for (var i=0; i < items.length; i++) { 
    	if(items[i].children[0].title && items[i].children[0].title != "") {   
			tabMenuIndexes[items[i].children[0].title] = i;
			items[i].children[0].id = i;
		}
    }

    //alert(getCookie("menuItemSelected"));
    
    activateMenu(items);
}

function getDefaultTabIndex(menuText) {
	if (tabMenuIndexes[tabMenuDefaultSubMenu[menuText]]) {
		return tabMenuIndexes[tabMenuDefaultSubMenu[menuText]];
	} else if (tabMenuIndexes[menuText]) {
		return tabMenuIndexes[menuText];
	} else {
		return 0;
	}
}

//call this function to set a specific menu-tab to active on next rendering
function setTabIndex(menuTabText) {
//	setCookie("menuItemSelected", getDefaultTabIndex(menuTabText));
//	alert(getCookie("menuItemSelected"));
//	return setCookie("menuItemSelected", getDefaultTabIndex(menuTabText));
	
	var responseText = new PPSNAjax("changeSelectedTab",["menuItemSelected="+getDefaultTabIndex(menuTabText)], true, false).get();
	
	return true;
	
}

function activateMenu(items) {
    var activeMenu;
    var found = 0;
    for (var i=0; i < items.length; i++) {
    	var url = items[i].children[0].href;
    	
        var current = document.location.toString();
        if (current.indexOf(url) != -1) {
            found++;
        }
    }

    // more than one found, use cookies
    var menuSelected = document.getElementById("menuSelected").value;
    if ( (found > 1 || menuSelected > 0) &&
     document.location.toString().indexOf("home") == -1 &&
     items[menuSelected] != undefined) {
        if (items[menuSelected].parentNode.className == "submenu") {
            items[menuSelected].children[0].className="selected";
            items[menuSelected].parentNode.parentNode.className="selected";
        } else {
            items[menuSelected].className+="selected";
        }
    } else {
        // only one found, match on URL
        for (var i=0; i < items.length; i++) {
            var url = items[i].children[0].href;
            var current = document.location.toString();
            if (current.indexOf(url) != -1) {
                if (items[i].parentNode.className == "submenu") {
                    items[i].children[0].className="selected";
                    items[i].parentNode.parentNode.className="selected";
                } else {
                    items[i].className+="selected";
                }
            }
        }
    }
}