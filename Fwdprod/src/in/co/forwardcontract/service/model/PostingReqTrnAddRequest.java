/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.PostingReqTrnAddRq;
/*    */ 
/*    */ public class PostingReqTrnAddRequest {
/*    */   private PostingReqTrnAddRq XferTrnAddRq;
/*    */   
/*    */   public PostingReqTrnAddRq getXferTrnAddRq() {
/*  8 */     return this.XferTrnAddRq;
/*    */   }
/*    */   private PostingReqCustomData XferTrnAdd_CustomData;
/*    */   public void setXferTrnAddRq(PostingReqTrnAddRq xferTrnAddRq) {
/* 12 */     this.XferTrnAddRq = xferTrnAddRq;
/*    */   }
/*    */   
/*    */   public PostingReqCustomData getXferTrnAdd_CustomData() {
/* 16 */     return this.XferTrnAdd_CustomData;
/*    */   }
/*    */   
/*    */   public void setXferTrnAdd_CustomData(PostingReqCustomData xferTrnAdd_CustomData) {
/* 20 */     this.XferTrnAdd_CustomData = xferTrnAdd_CustomData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\PostingReqTrnAddRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */