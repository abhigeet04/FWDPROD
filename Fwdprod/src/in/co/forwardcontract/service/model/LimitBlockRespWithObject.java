/*     */ package in.co.forwardcontract.service.model;
/*     */ 
/*     */ import in.co.forwardcontract.service.model.ResBaseUserMaintLiabAmt;
/*     */ import in.co.forwardcontract.service.model.ResCustId;
/*     */ import in.co.forwardcontract.service.model.ResDrwngPowerPcnt;
/*     */ import in.co.forwardcontract.service.model.ResDrwngValAmt;
/*     */ import in.co.forwardcontract.service.model.ResMinReqdCollPcntValue;
/*     */ import in.co.forwardcontract.service.model.ResSanctLimAmt;
/*     */ import in.co.forwardcontract.service.model.ResSharedLimitModLL_Det;
/*     */ import in.co.forwardcontract.service.model.ResTreasuryUtilLimit;
/*     */ import in.co.forwardcontract.service.model.ResUserMaintLiabModLL;
/*     */ import java.util.List;
/*     */ 
/*     */ public class LimitBlockRespWithObject
/*     */ {
/*     */   private ResUserMaintLiabModLL userMaintLiabModLL;
/*     */   private ResDrwngValAmt drwngPower;
/*     */   private ResSanctLimAmt sanctLimit;
/*     */   private String limitSuffix;
/*     */   private ResMinReqdCollPcntValue minReqdCollPcnt;
/*     */   private List<ResSharedLimitModLL_Det> sharedLimitModLL;
/*     */   private String committedFlg;
/*     */   private String limitPrefix;
/*     */   private String drwngPowerInd;
/*     */   
/*     */   public ResUserMaintLiabModLL getuserMaintLiabModLL() {
/*  27 */     return this.userMaintLiabModLL;
/*     */   }
/*     */   private ResTreasuryUtilLimit treasuryUtilLimit; private String limitSanctDate; private ResDrwngPowerPcnt drwngPowerPcnt; private String limitType; private ResBaseUserMaintLiabAmt baseUserMaintLiab; private String singleTranFlg; private ResCustId custId; private String crncy; private String limitDesc;
/*     */   public void setuserMaintLiabModLL(ResUserMaintLiabModLL userMaintLiabModLL) {
/*  31 */     this.userMaintLiabModLL = userMaintLiabModLL;
/*     */   }
/*     */   
/*     */   public ResDrwngValAmt getdrwngPower() {
/*  35 */     return this.drwngPower;
/*     */   }
/*     */   
/*     */   public void setdrwngPower(ResDrwngValAmt drwngPower) {
/*  39 */     this.drwngPower = drwngPower;
/*     */   }
/*     */   
/*     */   public ResSanctLimAmt getsanctLimit() {
/*  43 */     return this.sanctLimit;
/*     */   }
/*     */   
/*     */   public void setsanctLimit(ResSanctLimAmt sanctLimit) {
/*  47 */     this.sanctLimit = sanctLimit;
/*     */   }
/*     */   
/*     */   public String getlimitSuffix() {
/*  51 */     return this.limitSuffix;
/*     */   }
/*     */   
/*     */   public void setlimitSuffix(String limitSuffix) {
/*  55 */     this.limitSuffix = limitSuffix;
/*     */   }
/*     */   
/*     */   public ResMinReqdCollPcntValue getminReqdCollPcnt() {
/*  59 */     return this.minReqdCollPcnt;
/*     */   }
/*     */   
/*     */   public void setminReqdCollPcnt(ResMinReqdCollPcntValue minReqdCollPcnt) {
/*  63 */     this.minReqdCollPcnt = minReqdCollPcnt;
/*     */   }
/*     */   
/*     */   public List<ResSharedLimitModLL_Det> getsharedLimitModLL() {
/*  67 */     return this.sharedLimitModLL;
/*     */   }
/*     */   
/*     */   public void setsharedLimitModLL(List<ResSharedLimitModLL_Det> sharedLimitModLL) {
/*  71 */     this.sharedLimitModLL = sharedLimitModLL;
/*     */   }
/*     */   
/*     */   public String getcommittedFlg() {
/*  75 */     return this.committedFlg;
/*     */   }
/*     */   
/*     */   public void setcommittedFlg(String committedFlg) {
/*  79 */     this.committedFlg = committedFlg;
/*     */   }
/*     */   
/*     */   public String getlimitPrefix() {
/*  83 */     return this.limitPrefix;
/*     */   }
/*     */   
/*     */   public void setlimitPrefix(String limitPrefix) {
/*  87 */     this.limitPrefix = limitPrefix;
/*     */   }
/*     */   
/*     */   public String getdrwngPowerInd() {
/*  91 */     return this.drwngPowerInd;
/*     */   }
/*     */   
/*     */   public void setdrwngPowerInd(String drwngPowerInd) {
/*  95 */     this.drwngPowerInd = drwngPowerInd;
/*     */   }
/*     */   
/*     */   public ResTreasuryUtilLimit gettreasuryUtilLimit() {
/*  99 */     return this.treasuryUtilLimit;
/*     */   }
/*     */   
/*     */   public void settreasuryUtilLimit(ResTreasuryUtilLimit treasuryUtilLimit) {
/* 103 */     this.treasuryUtilLimit = treasuryUtilLimit;
/*     */   }
/*     */   
/*     */   public String getlimitSanctDate() {
/* 107 */     return this.limitSanctDate;
/*     */   }
/*     */   
/*     */   public void setlimitSanctDate(String limitSanctDate) {
/* 111 */     this.limitSanctDate = limitSanctDate;
/*     */   }
/*     */   
/*     */   public ResDrwngPowerPcnt getdrwngPowerPcnt() {
/* 115 */     return this.drwngPowerPcnt;
/*     */   }
/*     */   
/*     */   public void setdrwngPowerPcnt(ResDrwngPowerPcnt drwngPowerPcnt) {
/* 119 */     this.drwngPowerPcnt = drwngPowerPcnt;
/*     */   }
/*     */   
/*     */   public String getlimitType() {
/* 123 */     return this.limitType;
/*     */   }
/*     */   
/*     */   public void setlimitType(String limitType) {
/* 127 */     this.limitType = limitType;
/*     */   }
/*     */   
/*     */   public ResBaseUserMaintLiabAmt getbaseUserMaintLiab() {
/* 131 */     return this.baseUserMaintLiab;
/*     */   }
/*     */   
/*     */   public void setbaseUserMaintLiab(ResBaseUserMaintLiabAmt baseUserMaintLiab) {
/* 135 */     this.baseUserMaintLiab = baseUserMaintLiab;
/*     */   }
/*     */   
/*     */   public String getsingleTranFlg() {
/* 139 */     return this.singleTranFlg;
/*     */   }
/*     */   
/*     */   public void setsingleTranFlg(String singleTranFlg) {
/* 143 */     this.singleTranFlg = singleTranFlg;
/*     */   }
/*     */   
/*     */   public ResCustId getcustId() {
/* 147 */     return this.custId;
/*     */   }
/*     */   
/*     */   public void setcustId(ResCustId custId) {
/* 151 */     this.custId = custId;
/*     */   }
/*     */   
/*     */   public String getcrncy() {
/* 155 */     return this.crncy;
/*     */   }
/*     */   
/*     */   public void setcrncy(String crncy) {
/* 159 */     this.crncy = crncy;
/*     */   }
/*     */   
/*     */   public String getlimitDesc() {
/* 163 */     return this.limitDesc;
/*     */   }
/*     */   
/*     */   public void setlimitDesc(String limitDesc) {
/* 167 */     this.limitDesc = limitDesc;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitBlockRespWithObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */