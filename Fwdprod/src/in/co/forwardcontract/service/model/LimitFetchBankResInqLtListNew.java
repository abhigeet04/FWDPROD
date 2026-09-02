/*    */ package in.co.forwardcontract.service.model;
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResCustLimitDtls;
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResHeaderDtls;
/*    */ import in.co.forwardcontract.service.model.LimitFetchBankResLimitInq;
/*    */ 
/*    */ public class LimitFetchBankResInqLtListNew {
/*    */   private LimitFetchBankResCustLimitDtls customerLimitDetails;
/*    */   
/*    */   public LimitFetchBankResHeaderDtls getLimitHeaderDetails() {
/* 10 */     return this.limitHeaderDetails;
/*    */   }
/*    */   private LimitFetchBankResHeaderDtls limitHeaderDetails; private LimitFetchBankResLimitInq LimitInquire;
/*    */   public void setLimitHeaderDetails(LimitFetchBankResHeaderDtls limitHeaderDetails) {
/* 14 */     this.limitHeaderDetails = limitHeaderDetails;
/*    */   }
/*    */   
/*    */   public LimitFetchBankResLimitInq getLimitInquire() {
/* 18 */     return this.LimitInquire;
/*    */   }
/*    */   
/*    */   public void setLimitInquire(LimitFetchBankResLimitInq limitInquire) {
/* 22 */     this.LimitInquire = limitInquire;
/*    */   }
/*    */   
/*    */   public LimitFetchBankResCustLimitDtls getCustomerLimitDetails() {
/* 26 */     return this.customerLimitDetails;
/*    */   }
/*    */   
/*    */   public void setCustomerLimitDetails(LimitFetchBankResCustLimitDtls customerLimitDetails) {
/* 30 */     this.customerLimitDetails = customerLimitDetails;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitFetchBankResInqLtListNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */