/*     */ package in.co.forwardcontract.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ActionContext;
/*     */ import com.opensymphony.xwork2.ActionSupport;
/*     */ import in.co.forwardcontract.dao.exception.ApplicationException;
/*     */ import in.co.forwardcontract.utility.ActionConstants;
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import in.co.forwardcontract.utility.LogHelper;
/*     */ import in.co.forwardcontract.utility.LoggableStatement;
/*     */ import in.co.forwardcontract.vo.ForwardContractVO;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ForwardContractBaseAction
/*     */   extends ActionSupport
/*     */   implements ActionConstants
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   private static Logger logger = LogManager.getLogger(in.co.forwardcontract.action.ForwardContractBaseAction.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void throwApplicationException(Exception exception) throws ApplicationException {
/*  39 */     logger.error(exception.fillInStackTrace());
/*  40 */     LogHelper.logError(logger, exception);
/*  41 */     throw new ApplicationException(exception.getMessage(), exception);
/*     */   }
/*     */ 
/*     */   
/*     */   public String execute() throws Exception {
/*  46 */     return super.execute();
/*     */   }
/*     */   
/*     */   public boolean isSessionAvailable() throws ApplicationException {
/*  50 */     logger.info("Entering Method");
/*  51 */     String sessionUserName = null;
/*     */     
/*  53 */     ForwardContractVO chargVO = null;
/*  54 */     boolean isAvail = false;
/*  55 */     String userName = null;
/*  56 */     String loginedUserId = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  61 */       HttpSession session = ServletActionContext.getRequest().getSession();
/*     */       
/*  63 */       HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */       
/*  65 */       sessionUserName = (String)session.getAttribute("loginedUserName");
/*  66 */       logger.info("loginedUserName------------------" + sessionUserName);
/*     */ 
/*     */       
/*  69 */       if (sessionUserName == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  74 */         sessionUserName = request.getRemoteUser();
/*  75 */         logger.info("getRemoteUser[------------------" + sessionUserName);
/*     */         
/*  77 */         if (sessionUserName == null) {
/*     */           
/*  79 */           Connection them_con = null;
/*  80 */           them_con = DBConnectionUtility.getGlobalConnection();
/*     */ 
/*     */           
/*  83 */           sessionUserName = request.getRequestedSessionId();
/*  84 */           String get_User_ID = "SELECT SCT.USERNAME AS USER_ID FROM CENTRAL_SESSION_DETAILS SCT,LOCAL_SESSION_DETAILS LOC  WHERE SCT.CENTRAL_ID=LOC.CENTRAL_ID AND SCT.ENDED  IS NULL AND LOC.LOCAL_ID= ? ";
/*     */ 
/*     */ 
/*     */           
/*  88 */           LoggableStatement lst = new LoggableStatement(them_con, get_User_ID);
/*  89 */           lst.setString(1, sessionUserName);
/*  90 */           logger.info("Getting Session Value Query------------" + lst.getQueryString());
/*     */ 
/*     */           
/*  93 */           ResultSet rst = lst.executeQuery();
/*     */           
/*  95 */           while (rst.next()) {
/*     */             
/*  97 */             sessionUserName = rst.getString("USER_ID");
/*  98 */             logger.info("Getting Session Value Query-- user id value----------" + sessionUserName);
/*     */           } 
/*     */           
/* 101 */           session.setAttribute("loginedUserName", userName);
/* 102 */           session.setAttribute("loginedUserId", userName);
/* 103 */           DBConnectionUtility.surrenderDB(them_con, (Statement)lst, rst);
/* 104 */           logger.info("userName-----------" + userName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       if (sessionUserName != null) {
/*     */         
/* 113 */         chargVO = new ForwardContractVO();
/* 114 */         chargVO.setSessionUserName(sessionUserName);
/*     */         
/* 116 */         loginedUserId = String.valueOf(chargVO.getUserid());
/*     */         
/* 118 */         session.setAttribute("loginedUserName", sessionUserName);
/* 119 */         session.setAttribute("loginedUserId", loginedUserId);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 124 */       return isAvail;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 129 */     catch (Exception exception) {
/*     */       
/* 131 */       throwApplicationException(exception);
/*     */       
/* 133 */       logger.info("Exiting Method");
/* 134 */       return isAvail;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String isSessionAvailable1() throws ApplicationException {
/* 140 */     logger.info("Entering Method");
/* 141 */     String sessionUserName = null;
/*     */     
/* 143 */     ForwardContractVO chargVO = null;
/*     */     
/* 145 */     String userName = null;
/* 146 */     String loginedUserId = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 151 */       HttpSession session = ServletActionContext.getRequest().getSession();
/*     */       
/* 153 */       HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */       
/* 155 */       sessionUserName = (String)session.getAttribute("loginedUserName");
/* 156 */       logger.info("loginedUserName------------------" + sessionUserName);
/*     */ 
/*     */       
/* 159 */       if (sessionUserName == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         sessionUserName = request.getRemoteUser();
/* 165 */         logger.info("getRemoteUser[------------------" + sessionUserName);
/*     */ 
/*     */         
/* 168 */         if (sessionUserName == null) {
/*     */           
/* 170 */           Connection them_con = null;
/* 171 */           them_con = DBConnectionUtility.getGlobalConnection();
/*     */ 
/*     */           
/* 174 */           sessionUserName = request.getRequestedSessionId();
/* 175 */           String get_User_ID = "SELECT SCT.USERNAME AS USER_ID FROM CENTRAL_SESSION_DETAILS SCT,LOCAL_SESSION_DETAILS LOC  WHERE SCT.CENTRAL_ID=LOC.CENTRAL_ID AND SCT.ENDED  IS NULL AND LOC.LOCAL_ID= ? ";
/*     */ 
/*     */ 
/*     */           
/* 179 */           LoggableStatement lst = new LoggableStatement(them_con, get_User_ID);
/* 180 */           lst.setString(1, sessionUserName);
/* 181 */           logger.info("Getting Session Value Query------------" + lst.getQueryString());
/*     */ 
/*     */           
/* 184 */           ResultSet rst = lst.executeQuery();
/*     */           
/* 186 */           while (rst.next()) {
/*     */             
/* 188 */             sessionUserName = rst.getString("USER_ID");
/* 189 */             logger.info("Getting Session Value Query-- user id value----------" + sessionUserName);
/*     */           } 
/*     */           
/* 192 */           session.setAttribute("loginedUserName", userName);
/* 193 */           session.setAttribute("loginedUserId", userName);
/* 194 */           DBConnectionUtility.surrenderDB(them_con, (Statement)lst, rst);
/* 195 */           logger.info("userName-----------" + userName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       if (sessionUserName != null) {
/*     */         
/* 204 */         chargVO = new ForwardContractVO();
/* 205 */         chargVO.setSessionUserName(sessionUserName);
/*     */         
/* 207 */         loginedUserId = String.valueOf(chargVO.getUserid());
/*     */         
/* 209 */         session.setAttribute("loginedUserName", sessionUserName);
/* 210 */         session.setAttribute("loginedUserId", loginedUserId);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 215 */       return sessionUserName;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 220 */     catch (Exception exception) {
/*     */       
/* 222 */       throwApplicationException(exception);
/*     */       
/* 224 */       logger.info("Exiting Method");
/* 225 */       return sessionUserName;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\action\ForwardContractBaseAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */