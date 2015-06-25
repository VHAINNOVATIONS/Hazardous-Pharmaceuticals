/**
 * Copyright 2008, Southwest Research Institute
 *
 * Provides cookie functions. Prefixes all cookies to be unique to the PEPS application.
 */

var PEPS_COOKIE_PREFIX = "gov.va.med.pharmacy.peps.";

/**
 * This function is used to set cookies 
 * 
 * @param name cookie name
 * @param value cookie value
 * @param expires (optional) Date cookie expires
 * @param path (optional) path cookie is valid for
 * @param domain (optional) domain cookie is valid for
 * @param secure (optional) set the cookie to use SSL 
 */
function setCookie(name, value, expires, path, domain, secure) {
  document.cookie = PEPS_COOKIE_PREFIX + name + "=" + escape(value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + 
    ((secure) ? "; secure" : "");
}

function set_cookie ( cookie_name, cookie_value,
	    lifespan_in_days, valid_domain, secure )
	{
	    var domain_string = valid_domain ?
	                       ("; domain=" + valid_domain) : '' ;
	    document.cookie =  PEPS_COOKIE_PREFIX + cookie_name +
	                       "=" + encodeURIComponent( cookie_value ) +
	                       "; max-age=" + 60 * 60 *
	                       24 * lifespan_in_days +
	                       "; path=/" + domain_string +
	                       ((secure) ? "; secure" : "");
	}


function get_cookie ( cookie_name )
{
    var cookie_string = document.cookie ;
    cookie_name = PEPS_COOKIE_PREFIX + cookie_name;
    if (cookie_string.length != 0) {
        var cookie_value = cookie_string.match (
                        '(^|;)[\s]*' +
                        cookie_name +
                        '=([^;]*)' );
        return decodeURIComponent ( cookie_value[2] ) ;
    }
    return '' ;
}


/**
 * This function is used to get cookies 
 * 
 * @param name cookie name
 */
function getCookie(name) {
	var prefix = PEPS_COOKIE_PREFIX + name + "=";
	var start = document.cookie.indexOf(prefix);
	
	if (start == -1) {
		return null;
	}

	var end = document.cookie.indexOf(";", start + prefix.length);

	if (end == -1) {
		end=document.cookie.length;
	}

	var value = document.cookie.substring(start + prefix.length, end);

	return unescape(value);
}

/**
 * This function is used to delete cookies 
 *
 * @param name cookie name
 * @param path (optional) path cookie is valid for
 * @param domain (optional) domain cookie is valid for
 */
function deleteCookie(name, path, domain) {
  if (getCookie(name)) {
    document.cookie = PEPS_COOKIE_PREFIX + name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; max-age=0";
  }
}

/**
 * Delete all PEPS cookies.
 */
function deleteAllCookies() {
	var cookies = window.document.cookie.split(";");
	
	for (var i = 0; i < cookies.length; i++) {
		var cookie = cookies[i];
		var eqPos = cookie.indexOf("=");
		var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
		
		if (name.indexOf(PEPS_COOKIE_PREFIX) != -1) {
        	window.document.cookie = name + "=; max-age=0";
        }
	}
}

/**
 * Test if the user just came from the KAAJEE login screen. If so, delete all PEPS cookies.
 */
function deleteCookiesOnLogin() {
	var referrer = window.document.referrer;
	var index = referrer.indexOf("login/login.jsp"); // partial URL for KAAJEE login
	
	if (index != -1) {
		deleteAllCookies();
	}
}

/** 
 * Reset the timeout for the given cookie to the current session timeout date.
 *
 * @param name cookie to reset
 */
function resetCookieTimeout(name) {
	var value = getCookie(name);
	
	if (value != null) {
		set_cookie(name, value, getSessionTimeout());
	}
}