package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 担当者マスタ所属会社コンボ選択インプットチェック : 担当者マスタメンテ画面で所属会社ドロップダウン選択時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class TantoushaMstKaishaInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 担当者マスタ所属会社コンボ選択インプットチェックコンストラクタ
	 */
	public TantoushaMstKaishaInputCheck() {
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

			// 機能ID：SA290のインプットチェック（部署一覧情報取得キーチェック）を行う。
			bushoListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 部署一覧情報取得キーチェック
	 *  : 機能ID：SA290のドロップダウンリスト用の検索キーの入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void bushoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 所属会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：所属会社コード 		メッセージパラメータ："所属会社"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA290", 0, 0, "cd_kaisha"), "所属会社");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
