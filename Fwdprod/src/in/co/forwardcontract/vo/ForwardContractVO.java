/*      */ package in.co.forwardcontract.vo;

import java.util.ArrayList;

public class ForwardContractVO {
/*      */   private String id;
/*      */   private String category;
/*      */   private String subProduct;
/*      */   private String fwdContractNo;
/*      */   private String customerID;
/*      */   private String acctNumber;
/*      */   private String branchCode;
/*      */   private String dealCurrency;
/*      */   private String bookingDate;
/*      */   private String toCurrency;
/*      */   private String dealValidFromDate;
/*      */   private String treasuryRefNo;
/*      */   private String outstandingAmt;
/*      */   private String fwdContractAmt;
/*      */   private String toCurrencyAmt;
/*      */   private String dealValidToDate;
/*      */   private String treasuryRate;
/*      */   private String limitID;
/*      */   private String withoutLimit;
/*      */   private String availableLimit;
/*      */   private String washRate;
/*      */   private String plAmount;
/*      */   private String instructions;
/*      */   private String leiNumber;
/*      */   private String chargeAmount;
/*      */   private String gstAmount;
/*      */   private String margin;
/*      */   private String screenType;
/*      */   private String rateStatus;
/*      */   private String rateBuyOrSell;
/*      */   private String billId;
/*      */   private String buyOrSell;
/*      */   private String buyAmount;
/*      */   private String sellAmount;
/*      */   private String tranType;
/*      */   private String validFrom;
/*      */   private String validTo;
/*      */   private String flag;
/*      */   private String deleteFlag;
/*      */   private String spMakerFlag;
/*      */   ArrayList<FWCPostingVO> postingList;
/*      */   private ArrayList<in.co.forwardcontract.vo.ForwardContractVO> fwdContractDetailsList;
/*      */   private String customerCif;
/*      */   private String customerName;
/*      */   private String goodsDescription;

