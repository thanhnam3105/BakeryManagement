package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
 *
 * 担当者検索（営業）：担当者（営業）情報検索DB処理
 * @author Y.Nishigawa
 * @since  2011/01/28
 */
public class FGEN2110_Logic extends LogicBase{

	/**
	 * 担当者検索：担当者情報検索DB処理
	 * : インスタンス生成
	 */
	public FGEN2110_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者検索（営業）：担当者（営業）情報取得SQL作成
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
		// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//		List<?> MemberlstRecset = null;
		// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
			//SQL文の作成
			strSql = getTantouMemberLstCreateSQL(reqData, strSql);

			//メンバーリスト取得SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			HashMap<String, ArrayList<String>> mapMember = setTantoushaAry(lstRecset);
			// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end

			//SQL文の作成
			// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//			strSql = genryoData2CreateSQL(reqData, strSql);
			strSql = getTantouLstCreateSQL(reqData, strSql);
			// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end

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
			// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//			storageTantoushaData(lstRecset, resKind.getTableItem(strTableNm));
			storageTantoushaData(lstRecset, mapMember, resKind.getTableItem(strTableNm));
			// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end




		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）データ検索処理に失敗しました。");

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
	 * 担当者（営業）情報取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
	private StringBuffer getTantouLstCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end

		StringBuffer strAllSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			String strUserId = null;
			String strUserName = null;
			String strKaishaCd = null;
			String strBushoCd = null;
			String strPageNo = null;
			String strKbnShori = null;

			//営業（一般）権限コード取得
			String strEigyoIppan =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_IPPAN");
			//営業（本部権限）権限コード取得
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");
			//営業（システム管理者）権限コード取得
			String strEigyoSystem =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_SYSTEM");
			//営業（仮登録ユーザ）権限コード取得
			String strEigyoKari =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_KARI");
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			String strEigyoPass =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_PASS");
			String strEigyoCash =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_CASH");
			// ADD 2013/9/25 okano【QP@30151】No.28 end

			//ユーザIDの取得
			strUserId = toString(reqData.getFieldVale(0, 0, "id_user"));
			//ユーザ名の取得
			strUserName = toString(reqData.getFieldVale(0, 0, "nm_user"));
			//会社コードの取得
			strKaishaCd = toString(reqData.getFieldVale(0, 0, "cd_kaisha"));
			//部署コードの取得
			strBushoCd = toString(reqData.getFieldVale(0, 0, "cd_busho"));
			//選択ページNoの取得
			strPageNo = toString(reqData.getFieldVale(0, 0, "no_page"));
			//処理区分の取得
			strKbnShori = toString(reqData.getFieldVale(0, 0, "kbn_shori"));

			//機能ID取得用
			String strUKinoId = "";
			String strUDataId = "";

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString();
					//データIDを設定
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//ユーザIDが入力されている場合
			if (!strUserId.equals("")) {
				strWhere.append(" AND M101.id_user = '");
				strWhere.append(strUserId + "'");
			}
			//ユーザ名が入力されている場合
			if (!strUserName.equals("")) {
				strWhere.append(" AND M101.nm_user LIKE '%");
				strWhere.append(strUserName + "%'");
			}
			//会社が入力されている場合
			if (!strKaishaCd.equals("")) {
				strWhere.append(" AND M101.cd_kaisha = ");
				strWhere.append(strKaishaCd);
			}
			//部署が入力されている場合
			if (!strBushoCd.equals("")) {
				strWhere.append("AND  M101.cd_busho = ");
				strWhere.append(strBushoCd);
			}

			//担当者検索モード　且つ　権限が「自分のみ」の場合
			if(strUKinoId.equals("100") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND M101.id_user = ");
				strWhere.append(userInfoData.getId_user());
			}
			//担当者検索モード　且つ　権限が「全ての営業担当者（一般のみ）」の場合
			else if(strUKinoId.equals("101") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
			}
			//担当者検索モード　且つ　権限が「全ての営業担当者（一般、本部権限）」の場合
			else if(strUKinoId.equals("102") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND ( M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoHonbu + " )");
				// ADD 2013/11/7 QP@30154 okano start
				strWhere.append(" AND M101.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				// ADD 2013/11/7 QP@30154 okano end
			}
			//担当者検索モード　且つ　権限が「全ての営業担当者（一般、本部権限、システム管理者）」の場合
			else if(strUKinoId.equals("103") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND ( M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoHonbu);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoSystem + " )");
			}

			//SQL文の作成
			strAllSql.append(" SELECT ");
			strAllSql.append("  tbl.id_user ");
			strAllSql.append(" ,tbl.nm_user ");
			strAllSql.append(" ,tbl.cd_kengen ");
			strAllSql.append(" ,ISNULL(tbl.nm_kengen ");
			strAllSql.append(" 	,'') as nm_kengen ");
			strAllSql.append(" ,tbl.cd_kaisha ");
			strAllSql.append(" ,ISNULL(tbl.nm_kaisha ");
			strAllSql.append(" 	,'') as nm_kaisha ");
			strAllSql.append(" ,tbl.cd_busho ");
			strAllSql.append(" ,ISNULL(tbl.nm_busho ");
			strAllSql.append(" 	,'') as nm_busho ");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ,tbl.id_josi ");
			strAllSql.append(" ,tbl.nm_josi ");
			strAllSql.append(" FROM ( ");
			strAllSql.append(" 	SELECT ");
			strAllSql.append(" 	 datatbl.PageNO ");
			strAllSql.append(" 	,datatbl.id_user ");
			strAllSql.append(" 	,datatbl.nm_user ");
			strAllSql.append(" 	,datatbl.cd_kengen ");
			strAllSql.append(" 	,datatbl.nm_kengen ");
			strAllSql.append(" 	,datatbl.cd_kaisha ");
			strAllSql.append(" 	,datatbl.nm_kaisha ");
			strAllSql.append(" 	,datatbl.cd_busho ");
			strAllSql.append(" 	,datatbl.nm_busho ");
			strAllSql.append(" 	,datatbl.id_josi ");
			strAllSql.append(" 	,datatbl.nm_josi ");
			strAllSql.append(" 	FROM ( ");
			strAllSql.append(" 		SELECT ");
			strAllSql.append(" 		 Convert(int ");
			strAllSql.append(" 			,(ROW_NUMBER() OVER ( ");
			strAllSql.append(" 					ORDER BY M101.id_user)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append(" 		,RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append(" 				,M101.id_user) ");
			strAllSql.append(" 			,10) as id_user ");
			strAllSql.append(" 		,M101.nm_user ");
			strAllSql.append(" 		,M101.cd_kengen ");
			strAllSql.append(" 		,M102.nm_kengen ");
			strAllSql.append(" 		,M101.cd_kaisha ");
			strAllSql.append(" 		,M1041.nm_kaisha ");
			strAllSql.append(" 		,M101.cd_busho ");
			strAllSql.append(" 		,M1042.nm_busho ");
			strAllSql.append(" 		,M101.id_josi ");
			strAllSql.append(" 		,M101_2.nm_user AS nm_josi ");
			strAllSql.append(" 		FROM ma_user M101 ");
			strAllSql.append(" 		LEFT JOIN ( ");
			strAllSql.append(" 			SELECT ");
			strAllSql.append(" 			 cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha ");
			strAllSql.append(" 			FROM ma_busho ");
			strAllSql.append(" 			GROUP BY cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha) M1041 ");
			strAllSql.append(" 		ON M1041.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		LEFT JOIN ma_busho M1042 ");
			strAllSql.append(" 		ON M1042.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		AND M1042.cd_busho = M101.cd_busho ");
			strAllSql.append(" 		LEFT JOIN ma_kengen M102 ");
			strAllSql.append(" 		ON M101.cd_kengen = M102.cd_kengen ");
			strAllSql.append(" 		LEFT JOIN ma_user M101_2 ");
			strAllSql.append(" 		ON M101_2.id_user = M101.id_josi ");
			strAllSql.append(" 		WHERE M1042.flg_eigyo = 1 ");
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoKari);
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoPass);
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoCash);
			// ADD 2013/9/25 okano【QP@30151】No.28 end
			strAllSql.append(strWhere);
			strAllSql.append(" 	  ) AS datatbl ");
			strAllSql.append(" 	) AS tbl ");
			strAllSql.append(" ,( ");
			strAllSql.append(" 	SELECT ");
			strAllSql.append(" 	 COUNT(*) as max_row ");
			strAllSql.append(" 	FROM ( ");
			strAllSql.append(" 		SELECT ");
			strAllSql.append(" 		 Convert(int ");
			strAllSql.append(" 			,(ROW_NUMBER() OVER ( ");
			strAllSql.append(" 					ORDER BY M101.id_user)-1)/" + strListRowMax + "  + 1) AS PageNO ");
			strAllSql.append(" 		,RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append(" 				,M101.id_user) ");
			strAllSql.append(" 			,10) as id_user ");
			strAllSql.append(" 		,M101.nm_user ");
			strAllSql.append(" 		,M101.cd_kengen ");
			strAllSql.append(" 		,M102.nm_kengen ");
			strAllSql.append(" 		,M101.cd_kaisha ");
			strAllSql.append(" 		,M1041.nm_kaisha ");
			strAllSql.append(" 		,M101.cd_busho ");
			strAllSql.append(" 		,M1042.nm_busho ");
			strAllSql.append(" 		,M101.id_josi ");
			strAllSql.append(" 		,M101_2.nm_user AS nm_josi ");
			strAllSql.append(" 		FROM ma_user M101 ");
			strAllSql.append(" 		LEFT JOIN ( ");
			strAllSql.append(" 			SELECT ");
			strAllSql.append(" 			 cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha ");
			strAllSql.append(" 			FROM ma_busho ");
			strAllSql.append(" 			GROUP BY cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha) M1041 ");
			strAllSql.append(" 		ON M1041.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		LEFT JOIN ma_busho M1042 ");
			strAllSql.append(" 		ON M1042.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		AND M1042.cd_busho = M101.cd_busho ");
			strAllSql.append(" 		LEFT JOIN ma_kengen M102 ");
			strAllSql.append(" 		ON M101.cd_kengen = M102.cd_kengen ");
			strAllSql.append(" 		LEFT JOIN ma_user M101_2 ");
			strAllSql.append(" 		ON M101_2.id_user = M101.id_josi ");
			strAllSql.append(" 		WHERE M1042.flg_eigyo = 1 ");
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoKari);
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoPass);
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoCash);
			// ADD 2013/9/25 okano【QP@30151】No.28 end
			strAllSql.append(strWhere);
			strAllSql.append(" 	) AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY tbl.id_user ");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
			strWhere = null;
		}
		return strSql;
	}

	/**
	 * 担当者（営業）検索：担当者情報パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param MapMember : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//	private void storageTantoushaData(List<?> lstRecData, RequestResponsTableBean resTable)
	private void storageTantoushaData(List<?> lstRecData, HashMap<String, ArrayList<String>> MapMember, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start

		ArrayList<String> lstMember = null;
		String strMember = "";

		try {
			//営業（本部権限）権限コード取得
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");

			for (int i = 0; i < lstRecData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstRecData.get(i);

				resTable.addFieldVale(i, "no_row", Integer.toString(i+1));
				resTable.addFieldVale(i, "id_user", toString(items[0]));
				resTable.addFieldVale(i, "nm_user", toString(items[1]));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[4]));
				resTable.addFieldVale(i, "nm_kaisha", toString(items[5]));
				resTable.addFieldVale(i, "cd_busho", toString(items[6]));
				resTable.addFieldVale(i, "nm_busho", toString(items[7]));
				if(strEigyoHonbu.equals(toString(items[2]))){
					resTable.addFieldVale(i, "kbn_honbu", "あり");
				}
				else{
					resTable.addFieldVale(i, "kbn_honbu", "なし");
				}
				// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start
//				resTable.addFieldVale(i, "id_josi", toString(items[10]));
//				resTable.addFieldVale(i, "nm_josi", toString(items[11]));

				lstMember = MapMember.get(toString(items[0]));
				strMember = "";
				if (lstMember != null) {
					for (Iterator<String> it = lstMember.iterator(); it.hasNext();) {
						// 共有メンバーをカンマで区切る
						if (strMember != "") {
							strMember += ", ";
						}
						strMember += it.next();
					}
				}
			    resTable.addFieldVale(i, "nm_member", strMember);
				// MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end

			    resTable.addFieldVale(i, "list_max_row", toString(items[8]));
				resTable.addFieldVale(i, "max_row", toString(items[9]));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）データ検索処理に失敗しました。");

		} finally {

		}

	}


	/**
	 * 担当者（営業）情報取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer getTantouMemberLstCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();

		try {

			//SQL文の作成
			strAllSql.append(" SELECT ");
			strAllSql.append("  RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("  	,MEMBER.id_user) ");
			strAllSql.append("  	,10) as id_user ");
			strAllSql.append(" ,id_member ");
			strAllSql.append(" ,nm_user ");
			strAllSql.append(" FROM ma_member as MEMBER ");
			strAllSql.append(" 		LEFT JOIN  ma_user as M101  ");
			strAllSql.append(" 		ON MEMBER.id_member = M101.id_user ");
			strAllSql.append(" ORDER BY MEMBER.id_user ");
			strAllSql.append(" ,no_sort ");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）データ検索処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * 担当者（営業）検索：担当者共有メンバーをユーザー毎にMAPに格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @return HashMap         ：ユーザ毎の共有メンバーリスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private HashMap<String, ArrayList<String>> setTantoushaAry(List<?> lstRecData) {

		// 比較用ユーザコード
		String svUser = "";

		// 戻り値の宣言
		HashMap<String, ArrayList<String>> mapMember = new HashMap<String, ArrayList<String>>();
		// ユーザの共有メンバーリスト
		ArrayList<String>  strAry = new ArrayList<String>();

		for (int i = 0; i < lstRecData.size(); i++) {
			Object[] items = (Object[]) lstRecData.get(i);

			if (svUser.equals(toString(items[0]))) {
				// 共有メンバーの加算
				strAry.add(toString(items[2]));
			} else {
				// ユーザが変わった時MAPに保存する
				if (!svUser.equals("")) {
					mapMember.put(svUser, strAry);
				}
				// 共有メンバーリストの置き換え
				svUser = toString(items[0]);
				strAry = new ArrayList<String>();
				strAry.add(toString(items[2]));
			}
		}

		// 最後の共有メンバーリストを保存
		if (strAry.size() > 0) {
			mapMember.put(svUser, strAry);
		}

		// 格納したMAPを返す
		return mapMember;

	}


}
