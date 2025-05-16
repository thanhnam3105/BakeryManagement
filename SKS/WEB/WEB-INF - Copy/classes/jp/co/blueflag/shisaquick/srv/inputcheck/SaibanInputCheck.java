package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 自動採番インプットチェック : 自動採番時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/09
 */
public class SaibanInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 自動採番インプットチェックコンストラクタ
	 */
	public SaibanInputCheck() {
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

			// 機能ID：SA410のインプットチェック（採番項目チェック）を行う。
			saibanKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 採番項目チェック
	 *  : 機能ID：SA410の採番キーの入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void saibanKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 処理区分の必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：処理区分 		メッセージパラメータ："処理区分"　メッセージコードE000211
			super.hissuSaibanCheck(checkData.GetValueStr("SA410", 0, 0, "kbn_shori"), "処理区分");
//			// キー１の必須入力チェック（スーパークラスの必須チェック）を行う。
//			// ※引数1：キー１ 		メッセージパラメータ："キー１"　メッセージコードE000211
//			super.hissuSaibanCheck(checkData.GetValueStr("SA410", 0, 0, "key1"), "キー１");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
