/**
 * Copyright 2008, Southwest Research Institute
 *
 * Methods called to add/remove timeouts and intervals. 
 */
 
var TIMEOUTS = [];
var INTERVALS = [];
var MIGRATION_REFRESH_TIMEOUT_ID;


/**
 * Set a timeout, saving its ID to the timeouts array.
 *
 * @param statement code snippet to run after timeout milliseconds
 * @param timeout milliseconds after which to run statement
 * @return ID of new timeout 
 */
function addTimeout(statement, timeout) {
	var id = window.setTimeout(statement, timeout);
	TIMEOUTS[TIMEOUTS.length] = id;
	
	return id;
}

/**
 *	Clear the timeout with the given ID.
 *
 * @param timeout ID to clear
 */
function removeTimeout(timeout) {
    if(timeout != null) {
    	window.clearTimeout(timeout);
    	
    	for (var i = 0; i < TIMEOUTS.length; i++) {
    		if (TIMEOUTS[i] == timeout) {
    			TIMEOUTS[i] = null;
    		}
    	}
    }
}

/**
 * Clear all saved timeouts.
 */
function clearTimeouts() {
	for (var i = 0; i < TIMEOUTS.length; i++) {
		window.clearTimeout(TIMEOUTS[i]);
	}
}

/**
 * Set an interval, saving its ID to the intervals array.
 *
 * @param statement code snippet to run after every timeout milliseconds
 * @param timeout interval of milliseconds to run statement
 * @param ID of new interval
 */
function addInterval(statement, timeout) {
	var id = window.setInterval(statement, timeout);
	INTERVALS[INTERVALS.length] = id;
	
	return id;
}

/**
 *	Clear the interval with the given ID.
 *
 * @param interval ID to clear
 */
function removeInterval(interval) {
    if(interval != null) {
    	window.clearTimeout(interval);
    	
    	for (var i = 0; i < INTERVALS.length; i++) {
    		if (INTERVALS[i] == interval) {
    			INTERVALS[i] = null;
    		}
    	}
    }
}


/**
 * Clear all saved intervals.
 */
function clearIntervals() {
	for (var i = 0; i < INTERVALS.length; i++) {
		window.clearInterval(INTERVALS[i]);
	}
}

/**
 * cancel migration refresh timer.
 */
function cancelMigrationRefresh() {
    //var foo = "The MIGRATION_REFRESH_TIMEOUT_ID (" + MIGRATION_REFRESH_TIMEOUT_ID + ") is now: ";
    removeTimeout(MIGRATION_REFRESH_TIMEOUT_ID);
    //foo += "" + MIGRATION_REFRESH_TIMEOUT_ID;
    //alert(foo);
}