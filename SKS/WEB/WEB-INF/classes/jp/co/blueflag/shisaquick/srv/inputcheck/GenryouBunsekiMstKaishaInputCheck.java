package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 原料分析情報マスタ会社コンボ選択インプットチェック
 *  : 原料分析情報マスタメンテ画面で会社ドロップダウン選択時の各項目の入力値チェックを行う。
 * @author itou
 * @since  2009/04/03
 */
public class GenryouBunsekiMstKaishaInputCheck extends InputCheck {
	
	/**
	 * コンストラクタ
	 *  : 原料分析情報マスタ会社コンボ選択インプットチェックコンストラクタ
	 */
	public GenryouBunsekiMstKaishaInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
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
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			//SA180のインプットチェック（工場一覧情報取得キーチェック）を行う。
			kojoListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 工場一覧情報コンボ取得時インプットチェック
	 *  : SA180のドロップダウンリスト用の検索キーの入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kojoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：会社コード
			super.hissuInputCheck(checkData.GetValueStr("SA180", 0, 0, "cd_kaisha"),"会社");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
