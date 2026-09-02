/*      */ package in.co.forwardcontract.utility;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.NClob;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.RowId;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.SQLXML;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LoggableStatement
/*      */   implements PreparedStatement
/*      */ {
/*      */   private ArrayList<String> parameterValues;
/*      */   private String sqlTemplate;
/*      */   private PreparedStatement wrappedStatement;
/*      */   
/*      */   public LoggableStatement(Connection connection, String sql) throws SQLException {
/*   62 */     this.wrappedStatement = connection.prepareStatement(sql);
/*   63 */     this.sqlTemplate = sql;
/*   64 */     this.parameterValues = new ArrayList<>();
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
/*      */   public void addBatch() throws SQLException {
/*   77 */     this.wrappedStatement.addBatch();
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
/*      */   public void addBatch(String sql) throws SQLException {
/*   93 */     this.wrappedStatement.addBatch(sql);
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
/*      */   public void cancel() throws SQLException {
/*  105 */     this.wrappedStatement.cancel();
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
/*      */   public void clearBatch() throws SQLException {
/*  119 */     this.wrappedStatement.clearBatch();
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
/*      */   public void clearParameters() throws SQLException {
/*  135 */     this.wrappedStatement.clearParameters();
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
/*      */   public void clearWarnings() throws SQLException {
/*  147 */     this.wrappedStatement.clearWarnings();
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
/*      */   public void close() throws SQLException {
/*  165 */     this.wrappedStatement.close();
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
/*      */   public boolean execute() throws SQLException {
/*  179 */     return this.wrappedStatement.execute();
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
/*      */   public boolean execute(String sql) throws SQLException {
/*  208 */     return this.wrappedStatement.execute(sql);
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
/*      */   public int[] executeBatch() throws SQLException {
/*  225 */     return this.wrappedStatement.executeBatch();
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
/*      */   public ResultSet executeQuery() throws SQLException {
/*  238 */     return this.wrappedStatement.executeQuery();
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
/*      */   public ResultSet executeQuery(String sql) throws SQLException {
/*  253 */     return this.wrappedStatement.executeQuery(sql);
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
/*      */   public int executeUpdate() throws SQLException {
/*  267 */     return this.wrappedStatement.executeUpdate();
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
/*      */   public int executeUpdate(String sql) throws SQLException {
/*  284 */     return this.wrappedStatement.executeUpdate(sql);
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
/*      */   public Connection getConnection() throws SQLException {
/*  298 */     return this.wrappedStatement.getConnection();
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
/*      */   public int getFetchDirection() throws SQLException {
/*  316 */     return this.wrappedStatement.getFetchDirection();
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
/*      */   public int getFetchSize() throws SQLException {
/*  334 */     return this.wrappedStatement.getFetchSize();
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
/*      */   public int getMaxFieldSize() throws SQLException {
/*  349 */     return this.wrappedStatement.getMaxFieldSize();
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
/*      */   public int getMaxRows() throws SQLException {
/*  361 */     return this.wrappedStatement.getMaxRows();
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
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/*  375 */     return this.wrappedStatement.getMetaData();
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
/*      */   public boolean getMoreResults() throws SQLException {
/*  393 */     return this.wrappedStatement.getMoreResults();
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
/*      */   public int getQueryTimeout() throws SQLException {
/*  405 */     return this.wrappedStatement.getQueryTimeout();
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
/*      */   public ResultSet getResultSet() throws SQLException {
/*  419 */     return this.wrappedStatement.getResultSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getResultSetConcurrency() throws SQLException {
/*  428 */     return this.wrappedStatement.getResultSetConcurrency();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getResultSetType() throws SQLException {
/*  437 */     return this.wrappedStatement.getResultSetType();
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
/*      */   public int getUpdateCount() throws SQLException {
/*  452 */     return this.wrappedStatement.getUpdateCount();
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
/*      */   public SQLWarning getWarnings() throws SQLException {
/*  472 */     return this.wrappedStatement.getWarnings();
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
/*      */   public void setArray(int i, Array x) throws SQLException {
/*  488 */     this.wrappedStatement.setArray(i, x);
/*  489 */     saveQueryParamValue(i, x);
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
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/*  515 */     this.wrappedStatement.setAsciiStream(parameterIndex, x, length);
/*  516 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/*  532 */     this.wrappedStatement.setBigDecimal(parameterIndex, x);
/*  533 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/*  559 */     this.wrappedStatement.setBinaryStream(parameterIndex, x, length);
/*  560 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setBlob(int i, Blob x) throws SQLException {
/*  576 */     this.wrappedStatement.setBlob(i, x);
/*  577 */     saveQueryParamValue(i, x);
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
/*      */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/*  593 */     this.wrappedStatement.setBoolean(parameterIndex, x);
/*  594 */     saveQueryParamValue(parameterIndex, new Boolean(x));
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
/*      */   public void setByte(int parameterIndex, byte x) throws SQLException {
/*  610 */     this.wrappedStatement.setByte(parameterIndex, x);
/*  611 */     saveQueryParamValue(parameterIndex, new Integer(x));
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
/*      */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/*  629 */     this.wrappedStatement.setBytes(parameterIndex, x);
/*  630 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/*  658 */     this.wrappedStatement.setCharacterStream(parameterIndex, reader, length);
/*  659 */     saveQueryParamValue(parameterIndex, reader);
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
/*      */   public void setClob(int i, Clob x) throws SQLException {
/*  675 */     this.wrappedStatement.setClob(i, x);
/*  676 */     saveQueryParamValue(i, x);
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
/*      */   public void setCursorName(String name) throws SQLException {
/*  701 */     this.wrappedStatement.setCursorName(name);
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
/*      */   public void setDate(int parameterIndex, Date x) throws SQLException {
/*  717 */     this.wrappedStatement.setDate(parameterIndex, x);
/*  718 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/*  744 */     this.wrappedStatement.setDate(parameterIndex, x, cal);
/*  745 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setDouble(int parameterIndex, double x) throws SQLException {
/*  761 */     this.wrappedStatement.setDouble(parameterIndex, x);
/*  762 */     saveQueryParamValue(parameterIndex, new Double(x));
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
/*      */   public void setEscapeProcessing(boolean enable) throws SQLException {
/*  781 */     this.wrappedStatement.setEscapeProcessing(enable);
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
/*      */   public void setFetchDirection(int direction) throws SQLException {
/*  803 */     this.wrappedStatement.setFetchDirection(direction);
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
/*      */   public void setFetchSize(int rows) throws SQLException {
/*  822 */     this.wrappedStatement.setFetchSize(rows);
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
/*      */   public void setFloat(int parameterIndex, float x) throws SQLException {
/*  838 */     this.wrappedStatement.setFloat(parameterIndex, x);
/*  839 */     saveQueryParamValue(parameterIndex, new Float(x));
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
/*      */   public void setInt(int parameterIndex, int x) throws SQLException {
/*  854 */     this.wrappedStatement.setInt(parameterIndex, x);
/*  855 */     saveQueryParamValue(parameterIndex, new Integer(x));
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
/*      */   public void setLong(int parameterIndex, long x) throws SQLException {
/*  871 */     this.wrappedStatement.setLong(parameterIndex, x);
/*  872 */     saveQueryParamValue(parameterIndex, new Long(x));
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
/*      */   public void setMaxFieldSize(int max) throws SQLException {
/*  889 */     this.wrappedStatement.setMaxFieldSize(max);
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
/*      */   public void setMaxRows(int max) throws SQLException {
/*  903 */     this.wrappedStatement.setMaxRows(max);
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
/*      */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/*  921 */     this.wrappedStatement.setNull(parameterIndex, sqlType);
/*  922 */     saveQueryParamValue(parameterIndex, null);
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
/*      */   public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
/*  958 */     this.wrappedStatement.setNull(paramIndex, sqlType, typeName);
/*  959 */     saveQueryParamValue(paramIndex, null);
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
/*      */   public void setObject(int parameterIndex, Object x) throws SQLException {
/*  994 */     this.wrappedStatement.setObject(parameterIndex, x);
/*  995 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
/* 1014 */     this.wrappedStatement.setObject(parameterIndex, x, targetSqlType);
/* 1015 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
/* 1055 */     this.wrappedStatement.setObject(parameterIndex, x, targetSqlType, scale);
/* 1056 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setQueryTimeout(int seconds) throws SQLException {
/* 1070 */     this.wrappedStatement.setQueryTimeout(seconds);
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
/*      */   public void setRef(int i, Ref x) throws SQLException {
/* 1086 */     this.wrappedStatement.setRef(i, x);
/* 1087 */     saveQueryParamValue(i, x);
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
/*      */   public void setShort(int parameterIndex, short x) throws SQLException {
/* 1103 */     this.wrappedStatement.setShort(parameterIndex, x);
/* 1104 */     saveQueryParamValue(parameterIndex, new Integer(x));
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
/*      */   public void setString(int parameterIndex, String x) throws SQLException {
/* 1122 */     this.wrappedStatement.setString(parameterIndex, x);
/* 1123 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setTime(int parameterIndex, Time x) throws SQLException {
/* 1139 */     this.wrappedStatement.setTime(parameterIndex, x);
/* 1140 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
/* 1166 */     this.wrappedStatement.setTime(parameterIndex, x, cal);
/* 1167 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
/* 1183 */     this.wrappedStatement.setTimestamp(parameterIndex, x);
/* 1184 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
/* 1210 */     this.wrappedStatement.setTimestamp(parameterIndex, x, cal);
/* 1211 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 1241 */     this.wrappedStatement.setUnicodeStream(parameterIndex, x, length);
/* 1242 */     saveQueryParamValue(parameterIndex, x);
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
/*      */   public String getQueryString() {
/* 1256 */     StringBuffer buf = new StringBuffer();
/* 1257 */     int qMarkCount = 0;
/* 1258 */     StringTokenizer tok = new StringTokenizer(String.valueOf(this.sqlTemplate) + " ", "?");
/* 1259 */     while (tok.hasMoreTokens()) {
/* 1260 */       String oneChunk = tok.nextToken();
/* 1261 */       buf.append(oneChunk);
/*      */       try {
/*      */         Object value;
/* 1264 */         if (this.parameterValues.size() > 1 + qMarkCount) {
/*      */           
/* 1266 */           value = this.parameterValues.get(1 + qMarkCount++);
/*      */         }
/* 1268 */         else if (tok.hasMoreTokens()) {
/* 1269 */           value = null;
/*      */         } else {
/* 1271 */           value = "";
/*      */         } 
/*      */         
/* 1274 */         buf.append((String)value);
/* 1275 */       } catch (Throwable e) {
/* 1276 */         buf.append("ERROR WHEN PRODUCING QUERY STRING FOR LOG." + 
/* 1277 */             e.toString());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1282 */     return buf.toString().trim();
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
/*      */   private void saveQueryParamValue(int position, Object obj) {
/*      */     String strValue;
/* 1296 */     if (obj instanceof String || obj instanceof java.util.Date) {
/*      */       
/* 1298 */       strValue = "'" + obj + "'";
/*      */     }
/* 1300 */     else if (obj == null) {
/*      */       
/* 1302 */       strValue = "null";
/*      */     } else {
/*      */       
/* 1305 */       strValue = obj.toString();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     while (position >= this.parameterValues.size()) {
/* 1311 */       this.parameterValues.add(null);
/*      */     }
/*      */     
/* 1314 */     this.parameterValues.set(position, strValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public ParameterMetaData getParameterMetaData() throws SQLException {
/* 1319 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlob(int arg0, InputStream arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlob(int arg0, InputStream arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterStream(int arg0, Reader arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClob(int arg0, Reader arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNClob(int arg0, NClob arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNClob(int arg0, Reader arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNString(int arg0, String arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowId(int arg0, RowId arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURL(int arg0, URL arg1) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(String arg0, int arg1) throws SQLException {
/* 1405 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute(String arg0, int[] arg1) throws SQLException {
/* 1410 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean execute(String arg0, String[] arg1) throws SQLException {
/* 1415 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int executeUpdate(String arg0, int arg1) throws SQLException {
/* 1420 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int executeUpdate(String arg0, int[] arg1) throws SQLException {
/* 1425 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int executeUpdate(String arg0, String[] arg1) throws SQLException {
/* 1430 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResultSet getGeneratedKeys() throws SQLException {
/* 1435 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getMoreResults(int arg0) throws SQLException {
/* 1440 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getResultSetHoldability() throws SQLException {
/* 1445 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isClosed() throws SQLException {
/* 1450 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPoolable() throws SQLException {
/* 1455 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPoolable(boolean arg0) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWrapperFor(Class<?> arg0) throws SQLException {
/* 1465 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T unwrap(Class<T> arg0) throws SQLException {
/* 1471 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeOnCompletion() throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCloseOnCompletion() throws SQLException {
/* 1481 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontrac\\utility\LoggableStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */