package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 全工場単価歩留検索処理インプットチェック : 
 * 		全工場単価歩留画面で検索ボタン押下時の各項目の入力値チェックを行う。
 * 
 * @author jinbo
 * @since 2009/05/20
 */
public class ZenKojoTankabudomariSearchInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 全工場単価歩留検索処理インプットチェックコンストラクタ
	 */
	public ZenKojoTankabudomariSearchInputCheck() {
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

			// 機能ID：SA790のインプットチェック（全工場単価歩留検索条件値チェック）を行う。
			genryoSearchKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 全工場単価歩留情報の検索条件の入力値チェックを行う。
	 *  : 機能ID：SA790のインプットチェック（全工場単価歩留検索条件値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "cd_kaisha"), "会社");
			//名前が入力されている場合
			if (!checkData.GetValueStr("SA790", 0, 0, "nm_genryo").equals("")) {
				// 名前の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
				super.sizeCheckLen(checkData.GetValueStr("SA790", 0, 0, "nm_genryo"), "名前", 60);
			}
			// 対象データ区分の必須入力チェック（スーパークラスの必須チェック）を行う。
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "kbn_data"), "対象データ");
			// 出力項目の必須入力チェック（スーパークラスの必須チェック）を行う。
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "item_output"), "出力項目");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
