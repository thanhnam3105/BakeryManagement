package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW050】 試作データ画面リクエスト登録（製法コピー） インプットチェック
 *
 */
public class ShisakuTourokuSeihouInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuTourokuSeihouInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param checkData : リクエストデータ
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
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			//SA500のインプットチェックを行う。
			SeihoNoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 製法№登録項目チェック
	 *  : SA500のインプットチェックを行う。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SeihoNoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//① 試作CD－社員CDの必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "cd_shain"),"試作コード（社員コード）");
		    //② 試作CD－年の必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "nen"),"試作コード（年）");
		    //③ 試作CD－追番の必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_oi"),"試作コード（追番）");
		    //④ 試作SEQの必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuCodeCheck(checkData.GetValueStr("SA500", 0, 0, "seq_shisaku"),"試作列");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
