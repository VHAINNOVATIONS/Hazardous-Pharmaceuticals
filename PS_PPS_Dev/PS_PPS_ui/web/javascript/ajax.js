/**
 * Copyright 2008, Southwest Research Institute
 *
 * Send AJAX requests.
 */

/**
 * Create an XMLHttpRequest.
 */
function getXmlHttpRequest() {
	var xmlHttp = null;
	
	try {
 		// Firefox, Opera 8.0+, Safari
 		xmlHttp = new XMLHttpRequest();
 	}
	catch (e) { 
		// Internet Explorer
 		try {
   			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
   		}
 		catch (e) {
   			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
   		}
 	}
 	
 	if(xmlHttp.overrideMimeType) {
		xmlHttp.overrideMimeType("text/xml");
	}	
 	 	
	return xmlHttp;
}

/**
 * Send an AJAX request
 * If you are sending this to a specific Spring URL, set the event id to <path>.go (e.g. edit.go)
 * and send the parameters as a string (e.g. entityType=foo&id=bar)
 */
function PPSNAjax(eventId, parameters, submitFromParent, isSpringCall) {
	
	
	if(!isSpringCall) {
		this.parameters = createParameters(eventId, parameters);
	}
	this.submitFromParent = submitFromParent ? true : false;
	
	/**
 	 * Send an asynchronous GET AJAX request. The given callbackMethod must accept one parameter XMLHttpRequest containing
 	 * the response text.
 	 *
 	 * @param callbackMethod method to asynchronously call with response text
 	 */
	this.get = function(callbackMethod) {
		var request = getXmlHttpRequest();
		if(isSpringCall) {
			request.open("GET", createSpringAjaxUrl(eventId, parameters ), true);
		} else {	
			request.open("GET", createAjaxUrl(this.parameters, this.submitFromParent), true);
		}
		
		request.onreadystatechange = function() {
    		if (request.readyState == 4 && request.status == 200) {
      			if (request.responseText) {
          			callbackFunction(request);
      			}
    		}
  		};

		request.send(null);
 	}

 	/**
 	 * Send a synchronous GET AJAX request. The XMLHttpRequest is returned to the caller.
 	 *
 	 * @return response text
 	 */
	this.get = function() {
		var request = getXmlHttpRequest();
		if(isSpringCall) {
			request.open("GET", createSpringAjaxUrl(eventId, parameters ), true);
		} else {	
			request.open("GET", createAjaxUrl(this.parameters, this.submitFromParent), true);
		}
		//request.setRequestHeader("If-Modified-Since", "Thu, 1 Jan 1970 00:00:00 GMT");
		//request.setRequestHeader("Cache-Control", "no-cache");
		
		request.send(null);
		
		return request;
 	}

	/**
 	 * Send an asynchronous POST AJAX request. The given callbackMethod must accept one parameter XMLHttpRequest containing
 	 * the response text.
 	 *
 	 * @param callbackMethod method to asynchronously call with response text
 	 */
 	this.post = function(callbackMethod) {
 		var request = getXmlHttpRequest();
		request.open("POST", createAjaxUrl(this.parameters, this.submitFromParent), true);
 		
		request.onreadystatechange = function() {
    		if (request.readyState == 4 && request.status == 200) {
      			if (request.responseText) {
          			callbackFunction(request);
      			}
    		}
  		};

		request.send(null);
 	}

 	/**
 	 * Send a synchronous POST AJAX request. The response's text is returned to the caller.
 	 *
 	 * @return response text
 	 */
	this.post = function() {
		var request = getXmlHttpRequest();
		request.open("POST", createAjaxUrl(this.parameters, this.submitFromParent), false);
		request.send(null);
		
		return request.responseText;
 	}
 	
 	/**
 	 * Send an asynchronous POST AJAX request to submit the given form. The given callbackMethod must accept one parameter 
 	 * XMLHttpRequest containing the response text.
 	 *
 	 * @param formId DOM element ID for the form to submit
 	 * @param callbackMethod method to asynchronously call with response text
 	 */
 	this.submit = function(formId, callbackMethod) {
 		var request = getXmlHttpRequest();
		request.open("POST", createAjaxUrl(createFormParameters(formId, this.parameters), this.submitFromParent), true);
 		
		request.onreadystatechange = function() {
    		if (request.readyState == 4 && request.status == 200) {
      			if (request.responseText) {
          			callbackFunction(request);
      			}
    		}
  		};
		
		request.send(null);
 	}

 	/**
 	 * Send a synchronous POST AJAX request to submit the given form. The XMLHttpRequest containing the response text 
 	 * is returned to the caller.
 	 *
 	 * @param formId DOM element ID for the form to submit
 	 * @return response text
 	 */
	this.submit = function(formId) {
		var request = getXmlHttpRequest();
		request.open("POST", createAjaxUrl(createFormParameters(formId, this.parameters), this.submitFromParent), false);
		request.send(null);
		
		return request;
 	}
}

/**
 * Return the HTML content for the element in the given HTML text with the given ID.
 *
 * @param text HTML text to search
 * @param id HTML ID to search for
 * @return HTML text for given ID
 */
function getHtmlById(text, id) {
	// search for id:
	var idpos = text.indexOf("id=\""+id+"\"");
    var idtag = text.substring(text.substring(0, idpos).lastIndexOf("<")+1, idpos).split(" ");
	
	for (i = 0; i < idtag.length; i++) {
		if(idtag[i] != "") {
			idtag = idtag[i];
			break;
		}
	}
	
	idpos = text.indexOf(">", idpos) + 1;
	
	// search endtag:
	var alltags = text.substring(idpos).split(idtag);
	var open = 1;
	
    for (i = 0; i < alltags.length; i++) {
		// look for opening AND closing
		if (alltags[i].charAt(alltags[i].length-1) == '<') {
			open++;
		}
		
		if (alltags[i].charAt(alltags[i].length-1) == '/') {
			open--;
		}

		if (open == 0) {
			text = "";
			for (j=0; j <= i; j++)
				text  += alltags[j] + idtag;
			break;
		}
  	}
  	
	return text.substring(0, text.lastIndexOf('<'));
}

/**
 * Create the URL for the AJAX call to call.<b> 
 *
 * @param parameters (optional) parameters to use
 * @return URL
 */
function createAjaxUrl(parameters, submitFromParent) {
	var locationToSubmit;
	
	if (submitFromParent && window.opener) {
		locationToSubmit = window.opener.location;
	}
	else {
		locationToSubmit = window.location;
	}
	
	var url = locationToSubmit.href;
	
	var ctx = "${pageContext.request.contextPath}"
	
//	alert(url.indexOf("/PRE/"));
//	alert(url.substring(0, url.indexOf("/PRE/")+5))
	
	// Spring Web Flow has problems parsing the flow execution key if there is a hash mark in the URL
	//url = url.replace(locationToSubmit.hash, "");
	
	if(url.indexOf("?") == -1) {
		url += "?";
	}
	
	if (parameters != null && parameters != "") {
		url += "&" + parameters;
	}	
	
	// force a new URL so that the browser doesn't cache the response
	var currentTime = new Date().getTime();
	
//	if(url.indexOf("&") != -1) {
//		url += "&";
//	}
	url += "&time=" + encodeURIComponent(currentTime);
	
//	alert(url);
	
	return url;
}

function createSpringAjaxUrl(dotGoPath, parameters) {
	var url = window.location.href;
	url = url.substring(0, url.indexOf("/PRE/")+5);
	
	if (dotGoPath != null && dotGoPath != "") {
		url += dotGoPath;
	}
	
	if(url.indexOf("?") == -1) {
		url += "?";
	}
		
	if (parameters != null && parameters != "") {
		url += "&" + parameters;
	}	
	
	// force a new URL so that the browser doesn't cache the response
	var currentTime = new Date().getTime();

	url += "&time=" + encodeURIComponent(currentTime);
	
	
	return url;
}

/**
 * Create the request parameter for the given event ID.
 *
 * @param eventId Spring Web Flow event ID
 */
function createEventParameter(eventId) {
	var eventParameter;
	
	if (eventId != null) {
		eventParameter = "_eventId=" + encodeURIComponent(eventId) + "&_eventId_" + encodeURIComponent(eventId) + "=true";
	}
	else {
		eventParameter = null;
	}
	
	return eventParameter;
	
}

/**
 * Add the given eventId to the parameters.
 *
 * @param eventId Spring Web Flow event ID
 * @param parameters other parameters
 */
function createParameters(eventId, parameters) {
	var event = createEventParameter(eventId);
	var result;
	
	if (event == null && parameters == null) {
		result = "";
	}
	else if (event == null && parameters != null) {
		result = parameters;
	}
	else if (event != null && parameters == null) {
		result = event;
	}
	else {
		result = event + "&" + parameters;
	}
	
	return result;
}

/**
 * Create the URL parameters for the given form.
 *
 * @param formId Form ID in DOM to turn into parameter
 * @return form parameters
 */
function createFormParameters(formId, parameters) {
	var additionalParameters = parameters;
	
	if (additionalParameters == null) {
		additionalParameters = "";
	}
	
	
	var form = document.getElementById(formId);
	
	for (var i = 0; i < form.elements.length; i++) {
		if (additionalParameters.length > 0) {
			additionalParameters += "&";
		}
		
		additionalParameters += this.createElementParameter(form.elements[i]);
	}
	
	return additionalParameters;
}

/**
 * Create the URL parameters for the given form element.
 * 
 * @param element Element DOM to create parameters for
 * @return boolean true if there was a change in the given element, else false
 */
function createElementParameter(element) {
	var parameter = element.name + "=";
	
	if (element.type == 'checkbox' || element.type == 'radio') {
		parameter += encodeURIComponent(element.checked);
	}
	else if (element.type == 'file' || element.type == 'password' || element.type == 'text' || element.type == 'textarea') {
		parameter += encodeURIComponent(element.value);
	}
	else if (element.type == 'select-one' || element.type == 'select-multiple') {
		parameter = "";
		
		for (var i = 0; i < element.options.length; i++) {
			if (parameter.length > 0) {
				parameter += "&";
			}
			
			if (element.options[i].selected) {
				parameter += element.name + "=" + encodeURIComponent(element.options[i].value);
			}
		}
	}
	
	return parameter;
}