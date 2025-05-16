package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 原価試算、初期表示ﾍｯﾀﾞｰｲﾝﾌﾟｯﾄﾁｪｯｸ : 
 * 		原価試算、初期表示ﾍｯﾀﾞｰｲﾝﾌﾟｯﾄﾁｪｯｸ
 * 
 * @author Nishigawa
 * @since 2009/10/21
 */
public class RGEN0010_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 原価試算、初期表示ﾍｯﾀﾞｰｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN0010_inputcheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 各データチェック処理を管理する。
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

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
