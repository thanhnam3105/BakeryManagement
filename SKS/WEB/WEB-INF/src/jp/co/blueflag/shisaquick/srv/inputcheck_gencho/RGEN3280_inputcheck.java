package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class RGEN3280_inputcheck extends InputCheck {

	/**
	 *  : デザインスペース登録：登録ボタン押下時インプットチェック用コンストラクタ
	 */
	public RGEN3280_inputcheck() {
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

			// FGEN3280のインプットチェックを行う。
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 *  デザインスペース登録条件インプットチェック
	 *  : FGEN3280のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strNmSyurui = "";
			String strNmfile   = "";
			String[] lstNmSyurui = null;
			String[] lstNmFile = null;

			// 製造工場
			super.hissuInputCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_seizokojo"), "製造工場");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_seizokojo"), "製造工場");

			// 職場
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_shokuba"), "職場");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_shokuba"), "職場");

			// 製造ライン
		    super.hissuCodeCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_line"), "製造ライン");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_line"), "製造ライン");

			// 処理モード：登録の時
			if (checkData.GetValueStr("FGEN3280", 0, 0, "syoriMode").equals("ADD")) {
				// 種類
				strNmSyurui = checkData.GetValueStr("FGEN3280", 0, 0, "nm_syurui");
				// ファイル
				strNmfile = checkData.GetValueStr("FGEN3280", 0, 0, "nm_file");

            	// 登録情報を分割
            	lstNmSyurui = strNmSyurui.split(":::");
            	lstNmFile = strNmfile.split(":::");

				// 種類とファイル名のリスト数は同じ
            	//（PGで生成するのでここではチェックしない）
            	// 種類
				for (int i=0; i<lstNmSyurui.length; i++ ) {
					super.hissuCodeCheck(lstNmSyurui[i], "種類");
				}

				// ファイル名
				for (int i=0; i<lstNmFile.length; i++ ) {
					super.hissuCodeCheck(lstNmFile[i], "ファイル名");

					// ファイル文字列の不正チェック（ここでチェックしてもアップロードされてしまう）
					super.checkStringURL(lstNmFile[i], "ファイル名");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
