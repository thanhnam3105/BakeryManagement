package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * インプットチェック
 *  : リクエストパラメーター（Input）のチェックを行う。　※リクエスト単位で派生
 * @author TT.furuta
 * @since  2009/03/25
 */
public class InputCheck extends ObjectBase{

	//ユーザー情報管理
	protected UserInfoData userInfoData = null;

	/**
	 * コンストラクタ
	 *  : インプットチェックコンストラクタ
	 */
	public InputCheck() {
		super();
		
	}

	/**
	 * インプットチェックの実装
	 *  : 派生先でオーバーライドして、インプットチェックの実装を記述する
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 */
	public void execInputCheck(
			RequestData reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//ユーザー情報退避
		this.userInfoData = _userInfoData;

		//派生先にて実装
	}
	
	/**
	 * 必須入力チェック : 入力パラメーターが選択されているかチェックを行う。
	 * @param strChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuInputCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuInputCheck(strChkPrm,strChkName,"");
	}
	protected void hissuInputCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);
			
			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if (msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000200", strChkName, "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 必須入力チェック : 入力パラメーターが選択されているかチェックを行う。
	 * @param strChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuCodeCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuCodeCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuCodeCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);
			
			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if (msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000207", strChkName, "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 必須入力チェック : 採番処理が設定いるかチェックを行う。
	 * @param strChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuSaibanCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuSaibanCheck( strChkPrm,  strChkName , "");
	}
	protected void hissuSaibanCheck(String strChkPrm, String strChkName , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				
				if (msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000211", strChkName, "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}

			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 必須入力チェック : JNLPファイルの作成に設定されているかチェックを行う。
	 * @param strChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuJNLPSetCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuJNLPSetCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuJNLPSetCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				
				if (msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000300", strChkName, "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}

			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}

	/**
	 * 必須入力チェック : 入力パラメーターが選択されているかチェックを行う。
	 * @param lstChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuInputCheck(ArrayList<Object> lstChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuInputCheck( lstChkPrm,  strChkName, "");
	}
	protected void hissuInputCheck(ArrayList<Object> lstChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		boolean blnFlg = false;
		
		try {

			for (int i = 0; i < lstChkPrm.size(); i++) {
				//シングルクォーテーションの存在チェック
				checkSingleQuotation(lstChkPrm.get(i), strChkName);

				// チェックパラメーターの必須入力チェックを行う。
				if (!(lstChkPrm.get(i).toString().equals(null) || lstChkPrm.get(i).toString().equals(""))) {

					blnFlg = true;
					break;
				}
			}

			if (blnFlg == false) {
				if(msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000200", strChkName, "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	
	/**
	 * 必須入力チェック : 排他制御が設定されているかチェックを行う。
	 * （E000213:"排他制御失敗：$1未設定"）
	 * @param strChkPrm : チェックパラメーター
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuExclusiveControlCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuExclusiveControlCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuExclusiveControlCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if(msgNo.equals("")){
					
				}else{
					
				}
				
				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000213", strChkName, "", "");
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	/**
	 * 入力桁数チェック
	 *  : 入力パラメーター桁数のチェックを行う。(全角、半角を区別しない。）
	 * @param strChkPrm	: チェックパラメーター
	 * @param strParam : メッセージパラメータ
	 * @param iMaxLen : 最大桁数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeCheckLen(String strChkPrm, String strParam, int iMaxLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeCheckLen( strChkPrm,  strParam,  iMaxLen, "");
	}
	protected void sizeCheckLen(String strChkPrm, String strParam, int iMaxLen, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {					
	 
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.length();
			// ①:最大桁数を用い、入力桁数チェックを行う。
			if (iMaxLen < chkLen) {
				

				if(msgNo.equals("")){
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							"E000212",
							strParam,
							Integer.toString(iMaxLen),
							"");
					
				}else{
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							msgNo,
							strParam,
							Integer.toString(iMaxLen),
							"");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 入力桁数チェック
	 *  : 入力パラメーター桁数のチェックを行う。
	 * @param strChkPrm	: チェックパラメーター
	 * @param strParam : メッセージパラメータ
	 * @param iHalfLen : 半角最大桁数
	 * @param iFullLen : 全角最大桁数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeHalfFullLengthCheck(String strChkPrm, String strParam, int iHalfLen, int iFullLen ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeHalfFullLengthCheck( strChkPrm,  strParam,  iHalfLen,  iFullLen , "");
	}
	protected void sizeHalfFullLengthCheck(String strChkPrm, String strParam, int iHalfLen, int iFullLen , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {		

		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// ①:最大桁数を用い、入力桁数チェックを行う。
			if (iHalfLen < chkLen) {

				if(msgNo.equals("")){
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							"E000201",
							strParam,
							Integer.toString(iHalfLen),
							Integer.toString(iFullLen));
					
				}else{
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							msgNo,
							strParam,
							Integer.toString(iHalfLen),
							Integer.toString(iFullLen));
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 入力桁数チェック
	 *  : 入力パラメーター桁数のチェックを行う。
	 * @param strChkPrm	: チェックパラメーター
	 * @param strParam : メッセージパラメータ
	 * @param iHalfLen : 半角最大桁数
	 * @param iFullLen : 全角最大桁数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeHalfFullLengthCheck_hankaku(String strChkPrm, String strParam, int iHalfLen, int iFullLen ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeHalfFullLengthCheck_hankaku( strChkPrm,  strParam,  iHalfLen,  iFullLen , "");
	}
	protected void sizeHalfFullLengthCheck_hankaku(String strChkPrm, String strParam, int iHalfLen, int iFullLen , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {		

		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// ①:最大桁数を用い、入力桁数チェックを行う。
			if (iHalfLen < chkLen) {

				if(msgNo.equals("")){
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							"E000411",
							strParam,
							Integer.toString(iHalfLen),
							"");
					
				}else{
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							msgNo,
							strParam,
							Integer.toString(iHalfLen),
							"");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}
	
	/**
	 * 日付チェック
	 *  : 入力パラメーターの日付の整合性チェックを行う。
	 * @param strChkPrm : チェックパラメーター
	 * @param strChckName	: 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void dateCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		dateCheck( strChkPrm,  strChkName, "");
	}
	protected void dateCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
				
		try {
			String[] strTemps = null;
			
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

		    //strChkPrm = strChkPrm.replace('-', '/');
		    
			strChkPrm = cnvDateFormat(strChkPrm);
			strTemps = strChkPrm.split("/");
			
		    DateFormat format = DateFormat.getDateInstance();

		    // 日付/時刻解析を厳密に行うかどうかを設定する。
		    format.setLenient(false);

		    //日付の整合性チェック
		    try {
		        format.parse(strChkPrm);
		        
		    } catch (Exception e) {
		    	

				if(msgNo.equals("")){
			    	//入力日付不正をスローする。
		    		em.ThrowException(ExceptionKind.一般Exception, "E000202", strChkName, "", "");

				}else{
			    	//入力日付不正をスローする。
			    	em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");

				}
				
		    }

		    //日付範囲チェック（SQLServerの日付型は、9999年までしか対応していないため）
	    	if(strTemps[0].length() > 4){
				//入力日付不正をスローする。
	    		em.ThrowException(ExceptionKind.一般Exception, "E000203", strChkName, "2000年01月01日", "9999年12月31日");

	    	}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}

	/**
	 * 試作列最大値チェック
	 *  : 試作列の最大値チェックを行う。
	 * @param intRowValue		: 試作列数
	 * @param intMaxRowValue	: 最大行数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void maxRowCheck(int intRowValue, int intMaxRowValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		maxRowCheck(intRowValue, intMaxRowValue, strChkName, "");
	}
	protected void maxRowCheck(int intRowValue, int intMaxRowValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {

			if (intRowValue > intMaxRowValue){

				if(msgNo.equals("")){
			    	//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			"E000219", 
			    			Integer.toString(intMaxRowValue),
			    			"",
			    			"");
					
				}else{
			    	//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Double.toString(intMaxRowValue),
			    			"");
					
				}
			}
			
		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
		
	}
	
	/**
	 * 数字範囲チェック
	 *  : 数字項目の範囲チェックを行う。(※ー不可等)
	 * @param dblChkPrm	   : チェックパラメーター
	 * @param dblMinValue : 下限値
	 * @param dblMaxValue : 上限値
	 * @param strChkName  : 項目名 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void rangeNumCheck(double dblChkPrm, double dblMinValue, double dblMaxValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		rangeNumCheck( dblChkPrm,  dblMinValue,  dblMaxValue,  strChkName, "");
	}
	protected void rangeNumCheck(double dblChkPrm, double dblMinValue, double dblMaxValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
				
		try {
			
			//①：上限値、下限値を用い、チェックパラメーターの範囲チェックを行う。
			if (dblMaxValue < dblChkPrm || dblChkPrm < dblMinValue){
			

				if(msgNo.equals("")){
			    	//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			"E000203", 
			    			strChkName, 
			    			Double.toString(dblMinValue), 
			    			Double.toString(dblMaxValue));
					
				}else{
			    	//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Double.toString(dblMinValue), 
			    			Double.toString(dblMaxValue));
					
				}
				

			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 数字範囲チェック
	 *  : 数字項目の範囲チェックを行う。(※ー不可等)
	 * @param dblChkPrm	   : チェックパラメーター
	 * @param dblMinValue : 下限値
	 * @param dblMaxValue : 上限値
	 * @param strChkName  : 項目名 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void rangeNumCheck(BigDecimal dblChkPrm, BigDecimal dblMinValue, BigDecimal dblMaxValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		rangeNumCheck( dblChkPrm,  dblMinValue,  dblMaxValue,  strChkName, "");
	}
	protected void rangeNumCheck(BigDecimal dblChkPrm, BigDecimal dblMinValue, BigDecimal dblMaxValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
			
		boolean chkflg = false;
		
		try {
			
			
			//下限値よりチェックパラメーターが大きい　又は　等しい
			if(dblChkPrm.compareTo(dblMinValue) > -1){
				
				//上限値よりチェックパラメーターが小さい　又は　等しい
				if(dblChkPrm.compareTo(dblMaxValue) < 1){
					
					chkflg = true;
				}
				
			}
			
			//結果判定
			if(chkflg){
				
			}else{

				if(msgNo.equals("")){
					//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			"E000203", 
			    			strChkName, 
			    			dblMinValue.toString(), 
			    			dblMaxValue.toString());
					
				}else{
					//数字範囲不正をスローする。
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			msgNo, 
			    			strChkName, 
			    			dblMinValue.toString(), 
			    			dblMaxValue.toString());
					
				}
				
			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 小数桁数チェック
	 *  : 小数桁数の入力チェックを行う。（整数部の桁数指定がないのでErrorMessageパラメータには整数部入力桁数を指定するのか）
	 * @param dblChkPrm  : チェックパラメーター
	 * @param iKetasu    : 小数指定桁数
	 * @param strChkName : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void shousuRangeCheck(double dblChkPrm, int iKetasu, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iKetasu,  strChkName, "");
	}
	protected void shousuRangeCheck(double dblChkPrm, int iKetasu, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			String strDblData = Double.toString(dblChkPrm);
			
			// 小数桁数チェック
			shousuRangeCheck(strDblData, iKetasu, strChkName, msgNo);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 小数桁数チェック
	 *  : 小数桁数の入力チェックを行う。（整数部の桁数指定がないのでErrorMessageパラメータには整数部入力桁数を指定するのか）
	 * @param dblChkPrm  : チェックパラメーター
	 * @param iKetasu    : 小数指定桁数
	 * @param strChkName : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void shousuRangeCheck(String dblChkPrm, int iKetasu, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iKetasu,  strChkName, "");
	}
	protected void shousuRangeCheck(String dblChkPrm, int iKetasu, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			String strDblData = dblChkPrm;
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iKetasu < iSize){
			

				if(msgNo.equals("")){
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			"E000217", 
			    			strChkName, 
			    			Integer.toString(iKetasu),
			    			"");
					
				}else{
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Integer.toString(iKetasu),
			    			"");
					
				}

			}
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 小数桁数チェック
	 *  : 小数桁数の入力チェックを行う。
	 * @param dblChkPrm : チェックパラメーター
	 * @param iIntegeruPartLen : 整数指定桁数
	 * @param iDecimalPartLen : 小数指定桁数
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iIntegeruPartLen,  iDecimalPartLen, "");
	}
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			String strDblData = Double.toString(dblChkPrm);

			//小数桁数チェック
			shousuRangeCheck(strDblData, iIntegeruPartLen, iDecimalPartLen, strChkName);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/**
	 * 小数桁数チェック
	 *  : 小数桁数の入力チェックを行う。
	 * @param dblChkPrm : チェックパラメーター
	 * @param iIntegeruPartLen : 整数指定桁数
	 * @param iDecimalPartLen : 小数指定桁数
	 * @param strChkName : 項目名
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void shousuRangeCheck(String dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iIntegeruPartLen,  iDecimalPartLen,  strChkName, "");
	}
	protected void shousuRangeCheck(String dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName, String msgNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			int iSize = dblChkPrm.substring(dblChkPrm.indexOf(".") + 1).length();
			
			if (iSize != 0 && iDecimalPartLen < iSize){
			

				if(msgNo.equals("")){
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			"E000204", 
			    			strChkName, 
			    			String.valueOf(iIntegeruPartLen), 
			    			String.valueOf(iDecimalPartLen));
					
				}else{
			    	em.ThrowException(
			    			ExceptionKind.一般Exception, 
			    			msgNo, 
			    			strChkName, 
			    			String.valueOf(iIntegeruPartLen), 
			    			String.valueOf(iDecimalPartLen));
					
				}

			}
		} catch (Exception e) {
			
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
		
	/**
	 * ユーザ情報取得ID必須チェック
	 *  : ユーザ情報取得用のユーザID必須入力チェックを行う。
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void userInfoCheck(RequestData reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		userInfoCheck( reqData, "");
	}
	protected void userInfoCheck(RequestData reqData, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//処理区分が１以外の場合
			if (!reqData.GetValueStr("USERINFO", 0, 0, "kbn_shori").equals("1")) {
				//ユーザIDの取得
				String strUserId = reqData.GetValueStr("USERINFO", 0, 0, "id_user");
				
				//ユーザIDの必須入力チェックを行う。
				if (strUserId.equals(null) || strUserId.equals("")){

					if(msgNo.equals("")){
						em.ThrowException(ExceptionKind.一般Exception,"E000205","","","");
						
					}else{
						em.ThrowException(ExceptionKind.一般Exception,msgNo,"","","");
						
					}
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {
			
		}
	}
	
	/**
	 * 入力チェック : エラーメッセージ表示:"廃止されていない原料は削除できません。"
	 * (code="E000208")
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noDeleteNoHaishiGenryo()
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

			// 入力不正をスローする。
			noParamErrMsgOnlyDisp("E000208");

	}
	/**
	 * 入力チェック : エラーメッセージ表示:"既存原料は削除できません。"
	 * (code="E000209")
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noDeleteKizonGenryo()
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

			// 入力不正をスローする。
			noParamErrMsgOnlyDisp("E000209");

	}
	/**
	 * 入力チェック : エラーメッセージ表示:"データが未選択です。\n対象行を選択して下さい。"
	 * (code="E000210")
	 * @param strGenryoCD 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noSelectGyo(String strChkPrm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		noSelectGyo( strChkPrm, "E000210");
	}
	protected void noSelectGyo(String strChkPrm, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if(msgNo.equals("")){
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000210", "", "", "");
					
				}else{
					// 必須入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, "", "", "");
					
				}
				
			}
		} catch (Exception e) {
			em.ThrowException(e, "インプットチェックに失敗しました。");
		} finally {

		}
	}
	/**
	 * 入力チェック : パラメーターがない入力エラーメッセージのみ表示する共通メソッド。
	 * 
	 * @param strErrMsgCD
	 *            : エラーメッセージコード（Const_Msg.xmlより）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noParamErrMsgOnlyDisp(String strErrMsgCD) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			// 入力不正をスローする。
			em.ThrowException(ExceptionKind.一般Exception, strErrMsgCD, "", "", "");

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}

	/**
	 * 入力値チェック　：　数値チェック
	 * @param obj : 対象のオブジェクト
	 * @param strChkName : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void numberCheck(Object obj,String strChkName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		numberCheck( obj, strChkName, "");
	}
	protected void numberCheck(Object obj,String strChkName, String msgNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{			
			//【QP@10713】2011/11/21 TT H.SHIMA ADD Start 空白スペースチェック
			if(toString(obj).indexOf(" ") != -1){
				throw new NumberFormatException();
			}
			//【QP@10713】2011/11/21 TT H.SHIMA ADD End   空白スペースチェック
						
			Double.parseDouble(toString(obj));
			
		}catch(NumberFormatException e){

			if(msgNo.equals("")){
		    	em.ThrowException(
		    			ExceptionKind.一般Exception,
		    			//【QP@10713】2011/11/15 TT H.SHIMA ADD Start
		    			//"E000216", 
		    			"E000221", 
		    			//【QP@10713】2011/11/15 TT H.SHIMA ADD End
		    			strChkName, 
		    			"", 
		    			"");
				
			}else{
		    	em.ThrowException(
		    			ExceptionKind.一般Exception, 
		    			msgNo, 
		    			strChkName, 
		    			"", 
		    			"");
				
			}
			
			
		} catch (Exception ex) {
			em.ThrowException(ex, "インプットチェックに失敗しました。");

		} finally {

		}
		
	}

	/**
	 * 入力データの重複データチェックを行う
	 * @param checkData
	 * @param KindId : 機能ID
     * @param TableNm : テーブル名
	 * @param ValueNm : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		diffValueCheck( checkData,  KindId,  TableNm,  ValueNm,  strChkName, "");
	}
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName, String msgNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int intRecCount = checkData.GetRecCnt(KindId, TableNm);
		ArrayList<String> lstTantokaishaCd = new ArrayList<String>();
		// 画面から取得した選択リストが重複している場合、スローする。
		for (int i = 0; i < intRecCount; i++) {
			if (!lstTantokaishaCd.contains(prefixZeroCut(checkData.GetValueStr(KindId, TableNm, i, ValueNm)))) {
				lstTantokaishaCd.add(prefixZeroCut(checkData.GetValueStr(KindId,TableNm, i, ValueNm)));
			}else{
				if (msgNo.equals("")){
					// 入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000214", strChkName, "", "");
					
				}else{
					// 入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
			}
		}
		lstTantokaishaCd = null;
	}
	
	/**
	 * 配列の長さチェック
	 * @param checkData : チェックデータ
	 * @param iIndex : 配列の長さ（最小値：1）
	 * @param strChkName : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void arrayCheck(String checkData, int iIndex, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		arrayCheck( checkData,  iIndex,  strChkName, "");
	}
	protected void arrayCheck(String checkData, int iIndex, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {

			String[] aryChkData = checkData.split("-");
			
			//配列の長さチェックを行う
			if (aryChkData.length != iIndex){

				if(msgNo.equals("")){
					//エラーをスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000206", strChkName, "", "");
					
				}else{
					//エラーをスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}
	}

	/**
	 * 文字列比較に使うために文字列の頭文字０（ゼロ）を削除する
	 * @param String
	 * @return String
	 */
	private String prefixZeroCut(String fieldVale) {
		String strRet = fieldVale;
		for (int i = 0; i < fieldVale.length(); i++) {
			if (strRet.startsWith("0")) {
				strRet = strRet.replaceFirst("0", "");
			}
		}
		return strRet;
	}
	
	/**
	 * シングルクォーテーションの存在チェック
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkSingleQuotation(Object objValue, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkSingleQuotation( objValue,  strChkName, "");
	}
	private void checkSingleQuotation(Object objValue, String strChkName, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (objValue.toString().length() > 0) {
				// シングルクォーテーションのチェック
				if (objValue.toString().indexOf("'") != -1) {

					if(msgNo.equals("")){
						// 入力不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception, "E000215", strChkName, "", "");
						
					}else{
						// 入力不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
						
					}
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}

	}
	
	/**
	 * 文字の存在チェック（新規原料）
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void checkExistString(Object objValue, String strChk, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkExistString( objValue,  strChk,  strChkName, "");
	}
	protected void checkExistString(Object objValue, String strChk, String strChkName, String msgNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if(objValue.toString().indexOf(strChk) != -1){

				if(msgNo.equals("")){
					// 入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000307", strChkName, "", "");
					
				}else{
					// 入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}

	}
	
	/**
	 * データ件数チェック（配合量セル数：1500セルチェック）
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void DataCellCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "SA490";
		String strTableHaigo = "tr_haigo";
		String strTableShisaku = "tr_shisaku";
		
		try{
			//最大工程順取得
			int intSort1 = 0;
			for(int i=0; i < checkData.GetRecCnt(strKinoNm, strTableHaigo); i++){
				int strSort2 = Integer.parseInt(checkData.GetValueStr(strKinoNm, strTableHaigo, i, "sort_kotei"));
				if(strSort2 > intSort1){
					intSort1 = strSort2;
				}
			}
			
			//各データ件数取得
			int intHaigoCnt = checkData.GetRecCnt(strKinoNm, strTableHaigo) + intSort1;
			int intShisakuCnt = checkData.GetRecCnt(strKinoNm, strTableShisaku);
			
			//セル件数算出
			int intSumCnt = intHaigoCnt * intShisakuCnt;
			//セルチェック数取得
			int intCheckCnt = Integer.parseInt(ConstManager.getConstValue(Category.設定, "CHECK_MAX_CELL"));
			
			//セルチェック（指定セル数以下の場合）
			if(intSumCnt <= intCheckCnt){
				//何もしない
			}
			else{
				// 入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000220", ConstManager.getConstValue(Category.設定, "CHECK_MAX_CELL"), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "インプットチェックに失敗しました。");
			
		} finally {
			
		}
	}
	
	/*【QP@00342】
	 * URL予約語の存在チェック
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void checkStringURL(Object objValue, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkStringURL( objValue,  strChkName, "");
	}
	private void checkStringURL(Object objValue, String strChkName, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (objValue.toString().length() > 0) {
				if(toString(objValue).indexOf(";") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000319", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("/") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000320", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("?") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000321", strChkName, "", "");
				}
				else if(toString(objValue).indexOf(":") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000322", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("@") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000323", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("&") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000324", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("=") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000325", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("+") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000326", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("$") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000327", strChkName, "", "");
				}
				else if(toString(objValue).indexOf(",") >= 0){
					em.ThrowException(ExceptionKind.一般Exception, "E000328", strChkName, "", "");
				}
				// 2012/02/15 TT H.Shima add 改行チェック Start
				else if(toString(objValue).indexOf('\n') >= 0 || toString(objValue).indexOf('\r') >= 0 ){
					em.ThrowException(ExceptionKind.一般Exception, "E000332", strChkName, "", "");
				}
				// 2012/02/15 TT H.Shima add 改行チェック End
			}
		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}

	}
	
	// ADD 2013/9/25 okano【QP@30151】No.28 start
	
	/**
	 * 入力桁数チェック
	 *  : 入力パラメーター桁数のチェックを行う。(全角、半角を区別しない。）
	 * @param strChkPrm	: チェックパラメーター
	 * @param strParam : メッセージパラメータ
	 * @param iMaxLen : 最小桁数
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeCheckLenMin(String strChkPrm, String strParam, int iMinLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeCheckLenMin( strChkPrm,  strParam,  iMinLen, "");
	}
	protected void sizeCheckLenMin(String strChkPrm, String strParam, int iMinLen, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			int chkLen = strChkPrm.length();
			// ①:最小桁数を用い、入力桁数チェックを行う。
			if (iMinLen > chkLen) {
				if(msgNo.equals("")){
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							"E000226",
							strParam,
							Integer.toString(iMinLen),
							"");
					
				}else{
					// 入力桁数不正をスローする。
					em.ThrowException(
							ExceptionKind.一般Exception,
							msgNo,
							strParam,
							Integer.toString(iMinLen),
							"");
					
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー認証に失敗しました。");

		} finally {

		}
	}

	/**
	 * 入力文字列チェック
	 *  : 入力パラメーターの英数字混在チェックを行う。(全角、半角を区別しない。）
	 * @param strChkPrm	: チェックパラメーター
	 * @param strParam : メッセージパラメータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void strCheckPrm(String strChkPrm, String strParam) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//空白スペースチェック
			if(strChkPrm.indexOf(" ") != -1){

				em.ThrowException(ExceptionKind.一般Exception,
						"E000227",
						strParam,
						"",
						"");
			}
			
			//半角英数字混在チェック
			if(!strChkPrm.matches("^[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*$|^[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*$")){

				em.ThrowException(ExceptionKind.一般Exception,
						"E000227",
						strParam,
						"",
						"");
			}

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー認証に失敗しました。");

		} finally {

		}
	}
	// ADD 2013/9/25 okano【QP@30151】No.28 end
}