package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 製造担当会社検索DB処理
 *  : 製造担当会社情報を検索する。
 * @author furuta
 * @since  2009/03/29
 */
public class SeizouTantouKaishaSearchLogic extends LogicBase{

	/**
	 * 製造担当会社検索コンストラクタ 
	 * : インスタンス生成
	 */
	public SeizouTantouKaishaSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 製造担当会社データ取得SQL作成
	 *  : 製造担当会社データを取得するSQLを作成。
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

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strUserId = "";
			
			//テーブルBean取得
			RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//行Bean取得
			RequestResponsRowBean reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);
			//ユーザーID取得
			strUserId = reqRecBean.GetItemValue(0);
			
			//SQL文の作成
			strSql.append("SELECT ");
			strSql.append("B.cd_kaisha, B.nm_kaisha ");
			strSql.append("FROM ");
			strSql.append("ma_tantokaisya A JOIN ma_busho B ON A.cd_tantokaisha = B.cd_kaisha  ");
			strSql.append("WHERE A.id_user = ");
			strSql.append(strUserId);
			strSql.append(" GROUP BY B.cd_kaisha, B.nm_kaisha");
			strSql.append(" ORDER BY B.cd_kaisha");
			
			//DBインスタンス生成
			createSearchDB();
			//検索実行
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//レスポンスデータの形成
			storageSeizouTantouKaishaData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造担当会社検索DB処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}
		return resKind;
	}
	
	/**
	 * 製造担当会社データパラメーター格納
	 *  : 製造担当会社データ情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeizouTantouKaishaData(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[1].toString());

			}
			
			if (lstGroupCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "nm_kaisha", "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造担当会社検索DB処理に失敗しました。");

		} finally {

		}

	}

}
