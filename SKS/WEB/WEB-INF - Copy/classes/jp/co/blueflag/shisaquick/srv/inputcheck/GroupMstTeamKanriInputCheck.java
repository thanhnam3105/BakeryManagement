package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * グループマスタチーム更新処理インプットチェック : グループマスタメンテ画面で登録、更新、削除ボタン押下時にチーム更新内容の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/09
 */
public class GroupMstTeamKanriInputCheck extends InputCheck {

	/**
	 * コンストラクタ : グループマスタチーム更新処理インプットチェックコンストラクタ
	 */
	public GroupMstTeamKanriInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
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

			// 機能ID：SA090のインプットチェック（チーム更新内容チェック）を行う。
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック : 機能ID：SA090のインプットチェック（チーム更新内容チェック）を行う。
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
			if (checkData.GetValueStr("SA090", 0, 0, "kbn_shori").equals("1")) {
				// 処理区分＝登録の場合、登録値チェックを行う。
				insertValueCheck(checkData);
			} else if (checkData.GetValueStr("SA090", 0, 0, "kbn_shori").equals("2")) {
				// 処理区分＝更新の場合、更新値チェックを行う。
				updateValueCheck(checkData);
			} else if (checkData.GetValueStr("SA090", 0, 0, "kbn_shori").equals("3")) {
				// 処理区分＝削除の場合、削除値チェックを行う。
				deleteValueCheck(checkData);
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * 登録値チェック : 機能ID：SA090の登録内容の入力値チェックを行う。
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
			// グループコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：グループコード		 メッセージパラメータ："グループ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA090", 0, 0, "cd_group"), "グループ");
			// チーム名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：チーム名 		メッセージパラメータ："チーム名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA090", 0, 0, "nm_team"), "チーム名");
			// チーム名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：チーム名、引数2：100 		メッセージパラメータ："チーム名"　メッセージコード：E000201　引数4：100
			super.sizeCheckLen(checkData.GetValueStr("SA090", 0, 0, "nm_team"), "チーム名", 100);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	/**
	 * 更新内容チェック : 機能ID：SA090の更新内容の入力値チェックを行う。
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
			// ※引数1：グループコード		 メッセージパラメータ："グループ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA090", 0, 0, "cd_group"), "グループ");
			// チームコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：チームコード 		メッセージパラメータ："チーム"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA090", 0, 0, "cd_team"), "チーム");
			// チーム名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：チーム名 		メッセージパラメータ："チーム名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA090", 0, 0, "nm_team"), "チーム名");
			// チーム名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：チーム名、引数2：100 		メッセージパラメータ："チーム名"　メッセージコード：E000201　引数4：100
			super.sizeCheckLen(checkData.GetValueStr("SA090", 0, 0, "nm_team"), "チーム名", 100);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * 削除値チェック : 機能ID：SA090の削除内容の入力値チェックを行う。
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
			// ※引数1：グループコード		 メッセージパラメータ："グループ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA090", 0, 0, "cd_group"), "グループ");
			// チームコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：チームコード 		メッセージパラメータ："チーム"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA090", 0, 0, "cd_team"), "チーム");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}