/*      */ package in.co.forwardcontract.service.model;
/*      */ 
/*      */ import in.co.forwardcontract.utility.DBConnectionUtility;
/*      */ import java.sql.Connection;
///*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.datatype.XMLGregorianCalendar;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateTimeUtil
/*      */ {
/*   35 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.service.model.DateTimeUtil.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) throws Exception {
/*  179 */     String dateStr = "2019-03-21";
/*      */ 
/*      */ 
/*      */     
/*  183 */     System.out.println(getSqlDateByStringDate(dateStr, "yyyy-MM-dd"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String dateStrformatChange(String dateStr, String inpformat, String outFormat) {
/*  196 */     String result = "";
/*      */     try {
/*  198 */       DateFormat df = new SimpleDateFormat(inpformat);
/*  199 */       Date startDate = df.parse(dateStr);
/*  200 */       DateFormat df2 = new SimpleDateFormat(outFormat);
/*  201 */       String startDateString2 = df2.format(startDate);
/*      */       
/*  203 */       result = startDateString2;
/*      */     }
/*  205 */     catch (ParseException e) {
/*  206 */       logger.error("DateString conversion error!! " + e.getMessage());
/*  207 */       e.printStackTrace();
/*  208 */       result = dateStr;
/*      */     } 
/*  210 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getLocalDate() {
/*  220 */     return new Date();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getLocalTime() {
/*  229 */     Calendar cal = Calendar.getInstance();
/*  230 */     cal.setTime(new Date());
/*  231 */     return cal.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCurrentDate() {
/*  240 */     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
/*  241 */     Date date = new Date();
/*      */     
/*  243 */     return dateFormat.format(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalTime(String format) {
/*  263 */     SimpleDateFormat sdf = new SimpleDateFormat(format);
/*  264 */     return sdf.format(new Date());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getTimeNow() {
/*  287 */     SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
/*  288 */     Date now = new Date();
/*  289 */     String strDate = sdf.format(now);
/*      */     
/*  291 */     return strDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateByStringDateInFormat(String dateStr, String dateFormat) {
/*  303 */     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
/*  304 */     Date date = null;
/*      */     try {
/*  306 */       date = sdf.parse(dateStr);
/*  307 */     } catch (ParseException e) {
/*  308 */       logger.error(e.getMessage(), e);
/*      */     } 
/*      */     
/*  311 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalDate(Date date) {
/*  332 */     String result = "";
/*      */     
/*  334 */     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/*  335 */     result = formatter.format(date);
/*      */     
/*  337 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalDate(Date date, String format) {
/*  348 */     String result = "";
/*      */     try {
/*  350 */       SimpleDateFormat formatter = new SimpleDateFormat(format);
/*  351 */       result = formatter.format(date);
/*  352 */     } catch (Exception e) {
/*  353 */       e.printStackTrace();
/*      */     } 
/*  355 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringYear(String outformat) {
/*  364 */     String result = "";
/*  365 */     Date today = Calendar.getInstance().getTime();
/*  366 */     SimpleDateFormat formatter = new SimpleDateFormat(outformat);
/*  367 */     result = formatter.format(today);
/*  368 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalDate(String outformat) {
/*  377 */     String result = "";
/*  378 */     Date today = Calendar.getInstance().getTime();
/*  379 */     SimpleDateFormat formatter = new SimpleDateFormat(outformat);
/*  380 */     result = formatter.format(today);
/*  381 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalDate() {
/*  390 */     String result = "";
/*  391 */     Date today = Calendar.getInstance().getTime();
/*  392 */     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
/*  393 */     result = formatter.format(today);
/*  394 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalDateInFormat(String dateformat) {
/*  404 */     String result = "";
/*  405 */     DateFormat dateFormat = new SimpleDateFormat(dateformat);
/*  406 */     Date date = new Date();
/*  407 */     result = dateFormat.format(date);
/*  408 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringDateInFormatFromCalendar(String stringdateformat) {
/*  417 */     String result = "";
/*  418 */     Date today = Calendar.getInstance().getTime();
/*  419 */     SimpleDateFormat formatter = new SimpleDateFormat(stringdateformat);
/*  420 */     result = formatter.format(today);
/*  421 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalTimeInSwiftFormat() {
/*  431 */     SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
/*  432 */     return sdf.format(new Date());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringDate(String timesatmp, String inFormat, String outFormat) {
/*  444 */     String dateOnly = "";
/*      */     
/*  446 */     DateFormat df = new SimpleDateFormat(inFormat);
/*      */     
/*  448 */     Date dt = null;
/*      */     try {
/*  450 */       dt = df.parse(timesatmp);
/*      */       
/*  452 */       DateFormat dfmt = new SimpleDateFormat(outFormat);
/*  453 */       dateOnly = dfmt.format(dt);
/*      */     }
/*  455 */     catch (ParseException e) {
/*  456 */       logger.error("Formatter exceptions " + e.getMessage());
/*  457 */       e.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/*  461 */     return dateOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringTimestamp(String timesatmp, String inFormat, String outFormat) {
/*  473 */     String timeOnly = "";
/*      */     
/*  475 */     DateFormat df = new SimpleDateFormat(inFormat);
/*      */     
/*  477 */     Date dt = null;
/*      */     try {
/*  479 */       dt = df.parse(timesatmp);
/*      */       
/*  481 */       DateFormat Outdf = new SimpleDateFormat(outFormat);
/*  482 */       timeOnly = Outdf.format(dt);
/*      */     }
/*  484 */     catch (ParseException e) {
/*  485 */       logger.error("Formatter exceptions " + e.getMessage());
/*  486 */       e.printStackTrace();
/*      */     } 
/*      */     
/*  489 */     return timeOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringDateFromTimestamp(String timesatmp) {
/*  501 */     String dateOnly = "";
/*  502 */     String fmt = "MM/dd/yyyy HH:mm:ss.SSS";
/*  503 */     DateFormat df = new SimpleDateFormat(fmt);
/*      */     
/*  505 */     Date dt = null;
/*      */     try {
/*  507 */       dt = df.parse(timesatmp);
/*  508 */     } catch (ParseException e) {
/*  509 */       e.printStackTrace();
/*      */     } 
/*      */     
/*  512 */     DateFormat tdf = new SimpleDateFormat("HH:mm:ss a");
/*  513 */     DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
/*  514 */     String timeOnly = tdf.format(dt);
/*      */     
/*  516 */     dateOnly = dfmt.format(dt);
/*      */     
/*  518 */     logger.debug(String.valueOf(dateOnly) + "\t" + timeOnly);
/*  519 */     return dateOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCurrentTimeStamp() {
/*  553 */     SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss.FF a");
/*  554 */     Date now = new Date();
/*  555 */     String strDate = sdf.format(now);
/*      */     
/*  557 */     return strDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCurrentTimeStamp2() {
/*  570 */     SimpleDateFormat sdf = new SimpleDateFormat("HHmmssMs");
/*  571 */     Date now = new Date();
/*  572 */     String strDate = sdf.format(now);
/*      */     
/*  574 */     return strDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getCurrentTimeStamp3() {
/*  579 */     int unique_id = (int)((new Date()).getTime() / 1000L % 2147483647L);
/*      */     
/*  581 */     String str = String.valueOf(unique_id);
/*  582 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getConvToTimeStamp(String inputStr) {
/*  593 */     Timestamp timestampRes = null;
/*      */     
/*      */     try {
/*  596 */       if (inputStr != null && !inputStr.isEmpty()) {
/*  597 */         timestampRes = Timestamp.valueOf(inputStr);
/*      */       }
/*      */     }
/*  600 */     catch (Exception e) {
/*  601 */       logger.error("Exception " + e.getMessage());
/*  602 */       e.printStackTrace();
/*      */     } 
/*  604 */     return timestampRes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalTime() {
/*  636 */     Date date1 = new Date();
/*  637 */     Date today = Calendar.getInstance().getTime();
/*  638 */     SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss.S");
/*  639 */     return formatter.format(today);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalTimeFi() {
/*  645 */     Date date1 = new Date();
/*  646 */     Date today = Calendar.getInstance().getTime();
/*  647 */     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
/*  648 */     return formatter.format(today);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getH24Time() {
/*  654 */     String result = "";
/*  655 */     Date today = Calendar.getInstance().getTime();
/*  656 */     SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
/*  657 */     result = formatter.format(today);
/*  658 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringLocalTimeInFormat(String timeFormat) {
/*  668 */     Date date1 = new Date();
/*  669 */     Date today = Calendar.getInstance().getTime();
/*  670 */     SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
/*  671 */     return formatter.format(today);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringTimeZoneGMT() {
/*  681 */     Calendar c = Calendar.getInstance();
/*      */     
/*  683 */     TimeZone z = c.getTimeZone();
/*  684 */     int offset = z.getRawOffset();
/*  685 */     if (z.inDaylightTime(new Date())) {
/*  686 */       offset += z.getDSTSavings();
/*      */     }
/*  688 */     int offsetHrs = offset / 1000 / 60 / 60;
/*  689 */     int offsetMins = offset / 1000 / 60 % 60;
/*      */ 
/*      */     
/*  692 */     c.add(11, -offsetHrs);
/*  693 */     c.add(12, -offsetMins);
/*      */     
/*  695 */     String formattedoffsetHrs = String.format("%02d", new Object[] { Integer.valueOf(offsetHrs) });
/*      */     
/*  697 */     String time = "GMT+" + formattedoffsetHrs + ":" + offsetMins;
/*      */     
/*  699 */     return time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDateLocalDateTime() {
/*  710 */     Calendar cal = Calendar.getInstance();
/*  711 */     cal.setTime(new Date());
/*  712 */     return cal.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String GetStringLocalDateTimeInFormat() {
/*  775 */     SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SS a Z");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  781 */     Date now = new Date();
/*  782 */     String strDate = sdf.format(now);
/*  783 */     return strDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringEpochLocalDateTime() {
/*  795 */     String epochTime = "";
/*  796 */     long unixTime = System.currentTimeMillis() / 1000L;
/*  797 */     epochTime = String.valueOf(unixTime);
/*  798 */     return epochTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringDateTimeFromEpoch(long epochFormatTime, String dateTimeFormat) {
/*  810 */     String dateTime = (new SimpleDateFormat(dateTimeFormat))
/*  811 */       .format(new Date(epochFormatTime * 1000L));
/*  812 */     return dateTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getCurrentDateAsTreasury() {
/*  824 */     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  825 */     Date date = new Date();
/*      */     
/*  827 */     return dateFormat.format(date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDateAsEndSystemFormat() {
/*  837 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
/*  838 */     String date = sdf.format(new Date());
/*      */     
/*  840 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFinSysTimestamp() {
/*  850 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
/*  851 */     String date = sdf.format(new Date());
/*      */     
/*  853 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp GetLocalTimeStamp() {
/*  864 */     Date date = new Date();
/*  865 */     Timestamp ts = new Timestamp(date.getTime());
/*  866 */     return ts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getTimestamp() {
/*  875 */     Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
/*      */     
/*  877 */     return timeStamp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getTimeStampByDateAndFormat(String dateStr, String dateFormat) {
/*  889 */     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
/*  890 */     Date date = null;
/*  891 */     Timestamp sqlTimestamp = null;
/*      */     try {
/*  893 */       date = sdf.parse(dateStr);
/*  894 */       long t = date.getTime();
/*  895 */       sqlTimestamp = new Timestamp(t);
/*  896 */     } catch (ParseException e) {
/*  897 */       logger.error("Exception! Check the logs for details", e);
/*      */     } 
/*  899 */     return sqlTimestamp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDateTimeChangeFormat(String date, String currFrmt, String ChngeFormt) {
/*  914 */     String frmtChngeDte = "";
/*  915 */     SimpleDateFormat dateCurrFormat = new SimpleDateFormat(currFrmt);
/*  916 */     SimpleDateFormat dateChngeFormat = new SimpleDateFormat(ChngeFormt);
/*      */     try {
/*  918 */       Date valueDate = dateCurrFormat.parse(date);
/*  919 */       frmtChngeDte = dateChngeFormat.format(valueDate);
/*  920 */     } catch (ParseException e) {
/*  921 */       e.printStackTrace();
/*      */     } 
/*  923 */     return frmtChngeDte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getXmlGregorianCalendarLocalTimeToString() throws Exception {
/*  996 */     String result = "";
/*  997 */     Calendar calendar = getLocalDateInXMLGregorian().toGregorianCalendar();
/*  998 */     Date date = calendar.getTime();
/*  999 */     result = (new StringBuilder(String.valueOf(date.getTime()))).toString();
/* 1000 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getLocalDateInXMLGregorian() throws Exception {
/* 1010 */     XMLGregorianCalendar result = null;
/*      */     
/*      */     try {
/* 1013 */       Date dateTime = getLocalDate();
/* 1014 */       Calendar cal = Calendar.getInstance();
/* 1015 */       cal.setTime(dateTime);
/* 1016 */       int year = cal.get(1);
/* 1017 */       int month = cal.get(2);
/* 1018 */       int date = cal.get(5);
/* 1019 */       int hour = cal.get(10);
/* 1020 */       int minute = cal.get(12);
/* 1021 */       int second = cal.get(13);
/*      */       
/* 1023 */       int millisecond = cal.get(14);
/*      */       
/* 1025 */       result = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month + 1, date, hour, minute, second, 
/* 1026 */           millisecond, -2147483648);
/*      */     }
/* 1028 */     catch (Exception e) {
/* 1029 */       logger.error(e.getMessage(), e);
/* 1030 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1033 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar dateToXMLGregorianCalendarDate(Date date) throws ParseException, DatatypeConfigurationException {
/* 1045 */     GregorianCalendar cal = new GregorianCalendar();
/* 1046 */     cal.setTime(date);
/* 1047 */     XMLGregorianCalendar xmlGC = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(1), 
/* 1048 */         cal.get(2) + 1, cal.get(5), -2147483648);
/* 1049 */     return xmlGC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getDateInXMLGregorianByDate(Date dateToBeConvert) throws Exception {
/* 1061 */     XMLGregorianCalendar result = null;
/*      */     try {
/* 1063 */       Calendar cal = Calendar.getInstance();
/* 1064 */       cal.setTime(dateToBeConvert);
/* 1065 */       int year = cal.get(1);
/* 1066 */       int month = cal.get(2);
/* 1067 */       int date = cal.get(5);
/*      */       
/* 1069 */       result = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(year, month + 1, date, 
/* 1070 */           -2147483648);
/*      */     }
/* 1072 */     catch (Exception e) {
/* 1073 */       logger.error(e.getMessage(), e);
/* 1074 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1077 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getXmlGregorianDate(String dateString) {
/* 1082 */     if (dateString == null || dateString.isEmpty()) {
/* 1083 */       return null;
/*      */     }
/* 1085 */     DateFormat formatter = null;
/* 1086 */     Date date = null;
/* 1087 */     DatatypeFactory df = null;
/* 1088 */     GregorianCalendar gc = new GregorianCalendar();
/*      */     
/* 1090 */     if (dateString.charAt(4) == '-') {
/* 1091 */       if (dateString.length() > 10) {
/* 1092 */         formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
/*      */       }
/* 1094 */       formatter = new SimpleDateFormat("yyyy-MM-dd");
/* 1095 */     } else if (dateString.charAt(4) == '|') {
/* 1096 */       formatter = new SimpleDateFormat("yyyy|MMM|E");
/* 1097 */     } else if (dateString.charAt(4) == '/') {
/* 1098 */       formatter = new SimpleDateFormat("yyyy/MM/dd");
/* 1099 */     } else if (dateString.charAt(2) == '/') {
/* 1100 */       formatter = new SimpleDateFormat("dd/MM/yyyy");
/* 1101 */     } else if (dateString.charAt(2) == '/') {
/* 1102 */       formatter = new SimpleDateFormat("MM/dd/yyyy");
/* 1103 */     } else if (dateString.charAt(2) == '-') {
/* 1104 */       formatter = new SimpleDateFormat("dd-MMM-yy");
/*      */     } else {
/* 1106 */       formatter = new SimpleDateFormat("yyyyMMdd");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1114 */       date = formatter.parse(dateString);
/*      */       
/* 1116 */       df = DatatypeFactory.newInstance();
/*      */       
/* 1118 */       gc.setTimeInMillis(date.getTime());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1123 */     catch (DatatypeConfigurationException e) {
/* 1124 */       e.printStackTrace();
/* 1125 */     } catch (ParseException e) {
/* 1126 */       e.printStackTrace();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1136 */     XMLGregorianCalendar resultGC = df.newXMLGregorianCalendar(gc);
/*      */     
/* 1138 */     resultGC.setTime(-2147483648, -2147483648, 
/* 1139 */         -2147483648);
/* 1140 */     return resultGC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
/* 1149 */     GregorianCalendar gCalendar = new GregorianCalendar();
/* 1150 */     gCalendar.setTime(date);
/* 1151 */     XMLGregorianCalendar xmlCalendar = null;
/*      */     try {
/* 1153 */       xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
/* 1154 */     } catch (DatatypeConfigurationException datatypeConfigurationException) {}
/*      */ 
/*      */ 
/*      */     
/* 1158 */     return xmlCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getDateInXMLGregorianByStringDateInFormat(String dateString, String dateInFormat) throws Exception {
/* 1171 */     XMLGregorianCalendar result = null;
/*      */     
/*      */     try {
/* 1174 */       Date dateToBeConvert = getDateByStringDateInFormat(dateString, dateInFormat);
/* 1175 */       result = getDateInXMLGregorianByDate(dateToBeConvert);
/* 1176 */     } catch (Exception e) {
/* 1177 */       logger.error(e.getMessage(), e);
/* 1178 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1181 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getDateTimeInXMLGregorianByDate(Date dateToBeConvert) throws Exception {
/* 1193 */     XMLGregorianCalendar result = null;
/*      */     try {
/* 1195 */       Calendar cal = Calendar.getInstance();
/* 1196 */       cal.setTime(dateToBeConvert);
/* 1197 */       int year = cal.get(1);
/* 1198 */       int month = cal.get(2);
/* 1199 */       int date = cal.get(5);
/* 1200 */       int hour = cal.get(10);
/* 1201 */       int minute = cal.get(12);
/* 1202 */       int second = cal.get(13);
/*      */       
/* 1204 */       result = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month + 1, date, hour, minute, second, 
/* 1205 */           -2147483648, -2147483648);
/*      */     }
/* 1207 */     catch (Exception e) {
/* 1208 */       logger.error(e.getMessage(), e);
/* 1209 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1212 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getLocalDateTimemilliInXMLGregorian() throws Exception {
/* 1223 */     XMLGregorianCalendar result = null;
/*      */     try {
/* 1225 */       Date dateTime = getLocalDate();
/* 1226 */       Calendar cal = Calendar.getInstance();
/* 1227 */       cal.setTime(dateTime);
/* 1228 */       int year = cal.get(1);
/* 1229 */       int month = cal.get(2);
/* 1230 */       int date = cal.get(5);
/* 1231 */       int hour = cal.get(10);
/* 1232 */       int minute = cal.get(12);
/* 1233 */       int second = cal.get(13);
/*      */       
/* 1235 */       int millisecond = cal.get(14);
/*      */       
/* 1237 */       result = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month + 1, date, hour, minute, second, 
/* 1238 */           millisecond, -2147483648);
/*      */     }
/* 1240 */     catch (Exception e) {
/* 1241 */       logger.error(e.getMessage(), e);
/* 1242 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1245 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getLocalDateTimeInXMLGregorian() throws Exception {
/* 1255 */     XMLGregorianCalendar result = null;
/*      */     
/*      */     try {
/* 1258 */       Date dateTime = getLocalDate();
/* 1259 */       Calendar cal = Calendar.getInstance();
/* 1260 */       cal.setTime(dateTime);
/* 1261 */       int year = cal.get(1);
/* 1262 */       int month = cal.get(2);
/* 1263 */       int date = cal.get(5);
/* 1264 */       int hour = cal.get(10);
/* 1265 */       int minute = cal.get(12);
/* 1266 */       int second = cal.get(13);
/*      */ 
/*      */       
/* 1269 */       result = DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month + 1, date, hour, minute, second, 
/* 1270 */           -2147483648, -2147483648);
/*      */     }
/* 1272 */     catch (Exception e) {
/* 1273 */       logger.error(e.getMessage(), e);
/* 1274 */       throw new Exception(e.getMessage());
/*      */     } 
/*      */     
/* 1277 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLGregorianCalendar getXMLGregorianCalendarAsEndSystemFormat() {
/* 1286 */     XMLGregorianCalendar gDateFormatted2 = null;
/*      */     try {
/* 1288 */       Calendar cal = Calendar.getInstance();
/*      */       
/* 1290 */       gDateFormatted2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(1), 
/* 1291 */           cal.get(2) + 1, cal.get(5), cal.get(11), 
/* 1292 */           cal.get(12), cal.get(13), cal.get(14), 
/* 1293 */           -2147483648);
/*      */     }
/* 1295 */     catch (DatatypeConfigurationException e) {
/* 1296 */       e.printStackTrace();
/*      */     } 
/* 1298 */     return gDateFormatted2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getSqlLocalDate() {
/* 1401 */     Date date = new Date();
/* 1402 */     long t = date.getTime();
/* 1403 */     Date sqlDate = new Date(t);
/*      */     
/* 1405 */     return sqlDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getSqlDateByStringDate(String dateStr, String dateFormat) {
/* 1418 */     Date sqlDate = null;
/*      */     try {
/* 1420 */       if (dateStr != null && !dateStr.isEmpty()) {
/* 1421 */         SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
/* 1422 */         Date date = sdf.parse(dateStr);
/* 1423 */         long t = date.getTime();
/* 1424 */         sqlDate = new Date(t);
/*      */       } else {
/* 1426 */         logger.info("InputDateString is null or empty");
/*      */       } 
/* 1428 */     } catch (ParseException e) {
/* 1429 */       logger.error("Date parse exception " + e.getMessage());
/* 1430 */       e.printStackTrace();
/*      */     } 
/* 1432 */     return sqlDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getSqlDateByStringDateInFormat(String dateStr, String dateFormat) {
/* 1445 */     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
/* 1446 */     Date date = null;
/* 1447 */     Date sqlDate = null;
/*      */     try {
/* 1449 */       date = sdf.parse(dateStr);
/* 1450 */       long t = date.getTime();
/* 1451 */       sqlDate = new Date(t);
/*      */     }
/* 1453 */     catch (ParseException e) {
/* 1454 */       logger.error("Date convert exception " + e.getMessage());
/* 1455 */       e.printStackTrace();
/*      */     } 
/* 1457 */     return sqlDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getSqlLocalDateTime() {
/* 1468 */     Date date = new Date();
/* 1469 */     long t = date.getTime();
/* 1470 */     Timestamp sqlTimestamp = new Timestamp(t);
/* 1471 */     return sqlTimestamp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getSqlLocalTimestamp() {
/* 1479 */     Calendar calendar = Calendar.getInstance();
/* 1480 */     Date now = calendar.getTime();
/* 1481 */     Timestamp currentTimestamp = new Timestamp(now.getTime());
/*      */     
/* 1483 */     return currentTimestamp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Timestamp getSqlTimeStampByStringDateInFormat(String dateStr, String dateFormat) {
/* 1494 */     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
/* 1495 */     Date date = null;
/* 1496 */     Timestamp sqlTimestamp = null;
/*      */     try {
/* 1498 */       date = sdf.parse(dateStr);
/* 1499 */       long t = date.getTime();
/* 1500 */       sqlTimestamp = new Timestamp(t);
/* 1501 */     } catch (ParseException e) {
/* 1502 */       logger.error(e.getMessage(), e);
/*      */     } 
/* 1504 */     return sqlTimestamp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getSqlDateByXMLGregorianCalendar(XMLGregorianCalendar date) {
/* 1515 */     Date sqlDt = null;
/*      */     try {
/* 1517 */       if (date != null) {
/* 1518 */         Date dt = date.toGregorianCalendar().getTime();
/* 1519 */         sqlDt = new Date(dt.getTime());
/*      */       } 
/* 1521 */     } catch (Exception e) {
/* 1522 */       logger.error("Grgorian convertion exception " + e.getMessage());
/* 1523 */       e.printStackTrace();
/*      */     } 
/* 1525 */     return sqlDt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getSqlDateByUtilDate(Date utilDate) {
/* 1536 */     Date sqlDate = null;
/*      */     try {
/* 1538 */       if (utilDate != null) {
/*      */         
/* 1540 */         sqlDate = new Date(utilDate.getTime());
/* 1541 */         logger.debug("Converted value of java.sql.Date : " + sqlDate);
/*      */       } else {
/* 1543 */         logger.debug("java.util.Date utilDate is null");
/*      */       } 
/* 1545 */     } catch (Exception e) {
/* 1546 */       logger.error("Exception " + e.getMessage());
/* 1547 */       e.printStackTrace();
/*      */     } 
/* 1549 */     return sqlDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getUtilDateBySqlDate(Date sqlDate) {
/* 1560 */     Date utilDate = null;
/*      */     try {
/* 1562 */       if (sqlDate != null) {
/*      */         
/* 1564 */         utilDate = new Date(sqlDate.getTime());
/* 1565 */         logger.debug("Converted value of java.util.Date : " + utilDate);
/*      */       } else {
/* 1567 */         logger.debug("java.sql.Date utilDate is null");
/*      */       }
/*      */     
/* 1570 */     } catch (Exception e) {
/* 1571 */       logger.error("Exception " + e.getMessage());
/* 1572 */       e.printStackTrace();
/*      */     } 
/* 1574 */     return utilDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getTISystemDate() {
/* 1579 */     String tiCurrDate = null;
/* 1580 */     ResultSet rs = null;
/* 1581 */     Connection con = null;
/* 1582 */     PreparedStatement ps = null;
/*      */ 
/*      */     
/* 1585 */     String query = "SELECT to_char(PROCDATE,'yyyy-mm-dd') as PROCDATE FROM DLYPRCCYCL ";
/*      */ 
/*      */     
/*      */     try {
/* 1589 */       con = DBConnectionUtility.getZoneConnection();
/* 1590 */       ps = con.prepareStatement(query);
/* 1591 */       rs = ps.executeQuery();
/*      */       
/* 1593 */       while (rs.next()) {
/* 1594 */         tiCurrDate = rs.getString(1);
/*      */       }
/*      */     }
/* 1597 */     catch (SQLException e) {
/* 1598 */       logger.error("SQL Exceptions! Fince_Pst Failed to insert. " + e.getMessage(), e);
/* 1599 */       e.printStackTrace();
/*      */     }
/* 1601 */     catch (Exception e) {
/* 1602 */       logger.error("Exception! Fince_Pst Failed to insert " + e.getMessage(), e);
/* 1603 */       e.printStackTrace();
/*      */     } finally {
/*      */       
/* 1606 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*      */     } 
/*      */     
/* 1609 */     return tiCurrDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date getDlyProcCyclDate() {
/* 1619 */     Date tiCurrDate = null;
/*      */     
/* 1621 */     ResultSet rs = null;
/* 1622 */     Connection con = null;
/* 1623 */     PreparedStatement ps = null;
/*      */     
/* 1625 */     String query = "SELECT PROCDATE FROM DLYPRCCYCL ";
/*      */ 
/*      */     
/*      */     try {
/* 1629 */       con = DBConnectionUtility.getZoneConnection();
/* 1630 */       ps = con.prepareStatement(query);
/* 1631 */       rs = ps.executeQuery();
/* 1632 */       while (rs.next()) {
/* 1633 */         tiCurrDate = rs.getDate("PROCDATE");
/*      */       }
/*      */     }
/* 1636 */     catch (SQLException e) {
/* 1637 */       logger.error("SQL Exceptions! " + e.getMessage(), e);
/* 1638 */       e.printStackTrace();
/*      */     }
/* 1640 */     catch (Exception e) {
/* 1641 */       logger.error("Exception! " + e.getMessage(), e);
/* 1642 */       e.printStackTrace();
/*      */     } finally {
/*      */       
/* 1645 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*      */     } 
/* 1647 */     return tiCurrDate;
/*      */   }
/*      */   
/*      */   public static Date getTISystemSqlDate() {
/* 1651 */     Date tiCurrDate = null;
/* 1652 */     ResultSet rs = null;
/* 1653 */     Connection con = null;
/* 1654 */     PreparedStatement ps = null;
/*      */     
/* 1656 */     String query = "SELECT to_char(PROCDATE,'yyyy-mm-dd') as PROCDATE FROM DLYPRCCYCL ";
/*      */     
/*      */     try {
/* 1659 */       con = DBConnectionUtility.getZoneConnection();
/* 1660 */       ps = con.prepareStatement(query);
/* 1661 */       rs = ps.executeQuery();
/* 1662 */       while (rs.next()) {
/* 1663 */         tiCurrDate = rs.getDate(1);
/*      */       }
/* 1665 */     } catch (SQLException e) {
/* 1666 */       logger.error("SQL Exceptions! " + e.getMessage(), e);
/* 1667 */       e.printStackTrace();
/* 1668 */     } catch (Exception e) {
/* 1669 */       logger.error("Exception! " + e.getMessage(), e);
/* 1670 */       e.printStackTrace();
/*      */     } finally {
/* 1672 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*      */     } 
/* 1674 */     return tiCurrDate;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Date getTISystemValueDate() {
/* 1679 */     Date tiCurrDate = null;
/* 1680 */     ResultSet rs = null;
/* 1681 */     Connection con = null;
/* 1682 */     PreparedStatement ps = null;
/*      */ 
/*      */     
/* 1685 */     String query = "SELECT to_date(PROCDATE,'dd-MON-yy') as PROCDATE FROM DLYPRCCYCL ";
/*      */ 
/*      */     
/*      */     try {
/* 1689 */       con = DBConnectionUtility.getZoneConnection();
/* 1690 */       ps = con.prepareStatement(query);
/* 1691 */       rs = ps.executeQuery();
/* 1692 */       while (rs.next()) {
/* 1693 */         tiCurrDate = rs.getDate(1);
/*      */       }
/*      */     }
/* 1696 */     catch (SQLException e) {
/* 1697 */       logger.error("SQL Exceptions! " + e.getMessage(), e);
/* 1698 */       e.printStackTrace();
/*      */     }
/* 1700 */     catch (Exception e) {
/* 1701 */       logger.error("Exception! " + e.getMessage(), e);
/* 1702 */       e.printStackTrace();
/*      */     } finally {
/*      */       
/* 1705 */       DBConnectionUtility.surrenderDB(con, ps, rs);
/*      */     } 
/*      */     
/* 1708 */     return tiCurrDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int dateDiff(String fromDate, String toDate) {
/* 1787 */     SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
/*      */     
/* 1789 */     Date d1 = null;
/* 1790 */     Date d2 = null;
/*      */     try {
/* 1792 */       d1 = format.parse(fromDate);
/* 1793 */       d2 = format.parse(toDate);
/* 1794 */     } catch (ParseException e) {
/* 1795 */       e.printStackTrace();
/*      */     } 
/*      */     
/* 1798 */     long diff1 = d1.compareTo(d2);
/* 1799 */     System.out.println("From date is small>>>>" + diff1);
/*      */     
/* 1801 */     long diff2 = d2.compareTo(d1);
/* 1802 */     System.out.println("From date is big>>>>" + diff2);
/*      */     
/* 1804 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidDate(String dateToValidate, String dateFromat) {
/* 1817 */     boolean isvalid = true;
/*      */     
/* 1819 */     if (dateToValidate != null && !dateToValidate.isEmpty()) {
/*      */       
/* 1821 */       SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
/* 1822 */       sdf.setLenient(false);
/*      */       
/*      */       try {
/* 1825 */         Date date = sdf.parse(dateToValidate);
/* 1826 */         System.out.println(date);
/* 1827 */         isvalid = true;
/*      */       }
/* 1829 */       catch (ParseException e) {
/* 1830 */         isvalid = false;
/* 1831 */         System.out.println("ParseException " + e.getMessage());
/* 1832 */         e.printStackTrace();
/*      */       } 
/*      */     } else {
/*      */       
/* 1836 */       isvalid = false;
/*      */     } 
/*      */     
/* 1839 */     return isvalid;
/*      */   }
/*      */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\DateTimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */