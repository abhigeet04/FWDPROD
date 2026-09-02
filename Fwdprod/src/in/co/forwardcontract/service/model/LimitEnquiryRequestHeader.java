/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.LimitEnquiryRequestData;
/*    */ 
/*    */ public class LimitEnquiryRequestHeader {
/*    */   private String requestType;
/*    */   
/*    */   public String getRequestType() {
/*  9 */     return this.requestType;
/*    */   } private String msgid; private LimitEnquiryRequestData data;
/*    */   public void setRequestType(String requestType) {
/* 12 */     this.requestType = requestType;
/*    */   }
/*    */   public String getMsgid() {
/* 15 */     return this.msgid;
/*    */   }
/*    */   public void setMsgid(String msgid) {
/* 18 */     this.msgid = msgid;
/*    */   }
/*    */   public LimitEnquiryRequestData getData() {
/* 21 */     return this.data;
/*    */   }
/*    */   public void setData(LimitEnquiryRequestData data) {
/* 24 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitEnquiryRequestHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */