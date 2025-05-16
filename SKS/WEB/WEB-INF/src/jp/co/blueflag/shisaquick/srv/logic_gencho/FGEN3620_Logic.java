package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class FGEN3620_Logic extends LogicBaseJExcel {

	/**
	 * 資材手配一覧_Excel表を生成する
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

		//レスポンスデータ（機能）
		RequestResponsKindBean ret = null;
		//検索データ
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();
		//エクセルファイルパス
		String downLoadPath = "";

		try {
			// SQL作成（Where句取得）
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL作成（資材データ取得）
			strSql = createShizaiCodeSQL(reqData, strSql, strSqlWhere);

			// DB検索
			super.createSearchDB();
			lstRecset = getData(strSql.toString());

			// Excelファイル生成
			downLoadPath = makeExcelFile1(lstRecset, reqData);

			//レスポンスデータ生成
			ret = CreateRespons(downLoadPath, reqData);
			ret.setID(reqData.getID());

		} catch (Exception e) {
			em.ThrowException(e, "資材手配一覧Excel表の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}
		return reqData;

	}

	/**
	 * 資材手配一覧取得SQL作成 : 資材情報を取得するSQLを作成。
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
	private StringBuffer createShizaiCodeSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strAllSql = new StringBuffer();

		try {
			// 最大行数の取得
			int intListRowMax = toInteger(ConstManager.getConstValue(
					ConstManager.Category.設定, "SHIZAI_CODE_LIST_ROW_MAX"));
			String strListRowMax = toString(intListRowMax + 1);

			// SQL文の作成
			strAllSql.append(" SELECT ");
			strAllSql.append("   result.row_no, "); // 行番号
			strAllSql.append("   result.cd_shain, "); // 社員コード
			strAllSql.append("   result.nen, "); // 年
			strAllSql.append("   result.no_oi, "); // 追番
			strAllSql.append("   result.seq_shizai, "); // 行番
			strAllSql.append("   result.no_eda, "); // 枝番
			strAllSql.append("   result.cd_shizai_new, "); // 資材コード
			strAllSql.append("   result.nm_shizai_new, "); // 資材名
			strAllSql.append("   result.cd_shizai, "); // 旧資材コード
			strAllSql.append("   result.cd_shohin, "); // 製品（商品）コード
			strAllSql.append("   result.nm_shohin, "); // 製品（商品）名
			strAllSql.append("   result.name_nisugata, "); // 荷姿
			strAllSql.append("   result.nm_taisyo, "); // 対象資材名
			strAllSql.append("   result.cd_taisyo, "); // 対象資材コード
			strAllSql.append("   result.nm_hattyu, "); // 発注先
			strAllSql.append("   result.cd_hattyu, "); // 発注先コード
			strAllSql.append("   result.flg_tehai_status, "); // 手配ステータス
			strAllSql.append("   result.toroku , "); // 登録日(表示用)

			strAllSql.append("   result.naiyo ,"); // 内容
			strAllSql.append("   result.nounyusaki ,"); // 納入先
			strAllSql.append("   result.dt_han_payday ,"); // 版代支払日
			strAllSql.append("   result.han_pay ,"); // 版代
			strAllSql.append("   result.nm_file_aoyaki ,"); // 青焼ファイル名
			strAllSql.append("   result.file_path_aoyaki ,"); // 青焼ファイルパス
			strAllSql.append("   result.dt_hattyu ,"); // 発注日
			strAllSql.append("   result.sekkei1 ,"); // 設計①
			strAllSql.append("   result.sekkei2 ,"); // 設計②
			strAllSql.append("   result.sekkei3 ,"); // 設計③
			strAllSql.append("   result.zaishitsu ,"); // 材質
			strAllSql.append("   result.biko_tehai ,"); // 備考
			strAllSql.append("   result.printcolor ,"); // 印刷色
			strAllSql.append("   result.no_color ,"); // 色番号
			strAllSql.append("   result.henkounaiyoushosai ,"); // 変更内容詳細
			strAllSql.append("   result.nouki ,"); // 納期
			strAllSql.append("   result.suryo ,"); // 数量
			strAllSql.append("   result.nm_tanto , "); // 担当者
			strAllSql.append("   result.nounyu_day "); // 納入日

			strAllSql.append(" FROM ");
			strAllSql.append("   ( ");
			strAllSql.append("   SELECT ");
			strAllSql.append("     ROW_NUMBER() OVER ( ");
			strAllSql.append("       ORDER BY cast(T401.cd_shizai_new AS int),");
			strAllSql.append("       T401.dt_koshin) as row_no, "); // 行番号
			strAllSql.append("     T401.cd_shain, "); // 社員コード
			strAllSql.append("     T401.nen, "); // 年
			strAllSql.append("     T401.no_oi, "); // 追番
			strAllSql.append("     T401.seq_shizai, "); // 行番
			strAllSql.append("     T401.no_eda, "); // 枝番
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shizai_new), 6) AS cd_shizai_new, "); // 資材コード
			strAllSql.append("     T341.nm_shizai_new, "); // 資材名
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shizai), 6) AS cd_shizai, "); // 旧資材コード
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shohin), 6) AS cd_shohin, "); // 製品（商品）コード
			strAllSql.append("     T401.nm_shohin, "); // 製品（商品）名
			strAllSql.append("     M991.name_nisugata, "); // 荷姿
			strAllSql.append("     M302A.nm_ｌiteral AS nm_taisyo, "); // 対象資材名
			strAllSql.append("     M302A.cd_ｌiteral AS cd_taisyo, "); // 対象資材コード
			strAllSql.append("     M302B.nm_ｌiteral AS nm_hattyu, "); // 発注先
			strAllSql.append("     M302B.cd_ｌiteral AS cd_hattyu, "); // 発注先コード
			strAllSql.append("     T401.flg_tehai_status ,"); // 手配ステータス
			strAllSql.append("     CONVERT(NVARCHAR, T401.dt_koshin, 120) AS toroku , "); // 登録日(表示用)

			strAllSql.append("     T401.naiyo ,"); // 内容
			strAllSql.append("     CONVERT(NVARCHAR, T401.dt_han_payday, 111) AS dt_han_payday , "); // 版代支払日
			strAllSql.append("     REPLACE(CONVERT(nvarchar,CONVERT(money, T401.han_pay), 1), '.00', '') as han_pay ,"); // 版代
			strAllSql.append("     T401.nm_file_aoyaki ,"); // 青焼ファイル名
			strAllSql.append("     T401.file_path_aoyaki ,"); // 青焼ファイルパス
			strAllSql.append("     CONVERT(NVARCHAR, T341.dt_hattyu, 111) AS dt_hattyu , "); // 発注日
			strAllSql.append("     T401.sekkei1 ,"); // 設計①
			strAllSql.append("     T401.sekkei2 ,"); // 設計②
			strAllSql.append("     T401.sekkei3 ,"); // 設計③
			strAllSql.append("     T401.zaishitsu ,"); // 材質
			strAllSql.append("     T401.biko_tehai ,"); // 備考
			strAllSql.append("     T401.printcolor ,"); // 印刷色
			strAllSql.append("     T401.no_color ,"); // 色番号
			strAllSql.append("     T401.henkounaiyoushosai ,"); // 変更内容詳細
			strAllSql.append("     T401.nouki ,"); // 納期
			strAllSql.append("     T401.suryo ,"); // 数量
			strAllSql.append("     M101.nm_user AS nm_tanto , "); // 担当者
			strAllSql.append("     M104.nm_busho AS nounyusaki , "); // 納入先（製造工場）
			strAllSql.append("     CONVERT(NVARCHAR, niuke.dt_niuke, 111) AS nounyu_day "); // 納入日(表示用)

			strAllSql.append("   FROM ");
			strAllSql.append("     tr_shizai_tehai T401 ");
			strAllSql.append("       LEFT JOIN tr_shisan_shizai T341 ");
			strAllSql.append("         ON T401.cd_shohin = T341.cd_seihin ");
			strAllSql.append("        AND T401.cd_shizai = T341.cd_shizai ");
			strAllSql.append("        AND T401.cd_shizai_new = T341.cd_shizai_new ");
			strAllSql.append("        AND T401.cd_shain = T341.cd_shain ");
			strAllSql.append("        AND T401.nen = T341.nen ");
			strAllSql.append("        AND T401.no_oi = T341.no_oi ");
			strAllSql.append("        AND T401.seq_shizai = T341.seq_shizai ");
			strAllSql.append("        AND T401.no_eda = T341.no_eda ");
			strAllSql.append("       LEFT JOIN ma_Hinmei_Mst M991 ");
			strAllSql.append("         ON CONVERT(int,T401.cd_shohin) ");
			strAllSql.append("            = CONVERT(int,M991.cd_hinmei) ");
			strAllSql.append("        AND T341.cd_kaisha = M991.cd_kaisha ");
			strAllSql.append("       LEFT JOIN ( ");
			strAllSql.append("         SELECT DISTINCT ");
			strAllSql.append("           cd_ｌiteral, ");
			strAllSql.append("           nm_literal  ");
			strAllSql.append("         FROM ");
			strAllSql.append("           ma_literal ");
			strAllSql.append("         WHERE cd_category = 'C_taisyosizai' ");
			strAllSql.append("       ) M302A  ");
			strAllSql.append("         ON T341.cd_taisyoshizai = M302A.cd_ｌiteral ");
			strAllSql.append("       LEFT JOIN ( ");
			strAllSql.append("         SELECT DISTINCT ");
			strAllSql.append("           cd_ｌiteral, ");
			strAllSql.append("           nm_literal  ");
			strAllSql.append("         FROM ");
			strAllSql.append("           ma_literal ");
			strAllSql.append("         WHERE cd_category = 'C_hattyuusaki' ");
			strAllSql.append("       ) M302B ");
			strAllSql.append("         ON T341.cd_hattyusaki = M302B.cd_ｌiteral ");

			strAllSql.append("       LEFT JOIN ma_user M101 ");
			strAllSql.append("         ON   M101.id_user = T401.id_toroku ");
			strAllSql.append("       LEFT JOIN ma_busho M104 ");
			strAllSql.append("         ON  (T341.cd_kaisha = M104.cd_kaisha ");
			strAllSql.append("          AND  T341.cd_seizokojo = M104.cd_busho) ");

			strAllSql.append("       LEFT JOIN (SELECT cd_kaisha, cd_kojyo, cd_hin, dt_niuke ");
			strAllSql.append("                  from tr_nyukei_jisekiari ");
			strAllSql.append("                  group by cd_kaisha,cd_kojyo,cd_hin,dt_niuke ");
			strAllSql.append("                  )niuke ");
			strAllSql.append("         ON  (T341.cd_kaisha = convert(int, niuke.cd_kaisha) ");
			strAllSql.append("    AND  T341.cd_seizokojo = convert(int, niuke.cd_kojyo) ");
			strAllSql.append("    AND  T341.cd_seihin = convert(int, niuke.cd_hin)) ");

			strAllSql.append(strWhere);

			strAllSql.append("   ) AS result ");
			strAllSql.append(" WHERE ");
			strAllSql.append("   row_no BETWEEN 1 AND " + strListRowMax + " ");
			strAllSql.append(" ORDER BY ");
			strAllSql.append("   cast(cd_shizai_new AS int), toroku ");

			strSql = strAllSql;
		} catch (Exception e) {

			em.ThrowException(e, "資材手配一覧Excel表の生成に失敗しました。");

		} finally {
			// 変数の削除
			strAllSql = null;
		}

		return strSql;
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
			// 検索条件取得---------------------------------------------------------------------
			/* １行目 */
			// 手配済み
			String kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));
			// 未手配
			String kbn_mitehai = toString(reqData.getFieldVale(0, 0, "kbn_mitehai"));
			// 未入力
			String kbn_minyuryoku = toString(reqData.getFieldVale(0, 0, "kbn_minyuryoku"));
			/* ２行目 */
			// 資材コード
			String cd_shizai = toString(reqData.getFieldVale(0, 0, "cd_shizai"));
			// 資材名
			String nm_shizai = toString(reqData.getFieldVale(0, 0, "nm_shizai"));
			// 旧資材コード
			String cd_shizai_old = toString(reqData.getFieldVale(0, 0, "cd_shizai_old"));
			/* ３行目 */
			// 製品（商品）コード
			String cd_shohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
			// 製品（商品）名
			String nm_shohin = toString(reqData.getFieldVale(0, 0, "nm_shohin"));
			// 納入先（製造工場）
			String cd_seizoukojo = toString(reqData.getFieldVale(0, 0, "cd_seizoukojo"));
			/* ４行目 */
			// 対象資材
			String taisyo_shizai = toString(reqData.getFieldVale(0, 0, "taisyo_shizai"));
			// 発注先
			String cd_hattyusaki = toString(reqData.getFieldVale(0, 0, "cd_hattyusaki"));
			// 発注者
			String cd_hattyusya = toString(reqData.getFieldVale(0, 0, "cd_hattyusya"));
			/* ５行目 */
			// 発注日From
			String dt_hattyu_from = toString(reqData.getFieldVale(0, 0, "dt_hattyu_from"));
			// 発注日To
			String dt_hattyu_to = toString(reqData.getFieldVale(0, 0, "dt_hattyu_to"));
			// 納入日From
			String dt_nonyu_from = toString(reqData.getFieldVale(0, 0, "dt_nonyu_from"));
			// 納入日To
			String dt_nonyu_to = toString(reqData.getFieldVale(0, 0, "dt_nonyu_to"));
			// 版代支払日From
			String dt_han_payday_from = toString(reqData.getFieldVale(0, 0, "dt_han_payday_from"));
			// 版代支払日To
			String dt_han_payday_to = toString(reqData.getFieldVale(0, 0, "dt_han_payday_to"));
			// 未支払
			String kbn_mshiharai = toString(reqData.getFieldVale(0, 0, "kbn_mshiharai"));

			// WHERE句SQL作成----------------------------------------------------------------
			strWhere.append(" WHERE 1 = 1 ");

			/* １行目 */
			// 手配チェック項目がチェックされている場合
			if ("1".equals(kbn_tehaizumi) || "1".equals(kbn_mitehai)
					|| "1".equals(kbn_minyuryoku)) {
				boolean orFlg = false;
				strWhere.append(" AND (");
				if ("1".equals(kbn_tehaizumi)) {
					strWhere.append(" flg_tehai_status = 3 ");
					orFlg = true;
				}
				if ("1".equals(kbn_mitehai)) {
					if (orFlg) {
						strWhere.append(" OR ");
					}
					strWhere.append(" flg_tehai_status = 2 ");
					orFlg = true;
				}
				if ("1".equals(kbn_minyuryoku)) {
					if (orFlg) {
						strWhere.append(" OR ");
					}
					strWhere.append(" flg_tehai_status <= 1 ");
				}
				strWhere.append(") ");
			}

			/* ２行目 */
			// 資材コードが入力されている場合
			if (!cd_shizai.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shizai_new) = CONVERT(bigint, '"
						+ cd_shizai + "') ");
			}

			// 資材名が入力されている場合
			if (!nm_shizai.equals("")) {
				strWhere.append(" AND T341.nm_shizai_new LIKE '%" + nm_shizai
						+ "%' ");
			}

			// 旧資材コードが入力されている場合
			if (!cd_shizai_old.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shizai) = CONVERT(bigint, '"
						+ cd_shizai_old + "') ");
			}

			/* ３行目 */
			// 製品（商品）コードが入力されている場合
			if (!cd_shohin.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shohin ) = CONVERT(bigint, '"
						+ cd_shohin + "') ");
			}

			// 製品（商品）名が入力されている場合
			if (!nm_shohin.equals("")) {
				strWhere.append(" AND T401.nm_shohin LIKE '%" + nm_shohin
						+ "%' ");
			}

			// 納入先（製造工場）コードが入力されている場合
			if (!cd_seizoukojo.equals("")) {
				strWhere.append(" AND CONVERT(int, T341.cd_seizokojo ) = CONVERT(int, '"
						+ cd_seizoukojo + "') ");
			}

			/* ４行目 */
			// 対象資材が入力されている場合
			if (!taisyo_shizai.equals("")) {
				strWhere.append(" AND M302A.cd_ｌiteral = '" + taisyo_shizai
						+ "' ");
			}

			// 発注先が入力されている場合
			if (!cd_hattyusaki.equals("")) {
				strWhere.append(" AND T341.cd_hattyusaki = '" + cd_hattyusaki
						+ "' ");
			}
			// 発注者が入力されている場合
			if (!cd_hattyusya.equals("")) {
				strWhere.append(" AND T401.id_koshin = '" + cd_hattyusya + "'");
			}

			/* ５行目 */
			// 発注日(From,To)が入力されている場合
			if (!dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) BETWEEN '" + dt_hattyu_from.replace("/", "") + "' "
						+ " AND '" + dt_hattyu_to.replace("/", "") + "' ");
			}
			// 発注日(From)が入力されている場合
			else if (!dt_hattyu_from.equals("") && dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) >= '" + dt_hattyu_from.replace("/", "")
						+ "' ");
			}
			// 発注日(To)が入力されている場合
			else if (dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) <= '" + dt_hattyu_to.replace("/", "")
						+ "' ");
			}

			// 納入日(From,To)が入力されている場合
			if (!dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) BETWEEN '" + dt_nonyu_from.replace("/", "") + "' "
						+ " AND '" + dt_nonyu_to.replace("/", "") + "' ");
			}
			// 納入日(From)が入力されている場合
			else if (!dt_nonyu_from.equals("") && dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) >= '" + dt_nonyu_from.replace("/", "")
						+ "' ");
			}
			// 納入日(To)が入力されている場合
			else if (dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) <= '" + dt_nonyu_to.replace("/", "")
						+ "' ");
			}

			// 版代支払日(From,To)が入力されている場合
			if (!dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) BETWEEN '" + dt_han_payday_from.replace("/", "") + "' "
						+ " AND '" + dt_han_payday_to.replace("/", "") + "' ");
			}
			// 版代支払日(From)が入力されている場合
			else if (!dt_han_payday_from.equals("") && dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) >= '" + dt_han_payday_from.replace("/", "")
						+ "' ");
			}
			// 版代支払日(To)が入力されている場合
			else if (dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) <= '" + dt_han_payday_to.replace("/", "")
						+ "' ");
			}

			// 未支払がチェックされている場合
			if ("1".equals(kbn_mshiharai)) {
				strWhere.append(" AND (T401.dt_han_payday IS NULL OR T401.dt_han_payday = '') ");
				strWhere.append(" AND T401.han_pay != 0 ");
			}

		} catch (Exception e) {

			em.ThrowException(e, "資材検索処理に失敗しました。");

		} finally {

		}

		return strWhere;
	}

	/**
	 * 対象の資材データを検索する
	 * @param strSql : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData(String strSql) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//検索結果
		List<?> ret = null;

		try {
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "資材データ、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}

	/**
	 * 資材手配一覧Excelを生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try {

			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("資材手配一覧");
			ret = super.ExcelOutput("");
			for (int i = 0; i < lstRecset.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);
				// 値の取得
				String nm_tanto = toString(items[35]);				// 担当者
				String naiyo = toString(items[18]);					// 内容
				String cd_shohin = toString(items[9]);				// 製品（商品）コード
				String nm_shohin = toString(items[10]);				// 製品（商品）名
				String nisugata = toString(items[11]);				// 荷姿
				String nm_taisyo_shizai = toString(items[12]);		// 対象資材名
				String nm_hattyusaki = toString(items[14]);			// 発注先
				String nounyusaki = toString(items[19]);			// 納入先
				String cd_shizai_old = toString(items[8]);			// 旧資材コード
				String cd_shizai = toString(items[6]);				// 資材コード
				String nm_shizai = toString(items[7]);				// 資材名
				String sekkei1 = toString(items[25]);				// 設計①
				String sekkei2 = toString(items[26]);				// 設計②
				String sekkei3 = toString(items[27]);				// 設計③
				String zaishitsu = toString(items[28]);				// 材質
				String biko_tehai = toString(items[29]);			// 備考
				String printcolor = toString(items[30]);			// 印刷色
				String no_color = toString(items[31]);				// 色番号
				String henkounaiyoushosai = toString(items[32]);	// 変更内容詳細
				String nouki = toString(items[33]);					// 納期
				String suryo = toString(items[34]);					// 数量
				String dt_koshin_disp = toString(items[17]);		// 登録日
				String nounyu_day = toString(items[36]);			// 納入日
				String han_pay = toString(items[21]);				// 版代
				String dt_han_payday = toString(items[20]);			// 版代支払日
				String nm_file_aoyaki = toString(items[22]);		// 青焼アップロード(青焼ファイル名)

				// Excelに値をセットする
				super.ExcelSetValue("担当者", nm_tanto);
				super.ExcelSetValue("内容", naiyo);
				super.ExcelSetValue("製品コード", cd_shohin);
				super.ExcelSetValue("製品名", nm_shohin);
				super.ExcelSetValue("荷姿", nisugata);
				super.ExcelSetValue("対象資材", nm_taisyo_shizai);
				super.ExcelSetValue("発注先", nm_hattyusaki);
				super.ExcelSetValue("納入先", nounyusaki);
				super.ExcelSetValue("旧資材コード", cd_shizai_old);
				super.ExcelSetValue("資材コード", cd_shizai);
				super.ExcelSetValue("資材名", nm_shizai);
				super.ExcelSetValue("設計①", sekkei1);
				super.ExcelSetValue("設計②", sekkei2);
				super.ExcelSetValue("設計③", sekkei3);
				super.ExcelSetValue("材質", zaishitsu);
				super.ExcelSetValue("備考", biko_tehai);
				super.ExcelSetValue("印刷色", printcolor);
				super.ExcelSetValue("色番号", no_color);
				super.ExcelSetValue("変更内容詳細", henkounaiyoushosai);
				super.ExcelSetValue("納期", nouki);
				super.ExcelSetValue("数量", suryo);
				super.ExcelSetValue("登録日", dt_koshin_disp);
				super.ExcelSetValue("納入日", nounyu_day);
				super.ExcelSetValue("版代", han_pay);
				super.ExcelSetValue("版代支払日", dt_han_payday);
				super.ExcelSetValue("青焼アップロード", nm_file_aoyaki);

			}

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		return ret;
	}

	/**
	 * レスポンスデータを生成する
	 * @param DownLoadPath : ファイルパス生成ファイル格納先(ダウンロードパラメータ)
	 * @return RequestResponsKindBean : レスポンスデータ（機能）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath,  RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//ファイルパス	生成ファイル格納先
			reqData.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//処理結果①	成功可否
			reqData.addFieldVale(0, 0, "flg_return", "true");
			//処理結果②	メッセージ
			reqData.addFieldVale(0, 0, "msg_error", "");
			//処理結果③	処理名称
			reqData.addFieldVale(0, 0, "no_errmsg", "");
			//処理結果⑥	メッセージ番号
			reqData.addFieldVale(0, 0, "nm_class", "");
			//処理結果④	エラーコード
			reqData.addFieldVale(0, 0, "cd_error", "");
			//処理結果⑤	システムメッセージ
			reqData.addFieldVale(0, 0, "msg_system", "");
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return reqData;
	}

}
