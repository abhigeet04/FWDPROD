/*    */ package in.co.forwardcontract.bd;
/*    */ 
/*    */ import in.co.forwardcontract.bd.exception.BusinessException;
/*    */ import in.co.forwardcontract.utility.LogHelper;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class BaseBusinessDelegate
/*    */ {
/* 10 */   private static Logger logger = LogManager.getLogger(in.co.forwardcontract.bd.BaseBusinessDelegate.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void throwBDException(Exception exception) throws BusinessException {
/* 20 */     logger.error(exception.fillInStackTrace());
/* 21 */     LogHelper.logError(logger, exception);
/* 22 */     throw new BusinessException(exception.getMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\bd\BaseBusinessDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */