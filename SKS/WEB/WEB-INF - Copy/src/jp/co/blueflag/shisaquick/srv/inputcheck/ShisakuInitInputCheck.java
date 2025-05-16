package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW010】 試作データ画面初期表示インプットチェック
 *
 */
public class ShisakuInitInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public ShisakuInitInputCheck() {
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

			//SA480のインプットチェックを行う。
			searchKeyCheck(checkData);
			
			//SA210のインプットチェックを行う。
			tantokaishaSearchCheck(checkData);
			
			//SA600～SA780のインプットチェックを行う
			literalSearchCheck(checkData);
						
		} catch (Exception e) {
			this.em.ThrowException(e, "");
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

	/**
	 * 試作データ検索条件インプットチェック
	 *  : SA480のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//試作コードの必須チェックを行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "cd_shain"),"試作CD-社員CD");
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "nen"),"試作CD-年");
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "no_oi"),"試作CD-追番");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 製造担当会社検索条件インプットチェック
	 *  : SA210のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantokaishaSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//ユーザIDの必須チェックを行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA210", 0, 0, "id_user"),"ユーザID");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * リテラル検索条件インプットチェック
	 *  : SA600～SA780のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void literalSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//JWSリテラル検索
			literalHissuInputCheck(checkData,"SA600");		// 工程属性
			literalHissuInputCheck(checkData,"SA610");		// 一括表示
			literalHissuInputCheck(checkData,"SA620");		// ジャンル
			literalHissuInputCheck(checkData,"SA630");		// ユーザ
			literalHissuInputCheck(checkData,"SA640");		// 特徴原料
			literalHissuInputCheck(checkData,"SA650");		// 用途
			literalHissuInputCheck(checkData,"SA660");		// 価格帯
			literalHissuInputCheck(checkData,"SA670");		// 種別
			literalHissuInputCheck(checkData,"SA680");		// 少数指定
			literalHissuInputCheck(checkData,"SA690");		// 担当営業
			literalHissuInputCheck(checkData,"SA700");		// 製造方法
			literalHissuInputCheck(checkData,"SA710");		// 充填方法
			literalHissuInputCheck(checkData,"SA720");		// 殺菌方法
			literalHissuInputCheck(checkData,"SA730");		// 容器包材
			literalHissuInputCheck(checkData,"SA740");		// 容量
			literalHissuInputCheck(checkData,"SA750");		// 単位
			literalHissuInputCheck(checkData,"SA760");		// 荷姿
			literalHissuInputCheck(checkData,"SA770");		// 取扱温度
			literalHissuInputCheck(checkData,"SA780");		// 賞味期間
			literalHissuInputCheck(checkData,"SA850");		// 種別No
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	private void literalHissuInputCheck(RequestData checkData, String strKinoId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//ユーザIDの必須チェックを行う。
//	    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "id_user"),"ユーザID");
	    //画面IDの必須チェックを行う。
	    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "id_gamen"),"画面ID");
	}
}
