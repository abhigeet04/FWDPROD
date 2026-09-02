/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.AccountAvailBalBankResAmt;
/*    */ 
/*    */ 
/*    */ public class AccountAvailBalBankResData
/*    */ {
/*    */   private AccountAvailBalBankResAmt amount;
/*    */   private String ledgerBalance;
/*    */   private String bankTxnId;
/*    */   
/*    */   public AccountAvailBalBankResAmt getAmount() {
/* 13 */     return this.amount;
/*    */   } private String currency; private String transactionId; private String responseCode; private String status;
/*    */   public void setAmount(AccountAvailBalBankResAmt amount) {
/* 16 */     this.amount = amount;
/*    */   }
/*    */   public String getLedgerBalance() {
/* 19 */     return this.ledgerBalance;
/*    */   }
/*    */   public void setLedgerBalance(String ledgerBalance) {
/* 22 */     this.ledgerBalance = ledgerBalance;
/*    */   }
/*    */   public String getBankTxnId() {
/* 25 */     return this.bankTxnId;
/*    */   }
/*    */   public void setBankTxnId(String bankTxnId) {
/* 28 */     this.bankTxnId = bankTxnId;
/*    */   }
/*    */   public String getCurrency() {
/* 31 */     return this.currency;
/*    */   }
/*    */   public void setCurrency(String currency) {
/* 34 */     this.currency = currency;
/*    */   }
/*    */   public String getTransactionId() {
/* 37 */     return this.transactionId;
/*    */   }
/*    */   public void setTransactionId(String transactionId) {
/* 40 */     this.transactionId = transactionId;
/*    */   }
/*    */   public String getResponseCode() {
/* 43 */     return this.responseCode;
/*    */   }
/*    */   public void setResponseCode(String responseCode) {
/* 46 */     this.responseCode = responseCode;
/*    */   }
/*    */   public String getStatus() {
/* 49 */     return this.status;
/*    */   }
/*    */   public void setStatus(String status) {
/* 52 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\AccountAvailBalBankResData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */