/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.PostingReqCount;
/*    */ import in.co.forwardcontract.service.model.PostingReqTrnDetail;
/*    */ 
/*    */ public class PostingReqTrnAddRq {
/*    */   private PostingReqTrnHdr XferTrnHdr;
/*    */   
/*    */   public PostingReqTrnHdr getXferTrnHdr() {
/*  9 */     return this.XferTrnHdr;
/*    */   }
/*    */   private PostingReqCount XferCount; private PostingReqTrnDetail XferTrnDetail;
/*    */   public void setXferTrnHdr(PostingReqTrnHdr xferTrnHdr) {
/* 13 */     this.XferTrnHdr = xferTrnHdr;
/*    */   }
/*    */   
/*    */   public PostingReqCount getXferCount() {
/* 17 */     return this.XferCount;
/*    */   }
/*    */   
/*    */   public void setXferCount(PostingReqCount xferCount) {
/* 21 */     this.XferCount = xferCount;
/*    */   }
/*    */   
/*    */   public PostingReqTrnDetail getXferTrnDetail() {
/* 25 */     return this.XferTrnDetail;
/*    */   }
/*    */   
/*    */   public void setXferTrnDetail(PostingReqTrnDetail xferTrnDetail) {
/* 29 */     this.XferTrnDetail = xferTrnDetail;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\PostingReqTrnAddRq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */