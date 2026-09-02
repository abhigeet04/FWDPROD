/*      */ package in.co.forwardcontract.action;

/*      */
/*      */ import com.opensymphony.xwork2.ActionContext;
/*      */ import in.co.forwardcontract.action.ForwardContractBaseAction;
/*      */ import in.co.forwardcontract.bd.ForwardContractBD;
/*      */ import in.co.forwardcontract.dao.exception.ApplicationException;
/*      */ import in.co.forwardcontract.utility.CommonMethods;
/*      */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*      */ import in.co.forwardcontract.utility.LoggableStatement;
/*      */ import in.co.forwardcontract.vo.ForwardContractVO;
/*      */ import in.co.forwardcontract.vo.StaticDataVO;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.struts2.ServletActionContext;

/*      */
/*      */
/*      */
/*      */ public class ForwardContractAction/*      */ extends ForwardContractBaseAction
/*      */ {
	/* 30 */ private static final Logger logger = LogManager
			.getLogger(in.co.forwardcontract.action.ForwardContractAction.class);
	/*      */
	/*      */ private static final long serialVersionUID = 1L;
	/*      */
	/*      */ ForwardContractVO fwdContractVO;
	/*      */
	/*      */ StaticDataVO staticDataVo;
	/*      */
	/*      */ ArrayList<StaticDataVO> staticDataList;
	/*      */ ArrayList<ForwardContractVO> forwardContractList;

	/*      */
	/*      */ public ForwardContractVO getFwdContractVO() {
		/* 42 */ return this.fwdContractVO;
		/*      */ }

	/*      */
	/*      */ public void setFwdContractVO(ForwardContractVO fwdContractVO) {
		/* 46 */ this.fwdContractVO = fwdContractVO;
		/*      */ }

	/*      */
	/*      */ public StaticDataVO getStaticDataVo() {
		/* 50 */ return this.staticDataVo;
		/*      */ }

	/*      */
	/*      */ public void setStaticDataVo(StaticDataVO staticDataVo) {
		/* 54 */ this.staticDataVo = staticDataVo;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> getStaticDataList() {
		/* 58 */ return this.staticDataList;
		/*      */ }

	/*      */
	/*      */ public void setStaticDataList(ArrayList<StaticDataVO> staticDataList) {
		/* 62 */ this.staticDataList = staticDataList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<ForwardContractVO> getForwardContractList() {
		/* 66 */ return this.forwardContractList;
		/*      */ }

	/*      */
	/*      */ public void setForwardContractList(ArrayList<ForwardContractVO> forwardContractList) {
		/* 70 */ this.forwardContractList = forwardContractList;
		/*      */ }

	/*      */
	/* 73 */ String idAndFwdContractNo = null;
	ArrayList<ForwardContractVO> enquiryList;

	/*      */
	/*      */ public String getIdAndFwdContractNo() {
		/* 76 */ return this.idAndFwdContractNo;
		/*      */ }

	/*      */
	/*      */ public void setIdAndFwdContractNo(String idAndFwdContractNo) {
		/* 80 */ this.idAndFwdContractNo = idAndFwdContractNo;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public ArrayList<ForwardContractVO> getEnquiryList() {
		/* 86 */ return this.enquiryList;
		/*      */ }

	/*      */
	/*      */ public void setEnquiryList(ArrayList<ForwardContractVO> enquiryList) {
		/* 90 */ this.enquiryList = enquiryList;
		/*      */ }

	/*      */
	/* 93 */ Map<String, String> subProductList = null;
	/*      */
	/* 95 */ Map<String, String> statusList = null;

	/*      */
	/*      */
	/*      */ public Map<String, String> getSubProductList() {
		/* 99 */ return SUBPRODUCT;
		/*      */ }

	/*      */
	/*      */ public void setSubProductList(Map<String, String> subProductList) {
		/* 103 */ this.subProductList = subProductList;
		/*      */ }

	/*      */
	/*      */
	/*      */ public Map<String, String> getStatusList() {
		/* 108 */ return STATUSLIST;
		/*      */ }

	/*      */
	/*      */ public void setStatusList(Map<String, String> statusList) {
		/* 112 */ this.statusList = statusList;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String landingPage() throws ApplicationException {
		/* 117 */ logger.info("Entering Method");
		/* 118 */ String sessionUserName = null;
		/* 119 */ String result = null;
		/* 120 */ String roleCount = null;
		/* 121 */ int count = 0;
		/* 122 */ ForwardContractBD fwdContractBD = null;
		/* 123 */ ForwardContractVO fwdContractVO = null;
		/* 124 */ String target = "success";
		/*      */
		/*      */ try {
			/* 127 */ fwdContractBD = new ForwardContractBD();
			/* 128 */ fwdContractBD.setProcessDate();
			/* 129 */ isSessionAvailable();
			/* 130 */ fwdContractVO = new ForwardContractVO();
			/*      */
			/* 132 */ HttpSession session = ServletActionContext.getRequest().getSession();
			/*      */
			/* 134 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 135 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/*      */
			/* 137 */ sessionUserName = (String) session.getAttribute("loginedUserName");
			/* 138 */ logger.info("loginedUserName------------------>" + sessionUserName);
			/*      */
			/* 140 */ if (sessionUserName == null) {
				/*      */
				/* 142 */ sessionUserName = request.getRemoteUser();
				/* 143 */ logger.info("getRemoteUser------------------>" + sessionUserName);
				/*      */
				/* 145 */ if (sessionUserName == null) {
					/* 146 */ Connection globalConnection = null;
					/* 147 */ globalConnection = DBConnectionUtility.getGlobalConnection();
					/*      */
					/* 149 */ sessionUserName = request.getRequestedSessionId();
					/* 150 */ String get_User_ID = "SELECT SCT.USERNAME AS USER_ID FROM CENTRAL_SESSION_DETAILS SCT,LOCAL_SESSION_DETAILS LOC  WHERE SCT.CENTRAL_ID=LOC.CENTRAL_ID AND SCT.ENDED  IS NULL AND LOC.LOCAL_ID= ? ";
					/*      */
					/*      */
					/* 153 */ LoggableStatement lst = new LoggableStatement(globalConnection, get_User_ID);
					/* 154 */ lst.setString(1, sessionUserName);
					/* 155 */ logger.info("Getting Session Value Query------------" + lst.getQueryString());
					/*      */
					/* 157 */ ResultSet rst = lst.executeQuery();
///*      */           
					/* 159 */ while (rst.next()) {
						/* 160 */ sessionUserName = rst.getString("USER_ID");
						/* 161 */ logger
								.info("Getting Session Value Query-- user id value----------" + sessionUserName);
						/*      */ }
					/*      */
					/* 164 */ session.setAttribute("loginedUserName", sessionUserName);
					/* 165 */ session.setAttribute("loginedUserId", sessionUserName);
					/* 166 */ DBConnectionUtility.surrenderDB(globalConnection, (Statement) lst, rst);
					/* 167 */ logger.info("userName-----------" + sessionUserName);
					/*      */ }
				/*      */ }
			/*      */
			/*      */
			/* 172 */ logger.info("sessionUserName-------------------------------------->" + sessionUserName);
			/*      */
			/* 174 */ fwdContractVO.setSessionUserName(sessionUserName);
			/*      */ }
		/* 176 */ catch (Exception exception) {
			/* 177 */ logger.info("User landingPage-- Exception-------------->" + exception);
			/*      */
			/* 179 */ throwApplicationException(exception);
			/*      */ }
		/* 181 */ logger.info("target-------------" + target);
		/* 182 */ logger.info("Exiting Method");
		/*      */
		/* 184 */ return target;
		/*      */ }

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
	/*      */ public String loadCancelData() throws ApplicationException {
		/* 217 */ logger.info("Entering Method");
		/* 218 */ String target = "success";
		/* 219 */ int result = 0;
		/* 220 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 222 */ fwdContractBD = new ForwardContractBD();
			/* 223 */ String sessionUserName = isSessionAvailable1();
			/* 224 */ this.fwdContractVO.setSessionUserName(sessionUserName);
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
			/*      */ }
		/* 237 */ catch (Exception exception) {
			/* 238 */ logger.info("Exception is" + exception.getMessage());
			/* 239 */ exception.printStackTrace();
			/*      */ }
		/* 241 */ logger.info("Exiting Method");
		/* 242 */ return target;
		/*      */ }

	/*      */
	/*      */ public String loadMakerProcessData() throws ApplicationException {
		/* 246 */ logger.info("Entering Method");
		/* 247 */ int count = 0;
		/* 248 */ ForwardContractBD fwdContractBD = null;
		/* 249 */ ForwardContractVO fwdContractVO = null;
		/* 250 */ String target = "success";
		/*      */
		/*      */ try {
			/* 253 */ fwdContractBD = new ForwardContractBD();
			/* 254 */ fwdContractBD.setProcessDate();
			/* 255 */ fwdContractVO = new ForwardContractVO();
			/*      */
			/* 257 */ String sessionUserName = isSessionAvailable1();
			/*      */
			/* 259 */ if (sessionUserName != null)
			/*      */ {
				/* 261 */ logger.info("Inside outer session name");
				/* 262 */ fwdContractBD = new ForwardContractBD();
				/*      */
				/* 264 */ logger.info("sessionUserName--->" + sessionUserName);
				/*      */
				/* 266 */ fwdContractVO.setSessionUserName(sessionUserName);
				/*      */
				/* 268 */ fwdContractVO.setPageType("FWCMAKER");
				/*      */
				/* 270 */ count = fwdContractBD.checkLoginedUserType(fwdContractVO);
				/*      */
				/* 272 */ logger.info("Count Session Name for Checker Eligiblity");
				/*      */
				/* 274 */ if (count > 0) {
					/* 275 */ target = "success";
					/*      */ } else {
					/* 277 */ target = "fail";
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */ else
			/*      */ {
				/* 284 */ target = "fail";
				/*      */ }
			/*      */
			/*      */ }
		/* 288 */ catch (Exception exception) {
			/*      */
			/* 290 */ logger.info("Exception is" + exception.getMessage());
			/* 291 */ exception.printStackTrace();
			/*      */ }
		/*      */
		/* 294 */ logger.info("target-------------" + target);
		/* 295 */ logger.info("Exiting Method");
		/* 296 */ return target;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String loadCheckerProcessData() throws ApplicationException {
		/* 301 */ logger.info("Entering Method");
		/* 302 */ int count = 0;
		/* 303 */ ForwardContractBD fwdContractBD = null;
		/* 304 */ ForwardContractVO fwdContractVO = null;
		/* 305 */ String target = "success";
		/*      */
		/*      */ try {
			/* 308 */ fwdContractBD = new ForwardContractBD();
			/* 309 */ fwdContractBD.setProcessDate();
			/* 310 */ fwdContractVO = new ForwardContractVO();
			/*      */
			/* 312 */ String sessionUserName = isSessionAvailable1();
			/*      */
			/* 314 */ if (sessionUserName != null)
			/*      */ {
				/* 316 */ logger.info("Inside outer session name");
				/* 317 */ fwdContractBD = new ForwardContractBD();
				/*      */
				/* 319 */ logger.info("sessionUserName--->" + sessionUserName);
				/*      */
				/* 321 */ fwdContractVO.setSessionUserName(sessionUserName);
				/*      */
				/* 323 */ fwdContractVO.setPageType("FWCCHECKER");
				/*      */
				/* 325 */ count = fwdContractBD.checkLoginedUserType(fwdContractVO);
				/*      */
				/* 327 */ logger.info("Count Session Name for Checker Eligiblity");
				/*      */
				/* 329 */ if (count > 0) {
					/* 330 */ target = "success";
					/*      */ } else {
					/* 332 */ target = "fail";
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */ else
			/*      */ {
				/* 339 */ target = "fail";
				/*      */ }
			/*      */
			/*      */ }
		/* 343 */ catch (Exception exception) {
			/*      */
			/* 345 */ logger.info("Exception is" + exception.getMessage());
			/* 346 */ exception.printStackTrace();
			/*      */ }
		/*      */
		/* 349 */ logger.info("target-------------" + target);
		/* 350 */ logger.info("Exiting Method");
		/* 351 */ return target;
		/*      */ }
	
	// ABHISHEK FOR CHECKER CANCEL WITHOUT RATE
	
	public String loadCheckerProcessDataWithoutRate() throws ApplicationException {
		logger.info("Entering Method");

		    int count = 0;

		    ForwardContractBD fwdContractBD = null;

		    ForwardContractVO fwdContractVO = null;

		    String target = "success";

		    try {

		        fwdContractBD = new ForwardContractBD();

		        fwdContractBD.setProcessDate();

		        fwdContractVO = new ForwardContractVO();

		        String sessionUserName = isSessionAvailable1();

		        if (sessionUserName != null) {
		logger.info("Inside outer session name");

		            fwdContractBD = new ForwardContractBD();
		logger.info("sessionUserName--->" + sessionUserName);

		            fwdContractVO.setSessionUserName(sessionUserName);

		            fwdContractVO.setPageType("FWCCHECKER"); // same role check as regular checker - confirm if this should differ

		            count = fwdContractBD.checkLoginedUserType(fwdContractVO);
		logger.info("Count Session Name for Checker Eligiblity - Without Rate");

		            if (count > 0) {

		                target = "success";

		            } else {

		                target = "fail";

		            }

		        } else {

		            target = "fail";

		        }

		    } catch (Exception exception) {
		logger.info("Exception is" + exception.getMessage());

		        exception.printStackTrace();

		    }
		logger.info("target-------------" + target);
		logger.info("Exiting Method");

		    return target;

		}
		
		 // END
	
	

	/*      */
	/*      */
	/*      */
	/*      */ public String loadEnquiryProcessData() throws ApplicationException {
		/* 357 */ logger.info("Entering Method");
		/*      */
		/*      */ try {
			/* 360 */ isSessionAvailable();
			/*      */ }
		/* 362 */ catch (Exception exception) {
			/*      */
			/* 364 */ logger.info("Exception is" + exception.getMessage());
			/* 365 */ exception.printStackTrace();
			/*      */ }
		/*      */
		/*      */
		/* 369 */ logger.info("Exiting Method");
		/* 370 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public String loadCancelProcessData() throws ApplicationException {
		/* 376 */ logger.info("Entering Method :: loadCancelProcessData");
		/* 377 */ String target = "success";
		/* 378 */ int result = 0;
		/* 379 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 381 */ fwdContractBD = new ForwardContractBD();
			/* 382 */ String sessionUserName = isSessionAvailable1();
			/* 383 */ this.fwdContractVO.setSessionUserName(sessionUserName);
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
			/*      */ }
		/* 396 */ catch (Exception exception) {
			/* 397 */ logger.info("Exception is" + exception.getMessage());
			/* 398 */ exception.printStackTrace();
			/*      */ }
		/* 400 */ logger.info("Exiting Method :: loadCancelProcessData");
		/* 401 */ return target;
		/*      */ }

	/*      */
	/*      */ public String fetchFWCCancelDetails() throws ApplicationException {
		/* 405 */ ForwardContractBD fwdContractBD = null;
		/* 406 */ int result = 0;
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
		/*      */ try {
			/* 441 */ fwdContractBD = new ForwardContractBD();
			/*      */
			/* 443 */ logger.info("ForwardContract fetchFWCCancelDetails ... before if Condition...");
			/* 444 */ if (this.fwdContractVO != null &&
			/* 445 */ CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo())) {
				/* 446 */ logger.info("ForwardContract fetchFWCCancelDetails ... inside if Condition...");
				/*      */
				/* 448 */ this.fwdContractVO = fwdContractBD
						.fetchFWCReferenceDetails(this.fwdContractVO.getFwdContractNo());
				/*      */ }
			/*      */
			/*      */
			/* 452 */ String sessionUserName = isSessionAvailable1();
			/* 453 */ this.fwdContractVO.setSessionUserName(sessionUserName);
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
			/*      */ }
		/* 467 */ catch (Exception e) {
			/* 468 */ e.printStackTrace();
			/* 469 */ throwApplicationException(e);
			/*      */ }
		/* 471 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchFWCReferenceDetails() throws ApplicationException {
		/* 475 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 477 */ isSessionAvailable();
			/* 478 */ String screenType = this.fwdContractVO.getScreenType();
			/*      */
			/* 480 */ if (this.fwdContractVO != null) {
				/* 481 */ logger.info("Show FWCReference Details for " + this.idAndFwdContractNo);
				/* 482 */ fwdContractBD = new ForwardContractBD();
				/* 483 */ String fwdContractNo = this.idAndFwdContractNo;
				/* 484 */ this.fwdContractVO = fwdContractBD.fetchFWCReferenceDetails(fwdContractNo);
				/* 485 */ this.fwdContractVO.setScreenType(screenType);
				/*      */ }
			/*      */
			/* 488 */ } catch (Exception e) {
			/* 489 */ e.printStackTrace();
			/* 490 */ throwApplicationException(e);
			/*      */ }
		/* 492 */ return "success";
		/*      */ }

	// ABHISHEK NEW METHOD

	public String fetchFWCCancelDetailsWithoutRate() throws ApplicationException {
		try {
			logger.info("Entering fetchFWCCancelDetailsWithoutRate Action");
			ForwardContractBD bd = new ForwardContractBD();
			bd.setProcessDate();
			isSessionAvailable();
			fwdContractVO = bd.fetchFWCCancelDetailsWithoutRate(fwdContractVO.getFwdContractNo());
			logger.info("Exiting fetchFWCCancelDetailsWithoutRate Action");
		} catch (Exception exception) {
			logger.info("fetchFWCCancelDetailsWithoutRate Exception: " + exception);
			throwApplicationException(exception);
		}
		return "success";
	}

	/*      */
	/*      */ public String fetchDependentTreasuryDetails() throws ApplicationException {
		/* 496 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 498 */ isSessionAvailable();
			/*      */
			/* 500 */ if (this.fwdContractVO != null) {
				/* 501 */ if (CommonMethods.isValidString(this.fwdContractVO.getTreasuryRefNo()) &&
				/* 502 */ CommonMethods.isValidString(this.fwdContractVO.getCustomerID())) {
					/* 503 */ fwdContractBD = new ForwardContractBD();
					/* 504 */ this.fwdContractVO = fwdContractBD.fetchDependentTreasuryDetails(this.fwdContractVO);
					/*      */ } else {
					/* 506 */ this.fwdContractVO.setOutstandingAmt("");
					/* 507 */ this.fwdContractVO.setTreasuryRate("");
					/*      */ }
				/*      */ }
			/* 510 */ } catch (Exception e) {
			/* 511 */ e.printStackTrace();
			/* 512 */ throwApplicationException(e);
			/*      */ }
		/* 514 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchDependentTreasuryCancelDetailsWithPostings() throws ApplicationException {
		/* 518 */ ForwardContractBD fwdContractBD = null;
		/* 519 */ int result1 = 0;
		/*      */
		/*      */ try {
			/* 522 */ isSessionAvailable();
			/*      */
			/* 524 */ fwdContractBD = new ForwardContractBD();
			/* 525 */ if (this.fwdContractVO != null) {
				/* 526 */ if (CommonMethods.isValidString(this.fwdContractVO.getTreasuryRefNo()) &&
				/* 527 */ CommonMethods.isValidString(this.fwdContractVO.getCustomerID())) {
					/* 528 */ this.fwdContractVO = fwdContractBD
							.fetchDependentCancelTreasuryDetails(this.fwdContractVO);
					/* 529 */ this.fwdContractVO = fwdContractBD.getFWCPostingsToReverse(this.fwdContractVO);
					/*      */ } else {
					/* 531 */ this.fwdContractVO = fwdContractBD.getFWCPostingsToReverse(this.fwdContractVO);
					/* 532 */ this.fwdContractVO.setOutstandingAmt("");
					/* 533 */ this.fwdContractVO.setTreasuryRate("");
					/*      */ }
				/*      */ }
			/* 536 */ String sessionUserName = isSessionAvailable1();
			/* 537 */ this.fwdContractVO.setSessionUserName(sessionUserName);
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
			/*      */ }
		/* 549 */ catch (Exception e) {
			/* 550 */ e.printStackTrace();
			/* 551 */ throwApplicationException(e);
			/*      */ }
		/* 553 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public String fetchParticularFwdContractDetails() throws ApplicationException {
		/* 559 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 561 */ isSessionAvailable();
			/* 562 */ String screenType = this.fwdContractVO.getScreenType();
			/*      */
			/* 564 */ if (this.fwdContractVO != null) {
				/* 565 */ logger.info("Show details " + this.idAndFwdContractNo);
				/* 566 */ fwdContractBD = new ForwardContractBD();
				/* 567 */ String[] temp = this.idAndFwdContractNo.split(":");
				/* 568 */ String id = temp[0];
				/* 569 */ String fwdContractNo = temp[1];
				/* 570 */ this.fwdContractVO = fwdContractBD.fetchParticularFwdContractDetails(id, fwdContractNo);
				/* 571 */ this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
				/* 572 */ this.fwdContractVO.setScreenType(screenType);
				/*      */
				/* 574 */ int result = 0;
				/*      */
				/* 576 */ String sessionUserName = isSessionAvailable1();
				/* 577 */ this.fwdContractVO.setSessionUserName(sessionUserName);
				/*      */
				/* 579 */ result = fwdContractBD.checkLoginedUserType1(sessionUserName, "CHECKER");
				/* 580 */ logger.info("Book screen role Check :: " + result);
				/* 581 */ if (result == 2)
				/*      */ {
					/* 583 */ logger.info("Book screen role Check :: inside if... ");
					/* 584 */ this.fwdContractVO.setDeleteFlag("true");
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/* 589 */ } catch (Exception e) {
			/* 590 */ e.printStackTrace();
			/* 591 */ throwApplicationException(e);
			/*      */ }
		/* 593 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchParticularFwdContractDetailstoModify() throws ApplicationException {
		/* 597 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 599 */ isSessionAvailable();
			/* 600 */ String screenType = this.fwdContractVO.getScreenType();
			/*      */
			/* 602 */ if (this.fwdContractVO != null) {
				/* 603 */ logger.info("Show details " + this.idAndFwdContractNo);
				/* 604 */ fwdContractBD = new ForwardContractBD();
				/* 605 */ String[] temp = this.idAndFwdContractNo.split(":");
				/* 606 */ String id = temp[0];
				/* 607 */ String fwdContractNo = temp[1];
				/* 608 */ this.fwdContractVO = fwdContractBD.fetchParticularFwdContractDetailstoModify(id,
						fwdContractNo);
				/* 609 */ this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
				/* 610 */ this.fwdContractVO.setScreenType(screenType);
				/*      */
				/*      */ }
			/*      */
			/*      */ }
		/* 615 */ catch (Exception e) {
			/* 616 */ e.printStackTrace();
			/* 617 */ throwApplicationException(e);
			/*      */ }
		/* 619 */ return "success";
		/*      */ }
	
//	
	  /**
	    * Action method for VIEW (read-only) screen.
	    * Handles both FWCCANCEL and FWCUTIL without rate.
	    */
	public String fetchParticularCancelFwdContractDetailsWithoutRateView() throws Exception {
		logger.info("Entering Method fetchParticularCancelFwdContractDetailsWithoutRate Action");
		ForwardContractBD bd = null;
		try {
			isSessionAvailable();
			String screenType = this.fwdContractVO.getScreenType();
			logger.info("Screen Type: " + screenType);
			if (this.fwdContractVO != null) {
				bd = new ForwardContractBD();
				logger.info("idAndFwdContractNo: " + this.idAndFwdContractNo);
				String[] temp = this.idAndFwdContractNo.split(":");
				String id = temp[0];
				String fwdContractNo = temp[1];
				this.fwdContractVO = bd.fetchParticularCancelFwdContractDetailsWithoutRateView(id, fwdContractNo);
				// Generate postings for view
				ForwardContractBD fwdContractBD = new ForwardContractBD();
				this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
				this.fwdContractVO.setScreenType(screenType);
				logger.info("FWC Type set from category: " + this.fwdContractVO.getFwcType());
			}
		} catch (Exception e) {
			logger.info("Exception in fetchParticularCancelFwdContractDetailsWithoutRateView Action: " + e.getMessage());
			e.printStackTrace();
			throwApplicationException(e);
		}
		logger.info("Exiting Method fetchParticularCancelFwdContractDetailsWithoutRateView Action");
		return "success";
	}

	/**
	 * Action method for MODIFY screen. Handles both FWCCANCEL and FWCUTIL without
	 * rate. Routes back to maker without rate JSP.
	 */
	public String fetchParticularFwdContractDetailstoModifyWithoutRate() throws Exception {
		logger.info("Entering Method fetchParticularFwdContractDetailstoModifyWithoutRate Action");
		ForwardContractBD bd = null;
		try {
			isSessionAvailable();
			String screenType = this.fwdContractVO.getScreenType();
			logger.info("Screen Type: " + screenType);
			if (this.fwdContractVO != null) {
				bd = new ForwardContractBD();
				logger.info("idAndFwdContractNo: " + this.idAndFwdContractNo);
				String[] temp = this.idAndFwdContractNo.split(":");
				String id = temp[0];
				String fwdContractNo = temp[1];
				this.fwdContractVO = bd.fetchParticularFwdContractDetailstoModifyWithoutRate(id, fwdContractNo);
				// Generate postings for modify screen
				ForwardContractBD fwdContractBD = new ForwardContractBD();
				this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
				this.fwdContractVO.setScreenType(screenType);
				logger.info("FWC Type set from category: " + this.fwdContractVO.getFwcType());
			}
		} catch (Exception e) {
			logger.info("Exception in fetchParticularFwdContractDetailstoModifyWithoutRate Action: " + e.getMessage());
			e.printStackTrace();
			throwApplicationException(e);
		}
		logger.info("Exiting Method fetchParticularFwdContractDetailstoModifyWithoutRate Action");
		return "success";
	}
	
	
//	

	/*      */
	/*      */ public String fetchParticularCancelFwdContractDetails() throws ApplicationException {
		/* 623 */ ForwardContractBD fwdContractBD = null;
		/* 624 */ if (this.fwdContractVO != null) {
			/* 625 */ fwdContractBD = new ForwardContractBD();
			/*      */ }
		/* 627 */ int result = 0;
		/*      */
		/*      */ try {
			/* 630 */ isSessionAvailable();
			/* 631 */ String screenType = this.fwdContractVO.getScreenType();
			/*      */
			/* 633 */ logger.info("Screen Type:" + screenType);
			/*      */
			/* 635 */ if (this.fwdContractVO != null) {
				/* 636 */ logger.info("Cancel screen");
				/*      */
				/* 638 */ logger.info("Show details " + this.idAndFwdContractNo);
				/* 639 */ fwdContractBD = new ForwardContractBD();
				/* 640 */ String[] temp = this.idAndFwdContractNo.split(":");
				/* 641 */ String id = temp[0];
				/* 642 */ String fwdContractNo = temp[1];
				/*      */
				/* 644 */ this.fwdContractVO = fwdContractBD.fetchParticularCancelFwdContractDetails(id, fwdContractNo);
				/* 645 */ this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
				/* 646 */ this.fwdContractVO.setScreenType(screenType);
				/* 647 */ logger.info("screen" + screenType);
				/*      */
				/*      */
				/*      */
				/*      */
				/* 652 */ String sessionUserName = isSessionAvailable1();
				/* 653 */ this.fwdContractVO.setSessionUserName(sessionUserName);
				/*      */
				/* 655 */ result = fwdContractBD.checkLoginedUserType1(sessionUserName, "CHECKER");
				/* 656 */ logger.info("Cancel screen role Check :: " + result);
				/* 657 */ if (result == 2)
				/*      */ {
					/* 659 */ logger.info("Cancel screen role Check :: inside if... ");
					/* 660 */ this.fwdContractVO.setDeleteFlag("true");
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/*      */ }
		/* 667 */ catch (Exception e) {
			/* 668 */ e.printStackTrace();
			/* 669 */ throwApplicationException(e);
			/*      */ }
		/* 671 */ return "success";
		/*      */ }
	
	// ABHSIHEK CANCEL
	
	public String fetchParticularCancelFwdContractDetailsWithoutRate() throws ApplicationException {
		   ForwardContractBD fwdContractBD = null;
		   if (this.fwdContractVO != null) {
		       fwdContractBD = new ForwardContractBD();
		   }
		   int result = 0;
		   try {
		       isSessionAvailable();
		       String screenType = this.fwdContractVO.getScreenType();
		logger.info("Screen Type:" + screenType);
		       if (this.fwdContractVO != null) {
		logger.info("Cancel Without Rate screen");
		logger.info("Show details " + this.idAndFwdContractNo);
		           fwdContractBD = new ForwardContractBD();
		           String[] temp = this.idAndFwdContractNo.split(":");
		           String id = temp[0];
		           String fwdContractNo = temp[1];
		           // reuse existing DAO/BD fetch - purely a read, category-agnostic, no changes needed here
		           this.fwdContractVO = fwdContractBD.fetchParticularCancelFwdContractDetails(id, fwdContractNo);
		           this.fwdContractVO = fwdContractBD.generateFWCPostings(this.fwdContractVO);
		           this.fwdContractVO.setScreenType(screenType);
		logger.info("screen" + screenType);
		           String sessionUserName = isSessionAvailable1();
		           this.fwdContractVO.setSessionUserName(sessionUserName);
		           result = fwdContractBD.checkLoginedUserType1(sessionUserName, "CHECKER");
		logger.info("Cancel Without Rate screen role Check :: " + result);
		           if (result == 2) {
		logger.info("Cancel Without Rate screen role Check :: inside if... ");
		               this.fwdContractVO.setDeleteFlag("true");
		           }
		       }
		   } catch (Exception e) {
		       e.printStackTrace();
		       throwApplicationException(e);
		   }
		   return "success";
		}

	/*      */
	/*      */ public String approveFWC() throws ApplicationException {
		/* 675 */ ForwardContractBD fwdContractBD = null;
		/*      */ try {
			/* 677 */ isSessionAvailable();
			/* 678 */ if (this.fwdContractVO != null) {
				/* 679 */ logger.info(" show approveFWC Record Details");
				/*      */
				/* 681 */ fwdContractBD = new ForwardContractBD();
				/*      */
				/* 683 */ String category = this.fwdContractVO.getCategory();
				/*      */
				/* 685 */ if (category.equalsIgnoreCase("FWCBOOK")) {
					/* 686 */ this.fwdContractVO = fwdContractBD.approveFWC(this.fwdContractVO, "FWCBOOK");
					/*      */
					/* 688 */ if (this.fwdContractVO.getCount() == 1) {
						/* 689 */ addActionMessage("Forward Contract Booking Approved successfully for " +
						/* 690 */ this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/* 692 */ else if (this.fwdContractVO.getCount() == 2) {
						/* 693 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to Posting Failure, Contact support team.");
						/*      */ }
					/* 695 */ else if (this.fwdContractVO.getCount() == 3) {
						/* 696 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to TREAS API Failure, Contact support team.");
						/*      */ }
					/* 698 */ else if (this.fwdContractVO.getCount() == 4) {
						/* 699 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to FTRT API Failure, Contact support team.");
						/*      */ }
					/* 701 */ else if (this.fwdContractVO.getCount() == 5) {
						/* 702 */ addActionError("Could not approve " + this.fwdContractVO.getFwdContractNo()
								+ " due to insufficient balance in customer account.");
						/*      */ }
					/* 704 */ else if (this.fwdContractVO.getCount() == 6) {
						/* 705 */ addActionError("FWC number " + this.fwdContractVO.getFwdContractNo()
								+ " is rejected as Treasury Reference No. does not exist");
						/*      */
						/*      */ }
					/* 708 */ else if (this.fwdContractVO.getCount() == 7) {
						/* 709 */ addActionError("Approval Failed for  " + this.fwdContractVO.getFwdContractNo()
								+ " as the custom treasury table update failed, Contact support team.");
						/*      */ }
					/*      */ else {
						/*      */
						/* 713 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ ", Contact support team.");
						/*      */ }
					/* 715 */ } else if (category.equalsIgnoreCase("FWCCANCEL")) {
					/* 716 */ this.fwdContractVO = fwdContractBD.cancelBookingDetails(this.fwdContractVO, "FWCCANCEL");
					/* 717 */ if (this.fwdContractVO.getCount() == 1) {
						/* 718 */ addActionMessage("Forward Contract Cancellation Approved successfully for " +
						/* 719 */ this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/* 721 */ else if (this.fwdContractVO.getCount() == 2) {
						/* 722 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to Posting Failure.");
						/*      */ }
					/* 724 */ else if (this.fwdContractVO.getCount() == 3) {
						/* 725 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to TREAS API Failure.");
						/*      */ }
					/* 727 */ else if (this.fwdContractVO.getCount() == 4) {
						/* 728 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to FTRT API Failure.");
						/*      */ }
					/* 730 */ else if (this.fwdContractVO.getCount() == 5) {
						/* 731 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
								+ " due to insufficient balance.");
						/*      */ }
					/* 733 */ else if (this.fwdContractVO.getCount() == 6) {
						/* 734 */ addActionError("FWC number " + this.fwdContractVO.getFwdContractNo()
								+ " is not valid for cancellation as it is deleted");
						/*      */
						/*      */ }
					/* 737 */ else if (this.fwdContractVO.getCount() == 7) {
						/* 738 */ addActionError("Approval Failed for  " + this.fwdContractVO.getFwdContractNo()
								+ " as the custom treasury table update failed");
						/*      */ }
					/*      */ else {
						/*      */
						/* 742 */ addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/*      */ }
				/*      */
				/* 746 */ this.fwdContractVO = new ForwardContractVO();
				/*      */ }
			/*      */
			/* 749 */ } catch (Exception e) {
			/* 750 */ e.printStackTrace();
			/* 751 */ throwApplicationException(e);
			/*      */ }
		/* 753 */ return "success";
		/*      */ }
	
	// ABHISHEK CANCEL APPROVED WITHOUT RATE
	public String approveFWCWithoutRate() throws ApplicationException {
		   ForwardContractBD fwdContractBD = null;
		   try {
		       isSessionAvailable();
		       if (this.fwdContractVO != null) {
		logger.info("show approveFWCWithoutRate Record Details");
		           fwdContractBD = new ForwardContractBD();
		           String category = this.fwdContractVO.getCategory();
		           if (category.equalsIgnoreCase("FWCCANCEL") || category.equalsIgnoreCase("FWCUTIL")) {
		               this.fwdContractVO = fwdContractBD.cancelBookingDetailsWithoutRate(this.fwdContractVO, category);
		               if (this.fwdContractVO.getCount() == 1) {
		                   addActionMessage("Forward Contract " + category + " Approved successfully for " +
		                       this.fwdContractVO.getFwdContractNo());
		               } else if (this.fwdContractVO.getCount() == 2) {
		                   addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
		                       + " due to Posting Failure.");
		               } else if (this.fwdContractVO.getCount() == 5) {
		                   addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo()
		                       + " due to insufficient balance.");
		               } else if (this.fwdContractVO.getCount() == 6) {
		                   addActionError("FWC number " + this.fwdContractVO.getFwdContractNo()
		                       + " is not valid for cancellation as it is deleted");
		               } else {
		                   addActionError("Approval Failed for " + this.fwdContractVO.getFwdContractNo());
		               }
		           }
		           this.fwdContractVO = new ForwardContractVO();
		       }
		   } catch (Exception e) {
		       e.printStackTrace();
		       throwApplicationException(e);
		   }
		   return "success";
		}

	/*      */
	/*      */ public String rejectFWC() throws ApplicationException {
		/* 757 */ ForwardContractBD fwdContractBD = null;
		/*      */
		/*      */ try {
			/* 760 */ isSessionAvailable();
			/* 761 */ if (this.fwdContractVO != null) {
				/* 762 */ logger.info(" show rejectFWC Record Details");
				/* 763 */ fwdContractBD = new ForwardContractBD();
				/*      */
				/* 765 */ String category = this.fwdContractVO.getCategory();
				/*      */
				/*      */
				/* 768 */ if (category.equalsIgnoreCase("FWCBOOK")) {
					/* 769 */ this.fwdContractVO = fwdContractBD.rejectFWC(this.fwdContractVO, "FWCBOOK");
					/*      */
					/* 771 */ if (this.fwdContractVO.getCount() > 0) {
						/* 772 */ addActionMessage("Forward Contract Booking Rejected successfully for " +
						/* 773 */ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 775 */ addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/* 777 */ } else if (category.equalsIgnoreCase("FWCCANCEL")) {
					/* 778 */ this.fwdContractVO = fwdContractBD.rejectFWC(this.fwdContractVO, "FWCCANCEL");
					/*      */
					/* 780 */ if (this.fwdContractVO.getCount() > 0) {
						/* 781 */ addActionMessage("Forward Contract Cancellation Rejected successfully for " +
						/* 782 */ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 784 */ addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/*      */ }
				/* 787 */ this.fwdContractVO = new ForwardContractVO();
				/*      */ }
			/* 789 */ } catch (Exception e) {
			/* 790 */ e.printStackTrace();
			/* 791 */ throwApplicationException(e);
			/*      */ }
		/* 793 */ return "success";
		/*      */ }
	
	// REJECT BY ABHISHEK
	
	public String rejectFWCWithoutRate() throws ApplicationException {

	    ForwardContractBD fwdContractBD = null;

	    try {

	        isSessionAvailable();

	        if (this.fwdContractVO != null) {
	logger.info("show rejectFWCWithoutRate Record Details");

	            fwdContractBD = new ForwardContractBD();

	            String category = this.fwdContractVO.getCategory();

	            if (category.equalsIgnoreCase("FWCCANCEL") || category.equalsIgnoreCase("FWCUTIL")) {

	                this.fwdContractVO = fwdContractBD.rejectFWC(this.fwdContractVO, category);

	                if (this.fwdContractVO.getCount() > 0) {

	                    addActionMessage("Forward Contract " + category + " Rejected successfully for " +

	                        this.fwdContractVO.getFwdContractNo());

	                } else {

	                    addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());

	                }

	            }

	            this.fwdContractVO = new ForwardContractVO();

	        }

	    } catch (Exception e) {

	        e.printStackTrace();

	        throwApplicationException(e);

	    }

	    return "success";

	}

	/*      */
	/*      */ public String deleteFWC() throws ApplicationException {
		/* 797 */ ForwardContractBD fwdContractBD = null;
		/*      */
		/*      */ try {
			/* 800 */ isSessionAvailable();
			/* 801 */ if (this.fwdContractVO != null) {
				/* 802 */ logger.info(" show deleteFWC Record Details");
				/* 803 */ fwdContractBD = new ForwardContractBD();
				/*      */
				/* 805 */ String category = this.fwdContractVO.getCategory();
				/*      */
				/*      */
				/* 808 */ if (category.equalsIgnoreCase("FWCBOOK")) {
					/* 809 */ this.fwdContractVO = fwdContractBD.deleteFWC(this.fwdContractVO, "FWCBOOK");
					/*      */
					/* 811 */ if (this.fwdContractVO.getCount() > 0) {
						/* 812 */ addActionMessage("Forward Contract Booking Deleted successfully for " +
						/* 813 */ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 815 */ addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/* 817 */ } else if (category.equalsIgnoreCase("FWCCANCEL")) {
					/* 818 */ this.fwdContractVO = fwdContractBD.deleteFWC(this.fwdContractVO, "FWCCANCEL");
					/*      */
					/* 820 */ if (this.fwdContractVO.getCount() > 0) {
						/* 821 */ addActionMessage("Forward Contract Cancellation Deleted successfully for " +
						/* 822 */ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 824 */ addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
					/*      */ }
				/* 827 */ this.fwdContractVO = new ForwardContractVO();
				/*      */ }
			/* 829 */ } catch (Exception e) {
			/* 830 */ e.printStackTrace();
			/* 831 */ throwApplicationException(e);
			/*      */ }
		/* 833 */ return "success";
		/*      */ }
	
	// ABHISHEK CHECKER DELETE
	public String deleteFWCWithoutRate() throws ApplicationException {
		   ForwardContractBD fwdContractBD = null;
		   try {
		       isSessionAvailable();
		       if (this.fwdContractVO != null) {
		logger.info("show deleteFWCWithoutRate Record Details");
		           fwdContractBD = new ForwardContractBD();
		           String category = this.fwdContractVO.getCategory();
		           if (category.equalsIgnoreCase("FWCCANCEL") || category.equalsIgnoreCase("FWCUTIL")) {
		               this.fwdContractVO = fwdContractBD.deleteFWC(this.fwdContractVO, category);
		               if (this.fwdContractVO.getCount() > 0) {
		                   addActionMessage("Forward Contract " + category + " Deleted successfully for " +
		                       this.fwdContractVO.getFwdContractNo());
		               } else {
		                   addActionError("Action Failed for " + this.fwdContractVO.getFwdContractNo());
		               }
		           }
		           this.fwdContractVO = new ForwardContractVO();
		       }
		   } catch (Exception e) {
		       e.printStackTrace();
		       throwApplicationException(e);
		   }
		   return "success";
		}

	/*      */
	/*      */ public String execute() throws Exception {
		/* 837 */ logger.info("Entering Method");
		/* 838 */ HttpServletRequest request = ServletActionContext.getRequest();
		/* 839 */ ForwardContractBD bd = null;
		/* 840 */ String userName = null;
		/* 841 */ String userID = null;
		/*      */
		/*      */ try {
			/* 844 */ bd = new ForwardContractBD();
			/* 845 */ HttpServletRequest uRequest = (HttpServletRequest) ActionContext.getContext()
					/* 846 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 847 */ userName = uRequest.getRemoteUser();
			/* 848 */ logger.info((new StringBuilder(String.valueOf(userName))).toString());
			/* 849 */ userID = bd.getSessionUser(userName);
			/* 850 */ HttpSession httpSession = request.getSession();
			/* 851 */ logger.info("USERID------>" + userID);
			/* 852 */ httpSession.setAttribute("USERID", userID);
			/* 853 */ } catch (Exception exception) {
			/* 854 */ throwApplicationException(exception);
			/*      */ }
		/* 856 */ logger.info("Exiting Method");
		/* 857 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String staticDetails() throws Exception {
		/* 861 */ logger.info("Entering Method");
		/* 862 */ this.staticDataVo = null;
		/*      */ try {
			/* 864 */ this.staticDataVo = new StaticDataVO();
			/* 865 */ } catch (Exception exception) {
			/* 866 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 869 */ logger.info("Exiting Method");
		/* 870 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchCustomerStaticData() throws Exception {
		/* 874 */ logger.info("Entering Method");
		/* 875 */ ForwardContractBD bd = null;
		/*      */
		/*      */ try {
			/* 878 */ bd = new ForwardContractBD();
			/* 879 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 881 */ if (this.staticDataVo != null) {
				/* 882 */ if (CommonMethods.isNull(this.staticDataVo.getCustomerID()) &&
				/* 883 */ CommonMethods.isNull(this.staticDataVo.getCustomerName())) {
					/* 884 */ this.staticDataList = bd.getCustomerList(this.staticDataList);
					/*      */ }
				/*      */ else {
					/*      */
					/* 888 */ this.staticDataList = bd.filterCustomer(this.staticDataVo, this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 892 */ } catch (Exception exception) {
			/* 893 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 896 */ logger.info("Exiting Method");
		/* 897 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchAccountStaticData() throws Exception {
		/* 901 */ logger.info("Entering Method");
		/*      */
		/* 903 */ ForwardContractBD bd = null;
		/*      */
		/*      */ try {
			/* 906 */ bd = new ForwardContractBD();
			/* 907 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 909 */ if (this.staticDataVo != null) {
				/* 910 */ this.staticDataVo.setCustomerID(this.fwdContractVO.getCustomerID());
				/* 911 */ if (CommonMethods.isNull(this.staticDataVo.getCustomerID()) &&
				/* 912 */ CommonMethods.isNull(this.staticDataVo.getAcctNumber())) {
					/* 913 */ this.staticDataList = bd.getAccountList(this.staticDataList);
					/*      */ }
				/*      */ else {
					/*      */
					/* 917 */ this.staticDataList = bd.filterAccount(this.staticDataVo, this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 921 */ } catch (Exception exception) {
			/* 922 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 925 */ logger.info("Exiting Method");
		/* 926 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchBranchStaticData() throws Exception {
		/* 930 */ logger.info("Entering Method");
		/*      */
		/* 932 */ ForwardContractBD bd = null;
		/*      */ try {
			/* 934 */ bd = new ForwardContractBD();
			/* 935 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 937 */ if (this.staticDataVo != null) {
				/* 938 */ if (CommonMethods.isNull(this.staticDataVo.getBranchCode())) {
					/* 939 */ this.staticDataList = bd.getBranchList(this.staticDataList);
					/*      */ }
				/*      */ else {
					/*      */
					/* 943 */ this.staticDataList = bd.filterBranch(this.staticDataVo, this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 947 */ } catch (Exception exception) {
			/* 948 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 951 */ logger.info("Exiting Method");
		/* 952 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchCurrencyStaticData() throws Exception {
		/* 956 */ logger.info("Entering Method");
		/*      */
		/* 958 */ ForwardContractBD bd = null;
		/*      */
		/*      */ try {
			/* 961 */ bd = new ForwardContractBD();
			/* 962 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 964 */ if (this.staticDataVo != null) {
				/* 965 */ if (CommonMethods.isNull(this.staticDataVo.getCurrency())) {
					/* 966 */ this.staticDataList = bd.getCurrencyList(this.staticDataList);
					/*      */ }
				/*      */ else {
					/*      */
					/* 970 */ this.staticDataList = bd.filterCurrency(this.staticDataVo, this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 974 */ } catch (Exception exception) {
			/* 975 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 978 */ logger.info("Exiting Method");
		/* 979 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchTreasuryDetails() throws Exception {
		/* 983 */ logger.info("Entering Method");
		/*      */
		/* 985 */ ForwardContractBD bd = null;
		/*      */
		/*      */ try {
			/* 988 */ bd = new ForwardContractBD();
			/* 989 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 991 */ if (this.staticDataVo != null) {
				/* 992 */ this.staticDataVo.setCustomerID(this.fwdContractVO.getCustomerID());
				/* 993 */ if (CommonMethods.isNull(this.staticDataVo.getTreasuryRefNo()) &&
				/* 994 */ CommonMethods.isNull(this.staticDataVo.getCustomerID())) {
					/* 995 */ this.staticDataList = bd.getTreasuryList(this.staticDataList);
					/*      */ } else {
					/*      */
					/* 998 */ this.staticDataList = bd.filterTreasuryDetails(this.staticDataVo, this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 1002 */ } catch (Exception exception) {
			/* 1003 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 1006 */ logger.info("Exiting Method");
		/* 1007 */ return "success";
		/*      */ }

	/*      */
	/*      */ public String fetchLimitDetails() throws Exception {
		/* 1011 */ logger.info("Entering Method");
		/*      */
		/* 1013 */ ForwardContractBD bd = null;
		/*      */ try {
			/* 1015 */ bd = new ForwardContractBD();
			/* 1016 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 1018 */ if (this.fwdContractVO != null) {
				/* 1019 */ String customerID = this.fwdContractVO.getCustomerID().trim();
				/* 1020 */ if (CommonMethods.isValidString(customerID)) {
					/* 1021 */ this.staticDataList = bd.getLimitList(customerID);
					/*      */ }
				/*      */ }
			/*      */
			/* 1025 */ } catch (Exception exception) {
			/* 1026 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 1029 */ logger.info("Exiting Method");
		/* 1030 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */ public String closeWindow() throws Exception {
		/* 1035 */ logger.info("Entering Method");
		/* 1036 */ Connection con = null;
		/* 1037 */ LoggableStatement log = null;
		/* 1038 */ ResultSet rs = null;
		/* 1039 */ String closeUrl = "";
		/*      */ try {
			/* 1041 */ con = DBConnectionUtility.getZoneConnection();
			/*      */
			/* 1043 */ String query = "SELECT TRIM(VALUE1) AS CLOSEURL FROM ETT_PARAMETER_TBL WHERE PARAMETER_ID = 'closeURL'";
			/* 1044 */ log = new LoggableStatement(con, query);
			/* 1045 */ rs = log.executeQuery();
			/*      */
			/* 1047 */ if (rs.next()) {
				/* 1048 */ closeUrl = rs.getString("CLOSEURL");
				/*      */ }
			/*      */
			/* 1051 */ HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
					/* 1052 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
			/* 1053 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/*      */
			/* 1055 */ logger.info("ForwardContract closeWindow ... cookie clear before Redirect...");
			/*      */
			/* 1057 */ HttpSession ses = request.getSession(false);
			/* 1058 */ if (ses != null) {
				/*      */
				/* 1060 */ String sesId = ses.getId();
				/* 1061 */ ses.invalidate();
				/* 1062 */ Cookie[] cookies = request.getCookies();
				byte b;
				int i;
				Cookie[] arrayOfCookie1;
				/* 1063 */ for (i = (arrayOfCookie1 = cookies).length, b = 0; b < i;) {
					Cookie cookie = arrayOfCookie1[b];
					/* 1064 */ if (sesId.equalsIgnoreCase(cookie.getValue())) {
						/*      */
						/* 1066 */ cookie.setMaxAge(0);
						/* 1067 */ cookie.setValue(null);
						/* 1068 */ cookie.setDomain(request.getServerName());
						/* 1069 */ cookie.setPath(String.valueOf(request.getServletContext().getContextPath()) + "/");
						/* 1070 */ cookie.setSecure(request.isSecure());
						/* 1071 */ response.addCookie(cookie);
						/*      */ break;
						/*      */ }
					/*      */ b++;
				}
				/*      */
				/*      */ }
			/* 1077 */ response.sendRedirect(closeUrl);
			/*      */ }
		/* 1079 */ catch (Exception exception) {
			/* 1080 */ throwApplicationException(exception);
			/*      */ } finally {
			/* 1082 */ DBConnectionUtility.surrenderDB(con, (Statement) log, rs);
			/*      */ }
		/* 1084 */ logger.info("Exiting Method");
		/* 1085 */ return "none";
		/*      */ }

	/*      */
	/*      */ public String insertBookingDetails() throws Exception {
		/* 1089 */ ForwardContractBD bd = null;
		/* 1090 */ if (this.fwdContractVO != null) {
			/* 1091 */ bd = new ForwardContractBD();
			/*      */ }
		/* 1093 */ String result = "success";
		/*      */
		/*      */ try {
			/* 1096 */ String screen = this.fwdContractVO.getScreenType();
			/* 1097 */ if (screen.equalsIgnoreCase("MakerBookingScreen")) {
				/* 1098 */ if (CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo()))
				/* 1099 */ {
					logger.info("update BookingDetails");
					/*      */
					/*      */
					/* 1102 */ this.fwdContractVO = bd.updateFwdBookingContractDetails(this.fwdContractVO, "FWCBOOK",
							/* 1103 */ "PENDING FOR APPROVAL", "Modified");
					/*      */
					/* 1105 */ if (this.fwdContractVO.getCount() > 0) {
						/* 1106 */ addActionMessage("Forward Contract Booking updated successfully for " +
						/* 1107 */ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 1109 */ addActionError(
								"Forward Contract Booking failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
				}
				/* 1111 */ else {
					logger.info("insert BookingDetails");
					/* 1112 */ this.fwdContractVO = bd.insertBookingDetails(this.fwdContractVO, "FWCBOOK",
							/* 1113 */ "PENDING FOR APPROVAL", "Booked");
					/* 1114 */ if (this.fwdContractVO.getCount() > 0)
					/* 1115 */ {
						addActionMessage("Forward Contract Booking is success with Reference Number " +
						/* 1116 */ this.fwdContractVO.getFwdContractNo());
					}
					/*      */ else
					/* 1118 */ {
						addActionError("Forward Contract Booking failed");
					}
				}
				/*      */
				/* 1120 */ } else if (screen.equalsIgnoreCase("MakerCancelScreen")) {
				/* 1121 */ logger.info("Screentype:" + screen);
				/*      */
				/* 1123 */ int count = bd.getRecordCountFromDB(this.fwdContractVO, "FWCCANCEL");
				/* 1124 */ if (CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo()) && count == 1)
				/*      */
				/*      */ {
					/* 1127 */ this.fwdContractVO = bd.updateFwdCancelContractDetails(this.fwdContractVO, "FWCCANCEL",
							/* 1128 */ "PENDING FOR APPROVAL", "Modified");
					/*      */
					/* 1130 */ if (this.fwdContractVO.getCount() > 0)
					/* 1131 */ {
						addActionMessage("Forward Contract Cancellation updated successfully for " +
						/* 1132 */ this.fwdContractVO.getFwdContractNo());
					}
					/*      */ else
					/* 1134 */ {
						addActionError("Forward Contract Cancellation updation failed for " +
						/* 1135 */ this.fwdContractVO.getFwdContractNo());
					}
				}
				/* 1136 */ else if (CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo()) && count == 0)
				/* 1137 */ {
					this.fwdContractVO = bd.insertCancelDetails(this.fwdContractVO, "FWCCANCEL",
							/* 1138 */ "PENDING FOR APPROVAL", "Cancelled");
					/* 1139 */ if (this.fwdContractVO.getCount() > 0) {
						/* 1140 */ addActionMessage(/* 1141 */ "Forward Contract Cancellation is success for "
								+ this.fwdContractVO.getFwdContractNo());
						/*      */ } else {
						/* 1143 */ addActionError(
								"Forward Contract Cancellation failed for " + this.fwdContractVO.getFwdContractNo());
						/*      */ }
				}
				/* 1145 */ else {
					addActionError("Forward Contract Cancellation failed for " + this.fwdContractVO.getFwdContractNo());
				}
				/*      */
				/*      */ }
			// ABHISHEK
				else if (screen.equalsIgnoreCase("MakerCancelScreenWithoutRate")) {
					logger.info("Screentype:" + screen);
					   String fwcType = this.fwdContractVO.getFwcType();
					logger.info("FWC Type: " + fwcType);
					   int count = bd.getRecordCountFromDB(this.fwdContractVO, fwcType); // ← was "FWCCANCEL"
					   if (CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo()) && count == 1) {
					       this.fwdContractVO = bd.updateFwdCancelContractDetails(this.fwdContractVO, fwcType, // ← was "FWCCANCEL"
					           "PENDING FOR APPROVAL", "Modified");
					       if (this.fwdContractVO.getCount() > 0) {
					           addActionMessage("Forward Contract Cancellation updated successfully for "
					               + this.fwdContractVO.getFwdContractNo());
					       } else {
					           addActionError("Forward Contract Cancellation updation failed for "
					               + this.fwdContractVO.getFwdContractNo());
					       }
					   } else if (CommonMethods.isValidString(this.fwdContractVO.getFwdContractNo()) && count == 0) {
					       this.fwdContractVO = bd.insertCancelDetails(this.fwdContractVO, fwcType, // ← was "FWCCANCEL"
					           "PENDING FOR APPROVAL", "Cancelled");
					       if (this.fwdContractVO.getCount() > 0) {
					           addActionMessage("Forward Contract Cancellation is success for "
					               + this.fwdContractVO.getFwdContractNo());
					       } else {
					           addActionError("Forward Contract Cancellation failed for "
					               + this.fwdContractVO.getFwdContractNo());
					       }
					   } else {
					       addActionError("Forward Contract Cancellation failed for "
					           + this.fwdContractVO.getFwdContractNo());
					   }
					}

			/*      */
			/* 1149 */ if (this.fwdContractVO.getCount() > 0)
			/* 1150 */ {
				this.fwdContractVO = new ForwardContractVO();
				/* 1151 */ result = "success";
			}
			/*      */
			/* 1153 */ else if (this.fwdContractVO.getErrorList().size() > 0)
			/* 1154 */ {
				if (screen.equalsIgnoreCase("MakerBookingScreen")) {
					/* 1155 */ result = "book";
					/* 1156 */ } else if (screen.equalsIgnoreCase("MakerCancelScreen")) {
					/* 1157 */ result = "cancel";
					/*      */ }

				else if (screen.equalsIgnoreCase("MakerCancelScreenWithoutRate")) { // ← ADDDED THIS ABHISHEK
					result = "cancelWithoutRate";
				}
			}
			/* 1159 */ else {
				this.fwdContractVO = new ForwardContractVO();
				/* 1160 */ result = "success";
			}
			/*      */
			/*      */
			/* 1163 */ } catch (

		Exception e) {
			/* 1164 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/* 1166 */ return result;
		/*      */ }

	/*      */
	/*      */ public String saveBookingDetails() throws Exception {
		/* 1170 */ String result = "success";
		/* 1171 */ ForwardContractBD bd = null;
		/* 1172 */ if (this.fwdContractVO != null) {
			/* 1173 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */ try {
			/* 1176 */ String screen = this.fwdContractVO.getScreenType();
			/* 1177 */ this.fwdContractVO = bd.saveBookingDetails(this.fwdContractVO);
			/*      */
			/* 1179 */ if (this.fwdContractVO.getCount() > 0)
			/* 1180 */ {
				addActionMessage(
						"Forward Contract is Saved with Reference Number " + this.fwdContractVO.getFwdContractNo());
				/* 1182 */ this.fwdContractVO = new ForwardContractVO();
				/* 1183 */ result = "success";
			}
			/*      */
			/* 1185 */ else if (this.fwdContractVO.getErrorList().size() > 0)
			/* 1186 */ {
				if (screen.equalsIgnoreCase("MakerBookingScreen")) {
					/* 1187 */ result = "book";
					/* 1188 */ } else if (screen.equalsIgnoreCase("MakerCancelScreen")) {
					/* 1189 */ result = "cancel";
					/*      */ } else if (screen.equalsIgnoreCase("MakerCancelScreenWithoutRate")) { // ← ADDED THIS
																										// ABHISHEK
					result = "cancelWithoutRate";
				}
			}
			/* 1191 */ else {
				addActionMessage("Forward Contract failed to save");
				/* 1192 */ this.fwdContractVO = new ForwardContractVO();
				/* 1193 */ result = "success";
			}
			/*      */
			/*      */
			/*      */ }
		/* 1197 */ catch (Exception e) {
			/*      */
			/* 1199 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1202 */ return result;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String validateBookingDetails() throws Exception {
		/* 1207 */ ForwardContractBD bd = null;
		/* 1208 */ String result = "success";
		/* 1209 */ int result1 = 0;
		/* 1210 */ if (this.fwdContractVO != null) {
			/* 1211 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */
		/*      */ try {
			/* 1215 */ String screen = this.fwdContractVO.getScreenType();
			/* 1216 */ this.fwdContractVO = bd.validateBookingDetails(this.fwdContractVO);
			/*      */
			/* 1218 */ if (screen.equalsIgnoreCase("MakerBookingScreen")) {
				/* 1219 */ result = "book";
				/* 1220 */ if (CommonMethods.isValidString(this.fwdContractVO.getCustomerID()) &&
				/* 1221 */ CommonMethods.isValidString(this.fwdContractVO.getSubProduct()) &&
				/* 1222 */ CommonMethods.isValidString(this.fwdContractVO.getBranchCode()) &&
				/* 1223 */ CommonMethods.isValidString(this.fwdContractVO.getToCurrencyAmt()))
					/* 1224 */ this.fwdContractVO = bd.generateFWCPostings(this.fwdContractVO);
				/* 1225 */ } else if (screen.equalsIgnoreCase("MakerCancelScreen")) {
				/* 1226 */ result = "cancel";
				/* 1227 */ if (CommonMethods.isValidString(this.fwdContractVO.getCustomerID()) &&
				/* 1228 */ CommonMethods.isValidString(this.fwdContractVO.getSubProduct()) &&
				/* 1229 */ CommonMethods.isValidString(this.fwdContractVO.getBranchCode()) &&
				/* 1230 */ CommonMethods.isValidString(this.fwdContractVO.getToCurrencyAmt())) {
					/* 1231 */ bd.getFWCPostingsToReverse(this.fwdContractVO);
					/*      */ }
				/* 1233 */ String sessionUserName = isSessionAvailable1();
				/* 1234 */ this.fwdContractVO.setSessionUserName(sessionUserName);
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */ }

			else if (screen.equalsIgnoreCase("MakerCancelScreenWithoutRate")) { // ← ADD THIS BLOCK ABHISHEK
				result = "cancelWithoutRate";
				if (CommonMethods.isValidString(this.fwdContractVO.getCustomerID())
						&& CommonMethods.isValidString(this.fwdContractVO.getSubProduct())
						&& CommonMethods.isValidString(this.fwdContractVO.getBranchCode())
						&& CommonMethods.isValidString(this.fwdContractVO.getToCurrencyAmt())) {
					bd.getFWCPostingsToReverse(this.fwdContractVO); //
				}
				String sessionUserName = isSessionAvailable1();
				this.fwdContractVO.setSessionUserName(sessionUserName);
			}
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */ }
		/* 1249 */ catch (Exception e) {
			/*      */
			/* 1251 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1254 */ return result;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String generateFWCPostings() throws Exception {
		/* 1259 */ ForwardContractBD bd = null;
		/*      */
		/* 1261 */ if (this.fwdContractVO != null) {
			/* 1262 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */ try {
			/* 1265 */ this.fwdContractVO = bd.generateFWCPostings(this.fwdContractVO);
			/* 1266 */ } catch (Exception e) {
			/*      */
			/* 1268 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1271 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */ public String getFWCPostingsToReverse() throws Exception {
		/* 1276 */ ForwardContractBD bd = null;
		/*      */
		/* 1278 */ if (this.fwdContractVO != null) {
			/* 1279 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */ try {
			/* 1282 */ this.fwdContractVO = bd.getFWCPostingsToReverse(this.fwdContractVO);
			/*      */ }
		/* 1284 */ catch (Exception e) {
			/*      */
			/* 1286 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1289 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */ public String fetchFwdContractDetails() throws Exception {
		/* 1294 */ ForwardContractBD bd = null;
		/* 1295 */ if (this.fwdContractVO != null) {
			/* 1296 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */ try {
			/* 1299 */ this.forwardContractList = bd.fetchFwdContractDetails(this.fwdContractVO);
			/* 1300 */ setForwardContractList(this.forwardContractList);
			/*      */ }
		/* 1302 */ catch (Exception e) {
			/*      */
			/* 1304 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1307 */ return "success";
		/*      */ }
	
	// ABHSIHEK CHECKER ACTION CLASS
	
	public String fetchFwdContractDetailsWithoutRate() throws Exception {
		   ForwardContractBD bd = null;
		   if (this.fwdContractVO != null) {
		       bd = new ForwardContractBD();
		   }
		   try {
		       this.forwardContractList = bd.fetchFwdContractDetailsWithoutRate(this.fwdContractVO);
		       setForwardContractList(this.forwardContractList);
		   } catch (Exception e) {
		logger.info("Exception in action" + e.getMessage());
		   }
		   return "success";
		}
	

	/*      */
	/*      */
	/*      */ public String fetchFwdContractEnquiryDetails() throws Exception {
		/* 1312 */ ForwardContractBD bd = null;
		/* 1313 */ if (this.fwdContractVO != null) {
			/* 1314 */ bd = new ForwardContractBD();
			/*      */ }
		/*      */ try {
			/* 1317 */ this.forwardContractList = bd.fetchFwdContractEnquiryDetails(this.fwdContractVO);
			/* 1318 */ setForwardContractList(this.forwardContractList);
			/*      */ }
		/* 1320 */ catch (Exception e) {
			/*      */
			/* 1322 */ logger.info("Exception in action" + e.getMessage());
			/*      */ }
		/*      */
		/* 1325 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */ public String fetchFwdContractDetailsToCancel() throws Exception {
		/* 1330 */ logger.info("Entering Method");
		/*      */
		/* 1332 */ ForwardContractBD bd = null;
		/*      */
		/*      */ try {
			/* 1335 */ bd = new ForwardContractBD();
			/* 1336 */ this.staticDataList = new ArrayList<>();
			/*      */
			/* 1338 */ if (this.staticDataVo != null) {
				/*      */
				/* 1340 */ this.staticDataVo.setCustomerID(this.fwdContractVO.getCustomerID());
				/* 1341 */ if (CommonMethods.isNull(this.staticDataVo.getFwdContractNo()) &&
				/* 1342 */ CommonMethods.isNull(this.staticDataVo.getCustomerID())) {
					/* 1343 */ this.staticDataList = bd.fetchFwdContractList(this.staticDataList);
					/*      */ } else {
					/*      */
					/* 1346 */ this.staticDataList = bd.filterfwdContractDetails(this.staticDataVo,
							this.staticDataList);
					/*      */ }
				/*      */
				/*      */ }
			/* 1350 */ } catch (Exception exception) {
			/* 1351 */ throwApplicationException(exception);
			/*      */ }
		/*      */
		/* 1354 */ logger.info("Exiting Method");
		/* 1355 */ return "success";
		/*      */ }

	/*      */
	/*      */
	/*      */ public String resetval() {
		/* 1360 */ String result = "success";
		/*      */
		/*      */ try {
			/* 1363 */ if (this.fwdContractVO.getScreenType().equalsIgnoreCase("MakerBookingScreen")) {
				/* 1364 */ result = "book";
				/* 1365 */ } else if (this.fwdContractVO.getScreenType().equalsIgnoreCase("MakerCancelScreen")) {
				/* 1366 */ result = "cancel";
				/*      */ }
			/* 1368 */ this.fwdContractVO = new ForwardContractVO();
			/*      */ }
		/* 1370 */ catch (Exception e) {
			/* 1371 */ logger.info("Exception in resetval " + e.getMessage());
			/*      */ }
		/* 1373 */ return result;
		/*      */ }
	/*      */ }

/*
 * Location: C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626
 * (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\action\
 * ForwardContractAction.class Java compiler version: 8 (52.0) JD-Core Version:
 * 1.1.3
 */