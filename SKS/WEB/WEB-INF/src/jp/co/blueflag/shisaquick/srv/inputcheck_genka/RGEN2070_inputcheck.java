package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 担当者マスタ（営業）更新処理インプットチェック
 *  : 担当者マスタメンテ（営業）画面で登録、更新、削除ボタン押下時に更新内容の入力値チェックを行う。
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class RGEN2070_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 担当者マスタ更新処理インプットチェックコンストラクタ
	 */
	public RGEN2070_inputcheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 入力チェックの管理を行う。
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

			// 機能ID：FGEN2080のインプットチェック（更新内容チェック）を行う。
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック : 機能ID：FGEN2080の登録、更新、削除内容の入力値チェックを行う。
	 *
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// 処理区分＝登録の場合、登録値チェックを行う。
			if (checkData.GetValueStr("FGEN2080", "table", 0, "kbn_shori").equals("1")) {
				insertValueCheck(checkData);

			}
			// 処理区分＝更新の場合、更新値チェックを行う。
			else if (checkData.GetValueStr("FGEN2080", "table", 0, "kbn_shori").equals("2")) {
				updateValueCheck(checkData);

			}
			// 処理区分＝削除の場合、削除値チェックを行う。
			else if (checkData.GetValueStr("FGEN2080", "table", 0, "kbn_shori").equals("3")) {
				deleteValueCheck(checkData);

			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 登録値チェック : 登録内容の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 登録、更新共通部分入力チェック。
			// （ ユーザID,パスワード,権限コード,氏名,所属会社コード,所属部署コード,所属チームコード,役職コードおよび製造担当会社コードの入力チェック。）
			comInsertUpdateValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新値チェック : 更新内容の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// 登録、更新共通部分入力チェック。
			// （ ユーザID,パスワード,権限コード,氏名,所属会社コード,所属部署コード,所属チームコード,役職コードおよび製造担当会社コードの入力チェック。）
			comInsertUpdateValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 登録値・更新値　共通部分チェック :
	 * ユーザID,パスワード,権限コード,氏名,所属会社コード,所属部署コード,所属チームコード,役職コードおよび製造担当会社コードの入力チェックは登録、更新共通。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ユーザIDの必須入力チェック（スーパークラスの必須チェック）を行う。
		// MOD start 2015/07/30 TT.Kitazawa【QP@40812】No.5
//		super.hissuInputCheck(checkData.GetValueStr("FGEN2080", "table", 0, "id_user"), "ユーザID");
		super.hissuInputCheck(checkData.GetValueStr("FGEN2080", "table", 0, "id_user"), "社員番号");
		// MOD end 2015/07/30 TT.Kitazawa【QP@40812】No.5

		// パスワードの必須入力チェック（スーパークラスの必須チェック）を行う。
		super.hissuInputCheck(checkData.GetValueStr("FGEN2080", "table", 0, "password"), "パスワード");

		// パスワードの入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		super.sizeCheckLen(checkData.GetValueStr("FGEN2080", "table", 0, "password"), "パスワード", 30);

		// ADD 2013/9/25 okano【QP@30151】No.28 start
		// パスワードの最小入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		super.sizeCheckLenMin(checkData.GetValueStr("FGEN2080", "table", 0, "password"), "パスワード", 6);
		// パスワードの英数字混在チェック（スーパークラスの入力桁数チェック）を行う。
		super.strCheckPrm(checkData.GetValueStr("FGEN2080", "table", 0, "password"), "パスワード");
		// ADD 2013/9/25 okano【QP@30151】No.28 end

		// 権限コードの必須入力チェック（スーパークラスの必須チェック）を行う。
		super.hissuCodeCheck(checkData.GetValueStr("FGEN2080", "table", 0, "cd_kengen"), "権限");

		// 氏名の必須入力チェック（スーパークラスの必須チェック）を行う。
		super.hissuInputCheck(checkData.GetValueStr("FGEN2080", "table", 0, "nm_user"), "氏名");

		// 氏名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		super.sizeCheckLen(checkData.GetValueStr("FGEN2080", "table", 0, "nm_user"), "氏名", 60);

		// 所属会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
		super.hissuCodeCheck(checkData.GetValueStr("FGEN2080", "table", 0, "cd_kaisha"), "所属会社");

		// 所属部署コードの必須入力チェック（スーパークラスの必須チェック）を行う。
		super.hissuCodeCheck(checkData.GetValueStr("FGEN2080", "table", 0, "cd_busho"), "所属部署");

	}

	/**
	 * 削除値チェック : 削除内容の入力値チェックを行う。
	 *
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void deleteValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {

			// ユーザIDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：ユーザID 		メッセージパラメータ："ユーザID"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("FGEN2080", "table", 0, "id_user"), "ユーザID");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}