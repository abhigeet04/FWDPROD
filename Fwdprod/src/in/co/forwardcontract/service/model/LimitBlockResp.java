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
/*     */ 
/*     */ public class LimitBlockResp
/*     */ {
/*     */   private List<ResUserMaintLiabModLL> userMaintLiabModLL;
/*     */   private ResDrwngValAmt drwngPower;
/*     */   private ResSanctLimAmt sanctLimit;
/*     */   private String limitSuffix;
/*     */   private ResMinReqdCollPcntValue minReqdCollPcnt;
/*     */   private List<ResSharedLimitModLL_Det> sharedLimitModLL;
/*     */   private String committedFlg;
/*     */   private String limitPrefix;
/*     */   private String drwngPowerInd;
/*     */   
/*     */   public List<ResUserMaintLiabModLL> getuserMaintLiabModLL() {
/*  28 */     return this.userMaintLiabModLL;
/*     */   }
/*     */   private ResTreasuryUtilLimit treasuryUtilLimit; private String limitSanctDate; private ResDrwngPowerPcnt drwngPowerPcnt; private String limitType; private ResBaseUserMaintLiabAmt baseUserMaintLiab; private String singleTranFlg; private ResCustId custId; private String crncy; private String limitDesc;
/*     */   public void setuserMaintLiabModLL(List<ResUserMaintLiabModLL> userMaintLiabModLL) {
/*  32 */     this.userMaintLiabModLL = userMaintLiabModLL;
/*     */   }
/*     */   
/*     */   public ResDrwngValAmt getdrwngPower() {
/*  36 */     return this.drwngPower;
/*     */   }
/*     */   
/*     */   public void setdrwngPower(ResDrwngValAmt drwngPower) {
/*  40 */     this.drwngPower = drwngPower;
/*     */   }
/*     */   
/*     */   public ResSanctLimAmt getsanctLimit() {
/*  44 */     return this.sanctLimit;
/*     */   }
/*     */   
/*     */   public void setsanctLimit(ResSanctLimAmt sanctLimit) {
/*  48 */     this.sanctLimit = sanctLimit;
/*     */   }
/*     */   
/*     */   public String getlimitSuffix() {
/*  52 */     return this.limitSuffix;
/*     */   }
/*     */   
/*     */   public void setlimitSuffix(String limitSuffix) {
/*  56 */     this.limitSuffix = limitSuffix;
/*     */   }
/*     */   
/*     */   public ResMinReqdCollPcntValue getminReqdCollPcnt() {
/*  60 */     return this.minReqdCollPcnt;
/*     */   }
/*     */   
/*     */   public void setminReqdCollPcnt(ResMinReqdCollPcntValue minReqdCollPcnt) {
/*  64 */     this.minReqdCollPcnt = minReqdCollPcnt;
/*     */   }
/*     */   
/*     */   public List<ResSharedLimitModLL_Det> getsharedLimitModLL() {
/*  68 */     return this.sharedLimitModLL;
/*     */   }
/*     */   
/*     */   public void setsharedLimitModLL(List<ResSharedLimitModLL_Det> sharedLimitModLL) {
/*  72 */     this.sharedLimitModLL = sharedLimitModLL;
/*     */   }
/*     */   
/*     */   public String getcommittedFlg() {
/*  76 */     return this.committedFlg;
/*     */   }
/*     */   
/*     */   public void setcommittedFlg(String committedFlg) {
/*  80 */     this.committedFlg = committedFlg;
/*     */   }
/*     */   
/*     */   public String getlimitPrefix() {
/*  84 */     return this.limitPrefix;
/*     */   }
/*     */   
/*     */   public void setlimitPrefix(String limitPrefix) {
/*  88 */     this.limitPrefix = limitPrefix;
/*     */   }
/*     */   
/*     */   public String getdrwngPowerInd() {
/*  92 */     return this.drwngPowerInd;
/*     */   }
/*     */   
/*     */   public void setdrwngPowerInd(String drwngPowerInd) {
/*  96 */     this.drwngPowerInd = drwngPowerInd;
/*     */   }
/*     */   
/*     */   public ResTreasuryUtilLimit gettreasuryUtilLimit() {
/* 100 */     return this.treasuryUtilLimit;
/*     */   }
/*     */   
/*     */   public void settreasuryUtilLimit(ResTreasuryUtilLimit treasuryUtilLimit) {
/* 104 */     this.treasuryUtilLimit = treasuryUtilLimit;
/*     */   }
/*     */   
/*     */   public String getlimitSanctDate() {
/* 108 */     return this.limitSanctDate;
/*     */   }
/*     */   
/*     */   public void setlimitSanctDate(String limitSanctDate) {
/* 112 */     this.limitSanctDate = limitSanctDate;
/*     */   }
/*     */   
/*     */   public ResDrwngPowerPcnt getdrwngPowerPcnt() {
/* 116 */     return this.drwngPowerPcnt;
/*     */   }
/*     */   
/*     */   public void setdrwngPowerPcnt(ResDrwngPowerPcnt drwngPowerPcnt) {
/* 120 */     this.drwngPowerPcnt = drwngPowerPcnt;
/*     */   }
/*     */   
/*     */   public String getlimitType() {
/* 124 */     return this.limitType;
/*     */   }
/*     */   
/*     */   public void setlimitType(String limitType) {
/* 128 */     this.limitType = limitType;
/*     */   }
/*     */   
/*     */   public ResBaseUserMaintLiabAmt getbaseUserMaintLiab() {
/* 132 */     return this.baseUserMaintLiab;
/*     */   }
/*     */   
/*     */   public void setbaseUserMaintLiab(ResBaseUserMaintLiabAmt baseUserMaintLiab) {
/* 136 */     this.baseUserMaintLiab = baseUserMaintLiab;
/*     */   }
/*     */   
/*     */   public String getsingleTranFlg() {
/* 140 */     return this.singleTranFlg;
/*     */   }
/*     */   
/*     */   public void setsingleTranFlg(String singleTranFlg) {
/* 144 */     this.singleTranFlg = singleTranFlg;
/*     */   }
/*     */   
/*     */   public ResCustId getcustId() {
/* 148 */     return this.custId;
/*     */   }
/*     */   
/*     */   public void setcustId(ResCustId custId) {
/* 152 */     this.custId = custId;
/*     */   }
/*     */   
/*     */   public String getcrncy() {
/* 156 */     return this.crncy;
/*     */   }
/*     */   
/*     */   public void setcrncy(String crncy) {
/* 160 */     this.crncy = crncy;
/*     */   }
/*     */   
/*     */   public String getlimitDesc() {
/* 164 */     return this.limitDesc;
/*     */   }
/*     */   
/*     */   public void setlimitDesc(String limitDesc) {
/* 168 */     this.limitDesc = limitDesc;
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\service\model\LimitBlockResp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */