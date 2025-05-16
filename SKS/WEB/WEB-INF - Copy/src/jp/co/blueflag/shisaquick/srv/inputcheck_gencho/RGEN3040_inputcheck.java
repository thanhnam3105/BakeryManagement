package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3040_inputcheck extends InputCheck {

	/**
	 *  : コストテーブル参照画面 検索ボタン押下時インプットチェック用コンストラクタ
	 */
	public RGEN3040_inputcheck() {
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

			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			// FGEN3040のインプットチェックを行う。
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * コストテーブル参照検索条件インプットチェック
	 *  : FGEN3040のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// メーカー名
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3040", 0, 0, "cd_maker"), "メーカー名");

			// 包材
		    super.hissuCodeCheck(checkData.GetValueStr("FGEN3040", 0, 0, "cd_houzai"), "包材");

			// 版数
		    //【QP@30297】No.18 E.kitazawa 課題対応 --------------------- mod start
//		    super.hissuInputCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "版数");
		    super.hissuInputCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "版数", "E000340");
		    //【QP@30297】No.18 E.kitazawa 課題対応 --------------------- mod end

		    //【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
		    super.numberCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "版数");
		    //【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
