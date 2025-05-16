package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3360_inputcheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public RGEN3360_inputcheck() {
		// 基底クラスのコンストラクタ
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
	public void execInputCheck(RequestData checkData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			// FGEN3360のインプットチェックを行う。
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 資材手配情報登録更新インプットチェック : FGEN3360のインプットチェックを行う。<br>
	 * 更新データは複数件あるが、内容は全て同一の為チェックは最初の1件だけ行う。
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "FGEN3360";
		try {
			// 設計１
			String strSekkei1 = checkData.GetValueStr(strKinoNm, 0, 0,
					"sekkei1");
			// 必須入力チェック
			super.hissuInputCheck(strSekkei1, "設計①");
			// 設計１が250桁より多い字数の場合
			super.sizeCheckLen(strSekkei1, "設計①", 250);
			
			// 材質
			String strZaishitsu = checkData.GetValueStr(strKinoNm, 0, 0,
					"zaishitsu");
			// 必須入力チェック
			super.hissuInputCheck(strZaishitsu, "材質");
			// 材質が250桁より多い字数の場合
			super.sizeCheckLen(strZaishitsu, "材質", 250);
			
			// 印刷色
			String strPrintColor = checkData.GetValueStr(strKinoNm, 0, 0,
					"printcolor");
			// 必須入力チェック
			super.hissuInputCheck(strPrintColor, "印刷色");
			// 印刷色が250桁より多い字数の場合
			super.sizeCheckLen(strPrintColor, "印刷色", 250);
			
			// 色番号
			String strNoColor = checkData.GetValueStr(strKinoNm, 0, 0,
					"no_color");
			// 必須入力チェック
			super.hissuInputCheck(strNoColor, "色番号");
			// 色番号が250桁より多い字数の場合
			super.sizeCheckLen(strNoColor, "色番号", 250);
				
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
