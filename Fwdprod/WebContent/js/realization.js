$("#submit").click(function(){
	  $("#myForm").submit();
});

function cancel(){
	 window.location.href = "realization";	
}
 
 
function eventType(){
	$("#myForm").submit();
}
function  getGRDetails(){
	var value = document.getElementById("idEventRefNo").value;
	if(value != '<----->'){
		$("#myForm").attr("action","fetchDetails?mode=transaction");
		$("#myForm").submit();
	}
}

function getShippingDetails(){
	var value = document.getElementById("idEventRefNo").value;
	if(value != '<----->'){
		$("#myForm").attr("action","shippingDetails?mode=shipping");
		$("#myForm").submit();
	}
}

function submitForm(){
	$("#myForm").attr("action","realizationDetails?mode=submit");
	$("#myForm").submit();
}

function getShipping(){
	$("#myForm").attr("action","getShippingDetails?mode=editShip");
	$("#myForm").submit();
}

function getInvoice(){
	$("#myForm").attr("action","getInvoiceDetails?mode=editInvoice");
	$("#myForm").submit();
}

