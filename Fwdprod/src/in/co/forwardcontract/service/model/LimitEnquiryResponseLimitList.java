/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.LimitEnquiryResponseLimitDetails;
/*    */ import in.co.forwardcontract.service.model.LimitEnquiryResponseLimitDetailsUML;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class LimitEnquiryResponseLimitList {
/*    */   private ArrayList<LimitEnquiryResponseLimitDetails> UserLimitDetails;
/*    */   
/*    */   public ArrayList<LimitEnquiryResponseLimitDetails> getUserLimitDetails() {
/* 10 */     return this.UserLimitDetails;
/*    */   } private LimitEnquiryResponseLimitDetailsUML UML;
/*    */   public void setUserLimitDetails(ArrayList<LimitEnquiryResponseLimitDetails> userLimitDetails) {
/* 13 */     this.UserLimitDetails = userLimitDetails;
/*    */   }
/*    */   public LimitEnquiryResponseLimitDetailsUML getUML() {
/* 16 */     return this.UML;
/*    */   }
/*    */   public void setUML(LimitEnquiryResponseLimitDetailsUML uML) {
/* 19 */     this.UML = uML;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitEnquiryResponseLimitList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */