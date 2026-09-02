/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import in.co.forwardcontract.service.model.FtrtUpdateBankReq;
/*     */ import in.co.forwardcontract.service.model.FtrtUpdateBankReqData;
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
/*     */ public class FtrtUpdateUtil
/*     */ {
/*  21 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.FtrtUpdateUtil.class);
/*     */   
/*  23 */   static String bankRequestJson = "";
/*  24 */   static String bankEncRequest = "";
/*  25 */   static String EncrequestJson = "";
/*     */ 
/*     */ 
/*     */   
/*  29 */   static Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   
/*     */   public static Map<String, String> updateUtilizedAmountInFinacle(String treasuryRefNo, String fwdContractAmt) {
/*  32 */     String plainBankRequest = "";
/*  33 */     String tempEncRequest = "";
/*  34 */     String encBankRequest = "";
/*  35 */     String encBankResponse = "";
/*  36 */     String plainBankResponse = "";
/*  37 */     Timestamp bankRequestTime = null;
/*  38 */     Timestamp bankResponseTime = null;
/*  39 */     String status = "FAILED";
/*  40 */     ServiceUtility.getProperties();
/*  41 */     String ftrtUpdateURL = (String)ServiceUtility.TBProperties.get("FTRT_UPDATE_URL");
/*  42 */     String ftrtUpdateKey = (String)ServiceUtility.TBProperties.get("FTRT_UPDATE_KEY");
/*  43 */     Map<String, String> responseTokens = null;
/*  44 */     logger.info("Inside updateUtilizedAmountInFinacle");
/*     */ 
/*     */     
/*     */     try {
/*  48 */       Map<String, String> result = generateFtrtUpdateBankRequest(treasuryRefNo, fwdContractAmt);
/*  49 */       plainBankRequest = result.get("JSON");
/*  50 */       logger.info("ftrtUpdate Bank Request in Json Format -->" + plainBankRequest);
/*     */       
/*  52 */       tempEncRequest = ServiceUtility.generateEncryptBankRequest(plainBankRequest, ftrtUpdateKey);
/*     */ 
/*     */       
/*  55 */       encBankRequest = generateEncryptedFtrtUpdateJson(tempEncRequest, result.get("MSGID"));
/*     */       
/*  57 */       logger.info("ftrtUpdate Bank Enc Request -->" + encBankRequest);
/*  58 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*  59 */       encBankResponse = ServiceUtility.getBankFinResponse(encBankRequest, ftrtUpdateURL);
/*  60 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/*     */       
/*  62 */       plainBankResponse = ServiceUtility.generateDecryptBankResponse(encBankResponse, ftrtUpdateKey);
/*  63 */       logger.info("ftrtUpdate Bank Json Response -->" + plainBankResponse);
/*     */       
/*  65 */       if (plainBankResponse != null) {
/*  66 */         responseTokens = getFtrtUpdateResponseTokens(plainBankResponse);
/*     */       }
/*  68 */       if (plainBankResponse != null && ((String)responseTokens.get("FtrtUpdateStatus")).contains("S"))
/*  69 */       { status = "SUCCEEDED"; }
/*  70 */       else { status = "FAILED"; }
/*     */       
/*  72 */       ServiceLogging.pushServiceLogData("FTRT", "FTRTUpdate", "ZONE1", "FTI", "Finacle", treasuryRefNo, "", status, 
/*  73 */           plainBankRequest, plainBankResponse, bankRequestTime, bankResponseTime);
/*     */     }
/*  75 */     catch (Exception e) {
/*  76 */       e.printStackTrace();
/*     */     } 
/*  78 */     return responseTokens;
/*     */   }
/*     */   
/*     */   public static Map<String, String> generateFtrtUpdateBankRequest(String treasuryRefNo, String fwdContractAmt) {
/*  82 */     FtrtUpdateBankReq ftrtUpdateBankReq = new FtrtUpdateBankReq();
/*  83 */     FtrtUpdateBankReqData ftrtUpdateBankReqData = new FtrtUpdateBankReqData();
/*  84 */     String bankRequest = null;
/*  85 */     Map<String, String> result = new HashMap<>();
/*  86 */     String sequence = null;
/*     */ 
/*     */     
/*     */     try {
/*  90 */       String option = "5";
/*  91 */       String status = "U";
/*     */       
/*  93 */       logger.info("trRefNum: " + treasuryRefNo);
/*  94 */       logger.info("fwdContractAmt: " + fwdContractAmt);
/*     */       
/*  96 */       sequence = ServiceUtility.getSqlLocalDateTime().toString();
/*  97 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/*  99 */       ftrtUpdateBankReqData.setOption(option);
/* 100 */       ftrtUpdateBankReqData.setStatus(status);
/* 101 */       ftrtUpdateBankReqData.setUtilizedAmount(fwdContractAmt);
/* 102 */       ftrtUpdateBankReqData.setTrRefNum(treasuryRefNo);
/* 103 */       ftrtUpdateBankReq.setRequestType("0");
/* 104 */       ftrtUpdateBankReq.setMsgid(sequence);
/* 105 */       ftrtUpdateBankReq.setData(ftrtUpdateBankReqData);
/* 106 */       bankRequest = aGson.toJson(ftrtUpdateBankReq).trim();
/* 107 */       logger.info("bankRequest of FtrtUpdate: " + bankRequest);
/*     */     }
/* 109 */     catch (Exception e) {
/* 110 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 113 */     result.put("JSON", bankRequest);
/* 114 */     result.put("MSGID", sequence);
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateEncryptedFtrtUpdateJson(String bankEncRequest, String msgId) {
/* 123 */     ServiceUtility encryptedReq = new ServiceUtility(bankEncRequest, 
/* 124 */         msgId);
/* 125 */     String reqJson = aGson.toJson(encryptedReq).trim();
/* 126 */     return reqJson;
/*     */   }
/*     */   
/*     */   public static Map<String, String> getFtrtUpdateResponseTokens(String plainBankResponse) {
/* 130 */     TreasuryBankRes treasuryBankRes = new TreasuryBankRes();
/* 131 */     Map<String, String> ftrtUpdateTokens = new HashMap<>();
/* 132 */     ftrtUpdateTokens.put("FtrtUpdateStatus", "F");
/* 133 */     logger.info("Entering getFtrtUpdateResponseTokens ");
/*     */ 
/*     */     
/*     */     try {
/* 137 */       treasuryBankRes = (TreasuryBankRes)aGson.fromJson(plainBankResponse, TreasuryBankRes.class);
/*     */       
/* 139 */       if (treasuryBankRes != null && treasuryBankRes.getData() != null && 
/* 140 */         treasuryBankRes.getData().getExecuteFinacleScript_CustomData() != null) {
/*     */         
/* 142 */         String status = treasuryBankRes.getData().getExecuteFinacleScript_CustomData().getSuccessorfailure();
/*     */         
/* 144 */         ftrtUpdateTokens.put("FtrtUpdateStatus", status);
/*     */       } else {
/*     */         
/* 147 */         ftrtUpdateTokens.put("FtrtUpdateStatus", "F");
/*     */       } 
/* 149 */       logger.info("Exiting getFtrtUpdateResponseTokens ");
/*     */     }
/* 151 */     catch (Exception e) {
/* 152 */       logger.info("Exception in getFtrtUpdateResponseTokens " + e.getMessage());
/* 153 */       e.printStackTrace();
/*     */     } 
/* 155 */     return ftrtUpdateTokens;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\FtrtUpdateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */