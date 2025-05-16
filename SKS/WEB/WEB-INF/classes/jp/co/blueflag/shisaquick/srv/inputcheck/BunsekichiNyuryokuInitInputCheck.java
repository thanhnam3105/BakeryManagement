package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 分析値入力初期表示インプットチェック : 分析値入力画面の初期表示時に各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/06
 */
public class BunsekichiNyuryokuInitInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 分析値入力初期表示インプットチェックコンストラクタ
	 */
	public BunsekichiNyuryokuInitInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理 : 機能ID：SA360のインプットチェック（出力条件値チェック）を行う。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @param userInfoData : ユーザー情報
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

			// 機能ID：SA400のインプットチェック（検索条件値チェック）を行う。
			searchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * インプットチェック（検索条件の入力値チェック）
	 *  : 機能ID：SA400のインプットチェック（検索条件値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//処理区分＝2（詳細）の場合
			if (checkData.GetValueStr("SA400", 0, 0, "kbn_shori").equals("2")) {
				// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr("SA400", 0, 0, "cd_kaisha"),"会社");
				
				// 工場コードの必須入力チェック（スーパークラスの必須チェック）を行う。
				super.hissuInputCheck(checkData.GetValueStr("SA400", 0, 0, "cd_busho"),"工場");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
