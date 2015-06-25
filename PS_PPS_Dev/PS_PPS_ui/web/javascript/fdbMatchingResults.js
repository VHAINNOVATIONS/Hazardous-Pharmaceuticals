	function selectCheckBox(index)
	{
	    //alert("you selected checkbox :" + index);
	}
	

	function selectAllCheckBoxes(checkbox)
	{
	    var checked = true;
	    var items = document.matchResultForm.fdbItem;

	    if(checkbox.checked)
	    {
	        for(var i=0; i<items.length; i++){
	          	// Check if check-box is disabled
	        	if(items[i].disabled == false) {
	        		items[i].checked = true;
	        	}
	        }
	    }
	    else{
	        for(var i=0; i<items.length; i++){
	            items[i].checked = false;
	        }
	        
	    }
	}
	

    function addNdcToProduct(productlist)
    {
    	if(productlist == 'true')
    	{
    		alert("There are no products found! ");
    		return false;
    	}	
    	
    	for(i=0; i<document.matchResultForm.elements.length; i++)
    	{
    		var elementName = document.matchResultForm.elements[i].name;
    		if(elementName == 'cbProductItem') {
    		    if(document.matchResultForm.elements[i].checked)
    		    {
    		    	 var selectedValue = document.matchResultForm.elements[i].value;
    		         document.getElementById("productId").value = selectedValue;
    		    }
    		}
    	}
//        document.matchResultForm.action = '/PRE/addNdcToProducts.go';
//        document.matchResultForm.submit();
    }
    
    function useBlankTemplate(url)
    {
        document.matchResultForm.action = url;
        document.matchResultForm.submit();
    }
    
    function useExistingProduct(productlist)
    {
        if(productlist == 'true')
        {
            alert("There are no products found!");
            return false;
        }
        for(i=0; i<document.matchResultForm.elements.length; i++)
        {
            var elementName = document.matchResultForm.elements[i].name;
            if(elementName == 'cbProductItem') {
                if(document.matchResultForm.elements[i].checked)
                {
                     var selectedValue = document.matchResultForm.elements[i].value;
                     document.getElementById("productId").value = selectedValue;
                }
            }
        }
        
//        document.matchResultForm.action = '/PRE/existingTemplate.go';
//        document.matchResultForm.submit();
    }
    
    function cancels()
    {
  	    window.location = '/PRE/cancel.go';
    }

    function displayFdbDetails(ndc)
    {
        window.location = '/PRE/fdbDetails.go?ndc='+ndc;
    }
    
    
    function displayProductDetails(id)
    { 
    	window.location = '/PRE/fdbAddProductDetails.go?id='+id;
    }
    
    function searchProducts()
    {  
    	window.location = '/PRE/associateProduct.go?entityType=product&isFirstRun=true';
    }
    
    