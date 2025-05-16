package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 分析値入力インプットチェック : 分析値入力画面の連携設定（配合リンク検索）の入力値チェックを行う。
 *
 * @author TT Kitazawa
 * @since 2016/06/07
 */
public class BunsekichiHaigoLinkSearchInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 分析値入力：配合リンク検索インプットチェックコンストラクタ
	 */
	public BunsekichiHaigoLinkSearchInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理 : 機能ID：FGEN2260のインプットチェックを行う。
	 *
	 * @param requestData
	 *            : リクエストデータ
	 * @param userInfoData : ユーザー情報
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

			// 機能ID：FGEN2260のインプットチェック（検索条件値チェック）を行う。
			searchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * インプットチェック（検索条件の入力値チェック）
	 *  : 機能ID：FGEN2260のインプットチェック（検索条件値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//検索モード＝0（原料より）の場合
			if (checkData.GetValueStr(1, 0, 0, "syoriMode").equals("0")) {
				//会社コード、原料コード、部署コード（入力項目でない）

			} else {
				//検索モード（試作ｺｰﾄﾞより）の場合
				// 試作CD-社員CDの入力チェック（スーパークラスのチェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "cd_shain"),"試作Ｎｏ_社員コード");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "cd_shain"),"試作Ｎｏ_社員コード");

				// 試作CD-年の入力チェック（スーパークラスのチェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "nen"),"試作Ｎｏ_年");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "nen"),"試作Ｎｏ_年");

				// 試作CD-追番の入力チェック（スーパークラスのチェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "no_oi"),"試作Ｎｏ_追番");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "no_oi"),"試作Ｎｏ_追番");

				// 枝番号の入力チェック（スーパークラスのチェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "no_eda"),"試作Ｎｏ_枝番");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "no_eda"),"試作Ｎｏ_枝番");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
