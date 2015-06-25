/**
 * busyProcess
 * 
 * Add a busy indicator over an element while running the function it invokes
 * 
 * @param {Object}
 *            element clicked on to invoke the task
 * @param {Function}
 *            function to invoke after adding visual indicator
 */
function busyProcess(element, func) {
	var busyIndicator = document.createElement('img');
	busyIndicator.setAttribute('src', 'images/progressBar.gif');

	busyIndicator.setStyle({
		zIndex : "100",
		position : "absolute"
	});
	Position.clone($(element), busyIndicator);

	document.body.appendChild(busyIndicator);

	// function needs to be deferred in order for the browser to render the
	// busy indicator, but we need to wrap it in order to remove the busy
	// indicator
	// when it's done
	func = func.wrap(function(proceed) {
		proceed();
		busyIndicator.remove();
	});
	func.defer();
}
