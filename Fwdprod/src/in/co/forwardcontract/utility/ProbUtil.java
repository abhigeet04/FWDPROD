/*    */ package in.co.forwardcontract.utility;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class ProbUtil {
/*    */   public static Properties getPropertiesValue() throws IOException {
/*  9 */     InputStream input = null;
/* 10 */     Properties prop = new Properties();
/*    */     try {
/* 12 */       input = in.co.forwardcontract.utility.ProbUtil.class.getClassLoader().getResourceAsStream("in/co/forwardcontract/resources/ForwardContract.properties");
/* 13 */       prop.load(input);
/* 14 */     } catch (Exception e) {
/* 15 */       e.printStackTrace();
/*    */     } finally {
/* 17 */       if (input != null) {
/* 18 */         input.close();
/*    */       }
/*    */     } 
/* 21 */     return prop;
/*    */   }
/*    */   
/*    */   public static Properties getErrorPropertiesValue() throws IOException {
/* 25 */     InputStream input = null;
/* 26 */     Properties prop = new Properties();
/*    */     try {
/* 28 */       input = in.co.forwardcontract.utility.ProbUtil.class.getClassLoader().getResourceAsStream("in/co/forwardcontract/resources/ErrorValues.properties");
/* 29 */       prop.load(input);
/* 30 */     } catch (Exception e) {
/* 31 */       e.printStackTrace();
/*    */     } finally {
/* 33 */       if (input != null) {
/* 34 */         input.close();
/*    */       }
/*    */     } 
/* 37 */     return prop;
/*    */   }
/*    */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\ProbUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */