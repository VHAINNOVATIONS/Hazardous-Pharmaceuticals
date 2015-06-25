
	function refreshScreen()
    {
           window.location = '/PRE/status.go?&command=STATUS';
    }

    function scheduleJob(count, command, jobName)
    {
    	 var hourObj = document.getElementById('hour'+count);
         var hours = hourObj.options[hourObj.selectedIndex].value;
           
         var minsObj = document.getElementById('mins'+count);
         var minutes = minsObj.options[minsObj.selectedIndex].value;
         window.location = '/PRE/jobStart.go?jobName='+jobName+'&command='+command+"&hour="+hours+"&mins="+minutes;
    }
    
    function executeJob(count)
    {
        var selObj2 = document.getElementById('jobCommandValues'+count);
        var selectValue = selObj2.options[selObj2.selectedIndex].value;
        var selectText = selObj2.options[selObj2.selectedIndex].text;
        
        var hourObj = document.getElementById('hour'+count);
        var hourValue = hourObj.options[hourObj.selectedIndex].value;
        var hourText = hourObj.options[hourObj.selectedIndex].text;
        
        var minsObj = document.getElementById('mins'+count);
        var minsValue = minsObj.options[minsObj.selectedIndex].value;
        var minsText = minsObj.options[minsObj.selectedIndex].text;
           
        window.location = '/PRE/jobStart.go?jobName='+selectValue+'&command='+selectText+"&hour="+hourValue+"&mins="+minsValue;
    }
    
    function refreshScreen()
    {
        window.location = '/PRE/status.go?&command=STATUS';
    }
    
    function schedulerCommand(command)
    {
    	  window.location = '/PRE/startScheduler.go?&command='+command;
    }
    
    function disableButton()
    {
         document.getElementById('startButton1').disabled = true;
    }
         
    function toggleMessaging(toggle)
    {
    	var messagingState = false;
    	
        if(toggle == 'ON') {
        	document.getElementById('onButton').disabled = true;
        	document.getElementById('offButton').disabled = false;
        	messagingState = true;
        }
        else {
        	document.getElementById('offButton').disabled = true;
        	document.getElementById('onButton').disabled = false;
        	messagingState = false;
        }
        window.location = '/PRE/updateMessagingState.go?messagingState='+messagingState;
    }

    function updateHostName()
    {
    	var hostName = document.getElementById("fdaHostName").value;
    	window.location = '/PRE/updateHostName.go?&hostName='+hostName;
    }
