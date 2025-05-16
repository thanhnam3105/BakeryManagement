package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3460_inputcheck extends InputCheck {

	/**
	 *  : 資材手配入力：登録ボタン押下時（資材テーブル更新）インプットチェック用コンストラクタ
	 */
	public RGEN3460_inputcheck() {
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

			// FGEN3460のインプットチェックを行う。
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 資材テーブル更新条件インプットチェック
	 *  : FGEN3460のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 製品コード
			super.hissuInputCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_seihin"), "製品コード");
			super.numberCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_seihin"), "製品コード");

			// 試作No
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3460", 0, 0, "sisakuNo"), "試作No");

			// 製造工場
			super.hissuInputCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_seizokojo"), "製造工場");
			super.numberCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_seizokojo"), "製造工場");

			// 職場
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_shokuba"), "職場");
			super.numberCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_shokuba"), "職場");

			// 製造ライン
		    super.hissuCodeCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_line"), "製造ライン");
			super.numberCheck(checkData.GetValueStr("FGEN3460", 0, 0, "cd_line"), "製造ライン");

			// 明細行がない時
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3460", 0, 0, "seq_shizai"), "資材情報", "E000305");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
