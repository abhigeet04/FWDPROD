/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.ReqAccStatusData;
/*    */ 
/*    */ public class AccountStatusReq {
/*    */   private String requestType;
/*    */   
/*    */   public String getRequestType() {
/*  8 */     return this.requestType;
/*    */   } private String msgid; private ReqAccStatusData data;
/*    */   public void setRequestType(String requestType) {
/* 11 */     this.requestType = requestType;
/*    */   }
/*    */   public String getMsgid() {
/* 14 */     return this.msgid;
/*    */   }
/*    */   public void setMsgid(String msgid) {
/* 17 */     this.msgid = msgid;
/*    */   }
/*    */   public ReqAccStatusData getData() {
/* 20 */     return this.data;
/*    */   }
/*    */   public void setData(ReqAccStatusData data) {
/* 23 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\AccountStatusReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */