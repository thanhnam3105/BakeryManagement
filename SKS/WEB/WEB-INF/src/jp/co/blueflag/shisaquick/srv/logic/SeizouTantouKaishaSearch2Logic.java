package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 製造担当会社追加：製造担当会社検索DB処理
 *  : 製造担当会社追加：製造担当会社情報を検索する。
 * @author jinbo
 * @since  2009/04/08
 */
public class SeizouTantouKaishaSearch2Logic extends LogicBase{

	/**
	 * 製造担当会社検索コンストラクタ 
	 * : インスタンス生成
	 */
	public SeizouTantouKaishaSearch2Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 製造担当会社追加：製造担当会社データ取得SQL作成
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

			//テーブルBean取得
			RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//行Bean取得
			RequestResponsRowBean reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);
			
			//SQL文の作成
			strSql = seizouTantouKaisha2DataCreateSQL(reqData, strSql);
			
			//DBインスタンス生成
			createSearchDB();
			//検索実行
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//レスポンスデータの形成
			storageSeizouTantouKaisha2Data(lstRecset, resKind.getTableItem(0));
			
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
	 * 製造担当会社情報取得SQL作成
	 *  : 製造担当会社情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantouKaisha2DataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			String strKaishaName = null;
			String strPageNo = null;
			String dataId = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//会社名の取得
			strKaishaName = reqData.getFieldVale(0, 0, "nm_kaisha");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//担当者マスタメンテナンス画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL文の作成	
			strAllSql.append("SELECT");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append(" ,tbl.cd_kaisha");
			strAllSql.append(" ,tbl.nm_kaisha");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append(" FROM (");

			strSql.append("SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY cd_kaisha)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY cd_kaisha)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY cd_kaisha)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,RIGHT('00000' + CONVERT(varchar,cd_kaisha),5) as cd_kaisha");
			strSql.append(" ,nm_kaisha");
			strSql.append(" FROM (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M104 ");

			//会社名が入力されている場合
			if (!strKaishaName.equals("")) {
				strSql.append(" WHERE nm_kaisha LIKE '%");
				strSql.append(strKaishaName);
				strSql.append("%'");
			}
			
			//検索条件権限設定
			//自分のみ
			if(dataId.equals("1") || dataId.equals("9")) {
				//処理なし
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.cd_kaisha ");
			
			strSql = strAllSql;
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造担当会社検索DB処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}
	
	/**
	 * 製造担当会社追加：製造担当会社データパラメーター格納
	 *  : 製造担当会社データ情報をレスポンスデータへ格納する。
	 * @param lstSeizouTantouShaData2 : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeizouTantouKaisha2Data(List<?> lstSeizouTantouShaData2, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstSeizouTantouShaData2.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstSeizouTantouShaData2.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "no_row", items[0].toString());
				resTable.addFieldVale(i, "cd_kaisha", items[1].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[2].toString());
				resTable.addFieldVale(i, "list_max_row", items[3].toString());
				resTable.addFieldVale(i, "max_row", items[4].toString());

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造担当会社検索DB処理に失敗しました。");

		} finally {

		}

	}

}
