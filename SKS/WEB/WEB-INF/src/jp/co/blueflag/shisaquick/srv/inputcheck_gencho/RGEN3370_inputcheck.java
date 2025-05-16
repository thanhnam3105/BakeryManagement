package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3370_inputcheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public RGEN3370_inputcheck() {
		// 基底クラスのコンストラクタ
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
	public void execInputCheck(RequestData checkData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			// FGEN3370のインプットチェックを行う。
			tehaiIraiOutputCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 資材手配情報EXCEL出力インプットチェック : FGEN3370のインプットチェックを行う。
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tehaiIraiOutputCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 対象資材
			String strTaisyosizai = checkData.GetValueStr("FGEN3370", 0, 0,
					"taisyosizai");
			// 必須入力チェック
			super.hissuInputCheck(strTaisyosizai, "対象資材");

			// 発注先コード１
			String strHattyuusakiCd = checkData.GetValueStr("FGEN3370", 0, 0,
					"hattyusaki_com1");
			// 必須入力チェック
			super.hissuInputCheck(strHattyuusakiCd, "発注先コード１");

			// 担当者コード１
			String strTantousyaCd = checkData.GetValueStr("FGEN3370", 0, 0,
					"hattyusaki_user1");
			// 必須入力チェック
			super.hissuInputCheck(strTantousyaCd, "担当者コード１");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
