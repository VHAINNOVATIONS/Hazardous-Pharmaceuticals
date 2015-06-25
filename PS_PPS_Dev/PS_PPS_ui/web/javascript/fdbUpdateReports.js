
    function selectAllCheckBoxes(checkbox, type)
    { 
        var checked = true;
        
        if(type == 'ADD')
        {
        	var items = document.addFdbForm.ndcNo;
        }else {	   
        	var items = document.addFdbForm.eplIds;
        }

        if(checkbox.checked)
        {
            for(var i=0; i<items.length; i++){
                   items[i].checked = true;
            }
        }
        else{
            for(var i=0; i<items.length; i++){
                items[i].checked = false;
            }
            
        }
    }
    
    function deleteSelectedItem(url)
    {
        document.addFdbForm.action = url;
        document.addFdbForm.submit();   
    }
    
    
    function displayFdbUpdateDetails(id, type)
    {
        window.location = '/PRE/fdbUpdateDetails.go?id='+id+'&entityType=' + type;
    }
    
    function displayFdbAddDetails(id)
    {
        window.location = '/PRE/fdbAddDetails.go?id='+id;
    }
    
    function displayMatchingResults(url)
    {
        document.addFdbForm.action = url;
        document.addFdbForm.submit();
    } 
    
    function performSearch()
    {
        document.searchForm.submit();
        document.getElementById('submitSearch').disabled = true;
    }
