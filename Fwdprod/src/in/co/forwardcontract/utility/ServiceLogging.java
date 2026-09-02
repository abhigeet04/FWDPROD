/*     */ package in.co.forwardcontract.utility;
/*     */ 
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceLogging
/*     */ {
/*  16 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.utility.ServiceLogging.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void pushServiceLogData(String service, String operation, String zone, String source, String destination, String reference, String category, String status, String bankRequestJson, String bankResponseJson, Timestamp bankRequestTime, Timestamp bankResponseTime) {
/*  23 */     logger.info("Process entered into push Service Log Data process...!");
/*     */     
/*  25 */     String query = "INSERT INTO CUSTOM_FWC_SERVICE_LOG(SERVICE,OPERATION,ZONE, SOURCESYSTEM, TARGETSYSTEM,  REFERENCE, CATEGORY, STATUS,BANKREQUEST, BANKRESPONSE,BANKREQTIME,BANKRESTIME)  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
/*     */ 
/*     */     
/*  28 */     Connection zoneConnection = null;
/*  29 */     PreparedStatement aPreparedStatement = null;
/*     */ 
/*     */     
/*     */     try {
/*  33 */       zoneConnection = DBConnectionUtility.getZoneConnection();
/*  34 */       aPreparedStatement = zoneConnection.prepareStatement(query);
/*  35 */       aPreparedStatement.setString(1, service);
/*  36 */       aPreparedStatement.setString(2, operation);
/*  37 */       aPreparedStatement.setString(3, zone);
/*  38 */       aPreparedStatement.setString(4, source);
/*  39 */       aPreparedStatement.setString(5, destination);
/*  40 */       aPreparedStatement.setString(6, reference);
/*  41 */       aPreparedStatement.setString(7, category);
/*  42 */       aPreparedStatement.setString(8, status);
/*  43 */       aPreparedStatement.setString(9, bankRequestJson);
/*  44 */       aPreparedStatement.setString(10, bankResponseJson);
/*  45 */       aPreparedStatement.setTimestamp(11, bankRequestTime);
/*  46 */       aPreparedStatement.setTimestamp(12, bankResponseTime);
/*     */       
/*  48 */       aPreparedStatement.executeUpdate();
/*     */       
/*  50 */       logger.info(
/*  51 */           "pushServiceLogData is added successfully with count: " + aPreparedStatement.getUpdateCount());
/*  52 */     } catch (SQLException e) {
/*  53 */       e.printStackTrace();
/*  54 */     } catch (Exception e) {
/*  55 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  58 */         aPreparedStatement.close();
/*  59 */         zoneConnection.close();
/*  60 */       } catch (SQLException e) {
/*  61 */         e.printStackTrace();
/*     */       } 
/*     */     } 
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
/*     */   public static boolean insertLogData(String service, String operation, String zone, String branch, String sourceSys, String targetSys, String masterRef, String eventRef, String status, Date valueDate, String tiRequest, String tiResponse, String bankRequest, String bankResponse, Timestamp tiReqTime, Timestamp bankReqTime, Timestamp bankResTime, Timestamp tiResTime, String transactionkey1, String statickey1, String narrative1, String narrative2, boolean isReSubmitted, String reSubmittedCount, String description) {
/*  75 */     boolean result = true;
/*  76 */     Connection con = null;
/*  77 */     PreparedStatement ps = null;
/*     */     
/*  79 */     String query = "INSERT INTO SERVICELOG (ID,SERVICE,OPERATION,ZONE,BRANCH,SOURCESYSTEM,TARGETSYSTEM,MASTERREFERENCE,EVENTREFERENCE,STATUS,PROCESSTIME,TIREQUEST,TIRESPONSE,BANKREQUEST,BANKRESPONSE,TIREQTIME,BANKREQTIME,BANKRESTIME,TIRESTIME,TRANSACTIONKEY1,STATICKEY1,NARRATIVE1,NARRATIVE2,ISRESUBMITTED,RESUBMITTEDCOUNT,RESUBMITTEDTIME,DESCRIPTION,TYPEFLAG,NODE,VALUEDATE) VALUES (SERVICELOG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  84 */       con = DBConnectionUtility.getZoneConnection();
/*  85 */       ps = con.prepareStatement(query);
/*     */       
/*  87 */       ps.setString(1, service);
/*  88 */       ps.setString(2, operation);
/*  89 */       ps.setString(3, zone);
/*  90 */       ps.setString(4, branch);
/*  91 */       ps.setString(5, sourceSys);
/*  92 */       ps.setString(6, targetSys);
/*  93 */       ps.setString(7, masterRef);
/*  94 */       ps.setString(8, eventRef);
/*  95 */       ps.setString(9, status);
/*  96 */       ps.setDate(10, (Date)null);
/*  97 */       ps.setString(11, tiRequest);
/*  98 */       ps.setString(12, tiResponse);
/*  99 */       ps.setString(13, bankRequest);
/* 100 */       ps.setString(14, bankResponse);
/* 101 */       ps.setTimestamp(15, tiReqTime);
/* 102 */       ps.setTimestamp(16, bankReqTime);
/* 103 */       ps.setTimestamp(17, bankResTime);
/* 104 */       ps.setTimestamp(18, tiResTime);
/* 105 */       ps.setString(19, transactionkey1);
/* 106 */       ps.setString(20, statickey1);
/* 107 */       ps.setString(21, narrative1);
/* 108 */       ps.setString(22, narrative2);
/* 109 */       ps.setBoolean(23, isReSubmitted);
/* 110 */       ps.setInt(24, 0);
/* 111 */       ps.setTimestamp(25, (Timestamp)null);
/* 112 */       ps.setString(26, description);
/* 113 */       ps.setString(27, "");
/* 114 */       ps.setString(28, "");
/* 115 */       ps.setDate(29, valueDate);
/* 116 */       ps.executeUpdate();
/* 117 */       result = true;
/*     */     }
/* 119 */     catch (Exception e) {
/* 120 */       e.printStackTrace();
/* 121 */       result = false;
/*     */     } finally {
/*     */       
/* 124 */       DBConnectionUtility.surrenderDB(con, ps, null);
/*     */     } 
/* 126 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\ServiceLogging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */