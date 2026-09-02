<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="gr" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Forward Contract Cancel Without Rate - View</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/datepicker.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-dropdown.css" />
<link type="text/css" rel="stylesheet" href="css/headfoot.css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/commonTiplus.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/bootstrap.js" type="text/javascript"></script>
<script src="js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/macxdnie81.js"></script>
<script type="text/javascript" src="js/maxcdnie82.js"></script>
<script type="text/javascript" src="js/jquery.cookie.min.js"></script>
<script type="text/javascript" src="js/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/commonTiplus.js"></script>
</head>
<body class="body_bg" onload="display_ct()">
	<img src="images/FTI-UBI.png" width="100%" />
	<gr:form method="post" id="myForm" name="icForm">
		<div class="row">
			<div class="col-md-2">
				<div class="side_nav" style="width: 215px;">
					<ul class="nav nav-pills nav-stacked">
						<li style="text-align: center;"><a href="makerProcess">Close</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-10 content_box">
				<h5 style="font-weight: bold; font-size: 13px; color: #527BB8;">&nbsp;Forward

					Contract Cancellation Without Rate - View</h5>
				<br />
				<br />
				<c:if test="${not empty msg}">
					<h5 style="font-weight: lighter; font-size: 13px; color: #FF0000;">
						<gr:property value="msg" />
					</h5>
				</c:if>
				<!-- Postings -->
				<div class="row page_content">
					<div class="col-md-12">
						<div class="page_collapsible" id="body-section-postings">
							<span></span>
							<h5 style="font-weight: bold; font-size: 13px; color: #527BB8;">&nbsp;Postings</h5>
						</div>
						<div class="table">
							<table border="1px" align="left" id="postingList">
								<tbody>
									<tr>
										<th style="text-align: left; width: 50px"><label>&nbsp;&nbsp;Dr/Cr</label></th>
										<th style="text-align: left; width: 200px"><label>&nbsp;&nbsp;Account</label></th>
										<th style="text-align: left; width: 200px"><label>&nbsp;&nbsp;Amount</label></th>
										<th style="text-align: left; width: 150px"><label>&nbsp;&nbsp;Value
												Date</label></th>
										<th style="text-align: left; width: 500px"><label>&nbsp;&nbsp;Description</label></th>
									</tr>
									<gr:iterator value="fwdContractVO.postingList"
										var="postingList">
										<tr>
											<td><div class="form-group" align="left">
													<gr:property value="postingDrCrFlag" />
												</div></td>
											<td><div class="form-group" align="left">
													<gr:property value="postingAcctNumber" />
												</div></td>
											<td><div class="form-group" align="left">
													<gr:property value="postingAmountCcy" />
												</div></td>
											<td><div class="form-group" align="left">
													<gr:property value="postingValueDate" />
												</div></td>
											<td><div class="form-group" align="left">
													<gr:property value="postingDesc" />
												</div></td>
										</tr>
									</gr:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<br />
				<br />
				<!-- Cancellation Details -->
				<div class="row cont_colaps">
					<div class="col-md-12">
						<div class="page_collapsible" id="body-section1">
							<span></span>
							<h5 style="font-weight: bold; font-size: 13px; color: #527BB8;">&nbsp;Cancellation
								Details</h5>
						</div>
						<div class="col-md-12">
							<div class="row page_content">
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-md-5 Control-label"
											style="font-weight: normal;">Sub Product</label>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="subProduct" name="fwdContractVO.subProduct"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Forward Contract
												Number</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="fwdContractNo"
												name="fwdContractVO.fwdContractNo" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
								</div>
							</div>
							<!-- Customer Details -->
							<div class="row page_content">
								<div>
									<p
										style="font-weight: bold; color: #527BB8; text-decoration: underline; margin-left: 20px;">

										Customer Details</p>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Customer ID</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="customerID" name="fwdContractVO.customerID"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Account Number</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="acctNumber" name="fwdContractVO.acctNumber"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Branch Code</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="branchCode" name="fwdContractVO.branchCode"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Deal Currency</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="dealCurrency"
												name="fwdContractVO.dealCurrency" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
								</div>
							</div>
							<!-- Contract Details -->
							<div class="row page_content">
								<div>
									<p
										style="font-weight: bold; color: #527BB8; text-decoration: underline; margin-left: 20px;">

										Contract Details</p>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Treasury
												Reference Number</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="treasuryRefNo"
												name="fwdContractVO.treasuryRefNo" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">To Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="toCurrencyAmt"
												name="fwdContractVO.toCurrencyAmt" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Deal Valid From</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="dealValidFromDate"
												name="fwdContractVO.dealValidFromDate" readonly="true"
												cssClass="datepicker form-control text_box"
												style="text-align: left;" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Cancellation Date</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="bookingDate"
												name="fwdContractVO.bookingDate" readonly="true"
												cssClass="datepicker form-control text_box"
												style="text-align: left;" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Event Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="fwdContractAmt"
												name="fwdContractVO.fwdContractAmt" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Cancellation
												Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="outstandingAmt"
												name="fwdContractVO.outstandingAmt" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Deal Valid To</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="dealValidToDate"
												name="fwdContractVO.dealValidToDate" readonly="true"
												cssClass="datepicker form-control text_box"
												style="text-align: left;" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Treasury Rate</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="treasuryRate"
												name="fwdContractVO.treasuryRate" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Event Outstanding
												Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="cancellationamount"
												name="fwdContractVO.cancellationamount" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
								</div>
							</div>
							<!-- Limit Details -->
							<div class="row page_content">
								<div>
									<p
										style="font-weight: bold; color: #527BB8; text-decoration: underline; margin-left: 20px;">

										Limit Details</p>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Limit ID</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="limitID" name="fwdContractVO.limitID"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Available Limit</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="availableLimit"
												name="fwdContractVO.availableLimit" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Without Limit</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:checkbox id="withoutLimit"
												name="fwdContractVO.withoutLimit" disabled="disabled" />
										</div>
									</div>
									<!-- FWC Type: pre-selected from DB, read-only -->
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">FWC Type</label>
										</div>
										<div class="col-md-6 input-group input-group-md">
											<label style="font-weight: normal; margin-right: 20px;">
												<input type="radio" id="fwcTypeCancel"
												name="fwdContractVO.fwcType" value="FWCCANCEL"
												disabled="disabled"
												<c:if test="${fwdContractVO.fwcType == 'FWCCANCEL'}">checked="checked"</c:if> />
												&nbsp;FWCCANCEL
											</label> <label style="font-weight: normal;"> <input
												type="radio" id="fwcTypeUtil" name="fwdContractVO.fwcType"
												value="FWCUTIL" disabled="disabled"
												<c:if test="${fwdContractVO.fwcType == 'FWCUTIL'}">checked="checked"</c:if> />
												&nbsp;FWCUTIL
											</label>
										</div>
									</div>
								</div>
							</div>
							<!-- Charge Details -->
							<div class="row page_content">
								<div>
									<p
										style="font-weight: bold; color: #527BB8; text-decoration: underline; margin-left: 20px;">

										Charge Details</p>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Charge Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="chargeAmount"
												name="fwdContractVO.chargeAmount" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">GST Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="gstAmount" name="fwdContractVO.gstAmount"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
								</div>
							</div>
							<!-- Additional Details -->
							<div class="row page_content">
								<div>
									<p
										style="font-weight: bold; color: #527BB8; text-decoration: underline; margin-left: 20px;">

										Additional Details</p>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Wash Rate</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="washRate" name="fwdContractVO.washRate"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">P&amp;L Amount</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="plAmount" name="fwdContractVO.plAmount"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Booking Rate</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="bookingrate"
												name="fwdContractVO.bookingrate" readonly="true"
												cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Instructions</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textarea id="instructions"
												name="fwdContractVO.instructions" cols="18" rows="4"
												readonly="true"
												style="margin: 0px; width: 200px; height: 74px;" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Remarks</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textarea id="remarks" name="fwdContractVO.remarks"
												cols="18" rows="4" readonly="true"
												style="margin: 0px; width: 200px; height: 74px;" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">LEI Number</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="leiNumber" name="fwdContractVO.leiNumber"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Margin</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="margin" name="fwdContractVO.margin"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Transaction ID</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="transid" name="fwdContractVO.transid"
												readonly="true" cssClass="form-control text_box" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-5 Control-label">
											<label style="font-weight: normal;">Transaction Date</label>
										</div>
										<div class="col-md-3 input-group input-group-md">
											<gr:textfield id="transdate" name="fwdContractVO.transdate"
												readonly="true" cssClass="datepicker form-control text_box"
												style="text-align: left;" />
										</div>
									</div>
								</div>
							</div>
							<gr:hidden id="screenType" name="fwdContractVO.screenType"
								value="ViewCancelScreenWithoutRate" />
							<gr:hidden id="category" name="fwdContractVO.category" />
							<gr:hidden id="id" name="fwdContractVO.id" />
						</div>
					</div>
				</div>
				<div style="clear: both; height: 20px;"></div>
			</div>
		</div>
	</gr:form>
	<div id="footer">
		<%@ include file="/view/includes/FOOTER.jsp"%>
	</div>
</body>
</html>
