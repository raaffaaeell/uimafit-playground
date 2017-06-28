//global variable that prevents info to change so it can be viewed with ease
var freezeInfo = false;

$(document).ready(function() {

	var chk = $(".chk");

	chk.prop('checked', true);
	var textPreSpan = $("#text pre span");

	chk.change(function() {
		var type = $(this).attr("value");
		var inactive = type + 'Inactive';
		if ($(this).is(":checked")) {
			$('.' + inactive).toggleClass(type);
			$('.' + inactive).toggleClass(inactive);
		} else {
			$('.' + type).toggleClass(inactive);
			$('.' + type).toggleClass(type);
		}
		
		$("#info").html("");
		freezeInfo = false;
		
	}).change();


	textPreSpan.mouseover(function() {
		printInfo($(this));
	})

	textPreSpan.mouseout(function() {
		if (!freezeInfo) {
			$("#info").html("");
		}
	})

	$("#text pre").click(function() {
		freezeInfo = !freezeInfo;
	})

});

function printInfo(elem) {

	if (!freezeInfo) {
		var cssClasses = elem.attr("class").split(" ");
		var text = elem.text();
		var targetDiv = $("#info");

		var html = "";

		$.each(cssClasses, function(i, cssClass) {
			var test = cssClass.indexOf("Inactive") !== -1;
			//if the checkbox of the annotation isnt checked dont show info
			if (!(cssClass.indexOf("Inactive") !== -1)) {
				html += "<p class ='" + cssClass + "'>" + text + "</p>";
				html += "<p class ='text-info'>";
				$.each(elem.data(), function(i, data) {
					html += i + ": " + data + " ";
				});
			}
		});

		targetDiv.append(html);
	}
}