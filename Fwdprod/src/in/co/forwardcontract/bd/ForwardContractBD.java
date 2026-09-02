/*     */ package in.co.forwardcontract.bd;
/*     */ 
/*     */ import in.co.forwardcontract.bd.BaseBusinessDelegate;
/*     */ import in.co.forwardcontract.bd.exception.BusinessException;
/*     */ import in.co.forwardcontract.dao.ForwardContractDAO;
import in.co.forwardcontract.dao.exception.DAOException;
/*     */ import in.co.forwardcontract.utility.ActionConstants;
/*     */ import in.co.forwardcontract.vo.ForwardContractVO;
/*     */ import in.co.forwardcontract.vo.StaticDataVO;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ForwardContractBD
/*     */   extends BaseBusinessDelegate implements ActionConstants {
/*  15 */   private static final Logger logger = LogManager.getLogger(in.co.forwardcontract.bd.ForwardContractBD.class);
/*     */   
/*     */   static in.co.forwardcontract.bd.ForwardContractBD bd;
/*  18 */   ForwardContractVO fwdContractVO = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static in.co.forwardcontract.bd.ForwardContractBD getBD() {
/*  25 */     if (bd == null) {
/*  26 */       bd = new in.co.forwardcontract.bd.ForwardContractBD();
/*     */     }
/*  28 */     return bd;
/*     */   }
/*     */   
/*     */   public String getSessionUser(String userName) {
/*  32 */     logger.info("Entering Method");
/*  33 */     ForwardContractDAO dao = null;
/*  34 */     String sesID = "";
/*     */     try {
/*  36 */       dao = ForwardContractDAO.getDAO();
/*  37 */       sesID = dao.getSessionUserID(userName);
/*  38 */     } catch (Exception exception) {
/*  39 */       exception.printStackTrace();
/*     */     } 
/*  41 */     logger.info("Exiting Method");
/*  42 */     return sesID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO fetchDependentTreasuryDetails(ForwardContractVO fwdContractVO) throws BusinessException {
/*  48 */     logger.info("Entering Method");
/*  49 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/*  51 */       fwdContractDAO = ForwardContractDAO.getDAO();
/*  52 */       fwdContractVO = fwdContractDAO.fetchDependentTreasuryDetails(fwdContractVO);
/*  53 */     } catch (Exception exception) {
/*  54 */       throwBDException(exception);
/*     */     } 
/*  56 */     logger.info("Exiting Method");
/*  57 */     return fwdContractVO;
/*     */   }
/*     */   
/*     */   public ForwardContractVO fetchDependentCancelTreasuryDetails(ForwardContractVO fwdContractVO) throws BusinessException {
/*  61 */     logger.info("Entering Method");
/*  62 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/*  64 */       fwdContractDAO = ForwardContractDAO.getDAO();
/*  65 */       fwdContractVO = fwdContractDAO.fetchDependentCancelTreasuryDetails(fwdContractVO);
/*  66 */     } catch (Exception exception) {
/*  67 */       throwBDException(exception);
/*     */     } 
/*  69 */     logger.info("Exiting Method");
/*  70 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO fetchParticularFwdContractDetails(String id, String fwdContractNo) throws BusinessException {
/*  76 */     logger.info("Entering Method");
/*  77 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/*  79 */       this.fwdContractVO = new ForwardContractVO();
/*  80 */       fwdContractDAO = ForwardContractDAO.getDAO();
/*  81 */       this.fwdContractVO = fwdContractDAO.fetchParticularFwdContractDetails(id, fwdContractNo);
/*  82 */     } catch (Exception exception) {
/*  83 */       throwBDException(exception);
/*     */     } 
/*  85 */     logger.info("Exiting Method");
/*  86 */     return this.fwdContractVO;
/*     */   }
/*     */   
/*     */   public ForwardContractVO fetchParticularFwdContractDetailstoModify(String id, String fwdContractNo) throws BusinessException {
/*  90 */     logger.info("Entering Method");
/*  91 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/*  93 */       this.fwdContractVO = new ForwardContractVO();
/*  94 */       fwdContractDAO = ForwardContractDAO.getDAO();
/*  95 */       this.fwdContractVO = fwdContractDAO.fetchParticularFwdContractDetailstoModify(id, fwdContractNo);
/*  96 */     } catch (Exception exception) {
/*  97 */       throwBDException(exception);
/*     */     } 
/*  99 */     logger.info("Exiting Method");
/* 100 */     return this.fwdContractVO;
/*     */   }

//
	/**
	 * BD method for VIEW (read-only) screen. Works for both FWCCANCEL and FWCUTIL.
	 */
	public ForwardContractVO fetchParticularCancelFwdContractDetailsWithoutRateView(String id, String fwdContractNo)
	{
		ForwardContractDAO fwdContractDAO = null;
		logger.info("Entering Method fetchParticularCancelFwdContractDetailsWithoutRate BD");
		
		try {
			this.fwdContractVO = new ForwardContractVO();
			fwdContractDAO = ForwardContractDAO.getDAO();
			this.fwdContractVO  = fwdContractDAO.fetchParticularCancelFwdContractDetailsWithoutRateView(id, fwdContractNo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Exiting Method fetchParticularCancelFwdContractDetailsWithoutRate BD");
		return this.fwdContractVO;
	}

	/**
	 * BD method for MODIFY screen. Works for both FWCCANCEL and FWCUTIL.
	 */
	public ForwardContractVO fetchParticularFwdContractDetailstoModifyWithoutRate(String id, String fwdContractNo)
	{
		ForwardContractDAO fwdContractDAO = null;
		logger.info("Entering Method fetchParticularFwdContractDetailstoModifyWithoutRate BD");
		try {
			this.fwdContractVO = new ForwardContractVO();
			fwdContractDAO = ForwardContractDAO.getDAO();
			this.fwdContractVO = fwdContractDAO.fetchParticularFwdContractDetailstoModifyWithoutRate(id, fwdContractNo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Exiting Method fetchParticularFwdContractDetailstoModifyWithoutRate BD");
		return this.fwdContractVO;
	}
//

/*     */ 
/*     */   
/*     */   public ForwardContractVO fetchParticularCancelFwdContractDetails(String id, String fwdContractNo) throws BusinessException {
/* 105 */     logger.info("Entering Method");
/* 106 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/* 108 */       this.fwdContractVO = new ForwardContractVO();
/* 109 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 110 */       this.fwdContractVO = fwdContractDAO.fetchParticularCancelFwdContractDetails(id, fwdContractNo);
/* 111 */     } catch (Exception exception) {
/* 112 */       throwBDException(exception);
/*     */     } 
/* 114 */     logger.info("Exiting Method");
/* 115 */     return this.fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO fetchFWCReferenceDetails(String fwdContractNo) throws BusinessException {
/* 122 */     logger.info("Entering Method");
/* 123 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/* 125 */       this.fwdContractVO = new ForwardContractVO();
/* 126 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 127 */       this.fwdContractVO = fwdContractDAO.fetchFWCReferenceDetails(fwdContractNo);
/* 128 */     } catch (Exception exception) {
/* 129 */       throwBDException(exception);
/*     */     } 
/* 131 */     logger.info("Exiting Method");
/* 132 */     return this.fwdContractVO;
/*     */   }

// NEW METHOD BY ABHISHEK

/*     */   public ForwardContractVO fetchFWCCancelDetailsWithoutRate(String fwdContractNo) throws BusinessException {
/* 122 */     logger.info("Entering Method");
/* 123 */     ForwardContractDAO fwdContractDAO = null;
/*     */     try {
/* 125 */       this.fwdContractVO = new ForwardContractVO();
/* 126 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 127 */       this.fwdContractVO = fwdContractDAO.fetchFWCCancelDetailsWithoutRate(fwdContractNo);
/* 128 */     } catch (Exception exception) {
/* 129 */       throwBDException(exception);
/*     */     } 
/* 131 */     logger.info("Exiting Method");
/* 132 */     return this.fwdContractVO;
/*     */   }


/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO approveFWC(ForwardContractVO fwdContractVO, String category) throws BusinessException {
/* 138 */     logger.info("Entering Method");
/* 139 */     ForwardContractDAO fwdContractDAO = null;
/*     */     
/*     */     try {
/* 142 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 143 */       fwdContractVO = fwdContractDAO.approveFwdContractDetails(fwdContractVO, category);
/* 144 */     } catch (Exception exception) {
/* 145 */       throwBDException(exception);
/*     */     } 
/* 147 */     logger.info("Exiting Method");
/* 148 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForwardContractVO rejectFWC(ForwardContractVO fwdContractVO, String category) throws BusinessException {
/* 153 */     logger.info("Entering Method");
/* 154 */     ForwardContractDAO fwdContractDAO = null;
/*     */     
/*     */     try {
/* 157 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 158 */       fwdContractVO = fwdContractDAO.rejectFwdContractDetails(fwdContractVO, category);
/* 159 */     } catch (Exception exception) {
/* 160 */       throwBDException(exception);
/*     */     } 
/* 162 */     logger.info("Exiting Method");
/* 163 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForwardContractVO deleteFWC(ForwardContractVO fwdContractVO, String category) throws BusinessException {
/* 168 */     logger.info("Entering Method");
/* 169 */     ForwardContractDAO fwdContractDAO = null;
/*     */     
/*     */     try {
/* 172 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 173 */       fwdContractVO = fwdContractDAO.deleteFwdContractDetails(fwdContractVO, category);
/* 174 */     } catch (Exception exception) {
/* 175 */       throwBDException(exception);
/*     */     } 
/* 177 */     logger.info("Exiting Method");
/* 178 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRole(ForwardContractVO fwdContractVO) throws BusinessException {
/* 185 */     logger.info("Entering Method");
/* 186 */     ForwardContractDAO fwdContractDAO = null;
/* 187 */     String result = null;
/*     */     try {
/* 189 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 190 */       result = fwdContractDAO.getRole(fwdContractVO);
/* 191 */     } catch (Exception exception) {
/* 192 */       throwBDException(exception);
/*     */     } 
/* 194 */     logger.info("Exiting Method");
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int checkLoginedUserType(ForwardContractVO fwdContractVO) throws BusinessException {
/* 200 */     logger.info("Entering Method");
/* 201 */     ForwardContractDAO fwdContractDAO = null;
/* 202 */     int result = 0;
/*     */     try {
/* 204 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 205 */       result = fwdContractDAO.checkLoginedUserType(fwdContractVO);
/* 206 */     } catch (Exception exception) {
/* 207 */       throwBDException(exception);
/*     */     } 
/* 209 */     logger.info("Exiting Method");
/* 210 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int checkLoginedUserType1(String user, String teamName) throws BusinessException {
/* 215 */     logger.info("Entering Method");
/* 216 */     ForwardContractDAO fwdContractDAO = null;
/* 217 */     int result = 0;
/*     */     try {
/* 219 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 220 */       result = fwdContractDAO.checkLoginedUserType1(user, teamName);
/* 221 */     } catch (Exception exception) {
/* 222 */       throwBDException(exception);
/*     */     } 
/* 224 */     logger.info("Exiting Method");
/* 225 */     return result;
/*     */   }
/*     */   public ArrayList<StaticDataVO> getCustomerList(ArrayList<StaticDataVO> customerList) {
/* 228 */     logger.info("Entering Method");
/* 229 */     ForwardContractDAO dao = null;
/*     */     try {
/* 231 */       dao = ForwardContractDAO.getDAO();
/* 232 */       customerList = dao.customerSearch(customerList);
/*     */     }
/* 234 */     catch (Exception exception) {
/* 235 */       exception.printStackTrace();
/*     */     } 
/* 237 */     logger.info("Exiting Method");
/* 238 */     return customerList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> getAccountList(ArrayList<StaticDataVO> accountList) {
/* 242 */     logger.info("Entering Method");
/* 243 */     ForwardContractDAO dao = null;
/*     */     try {
/* 245 */       dao = ForwardContractDAO.getDAO();
/* 246 */       accountList = dao.accountSearch(accountList);
/*     */     }
/* 248 */     catch (Exception exception) {
/* 249 */       exception.printStackTrace();
/*     */     } 
/* 251 */     logger.info("Exiting Method");
/* 252 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> getBranchList(ArrayList<StaticDataVO> accountList) {
/* 256 */     logger.info("Entering Method");
/* 257 */     ForwardContractDAO dao = null;
/*     */     try {
/* 259 */       dao = ForwardContractDAO.getDAO();
/* 260 */       accountList = dao.branchSearch(accountList);
/*     */     }
/* 262 */     catch (Exception exception) {
/* 263 */       exception.printStackTrace();
/*     */     } 
/* 265 */     logger.info("Exiting Method");
/* 266 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> getCurrencyList(ArrayList<StaticDataVO> accountList) {
/* 270 */     logger.info("Entering Method");
/* 271 */     ForwardContractDAO dao = null;
/*     */     try {
/* 273 */       dao = ForwardContractDAO.getDAO();
/* 274 */       accountList = dao.currencySearch(accountList);
/*     */     }
/* 276 */     catch (Exception exception) {
/* 277 */       exception.printStackTrace();
/*     */     } 
/* 279 */     logger.info("Exiting Method");
/* 280 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> getTreasuryList(ArrayList<StaticDataVO> treasuryList) {
/* 284 */     logger.info("Entering Method");
/* 285 */     ForwardContractDAO dao = null;
/*     */     try {
/* 287 */       dao = ForwardContractDAO.getDAO();
/* 288 */       treasuryList = dao.fetchTreasuryDetails(treasuryList);
/*     */     }
/* 290 */     catch (Exception exception) {
/* 291 */       exception.printStackTrace();
/*     */     } 
/* 293 */     logger.info("Exiting Method");
/* 294 */     return treasuryList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> fetchFwdContractList(ArrayList<StaticDataVO> fwdContractList) {
/* 298 */     logger.info("Entering Method");
/* 299 */     ForwardContractDAO dao = null;
/*     */     try {
/* 301 */       dao = ForwardContractDAO.getDAO();
/* 302 */       fwdContractList = dao.fetchFwdContractList(fwdContractList);
/*     */     }
/* 304 */     catch (Exception exception) {
/* 305 */       exception.printStackTrace();
/*     */     } 
/* 307 */     logger.info("Exiting Method");
/* 308 */     return fwdContractList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> getLimitList(String customerID) {
/* 312 */     logger.info("Entering Method");
/* 313 */     ForwardContractDAO dao = null;
/* 314 */     ArrayList<StaticDataVO> limitList = new ArrayList<>();
/*     */     try {
/* 316 */       dao = ForwardContractDAO.getDAO();
/* 317 */       limitList = dao.fetchLimitDetails(customerID);
/*     */     }
/* 319 */     catch (Exception exception) {
/* 320 */       exception.printStackTrace();
/*     */     } 
/* 322 */     logger.info("Exiting Method");
/* 323 */     return limitList;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<StaticDataVO> filterCustomer(StaticDataVO cusDataVo, ArrayList<StaticDataVO> customerList) {
/* 328 */     logger.info("Entering Method");
/* 329 */     ForwardContractDAO dao = null;
/*     */     try {
/* 331 */       dao = ForwardContractDAO.getDAO();
/* 332 */       customerList = dao.filterCusList(customerList, cusDataVo);
/*     */     }
/* 334 */     catch (Exception exception) {
/* 335 */       exception.printStackTrace();
/*     */     } 
/* 337 */     logger.info("Exiting Method");
/* 338 */     return customerList;
/*     */   }
/*     */   public ArrayList<StaticDataVO> filterAccount(StaticDataVO acctDataVO, ArrayList<StaticDataVO> accountList) {
/* 341 */     logger.info("Entering Method");
/* 342 */     ForwardContractDAO dao = null;
/*     */     try {
/* 344 */       dao = ForwardContractDAO.getDAO();
/* 345 */       accountList = dao.filterAcctList(accountList, acctDataVO);
/*     */     }
/* 347 */     catch (Exception exception) {
/* 348 */       exception.printStackTrace();
/*     */     } 
/* 350 */     logger.info("Exiting Method");
/* 351 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> filterBranch(StaticDataVO acctDataVO, ArrayList<StaticDataVO> accountList) {
/* 355 */     logger.info("Entering Method");
/* 356 */     ForwardContractDAO dao = null;
/*     */     try {
/* 358 */       dao = ForwardContractDAO.getDAO();
/* 359 */       accountList = dao.filterBranchList(accountList, acctDataVO);
/*     */     }
/* 361 */     catch (Exception exception) {
/* 362 */       exception.printStackTrace();
/*     */     } 
/* 364 */     logger.info("Exiting Method");
/* 365 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> filterCurrency(StaticDataVO acctDataVO, ArrayList<StaticDataVO> accountList) {
/* 369 */     logger.info("Entering Method");
/* 370 */     ForwardContractDAO dao = null;
/*     */     try {
/* 372 */       dao = ForwardContractDAO.getDAO();
/* 373 */       accountList = dao.filterCurrencyList(accountList, acctDataVO);
/*     */     }
/* 375 */     catch (Exception exception) {
/* 376 */       exception.printStackTrace();
/*     */     } 
/* 378 */     logger.info("Exiting Method");
/* 379 */     return accountList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> filterTreasuryDetails(StaticDataVO treasuryDataVO, ArrayList<StaticDataVO> treasuryList) {
/* 383 */     logger.info("Entering Method");
/* 384 */     ForwardContractDAO dao = null;
/*     */     try {
/* 386 */       dao = ForwardContractDAO.getDAO();
/* 387 */       treasuryList = dao.filterTreasuryList(treasuryDataVO, treasuryList);
/*     */     }
/* 389 */     catch (Exception exception) {
/* 390 */       exception.printStackTrace();
/*     */     } 
/* 392 */     logger.info("Exiting Method");
/* 393 */     return treasuryList;
/*     */   }
/*     */   
/*     */   public ArrayList<StaticDataVO> filterfwdContractDetails(StaticDataVO fwdContractData, ArrayList<StaticDataVO> treasuryList) {
/* 397 */     logger.info("Entering Method");
/* 398 */     ForwardContractDAO dao = null;
/*     */     try {
/* 400 */       dao = ForwardContractDAO.getDAO();
/* 401 */       treasuryList = dao.filterFwdContractList(fwdContractData, treasuryList);
/*     */     }
/* 403 */     catch (Exception exception) {
/* 404 */       exception.printStackTrace();
/*     */     } 
/* 406 */     logger.info("Exiting Method");
/* 407 */     return treasuryList;
/*     */   }
/*     */   
/*     */   public ForwardContractVO saveBookingDetails(ForwardContractVO fwdContractVO) {
/* 411 */     ForwardContractDAO dao = null;
/*     */     try {
/* 413 */       dao = ForwardContractDAO.getDAO();
/* 414 */       dao.saveBookingDetails(fwdContractVO);
/*     */     }
/* 416 */     catch (Exception e) {
/* 417 */       e.printStackTrace();
/*     */     } 
/* 419 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO insertBookingDetails(ForwardContractVO fwdContractVO, String category, String status, String action) {
/* 425 */     ForwardContractDAO dao = null;
/*     */     try {
/* 427 */       dao = ForwardContractDAO.getDAO();
/* 428 */       dao.insertBookingDetails(fwdContractVO, category, status, action);
/*     */     }
/* 430 */     catch (Exception e) {
/* 431 */       e.printStackTrace();
/*     */     } 
/* 433 */     return fwdContractVO;
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
/*     */   public ForwardContractVO updateFwdBookingContractDetails(ForwardContractVO fwdContractVO, String category, String status, String action) {
/* 452 */     ForwardContractDAO dao = null;
/*     */     try {
/* 454 */       dao = ForwardContractDAO.getDAO();
/* 455 */       dao.updateFwdBookingContractDetails(fwdContractVO, category, status, action);
/*     */     }
/* 457 */     catch (Exception e) {
/* 458 */       e.printStackTrace();
/*     */     } 
/* 460 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForwardContractVO updateFwdCancelContractDetails(ForwardContractVO fwdContractVO, String category, String status, String action) {
/* 465 */     ForwardContractDAO dao = null;
/*     */     try {
/* 467 */       dao = ForwardContractDAO.getDAO();
/* 468 */       dao.updateFwdCancelContractDetails(fwdContractVO, category, status, action);
/*     */     }
/* 470 */     catch (Exception e) {
/* 471 */       e.printStackTrace();
/*     */     } 
/* 473 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO validateBookingDetails(ForwardContractVO fwdContractVO) {
/* 479 */     logger.info("Entering Method");
/* 480 */     ForwardContractDAO dao = null;
/*     */     try {
/* 482 */       dao = ForwardContractDAO.getDAO();
/* 483 */       fwdContractVO = dao.validateBookingDetails(fwdContractVO);
/*     */     }
/* 485 */     catch (Exception exception) {
/* 486 */       exception.printStackTrace();
/*     */     } 
/* 488 */     logger.info("Exiting Method");
/* 489 */     return fwdContractVO;
/*     */   }
/*     */   
/*     */   public ForwardContractVO generateFWCPostings(ForwardContractVO fwdContractVO) {
/* 493 */     logger.info("Entering Method");
/* 494 */     ForwardContractDAO dao = null;
/*     */     try {
/* 496 */       dao = ForwardContractDAO.getDAO();
/* 497 */       fwdContractVO = dao.generateFWCPostings(fwdContractVO);
/*     */     }
/* 499 */     catch (Exception exception) {
/* 500 */       exception.printStackTrace();
/*     */     } 
/* 502 */     logger.info("Exiting Method");
/* 503 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForwardContractVO getFWCPostingsToReverse(ForwardContractVO fwdContractVO) {
/* 508 */     logger.info("Entering Method");
/* 509 */     ForwardContractDAO dao = null;
/*     */     try {
/* 511 */       dao = ForwardContractDAO.getDAO();
/* 512 */       fwdContractVO = dao.getFWCPostingsToReverse(fwdContractVO);
/*     */     }
/* 514 */     catch (Exception exception) {
/* 515 */       exception.printStackTrace();
/*     */     } 
/* 517 */     logger.info("Exiting Method");
/* 518 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ForwardContractVO cancelBookingDetails(ForwardContractVO fwdContractVO, String category) throws BusinessException {
/* 524 */     logger.info("Entering Method");
/* 525 */     ForwardContractDAO fwdContractDAO = null;
/*     */     
/*     */     try {
/* 528 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 529 */       logger.info("Cancel Booking Details");
/* 530 */       fwdContractVO = fwdContractDAO.cancelFwdContractDetails(fwdContractVO, category);
/* 531 */     } catch (Exception exception) {
/* 532 */       throwBDException(exception);
/*     */     } 
/* 534 */     logger.info("Exiting Method");
/* 535 */     return fwdContractVO;
/*     */   }
/*     */ 
/*     */   
/*     */   public ForwardContractVO insertCancelDetails(ForwardContractVO fwdContractVO, String category, String status, String action) throws BusinessException {
/* 540 */     logger.info("Entering Method");
/* 541 */     ForwardContractDAO fwdContractDAO = null;
/*     */     
/*     */     try {
/* 544 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 545 */       fwdContractVO = fwdContractDAO.insertCancelDetails(fwdContractVO, category, status, action);
/* 546 */     } catch (Exception exception) {
/* 547 */       throwBDException(exception);
/*     */     } 
/* 549 */     logger.info("Exiting Method");
/* 550 */     return fwdContractVO;
/*     */   }

	public ForwardContractVO cancelBookingDetailsWithoutRate(ForwardContractVO fwdContractVO, String category)
			throws BusinessException {
		logger.info("Entering Method");

		ForwardContractDAO fwdContractDAO = null;

		try {

			fwdContractDAO = ForwardContractDAO.getDAO();
			logger.info("Cancel Booking Details Without Rate");

			fwdContractVO = fwdContractDAO.cancelFwdContractDetailsWithoutRate(fwdContractVO, category);

		} catch (Exception exception) {

			throwBDException(exception);

		}
		logger.info("Exiting Method");

		return fwdContractVO;

	}
 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ForwardContractVO> fetchFwdContractDetails(ForwardContractVO fwdContractVO) throws BusinessException {
/* 557 */     logger.info("Entering Method");
/* 558 */     ForwardContractDAO fwdContractDAO = null;
/* 559 */     ArrayList<ForwardContractVO> forwardContractList = null;
/*     */     
/*     */     try {
/* 562 */       forwardContractList = new ArrayList<>();
/* 563 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 564 */       if (fwdContractDAO != null) {
/* 565 */         forwardContractList = fwdContractDAO.fetchFwdContractDetails(fwdContractVO);
/*     */       }
/*     */     }
/* 568 */     catch (Exception exception) {
/*     */       
/* 570 */       logger.info("Exception in BD " + exception.getMessage());
/* 571 */       exception.printStackTrace();
/*     */     } 
/* 573 */     logger.info("Exiting Method");
/* 574 */     return forwardContractList;
/*     */   }


// ABHISHEK CHECKER

	public ArrayList<ForwardContractVO> fetchFwdContractDetailsWithoutRate(ForwardContractVO fwdContractVO)
			throws BusinessException {
		logger.info("Entering Method");

		ForwardContractDAO fwdContractDAO = null;

		ArrayList<ForwardContractVO> forwardContractList = null;

		try {

			forwardContractList = new ArrayList<>();

			fwdContractDAO = ForwardContractDAO.getDAO();

			if (fwdContractDAO != null) {

				forwardContractList = fwdContractDAO.fetchFwdContractDetailsWithoutRate(fwdContractVO);

			}

		} catch (Exception exception) {
			logger.info("Exception in BD " + exception.getMessage());

			exception.printStackTrace();

		}
		logger.info("Exiting Method");

		return forwardContractList;

	}

           
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ForwardContractVO> fetchFwdContractEnquiryDetails(ForwardContractVO fwdContractVO) throws BusinessException {
/* 581 */     logger.info("Entering Method");
/* 582 */     ForwardContractDAO fwdContractDAO = null;
/* 583 */     ArrayList<ForwardContractVO> forwardContractList = null;
/*     */     
/*     */     try {
/* 586 */       forwardContractList = new ArrayList<>();
/* 587 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 588 */       if (fwdContractDAO != null) {
/* 589 */         forwardContractList = fwdContractDAO.fetchFwdContractEnquiryDetails(fwdContractVO);
/*     */       }
/*     */     }
/* 592 */     catch (Exception exception) {
/*     */       
/* 594 */       logger.info("Exception in BD " + exception.getMessage());
/* 595 */       exception.printStackTrace();
/*     */     } 
/* 597 */     logger.info("Exiting Method");
/* 598 */     return forwardContractList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecordCountFromDB(ForwardContractVO fwdContractVO, String category) throws BusinessException {
/* 604 */     logger.info("Entering Method");
/* 605 */     ForwardContractDAO fwdContractDAO = null;
/* 606 */     int count = 0;
/*     */     
/*     */     try {
/* 609 */       fwdContractDAO = ForwardContractDAO.getDAO();
/* 610 */       count = fwdContractDAO.getRecordCountFromDB(fwdContractVO, category);
/* 611 */     } catch (Exception exception) {
/* 612 */       throwBDException(exception);
/*     */     } 
/* 614 */     logger.info("Exiting Method");
/* 615 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProcessDate() {
/* 620 */     logger.info("Entering Method");
/* 621 */     ForwardContractDAO dao = null;
/*     */     try {
/* 623 */       dao = ForwardContractDAO.getDAO();
/* 624 */       dao.getTICurrentDate();
/*     */     }
/* 626 */     catch (Exception exception) {
/* 627 */       exception.printStackTrace();
/*     */     } 
/* 629 */     logger.info("Exiting Method");
/*     */   }
/*     */ }


/* Location:              C:\Users\ditbit435\Downloads\FwdContractProcess_war_080626 (1)\FwdContractProcess.war!\WEB-INF\classes\in\co\forwardcontract\bd\ForwardContractBD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */