/**
 * @author Smith
 * @Working Whole script
 */


function display_c() {
		var refresh = 1000; // Refresh rate in milli seconds
		mytime = setTimeout('display_ct()', refresh);
	}

	$("#submit").click(function() {
		alert("hi");
		$("#myForm").submit();
	});



