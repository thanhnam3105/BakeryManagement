package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3450_inputcheck extends InputCheck {

	/**
	 *  : 資材手配入力：検索ボタン押下時インプットチェック用コンストラクタ
	 */
	public RGEN3450_inputcheck() {
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

			// RGEN3450のインプットチェックを行う。
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 資材テーブル検索条件インプットチェック
	 *  : FGEN3450のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 製品コード
			super.hissuInputCheck(checkData.GetValueStr("FGEN3450", 0, 0, "cd_seihin"), "製品コード");
			super.numberCheck(checkData.GetValueStr("FGEN3450", 0, 0, "cd_seihin"), "製品コード");

			// 試作No
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3450", 0, 0, "sisakuNo"), "試作No");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
