package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW630】 原料一覧画面リクエスト検索 インプットチェック
 *
 */
public class GenryoIchiranSearchInputCheck extends InputCheck {
	
	/**
	 * コンストラクタ
	 */
	public GenryoIchiranSearchInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
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

			//SA510のインプットチェックを行う。
			genryoSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 分析原料検索条件値チェック
	 *  : SA510のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//会社コードの必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "cd_kaisha"),"会社コード");
			//部署コードの必須入力チェック
		    //super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "cd_busho"),"部署コード");
			//原料コードの入力桁数チェック
		    super.sizeCheckLen(checkData.GetValueStr("SA510", 0, 0, "cd_genryo"),"コード",11);
			//原料名の入力桁数チェック
		    super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA510", 0, 0, "nm_genryo"),"名前",30,60);
			//選択ページ番号の必須入力チェック
		    super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "num_selectRow"),"選択ページ番号");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
