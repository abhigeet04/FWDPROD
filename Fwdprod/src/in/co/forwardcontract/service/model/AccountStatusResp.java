/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.ResData;
/*    */ 
/*    */ public class AccountStatusResp
/*    */ {
/*    */   private ResData data;
/*    */   private String msgrrn;
/*    */   private String msgtime;
/*    */   
/*    */   public ResData getData() {
/* 12 */     return this.data;
/*    */   }
/*    */   private String msgid; private String channelName; private String status;
/*    */   public void setData(ResData data) {
/* 16 */     this.data = data;
/*    */   }
/*    */   
/*    */   public String getMsgrrn() {
/* 20 */     return this.msgrrn;
/*    */   }
/*    */   
/*    */   public void setMsgrrn(String msgrrn) {
/* 24 */     this.msgrrn = msgrrn;
/*    */   }
/*    */   
/*    */   public String getMsgtime() {
/* 28 */     return this.msgtime;
/*    */   }
/*    */   
/*    */   public void setMsgtime(String msgtime) {
/* 32 */     this.msgtime = msgtime;
/*    */   }
/*    */   
/*    */   public String getMsgid() {
/* 36 */     return this.msgid;
/*    */   }
/*    */   
/*    */   public void setMsgid(String msgid) {
/* 40 */     this.msgid = msgid;
/*    */   }
/*    */   
/*    */   public String getChannelName() {
/* 44 */     return this.channelName;
/*    */   }
/*    */   
/*    */   public void setChannelName(String channelName) {
/* 48 */     this.channelName = channelName;
/*    */   }
/*    */   
/*    */   public String getStatus() {
/* 52 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(String status) {
/* 56 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\AccountStatusResp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */