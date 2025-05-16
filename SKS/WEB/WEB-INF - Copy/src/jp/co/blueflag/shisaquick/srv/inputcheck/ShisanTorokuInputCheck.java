package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【JW860】 原価試算登録インプットチェック
 *  : 原価試算登録時に各項目の入力値チェックを行う。
 * @author k-katayama
 * @since 2009/06/24
 */
public class ShisanTorokuInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 原価試算登録インプットチェックコンストラクタ
	 */
	public ShisanTorokuInputCheck() {
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

			// 機能ID：SA820のインプットチェック（登録キー項目チェック）を行う。
			rirekiKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}

	/**
	 * 試算確定履歴登録条件項目チェック : 機能ID：SA820の項目チェックを行う。
	 * 
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void rirekiKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		
		String strKinoId = "SA820";
		
		try {
			// 登録用キー項目の入力チェックを行う

			// ① 必須項目の入力チェック（スーパークラスの必須チェック）を行う。(メッセージコード:E000001)
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "cd_shain"),"試作コード（社員コード）");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "nen"),"試作コード（年）");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "no_oi"),"試作コード（追番）");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "seq_shisaku"),"試作SEQ");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "sort_rireki"),"履歴順");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoId = null;

		}
		
	}
	
}
