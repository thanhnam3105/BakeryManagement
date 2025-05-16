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
 * 原価試算　枝番作成時のｲﾝﾌﾟｯﾄﾁｪｯｸ : 
 * 		原価試算、枝番作成時のｲﾝﾌﾟｯﾄﾁｪｯｸ
 * 
 * @author Hisahori
 * @since 2012/4/10
 */
public class RGEN2200_inputcheck extends InputCheck {
	
	//【QP@00342】
	String nm_edaShisan = "";

	/**
	 * コンストラクタ : 原価試算、資材情報表示ﾍｯﾀﾞｰｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN2200_inputcheck() {
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
			
			//【QP@00342】リクエストデータから部署フラグ取得
			nm_edaShisan = toString(checkData.GetValueStr("FGEN2180", "kihon", 0, "eda_nm_shisaku"));

			//FGEN2180のインプットチェックを行う。
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
	 * 登録項目チェック
	 *  : FGEN2180のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//インプットチェック
			this.kihonInsertValueCheck(checkData);
			
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
	 * 試算試作名 入力テキストのインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN2180";

		String strTest = "";
		
		try {
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   枝番試作名
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

			strTest = checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku");
			
				if( !toString( checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku")).equals("") ){
					
					
					//桁数チェック（20桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku"),"枝番試作名",20);
					
				}
		
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			
		}
	}
}
