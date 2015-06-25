/**
 * Copyright 2008, Southwest Research Institute
 * 
 * Get timeout information concerning the user's current session.
 */

var SESSION_TIMEOUT_ALERT_ID;
var WARNING_MSG_TIMEOUT_ID;
var DO_LOGOUT = true;
 
/**
 * Parse the XML/HTML response for the session timeout, max inactive interval, and last accessed time.
 * Use these values to reset the session information and set a new session timeout alert.
 * 
 * @param responseText response received (likely by AJAX call) to parse for session timeout information
 */ 
function parseSessionTimeout(responseText) {
    var timeout = getHtmlById(responseText, "session.timeout");
    var lastAccessedTime = getHtmlById(responseText, "session.last.accessed.time");
    //alert(timeout + "\n" + lastAccessedTime);
    var maxInactiveInterval = getHtmlById(responseText, "session.max.inactive.interval") * 1000;
    var userLoggedIn = getHtmlById(responseText, "user.logged.in");

    resetSessionTimeout(timeout, lastAccessedTime, maxInactiveInterval, userLoggedIn);
}

/**
 * Set the session timeout, max inactive interval, and last accessed time and set a new session timeout alert.
 * 
 * @param timeout milliseconds representing the date when the session will timeout
 * @param lastAccessedTime milliseconds representing the date when the session was last accessed
 * @param maxInactiveInterval milliseconds representing how long a session can be inactive before it will timeout
 */ 
function resetSessionTimeout(timeout, lastAccessedTime, maxInactiveInterval, userLoggedIn) {
    setSessionTimeout(timeout);
    setSessionLastAccessedTime(lastAccessedTime);
    setSessionMaxInactiveInterval(maxInactiveInterval);
    setUserLoggedIn(userLoggedIn);
    
    setSessionTimeoutAlert();
}

/**
 * Set an alert to appear a minute before the session is about to timeout.
 * 
 * If this window has an opener (i.e., this window is a pop-up), recursively reset the session timeout information and set
 * an alert on the opener's window, not on this pop-up. This makes all alerts appear from the parent window, no matter how
 * many pop-ups are showing and should keep all windows up-to-date with the latest session timeout information.
 */
function setSessionTimeoutAlert() {
    if (window.opener) {
        window.opener.resetSessionTimeout(getSessionTimeout().getTime(), getSessionLastAccessedTime().getTime(), 
            getSessionMaxInactiveInterval(), isUserLoggedIn());
    } else {
        if (SESSION_TIMEOUT_ALERT_ID != null) {
            removeTimeout(SESSION_TIMEOUT_ALERT_ID);
        }
        
        if(isUserLoggedIn()) {
            SESSION_TIMEOUT_ALERT_ID = addTimeout("confirmSessionTimeout()", getMillisecondsToSessionTimeout() - 60000);
        }
    }
}

/**
 * function to test IE version requirement
 */
function isOldIE() {
    if(BrowserDetect.browser == "Explorer" && BrowserDetect.version < 9) {
        return true;
    }
    return false;
}

/**
 * Display a confirmation dialog when the session is about to timeout to allow user to continue.
 */
function confirmSessionTimeout() {
    DO_LOGOUT = true;
    
    removeTimeout(SESSION_TIMEOUT_ALERT_ID);
    removeTimeout(WARNING_MSG_TIMEOUT_ID);
    
    if(!isUserLoggedIn()) {
        return;
    }

    var message = window.document.getElementById("session.timeout.message").innerHTML;
    
    if(!isOldIE()) {
        // popup modal prompting user to continue session, if IE9.  Older versions of IE don't get a warning.
        Modalbox.show($('session.timeout.message'), {title: "Session logout warning", width: 600});
    }

    // after 55 seconds, logoutPart1() is called.
    WARNING_MSG_TIMEOUT_ID = addTimeout("logoutPart1()", 55000);
}

/**
 * Function will process a user's logout if executed.
 */
function logoutPart1() {
    var autosaveBtn = document.getElementById("button.autosave.button");
    
    if(DO_LOGOUT && typeof(autosaveBtn) != 'undefined' && autosaveBtn != null) {
        
        // on an edit page, allow the autosave button to process the logout.
        closeWarningAlert();
        clearTimeouts();
        autosaveBtn.click();
    } else if(DO_LOGOUT) {
        logoutPart2();
    }
}

/**
 * Log a user out of the system
 */
function logoutPart2() {
    var now = new Date();
    var sessionTimeout = new Date();
    sessionTimeout.setTime(getSessionTimeoutMillis());
    
    // if the user waited too long to click OK, logoff the user
    if (!isUserLoggedIn() || DO_LOGOUT || now.getTime() > sessionTimeout.getTime()) {
        clearTimeouts();
        closeWarningAlert();
        addTimeout("logoutPart3();", 1000)
    }

    DO_LOGOUT = true;
}

function logoutPart3() {
    clearTimeouts();
    closeWarningAlert();
    var sessionEndedMessage = window.document.getElementById("session.ended").innerHTML;
    alert(sessionEndedMessage);
    window.location.assign("/PRE/logout.go");
}
/**
 * remove the warning message timeout
 */
function clearWarningTimeout() {
    if(WARNING_MSG_TIMEOUT_ID != null) {
        removeTimeout(WARNING_MSG_TIMEOUT_ID);
    }
}

/**
 * close the warning alert
 */
function closeWarningAlert() {
    if(Modalbox.initialized && !isOldIE()) {
        Modalbox.hide();
    }
}

/**
 * Get the milliseconds from now that the session will timeout.
 */
function getMillisecondsToSessionTimeout() {
    var currentTime = new Date();
    
    return getSessionTimeout().getTime() - currentTime.getTime();
}

/**
 * Get the Date that the session will timeout.
 */
function getSessionTimeout() {    
    var timeoutMilliseconds = getSessionTimeoutMillis();
    var sessionTimeout = new Date();
    sessionTimeout.setTime(timeoutMilliseconds);
// alert("millis: " + timeoutMilliseconds)
// alert("date: " + sessionTimeout.toString());
    return sessionTimeout;
}

/**
 * get the session timeout value from the page.
 * 
 * @returns the timeout date-time in milliseconds
 */
function getSessionTimeoutMillis() {
    var t = window.document.getElementById("session.timeout").innerHTML;
    var timeoutMilliseconds = parseInt(t.replace(/^\s+|\s+$/g, ''));
    // alert("'" + timeoutMilliseconds + "'");
    return timeoutMilliseconds;
}
/**
 * Set the session timeout.
 * 
 * @param sessionTimeout milliseconds since January 1, 1970 GMT when the session will timeout
 */
function setSessionTimeout(sessionTimeout) {
    var d = new Date();
    d.setTime(sessionTimeout);
    //alert("setSessionTimeout: " + d.toString());
    window.document.getElementById("session.timeout").innerHTML = sessionTimeout;
}

/**
 * Get the Date that the session was last accessed.
 */
function getSessionLastAccessedTime() {
    var lastAccessedTime = new Date();
    var timeMilliseconds = window.document.getElementById("session.last.accessed.time").innerHTML;
    lastAccessedTime.setTime(timeMilliseconds);
    
    return lastAccessedTime;
}

/**
 * Set the milliseconds that the session was last accessed.
 * 
 * @param lastAccessedTime milliseconds since January 1, 1970 GMT when the session was last accessed
 */
function setSessionLastAccessedTime(lastAccessedTime) {
    window.document.getElementById("session.last.accessed.time").innerHTML = lastAccessedTime;
}

/**
 * Get the milliseconds of the max inactive interval for the user's session.
 */
function getSessionMaxInactiveInterval() {
    return window.document.getElementById("session.max.inactive.interval").innerHTML * 1000;
}

/**
 * Set the milliseconds of the max inactive interval for the user's session.
 * 
 * @param maxInactiveInterval milliseconds since January 1, 1970 GMT of the max inactive interval for the user's session
 */
function setSessionMaxInactiveInterval(maxInactiveInterval) {
    window.document.getElementById("session.max.inactive.interval").innerHTML = maxInactiveInterval / 1000;
}

/**
 * Return true if the user was logged in the last time this page was rendered. A true value does not guarantee that the
 * user is still logged in, only that the user was logged in when the page was rendered.
 * 
 * @return boolean true if the user was logged in when the page rendered
 */
function isUserLoggedIn() {
    var loggedIn = window.document.getElementById("user.logged.in").innerHTML;
    
    return loggedIn.toLowerCase().indexOf("true") != -1;
}

/**
 * Set if the user is logged in.
 * 
 * @param userLoggedIn boolean true/false
 */
function setUserLoggedIn(userLoggedIn) {
    window.document.getElementById("user.logged.in").innerHTML = userLoggedIn;
}

/**
 * will abort a logout process
 */
function skipLogout() {
    clearWarningTimeout();

    DO_LOGOUT = false;
    
    closeWarningAlert();    
    clearTimeouts();
    
    var responseText = new PPSNAjax().get().responseText;
    parseSessionTimeout(responseText);
    responseText = new PPSNAjax().get().responseText;
    parseSessionTimeout(responseText);
    
    logoutPart2();
}