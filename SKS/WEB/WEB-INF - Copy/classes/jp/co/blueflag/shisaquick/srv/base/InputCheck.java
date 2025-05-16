package jp.co.blueflag.shisaquick.srv.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
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
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);
			
			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000200", strChkName, "", "");
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
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);
			
			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000207", strChkName, "", "");
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
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000211", strChkName, "", "");
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
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000300", strChkName, "", "");
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
				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000200", strChkName, "", "");
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
		
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

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
	 
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.length();
			// ①:最大桁数を用い、入力桁数チェックを行う。
			if (iMaxLen < chkLen) {
				// 入力桁数不正をスローする。
				em.ThrowException(
						ExceptionKind.一般Exception,
						"E000212",
						strParam,
						Integer.toString(iMaxLen),
						"");
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

		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// ①:最大桁数を用い、入力桁数チェックを行う。
			if (iHalfLen < chkLen) {
				// 入力桁数不正をスローする。
				em.ThrowException(
						ExceptionKind.一般Exception,
						"E000201",
						strParam,
						Integer.toString(iHalfLen),
						Integer.toString(iFullLen));
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
				
		try {
			//シングルクォーテーションの存在チェック
			checkSingleQuotation(strChkPrm, strChkName);
			
			//①チェックパラメーターの日付の整合性チェックを行う。
			//デフォルトの日付型を取得
		    SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance();
		    
		    //フォーマットパタン設定
		    df.applyPattern("YYYY/MM/DD"); 

		    //チェックパラメーターを日付型に変換
	        df.parse(strChkPrm);

	    //変換失敗時の例外をキャッチ
	    } catch(ParseException ex) {

	    	//入力日付不正をスローする。
	    	em.ThrowException(ExceptionKind.一般Exception, "E000202", strChkName, "", "");
	    	
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
				
		try {
			
			//①：上限値、下限値を用い、チェックパラメーターの範囲チェックを行う。
			if (dblMaxValue < dblChkPrm || dblChkPrm < dblMinValue){
			
		    	//数字範囲不正をスローする。
		    	em.ThrowException(
		    			ExceptionKind.一般Exception, 
		    			"E000203", 
		    			strChkName, 
		    			Double.toString(dblMinValue), 
		    			Double.toString(dblMaxValue));

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
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			String strDblData = Double.toString(dblChkPrm);
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iKetasu < iSize){
			
		    	em.ThrowException(
		    			ExceptionKind.一般Exception, 
		    			"E000204", 
		    			strChkName, 
		    			String.valueOf(strDblData.substring(strDblData.indexOf(".")).length()), 
		    			Integer.toString(iSize));

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
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// 小数指定桁数を用い、チェックパラメーターの小数桁数チェックを行う。
			String strDblData = Double.toString(dblChkPrm);
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iDecimalPartLen < iSize){
			
		    	em.ThrowException(
		    			ExceptionKind.一般Exception, 
		    			"E000204", 
		    			strChkName, 
		    			String.valueOf(iIntegeruPartLen), 
		    			String.valueOf(iDecimalPartLen));

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

		try {

			//処理区分が１以外の場合
			if (!reqData.GetValueStr("USERINFO", 0, 0, "kbn_shori").equals("1")) {
				//ユーザIDの取得
				String strUserId = reqData.GetValueStr("USERINFO", 0, 0, "id_user");
				
				//ユーザIDの必須入力チェックを行う。
				if (strUserId.equals(null) || strUserId.equals("")){
					em.ThrowException(ExceptionKind.一般Exception,"E000205","","","");
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
		try {

			// チェックパラメーターの必須入力チェックを行う。
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000210", "", "", "");
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
	 * 入力データの重複データチェックを行う
	 * @param checkData
	 * @param KindId : 機能ID
     * @param TableNm : テーブル名
	 * @param ValueNm : 項目名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int intRecCount = checkData.GetRecCnt(KindId, TableNm);
		ArrayList<String> lstTantokaishaCd = new ArrayList<String>();
		// 画面から取得した選択リストが重複している場合、スローする。
		for (int i = 0; i < intRecCount; i++) {
			if (!lstTantokaishaCd.contains(prefixZeroCut(checkData.GetValueStr(KindId, TableNm, i, ValueNm)))) {
				lstTantokaishaCd.add(prefixZeroCut(checkData.GetValueStr(KindId,TableNm, i, ValueNm)));
			}else{
				// 入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000214", strChkName, "", "");
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
		
		try {

			String[] aryChkData = checkData.split("-");
			
			//配列の長さチェックを行う
			if (aryChkData.length != iIndex){
				//エラーをスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000206", strChkName, "", "");
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

		try {
			if (objValue.toString().length() > 0) {
				// シングルクォーテーションのチェック
				if (objValue.toString().indexOf("'") != -1) {
					// 入力不正をスローする。
					em.ThrowException(ExceptionKind.一般Exception, "E000215", strChkName, "", "");
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "インプットチェックに失敗しました。");

		} finally {

		}

	}

}
