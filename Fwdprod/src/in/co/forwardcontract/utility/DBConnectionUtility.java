/*     */ package in.co.forwardcontract.utility;
/*     */ 
/*     */ import in.co.forwardcontract.utility.ProbUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Properties;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DBConnectionUtility
/*     */ {
/*  20 */   public static Boolean isJNDIConn = Boolean.valueOf(true);
/*     */ 
/*     */   
/*  23 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.utility.DBConnectionUtility.class);
/*     */   
/*     */   public DBConnectionUtility() {
/*  26 */     logger.info("DBConnectionUtility started!");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Connection getZoneConnection() throws SQLException {
/*  31 */     Connection connection = null;
/*  32 */     if (isJNDIConn.booleanValue()) {
/*     */       
/*     */       try {
/*  35 */         Properties param = new Properties();
/*  36 */         param.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
/*  37 */         Context initialContext = new InitialContext(param);
/*  38 */         DataSource dataSource = (DataSource)initialContext.lookup("jdbc/zone");
/*  39 */         connection = dataSource.getConnection();
/*  40 */       } catch (NamingException e) {
/*  41 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  48 */         Properties prop = ProbUtil.getPropertiesValue();
/*  49 */         String driver = prop.getProperty("DriverClass");
/*  50 */         String url = prop.getProperty("Url");
/*  51 */         String userName = prop.getProperty("UserName");
/*  52 */         String password = prop.getProperty("Password");
/*     */ 
/*     */         
/*  55 */         Class.forName(driver);
/*     */         
/*  57 */         connection = DriverManager.getConnection(url, userName, password);
/*     */       
/*     */       }
/*  60 */       catch (Exception e) {
/*  61 */         logger.info("Error is " + e.getMessage());
/*     */       } 
/*     */     } 
/*  64 */     return connection;
/*     */   }
/*     */   
/*     */   public static Connection getDBLinkConnection() throws SQLException {
/*  68 */     Connection connection = null;
/*  69 */     if (isJNDIConn.booleanValue()) {
/*     */       
/*     */       try {
/*  72 */         Properties param = new Properties();
/*  73 */         param.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
/*  74 */         Context initialContext = new InitialContext(param);
/*  75 */         DataSource dataSource = (DataSource)initialContext.lookup("jdbc/dblink");
/*  76 */         connection = dataSource.getConnection();
/*  77 */       } catch (NamingException e) {
/*  78 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  85 */         Properties prop = ProbUtil.getPropertiesValue();
/*  86 */         String driver = prop.getProperty("DriverClass");
/*  87 */         String url = prop.getProperty("Url");
/*  88 */         String userName = prop.getProperty("UserName");
/*  89 */         String password = prop.getProperty("Password");
/*     */         
/*  91 */         Class.forName(driver);
/*  92 */         connection = DriverManager.getConnection(url, userName, password);
/*     */       }
/*  94 */       catch (Exception e) {
/*  95 */         logger.info("Error is " + e.getMessage());
/*     */       } 
/*     */     } 
/*  98 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Connection getGlobalConnection() throws SQLException {
/* 103 */     Connection connection = null;
/*     */     
/* 105 */     if (isJNDIConn.booleanValue()) {
/*     */       
/*     */       try {
/* 108 */         Properties param = new Properties();
/* 109 */         param.put("java.naming.factory.initial", 
/* 110 */             "com.ibm.websphere.naming.WsnInitialContextFactory");
/* 111 */         Context initialContext = new InitialContext(param);
/* 112 */         DataSource dataSource = (DataSource)initialContext.lookup("jdbc/global");
/* 113 */         connection = dataSource.getConnection();
/* 114 */       } catch (NamingException e) {
/* 115 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 122 */         String driver = "oracle.jdbc.driver.OracleDriver";
/*     */ 
/*     */ 
/*     */         
/* 126 */         String url = "jdbc:oracle:thin:@10.128.230.200:1529/FTRADE";
/*     */         
/* 128 */         String userName = "UBZONE";
/* 129 */         String password = "Cisco123";
/*     */ 
/*     */         
/* 132 */         Class.forName(driver);
/* 133 */         connection = DriverManager.getConnection(url, userName, password);
/*     */       }
/* 135 */       catch (Exception e) {
/* 136 */         logger.info("Exception-- getConnectionubiconnect--Connection-----------------" + e);
/* 137 */         e.getMessage();
/*     */       } 
/*     */     } 
/* 140 */     return connection;
/*     */   }
/*     */   
/*     */   public static Connection getubiconnectConnection() throws SQLException {
/* 144 */     Connection connection = null;
/*     */     
/* 146 */     if (isJNDIConn.booleanValue()) {
/*     */       
/*     */       try {
/* 149 */         Properties param = new Properties();
/* 150 */         param.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
/* 151 */         Context initialContext = new InitialContext(param);
/* 152 */         DataSource dataSource = (DataSource)initialContext.lookup("jdbc/themebridge");
/* 153 */         connection = dataSource.getConnection();
/* 154 */       } catch (NamingException e) {
/* 155 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 161 */         String driver = "oracle.jdbc.driver.OracleDriver";
/*     */         
/* 163 */         String url = "jdbc:oracle:thin:@10.128.230.200:1529/FTRADE";
/*     */         
/* 165 */         String userName = "ubiconnect";
/* 166 */         String password = "Cisco123";
/*     */         
/* 168 */         Class.forName(driver);
/* 169 */         connection = DriverManager.getConnection(url, userName, password);
/*     */       }
/* 171 */       catch (Exception e) {
/* 172 */         logger.info("Exception-- getConnectionubiconnect--Connection-----------------" + e);
/* 173 */         e.getMessage();
/*     */       } 
/*     */     } 
/* 176 */     return connection;
/*     */   }
/*     */   
/*     */   public static void surrenderDB(Connection con, Statement stmt, ResultSet res) {
/*     */     try {
/* 181 */       if (res != null) {
/* 182 */         res.close();
/*     */       }
/* 184 */       if (stmt != null) {
/* 185 */         stmt.close();
/*     */       }
/* 187 */       if (con != null) {
/* 188 */         con.close();
/*     */       }
/* 190 */     } catch (SQLException e) {
/* 191 */       logger.info("Connection Failed! Check output console");
/* 192 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\DBConnectionUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */