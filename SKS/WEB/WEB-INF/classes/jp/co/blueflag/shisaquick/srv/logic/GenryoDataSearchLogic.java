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
 * 原料分析情報マスタメンテ：原料分析情報検索DB処理
 *  : 原料分析情報マスタメンテ：原料分析情報を検索する。
 * @author jinbo
 * @since  2009/04/06
 */
public class GenryoDataSearchLogic extends LogicBase{
	
	/**
	 * 原料分析情報マスタメンテ：原料分析情報検索DB処理 
	 * : インスタンス生成
	 */
	public GenryoDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原料分析情報マスタメンテ：原料分析情報取得SQL作成
	 *  : 原料分析情報を取得するSQLを作成。
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
			strSql = genryoDataCreateSQL(reqData, strSql);
			
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
			storageGenryouData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ検索処理に失敗しました。");
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
	 * 原料分析情報取得SQL作成
	 *  : 原料分析情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			String strJokenKbn1 = null;
			String strJokenKbn2 = null;
			String strGenryoCd = null;
			String strGenryoName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
//			String dataId = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");
//			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + UserInfoData.getId_user();

			//対象原料（新規原料）の取得
			strJokenKbn1 = reqData.getFieldVale(0, 0, "kbn_joken1");
			//対象原料（既存原料）の取得
			strJokenKbn2 = reqData.getFieldVale(0, 0, "kbn_joken2");
			//原料コードの取得
			strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//原料名の取得
			strGenryoName = reqData.getFieldVale(0, 0, "nm_genryo");
			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//工場コードの取得
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojo");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//権限データID取得
//			for (int i = 0; i < UserInfoData.getId_gamen().size(); i++) {
//				if (UserInfoData.getId_gamen().get(i).toString().equals("20")){
//					//原料分析情報マスタメンテナンス画面のデータIDを設定
//					dataId = UserInfoData.getId_data().get(i).toString();
//				}
//			}
			
			//SQL文の作成
			strAllSql.append("SELECT");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append(" ,tbl.cd_kaisha");
			strAllSql.append(" ,tbl.cd_genryo");
			strAllSql.append(" ,tbl.nm_genryo");
			strAllSql.append(" ,CASE WHEN tbl.ritu_sakusan IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_sakusan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_sakusan)) END as ritu_sakusan");
			strAllSql.append(" ,CASE WHEN tbl.ritu_shokuen IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_shokuen),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_shokuen)) END as ritu_shokuen");
			strAllSql.append(" ,CASE WHEN tbl.ritu_sousan IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_sousan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_sousan)) END as ritu_sousan");
			strAllSql.append(" ,CASE WHEN tbl.ritu_abura IS NULL THEN ISNULL(CONVERT(VARCHAR,tbl.ritu_abura),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),tbl.ritu_abura)) END as ritu_abura");
			strAllSql.append(" ,ISNULL(tbl.hyojian,'') as hyojian");
			strAllSql.append(" ,ISNULL(tbl.tenkabutu,'') as tenkabutu");
			strAllSql.append(" ,ISNULL(tbl.memo,'') as memo");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo1,'') as no_eiyo1");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo2,'') as no_eiyo2");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo3,'') as no_eiyo3");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo4,'') as no_eiyo4");
			strAllSql.append(" ,ISNULL(tbl.no_eiyo5,'') as no_eiyo5");
			strAllSql.append(" ,ISNULL(tbl.wariai1,'') as wariai1");
			strAllSql.append(" ,ISNULL(tbl.wariai2,'') as wariai2");
			strAllSql.append(" ,ISNULL(tbl.wariai3,'') as wariai3");
			strAllSql.append(" ,ISNULL(tbl.wariai4,'') as wariai4");
			strAllSql.append(" ,ISNULL(tbl.wariai5,'') as wariai5");
			strAllSql.append(" ,ISNULL(tbl.kbn_haishi,'') as kbn_haishi");
			strAllSql.append(" ,ISNULL(tbl.nm_haishi,'') as nm_haishi");
			strAllSql.append(" ,ISNULL(tbl.cd_kakutei, '') as cd_kakutei");
			strAllSql.append(" ,ISNULL(tbl.dt_konyu, '') as dt_konyu");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append(" FROM (");
			
			strSql.append("SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY M402.cd_genryo)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,M402.cd_kaisha");
			strSql.append(" ,M402.cd_genryo");
			strSql.append(" ,M402.nm_genryo");
			strSql.append(" ,M401.ritu_sakusan");
			strSql.append(" ,M401.ritu_shokuen");
			strSql.append(" ,M401.ritu_sousan");
			strSql.append(" ,M401.ritu_abura");
			strSql.append(" ,M401.hyojian");
			strSql.append(" ,M401.tenkabutu");
			strSql.append(" ,M401.memo");
			strSql.append(" ,M401.no_eiyo1");
			strSql.append(" ,M401.no_eiyo2");
			strSql.append(" ,M401.no_eiyo3");
			strSql.append(" ,M401.no_eiyo4");
			strSql.append(" ,M401.no_eiyo5");
			strSql.append(" ,M401.wariai1");
			strSql.append(" ,M401.wariai2");
			strSql.append(" ,M401.wariai3");
			strSql.append(" ,M401.wariai4");
			strSql.append(" ,M401.wariai5");
			strSql.append(" ,M401.kbn_haishi");
			strSql.append(" ,CASE M401.kbn_haishi WHEN 0 THEN '使用可能' WHEN 1 THEN '廃止' END as nm_haishi");
			strSql.append(" ,convert(varchar,M401.cd_kakutei) as cd_kakutei");
			strSql.append(" ,convert(varchar(10),M402.dt_konyu,111) as dt_konyu");
			strSql.append(" FROM ma_genryokojo M402");
			strSql.append("      LEFT JOIN ma_genryo M401");
			strSql.append("      ON M402.cd_kaisha = M401.cd_kaisha");
			strSql.append("      AND M402.cd_genryo = M401.cd_genryo");
			strSql.append(" WHERE M402.cd_kaisha = ");
			strSql.append(strKaishaCd);
			strSql.append(" AND M402.cd_busho IN (");
			strSql.append(strKojoCd);
			strSql.append("," + ConstManager.getConstValue(ConstManager.Category.設定, "SHINKIGENRYO_BUSHOCD") + ")");

			//原料コードが入力されている場合
			if (!strGenryoCd.equals("")) {
				strSql.append(" AND M401.cd_genryo = '");
				strSql.append(strGenryoCd);
				strSql.append("'");
			}
			
			//原料名が入力されている場合
			if (!strGenryoName.equals("")) {
				strSql.append(" AND M402.nm_genryo LIKE '%");
				strSql.append(strGenryoName);
				strSql.append("%'");
			}

			//新規原料が検索対象の場合
			if (!strJokenKbn1.equals("") && strJokenKbn2.equals("")) {
				strSql.append(" AND SUBSTRING(M401.cd_genryo,1,1) > '9'");
			
			//既存原料が検索対象の場合
			}else if (strJokenKbn1.equals("") && !strJokenKbn2.equals("")) {
				strSql.append(" AND SUBSTRING(M401.cd_genryo,1,1) <= '9'");
			}
			
			//検索条件権限設定
