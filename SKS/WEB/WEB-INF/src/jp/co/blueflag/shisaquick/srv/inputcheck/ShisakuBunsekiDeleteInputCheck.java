package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 【JW730】 試作表分析データ確認画面リクエスト削除 インプットチェック
 *
 */
public class ShisakuBunsekiDeleteInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 */
	public ShisakuBunsekiDeleteInputCheck() {
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

			//SA370のインプットチェックを行う。
			DeleteKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 削除キー値チェック
	 *  : SA370のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void DeleteKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//会社コードの必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_kaisha"),"会社コード");
		    //原料コード必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_genryo"),"原料コード");
		    //原料コードの先頭1文字≠"N"の場合
		    if ( checkData.GetValueStr("SA370", 0, 0, "cd_genryo").charAt(0) != 'N' ) {
		    	this.em.ThrowException(ExceptionKind.一般Exception, "E000005", "", "", "");
		    }
		    //廃止フラグ＝1の場合
		    if ( !checkData.GetValueStr("SA370", 0, 0, "flg_haishi").equals("1") ) {
		    	this.em.ThrowException(ExceptionKind.一般Exception, "E000004", "", "", "");
		    }
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
