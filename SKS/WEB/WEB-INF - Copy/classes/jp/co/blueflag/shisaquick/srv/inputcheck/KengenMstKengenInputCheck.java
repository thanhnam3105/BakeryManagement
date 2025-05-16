package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 権限マスタ権限コンボ選択インプットチェック : 権限マスタメンテ画面で権限ドロップダウン選択時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class KengenMstKengenInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 権限マスタ権限コンボ選択インプットチェックコンストラクタ
	 */
	public KengenMstKengenInputCheck() {
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

			// 機能ID：SA160のインプットチェック（検索条件値チェック）を行う。
			searchKeyCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 検索条件値チェック
	 *  : 情報の検索条件の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 権限コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：権限コード 		メッセージパラメータ："権限"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA160", 0, 0, "cd_kengen"), "権限");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
