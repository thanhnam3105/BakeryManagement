package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;


/**
 * 原料情報、会社/工場（部署）洗い換え
 * :洗い換え先工場で原料情報を再取得し、再取得後の原料情報をレスポンスに設定する。
 * @author isono
 * @since  2009/06/07
 */
public class KoujouAraigaeLogic extends LogicBase {

	/**
	 * 原料情報、会社/工場（部署）洗い換えコンストラクタ
	 * : インスタンス生成
	 */
	public KoujouAraigaeLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 分析原料DB検索処理ロジック管理
	 * @param reqData : 機能リクエストデータ
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
		//検索用SQL格納用バッファ
		StringBuffer strSql = new StringBuffer();
		//検索結果格納用リスト
		List<?> lstRecset = null;
		//2011/06/02 QP@10181_No.66 TT T.Satoh Add Start -------------------------
		//検索結果格納用リスト(研究所用)
		List<?> kenkyujoRecset = null;
		//2011/06/02 QP@10181_No.66 TT T.Satoh Add End ---------------------------

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {

			//データの検索

			//SQL生成
			//2011/06/02 QP@10181_No.66 TT T.Satoh Change Start -------------------------
			//部署が研究所の場合
			if(reqData.getFieldVale(0, 0, "cd_new_busho").equals(ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KOJO"))
					&& reqData.getFieldVale(0, 0, "cd_new_kaisha").equals(ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA"))){
				strSql = this.AraigaeSQL(reqData);
				//DB検索用セッションを取得
				this.createSearchDB();
				//検索実行結果取得
				lstRecset = this.searchDB.dbSearch(strSql.toString());

				//検索用SQL格納用バッファ初期化
				strSql.delete(0, strSql.length());
				//研究所の原料データ取得用SQL作成
				strSql = this.KenkyujoSQL(reqData);
				//DB検索用セッションを取得
				this.createSearchDB();
				//検索実行結果取得
				kenkyujoRecset = this.searchDB.dbSearch(strSql.toString());
			}
			//部署が研究所以外の場合
			else {
				strSql = this.CreateSQL(reqData);
				//DB検索用セッションを取得
				this.createSearchDB();
				//検索実行結果取得
				lstRecset = this.searchDB.dbSearch(strSql.toString());
			}

//			strSql = this.CreateSQL(reqData);
//			//DB検索用セッションを取得
//			this.createSearchDB();
//			//検索実行結果取得
//			lstRecset = this.searchDB.dbSearch(strSql.toString());
			//2011/06/02 QP@10181_No.66 TT T.Satoh Change End ---------------------------

// 20160513  KPX@1600766 ADD start
			//試作原料データの会社の単価開示権限を取得する（リテラルマスタ）
			boolean blnKengen = getTankaHyouji_kengen(reqData, userInfoData);
// 20160513  KPX@1600766 ADD end
			//レスポンスの生成

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			// 機能IDの設定
			resKind.setID(toString(reqData.getID()));
			// テーブル名の設定
			resKind.addTableItem(toString(reqData.getTableID(0)));
			//検索結果をレスポンスデータに格納
			//2011/06/06 QP@10181_No.66 TT T.Satoh Change Start -------------------------
			//storageData(lstRecset, resKind.getTableItem(0), reqData);

// 20160513  KPX@1600766 MOD start
			//単価開示不可の会社の場合、取得した試作原料データより単価を空白に変更して返す。
//			storageData(lstRecset, kenkyujoRecset, resKind.getTableItem(0), reqData);
			storageData(lstRecset, kenkyujoRecset, resKind.getTableItem(0), reqData, blnKengen);
// 20160513  KPX@1600766 MOD start
			//2011/06/06 QP@10181_No.66 TT T.Satoh Change End ---------------------------

		} catch (Exception e) {
			this.em.ThrowException(e, "分析原料検索DB処理に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			lstRecset = null;

			//セッションのクローズ
			searchDB.Close();
			searchDB = null;

			//変数の削除
			strSql = null;

		}
		return resKind;

	}

	/**
	 * 分析原料データ取得SQL作成
	 * @param reqData : 機能リクエストデータ
	 * @return 分析原料検索用SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer CreateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ＳＱＬ
		StringBuffer strRetSQL = new StringBuffer();
		//原料条件
		String strGenryoVar = "";

		try {

			//原料条件を生成
			strGenryoVar = MakeGenryoVar(reqData);

			//SQLの作成
			strRetSQL.append("SELECT ");
			strRetSQL.append("  M401.cd_genryo     AS 原料CD ");
			strRetSQL.append(" ,M401.cd_kaisha     AS 会社CD ");
			strRetSQL.append(" ,M402_1.cd_busho    AS 部署CD ");
			strRetSQL.append(" ,M402_1.nm_genryo   AS 原料名称1  ");
			strRetSQL.append(" ,M402_2.nm_genryo   AS 原料名称2 ");
			strRetSQL.append(" ,M402_1.tanka       AS 単価  ");
			strRetSQL.append(" ,M402_1.budomari    AS 歩留  ");
			strRetSQL.append(" ,M401.ritu_abura    AS 油含有率  ");
			strRetSQL.append(" ,M401.ritu_sakusan  AS 酢酸  ");
			strRetSQL.append(" ,M401.ritu_shokuen  AS 食塩  ");
			strRetSQL.append(" ,M401.ritu_sousan   AS 総酸  ");
			strRetSQL.append(" ,M402_1.budomari    AS マスタ歩留  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1  ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append(" AND (M402_1.cd_busho = " + reqData.getFieldVale(0, 0, "cd_new_busho") + " OR M402_1.cd_busho = 0)");
			strRetSQL.append(" LEFT JOIN ( ");
			strRetSQL.append("    SELECT DISTINCT ");
			strRetSQL.append("      cd_kaisha ");
			strRetSQL.append("    , cd_genryo ");
			strRetSQL.append("    , nm_genryo ");
			strRetSQL.append("    FROM ");
			strRetSQL.append("       ma_genryokojo ");
			strRetSQL.append("    WHERE ");
			strRetSQL.append("       cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    ) AS M402_2  ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_2.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_2.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");

		} catch (Exception e) {
			this.em.ThrowException(e, "会社/工場洗い換えＤＢ検索処理に失敗しました。");

		} finally {
			//変数の削除

		}
		//②:作成したSQLを返却する。
		return strRetSQL;

	}

	//2011/05/30 QP@10181_No.66 TT T.Satoh Add Start -------------------------
	/**
	 * 研究所用分析原料データ取得SQL作成
	 * @param reqData : 機能リクエストデータ
	 * @return 分析原料検索用SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer AraigaeSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ＳＱＬ
		StringBuffer strRetSQL = new StringBuffer();
		//原料条件
		String strGenryoVar = "";

		try {

			//原料条件を生成
			strGenryoVar = MakeGenryoVar(reqData);

			//SQLの作成
			strRetSQL.append("SELECT ");
			strRetSQL.append("  DISTINCT M401.cd_genryo   AS 原料CD ");
			strRetSQL.append(" ,M401.cd_kaisha            AS 会社CD ");
			strRetSQL.append(" ,M402_1.cd_busho           AS 部署CD ");
			strRetSQL.append(" ,('☆' + M402_1.nm_genryo) AS 原料名称1 ");
			strRetSQL.append(" ,('☆' + M402_1.nm_genryo) AS 原料名称2 ");
			strRetSQL.append(" ,M402_2.tanka              AS 単価  ");
			strRetSQL.append(" ,M402_3.budomari           AS 歩留  ");
			strRetSQL.append(" ,M401.ritu_abura           AS 油含有率  ");
			strRetSQL.append(" ,M401.ritu_sakusan         AS 酢酸  ");
			strRetSQL.append(" ,M401.ritu_shokuen         AS 食塩  ");
			strRetSQL.append(" ,M401.ritu_sousan          AS 総酸  ");
			strRetSQL.append(" ,M402_3.budomari           AS マスタ歩留  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1 ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append(" INNER JOIN ( ");
			strRetSQL.append("    SELECT ");
			strRetSQL.append("      M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo AS cd_genryo ");
			strRetSQL.append("    , MAX(M402.tanka) AS tanka ");
			strRetSQL.append("    FROM ma_genryo M401");
			strRetSQL.append("    LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("    ON M401.cd_kaisha = M402.cd_kaisha ");
			strRetSQL.append("    AND M401.cd_genryo = M402.cd_genryo ");
			strRetSQL.append("    WHERE M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("       AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    GROUP BY M401.cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo) AS M402_2 ");
			strRetSQL.append(" ON M401.cd_kaisha = M402_2.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_2.cd_genryo ");
			strRetSQL.append(" AND M402_1.tanka = M402_2.tanka ");
			strRetSQL.append(" LEFT JOIN ( ");
			strRetSQL.append("    SELECT ");
			strRetSQL.append("      M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo AS cd_genryo ");
			strRetSQL.append("    , MIN(M402.budomari) AS budomari ");
			strRetSQL.append("    FROM ma_genryo M401 ");
			strRetSQL.append("    LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("    ON M401.cd_kaisha = M402.cd_kaisha ");
			strRetSQL.append("    AND M401.cd_genryo = M402.cd_genryo ");
			strRetSQL.append("    WHERE M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("       AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    GROUP BY M401.cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo) AS M402_3 ");
			strRetSQL.append(" ON M401.cd_kaisha = M402_3.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_3.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");

		} catch (Exception e) {
			this.em.ThrowException(e, "会社/工場洗い換えＤＢ検索処理に失敗しました。");

		} finally {
			//変数の削除

		}
		//②:作成したSQLを返却する。
		return strRetSQL;

	}

	/**
	 * 研究所用分析原料データ取得SQL作成
	 * @param reqData : 機能リクエストデータ
	 * @return 分析原料検索用SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer KenkyujoSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ＳＱＬ
		StringBuffer strRetSQL = new StringBuffer();
		//原料条件
		String strGenryoVar = "";

		try {

			//原料条件を生成
			strGenryoVar = MakeGenryoVar(reqData);

			//SQLの作成
			strRetSQL.append("SELECT ");
			strRetSQL.append("  DISTINCT M401.cd_genryo AS 原料CD ");
			strRetSQL.append(" ,M401.cd_kaisha          AS 会社CD ");
			strRetSQL.append(" ,M402_1.cd_busho         AS 部署CD ");
			strRetSQL.append(" ,M402_1.nm_genryo        AS 原料名称1 ");
			strRetSQL.append(" ,M402_1.nm_genryo        AS 原料名称2 ");
			strRetSQL.append(" ,M402_1.tanka            AS 単価  ");
			strRetSQL.append(" ,M402_1.budomari         AS 歩留  ");
			strRetSQL.append(" ,M401.ritu_abura         AS 油含有率  ");
			strRetSQL.append(" ,M401.ritu_sakusan       AS 酢酸  ");
			strRetSQL.append(" ,M401.ritu_shokuen       AS 食塩  ");
			strRetSQL.append(" ,M401.ritu_sousan        AS 総酸  ");
			strRetSQL.append(" ,M402_1.budomari         AS マスタ歩留  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1 ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("AND M402_1.cd_busho = " + reqData.getFieldVale(0, 0, "cd_new_busho") + " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "会社/工場洗い換えＤＢ検索処理に失敗しました。");

		} finally {
			//変数の削除

		}
		//②:作成したSQLを返却する。
		return strRetSQL;

	}
	//2011/05/30 QP@10181_No.66 TT T.Satoh Add End ---------------------------

	/**
	 * 原料CDを条件に変換
	 * @param reqData　：りくえすとでーた
	 * @return　：　条件
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeGenryoVar(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		boolean flg = true;

		try{

			for(int ix = 0; ix < reqData.getCntRow(0); ix++){

				if (flg){
					flg = false;

				}else{
					ret += ",";
				}
				ret += "'" + reqData.getFieldVale(0, ix, "cd_genryo") + "'";

			}

		}catch (Exception e) {
			this.em.ThrowException(e, "原料の条件指定文字列の生成に失敗しました。");

		} finally {
			//変数の削除

		}


		return ret;

	}

// 20160513  KPX@1600766 ADD start
	/**
	 * 単価開示権限取得
	 * @param reqData : リクエストデータ（機能）
	 * @return boolean  : 単価開示権限 （true：単価開示許可  false：単価開示不可）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private boolean getTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		boolean ret = false;			//単価開示不可

		//ユーザー情報退避
		userInfoData = _userInfoData;

		//リテラルマスタ検索クラス
		LiteralDataSearchLogic clsLiteralSearch = null;
		//リテラルマスタリクエスト
		RequestResponsKindBean reqLiteral = null;
		//リテラルマスタ検索レスポンス
		RequestResponsKindBean resLiteral = null;

		String value1 = "";

		try {
			//リクエストインスタンス
			reqLiteral = new RequestResponsKindBean();
			//リテラルマスタ検索リクエスト生成
			reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
			//会社コード
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_new_kaisha"));

			//リテラルマスタ検索：単価開示権限
			clsLiteralSearch = new LiteralDataSearchLogic();
			resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

			if (resLiteral != null) {
				value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
			}

			if (value1.equals("1") || value1.equals("9")) {
				ret = true;		//単価開示許可
			}

		} catch (Exception e) {
			//該当データ無しの時は開示不可
			ret = false;

		} finally {

		}
		return ret;
	}
// 20160513  KPX@1600766 ADD end


	/**
	 * レスポンスデータを生成する
	 * @param lstGenryoData : 検索結果情報リスト
	 * @param kengen  ：単価開示権限（true：許可　false：開示不可）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(List<?> lstData
			//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
			, List<?> kenkyujoLstData
			//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			// 20160513  KPX@1600766 ADD end
			, boolean kengen
			// 20160513  KPX@1600766 ADD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] items = null;
		Object[] tgItems = null;
		//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
		Object[] kenkyujoItems = null;
		boolean gaitoFlg = false;
		//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------

		try {

			//リクエストの原料分、レスポンスを生成する
			for(int ix = 0; ix < reqData.getCntRow(0); ix++){

				//リクエストされた原料に該当するデータを検索する
				for (int i = 0; i < lstData.size(); i++) {

					items = (Object[]) lstData.get(i);
					tgItems = null;

					//2011/06/06 QP@10181_No.66 TT T.Satoh Change Start -------------------------
//					if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
//						//該当するデータがある場合
//						tgItems = items;
//						break;
//
//					}

					//研究所の原料データがなかった場合
					if (kenkyujoLstData == null) {
						if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
						//該当するデータがある場合
						tgItems = items;
						break;

						}
					}
					//研究所の原料データがあった場合
					else {
						//研究所の原料データの配列をループ
						for (int j = 0; j < kenkyujoLstData.size(); j++) {

							//研究所の原料データの配列を1行取得
							kenkyujoItems = (Object[]) kenkyujoLstData.get(j);

							//原料コードが一致する場合
							if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))
									&& items[0].toString().equals(kenkyujoItems[0].toString())) {
								//原料データの配列内容入れ替え
								tgItems = kenkyujoItems;
								gaitoFlg = true;
								break;

							}

						}

						if (gaitoFlg) {
							//原料データの内容を入れ替えていた場合

						}
						else if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
							//該当するデータがある場合
							tgItems = items;
							gaitoFlg = true;
							break;

						}
					}

					//該当するデータがあった場合
					if (gaitoFlg) {
						//ループを抜ける
						break;
					}
					//2011/06/06 QP@10181_No.66 TT T.Satoh Change End ---------------------------

				}

				//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
				gaitoFlg = false;
				//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------

				//原料順
				resTable.addFieldVale("rec" + ix, "sort_genryo"
						, reqData.getFieldVale(0, ix, "sort_genryo"));
				//原料CD
				resTable.addFieldVale("rec" + ix, "cd_genryo"
						, reqData.getFieldVale(0, ix, "cd_genryo"));
				//会社CD
				resTable.addFieldVale("rec" + ix, "cd_kaisha"
						, reqData.getFieldVale(0, ix, "cd_new_kaisha"));
				//部署CD（工場CD）
				//新規原料の場合は0挿入
				if(reqData.getFieldVale(0, ix, "cd_busho").equals("0")){
					resTable.addFieldVale("rec" + ix, "cd_busho"	, "0");
				}else{
					resTable.addFieldVale("rec" + ix, "cd_busho"
							, reqData.getFieldVale(0, ix, "cd_new_busho"));
				}


				if (tgItems == null){
					//★会社に原料が存在しない場合

					//原料名称
					resTable.addFieldVale("rec" + ix, "nm_genryo"
							, "★" + reqData.getFieldVale(0, ix, "nm_genryo"));
					//単価
					resTable.addFieldVale("rec" + ix, "tanka"
							, reqData.getFieldVale(0, ix, "tanka"));
					//歩留
					resTable.addFieldVale("rec" + ix, "budomari"
							, reqData.getFieldVale(0, ix, "budomari"));
					//油含有率
					resTable.addFieldVale("rec" + ix, "ritu_abura"
							, reqData.getFieldVale(0, ix, "ritu_abura"));
					//酢酸
					resTable.addFieldVale("rec" + ix, "ritu_sakusan"
							, reqData.getFieldVale(0, ix, "ritu_sakusan"));
					//食塩
					resTable.addFieldVale("rec" + ix, "ritu_shokuen"
							, reqData.getFieldVale(0, ix, "ritu_shokuen"));
					//総酸
					resTable.addFieldVale("rec" + ix, "ritu_sousan"
							, reqData.getFieldVale(0, ix, "ritu_sousan"));

				}else{
					//会社に原料が存在する場合

					if(toString(tgItems[3]) == ""){
						//☆工場に存在しない場合

						//原料名称
						String mark = "";
						//新規原料の場合に☆つけない
						if(toString(tgItems[3]).equals("0")){

						}else{
							mark = "☆";
						}

						resTable.addFieldVale("rec" + ix, "nm_genryo"
								, mark + toString(tgItems[4]));
						//単価
						resTable.addFieldVale("rec" + ix, "tanka"
								, reqData.getFieldVale(0, ix, "tanka"));
						//歩留
						resTable.addFieldVale("rec" + ix, "budomari"
								, reqData.getFieldVale(0, ix, "budomari"));

					}else{
						//☆工場に存在する場合

						//原料名称
						resTable.addFieldVale("rec" + ix, "nm_genryo"
								, toString(tgItems[3]));
						//単価
						resTable.addFieldVale("rec" + ix, "tanka"
								, toString(tgItems[5]));
						//歩留
						resTable.addFieldVale("rec" + ix, "budomari"
								, toString(tgItems[6]));

					}

					//油含有率
					resTable.addFieldVale("rec" + ix, "ritu_abura"
							, toString(tgItems[7]));
					//酢酸
					resTable.addFieldVale("rec" + ix, "ritu_sakusan"
							, toString(tgItems[8]));
					//食塩
					resTable.addFieldVale("rec" + ix, "ritu_shokuen"
							, toString(tgItems[9]));
					//総酸
					resTable.addFieldVale("rec" + ix, "ritu_sousan"
							, toString(tgItems[10]));

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.14
					//新規原料の場合は単価・歩留は変更しない
					if(toString(tgItems[2]).equals("0")){
						//単価
						resTable.addFieldVale("rec" + ix, "tanka"
								, reqData.getFieldVale(0, ix, "tanka"));
						//歩留
						resTable.addFieldVale("rec" + ix, "budomari"
								, reqData.getFieldVale(0, ix, "budomari"));
					}
//mod end --------------------------------------------------------------------------------

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
					//マスタ歩留
					resTable.addFieldVale("rec" + ix, "ma_budomari"
							, toString(tgItems[11]));
//mod end --------------------------------------------------------------------------------

// 20160513  KPX@1600766 ADD start
					if (kengen == false) {
						//単価・歩留が開示不可の時、空白にする
						resTable.setFieldVale("rec" + ix, "tanka", "");
						resTable.setFieldVale("rec" + ix, "budomari", "");
						resTable.setFieldVale("rec" + ix, "ma_budomari", "");
					}
// 20160513  KPX@1600766 ADD end

				}

				//共通のレスポンス（処理の実行状態）

				//処理結果① 成功可否
				resTable.addFieldVale("rec" + ix, "flg_return"
						, "true");
				//処理結果② メッセージ
				resTable.addFieldVale("rec" + ix, "msg_error"
						, "");
				//処理結果③ 処理名称
				resTable.addFieldVale("rec" + ix, "no_errmsg"
						, "");
				//処理結果⑥ メッセージ番号
				resTable.addFieldVale("rec" + ix, "nm_class"
						, "");
				//処理結果④ エラーコード
				resTable.addFieldVale("rec" + ix, "cd_error"
						, "");
				//処理結果⑤ システムメッセージ
				resTable.addFieldVale("rec" + ix, "msg_system"
						, "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "会社/工場洗い換えレスポンスデータの生成に失敗しました。");

		} finally {
			tgItems = null;

		}

	}

}
