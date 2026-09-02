/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.infrasoft.kiya.security.EncryptionDecryptionImpl;
/*     */ import in.co.forwardcontract.service.model.AccountStatusReq;
/*     */ import in.co.forwardcontract.service.model.AccountStatusReqEnc;
/*     */ import in.co.forwardcontract.service.model.AccountStatusResp;
/*     */ import in.co.forwardcontract.service.model.ReqAccStatusData;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.RequestEntity;
/*     */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*     */ 
/*     */ public class AccountStatusUtility {
/*  17 */   EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*     */ 
/*     */   
/*     */   public String getStatusAccount(String requestType, String msgid, String type, String accountNumber, String senderCode) {
/*  21 */     ServiceUtility.getProperties();
/*  22 */     String key = (String)ServiceUtility.TBProperties.get("ACCOUNT_STATUS_KEY");
/*  23 */     String url = (String)ServiceUtility.TBProperties.get("ACCOUNT_STATUS_URL");
/*  24 */     String accStatus = "";
/*     */     
/*     */     try {
/*  27 */       String plainReqJson = generateAccountStatusJson(requestType, msgid, type, accountNumber, senderCode);
/*  28 */       System.out.println("Account Status Plain Request : " + plainReqJson);
/*     */       
/*  30 */       String encReqJson = encryptReqJson(plainReqJson, key);
/*     */       
/*  32 */       System.out.println("Account Status Encrypted Request : " + encReqJson);
/*     */       
/*  34 */       String encRequest = generateEncReq(encReqJson, msgid);
/*  35 */       String bankEncRes = callBankEndPoint(encRequest, url);
/*  36 */       System.out.println("Account Status Encrypted Bank Response : " + bankEncRes);
/*     */       
/*  38 */       String bankPlainRes = decryptResJson(bankEncRes, key);
/*     */       
/*  40 */       System.out.println("Account Status Plain Bank Response : " + bankPlainRes);
/*     */       
/*  42 */       accStatus = getAccountStatus(bankPlainRes);
/*     */     
/*     */     }
/*  45 */     catch (Exception e) {
/*  46 */       e.printStackTrace();
/*     */     } 
/*  48 */     return accStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String generateAccountStatusJson(String requestType, String msgid, String type, String accountNumber, String senderCode) {
/*  54 */     AccountStatusReq aAccountStatusReq = new AccountStatusReq();
/*  55 */     ReqAccStatusData aReqAccStatusData = new ReqAccStatusData();
/*  56 */     aAccountStatusReq.setRequestType(requestType);
/*  57 */     aAccountStatusReq.setMsgid(msgid);
/*  58 */     aReqAccStatusData.setType(type);
/*  59 */     aReqAccStatusData.setAccountNumber(accountNumber);
/*  60 */     aReqAccStatusData.setSenderCode(senderCode);
/*     */     
/*  62 */     aAccountStatusReq.setData(aReqAccStatusData);
/*     */     
/*  64 */     Gson agson = new Gson();
/*  65 */     String jsonString = agson.toJson(aAccountStatusReq);
/*  66 */     return jsonString;
/*     */   }
/*     */   
/*     */   public String generateEncReq(String encReqJson, String msgid) {
/*  70 */     AccountStatusReqEnc aAccountStatusReqEnc = new AccountStatusReqEnc();
/*  71 */     aAccountStatusReqEnc.setReqdata(encReqJson);
/*  72 */     aAccountStatusReqEnc.setMsgid(msgid);
/*  73 */     Gson aGson = new Gson();
/*  74 */     return aGson.toJson(aAccountStatusReqEnc);
/*     */   }
/*     */   
/*     */   public String encryptReqJson(String reqJsonStr, String key) {
/*  78 */     String encMes = null;
/*     */     try {
/*  80 */       encMes = this.obj.encryptMessage(reqJsonStr, key);
/*  81 */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     } 
/*  84 */     return encMes;
/*     */   }
/*     */   
/*     */   public String callBankEndPoint(String bankEncReq, String url) {
/*  88 */     String encResponse = null;
/*  89 */     PostMethod post = new PostMethod(url);
/*     */     try {
/*  91 */       StringRequestEntity requestEntity = new StringRequestEntity(bankEncReq, "application/json", "utf-8");
/*  92 */       post.setRequestEntity((RequestEntity)requestEntity);
/*  93 */       HttpClient httpclient = new HttpClient();
/*  94 */       int result = httpclient.executeMethod((HttpMethod)post);
/*  95 */       if (result != 200) {
/*  96 */         throw new Exception("Server returned code " + result);
/*     */       }
/*  98 */       encResponse = post.getResponseBodyAsString();
/*  99 */     } catch (Exception e) {
/* 100 */       e.printStackTrace();
/*     */     } finally {
/* 102 */       post.releaseConnection();
/*     */     } 
/* 104 */     return encResponse.trim();
/*     */   }
/*     */   
/*     */   public String decryptResJson(String encJsonResp, String key) {
/* 108 */     String plainResJson = null;
/*     */     try {
/* 110 */       plainResJson = this.obj.decryptMessage(encJsonResp, key);
/* 111 */     } catch (Exception e) {
/* 112 */       e.printStackTrace();
/*     */     } 
/* 114 */     return plainResJson;
/*     */   }
/*     */   
/*     */   public String getAccountStatus(String decRespJson) {
/* 118 */     Gson aGson = new Gson();
/* 119 */     AccountStatusResp aAccountStatusResp = (AccountStatusResp)aGson.fromJson(decRespJson, AccountStatusResp.class);
/* 120 */     return aAccountStatusResp.getData().getStatus();
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\AccountStatusUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */