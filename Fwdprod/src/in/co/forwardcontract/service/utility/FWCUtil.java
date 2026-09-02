/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import in.co.forwardcontract.vo.ForwardContractVO;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
///*     */ import java.util.Date; 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class FWCUtil
/*     */ {
	
/*  26 */   private static String treasuryHDDTableName = null;
/*     */   
/*  28 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.FWCUtil.class);
/*     */ 
/*     */   
/*     */   public static Map<String, String> insertFTIFwdContractDetails(ForwardContractVO fwdContractVO, String userId, String category) {
/*  32 */     logger.info("ForwardContractVO ---> " + fwdContractVO.toString());
/*  33 */     logger.info("Treasury Rate ---> " + fwdContractVO.getTreasuryRate());
/*  34 */     logger.info("FWD Ref Num ---> " + fwdContractVO.getFwdContractNo());
/*  35 */     Map<String, String> baseRecordTokens = new HashMap<>();
/*  36 */     Map<String, String> insertInFTIStatus = new HashMap<>();
/*     */     
/*  38 */     ServiceUtility.getProperties();
/*  39 */     treasuryHDDTableName = (String)ServiceUtility.TBProperties.get("TreasuryHDDTable");
/*  40 */     int count = 0;
/*  41 */     String sequence = "";
/*     */     
/*  43 */     insertInFTIStatus.put("SequenceNo", sequence);
/*  44 */     insertInFTIStatus.put("Count", String.valueOf(count));
/*     */     
/*     */     try {
/*  47 */       logger.info("Screen Type  --> " + fwdContractVO.getScreenType());
/*     */ 
/*     */       
/*  50 */       String subProduct = fwdContractVO.getSubProduct().trim();
/*  51 */       String treasuryRefNo = fwdContractVO.getTreasuryRefNo().trim();
/*  52 */       String treasuryRate = fwdContractVO.getTreasuryRate().trim();
/*  53 */       BigDecimal treasRate = new BigDecimal(treasuryRate);
/*  54 */       String fwdContractNo = fwdContractVO.getFwdContractNo().trim();
/*     */       
/*  56 */       String customerID = fwdContractVO.getCustomerID().trim();
/*     */       
/*  58 */       String branch = fwdContractVO.getBranchCode().trim();
/*     */       
/*  60 */       String toAmountCcy = fwdContractVO.getToCurrencyAmt().trim();
/*  61 */       String toCcy = toAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
/*  62 */       String toAmount = toAmountCcy.trim().replaceAll("[^0-9.]", "");
/*  63 */       BigDecimal toAmt = new BigDecimal(toAmount);
/*     */       
/*  65 */       String fwdContractAmount = fwdContractVO.getFwdContractAmt().trim();
/*  66 */       String dealCcy = fwdContractAmount.trim().replaceAll("[^A-Za-z]+", "");
/*  67 */       String dealAmount = fwdContractAmount.trim().replaceAll("[^0-9.]", "");
/*  68 */       BigDecimal dealAmt = new BigDecimal(dealAmount);
/*     */       
/*  70 */       String bookingDate = fwdContractVO.getBookingDate().trim();
/*  71 */       bookingDate = CommonMethods.convertToStringDateFormat(bookingDate, "dd/MM/yyyy", "yyyy-MM-dd");
/*     */       
/*  73 */       String dealDirection = null;
/*  74 */       String txnType = null;
/*  75 */       BigDecimal purchaseAmt = new BigDecimal(0);
/*  76 */       BigDecimal saleAmt = new BigDecimal(0);
/*  77 */       String saleCcy = null;
/*  78 */       String purchaseCcy = null;
/*     */       
/*  80 */       String selectDealCategory = null;
/*  81 */       String selectQueryFromTreasury = null;
/*  82 */       String insertQueryIntoFTI = null;
/*     */       
/*  84 */       if (subProduct.contains("Sale")) {
/*  85 */         dealDirection = "S";
/*  86 */         if (category.equalsIgnoreCase("FWCBOOK")) {
/*  87 */           txnType = "MFS";
/*  88 */         } else if (category.equalsIgnoreCase("FWCCANCEL")) {
/*  89 */           txnType = "MFSCAN";
/*  90 */         }  saleAmt = dealAmt;
/*  91 */         saleCcy = dealCcy;
/*  92 */         purchaseAmt = toAmt;
/*  93 */         purchaseCcy = toCcy;
/*  94 */       } else if (subProduct.contains("Purchase")) {
/*  95 */         dealDirection = "B";
/*  96 */         if (category.equalsIgnoreCase("FWCBOOK")) {
/*  97 */           txnType = "MFP";
/*  98 */         } else if (category.equalsIgnoreCase("FWCCANCEL")) {
/*  99 */           txnType = "MFPCAN";
/* 100 */         }  purchaseAmt = dealAmt;
/* 101 */         purchaseCcy = dealCcy;
/* 102 */         saleAmt = toAmt;
/* 103 */         saleCcy = toCcy;
/*     */       } 
/*     */       
/* 106 */       if (category.equalsIgnoreCase("FWCBOOK")) {
/* 107 */         selectDealCategory = "FXRATE";
/* 108 */       } else if (category.equalsIgnoreCase("FWCCANCEL")) {
/* 109 */         selectDealCategory = "FWCCANCEL";
/*     */       } 
/* 111 */       selectQueryFromTreasury = "SELECT TO_CHAR(HOST_TRAN_DATE,'YYYY-MM-DD') AS HOST_TRAN_DATE,TO_CHAR(START_DATE) AS START_DATE, TO_CHAR(END_DATE) AS END_DATE, TO_CHAR(VALUE_DATE,'YYYY-MM-DD') AS VALUE_DATE, DEAL_AMOUNT,DEAL_AMOUNT_CCY,FWD_CONTRACT_RATE,COUNTERPARTY_STRING,SOL_ID,ADDITIONAL_TEXT_1 AS TRAN_TYPE , BUY_OR_SELL,BUY_AMOUNT,BUY_AMOUNT_CCY,SELL_AMOUNT,SELL_AMOUNT_CCY,FWC_REF_NUM FROM " + 
/*     */ 
/*     */         
/* 114 */         treasuryHDDTableName + 
/* 115 */         " WHERE HOST_DEAL_CATEGORY=? AND COUNTERPARTY_STRING IS NOT NULL AND RECORD_STATUS='TRANSFER' AND REFERENCE_NUM = ? ";
/*     */       
/* 117 */       baseRecordTokens = getBaseRecordDetailsFromHDDTable(selectDealCategory, treasuryRefNo, bookingDate, 
/* 118 */           bookingDate, dealAmt, dealCcy, treasRate, customerID, branch, txnType, fwdContractNo, selectQueryFromTreasury);
/*     */       
/* 120 */       String recordStatus = baseRecordTokens.get("RecordStatus");
/* 121 */       String recordStatusReason = baseRecordTokens.get("RecordStatusReason");
/* 122 */       String startDate = baseRecordTokens.get("StartDate");
/* 123 */       String endDate = baseRecordTokens.get("EndDate");
/*     */       
/* 125 */       String panNumber = getPanNumberOfCustomer(customerID);
/*     */       
/* 127 */       sequence = getNextFBONumSeq();
/*     */       
/* 129 */       logger.info("sequence : " + sequence);
/*     */       
/* 131 */       Date bookingDateSqlFormat = new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(bookingDate).getTime());
/*     */       
/* 133 */       logger.info("bookingDate : " + bookingDate);
/*     */       
/* 135 */       insertQueryIntoFTI = "INSERT INTO CUSTOM_TREASURY_INSERT_TBL( FBO_ID_TYPE, FBO_ID_NUM, FBO_ID_VER, HOST_DEAL_CATEGORY, HOST_DEAL_ACTION, HOST_TRAN_DATE, CAPTURE_TIMESTAMP, UPDATE_TIMESTAMP, RECORD_STATUS, ENTRY_USER_ID, ACCEPT_USER_ID, BUY_OR_SELL, VALUE_DATE, DEAL_AMOUNT, DEAL_AMOUNT_CCY, BUY_AMOUNT, BUY_AMOUNT_CCY, SELL_AMOUNT, SELL_AMOUNT_CCY, FWD_CONTRACT_RATE, CONTRACT_RATE, LBS_RATE, INTERNAL_RATE,REFERENCE_NUM, COUNTERPARTY_STRING,START_DATE,END_DATE, SOL_ID, BILL_ID, FWC_REF_NUM, RATE_CODE, DEAL_REF, DEAL_VERSION, ADDITIONAL_TEXT_1, ADDITIONAL_TEXT_2, ADDITIONAL_TEXT_3, ADDITIONAL_TEXT_4, CONSOLIDATE_FLAG, ENTITY_FBO_ID_NUM, ADDITIONAL_TEXT_10) values ('HDEALD', ? , 1, ?, 'ACCEPT', ?, SYSDATE, SYSDATE, ?, ?, 'SYSTEM', ?,  TO_DATE(?,'dd-MM-yy'), ?, ?, ?, ?, ?, ?,  ?, 0, 0,?, ?, ?,TO_DATE(?,'dd-MM-yy'),TO_DATE(?,'dd-MM-yy'), ?, ?, ?, 'TTB',  0, 0, ?, 'FINASTRA', ?, ?, 'Y',1,'Y') ";
/*     */ 
/*     */       
/* 138 */       if (category != null && category.equalsIgnoreCase("FWCCANCEL")) {
/* 139 */         if (baseRecordTokens.get("BUYAMOUNT") != null)
/* 140 */           purchaseAmt = new BigDecimal(baseRecordTokens.get("BUYAMOUNT")); 
/* 141 */         if (baseRecordTokens.get("SELLAMOUNT") != null)
/* 142 */           saleAmt = new BigDecimal(baseRecordTokens.get("SELLAMOUNT")); 
/* 143 */         purchaseCcy = baseRecordTokens.get("BUYCCY");
/* 144 */         saleCcy = baseRecordTokens.get("SELLCCY");
/* 145 */         dealDirection = baseRecordTokens.get("BUYORSELL");
/*     */       } 
/*     */       
/* 148 */       if (StringUtils.equalsIgnoreCase(recordStatus, "MATCHED"))
/*     */       {
/* 150 */         count = insertFWCDetailsInFTI(category, sequence, bookingDateSqlFormat, recordStatus, userId, dealDirection, 
/* 151 */             endDate, dealAmt, dealCcy, purchaseAmt, purchaseCcy, saleAmt, saleCcy, treasRate, treasuryRefNo, 
/* 152 */             customerID, startDate, branch, fwdContractNo, txnType, panNumber, recordStatusReason, "", 
/* 153 */             insertQueryIntoFTI);
/*     */       }
/*     */ 
/*     */       
/* 157 */       insertInFTIStatus.put("SequenceNo", sequence);
/* 158 */       insertInFTIStatus.put("Count", String.valueOf(count));
/*     */     }
/* 160 */     catch (Exception e) {
/* 161 */       logger.info("Exception in insertFTIFwdContractDetails() : " + e.getMessage());
/* 162 */       e.printStackTrace();
/*     */     } 
/* 164 */     return insertInFTIStatus;
/*     */   }

	// ABHISHEK

public static Map<String, String> insertFTIFwdContractDetailsWithoutRate(ForwardContractVO fwdContractVO,
	    String userId, String category) {
	  logger.info("ForwardContractVO ---> " + fwdContractVO.toString());
	  logger.info("Treasury Rate ---> " + fwdContractVO.getTreasuryRate());
	  logger.info("FWD Ref Num ---> " + fwdContractVO.getFwdContractNo());

	  Map<String, String> baseRecordTokens = new HashMap<>();
	  Map<String, String> insertInFTIStatus = new HashMap<>();

	  int count = 0;
	  String sequence = "";

	  insertInFTIStatus.put("SequenceNo", sequence);
	  insertInFTIStatus.put("Count", String.valueOf(count));

	  try {
	    logger.info("Screen Type  --> " + fwdContractVO.getScreenType());

	    String subProduct = fwdContractVO.getSubProduct().trim();
	    String treasuryRefNo = fwdContractVO.getTreasuryRefNo().trim();
	    String treasuryRate = fwdContractVO.getTreasuryRate().trim();
	    BigDecimal treasRate = new BigDecimal(treasuryRate);
	    String fwdContractNo = fwdContractVO.getFwdContractNo().trim();
	    String customerID = fwdContractVO.getCustomerID().trim();
	    String branch = fwdContractVO.getBranchCode().trim();

	    // FIX 1: Use cancellationamount instead of fwdContractAmt
	    String fwdContractAmount = fwdContractVO.getCancellationamount().trim(); // e.g. 2500 USD
	    String dealCcy = fwdContractAmount.trim().replaceAll("[^A-Za-z]+", ""); // USD
	    String dealAmount = fwdContractAmount.trim().replaceAll("[^0-9.]", ""); // 2500
	    BigDecimal dealAmt = new BigDecimal(dealAmount);
	    logger.info("Without Rate FTI insert — dealAmt (maker entered): " + dealAmt + " " + dealCcy);

	    // FIX 2: Recalculate toAmt from dealAmt * treasRate
	    String toCcy = "INR";
	    BigDecimal toAmt = dealAmt.multiply(treasRate).setScale(4, RoundingMode.HALF_UP);
	    logger.info("Without Rate FTI insert — toAmt (recalculated): " + toAmt + " " + toCcy);

	    String bookingDate = fwdContractVO.getBookingDate().trim();
	    bookingDate = CommonMethods.convertToStringDateFormat(bookingDate, "dd/MM/yyyy", "yyyy-MM-dd");

	    String dealDirection = null;
	    String txnType = null;
	    BigDecimal purchaseAmt = new BigDecimal(0);
	    BigDecimal saleAmt = new BigDecimal(0);
	    String saleCcy = null;
	    String purchaseCcy = null;
	    String selectDealCategory = null;
	    String selectQueryFromTreasury = null;
	    String insertQueryIntoFTI = null;

	    if (subProduct.contains("Sale")) {
	      dealDirection = "S";
	      if (category.equalsIgnoreCase("FWCCANCEL")) {
	        txnType = "MFSCAN";
	      } else if (category.equalsIgnoreCase("FWCUTIL")) {
	        txnType = "FTT";
	      }
	      saleAmt = dealAmt;          // 2500 USD
	      saleCcy = dealCcy;          // USD
	      purchaseAmt = toAmt;        // 210081.25 INR
	      purchaseCcy = toCcy;        // INR

	    } else if (subProduct.contains("Purchase")) {
	      dealDirection = "B";
	      if (category.equalsIgnoreCase("FWCCANCEL")) {
	        txnType = "MFPCAN";
	      } else if (category.equalsIgnoreCase("FWCUTIL")) {
	        txnType = "INWR";
	      }
	      purchaseAmt = dealAmt;      // 2500 USD
	      purchaseCcy = dealCcy;      // USD
	      saleAmt = toAmt;            // 210081.25 INR
	      saleCcy = toCcy;            // INR
	    }

	    if (category.equalsIgnoreCase("FWCCANCEL")) {
	      selectDealCategory = "FWCCANCEL";
	      selectQueryFromTreasury = "SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') AS HOST_TRAN_DATE,"
	          + " TO_CHAR(START_DATE) AS START_DATE, TO_CHAR(END_DATE) AS END_DATE,"
	          + " TO_CHAR(SYSDATE,'YYYY-MM-DD') AS VALUE_DATE,"
	          + " CASE WHEN BUY_OR_SELL = 'B' THEN BUY_AMOUNT_OS ELSE SELL_AMOUNT_OS END DEAL_AMOUNT,"
	          + " DEAL_AMOUNT_CCY, FWD_CONTRACT_RATE, CUSTOMER AS COUNTERPARTY_STRING, SOL_ID,"
	          + " CASE WHEN BUY_OR_SELL = 'B' THEN 'MFPCAN' ELSE 'MFSCAN' END AS TRAN_TYPE,"
	          + " CASE WHEN BUY_OR_SELL = 'B' THEN 'S' ELSE 'B' END BUY_OR_SELL,"
	          + " SELL_AMOUNT_OS AS BUY_AMOUNT, SELL_AMOUNT_CCY AS BUY_AMOUNT_CCY,"
	          + " BUY_AMOUNT_OS AS SELL_AMOUNT, BUY_AMOUNT_CCY AS SELL_AMOUNT_CCY," 
	          + " FWC_REF_NUM"
	          + " FROM REP_FWC_OUTSTANDING_VIEW WHERE FWC_REF_NUM = ? ";

	    } else if (category.equalsIgnoreCase("FWCUTIL")) {
	      selectDealCategory = "FWCUTIL";
	      selectQueryFromTreasury = "SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') AS HOST_TRAN_DATE,"
	          + " TO_CHAR(START_DATE) AS START_DATE, TO_CHAR(END_DATE) AS END_DATE,"
	          + " TO_CHAR(SYSDATE,'YYYY-MM-DD') AS VALUE_DATE,"
	          + " CASE WHEN BUY_OR_SELL = 'B' THEN BUY_AMOUNT_OS ELSE SELL_AMOUNT_OS END DEAL_AMOUNT,"
	          + " DEAL_AMOUNT_CCY, FWD_CONTRACT_RATE, CUSTOMER AS COUNTERPARTY_STRING, SOL_ID,"
	          + " CASE WHEN BUY_OR_SELL = 'B' THEN 'INWR' ELSE 'FTT' END AS TRAN_TYPE," 
	          + " BUY_OR_SELL,"
	          + " BUY_AMOUNT_OS AS BUY_AMOUNT, BUY_AMOUNT_CCY,"
	          + " SELL_AMOUNT_OS AS SELL_AMOUNT, SELL_AMOUNT_CCY," 
	          + " FWC_REF_NUM"
	          + " FROM REP_FWC_OUTSTANDING_VIEW WHERE FWC_REF_NUM = ? ";
	    }

	    baseRecordTokens = getBaseRecordDetailsFromView(selectDealCategory, fwdContractNo, dealAmt, dealCcy,
	        customerID, branch, txnType, selectQueryFromTreasury);

	    String recordStatus = baseRecordTokens.get("RecordStatus");
	    String recordStatusReason = baseRecordTokens.get("RecordStatusReason");
	    String startDate = baseRecordTokens.get("StartDate");
	    String endDate = baseRecordTokens.get("EndDate");

	    String panNumber = getPanNumberOfCustomer(customerID);
	    sequence = getNextFBONumSeq();
	    logger.info("sequence : " + sequence);

	    Date bookingDateSqlFormat = new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(bookingDate).getTime());
	    logger.info("bookingDate : " + bookingDate);

	    insertQueryIntoFTI = "INSERT INTO CUSTOM_TREASURY_INSERT_TBL( FBO_ID_TYPE, FBO_ID_NUM, FBO_ID_VER, HOST_DEAL_CATEGORY, HOST_DEAL_ACTION, HOST_TRAN_DATE, CAPTURE_TIMESTAMP, UPDATE_TIMESTAMP, RECORD_STATUS, ENTRY_USER_ID, ACCEPT_USER_ID, BUY_OR_SELL, VALUE_DATE, DEAL_AMOUNT, DEAL_AMOUNT_CCY, BUY_AMOUNT, BUY_AMOUNT_CCY, SELL_AMOUNT, SELL_AMOUNT_CCY, FWD_CONTRACT_RATE, CONTRACT_RATE, LBS_RATE, INTERNAL_RATE,REFERENCE_NUM, COUNTERPARTY_STRING,START_DATE,END_DATE, SOL_ID, BILL_ID, FWC_REF_NUM, RATE_CODE, DEAL_REF, DEAL_VERSION, ADDITIONAL_TEXT_1, ADDITIONAL_TEXT_2, ADDITIONAL_TEXT_3, ADDITIONAL_TEXT_4, CONSOLIDATE_FLAG, ENTITY_FBO_ID_NUM, ADDITIONAL_TEXT_10) values ('HDEALD', ? , 1, ?, 'ACCEPT', ?, SYSDATE, SYSDATE, ?, ?, 'SYSTEM', ?,  TO_DATE(?,'dd-MM-yy'), ?, ?, ?, ?, ?, ?,  ?, 0, 0,?, ?, ?,TO_DATE(?,'dd-MM-yy'),TO_DATE(?,'dd-MM-yy'), ?, ?, ?, 'TTB',  0, 0, ?, 'FINASTRA', ?, ?, 'Y',1,'Y') ";

	    // FIX 3: ONLY override dealDirection, NEVER override purchaseCcy/saleCcy
	    if (category != null && (category.equalsIgnoreCase("FWCCANCEL") || category.equalsIgnoreCase("FWCUTIL"))) {
	      // Only override dealDirection from view (in case it's reversed for cancellation)
	      dealDirection = baseRecordTokens.get("BUYORSELL");

	      // DO NOT override purchaseCcy and saleCcy — they already have correct values!
	      // purchaseCcy = baseRecordTokens.get("BUYCCY");   // ← REMOVE THIS
	      // saleCcy = baseRecordTokens.get("SELLCCY");      // ← REMOVE THIS

	      logger.info("Without Rate FTI — purchaseAmt: " + purchaseAmt + " " + purchaseCcy);
	      logger.info("Without Rate FTI — saleAmt: " + saleAmt + " " + saleCcy);
	      logger.info("Without Rate FTI — dealDirection: " + dealDirection);
	    }

	    if (StringUtils.equalsIgnoreCase(recordStatus, "MATCHED")) {
	      count = insertFWCDetailsInFTI(category, sequence, bookingDateSqlFormat, recordStatus, userId,
	          dealDirection, endDate, dealAmt, dealCcy, purchaseAmt, purchaseCcy, saleAmt, saleCcy, treasRate,
	          treasuryRefNo, customerID, startDate, branch, fwdContractNo, txnType, panNumber,
	          recordStatusReason, "", insertQueryIntoFTI);
	    }

	    insertInFTIStatus.put("SequenceNo", sequence);
	    insertInFTIStatus.put("Count", String.valueOf(count));

	  } catch (Exception e) {
	    logger.info("Exception in insertFTIFwdContractDetailsWithoutRate() : " + e.getMessage());
	    e.printStackTrace();
	  }

	  return insertInFTIStatus;
	}
	 




/*     */   public static String getNextFBONumSeq() {
/* 167 */     String sourceRefSeq = "";
/* 168 */     ResultSet rs = null;
/* 169 */     Connection conn = null;
/* 170 */     PreparedStatement pst = null;
/*     */     try {
/* 172 */       conn = DBConnectionUtility.getZoneConnection();
/* 173 */       pst = conn.prepareStatement("Select FBO_ID_NUM_SEQ.nextval from dual");
/* 174 */       rs = pst.executeQuery();
/* 175 */       while (rs.next()) {
/* 176 */         sourceRefSeq = rs.getString("NEXTVAL");
/*     */       }
/* 178 */     } catch (Exception e) {
/* 179 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 182 */       DBConnectionUtility.surrenderDB(conn, pst, rs);
/*     */     } 
/* 184 */     System.out.println("FBO_ID_NUM_SEQ from getNextFBONumSeq() :----" + sourceRefSeq);
/* 185 */     return sourceRefSeq;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> getBaseRecordDetailsFromHDDTable(String dealCategory, String treasuryRefNo, String bookingDate, String bookingDate2, BigDecimal dealAmt, String dealCcy, BigDecimal treasRate, String customerID, String branch, String dealType, String forwardRefNum, String selectQueryFromTreasury) {
/* 191 */     Connection dbConnection = null;
/* 192 */     PreparedStatement preparedStatement = null;
/* 193 */     ResultSet resultSet = null;
/* 194 */     String matchedOrUnmatched = "UNMATCHED";
/* 195 */     String matchedOrUnmatchedReason = "UNMATCHED";
/* 196 */     String hostTranDate = null;
/* 197 */     String valueDate = null;
/* 198 */     String buyOrSell = null;
/* 199 */     BigDecimal dealAmount = null;
/* 200 */     BigDecimal fxRate = null;
/* 201 */     String cifId = null;
/* 202 */     String solId = null;
/* 203 */     String tranType = null;
/* 204 */     Map<String, String> baseRecordTokens = new HashMap<>();
/* 205 */     String startDate = null;
/* 206 */     String endDate = null;
/*     */     
/* 208 */     String buyAmount = null;
/* 209 */     String sellAmount = null;
/* 210 */     String buyCcy = null;
/* 211 */     String sellCcy = null;
/*     */     
/* 213 */     String fwcRefNum = null;
/*     */     
/*     */     try {
/* 216 */       logger.info("checkMatchedOrUnmatchedRecordStatus txnFxReference -->" + treasuryRefNo);
/*     */       
/* 218 */       logger.info("queryDetails -->" + selectQueryFromTreasury);
/*     */       try {
/* 220 */         dbConnection = DBConnectionUtility.getDBLinkConnection();
/*     */       }
/* 222 */       catch (Exception e) {
/*     */         
/* 224 */         logger.info("db connection not established for treasuryRefNo --> " + treasuryRefNo);
/* 225 */         e.printStackTrace();
/*     */       } 
/* 227 */       preparedStatement = dbConnection.prepareStatement(selectQueryFromTreasury);
/* 228 */       preparedStatement.setString(1, dealCategory);
/* 229 */       preparedStatement.setString(2, treasuryRefNo);
/* 230 */       resultSet = preparedStatement.executeQuery();
/*     */       
/* 232 */       while (resultSet.next()) {
/* 233 */         fwcRefNum = resultSet.getString("FWC_REF_NUM");
/* 234 */         hostTranDate = resultSet.getString("HOST_TRAN_DATE");
/* 235 */         valueDate = resultSet.getString("VALUE_DATE");
/*     */         
/* 237 */         buyOrSell = resultSet.getString("BUY_OR_SELL");
/*     */         
/* 239 */         if (CommonMethods.isValidString(buyOrSell)) {
/* 240 */           if (buyOrSell.equalsIgnoreCase("B") || buyOrSell.equalsIgnoreCase("P")) {
/* 241 */             dealAmount = resultSet.getBigDecimal("BUY_AMOUNT");
/* 242 */             dealCcy = resultSet.getString("BUY_AMOUNT_CCY");
/* 243 */           } else if (buyOrSell.equalsIgnoreCase("S")) {
/* 244 */             dealAmount = resultSet.getBigDecimal("SELL_AMOUNT");
/* 245 */             dealCcy = resultSet.getString("SELL_AMOUNT_CCY");
/*     */           } 
/*     */         }
/*     */         
/* 249 */         fxRate = resultSet.getBigDecimal("FWD_CONTRACT_RATE");
/* 250 */         cifId = resultSet.getString("COUNTERPARTY_STRING");
/* 251 */         solId = resultSet.getString("SOL_ID");
/* 252 */         tranType = resultSet.getString("TRAN_TYPE");
/* 253 */         startDate = resultSet.getString("START_DATE");
/* 254 */         endDate = resultSet.getString("END_DATE");
/*     */         
/* 256 */         if (resultSet.getBigDecimal("BUY_AMOUNT") != null)
/* 257 */           buyAmount = resultSet.getBigDecimal("BUY_AMOUNT").toString(); 
/* 258 */         if (resultSet.getBigDecimal("SELL_AMOUNT") != null)
/* 259 */           sellAmount = resultSet.getBigDecimal("SELL_AMOUNT").toString(); 
/* 260 */         buyCcy = resultSet.getString("BUY_AMOUNT_CCY");
/* 261 */         sellCcy = resultSet.getString("SELL_AMOUNT_CCY");
/*     */ 
/*     */         
/* 264 */         logger.info("Values from DBLink for fxReference --> " + treasuryRefNo);
/* 265 */         logger.info("fwd Ref Num -->" + forwardRefNum);
/* 266 */         logger.info("hostTranDate -->" + hostTranDate);
/* 267 */         logger.info("valueDate -->" + valueDate);
/* 268 */         logger.info("dealAmount -->" + dealAmount);
/* 269 */         logger.info("dealCcy -->" + dealCcy);
/* 270 */         logger.info("fxRate -->" + fxRate);
/* 271 */         logger.info("cifId -->" + cifId);
/* 272 */         logger.info("solId -->" + solId);
/* 273 */         logger.info("tranType -->" + tranType);
/* 274 */         logger.info("startDate -->" + startDate);
/* 275 */         logger.info("endDate -->" + endDate);
/*     */       } 
/*     */       
/* 278 */       dealAmount = dealAmount.setScale(6, RoundingMode.HALF_UP);
/* 279 */       dealAmt = dealAmt.setScale(6, RoundingMode.HALF_UP);
/* 280 */       logger.info("dealAmount -->" + dealAmount);
/*     */       
/* 282 */       treasRate = treasRate.setScale(4, RoundingMode.HALF_UP);
/* 283 */       fxRate = fxRate.setScale(4, RoundingMode.HALF_UP);
/* 284 */       logger.info("TreasuryRate: " + treasRate + "\t" + "FxRate: " + fxRate);
/*     */       
/* 286 */       if (StringUtils.equalsIgnoreCase(fwcRefNum, forwardRefNum) || StringUtils.equalsIgnoreCase(dealCategory, "FXRATE")) {
/* 287 */         logger.info("Forward Ref Num --> MATCHED");
/* 288 */         matchedOrUnmatched = "MATCHED";
/* 289 */         matchedOrUnmatchedReason = "MATCHED";
/*     */         
/* 291 */         if (hostTranDate.equalsIgnoreCase(bookingDate.toString())) {
/* 292 */           logger.info("hostTranDate --> MATCHED");
/* 293 */           matchedOrUnmatched = "MATCHED";
/* 294 */           matchedOrUnmatchedReason = "MATCHED";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 301 */           if (dealAmount.compareTo(dealAmt) == 0) {
/* 302 */             logger.info("dealAmount --> MATCHED");
/* 303 */             matchedOrUnmatched = "MATCHED";
/* 304 */             matchedOrUnmatchedReason = "MATCHED";
/*     */             
/* 306 */             if (dealCcy.equalsIgnoreCase(dealCcy)) {
/* 307 */               logger.info("dealCcy --> MATCHED");
/* 308 */               matchedOrUnmatched = "MATCHED";
/* 309 */               matchedOrUnmatchedReason = "MATCHED";
/*     */               
/* 311 */               if (fxRate.compareTo(treasRate) == 0) {
/* 312 */                 logger.info("fxRate --> MATCHED");
/* 313 */                 matchedOrUnmatched = "MATCHED";
/* 314 */                 matchedOrUnmatchedReason = "MATCHED";
/*     */                 
/* 316 */                 if (cifId.equalsIgnoreCase(customerID)) {
/* 317 */                   logger.info("cifId --> MATCHED");
/* 318 */                   matchedOrUnmatched = "MATCHED";
/* 319 */                   matchedOrUnmatchedReason = "MATCHED";
/*     */                   
/* 321 */                   if (solId.equalsIgnoreCase(branch)) {
/* 322 */                     logger.info("solId --> MATCHED");
/* 323 */                     matchedOrUnmatched = "MATCHED";
/* 324 */                     matchedOrUnmatchedReason = "MATCHED";
/*     */                     
/* 326 */                     if (tranType.equalsIgnoreCase(dealType)) {
/* 327 */                       logger.info("tranType --> MATCHED");
/* 328 */                       matchedOrUnmatched = "MATCHED";
/* 329 */                       matchedOrUnmatchedReason = "MATCHED";
/*     */                     } else {
/* 331 */                       logger.info("tranType --> UNMATCHED");
/* 332 */                       matchedOrUnmatched = "UNMATCHED";
/* 333 */                       matchedOrUnmatchedReason = "Transaction Type Unmatched";
/*     */                     } 
/*     */                   } else {
/* 336 */                     logger.info("solId --> UNMATCHED");
/* 337 */                     matchedOrUnmatched = "UNMATCHED";
/* 338 */                     matchedOrUnmatchedReason = "Sol ID Unmatched";
/*     */                   } 
/*     */                 } else {
/* 341 */                   logger.info("cifId --> UNMATCHED");
/* 342 */                   matchedOrUnmatched = "UNMATCHED";
/* 343 */                   matchedOrUnmatchedReason = "Customer Unmatched";
/*     */                 } 
/*     */               } else {
/* 346 */                 logger.info("fxRate --> UNMATCHED");
/* 347 */                 matchedOrUnmatched = "UNMATCHED";
/* 348 */                 matchedOrUnmatchedReason = "Rate Unmatched";
/*     */               } 
/*     */             } else {
/* 351 */               logger.info("dealCcy --> UNMATCHED");
/* 352 */               matchedOrUnmatched = "UNMATCHED";
/* 353 */               matchedOrUnmatchedReason = "Deal Currency Unmatched";
/*     */             } 
/*     */           } else {
/* 356 */             logger.info("dealAmount --> UNMATCHED");
/* 357 */             matchedOrUnmatched = "UNMATCHED";
/* 358 */             matchedOrUnmatchedReason = "Deal Amount Unmatched";
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 368 */           logger.info("hostTranDate --> UNMATCHED");
/* 369 */           matchedOrUnmatched = "UNMATCHED";
/* 370 */           matchedOrUnmatchedReason = "Host Tran Date Unmatched";
/*     */         } 
/*     */       } else {
/* 373 */         logger.info("Forward Ref Num --> UNMATCHED");
/* 374 */         matchedOrUnmatched = "UNMATCHED";
/* 375 */         matchedOrUnmatchedReason = "Forward Contract Ref Num Unmatched";
/*     */       } 
/*     */       
/* 378 */       logger.info("matchedOrUnmatched --> " + matchedOrUnmatched);
/* 379 */       logger.info("matchedOrUnmatchedReason --> " + matchedOrUnmatchedReason);
/* 380 */       baseRecordTokens.put("RecordStatus", matchedOrUnmatched);
/* 381 */       baseRecordTokens.put("RecordStatusReason", matchedOrUnmatchedReason);
/* 382 */       baseRecordTokens.put("StartDate", startDate);
/* 383 */       baseRecordTokens.put("EndDate", endDate);
/*     */       
/* 385 */       baseRecordTokens.put("BUYAMOUNT", buyAmount);
/* 386 */       baseRecordTokens.put("SELLAMOUNT", sellAmount);
/* 387 */       baseRecordTokens.put("BUYCCY", buyCcy);
/* 388 */       baseRecordTokens.put("SELLCCY", sellCcy);
/* 389 */       baseRecordTokens.put("BUYORSELL", buyOrSell);
/*     */     
/*     */     }
/* 392 */     catch (SQLException e) {
/* 393 */       matchedOrUnmatched = "UNMATCHED";
/* 394 */       e.printStackTrace();
/* 395 */       logger.info(e.getMessage());
/*     */     } finally {
/* 397 */       DBConnectionUtility.surrenderDB(dbConnection, preparedStatement, resultSet);
/*     */     } 
/* 399 */     return baseRecordTokens;
/*     */   }

// ABHISHEK

	public static Map<String, String> getBaseRecordDetailsFromView(String dealCategory, String forwardRefNum,
			BigDecimal dealAmt, String dealCcy, String customerID, String branch, String dealType,
			String selectQueryFromTreasury) {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String matchedOrUnmatched = "UNMATCHED";
		String matchedOrUnmatchedReason = "UNMATCHED";
		String hostTranDate = null;
		String valueDate = null;
		String buyOrSell = null;
		BigDecimal dealAmount = null;
		String cifId = null;
		String solId = null;
		String tranType = null;
		String startDate = null;
		String endDate = null;
		String buyAmount = null;
		String sellAmount = null;
		String buyCcy = null;
		String sellCcy = null;
		String fwcRefNum = null;
		String recordDealCcy = dealCcy;
		Map<String, String> baseRecordTokens = new HashMap<>();
		try {
			logger.info("getBaseRecordDetailsFromView forwardRefNum -->" + forwardRefNum);
			logger.info("queryDetails -->" + selectQueryFromTreasury);
			try {
				dbConnection = DBConnectionUtility.getDBLinkConnection();
			} catch (Exception e) {
				logger.info("db connection not established for forwardRefNum --> " + forwardRefNum);
				e.printStackTrace();
			}
			preparedStatement = dbConnection.prepareStatement(selectQueryFromTreasury);
			preparedStatement.setString(1, forwardRefNum);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fwcRefNum = resultSet.getString("FWC_REF_NUM");
				hostTranDate = resultSet.getString("HOST_TRAN_DATE");
				valueDate = resultSet.getString("VALUE_DATE");
				buyOrSell = resultSet.getString("BUY_OR_SELL");
				if (CommonMethods.isValidString(buyOrSell)) {
					if (buyOrSell.equalsIgnoreCase("B") || buyOrSell.equalsIgnoreCase("P")) {
						dealAmount = resultSet.getBigDecimal("BUY_AMOUNT");
						recordDealCcy = resultSet.getString("BUY_AMOUNT_CCY");
					} else if (buyOrSell.equalsIgnoreCase("S")) {
						dealAmount = resultSet.getBigDecimal("SELL_AMOUNT");
						recordDealCcy = resultSet.getString("SELL_AMOUNT_CCY");
					}
				}
				cifId = resultSet.getString("COUNTERPARTY_STRING");
				solId = resultSet.getString("SOL_ID");
				tranType = resultSet.getString("TRAN_TYPE");
				startDate = resultSet.getString("START_DATE");
				endDate = resultSet.getString("END_DATE");
				if (resultSet.getBigDecimal("BUY_AMOUNT") != null)
					buyAmount = resultSet.getBigDecimal("BUY_AMOUNT").toString();
				if (resultSet.getBigDecimal("SELL_AMOUNT") != null)
					sellAmount = resultSet.getBigDecimal("SELL_AMOUNT").toString();
				buyCcy = resultSet.getString("BUY_AMOUNT_CCY");
				sellCcy = resultSet.getString("SELL_AMOUNT_CCY");
				logger.info("fwcRefNum -->" + fwcRefNum);
				logger.info("hostTranDate -->" + hostTranDate);
				logger.info("valueDate -->" + valueDate);
				logger.info("dealAmount -->" + dealAmount);
				logger.info("recordDealCcy -->" + recordDealCcy);
				logger.info("cifId -->" + cifId);
				logger.info("solId -->" + solId);
				logger.info("tranType -->" + tranType);
				logger.info("startDate -->" + startDate);
				logger.info("endDate -->" + endDate);
			}
			if (dealAmount != null) {
				dealAmount = dealAmount.setScale(6, RoundingMode.HALF_UP);
			}
			if (dealAmt != null) {
				dealAmt = dealAmt.setScale(6, RoundingMode.HALF_UP);
			}
			logger.info("dealAmount (view) -->" + dealAmount + "\t dealAmt (VO) -->" + dealAmt);
			if (StringUtils.equalsIgnoreCase(fwcRefNum, forwardRefNum)) {
				logger.info("Forward Ref Num --> MATCHED");
				matchedOrUnmatched = "MATCHED";
				matchedOrUnmatchedReason = "MATCHED";
				// NOTE: dealAmount validation intentionally skipped for Without Rate flow.
				// FWCCANCEL swaps FWC_AMOUNT and TO_CCY_AMT at insert time, causing
				// a false mismatch between the view amount (foreign CCY) and VO amount (INR).
				if (recordDealCcy != null && recordDealCcy.equalsIgnoreCase(dealCcy)) {
					logger.info("dealCcy --> MATCHED");
					matchedOrUnmatched = "MATCHED";
					matchedOrUnmatchedReason = "MATCHED";
					if (cifId != null && cifId.equalsIgnoreCase(customerID)) {
						logger.info("cifId --> MATCHED");
						matchedOrUnmatched = "MATCHED";
						matchedOrUnmatchedReason = "MATCHED";
						if (solId != null && solId.equalsIgnoreCase(branch)) {
							logger.info("solId --> MATCHED");
							matchedOrUnmatched = "MATCHED";
							matchedOrUnmatchedReason = "MATCHED";
							if (tranType != null && tranType.equalsIgnoreCase(dealType)) {
								logger.info("tranType --> MATCHED");
								matchedOrUnmatched = "MATCHED";
								matchedOrUnmatchedReason = "MATCHED";
							} else {
								logger.info("tranType --> UNMATCHED");
								matchedOrUnmatched = "UNMATCHED";
								matchedOrUnmatchedReason = "Transaction Type Unmatched";
							}
						} else {
							logger.info("solId --> UNMATCHED");
							matchedOrUnmatched = "UNMATCHED";
							matchedOrUnmatchedReason = "Sol ID Unmatched";
						}
					} else {
						logger.info("cifId --> UNMATCHED");
						matchedOrUnmatched = "UNMATCHED";
						matchedOrUnmatchedReason = "Customer Unmatched";
					}
				}
//				 else {
//					logger.info("dealCcy --> UNMATCHED");
//					matchedOrUnmatched = "UNMATCHED";
//					matchedOrUnmatchedReason = "Deal Currency Unmatched";
//				}
			} 
//			else {
//				logger.info("Forward Ref Num --> UNMATCHED");
//				matchedOrUnmatched = "UNMATCHED";
//				matchedOrUnmatchedReason = "Forward Contract Ref Num Unmatched";
//			}
			logger.info("matchedOrUnmatched --> " + matchedOrUnmatched);
			logger.info("matchedOrUnmatchedReason --> " + matchedOrUnmatchedReason);
			baseRecordTokens.put("RecordStatus", matchedOrUnmatched);
			baseRecordTokens.put("RecordStatusReason", matchedOrUnmatchedReason);
			baseRecordTokens.put("StartDate", startDate);
			baseRecordTokens.put("EndDate", endDate);
			baseRecordTokens.put("BUYAMOUNT", buyAmount);
			baseRecordTokens.put("SELLAMOUNT", sellAmount);
			baseRecordTokens.put("BUYCCY", buyCcy);
			baseRecordTokens.put("SELLCCY", sellCcy);
			baseRecordTokens.put("BUYORSELL", buyOrSell);
		} catch (SQLException e) {
			matchedOrUnmatched = "UNMATCHED";
			e.printStackTrace();
			logger.info(e.getMessage());
		} finally {
			DBConnectionUtility.surrenderDB(dbConnection, preparedStatement, resultSet);
		}
		return baseRecordTokens;
	}

/*     */ 
/*     */   
/*     */   private static String getPanNumberOfCustomer(String customer) {
/* 404 */     ResultSet resultSet = null;
/* 405 */     Connection tiZoneConnection = null;
/* 406 */     PreparedStatement preparedStatement = null;
/* 407 */     String panNumberOfCustomerQuery = "";
/* 408 */     String panNumber = "";
/*     */     
/*     */     try {
/* 411 */       panNumberOfCustomerQuery = " SELECT PANNO FROM EXTCUST WHERE TRIM(CUST)=? ";
/*     */       
/* 413 */       logger.info("panNumOfCustomerQuery : " + panNumberOfCustomerQuery + "; Param[" + customer + "]");
/*     */       
/* 415 */       tiZoneConnection = DBConnectionUtility.getZoneConnection();
/* 416 */       preparedStatement = tiZoneConnection.prepareStatement(panNumberOfCustomerQuery);
/*     */       
/* 418 */       preparedStatement.setString(1, customer.trim());
/*     */       
/* 420 */       resultSet = preparedStatement.executeQuery();
/* 421 */       while (resultSet.next()) {
/* 422 */         if (CommonMethods.isValidString(resultSet.getString("PANNO")))
/* 423 */           panNumber = resultSet.getString("PANNO"); 
/* 424 */         logger.info("panNumber --> " + panNumber);
/*     */       }
/*     */     
/* 427 */     } catch (SQLException e) {
/* 428 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 431 */       DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
/*     */     } 
/*     */     
/* 434 */     return panNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateFWCReferenceNumber(String branchCode, String subProduct) {
/* 439 */     String fwdContractSeqNo = null;
/* 440 */     String productType = null;
/* 441 */     Connection aConnection = null;
/* 442 */     PreparedStatement aPreparedStatement = null;
/* 443 */     ResultSet aResultSet = null;
/* 444 */     String fwcSeqNo = "";
/*     */     try {
/* 446 */       aConnection = DBConnectionUtility.getZoneConnection();
/* 447 */       aPreparedStatement = aConnection.prepareStatement("SELECT LPAD(FWC_SEQ.NEXTVAL,5,'0') FROM DUAL");
/* 448 */       aResultSet = aPreparedStatement.executeQuery();
/*     */       
/* 450 */       if (aResultSet.next()) {
/* 451 */         fwcSeqNo = aResultSet.getString(1);
/* 452 */         logger.info("Seq number form DB " + fwcSeqNo);
/*     */       } 
/*     */       
/* 455 */       if (subProduct.contains("Purchase")) {
/* 456 */         productType = "MP";
/* 457 */       } else if (subProduct.contains("Sale")) {
/* 458 */         productType = "MS";
/* 459 */       }  fwdContractSeqNo = String.valueOf(branchCode) + productType + (new SimpleDateFormat("yy")).format(new java.util.Date()) + 
/* 460 */         fwcSeqNo;
/*     */       
/* 462 */       logger.info("Generated FWC number is " + fwdContractSeqNo);
/*     */     }
/* 464 */     catch (Exception e) {
/* 465 */       e.printStackTrace();
/*     */     } finally {
/* 467 */       DBConnectionUtility.surrenderDB(aConnection, aPreparedStatement, aResultSet);
/*     */     } 
/*     */     
/* 470 */     return fwdContractSeqNo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int insertFWCDetailsInFTI(String dealCategory, String sequence, Date txnBusinessDate, String recordStatus, String userId, String dealDirection, String endDate, BigDecimal dealAmt, String dealCcy, BigDecimal purchaseAmount, String purchaseCcy, BigDecimal saleAmount, String saleCcy, BigDecimal treasuryRate, String treasuryRefNo, String txnCustomer, String startDate, String txnBranch, String fwdContractNo, String txnDealType, String panNumber, String recordStatusReason, String nostroAcct, String queryDetails) {
/* 479 */     int count = 0;
/* 480 */     Connection dbConnection = null;
/* 481 */     PreparedStatement preparedStatement = null;
/*     */     try {
/* 483 */       dbConnection = DBConnectionUtility.getZoneConnection();
/* 484 */       if (dbConnection != null) {
/*     */         
/* 486 */         preparedStatement = dbConnection.prepareStatement(queryDetails);
/*     */         
/* 488 */         preparedStatement.setInt(1, Integer.parseInt(sequence));
/* 489 */         preparedStatement.setString(2, dealCategory);
/* 490 */         preparedStatement.setDate(3, txnBusinessDate);
/* 491 */         preparedStatement.setString(4, recordStatus);
/* 492 */         preparedStatement.setString(5, userId);
/* 493 */         preparedStatement.setString(6, dealDirection);
/* 494 */         preparedStatement.setString(7, endDate);
/* 495 */         preparedStatement.setBigDecimal(8, dealAmt);
/* 496 */         preparedStatement.setString(9, dealCcy);
/* 497 */         preparedStatement.setBigDecimal(10, purchaseAmount);
/* 498 */         preparedStatement.setString(11, purchaseCcy);
/* 499 */         preparedStatement.setBigDecimal(12, saleAmount);
/* 500 */         preparedStatement.setString(13, saleCcy);
/* 501 */         preparedStatement.setBigDecimal(14, treasuryRate);
/* 502 */         preparedStatement.setBigDecimal(15, treasuryRate);
/* 503 */         preparedStatement.setString(16, treasuryRefNo);
/* 504 */         preparedStatement.setString(17, txnCustomer);
/* 505 */         preparedStatement.setString(18, startDate);
/* 506 */         preparedStatement.setString(19, endDate);
/* 507 */         preparedStatement.setString(20, txnBranch);
/* 508 */         preparedStatement.setString(21, "");
/* 509 */         preparedStatement.setString(22, fwdContractNo);
/* 510 */         preparedStatement.setString(23, txnDealType);
/* 511 */         preparedStatement.setString(24, panNumber);
/* 512 */         preparedStatement.setString(25, recordStatusReason);
/*     */         
/* 514 */         preparedStatement.executeUpdate();
/*     */         
/* 516 */         count = preparedStatement.getUpdateCount();
/*     */         
/* 518 */         logger.info("Inserted Utlization Details into FTI Table successfully with the count: " + count + " for " + 
/* 519 */             fwdContractNo);
/*     */       } 
/* 521 */     } catch (Exception e) {
/* 522 */       e.printStackTrace();
/*     */     } finally {
/* 524 */       DBConnectionUtility.surrenderDB(dbConnection, preparedStatement, null);
/*     */     } 
/*     */     
/* 527 */     return count;
/*     */   }
/*     */   
/*     */   public static void insertUtilizationDetailsInTreasury(String txnReference, String finacleTranId, String category, String seqNo) {
/* 531 */     Connection dbConnection = null;
/* 532 */     CallableStatement callableStatement = null;
/*     */ 
/*     */     
/*     */     try {
/* 536 */       dbConnection = DBConnectionUtility.getZoneConnection();
/*     */       
/* 538 */       if (dbConnection != null) {
/*     */         
/* 540 */         callableStatement = dbConnection.prepareCall("{CALL CUSTOM_TREASURY_FWC_INSERT_PROC(?,?,?,?)}");
/*     */         
				callableStatement.setString(1, txnReference);
				callableStatement.setString(2, String.valueOf(txnReference) + " " + finacleTranId);
				callableStatement.setString(3, category);
				callableStatement.setString(4, seqNo);
				callableStatement.execute();
/*     */         
/* 548 */         logger.info("Inserted Utlization Details into Treasury Table successfully for " + txnReference + 
/* 549 */             " under category " + category);
/*     */       } 
/* 551 */     } catch (Exception e) {
/* 552 */       e.printStackTrace();
/*     */     } finally {
/* 554 */       DBConnectionUtility.surrenderDB(dbConnection, callableStatement, null);
/*     */     } 
/*     */   }


	public static void insertUtilizationDetailsInTreasuryWithoutRate(String txnReference, String finacleTranId,
			String category, String seqNo) {

		Connection dbConnection = null;

		CallableStatement callableStatement = null;

		try {

			dbConnection = DBConnectionUtility.getZoneConnection();

			if (dbConnection != null) {

				callableStatement = dbConnection.prepareCall("{CALL CUSTOM_TREASURY_FWC_INSERT_PROC(?,?,?,?)}");

				callableStatement.setString(1, txnReference);

				callableStatement.setString(2,

						(finacleTranId != null)

								? String.valueOf(txnReference) + " " + finacleTranId

								: String.valueOf(txnReference)); // no tranId appended if null

				callableStatement.setString(3, category);

				callableStatement.setString(4, seqNo);

				callableStatement.execute();
				logger.info("Inserted Utilization Details into Treasury Table successfully for " + txnReference

						+ " under category " + category + " (Without Rate)");

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			DBConnectionUtility.surrenderDB(dbConnection, callableStatement, null);

		}

	}
 
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\FWCUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */