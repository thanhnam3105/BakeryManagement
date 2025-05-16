package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 担当者検索（営業）検索処理インプットチェック : 担当者検索（営業）画面で検索ボタン押下、ページリンククリック時の各項目の入力値チェックを行う。
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class JW900_InputCheck extends InputCheck {

	/**
	 * コンストラクタ : 担当者検索検索処理インプットチェックコンストラクタ
	 */
	public JW900_InputCheck() {
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

			//FGEN2110のインプットチェックを行う。
			tantoshaSearchCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 担当者検索条件項目チェック
	 *  : FGEN2110のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantoshaSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//担当者名の入力桁数チェックを行う。
		    super.sizeCheckLen(checkData.GetValueStr("FGEN2110", 0, 0, "nm_user"),"担当者名",60);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}

