/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.FtrtUpdateBankReqData;
/*    */ 
/*    */ public class FtrtUpdateBankReq
/*    */ {
/*    */   private String requestType;
/*    */   
/*    */   public String getRequestType() {
/* 10 */     return this.requestType;
/*    */   }
/*    */   private String msgid; private FtrtUpdateBankReqData data;
/*    */   public void setRequestType(String requestType) {
/* 14 */     this.requestType = requestType;
/*    */   }
/*    */   
/*    */   public String getMsgid() {
/* 18 */     return this.msgid;
/*    */   }
/*    */   
/*    */   public void setMsgid(String msgid) {
/* 22 */     this.msgid = msgid;
/*    */   }
/*    */   
/*    */   public FtrtUpdateBankReqData getData() {
/* 26 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(FtrtUpdateBankReqData data) {
/* 30 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\FtrtUpdateBankReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */