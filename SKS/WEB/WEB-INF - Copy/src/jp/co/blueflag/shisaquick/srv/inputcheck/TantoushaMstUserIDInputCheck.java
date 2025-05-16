package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 担当者マスタ検索処理インプットチェック : 担当者マスタメンテ画面でユーザIDロストフォーカス時の各項目の入力値チェックを行う。
 *
 * @author itou
 * @since 2009/04/08
 */
public class TantoushaMstUserIDInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 担当者マスタ検索処理インプットチェックコンストラクタ
	 */
	public TantoushaMstUserIDInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

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

			// 機能ID：SA260のインプットチェック（担当者検索条件値チェック）を行う。
			tantoSearchKeyCheck(checkData);
			// 機能ID：SA210のインプットチェック（製造会社検索条件値チェック）を行う。
			kaishaSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 担当者情報の検索条件の入力値チェックを行う。
	 *  : 機能ID：SA260のインプットチェック（担当者検索条件値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tantoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ユーザIDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// MOD start 2015/07/30 TT.Kitazawa【QP@40812】No.5
//			super.hissuInputCheck(checkData.GetValueStr("SA260", 0, 0, "id_user"), "ユーザID");
			super.hissuInputCheck(checkData.GetValueStr("SA260", 0, 0, "id_user"), "社員番号");
			// MOD end 2015/07/30 TT.Kitazawa【QP@40812】No.5

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 製造会社検索条件値チェック
	 *  : 機能ID：SA210のインプットチェック（製造会社検索条件値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kaishaSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 処理区分が"1"の場合、ユーザIDの必須入力チェック（スーパークラスの必須チェック）を行う。
			if (checkData.GetValueStr("SA210", 0, 0, "kbn_shori").equals("1")) {
				super.hissuInputCheck(checkData.GetValueStr("SA210", 0, 0, "id_user"), "ユーザID");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
