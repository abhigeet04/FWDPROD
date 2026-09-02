/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.LimitBlockBankResCategoryCode;
/*    */ import in.co.forwardcontract.service.model.SerialNumber;
/*    */ 
/*    */ public class LimitBlockBankResSharedLimit {
/*    */   private String cifId;
/*    */   private SerialNumber key;
/*    */   
/*    */   public String getCifId() {
/* 11 */     return this.cifId;
/*    */   }
/*    */   private String primaryCustomer; private LimitBlockBankResCategoryCode limitCategoryCode; private String activeFlg;
/*    */   public void setCifId(String cifId) {
/* 15 */     this.cifId = cifId;
/*    */   }
/*    */   
/*    */   public SerialNumber getKey() {
/* 19 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(SerialNumber key) {
/* 23 */     this.key = key;
/*    */   }
/*    */   
/*    */   public String getPrimaryCustomer() {
/* 27 */     return this.primaryCustomer;
/*    */   }
/*    */   
/*    */   public void setPrimaryCustomer(String primaryCustomer) {
/* 31 */     this.primaryCustomer = primaryCustomer;
/*    */   }
/*    */   
/*    */   public LimitBlockBankResCategoryCode getLimitCategoryCode() {
/* 35 */     return this.limitCategoryCode;
/*    */   }
/*    */   
/*    */   public void setLimitCategoryCode(LimitBlockBankResCategoryCode limitCategoryCode) {
/* 39 */     this.limitCategoryCode = limitCategoryCode;
/*    */   }
/*    */   
/*    */   public String getActiveFlg() {
/* 43 */     return this.activeFlg;
/*    */   }
/*    */   
/*    */   public void setActiveFlg(String activeFlg) {
/* 47 */     this.activeFlg = activeFlg;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitBlockBankResSharedLimit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */