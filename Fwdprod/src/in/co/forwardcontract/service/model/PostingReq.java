/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.PostingReqTrnAddRequest;
/*    */ 
/*    */ public class PostingReq {
/*    */   private PostingReqTrnAddRequest XferTrnAddRequest;
/*    */   
/*    */   public PostingReqTrnAddRequest getXferTrnAddRequest() {
/*  8 */     return this.XferTrnAddRequest;
/*    */   }
/*    */   private String msgid;
/*    */   public void setXferTrnAddRequest(PostingReqTrnAddRequest xferTrnAddRequest) {
/* 12 */     this.XferTrnAddRequest = xferTrnAddRequest;
/*    */   }
/*    */   
/*    */   public String getMsgid() {
/* 16 */     return this.msgid;
/*    */   }
/*    */   
/*    */   public void setMsgid(String msgid) {
/* 20 */     this.msgid = msgid;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\PostingReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */