/*    */ package in.co.forwardcontract.bd.basicinfo;
/*    */ 
/*    */ import in.co.forwardcontract.bd.BaseBusinessDelegate;
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
/*    */ public class BasicinfoBD
/*    */   extends BaseBusinessDelegate
/*    */ {
/*    */   static in.co.forwardcontract.bd.basicinfo.BasicinfoBD bd;
/*    */   
/*    */   public static in.co.forwardcontract.bd.basicinfo.BasicinfoBD getBD() {
/* 23 */     if (bd == null) {
/* 24 */       bd = new in.co.forwardcontract.bd.basicinfo.BasicinfoBD();
/*    */     }
/* 26 */     return bd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\bd\basicinfo\BasicinfoBD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */