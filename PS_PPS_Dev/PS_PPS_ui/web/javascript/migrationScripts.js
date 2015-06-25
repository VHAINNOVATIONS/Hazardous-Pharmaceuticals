/**
 * Copyright 2011, Southwest Research Institute
 * 
 * This file contains all java scripts used for migration
 */


function validateNDCField() 
{
	var form = document.getElementById("migrationForm");
	for(var i=0; i < form.elements.length; i++ )
	{
		var name = form.elements[i].name;
		if(name == 'ndcFile')
		{
			if(form.elements[i].value == '')
			{
				return true;
			}
		}
	}
	return false;
}


function startMigration()
{
	if(!validateNDCField())
	{
		if (window.confirm("             Starting Migration will reset the database.\n\n"  + "If you have not downloaded the CSV files click cancel, otherwise click ok."))
		{	
			validateFiles();
		}
		else 
		{
			return false;
		}
	}
	else
	{
		displayPopupMessage("NDC File, is a required field, please select a file to upload.")
	}
}

function getExportStatus()
{
	window.location = '/PRE/getExportStatus.go';
}

function saveDownloadFile()
{
	var selObj = document.getElementById('csvFile');
	var selValue = selObj.options[selObj.selectedIndex].value;
	var selText = selObj.options[selObj.selectedIndex].text;
	
	window.location = '/PRE/saveDowloadFile.go?csvSelected='+selValue;;
	
}

function startDownload()
{
	window.location = '/PRE/download.go';

}

function validateFiles()
{
	document.migrationForm.submit();
}

function getDBResetStatus()
{
	window.location = '/PRE/getResetDataBaseStatus.go';
	
}

function displayPopupMessage(mess)
{
	alert(mess);
}

function beginMigration()
{
	window.location = '/PRE/migrationStart.go';
}

function exportMapping()
{
	window.location = '/PRE/domainMappingExport.go';

}

function importMapping()
{
	document.migrationForm.action = '/PRE/domainMappingImport.go';
	document.migrationForm.submit();
	
}




