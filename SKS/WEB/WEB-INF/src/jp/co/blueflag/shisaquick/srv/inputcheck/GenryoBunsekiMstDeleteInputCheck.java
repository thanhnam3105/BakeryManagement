package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 原料分析情報マスタ削除処理インプットチェック : 原料分析情報マスタメンテ画面で削除ボタン押下時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/03
 */
public class GenryoBunsekiMstDeleteInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 原料分析情報マスタ削除処理インプットチェックコンストラクタ
	 */
	public GenryoBunsekiMstDeleteInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理 : 各データチェック処理を管理する。
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

			// 機能ID：SA370のインプットチェック（削除キー値チェック）を行う。
			deleteKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * インプットチェック（削除キー値チェック）
	 *  : SA370のインプットチェック（削除キー値チェック）を行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void deleteKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_kaisha"), "会社コード");

			// 原料コード必須入力チェック（スーパークラスの必須チェック）を行う。
			String strGenryoCD = checkData.GetValueStr("SA370", 0, 0, "cd_genryo");
			super.noSelectGyo(strGenryoCD);
			
			// 原料コードの先頭1文字≠"N"の場合、スローする。
			if (strGenryoCD.charAt(0) != 'N') {
				super.noDeleteKizonGenryo();
			}

			// 廃止フラグ≠1の場合、スローする。
			if (!checkData.GetValueStr("SA370", 0, 0, "flg_haishi").equals("1")) {
				super.noDeleteNoHaishiGenryo();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}