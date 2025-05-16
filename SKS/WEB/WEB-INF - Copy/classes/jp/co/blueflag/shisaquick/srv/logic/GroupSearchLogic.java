package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * コンボ用：グループ情報検索DB処理
 *  : コンボ用：グループ情報を検索する。
 *  機能ID：SA050　
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class GroupSearchLogic extends LogicBase{

	/**
	 * コンボ用：グループ情報検索DB処理 
	 * : インスタンス生成
	 */
	public GroupSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：グループ情報取得SQL作成
	 *  : グループコンボボックス情報を取得するSQLを作成。
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
		
		//レスポンスデータ（機能）
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
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageGroupCmb(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "グループコンボボックス情報の取得に失敗しました。");
			
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
	 * グループ取得SQL作成
	 *  : グループを取得するSQLを作成。
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
			String userId = null;
			String gamenId = null;
			String dataId = null;

			//ユーザIDの取得
			userId = reqData.getFieldVale(0, 0, "id_user");
			//画面IDの取得
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//データIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成
			strSql.append("SELECT ");
			strSql.append("  cd_group");
			strSql.append(" ,nm_group");
			strSql.append(" FROM ma_group");
			
			//試作データ一覧画面
			if (gamenId.equals("10")) {
				if(dataId.equals("1") || dataId.equals("2") || dataId.equals("3")) {	//同一グループ且つ～
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("4") || dataId.equals("9")) {	//自工場分 or 全て
					//処理なし
				}

			//リテラルマスタメンテ画面
			} else if (gamenId.equals("60") || gamenId.equals("65")) {
				if (dataId == null) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							//データIDを設定
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}			
				}
				if(dataId.equals("1")) {	//同一グループ
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("9")) {	//全て
					//処理なし
				}

			//グループマスタメンテ画面
			} else if (gamenId.equals("70")) {
				if(dataId.equals("9")) {	//全て
					//処理なし
				}

			//担当者マスタメンテ画面
			} else if (gamenId.equals("90")) {
				if(dataId.equals("1")) {	//自分のみ
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("9")) {	//全て
					//処理なし
				}
			}
			strSql.append(" ORDER BY cd_group");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：グループパラメーター格納
	 *  : グループコンボボックス情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGroupCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
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
				resTable.addFieldVale(i, "cd_group", items[0].toString());
				resTable.addFieldVale(i, "nm_group", items[1].toString());

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
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "nm_group", "");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "グループコンボボックスのレスポンスデータ生成に失敗しました。");
			
		} finally {

		}
		
	}
	
}
