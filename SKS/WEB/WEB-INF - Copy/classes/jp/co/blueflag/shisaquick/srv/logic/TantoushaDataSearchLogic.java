package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 担当者検索：担当者情報検索DB処理
 *  : 担当者検索：担当者情報を検索する。
 * @author jinbo
 * @since  2009/04/07
 */
public class TantoushaDataSearchLogic extends LogicBase{

	/**
	 * 担当者検索：担当者情報検索DB処理 
	 * : インスタンス生成
	 */
	public TantoushaDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者検索：担当者情報取得SQL作成
	 *  : 担当者情報を取得するSQLを作成。
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

			//SQL文の作成
			strSql = genryoData2CreateSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageTantoushaData(lstRecset, resKind.getTableItem(strTableNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");
			
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
	 * 担当者情報取得SQL作成
	 *  : 担当者情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();
		
		try {
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			String strUserId = null;
			String strUserName = null;
			String strKaishaCd = null;
			String strBushoCd = null;
			String strGroupCd = null;
			String strTeamCd = null;
			String strPageNo = null;
			String dataId = null;
			
			//ユーザIDの取得
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			//ユーザ名の取得
			strUserName = reqData.getFieldVale(0, 0, "nm_user");
			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//部署コードの取得
			strBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//グループコードの取得
			strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
			//部署コードの取得
			strTeamCd = reqData.getFieldVale(0, 0, "cd_team");
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
			strAllSql.append(" SELECT ");
			strAllSql.append("	tbl.id_user");
			strAllSql.append(" ,tbl.nm_user");
			strAllSql.append(" ,tbl.cd_kengen");
			strAllSql.append(" ,ISNULL(tbl.nm_kengen,'') as nm_kengen");
			strAllSql.append(" ,tbl.cd_kaisha");
			strAllSql.append(" ,ISNULL(tbl.nm_kaisha,'') as nm_kaisha");
			strAllSql.append(" ,tbl.cd_busho");
			strAllSql.append(" ,ISNULL(tbl.nm_busho,'') as nm_busho");
			strAllSql.append(" ,tbl.cd_group");
			strAllSql.append(" ,ISNULL(tbl.nm_group,'') as nm_group");
			strAllSql.append(" ,tbl.cd_team");
			strAllSql.append(" ,ISNULL(tbl.nm_team,'') as nm_team");
			strAllSql.append(" ,tbl.cd_yakushoku");
			strAllSql.append(" ,ISNULL(tbl.nm_yakushoku, '') as nm_yakushoku");
			strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,tbl.cd_tantokaisha), '') as cd_tantokaisha");
			strAllSql.append(" ,ISNULL(tbl.nm_tantokaisha, '') as nm_tantokaisha");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append(" FROM (");
			
			strAllSql.append(" SELECT ");
			strAllSql.append("	datatbl.PageNO ");
			strAllSql.append(" ,datatbl.id_user");
			strAllSql.append(" ,datatbl.nm_user");
			strAllSql.append(" ,datatbl.cd_kengen");
			strAllSql.append(" ,datatbl.nm_kengen");
			strAllSql.append(" ,datatbl.cd_kaisha");
			strAllSql.append(" ,datatbl.nm_kaisha");
			strAllSql.append(" ,datatbl.cd_busho");
			strAllSql.append(" ,datatbl.nm_busho");
			strAllSql.append(" ,datatbl.cd_group");
			strAllSql.append(" ,datatbl.nm_group");
			strAllSql.append(" ,datatbl.cd_team");
			strAllSql.append(" ,datatbl.nm_team");
			strAllSql.append(" ,datatbl.cd_yakushoku");
			strAllSql.append(" ,datatbl.nm_yakushoku");
			strAllSql.append(" ,M107.cd_tantokaisha");
			strAllSql.append(" ,M1043.nm_kaisha as nm_tantokaisha");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT ");
			strSql.append("	Convert(int,(ROW_NUMBER() OVER (ORDER BY M101.id_user)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,RIGHT('0000000000' + CONVERT(varchar,M101.id_user),10) as id_user");
			strSql.append(" ,M101.nm_user");
			strSql.append(" ,M101.cd_kengen");
			strSql.append(" ,M102.nm_kengen");
			strSql.append(" ,M101.cd_kaisha");
			strSql.append(" ,M1041.nm_kaisha");
			strSql.append(" ,M101.cd_busho");
			strSql.append(" ,M1042.nm_busho");
			strSql.append(" ,M101.cd_group");
			strSql.append(" ,M105.nm_group");
			strSql.append(" ,M101.cd_team");
			strSql.append(" ,M106.nm_team");
			strSql.append(" ,M101.cd_yakushoku");
			strSql.append(" ,M302.nm_literal as nm_yakushoku");
//			strSql.append(" ,M107.cd_tantokaisha");
//			strSql.append(" ,M1043.nm_kaisha as nm_tantokaisha");
			strSql.append(" FROM ma_user M101");
			strSql.append("      LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1041");
			strSql.append("      ON M1041.cd_kaisha = M101.cd_kaisha");
			strSql.append("      LEFT JOIN ma_busho M1042");
			strSql.append("      ON M1042.cd_kaisha = M101.cd_kaisha");
			strSql.append("      AND M1042.cd_busho = M101.cd_busho");
			strSql.append("      LEFT JOIN ma_kengen M102");
			strSql.append("      ON M101.cd_kengen = M102.cd_kengen");
			strSql.append("      LEFT JOIN ma_literal M302");
			strSql.append("      ON M302.cd_category = 'K_yakusyoku'");
			strSql.append("      AND M302.cd_literal = M101.cd_yakushoku");
			strSql.append("      LEFT JOIN ma_group M105");
			strSql.append("      ON M105.cd_group = M101.cd_group");
			strSql.append("      LEFT JOIN ma_team M106");
			strSql.append("      ON M106.cd_group = M101.cd_group");
			strSql.append("      AND M106.cd_team = M101.cd_team");
//			strSql.append("      LEFT JOIN ma_tantokaisya M107");
//			strSql.append("      ON M107.id_user = M101.id_user");
//			strSql.append("      LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1043");
//			strSql.append("      ON M1043.cd_kaisha = M107.cd_tantokaisha");
			
			//ユーザIDが入力されている場合
			if (!strUserId.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.id_user = '");
				strWhere.append(strUserId + "'");
			}
			
			//ユーザ名が入力されている場合
			if (!strUserName.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.nm_user = '");
				strWhere.append(strUserName + "'");
			}
			
			//会社が入力されている場合
			if (!strKaishaCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_kaisha = ");
				strWhere.append(strKaishaCd);
			}

			//部署が入力されている場合
			if (!strBushoCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_busho = ");
				strWhere.append(strBushoCd);
			}

			//グループが入力されている場合
			if (!strGroupCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_group = ");
				strWhere.append(strGroupCd);
			}

			//チームが入力されている場合
			if (!strTeamCd.equals("")) {
				if (strWhere.toString().equals("")) {
					strWhere.append(" WHERE");
				} else {
					strWhere.append(" AND");
				}
				strWhere.append(" M101.cd_team = ");
				strWhere.append(strTeamCd);
			}
			strSql.append(strWhere);
			
			//検索条件権限設定
			//自分のみ
			if(dataId.equals("1")) {
				if (strWhere.toString().equals("")) {
					strSql.append(" WHERE");
				} else {
					strSql.append(" AND");
				}
				strSql.append(" M101.id_user = ");
				strSql.append(userInfoData.getId_user());

			//全て
			} else if (dataId.equals("9")) { 
				//なし
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS datatbl ");
			
			strAllSql.append(" LEFT JOIN ma_tantokaisya M107");
			strAllSql.append(" ON M107.id_user = datatbl.id_user");
			strAllSql.append(" LEFT JOIN (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M1043");
			strAllSql.append(" ON M1043.cd_kaisha = M107.cd_tantokaisha");

			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.id_user ");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
			strWhere = null;
		}
		return strSql;
	}

	/**
	 * 担当者検索：担当者情報パラメーター格納
	 *  : 担当者情報をレスポンスデータへ格納する。
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaData(List<?> lstTantouShaData, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			int iRow = 1;
			String strUserId = "";
			String strTantoKaishaCd = "";
			String strTantoKaishaName = "";

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(iRow-1, "flg_return", "true");
				resTable.addFieldVale(iRow-1, "msg_error", "");
				resTable.addFieldVale(iRow-1, "no_errmsg", "");
				resTable.addFieldVale(iRow-1, "nm_class", "");
				resTable.addFieldVale(iRow-1, "cd_error", "");
				resTable.addFieldVale(iRow-1, "msg_system", "");
				
				Object[] items = (Object[]) lstTantouShaData.get(i);

				//結果をレスポンスデータに格納
				if (!strUserId.equals(items[0].toString())) {
					//ユーザIDが前レコードと異なる場合
					
					if (i > 0 && i < lstTantouShaData.size()) {
						//先頭行以外で且つ最終行以外
						
						//recに担当会社を設定
						resTable.addFieldVale(iRow-1, "cd_tantokaisha", strTantoKaishaCd);
						resTable.addFieldVale(iRow-1, "nm_tantokaisha", strTantoKaishaName);

						iRow += 1;
					}

					resTable.addFieldVale(iRow-1, "no_row", Integer.toString(iRow));
					resTable.addFieldVale(iRow-1, "id_user", items[0].toString());
					resTable.addFieldVale(iRow-1, "nm_user", items[1].toString());
					resTable.addFieldVale(iRow-1, "cd_kengen", items[2].toString());
					resTable.addFieldVale(iRow-1, "nm_kengen", items[3].toString());
					resTable.addFieldVale(iRow-1, "cd_kaisha", items[4].toString());
					resTable.addFieldVale(iRow-1, "nm_kaisha", items[5].toString());
					resTable.addFieldVale(iRow-1, "cd_busho", items[6].toString());
					resTable.addFieldVale(iRow-1, "nm_busho", items[7].toString());
					resTable.addFieldVale(iRow-1, "cd_group", items[8].toString());
					resTable.addFieldVale(iRow-1, "nm_group", items[9].toString());
					resTable.addFieldVale(iRow-1, "cd_team", items[10].toString());
					resTable.addFieldVale(iRow-1, "nm_team", items[11].toString());
					resTable.addFieldVale(iRow-1, "cd_yakushoku", items[12].toString());
					resTable.addFieldVale(iRow-1, "nm_yakushoku", items[13].toString());
					resTable.addFieldVale(iRow-1, "list_max_row", items[16].toString());
					resTable.addFieldVale(iRow-1, "max_row", items[17].toString());
				}

				//ユーザIDが前回のレコードと同じ場合
				if (strUserId.equals(items[0].toString())) {
					strTantoKaishaCd += "," + items[14].toString();
					strTantoKaishaName += "\n" + items[15].toString();

				//ユーザIDが前回のレコードと異なる場合
				} else {
					//データの退避
					strUserId = items[0].toString();
					strTantoKaishaCd = items[14].toString();
					strTantoKaishaName = items[15].toString();
				}
			}

			resTable.addFieldVale(iRow-1, "cd_tantokaisha", strTantoKaishaCd);
			resTable.addFieldVale(iRow-1, "nm_tantokaisha", strTantoKaishaName);

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}
