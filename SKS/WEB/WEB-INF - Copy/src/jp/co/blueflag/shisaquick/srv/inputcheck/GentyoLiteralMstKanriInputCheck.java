package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 各リテラルマスタ更新処理インプットチェック : 各リテラルマスタメンテ画面で登録、更新、削除ボタン押下時に更新内容の入力値チェックを行う。
 *
 * @author hisahori
 * @since 2014/10/10
 */
public class GentyoLiteralMstKanriInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 各リテラルマスタ更新処理インプットチェックコンストラクタ
	 */
	public GentyoLiteralMstKanriInputCheck() {
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

			// 機能ID：SA331のインプットチェック（更新内容チェック）を行う。
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック : 登録、更新、削除内容の入力値チェックを行う。
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
			if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("1")) {
				// 処理区分＝登録の場合、登録値チェックを行う。
				insertValueCheck(checkData);
			}else if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("2")) {
				// 処理区分＝更新の場合、更新値チェックを行う。
				updateValueCheck(checkData);
			}else if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("3")) {
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
			// カテゴリコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：カテゴリコード	 メッセージパラメータ："カテゴリ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "カテゴリ");
			// リテラル名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：リテラル名	 メッセージパラメータ："リテラル名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "リテラル名");
			// リテラル名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：リテラル名、引数2：60		メッセージパラメータ："リテラル名"　メッセージコード：E000201　引数4：60
			super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "リテラル名", 60);
			// 表示順の必須入力チェックを行う。
			// ※引数1：表示順 		メッセージパラメータ："表示順"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_sort"), "表示順");
			if (!checkData.GetValueStr("SA331", 0, 0, "biko").toString().equals("")) {
				// 備考の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
				// ※引数1：備考、引数2：60 		メッセージパラメータ："備考"　メッセージコード：E000200
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "biko"), "備考", 60);
			}
			// 編集可否フラグの必須入力チェックを行う。
			// ※引数1：編集可否フラグ 	メッセージパラメータ："編集可否"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "flg_edit"), "編集可否");


			if(!checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal").toString().equals("")){
				// 第二リテラル名の必須入力チェック（スーパークラスの必須チェック）を行う。
				// ※引数1：第２リテラル名 			メッセージパラメータ："リテラル名"　メッセージコード：E000200
				super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "第二リテラル名");
				// 第二リテラル名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
				// ※引数1：第２リテラル名、引数2：60		 メッセージパラメータ："リテラル名" メッセージコード：E000201　引数4：60
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "第二リテラル名", 60);
				// 表示順の必須入力チェックを行う。
				// ※引数1：第２表示順 		メッセージパラメータ："第２表示順"　メッセージコード：E000200
				super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_2ndsort"), "第２表示順");
			}
//			if(!checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal").toString().equals("")){
//			}

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
			// カテゴリコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：カテゴリコード 		メッセージパラメータ："カテゴリ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "カテゴリ");
			// リテラルコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：リテラルコード 		メッセージパラメータ："リテラル"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_literal"), "リテラル");
			// リテラル名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：リテラル名 			メッセージパラメータ："リテラル名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "リテラル名");
			// リテラル名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：リテラル名、引数2：60		 メッセージパラメータ："リテラル名" メッセージコード：E000201　引数4：60
			super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "リテラル名", 60);
			// 表示順の必須入力チェックを行う。
			// ※引数1：表示順 			メッセージパラメータ："表示順"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_sort"), "表示順");
			if (!checkData.GetValueStr("SA331", 0, 0, "biko").toString().equals("")) {
				// 備考の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
				// ※引数1：備考、引数2：60 		メッセージパラメータ："備考"　メッセージコード：E000200
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "biko"), "備考", 60);
			}
			// 編集可否フラグの必須入力チェックを行う。
			// ※引数1：編集可否フラグ 		メッセージパラメータ："編集可否"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "flg_edit"), "編集可否");

			if(!checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal").toString().equals("")){

				// 第２リテラルの必須入力チェック（スーパークラスの必須チェック）を行う。
				// ※引数1：カテゴリコード 		メッセージパラメータ："カテゴリ"　メッセージコード：E000207
				super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal"), "第２リテラル");

				if(!checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal").toString().equals("")){
//					// 第二リテラル名の必須入力チェック（スーパークラスの必須チェック）を行う。
//					// ※引数1：リテラル名 			メッセージパラメータ："リテラル名"　メッセージコード：E000200
//					super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "第二リテラル名");
					// 第二リテラル名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
					// ※引数1：リテラル名、引数2：60		 メッセージパラメータ："リテラル名" メッセージコード：E000201　引数4：60
					super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "第二リテラル名", 60);
				}
			}

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
			// カテゴリコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：カテゴリコード 		メッセージパラメータ："カテゴリ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "カテゴリ");
			// リテラルコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：リテラルコード 		メッセージパラメータ："リテラル"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_literal"), "リテラル");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
