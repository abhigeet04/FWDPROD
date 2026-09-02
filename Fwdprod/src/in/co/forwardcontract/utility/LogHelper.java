/*    */ package in.co.forwardcontract.utility;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogHelper
/*    */ {
/* 12 */   public static int noOfLinesToBeDisplayed = 15;
/*    */   
/*    */   public static void logError(Logger log, Throwable ex) {
/* 15 */     StackTraceElement[] ste = ex.getStackTrace();
/* 16 */     for (int i = 0; i < ste.length; i++) {
/* 17 */       if (i == noOfLinesToBeDisplayed) {
/* 18 */         log.error("\t....... other lines are cropped.");
/*    */         return;
/*    */       } 
/* 21 */       if (i == 0) {
/* 22 */         log.error(ste[i]);
/*    */       } else {
/* 24 */         log.error("\t" + ste[i]);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\LogHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */