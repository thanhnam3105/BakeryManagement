package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 各リテラルマスタカテゴリコンボ選択インプットチェック : 各リテラルマスタメンテ画面でカテゴリドロップダウン選択時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/07
 */
public class LiteralMstCategoriInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 各リテラルマスタカテゴリコンボ選択インプットチェックコンストラクタ
	 */
	public LiteralMstCategoriInputCheck() {
		//基底クラスのコンストラクタ
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

			// 機能ID：SA110のインプットチェック（リテラル一覧情報取得キーチェック）を行う。
			literalListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * リテラル一覧情報取得キーチェック
	 *  : SA110のドロップダウンリスト用の検索キーの入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void literalListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// カテゴリコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：カテゴリコード メッセージ			パラメータ："カテゴリ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA110", 0, 0, "cd_category"), "カテゴリ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
