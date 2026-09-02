/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.AccountAvailRequestData;
/*    */ 
/*    */ public class AccountAvailRequestHeader {
/*    */   private String requestType;
/*    */   private String msgid;
/*    */   private AccountAvailRequestData data;
/*    */   
/*    */   public String getRequestType() {
/* 11 */     return this.requestType;
/*    */   }
/*    */   
/*    */   public void setRequestType(String requestType) {
/* 15 */     this.requestType = requestType;
/*    */   }
/*    */   
/*    */   public String getMsgid() {
/* 19 */     return this.msgid;
/*    */   }
/*    */   
/*    */   public void setMsgid(String msgid) {
/* 23 */     this.msgid = msgid;
/*    */   }
/*    */   
/*    */   public AccountAvailRequestData getData() {
/* 27 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(AccountAvailRequestData data) {
/* 31 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\AccountAvailRequestHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */