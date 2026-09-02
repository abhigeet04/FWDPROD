$(document).ready(function() {
	
	$(".editButton").click(function() {
		   var item = $(this).closest("tr").children("td");
		   var val1 = item.eq(0).text().trim();
		   var val2 = item.eq(2).text().trim();
		   var category = item.eq(3).text().trim();
		   var treasuryRefNo = item.eq(14).text().trim();
		   var enquiryValue = val1 + ':' + val2;
		   $.ajaxSetup({ async: false });
		   $('#idAndFwdContractNo').val(enquiryValue);
		   var idAndFwdContractNo = $('#idAndFwdContractNo').val();
		   if (idAndFwdContractNo != '' && category == 'FWCBOOK') {
		       $("#myForm").attr("action", "editForwardContractBooking");
		       $("#myForm").submit();
		   } else if (idAndFwdContractNo != '' && category == 'FWCUTIL') { // ← added
		       $("#myForm").attr("action", "editForwardContractCancellationWithoutRate");
		       $("#myForm").submit();
		   } else if (idAndFwdContractNo != '' && category == 'FWCCANCEL' && treasuryRefNo == '') {
		       $("#myForm").attr("action", "editForwardContractCancellationWithoutRate");
		       $("#myForm").submit();
		   } else if (idAndFwdContractNo != '' && category == 'FWCCANCEL') {
		       $("#myForm").attr("action", "editForwardContractCancellation");
		       $("#myForm").submit();
		   }
		});
					$(".viewButton")
							.click(
									function() {

										var item = $(this).closest("tr")
												.children("td");

										var val1 = item.eq(0).text().trim();

										var val2 = item.eq(2).text().trim();

										var category = item.eq(3).text().trim();

										var screen = $('#screenType').val();

										var enquiryValue = val1 + ':' + val2;

										$.ajaxSetup({
											async : false
										});

										$('#idAndFwdContractNo').val(
												enquiryValue);

										var idAndFwdContractNo = $(
												'#idAndFwdContractNo').val();

										// treasuryRefNo index differs between
										// maker (eq14) and enquiry (eq12)

										var treasuryRefNo = '';

										if (screen == 'EnquiryScreen') {

											treasuryRefNo = item.eq(12).text()
													.trim();

										} else {

											treasuryRefNo = item.eq(14).text()
													.trim();

										}

										if (idAndFwdContractNo != ''
												&& category == 'FWCBOOK') {

											$("#myForm").attr("action",
													"fetchDataView");

											$("#myForm").submit();

										} else if (idAndFwdContractNo != ''
												&& category == 'FWCUTIL') {

											$("#myForm")
													.attr("action",
															"fetchDataCancelViewWithoutRate");

											$("#myForm").submit();

										} else if (idAndFwdContractNo != ''
												&& category == 'FWCCANCEL'
												&& treasuryRefNo == '') {

											$("#myForm")
													.attr("action",
															"fetchDataCancelViewWithoutRate");

											$("#myForm").submit();

										} else if (idAndFwdContractNo != ''
												&& category == 'FWCCANCEL') {

											$("#myForm").attr("action",
													"fetchDataCancelView");

											$("#myForm").submit();

										}

									});
	 

	$(".ApproveOrRejectButton").click(function() {
		var item = $(this).closest("tr").children("td");
		var val1 = item.eq(0).text().trim();
		var val2 = item.eq(2).text().trim();
		var category = item.eq(3).text().trim();

		var enquiryValue = val1 + ':' + val2;

		$.ajaxSetup({
			async : false
		});

		$('#idAndFwdContractNo').val(enquiryValue);
		$('#category').val(category);

		var idAndFwdContractNo = $('#idAndFwdContractNo').val();

		if (idAndFwdContractNo != '' && category=='FWCBOOK') {
			$("#myForm").attr("action", "checkerProcessActionView");
			$("#myForm").submit();
		}
		else if(idAndFwdContractNo != '' && category=='FWCCANCEL')
			{
			$("#myForm").attr("action", "checkerProcesscancelActionView");
			$("#myForm").submit();
			}

	});
	
	//ABHISHEK
	$(document).ready(function() {
		 $(".ApproveOrRejectButtonWithoutRate").click(function() {
		   var item = $(this).closest("tr").children("td");
		   var val1 = item.eq(0).text().trim();
		   var val2 = item.eq(2).text().trim();
		   var category = item.eq(3).text().trim();
		   var enquiryValue = val1 + ':' + val2;
		   $.ajaxSetup({ async: false });
		   $('#idAndFwdContractNo').val(enquiryValue);
		   $('#category').val(category);
		   var idAndFwdContractNo = $('#idAndFwdContractNo').val();
		   if (idAndFwdContractNo != '') {
		     $("#myForm").attr("action", "checkerProcessCancelWithoutRateActionView");
		     $("#myForm").submit();
		   }
		 });
		 $(".viewButtonWithoutRate").click(function() {
		   var item = $(this).closest("tr").children("td");
		   var val1 = item.eq(0).text().trim();
		   var val2 = item.eq(2).text().trim();
		   var enquiryValue = val1 + ':' + val2;
		   $.ajaxSetup({ async: false });
		   $('#idAndFwdContractNo').val(enquiryValue);
		   var idAndFwdContractNo = $('#idAndFwdContractNo').val();
		   if (idAndFwdContractNo != '') {
		     $("#myForm").attr("action", "checkerProcessCancelWithoutRateActionView");
		     $("#myForm").submit();
		   }
		 });
		});
	
	


	$("#div1").delay(8000).hide(100, function() {
	});

	$("#reset").click(function() {

		$('#myForm').attr('action', 'reset');
		$('#myForm').submit();

	});
	


	$("#validate").click(function() {
		$('#myForm').attr('action', 'validateBookingDetails');
		$('#myForm').submit();

	});

});



function withoutLimit() {
	var limitID = document.getElementById("limitID").value;
	var check = document.getElementById("withoutLimit").checked;
	if (check == false && limitID == '') {
		alert("Limit ID is not provided. If customer does not have limit, kindly select the checkbox 'Without Limit' !");
		return false;
	} else if (check == true && limitID != '') {
		alert("Limit ID will not be used as 'Without Limit' is checked !");
		return false;
	} else
		return true;

}

	function validateFwcType() {
	   var fwcTypeCancel = document.getElementById("fwcTypeCancel").checked;
	   var fwcTypeUtil = document.getElementById("fwcTypeUtil").checked;
	   if (!fwcTypeCancel && !fwcTypeUtil) {
		   doLoad();
	       alert("FWC Type is Mandatory - Please select FWCCANCEL or FWCUTIL !!!");
	       return false;
	   }
	   return true;
	}
	
	
	$(document).ready(function() {
		   $("#insertFwcCancel").off("click").on("click", function(e) {
		       e.preventDefault();
		       var limitValue = withoutLimit();
		       var screen = document.getElementById('screenType').value;
		       if (screen == 'MakerCancelScreenWithoutRate') {
		           // Only check FWC Type on the Without Rate screen, where those elements exist
		           if (!validateFwcType()) {
		               return false;
		           }
		           if (limitValue == true) {
		               $('#myForm').attr('action', 'insertBookingDetails');
		           } else {
		               $('#myForm').attr('action', 'cancelProcessWithoutRate');
		           }
		           $('#myForm').submit();
		       } else {
		           // Regular Cancel screen - unchanged, no FWC Type check
		           if (limitValue == true) {
		               $('#myForm').attr('action', 'insertBookingDetails');
		           } else {
		               $('#myForm').attr('action', 'cancelProcess');
		           }
		           $('#myForm').submit();
		       }
		   });
		});
	



function insertFwcBook()
{
	//alert("Insert Booking Details");
	var value=withoutLimit();
	if (value==true) {
		$('#myForm').attr('action', 'insertBookingDetails');
		$('#myForm').submit();
	} else {
		$('#myForm').attr('action', 'bookingProcess');
		$('#myForm').submit();
	}
}

