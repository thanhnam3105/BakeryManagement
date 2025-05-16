package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class ShisakuSrvGroupInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ一覧画面 グループコンボ変更時インプットチェック用コンストラクタ
	 */
	public ShisakuSrvGroupInputCheck() {
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

			//SA080のインプットチェックを行う。
			teamListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * チームコンボ取得時インプットチェック
	 *  : SA080のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void teamListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
		    //処理区分＝1（パラメータ使用）の場合
			if (checkData.GetValueStr("SA080", 0, 0, "kbn_shori").equals("1")) {
				//グループコードの必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA080", 0, 0, "cd_group"),"所属グループ");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
