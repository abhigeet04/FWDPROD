/*     */ package in.co.forwardcontract.service.utility;
/*     */ import com.google.gson.Gson;
/*     */ import com.infrasoft.kiya.security.EncryptionDecryptionImpl;
/*     */ import in.co.forwardcontract.service.model.DateTimeUtil;
/*     */ import in.co.forwardcontract.service.model.LimitBlockReq;
/*     */ import in.co.forwardcontract.service.model.LimitBlockReqEnc;
/*     */ import in.co.forwardcontract.service.model.LimitBlockResp;
/*     */ import in.co.forwardcontract.service.model.LimitBlockRespWithObject;
/*     */ import in.co.forwardcontract.service.model.ReqKey;
/*     */ import in.co.forwardcontract.service.model.ReqModifyLimitNodeInputVO;
/*     */ import in.co.forwardcontract.service.model.ReqUmlLiabValue;
/*     */ import in.co.forwardcontract.service.model.ReqUserMaintLiabModLL;
/*     */ import in.co.forwardcontract.service.model.ResUserMaintLiabModLL;
/*     */ import in.co.forwardcontract.service.utility.ServiceUtility;
/*     */ import in.co.forwardcontract.utility.CommonMethods;
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import in.co.forwardcontract.utility.LimitEnquiryAdaptee;
/*     */ import in.co.forwardcontract.utility.LoggableStatement;
/*     */ import in.co.forwardcontract.utility.ServiceLogging;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.RequestEntity;
/*     */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LimitBlockUnblockUtil {
/*  41 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.LimitBlockUnblockUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> limitexposurethroughAPI(String fwdContractNo, String limitID, String limitAmount, String limitCcy, String BlockorUnblockstatus, String category) {
/*  51 */     Map<String, String> responseTokens = new HashMap<>();
/*  52 */     Timestamp bankRequestTime = null;
/*  53 */     Timestamp bankResponseTime = null;
/*  54 */     String plainReqJson = "";
/*  55 */     String bankPlainRes = "";
/*  56 */     String umlRefId = "";
/*  57 */     String umlRemarks = "";
/*  58 */     String status = "FAILED";
/*     */     
/*     */     try {
/*  61 */       int prefixEnd = limitID.indexOf("/");
/*  62 */       String limitPrefix = limitID.substring(0, prefixEnd);
/*  63 */       String limitSuffix = limitID.substring(prefixEnd + 1);
/*     */       
/*  65 */       String sequence = ServiceUtility.getSqlLocalDateTime().toString();
/*  66 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/*  68 */       logger.info("Fwd Contract No : " + fwdContractNo);
/*  69 */       String serialNum = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       umlRefId = fwdContractNo;
/*  76 */       Date tiSysdate = (Date) DateTimeUtil.getTISystemSqlDate();
/*     */       
/*  78 */       SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */ 
/*     */       
/*  82 */       String umlDate = String.valueOf(simpDate.format(tiSysdate)) + "T" + DateTimeUtil.getH24Time();
/*  83 */       String umlEndDate = "2099-12-31T" + DateTimeUtil.getH24Time();
/*  84 */       String umlDept = "001";
/*  85 */       logger.info("umlDept:" + umlDept);
/*     */       
/*  87 */       BigDecimal amtBD = new BigDecimal(limitAmount);
/*  88 */       if (limitAmount != null && (amtBD.compareTo(new BigDecimal(0)) == 0 || amtBD.compareTo(new BigDecimal(0)) == -1)) {
/*  89 */         limitAmount = "0.001";
/*     */       }
/*     */ 
/*     */       
/*  93 */       logger.info("limitAmount" + limitAmount);
/*  94 */       logger.info("Limit Currency:" + limitCcy);
/*  95 */       LimitEnquiryAdaptee aLimitEnquiryAdaptee = new LimitEnquiryAdaptee();
/*  96 */       serialNum = aLimitEnquiryAdaptee.enquireLimitSerialNum(limitPrefix, umlRefId, limitSuffix, "");
/*  97 */       logger.info("serial_num value in Limit Enquiry API: " + serialNum);
/*  98 */       if (serialNum == null || serialNum.isEmpty()) {
/*  99 */         serialNum = "009999";
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       logger.info("Serial Number:" + serialNum);
/* 107 */       String umlReasonCode = "ADHBG";
/* 108 */       umlRemarks = fwdContractNo;
/* 109 */       String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
/* 110 */       msgId = msgId.replaceAll("[- :.]", "");
/*     */       
/* 112 */       logger.info("msgId:" + msgId);
/* 113 */       plainReqJson = generateLimitBlockReqJson(limitPrefix, limitSuffix, serialNum, umlRefId, umlDate, 
/* 114 */           umlEndDate, umlDept, limitAmount, limitCcy, umlReasonCode, umlRemarks, msgId);
/*     */       
/* 116 */       logger.info("Plain Request values:");
/*     */       
/* 118 */       logger.info("Limit Block Plain Request : " + plainReqJson);
/*     */       
/* 120 */       ServiceUtility.getProperties();
/* 121 */       String key = (String)ServiceUtility.TBProperties.get("LIMIT_BLOCK_NEW_KEY");
/* 122 */       String url = (String)ServiceUtility.TBProperties.get("LIMIT_BLOCK_NEW_URL");
/* 123 */       String encReqJson = encryptReqJson(plainReqJson, key);
/*     */       
/* 125 */       logger.info("Limit Block URL&Key : " + url + " & " + key);
/* 126 */       logger.info("Limit Block Encrypted Request : " + encReqJson);
/* 127 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*     */ 
/*     */       
/* 130 */       String encRequest = generateEncReq(encReqJson, msgId);
/* 131 */       logger.info("encRequest:" + encRequest);
/* 132 */       String bankEncRes = callBankEndPoint(encRequest, url);
/*     */       
/* 134 */       logger.info("Limit Block Encrypted Bank Response : " + bankEncRes);
/* 135 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/* 136 */       bankPlainRes = decryptResJson(bankEncRes, key);
/*     */ 
/*     */       
/* 139 */       logger.info("Limit Block Plain Bank Response : " + bankPlainRes);
/* 140 */       String serialnum = null;
/* 141 */       if (bankPlainRes != null) {
/* 142 */         serialnum = getRespSerialNum(bankPlainRes, umlRemarks);
/*     */       }
/* 144 */       logger.info("Limit Block serialnum after getRespSerialNum : " + serialnum);
/* 145 */       if (serialnum != null)
/*     */       {
/* 147 */         insertLimitDetails(umlRefId, "", serialnum, String.valueOf(limitPrefix) + "/" + limitSuffix, limitAmount, limitCcy);
/*     */       }
/* 149 */       logger.info("Limit Block serialnum after insertLimitDetails : " + serialnum);
/* 150 */       if (CommonMethods.isValidString(serialnum)) {
/* 151 */         responseTokens.put("SerialNumber", serialnum);
/* 152 */         responseTokens.put("LimitBOUStatus", "S");
/*     */       }
/*     */       else {
/*     */         
/* 156 */         responseTokens.put("LimitBOUStatus", "F");
/*     */       } 
/* 158 */       if (((String)responseTokens.get("LimitBOUStatus")).contains("S"))
/* 159 */       { status = "SUCCEEDED"; }
/* 160 */       else { status = "FAILED"; }
/*     */       
/* 162 */       logger.info("status:" + status);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 167 */     catch (Exception e) {
/*     */       
/* 169 */       e.printStackTrace();
/*     */     } finally {
/* 171 */       ServiceLogging.pushServiceLogData("Limit", BlockorUnblockstatus, "ZONE1", "FTI", "Finacle", umlRefId, "", status, 
/* 172 */           plainReqJson, bankPlainRes, bankRequestTime, bankResponseTime);
/*     */       
/* 174 */       insertLogData("LIMIT", "EXPOSURE", "", "", "", "", umlRemarks, "", status, null, 
/* 175 */           "", "", plainReqJson, bankPlainRes, null, null, DateTimeUtil.getSqlLocalDateTime(), DateTimeUtil.getSqlLocalDateTime(), "", "", "", "", false, "0", "");
/*     */     } 
/*     */     
/* 178 */     return responseTokens;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void limitreversethroughAPI(String fwdContractNo, String limitID, String limitAmount, String limitCcy, String blockorUnblockstatus, String serialnum) {
/* 184 */     Timestamp bankRequestTime = null;
/* 185 */     Timestamp bankResponseTime = null;
/* 186 */     String status = "FAILED";
/*     */     try {
/* 188 */       int prefixEnd = limitID.indexOf("/");
/* 189 */       String limitPrefix = limitID.substring(0, prefixEnd);
/* 190 */       String limitSuffix = limitID.substring(prefixEnd + 1);
/*     */       
/* 192 */       String sequence = ServiceUtility.getSqlLocalDateTime().toString();
/* 193 */       sequence = sequence.replaceAll("[- :.]", "");
/*     */       
/* 195 */       logger.info("Fwd Contract No : " + fwdContractNo);
/*     */       
/* 197 */       String umlRefId = fwdContractNo;
/* 198 */       Date tiSysdate = (Date) DateTimeUtil.getTISystemSqlDate();
/*     */       
/* 200 */       SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd");
/* 201 */       String umlDate = String.valueOf(simpDate.format(tiSysdate)) + "T" + DateTimeUtil.getH24Time();
/* 202 */       String umlEndDate = "2099-12-31T" + DateTimeUtil.getH24Time();
/* 203 */       String umlDept = "001";
/* 204 */       logger.info("umlDept:" + umlDept);
/*     */       
/* 206 */       BigDecimal amtBD = new BigDecimal(limitAmount);
/* 207 */       if (limitAmount != null && amtBD.compareTo(new BigDecimal(0)) == 0) {
/* 208 */         limitAmount = "0.001";
/*     */       }
/*     */       
/* 211 */       String umlReasonCode = "ADHBG";
/* 212 */       String umlRemarks = fwdContractNo;
/* 213 */       String msgId = DateTimeUtil.getSqlLocalDateTime().toString();
/* 214 */       msgId = msgId.replaceAll("[- :.]", "");
/*     */       
/* 216 */       logger.info("msgId:" + msgId);
/* 217 */       String plainReqJson = generateLimitBlockReqJson(limitPrefix, limitSuffix, serialnum, umlRefId, umlDate, 
/* 218 */           umlEndDate, umlDept, limitAmount, limitCcy, umlReasonCode, umlRemarks, msgId);
/*     */ 
/*     */       
/* 221 */       logger.info("Plain Request values:");
/*     */       
/* 223 */       logger.info("Limit Block Plain Request : " + plainReqJson);
/*     */       
/* 225 */       ServiceUtility.getProperties();
/* 226 */       String key = (String)ServiceUtility.TBProperties.get("LIMIT_BLOCK_NEW_KEY");
/* 227 */       String url = (String)ServiceUtility.TBProperties.get("LIMIT_BLOCK_NEW_URL");
/* 228 */       String encReqJson = encryptReqJson(plainReqJson, key);
/*     */       
/* 230 */       logger.info("Limit Block URL&Key : " + url + " & " + key);
/* 231 */       logger.info("Limit Block Encrypted Request : " + encReqJson);
/* 232 */       bankRequestTime = CommonMethods.getSqlLocalDateTime();
/*     */ 
/*     */       
/* 235 */       String encRequest = generateEncReq(encReqJson, msgId);
/* 236 */       logger.info("encRequest:" + encRequest);
/* 237 */       String bankEncRes = callBankEndPoint(encRequest, url);
/*     */       
/* 239 */       logger.info("Limit Block Encrypted Bank Response : " + bankEncRes);
/* 240 */       bankResponseTime = CommonMethods.getSqlLocalDateTime();
/* 241 */       String bankPlainRes = decryptResJson(bankEncRes, key);
/*     */ 
/*     */       
/* 244 */       logger.info("Limit Block Plain Bank Response : " + bankPlainRes);
/* 245 */       if (bankPlainRes != null) {
/* 246 */         if (!bankPlainRes.contains("ErrorDetailList") && !bankPlainRes.contains("\"successorfailure\":\"N\"")) {
/* 247 */           status = "SUCCEEDED";
/*     */         }
/*     */         else {
/*     */           
/* 251 */           status = "FAILED";
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 256 */         status = "FAILED";
/*     */       } 
/* 258 */       ServiceLogging.pushServiceLogData("Limit", blockorUnblockstatus, "ZONE1", "FTI", "Finacle", umlRefId, "", status, 
/* 259 */           plainReqJson, bankPlainRes, bankRequestTime, bankResponseTime);
/* 260 */       logger.info("Limit has been reversed successfully for fwdContractNo : " + fwdContractNo);
/*     */     }
/* 262 */     catch (Exception e) {
/*     */       
/* 264 */       e.printStackTrace();
/* 265 */       logger.info("Exception in Limit reversal for fwdContractNo : " + fwdContractNo + " error : " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateEncReq(String encReqJson, String msgid) {
/* 271 */     logger.info("Generating Encryption Request");
/* 272 */     LimitBlockReqEnc aLimitBlockReqEnc = new LimitBlockReqEnc();
/* 273 */     aLimitBlockReqEnc.setReqdata(encReqJson);
/* 274 */     aLimitBlockReqEnc.setMsgid(msgid);
/* 275 */     Gson aGson = new Gson();
/*     */     
/* 277 */     logger.info(String.valueOf(aLimitBlockReqEnc.getReqdata()) + " " + aLimitBlockReqEnc.getMsgid());
/* 278 */     return aGson.toJson(aLimitBlockReqEnc);
/*     */   }
/*     */   public static String encryptReqJson(String reqJsonStr, String key) {
/* 281 */     EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/* 282 */     String encMes = null;
/*     */     try {
/* 284 */       logger.info("Encrypt Json:");
/* 285 */       encMes = obj.encryptMessage(reqJsonStr, key);
/*     */       
/* 287 */       logger.info("Encryption Message:" + encMes);
/* 288 */     } catch (Exception e) {
/* 289 */       e.printStackTrace();
/*     */     } 
/* 291 */     return encMes;
/*     */   }
/*     */   
/*     */   public static String callBankEndPoint(String bankEncReq, String url) {
/* 295 */     String encResponse = null;
/* 296 */     PostMethod post = new PostMethod(url);
/*     */     try {
/* 298 */       logger.info("Call Bank End Point");
/* 299 */       StringRequestEntity requestEntity = new StringRequestEntity(bankEncReq, "application/json", "utf-8");
/* 300 */       post.setRequestEntity((RequestEntity)requestEntity);
/* 301 */       HttpClient httpclient = new HttpClient();
/* 302 */       int result = httpclient.executeMethod((HttpMethod)post);
/* 303 */       if (result != 200) {
/* 304 */         throw new Exception("Server returned code " + result);
/*     */       }
/* 306 */       encResponse = post.getResponseBodyAsString();
/*     */       
/* 308 */       logger.info("Encryption Response:" + encResponse);
/* 309 */     } catch (Exception e) {
/* 310 */       e.printStackTrace();
/*     */     } finally {
/* 312 */       post.releaseConnection();
/*     */     } 
/* 314 */     return encResponse.trim();
/*     */   }
/*     */   public static String decryptResJson(String encJsonResp, String key) {
/* 317 */     EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/* 318 */     String plainResJson = null;
/*     */     
/*     */     try {
/* 321 */       logger.info("Decrypt Res Json");
/* 322 */       plainResJson = obj.decryptMessage(encJsonResp, key);
/* 323 */       logger.info("plainResJson" + plainResJson);
/* 324 */     } catch (Exception e) {
/* 325 */       e.printStackTrace();
/*     */     } 
/* 327 */     return plainResJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateLimitBlockReqJson(String limitPrefix, String limitSuffix, String serialNum, String umlRefId, String umlDate, String umlEndDate, String umlDept, String amountValue, String ccyCode, String umlReasonCode, String umlRemarks, String msgId) {
/* 332 */     LimitBlockReq lim = new LimitBlockReq();
/* 333 */     ReqModifyLimitNodeInputVO aModifyLimitNodeInputVO = new ReqModifyLimitNodeInputVO();
/* 334 */     List<ReqUserMaintLiabModLL> usermainlist = new LinkedList<>();
/* 335 */     ReqUserMaintLiabModLL usermain = new ReqUserMaintLiabModLL();
/*     */     
/* 337 */     logger.info("GenerateLimit Request:");
/*     */ 
/*     */     
/* 340 */     ReqKey aReqKey = new ReqKey();
/* 341 */     ReqUmlLiabValue mlval = new ReqUmlLiabValue();
/*     */     
/* 343 */     aModifyLimitNodeInputVO.setlimitPrefix(limitPrefix);
/* 344 */     aModifyLimitNodeInputVO.setlimitSuffix(limitSuffix);
/*     */     
/* 346 */     aReqKey.setserial_num(serialNum);
/*     */     
/* 348 */     usermain.setUmlReferenceId(umlRefId);
/* 349 */     usermain.setUmlDate(umlDate);
/* 350 */     usermain.setUmlEndDate(umlEndDate);
/* 351 */     usermain.setUmlDept(umlDept);
/*     */     
/* 353 */     mlval.setAmountValue(amountValue);
/* 354 */     mlval.setCurrencyCode(ccyCode);
/*     */     
/* 356 */     usermain.setUmlReasonCode(umlReasonCode);
/* 357 */     usermain.setUmlRemarks(umlRemarks);
/*     */     
/* 359 */     usermain.setKey(aReqKey);
/* 360 */     usermain.setUmlLiabValue(mlval);
/* 361 */     usermainlist.add(usermain);
/*     */     
/* 363 */     aModifyLimitNodeInputVO.setUserMaintLiabModLL(usermainlist);
/*     */     
/* 365 */     lim.setModifyLimitNodeInputVO(aModifyLimitNodeInputVO);
/* 366 */     lim.setmsgid(msgId);
/*     */ 
/*     */ 
/*     */     
/* 370 */     Gson gson = new Gson();
/*     */     
/* 372 */     String jsonString = gson.toJson(lim);
/*     */     
/* 374 */     logger.info("Json String:" + jsonString);
/*     */     
/* 376 */     return jsonString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertLimitDetails(String masterRef, String eventRef, String serialNo, String limitId, String limitAmt, String limitCcy) {
/* 383 */     boolean result = true;
/* 384 */     int rs = 0;
/* 385 */     Connection con = null;
/* 386 */     PreparedStatement ps = null;
/*     */     
/* 388 */     String query = "INSERT INTO LIMITDETAILS(SNO,MASTERREF,EVENTREF,SERIALNO,LIMITID,LIMITAMT,LIMITCCY,PROCESSDATETIME) VALUES (LIMITDETAILS_SEQ.NEXTVAL,?,?,?,?,?,?,SYSDATE)";
/* 389 */     logger.info("insertLimitDetails : " + query);
/*     */     
/*     */     try {
/* 392 */       con = DBConnectionUtility.getubiconnectConnection();
/* 393 */       ps = con.prepareStatement(query);
/* 394 */       ps.setString(1, masterRef);
/* 395 */       ps.setString(2, eventRef);
/* 396 */       ps.setString(3, serialNo);
/* 397 */       ps.setString(4, limitId);
/* 398 */       ps.setString(5, limitAmt);
/* 399 */       ps.setString(6, limitCcy);
/* 400 */       rs = ps.executeUpdate();
/* 401 */       logger.info("rs :: " + rs);
/* 402 */       result = true;
/*     */ 
/*     */     
/*     */     }
/* 406 */     catch (SQLException e) {
/* 407 */       logger.error("SQL Exceptions! insertLimitDetails " + e.getMessage(), e);
/* 408 */       e.printStackTrace();
/* 409 */       return result = false;
/*     */     }
/* 411 */     catch (Exception e) {
/* 412 */       logger.error("Exception! insertLimitDetails " + e.getMessage(), e);
/* 413 */       e.printStackTrace();
/* 414 */       return result = false;
/*     */     } finally {
/*     */       
/* 417 */       DBConnectionUtility.surrenderDB(con, ps, null);
/*     */     } 
/*     */     
/* 420 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRespSerialNum(String decRespJson, String umlRemarks) {
/* 452 */     String serialNumber = null;
/* 453 */     if (umlRemarks == null || umlRemarks.isEmpty()) {
/* 454 */       return serialNumber;
/*     */     }
/*     */     
/* 457 */     Gson aGsonLimResp = new Gson();
/* 458 */     LimitBlockResp aLimitBlockResp = null;
/* 459 */     LimitBlockRespWithObject aLimitBlockRespWithObject = null;
/*     */     
/*     */     try {
/* 462 */       aLimitBlockResp = (LimitBlockResp)aGsonLimResp.fromJson(decRespJson, LimitBlockResp.class);
/*     */     
/*     */     }
/* 465 */     catch (Exception e) {
/* 466 */       logger.info("inside catch block of getRespSerialNum()");
/* 467 */       aLimitBlockRespWithObject = (LimitBlockRespWithObject)aGsonLimResp.fromJson(decRespJson, LimitBlockRespWithObject.class);
/* 468 */       aLimitBlockResp = new LimitBlockResp();
/* 469 */       List<ResUserMaintLiabModLL> userMaintLiabModLLList = new ArrayList<>();
/* 470 */       if (aLimitBlockRespWithObject != null && aLimitBlockRespWithObject.getuserMaintLiabModLL() != null) {
/* 471 */         userMaintLiabModLLList.add(aLimitBlockRespWithObject.getuserMaintLiabModLL());
/*     */       }
/*     */       
/* 474 */       aLimitBlockResp.setuserMaintLiabModLL(userMaintLiabModLLList);
/*     */     } 
/*     */ 
/*     */     
/* 478 */     if (aLimitBlockResp != null && aLimitBlockResp.getuserMaintLiabModLL() != null) {
/* 479 */       for (int j = 0; j < aLimitBlockResp.getuserMaintLiabModLL().size(); j++) {
/* 480 */         ResUserMaintLiabModLL obj = aLimitBlockResp.getuserMaintLiabModLL().get(j);
/* 481 */         if (obj != null && obj.getumlRemarks() != null && obj.getumlRemarks().equals(umlRemarks) && obj.getkey() != null) {
/* 482 */           serialNumber = obj.getkey().getserial_num();
/* 483 */           logger.info("Serial Number : " + serialNumber);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 489 */     return serialNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getLimitSerialNo(String masterRef, String limitId) {
/* 494 */     String result = null;
/* 495 */     ResultSet rs = null;
/* 496 */     Connection con = null;
/* 497 */     PreparedStatement ps = null;
/*     */     
/* 499 */     String query = "SELECT SERIALNO FROM LIMITDETAILS WHERE MASTERREF = ? AND LIMITID = ? ORDER BY SNO DESC";
/*     */     
/*     */     try {
/* 502 */       con = DBConnectionUtility.getubiconnectConnection();
/* 503 */       ps = con.prepareStatement(query);
/* 504 */       ps.setString(1, masterRef);
/* 505 */       ps.setString(2, limitId);
/* 506 */       rs = ps.executeQuery();
/* 507 */       if (rs.next()) {
/* 508 */         result = rs.getString("SERIALNO");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 513 */       logger.info("getLimitSerialNo query & result --> " + query + " & " + result);
/*     */     }
/* 515 */     catch (SQLException e) {
/* 516 */       e.printStackTrace();
/* 517 */     } catch (Exception e) {
/* 518 */       e.printStackTrace();
/*     */     } finally {
/* 520 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*     */     } 
/*     */     
/* 523 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean insertLogData(String service, String operation, String zone, String branch, String sourceSys, String targetSys, String masterRef, String eventRef, String status, Date valueDate, String tiRequest, String tiResponse, String bankRequest, String bankResponse, Timestamp tiReqTime, Timestamp bankReqTime, Timestamp bankResTime, Timestamp tiResTime, String transactionkey1, String statickey1, String narrative1, String narrative2, boolean isReSubmitted, String reSubmittedCount, String description) {
/* 532 */     boolean result = true;
/* 533 */     Connection con = null;
/* 534 */     PreparedStatement ps = null;
/*     */     
/* 536 */     String query = "INSERT INTO SERVICELOG (ID,SERVICE,OPERATION,ZONE,BRANCH,SOURCESYSTEM,TARGETSYSTEM,MASTERREFERENCE,EVENTREFERENCE,STATUS,PROCESSTIME,TIREQUEST,TIRESPONSE,BANKREQUEST,BANKRESPONSE,TIREQTIME,BANKREQTIME,BANKRESTIME,TIRESTIME,TRANSACTIONKEY1,STATICKEY1,NARRATIVE1,NARRATIVE2,ISRESUBMITTED,RESUBMITTEDCOUNT,RESUBMITTEDTIME,DESCRIPTION,TYPEFLAG,NODE,VALUEDATE) VALUES (SERVICELOG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 541 */       con = DBConnectionUtility.getubiconnectConnection();
/* 542 */       ps = con.prepareStatement(query);
/*     */       
/* 544 */       ps.setString(1, service);
/* 545 */       ps.setString(2, operation);
/* 546 */       ps.setString(3, zone);
/* 547 */       ps.setString(4, branch);
/* 548 */       ps.setString(5, sourceSys);
/* 549 */       ps.setString(6, targetSys);
/* 550 */       ps.setString(7, masterRef);
/* 551 */       ps.setString(8, eventRef);
/* 552 */       ps.setString(9, status);
/* 553 */       ps.setDate(10, null);
/* 554 */       ps.setString(11, tiRequest);
/* 555 */       ps.setString(12, tiResponse);
/* 556 */       ps.setString(13, bankRequest);
/* 557 */       ps.setString(14, bankResponse);
/* 558 */       ps.setTimestamp(15, tiReqTime);
/* 559 */       ps.setTimestamp(16, bankReqTime);
/* 560 */       ps.setTimestamp(17, bankResTime);
/* 561 */       ps.setTimestamp(18, tiResTime);
/* 562 */       ps.setString(19, transactionkey1);
/* 563 */       ps.setString(20, statickey1);
/* 564 */       ps.setString(21, narrative1);
/* 565 */       ps.setString(22, narrative2);
/* 566 */       ps.setBoolean(23, isReSubmitted);
/* 567 */       ps.setInt(24, 0);
/* 568 */       ps.setTimestamp(25, null);
/* 569 */       ps.setString(26, description);
/* 570 */       ps.setString(27, "");
/* 571 */       ps.setString(28, "");
/* 572 */       ps.setDate(29, valueDate);
/* 573 */       ps.executeUpdate();
/* 574 */       result = true;
/*     */     }
/* 576 */     catch (Exception e) {
/* 577 */       e.printStackTrace();
/* 578 */       result = false;
/*     */     }
/*     */     finally {
/*     */       
/* 582 */       DBConnectionUtility.surrenderDB(con, ps, null);
/*     */     } 
/* 584 */     return result;
/*     */   }
/*     */   public static String getLimitNodeForBooking(String forwardContractNo) {
/* 587 */     logger.info("inside getLimitNodeForBooking");
/* 588 */     LoggableStatement pst = null;
/* 589 */     ResultSet rs = null;
/* 590 */     Connection con = null;
/* 591 */     String serial = null;
/*     */     try {
/* 593 */       logger.info("Enter into getLimitNodeForBooking");
/* 594 */       con = DBConnectionUtility.getZoneConnection();
/* 595 */       String query = "SELECT LIMIT_SERIAL_NUM FROM CUSTOM_FWC_DETAILS WHERE FWC_CONTRACT_NO='" + 
/* 596 */         forwardContractNo.trim() + "' AND CATEGORY ='FWCBOOK'";
/* 597 */       pst = new LoggableStatement(con, query);
/* 598 */       rs = pst.executeQuery();
/* 599 */       while (rs.next()) {
/* 600 */         serial = rs.getString("LIMIT_SERIAL_NUM");
/*     */       }
/* 602 */     } catch (Exception e) {
/* 603 */       e.printStackTrace();
/*     */     } finally {
/* 605 */       DBConnectionUtility.surrenderDB(con, (Statement)pst, rs);
/*     */     } 
/* 607 */     return serial;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\LimitBlockUnblockUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */