package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3330_inputcheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public RGEN3330_inputcheck() {
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

			// FGEN3330のインプットチェックを行う。
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 検索条件インプットチェック : FGEN3330のインプットチェックを行う。
	 * 
	 * @param requestData
	 *            : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
// 【KPX@1602367】add mod start
			// 資材コード
			String strShizaiCd = checkData.GetValueStr("FGEN3330", 0, 0,
					"cd_shizai");
			if (strShizaiCd != null && !"".equals(strShizaiCd)) {
				// 資材コードが6桁より多い場合
				super.sizeCheckLen(strShizaiCd, "資材コード", 6);
			}

			// 資材名
			String nm_shizai = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_shizai");
			if (nm_shizai != null && !"".equals(nm_shizai)) {
				 // 資材名の入力桁数チェックを行う
				 super.sizeCheckLen(nm_shizai, "資材名", 100);
			}

			 // 旧資材コード
			 String cd_shizai_old = checkData.GetValueStr("FGEN3330", 0, 0,
					 "cd_shizai_old");
			 if (cd_shizai_old != null && !"".equals(cd_shizai_old)) {
				 // 旧資材コードが6桁より多い場合
				 super.sizeCheckLen(cd_shizai_old, "旧資材コード", 6);
			 }
			 
			 // 製品（商品）コード
			 String cd_shohin = checkData.GetValueStr("FGEN3330", 0, 0,
					 "cd_shohin");
			if (cd_shohin != null && !"".equals(cd_shohin)) {
				 // 商品コードが6桁より多い場合
				 super.sizeCheckLen(cd_shohin, "商品コード", 6);
			}
			
			// 製品（商品）名
			String strShohinNm = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_shohin");
			if (strShohinNm != null && !"".equals(strShohinNm)) {
				// 商品名の入力桁数チェックを行う
				super.sizeCheckLen(strShohinNm, "商品名", 100);
			}

			// 納入先（製造工場）名
			String nmSeizoukojo = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_seizoukojo");
			if (nmSeizoukojo != null && !"".equals(nmSeizoukojo)) {
				// 製造工場名の入力桁数チェックを行う
				super.sizeCheckLen(nmSeizoukojo, "製造工場", 100);
			}

			// 発注日From
            String dt_hattyu_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_hattyu_from"));
            // 発注日Fromの形式チェック
            if (dt_hattyu_from != null && !"".equals(dt_hattyu_from)) {
            	super.dateCheck(dt_hattyu_from, "発注日From");
            }

			// 発注日To
            String dt_hattyu_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_hattyu_to"));
            // 発注日Toの形式チェック
            if (dt_hattyu_to != null && !"".equals(dt_hattyu_to)) {
            	super.dateCheck(dt_hattyu_to, "発注日To");
            }

			// 納入日From
            String dt_nonyu_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_nonyu_from"));
            // 納入日の形式チェック
            if (dt_nonyu_from != null && !"".equals(dt_nonyu_from)) {
            	super.dateCheck(dt_nonyu_from, "納入日From");
            }

			// 納入日To
            String dt_nonyu_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_nonyu_to"));
            // 納入日の形式チェック
            if (dt_nonyu_to != null && !"".equals(dt_nonyu_to)) {
            	super.dateCheck(dt_nonyu_to, "納入日To");
            }

            // 版代支払日From
            String dt_han_payday_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_payday_from"));
            // 版代支払日の形式チェック
            if (dt_han_payday_from != null && !"".equals(dt_han_payday_from)) {
            	super.dateCheck(dt_han_payday_from, "版代支払日From");
            }

            // 版代支払日To
            String dt_han_payday_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_payday_to"));
            // 版代支払日の形式チェック
            if (dt_han_payday_to != null && !"".equals(dt_han_payday_to)) {
            	super.dateCheck(dt_han_payday_to, "版代支払日To");
            }

//            // 締日
//            String dt_han_pay_due = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_pay_due"));
//            // 締日の形式チェック
//            if (dt_han_pay_due != null && !"".equals(dt_han_pay_due)) {
//            	super.dateCheck(dt_han_pay_due, "締日");
//            }
// 【KPX@1602367】add end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
