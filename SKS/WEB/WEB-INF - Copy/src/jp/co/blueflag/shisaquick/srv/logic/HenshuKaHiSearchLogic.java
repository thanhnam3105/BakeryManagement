package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * コンボ用：編集可否情報検索DB処理
 *  : コンボ用：編集可否情報を検索する。
 * @author jinbo
 * @since  2009/04/07
 */
public class HenshuKaHiSearchLogic extends LogicBase{
	
	/**
	 * コンボ用：編集可否情報検索DB処理 
	 * : インスタンス生成
	 */
	public HenshuKaHiSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：編集可否情報取得SQL作成
	 *  : 編集可否コンボボックス情報を取得するSQLを作成。
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageHenshuKaHiCmb(resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return resKind;

	}	

	/**
	 * コンボ用：編集可否パラメーター格納
	 *  : 編集可否コンボボックス情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHenshuKaHiCmb(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strEditFlg = null;
			
			for (int i = 0; i < 2; i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				//結果をレスポンスデータに格納
				if (i == 0) {
					//編集不可
					strEditFlg = ConstManager.getConstValue(ConstManager.Category.設定, "EDIT_FLG_IMPOSSIBLE");
				} else {
					//編集可
					strEditFlg = ConstManager.getConstValue(ConstManager.Category.設定, "EDIT_FLG_POSSIBLE");
				}
				resTable.addFieldVale(i, "cd_editflg", strEditFlg.split(",")[0]);
				resTable.addFieldVale(i, "nm_editflg", strEditFlg.split(",")[1]);
	
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	
}
