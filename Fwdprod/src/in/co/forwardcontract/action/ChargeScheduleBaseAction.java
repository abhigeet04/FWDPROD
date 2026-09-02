/*     */ package in.co.forwardcontract.action;
/*     */ 
/*     */ import com.opensymphony.xwork2.ActionContext;
/*     */ import com.opensymphony.xwork2.ActionSupport;
/*     */ import in.co.chargeSchedule.businessdelegate.ChargeScheduleBD;
/*     */ import in.co.chargeSchedule.dao.exception.ApplicationException;
/*     */ import in.co.chargeSchedule.utility.ActionConstants;
/*     */ import in.co.chargeSchedule.utility.DBConnectionUtility;
/*     */ import in.co.chargeSchedule.utility.LogHelper;
/*     */ import in.co.chargeSchedule.utility.LoggableStatement;
/*     */ import in.co.chargeSchedule.vo.ChargeScheduleVO;
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
/*     */ public class ChargeScheduleBaseAction
/*     */   extends ActionSupport
/*     */   implements ActionConstants
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  31 */   private static final Logger logger = LogManager.getLogger(in.co.chargeSchedule.action.ChargeScheduleBaseAction.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void throwApplicationException(Exception exception) throws ApplicationException {
/*  40 */     logger.error(exception.fillInStackTrace());
/*  41 */     LogHelper.logError(logger, exception);
/*  42 */     throw new ApplicationException(exception.getMessage(), exception);
/*     */   }
/*     */ 
/*     */   
/*     */   public String execute() throws Exception {
/*  47 */     return super.execute();
/*     */   }
/*     */   
/*     */   public boolean isSessionAvailable() throws ApplicationException {
/*  51 */     logger.info("Entering Method");
/*  52 */     String sessionUserName = null;
/*  53 */     ChargeScheduleBD chargBD = null;
/*  54 */     ChargeScheduleVO chargVO = null;
/*  55 */     boolean isAvail = false;
/*  56 */     String userName = null;
/*  57 */     String loginedUserId = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  62 */       HttpSession session = ServletActionContext.getRequest().getSession();
/*     */       
/*  64 */       HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
/*     */       
/*  66 */       sessionUserName = (String)session.getAttribute("loginedUserName");
/*  67 */       logger.info("loginedUserName------------------" + sessionUserName);
/*     */ 
/*     */       
/*  70 */       if (sessionUserName == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  75 */         sessionUserName = request.getRemoteUser();
/*  76 */         logger.info("getRemoteUser[------------------" + sessionUserName);
/*     */         
/*  78 */         if (sessionUserName == null) {
/*     */           
/*  80 */           Connection them_con = null;
/*  81 */           them_con = DBConnectionUtility.getConnectionubiconnect();
/*     */ 
/*     */           
/*  84 */           sessionUserName = request.getRequestedSessionId();
/*  85 */           String get_User_ID = "SELECT SCT.USERNAME AS USER_ID FROM CENTRAL_SESSION_DETAILS SCT,LOCAL_SESSION_DETAILS LOC  WHERE SCT.CENTRAL_ID=LOC.CENTRAL_ID AND SCT.ENDED  IS NULL AND LOC.LOCAL_ID= ? ";
/*     */ 
/*     */ 
/*     */           
/*  89 */           LoggableStatement lst = new LoggableStatement(them_con, get_User_ID);
/*  90 */           lst.setString(1, sessionUserName);
/*  91 */           logger.info("Getting Session Value Query------------" + lst.getQueryString());
/*     */ 
/*     */           
/*  94 */           ResultSet rst = lst.executeQuery();
/*     */           
/*  96 */           while (rst.next()) {
/*     */             
/*  98 */             sessionUserName = rst.getString("USER_ID");
/*  99 */             logger.info("Getting Session Value Query-- user id value----------" + sessionUserName);
/*     */           } 
/*     */           
/* 102 */           session.setAttribute("loginedUserName", userName);
/* 103 */           session.setAttribute("loginedUserId", userName);
/* 104 */           DBConnectionUtility.surrenderDB(them_con, (Statement)lst, rst);
/* 105 */           logger.info("userName-----------" + userName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       if (sessionUserName != null) {
/* 118 */         chargBD = new ChargeScheduleBD();
/* 119 */         chargVO = new ChargeScheduleVO();
/* 120 */         chargVO.setSessionUserName(sessionUserName);
/*     */         
/* 122 */         loginedUserId = String.valueOf(chargVO.getUserid());
/*     */         
/* 124 */         session.setAttribute("loginedUserName", sessionUserName);
/* 125 */         session.setAttribute("loginedUserId", loginedUserId);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       return isAvail;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 137 */     catch (Exception exception) {
/*     */       
/* 139 */       throwApplicationException(exception);
/*     */       
/* 141 */       logger.info("Exiting Method");
/* 142 */       return isAvail;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\action\ChargeScheduleBaseAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */