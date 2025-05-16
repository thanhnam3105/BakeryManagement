package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * グループマスタグループ更新処理インプットチェック : グループマスタメンテ画面で登録、更新、削除ボタン押下時にグループ更新内容の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class GroupMstGroupKanriInputCheck extends InputCheck {

	/**
	 * コンストラクタ : グループマスタグループ更新処理インプットチェックコンストラクタ
	 */
	public GroupMstGroupKanriInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
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

			// 機能ID：SA060のインプットチェック（グループ更新内容チェック）を行う。
			groupKanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * グループ更新内容チェック : グループの登録、更新、削除内容の入力値チェックを行う。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void groupKanriValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("1")) {
				// 処理区分＝登録の場合、登録値チェックを行う。
				insertValueCheck(checkData);
			}else if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("2")) {
				// 処理区分＝更新の場合、更新値チェックを行う。
				updateValueCheck(checkData);
			}else if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("3")) {
				// 処理区分＝削除の場合、削除値チェックを行う。
				deleteValueCheck(checkData);
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * 登録値チェック : 登録内容の入力値チェックを行う。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// グループ名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：グループ名		 メッセージパラメータ："グループ名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "グループ名");
			// グループ名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：グループ名、引数2：100 	メッセージパラメータ："グループ名"　メッセージコード：E000201 引数3："グループ名"、引数4：100
			super.sizeCheckLen(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "グループ名", 100);
			// ADD 2013/10/24 QP@30154 okano start
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：会社コード 		メッセージパラメータ："会社"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_kaisha"), "会社");
			// ADD 2013/10/24 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	/**
	 * 更新内容チェック : 更新内容の入力値チェックを行う。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// グループコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：グループコード 		メッセージパラメータ："グループ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_group"), "グループ");
			// グループ名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：グループ名 		メッセージパラメータ："グループ名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "グループ名");
			// グループ名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：グループ名、引数2：100 		メッセージパラメータ："グループ名"　メッセージコード：E000201　 引数3："グループ名"、引数4：100
			super.sizeCheckLen(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "グループ名", 100);
			// ADD 2013/10/24 QP@30154 okano start
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：会社コード 		メッセージパラメータ："会社"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_kaisha"), "会社");
			// ADD 2013/10/24 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
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

			// グループコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：グループコード 		メッセージパラメータ："グループ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_group"), "グループ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
