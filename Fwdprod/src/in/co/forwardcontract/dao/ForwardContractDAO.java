/*      */ package in.co.forwardcontract.dao;

/*      */
/*      */ import com.opensymphony.xwork2.ActionContext;
/*      */ import in.co.forwardcontract.dao.exception.DAOException;
/*      */ import in.co.forwardcontract.service.model.DateTimeUtil;
/*      */ import in.co.forwardcontract.service.utility.AvailBalAuthCheckUtility;
/*      */ import in.co.forwardcontract.service.utility.FWCUtil;
/*      */ import in.co.forwardcontract.service.utility.FtrtSelectUtil;
/*      */ import in.co.forwardcontract.service.utility.FtrtUpdateUtil;
/*      */ import in.co.forwardcontract.service.utility.LimitBlockUnblockUtil;
/*      */ import in.co.forwardcontract.service.utility.LimitFetchUtil;
/*      */ import in.co.forwardcontract.service.utility.PostingUtil;
/*      */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*      */ import in.co.forwardcontract.service.utility.TreasUpdateUtil;
/*      */ import in.co.forwardcontract.utility.ActionConstantsQuery;
/*      */ import in.co.forwardcontract.utility.CommonMethods;
/*      */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*      */ import in.co.forwardcontract.utility.LoggableStatement;
/*      */ import in.co.forwardcontract.vo.AlertMessagesVO;
/*      */ import in.co.forwardcontract.vo.FWCPostingVO;
/*      */ import in.co.forwardcontract.vo.ForwardContractVO;
/*      */ import in.co.forwardcontract.vo.StaticDataVO;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.RoundingMode;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.lang.ArrayUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.struts2.ServletActionContext;

