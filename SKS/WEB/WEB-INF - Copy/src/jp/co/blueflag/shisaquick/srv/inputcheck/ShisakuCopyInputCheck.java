package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW020】 試作データ画面リクエスト試作コピー インプットチェック
 *
 */
public class ShisakuCopyInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public ShisakuCopyInputCheck() {
		//基底クラスのコンストラクタ
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

			//SA420のインプットチェックを行う。
			exclusiveControlKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ画面リクエスト試作コピーインプットチェック処理が失敗しました。");
		} finally {
			
		}
	}

	/**
	 * 排他制御項目インプットチェック
	 *  : SA420のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//実行区分＝1（処理する）の場合
			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
				//ユーザIDの必須チェックを行う。
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"ユーザID");
	
			    //排他区分の必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"排他区分");
				//試作コードの必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"試作CD-社員CD");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"試作CD-年");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"試作CD-追番");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
