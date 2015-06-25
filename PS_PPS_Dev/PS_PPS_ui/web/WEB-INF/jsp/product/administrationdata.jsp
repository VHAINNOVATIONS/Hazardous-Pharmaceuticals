<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    <style>
    #dot\.shipping\.name\.editable\.image{
    	padding-left:25px;
    }
    #label_dot\.shipping\.name{
    	width:175px;
    }
     #waste\.sort\.code\.editable\.image{
    	padding-left:25px;
    }
    #label_waste\.sort\.code{
    	width:175px;
    }
     #primary\.epa\.code\.editable\.image{
    	padding-left:25px;
    }
    #label_primary\.epa\.code{
    	width:175px;
    }
    </style>
    
    <script type='text/javascript'>
    //First capture the existing onload function as a variable
    var otherOnLoad = window.onload;
    
    //Next issue a call to the handleDisposeClassChange after calling the existing onload functionality
    //The handleDisposeClassChange call will create the original state of the 3 haz to dispose fields
    
    window.onload = function() {
        otherOnLoad();
        var hazToDisposeCheckBox = document.getElementById('hazardous.to.dispose');     
        handleDisposeClassChange(hazToDisposeCheckBox);
    }
    
    function handleDisposeClassChange(checkbox) {
    	
    	//Get the hidden checkbox and set it's value - see form.js to see the default jsOnClick handler for checkboxes. Replicating the same behavior here
    	var hiddenCheckbox = document.getElementById(checkbox.name+".hidden");
    	
    	//Get hold of the 3 fields for the haz to dispose and set their maxLengths
    	var primaryEpaCodeField = document.getElementById("primary.epa.code");
    	primaryEpaCodeField.maxLength=50;
  		var dotShippingLabel = document.getElementById("dot.shipping.name");
  		dotShippingLabel.maxLength=50;
  		var wasteSortCode = document.getElementById("waste.sort.code");
  		wasteSortCode.maxLength=50;
  		    
    	if(checkbox.checked) {
    		//if checked, set the hidden checkbox to true and if the primaryEpaCode (or any of the other two fields) are read only, remove read only attribute
    		checkbox.value=true;
    		hiddenCheckbox.value = true;
    		
    		 if(primaryEpaCodeField.hasAttribute('readonly')){         	     
    	    	
    	    		 primaryEpaCodeField.removeAttribute('readonly');
    	    		 primaryEpaCodeField.style.background='#FFFFFF';
    	    		 wasteSortCode.removeAttribute('readonly');
    	    		 wasteSortCode.style.background='#FFFFFF';
    	    		 dotShippingLabel.removeAttribute('readonly');
    	    		 dotShippingLabel.style.background='#FFFFFF';
    	    		 
    	    	    }
    		
    	}
    	else{
    		//if unchecked, set the hidden checkbox to false and set the primaryEpaCode to readOnly
    		checkbox.value=false;
    		hiddenCheckbox.value = false;
    		primaryEpaCodeField.setAttribute('readonly', 'readonly');   
	    	primaryEpaCodeField.style.background='#C9C5C7';
	    	wasteSortCode.setAttribute('readonly', 'readonly');   
	    	wasteSortCode.style.background='#C9C5C7';
	    	dotShippingLabel.setAttribute('readonly', 'readonly');   
	    	dotShippingLabel.style.background='#C9C5C7';
    		
    	}
    	   	
    }
    </script>
<div class="panel">


    <fieldset><legend class="legend">High Risk / Hazardous Administration Data</legend>
    <div class="twoColumn">
        <peps:dataField item="${item}" key="patient.specific.label" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField item="${item}" key="do.not.handle.if.pregnant" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField item="${item}" key="witness.to.administration" cssClass="aligncheckbox225 labelreadonly" />   
        <peps:dataField item="${item}" key="follow.up.time" cssClass="aligncheckbox225 labelreadonly" />
        <peps:dataField item="${item}" key="monitor.max.days" />
    </div> 
    <div class="twoColumn">
        <peps:dataField item="${item}" key="high.risk" cssClass="aligncheckbox5 labelreadonly" />    
        <peps:dataField item="${item}" key="high.risk.followup" cssClass="aligncheckbox5 labelreadonly" />
        <peps:dataField item="${item}" key="high.risk.followup.time.period" />      
        <peps:dataField item="${item}" key="hazardous.to.handle" cssClass="aligncheckbox5 labelreadonly" />   
        <peps:dataField item="${item}" key="hazardous.to.dispose" cssClass="aligncheckbox5 labelreadonly"  jsOnClick="handleDisposeClassChange(this);" />
        <peps:dataField item="${item}" key="primary.epa.code"/>
      	<peps:dataField item="${item}"  key="waste.sort.code"  />
      	<peps:dataField  item="${item}" key="dot.shipping.name"  />               
        <peps:dataField item="${item}" key="hazardous.to.patient" cssClass="aligncheckbox5 labelreadonly" />    
    </div>                        
    </fieldset>
    <div class="horizontalspacer"></div>
    <fieldset><legend class="legend">Lab / Vital Look-Up Data</legend>
    <div class="panel">
        <peps:text item="${item}" key="lab.display.order.entry" label="true" cssClass="labelreadonly" />
        <peps:text item="${item}" key="lab.display.finishing.an.order" label="true" cssClass="labelreadonly" />
        <peps:text item="${item}" key="lab.display.administration" label="true" cssClass="labelreadonly" />
        <peps:text item="${item}" key="vitals.display.order.entry"  label="true" cssClass="labelreadonly" />
        <peps:text item="${item}" key="vitals.display.finish.an.order"  label="true" cssClass="labelreadonly" />
        <peps:text item="${item}" key="vitals.display.administration"  label="true" cssClass="labelreadonly"  />   
    </div> 
    </fieldset>
</div>