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
 * コンボ用：チーム情報検索DB処理
 *  : コンボ用：チーム情報を検索する。
 *  機能ID：SA080　
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class TeamSearchLogic extends LogicBase{
	
	/**
	 * コンボ用：チーム情報検索DB処理 
	 * : インスタンス生成
	 */
	public TeamSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：チーム情報取得SQL作成
	 *  : チームコンボボックス情報を取得するSQLを作成。
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
			storageTeamCmb(lstRecset, resKind.getTableItem(0));
			
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
	 * チーム取得SQL作成
	 *  : チームを取得するSQLを作成。
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
			String groupCd = null;
			String userId = null;
			String gamenId = null;
			String ShoriKbn = null;
			String dataId = null;

			//グループコードの取得
			groupCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_group");
			//処理区分の取得
			ShoriKbn = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("kbn_shori");
			//ユーザIDの取得
			userId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_user");
			//画面IDの取得
			gamenId = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//データIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//セッション使用する場合
			if (ShoriKbn.equals("2")) {
				//グループコードにセッション情報を格納
				groupCd = userInfoData.getCd_group();
			}
			
			//SQL文の作成
			strSql.append("SELECT ");
			strSql.append("  cd_team");
			strSql.append(" ,nm_team");
			strSql.append(" FROM ma_team");
			
			//担当者マスタメンテ以外の場合
			if (!gamenId.equals("90")) {
				strSql.append(" WHERE cd_group = ");
				strSql.append(groupCd);

				//試作データ一覧画面
				if (gamenId.equals("10")) {
					if(dataId.equals("1") || dataId.equals("3")) {	//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上） or 同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql.append(" AND cd_team = ");
						strSql.append(userInfoData.getCd_team());
					} else if (dataId.equals("2") || dataId.equals("4") || dataId.equals("9")) {	//同一グループ且つ担当会社 or 自工場分 or 全て
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
					if(dataId.equals("1") || dataId.equals("9")) {	//同一グループ or 全て
						//処理なし
					}
	
				//グループマスタメンテ画面
				} else if (gamenId.equals("70")) {
					if(dataId.equals("9")) {	//全て
						//処理なし
					}
				}
			
			//担当者マスタメンテ画面の場合
			} else  {
				//セッション使用する場合
				if (ShoriKbn.equals("2")) {
					strSql.append(" WHERE cd_group = (");
					strSql.append(" SELECT cd_group FROM ma_user WHERE id_user = ");
					strSql.append(userId + ")");
				} else {
					strSql.append(" WHERE cd_group = ");
					strSql.append(groupCd);
				}
				
				if(dataId.equals("1")) {    //自分のみ
					strSql.append(" AND cd_team = ");
					strSql.append(userInfoData.getCd_team());

				} else if(dataId.equals("9")) {	//全て
					//処理なし
				}
			}
			strSql.append(" ORDER BY cd_team");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：チームパラメーター格納
	 *  : チームコンボボックス情報をレスポンスデータへ格納する。
	 * @param lstTeamCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTeamCmb(List<?> lstTeamCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstTeamCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstTeamCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_team", items[0].toString());
				resTable.addFieldVale(i, "nm_team", items[1].toString());

			}

			if (lstTeamCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "nm_team", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	
}
