package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * コンボ用：担当者情報検索DB処理
 *  : コンボ用：担当者情報を検索する。
 *  機能ID：SA250　
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class TantoushaSearchLogic extends LogicBase{

	/**
	 * コンボ用：担当者情報検索DB処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public TantoushaSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：担当者情報取得SQL作成
	 *  : 担当者コンボボックス情報を取得するSQLを作成。
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
			storageTantoushaCmb(lstRecset, resKind.getTableItem(0));
			
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
	 * 担当者取得SQL作成
	 *  : 担当者を取得するSQLを作成。
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
			String teamCd = null;
			String userId = null;
			String gamenId = null;
			String ShoriKbn = null;
			String dataId = null;

			//グループコードの取得
			groupCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_group");
			//チームコードの取得
			teamCd = ((RequestResponsRowBean) ((RequestResponsTableBean) reqData.GetItem(0)).GetItem(0)).GetItemValue("cd_team");
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
				teamCd = userInfoData.getCd_team();
			}
			
			//SQL文の作成
			strSql.append("SELECT id_user, nm_user, cd_kaisha, cd_busho, cd_group, cd_team, cd_yakushoku");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE cd_group = " + groupCd);
			strSql.append(" AND   cd_team = " + teamCd);
			
			//試作データ一覧画面
			if (gamenId.equals("10")) {
				if(dataId.equals("1") || dataId.equals("3")) {	//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上） or 同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
					strSql.append(" AND id_user = ");
					strSql.append(userInfoData.getId_user());
					
					//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
					if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
							<= Integer.parseInt(userInfoData.getCd_literal())) {
						
						strSql.append(" OR (");
						strSql.append(" cd_group = " + groupCd);
						strSql.append(" AND cd_team = " + teamCd);
						strSql.append(" AND cd_yakushoku <= '");
						strSql.append(userInfoData.getCd_literal());
						strSql.append("')");
					}
				} else if(dataId.equals("4")) {	//自工場分
					strSql.append(" AND cd_kaisha = ");
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
				} else if (dataId.equals("2") || dataId.equals("9")) {	//同一グループ且つ担当会社  or 全て
					//処理なし
				}
			}
			strSql.append(" ORDER BY id_user");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：担当者パラメーター格納
	 *  : 担当者コンボボックス情報をレスポンスデータへ格納する。
	 * @param lstTantoushaCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaCmb(List<?> lstTantoushaCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstTantoushaCmb.size(); i++) {
				//該当データが有る場合

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstTantoushaCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "id_user", items[0].toString());
				resTable.addFieldVale(i, "nm_user", items[1].toString());
				resTable.addFieldVale(i, "cd_kaisha", items[2].toString());
				resTable.addFieldVale(i, "cd_busho", items[3].toString());
				resTable.addFieldVale(i, "cd_group", items[4].toString());
				resTable.addFieldVale(i, "cd_team", items[5].toString());
				resTable.addFieldVale(i, "cd_yakushoku", items[6].toString());

			}

			if (lstTantoushaCmb.size() == 0) {
				//処理結果の格納
				
				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "id_user", "");
				resTable.addFieldVale(0, "nm_user", "");
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "cd_yakushoku", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}

	}
	
}
