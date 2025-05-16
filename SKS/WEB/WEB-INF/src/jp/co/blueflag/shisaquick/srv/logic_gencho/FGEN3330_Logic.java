package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材コード検索DB処理 機能ID：FGEN3330　
 *
 * @author TT.Shima
 * @since 2014/09/18
 */
public class FGEN3330_Logic extends LogicBase {

	/**
	 * 資材データ検索コンストラクタ : インスタンス生成
	 */
	public FGEN3330_Logic() {
		super();
	}

	/**
	 * 資材データ検索 : 資材データを検索する。
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
			strSql = createShizaiCodeSQL(reqData, strSql, strSqlWhere);

			// DBインスタンス生成
			createSearchDB();

			if (strSqlWhere != null) {
				// 検索実行（資材データ取得）
				lstRecset = searchDB.dbSearch(strSql.toString());
				// 検索結果がない場合
				if (lstRecset.size() == 0) {
					em.ThrowException(ExceptionKind.警告Exception, "W000401",
							strSql.toString(), "", "");
				}
			}

			// 機能IDの設定
			resKind.setID(reqData.getID());

			// テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			// レスポンスデータの形成
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "資材コード検索DB処理に失敗しました。");
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
	 * 資材情報取得SQL作成 : 資材情報を取得するSQLを作成。
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
			strAllSql.append("     T401.cd_shizai_new, "); // 資材コード
			strAllSql.append("     T341.nm_shizai_new, "); // 資材名
			strAllSql.append("     T401.cd_shizai, "); // 旧資材コード
			strAllSql.append("     T401.cd_shohin, "); // 製品（商品）コード
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
			strAllSql.append("     T401.han_pay ,"); // 版代
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

			em.ThrowException(e, "資材検索処理に失敗しました。");

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

			/* １行目 */
			// 手配済み
			String kbn_tehaizumi = "";
			// 未手配
			String kbn_mitehai = "";
			// 未入力
			String kbn_minyuryoku = "";
			/* ２行目 */
			// 資材コード
			String cd_shizai = "";
			// 資材名
			String nm_shizai = "";
			// 旧資材コード
			String cd_shizai_old = "";
			/* ３行目 */
			// 製品（商品）コード
			String cd_shohin = "";
			// 製品（商品）名
			String nm_shohin = "";
			// 納入先（製造工場）
			String cd_seizoukojo = "";
			/* ４行目 */
			// 対象資材
			String taisyo_shizai = "";
			// 発注先
			String cd_hattyusaki = "";
			// 発注者
			String cd_hattyusya = "";
			/* ５行目 */
			// 発注日From
			String dt_hattyu_from = "";
			// 発注日To
			String dt_hattyu_to = "";
			// 納入日From
			String dt_nonyu_from = "";
			// 納入日To
			String dt_nonyu_to = "";
			// 版代支払日From
			String dt_han_payday_from = "";
			// 版代支払日To
			String dt_han_payday_to = "";
			// 未支払
			String kbn_mshiharai = "";

			kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));

			// 資材手配済一覧で登録アップロード後の画面再ロード用
			String strMovement_condition = toString(userInfoData.getMovement_condition());

			// 手配済空チェック
			if (kbn_tehaizumi.equals("") && strMovement_condition.length() < 36) {
				return null;

			} else if (kbn_tehaizumi.equals("") &&  strMovement_condition.length() >= 36) {

				// 手配済チェックボックス
				kbn_tehaizumi = toString(userInfoData.getMovement_condition().get(0));

				/* 2行目 */
				// 資材コード
				cd_shizai = toString(userInfoData.getMovement_condition().get(1));
				// 資材名
				nm_shizai = toString(userInfoData.getMovement_condition().get(2));
				// 旧資材コード
				cd_shizai_old = toString(userInfoData.getMovement_condition().get(3));

				/* 3行目 */
				// 製品コード
				cd_shohin = toString(userInfoData.getMovement_condition().get(4));
				// 製品名
				nm_shohin = toString(userInfoData.getMovement_condition().get(5));
				// 納入先
				cd_seizoukojo = toString(userInfoData.getMovement_condition().get(6));

				/* 4行目 */
				// 対象資材
				taisyo_shizai = toString(userInfoData.getMovement_condition().get(7));
				// 発注先
				cd_hattyusaki = toString(userInfoData.getMovement_condition().get(8));
				// 発注者
				cd_hattyusya = toString(userInfoData.getMovement_condition().get(9));

				/* 5行目 */
				// 発注日（from）
				String dt_hattyu_from_before = toString(userInfoData.getMovement_condition().get(10));
				dt_hattyu_from = getDateFormat(dt_hattyu_from_before);
				// 発注先（to）
				String dt_hattyu_to_before = toString(userInfoData.getMovement_condition().get(11));
				dt_hattyu_to = getDateFormat(dt_hattyu_to_before);
				// 納入日（from）
				String dt_nonyu_from_before = toString(userInfoData.getMovement_condition().get(12));
				dt_nonyu_from = getDateFormat(dt_nonyu_from_before);
				// 納入日（to）
				String dt_nonyu_to_before = toString(userInfoData.getMovement_condition().get(13));
				dt_nonyu_to = getDateFormat(dt_nonyu_to_before);
				// 版代支払日（from）
				String dt_han_payday_from_before = toString(userInfoData.getMovement_condition().get(14));
				dt_han_payday_from = getDateFormat(dt_han_payday_from_before);
				// 版代支払日 (to）
				String dt_han_payday_to_before = toString(userInfoData.getMovement_condition().get(15));
				getDateFormat(dt_han_payday_to_before);
				// 未支払
				kbn_mshiharai = toString(userInfoData.getMovement_condition().get(16));

			} else {

				// 検索条件取得---------------------------------------------------------------------
				/* １行目 */
				// 手配済み
				kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));
				// 未手配
				kbn_mitehai = toString(reqData.getFieldVale(0, 0, "kbn_mitehai"));
				// 未入力
				kbn_minyuryoku = toString(reqData.getFieldVale(0, 0, "kbn_minyuryoku"));
				/* ２行目 */
				// 資材コード
				cd_shizai = toString(reqData.getFieldVale(0, 0, "cd_shizai"));
				// 資材名
				nm_shizai = toString(reqData.getFieldVale(0, 0, "nm_shizai"));
				// 旧資材コード
				cd_shizai_old = toString(reqData.getFieldVale(0, 0, "cd_shizai_old"));
				/* ３行目 */
				// 製品（商品）コード
				cd_shohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
				// 製品（商品）名
				nm_shohin = toString(reqData.getFieldVale(0, 0, "nm_shohin"));
				// 納入先（製造工場）
				cd_seizoukojo = toString(reqData.getFieldVale(0, 0, "cd_seizoukojo"));
				/* ４行目 */
				// 対象資材
				taisyo_shizai = toString(reqData.getFieldVale(0, 0, "taisyo_shizai"));
				// 発注先
				cd_hattyusaki = toString(reqData.getFieldVale(0, 0, "cd_hattyusaki"));
				// 発注者
				cd_hattyusya = toString(reqData.getFieldVale(0, 0, "cd_hattyusya"));
				/* ５行目 */
				// 発注日From
				String dt_hattyu_from_before = toString(reqData.getFieldVale(0, 0, "dt_hattyu_from"));
				dt_hattyu_from = getDateFormat(dt_hattyu_from_before);
				// 発注日To
				String dt_hattyu_to_before = toString(reqData.getFieldVale(0, 0, "dt_hattyu_to"));
				dt_hattyu_to = getDateFormat(dt_hattyu_to_before);
				// 納入日From
				String dt_nonyu_from_before = toString(reqData.getFieldVale(0, 0, "dt_nonyu_from"));
				dt_nonyu_from = getDateFormat(dt_nonyu_from_before);
				// 納入日To
				String dt_nonyu_to_before = toString(reqData.getFieldVale(0, 0, "dt_nonyu_to"));
				dt_nonyu_to = getDateFormat(dt_nonyu_to_before);
				// 版代支払日From
				String dt_han_payday_from_before = toString(reqData.getFieldVale(0, 0, "dt_han_payday_from"));
				dt_han_payday_from = getDateFormat(dt_han_payday_from_before);
				// 版代支払日To
				String dt_han_payday_to_before = toString(reqData.getFieldVale(0, 0, "dt_han_payday_to"));
				dt_han_payday_to = getDateFormat(dt_han_payday_to_before);
				// 未支払
				kbn_mshiharai = toString(reqData.getFieldVale(0, 0, "kbn_mshiharai"));
			}
			System.out.println("cd_seizoukojo:[" + cd_seizoukojo + "]");
            System.out.println("発注先：[" + cd_hattyusaki + "]");

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
				strWhere.append(" AND M104.nm_busho LIKE '%"
						+ cd_seizoukojo +  "%' ");
			}

			/* ４行目 */
			// 対象資材が入力されている場合
			if (!taisyo_shizai.equals("")) {
				strWhere.append(" AND M302A.cd_ｌiteral = '" + taisyo_shizai
						+ "' ");
			}
			// 発注先が入力されている場合
			if (!cd_hattyusaki.equals("")) {
				strWhere.append(" AND T341.cd_hattyusaki = CONVERT(int," + cd_hattyusaki + ") ");
			}
			// 発注者が入力されている場合
			if (!cd_hattyusya.equals("")) {
				strWhere.append(" AND T401.id_koshin = '" + cd_hattyusya + "'");
			}

			/* ５行目 */
			// 発注日(From,To)が入力されている場合
			if (!dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) BETWEEN '" + dt_hattyu_from + "' "
						+ " AND '" + dt_hattyu_to + "' ");
			}
			// 発注日(From)が入力されている場合
			else if (!dt_hattyu_from.equals("") && dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) >= '" + dt_hattyu_from
						+ "' ");
			}
			// 発注日(To)が入力されている場合
			else if (dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) <= '" + dt_hattyu_to
						+ "' ");
			}

			// 納入日(From,To)が入力されている場合
			if (!dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) BETWEEN '" + dt_nonyu_from + "' "
						+ " AND '" + dt_nonyu_to + "' ");
			}
			// 納入日(From)が入力されている場合
			else if (!dt_nonyu_from.equals("") && dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) >= '" + dt_nonyu_from
						+ "' ");
			}
			// 納入日(To)が入力されている場合
			else if (dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) <= '" + dt_nonyu_to
						+ "' ");
			}

			// 版代支払日(From,To)が入力されている場合
			if (!dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) BETWEEN '" + dt_han_payday_from + "' "
						+ " AND '" + dt_han_payday_to + "' ");
			}
			// 版代支払日(From)が入力されている場合
			else if (!dt_han_payday_from.equals("") && dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) >= '" + dt_han_payday_from
						+ "' ");
			}
			// 版代支払日(To)が入力されている場合
			else if (dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) <= '" + dt_han_payday_to
						+ "' ");
			}

			// 未支払がチェックされている場合
			if ("1".equals(kbn_mshiharai)) {
				// 版代支払日が空白 かつ 版代が0以外
				strWhere.append(" AND (T401.dt_han_payday IS NULL OR T401.dt_han_payday = '') ");
				strWhere.append(" AND T401.han_pay != 0 ");
			}

		} catch (Exception e) {

			em.ThrowException(e, "資材検索処理に失敗しました。");

		} finally {

		}

		return strWhere;
	}

	private String getDateFormat(String date) {
		StringBuffer strbuf = new StringBuffer();
		String strMM;
		String strDD;
		String[] strTemps;
		if (!date.equals("")) {

			//strHanPayDayBefore =  strHanPayDay.replace("/", "");

			if (date.indexOf("/") != -1) {

				strTemps = date.split("/");

				// 年取り出し
				strbuf.append(strTemps[0].toString());
				if (strTemps[1].toString().length() == 1) {
					strMM = strTemps[1].toString();
					strbuf.append("0" + strMM);

				} else {
					strbuf.append(strTemps[1].toString());;
				}
				if (strTemps[2].toString().length() == 1) {

					strDD = strTemps[2].toString();
					strbuf.append("0" + strDD);
				} else {
					strbuf.append(strTemps[2].toString());
				}

			} else {
				strbuf.append(date);
			}
		} else {
			strbuf.append("");
		}
		return strbuf.toString();

	}

	/**
	 * 資材データパラメーター格納 : 資材データ情報をレスポンスデータへ格納する。
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

			if ((lstRecset == null) || (lstRecset.size() == 0)) {

				// データが取得できない時：エラーにしない為（jsのメッセージを返したい）
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "sisakuNo", "");

			} else {



				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				String strMovement_condition = toString(userInfoData.getMovement_condition());

				if (strMovement_condition.length() >= 36 && strMovement_condition.length() < 50 ) {

					// 手配済チェックボックス
					String strChkTehaizumiTemp = toString(userInfoData.getMovement_condition().get(0));

					/* 2行目 */
					// 資材コード
					String strShizaiCdTemp = toString(userInfoData.getMovement_condition().get(1));
					// 資材名
					String strShizaiNmTemp = toString(userInfoData.getMovement_condition().get(2));
					// 旧資材コード
					String strKyuShizaiCdTemp = toString(userInfoData.getMovement_condition().get(3));

					/* 3行目 */
					// 製品コード
					String strSyouhinCdTemp = toString(userInfoData.getMovement_condition().get(4));
					// 製品名
					String strSyouhinNmTemp = toString(userInfoData.getMovement_condition().get(5));
					// 納入先
					String strSeizoukojoTemp = toString(userInfoData.getMovement_condition().get(6));

					/* 4行目 */
					// 対象資材
					String strDdlShizaiTemp = toString(userInfoData.getMovement_condition().get(7));
					// 発注先
					String strHattyusakiTemp = toString(userInfoData.getMovement_condition().get(8));
					// 発注者
					String strDdlTantoTemp = toString(userInfoData.getMovement_condition().get(9));

					/* 5行目 */
					// 発注日（from）
					String strHattyubiFromTemp = toString(userInfoData.getMovement_condition().get(10));
					// 発注先（to）
					String strHattyubiToTemp = toString(userInfoData.getMovement_condition().get(11));
					// 納入日（from）
					String strNounyudayFromTemp = toString(userInfoData.getMovement_condition().get(12));
					// 納入日（to）
					String strNounyudayToTemp = toString(userInfoData.getMovement_condition().get(13));
					// 版代支払日（from）
					String strHanPaydayFromTemp = toString(userInfoData.getMovement_condition().get(14));
					// 版代支払日 (to）
					String strHanPaydayToTemp = toString(userInfoData.getMovement_condition().get(15));
					// 未支払
					String strChkMshiharaiTemp = toString(userInfoData.getMovement_condition().get(16));

					// 手配済チェックボックス
					resTable.addFieldVale(0, "chkTehaizumi", strChkTehaizumiTemp);
					// 資材コード
					resTable.addFieldVale(0, "txtShizaiCd", strShizaiCdTemp);
					// 資材名
					resTable.addFieldVale(0, "txtShizaiNm", strShizaiNmTemp);
					// 旧資材コード
					resTable.addFieldVale(0, "txtOldShizaiCd", strKyuShizaiCdTemp);
					// 製品コード
					resTable.addFieldVale(0, "txtSyohinCd", strSyouhinCdTemp);
					// 製品名
					resTable.addFieldVale(0, "txtSyohinNm", strSyouhinNmTemp);
					// 納入先
					resTable.addFieldVale(0, "txtSeizoukojo", strSeizoukojoTemp);
					// 対象資材
					resTable.addFieldVale(0, "ddlShizai", strDdlShizaiTemp);
					// 発注先
					resTable.addFieldVale(0, "ddlHattyusaki", strHattyusakiTemp);
					// 発注者
					resTable.addFieldVale(0, "ddlTanto", strDdlTantoTemp);
					// 発注日from
					resTable.addFieldVale(0, "txtHattyubiFrom", strHattyubiFromTemp);
					// 発注日to
					resTable.addFieldVale(0, "txtHattyubiTo", strHattyubiToTemp);
					// 納入日
					resTable.addFieldVale(0, "txtNounyudayFrom", strNounyudayFromTemp);
					// 版代支払日from
					resTable.addFieldVale(0, "txtHanPaydayFrom", strHanPaydayFromTemp);
					// 版代支払日to
					resTable.addFieldVale(0, "txtHanPaydayTo", strHanPaydayToTemp);
					// 未支払
					resTable.addFieldVale(0, "chkMshiharai", strChkMshiharaiTemp);
				}
				// 制限件数 返却時に"0"でない場合は上限オーバー
				// 上限オーバーの場合は上限値を設定
				String limit = "0";

				// ループ回数
				int loop_cnt = lstRecset.size();

				// 最大行数の取得
				int strListRowMax = toInteger(ConstManager.getConstValue(
						ConstManager.Category.設定, "SHIZAI_CODE_LIST_ROW_MAX"));
				// 最大行数以上の件数が取得された場合
				if (strListRowMax < lstRecset.size()) {
					limit = toString(strListRowMax);
					loop_cnt = strListRowMax;
				}

				// 資材データループ
				for (int i = 0; i < loop_cnt; i++) {

					// 検索結果取得
					Object[] items = (Object[]) lstRecset.get(i);
					String row_no = toString(items[0]);					// 行番号
					String cd_shain = toString(items[1]);				// 社員コード
					String nen = toString(items[2]);					// 年
					String no_oi = toString(items[3]);					// 追番
					String seq_shizai = toString(items[4]);				// 行番
					String no_eda = toString(items[5]);					// 枝番
					String cd_shizai = toString(items[6]);				// 資材コード
					String nm_shizai = toString(items[7]);				// 資材名
					String cd_shizai_old = toString(items[8]);			// 旧資材コード
					String cd_shohin = toString(items[9]);				// 製品（商品）コード
					String nm_shohin = toString(items[10]);				// 製品（商品）名
					String nisugata = toString(items[11]);				// 荷姿
					String nm_taisyo_shizai = toString(items[12]);		// 対象資材名
					String cd_taisyo_shizai = toString(items[13]);		// 対象資材コード
					String nm_hattyusaki = toString(items[14]);			// 発注先
					String cd_hattyusaki = toString(items[15]);			// 発注先コード
					String flg_status = toString(items[16]);			// 手配ステータス
					String dt_koshin_disp = toString(items[17]);		// 登録日

					String naiyo = toString(items[18]);					// 内容
					String nounyusaki = toString(items[19]);			// 納入先（製造工場）
					String dt_han_payday = toString(items[20]);			// 版代支払日
					String han_pay = toString(items[21]);				// 版代
					String nm_file_aoyaki = toString(items[22]);		// 青焼ファイル名
					String file_path_aoyaki = toString(items[23]);		// 青焼ファイルパス
					String dt_hattyu = toString(items[24]);				// 発注日
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
					String nm_tanto = toString(items[35]);				// 担当者
					String nounyu_day = toString(items[36]);			// 納入日


					// レスポンス生成
					resTable.addFieldVale(i, "row_no", row_no);								// 行番号
					resTable.addFieldVale(i, "cd_shain", cd_shain);							// 社員コード
					resTable.addFieldVale(i, "nen", nen);									// 年
					resTable.addFieldVale(i, "no_oi", no_oi);								// 追番
					resTable.addFieldVale(i, "seq_shizai", seq_shizai);						// 行番
					resTable.addFieldVale(i, "no_eda", no_eda);								// 枝番
					resTable.addFieldVale(i, "cd_shizai", cd_shizai);						// 資材コード
					resTable.addFieldVale(i, "nm_shizai", nm_shizai);						// 資材名
					resTable.addFieldVale(i, "cd_shizai_old", cd_shizai_old);				// 旧資材コード
					resTable.addFieldVale(i, "cd_shohin", cd_shohin);						// 製品（商品）コード
					resTable.addFieldVale(i, "nm_shohin", nm_shohin);						// 製品（商品）名
					resTable.addFieldVale(i, "nisugata", nisugata);							// 荷姿
					resTable.addFieldVale(i, "nm_taisyo_shizai", nm_taisyo_shizai);			// 対象資材名
					resTable.addFieldVale(i, "cd_taisyo_shizai", cd_taisyo_shizai);			// 対象資材コード
					resTable.addFieldVale(i, "nm_hattyusaki", nm_hattyusaki);				// 発注先
					resTable.addFieldVale(i, "cd_hattyusaki", cd_hattyusaki);				// 発注先コード
					resTable.addFieldVale(i, "flg_status", flg_status);						// 手配ステータス
					resTable.addFieldVale(i, "dt_koshin_disp", dt_koshin_disp);				// 登録日(表示用)

					resTable.addFieldVale(i, "naiyo", naiyo);								// 内容
					resTable.addFieldVale(i, "nounyusaki", nounyusaki);						// 納入先（製造工場）
					resTable.addFieldVale(i, "dt_han_payday", dt_han_payday);				// 版代支払日
					resTable.addFieldVale(i, "han_pay", han_pay);							// 版代
					resTable.addFieldVale(i, "nm_file_aoyaki", nm_file_aoyaki);				// 青焼ファイル名

					resTable.addFieldVale(i, "file_path_aoyaki", file_path_aoyaki);			// 青焼ファイルパス
					resTable.addFieldVale(i, "dt_hattyu", dt_hattyu);						// 発注日
					resTable.addFieldVale(i, "sekkei1", sekkei1);							// 設計①
					resTable.addFieldVale(i, "sekkei2", sekkei2);							// 設計②
					resTable.addFieldVale(i, "sekkei3", sekkei3);							// 設計③
					resTable.addFieldVale(i, "zaishitsu", zaishitsu);						// 材質
					resTable.addFieldVale(i, "biko_tehai", biko_tehai);						// 備考
					resTable.addFieldVale(i, "printcolor", printcolor);						// 印刷色
					resTable.addFieldVale(i, "no_color", no_color);							// 色番号
					resTable.addFieldVale(i, "henkounaiyoushosai", henkounaiyoushosai);		// 変更内容詳細
					resTable.addFieldVale(i, "nouki", nouki);								// 納期
					resTable.addFieldVale(i, "suryo", suryo);								// 数量
					resTable.addFieldVale(i, "nm_tanto", nm_tanto);							// 担当者
					resTable.addFieldVale(i, "nounyu_day", nounyu_day);						// 納入日

					resTable.addFieldVale(i, "loop_cnt", Integer.toString(loop_cnt));	// 取得件数
					resTable.addFieldVale(i, "limit_over", limit);						// 最大行数

				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "資材コード検索処理に失敗しました。");

		} finally {

		}

	}
}
