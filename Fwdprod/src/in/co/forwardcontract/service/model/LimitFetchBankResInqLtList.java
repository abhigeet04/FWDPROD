/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResCustLimitDtls;
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResHeaderDtls;
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResLimitInq;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LimitFetchBankResInqLtList {
/*    */   private List<LimitFetchBankResCustLimitDtls> customerLimitDetails;
/*    */   
/*    */   public List<LimitFetchBankResCustLimitDtls> getCustomerLimitDetails() {
/* 12 */     return this.customerLimitDetails;
/*    */   }
/*    */   private LimitFetchBankResHeaderDtls limitHeaderDetails; private LimitFetchBankResLimitInq LimitInquire;
/*    */   public void setCustomerLimitDetails(List<LimitFetchBankResCustLimitDtls> customerLimitDetails) {
/* 16 */     this.customerLimitDetails = customerLimitDetails;
/*    */   }
/*    */   
/*    */   public LimitFetchBankResHeaderDtls getLimitHeaderDetails() {
/* 20 */     return this.limitHeaderDetails;
/*    */   }
/*    */   
/*    */   public void setLimitHeaderDetails(LimitFetchBankResHeaderDtls limitHeaderDetails) {
/* 24 */     this.limitHeaderDetails = limitHeaderDetails;
/*    */   }
/*    */   
/*    */   public LimitFetchBankResLimitInq getLimitInquire() {
/* 28 */     return this.LimitInquire;
/*    */   }
/*    */   
/*    */   public void setLimitInquire(LimitFetchBankResLimitInq limitInquire) {
/* 32 */     this.LimitInquire = limitInquire;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitFetchBankResInqLtList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */