/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.LimitFetchCustomerReq;
/*    */ 
/*    */ public class LimitFetchBankReq {
/*    */   private String requestType;
/*    */   
/*    */   public String getRequestType() {
/*  9 */     return this.requestType;
/*    */   }
/*    */   private String msgid; private LimitFetchCustomerReq data;
/*    */   public void setRequestType(String requestType) {
/* 13 */     this.requestType = requestType;
/*    */   }
/*    */   
/*    */   public String getMsgid() {
/* 17 */     return this.msgid;
/*    */   }
/*    */   
/*    */   public void setMsgid(String msgid) {
/* 21 */     this.msgid = msgid;
/*    */   }
/*    */   
/*    */   public LimitFetchCustomerReq getData() {
/* 25 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(LimitFetchCustomerReq data) {
/* 29 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitFetchBankReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */