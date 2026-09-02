/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import in.co.forwardcontract.dao.ForwardContractDAO;
/*     */ import in.co.forwardcontract.service.model.AmtAndCcy;
/*     */ import in.co.forwardcontract.service.model.PostingReq;
/*     */ import in.co.forwardcontract.service.model.PostingReqAcc;
/*     */ import in.co.forwardcontract.service.model.PostingReqCount;
/*     */ import in.co.forwardcontract.service.model.PostingReqCustomData;
/*     */ import in.co.forwardcontract.service.model.PostingReqPartTrnRec;
/*     */ import in.co.forwardcontract.service.model.PostingReqPmtInst;
/*     */ import in.co.forwardcontract.service.model.PostingReqTranPart;
/*     */ import in.co.forwardcontract.service.model.PostingReqTrnAddRequest;
/*     */ import in.co.forwardcontract.service.model.PostingReqTrnAddRq;
/*     */ import in.co.forwardcontract.service.model.PostingReqTrnDetail;
/*     */ import in.co.forwardcontract.service.model.PostingReqTrnHdr;
/*     */ import in.co.forwardcontract.service.model.PostingRes;
/*     */ import in.co.forwardcontract.service.utility.FWCUtil;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import in.co.forwardcontract.utility.ServiceLogging;
/*     */ import in.co.forwardcontract.vo.FWCPostingVO;
/*     */ import in.co.forwardcontract.vo.ForwardContractVO;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PostingUtil {
/*  41 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.PostingUtil.class);
/*     */   
/*  43 */   private static String fwdContractNo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   static String tempEnc = "";
/*  53 */   static PostingRes postingRes = new PostingRes();
/*  54 */   static Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   static String encBankRes;
/*  56 */   static String dealCategory = "";
/*  57 */   static String sequenceNo = "";
/*     */ 
/*     */   
/*     */   public static Map<String, String> releaseTxnPostings(String category, ForwardContractVO fwdContractVO, String seqNo) {
/*  61 */     String plainBankRequest = "";
/*  62 */     String tempEncRequest = "";
/*  63 */     String encBankRequest = "";
/*  64 */     String encBankResponse = "";
/*  65 */     String plainBankResponse = "";
/*  66 */     Timestamp bankRequestTime = null;
/*  67 */     Timestamp bankResponseTime = null;
/*  68 */     String status = "FAILED";
/*  69 */     ServiceUtility.getProperties();
/*  70 */     String postingURL = (String)ServiceUtility.TBProperties.get("BO_BATCH_URL");
/*  71 */     String postingKey = (String)ServiceUtility.TBProperties.get("BO_BATCH_KEY");
/*  72 */     Map<String, String> responseTokens = null;
/*  73 */     dealCategory = category;
/*  74 */     sequenceNo = seqNo;
/*     */     
/*     */     try {
/*  77 */       logger.info("postingURL & postingKey  --> " + postingURL + " & " + postingKey);
/*     */       
/*  79 */       Map<String, String> result = generatePostingBankRequest(category, fwdContractVO);
/*  80 */       plainBankRequest = result.get("JSON");
/*  81 */       logger.info("Posting Bank Request in Json Format -->" + plainBankRequest);
/*     */ 
/*     */       
/*  84 */       tempEncRequest = ServiceUtility.generateEncryptBankRequest(plainBankRequest, postingKey);
/*  85 */       encBankRequest = generateEncryptedPostingJson(tempEncRequest, result.get("MSGID"));
/*     */       
/*  87 */       logger.info("Posting Bank Enc Request -->" + encBankRequest);
/*  88 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*  89 */       encBankResponse = ServiceUtility.getBankFinResponse(encBankRequest, postingURL);
/*  90 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/*  91 */       plainBankResponse = ServiceUtility.generateDecryptBankResponse(encBankResponse, postingKey);
/*  92 */       logger.info("Posting Bank Json Response -->" + plainBankResponse);
/*     */       
/*  94 */       if (plainBankResponse != null && !plainBankResponse.isEmpty() && !plainBankResponse.equals("{}")) {
/*  95 */         responseTokens = getPostingResponseTokens(plainBankResponse);
/*  96 */         if (responseTokens != null && ((String)responseTokens.get("PostingStatus")).contains("S")) {
/*  97 */           status = "SUCCEEDED";
/*     */         } else {
/*  99 */           status = "FAILED";
/*     */         } 
/*     */       } else {
/* 102 */         status = "FAILED";
/*     */       } 
/*     */       
/* 105 */       ServiceLogging.pushServiceLogData("Batch", "Posting", "ZONE1", "FTI", "Finacle", fwdContractVO.getFwdContractNo(), category, status, 
/* 106 */           plainBankRequest, plainBankResponse, bankRequestTime, bankResponseTime);
/*     */     }
/* 108 */     catch (Exception e) {
/* 109 */       e.printStackTrace();
/*     */     } 
/* 111 */     return responseTokens;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, String> generatePostingBankRequest(String category, ForwardContractVO fwdContractVO) {
/* 116 */     String bankRequest = null;
/* 117 */     int creditCount = 0;
/* 118 */     int debitCount = 0;
/* 119 */     int serialNo = 1;
/* 120 */     Map<String, String> result = new HashMap<>();
/* 121 */     List<PostingReqPartTrnRec> trnRecList = new LinkedList<>();
/* 122 */     List<PostingReqTranPart> trnPartList = new LinkedList<>();
/* 123 */     PostingReq postingReq = new PostingReq();
/* 124 */     ForwardContractDAO fwdcontractdao = new ForwardContractDAO();
/* 125 */     PostingReqTrnHdr aPostingReqTrnHdr = new PostingReqTrnHdr();
/* 126 */     PostingReqCount aPostingReqCount = new PostingReqCount();
/* 127 */     PostingReqTrnDetail aPostingReqTrnDetail = new PostingReqTrnDetail();
/* 128 */     PostingReqTrnAddRequest aPostingReqTrnAddRequest = new PostingReqTrnAddRequest();
/* 129 */     PostingReqCustomData aPostingReqCustomData = new PostingReqCustomData();
/* 130 */     PostingReqTrnAddRq aPostingReqTrnAddRq = new PostingReqTrnAddRq();
/* 131 */     String sequence = null;
/*     */     
/*     */     try {
/* 134 */       logger.info("Fwd Contract No : " + fwdContractVO.getFwdContractNo());
/*     */       
/* 136 */       fwdContractNo = fwdContractVO.getFwdContractNo().trim();
/* 137 */       String customerID = fwdContractVO.getCustomerID().trim();
/* 138 */       String postingBranch = fwdContractVO.getBranchCode();
/*     */       
/* 140 */       int postingCount = fwdContractVO.getPostingList().size();
/*     */       
/* 142 */       if (postingCount > 0)
/*     */       {
/* 144 */         for (int i = 0; i < postingCount; i++) {
/* 145 */           String forwardContractNo = fwdContractVO.getFwdContractNo();
/*     */           
/* 147 */           String postingAmountCcy = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingAmountCcy();
/* 148 */           String postingType = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingType();
/* 149 */           String postingCcy = postingAmountCcy.trim().replaceAll("[^A-Za-z]+", "");
/* 150 */           String postingAmount = postingAmountCcy.trim().replaceAll("[^0-9.]", "");
/*     */           
/* 152 */           if (category != null && category.equalsIgnoreCase("FWCCANCEL")) {
/*     */             
/* 154 */             logger.info("postingAmount in generatePostingBankRequest for FWCCANCEL --> " + postingAmount);
/* 155 */             String bookingrate = "";
/*     */             
/* 157 */             if (postingType != null && !"Charges".equalsIgnoreCase(postingType)) {
/*     */               
/* 159 */               bookingrate = fwdcontractdao.getBookingTreasuryrate(forwardContractNo);
/* 160 */               postingAmount = (new BigDecimal(postingAmount)).multiply(new BigDecimal(bookingrate)).setScale(4, 
/* 161 */                   RoundingMode.HALF_UP).toString();
/*     */             } 
/* 163 */             logger.info("postingAmount*bookingrate in generatePostingBankRequest for FWCCANCEL --> " + postingAmount);
/*     */           } 
/* 165 */           String postingAcctNo = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingAcctNumber();
/* 166 */           String postingDrCrFlag = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingDrCrFlag();
/* 167 */           String valueDate = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingValueDate();
/*     */           
/* 169 */           logger.info("TI posting value date --> " + valueDate);
/* 170 */           valueDate = CommonMethods.convertToStringDateFormat(valueDate, "dd-MM-yyyy", "yyyy-MM-dd");
/*     */           
/* 172 */           String postingDesc = ((FWCPostingVO)fwdContractVO.getPostingList().get(i)).getPostingDesc();
/*     */           
/* 174 */           if (CommonMethods.isValidString(postingDrCrFlag) && postingDrCrFlag.equalsIgnoreCase("C")) {
/* 175 */             creditCount++;
/* 176 */           } else if (CommonMethods.isValidString(postingDrCrFlag) && postingDrCrFlag.equalsIgnoreCase("D")) {
/* 177 */             debitCount++;
/*     */           } 
/*     */           
/* 180 */           PostingReqPartTrnRec aPostingReqPartTrnRec = new PostingReqPartTrnRec();
/*     */           
/* 182 */           PostingReqAcc aPostingReqAcc = new PostingReqAcc();
/* 183 */           aPostingReqAcc.setAcctId(postingAcctNo);
/*     */           
/* 185 */           aPostingReqPartTrnRec.setAcctId(aPostingReqAcc);
/* 186 */           aPostingReqPartTrnRec.setCreditDebitFlg(postingDrCrFlag);
/*     */           
/* 188 */           AmtAndCcy aAmtAndCcy = new AmtAndCcy();
/*     */           
/* 190 */           aAmtAndCcy.setAmountValue(postingAmount);
/* 191 */           aAmtAndCcy.setCurrencyCode(postingCcy);
/*     */           
/* 193 */           aPostingReqPartTrnRec.setTrnAmt(aAmtAndCcy);
/* 194 */           aPostingReqPartTrnRec.setTrnParticulars(fwdContractNo);
/* 195 */           aPostingReqPartTrnRec.setValueDt(String.valueOf(valueDate) + "T" + CommonMethods.getH24Time());
/* 196 */           aPostingReqPartTrnRec.setSerialNum((new StringBuilder(String.valueOf(serialNo))).toString());
/*     */           
/* 198 */           PostingReqPmtInst aPostingReqPmtInst = new PostingReqPmtInst();
/* 199 */           aPostingReqPmtInst.setPmtInstType("DV");
/*     */           
/* 201 */           aPostingReqPartTrnRec.setPmtInst(aPostingReqPmtInst);
/*     */           
/* 203 */           trnRecList.add(aPostingReqPartTrnRec);
/* 204 */           String reptcode = fetchReptCODE(fwdContractNo);
/* 205 */           if (reptcode == null)
/*     */           {
/* 207 */             reptcode = "";
/*     */           }
/* 209 */           PostingReqTranPart aPostingReqTranPart = new PostingReqTranPart();
/* 210 */           aPostingReqTranPart.setSERIAL_NUM((new StringBuilder(String.valueOf(serialNo))).toString());
/* 211 */           aPostingReqTranPart.setPARTTNDTLENTERED("1");
/* 212 */           aPostingReqTranPart.setENTITYDISPID(fwdContractNo);
/* 213 */           aPostingReqTranPart.setENTITY_TYPE("FWC");
/* 214 */           aPostingReqTranPart.setTRANRMKS(fwdContractNo);
/* 215 */           aPostingReqTranPart.setTRANPARTICULARS2(String.valueOf(fwdContractNo) + " " + category);
/* 216 */           aPostingReqTranPart.setREFNO(fwdContractNo);
/* 217 */           aPostingReqTranPart.setREPTCODE(reptcode);
/*     */           
/* 219 */           trnPartList.add(aPostingReqTranPart);
/*     */           
/* 221 */           pushCustomFWCPosting(category, fwdContractNo, customerID, postingDrCrFlag, postingAcctNo, 
/* 222 */               postingBranch, postingAmount, postingCcy, valueDate, postingDesc, serialNo);
/*     */           
/* 224 */           serialNo++;
/*     */         } 
/*     */ 
/*     */         
/* 228 */         aPostingReqTrnHdr.setTrnSubType("BI");
/* 229 */         aPostingReqTrnHdr.setTrnType("T");
/*     */         
/* 231 */         aPostingReqCount.setCreditCount((new StringBuilder(String.valueOf(creditCount))).toString());
/* 232 */         aPostingReqCount.setDebitCount((new StringBuilder(String.valueOf(debitCount))).toString());
/* 233 */         aPostingReqCount.setTotalXferCount((new StringBuilder(String.valueOf(creditCount + debitCount))).toString());
/*     */         
/* 235 */         aPostingReqTrnDetail.setPartTrnRec(trnRecList);
/*     */         
/* 237 */         aPostingReqTrnAddRq.setXferTrnHdr(aPostingReqTrnHdr);
/* 238 */         aPostingReqTrnAddRq.setXferCount(aPostingReqCount);
/* 239 */         aPostingReqTrnAddRq.setXferTrnDetail(aPostingReqTrnDetail);
/*     */         
/* 241 */         aPostingReqCustomData.setSolid(postingBranch);
/* 242 */         aPostingReqCustomData.setTRANPART(trnPartList);
/*     */         
/* 244 */         aPostingReqTrnAddRequest.setXferTrnAdd_CustomData(aPostingReqCustomData);
/* 245 */         aPostingReqTrnAddRequest.setXferTrnAddRq(aPostingReqTrnAddRq);
/*     */         
/* 247 */         sequence = ServiceUtility.getSqlLocalDateTime().toString();
/* 248 */         sequence = sequence.replaceAll("[- :.]", "");
/*     */         
/* 250 */         postingReq.setXferTrnAddRequest(aPostingReqTrnAddRequest);
/* 251 */         postingReq.setMsgid(sequence);
/*     */         
/* 253 */         bankRequest = aGson.toJson(postingReq).trim();
/* 254 */         logger.info("bankRequest of FWC posting: " + bankRequest);
/*     */       }
/*     */     
/* 257 */     } catch (Exception e) {
/* 258 */       e.printStackTrace();
/*     */     } 
/* 260 */     result.put("JSON", bankRequest);
/* 261 */     result.put("MSGID", sequence);
/* 262 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateEncryptedPostingJson(String bankEncRequest, String msgId) {
/* 268 */     ServiceUtility encryptedReq = new ServiceUtility(bankEncRequest, msgId);
/* 269 */     String reqJson = aGson.toJson(encryptedReq).trim();
/* 270 */     return reqJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fetchReptCODE(String masterRef) {
/* 275 */     logger.info("fetchReptCODE() : Started");
/* 276 */     ResultSet resultSet = null;
/* 277 */     Connection tiZoneConnection = null;
/* 278 */     PreparedStatement preparedStatement = null;
/* 279 */     String result = "";
/*     */     
/*     */     try {
/* 282 */       String query = "SELECT exte.RPTCODE AS RPTCODE  FROM master mas, baseevent bev, extevent exte where mas.KEY97 = bev.MASTER_KEY and bev.KEY97 = exte.EVENT and mas.MASTER_REF IN ('" + 
/* 283 */         masterRef.trim() + "')";
/* 284 */       logger.info("query :" + query);
/* 285 */       tiZoneConnection = DBConnectionUtility.getZoneConnection();
/* 286 */       preparedStatement = tiZoneConnection.prepareStatement(query);
/* 287 */       resultSet = preparedStatement.executeQuery();
/* 288 */       while (resultSet.next()) {
/* 289 */         result = resultSet.getString("RPTCODE");
/*     */       }
/*     */     }
/* 292 */     catch (Exception e) {
/*     */       
/* 294 */       logger.info(" fetchReptCODE() Exception" + e.getMessage());
/* 295 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 298 */       DBConnectionUtility.surrenderDB(tiZoneConnection, preparedStatement, resultSet);
/*     */     } 
/* 300 */     logger.info("fetchReptCODE() : result :" + result);
/* 301 */     return result;
/*     */   }
/*     */   public static Map<String, String> getPostingResponseTokens(String plainBankResponse) {
/* 304 */     Map<String, String> postingTokens = new HashMap<>();
/* 305 */     logger.info("Entering getPostingResponseTokens ");
/* 306 */     postingTokens.put("PostingStatus", "FAILURE");
/* 307 */     String tranID = "";
/* 308 */     String trandate = "";
/*     */     
/*     */     try {
/* 311 */       postingRes = (PostingRes)aGson.fromJson(plainBankResponse, PostingRes.class);
/*     */       
/* 313 */       if (!CommonMethods.isNull(postingRes.getStatus())) {
/*     */         
/* 315 */         String status = postingRes.getStatus();
/*     */         
/* 317 */         postingTokens.put("PostingStatus", status);
/*     */         
/* 319 */         if (status.equalsIgnoreCase("SUCCESS")) {
/* 320 */           tranID = postingRes.getTransactionId();
/*     */           
/* 322 */           trandate = postingRes.getTransactionDt();
/* 323 */           if (CommonMethods.isValidString(tranID)) {
/* 324 */             logger.info("FWC Posting SUCCEEDED with " + tranID);
/* 325 */             FWCUtil.insertUtilizationDetailsInTreasury(fwdContractNo.trim(), tranID.trim(), dealCategory, sequenceNo);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 334 */           postingTokens.put("PostingStatus", "FAILURE");
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 340 */         if (CommonMethods.isValidString(tranID) && CommonMethods.isValidString(trandate)) {
/* 341 */           postingTokens.put("TranID", tranID);
/* 342 */           postingTokens.put("Trandate", trandate);
/*     */         } else {
/* 344 */           postingTokens.put("PostingStatus", "FAILURE");
/*     */         } 
/*     */       } else {
/* 347 */         postingTokens.put("PostingStatus", "FAILURE");
/*     */       } 
/* 349 */       logger.info("Exiting getPostingResponseTokens ");
/*     */     }
/* 351 */     catch (Exception e) {
/* 352 */       logger.info("Exception in getPostingResponseTokens " + e.getMessage());
/* 353 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 356 */     return postingTokens;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void pushCustomFWCPosting(String category, String fwdContractNo, String customerID, String postingDrCrFlag, String postingAcctNo, String postingBranch, String postingAmount, String postingCcy, String valueDate, String postingDesc, int serialNo) {
/* 363 */     logger.info("Process entered into Push Custom FWC Posting...!");
/*     */     
/* 365 */     String query = "INSERT INTO CUSTOM_FWC_POSTING(CATEGORY,FWC_REFERENCE,CUSTOMER_ID,DR_CR_FLAG,POSTING_ACCT_NO,POSTING_BRANCH,POSTING_AMOUNT,POSTING_CCY,VALUE_DATE, POSTING_DESC,POSTING_SEQNO, POSTING_TIMESTAMP) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,SYSTIMESTAMP) ";
/*     */     
/* 367 */     Connection themeConnec = null;
/*     */     
/* 369 */     PreparedStatement pstmt = null;
/*     */     
/*     */     try {
/* 372 */       themeConnec = DBConnectionUtility.getZoneConnection();
/* 373 */       pstmt = themeConnec.prepareStatement(query);
/*     */       
/* 375 */       pstmt.setString(1, category);
/* 376 */       pstmt.setString(2, fwdContractNo);
/* 377 */       pstmt.setString(3, customerID);
/* 378 */       pstmt.setString(4, postingDrCrFlag);
/* 379 */       pstmt.setString(5, postingAcctNo);
/* 380 */       pstmt.setString(6, postingBranch);
/* 381 */       pstmt.setString(7, postingAmount);
/* 382 */       pstmt.setString(8, postingCcy);
/* 383 */       pstmt.setString(9, valueDate);
/* 384 */       pstmt.setString(10, postingDesc);
/* 385 */       pstmt.setString(11, Integer.toString(serialNo));
/*     */       
/* 387 */       pstmt.executeUpdate();
/*     */       
/* 389 */       logger.info("FWC Postings are added successfully with count: " + pstmt.getUpdateCount());
/*     */     }
/* 391 */     catch (SQLException e) {
/* 392 */       e.printStackTrace();
/* 393 */     } catch (Exception e) {
/* 394 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 397 */         pstmt.close();
/* 398 */         themeConnec.close();
/* 399 */       } catch (SQLException e) {
/* 400 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\PostingUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */