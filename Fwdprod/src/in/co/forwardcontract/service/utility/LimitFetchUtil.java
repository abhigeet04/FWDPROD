/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import in.co.forwardcontract.service.model.LimitFetchBankReq;
/*     */ import in.co.forwardcontract.service.model.LimitFetchBankRes;
/*     */ import in.co.forwardcontract.service.model.LimitFetchBankResCustLimitDtls;
/*     */ import in.co.forwardcontract.service.model.LimitFetchBankResNew;
/*     */ import in.co.forwardcontract.service.model.LimitFetchCustomerReq;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.ServiceLogging;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LimitFetchUtil
/*     */ {
/*  26 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.LimitFetchUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   static String tempEnc = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   static Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   static int tagcount;
/*     */   
/*     */   public static List<HashMap<String, String>> getLimitDetailsFromLimitAPI(String customer) {
/*  43 */     String plainBankRequest = "";
/*  44 */     String tempEncRequest = "";
/*  45 */     String encBankRequest = "";
/*  46 */     String encBankResponse = "";
/*  47 */     String plainBankResponse = "";
/*  48 */     Timestamp bankRequestTime = null;
/*  49 */     Timestamp bankResponseTime = null;
/*  50 */     String status = "FAILED";
/*  51 */     ServiceUtility.getProperties();
/*  52 */     String limitFetchURL = (String)ServiceUtility.TBProperties.get("LIMIT_FETCH_URL");
/*  53 */     String limitFetchKey = (String)ServiceUtility.TBProperties.get("LIMIT_FETCH_KEY");
/*  54 */     List<HashMap<String, String>> responseListTokens = null;
/*     */     
/*     */     try {
/*  57 */       logger.info("limitFetchURL & limitFetchKey  --> " + limitFetchURL + " & " + limitFetchKey);
/*     */       
/*  59 */       Map<String, String> result = generateLimitFetchBankRequest(customer);
/*  60 */       plainBankRequest = result.get("JSON");
/*  61 */       logger.info("LimitFetch Bank Request in Json Format -->" + plainBankRequest);
/*     */ 
/*     */       
/*  64 */       tempEncRequest = ServiceUtility.generateEncryptBankRequest(plainBankRequest, limitFetchKey);
/*     */ 
/*     */       
/*  67 */       encBankRequest = generateEncryptedFtrtSelectJson(tempEncRequest, result.get("MSGID"));
/*  68 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*  69 */       encBankResponse = ServiceUtility.getBankFinResponse(encBankRequest, limitFetchURL);
/*  70 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/*     */       
/*  72 */       plainBankResponse = ServiceUtility.generateDecryptBankResponse(encBankResponse, limitFetchKey);
/*     */       
/*  74 */       responseListTokens = getLimitFetchResponseTokens(plainBankResponse);
/*     */       
/*  76 */       if (responseListTokens.size() > 0) {
/*  77 */         status = "SUCCEEDED";
/*     */       } else {
/*  79 */         status = "FAILED";
/*     */       } 
/*  81 */       ServiceLogging.pushServiceLogData("Limit", "Fetch", "ZONE1", "FTI", "Finacle", customer, "", status, 
/*  82 */           plainBankRequest, plainBankResponse, bankRequestTime, bankResponseTime);
/*     */     }
/*  84 */     catch (Exception e) {
/*  85 */       e.printStackTrace();
/*     */     } 
/*  87 */     return responseListTokens;
/*     */   }
/*     */   
/*     */   public static Map<String, String> generateLimitFetchBankRequest(String customer) {
/*  91 */     String bankRequest = null;
/*     */ 
/*     */     
/*  94 */     LimitFetchBankReq limitFetchBankReq = new LimitFetchBankReq();
/*  95 */     LimitFetchCustomerReq limitFetchBankReqData = new LimitFetchCustomerReq();
/*  96 */     Map<String, String> result = new HashMap<>();
/*  97 */     String sequence = null;
/*     */ 
/*     */     
/*     */     try {
/* 101 */       sequence = ServiceUtility.getSqlLocalDateTime().toString();
/* 102 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/* 104 */       logger.info("customer: " + customer);
/*     */       
/* 106 */       limitFetchBankReqData.setCustCifId(customer);
/* 107 */       limitFetchBankReq.setMsgid(sequence);
/* 108 */       limitFetchBankReq.setData(limitFetchBankReqData);
/*     */       
/* 110 */       bankRequest = aGson.toJson(limitFetchBankReq).trim();
/*     */     }
/* 112 */     catch (Exception e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 116 */     result.put("JSON", bankRequest);
/* 117 */     result.put("MSGID", sequence);
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateEncryptedFtrtSelectJson(String bankEncRequest, String msgId) {
/* 124 */     ServiceUtility encryptedReq = new ServiceUtility(bankEncRequest, msgId);
/* 125 */     String reqJson = aGson.toJson(encryptedReq).trim();
/* 126 */     return reqJson;
/*     */   }
/*     */   
/*     */   public static List<HashMap<String, String>> getLimitFetchResponseTokens(String plainFtrtSelectBankResponse) {
/* 130 */     LimitFetchBankRes limitFetchBankRes = new LimitFetchBankRes();
/* 131 */     List<HashMap<String, String>> hashMapList = new ArrayList<>();
/* 132 */     logger.info("Entering getLimitTokenDetails ");
/*     */     
/*     */     try {
/* 135 */       limitFetchBankRes = (LimitFetchBankRes)aGson.fromJson(plainFtrtSelectBankResponse, LimitFetchBankRes.class);
/*     */       
/* 137 */       if (limitFetchBankRes != null && limitFetchBankRes.getData() != null && 
/* 138 */         limitFetchBankRes.getData().getInquireLimitList() != null) {
/*     */         
/* 140 */         tagcount = limitFetchBankRes.getData().getInquireLimitList().getCustomerLimitDetails().size();
/* 141 */         logger.info("BankResponse Customer Limit Details Count ---> : " + tagcount);
/*     */         
/* 143 */         for (int i = 0; i < tagcount; i++) {
/*     */           
/* 145 */           HashMap<String, String> hashmap = new HashMap<>();
/*     */           
/* 147 */           String expiryDate = CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData()
/* 148 */               .getInquireLimitList().getCustomerLimitDetails().get(i)).getExpiryDate());
/* 149 */           String expiryDateFormatted = CommonMethods.getTiDateFormat(expiryDate);
/* 150 */           hashmap.put("ti_expiryDate", expiryDateFormatted);
/* 151 */           hashmap.put("expiryDate", expiryDate);
/*     */           
/* 153 */           String crncyCode = CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData().getInquireLimitList()
/* 154 */               .getCustomerLimitDetails().get(i)).getCrncyCode());
/* 155 */           hashmap.put("crncyCode", crncyCode);
/*     */           
/* 157 */           String limitPrefix = CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData()
/* 158 */               .getInquireLimitList().getCustomerLimitDetails().get(i)).getLimitPrefix());
/* 159 */           hashmap.put("limitPrefix", limitPrefix);
/*     */           
/* 161 */           String limitSufix = CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData()
/* 162 */               .getInquireLimitList().getCustomerLimitDetails().get(i)).getLimitSuffix());
/* 163 */           hashmap.put("limitSuffix", limitSufix);
/*     */           
/* 165 */           String limitAmt = CommonMethods.returnZeroIfEmpty(CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes
/* 166 */                 .getData().getInquireLimitList().getCustomerLimitDetails().get(i)).getLimitAmt()));
/* 167 */           hashmap.put("limitAmt", limitAmt);
/*     */           
/* 169 */           String sanDate = CommonMethods.getTiDateFormat(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData().getInquireLimitList()
/* 170 */               .getCustomerLimitDetails().get(i)).getSanctionDate());
/* 171 */           hashmap.put("ti_sanDate", sanDate);
/* 172 */           hashmap.put("sanDate", ((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData().getInquireLimitList().getCustomerLimitDetails()
/* 173 */               .get(i)).getSanctionDate());
/*     */           
/* 175 */           String limitDesc = CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData().getInquireLimitList()
/* 176 */               .getCustomerLimitDetails().get(i)).getLimitDesc());
/* 177 */           hashmap.put("limitDesc", limitDesc);
/*     */           
/* 179 */           String totalLiability = 
/* 180 */             CommonMethods.returnZeroIfEmpty(CommonMethods.returnEmptyIfNull(((LimitFetchBankResCustLimitDtls)limitFetchBankRes.getData()
/* 181 */                 .getInquireLimitList().getCustomerLimitDetails().get(i)).getTotalLiability()));
/* 182 */           hashmap.put("totalLiability", totalLiability);
/*     */           
/* 184 */           hashMapList.add(hashmap);
/*     */         } 
/*     */       } 
/* 187 */       logger.info("After fetching all the limit details - list size ----> " + hashMapList.size());
/* 188 */       logger.info("Exiting getLimitDetailsFromRes ");
/* 189 */     } catch (Exception e) {
/* 190 */       logger.info("Limit facilities exception in getLimitDetailsFromRes " + e.getMessage());
/* 191 */       e.printStackTrace();
/* 192 */       return getLimitDetailsFromResNew(plainFtrtSelectBankResponse);
/*     */     } 
/*     */     
/* 195 */     return hashMapList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<HashMap<String, String>> getLimitDetailsFromResNew(String plainBankRes) {
/* 200 */     List<HashMap<String, String>> hashMapList = new ArrayList<>();
/* 201 */     System.out.println("Entering getLimitDetailsFromRes ");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 207 */       LimitFetchBankResNew aLimitFetchBankResNew = (LimitFetchBankResNew)aGson.fromJson(plainBankRes, LimitFetchBankResNew.class);
/*     */       
/* 209 */       HashMap<String, String> hashmap = new HashMap<>();
/*     */       
/* 211 */       String expiryDate = CommonMethods.returnEmptyIfNull(
/* 212 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getExpiryDate());
/* 213 */       String expiryDateFormatted = CommonMethods.getTiDateFormat(expiryDate);
/* 214 */       hashmap.put("ti_expiryDate", expiryDateFormatted);
/* 215 */       hashmap.put("expiryDate", expiryDate);
/*     */       
/* 217 */       String crncyCode = CommonMethods.returnEmptyIfNull(
/* 218 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getCrncyCode());
/* 219 */       hashmap.put("crncyCode", crncyCode);
/*     */       
/* 221 */       String limitPrefix = CommonMethods.returnEmptyIfNull(
/* 222 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getLimitPrefix());
/* 223 */       hashmap.put("limitPrefix", limitPrefix);
/*     */       
/* 225 */       String limitSufix = CommonMethods.returnEmptyIfNull(
/* 226 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getLimitSuffix());
/* 227 */       hashmap.put("limitSuffix", limitSufix);
/*     */       
/* 229 */       String limitAmt = CommonMethods.returnZeroIfEmpty(CommonMethods.returnEmptyIfNull(
/* 230 */             aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getLimitAmt()));
/* 231 */       hashmap.put("limitAmt", limitAmt);
/*     */       
/* 233 */       String sanDate = CommonMethods.getTiDateFormat(
/* 234 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getSanctionDate());
/* 235 */       hashmap.put("ti_sanDate", sanDate);
/* 236 */       hashmap.put("sanDate", 
/* 237 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getSanctionDate());
/*     */       
/* 239 */       String limitDesc = CommonMethods.returnEmptyIfNull(
/* 240 */           aLimitFetchBankResNew.getData().getInquireLimitList().getCustomerLimitDetails().getLimitDesc());
/*     */ 
/*     */ 
/*     */       
/* 244 */       hashmap.put("limitDesc", limitDesc);
/*     */       
/* 246 */       String totalLiability = 
/* 247 */         CommonMethods.returnZeroIfEmpty(CommonMethods.returnEmptyIfNull(aLimitFetchBankResNew.getData()
/* 248 */             .getInquireLimitList().getCustomerLimitDetails().getTotalLiability()));
/* 249 */       hashmap.put("totalLiability", totalLiability);
/*     */       
/* 251 */       hashMapList.add(hashmap);
/*     */       
/* 253 */       System.out.println("After fecthing all the limit details list size ----> " + hashMapList.size());
/* 254 */       System.out.println("Exiting getLimitDetailsFromRes ");
/* 255 */     } catch (Exception e) {
/* 256 */       System.out.println("Limit facilities exception in getLimitDetailsFromRes " + e.getMessage());
/* 257 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 260 */     return hashMapList;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\LimitFetchUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */