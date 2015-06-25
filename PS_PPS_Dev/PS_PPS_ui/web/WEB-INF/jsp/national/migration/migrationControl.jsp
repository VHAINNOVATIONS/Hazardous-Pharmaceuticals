<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="gov.va.med.pharmacy.peps.service.common.migration.ProcessStatus" %>

<script src="/PRE/javascript/progressBar.js" type="text/javascript"></script>

<script type="text/javascript">
	var processRunning = "<c:out value="${migrationState.running}"/>";
	var timer_is_on=0;

	var processStatus =  "<c:out value="${migrationState.status}"/>";
	var buttonState =  "<c:out value="${migrationState.pauseButtonText}"/>"
	var dbStatus =  "<c:out value="${dbstatus.status}"/>";
	
	function togglePauseButton()
	{
		if (processStatus=='RUNNING')
		{
			window.location = '/PRE/migrationPause.go';
		}
		else if (processStatus=='PAUSED') 
		{
			window.location = '/PRE/migrationResume.go';
		}
	}
	
	function stopMigration()
	{
		setTabIndex('Migration Control');
		window.location='/PRE/migrationStop.go';
	}
	
	function timedCount()
	{
		t=setTimeout("timedCount()",1000);
		document.migrationForm.submit();
	}
	function doTimer()
	{
		if (!timer_is_on)
		{
		 	timer_is_on=1;
			//timedCount();
		 	addMigrationPageRefresh(2000);
		}
	}

	/*
	 * add a page refresh timer.
	 * milliSeconds cannot be less than 2000 (2 seconds) as it is too fast for this to work.
	 */
	function addMigrationPageRefresh(milliSeconds) {
        if(typeof milliSeconds === "undefined" || isNaN(milliSeconds) || parseInt(milliSeconds) < 2000) {
            milliSeconds = 5000;
        }
        
        if(MIGRATION_REFRESH_TIMEOUT_ID != null) {
            removeTimeout(MIGRATION_REFRESH_TIMEOUT_ID);
        }
        var str = "addMigrationPageRefresh(" + milliSeconds + ")";
        MIGRATION_REFRESH_TIMEOUT_ID = addTimeout(str, milliSeconds);
        document.migrationForm.submit();
    }

    
    if (processStatus=='RUNNING')
    {
        doTimer();
    }
    else if (dbStatus == 'DB_RESET_RUNNING')
    {
        doTimer();
    }
</script>


<div class="panel"> 
	<form id="migrationForm" name="migrationForm" action="${actionUrl}" method="post" ">
	<table width="100%" border="0" cellpadding="6" >
       <tr>
       
     	  	<td width="230">&nbsp;</td>
     	  	<td>&nbsp;</td>
       		<td align="center" colspan="1" style="font-size:x-large"><strong>Migration Control</strong></td>
       		<td align="right" colspan="2">
       			<label style="font-size:large">Migration Status:</label>
       			<label style="font-size:large;color:blue"><c:out value="${migrationState.status}"/></label> 
  			</td>
       </tr>
       <tr>
	     <td>&nbsp;</td>
	     <td>&nbsp;</td>
	     <td>&nbsp;</td>
	     <td>&nbsp;</td>
	     <td>&nbsp;</td>
       </tr>
     
       <tr>
    		<td>&nbsp;</td>
    		
    		<td width="180">
    			<big>
    				<strong>Overall Migration Progress</strong>
   				</big>   
			</td>
			<script type="text/javascript">makeBar("progress",30); setProgressWidth('progress', '<c:out value="${migrationState.overAllProgress}%"/>'); </script>
			<td>&nbsp;</td>
    	</tr>
    	 <tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Database Reset</strong>
   			</td>
   			<script type="text/javascript">makeBar("progress10", 20); setProgressWidth('progress10', '<c:out value="${dbProgress}"/>%');</script>
   			<td>&nbsp;</td>
    	</tr>													
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Drug Units - 50.607</strong>
   			</td>
   			
   			<script type="text/javascript">makeBar("progress0", 20); setProgressWidth('progress0', '<c:out value="${migrationState.migratedDrugUnit.percent}%"/>');</script>
   			<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
	   			<strong>VA Dispense Unit - 50.64</strong>
   			</td>
    		<script type="text/javascript">makeBar("progress1", 20); setProgressWidth('progress1', '<c:out value="${migrationState.migratedVaDispenseUnit.percent}%"/>');</script>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>VA Generic Name - 50.6</strong>
   			</td>
   			<script type="text/javascript">makeBar("progress2", 20); setProgressWidth('progress2', '<c:out value="${migrationState.migratedVaGenericName.percent}%"/>');</script>
   			<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Dosage Form - 50.606</strong>
   			</td>
   			<script type="text/javascript">makeBar("progress3", 20); setProgressWidth('progress3', '<c:out value="${migrationState.migratedDosageForm.percent}%"/>');</script>
   			<td>&nbsp;</td>
   		</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Drug Class - 50.605</strong>
   			</td>
			<script type="text/javascript">makeBar("progress4", 20); setProgressWidth('progress4', '<c:out value="${migrationState.migratedDrugClass.percent}%"/>');</script>
			<td>&nbsp;</td>
   		</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Drug Ingredients - 50.416</strong>
   			</td>
    		<script type="text/javascript">makeBar("progress5", 20); setProgressWidth('progress5', '<c:out value="${migrationState.migratedIngredients.percent}%"/>');</script>
    		<td>&nbsp;</td>
   		</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>Orderable Items CSV File</strong>
   			</td>
   			<script type="text/javascript">makeBar("progress6",20); setProgressWidth('progress6', '<c:out value="${migrationState.migratedOrderableItemsCsv.percent}%"/>');</script>
   			<td>&nbsp;</td>
   		</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>VA Product - 50.68</strong>
   			</td>
    		<script type="text/javascript">makeBar("progress8", 20); setProgressWidth('progress8', '<c:out value="${migrationState.migratedVaProduct.percent}%"/>');</script>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td width="150">
    			<strong>NDC CSV File</strong>
   			</td>
  				<script type="text/javascript">makeBar("progress9", 20); setProgressWidth('progress9', '<c:out value="${migrationState.migratedNDCCsvFile.percent}%"/>');</script>
   			<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td>&nbsp;</td>
    		<td>&nbsp;</td>
    		<td colspan="1">
    			<c:choose>
    				<c:when test="${migrationState.status == 'DB_RESET_RUNNING'}">
    					<input disabled type="button" id="pauseResume" name="pauseResume" value="   ${migrationState.pauseButtonText}   " />
    				</c:when>
    				<c:otherwise>
    					<input type="button" id="pauseResume" name="pauseResume" value="   ${migrationState.pauseButtonText}   " onclick="javascript:togglePauseButton();" />
    				</c:otherwise>
    			</c:choose>
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input type="button" name="Stop Migration" value="    Stop    " onclick="stopMigration();"/>
        	</td>
	    	<td>&nbsp;</td>
	    	<td>&nbsp;</td>
    	</tr> 
	</table>
</form></div>