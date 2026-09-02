/*      */ package in.co.forwardcontract.vo;
/*      */ 
/*      */ import in.co.forwardcontract.vo.AlertMessagesVO;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ public class ChargeScheduleVO
/*      */ {
/*      */   private String customerCif;
/*      */   private String customerName;
/*      */   private String goodsDescription;
/*      */   
/*      */   public String getCustomerName() {
/*   14 */     return this.customerName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomerName(String customerName) {
/*   19 */     this.customerName = customerName;
/*      */   }
/*      */   
/*   22 */   String userTitle = null;
/*   23 */   String firstName = null;
/*   24 */   String lastName = null;
/*   25 */   String address = null;
/*      */   
/*   27 */   String address2 = null;
/*   28 */   String address3 = null;
/*   29 */   String city = null;
/*   30 */   String pincode = null;
/*   31 */   String state = null;
/*   32 */   private String inwardNo = null;
/*   33 */   String cityString = null;
/*   34 */   String stateString = null;
/*      */   
/*   36 */   String country = null;
/*   37 */   String mobileNumber = null;
/*   38 */   String emailId = null;
/*   39 */   String basicinforesult = null;
/*      */   
/*      */   int userid;
/*      */   String refNumber;
/*      */   int ngoId;
/*      */   private String copyVal;
/*   45 */   String destinationCountry = null;
/*   46 */   String NCIFList = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDestinationCountry() {
/*   53 */     return this.destinationCountry;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNCIFList() {
/*   58 */     return this.NCIFList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNCIFList(String nCIFList) {
/*   63 */     this.NCIFList = nCIFList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDestinationCountry(String destinationCountry) {
/*   68 */     this.destinationCountry = destinationCountry;
/*      */   }
/*      */   
/*   71 */   String sessionUserName = null;
/*      */   
/*      */   private String chargeType;
/*      */   private String chargeId;
/*      */   private String chargeDesc;
/*      */   private String chargeKey97;
/*      */   private String productType;
/*      */   private String productId;
/*      */   private String productDesc;
/*      */   private String productKey97;
/*      */   private String updateCusCif;
/*      */   private String updateChargeId;
/*      */   private String updateChargeKey97;
/*      */   private String updateProductId;
/*      */   private String exportOrderNumber;
/*      */   private String exporterOrderDate;
/*      */   private String cifID;
/*      */   private String beneficiaryName;
/*      */   private String incoTerms;
/*      */   private String goodsCode;
/*      */   private String poValue;
/*      */   private String exportcurrency;
/*      */   private String exportexpiryDate;
/*      */   private String lastShipmentDate;
/*      */   private String freightDeduction;
/*      */   private String insuranceDeduction;
/*      */   private String marginPercentage;
/*      */   
/*      */   public String getSessionUserName() {
/*  100 */     return this.sessionUserName;
/*      */   }
/*      */   
/*      */   private String eligibleAmount;
/*      */   private String description;
/*      */   private int count;
/*      */   private String importerName;
/*      */   private String poNo;
/*      */   private String poDate;
/*      */   private String poCif;
/*      */   private String poBen;
/*      */   private String poInco;
/*      */   private String poGoodDesc;
/*      */   private String poImpName;
/*      */   private String poAmtValue;
/*      */   private String poExpdate;
/*      */   private String poLastShipDate;
/*      */   private String poFrDeduct;
/*      */   private String poInsDeduct;
/*      */   private String poMargin;
/*      */   private String poEligAmt;
/*      */   private ArrayList<in.co.forwardcontract.vo.ChargeScheduleVO> purchaseOrderList;
/*      */   String fromDate;
/*      */   String toDate;
/*      */   private String status;
/*      */   String statusList;
/*      */   String remark;
/*      */   ArrayList<AlertMessagesVO> errorList;
/*      */   
/*      */   public void setSessionUserName(String sessionUserName) {
/*  130 */     this.sessionUserName = sessionUserName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserTitle() {
/*  138 */     return this.userTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserTitle(String userTitle) {
/*  147 */     this.userTitle = userTitle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFirstName() {
/*  155 */     return this.firstName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFirstName(String firstName) {
/*  164 */     this.firstName = firstName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLastName() {
/*  172 */     return this.lastName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastName(String lastName) {
/*  181 */     this.lastName = lastName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress() {
/*  189 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress(String address) {
/*  198 */     this.address = address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCity() {
/*  206 */     return this.city;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCity(String city) {
/*  215 */     this.city = city;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPincode() {
/*  223 */     return this.pincode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPincode(String pincode) {
/*  232 */     this.pincode = pincode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getState() {
/*  240 */     return this.state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setState(String state) {
/*  249 */     this.state = state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCountry() {
/*  257 */     return this.country;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCountry(String country) {
/*  266 */     this.country = country;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMobileNumber() {
/*  274 */     return this.mobileNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMobileNumber(String mobileNumber) {
/*  283 */     this.mobileNumber = mobileNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEmailId() {
/*  291 */     return this.emailId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmailId(String emailId) {
/*  300 */     this.emailId = emailId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBasicinforesult() {
/*  308 */     return this.basicinforesult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBasicinforesult(String basicinforesult) {
/*  317 */     this.basicinforesult = basicinforesult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUserid() {
/*  325 */     return this.userid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserid(int userid) {
/*  334 */     this.userid = userid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRefNumber() {
/*  342 */     return this.refNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRefNumber(String refNumber) {
/*  351 */     this.refNumber = refNumber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNgoId() {
/*  359 */     return this.ngoId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNgoId(int ngoId) {
/*  368 */     this.ngoId = ngoId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress2() {
/*  376 */     return this.address2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress2(String address2) {
/*  384 */     this.address2 = address2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAddress3() {
/*  392 */     return this.address3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAddress3(String address3) {
/*  400 */     this.address3 = address3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCityString() {
/*  408 */     return this.cityString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCityString(String cityString) {
/*  416 */     this.cityString = cityString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStateString() {
/*  424 */     return this.stateString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStateString(String stateString) {
/*  432 */     this.stateString = stateString;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRemark() {
/*  499 */     return this.remark;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRemark(String remark) {
/*  504 */     this.remark = remark;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatusList() {
/*  509 */     return this.statusList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatusList(String statusList) {
/*  514 */     this.statusList = statusList;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getStatus() {
/*  519 */     return this.status;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatus(String status) {
/*  524 */     this.status = status;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoNo() {
/*  529 */     return this.poNo;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFromDate() {
/*  534 */     return this.fromDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFromDate(String fromDate) {
/*  539 */     this.fromDate = fromDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getToDate() {
/*  544 */     return this.toDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setToDate(String toDate) {
/*  549 */     this.toDate = toDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoNo(String poNo) {
/*  554 */     this.poNo = poNo;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoDate() {
/*  559 */     return this.poDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoDate(String poDate) {
/*  564 */     this.poDate = poDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoCif() {
/*  569 */     return this.poCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoCif(String poCif) {
/*  574 */     this.poCif = poCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoBen() {
/*  579 */     return this.poBen;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoBen(String poBen) {
/*  584 */     this.poBen = poBen;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoInco() {
/*  589 */     return this.poInco;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoInco(String poInco) {
/*  594 */     this.poInco = poInco;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoGoodDesc() {
/*  599 */     return this.poGoodDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoGoodDesc(String poGoodDesc) {
/*  604 */     this.poGoodDesc = poGoodDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoImpName() {
/*  609 */     return this.poImpName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoImpName(String poImpName) {
/*  614 */     this.poImpName = poImpName;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoAmtValue() {
/*  619 */     return this.poAmtValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoAmtValue(String poAmtValue) {
/*  624 */     this.poAmtValue = poAmtValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoExpdate() {
/*  629 */     return this.poExpdate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoExpdate(String poExpdate) {
/*  634 */     this.poExpdate = poExpdate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoLastShipDate() {
/*  639 */     return this.poLastShipDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoLastShipDate(String poLastShipDate) {
/*  644 */     this.poLastShipDate = poLastShipDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoFrDeduct() {
/*  649 */     return this.poFrDeduct;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoFrDeduct(String poFrDeduct) {
/*  654 */     this.poFrDeduct = poFrDeduct;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoInsDeduct() {
/*  659 */     return this.poInsDeduct;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoInsDeduct(String poInsDeduct) {
/*  664 */     this.poInsDeduct = poInsDeduct;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoMargin() {
/*  669 */     return this.poMargin;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoMargin(String poMargin) {
/*  674 */     this.poMargin = poMargin;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoEligAmt() {
/*  679 */     return this.poEligAmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoEligAmt(String poEligAmt) {
/*  684 */     this.poEligAmt = poEligAmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getImporterName() {
/*  689 */     return this.importerName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setImporterName(String importerName) {
/*  694 */     this.importerName = importerName;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCount() {
/*  699 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCount(int count) {
/*  704 */     this.count = count;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  709 */     return this.description;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDescription(String description) {
/*  714 */     this.description = description;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getExportOrderNumber() {
/*  719 */     return this.exportOrderNumber;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExportOrderNumber(String exportOrderNumber) {
/*  724 */     this.exportOrderNumber = exportOrderNumber;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getExporterOrderDate() {
/*  729 */     return this.exporterOrderDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExporterOrderDate(String exporterOrderDate) {
/*  734 */     this.exporterOrderDate = exporterOrderDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCifID() {
/*  739 */     return this.cifID;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCifID(String cifID) {
/*  744 */     this.cifID = cifID;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getBeneficiaryName() {
/*  749 */     return this.beneficiaryName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBeneficiaryName(String beneficiaryName) {
/*  754 */     this.beneficiaryName = beneficiaryName;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getIncoTerms() {
/*  759 */     return this.incoTerms;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setIncoTerms(String incoTerms) {
/*  764 */     this.incoTerms = incoTerms;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getGoodsCode() {
/*  769 */     return this.goodsCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGoodsCode(String goodsCode) {
/*  774 */     this.goodsCode = goodsCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPoValue() {
/*  779 */     return this.poValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPoValue(String poValue) {
/*  784 */     this.poValue = poValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getExportcurrency() {
/*  789 */     return this.exportcurrency;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExportcurrency(String exportcurrency) {
/*  794 */     this.exportcurrency = exportcurrency;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getExportexpiryDate() {
/*  799 */     return this.exportexpiryDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExportexpiryDate(String exportexpiryDate) {
/*  804 */     this.exportexpiryDate = exportexpiryDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLastShipmentDate() {
/*  809 */     return this.lastShipmentDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastShipmentDate(String lastShipmentDate) {
/*  814 */     this.lastShipmentDate = lastShipmentDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFreightDeduction() {
/*  819 */     return this.freightDeduction;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFreightDeduction(String freightDeduction) {
/*  824 */     this.freightDeduction = freightDeduction;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInsuranceDeduction() {
/*  829 */     return this.insuranceDeduction;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInsuranceDeduction(String insuranceDeduction) {
/*  834 */     this.insuranceDeduction = insuranceDeduction;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getMarginPercentage() {
/*  839 */     return this.marginPercentage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMarginPercentage(String marginPercentage) {
/*  844 */     this.marginPercentage = marginPercentage;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getEligibleAmount() {
/*  849 */     return this.eligibleAmount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEligibleAmount(String eligibleAmount) {
/*  854 */     this.eligibleAmount = eligibleAmount;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUpdateProductId() {
/*  859 */     return this.updateProductId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpdateProductId(String updateProductId) {
/*  864 */     this.updateProductId = updateProductId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCustomerCif() {
/*  871 */     return this.customerCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomerCif(String customerCif) {
/*  876 */     this.customerCif = customerCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public ArrayList<AlertMessagesVO> getErrorList() {
/*  881 */     return this.errorList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setErrorList(ArrayList<AlertMessagesVO> errorList) {
/*  886 */     this.errorList = errorList;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getChargeType() {
/*  891 */     return this.chargeType;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChargeType(String chargeType) {
/*  896 */     this.chargeType = chargeType;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getChargeId() {
/*  901 */     return this.chargeId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChargeId(String chargeId) {
/*  906 */     this.chargeId = chargeId;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getChargeKey97() {
/*  911 */     return this.chargeKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChargeKey97(String chargeKey97) {
/*  916 */     this.chargeKey97 = chargeKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getProductType() {
/*  921 */     return this.productType;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProductType(String productType) {
/*  926 */     this.productType = productType;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getProductId() {
/*  931 */     return this.productId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProductId(String productId) {
/*  936 */     this.productId = productId;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getProductKey97() {
/*  941 */     return this.productKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProductKey97(String productKey97) {
/*  946 */     this.productKey97 = productKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getChargeDesc() {
/*  951 */     return this.chargeDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChargeDesc(String chargeDesc) {
/*  956 */     this.chargeDesc = chargeDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getProductDesc() {
/*  961 */     return this.productDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProductDesc(String productDesc) {
/*  966 */     this.productDesc = productDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUpdateCusCif() {
/*  971 */     return this.updateCusCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpdateCusCif(String updateCusCif) {
/*  976 */     this.updateCusCif = updateCusCif;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUpdateChargeId() {
/*  981 */     return this.updateChargeId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpdateChargeId(String updateChargeId) {
/*  986 */     this.updateChargeId = updateChargeId;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUpdateChargeKey97() {
/*  991 */     return this.updateChargeKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUpdateChargeKey97(String updateChargeKey97) {
/*  996 */     this.updateChargeKey97 = updateChargeKey97;
/*      */   }
/*      */ 
/*      */   
/*      */   public ArrayList<in.co.forwardcontract.vo.ChargeScheduleVO> getPurchaseOrderList() {
/* 1001 */     return this.purchaseOrderList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPurchaseOrderList(ArrayList<in.co.forwardcontract.vo.ChargeScheduleVO> purchaseOrderList) {
/* 1006 */     this.purchaseOrderList = purchaseOrderList;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInwardNo() {
/* 1011 */     return this.inwardNo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInwardNo(String inwardNo) {
/* 1016 */     this.inwardNo = inwardNo;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getGoodsDescription() {
/* 1021 */     return this.goodsDescription;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGoodsDescription(String goodsDescription) {
/* 1026 */     this.goodsDescription = goodsDescription;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCopyVal() {
/* 1031 */     return this.copyVal;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCopyVal(String copyVal) {
/* 1036 */     this.copyVal = copyVal;
/*      */   }
/*      */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\vo\ChargeScheduleVO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */