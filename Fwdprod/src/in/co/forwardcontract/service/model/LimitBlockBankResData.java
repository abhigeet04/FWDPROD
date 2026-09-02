/*    */ package in.co.forwardcontract.service.model;
/*    */ 
/*    */ import in.co.forwardcontract.service.model.LimitBlockBankResLtBlockList;
/*    */ 
/*    */ public class LimitBlockBankResData {
/*    */   private String Status;
/*    */   private String StatusDesc;
/*    */   
/*    */   public String getStatus() {
/* 10 */     return this.Status;
/*    */   } private LimitBlockBankResLtBlockList LimitblockList; private LimitBlockBankResLtBlockList LimitUnblockList;
/*    */   public void setStatus(String status) {
/* 13 */     this.Status = status;
/*    */   }
/*    */   public String getStatusDesc() {
/* 16 */     return this.StatusDesc;
/*    */   }
/*    */   public void setStatusDesc(String statusDesc) {
/* 19 */     this.StatusDesc = statusDesc;
/*    */   }
/*    */   public LimitBlockBankResLtBlockList getLimitblockList() {
/* 22 */     return this.LimitblockList;
/*    */   }
/*    */   public void setLimitblockList(LimitBlockBankResLtBlockList limitblockList) {
/* 25 */     this.LimitblockList = limitblockList;
/*    */   }
/*    */   public LimitBlockBankResLtBlockList getLimitUnblockList() {
/* 28 */     return this.LimitUnblockList;
/*    */   }
/*    */   public void setLimitUnblockList(LimitBlockBankResLtBlockList limitUnblockList) {
/* 31 */     this.LimitUnblockList = limitUnblockList;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitBlockBankResData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */