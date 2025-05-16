package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【JW830】 原価試算表出力インプットチェック
 *  : 原価試算表出力時に各項目の入力値チェックを行う。
 * @author k-katayama
 * @since 2009/05/21
 */
public class GenkaShisanHyoInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 原価試算表出力インプットチェックコンストラクタ
	 */
	public GenkaShisanHyoInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 各データチェック処理を管理する。
	 * 
	 * @param checkData : リクエストデータ
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

			// 機能ID：SA800のインプットチェック（出力項目チェック）を行う。
			outputKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 原価試算表出力条件項目チェック : 機能ID：SA800の原価試算表の出力条件の項目チェックを行う。
	 * 
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// 帳票の出力に必要な項目の入力チェックを行う

			// ① 必須項目の入力チェック（スーパークラスの必須チェック）を行う。(メッセージコード:E000001)
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "cd_shain"),"試作コード（社員コード）");
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "nen"),"試作コード（年）");
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "no_oi"),"試作コード（追番）");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
