/*     */ package in.co.forwardcontract.utility;
/*     */ import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/*     */ import com.infrasoft.kiya.security.EncryptionDecryptionImpl;
/*     */ import in.co.forwardcontract.service.model.DateTimeUtil;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryReqEnc;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryRequestData;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryRequestHeader;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryRespWithObject;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryRespWithObjectA;
/*     */ import in.co.forwardcontract.service.model.LimitEnquiryResponseLimitDetails;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.ServiceLogging;
/*     */ import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
/*     */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*     */ 
/*     */ public class LimitEnquiryAdaptee {
/*  18 */   LimitEnquiryRequestData aLimitEnquiryRequestData = new LimitEnquiryRequestData();
/*  19 */   LimitEnquiryRequestHeader aLimitEnquiryRequestHeader = new LimitEnquiryRequestHeader();
/*  20 */   EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*  21 */   Gson aGson = (new GsonBuilder()).disableHtmlEscaping().create();
/*     */   
/*     */   public String enquireLimitSerialNum(String custId, String refnum, String limitSuffix, String eventRef) {
/*  24 */     String serialNum = null;
/*  25 */     String requestType = "0";
/*  26 */     String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
/*  27 */     msgId = msgId.replaceAll("[- :.]", "");
/*  28 */     String plainReqJson = generateLimitEnquiryReq(requestType, custId, msgId, limitSuffix);
/*  29 */     System.out.println("Limit Enquiry Plain Request : " + plainReqJson);
/*  30 */     CommonMethods.getProperties();
/*  31 */     String key = (String)CommonMethods.TBProperties.get("LIMIT_ENQUIRY_KEY");
/*  32 */     String url = (String)CommonMethods.TBProperties.get("LIMIT_ENQUIRY_URL");
/*  33 */     String encReqJson = encryptReqJson(plainReqJson, key);
/*     */     
/*  35 */     System.out.println("Limit Enquiry URL&Key : " + url + " & " + key);
/*  36 */     System.out.println("Limit Enquiry Encrypted Request : " + encReqJson);
/*     */     
/*  38 */     String encRequest = generateEncReq(encReqJson, msgId);
/*  39 */     System.out.println("Enquiry encRequest:" + encRequest);
/*  40 */     String bankEncRes = callBankEndPoint(encRequest, url);
/*     */     
/*  42 */     String bankPlainRes = decryptResJson(bankEncRes, key);
/*     */     
/*  44 */     System.out.println("Limit Enquiry Plain Bank Response : " + bankPlainRes);
/*     */     
/*  46 */     if (bankPlainRes != null) {
/*  47 */       if (!bankPlainRes.contains("ErrorDetailList") && !bankPlainRes.contains("\"successorfailure\":\"N\"")) {
/*     */         
/*  49 */         serialNum = getRespSerialNum(bankPlainRes, refnum);
/*  50 */         ServiceLogging.pushServiceLogData("Limit", "Enquiry", "ZONE1", "FTI", "Finacle", refnum, serialNum, "SUCCEEDED", 
/*  51 */             plainReqJson, bankPlainRes, DateTimeUtil.getSqlLocalDateTime(), DateTimeUtil.getSqlLocalDateTime());
/*     */       } else {
/*     */         
/*  54 */         ServiceLogging.pushServiceLogData("Limit", "Enquiry", "ZONE1", "FTI", "Finacle", refnum, "", "FAILED", 
/*  55 */             plainReqJson, bankPlainRes, DateTimeUtil.getSqlLocalDateTime(), DateTimeUtil.getSqlLocalDateTime());
/*     */       } 
/*     */     } else {
/*  58 */       ServiceLogging.pushServiceLogData("Limit", "Enquiry", "ZONE1", "FTI", "Finacle", refnum, "", "FAILED", 
/*  59 */           plainReqJson, bankPlainRes, DateTimeUtil.getSqlLocalDateTime(), DateTimeUtil.getSqlLocalDateTime());
/*     */     } 
/*     */     
/*  62 */     return serialNum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRespSerialNum(String bankPlainRes, String refnum) {
/*  68 */     String serial_num = null;
/*  69 */     if (refnum == null || refnum.isEmpty()) {
/*  70 */       return serial_num;
/*     */     }
/*  72 */     Gson aGsonLimResp = new Gson();
/*  73 */     LimitEnquiryRespWithObject aLimitEnquiryRespWithObject = new LimitEnquiryRespWithObject();
/*  74 */     LimitEnquiryRespWithObjectA aLimitEnquiryRespWithObjectA = new LimitEnquiryRespWithObjectA();
/*     */     try {
/*  76 */       if (bankPlainRes != null && bankPlainRes.contains("\"UserLimitDetails\":[")) {
/*  77 */         aLimitEnquiryRespWithObject = (LimitEnquiryRespWithObject)aGsonLimResp.fromJson(bankPlainRes, LimitEnquiryRespWithObject.class);
/*  78 */         if (aLimitEnquiryRespWithObject != null && aLimitEnquiryRespWithObject.getData() != null && 
/*  79 */           aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList() != null && 
/*  80 */           aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList()
/*  81 */           .getUserLimitDetails() != null)
/*  82 */           for (int j = 0; j < aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList()
/*  83 */             .getUserLimitDetails().size(); j++) {
/*  84 */             if (((LimitEnquiryResponseLimitDetails)aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList()
/*  85 */               .getUserLimitDetails().get(j)).getReferenceId() != null) {
/*  86 */               if (((LimitEnquiryResponseLimitDetails)aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList()
/*  87 */                 .getUserLimitDetails().get(j)).getReferenceId().equalsIgnoreCase(refnum)) {
/*  88 */                 serial_num = ((LimitEnquiryResponseLimitDetails)aLimitEnquiryRespWithObject.getData().getUserMaintainedLimitInquiryList()
/*  89 */                   .getUserLimitDetails().get(j)).getKeySrNo();
/*     */               }
/*     */             }
/*     */           }  
/*     */       } else {
/*  94 */         aLimitEnquiryRespWithObjectA = (LimitEnquiryRespWithObjectA)aGsonLimResp.fromJson(bankPlainRes, LimitEnquiryRespWithObjectA.class);
/*  95 */         if (aLimitEnquiryRespWithObjectA != null && aLimitEnquiryRespWithObjectA.getData() != null && 
/*  96 */           aLimitEnquiryRespWithObjectA.getData().getUserMaintainedLimitInquiryList() != null && 
/*  97 */           aLimitEnquiryRespWithObjectA.getData().getUserMaintainedLimitInquiryList()
/*  98 */           .getUserLimitDetails() != null) {
/*  99 */           if (aLimitEnquiryRespWithObjectA.getData().getUserMaintainedLimitInquiryList().getUserLimitDetails()
/* 100 */             .getReferenceId() != null) {
/* 101 */             if (aLimitEnquiryRespWithObjectA.getData().getUserMaintainedLimitInquiryList()
/* 102 */               .getUserLimitDetails().getReferenceId().equalsIgnoreCase(refnum)) {
/* 103 */               serial_num = aLimitEnquiryRespWithObjectA.getData().getUserMaintainedLimitInquiryList()
/* 104 */                 .getUserLimitDetails().getKeySrNo();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 111 */     catch (Exception e) {
/* 112 */       e.printStackTrace();
/* 113 */       System.out.println("exception in converting response to json in : getRespSerialNum" + e.getMessage());
/*     */     } 
/*     */     
/* 116 */     System.out.println("serial_num value in : " + serial_num);
/* 117 */     return serial_num;
/*     */   }
/*     */   
/*     */   public String generateLimitEnquiryReq(String requestType, String custId, String msgId, String limitSuffix) {
/* 121 */     this.aLimitEnquiryRequestData.setCustId(custId);
/* 122 */     this.aLimitEnquiryRequestData.setSUFFIX(limitSuffix);
/* 123 */     this.aLimitEnquiryRequestHeader.setRequestType(requestType);
/* 124 */     this.aLimitEnquiryRequestHeader.setMsgid(msgId);
/* 125 */     this.aLimitEnquiryRequestHeader.setData(this.aLimitEnquiryRequestData);
/* 126 */     String bankReqJson = this.aGson.toJson(this.aLimitEnquiryRequestHeader);
/* 127 */     System.out.println("generateLimitEnquiryReq bankReqJson : " + bankReqJson);
/* 128 */     return bankReqJson;
/*     */   }
/*     */   
/*     */   public String encryptReqJson(String reqJsonStr, String key) {
/* 132 */     String encMes = null;
/* 133 */     EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*     */     try {
/* 135 */       encMes = obj.encryptMessage(reqJsonStr, key);
/* 136 */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/*     */     } 
/* 139 */     return encMes;
/*     */   }
/*     */   
/*     */   public String decryptResJson(String encJsonResp, String key) {
/* 143 */     String plainResJson = null;
/* 144 */     EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*     */     try {
/* 146 */       plainResJson = obj.decryptMessage(encJsonResp, key);
/* 147 */     } catch (Exception e) {
/* 148 */       e.printStackTrace();
/*     */     } 
/* 150 */     return plainResJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public String callBankEndPoint(String bankEncReq, String url) {
/* 155 */     String encResponse = null;
/* 156 */     PostMethod post = new PostMethod(url);
/*     */     
/*     */     try {
/* 159 */       StringRequestEntity requestEntity = new StringRequestEntity(bankEncReq, "application/json", "utf-8");
/* 160 */       post.setRequestEntity((RequestEntity)requestEntity);
/* 161 */       HttpClient httpclient = new HttpClient();
/*     */       
/* 163 */       int result = httpclient.executeMethod((HttpMethod)post);
/*     */       
/* 165 */       if (result != 200) {
	              System.out.println("Limit Enquiry API returned non 200 status: " + result);
/* 166 */         throw new Exception("Server returned code " + result);
/*     */       }
/*     */       
/* 169 */       encResponse = post.getResponseBodyAsString();
/* 170 */     } catch (Exception e) {
	            System.out.println("Call Bank End Point (Limit Enquiry) failed for URL: "+ url + ": " + e);
/* 171 */       e.printStackTrace();
/*     */     } finally {
/* 173 */       post.releaseConnection();
/*     */     } 
/* 175 */     return (encResponse != null) ? encResponse.trim() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateEncReq(String encReqJson, String msgid) {
/* 180 */     LimitEnquiryReqEnc aLimitEnquiryReqEnc = new LimitEnquiryReqEnc();
/* 181 */     aLimitEnquiryReqEnc.setReqdata(encReqJson);
/* 182 */     aLimitEnquiryReqEnc.setMsgid(msgid);
/* 183 */     Gson aGson = new Gson();
/* 184 */     return aGson.toJson(aLimitEnquiryReqEnc);
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\LimitEnquiryAdaptee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */