package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW110】 試作データ画面リクエスト原料コード入力 インプットチェック
 *
 */
public class ShisakuGenryoCdInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuGenryoCdInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param requestData : リクエストデータ
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

			//SA５８0のインプットチェックを行う。
			SearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ画面リクエスト原料コード入力 インプットチェックが失敗しました。");
		} finally {
			
		}
	}

	/**
	 * 【試作コード入力検索】 検索項目チェック
	 *  : SA580のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//会社コードの必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_kaisha"),"会社コード");
			//原料コードの必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_genryo"),"原料コード");
			//部署コードの必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_busho"),"部署コード");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