function closeView() {

	var screen = document.getElementById('screenType').value;
	if (screen == 'MakerScreen') {
		location.href = 'makerProcess';
	} else if (screen == 'CheckerScreen') {
		location.href = 'checkerProcess';
	} else if (screen == 'EnquiryScreen') {
		location.href = 'enquiryProcess';
	}

}
//ABHiSHEK 
function saveBookingDetails() {
	 var customerID = $.trim($('#customerID').val());
	 var screen = document.getElementById('screenType').value;
	 if (customerID.length == 0 && screen == 'MakerBookingScreen') {
	   alert("Customer ID is Mandatory !!!");
	   $('#myForm').attr('action', 'bookingProcess');
	   $('#myForm').submit();
	 } else if (customerID.length == 0 && screen == 'MakerCancelScreen') {
	   alert("Customer ID is Mandatory !!!");
	   $('#myForm').attr('action', 'cancelProcess');
	   $('#myForm').submit();
	 } else if (customerID.length == 0 && screen == 'MakerCancelScreenWithoutRate') { // ← ADD THIS
	   alert("Customer ID is Mandatory !!!");
	   $('#myForm').attr('action', 'cancelProcessWithoutRate');
	   $('#myForm').submit();
	 } else {
	   $('#myForm').attr('action', 'saveBookingDetails');
	   $('#myForm').submit();
	 }
	}

function generateFWCPostings() {

	var subProduct = $.trim($('#subProduct').val());
	var customerID = $.trim($('#customerID').val());
	var branchCode = $.trim($('#branchCode').val());
	var bookingDate = $.trim($('#bookingDate').val());
	var toCurrencyAmt = $.trim($('#toCurrencyAmt').val());

	if (subProduct.length == 0 || customerID.length == 0
			|| branchCode.length == 0 || bookingDate.length == 0
			|| toCurrencyAmt.length == 0) {
		alert("Sub Product, Customer ID, Branch Code, Booking Date and To Amount are Mandatory !!!");
		$('#myForm').attr('action', 'bookingProcess');
		$('#myForm').submit();
	} else {
		$('#myForm').attr('action', 'generateFWCPostings');
		$('#myForm').submit();
	}

}

function getFWCPostingsToReverse() {

	var fwdContractNo = $.trim($('#fwdContractNo').val());
	var subProduct = $.trim($('#subProduct').val());
	var customerID = $.trim($('#customerID').val());
	var branchCode = $.trim($('#branchCode').val());
	var bookingDate = $.trim($('#bookingDate').val());
	var toCurrencyAmt = $.trim($('#toCurrencyAmt').val());

	if (subProduct.length == 0 || customerID.length == 0 || branchCode.length == 0 
			|| bookingDate.length == 0 || toCurrencyAmt.length == 0 || fwdContractNo.length == 0) {
		alert("Forward Contract Number, Sub Product, Customer ID, Branch Code, Booking Date and To Amount  are Mandatory !!!");
		$('#myForm').attr('action', 'cancelProcess');
		$('#myForm').submit();
	} else {
		$('#myForm').attr('action', 'getFWCPostingsToReverse');
		$('#myForm').submit();
	}

}

// CHECKER ABHISHEK
function fetchCheckerFwdcontractDetailsWithoutRate() {
	   onChangeLoad();
	   $("#myForm").attr("action", "fetchCheckerFwdcontractDetailsWithoutRate");
	   $("#myForm").submit();
	}

function doLoad() {
	$('body').modal("hide");
	$('body').css('display', 'block');
	$('body').removeClass('removePageLoad');
	$('body').addClass('removePageLoad');
}

function onChangeLoad() {
	$('body').modal({
		show : 'false'
	});
	$('body').removeClass('removePageLoad');
	$('body').addClass('addPageLoad');
}

function customerStaticDetails() {
	$("#myForm").attr("action", "customerStaticDetails");
	$("#myForm").submit();
}

function accountStaticDetails() {
	$("#myForm").attr("action", "accountStaticDetails");
	$("#myForm").submit();
}

function branchStaticDetails() {
	$("#myForm").attr("action", "branchStaticDetails");
	$("#myForm").submit();
}

function currencyStaticDetails() {
	$("#myForm").attr("action", "currencyStaticDetails");
	$("#myForm").submit();
}

function treasuryRefDetails() {
	var customerID = document.getElementById("customerID").value;
	if (customerID != '') {
		$("#myForm").attr("action", "treasuryRefDetails");
		$("#myForm").submit();
	} else {
		alert("Customer ID is Mandatory !!!");
	}
}

function fwdContractDetails() {
	
		$("#myForm").attr("action", "fwdContractDetails");
		$("#myForm").submit();
}

function limitIDDetails() {

	var customerID = document.getElementById("customerID").value;
	if (customerID != '') {
		$("#myForm").attr("action", "fetchLimitDetails");
		$("#myForm").submit();
	} else {
		alert("Customer ID is Mandatory !!!");
	}
}

function fetchLimitDetails() {
	$("#myForm").attr("action", "fetchLimitDetails");
	$("#myForm").submit();
}

function insertBookingDetails() {
	onChangeLoad();
	$("#myForm").attr("action", "insertBookingDetails");
	$("#myForm").submit();
}

function bookForwardContract() {
	onChangeLoad();
	$("#myForm").attr("action", "bookingProcess");
	$("#myForm").submit();
}

function editForwardContract(event) {
	onChangeLoad();

	$("#myForm").attr("action", "editForwardContract");
	$("#myForm").submit();
}




//ABHISHEK
function fetchDependentTreasuryDetails() {
	 onChangeLoad();
	 var screen = document.getElementById('screenType').value;
	 if (screen == 'MakerBookingScreen') {
	   $("#myForm").attr("action", "fetchBookingTreasuryDetails");
	   $("#myForm").submit();
	 } else if (screen == 'MakerCancelScreen') {
	   $("#myForm").attr("action", "fetchCancelTreasuryDetails");
	   $("#myForm").submit();
	 } else if (screen == 'MakerCancelScreenWithoutRate') {  // ← ADD THIS
	   $("#myForm").attr("action", "fetchCancelTreasuryDetailsWithoutRate");
	   $("#myForm").submit();
	 }
	}

function cancelForwardContract(event) {
	onChangeLoad();

	$("#myForm").attr("action", "cancelForwardContract");
	$("#myForm").submit();
}

//Fetch FWC details for without-rate page  ABHISHEK

function fetchFWCCancelDetailsWithoutRate() {

  onChangeLoad();

  $("#myForm").attr("action", "fetchFWCCancelDetailsWithoutRate");

  $("#myForm").submit();  

}

// View Postings for without-rate page

function getFWCPostingsToReverseWithoutRate() {

  var fwdContractNo = $.trim($('#fwdContractNo').val());

  var subProduct = $.trim($('#subProduct').val());

  var customerID = $.trim($('#customerID').val());

  var branchCode = $.trim($('#branchCode').val());

  var bookingDate = $.trim($('#bookingDate').val());

  var toCurrencyAmt = $.trim($('#toCurrencyAmt').val());

  if (subProduct.length == 0 || customerID.length == 0 || branchCode.length == 0 

      || bookingDate.length == 0 || toCurrencyAmt.length == 0 || fwdContractNo.length == 0) {

    alert("Forward Contract Number, Sub Product, Customer ID, Branch Code, Booking Date and To Amount are Mandatory !!!");

    $('#myForm').attr('action', 'cancelProcessWithoutRate');

    $('#myForm').submit();

  } else {

    $('#myForm').attr('action', 'getFWCPostingsToReverseWithoutRate');

    $('#myForm').submit();

  }

}

// Save for without-rate page

function saveBookingDetailsWithoutRate() {

  var customerID = $.trim($('#customerID').val());

  if (customerID.length == 0) {

    alert("Customer ID is Mandatory !!!");

    $('#myForm').attr('action', 'cancelProcessWithoutRate');

    $('#myForm').submit();

  } else {

    $('#myForm').attr('action', 'saveBookingDetails');

    $('#myForm').submit();

  }

}
 

function cancelForwardContractWithoutRate() {
	onChangeLoad();
	   $("#myForm").attr("action","cancelForwardContractWithoutRate");
	   $("#myForm").submit();
	}

function fetchFWCCancelDetails() {
	onChangeLoad();
	$("#myForm").attr("action", "fetchFWCCancelDetails");
	$("#myForm").submit();	
}


function submitForm() {
	$("#myForm").submit();
}


