/*     */ package in.co.forwardcontract.service.utility;
/*     */ 
/*     */ import com.infrasoft.kiya.security.EncryptionDecryptionImpl;
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.methods.RequestEntity;
/*     */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ServiceUtility
/*     */ {
/*  22 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.utility.ServiceUtility.class);
/*     */   
/*  24 */   static EncryptionDecryptionImpl obj = new EncryptionDecryptionImpl();
/*  25 */   public static LinkedHashMap<String, String> TBProperties = new LinkedHashMap<>();
/*     */   
/*     */   String reqdata;
/*     */   String msgid;
/*     */   
/*     */   public ServiceUtility(String reqData, String msgId) {
/*  31 */     this.msgid = msgId;
/*  32 */     this.reqdata = reqData;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getBankFinResponse(String bankEncReq, String url) {
/*  37 */     String encResponse = null;
/*     */     
/*  39 */     PostMethod post = new PostMethod(url);
/*  40 */     logger.info("Entering getBankFinResponse");
/*     */     
/*     */     try {
/*  43 */       StringRequestEntity requestEntity = new StringRequestEntity(bankEncReq, "application/json", "utf-8");
/*  44 */       post.setRequestEntity((RequestEntity)requestEntity);
/*  45 */       HttpClient httpclient = new HttpClient();
/*     */       
/*  47 */       int result = httpclient.executeMethod((HttpMethod)post);
/*     */       
/*  49 */       if (result != 200) {
/*  50 */         throw new Exception("Server returned code " + result);
/*     */       }
/*  52 */       encResponse = post.getResponseBodyAsString();
/*  53 */       logger.info("Encrypted Response From Bank-->\n" + encResponse);
/*     */       
/*  55 */       logger.info("Exiting getBankFinResponse");
/*  56 */     } catch (Exception e) {
/*  57 */       logger.info("Exception in getBankFinResponse:- " + e);
/*  58 */       e.printStackTrace();
/*     */     } finally {
/*  60 */       post.releaseConnection();
/*     */     } 
/*  62 */     return encResponse.trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void getProperties() {
/*  68 */     Connection con = null;
/*  69 */     PreparedStatement pst = null;
/*  70 */     ResultSet rs = null;
/*  71 */     logger.info(" Entering getProperties ");
/*     */     try {
/*  73 */       con = DBConnectionUtility.getubiconnectConnection();
/*  74 */       String query = "SELECT * FROM Bridgeproperties ";
/*  75 */       pst = con.prepareStatement(query);
/*  76 */       rs = pst.executeQuery();
/*  77 */       while (rs.next()) {
/*  78 */         TBProperties.put(rs.getString("key").trim(), rs.getString("value").trim());
/*     */       }
/*  80 */       logger.info(" Size of Bridgeproperties From DB ---->" + TBProperties.size());
/*  81 */       logger.info(" Entering getProperties ");
/*  82 */     } catch (Exception e) {
/*  83 */       e.printStackTrace();
/*     */     } finally {
/*  85 */       DBConnectionUtility.surrenderDB(con, pst, rs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateEncryptBankRequest(String bankRequestJson, String key) {
/*  92 */     String encMes = null;
/*  93 */     logger.info(" Entering generateEncryptBankRequest ");
/*     */     
/*     */     try {
/*  96 */       encMes = obj.encryptMessage(bankRequestJson, key);
/*  97 */       logger.info(" Exiting generateEncryptBankRequest ");
/*  98 */     } catch (Exception e) {
/*  99 */       logger.info(" Error in  generateEncryptBankRequest --->" + e.getMessage());
/* 100 */       e.printStackTrace();
/*     */     } 
/* 102 */     return encMes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateDecryptBankResponse(String bankEncRes, String key) {
/* 110 */     String decMes = null;
/* 111 */     logger.info(" Entering generateDecryptBankResponse ");
/*     */     
/*     */     try {
/* 114 */       decMes = obj.decryptMessage(bankEncRes, key);
/* 115 */       logger.info(" Exiting generateDecryptBankResponse ");
/* 116 */     } catch (Exception e) {
/* 117 */       logger.info(" Error in  generateDecryptBankResponse --->" + e.getMessage());
/* 118 */       e.printStackTrace();
/*     */     } 
/* 120 */     return decMes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Timestamp getSqlLocalDateTime() {
/* 125 */     Date date = new Date();
/* 126 */     long t = date.getTime();
/* 127 */     Timestamp sqlTimestamp = new Timestamp(t);
/* 128 */     return sqlTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getBridgePropertyValue(String key) {
/* 133 */     logger.info("FWC Getting File Path Method ");
/* 134 */     String value = "";
/* 135 */     Connection con = null;
/* 136 */     ResultSet rs = null;
/* 137 */     PreparedStatement ps = null;
/*     */     try {
/* 139 */       con = DBConnectionUtility.getubiconnectConnection();
/* 140 */       if (con != null) {
/* 141 */         String bridgePropQuery = "SELECT ID, ZONE, BRANCH, KEY, VALUE, CATEGORY FROM BRIDGEPROPERTIES WHERE KEY = ? ";
/* 142 */         logger.info("FWC BridgePropQuery : " + bridgePropQuery + " Params[" + key + "]");
/*     */         
/* 144 */         ps = con.prepareStatement(bridgePropQuery);
/* 145 */         ps.setString(1, key);
/* 146 */         rs = ps.executeQuery();
/* 147 */         while (rs.next()) {
/* 148 */           value = rs.getString("VALUE");
/*     */         }
/* 150 */         logger.info(" FWC-----------File Location------------JOB Name : VALUE ---->>> " + key + 
/* 151 */             " Location File Saved---------: " + value);
/*     */       }
/*     */     
/* 154 */     } catch (Exception ex) {
/* 155 */       logger.info(
/* 156 */           " FWC-----------File Location----- getBridgePropertyValue--- Exception is :" + ex.getMessage());
/* 157 */       ex.printStackTrace();
/*     */     } finally {
/*     */       
/* 160 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*     */     } 
/*     */     
/* 163 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\servic\\utility\ServiceUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */