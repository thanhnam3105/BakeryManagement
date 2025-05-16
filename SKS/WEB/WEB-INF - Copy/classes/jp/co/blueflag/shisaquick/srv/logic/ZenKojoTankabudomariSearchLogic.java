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
 * 全工場単価歩留：全工場単価歩留情報検索DB処理
 *  : 全工場単価歩留：全工場単価歩留情報を検索する。
 * @author jinbo
 * @since  2009/05/19
 */
public class ZenKojoTankabudomariSearchLogic extends LogicBase{

	private int intRecCnt = 0;			    		//工場件数
	
	/**
	 * 全工場単価歩留：全工場単価歩留情報検索DB処理 
	 * : インスタンス生成
	 */
	public ZenKojoTankabudomariSearchLogic() {
		//基底クラスのコンストラクタ
		super();
	}

	/**
	 * 全工場単価歩留：全工場単価歩留情報取得SQL作成
	 *  : 全工場単価歩留情報を取得するSQLを作成。
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
			String strGenryoKbn = null;
			
			//対象データ区分の取得
			strGenryoKbn = reqData.getFieldVale(0, 0, "kbn_data");

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			if (strGenryoKbn.equals("0")) {
				strSql = genryoDataCreateSQL(reqData, strSql);
			} else {
				strSql = shizaiDataCreateSQL(reqData, strSql);
			}
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 1){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageGenryouData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "全工場単価歩留データ検索処理に失敗しました。");
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
	 * 全工場単価歩留（原料）情報取得SQL作成
	 *  : 全工場単価歩留（原料）情報を取得するSQLを作成。
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
			String strKaishaCd = null;
			String strCd = null;
			String strName = null;
			String strTaishoKbn = null;
			String strPageNo = null;
			String strDataId = null;
			
			//最大表示行数の取得
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//コードの取得
			strCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//名前の取得
			strName = reqData.getFieldVale(0, 0, "nm_genryo");
			//出力項目の取得
			strTaishoKbn = reqData.getFieldVale(0, 0, "item_output");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("50")){
					//データIDを設定
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//工場件数の取得
			getKojoCount(strKaishaCd, strDataId);
			
			//SQL文の作成
			strAllSql.append("SELECT *");
			strAllSql.append(" FROM (");
			strAllSql.append(" SELECT");
			strAllSql.append("  MBT.no_row");
			strAllSql.append(" ,MBT.cd_genryo");
			strAllSql.append(" ,MBT.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
				strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN MBT.Rank = " + i + " THEN MBT.disp_val end)),'') as disp_val" + i);
			}
			strAllSql.append(" ,MBT.list_max_row");
			strAllSql.append(" ,MBT.max_row ");
			strAllSql.append(" ,MBT.cnt_kojo ");
			strAllSql.append(" FROM (");

			strAllSql.append(" SELECT");
			strAllSql.append("  0 as no_row");
			strAllSql.append(" ,'' as cd_genryo");
			strAllSql.append(" ,'' as nm_genryo");
			strAllSql.append(" ,MB.cd_kaisha");
		    strAllSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,MB.cd_busho),2) + ':' + MB.nm_busho as disp_val");
			strAllSql.append(" ,0 as list_max_row");
			strAllSql.append(" ,0 as max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" ,Row_Number() over(partition by MB.cd_kaisha order by MB.cd_kaisha) as Rank");
			strAllSql.append(" FROM ma_busho MB");
			strAllSql.append(" WHERE MB.cd_kaisha = ");
			strAllSql.append(strKaishaCd);
			
			//自工場の場合
			if (strDataId.equals("2")) {
				strAllSql.append(" AND MB.cd_busho = ");
				strAllSql.append(userInfoData.getCd_busho());
			}
			
			strAllSql.append(" ) as MBT");
			strAllSql.append(" GROUP BY MBT.no_row, MBT.cd_genryo ,MBT.nm_genryo, MBT.list_max_row, MBT.max_row, MBT.cnt_kojo");
			strAllSql.append(" UNION");

			strAllSql.append(" SELECT");
			strAllSql.append("  tbl3.no_row");
			strAllSql.append(" ,tbl3.cd_genryo");
			strAllSql.append(" ,tbl3.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strAllSql.append(" ,CASE WHEN tbl3.disp_val" + i + " = '' THEN ISNULL(CONVERT(VARCHAR,tbl3.disp_val" + i + "),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(7,2),tbl3.disp_val" + i + ")) END as disp_val" + i);
			}
			strAllSql.append(" ,tbl3.list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,tbl2.cd_genryo");
			strSql.append(" ,tbl2.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,tbl2.disp_val" + i);
			}
			strSql.append(" ," + strListRowMax + " AS list_max_row");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  tbl.cd_genryo");
			strSql.append(" ,tbl.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN tbl.Rank = " + i + " THEN tbl.disp_val end)),'') as disp_val" + i);
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  LIST.cd_busho");
//			strSql.append(" ,RIGHT('000000' + LIST.cd_genryo,6) as cd_genryo");
			strSql.append(" ,LIST.cd_genryo");
			strSql.append(" ,LIST.nm_genryo");
			strSql.append(" ,LIST.disp_val");
			strSql.append(" ,Row_Number() over(partition by LIST.cd_genryo order by LIST.cd_genryo) as Rank");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  M402.cd_busho");
			strSql.append(" ,M402.cd_genryo");
			strSql.append(" ,M402.nm_genryo");
			//単価が対象の場合
			if (strTaishoKbn.equals("0")) {
				strSql.append(" ,M402.tanka as disp_val");
			//歩留が対象の場合
			} else {
				strSql.append(" ,M402.budomari as disp_val");
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append(" c.*, d.tanka, d.budomari");
			strSql.append(" FROM (");
			strSql.append(" SELECT DISTINCT");
			strSql.append(" a.cd_kaisha, a.cd_genryo, a.nm_genryo, b.cd_busho, b.nm_busho");
			strSql.append(" FROM ma_genryokojo a, ma_busho b");
			strSql.append(" WHERE a.cd_kaisha = ");
			strSql.append(strKaishaCd);
			
			//自工場の場合
			if (strDataId.equals("2")) {
				strSql.append(" AND a.cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}
			
			strSql.append(" AND a.cd_kaisha = b.cd_kaisha");
			strSql.append(" ) as c");
			strSql.append(" LEFT JOIN ma_genryokojo d");
			strSql.append(" ON c.cd_kaisha = d.cd_kaisha ");
			strSql.append(" AND c.cd_busho = d.cd_busho");
			strSql.append(" AND c.cd_genryo = d.cd_genryo ");		
			strSql.append(" ) as M402");		
			strSql.append(" WHERE M402.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//原料コードが入力されている場合
			if (!strCd.equals("")) {
				strSql.append(" AND M402.cd_genryo = '");
				strSql.append(strCd);
				strSql.append("'");
			}
			
			//原料名が入力されている場合
			if (!strName.equals("")) {
				strSql.append(" AND M402.nm_genryo LIKE '%");
				strSql.append(strName);
				strSql.append("%'");
			}
			
			strSql.append(" ) AS LIST");
			strSql.append(" ) AS tbl");
			strSql.append(" GROUP BY tbl.cd_genryo,tbl.nm_genryo");
			strSql.append(" ) AS tbl2");

			strAllSql.append(strSql);
			strAllSql.append(" ) AS tbl3");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");

			strAllSql.append(" WHERE tbl3.PageNO = ");
			strAllSql.append(strPageNo);
			strAllSql.append(" ) as T");
			strAllSql.append(" ORDER BY T.no_row");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "全工場単価歩留（原料）データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * 全工場単価歩留（資材）情報取得SQL作成
	 *  : 全工場単価歩留（資材）情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer shizaiDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			String strKaishaCd = null;
			String strCd = null;
			String strName = null;
			String strTaishoKbn = null;
			String strPageNo = null;
			String strDataId = null;
			
			//最大表示行数の取得
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//コードの取得
			strCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//名前の取得
			strName = reqData.getFieldVale(0, 0, "nm_genryo");
			//出力項目の取得
			strTaishoKbn = reqData.getFieldVale(0, 0, "item_output");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("50")){
					//データIDを設定
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//工場件数の取得
			getKojoCount(strKaishaCd, strDataId);
			
			//SQL文の作成
			strAllSql.append("SELECT *");
			strAllSql.append(" FROM (");
			strAllSql.append(" SELECT");
			strAllSql.append("  MBT.no_row");
			strAllSql.append(" ,MBT.cd_shizai");
			strAllSql.append(" ,MBT.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
				strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN MBT.Rank = " + i + " THEN MBT.disp_val end)),'') as disp_val" + i);
			}
			strAllSql.append(" ,MBT.list_max_row");
			strAllSql.append(" ,MBT.max_row ");
			strAllSql.append(" ,MBT.cnt_kojo ");
			strAllSql.append(" FROM (");

			strAllSql.append(" SELECT");
			strAllSql.append("  0 as no_row");
			strAllSql.append(" ,'' as cd_shizai");
			strAllSql.append(" ,'' as nm_shizai");
			strAllSql.append(" ,MB.cd_kaisha");
		    strAllSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,MB.cd_busho),2) + ':' + MB.nm_busho as disp_val");
			strAllSql.append(" ,0 as list_max_row");
			strAllSql.append(" ,0 as max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" ,Row_Number() over(partition by MB.cd_kaisha order by MB.cd_kaisha) as Rank");
			strAllSql.append(" FROM ma_busho MB");
			strAllSql.append(" WHERE MB.cd_kaisha = ");
			strAllSql.append(strKaishaCd);
			
			//自工場の場合
			if (strDataId.equals("2")) {
				strAllSql.append(" AND MB.cd_busho = ");
				strAllSql.append(userInfoData.getCd_busho());
			}
			
			strAllSql.append(" ) as MBT");
			strAllSql.append(" GROUP BY MBT.no_row, MBT.cd_shizai ,MBT.nm_shizai, MBT.list_max_row, MBT.max_row, MBT.cnt_kojo");
			strAllSql.append(" UNION");

			strAllSql.append(" SELECT");
			strAllSql.append("  tbl3.no_row");
			strAllSql.append(" ,tbl3.cd_shizai");
			strAllSql.append(" ,tbl3.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strAllSql.append(" ,CASE WHEN tbl3.disp_val" + i + " = '' THEN ISNULL(CONVERT(VARCHAR,tbl3.disp_val" + i + "),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(7,2),tbl3.disp_val" + i + ")) END as disp_val" + i);
			}
			strAllSql.append(" ,tbl3.list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,tbl2.cd_shizai");
			strSql.append(" ,tbl2.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,tbl2.disp_val" + i);
			}
			strSql.append(" ," + strListRowMax + " AS list_max_row");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  tbl.cd_shizai");
			strSql.append(" ,tbl.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN tbl.Rank = " + i + " THEN tbl.disp_val end)),'') as disp_val" + i);
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  LIST.cd_busho");
			strSql.append(" ,LIST.cd_shizai");
			strSql.append(" ,LIST.nm_shizai");
			strSql.append(" ,LIST.disp_val");
			strSql.append(" ,Row_Number() over(partition by LIST.cd_shizai order by LIST.cd_shizai) as Rank");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  M501.cd_busho");
			strSql.append(" ,M501.cd_shizai");
			strSql.append(" ,M501.nm_shizai");
			//単価が対象の場合
			if (strTaishoKbn.equals("0")) {
				strSql.append(" ,M501.tanka as disp_val");
			//歩留が対象の場合
			} else {
				strSql.append(" ,M501.budomari as disp_val");
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append(" c.*, d.tanka, d.budomari");
			strSql.append(" FROM (");
			strSql.append(" SELECT DISTINCT");
			strSql.append(" a.cd_kaisha, a.cd_shizai, a.nm_shizai, b.cd_busho, b.nm_busho");
			strSql.append(" FROM ma_shizai a, ma_busho b");
			strSql.append(" WHERE a.cd_kaisha = ");
			strSql.append(strKaishaCd);
			
			//自工場の場合
			if (strDataId.equals("2")) {
				strSql.append(" AND a.cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}
			
			strSql.append(" AND a.cd_kaisha = b.cd_kaisha");
			strSql.append(" ) as c");
			strSql.append(" LEFT JOIN ma_shizai d");
			strSql.append(" ON c.cd_kaisha = d.cd_kaisha ");
			strSql.append(" AND c.cd_busho = d.cd_busho");
			strSql.append(" AND c.cd_shizai = d.cd_shizai ");		
			strSql.append(" ) as M501");		
			strSql.append(" WHERE M501.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//資材コードが入力されている場合
			if (!strCd.equals("")) {
				strSql.append(" AND M501.cd_shizai = '");
				strSql.append(strCd);
				strSql.append("'");
			}
			
			//資材名が入力されている場合
			if (!strName.equals("")) {
				strSql.append(" AND M501.nm_shizai LIKE '%");
				strSql.append(strName);
				strSql.append("%'");
			}
			
			strSql.append(" ) AS LIST");
			strSql.append(" ) AS tbl");
			strSql.append(" GROUP BY tbl.cd_shizai,tbl.nm_shizai");
			strSql.append(" ) AS tbl2");

			strAllSql.append(strSql);
			strAllSql.append(" ) AS tbl3");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");

			strAllSql.append(" WHERE tbl3.PageNO = ");
			strAllSql.append(strPageNo);
			strAllSql.append(" ) as T");
			strAllSql.append(" ORDER BY T.no_row");
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "全工場単価歩留（資材）データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * 工場件数取得
	 *  : 会社に所属する工場の件数を取得する。
	 * @param strKaishaCd：会社コード
	 * @param strDataId：権限データID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void getKojoCount(String strKaishaCd, String strDataId) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append(" cd_busho ");
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");
			strSql.append(strKaishaCd);
			
			//自工場の場合
			if (strDataId.equals("2")) {
				strSql.append(" AND cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}

			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//結果の退避
			intRecCnt = lstRecset.size();

		} catch (Exception e) {
			this.em.ThrowException(e, "全工場単価歩留：工場件数取得処理に失敗しました。");
		} finally {
			removeList(lstRecset);
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
		}
	}

	/**
	 * 全工場単価歩留：全工場単価歩留情報パラメーター格納
	 *  : 全工場単価歩留情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @param resTable : レスポンステーブル情報
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
				resTable.addFieldVale(i, "cd_genryo", items[1].toString());
				resTable.addFieldVale(i, "nm_genryo", items[2].toString());
				for (int j = 1; j <= intRecCnt; j++) {
					resTable.addFieldVale(i, "disp_val" + j, items[j+2].toString());
				}
				resTable.addFieldVale(i, "list_max_row", items[intRecCnt+3].toString());
				resTable.addFieldVale(i, "max_row", items[intRecCnt+4].toString());
				resTable.addFieldVale(i, "cnt_kojo", items[intRecCnt+5].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "全工場単価歩留データ検索処理に失敗しました。");
		} finally {

		}
	}
	
}