//			//担当会社
//			if(dataId.equals("1")) {
//				strSql.append(" AND M402.cd_kaisha in ( ");
//				strSql.append(strSqlTanto + " ) ");
//
//			//自工場分
//			} else if (dataId.equals("2")) { 
//				strSql.append(" AND M402.cd_busho = ");
//				strSql.append(UserInfoData.getCd_busho());
//			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.no_row ");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * 原料分析情報マスタメンテ：原料分析情報パラメーター格納
	 *  : 原料分析情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryouData(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "no_row", items[0].toString());
				resTable.addFieldVale(i, "cd_kaisha", items[1].toString());
				resTable.addFieldVale(i, "cd_genryo", items[2].toString());
				resTable.addFieldVale(i, "nm_genryo", items[3].toString());
				resTable.addFieldVale(i, "ritu_sakusan", items[4].toString());
				resTable.addFieldVale(i, "ritu_shokuen", items[5].toString());
				resTable.addFieldVale(i, "ritu_sousan", items[6].toString());
				resTable.addFieldVale(i, "ritu_abura", items[7].toString());
				resTable.addFieldVale(i, "hyojian", items[8].toString());
				resTable.addFieldVale(i, "tenkabutu", items[9].toString());
				resTable.addFieldVale(i, "memo", items[10].toString());
				resTable.addFieldVale(i, "no_eiyo1", items[11].toString());
				resTable.addFieldVale(i, "no_eiyo2", items[12].toString());
				resTable.addFieldVale(i, "no_eiyo3", items[13].toString());
				resTable.addFieldVale(i, "no_eiyo4", items[14].toString());
				resTable.addFieldVale(i, "no_eiyo5", items[15].toString());
				resTable.addFieldVale(i, "wariai1", items[16].toString());
				resTable.addFieldVale(i, "wariai2", items[17].toString());
				resTable.addFieldVale(i, "wariai3", items[18].toString());
				resTable.addFieldVale(i, "wariai4", items[19].toString());
				resTable.addFieldVale(i, "wariai5", items[20].toString());
				resTable.addFieldVale(i, "kbn_haishi", items[21].toString());
				resTable.addFieldVale(i, "nm_haishi", items[22].toString());
				resTable.addFieldVale(i, "cd_kakutei", items[23].toString());
				resTable.addFieldVale(i, "dt_konyu", items[24].toString());
				resTable.addFieldVale(i, "list_max_row", items[25].toString());
				resTable.addFieldVale(i, "max_row", items[26].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}