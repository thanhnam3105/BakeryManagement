package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 製造担当会社検索検索処理インプットチェック : 製造担当会社検索画面で検索ボタン押下、ページリンククリック時の各項目の入力値チェックを行う。
 * 
 * @author itou
 * @since 2009/04/08
 */
public class SeizoTantoKaishaSearchInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 製造担当会社検索検索処理インプットチェックコンストラクタ
	 */
	public SeizoTantoKaishaSearchInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 入力チェックの管理を行う。
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

			// SA220のインプットチェックを行う。
			tantokaishaSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * 製造担当会社検索条件項目チェック
	 *  : SA240のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantokaishaSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//会社名の入力桁数チェックを行う。
		    super.sizeCheckLen(checkData.GetValueStr("SA220", 0, 0, "nm_kaisha"),"会社名",100);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}