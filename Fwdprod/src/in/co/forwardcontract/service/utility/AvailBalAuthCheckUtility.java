/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.infrasoft.kiya.security.EncryptionDecryptionImpl;
/*     */ import in.co.forwardcontract.service.model.AccountAvailBalBankResponse;
/*     */ import in.co.forwardcontract.service.model.AccountAvailBalEncryptedRequest;
/*     */ import in.co.forwardcontract.service.model.AccountAvailRequestData;
/*     */ import in.co.forwardcontract.service.model.AccountAvailRequestHeader;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.RequestEntity;
/*     */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class AvailBalAuthCheckUtility
/*     */ {
/*  21 */   EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*  22 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.AvailBalAuthCheckUtility.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAccountBalance(String requestType, String msgid, String type, String accountNumber, String senderCode) {
/*  28 */     ServiceUtility.getProperties();
/*     */ 
/*     */     
/*  31 */     String key = (String)ServiceUtility.TBProperties.get("AVAIL_BAL_KEY");
/*  32 */     String url = (String)ServiceUtility.TBProperties.get("AVAIL_BAL_URL");
/*     */     
/*  34 */     String accBalance = "";
/*     */     
/*     */     try {
/*  37 */       String plainReqJson = generateAccountBalanceJson(requestType, msgid, type, accountNumber, senderCode);
/*  38 */       logger.info("Account Balance Plain Request : " + plainReqJson);
/*     */       
/*  40 */       String encReqJson = encryptReqJson(plainReqJson, key);
/*     */       
/*  42 */       logger.info("Account Balance Encrypted Request : " + encReqJson);
/*     */       
/*  44 */       String encRequest = generateEncReq(encReqJson, msgid);
/*  45 */       String bankEncRes = callBankEndPoint(encRequest, url);
/*  46 */       logger.info("Account Balance Encrypted Bank Response : " + bankEncRes);
/*     */       
/*  48 */       String bankPlainRes = decryptResJson(bankEncRes, key);
/*  49 */       if (bankPlainRes != null) {
/*     */         
/*  51 */         logger.info("Account Balance Plain Bank Response : " + bankPlainRes);
/*     */       }
/*     */       else {
/*     */         
/*  55 */         logger.info("Account Balance Plain Bank Response is NULL");
/*     */       } 
/*     */ 
/*     */       
/*  59 */       accBalance = getAccountStatus(bankPlainRes);
/*     */     
/*     */     }
/*  62 */     catch (Exception e) {
/*  63 */       e.printStackTrace();
/*     */     } 
/*  65 */     logger.info("Account Balance Plain Bank Response : " + accBalance);
/*  66 */     return accBalance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String generateAccountBalanceJson(String requestType, String msgid, String type, String accountNumber, String senderCode) {
/*  73 */     AccountAvailRequestHeader aAccountBalanceReq = new AccountAvailRequestHeader();
/*  74 */     AccountAvailRequestData aReqAccBalanceData = new AccountAvailRequestData();
/*  75 */     aAccountBalanceReq.setRequestType(requestType);
/*  76 */     aAccountBalanceReq.setMsgid(msgid);
/*  77 */     aReqAccBalanceData.setType(type);
/*  78 */     aReqAccBalanceData.setAccountNumber(accountNumber);
/*  79 */     aReqAccBalanceData.setSenderCode(senderCode);
/*     */     
/*  81 */     aAccountBalanceReq.setData(aReqAccBalanceData);
/*     */     
/*  83 */     Gson agson = new Gson();
/*  84 */     String jsonString = agson.toJson(aAccountBalanceReq);
/*  85 */     return jsonString;
/*     */   }
/*     */   
/*     */   public String generateEncReq(String encReqJson, String msgid) {
/*  89 */     AccountAvailBalEncryptedRequest aAccountBalanceReqEnc = new AccountAvailBalEncryptedRequest();
/*  90 */     aAccountBalanceReqEnc.setReqdata(encReqJson);
/*  91 */     aAccountBalanceReqEnc.setMsgid(msgid);
/*  92 */     Gson aGson = new Gson();
/*  93 */     return aGson.toJson(aAccountBalanceReqEnc);
/*     */   }
/*     */   
/*     */   public String encryptReqJson(String reqJsonStr, String key) {
/*  97 */     String encMes = null;
/*     */     try {
/*  99 */       encMes = this.obj.encryptMessage(reqJsonStr, key);
/* 100 */     } catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     } 
/* 103 */     return encMes;
/*     */   }
/*     */   
/*     */   public String callBankEndPoint(String bankEncReq, String url) {
/* 107 */     String encResponse = null;
/* 108 */     PostMethod post = new PostMethod(url);
/*     */     try {
/* 110 */       StringRequestEntity requestEntity = new StringRequestEntity(bankEncReq, "application/json", "utf-8");
/* 111 */       post.setRequestEntity((RequestEntity)requestEntity);
/* 112 */       HttpClient httpclient = new HttpClient();
/* 113 */       int result = httpclient.executeMethod((HttpMethod)post);
/* 114 */       if (result != 200) {
/* 115 */         throw new Exception("Server returned code " + result);
/*     */       }
/* 117 */       encResponse = post.getResponseBodyAsString();
/* 118 */     } catch (Exception e) {
/* 119 */       e.printStackTrace();
/*     */     } finally {
/* 121 */       post.releaseConnection();
/*     */     } 
/* 123 */     return encResponse.trim();
/*     */   }
/*     */   
/*     */   public String decryptResJson(String encJsonResp, String key) {
/* 127 */     String plainResJson = null;
/*     */     try {
/* 129 */       plainResJson = this.obj.decryptMessage(encJsonResp, key);
/*     */     }
/* 131 */     catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */     } 
/* 134 */     return plainResJson;
/*     */   }
/*     */   
/*     */   public String getAccountStatus(String decRespJson) {
/* 138 */     Gson aGson = new Gson();
/* 139 */     AccountAvailBalBankResponse aAccountBalanceResp = (AccountAvailBalBankResponse)aGson.fromJson(decRespJson, AccountAvailBalBankResponse.class);
/* 140 */     if (aAccountBalanceResp.getData() != null && aAccountBalanceResp.getData().getAmount() != null && aAccountBalanceResp.getData().getAmount().getAvailBal() != null) {
/* 141 */       return aAccountBalanceResp.getData().getAmount().getAvailBal();
/*     */     }
/* 143 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\AvailBalAuthCheckUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */