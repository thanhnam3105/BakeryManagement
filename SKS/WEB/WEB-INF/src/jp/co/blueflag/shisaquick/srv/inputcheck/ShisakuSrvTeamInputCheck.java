package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class ShisakuSrvTeamInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ一覧画面 チームコンボ変更時インプットチェック用コンストラクタ
	 */
	public ShisakuSrvTeamInputCheck() {
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

			//SA250のインプットチェックを行う。
			tantoListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 担当者コンボ取得時インプットチェック
	 *  : SA250のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
		    //処理区分＝1（パラメータ使用）の場合
			if (checkData.GetValueStr("SA250", 0, 0, "kbn_shori").equals("1")) {
				//グループコードの必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA250", 0, 0, "cd_group"),"所属グループ");

				//チームコードの必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA250", 0, 0, "cd_team"),"所属チーム");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
