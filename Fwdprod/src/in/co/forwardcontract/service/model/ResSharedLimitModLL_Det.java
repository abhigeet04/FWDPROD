/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.ResLimitCategory;
/*    */ import in.co.forwardcontract.service.model.ResSharedLimSerialNum;
/*    */ 
/*    */ public class ResSharedLimitModLL_Det {
/*    */   private String primaryCustomer;
/*    */   private String activeFlg;
/*    */   
/*    */   public String getprimaryCustomer() {
/* 11 */     return this.primaryCustomer;
/*    */   }
/*    */   private ResLimitCategory limitCategoryCode; private ResSharedLimSerialNum key; private String cifId;
/*    */   public void setprimaryCustomer(String primaryCustomer) {
/* 15 */     this.primaryCustomer = primaryCustomer;
/*    */   }
/*    */   
/*    */   public String getactiveFlg() {
/* 19 */     return this.activeFlg;
/*    */   }
/*    */   
/*    */   public void setactiveFlg(String activeFlg) {
/* 23 */     this.activeFlg = activeFlg;
/*    */   }
/*    */   
/*    */   public ResLimitCategory getlimitCategoryCode() {
/* 27 */     return this.limitCategoryCode;
/*    */   }
/*    */   
/*    */   public void setlimitCategoryCode(ResLimitCategory limitCategoryCode) {
/* 31 */     this.limitCategoryCode = limitCategoryCode;
/*    */   }
/*    */   
/*    */   public ResSharedLimSerialNum getkey() {
/* 35 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setkey(ResSharedLimSerialNum key) {
/* 39 */     this.key = key;
/*    */   }
/*    */   
/*    */   public String getcifId() {
/* 43 */     return this.cifId;
/*    */   }
/*    */   
/*    */   public void setcifId(String cifId) {
/* 47 */     this.cifId = cifId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\ResSharedLimitModLL_Det.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */