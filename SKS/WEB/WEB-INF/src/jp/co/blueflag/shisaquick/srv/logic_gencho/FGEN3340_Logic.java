package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材情報取得DB処理
 *  機能ID：FGEN3340　
 *
 * @author TT.Shima
 * @since  2014/09/25
 */
public class FGEN3340_Logic extends LogicBase {

	/**
	 * 資材情報取得コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN3340_Logic(){
		super();
	}

	/**
	 * 資材情報取得 : 資材情報を取得する。
	 *
	 * @param reqData
	 *            : 機能リクエストデータ
	 * @param userInfoData
	 *            : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ユーザー情報退避
		userInfoData = _userInfoData;
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// SQL作成（Where句取得）
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL作成（資材データ取得）
			strSql = createTehaiSQL(reqData, strSql, strSqlWhere);

			// DBインスタンス生成
			createSearchDB();

			// 検索実行（資材データ取得）
			lstRecset = searchDB.dbSearch(strSql.toString());

			// 検索結果がない場合
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.警告Exception, "W000401",
						strSql.toString(), "", "");
			}

			// 機能IDの設定
			resKind.setID(reqData.getID());

			// テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			// レスポンスデータの形成
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");
		} finally {
			// リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			// 変数の削除
			strSqlWhere = null;
			strSql = null;
		}
		return resKind;

	}

	/**
	 * 取得SQL作成
	 *  : 手配情報を取得するSQLを作成。
	 *
	 * @param strSql
	 *            : 検索条件SQL
	 * @param arrCondition
	 *            ：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createTehaiSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strAllSql = new StringBuffer();

		try {
			// SQL文の作成
			strAllSql.append(" SELECT ");
			strAllSql.append("   T401.cd_shain, ");
			strAllSql.append("   T401.nen, ");
			strAllSql.append("   T401.no_oi, ");
			strAllSql.append("   T401.seq_shizai, ");
			strAllSql.append("   T401.no_eda, ");
//【KPX@1602367】20161020 add mod start
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shohin), 6) AS cd_shohin, "); // 製品（商品）コード
			strAllSql.append("   T401.nm_shohin, ");
			strAllSql.append("   M991.name_nisugata, ");
			strAllSql.append("   T341.cd_taisyoshizai, ");
			strAllSql.append("   T341.cd_hattyusaki, ");
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shizai), 6) AS cd_shizai, "); // 旧資材コード
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shizai_new), 6) AS cd_shizai_new, "); // 資材コード
			strAllSql.append("   T401.flg_tehai_status, ");

			strAllSql.append("   T401.naiyo ,");			 		// 内容
			strAllSql.append("   M104.nm_busho AS nounyusaki, "); 	// 納入先（製造工場）

			// 追加
			strAllSql.append("   T401.dt_han_payday AS dt_han_payday, "); 	// 版代支払日
			strAllSql.append("   T401.han_pay AS han_pay, "); 	// 版代
			strAllSql.append("   T401.nm_file_aoyaki AS nm_file_aoyaki, "); 	// 青焼きファイル名
			strAllSql.append("   T401.file_path_aoyaki AS file_path_aoyaki "); 	// 青焼きファイルパス

			strAllSql.append(" FROM ");
			strAllSql.append("   tr_shizai_tehai T401 ");
			strAllSql.append("     LEFT JOIN tr_shisan_shizai T341 ");
			strAllSql.append("       ON T401.cd_shohin = T341.cd_seihin ");
			strAllSql.append("      AND T401.cd_shizai = T341.cd_shizai ");
			strAllSql.append("      AND T401.cd_shizai_new = T341.cd_shizai_new ");
			strAllSql.append("      AND T401.cd_shain = T341.cd_shain ");
			strAllSql.append("      AND T401.nen = T341.nen ");
			strAllSql.append("      AND T401.no_oi = T341.no_oi ");
			strAllSql.append("      AND T401.seq_shizai = T341.seq_shizai ");
			strAllSql.append("      AND T401.no_eda = T341.no_eda ");

			strAllSql.append("     LEFT JOIN ma_Hinmei_Mst M991 ");
			strAllSql.append("         ON CONVERT(int,T401.cd_shohin) ");
			strAllSql.append("            = CONVERT(int,M991.cd_hinmei) ");
			strAllSql.append("      AND T341.cd_kaisha = M991.cd_kaisha ");
			strAllSql.append("     LEFT JOIN ma_busho M104 ");
			strAllSql.append("         ON  (T341.cd_kaisha = M104.cd_kaisha ");
			strAllSql.append("      AND  T341.cd_seizokojo = M104.cd_busho) ");
//【KPX@1602367】20161020 add end
			strAllSql.append(strWhere);
			strAllSql.append(" ORDER BY ");
			strAllSql.append("   T401.cd_shohin, ");
			strAllSql.append("   T401.nm_shohin, ");
			strAllSql.append("   M991.name_nisugata ");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");
		} finally {

		}
		return strAllSql;
	}

	/**
	 * Where句取得SQL作成
	 *
	 * @param reqData
	 *            : リクエストデータ
	 * @param strSql
	 *            : 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		StringBuffer strWhere = new StringBuffer();

		try {
			strWhere.append(" WHERE (");

			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				// 検索条件取得---------------------------------------------------------------------

				// 社員コード
				String cd_shain = toString(reqData.getFieldVale(0, i, "cd_shain"));

				// 年
				String nen = toString(reqData.getFieldVale(0, i, "nen"));

				// 追番
				String no_oi = toString(reqData.getFieldVale(0, i, "no_oi"));

				// 行番号
				String seq_shizai = toString(reqData.getFieldVale(0, i, "seq_shizai"));

				// 枝番
				String no_eda = toString(reqData.getFieldVale(0, i, "no_eda"));

				// WHERE句SQL作成----------------------------------------------------------------

				if(i != 0){
					strWhere.append(" OR ");
				}

				strWhere.append(" (T401.cd_shain = '" + cd_shain + "' ");
				strWhere.append(" AND T401.nen = '" + nen + "' ");
				strWhere.append(" AND T401.no_oi = '" + no_oi + "' ");
				strWhere.append(" AND T401.seq_shizai = '" + seq_shizai + "' ");
				strWhere.append(" AND T401.no_eda = '" + no_eda + "') ");

			}
			strWhere.append(" ) ");

		} catch (Exception e) {

			em.ThrowException(e, "資材情報取得DB処理に失敗しました。");

		} finally {

		}

		return strWhere;
	}

	/**
	 * 資材情報パラメーター格納 : 資材情報をレスポンスデータへ格納する。
	 *
	 * @param lstRecset
	 *            : 検索結果情報リスト
	 * @param resTable
	 *            : レスポンス情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShizaiData(List<?> lstRecset,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			for(int i = 0; i < lstRecset.size(); i++){
				// 検索結果取得
				Object[] items = (Object[]) lstRecset.get(i);
				String cd_shain = toString(items[0]);
				String nen = toString(items[1]);
				String no_oi = toString(items[2]);
				String seq_shizai = toString(items[3]);
				String no_eda = toString(items[4]);
				String cd_shohin = toString(items[5]);
				String nm_shohin = toString(items[6]);
				String name_nisugata = toString(items[7]);
				String cd_taisyosizai = toString(items[8]);
				String cd_hattyusaki = toString(items[9]);
				String cd_shizai = toString(items[10]);
				String cd_shizai_new = toString(items[11]);
				String flg_tehai_status = toString(items[12]);
				String naiyo = toString(items[13]);					// 内容
				String nounyusaki = toString(items[14]);			// 納入先（製造工場）

				String dt_han_payday = toString(items[15]);		// 版代支払日
				String han_pay = toString(items[16]);		// 版代
				String nm_file_aoyaki = toString(items[17]);		// 青焼きファイル名
				String file_path_aoyaki = toString(items[18]);		// 青焼きファイルパス

				// 処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				// レスポンス生成
				resTable.addFieldVale(i, "cd_shain", cd_shain);
				resTable.addFieldVale(i, "nen", nen);
				resTable.addFieldVale(i, "no_oi", no_oi);
				resTable.addFieldVale(i, "seq_shizai", seq_shizai);
				resTable.addFieldVale(i, "no_eda", no_eda);
				resTable.addFieldVale(i, "cd_shohin", cd_shohin);
				resTable.addFieldVale(i, "nm_shohin", nm_shohin);
				resTable.addFieldVale(i, "name_nisugata", name_nisugata);
				resTable.addFieldVale(i, "cd_taisyosizai", cd_taisyosizai);
				resTable.addFieldVale(i, "cd_hattyusaki", cd_hattyusaki);
				resTable.addFieldVale(i, "cd_shizai", cd_shizai);
				resTable.addFieldVale(i, "cd_shizai_new", cd_shizai_new);
				resTable.addFieldVale(i, "flg_tehai_status", flg_tehai_status);
				resTable.addFieldVale(i, "naiyo", naiyo);						// 内容
				resTable.addFieldVale(i, "nounyusaki", nounyusaki);				// 納入先（製造工場）

				resTable.addFieldVale(i, "dt_han_payday", dt_han_payday);					// 版代支払日
				resTable.addFieldVale(i, "han_pay", han_pay);				// 版代
				resTable.addFieldVale(i, "nm_file_aoyaki", nm_file_aoyaki);					// 青焼きファイル名
				resTable.addFieldVale(i, "file_path_aoyaki", file_path_aoyaki);				// 青焼きファイルパス

				resTable.addFieldVale(i, "loop_cnt", Integer.toString(lstRecset.size()));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報取得DB処理に失敗しました。");

		} finally {

		}

	}

}
