package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【JW130】 試作データ画面サンプル説明書力インプットチェック : 試作データ画面のサンプル説明書出力時に各項目の入力値チェックを行う。
 * 
 * @author k-katayama
 * @since 2009/05/20
 */
public class ShisakuSampleSetumeiInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 試作データ画面サンプル説明書力インプットチェックコンストラクタ
	 */
	public ShisakuSampleSetumeiInputCheck() {
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

			// 機能ID：SA450のインプットチェック（出力項目チェック）を行う。
			OutputKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * 出力条件項目チェック : 機能ID：SA450のサンプル説明書の出力条件の項目チェックを行う。
	 * 
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void OutputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// 帳票の出力に必要な項目の入力チェックを行う。
			
			// ①：必須項目の入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "cd_shain"),"試作CD-社員CD");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "nen"),"試作CD-年");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "no_oi"),"試作CD-追番");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "seq_shisaku1"),"試作SEQ");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
