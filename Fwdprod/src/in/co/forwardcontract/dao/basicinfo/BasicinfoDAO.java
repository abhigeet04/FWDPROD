/*    */ package in.co.forwardcontract.dao.basicinfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicinfoDAO
/*    */ {
/*    */   static in.co.forwardcontract.dao.basicinfo.BasicinfoDAO dao;
/*    */   int userid;
/*    */   
/*    */   public static in.co.forwardcontract.dao.basicinfo.BasicinfoDAO getDAO() {
/* 24 */     if (dao == null) {
/* 25 */       dao = new in.co.forwardcontract.dao.basicinfo.BasicinfoDAO();
/*    */     }
/* 27 */     return dao;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\dao\basicinfo\BasicinfoDAO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */