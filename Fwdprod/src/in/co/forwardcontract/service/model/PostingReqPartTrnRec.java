/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.AmtAndCcy;
/*    */ import in.co.forwardcontract.service.model.PostingReqAcc;
/*    */ import in.co.forwardcontract.service.model.PostingReqPmtInst;
/*    */ 
/*    */ public class PostingReqPartTrnRec {
/*    */   private PostingReqAcc AcctId;
/*    */   private String CreditDebitFlg;
/*    */   private AmtAndCcy TrnAmt;
/*    */   
/*    */   public PostingReqAcc getAcctId() {
/* 13 */     return this.AcctId;
/*    */   }
/*    */   private String TrnParticulars; private String ValueDt; private PostingReqPmtInst PmtInst; private String SerialNum;
/*    */   public void setAcctId(PostingReqAcc acctId) {
/* 17 */     this.AcctId = acctId;
/*    */   }
/*    */   
/*    */   public String getCreditDebitFlg() {
/* 21 */     return this.CreditDebitFlg;
/*    */   }
/*    */   
/*    */   public void setCreditDebitFlg(String creditDebitFlg) {
/* 25 */     this.CreditDebitFlg = creditDebitFlg;
/*    */   }
/*    */   
/*    */   public AmtAndCcy getTrnAmt() {
/* 29 */     return this.TrnAmt;
/*    */   }
/*    */   
/*    */   public void setTrnAmt(AmtAndCcy trnAmt) {
/* 33 */     this.TrnAmt = trnAmt;
/*    */   }
/*    */   
/*    */   public String getTrnParticulars() {
/* 37 */     return this.TrnParticulars;
/*    */   }
/*    */   
/*    */   public void setTrnParticulars(String trnParticulars) {
/* 41 */     this.TrnParticulars = trnParticulars;
/*    */   }
/*    */   
/*    */   public String getValueDt() {
/* 45 */     return this.ValueDt;
/*    */   }
/*    */   
/*    */   public void setValueDt(String valueDt) {
/* 49 */     this.ValueDt = valueDt;
/*    */   }
/*    */   
/*    */   public PostingReqPmtInst getPmtInst() {
/* 53 */     return this.PmtInst;
/*    */   }
/*    */   
/*    */   public void setPmtInst(PostingReqPmtInst pmtInst) {
/* 57 */     this.PmtInst = pmtInst;
/*    */   }
/*    */   
/*    */   public String getSerialNum() {
/* 61 */     return this.SerialNum;
/*    */   }
/*    */   
/*    */   public void setSerialNum(String serialNum) {
/* 65 */     this.SerialNum = serialNum;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\PostingReqPartTrnRec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */