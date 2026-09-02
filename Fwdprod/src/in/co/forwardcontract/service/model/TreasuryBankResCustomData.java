/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.TreasuryBankResCustomDataDetails;
/*    */ 
/*    */ public class TreasuryBankResCustomData
/*    */ {
/*    */   private String Message;
/*    */   
/*    */   public String getMessage() {
/* 10 */     return this.Message;
/*    */   } private String successorfailure; private TreasuryBankResCustomDataDetails StatementTransactionDetail;
/*    */   public void setMessage(String message) {
/* 13 */     this.Message = message;
/*    */   }
/*    */   public String getSuccessorfailure() {
/* 16 */     return this.successorfailure;
/*    */   }
/*    */   public void setSuccessorfailure(String successorfailure) {
/* 19 */     this.successorfailure = successorfailure;
/*    */   }
/*    */   public TreasuryBankResCustomDataDetails getStatementTransactionDetail() {
/* 22 */     return this.StatementTransactionDetail;
/*    */   }
/*    */   public void setStatementTransactionDetail(TreasuryBankResCustomDataDetails statementTransactionDetail) {
/* 25 */     this.StatementTransactionDetail = statementTransactionDetail;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\TreasuryBankResCustomData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */