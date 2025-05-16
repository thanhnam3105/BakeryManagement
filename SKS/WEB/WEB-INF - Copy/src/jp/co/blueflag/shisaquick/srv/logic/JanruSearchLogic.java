package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * コンボ用：ジャンル情報検索DB処理
 *  : コンボ用：ジャンル情報を検索する。
 *  機能ID：SA070　
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class JanruSearchLogic extends LogicBase{
	
	/**
	 * コンボ用：ジャンル情報検索DB処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public JanruSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：ジャンル情報取得SQL作成
	 *  : ジャンルコンボボックス情報を取得するSQLを作成。
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

		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			strSql = createSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageJanruCmb(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
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
	 * ジャンル取得SQL作成
	 *  : ジャンルを取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String categoryCd = null;
			String userId = null;
			String kinoId = null;
			String gamenId = null;
			String dataId = null;

			//カテゴリコードの取得
			categoryCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_category");
			//ユーザIDの取得
			userId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_user");
			//画面IDの取得
			gamenId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					
					//機能IDを設定
					kinoId = userInfoData.getId_kino().get(i).toString();
					
					//データIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成
			strSql.append("SELECT M302.cd_literal, M302.nm_literal, ISNULL(M302.cd_group, 0) ");
			strSql.append(" FROM ma_literal M302");

			//試作データ一覧画面
			if (gamenId.equals("10")) {
				
				//機能ID：閲覧
				if(kinoId.equals("10")){
					if(dataId.equals("1") || dataId.equals("2") || dataId.equals("3")) {	//同一グループ且つ～
						strSql.append(" ,ma_user M101");
						strSql.append(" WHERE M302.cd_category = '");
						strSql.append(categoryCd);
						strSql.append("' ");
						strSql.append(" AND (M302.cd_group IS NULL");
						strSql.append(" OR M302.cd_group = M101.cd_group)");
						strSql.append(" AND M101.id_user = ");
						strSql.append(userInfoData.getId_user());
					} else if (dataId.equals("4") || dataId.equals("9")) {	//自工場分 or 全て
						strSql.append(" WHERE M302.cd_category = '");
						strSql.append(categoryCd);
						strSql.append("' ");
					}
				}
				//機能ID：原価試算
				else if(kinoId.equals("60")){
					
					//1件も取得しない
					strSql.append(" WHERE 1 = 0");
				}
				
				
			}
			strSql.append(" ORDER BY M302.no_sort");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：ジャンルパラメーター格納
	 *  : ジャンルコンボボックス情報をレスポンスデータへ格納する。
	 * @param lstJanruCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageJanruCmb(List<?> lstJanruCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstJanruCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstJanruCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				resTable.addFieldVale(i, "cd_group"  , items[2].toString());

			}

			if (lstJanruCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_literal", "");
				resTable.addFieldVale(0, "nm_literal", "");
				resTable.addFieldVale(0, "cd_group"  , "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	
}
