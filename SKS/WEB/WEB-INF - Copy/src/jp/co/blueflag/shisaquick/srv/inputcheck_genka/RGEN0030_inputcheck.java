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
 * 原価試算　再計算ｲﾝﾌﾟｯﾄﾁｪｯｸ : 
 * 		原価試算、再計算のｲﾝﾌﾟｯﾄﾁｪｯｸ
 * 
 * @author Nishigawa
 * @since 2009/11/19
 */
public class RGEN0030_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 再計算ｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN0030_inputcheck() {
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
			
			
			//FGEN0030のインプットチェックを行う。
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
	 * 再計算項目チェック
	 *  : FGEN0030のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//テーブル：[kihon]のインプットチェック
			this.kihonInsertValueCheck(checkData);
			
			//テーブル：[genryo]のインプットチェック
			this.genryoInsertValueCheck(checkData);
			
			//テーブル：[keisan]のインプットチェック
			this.keisanInsertValueCheck(checkData);
			
			//テーブル：[shizai]のインプットチェック
			this.shizaiInsertValueCheck(checkData);

			
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
	 * テーブル：[kihon]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "kihon";
		
		try {
			
			
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			
		}
	}
	
	/**
	 * テーブル：[genryo]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "genryo";
		String strAddMsg = "";
		
		try {
			
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	/**
	 * テーブル：[keisan]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void keisanInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "keisan";
		String strAddMsg = "";
		
		try {
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//追加メッセージの編集
				strAddMsg = "【試算項目】 [ 列数：" + ( i + 1 ) + "]";
				
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	/**
	 * テーブル：[shizai]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shizaiInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "shizai";
		String strAddMsg = "";
		
		try {
			
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//追加メッセージの編集
				strAddMsg = "【資材情報】 [ 行数：" + ( i + 1 ) + "]";
				
				
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	
}
