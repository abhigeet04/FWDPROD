/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.AccountAvailBalBankResData;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountAvailBalBankResponse
/*    */ {
/*    */   private AccountAvailBalBankResData data;
/*    */   private String msgrrn;
/*    */   private String msgtime;
/*    */   
/*    */   public AccountAvailBalBankResData getData() {
/* 14 */     return this.data;
/*    */   } private String msgid; private String channelName; private String status; private String errorMsg;
/*    */   public void setData(AccountAvailBalBankResData data) {
/* 17 */     this.data = data;
/*    */   }
/*    */   public String getMsgrrn() {
/* 20 */     return this.msgrrn;
/*    */   }
/*    */   public void setMsgrrn(String msgrrn) {
/* 23 */     this.msgrrn = msgrrn;
/*    */   }
/*    */   public String getMsgtime() {
/* 26 */     return this.msgtime;
/*    */   }
/*    */   public void setMsgtime(String msgtime) {
/* 29 */     this.msgtime = msgtime;
/*    */   }
/*    */   public String getMsgid() {
/* 32 */     return this.msgid;
/*    */   }
/*    */   public void setMsgid(String msgid) {
/* 35 */     this.msgid = msgid;
/*    */   }
/*    */   public String getChannelName() {
/* 38 */     return this.channelName;
/*    */   }
/*    */   public void setChannelName(String channelName) {
/* 41 */     this.channelName = channelName;
/*    */   }
/*    */   public String getStatus() {
/* 44 */     return this.status;
/*    */   }
/*    */   public void setStatus(String status) {
/* 47 */     this.status = status;
/*    */   }
/*    */   public String getErrorMsg() {
/* 50 */     return this.errorMsg;
/*    */   }
/*    */   public void setErrorMsg(String errorMsg) {
/* 53 */     this.errorMsg = errorMsg;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\AccountAvailBalBankResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */