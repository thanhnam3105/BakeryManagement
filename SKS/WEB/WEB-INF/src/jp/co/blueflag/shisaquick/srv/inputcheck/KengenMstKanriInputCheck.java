package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 権限マスタ更新処理インプットチェック : 権限マスタメンテ画面で登録、更新、削除ボタン押下時に更新内容の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class KengenMstKanriInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 権限マスタ更新処理インプットチェックコンストラクタ
	 */
	public KengenMstKanriInputCheck() {
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

			// 機能ID：SA340のインプットチェック（更新内容チェック）を行う。
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック : 機能ID：SA340の登録、更新、削除内容の入力値チェックを行う。
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
			if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("1")) {
				// 処理区分＝登録の場合、登録値チェックを行う。
				insertValueCheck(checkData);
			} else if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("2")) {
				// 処理区分＝更新の場合、更新値チェックを行う。
				updateValueCheck(checkData);
			} else if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("3")) {
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
			// 登録、更新共通部分入力チェック。
			// （ 権限名,機能名,画面ID,機能ID,参照可能データID,備考の入力チェック。）
			comInsertUpdateValueCheck(checkData);
			
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
			// 権限コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：権限コード 			メッセージパラメータ："権限"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "cd_kengen"), "権限");
			// 登録、更新共通部分入力チェック。
			// （ 権限名,機能名,画面ID,機能ID,参照可能データID,備考の入力チェック。）
			comInsertUpdateValueCheck(checkData);
			
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
			// 権限コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：権限コード 			メッセージパラメータ："権限"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "cd_kengen"), "権限");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * 登録値更新値共通部分チェック : 
	 *  権限名,機能名,画面ID,機能ID,参照可能データID,備考の入力チェックは登録、更新共通。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		// 権限名の必須入力チェック（スーパークラスの必須チェック）を行う。
		// ※引数1：権限名		 メッセージパラメータ："権限名"　メッセージコード：E000200
		super.hissuInputCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "nm_kengen"), "権限名");
		// 権限名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		// ※引数1：権限名、引数2：60 		メッセージパラメータ："権限名"　メッセージコード：E000201　引数4：60
		super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kengen", 0, "nm_kengen"), "権限名", 60);
		
		int intRecCount = checkData.GetRecCnt("SA340", "ma_kinou");
		for (int i = 0; i < intRecCount; i++) {
			// 機能名の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：機能名 		メッセージパラメータ："機能名"　メッセージコード：E000200
			super.hissuInputCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "nm_kino"), "機能名");
			// 機能名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：機能名、引数2：60 		メッセージパラメータ："機能名"　メッセージコード：E000201　引数4：60
			super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kinou", i, "nm_kino"), "機能名", 60);
			// 画面IDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：画面ID 		メッセージパラメータ："画面"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_gamen"), "画面");
			// 機能IDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：機能ID 		メッセージパラメータ："機能"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_kino"), "機能");
			// 参照可能データIDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：参照可能データID 		メッセージパラメータ："参照可能データ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_data"), "参照可能データ");
			// 備考の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：備考、引数2：60 		メッセージパラメータ："備考"　メッセージコード：E000201 引数4：60
			super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kinou", i, "biko"), "備考", 60);
		}
		// 画面IDの重複データチェックを行う。
		super.diffValueCheck(checkData, "SA340", "ma_kinou", "id_gamen", "画面");
	}
}