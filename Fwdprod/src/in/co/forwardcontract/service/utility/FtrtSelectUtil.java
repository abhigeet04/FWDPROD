/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import in.co.forwardcontract.service.model.FtrtSelectBankReq;
/*     */ import in.co.forwardcontract.service.model.FtrtSelectBankReqData;
/*     */ import in.co.forwardcontract.service.model.TreasuryBankRes;
/*     */ import in.co.forwardcontract.service.model.TreasuryBankResCustomDataDetails;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.ServiceLogging;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FtrtSelectUtil
/*     */ {
/*  24 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.FtrtSelectUtil.class);
/*     */   
/*  26 */   static String bankRequestJson = "";
/*  27 */   static String bankEncRequest = "";
/*  28 */   static String EncrequestJson = "";
/*     */ 
/*     */ 
/*     */   
/*  32 */   static Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */ 
/*     */   
/*     */   public static Map<String, String> getRateDetailsFromFtrtAPI(String contractRef, String customer) {
/*  36 */     String plainBankRequest = "";
/*  37 */     String tempEncRequest = "";
/*  38 */     String encBankRequest = "";
/*  39 */     String encBankResponse = "";
/*  40 */     String plainBankResponse = "";
/*  41 */     Timestamp bankRequestTime = null;
/*  42 */     Timestamp bankResponseTime = null;
/*  43 */     String status = "FAILED";
/*  44 */     ServiceUtility.getProperties();
/*  45 */     String ftrtSelectURL = (String)ServiceUtility.TBProperties.get("FTRT_SELECT_URL");
/*  46 */     String ftrtSelectKey = (String)ServiceUtility.TBProperties.get("FTRT_SELECT_KEY");
/*  47 */     Map<String, String> responseTokens = null;
/*     */     
/*     */     try {
/*  50 */       logger.info("ftrtSelectURL & ftrtSelectKey  --> " + ftrtSelectURL + " & " + ftrtSelectKey);
/*     */       
/*  52 */       Map<String, String> result = generateFtrtSelectBankRequest(contractRef, customer);
/*  53 */       plainBankRequest = result.get("JSON");
/*  54 */       logger.info("FtrtSelect Bank Request in Json Format -->" + plainBankRequest);
/*     */ 
/*     */       
/*  57 */       tempEncRequest = ServiceUtility.generateEncryptBankRequest(plainBankRequest, ftrtSelectKey);
/*     */ 
/*     */       
/*  60 */       encBankRequest = generateEncryptedFtrtSelectJson(tempEncRequest, result.get("MSGID"));
/*     */       
/*  62 */       logger.info("FtrtSelect Bank Enc Request -->" + encBankRequest);
/*  63 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*  64 */       encBankResponse = ServiceUtility.getBankFinResponse(encBankRequest, ftrtSelectURL);
/*  65 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/*     */       
/*  67 */       plainBankResponse = ServiceUtility.generateDecryptBankResponse(encBankResponse, ftrtSelectKey);
/*  68 */       logger.info("FtrtSelect Bank Json Response -->" + plainBankResponse);
/*     */       
/*  70 */       responseTokens = getRateFtrtTokenDetails(plainBankResponse);
/*     */       
/*  72 */       if (((String)responseTokens.get("FtrtSelectStatus")).contains("S"))
/*  73 */       { status = "SUCCEEDED"; }
/*  74 */       else { status = "FAILED"; }
/*     */       
/*  76 */       ServiceLogging.pushServiceLogData("FTRT", "FTRTSelect", "ZONE1", "FTI", "Finacle", contractRef, "", status, 
/*  77 */           plainBankRequest, plainBankResponse, bankRequestTime, bankResponseTime);
/*     */     }
/*  79 */     catch (Exception e) {
/*  80 */       e.printStackTrace();
/*     */     } 
/*  82 */     return responseTokens;
/*     */   }
/*     */   
/*     */   public static Map<String, String> generateFtrtSelectBankRequest(String contractRef, String customer) {
/*  86 */     FtrtSelectBankReqData aRequestData = new FtrtSelectBankReqData();
/*  87 */     FtrtSelectBankReq aRequestHeader = new FtrtSelectBankReq();
/*  88 */     Map<String, String> result = new HashMap<>();
/*  89 */     String bankRequest = null;
/*  90 */     String sequence = null;
/*     */     
/*     */     try {
/*  93 */       String option = "4";
/*  94 */       sequence = ServiceUtility.getSqlLocalDateTime().toString();
/*  95 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/*  97 */       logger.info("contractRef: " + contractRef);
/*  98 */       logger.info("customer: " + customer);
/*     */       
/* 100 */       aRequestData.setOption(option);
/* 101 */       aRequestData.setTrRefNum(contractRef);
/* 102 */       aRequestData.setCifId(customer);
/*     */       
/* 104 */       aRequestHeader.setRequestType("0");
/* 105 */       aRequestHeader.setMsgid(sequence);
/* 106 */       aRequestHeader.setData(aRequestData);
/* 107 */       bankRequest = aGson.toJson(aRequestHeader).trim();
/* 108 */       logger.info("bankRequest of FtrtSelect: " + bankRequest);
/*     */     }
/* 110 */     catch (Exception e) {
/* 111 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 114 */     result.put("JSON", bankRequest);
/* 115 */     result.put("MSGID", sequence);
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateEncryptedFtrtSelectJson(String bankEncRequest, String msgId) {
/* 123 */     ServiceUtility encryptedReq = new ServiceUtility(bankEncRequest, 
/* 124 */         msgId);
/* 125 */     String reqJson = aGson.toJson(encryptedReq).trim();
/* 126 */     return reqJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, String> getRateFtrtTokenDetails(String plainFtrtSelectBankResponse) {
/* 131 */     Map<String, String> rateTokens = new HashMap<>();
/* 132 */     TreasuryBankRes treasuryBankRes = new TreasuryBankRes();
/* 133 */     rateTokens.put("FtrtSelectStatus", "F");
/*     */ 
/*     */     
/*     */     try {
/* 137 */       treasuryBankRes = (TreasuryBankRes)aGson.fromJson(plainFtrtSelectBankResponse, TreasuryBankRes.class);
/*     */       
/* 139 */       if (treasuryBankRes != null && treasuryBankRes.getData() != null && 
/* 140 */         treasuryBankRes.getData().getExecuteFinacleScript_CustomData() != null) {
/*     */         
/* 142 */         String status = treasuryBankRes.getData().getExecuteFinacleScript_CustomData().getSuccessorfailure();
/*     */         
/* 144 */         rateTokens.put("FtrtSelectStatus", status);
/*     */         
/* 146 */         if (status.equalsIgnoreCase("F")) {
/* 147 */           String message = treasuryBankRes.getData().getExecuteFinacleScript_CustomData().getMessage();
/* 148 */           rateTokens.put("Message", message);
/*     */         } 
/*     */         
/* 151 */         logger.info("status -->" + status);
/*     */         
/* 153 */         if (status.equalsIgnoreCase("S")) {
/*     */           
/* 155 */           TreasuryBankResCustomDataDetails ftrtSelectBankResCustomDataDetails = treasuryBankRes.getData()
/* 156 */             .getExecuteFinacleScript_CustomData().getStatementTransactionDetail();
/*     */           
/* 158 */           rateTokens.put("TrRefNum", ftrtSelectBankResCustomDataDetails.getTR_REF_NUM());
/* 159 */           rateTokens.put("SwapRate", ftrtSelectBankResCustomDataDetails.getSWAP_RATE());
/* 160 */           rateTokens.put("CustRate", ftrtSelectBankResCustomDataDetails.getCUST_RATE());
/* 161 */           rateTokens.put("FcRefNum", ftrtSelectBankResCustomDataDetails.getFC_REF_NUM());
/* 162 */           rateTokens.put("RelatedTrRefNum", ftrtSelectBankResCustomDataDetails.getRELATED_TR_REF_NUM());
/* 163 */           rateTokens.put("Remarks", ftrtSelectBankResCustomDataDetails.getREMARKS());
/* 164 */           rateTokens.put("BankId", ftrtSelectBankResCustomDataDetails.getBANK_ID());
/* 165 */           rateTokens.put("ToCrncyCode", ftrtSelectBankResCustomDataDetails.getTO_CRNCY_CODE());
/* 166 */           rateTokens.put("Status", ftrtSelectBankResCustomDataDetails.getSTATUS());
/* 167 */           rateTokens.put("EntityCreFlg", ftrtSelectBankResCustomDataDetails.getENTITY_CRE_FLG());
/* 168 */           rateTokens.put("RateCode", ftrtSelectBankResCustomDataDetails.getRATECODE());
/* 169 */           rateTokens.put("LchgUserId", ftrtSelectBankResCustomDataDetails.getLCHG_USER_ID());
/* 170 */           rateTokens.put("SwapChargeRate", ftrtSelectBankResCustomDataDetails.getSWAP_CHARGE_RATE());
/* 171 */           rateTokens.put("BuyOrSell", ftrtSelectBankResCustomDataDetails.getBUY_OR_SELL());
/* 172 */           rateTokens.put("TsCnt", ftrtSelectBankResCustomDataDetails.getTS_CNT());
/* 173 */           rateTokens.put("RefAmt", ftrtSelectBankResCustomDataDetails.getREF_AMT());
/* 174 */           rateTokens.put("CifId", ftrtSelectBankResCustomDataDetails.getCIF_ID());
/* 175 */           rateTokens.put("FreeCode1", ftrtSelectBankResCustomDataDetails.getFREE_CODE_1());
/* 176 */           rateTokens.put("UtilizedAmt", ftrtSelectBankResCustomDataDetails.getUTILIZED_AMT());
/* 177 */           rateTokens.put("FreeCode2", ftrtSelectBankResCustomDataDetails.getFREE_CODE_2());
/* 178 */           rateTokens.put("FreeCode3", ftrtSelectBankResCustomDataDetails.getFREE_CODE_3());
/* 179 */           rateTokens.put("RcreUserId", ftrtSelectBankResCustomDataDetails.getRCRE_USER_ID());
/* 180 */           rateTokens.put("DelFlg", ftrtSelectBankResCustomDataDetails.getDEL_FLG());
/* 181 */           rateTokens.put("FromCrncyCode", ftrtSelectBankResCustomDataDetails.getFROM_CRNCY_CODE());
/* 182 */           rateTokens.put("FundsDeliveryDate", ftrtSelectBankResCustomDataDetails.getFUNDS_DELIVERY_DATE());
/* 183 */           rateTokens.put("RcreTime", ftrtSelectBankResCustomDataDetails.getRCRE_TIME());
/* 184 */           rateTokens.put("RequestDate", ftrtSelectBankResCustomDataDetails.getREQUEST_DATE());
/* 185 */           rateTokens.put("EventId", ftrtSelectBankResCustomDataDetails.getEVENT_ID());
/* 186 */           rateTokens.put("LchgTime", ftrtSelectBankResCustomDataDetails.getLCHG_TIME());
/* 187 */           rateTokens.put("TreasuryRate", ftrtSelectBankResCustomDataDetails.getTREASURY_RATE());
/*     */           
/* 189 */           logger.info("Ftrt API for TrRefNum: " + (String)rateTokens.get("TrRefNum"));
/*     */           
/* 191 */           logger.info("Ref & Utilized Amount: " + (String)rateTokens.get("RefAmt") + " & " + 
/* 192 */               (String)rateTokens.get("UtilizedAmt"));
/*     */         } 
/*     */       } else {
/*     */         
/* 196 */         rateTokens.put("FtrtSelectStatus", "F");
/*     */       }
/*     */     
/* 199 */     } catch (Exception e) {
/* 200 */       e.printStackTrace();
/*     */     } 
/* 202 */     return rateTokens;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\FtrtSelectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */