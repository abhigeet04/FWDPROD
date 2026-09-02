/*      */ package in.co.forwardcontract.action;
/*      */ 
/*      */ import com.opensymphony.xwork2.ActionContext;
/*      */ import in.co.chargeSchedule.businessdelegate.ChargeScheduleBD;
/*      */ import in.co.chargeSchedule.businessdelegate.exception.BusinessException;
/*      */ import in.co.chargeSchedule.dao.ChargeScheduleDAO;
/*      */ import in.co.chargeSchedule.dao.exception.ApplicationException;
/*      */ import in.co.chargeSchedule.utility.ActionConstants;
/*      */ import in.co.chargeSchedule.utility.CommonMethods;
/*      */ import in.co.chargeSchedule.utility.DBConnectionUtility;
/*      */ import in.co.chargeSchedule.utility.LoggableStatement;
/*      */ import in.co.chargeSchedule.vo.ChargeScheduleVO;
/*      */ import in.co.chargeSchedule.vo.ChargeSelectionVO;
/*      */ import in.co.chargeSchedule.vo.CustomerDataVO;
/*      */ import in.co.chargeSchedule.vo.ProductSelectionVO;
/*      */ import in.co.forwardcontract.action.ChargeScheduleBaseAction;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.struts2.ServletActionContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ChargeScheduleAction
/*      */   extends ChargeScheduleBaseAction
/*      */ {
/*   40 */   private static Logger logger = LogManager.getLogger(in.co.chargeSchedule.action.ChargeScheduleAction.class);
/*      */   
/*      */   private static final long serialVersionUID = 1L;
/*      */   
/*      */   ChargeScheduleVO chargeVO;
/*      */   
/*      */   CustomerDataVO cusDataVo;
/*      */   
/*      */   ProductSelectionVO productVo;
/*      */   
/*      */   ChargeSelectionVO chargeSelectionVO;
/*      */   
/*      */   ArrayList<CustomerDataVO> customerList;
/*      */   
/*      */   ArrayList<CustomerDataVO> goodsList;
/*      */   ArrayList<CustomerDataVO> inwardDocList;
/*      */   ArrayList<ChargeSelectionVO> chargeList;
/*      */   ArrayList<ProductSelectionVO> productList;
/*      */   ArrayList<ChargeScheduleVO> chargeScheduleList;
/*   59 */   ArrayList<ChargeScheduleVO> multiPaymentReferenceList = null;
/*   60 */   String[] chkList = null;
/*   61 */   String remarks = null;
/*   62 */   String check = null;
/*   63 */   String enquiryDatas = null;
/*   64 */   String poNocifID = null;
/*   65 */   ArrayList<String> poListVal = null;
/*      */ 
/*      */ 
/*      */   
/*      */   ArrayList<ChargeScheduleVO> enquiryList;
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<String> getPoListVal() {
/*   74 */     return this.poListVal;
/*      */   }
/*      */   
/*      */   public void setPoListVal(ArrayList<String> poListVal) {
/*   78 */     this.poListVal = poListVal;
/*      */   }
/*      */   
/*      */   public ArrayList<CustomerDataVO> getInwardDocList() {
/*   82 */     return this.inwardDocList;
/*      */   }
/*      */   
/*      */   public void setInwardDocList(ArrayList<CustomerDataVO> inwardDocList) {
/*   86 */     this.inwardDocList = inwardDocList;
/*      */   }
/*      */   
/*      */   public ArrayList<CustomerDataVO> getGoodsList() {
/*   90 */     return this.goodsList;
/*      */   }
/*      */   
/*      */   public void setGoodsList(ArrayList<CustomerDataVO> goodsList) {
/*   94 */     this.goodsList = goodsList;
/*      */   }
/*      */   
/*      */   public String getPoNocifID() {
/*   98 */     return this.poNocifID;
/*      */   }
/*      */   
/*      */   public void setPoNocifID(String poNocifID) {
/*  102 */     this.poNocifID = poNocifID;
/*      */   }
/*      */   
/*      */   public String getEnquiryDatas() {
/*  106 */     return this.enquiryDatas;
/*      */   }
/*      */   
/*      */   public void setEnquiryDatas(String enquiryDatas) {
/*  110 */     this.enquiryDatas = enquiryDatas;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<ChargeScheduleVO> getEnquiryList() {
/*  116 */     return this.enquiryList;
/*      */   }
/*      */   
/*      */   public void setEnquiryList(ArrayList<ChargeScheduleVO> enquiryList) {
/*  120 */     this.enquiryList = enquiryList;
/*      */   }
/*      */   
/*  123 */   Map<String, String> statusList = null;
/*      */   
/*      */   public String getCheck() {
/*  126 */     return this.check;
/*      */   }
/*      */   String msg;
/*      */   public Map<String, String> getStatusList() {
/*  130 */     return ActionConstants.REC3;
/*      */   }
/*      */   
/*      */   public void setStatusList(Map<String, String> statusList) {
/*  134 */     this.statusList = statusList;
/*      */   }
/*      */   
/*      */   public void setCheck(String check) {
/*  138 */     this.check = check;
/*      */   }
/*      */   
/*      */   public String getRemarks() {
/*  142 */     return this.remarks;
/*      */   }
/*      */   
/*      */   public void setRemarks(String remarks) {
/*  146 */     this.remarks = remarks;
/*      */   }
/*      */   
/*      */   public String[] getChkList() {
/*  150 */     return this.chkList;
/*      */   }
/*      */   
/*      */   public void setChkList(String[] chkList) {
/*  154 */     this.chkList = chkList;
/*      */   }
/*      */   
/*      */   public ArrayList<ChargeScheduleVO> getMultiPaymentReferenceList() {
/*  158 */     return this.multiPaymentReferenceList;
/*      */   }
/*      */   
/*      */   public void setMultiPaymentReferenceList(ArrayList<ChargeScheduleVO> multiPaymentReferenceList) {
/*  162 */     this.multiPaymentReferenceList = multiPaymentReferenceList;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMsg() {
/*  168 */     return this.msg;
/*      */   }
/*      */   
/*      */   public void setMsg(String msg) {
/*  172 */     this.msg = msg;
/*      */   }
/*      */   
/*      */   public ChargeScheduleVO getChargeVO() {
/*  176 */     return this.chargeVO;
/*      */   }
/*      */   
/*      */   public void setChargeVO(ChargeScheduleVO chargeVO) {
/*  180 */     this.chargeVO = chargeVO;
/*      */   }
/*      */   
/*      */   public CustomerDataVO getCusDataVo() {
/*  184 */     return this.cusDataVo;
/*      */   }
/*      */   
/*      */   public void setCusDataVo(CustomerDataVO cusDataVo) {
/*  188 */     this.cusDataVo = cusDataVo;
/*      */   }
/*      */   
/*      */   public ArrayList<CustomerDataVO> getCustomerList() {
/*  192 */     return this.customerList;
/*      */   }
/*      */   
/*      */   public void setCustomerList(ArrayList<CustomerDataVO> customerList) {
/*  196 */     this.customerList = customerList;
/*      */   }
/*      */   
/*      */   public ChargeSelectionVO getChargeSelectionVO() {
/*  200 */     return this.chargeSelectionVO;
/*      */   }
/*      */   
/*      */   public void setChargeSelectionVO(ChargeSelectionVO chargeSelectionVO) {
/*  204 */     this.chargeSelectionVO = chargeSelectionVO;
/*      */   }
/*      */   
/*      */   public ArrayList<ChargeSelectionVO> getChargeList() {
/*  208 */     return this.chargeList;
/*      */   }
/*      */   
/*      */   public void setChargeList(ArrayList<ChargeSelectionVO> chargeList) {
/*  212 */     this.chargeList = chargeList;
/*      */   }
/*      */   
/*      */   public ProductSelectionVO getProductVo() {
/*  216 */     return this.productVo;
/*      */   }
/*      */   
/*      */   public void setProductVo(ProductSelectionVO productVo) {
/*  220 */     this.productVo = productVo;
/*      */   }
/*      */   
/*      */   public ArrayList<ProductSelectionVO> getProductList() {
/*  224 */     return this.productList;
/*      */   }
/*      */   
/*      */   public void setProductList(ArrayList<ProductSelectionVO> productList) {
/*  228 */     this.productList = productList;
/*      */   }
/*      */   
/*      */   public ArrayList<ChargeScheduleVO> getChargeScheduleList() {
/*  232 */     return this.chargeScheduleList;
/*      */   }
/*      */   
/*      */   public void setChargeScheduleList(ArrayList<ChargeScheduleVO> chargeScheduleList) {
/*  236 */     this.chargeScheduleList = chargeScheduleList;
/*      */   }
/*      */ 
/*      */   
/*      */   public String landingPage() throws ApplicationException {
/*  241 */     logger.info("Entering Method");
/*  242 */     String sessionUserName = null;
/*  243 */     String result = null;
/*  244 */     String count = null;
/*  245 */     String target = null;
/*  246 */     ChargeScheduleBD chargBD = null;
/*  247 */     ChargeScheduleVO chargVO = null;
/*  248 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  250 */       bd = ChargeScheduleBD.getBD();
/*  251 */       bd.setProcessDate();
/*  252 */       isSessionAvailable();
/*  253 */       chargVO = new ChargeScheduleVO();
/*      */       
/*  255 */       HttpSession session = ServletActionContext.getRequest().getSession();
/*      */       
/*  257 */       HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*      */       
/*  259 */       sessionUserName = (String)session.getAttribute("loginedUserName");
/*  260 */       logger.info("loginedUserName------------------" + sessionUserName);
/*      */ 
/*      */       
/*  263 */       if (sessionUserName == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  268 */         sessionUserName = request.getRemoteUser();
/*  269 */         logger.info("getRemoteUser[------------------" + sessionUserName);
/*      */         
/*  271 */         if (sessionUserName == null) {
/*      */           
/*  273 */           Connection them_con = null;
/*  274 */           them_con = DBConnectionUtility.getConnectionubiconnect();
/*      */ 
/*      */           
/*  277 */           sessionUserName = request.getRequestedSessionId();
/*  278 */           String get_User_ID = "SELECT SCT.USERNAME AS USER_ID FROM CENTRAL_SESSION_DETAILS SCT,LOCAL_SESSION_DETAILS LOC  WHERE SCT.CENTRAL_ID=LOC.CENTRAL_ID AND SCT.ENDED  IS NULL AND LOC.LOCAL_ID= ? ";
/*      */ 
/*      */ 
/*      */           
/*  282 */           LoggableStatement lst = new LoggableStatement(them_con, get_User_ID);
/*  283 */           lst.setString(1, sessionUserName);
/*  284 */           logger.info("Getting Session Value Query------------" + lst.getQueryString());
/*      */ 
/*      */           
/*  287 */           ResultSet rst = lst.executeQuery();
/*      */           
/*  289 */           while (rst.next()) {
/*      */             
/*  291 */             sessionUserName = rst.getString("USER_ID");
/*  292 */             logger.info("Getting Session Value Query-- user id value----------" + sessionUserName);
/*      */           } 
/*      */           
/*  295 */           session.setAttribute("loginedUserName", "root");
/*  296 */           session.setAttribute("loginedUserId", "root");
/*  297 */           DBConnectionUtility.surrenderDB(them_con, (Statement)lst, rst);
/*  298 */           logger.info("userName-----------root");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  309 */       chargBD = new ChargeScheduleBD();
/*      */       
/*  311 */       logger.info("sessionUserName-------------------------------------->" + sessionUserName);
/*      */       
/*  313 */       chargVO.setSessionUserName(sessionUserName);
/*  314 */       count = chargBD.getRole(chargVO);
/*  315 */       logger.info("get sessionUserName count" + count);
/*  316 */       session.setAttribute("count", count);
/*  317 */       if (count != null && count.equalsIgnoreCase("1")) {
/*      */ 
/*      */         
/*  320 */         if (result != null && result.trim().equalsIgnoreCase("POMAKER")) {
/*      */           
/*  322 */           target = "maker";
/*  323 */         } else if (result != null && result.trim().equalsIgnoreCase("POCHECKER")) {
/*      */           
/*  325 */           target = "checker";
/*      */         } 
/*  327 */       } else if (count != null && count.equalsIgnoreCase("2")) {
/*      */         
/*  329 */         target = "both";
/*  330 */       }  if (count != null && count.equalsIgnoreCase("4")) {
/*      */ 
/*      */ 
/*      */         
/*  334 */         target = "both";
/*      */       }
/*      */       else {
/*      */         
/*  338 */         target = "viewer";
/*      */       } 
/*      */       
/*  341 */       logger.info("User Status---------------->" + target);
/*      */     }
/*  343 */     catch (Exception exception) {
/*  344 */       logger.info("User landingPage-- Exception-------------->" + exception);
/*      */ 
/*      */       
/*  347 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  350 */     logger.info("Exiting Method");
/*      */ 
/*      */ 
/*      */     
/*  354 */     return "both";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String loadMultiPaymentReferenceData() throws ApplicationException {
/*  360 */     logger.info("Entering Method");
/*  361 */     ChargeScheduleBD chargBD = null;
/*  362 */     String value = null;
/*      */     
/*      */     try {
/*  365 */       logger.info("cOMING");
/*  366 */       isSessionAvailable();
/*  367 */       chargBD = ChargeScheduleBD.getBD();
/*  368 */       logger.info("Mohan 1");
/*      */ 
/*      */ 
/*      */       
/*  372 */       this.multiPaymentReferenceList = chargBD.loadMultiPaymentReferenceData(this.chargeVO);
/*      */       
/*  374 */       setMultiPaymentReferenceList(this.multiPaymentReferenceList);
/*  375 */       logger.info("eXITING");
/*      */     }
/*  377 */     catch (BusinessException exception) {
/*      */       
/*  379 */       logger.info("Exception is" + exception.getMessage());
/*  380 */       exception.printStackTrace();
/*      */       
/*  382 */       throwApplicationException((Exception)exception);
/*      */     } 
/*      */     
/*  385 */     logger.info("Exiting Method");
/*  386 */     return "success";
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
/*      */   public String loadEnquiryProcessData() throws ApplicationException {
/*  422 */     logger.info("Entering Method");
/*  423 */     ChargeScheduleBD chargBD = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  428 */       chargBD = ChargeScheduleBD.getBD();
/*      */ 
/*      */       
/*  431 */       this.multiPaymentReferenceList = chargBD.loadEnquiryProcess(this.chargeVO);
/*      */       
/*  433 */       setMultiPaymentReferenceList(this.multiPaymentReferenceList);
/*  434 */       logger.info("eXITING");
/*      */     }
/*  436 */     catch (BusinessException exception) {
/*      */       
/*  438 */       logger.info("Exception is" + exception.getMessage());
/*  439 */       exception.printStackTrace();
/*      */       
/*  441 */       throwApplicationException((Exception)exception);
/*      */     } 
/*      */     
/*  444 */     logger.info("Exiting Method");
/*  445 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String showEnquiryRecordDetails() throws ApplicationException {
/*  452 */     ChargeScheduleBD chargBD = null;
/*  453 */     ArrayList<ChargeScheduleVO> list1 = null;
/*      */     try {
/*  455 */       isSessionAvailable();
/*  456 */       list1 = new ArrayList<>();
/*  457 */       if (this.chargeVO != null) {
/*  458 */         logger.info("Entered show Enquiry Record Details & Fin details");
/*  459 */         chargBD = new ChargeScheduleBD();
/*  460 */         String[] temp = this.enquiryDatas.split(":");
/*  461 */         String poNo = temp[0];
/*  462 */         String cifId = temp[1];
/*  463 */         this.chargeVO = chargBD.getEnquiryProcess(poNo, cifId);
/*      */ 
/*      */ 
/*      */         
/*  467 */         this.multiPaymentReferenceList = chargBD.loadFinanceProcess(poNo);
/*  468 */         setMultiPaymentReferenceList(this.multiPaymentReferenceList);
/*      */       } else {
/*      */         
/*  471 */         logger.info("boeSearchVO is null");
/*      */       } 
/*  473 */     } catch (Exception e) {
/*  474 */       e.printStackTrace();
/*  475 */       throwApplicationException(e);
/*      */     } 
/*  477 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String showCheckerRecordDetails() throws ApplicationException {
/*  485 */     ChargeScheduleBD chargBD = null;
/*  486 */     ArrayList<ChargeScheduleVO> list1 = null;
/*      */     try {
/*  488 */       isSessionAvailable();
/*  489 */       list1 = new ArrayList<>();
/*  490 */       if (this.chargeVO != null) {
/*  491 */         logger.info(" show Checker Record Details");
/*  492 */         chargBD = new ChargeScheduleBD();
/*  493 */         String[] temp = this.poNocifID.split(":");
/*  494 */         String poNo = temp[0];
/*  495 */         String cifId = temp[1];
/*  496 */         this.chargeVO = chargBD.getCheckerProcess(poNo, cifId);
/*      */       } else {
/*      */         
/*  499 */         logger.info("boeSearchVO is null");
/*      */       } 
/*  501 */     } catch (Exception e) {
/*  502 */       e.printStackTrace();
/*  503 */       throwApplicationException(e);
/*      */     } 
/*  505 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String approveSinglePO() throws ApplicationException {
/*  512 */     ChargeScheduleBD chargBD = null;
/*      */     
/*      */     try {
/*  515 */       isSessionAvailable();
/*  516 */       if (this.chargeVO != null) {
/*  517 */         logger.info(" show Checker Record Details");
/*  518 */         chargBD = new ChargeScheduleBD();
/*  519 */         this.chargeVO = chargBD.approveSinglePO(this.chargeVO);
/*      */       } else {
/*      */         
/*  522 */         logger.info("boeSearchVO is null");
/*      */       } 
/*  524 */     } catch (Exception e) {
/*  525 */       e.printStackTrace();
/*  526 */       throwApplicationException(e);
/*      */     } 
/*  528 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String rejectSinglePO() throws ApplicationException {
/*  534 */     ChargeScheduleBD chargBD = null;
/*      */     
/*      */     try {
/*  537 */       isSessionAvailable();
/*  538 */       if (this.chargeVO != null) {
/*  539 */         logger.info(" show Checker Record Details");
/*  540 */         chargBD = new ChargeScheduleBD();
/*  541 */         this.chargeVO = chargBD.rejectSinglePO(this.chargeVO);
/*      */       } else {
/*      */         
/*  544 */         logger.info("boeSearchVO is null");
/*      */       } 
/*  546 */     } catch (Exception e) {
/*  547 */       e.printStackTrace();
/*  548 */       throwApplicationException(e);
/*      */     } 
/*  550 */     return "success";
/*      */   }
/*      */ 
/*      */   
/*      */   public String execute() throws Exception {
/*  555 */     logger.info("Entering Method");
/*  556 */     HttpServletRequest request = ServletActionContext.getRequest();
/*  557 */     ChargeScheduleBD bd = null;
/*  558 */     String userName = null;
/*  559 */     String userID = null;
/*      */     
/*      */     try {
/*  562 */       bd = new ChargeScheduleBD();
/*  563 */       HttpServletRequest uRequest = (HttpServletRequest)ActionContext.getContext()
/*  564 */         .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*  565 */       userName = uRequest.getRemoteUser();
/*  566 */       if (userName == null) {
/*  567 */         userName = "SUPERVISOR";
/*      */       }
/*  569 */       logger.info((new StringBuilder(String.valueOf(userName))).toString());
/*  570 */       userID = bd.getSessionUser(userName);
/*  571 */       HttpSession httpSession = request.getSession();
/*  572 */       logger.info("USERID------>" + userID);
/*  573 */       httpSession.setAttribute("USERID", userID);
/*      */     }
/*  575 */     catch (Exception exception) {
/*  576 */       throwApplicationException(exception);
/*      */     } 
/*  578 */     logger.info("Exiting Method");
/*  579 */     return "success";
/*      */   }
/*      */   
/*      */   public String customerCifCode() throws Exception {
/*  583 */     logger.info("Entering Method");
/*  584 */     this.cusDataVo = null;
/*      */     try {
/*  586 */       this.cusDataVo = new CustomerDataVO();
/*      */     }
/*  588 */     catch (Exception exception) {
/*  589 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  592 */     logger.info("Exiting Method");
/*  593 */     return "success";
/*      */   }
/*      */   
/*      */   public String incoTerms() throws Exception {
/*  597 */     logger.info("Entering Method");
/*  598 */     this.cusDataVo = null;
/*      */     try {
/*  600 */       this.cusDataVo = new CustomerDataVO();
/*      */     }
/*  602 */     catch (Exception exception) {
/*  603 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  606 */     logger.info("Exiting Method");
/*  607 */     return "success";
/*      */   }
/*      */   
/*      */   public String calculate() {
/*  611 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  613 */       bd = new ChargeScheduleBD();
/*  614 */       this.chargeVO = bd.fetchvalues(this.chargeVO);
/*  615 */     } catch (Exception e) {
/*  616 */       logger.info("Exception is " + e.getMessage());
/*      */     } 
/*  618 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchBenName() {
/*  622 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  624 */       bd = new ChargeScheduleBD();
/*  625 */       this.chargeVO = bd.fetchBeneficiary(this.chargeVO);
/*      */     }
/*  627 */     catch (Exception e) {
/*  628 */       logger.info("Exception is " + e.getMessage());
/*      */     } 
/*  630 */     return "success";
/*      */   }
/*      */   
/*      */   public void Errors() throws Exception {
/*  634 */     logger.info("Entering Method");
/*  635 */     ChargeScheduleBD bd = null;
/*  636 */     String errorCode = "";
/*      */     try {
/*  638 */       bd = new ChargeScheduleBD();
/*  639 */       if (this.chargeVO != null) {
/*  640 */         bd.getErrors(errorCode, this.chargeVO);
/*      */       }
/*      */     }
/*  643 */     catch (Exception exception) {
/*  644 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  647 */     logger.info("Exiting Method");
/*      */   }
/*      */   
/*      */   public String fetchCustomer() throws Exception {
/*  651 */     logger.info("Entering Method");
/*      */ 
/*      */     
/*  654 */     ChargeScheduleBD bd = null;
/*      */     
/*      */     try {
/*  657 */       bd = new ChargeScheduleBD();
/*  658 */       this.customerList = new ArrayList<>();
/*      */       
/*  660 */       if (this.cusDataVo != null) {
/*  661 */         if (CommonMethods.isNull(this.cusDataVo.getCifID()) && 
/*  662 */           CommonMethods.isNull(this.cusDataVo.getBeneficiaryName())) {
/*  663 */           this.customerList = bd.getCustomerList(this.customerList);
/*      */         }
/*      */         else {
/*      */           
/*  667 */           logger.info("CIF ID IS" + this.cusDataVo.getCifID());
/*  668 */           this.customerList = bd.filterCustomer(this.cusDataVo, this.customerList);
/*      */         }
/*      */       
/*      */       }
/*  672 */     } catch (Exception exception) {
/*  673 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  676 */     logger.info("Exiting Method");
/*  677 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchIncoTerms() throws Exception {
/*  681 */     logger.info("Entering Method");
/*      */ 
/*      */     
/*  684 */     ChargeScheduleBD bd = null;
/*      */     
/*      */     try {
/*  687 */       bd = new ChargeScheduleBD();
/*  688 */       this.customerList = new ArrayList<>();
/*      */       
/*  690 */       if (this.cusDataVo != null) {
/*  691 */         if (CommonMethods.isNull(this.cusDataVo.getIncoTerms())) {
/*  692 */           this.customerList = bd.fetchIncoTerms(this.customerList);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  697 */           this.customerList = bd.filterIncoTerms(this.cusDataVo, this.customerList);
/*      */         }
/*      */       
/*      */       }
/*  701 */     } catch (Exception exception) {
/*  702 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  705 */     logger.info("Exiting Method");
/*  706 */     return "success";
/*      */   }
/*      */   
/*      */   public String gotoHome() throws Exception {
/*  710 */     logger.info("Entering Method");
/*      */     try {
/*  712 */       if (this.chargeVO != null) {
/*  713 */         logger.info("CIF ID==>" + this.chargeVO.getCifID());
/*  714 */         this.chargeVO.setCifID(this.chargeVO.getCifID());
/*  715 */         this.chargeVO.setBeneficiaryName(this.chargeVO.getBeneficiaryName());
/*  716 */         logger.info("chargeVO.setBeneficiaryName==>" + this.chargeVO.getBeneficiaryName());
/*  717 */         this.chargeVO.setMarginPercentage(this.chargeVO.getMarginPercentage());
/*  718 */         logger.info("chargeVO.setMarginPercentage==>" + this.chargeVO.getMarginPercentage());
/*  719 */         this.chargeVO.setIncoTerms(this.chargeVO.getIncoTerms());
/*      */       }
/*      */     
/*  722 */     } catch (Exception exception) {
/*  723 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  726 */     return "success";
/*      */   }
/*      */   
/*      */   public String chargeType() throws Exception {
/*  730 */     logger.info("Entering Method");
/*  731 */     this.chargeSelectionVO = null;
/*      */     try {
/*  733 */       this.chargeSelectionVO = new ChargeSelectionVO();
/*      */     }
/*  735 */     catch (Exception exception) {
/*  736 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  739 */     logger.info("Exiting Method");
/*  740 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchChargeList() throws Exception {
/*  744 */     logger.info("Entering Method");
/*  745 */     ChargeScheduleBD bd = null;
/*      */     
/*      */     try {
/*  748 */       bd = new ChargeScheduleBD();
/*  749 */       this.chargeList = new ArrayList<>();
/*      */       
/*  751 */       if (this.chargeSelectionVO != null) {
/*  752 */         if (CommonMethods.isNull(this.chargeSelectionVO.getFilterChargeCode()) && 
/*  753 */           CommonMethods.isNull(this.chargeSelectionVO.getFilterChargeDesc())) {
/*  754 */           this.chargeList = bd.getChargeList(this.chargeList);
/*      */         } else {
/*  756 */           this.chargeList = bd.filterChargeList(this.chargeSelectionVO, this.chargeList);
/*      */         }
/*      */       
/*      */       }
/*  760 */     } catch (Exception exception) {
/*  761 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  764 */     logger.info("Exiting Method");
/*  765 */     return "success";
/*      */   }
/*      */   
/*      */   public String productType() throws Exception {
/*  769 */     logger.info("Entering Method");
/*  770 */     this.productVo = null;
/*      */     try {
/*  772 */       this.productVo = new ProductSelectionVO();
/*      */     }
/*  774 */     catch (Exception exception) {
/*  775 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  778 */     logger.info("Exiting Method");
/*  779 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchProductList() throws Exception {
/*  783 */     logger.info("Entering Method");
/*  784 */     ChargeScheduleBD bd = null;
/*      */     
/*      */     try {
/*  787 */       bd = new ChargeScheduleBD();
/*  788 */       this.productList = new ArrayList<>();
/*      */       
/*  790 */       if (this.productVo != null) {
/*  791 */         if (CommonMethods.isNull(this.productVo.getFilterProductCode()) && 
/*  792 */           CommonMethods.isNull(this.productVo.getFilterProductDesc())) {
/*  793 */           this.productList = bd.getProductList(this.productList);
/*      */         } else {
/*  795 */           this.productList = bd.filterProductList(this.productVo, this.productList);
/*      */         }
/*      */       
/*      */       }
/*  799 */     } catch (Exception exception) {
/*  800 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  803 */     logger.info("Exiting Method");
/*  804 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchChargeScheduleList() throws Exception {
/*  808 */     logger.info("Entering Method");
/*  809 */     ChargeScheduleBD bd = null;
/*  810 */     ChargeScheduleDAO dao = null;
/*      */     try {
/*  812 */       bd = new ChargeScheduleBD();
/*  813 */       this.chargeScheduleList = new ArrayList<>();
/*      */       
/*  815 */       if (this.chargeVO != null) {
/*  816 */         if (CommonMethods.isNull(this.chargeVO.getChargeType()) && CommonMethods.isNull(this.chargeVO.getCustomerCif())) {
/*  817 */           this.chargeScheduleList = bd.getChargeScheduleList(this.chargeScheduleList);
/*      */         }
/*      */         else {
/*      */           
/*  821 */           this.chargeScheduleList = bd.filterChargeScheduleList(this.chargeVO, this.chargeScheduleList);
/*      */         }
/*      */       
/*      */       }
/*  825 */     } catch (Exception exception) {
/*  826 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  829 */     logger.info("Exiting Method");
/*  830 */     return "success";
/*      */   }
/*      */   
/*      */   public String newChargeSchedule() throws Exception {
/*  834 */     logger.info("Entering Method");
/*  835 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  837 */       bd = new ChargeScheduleBD();
/*  838 */       if (this.chargeVO != null) {
/*  839 */         this.chargeScheduleList = bd.createChargeSchedule(this.chargeVO, this.chargeScheduleList);
/*      */       }
/*      */     }
/*  842 */     catch (Exception exception) {
/*  843 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  846 */     logger.info("Exiting Method");
/*  847 */     return "success";
/*      */   }
/*      */   
/*      */   public String updateChargeSchedule() throws Exception {
/*  851 */     logger.info("Entering Method");
/*  852 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  854 */       bd = new ChargeScheduleBD();
/*  855 */       if (this.chargeVO != null) {
/*  856 */         this.chargeScheduleList = bd.updateChargeSchedule(this.chargeVO, this.chargeScheduleList);
/*      */       }
/*  858 */     } catch (Exception exception) {
/*  859 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  862 */     logger.info("Exiting Method");
/*  863 */     return "success";
/*      */   }
/*      */   
/*      */   public String deleteChargeSchedule() throws Exception {
/*  867 */     logger.info("Entering Method");
/*  868 */     ChargeScheduleBD bd = null;
/*      */     try {
/*  870 */       bd = new ChargeScheduleBD();
/*  871 */       if (this.chargeVO != null) {
/*  872 */         this.chargeScheduleList = bd.delChargeSchedule(this.chargeVO, this.chargeScheduleList);
/*      */       }
/*  874 */     } catch (Exception exception) {
/*  875 */       throwApplicationException(exception);
/*      */     } 
/*      */     
/*  878 */     logger.info("Exiting Method");
/*  879 */     return "success";
/*      */   }
/*      */   
/*      */   public String updateStatusAction() throws ApplicationException {
/*  883 */     logger.info("Entering Method");
/*  884 */     ChargeScheduleBD chargBD = null;
/*      */     
/*      */     try {
/*  887 */       logger.info("Coming");
/*  888 */       chargBD = ChargeScheduleBD.getBD();
/*  889 */       if (this.chkList != null && this.check != null) {
/*  890 */         chargBD.updateStatus(this.chkList, this.check, this.remarks);
/*      */       }
/*      */       
/*  893 */       this.multiPaymentReferenceList = chargBD.loadMultiPaymentReferenceData(this.chargeVO);
/*      */       
/*  895 */       setMultiPaymentReferenceList(this.multiPaymentReferenceList);
/*      */     }
/*  897 */     catch (BusinessException exception) {
/*      */       
/*  899 */       throwApplicationException((Exception)exception);
/*      */     } 
/*      */     
/*  902 */     logger.info("Exiting Method");
/*  903 */     this.remarks = "";
/*  904 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String closeWindow() throws Exception {
/*  913 */     logger.info("Entering Method");
/*  914 */     Connection con = null;
/*  915 */     LoggableStatement log = null;
/*  916 */     ResultSet rs = null;
/*  917 */     String closeUrl = "";
/*      */     try {
/*  919 */       con = DBConnectionUtility.getConnection();
/*      */ 
/*      */       
/*  922 */       String query = "SELECT VALUE1 FROM ETT_PARAMETER_TBL WHERE PARAMETER_ID = 'closeURL'";
/*  923 */       log = new LoggableStatement(con, query);
/*  924 */       rs = log.executeQuery();
/*      */       
/*  926 */       if (rs.next()) {
/*  927 */         closeUrl = rs.getString("VALUE1");
/*      */       }
/*      */       
/*  930 */       HttpServletResponse response = 
/*  931 */         (HttpServletResponse)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
/*  932 */       response.sendRedirect(closeUrl);
/*      */     }
/*  934 */     catch (Exception exception) {
/*  935 */       throwApplicationException(exception);
/*      */     } finally {
/*  937 */       DBConnectionUtility.surrenderDB(con, (Statement)log, rs);
/*      */     } 
/*  939 */     logger.info("Exiting Method");
/*  940 */     return "none";
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
/*      */   public String insertExport() throws Exception {
/*  967 */     ChargeScheduleBD bd = null;
/*  968 */     if (this.chargeVO != null) {
/*  969 */       bd = new ChargeScheduleBD();
/*      */     }
/*      */     try {
/*  972 */       this.chargeVO = bd.insertExport(this.chargeVO);
/*      */       
/*  974 */       if (this.chargeVO.getCount() > 0) {
/*  975 */         this.chargeVO = new ChargeScheduleVO();
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  981 */     catch (Exception e) {
/*      */       
/*  983 */       logger.info("Exception in action" + e.getMessage());
/*      */     } 
/*      */     
/*  986 */     return "success";
/*      */   }
/*      */   
/*      */   public String purchaseOrderValidations() throws Exception {
/*  990 */     ChargeScheduleBD bd = null;
/*      */     
/*  992 */     if (this.chargeVO != null) {
/*  993 */       bd = new ChargeScheduleBD();
/*      */     }
/*      */     try {
/*  996 */       this.chargeVO = bd.purchaseOrderValidations(this.chargeVO);
/*      */       
/*  998 */       if (this.chargeVO.getCount() > 0) {
/*  999 */         this.chargeVO = new ChargeScheduleVO();
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1005 */     catch (Exception e) {
/*      */       
/* 1007 */       logger.info("Exception in action" + e.getMessage());
/*      */     } 
/*      */     
/* 1010 */     return "success";
/*      */   }
/*      */ 
/*      */   
/*      */   public String searchPurchaseOrder() throws Exception {
/* 1015 */     ChargeScheduleBD bd = null;
/*      */     try {
/* 1017 */       if (this.chargeVO != null) {
/* 1018 */         bd = new ChargeScheduleBD();
/* 1019 */         this.chargeVO = bd.searchPurchaseDetails(this.chargeVO);
/*      */       } 
/* 1021 */     } catch (Exception e) {
/* 1022 */       e.printStackTrace();
/*      */     } 
/* 1024 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchPurchaseOrder() throws Exception {
/* 1028 */     ChargeScheduleBD bd = null;
/* 1029 */     if (this.chargeVO != null) {
/* 1030 */       bd = new ChargeScheduleBD();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1035 */       this.chargeVO = bd.fetchPurchaseOrder(this.chargeVO);
/* 1036 */       this.chargeVO.setPurchaseOrderList(new ArrayList());
/* 1037 */     } catch (Exception e) {
/*      */       
/* 1039 */       logger.info("Exception in action" + e.getMessage());
/*      */     } 
/*      */     
/* 1042 */     return "success";
/*      */   }
/*      */ 
/*      */   
/*      */   public String resetval() {
/*      */     try {
/* 1048 */       this.chargeVO = new ChargeScheduleVO();
/*      */     }
/* 1050 */     catch (Exception e) {
/* 1051 */       logger.info("Exception in resetval " + e.getMessage());
/*      */     } 
/* 1053 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String fetchGoodsCode() {
/* 1062 */     return "success";
/*      */   }
/*      */   
/*      */   public String searchGoodsCode() throws ApplicationException {
/* 1066 */     ChargeScheduleBD bd = null;
/*      */     try {
/* 1068 */       bd = ChargeScheduleBD.getBD();
/* 1069 */       this.goodsList = bd.searchGoodsCode(this.cusDataVo);
/* 1070 */       logger.info("Goods List Size" + this.goodsList.size());
/* 1071 */     } catch (Exception e) {
/* 1072 */       e.printStackTrace();
/*      */     } 
/* 1074 */     return "success";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String setGoodsCodeValue() throws ApplicationException {
/*      */     try {
/* 1081 */       this.chargeVO.setGoodsCode(this.cusDataVo.getGoodsCode());
/* 1082 */     } catch (Exception e) {
/* 1083 */       e.printStackTrace();
/*      */     } 
/* 1085 */     return "success";
/*      */   }
/*      */   
/*      */   public String goToInwardPage() throws ApplicationException {
/*      */     try {
/* 1090 */       if (this.chargeVO != null && this.chargeVO.getCifID() instanceof String) {
/* 1091 */         this.cusDataVo = new CustomerDataVO();
/* 1092 */         this.cusDataVo.setCustNo(this.chargeVO.getCifID());
/*      */       } 
/* 1094 */     } catch (Exception e) {
/* 1095 */       e.printStackTrace();
/*      */     } 
/* 1097 */     return "success";
/*      */   }
/*      */   
/*      */   public String fetchInwardDetails() throws ApplicationException {
/* 1101 */     ChargeScheduleBD bd = null;
/*      */     try {
/* 1103 */       bd = ChargeScheduleBD.getBD();
/* 1104 */       this.inwardDocList = bd.fetchInwardDetails(this.cusDataVo);
/*      */     }
/* 1106 */     catch (Exception e) {
/* 1107 */       e.printStackTrace();
/*      */     } 
/* 1109 */     return "success";
/*      */   }
/*      */   
/*      */   public String setMasterDetails() throws ApplicationException {
/* 1113 */     ChargeScheduleBD bd = null;
/* 1114 */     String masRef = "";
/*      */     try {
/* 1116 */       masRef = this.cusDataVo.getMaster();
/*      */       
/* 1118 */       bd = ChargeScheduleBD.getBD();
/* 1119 */       this.chargeVO = bd.setMasterDetails(this.chargeVO, masRef);
/*      */     }
/* 1121 */     catch (Exception e) {
/* 1122 */       e.printStackTrace();
/*      */     } 
/* 1124 */     return "success";
/*      */   }
/*      */ 
/*      */   
/*      */   public String gotoPurchaseOrderScreen() throws ApplicationException {
/*      */     try {
/* 1130 */       this.chargeVO.setExportOrderNumber(this.chargeVO.getExportOrderNumber());
/* 1131 */       this.chargeVO.setCifID(this.chargeVO.getCifID());
/* 1132 */       fetchPurchaseOrder();
/* 1133 */     } catch (Exception e) {
/* 1134 */       e.printStackTrace();
/*      */     } 
/* 1136 */     return "success";
/*      */   }
/*      */   public String copyPurchaseOrderDetails() {
/* 1139 */     logger.info("Value " + this.chargeVO.getCopyVal());
/*      */     
/*      */     try {
/* 1142 */       if (this.chargeVO.getCopyVal() instanceof String && !this.chargeVO.getCopyVal().equalsIgnoreCase("")) {
/* 1143 */         this.chargeVO.setExportOrderNumber(this.chargeVO.getCopyVal());
/* 1144 */         fetchPurchaseOrder();
/*      */       }
/*      */     
/*      */     }
/* 1148 */     catch (Exception e) {
/*      */       
/* 1150 */       e.printStackTrace();
/*      */     } 
/* 1152 */     return "success";
/*      */   }
/*      */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\action\ChargeScheduleAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */