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
 * 原価試算　印刷ｲﾝﾌﾟｯﾄﾁｪｯｸ : 
 * 		印刷のｲﾝﾌﾟｯﾄﾁｪｯｸ
 * 
 * @author Nishigawa
 * @since 2009/11/10
 */
public class RGEN0051_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 類似品検索　製品-資材検索ｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN0051_inputcheck() {
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
			
			
			//FGEN0050のインプットチェックを行う。
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
	 * 印刷項目チェック
	 *  : FGEN0050のインプットチェックを行う。
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

		String strKinoNm = "FGEN0050";
		String strTableNm = "table";
		
		try {
			
			
			
			//列が選択されているかを確認
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   試作SEQ
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//必須チェック
					//super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"), "試作列の指定");
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"), "試作列", "E000404");
					
			}
			
			//４列以上選択されていないかを確認
			if(checkData.GetRecCnt(strKinoNm, strTableNm) > 3){
				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000218", "3", "", "");
			}
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			
		}
	}
	
}
