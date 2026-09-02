/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import in.co.forwardcontract.service.model.TreasUpdateBankReq;
/*     */ import in.co.forwardcontract.service.model.TreasUpdateBankReqData;
/*     */ import in.co.forwardcontract.service.model.TreasuryBankRes;
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
/*     */ public class TreasUpdateUtil
/*     */ {
/*  21 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.TreasUpdateUtil.class);
/*     */   
/*  23 */   static String bankRequestJson = "";
/*  24 */   static String bankEncRequest = "";
/*  25 */   static String EncrequestJson = "";
/*     */ 
/*     */ 
/*     */   
/*  29 */   static Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   
/*     */   public static Map<String, String> updateUtilizationAmountInTreasury(String treasuryRefNo, String fwdContractAmt) {
/*  32 */     String plainBankRequest = "";
/*  33 */     String tempEncRequest = "";
/*  34 */     String encBankRequest = "";
/*  35 */     String encBankResponse = "";
/*  36 */     String plainBankResponse = "";
/*  37 */     Timestamp bankRequestTime = null;
/*  38 */     Timestamp bankResponseTime = null;
/*  39 */     String status = "FAILED";
/*  40 */     ServiceUtility.getProperties();
/*  41 */     String treasUpdateURL = (String)ServiceUtility.TBProperties.get("TREAS_UPDATE_URL");
/*  42 */     String treasUpdateKey = (String)ServiceUtility.TBProperties.get("TREAS_UPDATE_KEY");
/*  43 */     Map<String, String> responseTokens = null;
/*  44 */     logger.info("Inside updateUtilizedAmountInTreasury");
/*     */ 
/*     */     
/*     */     try {
/*  48 */       Map<String, String> result = generateTreasUpdateBankRequest(treasuryRefNo, fwdContractAmt);
/*  49 */       plainBankRequest = result.get("JSON");
/*  50 */       logger.info("treasUpdate Bank Request in Json Format -->" + plainBankRequest);
/*     */ 
/*     */       
/*  53 */       tempEncRequest = ServiceUtility.generateEncryptBankRequest(plainBankRequest, treasUpdateKey);
/*     */ 
/*     */       
/*  56 */       encBankRequest = generateEncryptedTreasUpdateJson(tempEncRequest, result.get("MSGID"));
/*     */       
/*  58 */       logger.info("treasUpdate Bank Enc Request -->" + encBankRequest);
/*  59 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*  60 */       encBankResponse = ServiceUtility.getBankFinResponse(encBankRequest, treasUpdateURL);
/*  61 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/*     */       
/*  63 */       plainBankResponse = ServiceUtility.generateDecryptBankResponse(encBankResponse, treasUpdateKey);
/*  64 */       logger.info("treasUpdate Bank Json Response -->" + plainBankResponse);
/*     */       
/*  66 */       if (plainBankResponse != null) {
/*  67 */         responseTokens = getTreasUpdateResponseTokens(plainBankResponse);
/*     */       }
/*  69 */       if (plainBankResponse != null && ((String)responseTokens.get("TreasUpdateStatus")).contains("S"))
/*  70 */       { status = "SUCCEEDED"; }
/*  71 */       else { status = "FAILED"; }
/*     */       
/*  73 */       ServiceLogging.pushServiceLogData("TREAS", "TREASUpdate", "ZONE1", "FTI", "Treasury", treasuryRefNo, "", status, 
/*  74 */           plainBankRequest, plainBankResponse, bankRequestTime, bankResponseTime);
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       e.printStackTrace();
/*     */     } 
/*  79 */     return responseTokens;
/*     */   }
/*     */   
/*     */   public static Map<String, String> generateTreasUpdateBankRequest(String treasuryRefNo, String fwdContractAmt) {
/*  83 */     Map<String, String> result = new HashMap<>();
/*  84 */     TreasUpdateBankReq aRequestHeader = new TreasUpdateBankReq();
/*  85 */     TreasUpdateBankReqData aRequestData = new TreasUpdateBankReqData();
/*  86 */     String sequence = null;
/*  87 */     String bankRequest = null;
/*     */ 
/*     */     
/*     */     try {
/*  91 */       String option = "7";
/*     */       
/*  93 */       logger.info("treRefNo: " + treasuryRefNo);
/*  94 */       logger.info("fwdContractAmt: " + fwdContractAmt);
/*     */       
/*  96 */       sequence = ServiceUtility.getSqlLocalDateTime().toString();
/*  97 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/*  99 */       aRequestData.setOption(option);
/* 100 */       aRequestData.setUtilizationAmount(fwdContractAmt);
/* 101 */       aRequestData.setUnUtilizedAmount("0");
/* 102 */       aRequestData.setRefAmount(fwdContractAmt);
/* 103 */       aRequestData.setTreRefNo(treasuryRefNo);
/*     */       
/* 105 */       aRequestHeader.setRequestType("0");
/* 106 */       aRequestHeader.setMsgid(sequence);
/* 107 */       aRequestHeader.setData(aRequestData);
/* 108 */       bankRequest = aGson.toJson(aRequestHeader).trim();
/* 109 */       logger.info("bankRequest of TreasUpdate: " + bankRequest);
/*     */     }
/* 111 */     catch (Exception e) {
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 115 */     result.put("JSON", bankRequest);
/* 116 */     result.put("MSGID", sequence);
/* 117 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateEncryptedTreasUpdateJson(String bankEncRequest, String msgId) {
/* 122 */     ServiceUtility aBackOfficeBatchEncryptedReq = new ServiceUtility(bankEncRequest, 
/* 123 */         msgId);
/* 124 */     String reqJson = aGson.toJson(aBackOfficeBatchEncryptedReq).trim();
/* 125 */     return reqJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, String> getTreasUpdateResponseTokens(String plainBankResponse) {
/* 130 */     Map<String, String> treasUpdateTokens = new HashMap<>();
/* 131 */     TreasuryBankRes treasuryBankRes = new TreasuryBankRes();
/* 132 */     treasUpdateTokens.put("TreasUpdateStatus", "F");
/* 133 */     logger.info("Entering getTreasUpdateResponseTokens ");
/*     */     try {
/* 135 */       treasuryBankRes = (TreasuryBankRes)aGson.fromJson(plainBankResponse, TreasuryBankRes.class);
/*     */       
/* 137 */       if (treasuryBankRes != null && treasuryBankRes.getData() != null && 
/* 138 */         treasuryBankRes.getData().getExecuteFinacleScript_CustomData() != null) {
/*     */         
/* 140 */         String status = treasuryBankRes.getData().getExecuteFinacleScript_CustomData().getSuccessorfailure();
/*     */         
/* 142 */         treasUpdateTokens.put("TreasUpdateStatus", status);
/*     */       } else {
/* 144 */         treasUpdateTokens.put("TreasUpdateStatus", "F");
/*     */       } 
/* 146 */       logger.info("Exiting getTreasUpdateResponseTokens ");
/*     */     }
/* 148 */     catch (Exception e) {
/* 149 */       logger.info("Exception in getTreasUpdateResponseTokens " + e.getMessage());
/* 150 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 153 */     return treasUpdateTokens;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\TreasUpdateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */