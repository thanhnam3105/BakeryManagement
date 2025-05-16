package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 各リテラルマスタExcel処理インプットチェック : 各リテラルマスタメンテ画面でExcelボタン押下時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class LiteralMstExcelInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 各リテラルマスタExcel処理インプットチェックコンストラクタ
	 */
	public LiteralMstExcelInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 機能ID：SA320のインプットチェック（出力条件値チェック）を行う。
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

			// 機能ID：SA320のインプットチェック（出力条件値チェック）を行う。
			outputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * インプットチェック（出力条件値チェック）
	 *  : 出力条件の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// カテゴリコードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：カテゴリコード 		メッセージパラメータ："カテゴリ"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA320", 0, 0, "cd_category"), "カテゴリ");
//			// リテラルコードの必須入力チェック（スーパークラスの必須チェック）を行う。
//			// ※引数1：リテラルコード 		メッセージパラメータ："リテラル"　メッセージコード：E000207
//			super.hissuCodeCheck(checkData.GetValueStr("SA320", 0, 0, "cd_literal"), "リテラル");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
