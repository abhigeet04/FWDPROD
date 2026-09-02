/*    */ package in.co.forwardcontract.dao.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DAOException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -6677728600384808574L;
/*    */   
/*    */   public DAOException() {}
/*    */   
/*    */   public DAOException(String msg) {
/* 25 */     super(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DAOException(Throwable exception) {
/* 35 */     super(exception);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DAOException(String msg, Throwable exception) {
/* 46 */     super(msg, exception);
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\dao\exception\DAOException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */