package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 類似品検索　製品検索ｲﾝﾌﾟｯﾄﾁｪｯｸ : 
 * 		製品検索のｲﾝﾌﾟｯﾄﾁｪｯｸ
 * 
 * @author Nishigawa
 * @since 2009/11/11
 */
public class RGEN1030_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 類似品検索　製品検索ｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN1030_inputcheck() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  execInputCheck（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * インプットチェック管理 : 各データチェック処理を管理する。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);
			
			
			//FGEN1030のインプットチェックを行う。
			insertValueCheck(checkData);
			
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                           insertValueCheck（ｲﾝﾌﾟｯﾄﾁｪｯｸ開始開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * FGEN1030のインプットチェック
	 *  : FGEN1030のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//テーブル：[table]のインプットチェック
			this.tableInsertValueCheck(checkData);
			

			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		
	}
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                              TableValueCheck（各ﾃｰﾌﾞﾙｲﾝﾌﾟｯﾄﾁｪｯｸ） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * テーブル：[table]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tableInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN1030";
		String strTableNm = "table";
		
		try {
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   製品コード
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//数値
					if(!checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_seihin").equals("")){
						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_seihin"), "", ","), "製品コード");
					}
					
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			
		}
	}
	
}
