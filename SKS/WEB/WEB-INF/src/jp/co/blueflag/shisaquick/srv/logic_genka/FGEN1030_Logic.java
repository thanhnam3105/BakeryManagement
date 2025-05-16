package jp.co.blueflag.shisaquick.srv.logic_genka;

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
 * 製品検索
 *  : 類似品検索：製品情報を検索する。
 * @author nishigawa
 * @since  2009/11/05
 */
public class FGEN1030_Logic extends LogicBase{
	
	/**
	 * 類似品検索：製品情報を検索する。
	 * : インスタンス生成
	 */
	public FGEN1030_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 類似品検索：製品情報を検索する。
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
		//総件数
		List<?> lstRecset_cnt = null;
		//該当資材情報
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			super.createSearchDB();

			//SQL文の作成(総件数)
			strSql = genryoData_kensu_CreateSQL(reqData);
			//SQLを実行(総件数)
			lstRecset_cnt = searchDB.dbSearch(strSql.toString());

			//SQL文の作成（該当資材）
			strSql = genryoDataCreateSQL(reqData);
			//SQLを実行（該当資材）
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			resKind.addTableItem("kihon");
			resKind.addTableItem("seihin");

			//レスポンスデータの形成
			storageKihonData(lstRecset_cnt, reqData, resKind.getTableItem("kihon"));
			storageSeihinData(lstRecset, resKind.getTableItem("seihin"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製品情報検索処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			removeList(lstRecset_cnt);
			
			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;

		}
		return resKind;

	}
	
	/**
	 * 製品検索情報取得SQL作成
	 *  : 製品情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoData_kensu_CreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strSeihinCd = null;
			String strSeihinName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//製品コードの取得
			strSeihinCd = toString(reqData.getFieldVale(0, 0, "cd_seihin"));
			//製品名の取得
			strSeihinName = toString(reqData.getFieldVale(0, 0, "nm_seihin"));
			//会社コードの取得
			strKaishaCd = toString(reqData.getFieldVale(0, 0, "cd_kaisya"));
			//工場コードの取得
			strKojoCd = toString(reqData.getFieldVale(0, 0, "cd_kojyo"));
			//選択ページNoの取得
			strPageNo = toString(reqData.getFieldVale(0, 0, "no_page"));

			//SQL文の作成
			
			strSQL.append(" SELECT ");
			strSQL.append(" COUNT(M801.cd_kaisha_seihin) AS CNT ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_seihin AS M01");
			strSQL.append(" WHERE ");
			strSQL.append("     1                     = 1 ");
			if (!strKaishaCd.equals("")){
				strSQL.append(" AND M01.cd_kaisha_seihin = " + strKaishaCd + " ");
				
			}
			if (!strKojoCd.equals("")){
				strSQL.append(" AND M01.cd_busho_seihin  = " + strKojoCd + " ");
				
			}
			if (!strSeihinCd.equals("")){
				strSQL.append(" AND M01.cd_seihin        = " + strSeihinCd + " ");
				
			}
			if (!strSeihinName.equals("")){
				strSQL.append(" AND M01.nm_seihin        LIKE '%" + strSeihinName + "%' ");
				
			}
			strSQL.append(" GROUP BY ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" ) AS M801 ");

		} catch (Exception e) {
			this.em.ThrowException(e, "製品情報データ件数、検索処理に失敗しました。");
		} finally {
			//変数の削除

		}
		return strSQL;
	}
	/**
	 * 製品検索情報取得SQL作成
	 *  : 製品情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strSeihinCd = null;
			String strSeihinName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;

			//MAXﾍﾟｰｼﾞ取得
			String strListRowMax = toString(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"), "50");
			//製品コードの取得
			strSeihinCd = reqData.getFieldVale(0, 0, "cd_seihin");
			//製品名の取得
			strSeihinName = reqData.getFieldVale(0, 0, "nm_seihin");
			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//工場コードの取得
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//SQL文の作成

			strSQL.append(" SELECT TOP " + strListRowMax + " ");
			strSQL.append("  * ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  ROW_NUMBER() OVER (ORDER BY M801.cd_kaisha_seihin, M801.cd_busho_seihin, M801.cd_seihin ) AS NUM ");
			strSQL.append(" ,M801.cd_kaisha_seihin ");	//1
			strSQL.append(" ,M801.cd_busho_seihin ");	//2
			
			//strSQL.append(" ,M801.cd_seihin ");		//3
			strSQL.append(" ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_seihin),ISNULL(M104.keta_genryo,6)) AS cd_seihin ");			//3
			
			strSQL.append(" ,M801.nm_seihin ");			//4
			strSQL.append(" ,M302.nm_literal ");		//5
			
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append(" SELECT ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_seihin  AS M01");
			strSQL.append(" WHERE ");
			strSQL.append("     1                     = 1 ");
			if (!strKaishaCd.equals("")){
				strSQL.append(" AND M01.cd_kaisha_seihin = " + strKaishaCd + " ");
				
			}
			if (!strKojoCd.equals("")){
				strSQL.append(" AND M01.cd_busho_seihin  = " + strKojoCd + " ");
				
			}
			if (!strSeihinCd.equals("")){
				strSQL.append(" AND M01.cd_seihin        = " + strSeihinCd + " ");
				
			}
			if (!strSeihinName.equals("")){
				strSQL.append(" AND M01.nm_seihin        LIKE '%" + strSeihinName + "%' ");
				
			}
			strSQL.append(" GROUP BY ");
			strSQL.append("  cd_kaisha_seihin ");
			strSQL.append(" ,cd_busho_seihin ");
			strSQL.append(" ,cd_seihin ");
			strSQL.append(" ,nm_seihin ");
			strSQL.append(" ) AS M801 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M801.cd_kaisha_seihin) + '-' + CONVERT(varchar, M801.cd_busho_seihin) ");
			
			strSQL.append(" LEFT JOIN ma_busho AS M104 ");
			strSQL.append(" ON cd_kaisha_seihin = M104.cd_kaisha ");
			strSQL.append(" AND cd_busho_seihin = M104.cd_busho ");
			
			strSQL.append(" ) AS M801_1 ");
			strSQL.append(" WHERE ");
			strSQL.append("     M801_1.NUM >= " + 
					//先頭行番号の計算
					toString( (toInteger(strListRowMax) * (toInteger(strPageNo)-1) +1 ), 0, 1, false, "1")
					+ " ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製品情報データ検索処理に失敗しました。");
		} finally {
			//変数の削除

		}
		return strSQL;
	}

	/**
	 * 類似品検索：基本情報パラメーター格納
	 *  : 基本情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageKihonData(
			
			List<?> lstGenryouData
			, RequestResponsKindBean reqData
			, RequestResponsTableBean resTable
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//表示MAX行数
			String strListRowMax = toString(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"), "50");
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object items = (Object) lstGenryouData.get(i);
				
				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "max_cnt", toString(items));
				resTable.addFieldVale(i, "no_page", reqData.getFieldVale(0, 0, "no_page"));
				resTable.addFieldVale(i, "max_page", toString(
						(toInteger(items) / toInteger(strListRowMax))
						, 0
						, 2
						, false
						, "1"));
				resTable.addFieldVale(i, "disp_cnt", toString(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"), "50"));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ検索処理に失敗しました。");

		} finally {

		}

	}
	
	/**
	 * 類似品検索：製品情報パラメーター格納
	 *  : 製品情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihinData(
			
			List<?> lstGenryouData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[1]));
				resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[2]));
				resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[5]));
				
				//指定桁数にて0埋め
				resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[3]));
				
				resTable.addFieldVale("rec" + i, "nm_seihin", toString(items[4]));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製品情報データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}