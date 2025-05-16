package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3690_inputcheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public RGEN3690_inputcheck() {
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

			// FGEN3690のインプットチェックを行う。
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 検索条件インプットチェック : FGEN3330のインプットチェックを行う。
	 *
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 版代
			String strHanPay = checkData.GetValueStr("FGEN3690", 0, 0, "han_pay");
			if (strHanPay != null && !"".equals(strHanPay)) {
				// 版代数値チェック
				super.numberCheck(strHanPay, "版代");
			}

            // 版代支払日
            String dt_han_payday = toString(checkData.GetValueStr("FGEN3690", 0, 0, "han_payday"));
            // 版代支払日の形式チェック
            if (dt_han_payday != null && !"".equals(dt_han_payday)) {
            	super.dateCheck(dt_han_payday, "版代支払日");
            }



		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
