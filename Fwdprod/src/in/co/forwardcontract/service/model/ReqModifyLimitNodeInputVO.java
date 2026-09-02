/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.ReqUserMaintLiabModLL;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReqModifyLimitNodeInputVO {
/*    */   private String limitPrefix;
/*    */   private String limitSuffix;
/*    */   private List<ReqUserMaintLiabModLL> userMaintLiabModLL;
/*    */   
/*    */   public List<ReqUserMaintLiabModLL> getUserMaintLiabModLL() {
/* 12 */     return this.userMaintLiabModLL;
/*    */   }
/*    */   
/*    */   public void setUserMaintLiabModLL(List<ReqUserMaintLiabModLL> userMaintLiabModLL) {
/* 16 */     this.userMaintLiabModLL = userMaintLiabModLL;
/*    */   }
/*    */   
/*    */   public String getlimitPrefix() {
/* 20 */     return this.limitPrefix;
/*    */   }
/*    */   
/*    */   public void setlimitPrefix(String limitPrefix) {
/* 24 */     this.limitPrefix = limitPrefix;
/*    */   }
/*    */   
/*    */   public String getlimitSuffix() {
/* 28 */     return this.limitSuffix;
/*    */   }
/*    */   
/*    */   public void setlimitSuffix(String limitSuffix) {
/* 32 */     this.limitSuffix = limitSuffix;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\ReqModifyLimitNodeInputVO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */