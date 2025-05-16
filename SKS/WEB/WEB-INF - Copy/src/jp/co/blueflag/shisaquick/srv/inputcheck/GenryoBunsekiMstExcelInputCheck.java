package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 原料分析情報マスタExcel処理インプットチェック : 原料分析情報マスタメンテ画面でExcelボタン押下時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/03
 */
public class GenryoBunsekiMstExcelInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 原料分析情報マスタExcel処理インプットチェックコンストラクタ
	 */
	public GenryoBunsekiMstExcelInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理 : 機能ID：SA360のインプットチェック（出力条件値チェック）を行う。
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

			// 機能ID：SA360のインプットチェック（出力条件値チェック）を行う。
			outputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * インプットチェック（出力条件値チェック）
	 *  : CSVファイル出力条件の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ArrayList<Object> lstParam = null;
		
		try {
			lstParam = new ArrayList<Object>();

			//　対象原料の必須入力チェック（スーパークラスの必須チェック）を行う。
			lstParam.add(checkData.GetValueStr("SA360", 0, 0, "kbn_joken1"));
			lstParam.add(checkData.GetValueStr("SA360", 0, 0, "kbn_joken2"));
			super.hissuInputCheck(lstParam, "対象原料");
			
			// 原料コードの入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			super.sizeCheckLen(checkData.GetValueStr("SA360", 0, 0, "cd_genryo"),"原料コード",11);
			
			// 原料名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA360", 0, 0, "nm_genryo"),"原料名",30,60);
			
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			super.hissuInputCheck(checkData.GetValueStr("SA360", 0, 0, "cd_kaisha"),"会社");
			
			// 工場コードの必須入力チェック（スーパークラスの必須チェック）を行う。
//			super.hissuInputCheck(checkData.GetValueStr("SA360", 0, 0, "cd_kojo"),"工場");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
			lstParam = null;
			
		}
	}
}
