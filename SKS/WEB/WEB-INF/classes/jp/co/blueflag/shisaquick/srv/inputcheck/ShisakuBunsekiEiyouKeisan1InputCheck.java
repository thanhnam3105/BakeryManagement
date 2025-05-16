package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【JW740】 試作表分析データ確認画面栄養計算1（五訂）出力インプットチェック
 *  : 試作表分析データ確認画面の栄養計算（五訂）出力時に各項目の入力値チェックを行う。
 * 
 * @author k-katayama
 * @since 2009/05/22
 */
public class ShisakuBunsekiEiyouKeisan1InputCheck extends InputCheck {

	/**
	 * コンストラクタ : 試作表分析データ確認画面栄養計算（五訂）出力インプットチェックコンストラクタ
	 */
	public ShisakuBunsekiEiyouKeisan1InputCheck() {
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

			// 機能ID：SA430のインプットチェック（出力項目チェック）を行う。
			outputKeyCheckEiyou1(checkData);

			// 機能ID：SA440のインプットチェック（出力項目チェック）を行う。
			outputKeyCheckEiyou2(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 出力条件項目チェック
	 *  : 機能ID：SA430の栄養計算1（五訂増補）の出力条件の項目チェックを行う。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheckEiyou1(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// 帳票の出力に必要な項目の入力チェックを行う。
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 出力条件項目チェック
	 *  : 機能ID：SA440の栄養計算2（五訂）の出力条件の項目チェックを行う。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheckEiyou2(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// 帳票の出力に必要な項目の入力チェックを行う。
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
