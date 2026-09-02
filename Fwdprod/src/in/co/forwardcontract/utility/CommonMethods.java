/*     */ package in.co.forwardcontract.utility;
/*     */ 
/*     */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*     */ import in.co.forwardcontract.utility.LoggableStatement;
/*     */ import in.co.forwardcontract.utility.ProbUtil;
/*     */ import in.co.forwardcontract.vo.AlertMessagesVO;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.sql.Connection;
///*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Currency;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ 
/*     */ public class CommonMethods
/*     */ {
/*  35 */   public static LinkedHashMap<String, String> TBProperties = new LinkedHashMap<>();
/*  36 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.utility.CommonMethods.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String setErrorString(String errorString, String currentError) {
/*  47 */     if (errorString.length() > 0)
/*  48 */       errorString = String.valueOf(errorString) + ","; 
/*  49 */     errorString = String.valueOf(errorString) + currentError;
/*  50 */     return errorString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int retrieveNoOfErrors(String errorString) {
/*  61 */     StringTokenizer stringTokenizer = new StringTokenizer(errorString, ",");
/*  62 */     int n = stringTokenizer.countTokens();
/*  63 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNull(String value) {
/*  74 */     boolean result = false;
/*  75 */     if (value == null || value.equalsIgnoreCase("")) {
/*  76 */       result = true;
/*     */     }
/*  78 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidString(String string) {
/*  89 */     if (string == null || "".equalsIgnoreCase(string) || string.isEmpty()) {
/*  90 */       return false;
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public String getEmptyIfNull(Object sourceStr) {
/*  96 */     return convertIfNull(sourceStr, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertIfNull(Object sourceStr, Object toConvert) {
/* 111 */     return isNullValue(sourceStr) ? toConvert.toString() : sourceStr.toString();
/*     */   }
/*     */   
/*     */   public static boolean isNullValue(Object obj) {
/* 115 */     if (obj == null)
/* 116 */       return true; 
/* 117 */     if (obj instanceof String)
/* 118 */       return (((String)obj).trim().length() == 0); 
/* 119 */     if (obj instanceof Collection) {
/* 120 */       return (((Collection)obj).size() == 0);
/*     */     }
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String nullAndTrimString(String value) {
/* 127 */     if (value == null) {
/* 128 */       value = "";
/* 129 */       return value;
/*     */     } 
/* 131 */     return value.trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setErrorvalues(Object[] arg, ArrayList<AlertMessagesVO> alertMsgArray) {
/* 136 */     in.co.forwardcontract.utility.CommonMethods commonMethods = new in.co.forwardcontract.utility.CommonMethods();
/* 137 */     AlertMessagesVO altMsg = new AlertMessagesVO();
/* 138 */     altMsg.setErrorId(commonMethods.getEmptyIfNull(arg[1]).equalsIgnoreCase("W") ? "WARNING" : "ERROR");
/* 139 */     altMsg.setErrorDesc("GENERAL");
/* 140 */     altMsg.setErrorCode(commonMethods.getEmptyIfNull(arg[3]));
/* 141 */     altMsg.setErrorDetails(commonMethods.getEmptyIfNull(arg[2]));
/* 142 */     altMsg.setErrorMsg(commonMethods.getEmptyIfNull(arg[1]).equalsIgnoreCase("W") ? "N" : "");
/* 143 */     alertMsgArray.add(altMsg);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getErrorDescFromProperties(String id) {
/* 148 */     String errorDesc = "";
/*     */     try {
/* 150 */       Properties prop = ProbUtil.getErrorPropertiesValue();
/* 151 */       errorDesc = prop.getProperty(id.toUpperCase().trim());
/* 152 */     } catch (IOException e) {
/* 153 */       e.printStackTrace();
/*     */     } 
/* 155 */     return errorDesc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String returnEmptyIfNull(String value) {
/*     */     try {
/* 161 */       if (value == null) {
/* 162 */         value = "";
/* 163 */       } else if (value instanceof String) {
/* 164 */         value = value.trim();
/*     */       } 
/* 166 */     } catch (Exception e) {
/* 167 */       e.printStackTrace();
/*     */     } 
/* 169 */     return value;
/*     */   }
/*     */   
/*     */   public static String getErrorDesc(String errorCD, String screenId) {
/* 173 */     String errorMsg = "";
/* 174 */     Connection con = null;
/* 175 */     LoggableStatement pst = null;
/* 176 */     ResultSet rs = null;
/*     */     try {
/* 178 */       con = DBConnectionUtility.getZoneConnection();
/*     */ 
/*     */       
/* 181 */       pst = new LoggableStatement(con, 
/* 182 */           "SELECT ERROR_MSG FROM ETT_SCHEDULE_ERRORCODE WHERE ERROR_CODE=? AND SCREEN_ID=?");
/* 183 */       pst.setString(1, errorCD);
/* 184 */       pst.setString(2, screenId);
/* 185 */       logger.info(pst.getQueryString());
/* 186 */       rs = pst.executeQuery();
/* 187 */       while (rs.next()) {
/* 188 */         errorMsg = rs.getString("ERROR_MSG");
/*     */       }
/* 190 */       logger.info("Error msg is " + errorMsg);
/* 191 */     } catch (Exception e) {
/* 192 */       e.printStackTrace();
/*     */     } finally {
/* 194 */       DBConnectionUtility.surrenderDB(con, (Statement)pst, rs);
/*     */     } 
/* 196 */     return errorMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isThisDateValid(String dateToValidate) {
/* 202 */     if (dateToValidate == null) {
/* 203 */       return false;
/*     */     }
/*     */     
/* 206 */     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
/* 207 */     sdf.setLenient(false);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 212 */       Date date = sdf.parse(dateToValidate);
/* 213 */       logger.info(date);
/*     */     }
/* 215 */     catch (ParseException e) {
/*     */       
/* 217 */       e.printStackTrace();
/* 218 */       logger.info("false");
/* 219 */       return false;
/*     */     } 
/*     */     
/* 222 */     logger.info("true");
/* 223 */     return true;
/*     */   }
/*     */   
/*     */   public static String getUserID() {
/* 227 */     HttpSession session = ServletActionContext.getRequest().getSession();
/* 228 */     String userID = (String)session.getAttribute("USERID");
/* 229 */     return userID;
/*     */   }
/*     */   
/*     */   public static String getCurrentDate(String dateFormat) {
/* 233 */     SimpleDateFormat df = new SimpleDateFormat(dateFormat);
/* 234 */     String currDate = df.format(new Date());
/* 235 */     return currDate;
/*     */   }
/*     */   
/*     */   public static boolean findDouble(String value) {
/* 239 */     boolean result = false;
/*     */     try {
/* 241 */       double amt = Double.parseDouble(value);
/* 242 */       logger.info("amt in findDouble-----> " + amt);
/* 243 */     } catch (NumberFormatException e) {
/* 244 */       return true;
/*     */     } 
/*     */     
/* 247 */     return result;
/*     */   }
/*     */   
/*     */   public static boolean bitwiseEqualsWithCanonicalNaN(double x, double y) {
/* 251 */     return (Double.doubleToLongBits(x) == Double.doubleToLongBits(y));
/*     */   }
/*     */   
/*     */   public static String getTIDateAddOneYear() {
/* 255 */     logger.info("Entering Method");
/* 256 */     LoggableStatement pst = null;
/* 257 */     ResultSet rs = null;
/* 258 */     Connection con = null;
/* 259 */     String tiDate = null;
/*     */ 
/*     */     
/*     */     try {
/* 263 */       con = DBConnectionUtility.getZoneConnection();
/* 264 */       String query = "SELECT TO_CHAR(TO_DATE(PROCDATE, 'dd-mm-yy')+365,'yyyy-mm-dd') as PROCDATE FROM dlyprccycl";
/* 265 */       pst = new LoggableStatement(con, query);
/* 266 */       logger.info(pst.getQueryString());
/* 267 */       rs = pst.executeQuery();
/* 268 */       while (rs.next()) {
/* 269 */         tiDate = rs.getString("PROCDATE").trim();
/*     */       }
/* 271 */     } catch (Exception exception) {
/* 272 */       exception.printStackTrace();
/*     */     } finally {
/*     */       
/* 275 */       DBConnectionUtility.surrenderDB(con, (Statement)pst, rs);
/*     */     } 
/* 277 */     logger.info("Exiting Method");
/* 278 */     return tiDate;
/*     */   }
/*     */   
/*     */   public static String removeComma(String value) {
/* 282 */     if (value == null) {
/* 283 */       value = "";
/* 284 */       return value;
/*     */     } 
/* 286 */     return value.replace(",", "");
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTiDateFormat(String date) throws ParseException {
/* 291 */     SimpleDateFormat tiFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 292 */     DateFormat jsonFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 293 */     if (date != null && !date.equalsIgnoreCase("")) {
/* 294 */       Date d1 = jsonFormat.parse(date);
/* 295 */       date = tiFormat.format(d1);
/*     */     } else {
/*     */       
/* 298 */       date = "";
/*     */     } 
/*     */     
/* 301 */     return date;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String returnZeroIfEmpty(String Value) {
/* 306 */     if (Value == "") {
/* 307 */       Value = "0.00";
/*     */     }
/* 309 */     return Value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTISystemDate() {
/* 315 */     String tiCurrDate = null;
/* 316 */     ResultSet rs = null;
/* 317 */     Connection con = null;
/* 318 */     PreparedStatement ps = null;
/*     */     
/* 320 */     String query = "SELECT to_char(PROCDATE,'dd-mm-yyyy') as PROCDATE FROM DLYPRCCYCL";
/*     */     
/*     */     try {
/* 323 */       con = DBConnectionUtility.getZoneConnection();
/* 324 */       ps = con.prepareStatement(query);
/* 325 */       rs = ps.executeQuery();
/*     */       
/* 327 */       while (rs.next()) {
/* 328 */         tiCurrDate = rs.getString(1);
/*     */       }
/*     */     }
/* 331 */     catch (SQLException e) {
/* 332 */       e.printStackTrace();
/*     */     }
/* 334 */     catch (Exception e) {
/* 335 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 338 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*     */     } 
/*     */     
/* 341 */     return tiCurrDate;
/*     */   }
/*     */   
/*     */   public static Date getTISystemSqlDate() {
/* 345 */     Date tiCurrDate = null;
/* 346 */     ResultSet rs = null;
/* 347 */     Connection con = null;
/* 348 */     PreparedStatement ps = null;
/* 349 */     String query = "SELECT to_char(PROCDATE,'yyyy-mm-dd') as PROCDATE FROM DLYPRCCYCL ";
/*     */     try {
/* 351 */       con = DBConnectionUtility.getZoneConnection();
/* 352 */       ps = con.prepareStatement(query);
/* 353 */       rs = ps.executeQuery();
/* 354 */       while (rs.next()) {
/* 355 */         tiCurrDate = rs.getDate(1);
/*     */       }
/* 357 */     } catch (SQLException e) {
/* 358 */       logger.error("SQL Exceptions! " + e.getMessage(), e);
/* 359 */       e.printStackTrace();
/* 360 */     } catch (Exception e) {
/* 361 */       logger.error("Exception! " + e.getMessage(), e);
/* 362 */       e.printStackTrace();
/*     */     } finally {
/* 364 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*     */     } 
/* 366 */     return tiCurrDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getEquivalentINRAmount(String outputCcyCode, String regfrmtAmount, String regfrmtSpotRate) {
/* 371 */     String respAmount = "";
/*     */     
/*     */     try {
/* 374 */       BigDecimal value1 = new BigDecimal(regfrmtAmount);
/* 375 */       BigDecimal value2 = new BigDecimal(regfrmtSpotRate);
/*     */       
/* 377 */       BigDecimal multipliedValue = value1.multiply(value2);
/*     */       
/* 379 */       Currency ccyNameCode = Currency.getInstance(outputCcyCode);
/* 380 */       int precision = ccyNameCode.getDefaultFractionDigits();
/* 381 */       RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;
/*     */       
/* 383 */       BigDecimal roundOffValue = null;
/*     */       
/* 385 */       roundOffValue = multipliedValue.setScale(precision, DEFAULT_ROUNDING);
/*     */       
/* 387 */       respAmount = String.valueOf(roundOffValue);
/*     */     }
/* 389 */     catch (Exception e) {
/* 390 */       System.out.println("Roundoff amount exception " + e.getMessage());
/* 391 */       e.printStackTrace();
/*     */     } 
/* 393 */     return respAmount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getH24Time() {
/* 427 */     String result = "";
/* 428 */     Date today = Calendar.getInstance().getTime();
/* 429 */     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
/* 430 */     result = formatter.format(today);
/* 431 */     return result;
/*     */   }
/*     */   
/*     */   public static String convertToStringDateFormat(String value, String fromDateFormat, String toDateFormat) {
/*     */     try {
/* 436 */       SimpleDateFormat fromFormat = new SimpleDateFormat(fromDateFormat);
/* 437 */       SimpleDateFormat toFormat = new SimpleDateFormat(toDateFormat);
/* 438 */       Date date = fromFormat.parse(value);
/* 439 */       value = toFormat.format(date);
/* 440 */       System.out.println("Expected String Date Format Value : " + value);
/* 441 */     } catch (Exception e) {
/* 442 */       e.printStackTrace();
/*     */     } 
/* 444 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Timestamp getSqlLocalDateTime() {
/* 453 */     Date date = new Date();
/* 454 */     long t = date.getTime();
/* 455 */     Timestamp sqlTimestamp = new Timestamp(t);
/* 456 */     return sqlTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void getProperties() {
/* 461 */     Connection con = null;
/* 462 */     PreparedStatement pst = null;
/* 463 */     ResultSet rs = null;
/* 464 */     System.out.println(" Entering getProperties ");
/*     */     try {
/* 466 */       con = DBConnectionUtility.getubiconnectConnection();
/* 467 */       String query = "SELECT * FROM Bridgeproperties ";
/* 468 */       pst = con.prepareStatement(query);
/* 469 */       rs = pst.executeQuery();
/* 470 */       while (rs.next()) {
/* 471 */         TBProperties.put(rs.getString("key").trim(), rs.getString("value").trim());
/*     */       }
/*     */ 
/*     */       
/* 475 */       System.out.println(" Size of Bridgeproperties From DB ---->" + TBProperties.size());
/* 476 */       System.out.println(" Entering getProperties ");
/* 477 */     } catch (Exception e) {
/* 478 */       e.printStackTrace();
/*     */     } finally {
/* 480 */       DBConnectionUtility.surrenderDB(con, pst, rs);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\CommonMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */