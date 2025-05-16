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
 * リクエストID：RGEN1070　類似品検索CSVファイル生成インプットチェック : 
 * 		出力時画面情報データ存在チェック
 * 
 * @author Nishigawa
 * @since 2009/11/10
 */
public class RGEN1070_inputcheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public RGEN1070_inputcheck() {
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
			
			//FGEN1050のインプットチェックを行う。
			listExistenceCheck(checkData);
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	
	/**
	 * リクエストID：RGEN1070　ファンクションID：FGEN1050のリクエストデータの[seihin][shizai][sentaku]テーブル内データ存在チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void listExistenceCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN1050";
		String strSeihinList = "seihin";
		String strSizaiList = "shizai";
		String strSentakuList = "sentaku";
		int recCnt = 0;
		
		try {
			
			//製品一覧のレコード数を加算
			recCnt += checkData.GetRecCnt(strKinoNm, strSeihinList);
			
			//資材一覧のレコード数を加算
			recCnt += checkData.GetRecCnt(strKinoNm, strSizaiList);
			
			//選択一覧のレコード数を加算
			recCnt += checkData.GetRecCnt(strKinoNm, strSentakuList);
			
			//一覧レコードの合計が0の場合
			if(recCnt == 0){
				
				//不正をスローする。
		    	em.ThrowException(
		    			ExceptionKind.一般Exception, 
		    			"E000220", 
		    			"", 
		    			"", 
		    			"");
				
			}
			
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strSeihinList = null;
			strSizaiList = null;
			strSentakuList = null;
			
		}
	}
	
}
