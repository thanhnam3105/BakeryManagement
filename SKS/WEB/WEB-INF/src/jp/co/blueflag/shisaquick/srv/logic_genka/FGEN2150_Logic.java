package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;


/**
 *
 * 原価試算データ検索DB処理
 *  機能ID：FGEN2150　
 *
 * @author TT.Nishigawa
 * @since  2011/01/31
 */
public class FGEN2150_Logic extends LogicBase {

	/**
	 * 試作データ検索コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN2150_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原価試算データ取得管理
	 *  : 原価試算データ取得処理を管理する。
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
		List<?> lstRecset = null;
		List<?> lstRecset2 = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();

		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//テーブルBean取得
			reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//行Bean取得
			reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);

			//SQL作成（Where句取得）
			strSqlWhere = createWhereSQL(reqData,strSqlWhere);

			//SQL作成（原価試算データ取得）
			strSql = createGenkaShisanSQL(reqData,strSql,strSqlWhere);

			//DBインスタンス生成
			createSearchDB();

			//検索実行（原価試算データ取得）
			lstRecset = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//SQL作成（試算依頼ｻﾝﾌﾟﾙNoデータ取得）
			strSql2 = createGenkaShisanSampleSQL(lstRecset);

			//検索実行（原価試算データ取得）
			lstRecset2 = searchDB.dbSearch(strSql2.toString());

			//機能IDの設定
			resKind.setID(reqData.getID());

			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			//レスポンスデータの形成
			storageShisakuData(lstRecset, lstRecset2, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算データ検索DB処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			removeList(lstRecset2);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSqlWhere = null;
			strSql = null;
			strSql2 = null;
			reqTableBean = null;
			reqRecBean = null;
		}
		return resKind;
	}

	/**
	 *Where句取得SQL作成
	 * @param reqData : リクエストデータ
	 * @param strSql : 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(
			RequestResponsKindBean reqData
			,StringBuffer strSql
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strWhere = new StringBuffer();

		try {
			//検索条件取得---------------------------------------------------------------------
			//検索内容①設定
			String kbn_joken1 = toString(reqData.getFieldVale(0, 0, "kbn_joken1"));
			//検索内容②設定
			String kbn_joken2 = toString(reqData.getFieldVale(0, 0, "kbn_joken2"));
			//検索内容③設定
			String kbn_joken3 = toString(reqData.getFieldVale(0, 0, "kbn_joken3"));
			//試作№設定
			String no_shisaku = toString(reqData.getFieldVale(0, 0, "no_shisaku"));
			//試作名
			String nm_shisaku = toString(reqData.getFieldVale(0, 0, "nm_shisaku"));
			//試算期日From
			String hi_kizitu_from = toString(reqData.getFieldVale(0, 0, "hi_kizitu_from"));
			//試算期日To
			String hi_kizitu_to = toString(reqData.getFieldVale(0, 0, "hi_kizitu_to"));
			//所属グループコード
			String cd_group = toString(reqData.getFieldVale(0, 0, "cd_group"));
			//所属チームコード
			String cd_team = toString(reqData.getFieldVale(0, 0, "cd_team"));
			//【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
			//担当者
			String cd_tanto = toString(reqData.getFieldVale(0,0,"cd_tanto"));
			//【QP@20505】No.31 2012/09/14 TT H.Shima ADD End
			//ユーザーコード
			String cd_user = toString(reqData.getFieldVale(0, 0, "cd_user"));
			//会社コード
			String cd_kaisha = toString(reqData.getFieldVale(0, 0, "cd_kaisha"));
			//部署コード
			String cd_busho = toString(reqData.getFieldVale(0, 0, "cd_busho"));
			//状況部署
			String busho_zyokyou = toString(reqData.getFieldVale(0, 0, "busho_zyokyou"));
			//ステータス
			String status = toString(reqData.getFieldVale(0, 0, "status"));
			//枝番種類
			String eda_shurui = toString(reqData.getFieldVale(0, 0, "eda_shurui"));

			//WHERE句SQL作成----------------------------------------------------------------
			strWhere.append(" WHERE 1 = 1 ");

			//試作Noが入力されている場合
			if (!no_shisaku.equals("")){
				//試作コードを分解
				String[] strShisaku = no_shisaku.split("-");

				if (strShisaku.length >= 1){

					//社員CDのみの場合
					if (strShisaku.length == 1){
						strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//社員CDと年のみの場合
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
					}
					//社員CDと年と追番の場合
					if (strShisaku.length == 3){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							strWhere.append(" AND RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					//社員CDと年と追番と枝番の場合
					if (strShisaku.length == 4){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" AND RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							strWhere.append(" AND RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
						if (!(strShisaku[3].equals(""))){
							strWhere.append(" AND RIGHT('000' + CAST(T1.no_eda AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[3] + "%' ");
						}
					}
				}
			}

			//試作名が入力されている場合
			if (!nm_shisaku.equals("")){
				// MOD H.SHIMA 【H24年度対応】No.16 Start
				String searchStr = nm_shisaku.replace("　"," ");
				String[] multiple_search = searchStr.split(" ");
				for(int i = 0; i < multiple_search.length; i++){
// MOD start 【H24年度対応】 20120416 hisahori
//					strWhere.append(" AND T3.nm_hin LIKE '%");
					strWhere.append(" AND ISNULL(T3.nm_hin,'') + ISNULL(T1.nm_edaShisaku,'') LIKE '%");
// MOD end 【H24年度対応】 20120416 hisahori
					strWhere.append(multiple_search[i] + "%' ");
				}
//				strWhere.append(" AND T3.nm_hin LIKE '%");
//				strWhere.append(nm_shisaku + "%' ");
				// MOD H.SHIMA 【H24年度対応】No.16 End
			}

			//試算期日Fromが入力されている場合
			if (!hi_kizitu_from.equals("")){
				strWhere.append(" AND T1.dt_kizitu >= '");
				strWhere.append(hi_kizitu_from + "' ");
			}

			//試算期日Toが入力されている場合
			if (!hi_kizitu_to.equals("")){
				strWhere.append(" AND T1.dt_kizitu <= '");
				strWhere.append(hi_kizitu_to + "' ");
			}

			//グループが入力されている場合
			if (!cd_group.equals("")){
				strWhere.append(" AND T3.cd_group = ");
				strWhere.append(cd_group);
			}

			//チームが入力されている場合
			if (!cd_team.equals("")){
				strWhere.append(" AND T3.cd_team = ");
				strWhere.append(cd_team);
			}

			//【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start
			//担当者が入力されている場合
			if(!cd_tanto.equals("")){
				strWhere.append(" AND T3.id_toroku = ");
				strWhere.append(cd_tanto);
			}
			//【QP@20505】No.31 2012/09/14 TT H.Shima ADD Start

			//ユーザが入力されている場合
			if (!cd_user.equals("")){
				strWhere.append(" AND T3.cd_user = '");
				strWhere.append(cd_user +"'");
			}

			//製造会社が入力されている場合
			if (!cd_kaisha.equals("")){
				strWhere.append(" AND T1.cd_kaisha = ");
				strWhere.append(cd_kaisha);
			}

			//製造工場が入力されている場合
			if (!cd_busho.equals("")){
				strWhere.append(" AND T1.cd_kojo = ");
				strWhere.append(cd_busho);
			}

			//状況部署、ステータスが入力されている場合
			if (!busho_zyokyou.equals("") && !status.equals("")){
				//研究所
				if(busho_zyokyou.equals("1")){
					strWhere.append(" AND M2.st_kenkyu = ");
					strWhere.append(status);
				}
				//生産管理部
				else if(busho_zyokyou.equals("2")){
					strWhere.append(" AND M2.st_seisan = ");
					strWhere.append(status);
				}
				//原資材調達部
				else if(busho_zyokyou.equals("3")){
					strWhere.append(" AND M2.st_gensizai = ");
					strWhere.append(status);
				}
				//工場
				else if(busho_zyokyou.equals("4")){
					strWhere.append(" AND M2.st_kojo = ");
					strWhere.append(status);
				}
				//営業
				else if(busho_zyokyou.equals("5")){
					strWhere.append(" AND M2.st_eigyo = ");
					strWhere.append(status);
				}
			}

			//枝番種類が入力されている場合
			if (!eda_shurui.equals("")){
				strWhere.append(" AND T1.shurui_eda = '");
				strWhere.append(eda_shurui + "'");
			}

			//TODOタブが選択されている場合
			if (kbn_joken1.equals("0")){
				strWhere.append(" AND M2.st_eigyo < 4 ");
			}

			//未入力チェックボックスが選択されている場合
			if (kbn_joken2.equals("1")){
				strWhere.append(" AND T1.dt_kizitu IS NULL ");
			}

			//確認完了チェックボックスが選択されている場合
			if(kbn_joken3.equals("1")){
				//状況部署、ステータスが入力されている場合
				if (!busho_zyokyou.equals("") && !status.equals("")){
					strWhere.append(" OR M2.st_eigyo = 3 ");
				}
				else{
					strWhere.append(" AND M2.st_eigyo = 3 ");
				}
			}

			//権限での検索条件-------------------------------------------------------------------

			//原価試算一覧画面のデータID取得
			String strKengen = "";
			for (int i=0;i < userInfoData.getId_gamen().size();i++){
				if (userInfoData.getId_gamen().get(i).toString().equals("180")){
					//データIDを設定
					strKengen = userInfoData.getId_data().get(i).toString();
				}
			}

			// MOD 2013/11/5 QP@30154 okano start
//				//同一グループ
//				if(strKengen.equals("1")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" ( ");
//					strWhere.append(" T3.cd_group = ");
//					strWhere.append(userInfoData.getCd_group());
//
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" T3.cd_team = ");
//	//				strWhere.append(userInfoData.getCd_team());
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" T3.id_toroku = ");
//	//				strWhere.append(userInfoData.getId_user());
//	//				strWhere.append(" AND ");
//	//				strWhere.append(" M6.cd_yakushoku = '001'");
//
//					strWhere.append(" ) ");
//
//	//
//	//				//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
//	//				int TEAM_LEADER_CD = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"));
//	//				int user_yakushoku = Integer.parseInt(userInfoData.getCd_literal());
//	//				if( TEAM_LEADER_CD <= user_yakushoku ){
//	//					strWhere.append(" OR ");
//	//					strWhere.append(" ( ");
//	//					strWhere.append(" T3.cd_group = ");
//	//					strWhere.append(userInfoData.getCd_group());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" T3.cd_team = ");
//	//					strWhere.append(userInfoData.getCd_team());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" M6.cd_yakushoku <= '");
//	//					strWhere.append(userInfoData.getCd_literal());
//	//					strWhere.append("' ) ");
//	//				}
//	//
//	//				//セッション情報．役職コード≧グループリーダーの場合、以下をOR条件で追加
//	//				int TEAM_GROUP_CD = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"));
//	//				if( TEAM_GROUP_CD <= user_yakushoku ){
//	//					strWhere.append(" OR ");
//	//					strWhere.append(" ( ");
//	//					strWhere.append(" T3.cd_group = ");
//	//					strWhere.append(userInfoData.getCd_group());
//	//					strWhere.append(" AND ");
//	//					strWhere.append(" M6.cd_yakushoku <= '");
//	//					strWhere.append(userInfoData.getCd_literal());
//	//					strWhere.append("' ) ");
//	//				}
//
//					strWhere.append(" ) ");
//
//				}
//				//自工場分
//				else if(strKengen.equals("2")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" T1.cd_kaisha = ");
//					strWhere.append(userInfoData.getCd_kaisha());
//					strWhere.append(" AND ");
//					strWhere.append(" T1.cd_kojo = ");
//					strWhere.append(userInfoData.getCd_busho());
//					strWhere.append(" ) ");
//
//				}
//				//営業担当のみ
//				else if(strKengen.equals("4")){
//					strWhere.append(" AND ( ");
//					strWhere.append(" 	T3.cd_eigyo = " + userInfoData.getId_user());
//					strWhere.append(" 	OR T3.cd_eigyo IN( ");
//					strWhere.append(" 		SELECT DISTINCT id_user ");
//					strWhere.append(" 		FROM ma_user ");
//					strWhere.append(" 		WHERE id_user IN ( ");
//					strWhere.append(" 			SELECT id_user ");
//					strWhere.append(" 			FROM ma_user ");
//					strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//					strWhere.append(" 		) ");
//					strWhere.append(" 		OR id_josi IN ( ");
//					strWhere.append(" 			SElECT id_user ");
//					strWhere.append(" 			FROM ma_user ");
//					strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//					strWhere.append(" 		) ");
//					strWhere.append(" 	) ");
//					strWhere.append(" ) ");
//
//				}
//				//全て
//				else if(strKengen.equals("5")){
//
//				}
			//研究所
			if(strKengen.equals("1")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T1.cd_kaisha = " + userInfoData.getCd_kaisha());
				strWhere.append(" 	OR T1.cd_hanseki = " + userInfoData.getCd_kaisha());
				strWhere.append(" ) ");

			}
			//製造工場
			else if(strKengen.equals("2")){
				strWhere.append(" AND ( ");
				strWhere.append(" T1.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kojo = ");
				strWhere.append(userInfoData.getCd_busho());
				strWhere.append(" ) ");

			}
			//生産管理部
			else if(strKengen.equals("3")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T1.cd_kaisha = " + userInfoData.getCd_kaisha());
				strWhere.append(" 	OR T1.cd_hanseki = " + userInfoData.getCd_kaisha());
				strWhere.append(" ) ");

			}
			//原料調達部
			else if(strKengen.equals("4")){
				strWhere.append(" AND T1.cd_kaisha = " + userInfoData.getCd_kaisha());

			}
			//営業（一般）
			else if(strKengen.equals("5")){
				strWhere.append(" AND ( ");
				strWhere.append(" 	T3.cd_eigyo = " + userInfoData.getId_user());
				strWhere.append(" 	OR T3.cd_eigyo IN( ");
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
//				strWhere.append(" 		SELECT DISTINCT id_user ");
//				strWhere.append(" 		FROM ma_user ");
//				strWhere.append(" 		WHERE id_user IN ( ");
//				strWhere.append(" 			SELECT id_user ");
//				strWhere.append(" 			FROM ma_user ");
//				strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//				strWhere.append(" 		) ");
//				strWhere.append(" 		OR id_josi IN ( ");
//				strWhere.append(" 			SElECT id_user ");
//				strWhere.append(" 			FROM ma_user ");
//				strWhere.append(" 			WHERE id_josi = " + userInfoData.getId_user());
//				strWhere.append(" 		) ");

				// ログインユーザの共有メンバーが閲覧可
				strWhere.append(" 		SELECT id_member ");
				strWhere.append(" 		FROM ma_member  ");
				strWhere.append(" 		WHERE id_user = " + userInfoData.getId_user());
				// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end
				strWhere.append(" 	) ");
				strWhere.append(" ) ");
				strWhere.append(" AND T1.cd_hanseki = " + userInfoData.getCd_kaisha());

			}
			//営業（本部権限）
			else if(strKengen.equals("6")){
				strWhere.append(" AND T1.cd_hanseki = " + userInfoData.getCd_kaisha());

			}
			//全て
			else if(strKengen.equals("7")){

			}
			// MOD 2013/11/5 QP@30154 okano end


		} catch (Exception e) {

			em.ThrowException(e, "原価試算データ検索処理に失敗しました。");

		} finally {

		}

		return strWhere;
	}

	/**
	 * 原価試算データ取得SQL作成
	 *  : 原価試算データを取得するSQLを作成。
	 * @param strSql : 検索条件SQL
	 * @param arrCondition：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaShisanSQL(
			RequestResponsKindBean reqData
			,StringBuffer strSql
			,StringBuffer strWhere
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();

		try {
			//ページ番号
			String no_page = toString(reqData.getFieldVale(0, 0, "no_page"));

			//最大行数の取得
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			// ADD 2013/8/7 okano【QP@30151】No.12 start
			String kbn_joken1 = toString(reqData.getFieldVale(0, 0, "kbn_joken1"));
			// ADD 2013/8/7 okano【QP@30151】No.12 end

			//SQL文の作成
			strAllSql.append("	SELECT ");
			strAllSql.append("		 tbl.no_row ");
			strAllSql.append("		,tbl.dt_kizitu ");
			strAllSql.append("		,tbl.su_irai ");
			strAllSql.append("		,tbl.no_shisaku ");
			strAllSql.append("		,tbl.no_eda ");
			strAllSql.append("		,tbl.nm_hin ");
			strAllSql.append("		,tbl.nm_shurui ");
			strAllSql.append("		,tbl.st_kenkyu ");
			strAllSql.append("		,tbl.st_seisan ");
			strAllSql.append("		,tbl.st_gensizai ");
			strAllSql.append("		,tbl.st_kojo ");
			strAllSql.append("		,tbl.st_eigyo ");
			strAllSql.append("		,saiyo_sample ");
			strAllSql.append("		,nm_sample ");
			strAllSql.append("		,nm_team ");
			strAllSql.append("		,nm_liuser ");
			strAllSql.append("		,nm_kaisha ");
			strAllSql.append("		,nm_busho ");
			strAllSql.append("		,memo_eigyo ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			strAllSql.append("		,cd_nisugata ");
			strAllSql.append("		,nm_ondo ");
			strAllSql.append("		,mn_tantoeigyo ");
			strAllSql.append("		,mn_tantosya ");
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append("		," + strListRowMax + " AS list_max_row ");
			strAllSql.append("		,cnttbl.max_row ");
			strAllSql.append("	 ");
			strAllSql.append("	FROM ");
			strAllSql.append("	( ");
			strAllSql.append("		SELECT  ");
			strAllSql.append("			(CASE ");
			strAllSql.append("				WHEN ROW_NUMBER() OVER ( ");
			strAllSql.append("				ORDER BY ");
			// MOD 2013/8/7 okano【QP@30151】No.12 start
//				strAllSql.append("					TBL2.dt_kizitu ");
//				strAllSql.append("					,TBL2.no_shisaku ");
//				strAllSql.append("					,TBL2.no_eda ");
//				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
//				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
//				strAllSql.append("				ORDER BY  ");
//				strAllSql.append("					TBL2.dt_kizitu ");
//				strAllSql.append("					,TBL2.no_shisaku ");
//				strAllSql.append("					,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("					TBL2.dt_kizitu ");
				strAllSql.append("					,TBL2.no_shisaku ");
				strAllSql.append("					,TBL2.no_eda ");
				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("				ORDER BY ");
				strAllSql.append("					TBL2.dt_kizitu ");
				strAllSql.append("					,TBL2.no_shisaku ");
				strAllSql.append("					,TBL2.no_eda ");
			} else {
				strAllSql.append("					case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("						when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("						when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("						when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("						else 5 end ");
				strAllSql.append("					,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("						else 1 end ");
				strAllSql.append("					,TBL2.dt_shisan desc ");
				strAllSql.append("					,TBL2.no_shisaku asc ");
				strAllSql.append("					,TBL2.no_eda asc ");
				strAllSql.append("					)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("			ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("				ORDER BY ");
				strAllSql.append("					case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("						when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("						when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("						when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("						else 5 end ");
				strAllSql.append("					,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("						else 1 end ");
				strAllSql.append("					,TBL2.dt_shisan desc ");
				strAllSql.append("					,TBL2.no_shisaku asc ");
				strAllSql.append("					,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano【QP@30151】No.12 end
			strAllSql.append("					)%" + strListRowMax + " ");
			strAllSql.append("			END) AS no_row ");
			strAllSql.append("			,Convert(int ");
			strAllSql.append("				,(ROW_NUMBER() OVER ( ");
			strAllSql.append("					ORDER BY ");
			// MOD 2013/8/7 okano【QP@30151】No.12 start
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");

			} else {
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano【QP@30151】No.12 end
			strAllSql.append("					)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append("			,TBL2.dt_kizitu ");
			strAllSql.append("			,TBL2.su_irai ");
			strAllSql.append("			,TBL2.no_shisaku ");
			strAllSql.append("			,TBL2.no_eda ");
			strAllSql.append("			,TBL2.nm_shurui ");
			strAllSql.append("			,TBL2.nm_hin ");
			strAllSql.append("			,TBL2.st_kenkyu ");
			strAllSql.append("			,TBL2.st_seisan ");
			strAllSql.append("			,TBL2.st_gensizai ");
			strAllSql.append("			,TBL2.st_kojo ");
			strAllSql.append("			,TBL2.st_eigyo ");
			strAllSql.append("			,TBL2.saiyo_sample ");
			strAllSql.append("			,TBL2.nm_sample ");
			strAllSql.append("			,TBL2.nm_team ");
			strAllSql.append("			,TBL2.nm_liuser ");
			strAllSql.append("			,TBL2.nm_kaisha ");
			strAllSql.append("			,TBL2.nm_busho ");
			strAllSql.append("			,TBL2.memo_eigyo ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			strAllSql.append("			,TBL2.cd_nisugata ");
			strAllSql.append("			,TBL2.nm_ondo ");
			strAllSql.append("			,TBL2.mn_tantoeigyo ");
			strAllSql.append("			,TBL2.mn_tantosya ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("			,TBL2.dt_shisan ");
			strAllSql.append("			,TBL2.fg_chusi ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append("		FROM ");
			strAllSql.append("		( ");
			strAllSql.append("			SELECT ");
			strAllSql.append("				CASE M2.st_eigyo ");
			strAllSql.append("					WHEN 3 THEN '確認完了' ");
			strAllSql.append("					WHEN 4 THEN '採用決定' ");
			strAllSql.append("					ELSE ISNULL(CONVERT(VARCHAR,T1.dt_kizitu,111),'未定') ");
			strAllSql.append("				END AS dt_kizitu ");
			strAllSql.append("				, T1.su_irai ");
			strAllSql.append("				, RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("						,T1.cd_shain) ");
			strAllSql.append("					,10) + '-' + RIGHT('00' + CONVERT(varchar ");
			strAllSql.append("						,T1.nen) ");
			strAllSql.append("					,2) + '-' + RIGHT('000' + CONVERT(varchar ");
			strAllSql.append("						,T1.no_oi) ");
			strAllSql.append("					,3) as no_shisaku ");
			strAllSql.append("				, T1.no_eda ");
			strAllSql.append("				, M1.nm_ｌiteral AS nm_shurui ");
// MOD start 【H24年度対応】 20120416 hisahori
//			strAllSql.append("				, T3.nm_hin ");
			//TODO strAllSql.append("				, CASE WHEN T1.nm_edaShisaku IS NULL THEN T3.nm_hin ELSE T3.nm_hin + ' 【' + T1.nm_edaShisaku + '】' END AS nm_hin");
			strAllSql.append(" , CASE WHEN T1.nm_edaShisaku IS NOT NULL ");
			strAllSql.append("  THEN ");
			strAllSql.append("	CASE RTRIM(T1.nm_edaShisaku) WHEN '' ");
			strAllSql.append("	THEN T3.nm_hin ");
			strAllSql.append("	ELSE T3.nm_hin + ' 【' + T1.nm_edaShisaku + '】' END ");
			strAllSql.append(" ELSE T3.nm_hin END AS nm_hin ");
// MOD end 【H24年度対応】 20120416 hisahori
			strAllSql.append("				, M2.st_kenkyu ");
			strAllSql.append("				, M2.st_seisan ");
			strAllSql.append("				, M2.st_gensizai ");
			strAllSql.append("				, M2.st_kojo ");
			strAllSql.append("				, M2.st_eigyo ");
			strAllSql.append("				, T1.saiyo_sample ");
			strAllSql.append("				, CASE T1.saiyo_sample ");
			strAllSql.append("				WHEN -1 THEN '採用なし' ");
			strAllSql.append("				ELSE T2.nm_sample ");
			strAllSql.append("				END AS nm_sample ");
			strAllSql.append("				, M3.nm_team ");
			strAllSql.append("				, M4.nm_ｌiteral AS nm_liuser ");
			strAllSql.append("				, M5.nm_kaisha ");
			strAllSql.append("				, M5.nm_busho ");
			strAllSql.append("				, T4.memo_eigyo ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			//2011/12/21 TT H.SHIMA MOD Start	荷姿取得テーブルを試作から原価試算へ
//			strAllSql.append("				, T3.cd_nisugata ");
			strAllSql.append("				, T1.cd_nisugata ");
			//2011/12/21 TT H.SHIMA MOD End
			strAllSql.append("				, M111.nm_literal AS nm_ondo ");
			strAllSql.append("				, M113.nm_user AS mn_tantoeigyo ");
			strAllSql.append("				, M114.nm_user AS mn_tantosya ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("				, T5.dt_shisan ");
			strAllSql.append("				, T5.fg_chusi ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append("				 ");
			strAllSql.append("			FROM tr_shisan_shisakuhin AS T1 ");
			strAllSql.append("				LEFT JOIN ma_literal AS M1 ");
			strAllSql.append("				ON 'shurui_eda' = M1.cd_category ");
			strAllSql.append("				AND T1.shurui_eda = M1.cd_ｌiteral ");
			strAllSql.append("				LEFT JOIN tr_shisan_status AS M2 ");
			strAllSql.append("				ON T1.cd_shain = M2.cd_shain ");
			strAllSql.append("				AND T1.nen = M2.nen ");
			strAllSql.append("				AND T1.no_oi = M2.no_oi ");
			strAllSql.append("				AND T1.no_eda = M2.no_eda ");
			strAllSql.append("				LEFT JOIN tr_shisaku AS T2 ");
			strAllSql.append("				ON T1.cd_shain = T2.cd_shain ");
			strAllSql.append("				AND T1.nen = T2.nen ");
			strAllSql.append("				AND T1.no_oi = T2.no_oi ");
			strAllSql.append("				AND T1.saiyo_sample = T2.seq_shisaku ");
			strAllSql.append("				LEFT JOIN tr_shisakuhin AS T3 ");
			strAllSql.append("				ON T1.cd_shain = T3.cd_shain ");
			strAllSql.append("				AND T1.nen = T3.nen ");
			strAllSql.append("				AND T1.no_oi = T3.no_oi ");
			strAllSql.append("				LEFT JOIN ma_team AS M3 ");
			strAllSql.append("				ON T3.cd_group = M3.cd_group ");
			strAllSql.append("				AND T3.cd_team = M3.cd_team ");
			strAllSql.append("				LEFT JOIN ma_literal AS M4 ");
			strAllSql.append("				ON 'K_yuza' = M4.cd_category ");
			strAllSql.append("				AND T3.cd_user = M4.cd_ｌiteral ");
			strAllSql.append("				LEFT JOIN ma_busho AS M5 ");
			strAllSql.append("				ON T1.cd_kaisha = M5.cd_kaisha ");
			strAllSql.append("				AND T1.cd_kojo = M5.cd_busho ");
			strAllSql.append("				LEFT JOIN tr_shisan_memo_eigyo AS T4 ");
			strAllSql.append("				ON T1.cd_shain = T4.cd_shain ");
			strAllSql.append("				AND T1.nen = T4.nen ");
			strAllSql.append("				AND T1.no_oi = T4.no_oi ");
			strAllSql.append("				LEFT JOIN ma_user M6 ");
			strAllSql.append("				ON T3.id_toroku = M6.id_user ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			strAllSql.append("				LEFT JOIN ma_literal AS M111 ");
			strAllSql.append("				ON 'K_toriatukaiondo' = M111.cd_category AND T3.cd_ondo = M111.cd_literal ");
			strAllSql.append("				LEFT JOIN ma_user M113 ");
			strAllSql.append("				ON T3.cd_eigyo = M113.id_user ");
			strAllSql.append("				LEFT JOIN ma_user M114 ");
			strAllSql.append("				ON T1.cd_shain = M114.id_user ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("				left join tr_shisan_shisaku AS T5 ");
			strAllSql.append("				on T1.cd_shain = T5.cd_shain ");
			strAllSql.append("				and T1.nen = T5.nen ");
			strAllSql.append("				and T1.no_oi = T5.no_oi ");
			strAllSql.append("				and T1.no_eda = T5.no_eda ");
			strAllSql.append("				and T5.seq_shisaku = ( ");
			strAllSql.append("				select top 1 seq_shisaku from tr_shisan_shisaku ");
			strAllSql.append("				where cd_shain = T1.cd_shain ");
			strAllSql.append("				and nen = T1.nen ");
			strAllSql.append("				and no_oi = T1.no_oi ");
			strAllSql.append("				and no_eda = T1.no_eda ");
			strAllSql.append("				order by ");
			strAllSql.append("				case when fg_chusi IS NULL then 0 ");
			strAllSql.append("					else 1 end ");
			strAllSql.append("				,dt_shisan desc ");
			strAllSql.append("				,seq_shisaku ");
			strAllSql.append("				) ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append(strWhere);
			strAllSql.append("		) AS TBL2 ");
			strAllSql.append("	) AS tbl ");
			strAllSql.append("	,( ");
			strAllSql.append("		SELECT ");
			strAllSql.append("			COUNT(*) as max_row ");
			strAllSql.append("		FROM  ");
			strAllSql.append("		( ");
			strAllSql.append("			SELECT  ");
			strAllSql.append("				(CASE ");
			strAllSql.append("					WHEN ROW_NUMBER() OVER ( ");
			strAllSql.append("					ORDER BY ");
			// MOD 2013/8/7 okano【QP@30151】No.12 start
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
//				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
//				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
//				strAllSql.append("					ORDER BY  ");
//				strAllSql.append("						TBL2.dt_kizitu ");
//				strAllSql.append("						,TBL2.no_shisaku ");
//				strAllSql.append("						,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");
				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("					ORDER BY ");
				strAllSql.append("						TBL2.dt_kizitu ");
				strAllSql.append("						,TBL2.no_shisaku ");
				strAllSql.append("						,TBL2.no_eda ");
			} else {
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
				strAllSql.append("						)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ");
				strAllSql.append("				ELSE ROW_NUMBER() OVER ( ");
				strAllSql.append("					ORDER BY ");
				strAllSql.append("						case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("							when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("							when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("							when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("							else 5 end ");
				strAllSql.append("						,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("							else 1 end ");
				strAllSql.append("						,TBL2.dt_shisan desc ");
				strAllSql.append("						,TBL2.no_shisaku asc ");
				strAllSql.append("						,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano【QP@30151】No.12 end
			strAllSql.append("						)%" + strListRowMax + " ");
			strAllSql.append("				END) AS no_row ");
			strAllSql.append("				,Convert(int ");
			strAllSql.append("					,(ROW_NUMBER() OVER ( ");
			strAllSql.append("						ORDER BY ");
			// MOD 2013/8/7 okano【QP@30151】No.12 start
//				strAllSql.append("							TBL2.dt_kizitu ");
//				strAllSql.append("							,TBL2.no_shisaku ");
//				strAllSql.append("							,TBL2.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("							TBL2.dt_kizitu ");
				strAllSql.append("							,TBL2.no_shisaku ");
				strAllSql.append("							,TBL2.no_eda ");
			} else {
				strAllSql.append("							case when TBL2.st_eigyo = 4 and TBL2.saiyo_sample < 0 then 4 ");
				strAllSql.append("								when TBL2.st_eigyo = 4 then 1 ");
				strAllSql.append("								when TBL2.st_eigyo = 3 then 2 ");
				strAllSql.append("								when TBL2.st_eigyo = 2 then 3 ");
				strAllSql.append("								else 5 end ");
				strAllSql.append("							,case when TBL2.fg_chusi IS NULL then 0 ");
				strAllSql.append("								else 1 end ");
				strAllSql.append("							,TBL2.dt_shisan desc ");
				strAllSql.append("							,TBL2.no_shisaku asc ");
				strAllSql.append("							,TBL2.no_eda asc ");
			}
			// MOD 2013/8/7 okano【QP@30151】No.12 end
			strAllSql.append("						)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append("				,TBL2.dt_kizitu ");
			strAllSql.append("				,TBL2.su_irai ");
			strAllSql.append("				,TBL2.no_shisaku ");
			strAllSql.append("				,TBL2.no_eda ");
			strAllSql.append("				,TBL2.nm_shurui ");
			strAllSql.append("				,TBL2.nm_hin ");
			strAllSql.append("				,TBL2.st_kenkyu ");
			strAllSql.append("				,TBL2.st_seisan ");
			strAllSql.append("				,TBL2.st_gensizai ");
			strAllSql.append("				,TBL2.st_kojo ");
			strAllSql.append("				,TBL2.st_eigyo ");
			strAllSql.append("				,TBL2.saiyo_sample ");
			strAllSql.append("				,TBL2.nm_sample ");
			strAllSql.append("				,TBL2.nm_team ");
			strAllSql.append("				,TBL2.nm_liuser ");
			strAllSql.append("				,TBL2.nm_kaisha ");
			strAllSql.append("				,TBL2.nm_busho ");
			strAllSql.append("				,TBL2.memo_eigyo ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			strAllSql.append("				,TBL2.cd_nisugata ");
			strAllSql.append("				,TBL2.nm_ondo ");
			strAllSql.append("				,TBL2.mn_tantoeigyo ");
			strAllSql.append("				,TBL2.mn_tantosya ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("				,TBL2.dt_shisan ");
			strAllSql.append("				,TBL2.fg_chusi ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append("			FROM ");
			strAllSql.append("			( ");
			strAllSql.append("				SELECT ");
			strAllSql.append("					CASE M2.st_eigyo ");
			strAllSql.append("						WHEN 3 THEN '確認完了' ");
			strAllSql.append("						WHEN 4 THEN '採用決定' ");
			strAllSql.append("						ELSE ISNULL(CONVERT(VARCHAR,T1.dt_kizitu,111),'未定') ");
			strAllSql.append("					END AS dt_kizitu ");
			strAllSql.append("					, T1.su_irai ");
			strAllSql.append("					, RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("							,T1.cd_shain) ");
			strAllSql.append("						,10) + '-' + RIGHT('00' + CONVERT(varchar ");
			strAllSql.append("							,T1.nen) ");
			strAllSql.append("						,2) + '-' + RIGHT('000' + CONVERT(varchar ");
			strAllSql.append("							,T1.no_oi) ");
			strAllSql.append("						,3) as no_shisaku ");
			strAllSql.append("					, T1.no_eda ");
			strAllSql.append("					, M1.nm_ｌiteral AS nm_shurui ");
			strAllSql.append("					, T3.nm_hin ");
			strAllSql.append("					, M2.st_kenkyu ");
			strAllSql.append("					, M2.st_seisan ");
			strAllSql.append("					, M2.st_gensizai ");
			strAllSql.append("					, M2.st_kojo ");
			strAllSql.append("					, M2.st_eigyo ");
			strAllSql.append("					, T1.saiyo_sample ");
			strAllSql.append("					, CASE T1.saiyo_sample ");
			strAllSql.append("					WHEN -1 THEN '採用なし' ");
			strAllSql.append("					ELSE T2.nm_sample ");
			strAllSql.append("					END AS nm_sample ");
			strAllSql.append("					, M3.nm_team ");
			strAllSql.append("					, M4.nm_ｌiteral AS nm_liuser ");
			strAllSql.append("					, M5.nm_kaisha ");
			strAllSql.append("					, M5.nm_busho ");
			strAllSql.append("					, T4.memo_eigyo ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			//2011/12/21 TT H.SHIMA MOD Start	荷姿取得テーブルを試作から原価試算へ
//			strAllSql.append("					, T3.cd_nisugata ");
			strAllSql.append("			    	, T1.cd_nisugata ");
			//2011/12/21 TT H.SHIMA MOD End
			strAllSql.append("					, M111.nm_literal AS nm_ondo ");
			strAllSql.append("					, M113.nm_user AS mn_tantoeigyo ");
			strAllSql.append("					, M114.nm_user AS mn_tantosya ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("					, T5.dt_shisan ");
			strAllSql.append("					, T5.fg_chusi ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append("					 ");
			strAllSql.append("				FROM tr_shisan_shisakuhin AS T1 ");
			strAllSql.append("					LEFT JOIN ma_literal AS M1 ");
			strAllSql.append("					ON 'shurui_eda' = M1.cd_category ");
			strAllSql.append("					AND T1.shurui_eda = M1.cd_ｌiteral ");
			strAllSql.append("					LEFT JOIN tr_shisan_status AS M2 ");
			strAllSql.append("					ON T1.cd_shain = M2.cd_shain ");
			strAllSql.append("					AND T1.nen = M2.nen ");
			strAllSql.append("					AND T1.no_oi = M2.no_oi ");
			strAllSql.append("					AND T1.no_eda = M2.no_eda ");
			strAllSql.append("					LEFT JOIN tr_shisaku AS T2 ");
			strAllSql.append("					ON T1.cd_shain = T2.cd_shain ");
			strAllSql.append("					AND T1.nen = T2.nen ");
			strAllSql.append("					AND T1.no_oi = T2.no_oi ");
			strAllSql.append("					AND T1.saiyo_sample = T2.seq_shisaku ");
			strAllSql.append("					LEFT JOIN tr_shisakuhin AS T3 ");
			strAllSql.append("					ON T1.cd_shain = T3.cd_shain ");
			strAllSql.append("					AND T1.nen = T3.nen ");
			strAllSql.append("					AND T1.no_oi = T3.no_oi ");
			strAllSql.append("					LEFT JOIN ma_team AS M3 ");
			strAllSql.append("					ON T3.cd_group = M3.cd_group ");
			strAllSql.append("					AND T3.cd_team = M3.cd_team ");
			strAllSql.append("					LEFT JOIN ma_literal AS M4 ");
			strAllSql.append("					ON 'K_yuza' = M4.cd_category ");
			strAllSql.append("					AND T3.cd_user = M4.cd_ｌiteral ");
			strAllSql.append("					LEFT JOIN ma_busho AS M5 ");
			strAllSql.append("					ON T1.cd_kaisha = M5.cd_kaisha ");
			strAllSql.append("					AND T1.cd_kojo = M5.cd_busho ");
			strAllSql.append("					LEFT JOIN tr_shisan_memo_eigyo AS T4 ");
			strAllSql.append("					ON T1.cd_shain = T4.cd_shain ");
			strAllSql.append("					AND T1.nen = T4.nen ");
			strAllSql.append("					AND T1.no_oi = T4.no_oi ");
			strAllSql.append("					LEFT JOIN ma_user M6 ");
			strAllSql.append("					ON T3.id_toroku = M6.id_user ");
			//  【QP@10713】2011.10.28 ADD start hisahori
			strAllSql.append("					LEFT JOIN ma_literal AS M111 ");
			strAllSql.append("					ON 'K_toriatukaiondo' = M111.cd_category AND T3.cd_ondo = M111.cd_literal ");
			strAllSql.append("					LEFT JOIN ma_user M113 ");
			strAllSql.append("					ON T3.cd_eigyo = M113.id_user ");
			strAllSql.append("					LEFT JOIN ma_user M114 ");
			strAllSql.append("					ON T1.cd_shain = M114.id_user ");
			// ADD 2013/8/7 okano【QP@30151】No.12 start
			strAllSql.append("					left join tr_shisan_shisaku AS T5 ");
			strAllSql.append("					on T1.cd_shain = T5.cd_shain ");
			strAllSql.append("					and T1.nen = T5.nen ");
			strAllSql.append("					and T1.no_oi = T5.no_oi ");
			strAllSql.append("					and T1.no_eda = T5.no_eda ");
			strAllSql.append("					and T5.seq_shisaku = ( ");
			strAllSql.append("					select top 1 seq_shisaku from tr_shisan_shisaku ");
			strAllSql.append("					where cd_shain = T1.cd_shain ");
			strAllSql.append("					and nen = T1.nen ");
			strAllSql.append("					and no_oi = T1.no_oi ");
			strAllSql.append("					and no_eda = T1.no_eda ");
			strAllSql.append("					order by ");
			strAllSql.append("					case when fg_chusi IS NULL then 0 ");
			strAllSql.append("						else 1 end ");
			strAllSql.append("					,dt_shisan desc ");
			strAllSql.append("					,seq_shisaku ");
			strAllSql.append("					) ");
			// ADD 2013/8/7 okano【QP@30151】No.12 end
			//  【QP@10713】2011.10.28 ADD end
			strAllSql.append(strWhere);
			strAllSql.append("			) AS TBL2 ");
			strAllSql.append("		) AS CT ");
			strAllSql.append("	) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + no_page);
			// MOD 2013/8/7 okano【QP@30151】No.12 start
//				strAllSql.append("	ORDER BY dt_kizitu ");
//				strAllSql.append("	,tbl.no_shisaku ");
//				strAllSql.append("	,tbl.no_eda ");
			if(kbn_joken1.equals("0")){
				strAllSql.append("	ORDER BY dt_kizitu ");
				strAllSql.append("	,tbl.no_shisaku ");
				strAllSql.append("	,tbl.no_eda ");
			}else{
				strAllSql.append("	ORDER BY ");
				strAllSql.append("	case when st_eigyo = 4 and saiyo_sample < 0 then 4 ");
				strAllSql.append("		when st_eigyo = 4 then 1 ");
				strAllSql.append("		when st_eigyo = 3 then 2 ");
				strAllSql.append("		when st_eigyo = 2 then 3 ");
				strAllSql.append("		else 5 end ");
				strAllSql.append("	,case when tbl.fg_chusi IS NULL then 0 ");
				strAllSql.append("		else 1 end ");
				strAllSql.append("	,tbl.dt_shisan desc ");
				strAllSql.append("	,tbl.no_shisaku asc ");
				strAllSql.append("	,tbl.no_eda asc ");
			}
			// MOD 2013/8/7 okano【QP@30151】No.12 end

			strSql = strAllSql;
		} catch (Exception e) {

			em.ThrowException(e, "原価試算データ検索処理に失敗しました。");

		} finally {
			//変数の削除
			strAllSql = null;
		}

		return strSql;
	}

	/**
	 * 試算依頼ｻﾝﾌﾟﾙNoデータ取得SQL作成
	 *  : 試算依頼ｻﾝﾌﾟﾙNoデータを取得するSQLを作成。
	 * @param strSql : 検索条件SQL
	 * @param arrCondition：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaShisanSampleSQL(
			List<?> lstGenkaShisan
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		StringBuffer strSqlWhere = new StringBuffer();

		try {
			//IN句　内部生成
			for (int i = 0; i < lstGenkaShisan.size(); i++) {
				//検索結果取得（試作No）
				Object[] items = (Object[]) lstGenkaShisan.get(i);
				String no_shisaku = toString(items[3]);

				//初回
				if( i == 0){
					strSqlWhere.append( " '" + no_shisaku + "'" );
				}
				//初回以降
				else{
					strSqlWhere.append( " ,'" + no_shisaku + "'" );
				}

			}

			//SQL生成
			strSql.append(" SELECT ");
			strSql.append(" 	TT.no_shisaku ");
			strSql.append(" 	,TT.no_eda ");
			strSql.append(" 	,TT.nm_sample ");
			strSql.append(" FROM  ");
			strSql.append(" 	( ");
			strSql.append(" 	SELECT ");
			strSql.append(" 		RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) ");
			strSql.append(" 			+ '-' + RIGHT('00' + CONVERT(varchar,T1.nen),2) ");
			strSql.append(" 			+ '-' + RIGHT('000' + CONVERT(varchar,T1.no_oi),3) AS no_shisaku ");
			strSql.append(" 		, T1.no_eda ");
			strSql.append(" 		, nm_sample ");
			strSql.append(" 		, sort_shisaku ");
			strSql.append(" 	FROM ");
			strSql.append(" 		tr_shisan_shisaku AS T1 ");
			strSql.append(" 		INNER JOIN tr_shisaku AS T2 ");
			strSql.append(" 				ON T1.cd_shain = T2.cd_shain ");
			strSql.append(" 			   AND T1.nen = T2.nen ");
			strSql.append(" 			   AND T1.no_oi = T2.no_oi ");
			strSql.append(" 			   AND T1.seq_shisaku = T2.seq_shisaku ");
			strSql.append(" 	WHERE  ");
			strSql.append(" 		T1.fg_chusi IS NULL and T1.dt_shisan IS NULL");
			strSql.append(" 	) AS TT ");
			strSql.append(" WHERE ");
			strSql.append(" 	TT.no_shisaku IN ( ");
			strSql.append(strSqlWhere);
			strSql.append(" 	) ");
			strSql.append(" ORDER BY sort_shisaku ");

		} catch (Exception e) {

			em.ThrowException(e, "原価試算データ検索処理に失敗しました。");

		} finally {
			strSqlWhere = null;
		}

		return strSql;
	}

	/**
	 * 試作データパラメーター格納
	 *  : 試作データ情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShisakuData(
							List<?> lstRecset
							, List<?> lstRecset2
							, RequestResponsTableBean resTable
						)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//原価試算データループ（試算　試作品テーブル数）
			for (int i = 0; i < lstRecset.size(); i++) {


				//検索結果取得
				Object[] items = (Object[]) lstRecset.get(i);
				String no_row = toString(items[0]);
				String hi_kizitu = toString(items[1]);
				String cnt_irai = toString(items[2]);
				String no_shisaku = toString(items[3]);
				String no_eda = toString(items[4]);
				String nm_shisaku = toString(items[5]);
				String nm_shurui = toString(items[6]);
				String st_kenkyu = toString(items[7]);
				String st_seikan = toString(items[8]);
				String st_gentyo = toString(items[9]);
				String st_kojo = toString(items[10]);
				String st_eigyo = toString(items[11]);
				String cd_saiyou = toString(items[12]);
				String no_saiyou = toString(items[13]);
				String nm_team = toString(items[14]);
				String nm_liuser = toString(items[15]);
				String nm_kaisha_seizo = toString(items[16]);
				String nm_kaisha_busho = toString(items[17]);
				String memo_eigyo = toString(items[18]);
				//  【QP@10713】2011.10.28 ADD start hisahori
				String cd_nisugata = toString(items[19]);
				String nm_ondo = toString(items[20]);
				String nm_tantoeigyo = toString(items[21]);
				String nm_tantosha = toString(items[22]);
				//  【QP@10713】2011.10.28 ADD end
				String list_max_row = toString(items[23]);
				String max_row = toString(items[24]);

				//試算依頼サンプルデータループ（試算　試作テーブル数）
				String no_iraisample = "";
				for (int j = 0; j < lstRecset2.size(); j++) {
					//検索結果取得
					Object[] items2 = (Object[]) lstRecset2.get(j);
					String no_shisaku_irai = toString(items2[0]);
					String no_eda_irai = toString(items2[1]);
					String nm_sample_irai = toString(items2[2]);

					//試作No、枝番が等しい場合にｻﾝﾌﾟﾙNO追加
					if( no_shisaku.equals( no_shisaku_irai ) && no_eda.equals( no_eda_irai ) ){
						if( no_iraisample.equals("") ){
							no_iraisample = nm_sample_irai;
						}
						else{
							no_iraisample = no_iraisample + "," + nm_sample_irai;
						}
					}
				}

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				//レスポンス生成
				resTable.addFieldVale(i, "no_row", no_row);
				resTable.addFieldVale(i, "hi_kizitu", hi_kizitu);
				resTable.addFieldVale(i, "cnt_irai", cnt_irai);
				resTable.addFieldVale(i, "no_shisaku", no_shisaku);
				resTable.addFieldVale(i, "no_eda", no_eda);
				resTable.addFieldVale(i, "nm_shisaku", nm_shisaku);
				resTable.addFieldVale(i, "nm_shurui", nm_shurui);
				resTable.addFieldVale(i, "no_iraisample", no_iraisample);
				resTable.addFieldVale(i, "st_kenkyu", st_kenkyu);
				resTable.addFieldVale(i, "st_seikan", st_seikan);
				resTable.addFieldVale(i, "st_gentyo", st_gentyo);
				resTable.addFieldVale(i, "st_kojo", st_kojo);
				resTable.addFieldVale(i, "st_eigyo", st_eigyo);
				resTable.addFieldVale(i, "cd_saiyou", cd_saiyou);
				resTable.addFieldVale(i, "no_saiyou", no_saiyou);
				resTable.addFieldVale(i, "nm_team", nm_team);
				resTable.addFieldVale(i, "nm_liuser", nm_liuser);
				resTable.addFieldVale(i, "nm_kaisha_seizo", nm_kaisha_seizo);
				resTable.addFieldVale(i, "nm_kaisha_busho", nm_kaisha_busho);
				resTable.addFieldVale(i, "memo_eigyo", memo_eigyo);
				//  【QP@10713】2011.10.28 ADD start hisahori
				resTable.addFieldVale(i, "cd_nisugata", cd_nisugata);
				resTable.addFieldVale(i, "nm_ondo", nm_ondo);
				resTable.addFieldVale(i, "nm_tantoeigyo", nm_tantoeigyo);
				resTable.addFieldVale(i, "nm_tantosha", nm_tantosha);
				//  【QP@10713】2011.10.28 ADD end
				resTable.addFieldVale(i, "list_max_row", list_max_row);
				resTable.addFieldVale(i, "max_row", max_row);
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstRecset.size()));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算データ検索処理に失敗しました。");

		} finally {

		}

	}
}
