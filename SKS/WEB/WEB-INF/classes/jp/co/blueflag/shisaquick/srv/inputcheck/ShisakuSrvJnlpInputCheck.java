package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 試作データ一覧JNLP作成インプットチェック : 試作データ一覧画面でグループドロップダウン選択時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/09
 */
public class ShisakuSrvJnlpInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 試作データ一覧JNLP作成インプットチェックコンストラクタ
	 */
	public ShisakuSrvJnlpInputCheck() {
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

			// 機能ID：SA550のインプットチェック（JNLPパラメータチェック）を行う。
			paramValueCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * JNLPパラメータチェック
	 *  : 機能ID：SA550のJNLP作成時に設定するパラメータ値のチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void paramValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// ユーザIDの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：ユーザID 		メッセージパラメータ："ユーザID"　メッセージコード：E000300
			super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "id_user"), "ユーザID");
			//新規モード以外
			if (!checkData.GetValueStr("SA550", 0, 0, "mode").equals("110")) {
				// 試作コードの必須入力チェック（スーパークラスの必須チェック）を行う。
				// ※引数1：試作コード 		メッセージパラメータ："試作コード"　メッセージコード：E000300
				super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "no_shisaku"), "試作コード");
			}
			// 起動モードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：起動モード 		メッセージパラメータ："起動モード"　メッセージコード：E000300
			super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "mode"), "起動モード");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
