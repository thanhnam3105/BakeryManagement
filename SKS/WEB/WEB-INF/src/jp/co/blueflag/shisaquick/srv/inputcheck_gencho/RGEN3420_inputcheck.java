package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3420_inputcheck extends InputCheck {

	/**
	 *  : 資材手配入力：製品コード検索 インプットチェック用コンストラクタ
	 */
	public RGEN3420_inputcheck() {
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

			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			// RGEN3420のインプットチェックを行う。
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 製品コード検索条件インプットチェック
	 *  : RGEN3420のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 製品コード
			super.hissuInputCheck(checkData.GetValueStr("FGEN3420", 0, 0, "cd_seihin"), "製品コード");

			super.numberCheck(checkData.GetValueStr("FGEN3420", 0, 0, "cd_seihin"), "製品コード");


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
