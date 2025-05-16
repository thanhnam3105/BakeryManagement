package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【JW120】 試作データ画面試作表出力インプットチェック : 試作データ画面の試作表出力時に各項目の入力値チェックを行う。
 * 
 * @author k-katayama
 * @since 2009/05/20
 */
public class ShisakuShisakuhyoInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 試作データ画面 試作データ画面試作表出力インプットチェックコンストラクタ
	 */
	public ShisakuShisakuhyoInputCheck() {
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

			// 機能ID：SA460のインプットチェック（出力項目チェック）を行う。
			OutputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 出力条件項目チェック : 機能ID：SA460 試作表の出力条件の項目チェックを行う。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void OutputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// 帳票の出力に必要な項目の入力チェックを行う。
			String strKinoNm = "SA460";
			
			// ① 必須項目の入力チェック（スーパークラスの必須チェック）を行う。(メッセージコード:E000001)
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, 0); i++ ) {	
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "cd_shain"),"試作コード(社員コード)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "nen"),"試作コード(年)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "no_oi"),"試作コード(追番)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "seq_shisaku"),"試作SEQ");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "cd_kotei"),"工程CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "seq_kotei"),"工程SEQ");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
}