            private String fwcType;
            public String getFwcType() { return fwcType; }
            public void setFwcType(String fwcType) { this.fwcType = fwcType; }
/*      */   
/*      */   public String getSpMakerFlag() {
/*   49 */     return this.spMakerFlag;
/*      */   }
/*      */   
/*      */   public void setSpMakerFlag(String spMakerFlag) {
/*   53 */     this.spMakerFlag = spMakerFlag;
/*      */   }
/*      */   
/*      */   public String getDeleteFlag() {
/*   57 */     return this.deleteFlag;
/*      */   }
/*      */   
/*      */   public void setDeleteFlag(String deleteFlag) {
/*   61 */     this.deleteFlag = deleteFlag;
/*      */   }
/*      */   
/*      */   public String getFlag() {
/*   65 */     return this.flag;
/*      */   }
/*      */   
/*      */   public void setFlag(String flag) {
/*   69 */     this.flag = flag;
/*      */   }
/*      */   
/*      */   public String getValidFrom() {
/*   73 */     return this.validFrom;
/*      */   }
/*      */   
/*      */   public void setValidFrom(String validFrom) {
/*   77 */     this.validFrom = validFrom;
/*      */   }
/*      */   
/*      */   public String getValidTo() {
/*   81 */     return this.validTo;
/*      */   }
/*      */   
/*      */   public void setValidTo(String validTo) {
/*   85 */     this.validTo = validTo;
/*      */   }
/*      */   
/*      */   public String getBillId() {
/*   89 */     return this.billId;
/*      */   }
/*      */   
/*      */   public void setBillId(String billId) {
/*   93 */     this.billId = billId;
/*      */   }
/*      */   
/*      */   public String getBuyOrSell() {
/*   97 */     return this.buyOrSell;
/*      */   }
/*      */   
/*      */   public void setBuyOrSell(String buyOrSell) {
/*  101 */     this.buyOrSell = buyOrSell;
/*      */   }
/*      */   
/*      */   public String getBuyAmount() {
/*  105 */     return this.buyAmount;
/*      */   }
/*      */   
/*      */   public void setBuyAmount(String buyAmount) {
/*  109 */     this.buyAmount = buyAmount;
/*      */   }
/*      */   
/*      */   public String getSellAmount() {
/*  113 */     return this.sellAmount;
/*      */   }
/*      */   
/*      */   public void setSellAmount(String sellAmount) {
/*  117 */     this.sellAmount = sellAmount;
/*      */   }
/*      */   
/*      */   public String getTranType() {
/*  121 */     return this.tranType;
/*      */   }
/*      */   
/*      */   public void setTranType(String tranType) {
/*  125 */     this.tranType = tranType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<FWCPostingVO> getPostingList() {
/*  131 */     return this.postingList;
/*      */   }
/*      */   
/*      */   public void setPostingList(ArrayList<FWCPostingVO> postingList) {
/*  135 */     this.postingList = postingList;
/*      */   }
/*      */   
/*      */   public String getRateStatus() {
/*  139 */     return this.rateStatus;
/*      */   }
/*      */   
/*      */   public void setRateStatus(String rateStatus) {
/*  143 */     this.rateStatus = rateStatus;
/*      */   }
/*      */   
/*      */   public String getRateBuyOrSell() {
/*  147 */     return this.rateBuyOrSell;
/*      */   }
/*      */   
/*      */   public void setRateBuyOrSell(String rateBuyOrSell) {
/*  151 */     this.rateBuyOrSell = rateBuyOrSell;
/*      */   }
/*      */   
/*      */   public String getScreenType() {
/*  155 */     return this.screenType;
/*      */   }
/*      */   
/*      */   public void setScreenType(String screenType) {
/*  159 */     this.screenType = screenType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<in.co.forwardcontract.vo.ForwardContractVO> getFwdContractDetailsList() {
/*  165 */     return this.fwdContractDetailsList;
/*      */   }
/*      */   
/*      */   public void setFwdContractDetailsList(ArrayList<in.co.forwardcontract.vo.ForwardContractVO> fwdContractDetailsList) {
/*  169 */     this.fwdContractDetailsList = fwdContractDetailsList;
/*      */   }
/*      */   
/*      */   public String getId() {
/*  173 */     return this.id;
/*      */   }
/*      */   
/*      */   public void setId(String id) {
/*  177 */     this.id = id;
/*      */   }
/*      */   
/*      */   public String getCategory() {
/*  181 */     return this.category;
/*      */   }
/*      */   
/*      */   public void setCategory(String category) {
/*  185 */     this.category = category;
/*      */   }
/*      */   
/*      */   public String getSubProduct() {
/*  189 */     return this.subProduct;
/*      */   }
/*      */   
/*      */   public void setSubProduct(String subProduct) {
/*  193 */     this.subProduct = subProduct;
/*      */   }
/*      */   
/*      */   public String getFwdContractNo() {
/*  197 */     return this.fwdContractNo;
/*      */   }
/*      */   
/*      */   public void setFwdContractNo(String fwdContractNo) {
/*  201 */     this.fwdContractNo = fwdContractNo;
/*      */   }
/*      */   
/*      */   public String getCustomerID() {
/*  205 */     return this.customerID;
/*      */   }
/*      */   
/*      */   public void setCustomerID(String customerID) {
/*  209 */     this.customerID = customerID;
/*      */   }
/*      */   
/*      */   public String getAcctNumber() {
/*  213 */     return this.acctNumber;
/*      */   }
/*      */   
/*      */   public void setAcctNumber(String acctNumber) {
/*  217 */     this.acctNumber = acctNumber;
/*      */   }
/*      */   
/*      */   public String getBranchCode() {
/*  221 */     return this.branchCode;
/*      */   }
/*      */   
/*      */   public void setBranchCode(String branchCode) {
/*  225 */     this.branchCode = branchCode;
/*      */   }
/*      */   
/*      */   public String getDealCurrency() {
/*  229 */     return this.dealCurrency;
/*      */   }
/*      */   
/*      */   public void setDealCurrency(String dealCurrency) {
/*  233 */     this.dealCurrency = dealCurrency;
/*      */   }
/*      */   
/*      */   public String getBookingDate() {
/*  237 */     return this.bookingDate;
/*      */   }
/*      */   
/*      */   public void setBookingDate(String bookingDate) {
/*  241 */     this.bookingDate = bookingDate;
/*      */   }
/*      */   
/*      */   public String getToCurrency() {
/*  245 */     return this.toCurrency;
/*      */   }
/*      */   
/*      */   public void setToCurrency(String toCurrency) {
/*  249 */     this.toCurrency = toCurrency;
/*      */   }
/*      */   
/*      */   public String getDealValidFromDate() {
/*  253 */     return this.dealValidFromDate;
/*      */   }
/*      */   
/*      */   public void setDealValidFromDate(String dealValidFromDate) {
/*  257 */     this.dealValidFromDate = dealValidFromDate;
/*      */   }
/*      */   
/*      */   public String getTreasuryRefNo() {
/*  261 */     return this.treasuryRefNo;
/*      */   }
/*      */   
/*      */   public void setTreasuryRefNo(String treasuryRefNo) {
/*  265 */     this.treasuryRefNo = treasuryRefNo;
/*      */   }
/*      */   
/*      */   public String getOutstandingAmt() {
/*  269 */     return this.outstandingAmt;
/*      */   }
/*      */   
/*      */   public void setOutstandingAmt(String outstandingAmt) {
/*  273 */     this.outstandingAmt = outstandingAmt;
/*      */   }
/*      */   
/*      */   public String getFwdContractAmt() {
/*  277 */     return this.fwdContractAmt;
/*      */   }
/*      */   
/*      */   public void setFwdContractAmt(String fwdContractAmt) {
/*  281 */     this.fwdContractAmt = fwdContractAmt;
/*      */   }
/*      */   
/*      */   public String getToCurrencyAmt() {
/*  285 */     return this.toCurrencyAmt;
/*      */   }
/*      */   
/*      */   public void setToCurrencyAmt(String toCurrencyAmt) {
/*  289 */     this.toCurrencyAmt = toCurrencyAmt;
/*      */   }
/*      */   
/*      */   public String getDealValidToDate() {
/*  293 */     return this.dealValidToDate;
/*      */   }
/*      */   
/*      */   public void setDealValidToDate(String dealValidToDate) {
/*  297 */     this.dealValidToDate = dealValidToDate;
/*      */   }
/*      */   
/*      */   public String getTreasuryRate() {
/*  301 */     return this.treasuryRate;
/*      */   }
/*      */   
/*      */   public void setTreasuryRate(String treasuryRate) {
/*  305 */     this.treasuryRate = treasuryRate;
/*      */   }
/*      */   
/*      */   public String getLimitID() {
/*  309 */     return this.limitID;
/*      */   }
/*      */   
/*      */   public void setLimitID(String limitID) {
/*  313 */     this.limitID = limitID;
/*      */   }
/*      */   
/*      */   public String getWithoutLimit() {
/*  317 */     return this.withoutLimit;
/*      */   }
/*      */   
/*      */   public void setWithoutLimit(String withoutLimit) {
/*  321 */     this.withoutLimit = withoutLimit;
/*      */   }
/*      */   
/*      */   public String getAvailableLimit() {
/*  325 */     return this.availableLimit;
/*      */   }
/*      */   
/*      */   public void setAvailableLimit(String availableLimit) {
/*  329 */     this.availableLimit = availableLimit;
/*      */   }
/*      */   
/*      */   public String getWashRate() {
/*  333 */     return this.washRate;
/*      */   }
/*      */   
/*      */   public void setWashRate(String washRate) {
/*  337 */     this.washRate = washRate;
/*      */   }
/*      */   
/*      */   public String getPlAmount() {
/*  341 */     return this.plAmount;
/*      */   }
/*      */   
/*      */   public void setPlAmount(String plAmount) {
/*  345 */     this.plAmount = plAmount;
/*      */   }
/*      */   
/*      */   public String getInstructions() {
/*  349 */     return this.instructions;
/*      */   }
/*      */   
/*      */   public void setInstructions(String instructions) {
/*  353 */     this.instructions = instructions;
/*      */   }
/*      */   
/*      */   public String getLeiNumber() {
/*  357 */     return this.leiNumber;
/*      */   }
/*      */   
/*      */   public void setLeiNumber(String leiNumber) {
/*  361 */     this.leiNumber = leiNumber;
/*      */   }
/*      */   
/*      */   public String getChargeAmount() {
/*  365 */     return this.chargeAmount;
/*      */   }
/*      */   
/*      */   public void setChargeAmount(String chargeAmount) {
/*  369 */     this.chargeAmount = chargeAmount;
/*      */   }
/*      */   
/*      */   public String getGstAmount() {
/*  373 */     return this.gstAmount;
/*      */   }
/*      */   
/*      */   public void setGstAmount(String gstAmount) {
/*  377 */     this.gstAmount = gstAmount;
/*      */   }
/*      */   
/*      */   public String getMargin() {
/*  381 */     return this.margin;
/*      */   }
/*      */   
/*      */   public void setMargin(String margin) {
/*  385 */     this.margin = margin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  391 */   String userTitle = null;
/*  392 */   String firstName = null;
/*  393 */   String lastName = null;
/*  394 */   String address = null;
/*      */   
/*  396 */   String address2 = null;
/*  397 */   String address3 = null;
/*  398 */   String city = null;
/*  399 */   String pincode = null;
/*  400 */   String state = null;
/*  401 */   private String inwardNo = null;
/*  402 */   String cityString = null;
/*  403 */   String stateString = null;
/*      */   
/*  405 */   String country = null;
/*  406 */   String mobileNumber = null;
/*  407 */   String emailId = null;
/*  408 */   String basicinforesult = null;
/*      */   
/*      */   int userid;
/*      */   String refNumber;
/*      */   int ngoId;
/*      */   private String copyVal;
/*  414 */   String destinationCountry = null;
/*  415 */   String NCIFList = null;
/*      */ 
/*      */   
/*      */   public String getCustomerName() {
/*  419 */     return this.customerName;
/*      */   }
/*      */   
/*      */   public void setCustomerName(String customerName) {
/*  423 */     this.customerName = customerName;
/*      */   }
/*      */   
/*      */   public String getDestinationCountry() {
/*  427 */     return this.destinationCountry;
/*      */   }
/*      */   
/*      */   public String getNCIFList() {
/*  431 */     return this.NCIFList;
/*      */   }
/*      */   
/*      */   public void setNCIFList(String nCIFList) {
/*  435 */     this.NCIFList = nCIFList;
/*      */   }
/*      */   
/*      */   public void setDestinationCountry(String destinationCountry) {
/*  439 */     this.destinationCountry = destinationCountry;
/*      */   }
/*      */   
/*  442 */   String sessionUserName = null; private String chargeType; private String chargeId; private String chargeDesc; private String chargeKey97; private String productType; private String productId; private String productDesc; private String productKey97; private String updateCusCif; private String updateChargeId; private String updateChargeKey97; private String updateProductId; private String exportOrderNumber; private String exporterOrderDate; private String incoTerms; private String goodsCode; private String poValue; private String exportcurrency; private String exportexpiryDate; private String lastShipmentDate; private String freightDeduction; private String insuranceDeduction; private String marginPercentage; private String eligibleAmount; private String description; private int count;
/*  443 */   String pageType = null; private String importerName; private String poNo; private String poDate; private String poCif; private String poBen; private String poInco; private String poGoodDesc; private String poImpName; private String poAmtValue; private String poExpdate; private String poLastShipDate; private String poFrDeduct; private String poInsDeduct; private String poMargin; private String poEligAmt; private ArrayList<in.co.forwardcontract.vo.ForwardContractVO> purchaseOrderList; String fromDate; String toDate; private String status; String statusList; String remarks; ArrayList<AlertMessagesVO> errorList; String cancellationamount; String bookingrate; String transid; String transdate;
/*      */   public String getPageType() {
/*  445 */     return this.pageType;
/*      */   }
/*      */   public void setPageType(String pageType) {
/*  448 */     this.pageType = pageType;
/*      */   }
/*      */   
/*      */   public String getSessionUserName() {
/*  452 */     return this.sessionUserName;
/*      */   }
/*      */   
/*      */   public void setSessionUserName(String sessionUserName) {
/*  456 */     this.sessionUserName = sessionUserName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserTitle() {
/*  463 */     return this.userTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserTitle(String userTitle) {
/*  471 */     this.userTitle = userTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFirstName() {
/*  478 */     return this.firstName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFirstName(String firstName) {
/*  486 */     this.firstName = firstName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLastName() {
/*  493 */     return this.lastName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastName(String lastName) {
/*  501 */     this.lastName = lastName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress() {
/*  508 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress(String address) {
/*  516 */     this.address = address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCity() {
/*  523 */     return this.city;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCity(String city) {
/*  531 */     this.city = city;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPincode() {
/*  538 */     return this.pincode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPincode(String pincode) {
/*  546 */     this.pincode = pincode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getState() {
/*  553 */     return this.state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setState(String state) {
/*  561 */     this.state = state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCountry() {
/*  568 */     return this.country;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCountry(String country) {
/*  576 */     this.country = country;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMobileNumber() {
/*  583 */     return this.mobileNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMobileNumber(String mobileNumber) {
/*  591 */     this.mobileNumber = mobileNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEmailId() {
/*  598 */     return this.emailId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmailId(String emailId) {
/*  606 */     this.emailId = emailId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBasicinforesult() {
/*  613 */     return this.basicinforesult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBasicinforesult(String basicinforesult) {
/*  621 */     this.basicinforesult = basicinforesult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUserid() {
/*  628 */     return this.userid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserid(int userid) {
/*  636 */     this.userid = userid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRefNumber() {
/*  643 */     return this.refNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRefNumber(String refNumber) {
/*  651 */     this.refNumber = refNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNgoId() {
/*  658 */     return this.ngoId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNgoId(int ngoId) {
/*  666 */     this.ngoId = ngoId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress2() {
/*  673 */     return this.address2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress2(String address2) {
/*  681 */     this.address2 = address2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress3() {
/*  688 */     return this.address3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress3(String address3) {
/*  696 */     this.address3 = address3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCityString() {
/*  703 */     return this.cityString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCityString(String cityString) {
/*  711 */     this.cityString = cityString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStateString() {
/*  718 */     return this.stateString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStateString(String stateString) {
/*  726 */     this.stateString = stateString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRemarks() {
/*  787 */     return this.remarks;
/*      */   }
/*      */   
/*      */   public void setRemarks(String remarks) {
/*  791 */     this.remarks = remarks;
/*      */   }
/*      */   
/*      */   public String getStatusList() {
/*  795 */     return this.statusList;
/*      */   }
/*      */   
/*      */   public void setStatusList(String statusList) {
/*  799 */     this.statusList = statusList;
/*      */   }
/*      */   
/*      */   public String getStatus() {
/*  803 */     return this.status;
/*      */   }
/*      */   
/*      */   public void setStatus(String status) {
/*  807 */     this.status = status;
/*      */   }
/*      */   
/*      */   public String getPoNo() {
/*  811 */     return this.poNo;
/*      */   }
/*      */   
/*      */   public String getFromDate() {
/*  815 */     return this.fromDate;
/*      */   }
/*      */   
/*      */   public void setFromDate(String fromDate) {
/*  819 */     this.fromDate = fromDate;
/*      */   }
/*      */   
/*      */   public String getToDate() {
/*  823 */     return this.toDate;
/*      */   }
/*      */   
/*      */   public void setToDate(String toDate) {
/*  827 */     this.toDate = toDate;
/*      */   }
/*      */   
/*      */   public void setPoNo(String poNo) {
/*  831 */     this.poNo = poNo;
/*      */   }
/*      */   
/*      */   public String getPoDate() {
/*  835 */     return this.poDate;
/*      */   }
/*      */   
/*      */   public void setPoDate(String poDate) {
/*  839 */     this.poDate = poDate;
/*      */   }
/*      */   
/*      */   public String getPoCif() {
/*  843 */     return this.poCif;
/*      */   }
/*      */   
/*      */   public void setPoCif(String poCif) {
/*  847 */     this.poCif = poCif;
/*      */   }
/*      */   
/*      */   public String getPoBen() {
/*  851 */     return this.poBen;
/*      */   }
/*      */   
/*      */   public void setPoBen(String poBen) {
/*  855 */     this.poBen = poBen;
/*      */   }
/*      */   
/*      */   public String getPoInco() {
/*  859 */     return this.poInco;
/*      */   }
/*      */   
/*      */   public void setPoInco(String poInco) {
/*  863 */     this.poInco = poInco;
/*      */   }
/*      */   
/*      */   public String getPoGoodDesc() {
/*  867 */     return this.poGoodDesc;
/*      */   }
/*      */   
/*      */   public void setPoGoodDesc(String poGoodDesc) {
/*  871 */     this.poGoodDesc = poGoodDesc;
/*      */   }
/*      */   
/*      */   public String getPoImpName() {
/*  875 */     return this.poImpName;
/*      */   }
/*      */   
/*      */   public void setPoImpName(String poImpName) {
/*  879 */     this.poImpName = poImpName;
/*      */   }
/*      */   
/*      */   public String getPoAmtValue() {
/*  883 */     return this.poAmtValue;
/*      */   }
/*      */   
/*      */   public void setPoAmtValue(String poAmtValue) {
/*  887 */     this.poAmtValue = poAmtValue;
/*      */   }
/*      */   
/*      */   public String getPoExpdate() {
/*  891 */     return this.poExpdate;
/*      */   }
/*      */   
/*      */   public void setPoExpdate(String poExpdate) {
/*  895 */     this.poExpdate = poExpdate;
/*      */   }
/*      */   
/*      */   public String getPoLastShipDate() {
/*  899 */     return this.poLastShipDate;
/*      */   }
/*      */   
/*      */   public void setPoLastShipDate(String poLastShipDate) {
/*  903 */     this.poLastShipDate = poLastShipDate;
/*      */   }
/*      */   
/*      */   public String getPoFrDeduct() {
/*  907 */     return this.poFrDeduct;
/*      */   }
/*      */   
/*      */   public void setPoFrDeduct(String poFrDeduct) {
/*  911 */     this.poFrDeduct = poFrDeduct;
/*      */   }
/*      */   
/*      */   public String getPoInsDeduct() {
/*  915 */     return this.poInsDeduct;
/*      */   }
/*      */   
/*      */   public void setPoInsDeduct(String poInsDeduct) {
/*  919 */     this.poInsDeduct = poInsDeduct;
/*      */   }
/*      */   
/*      */   public String getPoMargin() {
/*  923 */     return this.poMargin;
/*      */   }
/*      */   
/*      */   public void setPoMargin(String poMargin) {
/*  927 */     this.poMargin = poMargin;
/*      */   }
/*      */   
/*      */   public String getPoEligAmt() {
/*  931 */     return this.poEligAmt;
/*      */   }
/*      */   
/*      */   public void setPoEligAmt(String poEligAmt) {
/*  935 */     this.poEligAmt = poEligAmt;
/*      */   }
/*      */   
/*      */   public String getImporterName() {
/*  939 */     return this.importerName;
/*      */   }
/*      */   
/*      */   public void setImporterName(String importerName) {
/*  943 */     this.importerName = importerName;
/*      */   }
/*      */   
/*      */   public int getCount() {
/*  947 */     return this.count;
/*      */   }
/*      */   
/*      */   public void setCount(int count) {
/*  951 */     this.count = count;
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  955 */     return this.description;
/*      */   }
/*      */   
/*      */   public void setDescription(String description) {
/*  959 */     this.description = description;
/*      */   }
/*      */   
/*      */   public String getExportOrderNumber() {
/*  963 */     return this.exportOrderNumber;
/*      */   }
/*      */   
/*      */   public void setExportOrderNumber(String exportOrderNumber) {
/*  967 */     this.exportOrderNumber = exportOrderNumber;
/*      */   }
/*      */   
/*      */   public String getExporterOrderDate() {
/*  971 */     return this.exporterOrderDate;
/*      */   }
/*      */   
/*      */   public void setExporterOrderDate(String exporterOrderDate) {
/*  975 */     this.exporterOrderDate = exporterOrderDate;
/*      */   }
/*      */   
/*      */   public String getIncoTerms() {
/*  979 */     return this.incoTerms;
/*      */   }
/*      */   
/*      */   public void setIncoTerms(String incoTerms) {
/*  983 */     this.incoTerms = incoTerms;
/*      */   }
/*      */   
/*      */   public String getGoodsCode() {
/*  987 */     return this.goodsCode;
/*      */   }
/*      */   
/*      */   public void setGoodsCode(String goodsCode) {
/*  991 */     this.goodsCode = goodsCode;
/*      */   }
/*      */   
/*      */   public String getPoValue() {
/*  995 */     return this.poValue;
/*      */   }
/*      */   
/*      */   public void setPoValue(String poValue) {
/*  999 */     this.poValue = poValue;
/*      */   }
/*      */   
/*      */   public String getExportcurrency() {
/* 1003 */     return this.exportcurrency;
/*      */   }
/*      */   
/*      */   public void setExportcurrency(String exportcurrency) {
/* 1007 */     this.exportcurrency = exportcurrency;
/*      */   }
/*      */   
/*      */   public String getExportexpiryDate() {
/* 1011 */     return this.exportexpiryDate;
/*      */   }
/*      */   
/*      */   public void setExportexpiryDate(String exportexpiryDate) {
/* 1015 */     this.exportexpiryDate = exportexpiryDate;
/*      */   }
/*      */   
/*      */   public String getLastShipmentDate() {
/* 1019 */     return this.lastShipmentDate;
/*      */   }
/*      */   
/*      */   public void setLastShipmentDate(String lastShipmentDate) {
/* 1023 */     this.lastShipmentDate = lastShipmentDate;
/*      */   }
/*      */   
/*      */   public String getFreightDeduction() {
/* 1027 */     return this.freightDeduction;
/*      */   }
/*      */   
/*      */   public void setFreightDeduction(String freightDeduction) {
/* 1031 */     this.freightDeduction = freightDeduction;
/*      */   }
/*      */   
/*      */   public String getInsuranceDeduction() {
/* 1035 */     return this.insuranceDeduction;
/*      */   }
/*      */   
/*      */   public void setInsuranceDeduction(String insuranceDeduction) {
/* 1039 */     this.insuranceDeduction = insuranceDeduction;
/*      */   }
/*      */   
/*      */   public String getMarginPercentage() {
/* 1043 */     return this.marginPercentage;
/*      */   }
/*      */   
/*      */   public void setMarginPercentage(String marginPercentage) {
/* 1047 */     this.marginPercentage = marginPercentage;
/*      */   }
/*      */   
/*      */   public String getEligibleAmount() {
/* 1051 */     return this.eligibleAmount;
/*      */   }
/*      */   
/*      */   public void setEligibleAmount(String eligibleAmount) {
/* 1055 */     this.eligibleAmount = eligibleAmount;
/*      */   }
/*      */   
/*      */   public String getUpdateProductId() {
/* 1059 */     return this.updateProductId;
/*      */   }
/*      */   
/*      */   public void setUpdateProductId(String updateProductId) {
/* 1063 */     this.updateProductId = updateProductId;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCustomerCif() {
/* 1069 */     return this.customerCif;
/*      */   }
/*      */   
/*      */   public void setCustomerCif(String customerCif) {
/* 1073 */     this.customerCif = customerCif;
/*      */   }
/*      */   
/*      */   public ArrayList<AlertMessagesVO> getErrorList() {
/* 1077 */     return this.errorList;
/*      */   }
/*      */   
/*      */   public void setErrorList(ArrayList<AlertMessagesVO> errorList) {
/* 1081 */     this.errorList = errorList;
/*      */   }
/*      */   
/*      */   public String getChargeType() {
/* 1085 */     return this.chargeType;
/*      */   }
/*      */   
/*      */   public void setChargeType(String chargeType) {
/* 1089 */     this.chargeType = chargeType;
/*      */   }
/*      */   
/*      */   public String getChargeId() {
/* 1093 */     return this.chargeId;
/*      */   }
/*      */   
/*      */   public void setChargeId(String chargeId) {
/* 1097 */     this.chargeId = chargeId;
/*      */   }
/*      */   
/*      */   public String getChargeKey97() {
/* 1101 */     return this.chargeKey97;
/*      */   }
/*      */   
/*      */   public void setChargeKey97(String chargeKey97) {
/* 1105 */     this.chargeKey97 = chargeKey97;
/*      */   }
/*      */   
/*      */   public String getProductType() {
/* 1109 */     return this.productType;
/*      */   }
/*      */   
/*      */   public void setProductType(String productType) {
/* 1113 */     this.productType = productType;
/*      */   }
/*      */   
/*      */   public String getProductId() {
/* 1117 */     return this.productId;
/*      */   }
/*      */   
/*      */   public void setProductId(String productId) {
/* 1121 */     this.productId = productId;
/*      */   }
/*      */   
/*      */   public String getProductKey97() {
/* 1125 */     return this.productKey97;
/*      */   }
/*      */   
/*      */   public void setProductKey97(String productKey97) {
/* 1129 */     this.productKey97 = productKey97;
/*      */   }
/*      */   
/*      */   public String getChargeDesc() {
/* 1133 */     return this.chargeDesc;
/*      */   }
/*      */   
/*      */   public void setChargeDesc(String chargeDesc) {
/* 1137 */     this.chargeDesc = chargeDesc;
/*      */   }
/*      */   
/*      */   public String getProductDesc() {
/* 1141 */     return this.productDesc;
/*      */   }
/*      */   
/*      */   public void setProductDesc(String productDesc) {
/* 1145 */     this.productDesc = productDesc;
/*      */   }
/*      */   
/*      */   public String getUpdateCusCif() {
/* 1149 */     return this.updateCusCif;
/*      */   }
/*      */   
/*      */   public void setUpdateCusCif(String updateCusCif) {
/* 1153 */     this.updateCusCif = updateCusCif;
/*      */   }
/*      */   
/*      */   public String getUpdateChargeId() {
/* 1157 */     return this.updateChargeId;
/*      */   }
/*      */   
/*      */   public void setUpdateChargeId(String updateChargeId) {
/* 1161 */     this.updateChargeId = updateChargeId;
/*      */   }
/*      */   
/*      */   public String getUpdateChargeKey97() {
/* 1165 */     return this.updateChargeKey97;
/*      */   }
/*      */   
/*      */   public void setUpdateChargeKey97(String updateChargeKey97) {
/* 1169 */     this.updateChargeKey97 = updateChargeKey97;
/*      */   }
/*      */   
/*      */   public ArrayList<in.co.forwardcontract.vo.ForwardContractVO> getPurchaseOrderList() {
/* 1173 */     return this.purchaseOrderList;
/*      */   }
/*      */   
/*      */   public void setPurchaseOrderList(ArrayList<in.co.forwardcontract.vo.ForwardContractVO> purchaseOrderList) {
/* 1177 */     this.purchaseOrderList = purchaseOrderList;
/*      */   }
/*      */   
/*      */   public String getInwardNo() {
/* 1181 */     return this.inwardNo;
/*      */   }
/*      */   
/*      */   public void setInwardNo(String inwardNo) {
/* 1185 */     this.inwardNo = inwardNo;
/*      */   }
/*      */   
/*      */   public String getGoodsDescription() {
/* 1189 */     return this.goodsDescription;
/*      */   }
/*      */   
/*      */   public void setGoodsDescription(String goodsDescription) {
/* 1193 */     this.goodsDescription = goodsDescription;
/*      */   }
/*      */   
/*      */   public String getCopyVal() {
/* 1197 */     return this.copyVal;
/*      */   }
/*      */   
/*      */   public void setCopyVal(String copyVal) {
/* 1201 */     this.copyVal = copyVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCancellationamount() {
/* 1211 */     return this.cancellationamount;
/*      */   }
/*      */   
/*      */   public void setCancellationamount(String cancellationamount) {
/* 1215 */     this.cancellationamount = cancellationamount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBookingrate() {
/* 1223 */     return this.bookingrate;
/*      */   }
/*      */   
/*      */   public void setBookingrate(String bookingrate) {
/* 1227 */     this.bookingrate = bookingrate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTransid() {
/* 1237 */     return this.transid;
/*      */   }
/*      */   
/*      */   public void setTransid(String transid) {
/* 1241 */     this.transid = transid;
/*      */   }
/*      */   
/*      */   public String getTransdate() {
/* 1245 */     return this.transdate;
/*      */   }
/*      */   
/*      */   public void setTransdate(String transdate) {
/* 1249 */     this.transdate = transdate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1254 */     return "ForwardContractVO [id=" + this.id + ", category=" + this.category + ", subProduct=" + this.subProduct + 
/* 1255 */       ", fwdContractNo=" + this.fwdContractNo + ", customerID=" + this.customerID + ", acctNumber=" + this.acctNumber + 
/* 1256 */       ", branchCode=" + this.branchCode + ", dealCurrency=" + this.dealCurrency + ", bookingDate=" + this.bookingDate + 
/* 1257 */       ", toCurrency=" + this.toCurrency + ", dealValidFromDate=" + this.dealValidFromDate + ", treasuryRefNo=" + 
/* 1258 */       this.treasuryRefNo + ", outstandingAmt=" + this.outstandingAmt + ", fwdContractAmt=" + this.fwdContractAmt + 
/* 1259 */       ", toCurrencyAmt=" + this.toCurrencyAmt + ", dealValidToDate=" + this.dealValidToDate + ", treasuryRate=" + 
/* 1260 */       this.treasuryRate + ", limitID=" + this.limitID + ", withoutLimit=" + this.withoutLimit + ", availableLimit=" + 
/* 1261 */       this.availableLimit + ", washRate=" + this.washRate + ", plAmount=" + this.plAmount + ", instructions=" + 
/* 1262 */       this.instructions + ", leiNumber=" + this.leiNumber + ", chargeAmount=" + this.chargeAmount + ", gstAmount=" + 
/* 1263 */       this.gstAmount + ", margin=" + this.margin + ", screenType=" + this.screenType + ", rateStatus=" + this.rateStatus + 
/* 1264 */       ", rateBuyOrSell=" + this.rateBuyOrSell + ", billId=" + this.billId + ", buyOrSell=" + this.buyOrSell + 
/* 1265 */       ", buyAmount=" + this.buyAmount + ", sellAmount=" + this.sellAmount + ", tranType=" + this.tranType + ", validFrom=" + 
/* 1266 */       this.validFrom + ", validTo=" + this.validTo + ", postingList=" + this.postingList + ", fwdContractDetailsList=" + 
/* 1267 */       this.fwdContractDetailsList + ", customerCif=" + this.customerCif + ", customerName=" + this.customerName + 
/* 1268 */       ", goodsDescription=" + this.goodsDescription + ", userTitle=" + this.userTitle + ", firstName=" + this.firstName + 
/* 1269 */       ", lastName=" + this.lastName + ", address=" + this.address + ", address2=" + this.address2 + ", address3=" + 
/* 1270 */       this.address3 + ", city=" + this.city + ", pincode=" + this.pincode + ", state=" + this.state + ", inwardNo=" + this.inwardNo + 
/* 1271 */       ", cityString=" + this.cityString + ", stateString=" + this.stateString + ", country=" + this.country + 
/* 1272 */       ", mobileNumber=" + this.mobileNumber + ", emailId=" + this.emailId + ", basicinforesult=" + this.basicinforesult + 
/* 1273 */       ", userid=" + this.userid + ", refNumber=" + this.refNumber + ", ngoId=" + this.ngoId + ", copyVal=" + this.copyVal + 
/* 1274 */       ", destinationCountry=" + this.destinationCountry + ", NCIFList=" + this.NCIFList + ", sessionUserName=" + 
/* 1275 */       this.sessionUserName + ", chargeType=" + this.chargeType + ", chargeId=" + this.chargeId + ", chargeDesc=" + 
/* 1276 */       this.chargeDesc + ", chargeKey97=" + this.chargeKey97 + ", productType=" + this.productType + ", productId=" + 
/* 1277 */       this.productId + ", productDesc=" + this.productDesc + ", productKey97=" + this.productKey97 + ", updateCusCif=" + 
/* 1278 */       this.updateCusCif + ", updateChargeId=" + this.updateChargeId + ", updateChargeKey97=" + this.updateChargeKey97 + 
/* 1279 */       ", updateProductId=" + this.updateProductId + ", exportOrderNumber=" + this.exportOrderNumber + 
/* 1280 */       ", exporterOrderDate=" + this.exporterOrderDate + ", incoTerms=" + this.incoTerms + ", goodsCode=" + this.goodsCode + 
/* 1281 */       ", poValue=" + this.poValue + ", exportcurrency=" + this.exportcurrency + ", exportexpiryDate=" + 
/* 1282 */       this.exportexpiryDate + ", lastShipmentDate=" + this.lastShipmentDate + ", freightDeduction=" + this.freightDeduction + 
/* 1283 */       ", insuranceDeduction=" + this.insuranceDeduction + ", marginPercentage=" + this.marginPercentage + 
/* 1284 */       ", eligibleAmount=" + this.eligibleAmount + ", description=" + this.description + ", count=" + this.count + 
/* 1285 */       ", importerName=" + this.importerName + ", poNo=" + this.poNo + ", poDate=" + this.poDate + ", poCif=" + this.poCif + 
/* 1286 */       ", poBen=" + this.poBen + ", poInco=" + this.poInco + ", poGoodDesc=" + this.poGoodDesc + ", poImpName=" + this.poImpName + 
/* 1287 */       ", poAmtValue=" + this.poAmtValue + ", poExpdate=" + this.poExpdate + ", poLastShipDate=" + this.poLastShipDate + 
/* 1288 */       ", poFrDeduct=" + this.poFrDeduct + ", poInsDeduct=" + this.poInsDeduct + ", poMargin=" + this.poMargin + 
/* 1289 */       ", poEligAmt=" + this.poEligAmt + ", purchaseOrderList=" + this.purchaseOrderList + ", fromDate=" + this.fromDate + 
/* 1290 */       ", toDate=" + this.toDate + ", status=" + this.status + ", statusList=" + this.statusList + ", remarks=" + this.remarks + 
/* 1291 */       ", errorList=" + this.errorList + ", cancellationamount=" + this.cancellationamount + ", bookingrate=" + 
/* 1292 */       this.bookingrate + ", transid=" + this.transid + ", transdate=" + this.transdate + "]";
/*      */   }
/*      */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\vo\ForwardContractVO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */