   
    function selectAllCheckBoxes(checkbox, type)
    {
        var checked = true;
        if(type == 'ADD')
        {
        	var items = document.addFdbForm.fdbItem;
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
    
    function fdbAdddeleteItem(url)
    {
        document.editMultiple.action = url;
        document.editMultiple.method="POST";
        document.editMultiple.submit();   
    }
    
    function deleteSelectedItem(url)
    {
        document.addFdbForm.action = url;
        document.addFdbForm.submit();   

    }
    
    function displayFdbEdit(id, type)
    {
        window.location = '/PRE/fdbUpdateEdit.go?id='+id+'&entityType=' + type;
    }
    
    function fdbMatchingResults(url)
    {
        document.editMultiple.action = url;
        document.editMultiple.method="POST";
        document.editMultiple.submit();
    } 
    
    function hideExistingPPS(url)
    {
    	checkall('select');
    	
        document.editMultiple.action = url;
        document.editMultiple.method="POST";
        document.editMultiple.submit();
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
    
    function displayFdbDetails(ndc)
    { 
    	window.location = '/PRE/fdbDetails.go?ndc='+ndc;
    }
 
    function selectCheckBox(count)
    {
    	//alert("you selected checkbox :" + count);
    }
     		 
    
    
    function downloadCSVFile(file)
    {
    	window.location = '/PRE/exportToCsv.go?file='+file;
    }
    