/*      */
/*      */
/*      */
/*      */
/*      */
/*      */ public class ForwardContractDAO/*      */ implements ActionConstantsQuery
/*      */ {
	/* 52 */ private static final String DELETE = null;
	/*      */
	/* 54 */ private static String treasuryHDDTableName = null;
	/*      */
	/*      */ static in.co.forwardcontract.dao.ForwardContractDAO dao;
	/*      */
	/* 58 */ private static final Logger logger = LogManager
			.getLogger(in.co.forwardcontract.dao.ForwardContractDAO.class);
	/*      */
	/* 60 */ private ArrayList<AlertMessagesVO> alertMsgArray = new ArrayList<>();

	/*      */
	/*      */ public static in.co.forwardcontract.dao.ForwardContractDAO getDAO() {
		/* 63 */ if (dao == null) {
			/* 64 */ dao = new in.co.forwardcontract.dao.ForwardContractDAO();
			/*      */ }
		/* 66 */ return dao;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchDependentTreasuryDetails(ForwardContractVO fwdContractVO) {
		/*      */ try {
			/* 72 */ String treRefNo = fwdContractVO.getTreasuryRefNo().trim();
			/* 73 */ String cifID = fwdContractVO.getCustomerID().trim();
			/* 74 */ String accNo = fwdContractVO.getAcctNumber();
			/* 75 */ String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
			/* 76 */ msgId = msgId.replaceAll("[- :.]", "");
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 84 */ logger.info("treRefNo & cifID  & Acc Num -->" + treRefNo + " & " + cifID + " & " + accNo);
			/* 85 */ HashMap map = checkDealUtilization(treRefNo, cifID, fwdContractVO.getFwdContractNo());
			/* 86 */ String flag = map.get("errormsg").toString();
			/*      */
			/*      */
			/*      */
			/*      */
			/* 91 */ Map<String, String> fxOptionTokens = FtrtSelectUtil.getRateDetailsFromFtrtAPI(treRefNo, cifID);
			/* 92 */ String rateStatus = ((String) fxOptionTokens.get("FtrtSelectStatus")).trim();
			/* 93 */ fwdContractVO.setRateStatus(rateStatus);
			/*      */
			/* 95 */ if (rateStatus.equalsIgnoreCase("S")) {
				/* 96 */ String treasuryRefNo = fxOptionTokens.get("TrRefNum");
				/* 97 */ String customerID = fxOptionTokens.get("CifId");
				/* 98 */ String treasuryRate = fxOptionTokens.get("TreasuryRate");
				/* 99 */ String fromCrncyCode = fxOptionTokens.get("FromCrncyCode");
				/* 100 */ String refAmount = fxOptionTokens.get("RefAmt");
				/* 101 */ String utilizedAmount = null;
				/* 102 */ String toCrncyCode = fxOptionTokens.get("ToCrncyCode");
				/* 103 */ String buyOrSell = fxOptionTokens.get("BuyOrSell");
				/* 104 */ String branchCode = fxOptionTokens.get("FreeCode1");
				/*      */
				/*      */
				/*      */
				/* 108 */ logger.info(" refAmount --> " + refAmount);
				/*      */
				/* 110 */ if (CommonMethods.isValidString(fxOptionTokens.get("UtilizedAmt"))) {
					/* 111 */ utilizedAmount = fxOptionTokens.get("UtilizedAmt");
					/*      */ } else {
					/* 113 */ utilizedAmount = (new BigDecimal(0)).toString();
					/*      */ }
				/* 115 */ logger.info("utilizedAmount" + utilizedAmount);
				/*      */
				/* 117 */ BigDecimal availableAmount = (new BigDecimal(fxOptionTokens.get("RefAmt")))
						/* 118 */ .subtract(new BigDecimal(utilizedAmount));
				/* 119 */ logger.info("availableAmount" + availableAmount);
				/* 120 */ logger.info("refAmount" + refAmount);
				/*      */
				/* 122 */ int compareResult = availableAmount.compareTo(new BigDecimal(refAmount));
				/*      */
				/* 124 */ logger.info("compare result:" + compareResult);
				/*      */
				/* 126 */ if (compareResult != -1) {
					/*      */
					/* 128 */ fwdContractVO.setCustomerID(customerID);
					/* 129 */ fwdContractVO.setTreasuryRefNo(treasuryRefNo);
					/* 130 */ fwdContractVO.setTreasuryRate(treasuryRate);
					/* 131 */ fwdContractVO.setOutstandingAmt(availableAmount + " " + fromCrncyCode);
					/* 132 */ fwdContractVO.setFwdContractAmt(String.valueOf(refAmount) + " " + fromCrncyCode);
					/* 133 */ fwdContractVO.setDealCurrency(fromCrncyCode);
					/* 134 */ fwdContractVO.setBranchCode(branchCode);
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/* 140 */ BigDecimal toAmount = new BigDecimal(0);
					/*      */
					/* 142 */ if (CommonMethods.isValidString(toCrncyCode)
							&& CommonMethods.isValidString(treasuryRate)) {
						/* 143 */ toAmount = (new BigDecimal(refAmount)).multiply(new BigDecimal(treasuryRate));
						/* 144 */ logger.info(" treasuryRate --> " + treasuryRate);
						/*      */ }
					/* 146 */ toAmount = toAmount.setScale(2, RoundingMode.HALF_UP);
					/* 147 */ String toCcyAmount = toAmount + " " + toCrncyCode;
					/* 148 */ logger.info(" toAmount &&  toCrncyCode --> " + toCcyAmount);
					/* 149 */ fwdContractVO.setToCurrencyAmt(toCcyAmount);
					/* 150 */ fwdContractVO.setRateBuyOrSell(buyOrSell);
					/*      */ } else {
					/*      */
					/* 153 */ fwdContractVO.setRateStatus("NoBal");
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/*      */ }
		/* 160 */ catch (Exception e) {
			/* 161 */ e.printStackTrace();
			/*      */ }
		/* 163 */ logger.info("Exiting Method");
		/* 164 */ return fwdContractVO;
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
	/*      */ public HashMap checkDealUtilization(String treRefNo, String cifID, String forwardnum) {
		/* 286 */ String errormsg = "Y";
		/* 287 */ String fwcnum = "";
		/* 288 */ String query = null;
		/* 289 */ ResultSet resultSet = null;
		/* 290 */ HashMap<String, String> map = new HashMap<>();
		/* 291 */ Connection tiZoneConnection = null;
		/* 292 */ PreparedStatement preparedStatement = null;
		/*      */
		/*      */ try {
			/* 295 */ query = "SELECT * FROM CUSTOM_FWC_DETAILS WHERE TREASURY_REF_NO =? AND CIF_ID=?";
			/*      */
			/* 297 */ tiZoneConnection = DBConnectionUtility.getZoneConnection();
			/* 298 */ preparedStatement = tiZoneConnection.prepareStatement(query);
			/* 299 */ preparedStatement.setString(1, treRefNo);
			/* 300 */ preparedStatement.setString(2, cifID);
			/* 301 */ resultSet = preparedStatement.executeQuery();
			/* 302 */ while (resultSet.next()) {
				/* 303 */ if (resultSet.getString("STATUS") != null && (
				/* 304 */ !"REJECTED".equalsIgnoreCase(resultSet.getString("STATUS"))
						|| ("REJECTED".equalsIgnoreCase(resultSet.getString("STATUS")) &&
						/* 305 */ !forwardnum.equalsIgnoreCase(resultSet.getString("FWC_CONTRACT_NO"))))) {
					/* 306 */ errormsg = "AU";
					/* 307 */ fwcnum = (resultSet.getString("FWC_CONTRACT_NO") != null)
							? resultSet.getString("FWC_CONTRACT_NO")
							:
							/* 308 */ "";
					break;
					/*      */ }
				/* 310 */ if (resultSet.getString("STATUS") != null &&
				/* 311 */ "REJECTED".equalsIgnoreCase(resultSet.getString("STATUS"))) {
					/* 312 */ String date1 = resultSet.getString("BOOKING_DATE");
					/* 313 */ logger.info("BOOKING_DATE date1 : " + date1);
					/*      */
					/* 315 */ if (date1.length() > 10) {
						/* 316 */ date1 = date1.substring(0, 10).replace('-', '/');
						/*      */ } else {
						/* 318 */ date1 = "20" + date1.substring(6) + "/" + date1.substring(3, 5) + "/"
								+ date1.substring(0, 2);
						/* 319 */ }
					logger.info("checkDealUtilization date1 : " + date1);
					/* 320 */ String date2 = getTICurrentDateFormat();
					/* 321 */ logger.info("checkDealUtilization date2 : " + date2);
					/* 322 */ if (date1 != null && date2 != null && date1.compareTo(date2) != 0) {
						/* 323 */ errormsg = "AR";
						/* 324 */ fwcnum = (resultSet.getString("FWC_CONTRACT_NO") != null)
								? resultSet.getString("FWC_CONTRACT_NO")
								:
								/* 325 */ "";
						/*      */ }
					/*      */ }
				/*      */ }
			/* 329 */ logger.info("checkDealUtilization result : " + errormsg);
			/* 330 */ map.put("errormsg", errormsg);
			/* 331 */ map.put("fwcnum", fwcnum);
			/*      */ }
		/* 333 */ catch (SQLException e) {
			/* 334 */ e.printStackTrace();
			/*      */ } finally {
			/* 336 */ DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
			/*      */ }
		/* 338 */ return map;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchDependentCancelTreasuryDetails(ForwardContractVO fwdContractVO) {
		/*      */ try {
			/* 345 */ logger.info("Enter into fetch DependantCancel ");
			/*      */
			/* 347 */ String treRefNo = fwdContractVO.getTreasuryRefNo().trim();
			/* 348 */ String cifID = fwdContractVO.getCustomerID().trim();
			/*      */
			/* 350 */ logger.info("treRefNo & cifID -->" + treRefNo + " & " + cifID);
			/*      */
			/* 352 */ Map<String, String> fxOptionTokens = FtrtSelectUtil.getRateDetailsFromFtrtAPI(treRefNo, cifID);
			/*      */
			/* 354 */ String rateStatus = ((String) fxOptionTokens.get("FtrtSelectStatus")).trim();
			/* 355 */ fwdContractVO.setRateStatus(rateStatus);
			/* 356 */ String fwdcontractnum = fwdContractVO.getFwdContractNo();
			/*      */
			/*      */
			/* 359 */ String buyorsell = getBuyorSell(fwdcontractnum);
			/* 360 */ logger.info("Forward Contract Number:" + fwdcontractnum);
			/* 361 */ String fwdcontrbal = null;
			/* 362 */ logger.info("buyorsell:" + buyorsell);
			/*      */
			/* 364 */ if (buyorsell.equalsIgnoreCase("S")) {
				/* 365 */ fwdcontrbal = getFwContractBalsell(fwdContractVO.getFwdContractNo(), cifID);
				/* 366 */ } else if (buyorsell.equalsIgnoreCase("B")) {
				/* 367 */ fwdcontrbal = getFwContractBalbuy(fwdContractVO.getFwdContractNo(), cifID);
				/*      */ }
			/* 369 */ logger.info("Forward Contract Balance:" + fwdcontrbal);
			/*      */
			/* 371 */ String bookingrate = getBookingTreasuryrate(fwdContractVO.getFwdContractNo());
			/*      */
			/* 373 */ if (rateStatus.equalsIgnoreCase("S")) {
				/* 374 */ String treasuryRefNo = fxOptionTokens.get("TrRefNum");
				/* 375 */ String customerID = fxOptionTokens.get("CifId");
				/* 376 */ String treasuryRate = fxOptionTokens.get("TreasuryRate");
				/* 377 */ String fromCrncyCode = fxOptionTokens.get("FromCrncyCode");
				/* 378 */ String refAmount = fxOptionTokens.get("RefAmt");
				/* 379 */ String utilizedAmount = null;
				/* 380 */ String toCrncyCode = fxOptionTokens.get("ToCrncyCode");
				/* 381 */ String buyOrSell = fxOptionTokens.get("BuyOrSell");
				/* 382 */ String branchCode = fxOptionTokens.get("FreeCode1");
				/*      */
				/*      */
				/*      */
				/* 386 */ logger.info(" refAmount --> " + refAmount);
				/*      */
				/* 388 */ if (CommonMethods.isValidString(fxOptionTokens.get("UtilizedAmt"))) {
					/* 389 */ utilizedAmount = fxOptionTokens.get("UtilizedAmt");
					/*      */ } else {
					/* 391 */ utilizedAmount = (new BigDecimal(0)).toString();
					/*      */ }
				/* 393 */ logger.info("utilizedAmount" + utilizedAmount);
				/*      */
				/* 395 */ BigDecimal availableAmount = (new BigDecimal(fxOptionTokens.get("RefAmt")))
						/* 396 */ .subtract(new BigDecimal(utilizedAmount));
				/* 397 */ logger.info("availableAmount" + availableAmount);
				/* 398 */ logger.info("refAmount" + refAmount);
				/*      */
				/* 400 */ int compareResult = availableAmount.compareTo(new BigDecimal(refAmount));
				/*      */
				/* 402 */ logger.info("compare result:" + compareResult);
				/*      */
				/* 404 */ if (compareResult != -1) {
					/*      */
					/* 406 */ fwdContractVO.setCustomerID(customerID);
					/* 407 */ fwdContractVO.setTreasuryRefNo(treasuryRefNo);
					/* 408 */ fwdContractVO.setTreasuryRate(treasuryRate);
					/*      */
					/*      */
					/*      */
					/*      */
					/* 413 */ fwdContractVO.setOutstandingAmt(availableAmount + " " + fromCrncyCode);
					/* 414 */ fwdContractVO.setFwdContractAmt(String.valueOf(refAmount) + " " + fromCrncyCode);
					/* 415 */ fwdContractVO.setDealCurrency(fromCrncyCode);
					/* 416 */ fwdContractVO.setBranchCode(branchCode);
					/* 417 */ fwdContractVO.setCancellationamount(String.valueOf(fwdcontrbal) + " " + fromCrncyCode);
					/* 418 */ fwdContractVO.setBookingrate(bookingrate);
					/*      */
					/* 420 */ BigDecimal PLAmt = new BigDecimal(0);
					/* 421 */ BigDecimal PLAmtBeforeConvertion = new BigDecimal(0);
					/* 422 */ BigDecimal cancelamt = availableAmount;
					/* 423 */ BigDecimal bookingamount = new BigDecimal(refAmount);
					/* 424 */ BigDecimal bookingtreasrate = new BigDecimal(bookingrate);
					/* 425 */ logger.info("bookingamount" + bookingamount);
					/* 426 */ BigDecimal cancelrate = new BigDecimal(treasuryRate);
					/* 427 */ String fxRateOnCurrency = "";
					/* 428 */ String cancelAmtTres = "";
					/* 429 */ if (buyorsell.equalsIgnoreCase("S")) {
						/*      */
						/* 431 */ BigDecimal rateDiff = new BigDecimal(0);
						/* 432 */ rateDiff = bookingtreasrate.subtract(cancelrate);
						/* 433 */ logger.info("bookingtreasrate " + bookingtreasrate + " cancelrate " + cancelrate
								+ " rateDiff " + rateDiff);
						/* 434 */ cancelAmtTres = getBuyOrSellAmount(fwdcontractnum, treRefNo, buyOrSell);
						/* 435 */ BigDecimal cancellationamtFrmTreasury = new BigDecimal(cancelAmtTres);
						/* 436 */ PLAmtBeforeConvertion = cancellationamtFrmTreasury.multiply(rateDiff);
						/* 437 */ logger.info("PLAmtBeforeConvertion" + PLAmtBeforeConvertion);
						/*      */
						/* 439 */ if (PLAmtBeforeConvertion.compareTo(BigDecimal.ZERO) > 0) {
							/* 440 */ fxRateOnCurrency = getRateForConversion(toCrncyCode, "B");
							/*      */ } else {
							/* 442 */ fxRateOnCurrency = getRateForConversion(toCrncyCode, "S");
							/*      */ }
						/*      */
						/* 445 */ BigDecimal fxRateOnCurrencyFetched = new BigDecimal(fxRateOnCurrency);
						/* 446 */ PLAmt = PLAmtBeforeConvertion.multiply(fxRateOnCurrencyFetched);
						/*      */
						/*      */
						/*      */ }
					/* 450 */ else if (buyorsell.equalsIgnoreCase("B")) {
						/*      */
						/* 452 */ BigDecimal rateDiff = new BigDecimal(0);
						/* 453 */ rateDiff = bookingtreasrate.subtract(cancelrate);
						/* 454 */ logger.info("bookingtreasrate " + bookingtreasrate + " cancelrate " + cancelrate
								+ " rateDiff " + rateDiff);
						/* 455 */ cancelAmtTres = getBuyOrSellAmount(fwdcontractnum, treRefNo, buyOrSell);
						/* 456 */ BigDecimal cancellationamtFrmTreasury = new BigDecimal(cancelAmtTres);
						/* 457 */ PLAmtBeforeConvertion = cancellationamtFrmTreasury.multiply(rateDiff);
						/* 458 */ logger.info("PLAmtBeforeConvertion" + PLAmtBeforeConvertion);
						/*      */
						/* 460 */ if (PLAmtBeforeConvertion.compareTo(BigDecimal.ZERO) > 0) {
							/* 461 */ fxRateOnCurrency = getRateForConversion(toCrncyCode, "B");
							/*      */ } else {
							/* 463 */ fxRateOnCurrency = getRateForConversion(toCrncyCode, "S");
							/*      */ }
						/*      */
						/* 466 */ BigDecimal fxRateOnCurrencyFetched = new BigDecimal(fxRateOnCurrency);
						/* 467 */ PLAmt = PLAmtBeforeConvertion.multiply(fxRateOnCurrencyFetched);
						/*      */ }
					/*      */
					/* 470 */ fwdContractVO.setPlAmount(PLAmt + " " + "INR");
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
					/* 494 */ BigDecimal toAmount = new BigDecimal(0);
					/*      */
					/* 496 */ if (CommonMethods.isValidString(toCrncyCode)
							&& CommonMethods.isValidString(treasuryRate)) {
						/* 497 */ toAmount = (new BigDecimal(refAmount)).multiply(new BigDecimal(treasuryRate));
						/* 498 */ logger.info(" treasuryRate --> " + treasuryRate);
						/*      */ }
					/* 500 */ toAmount = toAmount.setScale(2, RoundingMode.HALF_UP);
					/* 501 */ String toCcyAmount = toAmount + " " + toCrncyCode;
					/* 502 */ logger.info(" toAmount &&  toCrncyCode --> " + toCcyAmount);
					/* 503 */ fwdContractVO.setToCurrencyAmt(toCcyAmount);
					/* 504 */ fwdContractVO.setRateBuyOrSell(buyOrSell);
					/*      */ } else {
					/* 506 */ fwdContractVO.setRateStatus("NoBal");
					/*      */ }
				/*      */
				/*      */ }
			/* 510 */ } catch (Exception e) {
			/* 511 */ fwdContractVO.setRateStatus("D");
			/* 512 */ e.printStackTrace();
			/*      */ }
		/* 514 */ logger.info("Exiting Method");
		/* 515 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String getBuyorSell(String fwdcontractnum) {
		/* 524 */ logger.info("Entering Method");
		/* 525 */ LoggableStatement pst = null;
		/* 526 */ ResultSet rs = null;
		/* 527 */ Connection con = null;
		/* 528 */ String buyorsell = null;
		/* 529 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */ try {
			/* 531 */ logger.info("Enter into Buy or sell Rate");
			/* 532 */ con = DBConnectionUtility.getZoneConnection();
			/* 533 */ String query = "SELECT BUY_OR_SELL FROM " + treasuryHDDTableName + " WHERE FWC_REF_NUM='" +
			/* 534 */ fwdcontractnum.trim() + "' AND HOST_DEAL_CATEGORY ='FWCBOOK'";
			/* 535 */ logger.info("Query:" + query);
			/* 536 */ pst = new LoggableStatement(con, query);
			/* 537 */ rs = pst.executeQuery();
			/* 538 */ while (rs.next()) {
				/* 539 */ buyorsell = rs.getString("BUY_OR_SELL");
				/* 540 */ logger.info("buyorsell::" + buyorsell);
				/*      */ }
			/*      */
			/* 543 */ logger.info("Buy or Sell:" + buyorsell);
			/* 544 */ } catch (Exception e) {
			/* 545 */ e.printStackTrace();
			/*      */ } finally {
			/* 547 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/*      */
		/* 550 */ return buyorsell;
		/*      */ }

	/*      */
	/*      */
	/*      */ public static String getFwContractBalbuy(String fwContractNo, String custId) {
		/* 555 */ ResultSet resultSet = null;
		/*      */
		/* 557 */ Connection tiZoneConnection = null;
		/*      */
		/* 559 */ PreparedStatement preparedStatement = null;
		/* 560 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */
		/* 562 */ String purchaseAmount = null;
		/*      */
		/* 564 */ String saleAmount = null;
		/*      */
		/*      */
		/*      */ try {
			/* 568 */ String availablePurchaseAndSaleAmtsQuery = "SELECT TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN BUY_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN BUY_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -BUY_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -SELL_AMOUNT END)) AS BUY_AMOUNT,  TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN SELL_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN SELL_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -SELL_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -BUY_AMOUNT END)) AS SELL_AMOUNT FROM "
					+
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
					/* 584 */ treasuryHDDTableName +
					/*      */
					/* 586 */ " WHERE RECORD_STATUS <>'DELETED' AND RECORD_STATUS <>'TRANSFER' AND COUNTERPARTY_STRING IS NOT NULL AND FWC_REF_NUM IS NOT NULL "
					+
					/*      */
					/* 588 */ " AND FWC_REF_NUM=? AND COUNTERPARTY_STRING =? AND HOST_DEAL_CATEGORY <> 'FXRATE'";
			/*      */
			/* 590 */ tiZoneConnection = DBConnectionUtility.getZoneConnection();
			/*      */
			/* 592 */ preparedStatement = tiZoneConnection.prepareStatement(availablePurchaseAndSaleAmtsQuery);
			/*      */
			/* 594 */ preparedStatement.setString(1, fwContractNo.trim());
			/*      */
			/* 596 */ preparedStatement.setString(2, custId.trim());
			/*      */
			/* 598 */ resultSet = preparedStatement.executeQuery();
			/*      */
			/* 600 */ while (resultSet.next())
			/*      */ {
				/* 602 */ if (CommonMethods.isValidString(resultSet.getString("BUY_AMOUNT")))
				/*      */ {
					/* 604 */ purchaseAmount = resultSet.getString("BUY_AMOUNT");
					/*      */ }
				/* 606 */ if (CommonMethods.isValidString(resultSet.getString("SELL_AMOUNT")))
				/*      */ {
					/* 608 */ saleAmount = resultSet.getString("SELL_AMOUNT");
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/*      */
			/*      */ }
		/* 616 */ catch (SQLException e) {
			/*      */
			/* 618 */ e.printStackTrace();
			/*      */ }
		/*      */ finally {
			/*      */
			/* 622 */ DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
			/*      */ }
		/*      */
		/*      */
		/* 626 */ return purchaseAmount;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public static String getFwContractBalsell(String fwContractNo, String custId) {
		/* 632 */ ResultSet resultSet = null;
		/*      */
		/* 634 */ Connection tiZoneConnection = null;
		/*      */
		/* 636 */ PreparedStatement preparedStatement = null;
		/* 637 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */
		/* 639 */ String purchaseAmount = null;
		/*      */
		/* 641 */ String saleAmount = null;
		/*      */
		/*      */
		/*      */ try {
			/* 645 */ String availablePurchaseAndSaleAmtsQuery = "SELECT TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN BUY_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN BUY_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -BUY_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -SELL_AMOUNT END)) AS BUY_AMOUNT,  TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN SELL_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN SELL_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -SELL_AMOUNT           WHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -BUY_AMOUNT END)) AS SELL_AMOUNT FROM "
					+
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
					/* 661 */ treasuryHDDTableName +
					/*      */
					/* 663 */ " WHERE RECORD_STATUS <>'DELETED' AND RECORD_STATUS <> 'TRANSFER' AND COUNTERPARTY_STRING IS NOT NULL AND FWC_REF_NUM IS NOT NULL "
					+
					/*      */
					/* 665 */ " AND FWC_REF_NUM=? AND COUNTERPARTY_STRING =? AND HOST_DEAL_CATEGORY <> 'FXRATE'";
			/*      */
			/* 667 */ tiZoneConnection = DBConnectionUtility.getZoneConnection();
			/*      */
			/* 669 */ preparedStatement = tiZoneConnection.prepareStatement(availablePurchaseAndSaleAmtsQuery);
			/*      */
			/* 671 */ preparedStatement.setString(1, fwContractNo.trim());
			/*      */
			/* 673 */ preparedStatement.setString(2, custId.trim());
			/*      */
			/* 675 */ resultSet = preparedStatement.executeQuery();
			/*      */
			/* 677 */ while (resultSet.next())
			/*      */ {
				/* 679 */ if (CommonMethods.isValidString(resultSet.getString("BUY_AMOUNT")))
				/*      */ {
					/* 681 */ purchaseAmount = resultSet.getString("BUY_AMOUNT");
					/*      */ }
				/* 683 */ if (CommonMethods.isValidString(resultSet.getString("SELL_AMOUNT")))
				/*      */ {
					/* 685 */ saleAmount = resultSet.getString("SELL_AMOUNT");
					/*      */
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/*      */
			/*      */ }
		/* 693 */ catch (SQLException e) {
			/*      */
			/* 695 */ e.printStackTrace();
			/*      */ }
		/*      */ finally {
			/*      */
			/* 699 */ DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
			/*      */ }
		/*      */
		/*      */
		/* 703 */ return saleAmount;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchParticularFwdContractDetails(String id, String fwdContractNo)
			throws DAOException {
		/* 710 */ logger.info("Entering Method");
		/* 711 */ Connection con = null;
		/* 712 */ LoggableStatement loggableStatement = null;
		/* 713 */ ResultSet rs = null;
		/*      */
		/* 715 */ ForwardContractVO fwdContractVO = null;
		/*      */
		/*      */ try {
			/* 718 */ fwdContractVO = new ForwardContractVO();
			/* 719 */ con = DBConnectionUtility.getZoneConnection();
			/* 720 */ loggableStatement = new LoggableStatement(con,
					"SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY, STATUS,  FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,  TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT,AVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,  PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,REMARKS FROM CUSTOM_FWC_DETAILS WHERE ID=? AND FWC_CONTRACT_NO=? ");
			/* 721 */ loggableStatement.setInt(1, Integer.valueOf(id.trim()).intValue());
			/* 722 */ loggableStatement.setString(2, fwdContractNo.trim());
			/*      */
			/* 724 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 726 */ while (rs.next()) {
				/* 727 */ fwdContractVO.setId(String.valueOf(rs.getInt("ID")));
				/* 728 */ fwdContractVO.setCategory(rs.getString("CATEGORY"));
				/* 729 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				/* 730 */ fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				/* 731 */ fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				/* 732 */ fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				/* 733 */ fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				/* 734 */ fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				/* 735 */ fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
				/* 736 */ fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
				/* 737 */ fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
				/* 738 */ fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
				/* 739 */ fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
				/* 740 */ fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
				/* 741 */ fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
				/* 742 */ fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
				/* 743 */ fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				/* 744 */ fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				/* 745 */ fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));
				/* 746 */ fwdContractVO.setWashRate(rs.getString("WASH_RATE"));
				/* 747 */ fwdContractVO.setLeiNumber(rs.getString("LEI_NUMBER"));
				/* 748 */ fwdContractVO.setPlAmount(rs.getString("PL_AMOUNT"));
				/* 749 */ fwdContractVO.setChargeAmount(rs.getString("CHARGE_AMOUNT"));
				/* 750 */ fwdContractVO.setGstAmount(rs.getString("GST_AMOUNT"));
				/* 751 */ fwdContractVO.setInstructions(rs.getString("INSTRUCTIONS"));
				/* 752 */ fwdContractVO.setMargin(rs.getString("MARGIN"));
				/* 753 */ fwdContractVO.setRemarks(rs.getString("REMARKS"));
				/*      */ }
			/*      */
			/*      */ }
		/* 757 */ catch (Exception e) {
			/* 758 */ e.printStackTrace();
			/*      */ } finally {
			/* 760 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 763 */ logger.info("Exiting Method");
		/* 764 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchParticularFwdContractDetailstoModify(String id, String fwdContractNo)
			throws DAOException {
		/* 769 */ logger.info("Entering Method");
		/* 770 */ Connection con = null;
		/* 771 */ LoggableStatement loggableStatement = null;
		/* 772 */ ResultSet rs = null;
		/*      */
		/* 774 */ ForwardContractVO fwdContractVO = null;
		/*      */
		/*      */ try {
			/* 777 */ fwdContractVO = new ForwardContractVO();
			/* 778 */ con = DBConnectionUtility.getZoneConnection();
			/* 779 */ loggableStatement = new LoggableStatement(con,
					"SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY, STATUS,  FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,  TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT,AVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,  PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,REMARKS FROM CUSTOM_FWC_DETAILS WHERE ID=? AND FWC_CONTRACT_NO=? ");
			/* 780 */ loggableStatement.setInt(1, Integer.valueOf(id.trim()).intValue());
			/* 781 */ loggableStatement.setString(2, fwdContractNo.trim());
			/*      */
			/* 783 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 785 */ while (rs.next()) {
				/* 786 */ fwdContractVO.setId(String.valueOf(rs.getInt("ID")));
				/* 787 */ fwdContractVO.setCategory(rs.getString("CATEGORY"));
				fwdContractVO.setFwcType(rs.getString("CATEGORY"));
				// ← add this
				/* 788 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				/* 789 */ fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				/* 790 */ fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				/* 791 */ fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				/* 792 */ fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				/* 793 */ fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				/* 794 */ if (rs.getString("STATUS") != null
						&& "PENDING TO SUBMIT".equalsIgnoreCase(rs.getString("STATUS"))) {
					/*      */
					/* 796 */ fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
					/* 797 */ fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
					/* 798 */ fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
					/* 799 */ fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
					/* 800 */ fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
					/* 801 */ fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
					/* 802 */ fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
					/* 803 */ fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
					/*      */ }
				/* 805 */ fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				/* 806 */ fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				/* 807 */ fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));
				/* 808 */ fwdContractVO.setWashRate(rs.getString("WASH_RATE"));
				/* 809 */ fwdContractVO.setLeiNumber(rs.getString("LEI_NUMBER"));
				/* 810 */ fwdContractVO.setPlAmount(rs.getString("PL_AMOUNT"));
				/* 811 */ fwdContractVO.setChargeAmount(rs.getString("CHARGE_AMOUNT"));
				/* 812 */ fwdContractVO.setGstAmount(rs.getString("GST_AMOUNT"));
				/* 813 */ fwdContractVO.setInstructions(rs.getString("INSTRUCTIONS"));
				/* 814 */ fwdContractVO.setMargin(rs.getString("MARGIN"));
				/* 815 */ fwdContractVO.setRemarks(rs.getString("REMARKS"));
				/*      */ }
			/*      */
			/*      */ }
		/* 819 */ catch (Exception e) {
			/* 820 */ logger.info("Exception in fetchParticularFwdContractDetailstoModify" + e.getMessage());
			/* 821 */ e.printStackTrace();
			/*      */ } finally {
			/* 823 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 826 */ logger.info("Exiting Method");
		/* 827 */ return fwdContractVO;
		/*      */ }

	/**
	 * Fetches contract details for VIEW (read-only) screen. Works for both
	 * FWCCANCEL and FWCUTIL categories.
	 */
	public ForwardContractVO fetchParticularCancelFwdContractDetailsWithoutRateView(String id, String fwdContractNo)
			throws DAOException {
		logger.info("Entering Method fetchParticularCancelFwdContractDetailsWithoutRate");
		Connection con = null;
		LoggableStatement loggableStatement = null;
		ResultSet rs = null;
		ForwardContractVO fwdContractVO = null;
		try {
			fwdContractVO = new ForwardContractVO();
			con = DBConnectionUtility.getZoneConnection();
			loggableStatement = new LoggableStatement(con,
					"SELECT ID, CATEGORY, FWC_CONTRACT_NO, SUB_PRODUCT, CIF_ID, BRANCH, ACCT_NUMBER, DEAL_CCY, "
							+ "FWC_AMOUNT, TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE, "
							+ "TO_CCY_AMT, TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM, "
							+ "TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO, "
							+ "TREASURY_REF_NO, TREASURY_RATE, OUTSTANDING_AMT, LIMIT_ID, WITHOUT_LIMIT, AVAILABLE_LIMIT, "
							+ "WASH_RATE, LEI_NUMBER, PL_AMOUNT, CHARGE_AMOUNT, GST_AMOUNT, INSTRUCTIONS, MARGIN, REMARKS, "
							+ "CANCELLATION_AMOUNT, TRANS_ID, TRANS_DATE, BOOKING_RATE "
							+ "FROM CUSTOM_FWC_DETAILS WHERE ID=? AND FWC_CONTRACT_NO=? "
							+ "AND CATEGORY IN ('FWCCANCEL','FWCUTIL')");
			loggableStatement.setInt(1, Integer.valueOf(id.trim()).intValue());
			loggableStatement.setString(2, fwdContractNo.trim());
			rs = loggableStatement.executeQuery();
			while (rs.next()) {
				fwdContractVO.setId(String.valueOf(rs.getInt("ID")));
				fwdContractVO.setCategory(rs.getString("CATEGORY"));
				fwdContractVO.setFwcType(rs.getString("CATEGORY")); // FWCCANCEL or FWCUTIL
				fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
				fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
				fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
				fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
				fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
				fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
				fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
				fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
				fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));
				fwdContractVO.setWashRate(rs.getString("WASH_RATE"));
				fwdContractVO.setLeiNumber(rs.getString("LEI_NUMBER"));
				fwdContractVO.setPlAmount(rs.getString("PL_AMOUNT"));
				fwdContractVO.setChargeAmount(rs.getString("CHARGE_AMOUNT"));
				fwdContractVO.setGstAmount(rs.getString("GST_AMOUNT"));
				fwdContractVO.setInstructions(rs.getString("INSTRUCTIONS"));
				fwdContractVO.setMargin(rs.getString("MARGIN"));
				fwdContractVO.setRemarks(rs.getString("REMARKS"));
				fwdContractVO.setCancellationamount(rs.getString("CANCELLATION_AMOUNT"));
				fwdContractVO.setTransid(rs.getString("TRANS_ID"));
				fwdContractVO.setTransdate(rs.getString("TRANS_DATE"));
				fwdContractVO.setBookingrate(rs.getString("BOOKING_RATE"));
			}
		} catch (Exception e) {
			logger.info("Exception in fetchParticularCancelFwdContractDetailsWithoutRate: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
		}
		logger.info("Exiting Method fetchParticularCancelFwdContractDetailsWithoutRate");
		return fwdContractVO;
	}

	/**
	 * Fetches contract details for MODIFY screen. Works for both FWCCANCEL and
	 * FWCUTIL categories. Only populates amount/date fields if status is PENDING TO
	 * SUBMIT or REJECTED.
	 */
	public ForwardContractVO fetchParticularFwdContractDetailstoModifyWithoutRate(String id, String fwdContractNo)
			throws DAOException {
		logger.info("Entering Method fetchParticularFwdContractDetailstoModifyWithoutRate");
		Connection con = null;
		LoggableStatement loggableStatement = null;
		ResultSet rs = null;
		ForwardContractVO fwdContractVO = null;
		try {
			fwdContractVO = new ForwardContractVO();
			con = DBConnectionUtility.getZoneConnection();
			loggableStatement = new LoggableStatement(con,
					"SELECT ID, CATEGORY, FWC_CONTRACT_NO, SUB_PRODUCT, CIF_ID, BRANCH, ACCT_NUMBER, DEAL_CCY, STATUS, "
							+ "FWC_AMOUNT, TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE, "
							+ "TO_CCY_AMT, TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM, "
							+ "TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO, "
							+ "TREASURY_REF_NO, TREASURY_RATE, OUTSTANDING_AMT, LIMIT_ID, WITHOUT_LIMIT, AVAILABLE_LIMIT, "
							+ "WASH_RATE, LEI_NUMBER, PL_AMOUNT, CHARGE_AMOUNT, GST_AMOUNT, INSTRUCTIONS, MARGIN, REMARKS, "
							+ "CANCELLATION_AMOUNT, TRANS_ID, TRANS_DATE, BOOKING_RATE "
							+ "FROM CUSTOM_FWC_DETAILS WHERE ID=? AND FWC_CONTRACT_NO=? "
							+ "AND CATEGORY IN ('FWCCANCEL','FWCUTIL')");
			loggableStatement.setInt(1, Integer.valueOf(id.trim()).intValue());
			loggableStatement.setString(2, fwdContractNo.trim());
			rs = loggableStatement.executeQuery();
			while (rs.next()) {
				fwdContractVO.setId(String.valueOf(rs.getInt("ID")));
				fwdContractVO.setCategory(rs.getString("CATEGORY"));
				fwdContractVO.setFwcType(rs.getString("CATEGORY")); // ← pre-select radio button
				fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				String status = rs.getString("STATUS");
				if (status != null
						&& ("PENDING TO SUBMIT".equalsIgnoreCase(status) || "REJECTED".equalsIgnoreCase(status))) {
					fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
					fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
					fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
					fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
					fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
					fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
					fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
					fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
					fwdContractVO.setCancellationamount(rs.getString("CANCELLATION_AMOUNT"));
					fwdContractVO.setTransid(rs.getString("TRANS_ID"));
					fwdContractVO.setTransdate(rs.getString("TRANS_DATE"));
					fwdContractVO.setBookingrate(rs.getString("BOOKING_RATE"));
				}
				fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));
				fwdContractVO.setWashRate(rs.getString("WASH_RATE"));
				fwdContractVO.setLeiNumber(rs.getString("LEI_NUMBER"));
				fwdContractVO.setPlAmount(rs.getString("PL_AMOUNT"));
				fwdContractVO.setChargeAmount(rs.getString("CHARGE_AMOUNT"));
				fwdContractVO.setGstAmount(rs.getString("GST_AMOUNT"));
				fwdContractVO.setInstructions(rs.getString("INSTRUCTIONS"));
				fwdContractVO.setMargin(rs.getString("MARGIN"));
				fwdContractVO.setRemarks(rs.getString("REMARKS"));
			}
		} catch (Exception e) {
			logger.info("Exception in fetchParticularFwdContractDetailstoModifyWithoutRate: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
		}
		logger.info("Exiting Method fetchParticularFwdContractDetailstoModifyWithoutRate");
		return fwdContractVO;
	}

	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchParticularCancelFwdContractDetails(String id, String fwdContractNo)
			throws DAOException {
		/* 833 */ logger.info("Entering Method");
		/* 834 */ Connection con = null;
		/* 835 */ LoggableStatement loggableStatement = null;
		/* 836 */ ResultSet rs = null;
		/*      */
		/* 838 */ ForwardContractVO fwdContractVO = null;
		/*      */
		/*      */ try {
			/* 841 */ fwdContractVO = new ForwardContractVO();
			/* 842 */ con = DBConnectionUtility.getZoneConnection();
			/* 843 */ loggableStatement = new LoggableStatement(con,
					"SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY,  FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,  TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT,AVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,  PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,REMARKS,CANCELLATION_AMOUNT,TRANS_ID,TRANS_DATE,BOOKING_RATE FROM CUSTOM_FWC_DETAILS WHERE ID=? AND FWC_CONTRACT_NO=? ");
			/* 844 */ loggableStatement.setInt(1, Integer.valueOf(id.trim()).intValue());
			/* 845 */ loggableStatement.setString(2, fwdContractNo.trim());
			/*      */
			/* 847 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 849 */ while (rs.next()) {
				/* 850 */ fwdContractVO.setId(String.valueOf(rs.getInt("ID")));
				/* 851 */ fwdContractVO.setCategory(rs.getString("CATEGORY"));
				fwdContractVO.setFwcType(rs.getString("CATEGORY"));
				// ← add this
				/* 852 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				/* 853 */ fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				/* 854 */ fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				/* 855 */ fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				/* 856 */ fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				/* 857 */ fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				/* 858 */ fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
				/* 859 */ fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
				/* 860 */ fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
				/* 861 */ fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
				/* 862 */ fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
				/* 863 */ fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
				/* 864 */ fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
				/* 865 */ fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
				/* 866 */ fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				/* 867 */ fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				/* 868 */ fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));
				/* 869 */ fwdContractVO.setWashRate(rs.getString("WASH_RATE"));
				/* 870 */ fwdContractVO.setLeiNumber(rs.getString("LEI_NUMBER"));
				/* 871 */ fwdContractVO.setPlAmount(rs.getString("PL_AMOUNT"));
				/* 872 */ fwdContractVO.setChargeAmount(rs.getString("CHARGE_AMOUNT"));
				/* 873 */ fwdContractVO.setGstAmount(rs.getString("GST_AMOUNT"));
				/* 874 */ fwdContractVO.setInstructions(rs.getString("INSTRUCTIONS"));
				/* 875 */ fwdContractVO.setMargin(rs.getString("MARGIN"));
				/* 876 */ fwdContractVO.setRemarks(rs.getString("REMARKS"));
				/* 877 */ fwdContractVO.setCancellationamount(rs.getString("CANCELLATION_AMOUNT"));
				/* 878 */ fwdContractVO.setTransid(rs.getString("TRANS_ID"));
				/* 879 */ fwdContractVO.setTransdate(rs.getString("TRANS_DATE"));
				/* 880 */ fwdContractVO.setBookingrate(rs.getString("BOOKING_RATE"));
				/*      */ }
			/*      */
			/*      */ }
		/* 884 */ catch (Exception e) {
			/* 885 */ e.printStackTrace();
			/*      */ } finally {
			/* 887 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 890 */ logger.info("Exiting Method");
		/* 891 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO fetchFWCReferenceDetails(String fwdContractNo) throws DAOException {
		/* 898 */ logger.info("Entering Method");
		/* 899 */ Connection con = null;
		/* 900 */ LoggableStatement loggableStatement = null;
		/* 901 */ ResultSet rs = null;
		/*      */
		/* 903 */ ForwardContractVO fwdContractVO = null;
		/*      */
		/*      */ try {
			/* 906 */ fwdContractVO = new ForwardContractVO();
			/* 907 */ con = DBConnectionUtility.getZoneConnection();
			/* 908 */ loggableStatement = new LoggableStatement(con,
					"SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY,  FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,  TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT,AVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,  PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,REMARKS FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO=? AND CATEGORY='FWCBOOK' ");
			/* 909 */ loggableStatement.setString(1, fwdContractNo.trim());
			/*      */
			/* 911 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 913 */ while (rs.next())
			/*      */ {
				/* 915 */ fwdContractVO.setCategory(rs.getString("CATEGORY"));
				/* 916 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				/* 917 */ fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				/* 918 */ fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				/* 919 */ fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				/* 920 */ fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				/* 921 */ fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
				/* 922 */ fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
				/* 923 */ fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
				/* 924 */ fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				/* 925 */ fwdContractVO.setWithoutLimit(rs.getString("WITHOUT_LIMIT"));
				/* 926 */ fwdContractVO.setAvailableLimit(rs.getString("AVAILABLE_LIMIT"));

				/*      */ }
			/*      */
			/* 929 */ } catch (Exception e) {
			/* 930 */ e.printStackTrace();
			/*      */ } finally {
			/* 932 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 935 */ logger.info("Exiting Method");
		/* 936 */ return fwdContractVO;
		/*      */ }

// ABHISHEK NEW METHOD

	public ForwardContractVO fetchFWCCancelDetailsWithoutRate(String fwdContractNo) throws DAOException {
		logger.info("Entering fetchFWCCancelDetailsWithoutRate Method");
		Connection con = null;
		LoggableStatement loggableStatement = null;
		ResultSet rs = null;
		ForwardContractVO fwdContractVO = null;
		try {
			fwdContractVO = new ForwardContractVO();
			con = DBConnectionUtility.getZoneConnection();
			loggableStatement = new LoggableStatement(con,
					"SELECT * FROM REP_FWC_OUTSTANDING_VIEW WHERE FWC_REF_NUM = ?");
			loggableStatement.setString(1, fwdContractNo.trim());
			rs = loggableStatement.executeQuery();
			while (rs.next()) {
				String buyOrSell = rs.getString("BUY_OR_SELL");
				String fwdContractRate = rs.getString("FWD_CONTRACT_RATE");
				// Basic Details
				fwdContractVO.setFwdContractNo(rs.getString("FWC_REF_NUM"));
				fwdContractVO.setBranchCode(rs.getString("SOL_ID"));
				fwdContractVO.setCustomerID(rs.getString("CUSTOMER"));
				fwdContractVO.setDealCurrency(rs.getString("DEAL_AMOUNT_CCY"));
				fwdContractVO.setTreasuryRate(fwdContractRate);

				// Deal Valid From and To - reformatted to dd/MM/yyyy
				SimpleDateFormat dbFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat outFmt = new SimpleDateFormat("dd/MM/yyyy");
				String rawFromDate = rs.getString("START_DATE");
				String rawToDate = rs.getString("END_DATE");
				try {
					fwdContractVO.setDealValidFromDate((rawFromDate != null && !rawFromDate.trim().isEmpty())
							? outFmt.format(dbFmt.parse(rawFromDate))
							: "");
				} catch (ParseException e) {
					logger.error("Failed to parse START_DATE: " + rawFromDate, e);
					fwdContractVO.setDealValidFromDate("");
				}
				try {
					fwdContractVO.setDealValidToDate(
							(rawToDate != null && !rawToDate.trim().isEmpty()) ? outFmt.format(dbFmt.parse(rawToDate))
									: "");
				} catch (ParseException e) {
					logger.error("Failed to parse END_DATE: " + rawToDate, e);
					fwdContractVO.setDealValidToDate("");
				}

				// Limit
				fwdContractVO.setLimitID(rs.getString("LIMIT_ID"));
				// Requirement 1: Treasury Reference Number always blank
				fwdContractVO.setTreasuryRefNo("");
				// Requirement 2: Cancellation Date = system date
				fwdContractVO.setBookingDate(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
				// Requirement 3 & 4: Event Amount and Cancellation Amount
				// based on BUY_OR_SELL flag
				String eventAmount = "";
				String eventCcy = "";
				if ("B".equalsIgnoreCase(buyOrSell)) {
					eventAmount = rs.getString("BUY_AMOUNT_OS");
					eventCcy = rs.getString("BUY_AMOUNT_CCY");
				} else if ("S".equalsIgnoreCase(buyOrSell)) {
					eventAmount = rs.getString("SELL_AMOUNT_OS");
					eventCcy = rs.getString("SELL_AMOUNT_CCY");
				}
				String eventAmountWithCcy = eventAmount + " " + eventCcy;
				// Event Amount (fwdContractAmt field in JSP)
				fwdContractVO.setFwdContractAmt(eventAmountWithCcy);
				// Cancellation Amount (outstandingAmt field in JSP) - same as Event Amount
				fwdContractVO.setOutstandingAmt(eventAmountWithCcy);
				logger.info("setOutstandingAmt" + eventAmountWithCcy);
				
				// Requirement 2: Event Outstanding Amount = DEAL_AMOUNT with CCY
				String dealAmount = rs.getString("DEAL_AMOUNT");
				String dealAmountCcy = rs.getString("DEAL_AMOUNT_CCY");
				fwdContractVO.setCancellationamount(dealAmount + " " + dealAmountCcy);
				logger.info("setCancellationamount" + dealAmount + "" + dealAmountCcy);
				
				// Requirement 3: To Amount = Event Amount * FWD_CONTRACT_RATE
				// Currency will be SELL_AMOUNT_CCY when B, BUY_AMOUNT_CCY when S
				try {
					if (eventAmount != null && !eventAmount.trim().isEmpty() && fwdContractRate != null
							&& !fwdContractRate.trim().isEmpty()) {
						BigDecimal eventAmtBD = new BigDecimal(eventAmount.trim());
						BigDecimal rateBD = new BigDecimal(fwdContractRate.trim());
						BigDecimal toAmount = eventAmtBD.multiply(rateBD).setScale(2, RoundingMode.HALF_UP);
						String toAmountCcy = "";
						if ("B".equalsIgnoreCase(buyOrSell)) {
							toAmountCcy = rs.getString("SELL_AMOUNT_CCY"); // B buys foreign, sells INR
						} else if ("S".equalsIgnoreCase(buyOrSell)) {
							toAmountCcy = rs.getString("BUY_AMOUNT_CCY");
						}
						fwdContractVO.setToCurrencyAmt(toAmount + " " + toAmountCcy);
						logger.info("toAmount - toAmountCcy" + toAmount + "" + toAmountCcy);
					}
				} catch (NumberFormatException e) {
					logger.info("Error calculating To Amount: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.info("fetchFWCCancelDetailsWithoutRate Exception: " + e);
			e.printStackTrace();
		} finally {
			DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
		}
		logger.info("Exiting fetchFWCCancelDetailsWithoutRate Method");
		return fwdContractVO;
	}

	/*      */
	/*      */
	/*      */ public String getRole(ForwardContractVO fwdContractVO) throws DAOException {
		/* 941 */ logger.info("Entering Method");
		/* 942 */ Connection con = null;
		/* 943 */ LoggableStatement loggableStatement = null;
		/* 944 */ ResultSet rs = null;
		/* 945 */ String result = null;
		/* 946 */ String sqlQuery = null;
		/*      */
		/*      */
		/*      */ try {
			/* 950 */ logger.info("get sessionUserName --> " + fwdContractVO.getSessionUserName());
			/*      */
			/* 952 */ if (!CommonMethods.isNull(fwdContractVO.getSessionUserName())) {
				/*      */
				/* 954 */ sqlQuery = "SELECT COUNT(*) as Count FROM SECAGE88 U, TEAMUSRMAP T  WHERE  T.USERKEY = U.SKEY80 AND U.NAME85  = '"
						+
						/* 955 */ fwdContractVO.getSessionUserName().trim() +
						/* 956 */ "' AND UPPER(T.TEAMKEY) LIKE '%FWC%' group by U.NAME85 ";
				/*      */
				/* 958 */ con = DBConnectionUtility.getZoneConnection();
				/*      */
				/* 960 */ loggableStatement = new LoggableStatement(con, sqlQuery);
				/*      */
				/*      */
				/*      */
				/*      */
				/* 965 */ logger.info(loggableStatement.getQueryString());
				/* 966 */ rs = loggableStatement.executeQuery();
				/*      */
				/* 968 */ while (rs.next()) {
					/* 969 */ result = Integer.toString(rs.getInt(1));
					/* 970 */ logger.info("get Role result" + result);
					/*      */ }
				/*      */
				/*      */ }
			/* 974 */ } catch (Exception exception) {
			/* 975 */ exception.printStackTrace();
			/*      */ } finally {
			/* 977 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 979 */ return result;
		/*      */ }

	/*      */
	/*      */ public int checkLoginedUserType(ForwardContractVO fwdContractVO) throws DAOException {
		/* 983 */ logger.info("Entering Method");
		/* 984 */ Connection con = null;
		/* 985 */ LoggableStatement loggableStatement = null;
		/* 986 */ ResultSet rs = null;
		/* 987 */ int result = 0;
		/* 988 */ String sqlQuery = null;
		/*      */
		/*      */ try {
			/* 991 */ if (CommonMethods.isValidString(fwdContractVO.getSessionUserName())) {
				/* 992 */ con = DBConnectionUtility.getZoneConnection();
				/*      */
				/* 994 */ sqlQuery = "SELECT Count(*) AS TEAMCNT FROM SECAGE88 U LEFT JOIN TEAMUSRMAP T  ON T.USERKEY = U.SKEY80 WHERE TRIM(UPPER(U.NAME85))  = TRIM(UPPER('"
						+
						/* 995 */ fwdContractVO.getSessionUserName().trim() + "')) " +
						/* 996 */ " AND TRIM(UPPER(T.TEAMKEY)) = TRIM(UPPER('" + fwdContractVO.getPageType() + "'))";
				/*      */
				/* 998 */ loggableStatement = new LoggableStatement(con, sqlQuery);
				/*      */
				/*      */
				/*      */
				/* 1002 */ logger.info("CheckLoginedUserType: " + loggableStatement.getQueryString());
				/*      */
				/* 1004 */ rs = loggableStatement.executeQuery();
				/*      */
				/* 1006 */ if (rs.next()) {
					/* 1007 */ result = rs.getInt("TEAMCNT");
					/* 1008 */ logger.info("check Logined UserType result" + result);
					/*      */ }
				/*      */ }
			/* 1011 */ } catch (Exception exception) {
			/* 1012 */ exception.printStackTrace();
			/*      */ } finally {
			/* 1014 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 1016 */ logger.info("Exiting Method");
		/* 1017 */ return result;
		/*      */ }

	/*      */
	/*      */ public int checkLoginedUserType1(String user, String team) throws DAOException {
		/* 1021 */ logger.info("Entering Method");
		/* 1022 */ Connection con = null;
		/* 1023 */ LoggableStatement loggableStatement = null;
		/* 1024 */ ResultSet rs = null;
		/* 1025 */ int result = 0;
		/* 1026 */ String sqlQuery = null;
		/*      */
		/*      */ try {
			/* 1029 */ if (CommonMethods.isValidString(user)) {
				/* 1030 */ con = DBConnectionUtility.getZoneConnection();
				/*      */
				/* 1032 */ sqlQuery = "SELECT count(T.TEAMKEY) as TEAMCNT FROM SECAGE88 U LEFT JOIN TEAMUSRMAP T  ON T.USERKEY = U.SKEY80 WHERE TRIM(UPPER(U.NAME85))  = TRIM(UPPER('"
						+
						/* 1033 */ user.trim() + "')) " +
						/* 1034 */ " AND TRIM(UPPER(T.TEAMKEY)) LIKE 'FWC%" + team + "%'";
				/*      */
				/* 1036 */ loggableStatement = new LoggableStatement(con, sqlQuery);
				/*      */
				/*      */
				/*      */
				/* 1040 */ logger.info("CheckLoginedUserType: " + loggableStatement.getQueryString());
				/*      */
				/* 1042 */ rs = loggableStatement.executeQuery();
				/*      */
				/* 1044 */ if (rs.next()) {
					/* 1045 */ result = rs.getInt("TEAMCNT");
					/* 1046 */ logger.info("check Logined UserType result" + result);
					/*      */ }
				/*      */ }
			/* 1049 */ } catch (Exception exception) {
			/* 1050 */ exception.printStackTrace();
			/*      */ } finally {
			/* 1052 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 1054 */ logger.info("Exiting Method");
		/* 1055 */ return result;
		/*      */ }

	/*      */
	/*      */ public String getSessionUserID(String userName) {
		/* 1059 */ logger.info("Entering Method");
		/* 1060 */ Connection con = null;
		/* 1061 */ LoggableStatement pst = null;
		/* 1062 */ ResultSet rs = null;
		/* 1063 */ String sessionUserId = null;
		/* 1064 */ String QUERY = "select skey80 from secage88 where name85='" + userName.trim() + "'";
		/*      */ try {
			/* 1066 */ con = DBConnectionUtility.getZoneConnection();
			/* 1067 */ pst = new LoggableStatement(con, QUERY);
			/*      */
			/* 1069 */ logger.info(pst.getQueryString());
			/* 1070 */ rs = pst.executeQuery();
			/* 1071 */ while (rs.next()) {
				/* 1072 */ sessionUserId = rs.getString("skey80");
				/*      */ }
			/* 1074 */ } catch (Exception e) {
			/* 1075 */ e.printStackTrace();
			/*      */ } finally {
			/* 1077 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1079 */ logger.info("Exiting Method");
		/* 1080 */ return sessionUserId;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> customerSearch(ArrayList<StaticDataVO> customerList) {
		/* 1084 */ logger.info("Entering Method");
		/* 1085 */ LoggableStatement pst = null;
		/* 1086 */ ResultSet rs = null;
		/* 1087 */ Connection con = null;
		/*      */ try {
			/* 1089 */ customerList = new ArrayList<>();
			/* 1090 */ con = DBConnectionUtility.getZoneConnection();
			/* 1091 */ pst = new LoggableStatement(con, "select GFCUS1,GFCUN,GFCPNC from GFPF order by GFCUS1");
			/* 1092 */ logger.info(pst.getQueryString());
			/* 1093 */ rs = pst.executeQuery();
			/* 1094 */ while (rs.next()) {
				/* 1095 */ StaticDataVO cusVo = new StaticDataVO();
				/* 1096 */ cusVo.setCustomerID(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1097 */ cusVo.setCustomerName(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1098 */ customerList.add(cusVo);
				/*      */ }
			/* 1100 */ } catch (Exception e) {
			/* 1101 */ e.printStackTrace();
			/*      */ } finally {
			/* 1103 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1105 */ logger.info("Exiting Method");
		/* 1106 */ return customerList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> accountSearch(ArrayList<StaticDataVO> accountList) {
		/* 1110 */ logger.info("Entering Method");
		/* 1111 */ LoggableStatement pst = null;
		/* 1112 */ ResultSet rs = null;
		/* 1113 */ Connection con = null;
		/*      */ try {
			/* 1115 */ accountList = new ArrayList<>();
			/* 1116 */ con = DBConnectionUtility.getZoneConnection();
			/* 1117 */ pst = new LoggableStatement(con,
					"SELECT BO_ACCTNO,ACC_TYPE,BRCH_MNM,CUS_MNM,SHORTNAME,CURRENCY FROM ACCOUNT WHERE TRIM(CUS_MNM) IS NOT NULL AND TRIM(BO_ACCTNO) IS NOT NULL AND CURRENCY = 'INR' ORDER BY CUS_MNM,BO_ACCTNO,ACC_TYPE ");
			/* 1118 */ logger.info(pst.getQueryString());
			/* 1119 */ rs = pst.executeQuery();
			/* 1120 */ while (rs.next()) {
				/* 1121 */ StaticDataVO acctVO = new StaticDataVO();
				/* 1122 */ acctVO.setAcctNumber(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1123 */ acctVO.setAcctType(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1124 */ acctVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString(3)));
				/* 1125 */ acctVO.setCustomerID(CommonMethods.nullAndTrimString(rs.getString(4)));
				/* 1126 */ acctVO.setShortName(CommonMethods.nullAndTrimString(rs.getString(5)));
				/* 1127 */ acctVO.setCurrency(CommonMethods.nullAndTrimString(rs.getString(6)));
				/* 1128 */ accountList.add(acctVO);
				/*      */ }
			/* 1130 */ } catch (Exception e) {
			/* 1131 */ e.printStackTrace();
			/*      */ } finally {
			/* 1133 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1135 */ logger.info("Exiting Method");
		/* 1136 */ return accountList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> branchSearch(ArrayList<StaticDataVO> branchList) {
		/* 1140 */ logger.info("Entering Method");
		/* 1141 */ LoggableStatement pst = null;
		/* 1142 */ ResultSet rs = null;
		/* 1143 */ Connection con = null;
		/*      */ try {
			/* 1145 */ branchList = new ArrayList<>();
			/* 1146 */ con = DBConnectionUtility.getZoneConnection();
			/* 1147 */ pst = new LoggableStatement(con, "SELECT CABRNM,FULLNAME FROM CAPF ORDER BY CABRNM");
			/* 1148 */ logger.info(pst.getQueryString());
			/* 1149 */ rs = pst.executeQuery();
			/* 1150 */ while (rs.next()) {
				/* 1151 */ StaticDataVO branchVO = new StaticDataVO();
				/* 1152 */ branchVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1153 */ branchVO.setBranchFullName(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1154 */ branchList.add(branchVO);
				/*      */ }
			/* 1156 */ } catch (Exception e) {
			/* 1157 */ e.printStackTrace();
			/*      */ } finally {
			/* 1159 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1161 */ logger.info("Exiting Method");
		/* 1162 */ return branchList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> currencySearch(ArrayList<StaticDataVO> currencyList) {
		/* 1166 */ logger.info("Entering Method");
		/* 1167 */ LoggableStatement pst = null;
		/* 1168 */ ResultSet rs = null;
		/* 1169 */ Connection con = null;
		/*      */
		/*      */ try {
			/* 1172 */ currencyList = new ArrayList<>();
			/* 1173 */ con = DBConnectionUtility.getZoneConnection();
			/* 1174 */ pst = new LoggableStatement(con, "SELECT C8CCY,C8CUR FROM C8PF ORDER BY C8CCY");
			/* 1175 */ logger.info(pst.getQueryString());
			/* 1176 */ rs = pst.executeQuery();
			/* 1177 */ while (rs.next()) {
				/* 1178 */ StaticDataVO ccyVO = new StaticDataVO();
				/* 1179 */ ccyVO.setCurrency(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1180 */ ccyVO.setCurrencyFullName(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1181 */ currencyList.add(ccyVO);
				/*      */ }
			/* 1183 */ } catch (Exception e) {
			/* 1184 */ e.printStackTrace();
			/*      */ } finally {
			/* 1186 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1188 */ logger.info("Exiting Method");
		/* 1189 */ return currencyList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> fetchTreasuryDetails(ArrayList<StaticDataVO> treasuryList) {
		/* 1193 */ logger.info("Entering Method");
		/* 1194 */ LoggableStatement pst = null;
		/* 1195 */ ResultSet rs = null;
		/* 1196 */ Connection con = null;
		/* 1197 */ ServiceUtility.getProperties();
		/* 1198 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */
		/* 1200 */ String treasuryListQuery = "SELECT REFERENCE_NUM,COUNTERPARTY_STRING,TO_CHAR(TO_DATE(HOST_TRAN_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS HOST_TRAN_DATE, TO_CHAR(TO_DATE(START_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS START_DATE,TO_CHAR(TO_DATE(END_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS END_DATE FROM "
				+
				/*      */
				/* 1202 */ treasuryHDDTableName +
				/* 1203 */ " WHERE ((HOST_DEAL_CATEGORY = 'FXRATE' AND (FWC_REF_NUM IS NULL OR TRIM(FWC_REF_NUM)='') AND HOST_DEAL_SUB_CATEGORY='FWCBOOK') "
				+
				/* 1204 */ " OR (HOST_DEAL_CATEGORY ='FWCCANCEL' AND FWC_REF_NUM IS NOT NULL AND ADDITIONAL_TEXT_1 IN ('MFPCAN','MFSCAN'))) AND RECORD_STATUS='TRANSFER' "
				+
				/* 1205 */ " ORDER BY COUNTERPARTY_STRING";
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
			/* 1224 */ treasuryList = new ArrayList<>();
			/*      */
			/* 1226 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 1227 */ pst = new LoggableStatement(con, treasuryListQuery);
			/* 1228 */ logger.info(pst.getQueryString());
			/* 1229 */ rs = pst.executeQuery();
			/* 1230 */ while (rs.next()) {
				/* 1231 */ StaticDataVO treasuryDataVO = new StaticDataVO();
				/* 1232 */ treasuryDataVO
						.setTreasuryRefNo(CommonMethods.nullAndTrimString(rs.getString("REFERENCE_NUM")));
				/* 1233 */ treasuryDataVO
						.setCustomerID(CommonMethods.nullAndTrimString(rs.getString("COUNTERPARTY_STRING")));
				/* 1234 */ treasuryDataVO
						.setBookingDate(CommonMethods.nullAndTrimString(rs.getString("HOST_TRAN_DATE")));
				/* 1235 */ treasuryDataVO
						.setDealValidFromDate(CommonMethods.nullAndTrimString(rs.getString("START_DATE")));
				/* 1236 */ treasuryDataVO.setDealValidToDate(CommonMethods.nullAndTrimString(rs.getString("END_DATE")));
				/* 1237 */ treasuryList.add(treasuryDataVO);
				/*      */ }
			/* 1239 */ } catch (Exception e) {
			/* 1240 */ e.printStackTrace();
			/*      */ } finally {
			/* 1242 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1244 */ logger.info("Exiting Method");
		/* 1245 */ return treasuryList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> fetchFwdContractList(ArrayList<StaticDataVO> fwdContractList) {
		/* 1249 */ logger.info("Entering Method");
		/* 1250 */ LoggableStatement pst = null;
		/* 1251 */ ResultSet rs = null;
		/* 1252 */ Connection con = null;
		/*      */ try {
			/* 1254 */ fwdContractList = new ArrayList<>();
			/*      */
			/* 1256 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 1257 */ pst = new LoggableStatement(con,
					"SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,CATEGORY FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND STATUS='APPROVED' AND CATEGORY ='FWCBOOK' ORDER BY ID DESC ");
			/* 1258 */ logger.info(pst.getQueryString());
			/* 1259 */ rs = pst.executeQuery();
			/* 1260 */ while (rs.next()) {
				/* 1261 */ StaticDataVO fwdContractDataVO = new StaticDataVO();
				/*      */
				/* 1263 */ fwdContractDataVO
						.setFwdContractNo(CommonMethods.nullAndTrimString(rs.getString("FWC_CONTRACT_NO")));
				/* 1264 */ fwdContractDataVO.setCustomerID(CommonMethods.nullAndTrimString(rs.getString("CIF_ID")));
				/* 1265 */ fwdContractDataVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString("BRANCH")));
				/* 1266 */ fwdContractDataVO
						.setSubProduct(CommonMethods.nullAndTrimString(rs.getString("SUB_PRODUCT")));
				/* 1267 */ fwdContractDataVO.setCategory(CommonMethods.nullAndTrimString(rs.getString("CATEGORY")));
				/* 1268 */ fwdContractList.add(fwdContractDataVO);
				/*      */ }
			/* 1270 */ } catch (Exception e) {
			/* 1271 */ e.printStackTrace();
			/*      */ } finally {
			/* 1273 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1275 */ logger.info("Exiting Method");
		/* 1276 */ return fwdContractList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> fetchLimitDetails(String customerID) {
		/* 1280 */ logger.info("Entering Method");
		/* 1281 */ LoggableStatement pst = null;
		/* 1282 */ ResultSet rs = null;
		/* 1283 */ Connection con = null;
		/* 1284 */ ArrayList<StaticDataVO> limitList = null;
		/*      */
		/*      */ try {
			/* 1287 */ ServiceUtility.getProperties();
			/* 1288 */ String[] allowedFWCLimitSuffixes = ((String) ServiceUtility.TBProperties.get("AllowedFWCLimits"))
					.split(",");
			/*      */
			/* 1290 */ limitList = new ArrayList<>();
			/*      */
			/* 1292 */ List<HashMap<String, String>> limitDtlsResList = LimitFetchUtil
					.getLimitDetailsFromLimitAPI(customerID);
			/*      */
			/* 1294 */ for (int i = 0; i < limitDtlsResList.size(); i++) {
				/*      */
				/* 1296 */ StaticDataVO limitDataVO = new StaticDataVO();
				/*      */
				/* 1298 */ String limitPrefix = (String) ((HashMap) limitDtlsResList.get(i)).get("limitPrefix");
				/* 1299 */ String limitSuffix = ((String) ((HashMap) limitDtlsResList.get(i)).get("limitSuffix"))
						.trim();
				/* 1300 */ limitDataVO.setLimitID(String.valueOf(CommonMethods.returnEmptyIfNull(limitPrefix)) + "/" +
				/* 1301 */ CommonMethods.returnEmptyIfNull(limitSuffix));
				/* 1302 */ limitDataVO.setLimitDesc((String) ((HashMap) limitDtlsResList.get(i)).get("limitDesc"));
				/*      */
				/* 1304 */ limitDataVO.setLimitAmount((String) ((HashMap) limitDtlsResList.get(i)).get("limitAmt"));
				/* 1305 */ limitDataVO
						.setTotalLiabilityAmt((String) ((HashMap) limitDtlsResList.get(i)).get("totalLiability"));
				/* 1306 */ limitDataVO.setCurrency((String) ((HashMap) limitDtlsResList.get(i)).get("crncyCode"));
				/* 1307 */ limitDataVO.setSanctionDate((String) ((HashMap) limitDtlsResList.get(i)).get("sanDate"));
				/* 1308 */ limitDataVO.setExpiryDate((String) ((HashMap) limitDtlsResList.get(i)).get("expiryDate"));
				/*      */
				/* 1310 */ if (ArrayUtils.contains((Object[]) allowedFWCLimitSuffixes, limitSuffix)) {
					/* 1311 */ limitList.add(limitDataVO);
					/*      */ }
				/*      */ }
			/* 1314 */ } catch (Exception e) {
			/* 1315 */ e.printStackTrace();
			/*      */ } finally {
			/* 1317 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1319 */ logger.info("Exiting Method");
		/* 1320 */ return limitList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> filterCusList(ArrayList<StaticDataVO> customerList,
			StaticDataVO cusDataVo) {
		/* 1324 */ logger.info("Entering Method");
		/* 1325 */ LoggableStatement pst = null;
		/* 1326 */ ResultSet rs = null;
		/* 1327 */ Connection con = null;
		/* 1328 */ String cusName = "";
		/* 1329 */ String cusNumber = "";
		/* 1330 */ String setValue = null;
		/* 1331 */ String setValue1 = null;
		/*      */
		/*      */ try {
			/* 1334 */ String query = "";
			/* 1335 */ customerList = new ArrayList<>();
			/* 1336 */ con = DBConnectionUtility.getZoneConnection();
			/* 1337 */ if (!CommonMethods.isNull(cusDataVo.getCustomerName())) {
				/* 1338 */ query = "select GFCUS1 AS CIFID,GFCUN AS CUSTOMER from GFPF  WHERE GFCUN like '%'||?||'%' ";
				/* 1339 */ setValue = cusDataVo.getCustomerName();
				/*      */ }
			/* 1341 */ else if (!CommonMethods.isNull(cusDataVo.getCustomerID())) {
				/* 1342 */ query = "select GFCUS1 AS CIFID,GFCUN AS CUSTOMER from GFPF  WHERE GFCUS1 like '%'||?||'%' ";
				/* 1343 */ setValue = cusDataVo.getCustomerID();
				/*      */ }
			/* 1345 */ else if (!CommonMethods.isNull(cusDataVo.getCustomerName()) &&
			/* 1346 */ !CommonMethods.isNull(cusDataVo.getCustomerID())) {
				/* 1347 */ query = "select GFCUS1 AS CIFID,GFCUN AS CUSTOMER from GFPF  WHERE GFCUN like '%'||?||'%' AND GFCUS1 like '%'||?||'%' ";
				/* 1348 */ setValue = cusDataVo.getCustomerName();
				/* 1349 */ setValue1 = cusDataVo.getCustomerID();
				/*      */ } else {
				/* 1351 */ query = "select GFCUS1 AS CIFID,GFCUN AS CUSTOMER from GFPF ";
				/*      */ }
			/*      */
			/* 1354 */ pst = new LoggableStatement(con, query);
			/* 1355 */ if (setValue != null) {
				/* 1356 */ pst.setString(1, setValue);
				/*      */ }
			/* 1358 */ if (setValue1 != null) {
				/* 1359 */ pst.setString(2, setValue1);
				/*      */ }
			/* 1361 */ logger.info(pst.getQueryString());
			/* 1362 */ rs = pst.executeQuery();
			/* 1363 */ while (rs.next()) {
				/* 1364 */ StaticDataVO cusVo = new StaticDataVO();
				/* 1365 */ cusVo.setCustomerID(CommonMethods.nullAndTrimString(rs.getString("CIFID")));
				/* 1366 */ cusVo.setCustomerName(CommonMethods.nullAndTrimString(rs.getString("CUSTOMER")));
				/*      */
				/* 1368 */ customerList.add(cusVo);
				/*      */ }
			/*      */
			/* 1371 */ } catch (Exception e) {
			/* 1372 */ e.printStackTrace();
			/*      */ } finally {
			/* 1374 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1376 */ logger.info("Exiting Method");
		/* 1377 */ return customerList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> filterAcctList(ArrayList<StaticDataVO> accountList,
			StaticDataVO acctDataVO) {
		/* 1381 */ logger.info("Entering Method");
		/* 1382 */ LoggableStatement pst = null;
		/* 1383 */ ResultSet rs = null;
		/* 1384 */ Connection con = null;
		/* 1385 */ String setValue = null;
		/* 1386 */ String setValue1 = null;
		/*      */
		/*      */ try {
			/* 1389 */ String query = "";
			/* 1390 */ accountList = new ArrayList<>();
			/* 1391 */ con = DBConnectionUtility.getZoneConnection();
			/* 1392 */ if (!CommonMethods.isNull(acctDataVO.getAcctNumber())) {
				/* 1393 */ query = "SELECT BO_ACCTNO,ACC_TYPE,BRCH_MNM,CUS_MNM,SHORTNAME,CURRENCY FROM ACCOUNT WHERE  CURRENCY = 'INR' AND BO_ACCTNO like '%'||?||'%' ";
				/* 1394 */ setValue = acctDataVO.getAcctNumber();
				/*      */ }
			/* 1396 */ else if (!CommonMethods.isNull(acctDataVO.getCustomerID())) {
				/* 1397 */ query = "SELECT BO_ACCTNO,ACC_TYPE,BRCH_MNM,CUS_MNM,SHORTNAME,CURRENCY FROM ACCOUNT WHERE  CURRENCY = 'INR' AND CUS_MNM like '%'||?||'%' ";
				/* 1398 */ setValue = acctDataVO.getCustomerID();
				/*      */ }
			/* 1400 */ else if (!CommonMethods.isNull(acctDataVO.getAcctNumber()) &&
			/* 1401 */ !CommonMethods.isNull(acctDataVO.getCustomerID())) {
				/* 1402 */ query = "SELECT BO_ACCTNO,ACC_TYPE,BRCH_MNM,CUS_MNM,SHORTNAME,CURRENCY FROM ACCOUNT WHERE  CURRENCY = 'INR' AND BO_ACCTNO like '%'||?||'%' AND CUS_MNM like '%'||?||'%' ";
				/* 1403 */ setValue = acctDataVO.getAcctNumber();
				/* 1404 */ setValue1 = acctDataVO.getCustomerID();
				/*      */ } else {
				/* 1406 */ query = "SELECT BO_ACCTNO,ACC_TYPE,BRCH_MNM,CUS_MNM,SHORTNAME,CURRENCY FROM ACCOUNT WHERE  CURRENCY = 'INR'";
				/*      */ }
			/*      */
			/* 1409 */ pst = new LoggableStatement(con, query);
			/* 1410 */ if (setValue != null) {
				/* 1411 */ pst.setString(1, setValue);
				/*      */ }
			/* 1413 */ if (setValue1 != null) {
				/* 1414 */ pst.setString(2, setValue1);
				/*      */ }
			/* 1416 */ logger.info(pst.getQueryString());
			/* 1417 */ rs = pst.executeQuery();
			/* 1418 */ while (rs.next()) {
				/* 1419 */ StaticDataVO acctVO = new StaticDataVO();
				/* 1420 */ acctVO.setAcctNumber(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1421 */ acctVO.setAcctType(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1422 */ acctVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString(3)));
				/* 1423 */ acctVO.setCustomerID(CommonMethods.nullAndTrimString(rs.getString(4)));
				/* 1424 */ acctVO.setShortName(CommonMethods.nullAndTrimString(rs.getString(5)));
				/* 1425 */ acctVO.setCurrency(CommonMethods.nullAndTrimString(rs.getString(6)));
				/* 1426 */ accountList.add(acctVO);
				/*      */ }
			/* 1428 */ } catch (Exception e) {
			/* 1429 */ e.printStackTrace();
			/*      */ } finally {
			/* 1431 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1433 */ logger.info("Exiting Method");
		/* 1434 */ return accountList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> filterBranchList(ArrayList<StaticDataVO> branchList,
			StaticDataVO branchDataVO) {
		/* 1438 */ logger.info("Entering Method");
		/* 1439 */ LoggableStatement pst = null;
		/* 1440 */ ResultSet rs = null;
		/* 1441 */ Connection con = null;
		/* 1442 */ String setValue = null;
		/*      */
		/*      */ try {
			/* 1445 */ String query = "";
			/* 1446 */ branchList = new ArrayList<>();
			/* 1447 */ con = DBConnectionUtility.getZoneConnection();
			/* 1448 */ if (!CommonMethods.isNull(branchDataVO.getBranchCode())) {
				/* 1449 */ query = "SELECT CABRNM,FULLNAME FROM CAPF  WHERE CABRNM like ?||'%' ";
				/* 1450 */ setValue = branchDataVO.getBranchCode();
				/*      */ } else {
				/*      */
				/* 1453 */ query = "SELECT CABRNM,FULLNAME FROM CAPF ";
				/*      */ }
			/*      */
			/* 1456 */ pst = new LoggableStatement(con, query);
			/* 1457 */ if (setValue != null) {
				/* 1458 */ pst.setString(1, setValue);
				/*      */ }
			/*      */
			/* 1461 */ logger.info(pst.getQueryString());
			/* 1462 */ rs = pst.executeQuery();
			/* 1463 */ while (rs.next()) {
				/* 1464 */ StaticDataVO branchVO = new StaticDataVO();
				/* 1465 */ branchVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1466 */ branchVO.setBranchFullName(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1467 */ branchList.add(branchVO);
				/*      */ }
			/* 1469 */ } catch (Exception e) {
			/* 1470 */ e.printStackTrace();
			/*      */ } finally {
			/* 1472 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1474 */ logger.info("Exiting Method");
		/* 1475 */ return branchList;
		/*      */ }

	/*      */
	/*      */ public ArrayList<StaticDataVO> filterCurrencyList(ArrayList<StaticDataVO> currencyList,
			StaticDataVO ccyDataVO) {
		/* 1479 */ logger.info("Entering Method");
		/* 1480 */ LoggableStatement pst = null;
		/* 1481 */ ResultSet rs = null;
		/* 1482 */ Connection con = null;
		/* 1483 */ String setValue = null;
		/*      */
		/*      */ try {
			/* 1486 */ String query = "";
			/* 1487 */ currencyList = new ArrayList<>();
			/* 1488 */ con = DBConnectionUtility.getZoneConnection();
			/* 1489 */ if (!CommonMethods.isNull(ccyDataVO.getCurrency())) {
				/* 1490 */ query = "SELECT C8CCY,C8CUR FROM C8PF  WHERE C8CCY like '%'||?||'%' ";
				/* 1491 */ setValue = ccyDataVO.getCurrency();
				/*      */ } else {
				/*      */
				/* 1494 */ query = "SELECT C8CCY,C8CUR FROM C8PF ";
				/*      */ }
			/*      */
			/* 1497 */ pst = new LoggableStatement(con, query);
			/* 1498 */ if (setValue != null) {
				/* 1499 */ pst.setString(1, setValue);
				/*      */ }
			/*      */
			/* 1502 */ logger.info(pst.getQueryString());
			/* 1503 */ rs = pst.executeQuery();
			/* 1504 */ while (rs.next()) {
				/* 1505 */ StaticDataVO ccyVO = new StaticDataVO();
				/* 1506 */ ccyVO.setCurrency(CommonMethods.nullAndTrimString(rs.getString(1)));
				/* 1507 */ ccyVO.setCurrencyFullName(CommonMethods.nullAndTrimString(rs.getString(2)));
				/* 1508 */ currencyList.add(ccyVO);
				/*      */ }
			/*      */
			/* 1511 */ } catch (Exception e) {
			/* 1512 */ e.printStackTrace();
			/*      */ } finally {
			/* 1514 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1516 */ logger.info("Exiting Method");
		/* 1517 */ return currencyList;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ArrayList<StaticDataVO> filterTreasuryList(StaticDataVO treasuryDataVO,
			ArrayList<StaticDataVO> treasuryList) {
		/* 1522 */ logger.info("Entering Method");
		/* 1523 */ LoggableStatement pst = null;
		/* 1524 */ ResultSet rs = null;
		/* 1525 */ Connection con = null;
		/* 1526 */ String setValue = null;
		/* 1527 */ String setValue1 = null;
		/*      */
		/* 1529 */ ServiceUtility.getProperties();
		/* 1530 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */
		/* 1532 */ String treasuryFilterQuery = "SELECT REFERENCE_NUM,COUNTERPARTY_STRING,TO_CHAR(TO_DATE(HOST_TRAN_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS HOST_TRAN_DATE, TO_CHAR(TO_DATE(START_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS START_DATE,TO_CHAR(TO_DATE(END_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS END_DATE FROM "
				+
				/*      */
				/* 1534 */ treasuryHDDTableName +
				/* 1535 */ " WHERE ((HOST_DEAL_CATEGORY = 'FXRATE' AND (FWC_REF_NUM IS NULL OR TRIM(FWC_REF_NUM)='') AND HOST_DEAL_SUB_CATEGORY='FWCBOOK') "
				+
				/* 1536 */ " OR (HOST_DEAL_CATEGORY ='FWCCANCEL' AND FWC_REF_NUM IS NOT NULL AND ADDITIONAL_TEXT_1 IN ('MFPCAN','MFSCAN'))) AND RECORD_STATUS='TRANSFER' ";
		/*      */
		/*      */ try {
			/* 1539 */ String query = "";
			/*      */
			/* 1541 */ treasuryList = new ArrayList<>();
			/* 1542 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 1543 */ if (!CommonMethods.isNull(treasuryDataVO.getTreasuryRefNo())) {
				/* 1544 */ query = String.valueOf(treasuryFilterQuery) + " AND REFERENCE_NUM like '%'||?||'%' ";
				/* 1545 */ setValue = treasuryDataVO.getTreasuryRefNo();
				/*      */ }
			/* 1547 */ else if (!CommonMethods.isNull(treasuryDataVO.getCustomerID())) {
				/* 1548 */ query = String.valueOf(treasuryFilterQuery) + " AND COUNTERPARTY_STRING like '%'||?||'%' ";
				/* 1549 */ setValue = treasuryDataVO.getCustomerID();
				/*      */ }
			/* 1551 */ else if (!CommonMethods.isNull(treasuryDataVO.getTreasuryRefNo()) &&
			/* 1552 */ !CommonMethods.isNull(treasuryDataVO.getCustomerID())) {
				/* 1553 */ query = String.valueOf(treasuryFilterQuery) +
				/* 1554 */ " AND REFERENCE_NUM like '%'||?||'%' AND COUNTERPARTY_STRING like '%'||?||'%' ";
				/* 1555 */ setValue = treasuryDataVO.getTreasuryRefNo();
				/* 1556 */ setValue1 = treasuryDataVO.getCustomerID();
				/*      */ } else {
				/* 1558 */ query = treasuryFilterQuery;
				/*      */ }
			/*      */
			/* 1561 */ pst = new LoggableStatement(con, query);
			/* 1562 */ if (setValue != null) {
				/* 1563 */ pst.setString(1, setValue);
				/*      */ }
			/* 1565 */ if (setValue1 != null) {
				/* 1566 */ pst.setString(2, setValue1);
				/*      */ }
			/* 1568 */ logger.info(pst.getQueryString());
			/* 1569 */ rs = pst.executeQuery();
			/* 1570 */ while (rs.next()) {
				/* 1571 */ StaticDataVO treasuryVO = new StaticDataVO();
				/* 1572 */ treasuryVO.setTreasuryRefNo(CommonMethods.nullAndTrimString(rs.getString("REFERENCE_NUM")));
				/* 1573 */ treasuryVO
						.setCustomerID(CommonMethods.nullAndTrimString(rs.getString("COUNTERPARTY_STRING")));
				/* 1574 */ treasuryVO.setBookingDate(CommonMethods.nullAndTrimString(rs.getString("HOST_TRAN_DATE")));
				/* 1575 */ treasuryVO.setDealValidFromDate(CommonMethods.nullAndTrimString(rs.getString("START_DATE")));
				/* 1576 */ treasuryVO.setDealValidToDate(CommonMethods.nullAndTrimString(rs.getString("END_DATE")));
				/* 1577 */ treasuryList.add(treasuryVO);
				/*      */ }
			/* 1579 */ } catch (Exception e) {
			/* 1580 */ e.printStackTrace();
			/*      */ } finally {
			/* 1582 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1584 */ logger.info("Exiting Method");
		/* 1585 */ return treasuryList;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ArrayList<StaticDataVO> filterFwdContractList(StaticDataVO fwdContactDataVO,
			ArrayList<StaticDataVO> fwdContractList) {
		/* 1590 */ logger.info("Entering Method");
		/* 1591 */ LoggableStatement pst = null;
		/* 1592 */ ResultSet rs = null;
		/* 1593 */ Connection con = null;
		/* 1594 */ String setValue = null;
		/* 1595 */ String setValue1 = null;
		/*      */
		/*      */ try {
			/* 1598 */ String query = "";
			/* 1599 */ fwdContractList = new ArrayList<>();
			/* 1600 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 1601 */ if (!CommonMethods.isNull(fwdContactDataVO.getFwdContractNo())) {
				/* 1602 */ query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,CATEGORY FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND STATUS='APPROVED' AND CATEGORY ='FWCBOOK'  AND FWC_CONTRACT_NO like '%'||?||'%' ";
				/* 1603 */ setValue = fwdContactDataVO.getFwdContractNo();
				/*      */ }
			/* 1605 */ else if (!CommonMethods.isNull(fwdContactDataVO.getCustomerID())) {
				/* 1606 */ query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,CATEGORY FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND STATUS='APPROVED' AND CATEGORY ='FWCBOOK'  AND CIF_ID like '%'||?||'%' ";
				/* 1607 */ setValue = fwdContactDataVO.getCustomerID();
				/*      */ }
			/* 1609 */ else if (!CommonMethods.isNull(fwdContactDataVO.getFwdContractNo()) &&
			/* 1610 */ !CommonMethods.isNull(fwdContactDataVO.getCustomerID())) {
				/* 1611 */ query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,CATEGORY FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND STATUS='APPROVED' AND CATEGORY ='FWCBOOK'  AND FWC_CONTRACT_NO like '%'||?||'%' AND CIF_ID like '%'||?||'%' ";
				/*      */
				/* 1613 */ setValue = fwdContactDataVO.getFwdContractNo();
				/* 1614 */ setValue1 = fwdContactDataVO.getCustomerID();
				/*      */ } else {
				/* 1616 */ query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,CATEGORY FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND STATUS='APPROVED' AND CATEGORY ='FWCBOOK' ";
				/*      */ }
			/*      */
			/* 1619 */ pst = new LoggableStatement(con, query);
			/* 1620 */ if (setValue != null) {
				/* 1621 */ pst.setString(1, setValue);
				/*      */ }
			/* 1623 */ if (setValue1 != null) {
				/* 1624 */ pst.setString(2, setValue1);
				/*      */ }
			/* 1626 */ logger.info(pst.getQueryString());
			/* 1627 */ rs = pst.executeQuery();
			/* 1628 */ while (rs.next()) {
				/* 1629 */ StaticDataVO fwdContractDataVO = new StaticDataVO();
				/*      */
				/* 1631 */ fwdContractDataVO
						.setFwdContractNo(CommonMethods.nullAndTrimString(rs.getString("FWC_CONTRACT_NO")));
				/* 1632 */ fwdContractDataVO.setCustomerID(CommonMethods.nullAndTrimString(rs.getString("CIF_ID")));
				/* 1633 */ fwdContractDataVO.setBranchCode(CommonMethods.nullAndTrimString(rs.getString("BRANCH")));
				/* 1634 */ fwdContractDataVO
						.setSubProduct(CommonMethods.nullAndTrimString(rs.getString("SUB_PRODUCT")));
				/* 1635 */ fwdContractDataVO.setCategory(CommonMethods.nullAndTrimString(rs.getString("CATEGORY")));
				/* 1636 */ fwdContractList.add(fwdContractDataVO);
				/*      */ }
			/*      */
			/* 1639 */ } catch (Exception e) {
			/* 1640 */ e.printStackTrace();
			/*      */ } finally {
			/* 1642 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 1644 */ logger.info("Exiting Method");
		/* 1645 */ return fwdContractList;
		/*      */ }

	/*      */
	/*      */ public void setErrorForFWCDetails(String errorCode, ForwardContractVO chargeVO) {
		/* 1649 */ String errormsg = CommonMethods.getErrorDescFromProperties(errorCode);
		/* 1650 */ Object[] arg = { Integer.valueOf(0), "E", errormsg, "INPUT" };
		/* 1651 */ CommonMethods.setErrorvalues(arg, this.alertMsgArray);
		/*      */
		/* 1653 */ if (this.alertMsgArray.size() > 0) {
			/* 1654 */ chargeVO.setErrorList(this.alertMsgArray);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public ForwardContractVO saveBookingDetails(ForwardContractVO fwdContractVO) throws DAOException {
		/* 1659 */ HttpSession session = ServletActionContext.getRequest().getSession();
		/* 1660 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				/* 1661 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		/*      */ try {
			/* 1663 */ logger.info("CIF ID-------------------------------" + fwdContractVO.getCustomerID().trim());
			/* 1664 */ validateBookingDetails(fwdContractVO);
			/* 1665 */ logger.info("alertMsgArray.size() " + this.alertMsgArray.size());
			/* 1666 */ if (this.alertMsgArray.size() == 0) {
				/* 1667 */ String userID = request.getRemoteUser();
				/*      */
				/* 1669 */ if (userID == null) {
					/* 1670 */ userID = "SUPERVISOR";
					/*      */ }
				/*      */
				/* 1673 */ logger.info("User ID--------------------------->" + userID);
				/* 1674 */ String screen = fwdContractVO.getScreenType();
				/* 1675 */ if (screen.equalsIgnoreCase("MakerBookingScreen")) {
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/* 1684 */ if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo())) {
						/* 1685 */ updateFwdBookingContractDetails(fwdContractVO, "FWCBOOK", "PENDING TO SUBMIT",
								"Saved");
						/*      */ } else {
						/* 1687 */ insertBookingDetails(fwdContractVO, "FWCBOOK", "PENDING TO SUBMIT", "Saved");
						/*      */ }
					/*      */
					/* 1690 */ } else if (screen.equalsIgnoreCase("MakerCancelScreen")) {
					/*      */
					/* 1692 */ int count = getRecordCountFromDB(fwdContractVO, "FWCCANCEL");

					/* 1703 */ if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo()) && count == 1) {
						/* 1704 */ fwdContractVO = updateFwdCancelContractDetails(fwdContractVO, "FWCCANCEL",
								"PENDING TO SUBMIT", "Saved");
						/* 1705 */ } else if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo())
								&& count == 0) {
						/* 1706 */ fwdContractVO = insertCancelDetails(fwdContractVO, "FWCCANCEL", "PENDING TO SUBMIT",
								"Saved");
						/*      */ }
					/*      */
					/*      */ }

				else if (screen.equalsIgnoreCase("MakerCancelScreenWithoutRate")) {
					/*      */
					/* 1692 */ int count = getRecordCountFromDB(fwdContractVO, "FWCCANCEL");

					/* 1703 */ if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo()) && count == 1) {
						/* 1704 */ fwdContractVO = updateFwdCancelContractDetails(fwdContractVO, "FWCCANCEL",
								"PENDING TO SUBMIT", "Saved");
						/* 1705 */ } else if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo())
								&& count == 0) {
						/* 1706 */ fwdContractVO = insertCancelDetails(fwdContractVO, "FWCCANCEL", "PENDING TO SUBMIT",
								"Saved");
						/*      */ }
					/*      */
					/*      */ }

				/*      */ }
			/* 1711 */ } catch (Exception e) {
			/* 1712 */ e.printStackTrace();
			/*      */ }
		/* 1714 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO insertBookingDetails(ForwardContractVO fwdContractVO, String category,
			String status, String action) throws DAOException {
		/* 1719 */ Connection connection = null;
		/* 1720 */ LoggableStatement loggableStatement = null;
		/* 1721 */ HttpSession session = ServletActionContext.getRequest().getSession();
		/* 1722 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				/* 1723 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		/*      */
		/*      */ try {
			/* 1726 */ logger.info("Sub product-------------------------------" + fwdContractVO.getSubProduct().trim());
			/* 1727 */ logger.info("CIF ID-------------------------------" + fwdContractVO.getCustomerID().trim());
			/* 1728 */ validateBookingDetails(fwdContractVO);
			/* 1729 */ logger.info("alertMsgArray size " + this.alertMsgArray.size());
			/* 1730 */ if (this.alertMsgArray.size() == 0)
			/*      */ {
				/* 1732 */ String userID = request.getRemoteUser();
				/*      */
				/* 1734 */ if (userID == null) {
					/* 1735 */ userID = "SUPERVISOR";
					/*      */ }
				/*      */
				/* 1738 */ connection = DBConnectionUtility.getZoneConnection();
				/* 1739 */ loggableStatement = new LoggableStatement(connection,
						"INSERT INTO CUSTOM_FWC_DETAILS(CATEGORY,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY, \tBOOKING_DATE,FWC_AMOUNT,TO_CCY_AMT,DEAL_VALID_FROM,DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT, \tAVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,STATUS,INPUT_BY,INPUT_TIMESTAMP,LAST_ACTION,FWC_CONTRACT_NO) \tVALUES(?,?,?,?,?,?,TO_DATE(?,'dd/mm/yyyy'),?,?,TO_DATE(?,'dd/mm/yyyy'),TO_DATE(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?,?) ");
				/* 1740 */ loggableStatement.setString(1, category);
				/* 1741 */ loggableStatement.setString(2, fwdContractVO.getSubProduct());
				/* 1742 */ loggableStatement.setString(3, fwdContractVO.getCustomerID());
				/* 1743 */ loggableStatement.setString(4, fwdContractVO.getBranchCode());
				/* 1744 */ loggableStatement.setString(5, fwdContractVO.getAcctNumber());
				/* 1745 */ loggableStatement.setString(6, fwdContractVO.getDealCurrency());
				/* 1746 */ loggableStatement.setString(7, fwdContractVO.getBookingDate());
				/* 1747 */ loggableStatement.setString(8, fwdContractVO.getFwdContractAmt());
				/* 1748 */ loggableStatement.setString(9, fwdContractVO.getToCurrencyAmt());
				/* 1749 */ loggableStatement.setString(10, fwdContractVO.getDealValidFromDate());
				/* 1750 */ loggableStatement.setString(11, fwdContractVO.getDealValidToDate());
				/* 1751 */ loggableStatement.setString(12, fwdContractVO.getTreasuryRefNo());
				/* 1752 */ loggableStatement.setString(13, fwdContractVO.getTreasuryRate());
				/* 1753 */ loggableStatement.setString(14, fwdContractVO.getOutstandingAmt());
				/*      */
				/* 1755 */ loggableStatement.setString(15, fwdContractVO.getLimitID());
				/* 1756 */ loggableStatement.setString(16, fwdContractVO.getWithoutLimit());
				/* 1757 */ loggableStatement.setString(17, fwdContractVO.getAvailableLimit());
				/*      */
				/* 1759 */ loggableStatement.setString(18, fwdContractVO.getWashRate());
				/* 1760 */ loggableStatement.setString(19, fwdContractVO.getLeiNumber());
				/* 1761 */ loggableStatement.setString(20, fwdContractVO.getPlAmount());
				/* 1762 */ loggableStatement.setString(21, fwdContractVO.getChargeAmount());
				/* 1763 */ loggableStatement.setString(22, fwdContractVO.getGstAmount());
				/* 1764 */ loggableStatement.setString(23, fwdContractVO.getInstructions());
				/* 1765 */ loggableStatement.setString(24, fwdContractVO.getMargin());
				/* 1766 */ loggableStatement.setString(25, status);
				/* 1767 */ loggableStatement.setString(26, userID.trim());
				/* 1768 */ loggableStatement.setString(27, action);
				/*      */
				/* 1770 */ String fwdContractSeqNo = FWCUtil.generateFWCReferenceNumber(fwdContractVO.getBranchCode(),
						/* 1771 */ fwdContractVO.getSubProduct());
				/* 1772 */ loggableStatement.setString(28, fwdContractSeqNo);
				/*      */
				/* 1774 */ logger.info("Insert Query----------------->" + loggableStatement.getQueryString());
				/*      */
				/* 1776 */ int count = loggableStatement.executeUpdate();
				/*      */
				/* 1778 */ if (count > 0) {
					/* 1779 */ logger.info("Inserted Successfully");
					/* 1780 */ fwdContractVO.setCount(count);
					/* 1781 */ fwdContractVO.setFwdContractNo(fwdContractSeqNo);
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/* 1786 */ } catch (Exception e) {
			/* 1787 */ e.printStackTrace();
			/*      */ } finally {
			/* 1789 */ DBConnectionUtility.surrenderDB(connection, (Statement) loggableStatement, null);
			/*      */ }
		/* 1791 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO updateFwdBookingContractDetails(ForwardContractVO fwdContractVO,
			String category, String status, String action) throws DAOException {
		/* 1796 */ Connection connection = null;
		/* 1797 */ LoggableStatement loggableStatement = null;
		/* 1798 */ HttpSession session = ServletActionContext.getRequest().getSession();
		/* 1799 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				/* 1800 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		/*      */
		/*      */ try {
			/* 1803 */ logger
					.info("Forward Contract-------------------------------" + fwdContractVO.getFwdContractNo().trim());
			/* 1804 */ logger.info("CIF ID-------------------------------" + fwdContractVO.getCustomerID().trim());
			/* 1805 */ validateBookingDetails(fwdContractVO);
			/* 1806 */ logger.info("alertMsgArray size " + this.alertMsgArray.size());
			/* 1807 */ if (this.alertMsgArray.size() == 0)
			/*      */ {
				/* 1809 */ String userID = request.getRemoteUser();
				/*      */
				/* 1811 */ if (userID == null) {
					/* 1812 */ userID = "SUPERVISOR";
					/*      */ }
				/*      */
				/* 1815 */ connection = DBConnectionUtility.getZoneConnection();
				/* 1816 */ loggableStatement = new LoggableStatement(connection,
						"UPDATE CUSTOM_FWC_DETAILS SET SUB_PRODUCT=?,BRANCH=?,ACCT_NUMBER=?,DEAL_CCY=?,BOOKING_DATE=TO_DATE(?,'dd/mm/yyyy'),  FWC_AMOUNT=?,TO_CCY_AMT=?,DEAL_VALID_FROM=TO_DATE(?,'dd/mm/yyyy'),DEAL_VALID_TO=TO_DATE(?,'dd/mm/yyyy') ,TREASURY_REF_NO=?,TREASURY_RATE=?,OUTSTANDING_AMT=?,LIMIT_ID=?,WITHOUT_LIMIT=?,AVAILABLE_LIMIT=?,WASH_RATE=?,LEI_NUMBER=?,  PL_AMOUNT=?,CHARGE_AMOUNT=?,GST_AMOUNT=?,INSTRUCTIONS=?,MARGIN=?,STATUS=?,MODIFIED_BY=?,MODIFIED_TIMESTAMP=SYSTIMESTAMP,LAST_ACTION=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? ");
				/* 1817 */ loggableStatement.setString(1, fwdContractVO.getSubProduct());
				/* 1818 */ loggableStatement.setString(2, fwdContractVO.getBranchCode());
				/* 1819 */ loggableStatement.setString(3, fwdContractVO.getAcctNumber());
				/* 1820 */ loggableStatement.setString(4, fwdContractVO.getDealCurrency());
				/* 1821 */ loggableStatement.setString(5, fwdContractVO.getBookingDate());
				/* 1822 */ loggableStatement.setString(6, fwdContractVO.getFwdContractAmt());
				/* 1823 */ loggableStatement.setString(7, fwdContractVO.getToCurrencyAmt());
				/* 1824 */ loggableStatement.setString(8, fwdContractVO.getDealValidFromDate());
				/* 1825 */ loggableStatement.setString(9, fwdContractVO.getDealValidToDate());
				/* 1826 */ loggableStatement.setString(10, fwdContractVO.getTreasuryRefNo());
				/* 1827 */ loggableStatement.setString(11, fwdContractVO.getTreasuryRate());
				/* 1828 */ loggableStatement.setString(12, fwdContractVO.getOutstandingAmt());
				/*      */
				/* 1830 */ loggableStatement.setString(13, fwdContractVO.getLimitID());
				/* 1831 */ loggableStatement.setString(14, fwdContractVO.getWithoutLimit());
				/* 1832 */ loggableStatement.setString(15, fwdContractVO.getAvailableLimit());
				/*      */
				/* 1834 */ loggableStatement.setString(16, fwdContractVO.getWashRate());
				/* 1835 */ loggableStatement.setString(17, fwdContractVO.getLeiNumber());
				/* 1836 */ loggableStatement.setString(18, fwdContractVO.getPlAmount());
				/* 1837 */ loggableStatement.setString(19, fwdContractVO.getChargeAmount());
				/* 1838 */ loggableStatement.setString(20, fwdContractVO.getGstAmount());
				/* 1839 */ loggableStatement.setString(21, fwdContractVO.getInstructions());
				/* 1840 */ loggableStatement.setString(22, fwdContractVO.getMargin());
				/* 1841 */ loggableStatement.setString(23, status);
				/* 1842 */ loggableStatement.setString(24, userID.trim());
				/* 1843 */ loggableStatement.setString(25, action);
				/* 1844 */ loggableStatement.setString(26, category);
				/* 1845 */ loggableStatement.setString(27, fwdContractVO.getFwdContractNo());
				/*      */
				/*      */
				/* 1848 */ logger.info("Update Query----------------->" + loggableStatement.getQueryString());
				/*      */
				/* 1850 */ int count = loggableStatement.executeUpdate();
				/*      */
				/* 1852 */ if (count > 0) {
					/* 1853 */ logger.info("Updated Successfully");
					/* 1854 */ fwdContractVO.setCount(count);
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/* 1859 */ } catch (Exception e) {
			/* 1860 */ e.printStackTrace();
			/*      */ } finally {
			/* 1862 */ DBConnectionUtility.surrenderDB(connection, (Statement) loggableStatement, null);
			/*      */ }
		/* 1864 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO updateFwdCancelContractDetails(ForwardContractVO fwdContractVO, String category,
			String status, String action) throws DAOException {
		/* 1869 */ Connection connection = null;
		/* 1870 */ LoggableStatement loggableStatement = null;
		/* 1871 */ HttpSession session = ServletActionContext.getRequest().getSession();
		/* 1872 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				/* 1873 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		/*      */
		/*      */ try {
			/* 1876 */ logger
					.info("Forward Contract-------------------------------" + fwdContractVO.getFwdContractNo().trim());
			/* 1877 */ logger.info("CIF ID-------------------------------" + fwdContractVO.getCustomerID().trim());
			/* 1878 */ validateBookingDetails(fwdContractVO);
			/* 1879 */ logger.info("alertMsgArray size " + this.alertMsgArray.size());
			/* 1880 */ if (this.alertMsgArray.size() == 0)
			/*      */ {
				/* 1882 */ String userID = request.getRemoteUser();
				/*      */
				/* 1884 */ if (userID == null) {
					/* 1885 */ userID = "SUPERVISOR";
					/*      */ }
				/* 1887 */ logger.info("Update Forward Contract Cancel Details");
				/*      */
				/* 1889 */ connection = DBConnectionUtility.getZoneConnection();
				/* 1890 */ logger.info("after getting db connection");
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 1899 */ loggableStatement = new LoggableStatement(connection,
						"UPDATE CUSTOM_FWC_DETAILS SET SUB_PRODUCT=?,BRANCH=?,ACCT_NUMBER=?,DEAL_CCY=?,BOOKING_DATE=TO_DATE(?,'dd/mm/yyyy'),  FWC_AMOUNT=?,TO_CCY_AMT=?,DEAL_VALID_FROM=TO_DATE(?,'dd/mm/yyyy'),DEAL_VALID_TO=TO_DATE(?,'dd/mm/yyyy') ,TREASURY_REF_NO=?,TREASURY_RATE=?,OUTSTANDING_AMT=?,LIMIT_ID=?,WITHOUT_LIMIT=?,AVAILABLE_LIMIT=?,WASH_RATE=?,LEI_NUMBER=?,  PL_AMOUNT=?,CHARGE_AMOUNT=?,GST_AMOUNT=?,INSTRUCTIONS=?,MARGIN=?,STATUS=?,MODIFIED_BY=?,MODIFIED_TIMESTAMP=SYSTIMESTAMP,LAST_ACTION=?,CANCELLATION_AMOUNT=?,TRANS_ID=?,TRANS_DATE=?,BOOKING_RATE=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? ");
				/* 1900 */ logger.info("Output of query:");
				/* 1901 */ loggableStatement.setString(1, fwdContractVO.getSubProduct());
				/* 1902 */ loggableStatement.setString(2, fwdContractVO.getBranchCode());
				/* 1903 */ loggableStatement.setString(3, fwdContractVO.getAcctNumber());
				/* 1904 */ loggableStatement.setString(4, fwdContractVO.getDealCurrency());
				/* 1905 */ loggableStatement.setString(5, fwdContractVO.getBookingDate());

				String fwcAmount = fwdContractVO.getFwdContractAmt();

				String toCcyAmt = fwdContractVO.getToCurrencyAmt();

				if ("FWCCANCEL".equalsIgnoreCase(category)) {

					loggableStatement.setString(6, toCcyAmt); // FWC_AMOUNT

					loggableStatement.setString(7, fwcAmount); // TO_CCY_AMT

				} else {

					loggableStatement.setString(6, fwcAmount);

					loggableStatement.setString(7, toCcyAmt);

				}

				/* 1908 */ loggableStatement.setString(8, fwdContractVO.getDealValidFromDate());
				/* 1909 */ loggableStatement.setString(9, fwdContractVO.getDealValidToDate());
				/* 1910 */ loggableStatement.setString(10, fwdContractVO.getTreasuryRefNo());
				/* 1911 */ loggableStatement.setString(11, fwdContractVO.getTreasuryRate());
				/* 1912 */ loggableStatement.setString(12, fwdContractVO.getOutstandingAmt());
				/*      */
				/* 1914 */ loggableStatement.setString(13, fwdContractVO.getLimitID());
				/* 1915 */ loggableStatement.setString(14, fwdContractVO.getWithoutLimit());
				/* 1916 */ loggableStatement.setString(15, fwdContractVO.getAvailableLimit());
				/*      */
				/* 1918 */ loggableStatement.setString(16, fwdContractVO.getWashRate());
				/* 1919 */ loggableStatement.setString(17, fwdContractVO.getLeiNumber());
				/* 1920 */ loggableStatement.setString(18, fwdContractVO.getPlAmount());
				/* 1921 */ loggableStatement.setString(19, fwdContractVO.getChargeAmount());
				/* 1922 */ loggableStatement.setString(20, fwdContractVO.getGstAmount());
				/* 1923 */ loggableStatement.setString(21, fwdContractVO.getInstructions());
				/* 1924 */ loggableStatement.setString(22, fwdContractVO.getMargin());
				/* 1925 */ loggableStatement.setString(23, status);
				/* 1926 */ loggableStatement.setString(24, userID.trim());
				/* 1927 */ loggableStatement.setString(25, action);
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 1934 */ loggableStatement.setString(26, fwdContractVO.getCancellationamount());
				/* 1935 */ loggableStatement.setString(27, fwdContractVO.getTransid());
				/* 1936 */ loggableStatement.setString(28, fwdContractVO.getTransdate());
				/* 1937 */ loggableStatement.setString(29, fwdContractVO.getBookingrate());
				/* 1938 */ loggableStatement.setString(30, category);
				/* 1939 */ loggableStatement.setString(31, fwdContractVO.getFwdContractNo());
				/*      */
				/*      */
				/* 1942 */ logger.info("Update Query----------------->" + loggableStatement.getQueryString());
				/*      */
				/* 1944 */ int count = loggableStatement.executeUpdate();
				/*      */
				/* 1946 */ if (count > 0) {
					/* 1947 */ logger.info("Updated Successfully");
					/* 1948 */ fwdContractVO.setCount(count);
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/* 1953 */ } catch (Exception e) {
			/* 1954 */ e.printStackTrace();
			/* 1955 */ logger.info("Inside exception cancel fwc : " + e.getMessage());
			/*      */ } finally {
			/* 1957 */ DBConnectionUtility.surrenderDB(connection, (Statement) loggableStatement, null);
			/*      */ }
		/* 1959 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public ForwardContractVO insertCancelDetails(ForwardContractVO fwdContractVO, String category,
			String status, String action) throws DAOException {

		/* 1964 */ Connection connection = null;
		/* 1965 */ LoggableStatement loggableStatement = null;
		/* 1966 */ HttpSession session = ServletActionContext.getRequest().getSession();
		/* 1967 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				/* 1968 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		/*      */
		/*      */ try {
			/* 1971 */ logger.info("Sub product-------------------------------" + fwdContractVO.getSubProduct().trim());
			/* 1972 */ logger.info("CIF ID-------------------------------" + fwdContractVO.getCustomerID().trim());
			/* 1973 */ validateBookingDetails(fwdContractVO);
			/* 1974 */ logger.info("alertMsgArray size " + this.alertMsgArray.size());
			/* 1975 */ if (this.alertMsgArray.size() == 0)
			/*      */ {
				/* 1977 */ String userID = request.getRemoteUser();
				/*      */
				/* 1979 */ if (userID == null) {
					/* 1980 */ userID = "SUPERVISOR";
					/*      */ }
				
				
				logger.info("=== INSERT DEBUG ===");
				logger.info("category --> " + category);
				logger.info("screenType --> " + fwdContractVO.getScreenType());
				logger.info("fwdContractAmt (Event Amount) --> " + fwdContractVO.getFwdContractAmt());
				logger.info("toCurrencyAmt (To Amount) --> " + fwdContractVO.getToCurrencyAmt());
				logger.info("Cancellation Amount --> " + fwdContractVO.getOutstandingAmt());
				logger.info("Event Outstanding --> " + fwdContractVO.getCancellationamount());
				logger.info("===================");
				
				/*      */
				/* 1983 */ connection = DBConnectionUtility.getZoneConnection();
				/* 1984 */ loggableStatement = new LoggableStatement(connection,
						"INSERT INTO CUSTOM_FWC_DETAILS(CATEGORY,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY, \tBOOKING_DATE,FWC_AMOUNT,TO_CCY_AMT,DEAL_VALID_FROM,DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,LIMIT_ID,WITHOUT_LIMIT, \tAVAILABLE_LIMIT,WASH_RATE,LEI_NUMBER,PL_AMOUNT,CHARGE_AMOUNT,GST_AMOUNT,INSTRUCTIONS,MARGIN,STATUS,INPUT_BY,INPUT_TIMESTAMP,LAST_ACTION,FWC_CONTRACT_NO,CANCELLATION_AMOUNT,TRANS_ID, TRANS_DATE, BOOKING_RATE)\tVALUES(?,?,?,?,?,?,TO_DATE(?,'dd/mm/yyyy'),?,?,TO_DATE(?,'dd/mm/yyyy'),TO_DATE(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?,?) ");
				/* 1985 */ loggableStatement.setString(1, category);
				/* 1986 */ loggableStatement.setString(2, fwdContractVO.getSubProduct());
				/* 1987 */ loggableStatement.setString(3, fwdContractVO.getCustomerID());
				/* 1988 */ loggableStatement.setString(4, fwdContractVO.getBranchCode());
				/* 1989 */ loggableStatement.setString(5, fwdContractVO.getAcctNumber());
				/* 1990 */ loggableStatement.setString(6, fwdContractVO.getDealCurrency());
				/* 1991 */ loggableStatement.setString(7, fwdContractVO.getBookingDate());
				
						   String fwcAmount = fwdContractVO.getFwdContractAmt();
						   String toCcyAmt = fwdContractVO.getToCurrencyAmt();
					       String screenType = fwdContractVO.getScreenType();
				
					       loggableStatement.setString(8, fwcAmount);
					       loggableStatement.setString(9, toCcyAmt);
				

				/* 1994 */ loggableStatement.setString(10, fwdContractVO.getDealValidFromDate());
				/* 1995 */ loggableStatement.setString(11, fwdContractVO.getDealValidToDate());
				/* 1996 */ loggableStatement.setString(12, fwdContractVO.getTreasuryRefNo());
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 2002 */ loggableStatement.setString(13, fwdContractVO.getTreasuryRate());
				
				
				if ("MakerCancelScreenWithoutRate".equalsIgnoreCase(screenType)) {
				    loggableStatement.setString(14, fwdContractVO.getCancellationamount()); // 50000 EUR ← full contract
				} else {
				    loggableStatement.setString(14, fwdContractVO.getOutstandingAmt()); // existing flow
				}
				/*      */
				/* 2005 */ loggableStatement.setString(15, fwdContractVO.getLimitID());
				/* 2006 */ loggableStatement.setString(16, fwdContractVO.getWithoutLimit());
				/* 2007 */ loggableStatement.setString(17, fwdContractVO.getAvailableLimit());
				/*      */
				/* 2009 */ loggableStatement.setString(18, fwdContractVO.getWashRate());
				/* 2010 */ loggableStatement.setString(19, fwdContractVO.getLeiNumber());
				/* 2011 */ loggableStatement.setString(20, fwdContractVO.getPlAmount());
				/* 2012 */ loggableStatement.setString(21, fwdContractVO.getChargeAmount());
				/* 2013 */ loggableStatement.setString(22, fwdContractVO.getGstAmount());
				/* 2014 */ loggableStatement.setString(23, fwdContractVO.getInstructions());
				/* 2015 */ loggableStatement.setString(24, fwdContractVO.getMargin());
				/* 2016 */ loggableStatement.setString(25, status);
				/* 2017 */ loggableStatement.setString(26, userID.trim());
				/* 2018 */ loggableStatement.setString(27, action);
				/* 2019 */ loggableStatement.setString(28, fwdContractVO.getFwdContractNo());
				/*      */
				// line 2021 — change for Without Rate flow

				if ("MakerCancelScreenWithoutRate".equalsIgnoreCase(screenType)) {
				    loggableStatement.setString(29, fwdContractVO.getOutstandingAmt()); // 1555 EUR ← CORRECT
				} else {
				    loggableStatement.setString(29, fwdContractVO.getCancellationamount()); // existing flow
				}
				 
				/* 2022 */ loggableStatement.setString(30, fwdContractVO.getTransid());
				/* 2023 */ loggableStatement.setString(31, fwdContractVO.getTransdate());
				/* 2024 */ loggableStatement.setString(32, fwdContractVO.getBookingrate());
				/*      */
				/* 2026 */ logger
						.info("Insert Query for cancel booking----------------->" + loggableStatement.getQueryString());
				/*      */
				/* 2028 */ int count = loggableStatement.executeUpdate();
				/*      */
				/* 2030 */ if (count > 0) {
					/* 2031 */ logger.info("Inserted Successfully");
					/* 2032 */ fwdContractVO.setCount(count);
					/*      */ }
				/*      */
				/*      */ }
			/*      */
			/* 2037 */ } catch (Exception e) {
			/* 2038 */ e.printStackTrace();
			/*      */ } finally {
			/* 2040 */ DBConnectionUtility.surrenderDB(connection, (Statement) loggableStatement, null);
			/*      */ }
		/* 2042 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */ public String getBookingTreasuryrate(String forwardContractNo) {
		/* 2046 */ logger.info("Entering Method");
		/* 2047 */ LoggableStatement pst = null;
		/* 2048 */ ResultSet rs = null;
		/* 2049 */ Connection con = null;
		/* 2050 */ String treasuryrate = null;
		/*      */ try {
			/* 2052 */ logger.info("Enter into getBookingTreasuryrate");
			/* 2053 */ con = DBConnectionUtility.getZoneConnection();
			/* 2054 */ String query = "SELECT TREASURY_RATE FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO='" +
			/* 2055 */ forwardContractNo.trim() + "' AND CATEGORY ='FWCBOOK'";
			/* 2056 */ pst = new LoggableStatement(con, query);
			/* 2057 */ rs = pst.executeQuery();
			/* 2058 */ while (rs.next()) {
				/* 2059 */ treasuryrate = rs.getString("TREASURY_RATE");
				/*      */ }
			/* 2061 */ logger.info("Exit getBookingTreasuryrate treasuryrate= " + treasuryrate);
			/* 2062 */ } catch (Exception e) {
			/* 2063 */ e.printStackTrace();
			/*      */ } finally {
			/* 2065 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 2067 */ logger.info("Exiting Method");
		/* 2068 */ return treasuryrate;
		/*      */ }

	/*      */
	/*      */ public String getBookingAmount(String forwardContractNo) {
		/* 2072 */ logger.info("Entering Method");
		/* 2073 */ LoggableStatement pst = null;
		/* 2074 */ ResultSet rs = null;
		/* 2075 */ Connection con = null;
		/* 2076 */ String amt = null;
		/*      */ try {
			/* 2078 */ logger.info("Enter into getBookingAmount");
			/* 2079 */ con = DBConnectionUtility.getZoneConnection();
			/* 2080 */ String query = "SELECT FWC_AMOUNT FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO='" +
			/* 2081 */ forwardContractNo.trim() + "' AND CATEGORY ='FWCBOOK'";
			/* 2082 */ pst = new LoggableStatement(con, query);
			/* 2083 */ rs = pst.executeQuery();
			/* 2084 */ while (rs.next()) {
				/* 2085 */ amt = rs.getString("FWC_AMOUNT");
				/*      */ }
			/* 2087 */ logger.info("Enter getBookingAmount amt= " + amt);
			/* 2088 */ } catch (Exception e) {
			/* 2089 */ e.printStackTrace();
			/*      */ } finally {
			/* 2091 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 2093 */ logger.info("Exiting Method");
		/* 2094 */ return amt;
		/*      */ }

	/*      */
	/*      */ public String getBuyOrSellAmount(String forwardContractNo, String treasuryRefNo, String buysell) {
		/* 2098 */ logger.info("Entering Method");
		/* 2099 */ LoggableStatement pst = null;
		/* 2100 */ ResultSet rs = null;
		/* 2101 */ Connection con = null;
		/* 2102 */ String amt = null;
		/*      */ try {
			/* 2104 */ logger.info("Enter into getBuyOrSellAmount");
			/* 2105 */ ServiceUtility.getProperties();
			/* 2106 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
			/* 2107 */ con = DBConnectionUtility.getZoneConnection();
			/* 2108 */ String query = "";
			/* 2109 */ if (buysell.equalsIgnoreCase("S")) {
				/* 2110 */ query = "SELECT BUY_AMOUNT AS FWC_AMOUNT FROM " + treasuryHDDTableName
						+ " WHERE FWC_REF_NUM='" +
						/* 2111 */ forwardContractNo.trim() + "' AND REFERENCE_NUM= '" + treasuryRefNo.trim()
						+ "' AND HOST_DEAL_CATEGORY ='FWCCANCEL'";
				/* 2112 */ } else if (buysell.equalsIgnoreCase("B") || buysell.equalsIgnoreCase("P")) {
				/* 2113 */ query = "SELECT SELL_AMOUNT AS FWC_AMOUNT FROM " + treasuryHDDTableName
						+ " WHERE FWC_REF_NUM='" +
						/* 2114 */ forwardContractNo.trim() + "' AND REFERENCE_NUM= '" + treasuryRefNo.trim()
						+ "' AND HOST_DEAL_CATEGORY ='FWCCANCEL'";
				/*      */ }
			/* 2116 */ pst = new LoggableStatement(con, query);
			/* 2117 */ logger.info("Query= " + pst.getQueryString());
			/* 2118 */ rs = pst.executeQuery();
			/* 2119 */ while (rs.next()) {
				/* 2120 */ amt = rs.getString("FWC_AMOUNT");
				/*      */ }
			/* 2122 */ logger.info("Exit getBuyOrSellAmount amt= " + amt);
			/* 2123 */ } catch (Exception e) {
			/* 2124 */ e.printStackTrace();
			/*      */ } finally {
			/* 2126 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 2128 */ logger.info("Exiting Method");
		/* 2129 */ return amt;
		/*      */ }

	/*      */
	/*      */ public String getRateForConversion(String currency, String buysell) {
		/* 2133 */ logger.info("Entering Method");
		/* 2134 */ LoggableStatement pst = null;
		/* 2135 */ ResultSet rs = null;
		/* 2136 */ Connection con = null;
		/* 2137 */ String rate = null;
		/*      */ try {
			/* 2139 */ logger.info("Enter into getRateForConversion");
			/* 2140 */ con = DBConnectionUtility.getZoneConnection();
			/* 2141 */ String query = "";
			/* 2142 */ if (buysell.equalsIgnoreCase("S")) {
				/* 2143 */ query = "SELECT SELLEX99 AS CUR_RATE FROM FXRATE86 WHERE CURREN49='" + currency.trim() + "'";
				/* 2144 */ } else if (buysell.equalsIgnoreCase("B")) {
				/* 2145 */ query = "SELECT BUYEXC03 AS CUR_RATE FROM FXRATE86 WHERE CURREN49='" + currency.trim() + "'";
				/*      */ }
			/* 2147 */ pst = new LoggableStatement(con, query);
			/* 2148 */ rs = pst.executeQuery();
			/* 2149 */ while (rs.next()) {
				/* 2150 */ rate = rs.getString("CUR_RATE");
				/*      */ }
			/* 2152 */ logger.info("Exiting into getRateForConversion rate= " + rate);
			/* 2153 */ } catch (Exception e) {
			/* 2154 */ e.printStackTrace();
			/*      */ } finally {
			/* 2156 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 2158 */ logger.info("Exiting Method");
		/* 2159 */ return rate;
		/*      */ }

	/*      */
	/*      */ public String getLimitNodeForBooking(String forwardContractNo) {
		/* 2163 */ logger.info("Entering Method");
		/* 2164 */ LoggableStatement pst = null;
		/* 2165 */ ResultSet rs = null;
		/* 2166 */ Connection con = null;
		/* 2167 */ String serial = null;
		/*      */ try {
			/* 2169 */ logger.info("Enter into getLimitNodeForBooking");
			/* 2170 */ con = DBConnectionUtility.getZoneConnection();
			/* 2171 */ String query = "SELECT LIMIT_SERIAL_NUM FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO='" +
			/* 2172 */ forwardContractNo.trim() + "' AND CATEGORY ='FWCBOOK'";
			/* 2173 */ pst = new LoggableStatement(con, query);
			/* 2174 */ rs = pst.executeQuery();
			/* 2175 */ while (rs.next()) {
				/* 2176 */ serial = rs.getString("LIMIT_SERIAL_NUM");
				/*      */ }
			/* 2178 */ } catch (Exception e) {
			/* 2179 */ e.printStackTrace();
			/*      */ } finally {
			/* 2181 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 2183 */ return serial;
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
	/*      */ public ForwardContractVO validateBookingDetails(ForwardContractVO fwdContractVO) {
		/* 2198 */ String customerID = "";
		/* 2199 */ String subProduct = "";
		/* 2200 */ String fwdContractAmtCcy = "";
		/* 2201 */ String fwdContractAmt = "";
		/* 2202 */ String fwdContractCcy = "";
		/* 2203 */ String treasuryRate = "";
		/* 2204 */ String treRefNo = "";
		/* 2205 */ String branch = "";
		/* 2206 */ String bookingDate = "";
		/* 2207 */ String limitID = "";
		/* 2208 */ String toAmtCcy = "";
		/* 2209 */ String toCcy = "";
		/* 2210 */ String toAmount = "";
		/* 2211 */ String chargeAmountCcy = "";
		/* 2212 */ String gstAmountCcy = "";
		/* 2213 */ String chargeAmt = "";
		/* 2214 */ String chargeCcy = "";
		/* 2215 */ String gstAmt = "";
		/* 2216 */ String gstCcy = "";
		/* 2217 */ String customerAcctNo = "";
		/* 2218 */ String washRate = "";
		/* 2219 */ String outstandingamt = "";
		/* 2220 */ String outstandingamtCcy = "";
		/* 2221 */ AvailBalAuthCheckUtility accountBalance = new AvailBalAuthCheckUtility();
		/* 2222 */ String balance = "";
		/* 2223 */ String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
		/* 2224 */ msgId = msgId.replaceAll("[- :.]", "");
		/* 2225 */ String totalAmt = "";
		/* 2226 */ String outstandingccy = "";
		/* 2227 */ String cancellationamountccy = "";
		/*      */
		/*      */
		/*      */
		/*      */ try {
			/* 2232 */ StaticDataVO staticdatavo = new StaticDataVO();
			/* 2233 */ String screenType = fwdContractVO.getScreenType();
			/*      */
			/* 2235 */ logger.info("Inside validate of screen --> " + screenType);
			/*      */
			/* 2237 */ if (this.alertMsgArray != null &&
			/* 2238 */ this.alertMsgArray.size() > 0) {
				/* 2239 */ this.alertMsgArray.clear();
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
			/* 2250 */ if (CommonMethods.isValidString(fwdContractVO.getCustomerID())) {
				/* 2251 */ customerID = fwdContractVO.getCustomerID().trim();
				/*      */ }
			/* 2253 */ if (CommonMethods.isValidString(fwdContractVO.getFwdContractAmt())) {
				/* 2254 */ fwdContractAmtCcy = fwdContractVO.getFwdContractAmt().trim();
				/* 2255 */ fwdContractCcy = fwdContractAmtCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 2256 */ fwdContractAmt = fwdContractAmtCcy.trim().replaceAll("[^0-9.]", "");
				/* 2257 */ logger.info("fwdContractAmtCcy --> " + fwdContractAmtCcy + " :: fwdContractCcy --> "
						+ fwdContractCcy + " :: fwdContractAmt --> " + fwdContractAmt);
				/*      */ }
			/*      */
			/* 2260 */ if (CommonMethods.isValidString(fwdContractVO.getTreasuryRate())) {
				/* 2261 */ treasuryRate = fwdContractVO.getTreasuryRate().trim();
				/*      */ }
			/* 2263 */ if (CommonMethods.isValidString(fwdContractVO.getSubProduct())) {
				/* 2264 */ subProduct = fwdContractVO.getSubProduct().trim();
				/*      */ }
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 2271 */ if (CommonMethods.isValidString(fwdContractVO.getTreasuryRefNo())) {
				/* 2272 */ treRefNo = fwdContractVO.getTreasuryRefNo().trim();
				/*      */ }
			/* 2274 */ if (CommonMethods.isValidString(fwdContractVO.getBranchCode())) {
				/* 2275 */ branch = fwdContractVO.getBranchCode().trim();
				/*      */ }
			/* 2277 */ if (CommonMethods.isValidString(fwdContractVO.getBookingDate())) {
				/* 2278 */ bookingDate = fwdContractVO.getBookingDate().trim();
				/*      */ }
			/* 2280 */ if (CommonMethods.isValidString(fwdContractVO.getLimitID())) {
				/* 2281 */ limitID = fwdContractVO.getLimitID().trim();
				/*      */ }
			/* 2283 */ if (CommonMethods.isValidString(fwdContractVO.getAcctNumber())) {
				/* 2284 */ customerAcctNo = fwdContractVO.getAcctNumber().trim();
				/*      */ }
			/* 2286 */ if (CommonMethods.isValidString(fwdContractVO.getToCurrencyAmt())) {
				/* 2287 */ toAmtCcy = fwdContractVO.getToCurrencyAmt().trim();
				/* 2288 */ toCcy = toAmtCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 2289 */ toAmount = toAmtCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 2292 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
				/* 2293 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
				/* 2294 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 2295 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 2298 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
				/* 2299 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
				/* 2300 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 2301 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 2304 */ if (CommonMethods.isValidString(fwdContractVO.getWashRate())) {
				/* 2305 */ washRate = fwdContractVO.getWashRate().trim();
				/*      */ }
			/* 2307 */ if (screenType.equals("MakerBookingScreen")) {
				/* 2308 */ String treasuryHostDealCategory = getBookHostDealCategoryFromTreasury(treRefNo, customerID);
				/* 2309 */ if (treasuryHostDealCategory == null || treasuryHostDealCategory.equalsIgnoreCase("null")
						|| !treasuryHostDealCategory.equals("FWCBOOK")) {
					/* 2310 */ setErrorForFWCDetails("DEAL_CATEGORY_CHECK", fwdContractVO);
					/*      */ }
				/* 2312 */ } else if (screenType.equals("MakerCancelScreen")) {
				/* 2313 */ String treasuryHostDealCategory = getCancelHostDealCategoryFromTreasury(treRefNo,
						customerID);
				/* 2314 */ if (treasuryHostDealCategory == null || treasuryHostDealCategory.equalsIgnoreCase("null")
						|| !treasuryHostDealCategory.equals("FWCCANCEL")) {
					/* 2315 */ setErrorForFWCDetails("DEAL_CATEGORY_CHECK", fwdContractVO);
					/*      */ }
				/*      */ }
			/* 2318 */ if (screenType.equals("MakerCancelScreen") &&
			/* 2319 */ !CommonMethods.isValidString(fwdContractVO.getFwdContractNo())) {
				/* 2320 */ setErrorForFWCDetails("FWCNO_MANDATORY", fwdContractVO);
				/*      */ }
			/*      */
			/* 2323 */ if (!CommonMethods.isValidString(treRefNo)
					&& !screenType.equals("MakerCancelScreenWithoutRate")) {
				setErrorForFWCDetails("TRYREFNO_MANDATORY", fwdContractVO);
			}
			/*      */
			/* 2327 */ if (screenType.equals("MakerBookingScreen") && screenType.equals("MakerCancelScreen") &&
			/* 2328 */ !CommonMethods.isValidString(treRefNo) &&
			/* 2329 */ !CommonMethods.isValidString(staticdatavo.getTreasuryRefNo())) {
				/* 2330 */ setErrorForFWCDetails("TRY_TRANSFER", fwdContractVO);
				/*      */ }
			/*      */
			/* 2333 */ if (!screenType.equals("MakerCancelScreen")) {
				/*      */
				/* 2335 */ if (!CommonMethods.isValidString(subProduct)) {
					/* 2336 */ setErrorForFWCDetails("SUBPRODUCT_MANDATORY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2339 */ if (!CommonMethods.isValidString(customerID)) {
					/* 2340 */ setErrorForFWCDetails("CIF_MANDATORY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2343 */ if (!CommonMethods.isValidString(treasuryRate)) {
					/* 2344 */ setErrorForFWCDetails("TRYRATE_MANDATORY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2347 */ if (!CommonMethods.isValidString(branch)) {
					/* 2348 */ setErrorForFWCDetails("BRANCH_MANDATORY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2351 */ if (!CommonMethods.isValidString(bookingDate)) {
					/* 2352 */ setErrorForFWCDetails("BOOKINGDATE_MANDATORY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2355 */ if (!CommonMethods.isValidString(customerAcctNo)) {
					/* 2356 */ setErrorForFWCDetails("ACCTNO_MANDATORY", fwdContractVO);
					/*      */ }
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
			/* 2376 */ if (CommonMethods.isValidString(fwdContractVO.getTransid())) {
				/* 2377 */ String transid = fwdContractVO.getTransid().trim();
				/* 2378 */ logger.info("transid" + transid);
				/*      */ }
			/*      */
			/* 2381 */ if (CommonMethods.isValidString(fwdContractVO.getTransdate())) {
				/* 2382 */ String transdate = fwdContractVO.getTransdate().trim();
				/* 2383 */ logger.info("transdate" + transdate);
				/*      */ }
			/* 2385 */ if (CommonMethods.isValidString(fwdContractVO.getBookingrate())) {
				/* 2386 */ String bookingrate = fwdContractVO.getTransdate().trim();
				/* 2387 */ logger.info("bookingrate:" + bookingrate);
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
			/* 2399 */ if (CommonMethods.isValidString(fwdContractAmtCcy)) {
				/*      */
				/* 2401 */ logger.info("FWC Currency ------------>" + fwdContractAmtCcy);
				/* 2402 */ if (!(fwdContractCcy instanceof String) || fwdContractCcy.trim().equalsIgnoreCase("")) {
					/* 2403 */ setErrorForFWCDetails("DEALCCY_MANDATORY", fwdContractVO);
					/* 2404 */ } else if (!executeGenericQuery("select COUNT(1) from c8pf where TRIM(C8CCY)=?",
							fwdContractCcy)) {
					/* 2405 */ setErrorForFWCDetails("INVALID_CURRENCY", fwdContractVO);
					/*      */ }
				/*      */
				/* 2408 */ if (fwdContractAmt instanceof String && !fwdContractAmt.trim().equalsIgnoreCase("")) {
					/*      */
					/* 2410 */ if (Double.valueOf(fwdContractAmt).doubleValue() <= 0.0D) {
						/* 2411 */ setErrorForFWCDetails("AMOUNT_VALUE", fwdContractVO);
						/*      */ }
					/*      */ } else {
					/* 2414 */ setErrorForFWCDetails("AMOUNT_NULL", fwdContractVO);
					/*      */ }
				/*      */ } else {
				/*      */
				/* 2418 */ setErrorForFWCDetails("AMOUNT_NULL", fwdContractVO);
				/*      */ }
			/*      */
			/*      */
			/*      */
			/* 2423 */ if (CommonMethods.isValidString(customerID) &&
			/* 2424 */ !executeGenericQuery("select COUNT(1) from GFPF  WHERE TRIM(GFCPNC) = ?", customerID)) {
				/* 2425 */ setErrorForFWCDetails("INVALID_CUSTOMER", fwdContractVO);
				/*      */ }
			/*      */
			/* 2440 */ logger.info("Normal Validations--------------");
			/* 2441 */ if (!screenType.equals("MakerCancelScreen") && !screenType.equals("MakerCancelScreenWithoutRate")
					&& CommonMethods.isValidString(treRefNo) &&
					/* 2442 */ CommonMethods.isValidString(customerID) && CommonMethods.isValidString(subProduct)) {
				/*      */
				/* 2444 */ fwdContractVO = fetchDependentTreasuryDetails(fwdContractVO);
				/* 2445 */ String rateStatus = fwdContractVO.getRateStatus();
				/*      */
				/* 2447 */ if (CommonMethods.isValidString(rateStatus) && rateStatus.trim().equalsIgnoreCase("D")) {
					/* 2448 */ setErrorForFWCDetails("FINACLE_SERVICE_DOWN", fwdContractVO);
					/* 2449 */ } else if (CommonMethods.isValidString(rateStatus)
							&& rateStatus.trim().equalsIgnoreCase("F")) {
					/* 2450 */ setErrorForFWCDetails("INVALID_TREASURY_REF_NO", fwdContractVO);
					/*      */ }
				/* 2478 */ else if (CommonMethods.isValidString(rateStatus) &&
				/* 2479 */ rateStatus.trim().equalsIgnoreCase("NoBal")) {
					/* 2480 */ setErrorForFWCDetails("INSUFFICIENT_BALANCE", fwdContractVO);
					/* 2481 */ } else if (CommonMethods.isValidString(rateStatus)
							&& rateStatus.trim().equalsIgnoreCase("S")) {
					/* 2482 */ String rateBuyOrSell = fwdContractVO.getRateBuyOrSell();
					/* 2483 */ if (CommonMethods.isValidString(rateBuyOrSell)) {
						/* 2484 */ if ((rateBuyOrSell.trim().equalsIgnoreCase("B")
								|| rateBuyOrSell.trim().equalsIgnoreCase("P")) &&
						/* 2485 */ subProduct.contains("Sale")) {
							/* 2486 */ setErrorForFWCDetails("INVALID_SUBPRODUCT_S", fwdContractVO);
							/*      */ }
						/* 2488 */ if (rateBuyOrSell.trim().equalsIgnoreCase("S") && subProduct.contains("Purchase")) {
							/* 2489 */ setErrorForFWCDetails("INVALID_SUBPRODUCT_P", fwdContractVO);
							/*      */ }
						/*      */ }
					/*      */ }
				/*      */ }
			/* 2512 */ logger.info("Validation to check available balance--------------");
			/* 2513 */ if ((screenType.equals("MakerBookingScreen") || screenType.equals("MakerCancelScreen")) &&
			/* 2514 */ CommonMethods.isValidString(treRefNo) && CommonMethods.isValidString(customerID) &&
			/* 2515 */ CommonMethods.isValidString(subProduct) || (screenType.equals("MakerCancelScreenWithoutRate") // ←
																														// separate
																														// condition
					&& CommonMethods.isValidString(customerID) // ← no treRefNo needed
					&& CommonMethods.isValidString(subProduct)))

			{
				/* 2516 */ balance = accountBalance.getAccountBalance("0", msgId, "account",
						fwdContractVO.getAcctNumber(), "");
				/* 2517 */ logger.info(
						"Account Balance availiable for account number " + fwdContractVO.getAcctNumber() + " is " +
						/* 2518 */ balance);
				/*      */
				/* 2520 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
					/* 2521 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
					/* 2522 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 2523 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/*      */
				/* 2526 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
					/* 2527 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
					/* 2528 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 2529 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/* 2531 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy)
						&&
						/* 2532 */ CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy) &&
						/* 2533 */ chargeCcy.equalsIgnoreCase(gstCcy))
					/* 2534 */ totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
				/* 2535 */ logger.info("totalAmt :: " + totalAmt);
				/*      */
				/* 2537 */ int balanceCompare = (new BigDecimal(totalAmt)).compareTo(new BigDecimal(balance));
				/*      */
				/* 2539 */ logger.info("balanceCompare :: " + balanceCompare);
				/*      */
				/* 2541 */ if (balanceCompare == 1)
				/*      */ {
					/* 2543 */ fwdContractVO.setRateStatus("AB" + balance.toString());
					/*      */ }
				/*      */
				/* 2546 */ String rateStatus = fwdContractVO.getRateStatus();
				/* 2547 */ if (CommonMethods.isValidString(rateStatus) && rateStatus.trim().contains("AB")) {
					/* 2548 */ logger.info("rate Status--" + rateStatus);
					/* 2549 */ Object[] arg = { Integer.valueOf(0), "E", "Insufficient Balance for customer account " +
							/* 2550 */ fwdContractVO.getAcctNumber() + ". Available balance is:"
							+ rateStatus.substring(2), /* 2551 */ "INPUT" };
					/* 2552 */ CommonMethods.setErrorvalues(arg, this.alertMsgArray);
					/* 2553 */ if (this.alertMsgArray.size() > 0) {
						/* 2554 */ fwdContractVO.setErrorList(this.alertMsgArray);
						/*      */ }
					/*      */ }
				/*      */ }
			/*      */
			/*      */
			// ABHISHEK
			/* 2560 */ logger.info("Validation for deal type--------------");
			if (((screenType.equals("MakerBookingScreen") || screenType.equals("MakerCancelScreen"))
					&& CommonMethods.isValidString(treRefNo) && CommonMethods.isValidString(customerID)
					&& CommonMethods.isValidString(subProduct))
					|| (screenType.equals("MakerCancelScreenWithoutRate") && CommonMethods.isValidString(treRefNo) // only
																													// runs
																													// if
																													// treRefNo
																													// entered
							&& CommonMethods.isValidString(customerID))) {
				HashMap map = checkDealUtilization(treRefNo, fwdContractVO.getCustomerID(),
						fwdContractVO.getFwdContractNo());
				String flag = map.get("errormsg").toString();
				if (flag != null && !"Y".equalsIgnoreCase(flag)) {
					fwdContractVO.setRateStatus(String.valueOf(flag) + map.get("fwcnum"));
				} else {
					fwdContractVO.setRateStatus(flag);
				}
				String rateStatus = fwdContractVO.getRateStatus();
				if (CommonMethods.isValidString(rateStatus) && rateStatus.trim().contains("AU")) {
					String fwc_Status = "";
					String fwc_UtilizedContractNo = "";
					String fwc_StatusNContract = checkFWC_Status(treRefNo, fwdContractVO.getCustomerID(),
							fwdContractVO.getFwdContractNo());
					logger.info("fwc_StatusNContract--" + fwc_StatusNContract);
					String[] fwc_StatusNContractSplit = fwc_StatusNContract.split("-");
					fwc_Status = fwc_StatusNContractSplit[0].toString();
					logger.info("fwc_Status--" + fwc_Status);
					fwc_UtilizedContractNo = fwc_StatusNContractSplit[1].toString();
					logger.info("fwc_UtilizedContractNo--" + fwc_UtilizedContractNo);
					if (!fwc_Status.equalsIgnoreCase("PENDING TO SUBMIT")) {
						logger.info("rate Status--" + rateStatus);
						Object[] arg = { Integer.valueOf(0), "E",
								"Deal already used against Forward Reference Number:" + fwc_UtilizedContractNo,
								"INPUT" };
						CommonMethods.setErrorvalues(arg, this.alertMsgArray);
						if (this.alertMsgArray.size() > 0) {
							fwdContractVO.setErrorList(this.alertMsgArray);
						}
					}
				} else if (CommonMethods.isValidString(rateStatus) && rateStatus.trim().contains("AR")) {
					logger.info("rate Status--" + rateStatus);
					setErrorForFWCDetails("REJECTED_DEAL", fwdContractVO);
				}
			}

			/*      */
			/* 2601 */ if ((screenType.equals("MakerCancelScreen") || screenType.equals("MakerCancelScreenWithoutrate"))
					
					&& CommonMethods.isValidString(treRefNo)) {
				/*      */
				/* 2603 */ String cancelamtccy = fwdContractVO.getOutstandingAmt();
				/* 2604 */ String outstandingamountccy = fwdContractVO.getCancellationamount();
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 2614 */ if (CommonMethods.isValidString(cancelamtccy)
						&& CommonMethods.isValidString(outstandingamountccy)) {
					/* 2615 */ String cancelamt = cancelamtccy.trim().replaceAll("[^0-9.]", "");
					/*      */
					/*      */
					/* 2618 */ String outstandamt = outstandingamountccy.trim().replaceAll("[^0-9.]", "");
					/*      */
					/*      */
					/*      */
					/* 2622 */ BigDecimal outstandamount = (new BigDecimal(outstandamt)).setScale(4,
							RoundingMode.HALF_UP);
					/* 2623 */ BigDecimal cancelamount = (new BigDecimal(cancelamt)).setScale(4, RoundingMode.HALF_UP);
					/*      */
					/* 2625 */ logger.info(/* 2626 */ "outstandingamount.compareTo(cancelamount) : "
							+ outstandamount.compareTo(cancelamount));
					/* 2627 */ if (outstandamount.compareTo(cancelamount) == -1) {
						/* 2628 */ setErrorForFWCDetails("NOT_VALID", /* 2629 */ fwdContractVO);
						/*      */
						/*      */ }
					/*      */ else {
						/*      */
						/*      */
						/* 2635 */ logger.info("Valid amount");
						/*      */ }
					/*      */ }
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
			/* 2651 */ logger.info("fwdContractAmtCcy --> " + fwdContractAmtCcy + " :: toAmtCcy --> " + toAmtCcy
					+ " ::Treasury Rate --> " + treasuryRate);
			/* 2652 */ if (CommonMethods.isValidString(fwdContractAmtCcy) && CommonMethods.isValidString(treasuryRate)
					&&
					/* 2653 */ CommonMethods.isValidString(toAmtCcy)) {
				/* 2654 */ BigDecimal toAmtValue = (new BigDecimal(fwdContractAmt))
						.multiply(new BigDecimal(treasuryRate));
				/*      */
				/* 2656 */ String toCcyAmtValue = toAmtValue + " " + toCcy;
				/* 2657 */ logger.info("ToCcyAmtValue --> " + toCcyAmtValue);
				/* 2658 */ fwdContractVO.setToCurrencyAmt(toCcyAmtValue);
				/*      */ }
			/*      */
			/*      */
			/*      */
			/* 2663 */ if (CommonMethods.isValidString(toCcy) && !toCcy.equalsIgnoreCase("INR") &&
			/* 2664 */ !CommonMethods.isValidString(washRate)) {
				/* 2665 */ logger.info("validate wash rate mandatory for " + toCcy);
				/* 2666 */ setErrorForFWCDetails("WASHRATE_MANDATORY", fwdContractVO);
				/*      */ }
			/*      */
			/*      */
			/*      */
			/* 2671 */ if (CommonMethods.isValidString(chargeAmountCcy) && !CommonMethods.isValidString(gstAmountCcy)) {
				/* 2672 */ setErrorForFWCDetails("INPUT_GST_AMT", fwdContractVO);
				/*      */ }
			/* 2674 */ if (CommonMethods.isValidString(gstAmountCcy) && !CommonMethods.isValidString(chargeAmountCcy)) {
				/* 2675 */ setErrorForFWCDetails("INPUT_CHARGE_AMT", fwdContractVO);
				/*      */ }
			/* 2677 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy) &&
			/* 2678 */ !chargeCcy.equalsIgnoreCase(gstCcy)) {
				/* 2679 */ setErrorForFWCDetails("INVALID_CHARGE_GST_CCY", fwdContractVO);
				/*      */ }
			/* 2681 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy) &&
			/* 2682 */ !CommonMethods.isValidString(customerAcctNo)) {
				/* 2683 */ setErrorForFWCDetails("INPUT_ACCOUNT_NO", fwdContractVO);
				/*      */ }
			/* 2685 */ int accountCheck = checkAccNo(fwdContractVO.getAcctNumber(), fwdContractVO.getCustomerID());
			/* 2686 */ if (accountCheck == 0) {
				/* 2687 */ setErrorForFWCDetails("INVALID_ACCOUNT_NO", fwdContractVO);
				/*      */ }
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 2695 */ if (CommonMethods.isValidString(chargeAmountCcy) && !CommonMethods.isValidString(chargeCcy)) {
				/* 2696 */ setErrorForFWCDetails("CHARGE_CCY_NULL", fwdContractVO);
				/*      */ }
			/* 2698 */ if (CommonMethods.isValidString(gstAmountCcy) && !CommonMethods.isValidString(gstCcy)) {
				/* 2699 */ setErrorForFWCDetails("GST_CCY_NULL", fwdContractVO);
				/*      */ }
			/* 2701 */ if (CommonMethods.isValidString(chargeCcy)
					&& !executeGenericQuery("select COUNT(1) from c8pf where TRIM(C8CCY)=?", chargeCcy.trim())) {
				/* 2702 */ setErrorForFWCDetails("INVALID_CURRENCY", fwdContractVO);
				/*      */ }
			/*      */
			/* 2705 */ if (CommonMethods.isValidString(gstCcy)
					&& !executeGenericQuery("select COUNT(1) from c8pf where TRIM(C8CCY)=?", gstCcy.trim())) {
				/* 2706 */ setErrorForFWCDetails("INVALID_CURRENCY", fwdContractVO);
				/*      */ }
			/*      */
			/* 2709 */ logger.info("Validation ends");
			/*      */ }
		/* 2711 */ catch (Exception e) {
			/* 2712 */ e.printStackTrace();
			/*      */ }
		/* 2714 */ return fwdContractVO;
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
	/*      */ public ForwardContractVO generateFWCPostings(ForwardContractVO fwdContractVO) {
		/* 3066 */ ResultSet rs1 = null;
		/* 3067 */ Statement st1 = null;
		/* 3068 */ LoggableStatement pst = null;
		/* 3069 */ ResultSet rs = null;
		/* 3070 */ Connection con = null;
		/* 3071 */ ArrayList<FWCPostingVO> postingVO = new ArrayList<>();
		/* 3072 */ String query = "SELECT TYPE,ACCOUNT_NUMBER,DR_CR_FLAG,DESCRIPTION FROM CUSTOM_FWC_GL_ACCOUNTS WHERE SUBPRODUCT LIKE '%'||?||'%' AND ACCOUNT_NUMBER IN ('4220013000', 'CustomerAccount')";
		/* 3073 */ String subProduct = "";
		/* 3074 */ String branch = "";
		/* 3075 */ String toAmtCcy = "";
		/* 3076 */ String toCcy = "";
		/* 3077 */ String toCcyToCheck = "";
		/* 3078 */ String toAmount = "";
		/* 3079 */ String chargeAmountCcy = "";
		/* 3080 */ String gstAmountCcy = "";
		/* 3081 */ String chargeAmt = "";
		/* 3082 */ String chargeCcy = "";
		/* 3083 */ String gstAmt = "";
		/* 3084 */ String gstCcy = "";
		/* 3085 */ String customerAcctNo = "";
		/* 3086 */ String totalAmt = "";
		/* 3087 */ String washRate = "";
		/*      */
		/*      */
		/*      */ try {
			/* 3091 */ logger.info("Inside generateFWCPostings ");
			/*      */
			/* 3093 */ if (CommonMethods.isValidString(fwdContractVO.getSubProduct())) {
				/* 3094 */ subProduct = fwdContractVO.getSubProduct().trim();
				/*      */ }
			/* 3096 */ if (CommonMethods.isValidString(fwdContractVO.getBranchCode())) {
				/* 3097 */ branch = fwdContractVO.getBranchCode().trim();
				/*      */ }
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 3106 */ String forwardContractNo = fwdContractVO.getFwdContractNo();
			/* 3107 */ BigDecimal postingamount = new BigDecimal(0);
			/* 3108 */ String treasuryrate = getBookingTreasuryrate(forwardContractNo);
			/* 3109 */ logger.info("treasuryrate:" + treasuryrate);
			/* 3110 */ if (CommonMethods.isValidString(fwdContractVO.getOutstandingAmt()) &&
			/* 3111 */ CommonMethods.isValidString(treasuryrate)) {
				/* 3112 */ toAmtCcy = fwdContractVO.getOutstandingAmt().trim();
				/* 3113 */ logger.info("toAmtCcy:" + toAmtCcy);
				/* 3114 */ toCcy = toAmtCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3115 */ toCcyToCheck = toAmtCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3116 */ logger.info("toCcy:" + toCcy);
				/* 3117 */ toAmount = toAmtCcy.trim().replaceAll("[^0-9.]", "");
				/* 3118 */ logger.info("toAmount:" + toAmount);
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
					/* 3135 */ if (!toCcy.equalsIgnoreCase("INR"))
						/* 3136 */ toCcy = "INR";
					/* 3137 */ } catch (Exception e) {
					/* 3138 */ logger.info(e);
					/*      */ }
				/* 3140 */ postingamount = (new BigDecimal(toAmount)).multiply(new BigDecimal(treasuryrate)).setScale(4,
						/* 3141 */ RoundingMode.HALF_UP);
				/* 3142 */ logger.info("postingMOUNT:" + postingamount + toCcy);
				/*      */ }
			/*      */
			/* 3145 */ logger.info(" postingMOUNT & toCcy -->" + postingamount + " " + toCcy);
			/*      */
			/* 3147 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
				/* 3148 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
				/* 3149 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3150 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 3153 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
				/* 3154 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
				/* 3155 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3156 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 3159 */ if (CommonMethods.isValidString(fwdContractVO.getAcctNumber())) {
				/* 3160 */ customerAcctNo = fwdContractVO.getAcctNumber().trim();
				/*      */ }
			/* 3162 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy) &&
			/* 3163 */ CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy) &&
			/* 3164 */ chargeCcy.equalsIgnoreCase(gstCcy)) {
				/* 3165 */ totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
				/*      */ }
			/* 3167 */ if (CommonMethods.isValidString(fwdContractVO.getWashRate())) {
				/* 3168 */ washRate = fwdContractVO.getWashRate().trim();
				/*      */ }
			/* 3170 */ logger.info(" Wash rate is : " + washRate);
			/* 3171 */ if (CommonMethods.isValidString(toCcyToCheck) && !toCcyToCheck.equalsIgnoreCase("INR") &&
			/* 3172 */ CommonMethods.isValidString(washRate)) {
				/* 3173 */ toAmount = CommonMethods.getEquivalentINRAmount("INR", toAmount, washRate);
				/* 3174 */ logger.info(" toAmount * washrate -->" + postingamount);
				/* 3175 */ postingamount = (new BigDecimal(
						/* 3176 */ CommonMethods.getEquivalentINRAmount("INR", postingamount.toString(), washRate)))
								.setScale(4, /* 3177 */ RoundingMode.HALF_UP);
				/* 3178 */ logger.info(" postingamount * washrate -->" + postingamount);
				/* 3179 */ toCcy = "INR";
				/*      */ }
			/*      */
			/* 3182 */ if (CommonMethods.isValidString(chargeCcy) && !chargeCcy.equalsIgnoreCase("INR") &&
			/* 3183 */ CommonMethods.isValidString(washRate)) {
				/* 3184 */ totalAmt = CommonMethods.getEquivalentINRAmount("INR", totalAmt, washRate);
				/* 3185 */ chargeCcy = "INR";
				/*      */ }
			/*      */
			/* 3188 */ if (CommonMethods.isValidString(totalAmt) && CommonMethods.isValidString(customerAcctNo)) {
				/* 3189 */ query = String.valueOf(query) + " OR TYPE LIKE '%Charges%' ";
				/*      */ }
			/* 3191 */ logger.info(" subProduct -->" + subProduct);
			/* 3192 */ String tiSystemDate = CommonMethods.getTISystemDate();
			/*      */
			/* 3194 */ con = DBConnectionUtility.getZoneConnection();
			/* 3195 */ pst = new LoggableStatement(con, query);
			/* 3196 */ pst.setString(1, subProduct);
			/* 3197 */ logger.info(pst.getQueryString());
			/* 3198 */ rs = pst.executeQuery();
			/*      */
			/* 3200 */ while (rs.next()) {
				/*      */
				/* 3202 */ FWCPostingVO fwcPostingVO = new FWCPostingVO();
				/*      */
				/* 3204 */ if (rs.getString("ACCOUNT_NUMBER").equalsIgnoreCase("CustomerAccount")) {
					/* 3205 */ fwcPostingVO.setPostingAcctNumber(customerAcctNo);
					/*      */ } else {
					/* 3207 */ fwcPostingVO
							.setPostingAcctNumber(String.valueOf(branch) + rs.getString("ACCOUNT_NUMBER"));
					/*      */ }
				/* 3209 */ fwcPostingVO.setPostingDrCrFlag(rs.getString("DR_CR_FLAG"));
				/*      */
				/* 3211 */ if (rs.getString("TYPE").equalsIgnoreCase("Charges")) {
					/* 3212 */ fwcPostingVO.setPostingAmountCcy(String.valueOf(totalAmt) + " " + chargeCcy);
					/*      */
					/*      */
					/*      */
					/*      */ }
				/* 3217 */ else if (rs.getString("TYPE").equalsIgnoreCase("Charges") && postingamount != null) {
					/* 3218 */ fwcPostingVO.setPostingAmountCcy(postingamount + " " + toCcy);
					/*      */ } else {
					/* 3220 */ logger.info("toAmount & toCcy:" + postingamount + toCcy);
					/* 3221 */ fwcPostingVO.setPostingAmountCcy(postingamount + " " + toCcy);
					/*      */ }
				/*      */
				/* 3224 */ fwcPostingVO.setPostingValueDate(tiSystemDate);
				/*      */
				/* 3226 */ if (rs.getString("DESCRIPTION").equalsIgnoreCase("Customer Account")) {
					/*      */
					/* 3228 */ String acctName = null;
					/* 3229 */ st1 = con.createStatement();
					/* 3230 */ String query1 = "SELECT cast(SHORTNAME as varchar2(15)) AS SHORTNAME FROM ACCOUNT WHERE  CURRENCY = 'INR' AND BO_ACCTNO='"
							+ customerAcctNo + "'";
					/* 3231 */ rs1 = st1.executeQuery(query1);
					/* 3232 */ if (rs1.next()) {
						/* 3233 */ acctName = rs1.getString("SHORTNAME");
						/* 3234 */ logger.info(" Account Name " + acctName);
						/*      */ }
					/*      */
					/* 3237 */ if (acctName.equalsIgnoreCase("") || acctName.equalsIgnoreCase("null")
							|| acctName == null) {
						/* 3238 */ fwcPostingVO.setPostingDesc(rs.getString("DESCRIPTION"));
						/*      */ } else {
						/*      */
						/* 3241 */ fwcPostingVO.setPostingDesc(acctName);
						/*      */ }
					/*      */ } else {
					/*      */
					/* 3245 */ fwcPostingVO.setPostingDesc(rs.getString("DESCRIPTION"));
					/*      */ }
				/*      */
				/*      */
				/* 3249 */ postingVO.add(fwcPostingVO);
				/*      */ }
			/*      */
			/*      */
			/* 3253 */ logger.info(" postingVO size in generateFWCPostings -->" + postingVO.size());
			/*      */
			/* 3255 */ if (postingVO.size() > 0) {
				/* 3256 */ fwdContractVO.setPostingList(postingVO);
				/*      */ }
			/*      */ }
		/* 3259 */ catch (Exception e) {
			/* 3260 */ e.printStackTrace();
			/*      */ } finally {
			/* 3262 */ DBConnectionUtility.surrenderDB(null, st1, rs1);
			/* 3263 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 3265 */ logger.info("Exiting Method");
		/* 3266 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public int checkAccNo(String customerAcctNo, String customerId) {
		/* 3271 */ logger.info("Entering Method");
		/* 3272 */ int cnt = 0;
		/* 3273 */ Connection con = null;
		/* 3274 */ ResultSet rs1 = null;
		/* 3275 */ Statement st1 = null;
		/*      */ try {
			/* 3277 */ con = DBConnectionUtility.getZoneConnection();
			/* 3278 */ st1 = con.createStatement();
			/* 3279 */ String query1 = "SELECT count(*) AS CNT FROM ACCOUNT WHERE TRIM(BO_ACCTNO)='" + customerAcctNo
					+ "' AND TRIM(CUS_MNM)='" + customerId + "'";
			/* 3280 */ rs1 = st1.executeQuery(query1);
			/* 3281 */ if (rs1.next()) {
				/* 3282 */ cnt = rs1.getInt("CNT");
				/*      */ }
			/* 3284 */ logger.info(" Account Cnt " + cnt);
			/* 3285 */ } catch (Exception e) {
			/* 3286 */ e.printStackTrace();
			/*      */ } finally {
			/* 3288 */ DBConnectionUtility.surrenderDB(con, st1, rs1);
			/*      */ }
		/* 3290 */ logger.info("Exiting Method");
		/* 3291 */ return cnt;
		/*      */ }

	/*      */
	/*      */ public ForwardContractVO getFWCPostingsToReverse(ForwardContractVO fwdContractVO) {
		/* 3295 */ LoggableStatement pst = null;
		/* 3296 */ ResultSet rs = null;
		/* 3297 */ Connection con = null;
		/* 3298 */ ArrayList<FWCPostingVO> postingVO = new ArrayList<>();
		/* 3299 */ String query = "SELECT TYPE,ACCOUNT_NUMBER,DR_CR_FLAG,DESCRIPTION FROM CUSTOM_FWC_GL_ACCOUNTS WHERE SUBPRODUCT LIKE '%'||?||'%' AND ACCOUNT_NUMBER IN ('4220013000', 'CustomerAccount')";
		/* 3300 */ String subProduct = "";
		/* 3301 */ String branch = "";
		/* 3302 */ String toAmtCcy = "";
		/* 3303 */ String toCcy = "";
		/* 3304 */ String toAmount = "";
		/* 3305 */ String chargeAmountCcy = "";
		/* 3306 */ String gstAmountCcy = "";
		/* 3307 */ String chargeAmt = "";
		/* 3308 */ String chargeCcy = "";
		/* 3309 */ String gstAmt = "";
		/* 3310 */ String gstCcy = "";
		/* 3311 */ String customerAcctNo = "";
		/* 3312 */ String totalAmt = "";
		/* 3313 */ String washRate = "";
		/*      */
		/*      */
		/*      */ try {
			/* 3317 */ logger.info(" get FWCPostings To Reverse ");
			/* 3318 */ if (CommonMethods.isValidString(fwdContractVO.getSubProduct())) {
				/* 3319 */ subProduct = fwdContractVO.getSubProduct().trim();
				/*      */ }
			/* 3321 */ if (CommonMethods.isValidString(fwdContractVO.getBranchCode())) {
				/* 3322 */ branch = fwdContractVO.getBranchCode().trim();
				/*      */ }
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 3332 */ String forwardContractNo = fwdContractVO.getFwdContractNo();
			/* 3333 */ BigDecimal postingamount = new BigDecimal(0);
			/* 3334 */ String treasuryrate = getBookingTreasuryrate(forwardContractNo);
			/* 3335 */ logger.info("treasuryrate:" + treasuryrate);
			/* 3336 */ if (CommonMethods.isValidString(fwdContractVO.getOutstandingAmt()) &&
			/* 3337 */ CommonMethods.isValidString(treasuryrate)) {
				/* 3338 */ toAmtCcy = fwdContractVO.getOutstandingAmt().trim();
				/* 3339 */ logger.info("toAmtCcy:" + toAmtCcy);
				/* 3340 */ toCcy = toAmtCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3341 */ logger.info("toCcy:" + toCcy);
				/* 3342 */ toAmount = toAmtCcy.trim().replaceAll("[^0-9.]", "");
				/* 3343 */ logger.info("toAmount:" + toAmount);
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */ try {
					/* 3350 */ if (!toCcy.equalsIgnoreCase("INR"))
						/* 3351 */ toCcy = "INR";
					/* 3352 */ } catch (Exception e) {
					/* 3353 */ logger.info(e);
					/*      */ }
				/* 3355 */ postingamount = (new BigDecimal(toAmount)).multiply(new BigDecimal(treasuryrate)).setScale(4,
						/* 3356 */ RoundingMode.HALF_UP);
				/*      */
				/* 3358 */ logger.info("postingMOUNT:" + postingamount + toCcy);
				/*      */ }
			/*      */
			/* 3361 */ logger.info(" toAmount & toCcy -->" + postingamount + " " + toCcy);
			/*      */
			/* 3363 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
				/* 3364 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
				/* 3365 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3366 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 3369 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
				/* 3370 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
				/* 3371 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
				/* 3372 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
				/*      */ }
			/*      */
			/* 3375 */ if (CommonMethods.isValidString(fwdContractVO.getAcctNumber())) {
				/* 3376 */ customerAcctNo = fwdContractVO.getAcctNumber().trim();
				/*      */ }
			/* 3378 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy) &&
			/* 3379 */ CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy) &&
			/* 3380 */ chargeCcy.equalsIgnoreCase(gstCcy)) {
				/* 3381 */ totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
				/*      */ }
			/* 3383 */ if (CommonMethods.isValidString(fwdContractVO.getWashRate())) {
				/* 3384 */ washRate = fwdContractVO.getWashRate().trim();
				/*      */ }
			/* 3386 */ if (CommonMethods.isValidString(toCcy) && !toCcy.equalsIgnoreCase("INR") &&
			/* 3387 */ CommonMethods.isValidString(washRate)) {
				/* 3388 */ toAmount = CommonMethods.getEquivalentINRAmount("INR", toAmount, washRate);
				/* 3389 */ toCcy = "INR";
				/*      */ }
			/* 3391 */ if (CommonMethods.isValidString(toCcy) && !toCcy.equalsIgnoreCase("INR") &&
			/* 3392 */ CommonMethods.isValidString(washRate))
			/*      */ {
				/*      */
				/* 3395 */ toCcy = "INR";
				/*      */ }
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/*      */
			/* 3405 */ if (CommonMethods.isValidString(toCcy) && !toCcy.equalsIgnoreCase("INR") &&
			/* 3406 */ CommonMethods.isValidString(treasuryrate)) {
				/* 3407 */ logger.info("To Amount and Currency:" + toCcy + " " + toAmount);
				/* 3408 */ toAmount = CommonMethods.getEquivalentINRAmount("INR", String.valueOf(toAmount),
						treasuryrate);
				/*      */
				/* 3410 */ toCcy = "INR";
				/*      */ }
			/*      */
			/* 3413 */ if (CommonMethods.isValidString(totalAmt) && CommonMethods.isValidString(customerAcctNo)) {
				/* 3414 */ query = String.valueOf(query) + " OR TYPE LIKE '%Charges%' ";
				/*      */ }
			/* 3416 */ logger.info(" subProduct -->" + subProduct);
			/* 3417 */ String tiSystemDate = CommonMethods.getTISystemDate();
			/*      */
			/* 3419 */ con = DBConnectionUtility.getZoneConnection();
			/* 3420 */ pst = new LoggableStatement(con, query);
			/* 3421 */ pst.setString(1, subProduct);
			/* 3422 */ logger.info(pst.getQueryString());
			/* 3423 */ rs = pst.executeQuery();
			/*      */
			/* 3425 */ while (rs.next()) {
				/*      */
				/* 3427 */ FWCPostingVO fwcPostingVO = new FWCPostingVO();
				/*      */
				/* 3429 */ if (rs.getString("ACCOUNT_NUMBER").equalsIgnoreCase("CustomerAccount")) {
					/* 3430 */ fwcPostingVO.setPostingAcctNumber(customerAcctNo);
					/*      */ } else {
					/* 3432 */ fwcPostingVO
							.setPostingAcctNumber(String.valueOf(branch) + rs.getString("ACCOUNT_NUMBER"));
					/*      */ }
				/* 3434 */ String drCrFlag = rs.getString("DR_CR_FLAG");
				/*      */
				/* 3436 */ if (drCrFlag.equalsIgnoreCase("D") && !rs.getString("TYPE").equalsIgnoreCase("Charges")) {
					/* 3437 */ drCrFlag = "C";
					/* 3438 */ } else if (drCrFlag.equalsIgnoreCase("C")
							&& !rs.getString("TYPE").equalsIgnoreCase("Charges")) {
					/* 3439 */ drCrFlag = "D";
					/*      */ }
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 3448 */ fwcPostingVO.setPostingDrCrFlag(drCrFlag);
				/*      */
				/* 3450 */ if (rs.getString("TYPE").equalsIgnoreCase("Charges")) {
					/* 3451 */ logger.info("total amount & charge currency:" + totalAmt + chargeCcy);
					/* 3452 */ fwcPostingVO.setPostingAmountCcy(String.valueOf(totalAmt) + " " + chargeCcy);
					/* 3453 */ } else if (rs.getString("TYPE").equalsIgnoreCase("Charges") && postingamount != null) {
					/* 3454 */ fwcPostingVO.setPostingAmountCcy(postingamount + " " + toCcy);
					/*      */ } else {
					/* 3456 */ logger.info("toAmount & toCcy:" + postingamount + toCcy);
					/* 3457 */ fwcPostingVO.setPostingAmountCcy(postingamount + " " + toCcy);
					/*      */ }
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 3466 */ fwcPostingVO.setPostingValueDate(tiSystemDate);
				/* 3467 */ fwcPostingVO.setPostingDesc(rs.getString("DESCRIPTION"));
				/*      */
				/* 3469 */ postingVO.add(fwcPostingVO);
				/*      */ }
			/*      */
			/*      */
			/* 3473 */ logger.info(" postingVO size in getFWCPostingsToReverse -->" + postingVO.size());
			/*      */
			/* 3475 */ if (postingVO.size() > 0) {
				/* 3476 */ fwdContractVO.setPostingList(postingVO);
				/*      */ }
			/*      */ }
		/* 3479 */ catch (Exception e) {
			/* 3480 */ e.printStackTrace();
			/*      */ } finally {
			/* 3482 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/* 3484 */ logger.info("Exiting Method");
		/* 3485 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public ArrayList<ForwardContractVO> fetchFwdContractDetails(ForwardContractVO fwdContractVO)
			throws DAOException {
		/* 3495 */ logger.info("Entering Method");
		/* 3496 */ Connection con = null;
		/* 3497 */ LoggableStatement loggableStatement = null;
		/* 3498 */ ResultSet rs = null;
		/* 3499 */ String sqlQuery = null;
		/* 3500 */ ArrayList<ForwardContractVO> fwdContractList = null;
		/* 3501 */ String query = null;
		/* 3502 */ String fwdContractNo = null;
		/* 3503 */ int setValue = 0;
		/* 3504 */ boolean fwdContractNoFlag = false;
		/* 3505 */ boolean custIDFlag = false;
		/* 3506 */ boolean subProductFlag = false;
		/* 3507 */ boolean branchFlag = false;
		/* 3508 */ boolean fwdContractAmtFlag = false;
		/* 3509 */ boolean bookingDateFlag = false;
		/* 3510 */ boolean acctNoFlag = false;
		/* 3511 */ boolean statusFlag = false;
		/* 3512 */ boolean dealCcyFlag = false;
		/*      */
		/*      */
		/*      */ try {
			/* 3516 */ fwdContractList = new ArrayList<>();
			/*      */
			/* 3518 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 3519 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 3520 */ String userID = request.getRemoteUser();
			/*      */
			/* 3522 */ if (userID == null) {
				/* 3523 */ userID = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 3526 */ logger.info("User ID-----------" + userID);
			/*      */
			/* 3528 */ query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY,FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,STATUS FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL ";
			/*      */
			/* 3530 */ fwdContractNo = fwdContractVO.getFwdContractNo();
			/* 3531 */ String subProduct = fwdContractVO.getSubProduct();
			/* 3532 */ String custID = fwdContractVO.getCustomerID();
			/* 3533 */ String branch = fwdContractVO.getBranchCode();
			/* 3534 */ String acctNo = fwdContractVO.getAcctNumber();
			/* 3535 */ String fwdContractAmt = fwdContractVO.getFwdContractAmt();
			/* 3536 */ String dealCcy = fwdContractVO.getDealCurrency();
			/* 3537 */ String bookingDate = fwdContractVO.getBookingDate();
			/* 3538 */ String status = fwdContractVO.getStatus();
			/*      */
			/* 3540 */ if (fwdContractVO != null) {
				/* 3541 */ if (!CommonMethods.isNull(fwdContractNo)) {
					/* 3542 */ query = String.valueOf(query) + " AND FWC_CONTRACT_NO LIKE '%'||?||'%'";
					/* 3543 */ fwdContractNoFlag = true;
					/*      */ }
				/*      */
				/* 3546 */ if (!CommonMethods.isNull(subProduct)) {
					/* 3547 */ query = String.valueOf(query) + " AND SUB_PRODUCT LIKE ?";
					/* 3548 */ subProductFlag = true;
					/*      */ }
				/*      */
				/* 3551 */ if (!CommonMethods.isNull(custID)) {
					/* 3552 */ query = String.valueOf(query) + " AND CIF_ID LIKE '%'||?||'%'";
					/* 3553 */ custIDFlag = true;
					/*      */ }
				/*      */
				/* 3556 */ if (!CommonMethods.isNull(branch)) {
					/* 3557 */ query = String.valueOf(query) + " AND BRANCH LIKE '%'||?||'%'";
					/* 3558 */ branchFlag = true;
					/*      */ }
				/*      */
				/* 3561 */ if (!CommonMethods.isNull(acctNo)) {
					/* 3562 */ query = String.valueOf(query) + " AND ACCT_NUMBER LIKE '%'||?||'%'";
					/* 3563 */ acctNoFlag = true;
					/*      */ }
				/*      */
				/* 3566 */ if (!CommonMethods.isNull(fwdContractAmt)) {
					/* 3567 */ query = String.valueOf(query) + " AND FWC_AMOUNT LIKE '%'||?||'%'";
					/* 3568 */ fwdContractAmtFlag = true;
					/*      */ }
				/*      */
				/* 3571 */ if (!CommonMethods.isNull(dealCcy)) {
					/* 3572 */ query = String.valueOf(query) + " AND DEAL_CCY LIKE '%'||?||'%'";
					/* 3573 */ dealCcyFlag = true;
					/*      */ }
				/*      */
				/* 3576 */ if (!CommonMethods.isNull(bookingDate)) {
					/* 3577 */ query = String.valueOf(query)
							+ " AND TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') LIKE '%'||?||'%'";
					/* 3578 */ bookingDateFlag = true;
					/*      */ }
				/*      */
				/* 3581 */ if (!CommonMethods.isNull(status)) {
					/* 3582 */ query = String.valueOf(query) + " AND STATUS LIKE '%'||?||'%'";
					/* 3583 */ statusFlag = true;
					/*      */ }
				/*      */ }
			/* 3586 */ query = String.valueOf(query) + " ORDER BY ID DESC";
			/*      */
			/* 3588 */ con = DBConnectionUtility.getZoneConnection();
			/* 3589 */ loggableStatement = new LoggableStatement(con, query);
			/*      */
			/* 3591 */ if (fwdContractNoFlag) {
				/* 3592 */ loggableStatement.setString(++setValue, fwdContractNo.trim());
				/*      */ }
			/* 3594 */ if (custIDFlag) {
				/* 3595 */ loggableStatement.setString(++setValue, custID.trim());
				/*      */ }
			/* 3597 */ if (subProductFlag) {
				/* 3598 */ loggableStatement.setString(++setValue, subProduct.trim());
				/*      */ }
			/* 3600 */ if (fwdContractAmtFlag) {
				/* 3601 */ loggableStatement.setString(++setValue, fwdContractAmt.trim());
				/*      */ }
			/* 3603 */ if (bookingDateFlag) {
				/* 3604 */ loggableStatement.setString(++setValue, bookingDate.trim());
				/*      */ }
			/* 3606 */ if (acctNoFlag) {
				/* 3607 */ loggableStatement.setString(++setValue, acctNo.trim());
				/*      */ }
			/* 3609 */ if (branchFlag) {
				/* 3610 */ loggableStatement.setString(++setValue, branch.trim());
				/*      */ }
			/* 3612 */ if (dealCcyFlag) {
				/* 3613 */ loggableStatement.setString(++setValue, dealCcy.trim());
				/*      */ }
			/* 3615 */ if (statusFlag) {
				/* 3616 */ loggableStatement.setString(++setValue, status.trim());
				/*      */ }
			/*      */
			/* 3619 */ logger.info("RetriveDetailsFrom FWC: " + loggableStatement.getQueryString());
			/*      */
			/* 3621 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 3623 */ while (rs.next())
			/*      */ {
				/* 3625 */ fwdContractVO = new ForwardContractVO();
				/* 3626 */ fwdContractVO.setId(rs.getString("ID"));
				/* 3627 */ fwdContractVO.setCategory(rs.getString("CATEGORY"));
				/* 3628 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));
				/* 3629 */ fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));
				/* 3630 */ fwdContractVO.setCustomerID(rs.getString("CIF_ID"));
				/* 3631 */ fwdContractVO.setBranchCode(rs.getString("BRANCH"));
				/* 3632 */ fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));
				/* 3633 */ fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));
				/* 3634 */ fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));
				/* 3635 */ fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));
				/* 3636 */ fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));
				/* 3637 */ fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));
				/* 3638 */ fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));
				/* 3639 */ fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));
				/* 3640 */ fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));
				/* 3641 */ fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));
				/* 3642 */ fwdContractVO.setStatus(rs.getString("STATUS"));
				/* 3643 */ fwdContractList.add(fwdContractVO);
				/*      */ }
			/*      */
			/*      */ }
		/* 3647 */ catch (Exception e) {
			/* 3648 */ e.printStackTrace();
			/*      */ } finally {
			/* 3650 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 3653 */ logger.info("Exiting Method");
		/* 3654 */ return fwdContractList;
		/*      */ }

	// ABHISEHK CHECKER

	public ArrayList<ForwardContractVO> fetchFwdContractDetailsWithoutRate(ForwardContractVO fwdContractVO)

			throws DAOException {
		logger.info("Entering Method");

		Connection con = null;

		LoggableStatement loggableStatement = null;

		ResultSet rs = null;

		String query = null;

		String fwdContractNo = null;

		int setValue = 0;

		boolean fwdContractNoFlag = false;

		boolean custIDFlag = false;

		boolean subProductFlag = false;

		boolean branchFlag = false;

		boolean fwdContractAmtFlag = false;

		boolean bookingDateFlag = false;

		boolean acctNoFlag = false;

		boolean statusFlag = false;

		boolean dealCcyFlag = false;

		ArrayList<ForwardContractVO> fwdContractList = null;

		try {

			fwdContractList = new ArrayList<>();

			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()

					.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

			String userID = request.getRemoteUser();

			if (userID == null) {

				userID = "SUPERVISOR";

			}
			logger.info("User ID-----------" + userID);

			// Only change vs original: added CATEGORY filter to restrict to without-rate
			// records

			query = "SELECT ID,CATEGORY,FWC_CONTRACT_NO,SUB_PRODUCT,CIF_ID,BRANCH,ACCT_NUMBER,DEAL_CCY,FWC_AMOUNT,TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') AS BOOKING_DATE,TO_CCY_AMT,TO_CHAR(TO_DATE(DEAL_VALID_FROM,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_FROM,TO_CHAR(TO_DATE(DEAL_VALID_TO,'DD/MM/YY'),'dd/mm/YYYY') AS DEAL_VALID_TO,TREASURY_REF_NO,TREASURY_RATE,OUTSTANDING_AMT,STATUS FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO IS NOT NULL AND CATEGORY IN ('FWCCANCEL','FWCUTIL','FWCBOOK') ";

			fwdContractNo = fwdContractVO.getFwdContractNo();

			String subProduct = fwdContractVO.getSubProduct();

			String custID = fwdContractVO.getCustomerID();

			String branch = fwdContractVO.getBranchCode();

			String acctNo = fwdContractVO.getAcctNumber();

			String fwdContractAmt = fwdContractVO.getFwdContractAmt();

			String dealCcy = fwdContractVO.getDealCurrency();

			String bookingDate = fwdContractVO.getBookingDate();

			String status = fwdContractVO.getStatus();

			if (fwdContractVO != null) {

				if (!CommonMethods.isNull(fwdContractNo)) {

					query = query + " AND FWC_CONTRACT_NO LIKE '%'||?||'%'";

					fwdContractNoFlag = true;

				}

				if (!CommonMethods.isNull(subProduct)) {

					query = query + " AND SUB_PRODUCT LIKE ?";

					subProductFlag = true;

				}

				if (!CommonMethods.isNull(custID)) {

					query = query + " AND CIF_ID LIKE '%'||?||'%'";

					custIDFlag = true;

				}

				if (!CommonMethods.isNull(branch)) {

					query = query + " AND BRANCH LIKE '%'||?||'%'";

					branchFlag = true;

				}

				if (!CommonMethods.isNull(acctNo)) {

					query = query + " AND ACCT_NUMBER LIKE '%'||?||'%'";

					acctNoFlag = true;

				}

				if (!CommonMethods.isNull(fwdContractAmt)) {

					query = query + " AND FWC_AMOUNT LIKE '%'||?||'%'";

					fwdContractAmtFlag = true;

				}

				if (!CommonMethods.isNull(dealCcy)) {

					query = query + " AND DEAL_CCY LIKE '%'||?||'%'";

					dealCcyFlag = true;

				}

				if (!CommonMethods.isNull(bookingDate)) {

					query = query + " AND TO_CHAR(TO_DATE(BOOKING_DATE,'DD/MM/YY'),'dd/mm/YYYY') LIKE '%'||?||'%'";

					bookingDateFlag = true;

				}

				if (!CommonMethods.isNull(status)) {

					query = query + " AND STATUS LIKE '%'||?||'%'";

					statusFlag = true;

				}

			}

			query = query + " ORDER BY ID DESC";

			con = DBConnectionUtility.getZoneConnection();

			loggableStatement = new LoggableStatement(con, query);

			if (fwdContractNoFlag)
				loggableStatement.setString(++setValue, fwdContractNo.trim());

			if (custIDFlag)
				loggableStatement.setString(++setValue, custID.trim());

			if (subProductFlag)
				loggableStatement.setString(++setValue, subProduct.trim());

			if (fwdContractAmtFlag)
				loggableStatement.setString(++setValue, fwdContractAmt.trim());

			if (bookingDateFlag)
				loggableStatement.setString(++setValue, bookingDate.trim());

			if (acctNoFlag)
				loggableStatement.setString(++setValue, acctNo.trim());

			if (branchFlag)
				loggableStatement.setString(++setValue, branch.trim());

			if (dealCcyFlag)
				loggableStatement.setString(++setValue, dealCcy.trim());

			if (statusFlag)
				loggableStatement.setString(++setValue, status.trim());
			logger.info("RetriveDetailsFrom FWC WithoutRate: " + loggableStatement.getQueryString());

			rs = loggableStatement.executeQuery();

			while (rs.next()) {

				fwdContractVO = new ForwardContractVO();

				fwdContractVO.setId(rs.getString("ID"));

				fwdContractVO.setCategory(rs.getString("CATEGORY"));

				fwdContractVO.setFwdContractNo(rs.getString("FWC_CONTRACT_NO"));

				fwdContractVO.setSubProduct(rs.getString("SUB_PRODUCT"));

				fwdContractVO.setCustomerID(rs.getString("CIF_ID"));

				fwdContractVO.setBranchCode(rs.getString("BRANCH"));

				fwdContractVO.setAcctNumber(rs.getString("ACCT_NUMBER"));

				fwdContractVO.setDealCurrency(rs.getString("DEAL_CCY"));

				fwdContractVO.setFwdContractAmt(rs.getString("FWC_AMOUNT"));

				fwdContractVO.setBookingDate(rs.getString("BOOKING_DATE"));

				fwdContractVO.setToCurrencyAmt(rs.getString("TO_CCY_AMT"));

				fwdContractVO.setDealValidFromDate(rs.getString("DEAL_VALID_FROM"));

				fwdContractVO.setDealValidToDate(rs.getString("DEAL_VALID_TO"));

				fwdContractVO.setTreasuryRefNo(rs.getString("TREASURY_REF_NO"));

				fwdContractVO.setTreasuryRate(rs.getString("TREASURY_RATE"));

				fwdContractVO.setOutstandingAmt(rs.getString("OUTSTANDING_AMT"));

				fwdContractVO.setStatus(rs.getString("STATUS"));

				fwdContractList.add(fwdContractVO);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);

		}
		logger.info("Exiting Method");

		return fwdContractList;

	}

	/*      */
	/*      */
	/*      */
	/*      */ public ArrayList<ForwardContractVO> fetchFwdContractEnquiryDetails(ForwardContractVO fwdContractVO)
			throws DAOException {
		/* 3660 */ logger.info("Entering Method");
		/* 3661 */ Connection con = null;
		/* 3662 */ LoggableStatement loggableStatement = null;
		/* 3663 */ ResultSet rs = null;
		/* 3664 */ String sqlQuery = null;
		/* 3665 */ ArrayList<ForwardContractVO> fwdContractList = null;
		/* 3666 */ String query = null;
		/* 3667 */ String fwdContractNo = null;
		/* 3668 */ int setValue = 0;
		/* 3669 */ boolean fwdContractNoFlag = false;
		/* 3670 */ boolean custIDFlag = false;
		/* 3671 */ boolean branchFlag = false;
		/* 3672 */ boolean startDateFlag = false;
		/* 3673 */ boolean endDateFlag = false;
		/*      */
		/*      */
		/*      */ try {
			/* 3677 */ fwdContractList = new ArrayList<>();
			/*      */
			/* 3679 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 3680 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 3681 */ String userID = request.getRemoteUser();
			/*      */
			/* 3683 */ if (userID == null) {
				/* 3684 */ userID = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 3687 */ logger.info("User ID-----------" + userID);
			/*      */
			/* 3689 */ query = "SELECT * FROM CUSTOM_FWC_ENQ_DETAILS_VIEW WHERE FWC_REF_NUM IS NOT NULL AND COUNTERPARTY_STRING IS NOT NULL ";
			/*      */
			/* 3691 */ fwdContractNo = fwdContractVO.getFwdContractNo();
			/* 3692 */ String custID = fwdContractVO.getCustomerID();
			/* 3693 */ String branch = fwdContractVO.getBranchCode();
			/* 3694 */ String validFromDate = fwdContractVO.getValidFrom();
			/* 3695 */ String validToDate = fwdContractVO.getValidTo();
			/*      */
			/* 3697 */ if (fwdContractVO != null) {
				/*      */
				/* 3699 */ if (!CommonMethods.isNull(fwdContractNo)) {
					/* 3700 */ query = String.valueOf(query) + " AND FWC_REF_NUM LIKE '%'||?||'%'";
					/* 3701 */ fwdContractNoFlag = true;
					/*      */ }
				/*      */
				/* 3704 */ if (!CommonMethods.isNull(custID)) {
					/* 3705 */ query = String.valueOf(query) + " AND COUNTERPARTY_STRING LIKE '%'||?||'%'";
					/* 3706 */ custIDFlag = true;
					/*      */ }
				/*      */
				/* 3709 */ if (!CommonMethods.isNull(branch)) {
					/* 3710 */ query = String.valueOf(query) + " AND SOL_ID LIKE '%'||?||'%'";
					/* 3711 */ branchFlag = true;
					/*      */ }
				/*      */
				/* 3714 */ if (!CommonMethods.isNull(validFromDate)) {
					/* 3715 */ query = String.valueOf(query)
							+ " AND TO_CHAR(TO_DATE(START_DATE,'DD/MM/YY'),'dd/mm/YYYY') LIKE '%'||?||'%'";
					/* 3716 */ startDateFlag = true;
					/*      */ }
				/*      */
				/* 3719 */ if (!CommonMethods.isNull(validToDate)) {
					/* 3720 */ query = String.valueOf(query)
							+ " AND TO_CHAR(TO_DATE(END_DATE,'DD/MM/YY'),'dd/mm/YYYY') LIKE '%'||?||'%'";
					/* 3721 */ endDateFlag = true;
					/*      */ }
				/*      */ }
			/*      */
			/* 3725 */ query = String.valueOf(query) + " ORDER BY FWC_REF_NUM DESC";
			/*      */
			/* 3727 */ con = DBConnectionUtility.getZoneConnection();
			/* 3728 */ loggableStatement = new LoggableStatement(con, query);
			/*      */
			/* 3730 */ if (fwdContractNoFlag) {
				/* 3731 */ loggableStatement.setString(++setValue, fwdContractNo.trim());
				/*      */ }
			/* 3733 */ if (custIDFlag) {
				/* 3734 */ loggableStatement.setString(++setValue, custID.trim());
				/*      */ }
			/* 3736 */ if (startDateFlag) {
				/* 3737 */ loggableStatement.setString(++setValue, validFromDate.trim());
				/*      */ }
			/* 3739 */ if (endDateFlag) {
				/* 3740 */ loggableStatement.setString(++setValue, validToDate.trim());
				/*      */ }
			/* 3742 */ if (branchFlag) {
				/* 3743 */ loggableStatement.setString(++setValue, branch.trim());
				/*      */ }
			/*      */
			/* 3746 */ logger.info("Retrive enquiry Details From FWC: " + loggableStatement.getQueryString());
			/*      */
			/* 3748 */ rs = loggableStatement.executeQuery();
			/*      */
			/* 3750 */ while (rs.next())
			/*      */ {
				/* 3752 */ fwdContractVO = new ForwardContractVO();
				/* 3753 */ fwdContractVO.setId(rs.getString("ID"));
				/* 3754 */ fwdContractVO.setFwdContractNo(rs.getString("FWC_REF_NUM"));
				/* 3755 */ fwdContractVO.setCategory(rs.getString("HOST_DEAL_CATEGORY"));
				/* 3756 */ fwdContractVO.setBillId(rs.getString("BILL_ID"));
				/* 3757 */ fwdContractVO.setCustomerID(rs.getString("COUNTERPARTY_STRING"));
				/* 3758 */ fwdContractVO.setBranchCode(rs.getString("SOL_ID"));
				/* 3759 */ fwdContractVO.setBuyOrSell(rs.getString("BUY_OR_SELL"));
				/* 3760 */ fwdContractVO.setBuyAmount(
						String.valueOf(rs.getString("BUY_AMOUNT")) + " " + rs.getString("BUY_AMOUNT_CCY"));
				/* 3761 */ fwdContractVO.setSellAmount(
						String.valueOf(rs.getString("SELL_AMOUNT")) + " " + rs.getString("SELL_AMOUNT_CCY"));
				/* 3762 */ fwdContractVO.setTranType(rs.getString("TRAN_TYPE"));
				/*      */
				/* 3764 */ HashMap<String, String> purchaseAndSaleAmtMap = getAvailablePurchaseAndSaleAmts(
						/* 3765 */ fwdContractVO.getFwdContractNo(), fwdContractVO.getCustomerID());
				/*      */
				/* 3767 */ if (fwdContractVO.getBuyOrSell().equalsIgnoreCase("B") ||
				/* 3768 */ fwdContractVO.getBuyOrSell().equalsIgnoreCase("P"))
					/* 3769 */ fwdContractVO
							.setOutstandingAmt(/* 3770 */ String.valueOf(purchaseAndSaleAmtMap.get("PurchaseAmount"))
									+ " " + rs.getString("BUY_AMOUNT_CCY"));
				/* 3771 */ if (fwdContractVO.getBuyOrSell().equalsIgnoreCase("S")) {
					/* 3772 */ fwdContractVO
							.setOutstandingAmt(/* 3773 */ String.valueOf(purchaseAndSaleAmtMap.get("SaleAmount")) + " "
									+ rs.getString("SELL_AMOUNT_CCY"));
					/*      */ }
				/* 3775 */ fwdContractVO.setDealValidFromDate(rs.getString("START_DATE"));
				/* 3776 */ fwdContractVO.setDealValidToDate(rs.getString("END_DATE"));
				/* 3777 */ fwdContractVO.setTreasuryRefNo(rs.getString("REFERENCE_NUM"));
				/* 3778 */ fwdContractVO.setTreasuryRate(rs.getString("FWD_CONTRACT_RATE"));
				/* 3779 */ fwdContractVO.setStatus(rs.getString("RECORD_STATUS"));
				/* 3780 */ fwdContractList.add(fwdContractVO);
				/*      */ }
			/*      */
			/*      */ }
		/* 3784 */ catch (Exception e) {
			/* 3785 */ e.printStackTrace();
			/*      */ } finally {
			/* 3787 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 3790 */ logger.info("Exiting Method");
		/* 3791 */ return fwdContractList;
		/*      */ }

	/*      */
	/*      */
	/*      */ private HashMap<String, String> getAvailablePurchaseAndSaleAmts(String contractRef, String customer) {
		/* 3796 */ ResultSet resultSet = null;
		/* 3797 */ Connection tiZoneConnection = null;
		/* 3798 */ PreparedStatement preparedStatement = null;
		/* 3799 */ String purchaseAmount = "0.0";
		/* 3800 */ String saleAmount = "0.0";
		/* 3801 */ HashMap<String, String> aHashMap = new HashMap<>();
		/*      */
		/*      */ try {
			/* 3804 */ String availablePurchaseAndSaleAmtsQuery = "SELECT TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN BUY_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN BUY_AMOUNT \tWHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -BUY_AMOUNT \tWHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -SELL_AMOUNT END)) AS BUY_AMOUNT,  TO_CHAR(SUM(CASE WHEN HOST_DEAL_CATEGORY='FXRATE' THEN SELL_AMOUNT  WHEN HOST_DEAL_CATEGORY='FWCBOOK' THEN SELL_AMOUNT \tWHEN HOST_DEAL_CATEGORY='FWCUTIL' THEN -SELL_AMOUNT \tWHEN HOST_DEAL_CATEGORY='FWCCANCEL' THEN -BUY_AMOUNT END)) AS SELL_AMOUNT FROM CUSTOM_TREASURY_INSERT_TBL  WHERE RECORD_STATUS <>'DELETED' AND COUNTERPARTY_STRING IS NOT NULL AND FWC_REF_NUM IS NOT NULL  AND FWC_REF_NUM=? AND COUNTERPARTY_STRING =?";
			/*      */
			/* 3806 */ tiZoneConnection = DBConnectionUtility.getZoneConnection();
			/* 3807 */ preparedStatement = tiZoneConnection.prepareStatement(availablePurchaseAndSaleAmtsQuery);
			/*      */
			/* 3809 */ preparedStatement.setString(1, contractRef.trim());
			/* 3810 */ preparedStatement.setString(2, customer.trim());
			/*      */
			/* 3812 */ resultSet = preparedStatement.executeQuery();
			/* 3813 */ while (resultSet.next())
			/*      */ {
				/* 3815 */ if (CommonMethods.isValidString(resultSet.getString("BUY_AMOUNT"))) {
					/* 3816 */ purchaseAmount = resultSet.getString("BUY_AMOUNT");
					/*      */ }
				/* 3818 */ if (CommonMethods.isValidString(resultSet.getString("SELL_AMOUNT"))) {
					/* 3819 */ saleAmount = resultSet.getString("SELL_AMOUNT");
					/*      */ }
				/* 3821 */ aHashMap.put("PurchaseAmount", purchaseAmount);
				/* 3822 */ aHashMap.put("SaleAmount", saleAmount);
				/*      */
				/* 3824 */ logger
						.info("purchaseAmt and saleAmt from Treasury : " + purchaseAmount + " & " + saleAmount);
				/*      */ }
			/*      */
			/* 3827 */ } catch (SQLException e) {
			/* 3828 */ e.printStackTrace();
			/*      */ } finally {
			/*      */
			/* 3831 */ DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
			/*      */ }
		/*      */
		/* 3834 */ return aHashMap;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO approveFwdContractDetails(ForwardContractVO fwdContractVO, String category)
			throws DAOException {
		/* 3840 */ logger.info("Entering Method");
		/* 3841 */ Connection con = null;
		/* 3842 */ LoggableStatement loggableStatement = null;
		/* 3843 */ ResultSet rs = null;
		/* 3844 */ ResultSet rs1 = null;
		/* 3845 */ int records = 0;
		/*      */
		/*      */
		/*      */
		/* 3849 */ Map<String, String> limitexposureTokens = new HashMap<>();
		/* 3850 */ Map<String, String> postingTokens = new HashMap<>();
		/* 3851 */ Map<String, String> ftrtUpdateTokens = new HashMap<>();
		/* 3852 */ Map<String, String> treasUpdateTokens = new HashMap<>();
		/* 3853 */ String limitBlockedID = "";
		/* 3854 */ String postingTranID = "";
		/* 3855 */ String limitStatus = "S";
		/* 3856 */ String postingStatus = "";
		/* 3857 */ String ftrtUpdateStatus = "";
		/* 3858 */ String treasUpdateStatus = "";
		/* 3859 */ int insertedCount = 0;
		/* 3860 */ String seqNo = "";
		/* 3861 */ String postingTransdate = "";
		/* 3862 */ AvailBalAuthCheckUtility accountBalance = new AvailBalAuthCheckUtility();
		/* 3863 */ String balance = "";
		/* 3864 */ String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
		/* 3865 */ msgId = msgId.replaceAll("[- :.]", "");
		/* 3866 */ String chargeAmountCcy = "";
		/* 3867 */ String chargeCcy = "";
		/* 3868 */ String chargeAmt = "";
		/* 3869 */ String gstAmountCcy = "";
		/* 3870 */ String gstCcy = "";
		/* 3871 */ String gstAmt = "";
		/* 3872 */ String totalAmt = "";
		/* 3873 */ Boolean flag = Boolean.valueOf(true);
		/* 3874 */ String BlockorUnblockstatus = "LIMITBLOCKED";
		/*      */
		/* 3876 */ String treasuryHDDTableName = ServiceUtility.getBridgePropertyValue("TreasuryHDDTable");
		/*      */
		/*      */
		/*      */ try {
			/* 3880 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 3881 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 3882 */ String userId = request.getRemoteUser();
			/* 3883 */ if (userId == null) {
				/* 3884 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 3887 */ logger.info("fwdContractVO.getRemarks()=>" + fwdContractVO.getRemarks());
			/* 3888 */ logger.info("fwdContractVO.getFwdContractNo()==>" + fwdContractVO.getFwdContractNo());
			/* 3889 */ logger.info("fwdContractVO.getCustomerID()==>" + fwdContractVO.getCustomerID());
			/* 3890 */ logger.info("fwdContractVO.getScreenType()==>" + fwdContractVO.getScreenType());
			/* 3891 */ logger.info("ftreasuryHDDTableName==>" + treasuryHDDTableName);
			/* 3892 */ String treasuryRefNo = fwdContractVO.getTreasuryRefNo().trim();
			/* 3893 */ String fwdContractNo = fwdContractVO.getFwdContractNo().trim();
			/* 3894 */ String remarks = fwdContractVO.getRemarks().trim();
			/* 3895 */ String customer = fwdContractVO.getCustomerID().trim();
			/* 3896 */ String limitID = fwdContractVO.getLimitID().trim();
			/* 3897 */ String Id = fwdContractVO.getId().trim();
			/* 3898 */ String accNo = fwdContractVO.getAcctNumber().trim();
			/* 3899 */ String whereClause = "";
			/*      */
			/* 3901 */ if (customer != null && !customer.isEmpty())
				/* 3902 */ whereClause = String.valueOf(whereClause) + " AND TRIM(COUNTERPARTY_STRING) LIKE '"
						+ customer + "' ";
			/* 3903 */ if (treasuryRefNo != null && !treasuryRefNo.isEmpty())
				/* 3904 */ whereClause = String.valueOf(whereClause) + " AND REFERENCE_NUM LIKE '" + treasuryRefNo
						+ "' ";
			/* 3905 */ if (fwdContractVO.getBranchCode() != null && !fwdContractVO.getBranchCode().isEmpty()) {
				/* 3906 */ whereClause = String.valueOf(whereClause) + " AND SOL_ID LIKE '"
						+ fwdContractVO.getBranchCode() + "' ";
				/*      */ }
			/* 3908 */ String fxOptionSearchQuery = "SELECT COUNT(*) AS COUNT FROM " + treasuryHDDTableName +
			/* 3909 */ " WHERE HOST_DEAL_CATEGORY='FXRATE' AND RECORD_STATUS = 'TRANSFER' AND HOST_DEAL_SUB_CATEGORY IN ('FWCBOOK','FXBS')"
					+
					/* 3910 */ " AND COUNTERPARTY_STRING IS NOT NULL AND REFERENCE_NUM IS NOT NULL AND FWC_REF_NUM IS NULL "
					+ whereClause;
			/*      */
			/*      */
			/*      */
			/* 3914 */ con = DBConnectionUtility.getZoneConnection();
			/* 3915 */ loggableStatement = new LoggableStatement(con, fxOptionSearchQuery);
			/* 3916 */ logger.info(loggableStatement.getQueryString());
			/* 3917 */ rs1 = loggableStatement.executeQuery();
			/*      */
			/*      */
			/* 3920 */ while (rs1.next()) {
				/* 3921 */ records = rs1.getInt(1);
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
			/* 3986 */ logger.info("get Role result" + records);
			/* 3987 */ String bookingdate = fwdContractVO.getBookingDate().trim();
			/* 3988 */ bookingdate = "20" + bookingdate.substring(6) + "/" + bookingdate.substring(3, 5) + "/" +
			/* 3989 */ bookingdate.substring(0, 2);
			/* 3990 */ logger.info("approveFwdContractDetails bookingdate :: " + bookingdate);
			/*      */
			/* 3992 */ String date2 = getTICurrentDateFormat();
			/* 3993 */ logger.info("approveFwdContractDetails date2 : " + date2);
			/* 3994 */ if (bookingdate != null && date2 != null && bookingdate.compareTo(date2) != 0 && records > 0
					&& flag.booleanValue()) {
				/*      */
				/*      */
				/*      */
				/*      */
				/*      */
				/* 4000 */ String fwdContractAmt = fwdContractVO.getFwdContractAmt();
				/* 4001 */ fwdContractAmt = fwdContractAmt.trim().replaceAll("[^0-9.]", "");
				/* 4002 */ String toAmount = fwdContractVO.getToCurrencyAmt().trim();
				/*      */
				/* 4004 */ String limitCcy = toAmount.trim().replaceAll("[^A-Za-z]+", "");
				/* 4005 */ String limitAmount = toAmount.trim().replaceAll("[^0-9.]", "");
				/*      */
				/* 4007 */ String washRate = fwdContractVO.getWashRate().trim();
				/*      */
				/* 4009 */ if (CommonMethods.isValidString(limitCcy) && !limitCcy.equalsIgnoreCase("INR") &&
				/* 4010 */ CommonMethods.isValidString(washRate)) {
					/* 4011 */ limitAmount = CommonMethods.getEquivalentINRAmount("INR", limitAmount, washRate);
					/*      */
					/*      */
					/* 4014 */ limitCcy = "INR";
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
				/* 4028 */ balance = accountBalance.getAccountBalance("0", msgId, "account", accNo, "");
				/* 4029 */ logger.info("Account Balance available for account number " + accNo + " is " + balance);
				/*      */
				/* 4031 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
					/* 4032 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
					/* 4033 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 4034 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/*      */
				/* 4037 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
					/* 4038 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
					/* 4039 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 4040 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/* 4042 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy)
						&&
						/* 4043 */ CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy) &&
						/* 4044 */ chargeCcy.equalsIgnoreCase(gstCcy)) {
					/* 4045 */ totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
					/*      */ }
				/*      */
				/* 4048 */ int balanceCompare = 0;
				/* 4049 */ if (balance != null && !balance.isEmpty()) {
					/* 4050 */ balanceCompare = (new BigDecimal(totalAmt)).compareTo(new BigDecimal(balance));
					/*      */ } else {
					/* 4052 */ balanceCompare = 1;
					/*      */ }
				/* 4054 */ logger.info("balanceCompare :: " + balanceCompare);
				/*      */
				/* 4056 */ if (balanceCompare != 1) {
					/* 4057 */ if (CommonMethods.isValidString(limitID)) {
						/*      */
						/*      */ try {
							/* 4060 */ limitexposureTokens = LimitBlockUnblockUtil.limitexposurethroughAPI(
									fwdContractNo, limitID, /* 4061 */ limitAmount, limitCcy, BlockorUnblockstatus,
									"FWCBOOK");
							/* 4062 */ limitStatus = ((String) limitexposureTokens.get("LimitBOUStatus")).trim();
							/* 4063 */ if (limitStatus.equalsIgnoreCase("S"))
								/* 4064 */ limitBlockedID = ((String) limitexposureTokens.get("SerialNumber")).trim();
							/* 4065 */ } catch (Exception e) {
							/* 4066 */ e.printStackTrace();
							/* 4067 */ logger
									.info("Exception in Limit Block for FWD ref :" + fwdContractVO.getFwdContractNo() +
									/* 4068 */ " error: " + e.getMessage());
							/*      */ }
						/*      */ }
					/* 4071 */ Map<String, String> insertInFTIStatus = FWCUtil
							.insertFTIFwdContractDetails(fwdContractVO, userId, /* 4072 */ "FWCBOOK");
					/* 4073 */ insertedCount = Integer.valueOf(insertInFTIStatus.get("Count")).intValue();
					/* 4074 */ seqNo = insertInFTIStatus.get("SequenceNo");
					/*      */
					/* 4076 */ if (insertedCount > 0) {
						/* 4077 */ ftrtUpdateTokens = FtrtUpdateUtil.updateUtilizedAmountInFinacle(treasuryRefNo,
								fwdContractAmt);
						/* 4078 */ ftrtUpdateStatus = ((String) ftrtUpdateTokens.get("FtrtUpdateStatus")).trim();
						/*      */
						/* 4080 */ treasUpdateTokens = TreasUpdateUtil.updateUtilizationAmountInTreasury(treasuryRefNo,
								/* 4081 */ fwdContractAmt);
						/* 4082 */ treasUpdateStatus = ((String) treasUpdateTokens.get("TreasUpdateStatus")).trim();
						/*      */
						/* 4084 */ fwdContractVO = generateFWCPostings(fwdContractVO);
						/*      */
						/* 4086 */ if (ftrtUpdateStatus.equalsIgnoreCase("S")) {
							/* 4087 */ if (treasUpdateStatus.equalsIgnoreCase("S")
									&& fwdContractVO.getPostingList().size() > 0) {
								/*      */
								/* 4089 */ postingTokens = PostingUtil.releaseTxnPostings("FWCBOOK", fwdContractVO,
										seqNo);
								/* 4090 */ if (postingTokens != null) {
									/* 4091 */ postingStatus = ((String) postingTokens.get("PostingStatus")).trim();
									/*      */ } else {
									/* 4093 */ postingStatus = "FAILED";
									/*      */ }
								/* 4095 */ logger.info("Posting Status -->" + postingStatus);
								/*      */
								/*      */
								/* 4098 */ if (postingStatus.equalsIgnoreCase("SUCCESS")) {
									/* 4099 */ postingTranID = ((String) postingTokens.get("TranID")).trim();
									/* 4100 */ logger.info("postingTranID:" + postingTranID);
									/* 4101 */ postingTransdate = ((String) postingTokens.get("Trandate")).trim();
									/* 4102 */ logger.info("postingTransdate:" + postingTransdate);
									/* 4103 */ fwdContractVO.setTransid(postingTranID);
									/* 4104 */ fwdContractVO.setTransdate(postingTransdate);
									/* 4105 */ String updatebooktransdetails = UpdatebookTransdetails(fwdContractVO,
											postingTranID, /* 4106 */ postingTransdate);
									/* 4107 */ logger.info("updatebooktransdetails:" + updatebooktransdetails);
									/*      */
									/* 4109 */ logger.info(" inside fwd contract updation in FTI table");
									/*      */
									/*      */
									/* 4112 */ loggableStatement = new LoggableStatement(con,
											"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?,LIMIT_SERIAL_NUM=?,POSTING_TRAN_ID=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
									/*      */
									/* 4114 */ loggableStatement.setString(1, "APPROVED");
									/* 4115 */ loggableStatement.setString(2, userId.trim());
									/* 4116 */ loggableStatement.setString(3, remarks);
									/* 4117 */ loggableStatement.setString(4, "Approve");
									/* 4118 */ loggableStatement.setString(5, limitBlockedID);
									/* 4119 */ loggableStatement.setString(6, postingTranID);
									/* 4120 */ loggableStatement.setString(7, category);
									/* 4121 */ loggableStatement.setString(8, fwdContractNo);
									/* 4122 */ loggableStatement.setInt(9, Integer.valueOf(Id).intValue());
									/*      */
									/* 4124 */ logger.info(/* 4125 */ "UPDATE approve CHECKER Details: "
											+ loggableStatement.getQueryString());
									/*      */
									/* 4127 */ int count = loggableStatement.executeUpdate();
									/*      */
									/* 4129 */ if (count > 0) {
										/* 4130 */ logger.info("Approved Successfully");
										/* 4131 */ fwdContractVO.setCount(count);
										/*      */ }
									/*      */ } else {
									/*      */
									/* 4135 */ int count = updateFailedStatus(fwdContractVO, category);
									/* 4136 */ if (count > 0) {
										/* 4137 */ fwdContractVO.setCount(0);
										/*      */
										/* 4139 */ if (CommonMethods.isValidString(limitID) &&
										/* 4140 */ limitStatus != null && limitStatus.equalsIgnoreCase("S")) {
											/* 4141 */ BlockorUnblockstatus = "LIMITUNBLOCKED";
											/* 4142 */ LimitBlockUnblockUtil.limitreversethroughAPI(fwdContractNo,
													limitID, /* 4143 */ "0", limitCcy, BlockorUnblockstatus,
													limitBlockedID);
											/*      */ }
										/*      */ }
									/*      */
									/*      */
									/* 4148 */ fwdContractVO.setCount(2);
									/*      */ }
								/*      */ } else {
								/* 4151 */ int count = updateFailedStatus(fwdContractVO, category);
								/* 4152 */ if (count > 0) {
									/* 4153 */ fwdContractVO.setCount(3);
									/*      */ }
								/*      */ }
							/*      */ } else {
							/* 4157 */ int count = updateFailedStatus(fwdContractVO, category);
							/* 4158 */ if (count > 0) {
								/* 4159 */ fwdContractVO.setCount(4);
								/*      */ }
							/*      */ }
						/*      */ } else {
						/* 4163 */ int count = updateFailedStatus(fwdContractVO, category);
						/* 4164 */ if (count > 0) {
							/* 4165 */ fwdContractVO.setCount(7);
							/*      */ }
						/*      */ }
					/*      */ } else {
					/*      */
					/* 4170 */ int count = updatePendingStatus(fwdContractVO, category);
					/* 4171 */ if (count > 0) {
						/* 4172 */ fwdContractVO.setCount(5);
						/*      */ }
					/*      */ }
				/*      */
				/*      */ } else {
				/*      */
				/* 4178 */ int count = updateRejectStatus(fwdContractVO, category);
				/* 4179 */ if (count > 0) {
					/* 4180 */ fwdContractVO.setCount(6);
					/*      */ }
				/*      */ }
			/*      */
			/* 4184 */ logger.info("fwdContractVO COUNT-->" + fwdContractVO.getCount());
			/*      */
			/*      */ }
		/* 4187 */ catch (SQLException e) {
			/* 4188 */ e.printStackTrace();
			/*      */ } finally {
			/* 4190 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/* 4191 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs1);
			/*      */ }
		/* 4193 */ logger.info("Exiting Method");
		/* 4194 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */ public int updateRejectStatus(ForwardContractVO fwdContractVO, String category) throws DAOException {
		/* 4199 */ logger.info("Entering Method");
		/* 4200 */ Connection con = null;
		/* 4201 */ LoggableStatement loggableStatement = null;
		/* 4202 */ ResultSet rs = null;
		/* 4203 */ int count = 0;
		/*      */
		/*      */ try {
			/* 4206 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4207 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4208 */ String userId = request.getRemoteUser();
			/* 4209 */ if (userId == null) {
				/* 4210 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4213 */ String Id = fwdContractVO.getId().trim();
			/*      */
			/* 4215 */ con = DBConnectionUtility.getZoneConnection();
			/* 4216 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?  WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
			/* 4217 */ loggableStatement.setString(1, "REJECTED");
			/* 4218 */ loggableStatement.setString(2, userId.trim());
			/* 4219 */ loggableStatement.setString(3, fwdContractVO.getRemarks());
			/* 4220 */ loggableStatement.setString(4, "Rejected");
			/* 4221 */ loggableStatement.setString(5, category);
			/* 4222 */ loggableStatement.setString(6, fwdContractVO.getFwdContractNo());
			/* 4223 */ loggableStatement.setInt(7, Integer.valueOf(Id).intValue());
			/* 4224 */ logger.info("Update as Pending for Approval : " + loggableStatement.getQueryString());
			/*      */
			/* 4226 */ count = loggableStatement.executeUpdate();
			/*      */
			/* 4228 */ if (count > 0) {
				/* 4229 */ logger.info("Updated Successfully");
				/*      */ }
			/* 4231 */ } catch (SQLException e) {
			/* 4232 */ e.printStackTrace();
			/*      */ } finally {
			/* 4234 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 4236 */ logger.info("Exiting Method");
		/* 4237 */ return count;
		/*      */ }

	/*      */
	/*      */ public ForwardContractVO rejectFwdContractDetails(ForwardContractVO fwdContractVO, String category)
			throws DAOException {
		/* 4241 */ logger.info("Entering Method");
		/* 4242 */ Connection con = null;
		/* 4243 */ LoggableStatement loggableStatement = null;
		/* 4244 */ ResultSet rs = null;
		/*      */
		/*      */ try {
			/* 4247 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4248 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4249 */ String userId = request.getRemoteUser();
			/* 4250 */ if (userId == null) {
				/* 4251 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4254 */ logger.info("fwdContractVO.getRemarks()=>" + fwdContractVO.getRemarks());
			/* 4255 */ logger.info("fwdContractVO.getFwdContractNo()==>" + fwdContractVO.getFwdContractNo());
			/* 4256 */ logger.info("fwdContractVO.getCustomerID()==>" + fwdContractVO.getCustomerID());
			/* 4257 */ String Id = fwdContractVO.getId().trim();
			/* 4258 */ con = DBConnectionUtility.getZoneConnection();
			/* 4259 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?  WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
			/*      */
			/* 4261 */ loggableStatement.setString(1, "REJECTED");
			/* 4262 */ loggableStatement.setString(2, userId.trim());
			/* 4263 */ loggableStatement.setString(3, fwdContractVO.getRemarks());
			/* 4264 */ loggableStatement.setString(4, "Rejected");
			/* 4265 */ loggableStatement.setString(5, category);
			/* 4266 */ loggableStatement.setString(6, fwdContractVO.getFwdContractNo());
			/* 4267 */ loggableStatement.setInt(7, Integer.valueOf(Id).intValue());
			/*      */
			/* 4269 */ logger.info("UPDATE reject CHECKER Details: " + loggableStatement.getQueryString());
			/*      */
			/* 4271 */ int count = loggableStatement.executeUpdate();
			/*      */
			/* 4273 */ if (count > 0) {
				/* 4274 */ logger.info("Rejected Successfully");
				/* 4275 */ fwdContractVO.setCount(count);
				/*      */ }
			/*      */
			/* 4278 */ } catch (SQLException e) {
			/* 4279 */ e.printStackTrace();
			/*      */ } finally {
			/* 4281 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 4284 */ logger.info("Exiting Method");
		/* 4285 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO deleteFwdContractDetails(ForwardContractVO fwdContractVO, String category)
			throws DAOException {
		/* 4291 */ logger.info("Entering Method");
		/* 4292 */ Connection con = null;
		/* 4293 */ LoggableStatement loggableStatement = null;
		/* 4294 */ ResultSet rs = null;
		/*      */
		/*      */ try {
			/* 4297 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4298 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4299 */ String userId = request.getRemoteUser();
			/* 4300 */ if (userId == null) {
				/* 4301 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4304 */ logger.info("fwdContractVO.getRemarks()=>" + fwdContractVO.getRemarks());
			/* 4305 */ logger.info("fwdContractVO.getFwdContractNo()==>" + fwdContractVO.getFwdContractNo());
			/* 4306 */ logger.info("fwdContractVO.getCustomerID()==>" + fwdContractVO.getCustomerID());
			/* 4307 */ String Id = fwdContractVO.getId().trim();
			/* 4308 */ con = DBConnectionUtility.getZoneConnection();
			/* 4309 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?  WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
			/*      */
			/* 4311 */ loggableStatement.setString(1, "DELETED");
			/* 4312 */ loggableStatement.setString(2, userId.trim());
			/* 4313 */ loggableStatement.setString(3, fwdContractVO.getRemarks());
			/* 4314 */ loggableStatement.setString(4, DELETE);
			/* 4315 */ loggableStatement.setString(5, category);
			/* 4316 */ loggableStatement.setString(6, fwdContractVO.getFwdContractNo());
			/* 4317 */ loggableStatement.setInt(7, Integer.valueOf(Id).intValue());
			/*      */
			/* 4319 */ logger.info("UPDATE DELETE CHECKER Details: " + loggableStatement.getQueryString());
			/*      */
			/* 4321 */ int count = loggableStatement.executeUpdate();
			/*      */
			/* 4323 */ if (count > 0) {
				/* 4324 */ logger.info("Deleted Successfully");
				/* 4325 */ fwdContractVO.setCount(count);
				/*      */ }
			/*      */
			/* 4328 */ } catch (SQLException e) {
			/* 4329 */ e.printStackTrace();
			/*      */ } finally {
			/* 4331 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 4334 */ logger.info("Exiting Method");
		/* 4335 */ return fwdContractVO;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public ForwardContractVO cancelFwdContractDetails(ForwardContractVO fwdContractVO, String category)
			throws DAOException {
		/* 4341 */ logger.info("Entering Method");
		/* 4342 */ Connection con = null;
		/* 4343 */ LoggableStatement loggableStatement = null;
		/* 4344 */ ResultSet rs = null;
		/* 4345 */ ResultSet rs1 = null;
		/* 4346 */ int records = 0;
		/*      */
		/*      */
		/*      */
		/*      */
		/* 4351 */ Map<String, String> limitexposureTokens = new HashMap<>();
		/* 4352 */ Map<String, String> ftrtUpdateTokens = new HashMap<>();
		/* 4353 */ Map<String, String> treasUpdateTokens = new HashMap<>();
		/* 4354 */ String BlockorUnblockstatus = "LIMIT_UNBLOCKED";
		/* 4355 */ Map<String, String> postingTokens = new HashMap<>();
		/* 4356 */ String postingTranID = "";
		/* 4357 */ String limitUnblockedID = "";
		/* 4358 */ String postingStatus = "";
		/* 4359 */ String limitStatus = "S";
		/* 4360 */ String washRate = "";
		/* 4361 */ int insertedCount = 0;
		/* 4362 */ String seqNo = "";
		/* 4363 */ String postingTransdate = "";
		/* 4364 */ String ftrtUpdateStatus = "";
		/* 4365 */ String treasUpdateStatus = "";
		/* 4366 */ String chargeAmountCcy = "";
		/* 4367 */ String chargeCcy = "";
		/* 4368 */ String chargeAmt = "";
		/* 4369 */ String gstAmountCcy = "";
		/* 4370 */ String gstCcy = "";
		/* 4371 */ String gstAmt = "";
		/* 4372 */ String totalAmt = "";
		/* 4373 */ String treasuryHDDTableName1 = ServiceUtility.getBridgePropertyValue("TreasuryHDDTable");
		/*      */
		/*      */ try {
			/* 4376 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4377 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4378 */ String userId = request.getRemoteUser();
			/* 4379 */ if (userId == null) {
				/* 4380 */ userId = "SUPERVISOR";
				/*      */ }
			/* 4382 */ logger.info("fwdContractVO.getRemarks()=>" + fwdContractVO.getRemarks());
			/* 4383 */ logger.info("fwdContractVO.getFwdContractNo()==>" + fwdContractVO.getFwdContractNo());
			/* 4384 */ logger.info("fwdContractVO.getCustomerID()==>" + fwdContractVO.getCustomerID());
			/*      */
			/* 4386 */ String treasuryRefNo = fwdContractVO.getTreasuryRefNo().trim();
			/* 4387 */ String fwdContractNo = fwdContractVO.getFwdContractNo().trim();
			/* 4388 */ String remarks = fwdContractVO.getRemarks().trim();
			/* 4389 */ String customer = fwdContractVO.getCustomerID().trim();
			/* 4390 */ String limitID = fwdContractVO.getLimitID().trim();
			/* 4391 */ String accNo = fwdContractVO.getAcctNumber();
			/* 4392 */ logger.info("fwdContractVO.getLimitID()=>" + fwdContractVO.getLimitID());
			/* 4393 */ String Id = fwdContractVO.getId().trim();
			/* 4394 */ String outstandingamtccy = fwdContractVO.getCancellationamount();
			/* 4395 */ String cancellationamountccy = fwdContractVO.getOutstandingAmt();
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
			/* 4429 */ String bookingamt = getBookingAmount(fwdContractVO.getFwdContractNo());
			/* 4430 */ String bookingdate = fwdContractVO.getBookingDate().trim();
			/* 4431 */ bookingdate = "20" + bookingdate.substring(6) + "/" + bookingdate.substring(3, 5) + "/" +
			/* 4432 */ bookingdate.substring(0, 2);
			/* 4433 */ logger.info("approveFwdContractDetails bookingdate :: " + bookingdate);
			/*      */
			/* 4435 */ String date2 = getTICurrentDateFormat();
			/* 4436 */ logger.info("approveFwdContractDetails date2 : " + date2);
			/* 4437 */ if (bookingdate != null && date2 != null && bookingdate.compareTo(date2) != 0) {
				/*      */
				/* 4439 */ String outstandingamt = outstandingamtccy.trim().replaceAll("[^0-9.]", "");
				/* 4440 */ String outstandingccy = outstandingamtccy.trim().replaceAll("[^A-Za-z]+", "");
				/*      */
				/* 4442 */ String cancellationamount = cancellationamountccy.trim().replaceAll("[^0-9.]", "");
				/* 4443 */ String cancellationccy = cancellationamountccy.trim().replaceAll("[^A-Za-z]+", "");
				/* 4444 */ AvailBalAuthCheckUtility accountBalance = new AvailBalAuthCheckUtility();
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
				/* 4459 */ BigDecimal limitamt = new BigDecimal(0);
				/* 4460 */ logger.info("outstandingamt Amount:" + outstandingamt);
				/* 4461 */ BigDecimal outstandingamtdec = new BigDecimal(outstandingamt);
				/* 4462 */ logger.info("outstandingamtdec Amount:" + outstandingamtdec);
				/* 4463 */ logger.info("cancellationamount Amount:" + cancellationamount);
				/* 4464 */ BigDecimal cancellationamountdec = new BigDecimal(cancellationamount);
				/* 4465 */ logger.info("cancellationamountdec Amount:" + cancellationamountdec);
				/* 4466 */ limitamt = outstandingamtdec.subtract(cancellationamountdec);
				/* 4467 */ logger.info("Limit Amount:" + limitamt);
				/* 4468 */ String bookingrate = getBookingTreasuryrate(fwdContractVO.getFwdContractNo());
				/* 4469 */ BigDecimal bookingtreasrate = new BigDecimal(bookingrate);
				/* 4470 */ limitamt = limitamt.multiply(bookingtreasrate);
				/* 4471 */ String limitsr = getLimitNodeForBooking(fwdContractVO.getFwdContractNo());
				/* 4472 */ logger.info("Limit Amount:" + limitamt);
				/* 4473 */ if (limitamt.compareTo(new BigDecimal("0.00")) <= 0) {
					/* 4474 */ limitamt = new BigDecimal("0.001");
					/*      */ }
				/*      */
				/* 4477 */ String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
				/* 4478 */ msgId = msgId.replaceAll("[- :.]", "");
				/*      */
				/* 4480 */ String balance = accountBalance.getAccountBalance("0", msgId, "account", accNo, "");
				/* 4481 */ logger.info("Account Balance available for account number " + accNo + " is " + balance);
				/*      */
				/* 4483 */ if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
					/* 4484 */ chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
					/* 4485 */ chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 4486 */ chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/*      */
				/* 4489 */ if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
					/* 4490 */ gstAmountCcy = fwdContractVO.getGstAmount().trim();
					/* 4491 */ gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
					/* 4492 */ gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
					/*      */ }
				/* 4494 */ if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy)
						&&
						/* 4495 */ CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy) &&
						/* 4496 */ chargeCcy.equalsIgnoreCase(gstCcy)) {
					/* 4497 */ totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
					/*      */ }
				/*      */
				/*      */
				/* 4501 */ int balanceCompare = 0;
				/* 4502 */ if (balance != null && !balance.isEmpty()) {
					/* 4503 */ balanceCompare = (new BigDecimal(totalAmt)).compareTo(new BigDecimal(balance));
					/*      */ } else {
					/* 4505 */ balanceCompare = 1;
					/*      */ }
				/* 4507 */ logger.info("balanceCompare :: " + balanceCompare);
				/*      */
				/* 4509 */ if (balanceCompare != 1) {
					/* 4510 */ fwdContractVO = getFWCPostingsToReverse(fwdContractVO);
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/* 4517 */ String toAmount = fwdContractVO.getToCurrencyAmt().trim();
					/*      */
					/* 4519 */ String limitCcy = toAmount.trim().replaceAll("[^A-Za-z]+", "");
					/*      */
					/*      */
					/*      */
					/*      */
					/*      */
					/* 4525 */ String limitAmount = limitamt.toString();
					/*      */
					/* 4527 */ if (CommonMethods.isValidString(fwdContractVO.getWashRate())) {
						/* 4528 */ washRate = fwdContractVO.getWashRate().trim();
						/*      */ }
					/* 4530 */ if (CommonMethods.isValidString(limitCcy) && !limitCcy.equalsIgnoreCase("INR") &&
					/* 4531 */ CommonMethods.isValidString(washRate)) {
						/* 4532 */ limitAmount = CommonMethods.getEquivalentINRAmount("INR", limitAmount, washRate);
						/* 4533 */ limitCcy = "INR";
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
					/* 4546 */ if (CommonMethods.isValidString(limitID)) {
						/*      */
						/* 4548 */ logger.info("Cancel forward Contract in Limit");
						/*      */
						/* 4550 */ logger.info(String.valueOf(fwdContractNo) + limitID + limitAmount + limitCcy +
						/* 4551 */ BlockorUnblockstatus);
						/* 4552 */ limitexposureTokens = LimitBlockUnblockUtil.limitexposurethroughAPI(fwdContractNo,
								limitID, /* 4553 */ limitAmount, limitCcy, BlockorUnblockstatus, "FWCCANCEL");
						/* 4554 */ limitStatus = ((String) limitexposureTokens.get("LimitBOUStatus")).trim();
						/* 4555 */ if (limitStatus.equalsIgnoreCase("S")) {
							/* 4556 */ limitUnblockedID = ((String) limitexposureTokens.get("SerialNumber")).trim();
							/*      */ }
						/*      */ }
					/*      */
					/*      */
					/*      */
					/* 4562 */ logger.info("Limit status --> " + limitStatus);
					/*      */
					/* 4564 */ Map<String, String> insertInFTIStatus = FWCUtil
							.insertFTIFwdContractDetails(fwdContractVO, userId, /* 4565 */ "FWCCANCEL");
					/* 4566 */ insertedCount = Integer.valueOf(insertInFTIStatus.get("Count")).intValue();
					/* 4567 */ seqNo = insertInFTIStatus.get("SequenceNo");
					/*      */
					/* 4569 */ if (insertedCount > 0) {

						/* 4570 */ ftrtUpdateTokens = FtrtUpdateUtil.updateUtilizedAmountInFinacle(treasuryRefNo,
								/* 4571 */ cancellationamount);
						/* 4572 */ ftrtUpdateStatus = ((String) ftrtUpdateTokens.get("FtrtUpdateStatus")).trim();
						/*      */
						/* 4574 */ treasUpdateTokens = TreasUpdateUtil.updateUtilizationAmountInTreasury(treasuryRefNo,
								/* 4575 */ cancellationamount);
						/* 4576 */ treasUpdateStatus = ((String) treasUpdateTokens.get("TreasUpdateStatus")).trim();
						/*      */
						/* 4578 */ if (ftrtUpdateStatus.equalsIgnoreCase("S")) {
							/* 4579 */ if (treasUpdateStatus.equalsIgnoreCase("S")
									&& fwdContractVO.getPostingList().size() > 0) {
								/* 4580 */ postingTokens = PostingUtil.releaseTxnPostings("FWCCANCEL", fwdContractVO,
										seqNo);
								/* 4581 */ postingStatus = ((String) postingTokens.get("PostingStatus")).trim();
								/*      */
								/* 4583 */ if (postingStatus.equalsIgnoreCase("SUCCESS")) {
									/* 4584 */ postingTranID = ((String) postingTokens.get("TranID")).trim();
									/* 4585 */ postingTranID = ((String) postingTokens.get("TranID")).trim();
									/* 4586 */ logger.info("postingTranID:" + postingTranID);
									/* 4587 */ postingTransdate = ((String) postingTokens.get("Trandate")).trim();
									/* 4588 */ logger.info("postingTransdate:" + postingTransdate);
									/* 4589 */ fwdContractVO.setTransid(postingTranID);
									/* 4590 */ fwdContractVO.setTransdate(postingTransdate);
									/*      */
									/* 4592 */ String updatecanceltransdetails = UpdatecancelTransdetails(fwdContractVO,
											/* 4593 */ postingTranID, postingTransdate);
									/*      */
									/* 4595 */ logger.info("updatecanceltransdetails:" + updatecanceltransdetails);
									/* 4596 */ logger.info("postingStatus status --> " + postingStatus);
									/*      */
									/* 4598 */ con = DBConnectionUtility.getZoneConnection();
									/* 4599 */ loggableStatement = new LoggableStatement(con,
											"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?,LIMIT_SERIAL_NUM=?,POSTING_TRAN_ID=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
									/*      */
									/* 4601 */ loggableStatement.setString(1, "APPROVED");
									/* 4602 */ loggableStatement.setString(2, userId.trim());
									/* 4603 */ loggableStatement.setString(3, remarks);
									/* 4604 */ loggableStatement.setString(4, "Approved");
									/* 4605 */ loggableStatement.setString(5, limitUnblockedID);
									/* 4606 */ loggableStatement.setString(6, postingTranID);
									/* 4607 */ loggableStatement.setString(7, category);
									/* 4608 */ loggableStatement.setString(8, fwdContractNo);
									/* 4609 */ loggableStatement.setInt(9, Integer.valueOf(Id).intValue());
									/*      */
									/* 4611 */ logger
											.info("UPDATE cancel Details: " + loggableStatement.getQueryString());
									/*      */
									/* 4613 */ int count = loggableStatement.executeUpdate();
									/*      */
									/* 4615 */ if (count > 0) {
										/* 4616 */ logger.info("Approved Successfully");
										/* 4617 */ fwdContractVO.setCount(count);
										/*      */ }
									/*      */ } else {
									/*      */
									/* 4621 */ int count = updateFailedStatus(fwdContractVO, category);
									/* 4622 */ if (count > 0) {
										/* 4623 */ fwdContractVO.setCount(2);
										/*      */ }
									/*      */ }
								/*      */
								/*      */ } else {
								/*      */
								/* 4629 */ int count = updateFailedStatus(fwdContractVO, category);
								/* 4630 */ if (count > 0) {
									/* 4631 */ fwdContractVO.setCount(3);
									/*      */ }
								/*      */ }
							/*      */
							/*      */ } else {
							/*      */
							/* 4637 */ int count = updateFailedStatus(fwdContractVO, category);
							/* 4638 */ if (count > 0) {
								/* 4639 */ fwdContractVO.setCount(4);
								/*      */ }
							/*      */ }
						/*      */ } else {
						/* 4643 */ int count = updateFailedStatus(fwdContractVO, category);
						/* 4644 */ if (count > 0) {
							/* 4645 */ fwdContractVO.setCount(7);
							/*      */ }
						/*      */ }
					/*      */ } else {
					/*      */
					/* 4650 */ int count = updatePendingStatus(fwdContractVO, category);
					/* 4651 */ if (count > 0) {
						/* 4652 */ fwdContractVO.setCount(5);
						/*      */ }
					/*      */ }
				/*      */ } else {
				/*      */
				/* 4657 */ int count = updateRejectStatus(fwdContractVO, category);
				/* 4658 */ if (count > 0) {
					/* 4659 */ fwdContractVO.setCount(6);
					/*      */
					/*      */ }
				/*      */ }
			/*      */
			/*      */ }
		/* 4665 */ catch (Exception e) {
			/* 4666 */ e.printStackTrace();
			/*      */ } finally {
			/* 4668 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/*      */
		/* 4671 */ logger.info("Exiting Method");
		/* 4672 */ return fwdContractVO;
		/*      */ }
	// ABHISHEK
		public ForwardContractVO cancelFwdContractDetailsWithoutRate(ForwardContractVO fwdContractVO, String category)
			throws DAOException {
			logger.info("========== ENTERING cancelFwdContractDetailsWithoutRate ==========");
				  logger.info("Category: " + category);
				  logger.info("FWD Contract No: " + fwdContractVO.getFwdContractNo());
				  logger.info("Customer ID: " + fwdContractVO.getCustomerID());
		Connection con = null;
		LoggableStatement loggableStatement = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		int records = 0;
		Map<String, String> limitexposureTokens = new HashMap<>();
		String BlockorUnblockstatus = "LIMIT_UNBLOCKED";
		Map<String, String> postingTokens = new HashMap<>();
		String postingTranID = "";
		String limitUnblockedID = "";
		String postingStatus = "";
		String limitStatus = "S";
		String washRate = "";
		int insertedCount = 0;
		String seqNo = "";
		String postingTransdate = "";
		String chargeAmountCcy = "";
		String chargeCcy = "";
		String chargeAmt = "";
		String gstAmountCcy = "";
		String gstCcy = "";
		String gstAmt = "";
		String totalAmt = "0"; // default to "0" to avoid NumberFormatException when charge/GST are blank
		String treasuryHDDTableName1 = ServiceUtility.getBridgePropertyValue("TreasuryHDDTable");
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			String userId = request.getRemoteUser();
			if (userId == null) {
				userId = "SUPERVISOR";
			}
			logger.info("fwdContractVO.getRemarks()=>" + fwdContractVO.getRemarks());
			logger.info("fwdContractVO.getFwdContractNo()==>" + fwdContractVO.getFwdContractNo());
			logger.info("fwdContractVO.getCustomerID()==>" + fwdContractVO.getCustomerID());
			String treasuryRefNo = fwdContractVO.getTreasuryRefNo().trim();
			String fwdContractNo = fwdContractVO.getFwdContractNo().trim();
			String remarks = fwdContractVO.getRemarks().trim();
			String customer = fwdContractVO.getCustomerID().trim();
			String limitID = fwdContractVO.getLimitID().trim();
			String accNo = fwdContractVO.getAcctNumber();
			logger.info("fwdContractVO.getLimitID()=>" + fwdContractVO.getLimitID());
			String Id = fwdContractVO.getId().trim();

			String outstandingamtccy = fwdContractVO.getOutstandingAmt();
			String cancellationamountccy = fwdContractVO.getCancellationamount();
			logger.info("\n----- STEP 1: Amount Extraction -----");
		    logger.info("Outstanding Amount (Raw): " + outstandingamtccy);
		    logger.info("Cancellation Amount (Raw): " + cancellationamountccy);
		    
			String bookingamt = getBookingAmount(fwdContractVO.getFwdContractNo());
			String bookingdate = fwdContractVO.getBookingDate().trim();
			bookingdate = "20" + bookingdate.substring(6) + "/" + bookingdate.substring(3, 5) + "/"
					+ bookingdate.substring(0, 2);
			logger.info("approveFwdContractDetailsWithoutRate bookingdate :: " + bookingdate);
			String date2 = getTICurrentDateFormat();
			logger.info("approveFwdContractDetailsWithoutRate date2 : " + date2);
			if (bookingdate != null && date2 != null && bookingdate.compareTo(date2) != 0) {
				String outstandingamt = outstandingamtccy.trim().replaceAll("[^0-9.]", "");
				String outstandingccy = outstandingamtccy.trim().replaceAll("[^A-Za-z]+", "");
				String cancellationamount = cancellationamountccy.trim().replaceAll("[^0-9.]", "");
				String cancellationccy = cancellationamountccy.trim().replaceAll("[^A-Za-z]+", "");
				
			    logger.info("Outstanding Amount (Parsed): " + outstandingamt + " " + outstandingccy);
			    logger.info("Cancellation Amount (Parsed): " + cancellationamount + " " + cancellationccy);
			    
				AvailBalAuthCheckUtility accountBalance = new AvailBalAuthCheckUtility();
				BigDecimal limitamt = new BigDecimal(0);
				logger.info("outstandingamt Amount:" + outstandingamt);
				BigDecimal outstandingamtdec = new BigDecimal(outstandingamt);
				logger.info("outstandingamtdec Amount:" + outstandingamtdec);
				logger.info("cancellationamount Amount:" + cancellationamount);
				BigDecimal cancellationamountdec = new BigDecimal(cancellationamount);
				logger.info("cancellationamountdec Amount:" + cancellationamountdec);
				limitamt = outstandingamtdec.subtract(cancellationamountdec);
				logger.info("Limit Amount:" + limitamt);
				
				String bookingrate = getBookingTreasuryrate(fwdContractVO.getFwdContractNo());
				BigDecimal bookingtreasrate = new BigDecimal(bookingrate);
			    logger.info("\n----- STEP 3: Booking Rate Application -----");
			    logger.info("Booking Rate: " + bookingrate);
			    
				limitamt = limitamt.multiply(bookingtreasrate);
				logger.info("Remaining Before Rate: " + limitamt);
				
				String limitsr = getLimitNodeForBooking(fwdContractVO.getFwdContractNo());
				logger.info("Limit Amount:" + limitamt);
				if (limitamt.compareTo(new BigDecimal("0.00")) <= 0) {
					limitamt = new BigDecimal("0.001");
				}
				logger.info("Final Limit Amount: " + limitamt);
				
				String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
				msgId = msgId.replaceAll("[- :.]", "");

				// ── BALANCE CHECK: only for FWCUTIL, skip for FWCCANCEL ──
				int balanceCompare = 0; // default → always proceed for FWCCANCEL
				if ("FWCUTIL".equalsIgnoreCase(category)) {
					logger.info("FWCUTIL — performing balance check for account " + accNo);
					String balance = accountBalance.getAccountBalance("0", msgId, "account", accNo, "");
					logger.info("Account Balance available for account number " + accNo + " is " + balance);
					if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
						chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
						chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
						chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
					}
					if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
						gstAmountCcy = fwdContractVO.getGstAmount().trim();
						gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
						gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
					}
					if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy)
							&& CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy)
							&& chargeCcy.equalsIgnoreCase(gstCcy)) {
						totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
					}
					if (balance != null && !balance.isEmpty()) {
						balanceCompare = (new BigDecimal(totalAmt)).compareTo(new BigDecimal(balance));
					} else {
						balanceCompare = 1;
					}

					logger.info("balanceCompare :: " + balanceCompare);
				}

				else {
					// FWCCANCEL — skip balance API, still parse charge/GST for zero charge check
					logger.info("FWCCANCEL — skipping balance check, proceeding directly");
					if (CommonMethods.isValidString(fwdContractVO.getChargeAmount())) {
						chargeAmountCcy = fwdContractVO.getChargeAmount().trim();
						chargeCcy = chargeAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
						chargeAmt = chargeAmountCcy.trim().replaceAll("[^0-9.]", "");
					}
					if (CommonMethods.isValidString(fwdContractVO.getGstAmount())) {
						gstAmountCcy = fwdContractVO.getGstAmount().trim();
						gstCcy = gstAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
						gstAmt = gstAmountCcy.trim().replaceAll("[^0-9.]", "");
					}
					if (CommonMethods.isValidString(chargeAmountCcy) && CommonMethods.isValidString(gstAmountCcy)
							&& CommonMethods.isValidString(chargeCcy) && CommonMethods.isValidString(gstCcy)
							&& chargeCcy.equalsIgnoreCase(gstCcy)) {
						totalAmt = (new BigDecimal(chargeAmt)).add(new BigDecimal(gstAmt)).toString();
					}
					// balanceCompare stays 0 → flow always proceeds for FWCCANCEL
				}
				// ── END BALANCE CHECK ──
				if (balanceCompare != 1) {
					fwdContractVO = getFWCPostingsToReverse(fwdContractVO);
					String toAmount = fwdContractVO.getToCurrencyAmt().trim();
					String limitCcy = toAmount.trim().replaceAll("[^A-Za-z]+", "");
					String limitAmount = limitamt.toString();
					if (CommonMethods.isValidString(fwdContractVO.getWashRate())) {
						washRate = fwdContractVO.getWashRate().trim();
					}
					if (CommonMethods.isValidString(limitCcy) && !limitCcy.equalsIgnoreCase("INR")
							&& CommonMethods.isValidString(washRate)) {
						limitAmount = CommonMethods.getEquivalentINRAmount("INR", limitAmount, washRate);
						limitCcy = "INR";
					}
					if (CommonMethods.isValidString(limitID)) {
						logger.info("Processing " + category + " forward Contract in Limit");
						logger.info(String.valueOf(fwdContractNo) + limitID + limitAmount + limitCcy
								+ BlockorUnblockstatus);
						limitexposureTokens = LimitBlockUnblockUtil.limitexposurethroughAPI(fwdContractNo, limitID,
								limitAmount, limitCcy, BlockorUnblockstatus, category);
						limitStatus = ((String) limitexposureTokens.get("LimitBOUStatus")).trim();
						if (limitStatus.equalsIgnoreCase("S")) {
							limitUnblockedID = ((String) limitexposureTokens.get("SerialNumber")).trim();
						}
					}
					logger.info("Limit status --> " + limitStatus);
					// FTI insert against REP_FWC_OUTSTANDING_VIEW (no rate match)
					Map<String, String> insertInFTIStatus = FWCUtil
							.insertFTIFwdContractDetailsWithoutRate(fwdContractVO, userId, category);
					insertedCount = Integer.valueOf(insertInFTIStatus.get("Count")).intValue();
					seqNo = insertInFTIStatus.get("SequenceNo");
					if (insertedCount > 0) {
						logger.info("Skipping FTRT/Treasury update for contract " + fwdContractNo + " (" + category
								+ ", Without Rate)");
						BigDecimal totalAmtBD = new BigDecimal(totalAmt);
						if (totalAmtBD.compareTo(BigDecimal.ZERO) == 0) {
							// Charge and GST are zero — skip posting API call, directly approve
							logger.info("Charge and GST are zero — skipping posting API call for " + fwdContractNo);
							// Push to treasury without posting tranId
							FWCUtil.insertUtilizationDetailsInTreasuryWithoutRate(fwdContractNo.trim(), null, category,
									seqNo);
							logger.info("Inserted into Treasury Table without posting for " + fwdContractNo);
							con = DBConnectionUtility.getZoneConnection();
							loggableStatement = new LoggableStatement(con,
									"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?,LIMIT_SERIAL_NUM=?,POSTING_TRAN_ID=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
							loggableStatement.setString(1, "APPROVED");
							loggableStatement.setString(2, userId.trim());
							loggableStatement.setString(3, remarks);
							loggableStatement.setString(4, "Approved");
							loggableStatement.setString(5, limitUnblockedID);
							loggableStatement.setString(6, ""); // no posting tran ID
							loggableStatement.setString(7, category);
							loggableStatement.setString(8, fwdContractNo);
							loggableStatement.setInt(9, Integer.valueOf(Id).intValue());
							logger.info("UPDATE Details WithoutRate (Zero Charge, No Posting): "
									+ loggableStatement.getQueryString());
							int count = loggableStatement.executeUpdate();
							if (count > 0) {
								logger.info("Approved Successfully without posting");
								fwdContractVO.setCount(1);
							}
						} else if (fwdContractVO.getPostingList().size() > 0) {
							// Charge/GST present — proceed with posting API
							postingTokens = PostingUtil.releaseTxnPostings(category, fwdContractVO, seqNo);
							postingStatus = ((String) postingTokens.get("PostingStatus")).trim();
							if (postingStatus.equalsIgnoreCase("SUCCESS")) {
								postingTranID = ((String) postingTokens.get("TranID")).trim();
								logger.info("postingTranID:" + postingTranID);
								postingTransdate = ((String) postingTokens.get("Trandate")).trim();
								logger.info("postingTransdate:" + postingTransdate);
								fwdContractVO.setTransid(postingTranID);
								fwdContractVO.setTransdate(postingTransdate);
								String updatecanceltransdetails = UpdatecancelTransdetails(fwdContractVO, postingTranID,
										postingTransdate);
								logger.info("updatecanceltransdetails:" + updatecanceltransdetails);
								logger.info("postingStatus status --> " + postingStatus);
								con = DBConnectionUtility.getZoneConnection();
								loggableStatement = new LoggableStatement(con,
										"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=?,LIMIT_SERIAL_NUM=?,POSTING_TRAN_ID=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
								loggableStatement.setString(1, "APPROVED");
								loggableStatement.setString(2, userId.trim());
								loggableStatement.setString(3, remarks);
								loggableStatement.setString(4, "Approved");
								loggableStatement.setString(5, limitUnblockedID);
								loggableStatement.setString(6, postingTranID);
								loggableStatement.setString(7, category);
								loggableStatement.setString(8, fwdContractNo);
								loggableStatement.setInt(9, Integer.valueOf(Id).intValue());
								logger.info("UPDATE Details WithoutRate (" + category + "): "
										+ loggableStatement.getQueryString());
								int count = loggableStatement.executeUpdate();
								if (count > 0) {
									logger.info("Approved Successfully");
									fwdContractVO.setCount(count);
								}
							} else {
								int count = updateFailedStatus(fwdContractVO, category);
								if (count > 0)
									fwdContractVO.setCount(2);
							}
						} else {
							int count = updateFailedStatus(fwdContractVO, category);
							if (count > 0)
								fwdContractVO.setCount(3);
						}
					} else {
						int count = updateFailedStatus(fwdContractVO, category);
						if (count > 0)
							fwdContractVO.setCount(7);
					}
				} else {
					// Balance insufficient — set pending
					int count = updatePendingStatus(fwdContractVO, category);
					if (count > 0)
						fwdContractVO.setCount(5);
				}
			} else {
				// Booking date mismatch — reject
				int count = updateRejectStatus(fwdContractVO, category);
				if (count > 0)
					fwdContractVO.setCount(6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
		}
		logger.info("Exiting Method");
		return fwdContractVO;
	}
		 

	/*      */
	/*      */
	/*      */ public int updateFailedStatus(ForwardContractVO fwdContractVO, String category) throws DAOException {
		/* 4677 */ logger.info("Entering Method");
		/* 4678 */ Connection con = null;
		/* 4679 */ LoggableStatement loggableStatement = null;
		/* 4680 */ ResultSet rs = null;
		/* 4681 */ int count = 0;
		/*      */
		/*      */ try {
			/* 4684 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4685 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4686 */ String userId = request.getRemoteUser();
			/* 4687 */ if (userId == null) {
				/* 4688 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4691 */ String Id = fwdContractVO.getId().trim();
			/*      */
			/* 4693 */ con = DBConnectionUtility.getZoneConnection();
			/* 4694 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
			/* 4695 */ loggableStatement.setString(1, "FAILED");
			/* 4696 */ loggableStatement.setString(2, userId.trim());
			/* 4697 */ loggableStatement.setString(3, fwdContractVO.getRemarks());
			/* 4698 */ loggableStatement.setString(4, "Failed");
			/* 4699 */ loggableStatement.setString(5, category);
			/* 4700 */ loggableStatement.setString(6, fwdContractVO.getFwdContractNo());
			/* 4701 */ loggableStatement.setInt(7, Integer.valueOf(Id).intValue());
			/* 4702 */ logger.info("Update as Failed : " + loggableStatement.getQueryString());
			/*      */
			/* 4704 */ count = loggableStatement.executeUpdate();
			/*      */
			/* 4706 */ if (count > 0) {
				/* 4707 */ logger.info("Updated Successfully");
				/*      */ }
			/* 4709 */ } catch (SQLException e) {
			/* 4710 */ e.printStackTrace();
			/*      */ } finally {
			/* 4712 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 4714 */ logger.info("Exiting Method");
		/* 4715 */ return count;
		/*      */ }

	/*      */
	/*      */ public int updatePendingStatus(ForwardContractVO fwdContractVO, String category) throws DAOException {
		/* 4719 */ logger.info("Entering Method");
		/* 4720 */ Connection con = null;
		/* 4721 */ LoggableStatement loggableStatement = null;
		/* 4722 */ ResultSet rs = null;
		/* 4723 */ int count = 0;
		/*      */
		/*      */ try {
			/* 4726 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4727 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4728 */ String userId = request.getRemoteUser();
			/* 4729 */ if (userId == null) {
				/* 4730 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4733 */ String Id = fwdContractVO.getId().trim();
			/*      */
			/* 4735 */ con = DBConnectionUtility.getZoneConnection();
			/* 4736 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET STATUS =?,CHECKER_ACTION_BY=?,CHECKER_ACTION_TIMESTAMP=SYSTIMESTAMP,REMARKS=?,LAST_ACTION=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=? ");
			/* 4737 */ loggableStatement.setString(1, "PENDING FOR APPROVAL");
			/* 4738 */ loggableStatement.setString(2, userId.trim());
			/* 4739 */ loggableStatement.setString(3, fwdContractVO.getRemarks());
			/* 4740 */ loggableStatement.setString(4, "Failed");
			/* 4741 */ loggableStatement.setString(5, category);
			/* 4742 */ loggableStatement.setString(6, fwdContractVO.getFwdContractNo());
			/* 4743 */ loggableStatement.setInt(7, Integer.valueOf(Id).intValue());
			/* 4744 */ logger.info("Update as Pending for Approval : " + loggableStatement.getQueryString());
			/*      */
			/* 4746 */ count = loggableStatement.executeUpdate();
			/*      */
			/* 4748 */ if (count > 0) {
				/* 4749 */ logger.info("Updated Successfully");
				/*      */ }
			/* 4751 */ } catch (SQLException e) {
			/* 4752 */ e.printStackTrace();
			/*      */ } finally {
			/* 4754 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 4756 */ logger.info("Exiting Method");
		/* 4757 */ return count;
		/*      */ }

	/*      */
	/*      */ public static String UpdatecancelTransdetails(ForwardContractVO fwdContractVO, String postingTranID,
			String postingTransdate) {
		/* 4761 */ String status = null;
		/* 4762 */ logger.info("Enter into Update trans details");
		/* 4763 */ Connection con = null;
		/* 4764 */ LoggableStatement loggableStatement = null;
		/* 4765 */ ResultSet rs = null;
		/* 4766 */ int count = 0;
		/*      */
		/*      */
		/*      */ try {
			/* 4770 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4771 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4772 */ String userId = request.getRemoteUser();
			/* 4773 */ if (userId == null) {
				/* 4774 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4777 */ String Id = fwdContractVO.getId().trim();
			/*      */
			/* 4779 */ con = DBConnectionUtility.getZoneConnection();
			/* 4780 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET TRANS_ID =?,TRANS_DATE=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=?");
			/* 4781 */ logger.info(
					"Update Query :UPDATE CUSTOM_FWC_DETAILS SET TRANS_ID =?,TRANS_DATE=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=?");
			/* 4782 */ loggableStatement.setString(1, postingTranID);
			/* 4783 */ loggableStatement.setString(2, postingTransdate);
			/* 4784 */ loggableStatement.setString(3, "FWCCANCEL");
			/* 4785 */ loggableStatement.setString(4, fwdContractVO.getFwdContractNo());
			/* 4786 */ loggableStatement.setString(5, Id);
			/* 4787 */ count = loggableStatement.executeUpdate();
			/*      */
			/* 4789 */ if (count > 0) {
				/* 4790 */ logger.info("Updated Successfully");
				/* 4791 */ status = "success";
				/*      */ }
			/*      */
			/* 4794 */ } catch (Exception e) {
			/* 4795 */ e.printStackTrace();
			/* 4796 */ logger.info(e.getMessage());
			/*      */ } finally {
			/* 4798 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 4800 */ logger.info("Exiting Method");
		/* 4801 */ return status;
		/*      */ }

	/*      */
	/*      */
	/*      */ public static String UpdatebookTransdetails(ForwardContractVO fwdContractVO, String postingTranID,
			String postingTransdate) {
		/* 4806 */ String status = null;
		/* 4807 */ logger.info("Enter into Update trans details");
		/* 4808 */ Connection con = null;
		/* 4809 */ LoggableStatement loggableStatement = null;
		/* 4810 */ ResultSet rs = null;
		/* 4811 */ int count = 0;
		/*      */
		/*      */
		/*      */ try {
			/* 4815 */ HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
					/* 4816 */ .get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
			/* 4817 */ String userId = request.getRemoteUser();
			/* 4818 */ if (userId == null) {
				/* 4819 */ userId = "SUPERVISOR";
				/*      */ }
			/*      */
			/* 4822 */ String Id = fwdContractVO.getId().trim();
			/*      */
			/* 4824 */ con = DBConnectionUtility.getZoneConnection();
			/* 4825 */ loggableStatement = new LoggableStatement(con,
					"UPDATE CUSTOM_FWC_DETAILS SET TRANS_ID =?,TRANS_DATE=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=?");
			/* 4826 */ logger.info(
					"Update Query :UPDATE CUSTOM_FWC_DETAILS SET TRANS_ID =?,TRANS_DATE=? WHERE CATEGORY=? AND FWC_CONTRACT_NO=? AND ID=?");
			/* 4827 */ loggableStatement.setString(1, postingTranID);
			/* 4828 */ loggableStatement.setString(2, postingTransdate);
			/* 4829 */ loggableStatement.setString(3, "FWCBOOK");
			/* 4830 */ loggableStatement.setString(4, fwdContractVO.getFwdContractNo());
			/* 4831 */ loggableStatement.setString(5, Id);
			/* 4832 */ count = loggableStatement.executeUpdate();
			/*      */
			/* 4834 */ if (count > 0) {
				/* 4835 */ logger.info("Updated Successfully");
				/* 4836 */ status = "success";
				/*      */ }
			/*      */
			/* 4839 */ } catch (Exception e) {
			/* 4840 */ e.printStackTrace();
			/* 4841 */ logger.info(e.getMessage());
			/*      */ } finally {
			/* 4843 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 4845 */ logger.info("Exiting Method");
		/* 4846 */ return status;
		/*      */ }

	/*      */
	/*      */
	/*      */ public static String readFileInpStream(InputStream inpStream) throws IOException {
		/* 4851 */ String result = "";
		/* 4852 */ BufferedReader buffReader = null;
		/*      */
		/*      */ try {
			/* 4855 */ String line = null;
			/* 4856 */ StringBuilder strBuilder = new StringBuilder();
			/* 4857 */ String newLineSeparator = System.getProperty("line.separator");
			/* 4858 */ buffReader = new BufferedReader(new InputStreamReader(inpStream));
			/*      */
			/* 4860 */ int lineCount = 1;
			/* 4861 */ while ((line = buffReader.readLine()) != null) {
				/* 4862 */ if (lineCount > 1)
					/* 4863 */ strBuilder.append(newLineSeparator);
				/* 4864 */ strBuilder.append(line);
				/* 4865 */ lineCount++;
				/*      */ }
			/*      */
			/* 4868 */ result = strBuilder.toString();
			/*      */ }
		/* 4870 */ catch (IOException ex) {
			/* 4871 */ logger.error("IOException " + ex.getMessage());
			/* 4872 */ ex.printStackTrace();
			/*      */ } finally {
			/*      */
			/*      */ try {
				/* 4876 */ if (buffReader != null)
					/* 4877 */ buffReader.close();
				/* 4878 */ } catch (IOException ex) {
				/* 4879 */ logger.error("BufferedReader close exception! " + ex.getMessage());
				/* 4880 */ ex.printStackTrace();
				/*      */ }
			/*      */ }
		/*      */
		/* 4884 */ return result;
		/*      */ }

	/*      */
	/*      */ public boolean executeGenericQuery(String query, String parameters) {
		/* 4888 */ Connection connection = null;
		/* 4889 */ LoggableStatement loggableStatement = null;
		/* 4890 */ ResultSet resultSet = null;
		/*      */ try {
			/* 4892 */ int parameterCount = 1;
			/* 4893 */ connection = DBConnectionUtility.getZoneConnection();
			/* 4894 */ loggableStatement = new LoggableStatement(connection, query);
			/* 4895 */ if (parameters instanceof String && !parameters.trim().equalsIgnoreCase("")) {
				/* 4896 */ byte b;
				int i;
				String[] arrayOfString;
				for (i = (arrayOfString = parameters.split("\\|")).length, b = 0; b < i;) {
					String invidualParameters = arrayOfString[b];
					/* 4897 */ loggableStatement.setString(parameterCount, invidualParameters);
					/* 4898 */ parameterCount++;
					b++;
				}
				/*      */
				/*      */ }
			/* 4901 */ logger.info("Query ----------->" + loggableStatement.getQueryString());
			/* 4902 */ resultSet = loggableStatement.executeQuery();
			/* 4903 */ while (resultSet.next()) {
				/* 4904 */ if (resultSet.getInt(1) > 0) {
					/* 4905 */ return true;
					/*      */ }
				/*      */ }
			/* 4908 */ } catch (Exception e) {
			/* 4909 */ e.printStackTrace();
			/*      */ } finally {
			/* 4911 */ DBConnectionUtility.surrenderDB(connection, (Statement) loggableStatement, resultSet);
			/*      */ }
		/*      */
		/* 4914 */ return false;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String getTICurrentDate() {
		/* 4919 */ String tiCurrDate = "";
		/* 4920 */ ResultSet rs = null;
		/* 4921 */ Connection con = null;
		/* 4922 */ PreparedStatement ps = null;
		/* 4923 */ Map<String, Object> session = ActionContext.getContext().getSession();
		/* 4924 */ String query = "SELECT to_char(PROCDATE,'dd/mm/yyyy') as PROCDATE FROM DLYPRCCYCL ";
		/*      */
		/*      */ try {
			/* 4927 */ con = DBConnectionUtility.getZoneConnection();
			/* 4928 */ ps = con.prepareStatement(query);
			/* 4929 */ rs = ps.executeQuery();
			/*      */
			/* 4931 */ while (rs.next()) {
				/* 4932 */ tiCurrDate = rs.getString(1);
				/* 4933 */ logger.info("tiCurrDate====>" + tiCurrDate);
				/*      */
				/* 4935 */ session.put("processDate", tiCurrDate);
				/*      */ }
			/* 4937 */ logger.info("TIDATE-----" + tiCurrDate);
			/* 4938 */ } catch (Exception e) {
			/*      */
			/* 4940 */ logger.info("tiCurrDate=== Exception=>" + e);
			/* 4941 */ e.printStackTrace();
			/*      */ } finally {
			/*      */
			/* 4944 */ DBConnectionUtility.surrenderDB(con, ps, rs);
			/*      */ }
		/*      */
		/* 4947 */ return tiCurrDate;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String getTICurrentDateFormat() {
		/* 4952 */ String tiCurrDate = "";
		/* 4953 */ ResultSet rs = null;
		/* 4954 */ Connection con = null;
		/* 4955 */ PreparedStatement ps = null;
		/* 4956 */ Map<String, Object> session = ActionContext.getContext().getSession();
		/* 4957 */ String query = "SELECT to_char(PROCDATE,'yyyy/mm/dd') as PROCDATE FROM DLYPRCCYCL ";
		/*      */
		/*      */ try {
			/* 4960 */ con = DBConnectionUtility.getZoneConnection();
			/* 4961 */ ps = con.prepareStatement(query);
			/* 4962 */ rs = ps.executeQuery();
			/*      */
			/* 4964 */ while (rs.next()) {
				/* 4965 */ tiCurrDate = rs.getString(1);
				/* 4966 */ logger.info("tiCurrDate====>" + tiCurrDate);
				/*      */
				/* 4968 */ session.put("processDate", tiCurrDate);
				/*      */ }
			/* 4970 */ logger.info("TIDATE-----" + tiCurrDate);
			/* 4971 */ } catch (Exception e) {
			/*      */
			/* 4973 */ logger.info("tiCurrDate=== Exception=>" + e);
			/* 4974 */ e.printStackTrace();
			/*      */ } finally {
			/*      */
			/* 4977 */ DBConnectionUtility.surrenderDB(con, ps, rs);
			/*      */ }
		/*      */
		/* 4980 */ return tiCurrDate;
		/*      */ }

	/*      */
	/*      */ public String checkFWC_Status(String treRefNo, String cifID, String forwardnum) {
		/* 4984 */ String fwcStatusNContractNo = "";
		/* 4985 */ String fwcStatus = "";
		/* 4986 */ String fwcnum = "";
		/* 4987 */ String query = null;
		/* 4988 */ ResultSet resultSet = null;
		/* 4989 */ Connection tiZoneConnection = null;
		/* 4990 */ PreparedStatement preparedStatement = null;
		/*      */
		/*      */ try {
			/* 4993 */ query = "SELECT * FROM CUSTOM_FWC_DETAILS WHERE TREASURY_REF_NO =? AND CIF_ID=?";
			/*      */
			/* 4995 */ tiZoneConnection = DBConnectionUtility.getZoneConnection();
			/* 4996 */ preparedStatement = tiZoneConnection.prepareStatement(query);
			/* 4997 */ preparedStatement.setString(1, treRefNo);
			/* 4998 */ preparedStatement.setString(2, cifID);
			/* 4999 */ resultSet = preparedStatement.executeQuery();
			/* 5000 */ while (resultSet.next()) {
				/* 5001 */ fwcStatus = resultSet.getString("STATUS");
				/* 5002 */ fwcnum = resultSet.getString("FWC_CONTRACT_NO");
				/*      */ }
			/* 5004 */ fwcStatusNContractNo = String.valueOf(fwcStatus) + "-" + fwcnum;
			/* 5005 */ logger.info("checkFWC_Status result fwcStatus & ContractNo : " + fwcStatusNContractNo);
			/*      */
			/*      */ }
		/* 5008 */ catch (SQLException e) {
			/* 5009 */ e.printStackTrace();
			/*      */ } finally {
			/* 5011 */ DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
			/*      */ }
		/* 5013 */ return fwcStatusNContractNo;
		/*      */ }

	/*      */
	/*      */
	/*      */ public String getBookHostDealCategoryFromTreasury(String treRefNo, String cifID) {
		/* 5018 */ logger.info("Entering Method");
		/* 5019 */ LoggableStatement pst = null;
		/* 5020 */ ResultSet rs = null;
		/* 5021 */ Connection con = null;
		/* 5022 */ String treas_Host_Deal_Category = null;
		/* 5023 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */ try {
			/* 5025 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 5026 */ String query = "SELECT HOST_DEAL_SUB_CATEGORY FROM " + treasuryHDDTableName
					+ " WHERE HOST_DEAL_CATEGORY='FXRATE' AND RECORD_STATUS='TRANSFER' AND REFERENCE_NUM=? AND COUNTERPARTY_STRING=? ";
			/* 5027 */ logger.info("Query:" + query);
			/* 5028 */ pst = new LoggableStatement(con, query);
			/* 5029 */ pst.setString(1, treRefNo);
			/* 5030 */ pst.setString(2, cifID);
			/* 5031 */ rs = pst.executeQuery();
			/* 5032 */ while (rs.next()) {
				/* 5033 */ treas_Host_Deal_Category = rs.getString("HOST_DEAL_SUB_CATEGORY");
				/*      */ }
			/*      */
			/* 5036 */ logger.info("Host Deal category:" + treas_Host_Deal_Category);
			/* 5037 */ } catch (Exception e) {
			/* 5038 */ e.printStackTrace();
			/*      */ } finally {
			/* 5040 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/*      */
		/* 5043 */ return treas_Host_Deal_Category;
		/*      */ }

	/*      */
	/*      */ public String getCancelHostDealCategoryFromTreasury(String treRefNo, String cifID) {
		/* 5047 */ logger.info("Entering Method");
		/* 5048 */ LoggableStatement pst = null;
		/* 5049 */ ResultSet rs = null;
		/* 5050 */ Connection con = null;
		/* 5051 */ String treas_Host_Deal_Category = null;
		/* 5052 */ treasuryHDDTableName = (String) ServiceUtility.TBProperties.get("TreasuryHDDTable");
		/*      */ try {
			/* 5054 */ con = DBConnectionUtility.getDBLinkConnection();
			/* 5055 */ String query = "SELECT HOST_DEAL_CATEGORY FROM " + treasuryHDDTableName
					+ " WHERE HOST_DEAL_CATEGORY='FWCCANCEL' AND RECORD_STATUS='TRANSFER' AND REFERENCE_NUM=? AND COUNTERPARTY_STRING=? ";
			/* 5056 */ logger.info("Query:" + query);
			/* 5057 */ pst = new LoggableStatement(con, query);
			/* 5058 */ pst.setString(1, treRefNo);
			/* 5059 */ pst.setString(2, cifID);
			/* 5060 */ rs = pst.executeQuery();
			/* 5061 */ while (rs.next()) {
				/* 5062 */ treas_Host_Deal_Category = rs.getString("HOST_DEAL_CATEGORY");
				/*      */ }
			/*      */
			/* 5065 */ logger.info("Host Deal category:" + treas_Host_Deal_Category);
			/* 5066 */ } catch (Exception e) {
			/* 5067 */ e.printStackTrace();
			/*      */ } finally {
			/* 5069 */ DBConnectionUtility.surrenderDB(con, (Statement) pst, rs);
			/*      */ }
		/*      */
		/* 5072 */ return treas_Host_Deal_Category;
		/*      */ }

	/*      */
	/*      */ public int getRecordCountFromDB(ForwardContractVO fwdContractVO, String category) {
		/* 5076 */ Connection con = null;
		/* 5077 */ LoggableStatement loggableStatement = null;
		/* 5078 */ ResultSet rs = null;
		/* 5079 */ int count = 0;
		/*      */ try {
			/* 5081 */ if (CommonMethods.isValidString(fwdContractVO.getFwdContractNo())) {
				/* 5082 */ con = DBConnectionUtility.getZoneConnection();
				/* 5083 */ loggableStatement = new LoggableStatement(con,
						"SELECT COUNT(*) AS COUNT FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO=? AND CATEGORY=? AND ID=?");
				/* 5084 */ loggableStatement.setString(1, fwdContractVO.getFwdContractNo());
				/* 5085 */ loggableStatement.setString(2, category);
				/*      */
				/* 5087 */ if (CommonMethods.isValidString(fwdContractVO.getId())) {
					/* 5088 */ loggableStatement.setInt(3, Integer.valueOf(fwdContractVO.getId()).intValue());
					/*      */ } else {
					/* 5090 */ loggableStatement.setInt(3, 0);
					/*      */ }
				/*      */
				/* 5093 */ logger
						.info("getRecordCountFromDB Query----------------->" + loggableStatement.getQueryString());
				/*      */
				/* 5095 */ rs = loggableStatement.executeQuery();
				/* 5096 */ if (rs.next()) {
					/* 5097 */ count = rs.getInt("COUNT");
					/* 5098 */ logger.info("Record Count from dB --> " + count);
					/*      */ }
				/* 5100 */ logger.info("getRecordCountFromDB --> " + count);
				/*      */ }
			/* 5102 */ } catch (Exception exception) {
			/* 5103 */ exception.printStackTrace();
			/*      */ } finally {
			/* 5105 */ DBConnectionUtility.surrenderDB(con, (Statement) loggableStatement, rs);
			/*      */ }
		/* 5107 */ return count;
		/*      */ }
	/*      */ }

/*
 * Location: C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626
 * (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\dao\
 * ForwardContractDAO.class Java compiler version: 8 (52.0) JD-Core Version:
 * 1.1.3
 */