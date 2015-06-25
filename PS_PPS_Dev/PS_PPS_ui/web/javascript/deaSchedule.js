/**
 * Copyright 2007, Southwest Research Institute
 *
 * deaSchedule.js is used to hold JavaScript functions to handle rules related to CS Federal schedule, 
 * DEA Schedule and Special Handling
 */
 
 
 function processChangedCsFedSchedule() {
 

	var csFed = document.getElementById("cs.fed.schedule");
	var deaSchedule = document.getElementById("dea.schedule");
	var specialHandlings = document.getElementById("special.handlings");	
	
		if (csFed != null && deaSchedule != null && specialHandlings != null) {
		
		var dropDown = document.getElementById("special.handlings_0");
		
		var supplySelected = false;
		
		var i = 1;
		while((dropDown != null || dropDown != undefined)) {			
			if(dropDown.options[dropDown.selectedIndex].text.startsWith('S')) {
				supplySelected = true;
				break;
			}
			dropDown = document.getElementById("special.handlings_"+i);
			i++
		}
			
		var index = csFed.selectedIndex;
			
		deaSchedule.options.length = 0;
		
		if (index == 0) {
			deaSchedule.options[0]=new Option("", "", true, true);
			deaSchedule.options[1]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[2]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[3]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[4]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[5]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[6]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[7]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[8]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
			
			if(supplySelected) {
				resetSpecialHandlings("S");
			} else {
				resetSpecialHandlings("");
			}
		}
		
		if (index == 1) {
			deaSchedule.options[0]=new Option("", "", false, false);
			deaSchedule.options[1]=new Option("0-Unscheduled", "0-Unscheduled", true, true);
			deaSchedule.options[2]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[3]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[4]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[5]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[6]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[7]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[8]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
			
			if(supplySelected) {
				resetSpecialHandlings("S");
			} else {
				resetSpecialHandlings("");
			}
		}
		
		if (index == 2) {
			deaSchedule.options[0]=new Option("", "", false, false)
			deaSchedule.options[1]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[2]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", true, true);
			deaSchedule.options[3]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[4]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[5]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[6]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[7]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[8]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
		}	
		
		if (index == 3 || index == 4) {
			deaSchedule.options[0]=new Option("", "", false, false)
			deaSchedule.options[1]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[2]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[3]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", true, true );
			deaSchedule.options[4]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[5]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[6]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[7]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[8]=new Option("9-Over the Counter", "9-Over the Counter", false, false);			
			
			if (index == 3) {
				if(supplySelected) {
					resetSpecialHandlings("A","W","F","S");
				} else {
					resetSpecialHandlings("A","W","F");
				}		
			}
			else {
				if(supplySelected) {
					resetSpecialHandlings("C","W","F","S");
				} else {
					resetSpecialHandlings("C","W","F");
				}
			}
			
		}
		
		if (index == 5 || index == 6) {
			deaSchedule.options[0]=new Option("", "", false, false)
			deaSchedule.options[0]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[1]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[2]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[3]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", true, true);
			deaSchedule.options[4]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[5]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[6]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[7]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
			
			if (index == 5) {
				if(supplySelected) {
					resetSpecialHandlings("A","S");
				} else {
					resetSpecialHandlings("A");
				}
			}
			if (index == 6) {
				if(supplySelected) {
					resetSpecialHandlings("C","S");
				} else {
					resetSpecialHandlings("C");
				}
			}
		
		}
		
		if (index == 7) {
			deaSchedule.options[0]=new Option("", "", false, false)
			deaSchedule.options[0]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[1]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[2]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[3]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[4]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", true, true);
			deaSchedule.options[5]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", false, false);
			deaSchedule.options[6]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[7]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
			
		}
	
		if (index == 8) {
			deaSchedule.options[0]=new Option("", "", false, false)
			deaSchedule.options[0]=new Option("0-Unscheduled", "0-Unscheduled", false, false);
			deaSchedule.options[1]=new Option("1-Schedule 1 Item", "1-Schedule 1 Item", false, false);
			deaSchedule.options[2]=new Option("2-Schedule 2 Item", "2-Schedule 2 Item", false, false );
			deaSchedule.options[3]=new Option("3-Schedule 3 Item", "3-Schedule 3 Item", false, false);
			deaSchedule.options[4]=new Option("4-Schedule 4 Item", "4-Schedule 4 Item", false, false);
			deaSchedule.options[5]=new Option("5-Schedule 5 Item", "5-Schedule 5 Item", true, true);
			deaSchedule.options[6]=new Option("6-Legend Item", "6-Legend Item", false, false);
			deaSchedule.options[7]=new Option("9-Over the Counter", "9-Over the Counter", false, false);
		}
	}

	
	


}



/**
Resets special handlings fields to the selected values
*/
function resetSpecialHandlings() {

	var arg = resetSpecialHandlings.arguments;
	var table = document.getElementById("special.handlings_table");
	var tableLength = table.rows.length
	
		for (j = 0; j < arg.length; j++) {
				var button = document.getElementById("special.handlings_add");				
				var name = "special.handlings_" + j;					
				
				var dropDown = document.getElementById(name);
			
				if (dropDown == null || dropDown == undefined || j == table.rows.length - 2) {					
//					button.click();
					dropDown = document.getElementById(name);
					dropDown = addRow(button, "specialHandlings");
				} 
				
				var index;
				
				for (i = 0; i<dropDown.length; i++) {
					text = dropDown[i].text;
					if (text.startsWith(arg[j])) {								
						index = i;
						break;
					}							
				}
				
				dropDown.selectedIndex = index;
				dropDown.show();
		}
		clearSpecialHandlings(arg.length);
}

function clearSpecialHandlings(argLength) {

		var table = document.getElementById("special.handlings_table");
		var tableLength = table.rows.length

		if (tableLength - 2 > argLength) {		
			for (var i = tableLength - 3; i >= argLength; i--) {
				removeButton = document.getElementById("special.handlings_remove_"+i);
//				removeRow(removeButton,'specialHandlings', 'special.handlings_removedRows'); 
				removeButton.click();
			}
		}
		
}

 