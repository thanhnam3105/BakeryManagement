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
 *
 * [S3-53 SA580] 試作コード入力検索ＤＢ処理
 * @author TT.katayama
 * @since 2009/04/08
 *
 */
public class InputGenryoSearchLogic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public InputGenryoSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作コード入力データ取得処理
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

		//リクエスト
		String strReqKaishaCd = "";
		String strReqBushoCd = "";
		String strReqGenryoCd = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①：リクエストデータより、試作コード入力検索条件を抽出し、試作コード入力検索データ情報を取得するSQLを作成する。

			//機能リクエストデータより取得
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");

			//SQL文の作成
			strSql = createSQL(strReqKaishaCd,strReqBushoCd,strReqGenryoCd);

			//②－①：データベース検索を用い、試作原料データを取得する。
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
			//②－②：部署が研究所で該当データが無かった場合、この時点で別工場に原料が存在するか検索を行い試作原料データを取得する。
			if(strReqBushoCd.equals(ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KOJO"))
					&& strReqKaishaCd.equals(ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA"))
					&& lstRecset.size() == 0 ){
				strSql.setLength(0);
				strSql = createStudySQL(strReqKaishaCd,strReqGenryoCd);
				super.createSearchDB();
				lstRecset = this.searchDB.dbSearch(strSql.toString());
			}
//mod end --------------------------------------------------------------------------------

			//③：試作原料データパラメーター格納メソッドを呼出し、レスポンスデータを形成する。

// 20160513  KPX@1600766 ADD start
			//③－１：試作原料データの会社の単価開示権限を取得する（リテラルマスタ）
			boolean blnKengen = getTankaHyouji_kengen(reqData, userInfoData);

// 20160513  KPX@1600766 ADD end

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

// 20160513  KPX@1600766 MOD start
			//③－２：単価開示不可の会社の場合、取得した試作原料データより単価を空白に変更して返す。
			//レスポンスデータの形成
//			storageInputGenryoData(lstRecset, resKind.getTableItem(0));
			storageInputGenryoData(lstRecset, resKind.getTableItem(0), blnKengen);
// 20160513  KPX@1600766 MOD end
		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード入力データ取得処理が失敗しました。");

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
	 * 検索用SQL作成
	 * @param strKaishaCd : 会社コード　
	 * @param strBushoCd : 部署コード
	 * @param strGenryoCd : 原料コード
	 * @return 作成したSQL
	 */
	private StringBuffer createSQL(String strKaishaCd, String strBushoCd, String strGenryoCd) {
		StringBuffer strRetSql = new StringBuffer();;

		//①：試作コード入力情報を取得するためのSQLを作成
		strRetSql.append(" SELECT  ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,M402.nm_genryo AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append("  ,kbn_haishi AS kbn_haishi ");
//add start ---------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
		strRetSql.append("  ,M402.cd_busho AS cd_busho ");
//add end -----------------------------------------------------------------------------------
//add start ------------------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
		strRetSql.append(" ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS ma_budomari ");
//add end --------------------------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
// ADD end 20121003 QP@20505 No.24
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");
		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M402.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");

		//②:作成したSQLを返却
		return strRetSql;
	}


//add start ------------------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
	/**
	 * 研究所用・検索SQL作成
	 * @param strKaishaCd : 会社コード　
	 * @param strBushoCd : 部署コード
	 * @param strGenryoCd : 原料コード
	 * @return 作成したSQL
	 */
	private StringBuffer createStudySQL(String strKaishaCd, String strGenryoCd){
		StringBuffer strRetSql = new StringBuffer();;

		//①－①：試作コード入力情報を取得するためのSQLを作成(単価が一番高い原料情報を取得し、歩留のみ一番低いものを表示)
		strRetSql.append(" SELECT  ");
		strRetSql.append(" TOP 1 ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,('☆' + M402.nm_genryo) AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");

		//①－②：歩留の取得方法[全工場で検索を行い一番低い歩留を取得する](2009/12/18変更)
		strRetSql.append(" ,ISNULL(CONVERT(VARCHAR,( SELECT  ");
		strRetSql.append(" TOP 1 ");
		strRetSql.append("  M402_budomari.budomari AS budomari ");
		strRetSql.append(" FROM ma_genryo M401_budomari ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402_budomari ");
		strRetSql.append("   ON  M402_budomari.cd_kaisha = M401_budomari.cd_kaisha ");
		strRetSql.append("   AND M402_budomari.cd_genryo = M401_budomari.cd_genryo ");
		strRetSql.append(" WHERE M401_budomari.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M401_budomari.cd_genryo = '" + strGenryoCd + "' ");
		strRetSql.append("  AND M402_budomari.budomari IS NOT NULL ");
		strRetSql.append(" ORDER BY ");
		strRetSql.append(" budomari  )),'') AS budomari ");
		//------------------------------------------------------------------------------
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append("  ,kbn_haishi AS kbn_haishi ");
		strRetSql.append("  ,M402.cd_busho AS cd_busho ");
	//add start ------------------------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.31
		strRetSql.append(" ,M403.budomari AS ma_budomari ");
	//add end --------------------------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
// ADD end 20121003 QP@20505 No.24
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");

	//add start ------------------------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.31
		strRetSql.append(" LEFT JOIN ma_genryokojo M403 ");
		strRetSql.append(" ON M403.cd_kaisha = M402.cd_kaisha ");
		strRetSql.append(" AND M403.cd_busho = M402.cd_busho ");
		strRetSql.append(" AND M403.cd_genryo = M402.cd_genryo ");
	//add end --------------------------------------------------------------------------------------------------

		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");

		strRetSql.append(" ORDER BY ");
		strRetSql.append(" M402.tanka DESC ");

		//②:作成したSQLを返却
		return strRetSql;
	}
//add end ----------------------------------------------------------------------------------------------

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
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_kaisha"));

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

// 20160513  KPX@1600766 MOD start
	/**
	 * 試作コード入力データパラメーター格納
	 * @param lstGenryoData : 検索結果情報リスト
	 * @param kengen  ：単価開示権限（true：許可　false：開示不可）
	 */
//	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable)
	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable, boolean kengen)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
// 20160513  KPX@1600766 MOD end
		//①：引数　検索結果情報リストに保持している各パラメーターをレスポンスデータへ格納する。
		try {

			for (int i = 0; i < lstGenryoData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstGenryoData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec" + i, "cd_genryo", toString(items[0]));

				if(toString(items[1]).equals("")){
					resTable.addFieldVale("rec" + i, "nm_genryo", "★" + toString(items[1]));
				}
				else{
					resTable.addFieldVale("rec" + i, "nm_genryo", toString(items[1]));
				}

// 20160513  KPX@1600766 MOD start
//				resTable.addFieldVale("rec" + i, "tanka", toString(items[2]));
//				resTable.addFieldVale("rec" + i, "budomari", toString(items[3]));
				if (kengen) {
					resTable.addFieldVale("rec" + i, "tanka", toString(items[2]));
					resTable.addFieldVale("rec" + i, "budomari", toString(items[3]));
				} else {
					resTable.addFieldVale("rec" + i, "tanka", "");
					resTable.addFieldVale("rec" + i, "budomari", "");
				}
// 20160513  KPX@1600766 MOD end
				resTable.addFieldVale("rec" + i, "ritu_abura", toString(items[4]));
				resTable.addFieldVale("rec" + i, "ritu_sakusan", toString(items[5]));
				resTable.addFieldVale("rec" + i, "ritu_shokuen", toString(items[6]));
				resTable.addFieldVale("rec" + i, "ritu_sousan", toString(items[7]));
				resTable.addFieldVale("rec" + i, "kbn_haishi", toString(items[8]));
//add start ---------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
				resTable.addFieldVale("rec" + i, "cd_busho", toString(items[9]));
//add end ----------------------------------------------------------------------------------
//add start ---------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
// 20160513  KPX@1600766 MOD start
//				resTable.addFieldVale("rec" + i, "ma_budomari", toString(items[10]));
				if (kengen) {
					resTable.addFieldVale("rec" + i, "ma_budomari", toString(items[10]));
				} else {
					resTable.addFieldVale("rec" + i, "ma_budomari", "");
				}
// 20160513  KPX@1600766 MOD end
//add end ----------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
				resTable.addFieldVale("rec" + i, "ritu_msg", toString(items[11]));
// ADD end 20121003 QP@20505 No.24
			}

			if (lstGenryoData.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec0", "cd_genryo", "");
				resTable.addFieldVale("rec0", "nm_genryo", "");
				resTable.addFieldVale("rec0", "tanka", "");
				resTable.addFieldVale("rec0", "budomari", "");
				resTable.addFieldVale("rec0", "ritu_abura", "");
				resTable.addFieldVale("rec0", "ritu_sakusan", "");
				resTable.addFieldVale("rec0", "ritu_shokuen", "");
				resTable.addFieldVale("rec0", "ritu_sousan", "");
				resTable.addFieldVale("rec0", "kbn_haishi", "");
//add start ---------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
				resTable.addFieldVale("rec0", "cd_busho", "");
//add end -----------------------------------------------------------------------------------
//add start ---------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
				resTable.addFieldVale("rec0", "ma_budomari", "");
//add end -----------------------------------------------------------------------------------
// ADD start 20121003 QP@20505 No.24
				resTable.addFieldVale("rec0", "ritu_msg", "");
// ADD end 20121003 QP@20505 No.24
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード入力データパラメーター格納処理が失敗しました。");

		} finally {

		}

	}

}
