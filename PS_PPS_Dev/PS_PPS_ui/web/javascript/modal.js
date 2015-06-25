function SessionModal() {
	this.square = null;
	this.overdiv = null;

	this.popOut = function(msgtxt) {
		// filter:alpha(opacity=25);-moz-opacity:.25;opacity:.25;
		this.overdiv = document.createElement("div");
		this.overdiv.className = "overdiv";

		this.square = document.createElement("div");
		this.square.className = "square";
		this.square.Code = this;
		var msg = document.createElement("div");
		msg.className = "msg";
		msg.innerHTML = msgtxt;
		this.square.appendChild(msg);
		var closebtn = document.createElement("button");
		closebtn.onclick = function() {
			this.parentNode.Code.popIn();
		}
		closebtn.innerHTML = "Close";
		this.square.appendChild(closebtn);

		document.body.appendChild(this.overdiv);
		document.body.appendChild(this.square);
	}
	this.popIn = function() {
		if (this.square != null) {
			document.body.removeChild(this.square);
			this.square = null;
		}
		if (this.overdiv != null) {
			document.body.removeChild(this.overdiv);
			this.overdiv = null;
		}

	}
}
