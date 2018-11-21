
var focusTarget;

$(document).ready(function() {
	$(function($) {
		document.oncontextmenu = document.onselectstart = document.ondragstart = function() {
			return false;
		}
			
		$(document).mousedown(function () {
			var e = e || event;
			focusTarget = $(e.target)[0].tagName;
		});
		
		$(document).keydown(function(e) {
			var currKey = 0, e = e || event;
			currKey = e.keyCode || e.which || e.charCode;
			switch (currKey) {
				case 8: //BACKSPACE
					if(!/^(input|textarea)/i.test(focusTarget)) {
						e.preventDefault();
					} 
					break;
				case 37: //ALT+ <-
					if (e.altKey) {
						e.preventDefault(); 
					}
					break;
				case 39: //ALT+ ->
					if (e.altKey) {
						e.preventDefault(); 
					}
					break;
				case 87: //CTRL+W
					if (e.ctrlKey) {
						e.preventDefault(); 
					}
					break;
				case 116: //F5
					e.preventDefault(); 
					break;
				case 112: //F1
					e.preventDefault(); 
					break;
				case 114: //F3
					e.preventDefault(); 
					break;
				default:
					break;
			}
		});
	});
});