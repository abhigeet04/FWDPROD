/*     */ package in.co.forwardcontract.dao;
/*     */ 
/*     */ import in.co.forwardcontract.dao.exception.DAOException;
/*     */ import in.co.forwardcontract.utility.LogHelper;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractDAO
/*     */ {
/*  20 */   private static Logger logger = LogManager.getLogger(in.co.forwardcontract.dao.AbstractDAO.class);
/*     */   
/*  22 */   protected static DataSource dataSource = null;
/*     */   
/*     */   public boolean logDebug = false;
/*     */   
/*     */   protected Connection getConnection() throws Exception {
/*  27 */     logger.debug("Entering Method");
/*  28 */     Connection conn = null;
/*  29 */     String userName = "root";
/*  30 */     String password = "root";
/*     */     
/*  32 */     String url = "jdbc:mysql://localhost:3306/demohab_myshare";
/*  33 */     Class.forName("com.mysql.jdbc.Driver").newInstance();
/*  34 */     conn = DriverManager.getConnection(url, userName, password);
/*     */ 
/*     */     
/*  37 */     logger.debug("Exiting Method");
/*  38 */     return conn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeConnection(Connection myConnection) {
/*     */     try {
/*  49 */       if (myConnection != null) {
/*  50 */         myConnection.close();
/*     */       }
/*  52 */     } catch (Exception excepConnection) {
/*  53 */       logger.error(excepConnection.fillInStackTrace());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closePreparedStatement(PreparedStatement preparedStatement) {
/*     */     try {
/*  64 */       if (preparedStatement != null) {
/*  65 */         preparedStatement.close();
/*     */       }
/*  67 */     } catch (Exception excepConnection) {
/*  68 */       logger.error(excepConnection.fillInStackTrace());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeResultSet(ResultSet resultSet) {
/*     */     try {
/*  79 */       if (resultSet != null) {
/*  80 */         resultSet.close();
/*     */       }
/*  82 */     } catch (Exception excepConnection) {
/*  83 */       logger.error(excepConnection.fillInStackTrace());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeStatement(Statement statement) {
/*     */     try {
/*  94 */       if (statement != null) {
/*  95 */         statement.close();
/*     */       }
/*  97 */     } catch (Exception excepConnection) {
/*  98 */       logger.error(excepConnection.fillInStackTrace());
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
/*     */   public void throwDAOException(Exception exception) throws DAOException {
/* 111 */     logger.error(exception.fillInStackTrace());
/* 112 */     LogHelper.logError(logger, exception);
/* 113 */     throw new DAOException(exception.getMessage());
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
/*     */   public void closeSqlRefferance(PreparedStatement preparedStatement, Connection connection) {
/* 126 */     closePreparedStatement(preparedStatement);
/* 127 */     closeConnection(connection);
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
/*     */   public void closeSqlRefferance(ResultSet result, PreparedStatement preparedStatement, Connection connection) {
/* 140 */     closeResultSet(result);
/* 141 */     closePreparedStatement(preparedStatement);
/* 142 */     closeConnection(connection);
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\dao\AbstractDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */