/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.PostingReqTranPart;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PostingReqCustomData {
/*    */   private String solid;
/*    */   
/*    */   public String getSolid() {
/* 10 */     return this.solid;
/*    */   }
/*    */   private List<PostingReqTranPart> TRANPART;
/*    */   public void setSolid(String solid) {
/* 14 */     this.solid = solid;
/*    */   }
/*    */   
/*    */   public List<PostingReqTranPart> getTRANPART() {
/* 18 */     return this.TRANPART;
/*    */   }
/*    */   
/*    */   public void setTRANPART(List<PostingReqTranPart> tRANPART) {
/* 22 */     this.TRANPART = tRANPART;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\PostingReqCustomData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */