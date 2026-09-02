/*    */ package in.co.forwardcontract.dao.exception;
/*    */ 
/*    */ 
/*    */ public class ApplicationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*  8 */   Throwable exceptionClass = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ApplicationException(String msg) {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ApplicationException(String msg, Throwable exception) {
/* 27 */     super(msg, exception);
/* 28 */     this.exceptionClass = exception;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void printStackTrace() {
/* 36 */     if (this.exceptionClass != null) {
/* 37 */       System.err.println("An exception has caused by " + 
/* 38 */           this.exceptionClass.toString());
/* 39 */       this.exceptionClass.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\dao\exception\ApplicationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */