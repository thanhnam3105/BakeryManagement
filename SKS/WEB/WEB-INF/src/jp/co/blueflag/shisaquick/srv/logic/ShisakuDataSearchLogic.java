package jp.co.blueflag.shisaquick.srv.logic;

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
import jp.co.blueflag.shisaquick.srv.commonlogic.ImmGetConvList;


/**
 * 
 * 試作データ検索DB処理
 *  : 試作データ情報を検索する。
 *  機能ID：SA200　
 *  
 * @author TT.furuta
 * @since  2009/03/29
 */
public class ShisakuDataSearchLogic extends LogicBase {
	
	/**
	 * 試作データ検索コンストラクタ 
	 * : インスタンス生成
	 */
	public ShisakuDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();
		
	}

	/**
	 * 試作データ取得管理
	 *  : 試作データ取得処理を管理する。
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
		ArrayList<Object> arrCondition = null;
		StringBuffer strSql = new StringBuffer();
		
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
			
			//検索条件の取得
			arrCondition = createSerchCondition(reqRecBean);
						
			//SQL作成
			//strSql = createShisakuSQL(arrCondition, strSql);
			
			
			
			if (arrCondition.get(17).equals("1")){
				strSql = createShisakuGenkaSQL(arrCondition, strSql);
			}else{
				strSql = createShisakuSQL(arrCondition, strSql);
			}
			
			
			
			
			//DBインスタンス生成
			createSearchDB();
			//検索実行
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}
			
			//機能IDの設定
			resKind.setID(reqData.getID());
			
			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));

			//レスポンスデータの形成
			storageShisakuData(lstRecset, resKind.getTableItem(0));
					
		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ検索DB処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			removeList(arrCondition);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
			reqTableBean = null;
			reqRecBean = null;
		}
		return resKind;
	}
	
	/**
	 * 試作データ取得SQL作成
	 *  : 試作データを取得するSQLを作成。
	 * @param strSql : 検索条件SQL
	 * @param arrCondition：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuSQL(ArrayList<Object> arrCondition, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strWhere = new StringBuffer();
		StringBuffer strAllSql = new StringBuffer();
		
		try {
		
			boolean blAndOr = false;
			
			String strAndOr = " OR ";
			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");
			
			//SQL文の作成
			strAllSql.append("	SELECT ");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append("	,tbl.no_shisaku ");
			strAllSql.append("	,tbl.no_seiho ");
			strAllSql.append("	,tbl.nm_hin ");
			strAllSql.append("	,tbl.id_toroku ");
			strAllSql.append("	,tbl.nm_tanto ");
			strAllSql.append("	,tbl.nm_user ");
			strAllSql.append("	,tbl.nm_genre ");
			strAllSql.append("	,tbl.nm_ikatu ");
			strAllSql.append("	,tbl.nm_youto ");
			strAllSql.append("	,tbl.nm_genryo ");
			strAllSql.append("	,tbl.kbn_haishi ");
			strAllSql.append("	,tbl.nm_haishi ");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			//原価試算追加
			strAllSql.append("	,tbl.flg_shisanIrai AS flg_shisanIrai ");
			
			strAllSql.append("	FROM ( ");
			
			strSql.append("	SELECT ");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append("	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) + '-' +  ");
			strSql.append("	 RIGHT('00' + CONVERT(varchar,T1.nen),2) + '-' + ");
			strSql.append("	 RIGHT('000' + CONVERT(varchar,T1.no_oi),3) as no_shisaku ");
			strSql.append("	,ISNULL(T2.no_seiho1, '') as no_seiho ");
			strSql.append("	,ISNULL(T1.nm_hin, '') as nm_hin ");
			strSql.append("	,ISNULL(T1.id_toroku, 0) as id_toroku ");
			strSql.append("	,ISNULL(M2.nm_user,'') as nm_tanto ");
			strSql.append("	,ISNULL(MU.nm_literal, '') as nm_user ");
			strSql.append("	,ISNULL(MJ.nm_literal, '') as nm_genre ");
			strSql.append("	,ISNULL(MI.nm_literal, '') as nm_ikatu ");
//			strSql.append("	,MS.nm_literal as nm_youto ");
//			strSql.append("	,MT.nm_literal as nm_genryo ");
			strSql.append("	,ISNULL(T1.youto,'') as nm_youto ");
			strSql.append("	,ISNULL(T1.tokuchogenryo,'') as nm_genryo ");
			strSql.append("	,T1.kbn_haishi ");
			strSql.append("	,CASE T1.kbn_haishi WHEN 0 THEN '使用可能' WHEN 1 THEN '廃止' END as nm_haishi ");
			
//2009/10/20 TT.A.ISONO ADD START [原価試算：試算依頼（＄）追加]
			
			strSql.append("	,CASE T4.flg_shisanIrai  WHEN 0 THEN ''  WHEN 1 THEN '$'  END as flg_shisanIrai ");
			
//2009/10/20 TT.A.ISONO ADD END   [原価試算：試算依頼（＄）追加]
						
			strSql.append("	FROM tr_shisakuhin T1 ");
			strSql.append("	LEFT JOIN tr_shisaku T2 ON T1.cd_shain = T2.cd_shain AND T1.nen = T2.nen AND T1.no_oi = T2.no_oi AND T1.seq_shisaku = T2.seq_shisaku ");
//			strSql.append("	LEFT JOIN tr_haigo T3 ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
//			strSql.append("	LEFT JOIN ma_user M1 ON M1.id_user = ");
//			strSql.append(userInfoData.getId_user());
			strSql.append("	LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strSql.append("	LEFT JOIN ma_literal MU ON MU.cd_category = 'K_yuza' AND MU.cd_literal = T1.cd_user	");
			strSql.append("	LEFT JOIN ma_literal MJ ON MJ.cd_category = 'K_jyanru' AND MJ.cd_literal = T1.cd_genre ");
			strSql.append("	LEFT JOIN ma_literal MI ON MI.cd_category = 'K_ikatuhyouzi' AND MI.cd_literal = T1.cd_ikatu ");
//			strSql.append("	LEFT JOIN ma_literal MS ON MS.cd_category = 'K_yoto' AND MS.cd_literal = T1.youto ");
//			strSql.append("	LEFT JOIN ma_literal MT ON MT.cd_category = 'K_tokucyogenryo' AND MT.cd_literal = T1.tokuchogenryo ");

			
//2009/10/20 TT.A.ISONO ADD START [原価試算：試算依頼データ特定用の検索を追加]
						
			strSql.append(" LEFT JOIN ( ");
			strSql.append("   SELECT  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ,MAX(flg_shisanIrai) AS flg_shisanIrai ");
			strSql.append("   FROM ");
			strSql.append("    tr_shisaku ");
			strSql.append("   GROUP BY  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ) T4 ");
			strSql.append(" ON T1.cd_shain = T4.cd_shain ");
			strSql.append(" AND T1.nen = T4.nen ");
			strSql.append(" AND T1.no_oi = T4.no_oi ");
			
//2009/10/20 TT.A.ISONO ADD END   [原価試算：試算依頼データ特定用の検索を追加]
			
//　以下、検索条件により付加-----------------------------------------------------------------			
			//キーワード検索が入力されている場合
			//キーワード検索のロジック変更　2009/7/15　isono　START
			//			if (!arrCondition.get(14).equals("")){
//				strSql.append("	LEFT JOIN (SELECT cd_shain, nen, no_oi ");
//				strSql.append("	FROM tr_haigo ");
//				strSql.append("	WHERE ");
//
//				//キーワードを分解
//				String[] strKeyWord = arrCondition.get(14).toString().split(",");
//			
//				strSql.append(" ( ");
//				
//				for (int i=0;i<strKeyWord.length;i++){
//			
//					if (i != 0){
//						strSql.append(" AND ");
//					}
//					
//					//数値の場合
//					if (strKeyWord[i].matches("[0-9]+")){
//						strSql.append(" (cd_genryo = '");
//						strSql.append(strKeyWord[i] + "'");
//						strSql.append(" OR nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%') ");
//					//文字の場合
//					}else{
//						strSql.append(" nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}
//				}
//				
//				strSql.append(" ) ");
//				strSql.append(" GROUP BY cd_shain, nen, no_oi ");
//				strSql.append(" ) T3 ");
//				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
//			}

			if (!arrCondition.get(14).equals("")){
				strSql.append("	LEFT JOIN (SELECT HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				strSql.append("	FROM tr_haigo  AS HIG_M");

				//キーワードを分解
				String[] strKeyWord = arrCondition.get(14).toString().split(",");
				
				//IME変換取得用クラス生成
				ImmGetConvList ImeSearch = new ImmGetConvList();
			
				for (int i=0;i<strKeyWord.length;i++){
			
					strSql.append("	    RIGHT JOIN (");
					strSql.append("        SELECT");
					strSql.append("              cd_shain");
					strSql.append("            , nen");
					strSql.append("            , no_oi");
					strSql.append("        FROM");
					strSql.append("            tr_haigo");
					strSql.append("        WHERE");
					
//					//数字/文字を判定　※数字の場合、原料コードと見なす
//					if (strKeyWord[i].matches("[0-9]+")){
//						//数値の場合
//						strSql.append(" cd_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%'");
//						strSql.append(" OR nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}else{
//						//文字の場合
//						strSql.append(" nm_genryo LIKE '%");
//						strSql.append(strKeyWord[i] + "%' ");
//					}
					
					
					//キーワード検索のロジック変更　2009/8/26　nishigawa　START
					
					//変換実行
					ArrayList arySearch = ImeSearch.ImmGetConvListChange(strKeyWord[i]);
					
					for(int j = 0; j < arySearch.size(); j++){
						
						//候補文字取得
						String getStr = (String)arySearch.get(j);
						
						//1件目の場合
						if(j < 1){
							
						//2件目以降
						}else{
							
							strSql.append(" OR ");
							
						}
						
						//数字/文字を判定　※数字の場合、原料コードと見なす
						if (getStr.matches("[0-9]+")){
							
							//数値の場合
							strSql.append(" cd_genryo LIKE '%");
							strSql.append(getStr + "%'");
							strSql.append(" OR nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}else{
							
							//文字の場合
							strSql.append(" nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}
						
					}
					
					//キーワード検索のロジック変更　2009/8/26　nishigawa　End
					
					strSql.append("	        ) AS HIG_");
					strSql.append(toString(i));
					strSql.append("    ON");
					strSql.append("        HIG_M.cd_shain = HIG_");
					strSql.append(toString(i));
					strSql.append(".cd_shain");
					strSql.append("    AND HIG_M.nen = HIG_");
					strSql.append(toString(i));
					strSql.append(".nen");
					strSql.append("    AND HIG_M.no_oi = HIG_");
					strSql.append(toString(i));
					strSql.append(".no_oi");
				
				}
				
				strSql.append(" WHERE");
				strSql.append("     ISNULL(HIG_M.cd_shain,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.nen,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.no_oi,-1) != -1");
				strSql.append(" GROUP BY HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				
				strSql.append(" ) T3 ");
				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			}
			//キーワード検索のロジック変更　2009/7/15　isono　END
			
			//「条件で絞込み」がチェックされている場合、ANDを設定。
			if (!arrCondition.get(1).equals("")){
				strAndOr = "AND";
			}
 			
			
			
			
			//試作Noが入力されている場合
			if (!arrCondition.get(3).equals("")){
				//試作コードを分解
				String[] strShisaku = arrCondition.get(3).toString().split("-");
				
				//2009/08/04 UPD START 課題№295の対応
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}
//				
//				strWhere.append(" ( ");
//				strWhere.append(" T1.cd_shain = ");
//				strWhere.append(strShisaku[0]);
//				strWhere.append(" AND T1.nen = ");
//				strWhere.append(strShisaku[1]);
//				strWhere.append(" AND T1.no_oi = ");
//				strWhere.append(strShisaku[2]);
//				strWhere.append(" ) ");

				if (strShisaku.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					
					strWhere.append(" ( ");
					//社員CDのみの場合
					if (strShisaku.length == 1){
						strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//社員CDと年のみの場合
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
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
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							if (!(strShisaku[0].equals("") && strShisaku[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
				//2009/08/04 UPD END
			}
	
			
			
			
			//製法Noが入力されている、且つ「製法も検索対象」がチェックされている場合
			if (!arrCondition.get(4).equals("") && !arrCondition.get(0).equals("")){
				
				//製法Noを分割
				String[] strSeiho = arrCondition.get(4).toString().split("-");
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}

//				strWhere.append(" ( ");
//				strWhere.append(" T2.cd_shain = T1.cd_shain ");
//				strWhere.append(" AND T2.nen  = T1.nen ");
//				strWhere.append(" AND T2.no_oi = T1.no_oi ");
//				strWhere.append(" AND T2.seq_shisaku = T1.seq_shisaku ");
//				strWhere.append(" AND T2.no_seiho1 = '");
//				strWhere.append(arrCondition.get(4).toString() + "' ");
//				strWhere.append(" ) ");
				
				if (strSeiho.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					strWhere.append(" ( ");
				
					//会社コードのみの場合
					if (strSeiho.length == 1){
						strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
						strWhere.append(strSeiho[0] + "%' ");
					}
					//会社コードと種別の場合
					if (strSeiho.length == 2){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
					}
					//会社コードと種別と年の場合
					if (strSeiho.length == 3){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
					}
					//会社コードと種別と年と追番の場合
					if (strSeiho.length == 4){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
						if (!(strSeiho[3].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals("") && strSeiho[2].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,13,4) LIKE '%");
							strWhere.append(strSeiho[3] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
			}
			
			//試作名が入力されている場合
			if (!arrCondition.get(5).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.nm_hin LIKE '%");
				strWhere.append(arrCondition.get(5).toString() + "%' ");
			}
			
			//所属グループが選択されている場合
			if (!arrCondition.get(6).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
			}
			
			//所属チームが選択されている場合
			if (!arrCondition.get(7).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
				strWhere.append(" AND T1.cd_team = ");
				strWhere.append(arrCondition.get(7).toString());
				strWhere.append(" ) ");
			}
			
			//担当者が選択されている場合
			if (!arrCondition.get(8).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.id_toroku = ");
				strWhere.append(arrCondition.get(8).toString());
			}
			
			//ユーザが選択されている場合
			if (!arrCondition.get(9).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_user = '");
				strWhere.append(arrCondition.get(9).toString());
				strWhere.append("'");
			}
			
			//ジャンルが選択されている場合
			if (!arrCondition.get(10).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_genre = '");
				strWhere.append(arrCondition.get(10).toString());
				strWhere.append("'");
			}
			
			//一括表示名称が選択されている場合
			if (!arrCondition.get(11).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_ikatu = '");
				strWhere.append(arrCondition.get(11).toString());
				strWhere.append("'");
			}
	
			//製品の用途が選択されている場合
			if (!arrCondition.get(12).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

//				strWhere.append(" T1.youto = ");
				strWhere.append(" T1.youto LIKE '%");
				strWhere.append(arrCondition.get(12).toString());
				strWhere.append("%'");
			}
	
			//特徴原料が選択されている場合
			if (!arrCondition.get(13).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

//				strWhere.append(" T1.tokuchogenryo = ");
				strWhere.append(" T1.tokuchogenryo LIKE '%");
				strWhere.append(arrCondition.get(13).toString());
				strWhere.append("%'");
			}
	
			//キーワード検索が入力されている場合
			if (!arrCondition.get(14).equals("")){
//				//キーワードを分解
//				String[] strKeyWord = arrCondition.get(14).toString().split(",");
//			
//				if (!blAndOr){
//					strWhere.append(" ( ");
//					blAndOr = true;
//				} else {
//					strWhere.append(strAndOr);
//					blAndOr = true;
//				}
//				
//				strWhere.append(" ( ");
//				
//				for (int i=0;i<strKeyWord.length;i++){
//			
//					if (i != 0){
//						strWhere.append(" AND ");
//					}
//					
//					//数値の場合
//					if (strKeyWord[i].matches("[0-9]+")){
//						strWhere.append(" (T3.cd_genryo = ");
//						strWhere.append(strKeyWord[i]);
//						strWhere.append(" OR T3.nm_genryo LIKE '%");
//						strWhere.append(strKeyWord[i] + "%') ");
//					//文字の場合
//					}else{
//						strWhere.append(" T3.nm_genryo LIKE '%");
//						strWhere.append(strKeyWord[i] + "%' ");
//					}
//				}
//				
//				strWhere.append(" ) ");
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_shain = T3.cd_shain");
				strWhere.append(" AND T1.nen = T3.nen");
				strWhere.append(" AND T1.no_oi = T3.no_oi");
				strWhere.append(" ) ");
			}

			//「廃止も検索対象」がチェックされていない場合
			if (arrCondition.get(2).equals("")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) AND ");
					blAndOr = true;
				}

				strWhere.append(" T1.kbn_haishi = 0 ");
			} else {
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) ");
					blAndOr = true;
				}

			}
			
			//検索条件権限設定
			//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
			if (arrCondition.get(16).equals("1")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append(" AND ");
						blAndOr = true;
					}
				}
				
				strWhere.append(" ( ");
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());				
				strWhere.append(" ) ");
				
				//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){
					strWhere.append(" OR ");				
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
//					strWhere.append(" AND ");
//					strWhere.append(" M1.id_user = ");
//					strWhere.append(userInfoData.getId_user());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());				
					strWhere.append("' ) ");									
				}
				strWhere.append(" ) ");
				
			//同一グループ且つ担当会社
			} else if (arrCondition.get(16).equals("2")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
//				strWhere.append(" AND ");
//				strWhere.append(" T1.cd_team = ");
//				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto + " ) ");
				strWhere.append(" ) ");

				

			//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
			} else if (arrCondition.get(16).equals("3")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}
								
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto);
				strWhere.append(" ) ");

				//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){

					strWhere.append(" OR ");
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());
					strWhere.append("' AND ");
					strWhere.append(" T1.cd_kaisha in ( ");
					strWhere.append(strSqlTanto);
					strWhere.append(" ) ");
					strWhere.append(" ) ");
				}
				
				strWhere.append(" ) ");
			//自工場分
			} else if (arrCondition.get(16).equals("4")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append(" AND ");
						blAndOr = true;
					}
				}
				
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				strWhere.append(" AND ");
//				strWhere.append(" T1.cd_busho = ");
				strWhere.append(" T1.cd_kojo = ");
				strWhere.append(userInfoData.getCd_busho());
				strWhere.append(" ) ");
			}

			//権限が「自工場分」以外の場合
			if (!arrCondition.get(16).equals("4")){

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
				//現状、キユーピーの方のみシサクイックを使用する為、下記条件をコメントアウト
//				if (!blAndOr){
//					blAndOr = true;
//				} else {
//					if (!strWhere.toString().equals("")) {
//						strWhere.append(" AND ");
//						blAndOr = true;
//					}
//				}
//				strWhere.append(" T1.cd_shain IN ");
//				strWhere.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end	
				
			}
			
//2009/10/20 TT.A.ISONO ADD START [原価試算：依頼データのみチェックがオンの場合の条件を追加]

			if (arrCondition.get(17).equals("1")){
				if (!blAndOr){
					strWhere.append(" ");
					blAndOr = true;
					
				} else {
					strWhere.append(" AND ");
					blAndOr = true;
					
				}
				strWhere.append("(ISNULL(T4.flg_shisanIrai,0) = 1)");
				
			}

//2009/10/20 TT.A.ISONO ADD END   [原価試算：依頼データのみチェックがオンの場合の条件を追加]
			
			//条件有りの場合Where句設定
			if (!strWhere.toString().equals("")){
				strSql.append("	WHERE ").append(strWhere);				
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + arrCondition.get(15));
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.no_shisaku ");
			
			strSql = strAllSql;
		} catch (Exception e) {
			
			em.ThrowException(e, "試作データ検索処理に失敗しました。");
			
		} finally {
			//変数の削除
			strWhere = null;
			strAllSql = null;
		}
		
		return strSql;
	}
	/**
	 * 検索条件取得
	 *  : 試作データの抽出SQL用の条件を取得
	 * @param reqRecBean : 機能リクエストデータ
	 * @param arr：検索条件リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private ArrayList<Object> createSerchCondition(RequestResponsRowBean reqRecBean) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		ArrayList<Object> arr = null;
		
		try {
			
			//検索条件項目
			String strKbn_joken1 = "";
			String strKbn_joken2 = "";
			String strKbn_joken3 = "";
			String strNo_shisaku = "";
			String strNo_seiho = "";
			String strNm_shisaku = "";
			String strCd_group = "";
			String strCd_team = "";
			String strCd_tanto = "";
			String strCd_user = "";
			String strCd_genre = "";
			String strCd_ikatu = "";
			String strCd_youto = "";
			String strCd_genryo = "";
			String strKeyword = "";
			String strKengen = "";
			String strNo_page = "";

			String strKbn_joken4 = "";
			
			//検索条件取得
			//検索内容①設定
			strKbn_joken1 = reqRecBean.getFieldVale("kbn_joken1");
			//検索内容②設定
			strKbn_joken2 = reqRecBean.getFieldVale("kbn_joken2");					
			//検索内容③設定
			strKbn_joken3 = reqRecBean.getFieldVale("kbn_joken3");					
			//試作№設定
			strNo_shisaku = reqRecBean.getFieldVale("no_shisaku");					
			//製法№設定
			strNo_seiho = reqRecBean.getFieldVale("no_seiho");					
			//試作名設定
			strNm_shisaku = reqRecBean.getFieldVale("nm_shisaku");					
			//所属グループコード
			strCd_group = reqRecBean.getFieldVale("cd_group");				
			//所属チームコード
			strCd_team = reqRecBean.getFieldVale("cd_team");						
			//担当者コード
			strCd_tanto = reqRecBean.getFieldVale("cd_tanto");						
			//ユーザーコード
			strCd_user = reqRecBean.getFieldVale("cd_user");							
			//ジャンルコード
			strCd_genre = reqRecBean.getFieldVale("cd_genre");						
			//一括表示名称コード
			strCd_ikatu = reqRecBean.getFieldVale("cd_ikatu");					
			//製品の用途
			strCd_youto = reqRecBean.getFieldVale("cd_youto");					
			//特徴原料
			strCd_genryo = reqRecBean.getFieldVale("cd_genryo");				
			//キーワード
			strKeyword = reqRecBean.getFieldVale("keyword");					
			//ページ番号
			strNo_page = reqRecBean.getFieldVale("no_page");
			
			//検索内容④設定（原価依頼のみチェックボックス選択時に設定）
			try{
				strKbn_joken4 = reqRecBean.getFieldVale("kbn_joken4");					
				
			}catch(Exception e){
				
			}
			
			//検索条件をリストに設定
			arr = new ArrayList<Object>();
			//検索内容①設定		index:0
			arr.add(strKbn_joken1);		
			//検索内容②設定		index:1
			arr.add(strKbn_joken2);		
			//検索内容③設定		index:2
			arr.add(strKbn_joken3);		
			//試作№設定			index:3
			arr.add(strNo_shisaku);		
			//製法№設定			index:4
			arr.add(strNo_seiho);		
			//試作名設定			index:5
			arr.add(strNm_shisaku);		
			//所属グループコード		index:6
			arr.add(strCd_group);		
			//所属チームコード		index:7
			arr.add(strCd_team);		
			//担当者コード			index:8
			arr.add(strCd_tanto);		
			//ユーザーコード		index:9
			arr.add(strCd_user);		
			//ジャンルコード			index:10
			arr.add(strCd_genre);		
			//一括表示名称コード	index:11
			arr.add(strCd_ikatu);		
			//製品の用途			index:12
			arr.add(strCd_youto);		
			//特徴原料			index:13
			arr.add(strCd_genryo);		
			//キーワード			index:14
			arr.add(strKeyword);		
			//ページNo			index:15
			arr.add(strNo_page);		
			
			//権限取得
			for (int i=0;i < userInfoData.getId_gamen().size();i++){
				
				if (userInfoData.getId_gamen().get(i).toString().equals("10")){
					//データIDを設定
					strKengen = userInfoData.getId_data().get(i).toString();
				}
			}
			//権限情報(データID)	index:16
			arr.add(strKengen);			
			
//2009/10/20 TT.A.ISONO ADD START [原価試算：原価依頼のみチェックボックスの状態を設定する]
			
			//検索内容④設定		index:17
			arr.add(strKbn_joken4);

//2009/10/20 TT.A.ISONO ADD END   [原価試算：原価依頼のみチェックボックスの状態を設定する]
			
		} catch (Exception e) {
			
			em.ThrowException(e, "試作データ検索処理に失敗しました。");
			
		} finally {
			
		}
		return arr;
	}

	/**
	 * 試作データパラメーター格納
	 *  : 試作データ情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageShisakuData(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);
				int j = 0;

				//結果をレスポンスデータに格納
//				resTable.addFieldVale(i, "no_row", items[j++].toString());
//				resTable.addFieldVale(i, "no_shisaku", items[j++].toString());
//				resTable.addFieldVale(i, "no_seiho", items[j++].toString());
//				resTable.addFieldVale(i, "nm_hin", items[j++].toString());
//				resTable.addFieldVale(i, "id_toroku", items[j++].toString());
//				resTable.addFieldVale(i, "nm_tanto", items[j++].toString());
//				resTable.addFieldVale(i, "nm_user", items[j++].toString());
//				resTable.addFieldVale(i, "nm_genre", items[j++].toString());
//				resTable.addFieldVale(i, "nm_ikatu", items[j++].toString());
//				resTable.addFieldVale(i, "nm_youto", items[j++].toString());
//				resTable.addFieldVale(i, "nm_genryo", items[j++].toString());
//				resTable.addFieldVale(i, "kbn_haishi", items[j++].toString());
//				resTable.addFieldVale(i, "nm_haishi", items[j++].toString());
//				resTable.addFieldVale(i, "list_max_row", items[j++].toString());
//				resTable.addFieldVale(i, "max_row", items[j++].toString());

				resTable.addFieldVale(i, "no_row", toString(items[j++]));
				resTable.addFieldVale(i, "no_shisaku", toString(items[j++]));
				resTable.addFieldVale(i, "no_seiho", toString(items[j++]));
				resTable.addFieldVale(i, "nm_hin", toString(items[j++]));
				resTable.addFieldVale(i, "id_toroku", toString(items[j++]));
				resTable.addFieldVale(i, "nm_tanto", toString(items[j++]));
				resTable.addFieldVale(i, "nm_user", toString(items[j++]));
				resTable.addFieldVale(i, "nm_genre", toString(items[j++]));
				resTable.addFieldVale(i, "nm_ikatu", toString(items[j++]));
				resTable.addFieldVale(i, "nm_youto", toString(items[j++]));
				resTable.addFieldVale(i, "nm_genryo", toString(items[j++]));
				resTable.addFieldVale(i, "kbn_haishi", toString(items[j++]));
				resTable.addFieldVale(i, "nm_haishi", toString(items[j++]));
				resTable.addFieldVale(i, "list_max_row", toString(items[j++]));
				resTable.addFieldVale(i, "max_row", toString(items[j++]));

//2009/10/20 TT.A.ISONO ADD START [原価試算：原価試算依頼マーク（＄）を追加]
				
				resTable.addFieldVale(i, "dara", toString(items[j++]));

//2009/10/20 TT.A.ISONO ADD END   [原価試算：原価試算依頼マーク（＄）を追加]
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ検索処理に失敗しました。");
			
		} finally {

		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 試作データ取得SQL作成
	 *  : 試作データを取得するSQLを作成。
	 * @param strSql : 検索条件SQL
	 * @param arrCondition：検索条件項目
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuGenkaSQL(ArrayList<Object> arrCondition, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strWhere = new StringBuffer();
		StringBuffer strAllSql = new StringBuffer();
		
		try {
		
			boolean blAndOr = false;
			
			String strAndOr = " OR ";
			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");
			
			//SQL文の作成
			strAllSql.append("	SELECT ");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append("	,tbl.no_shisaku ");
			strAllSql.append("	,tbl.no_seiho ");
			strAllSql.append("	,tbl.nm_hin ");
			strAllSql.append("	,tbl.id_toroku ");
			strAllSql.append("	,tbl.nm_tanto ");
			strAllSql.append("	,tbl.nm_user ");
			strAllSql.append("	,tbl.nm_genre ");
			strAllSql.append("	,tbl.nm_ikatu ");
			strAllSql.append("	,tbl.nm_youto ");
			strAllSql.append("	,tbl.nm_genryo ");
			strAllSql.append("	,tbl.kbn_haishi ");
			strAllSql.append("	,tbl.nm_haishi ");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			//原価試算追加
			strAllSql.append("	,tbl.flg_shisanIrai AS flg_shisanIrai ");
			
			strAllSql.append("	FROM ( ");
			
			strSql.append("	SELECT ");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY T1.cd_shain, T1.nen, T1.no_oi)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append("	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) + '-' +  ");
			strSql.append("	 RIGHT('00' + CONVERT(varchar,T1.nen),2) + '-' + ");
			strSql.append("	 RIGHT('000' + CONVERT(varchar,T1.no_oi),3) as no_shisaku ");
			strSql.append("	,ISNULL(T2.no_seiho1, '') as no_seiho ");
			strSql.append("	,ISNULL(T1.nm_hin, '') as nm_hin ");
			strSql.append("	,ISNULL(T1.id_toroku, 0) as id_toroku ");
			strSql.append("	,ISNULL(M2.nm_user,'') as nm_tanto ");
			strSql.append("	,ISNULL(MU.nm_literal, '') as nm_user ");
			strSql.append("	,ISNULL(MJ.nm_literal, '') as nm_genre ");
			strSql.append("	,ISNULL(MI.nm_literal, '') as nm_ikatu ");
			strSql.append("	,ISNULL(T1.youto,'') as nm_youto ");
			strSql.append("	,ISNULL(T1.tokuchogenryo,'') as nm_genryo ");
			strSql.append("	,T1.kbn_haishi ");
			strSql.append("	,CASE T1.kbn_haishi WHEN 0 THEN '使用可能' WHEN 1 THEN '廃止' END as nm_haishi ");
			
//2009/10/20 TT.A.ISONO ADD START [原価試算：試算依頼（＄）追加]
			
			strSql.append("	,CASE T4.flg_shisanIrai  WHEN 0 THEN ''  WHEN 1 THEN '$'  END as flg_shisanIrai ");
			
//2009/10/20 TT.A.ISONO ADD END   [原価試算：試算依頼（＄）追加]
						
			strSql.append("	FROM tr_shisakuhin T1 ");
			

//2009/11/07 TT.NISHIGAWA ADD START [原価試算]
			
			strSql.append("	INNER JOIN tr_shisan_shisakuhin T0 ON T1.cd_shain = T0.cd_shain AND T1.nen = T0.nen AND T1.no_oi = T0.no_oi ");
			
			//【QP@00342】元版のデータのみ取得
			strSql.append("	AND T0.no_eda = 0 ");
			
//2009/11/07 TT.NISHIGAWA ADD END [原価試算]
			
			
			strSql.append("	LEFT JOIN tr_shisaku T2 ON T1.cd_shain = T2.cd_shain AND T1.nen = T2.nen AND T1.no_oi = T2.no_oi AND T1.seq_shisaku = T2.seq_shisaku ");
			strSql.append("	LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strSql.append("	LEFT JOIN ma_literal MU ON MU.cd_category = 'K_yuza' AND MU.cd_literal = T1.cd_user	");
			strSql.append("	LEFT JOIN ma_literal MJ ON MJ.cd_category = 'K_jyanru' AND MJ.cd_literal = T1.cd_genre ");
			strSql.append("	LEFT JOIN ma_literal MI ON MI.cd_category = 'K_ikatuhyouzi' AND MI.cd_literal = T1.cd_ikatu ");

			
//2009/10/20 TT.A.ISONO ADD START [原価試算：試算依頼データ特定用の検索を追加]
						
			strSql.append(" LEFT JOIN ( ");
			strSql.append("   SELECT  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ,MAX(flg_shisanIrai) AS flg_shisanIrai ");
			strSql.append("   FROM ");
			strSql.append("    tr_shisaku ");
			strSql.append("   GROUP BY  ");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append("   ) T4 ");
			strSql.append(" ON T1.cd_shain = T4.cd_shain ");
			strSql.append(" AND T1.nen = T4.nen ");
			strSql.append(" AND T1.no_oi = T4.no_oi ");
			
//2009/10/20 TT.A.ISONO ADD END   [原価試算：試算依頼データ特定用の検索を追加]
			

			if (!arrCondition.get(14).equals("")){
				strSql.append("	LEFT JOIN (SELECT HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				strSql.append("	FROM tr_haigo  AS HIG_M");

				//キーワードを分解
				String[] strKeyWord = arrCondition.get(14).toString().split(",");
				
				//IME変換取得用クラス生成
				ImmGetConvList ImeSearch = new ImmGetConvList();
			
				for (int i=0;i<strKeyWord.length;i++){
			
					strSql.append("	    RIGHT JOIN (");
					strSql.append("        SELECT");
					strSql.append("              cd_shain");
					strSql.append("            , nen");
					strSql.append("            , no_oi");
					strSql.append("        FROM");
					strSql.append("            tr_haigo");
					strSql.append("        WHERE");
					
					//キーワード検索のロジック変更　2009/8/26　nishigawa　START
					
					//変換実行
					ArrayList arySearch = ImeSearch.ImmGetConvListChange(strKeyWord[i]);
					
					for(int j = 0; j < arySearch.size(); j++){
						
						//候補文字取得
						String getStr = (String)arySearch.get(j);
						
						//1件目の場合
						if(j < 1){
							
						//2件目以降
						}else{
							
							strSql.append(" OR ");
							
						}
						
						//数字/文字を判定　※数字の場合、原料コードと見なす
						if (getStr.matches("[0-9]+")){
							
							//数値の場合
							strSql.append(" cd_genryo LIKE '%");
							strSql.append(getStr + "%'");
							strSql.append(" OR nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}else{
							
							//文字の場合
							strSql.append(" nm_genryo LIKE '%");
							strSql.append(getStr + "%' ");
							
						}
						
					}
					
					//キーワード検索のロジック変更　2009/8/26　nishigawa　End
					
					strSql.append("	        ) AS HIG_");
					strSql.append(toString(i));
					strSql.append("    ON");
					strSql.append("        HIG_M.cd_shain = HIG_");
					strSql.append(toString(i));
					strSql.append(".cd_shain");
					strSql.append("    AND HIG_M.nen = HIG_");
					strSql.append(toString(i));
					strSql.append(".nen");
					strSql.append("    AND HIG_M.no_oi = HIG_");
					strSql.append(toString(i));
					strSql.append(".no_oi");
				
				}
				
				strSql.append(" WHERE");
				strSql.append("     ISNULL(HIG_M.cd_shain,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.nen,-1) != -1");
				strSql.append(" AND ISNULL(HIG_M.no_oi,-1) != -1");
				strSql.append(" GROUP BY HIG_M.cd_shain, HIG_M.nen, HIG_M.no_oi ");
				
				strSql.append(" ) T3 ");
				strSql.append("	ON T1.cd_shain = T3.cd_shain AND T1.nen = T3.nen AND T1.no_oi = T3.no_oi ");
			}
			//キーワード検索のロジック変更　2009/7/15　isono　END
			
			//「条件で絞込み」がチェックされている場合、ANDを設定。
			if (!arrCondition.get(1).equals("")){
				strAndOr = "AND";
			}
 			
			
			
			
			//試作Noが入力されている場合
			if (!arrCondition.get(3).equals("")){
				//試作コードを分解
				String[] strShisaku = arrCondition.get(3).toString().split("-");
				
				if (strShisaku.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					
					strWhere.append(" ( ");
					//社員CDのみの場合
					if (strShisaku.length == 1){
						strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
						strWhere.append(strShisaku[0] + "%' ");
					}
					//社員CDと年のみの場合
					if (strShisaku.length == 2){
						if (!(strShisaku[0].equals(""))){
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
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
							strWhere.append(" RIGHT('0000000000' + CAST(T1.cd_shain AS VARCHAR), 10) LIKE '%");
							strWhere.append(strShisaku[0] + "%' ");
						}
						if (!(strShisaku[1].equals(""))){
							if (!(strShisaku[0].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('00' + CAST(T1.nen AS VARCHAR), 2) LIKE '%");
							strWhere.append(strShisaku[1] + "%' ");
						}
						if (!(strShisaku[2].equals(""))){
							if (!(strShisaku[0].equals("") && strShisaku[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" RIGHT('000' + CAST(T1.no_oi AS VARCHAR), 3) LIKE '%");
							strWhere.append(strShisaku[2] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
				//2009/08/04 UPD END
			}
	
			
			
			
			//製法Noが入力されている、且つ「製法も検索対象」がチェックされている場合
			if (!arrCondition.get(4).equals("") && !arrCondition.get(0).equals("")){
				
				//製法Noを分割
				String[] strSeiho = arrCondition.get(4).toString().split("-");
				
				if (strSeiho.length >= 1){
					if (!blAndOr){
						strWhere.append(" ( ");
						blAndOr = true;
					} else {
						strWhere.append(strAndOr);
						blAndOr = true;
					}
					strWhere.append(" ( ");
				
					//会社コードのみの場合
					if (strSeiho.length == 1){
						strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
						strWhere.append(strSeiho[0] + "%' ");
					}
					//会社コードと種別の場合
					if (strSeiho.length == 2){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
					}
					//会社コードと種別と年の場合
					if (strSeiho.length == 3){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
					}
					//会社コードと種別と年と追番の場合
					if (strSeiho.length == 4){
						if (!(strSeiho[0].equals(""))){
							strWhere.append(" SUBSTRING(T2.no_seiho1,1,4) LIKE '%");
							strWhere.append(strSeiho[0] + "%' ");
						}
						if (!(strSeiho[1].equals(""))){
							if(!strSeiho[0].equals("")){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,6,3) LIKE '%");
							strWhere.append(strSeiho[1] + "%' ");
						}
						if (!(strSeiho[2].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,10,2) LIKE '%");
							strWhere.append(strSeiho[2] + "%' ");
						}
						if (!(strSeiho[3].equals(""))){
							if (!(strSeiho[0].equals("") && strSeiho[1].equals("") && strSeiho[2].equals(""))){
								strWhere.append(" AND ");
							}
							strWhere.append(" SUBSTRING(T2.no_seiho1,13,4) LIKE '%");
							strWhere.append(strSeiho[3] + "%' ");
						}
					}
					strWhere.append(" ) ");
				}
			}
			
			//試作名が入力されている場合
			if (!arrCondition.get(5).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.nm_hin LIKE '%");
				strWhere.append(arrCondition.get(5).toString() + "%' ");
			}
			
			//所属グループが選択されている場合
			if (!arrCondition.get(6).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
			}
			
			//所属チームが選択されている場合
			if (!arrCondition.get(7).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(arrCondition.get(6).toString());
				strWhere.append(" AND T1.cd_team = ");
				strWhere.append(arrCondition.get(7).toString());
				strWhere.append(" ) ");
			}
			
			//担当者が選択されている場合
			if (!arrCondition.get(8).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.id_toroku = ");
				strWhere.append(arrCondition.get(8).toString());
			}
			
			//ユーザが選択されている場合
			if (!arrCondition.get(9).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_user = '");
				strWhere.append(arrCondition.get(9).toString());
				strWhere.append("'");
			}
			
			//ジャンルが選択されている場合
			if (!arrCondition.get(10).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_genre = '");
				strWhere.append(arrCondition.get(10).toString());
				strWhere.append("'");
			}
			
			//一括表示名称が選択されている場合
			if (!arrCondition.get(11).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.cd_ikatu = '");
				strWhere.append(arrCondition.get(11).toString());
				strWhere.append("'");
			}
	
			//製品の用途が選択されている場合
			if (!arrCondition.get(12).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

//				strWhere.append(" T1.youto = ");
				strWhere.append(" T1.youto LIKE '%");
				strWhere.append(arrCondition.get(12).toString());
				strWhere.append("%'");
			}
	
			//特徴原料が選択されている場合
			if (!arrCondition.get(13).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" T1.tokuchogenryo LIKE '%");
				strWhere.append(arrCondition.get(13).toString());
				strWhere.append("%'");
			}
	
			//キーワード検索が入力されている場合
			if (!arrCondition.get(14).equals("")){
				if (!blAndOr){
					strWhere.append(" ( ");
					blAndOr = true;
				} else {
					strWhere.append(strAndOr);
					blAndOr = true;
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_shain = T3.cd_shain");
				strWhere.append(" AND T1.nen = T3.nen");
				strWhere.append(" AND T1.no_oi = T3.no_oi");
				strWhere.append(" ) ");
			}

			//「廃止も検索対象」がチェックされていない場合
			if (arrCondition.get(2).equals("")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) AND ");
					blAndOr = true;
				}

				strWhere.append(" T1.kbn_haishi = 0 ");
			} else {
				if (!blAndOr){
					blAndOr = true;
				} else {
					strWhere.append(" ) ");
					blAndOr = true;
				}

			}
			
			//検索条件権限設定
			//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
			if (arrCondition.get(16).equals("1")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append(" AND ");
						blAndOr = true;
					}
				}
				
				strWhere.append(" ( ");
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());				
				strWhere.append(" ) ");
				
				//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){
					strWhere.append(" OR ");				
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());				
					strWhere.append("' ) ");									
				}
				strWhere.append(" ) ");
				
			//同一グループ且つ担当会社
			} else if (arrCondition.get(16).equals("2")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}

				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto + " ) ");
				strWhere.append(" ) ");

				

			//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
			} else if (arrCondition.get(16).equals("3")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append("AND");
						blAndOr = true;
					}
				}
								
				strWhere.append(" ( ");
				strWhere.append(" T1.cd_group = ");
				strWhere.append(userInfoData.getCd_group());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_team = ");
				strWhere.append(userInfoData.getCd_team());
				strWhere.append(" AND ");
				strWhere.append(" T1.id_toroku = ");
				strWhere.append(userInfoData.getId_user());
				strWhere.append(" AND ");
				strWhere.append(" T1.cd_kaisha in ( ");
				strWhere.append(strSqlTanto);
				strWhere.append(" ) ");

				//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
				if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
						<= Integer.parseInt(userInfoData.getCd_literal())){

					strWhere.append(" OR ");
					strWhere.append(" ( ");
					strWhere.append(" T1.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" T1.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_group = ");
					strWhere.append(userInfoData.getCd_group());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_team = ");
					strWhere.append(userInfoData.getCd_team());
					strWhere.append(" AND ");
					strWhere.append(" M2.cd_yakushoku <= '");
					strWhere.append(userInfoData.getCd_literal());
					strWhere.append("' AND ");
					strWhere.append(" T1.cd_kaisha in ( ");
					strWhere.append(strSqlTanto);
					strWhere.append(" ) ");
					strWhere.append(" ) ");
				}
				
				strWhere.append(" ) ");
			//自工場分
			} else if (arrCondition.get(16).equals("4")){
				if (!blAndOr){
					blAndOr = true;
				} else {
					if (!strWhere.toString().equals("")) {
						strWhere.append(" AND ");
						blAndOr = true;
					}
				}
				
//2009/11/7 TT.A.ISONO ADD START [原価試算：原価試算データの工場を確認]
				
				strWhere.append( " T0.cd_kaisha = "+ userInfoData.getCd_kaisha() );
				strWhere.append( " AND T0.cd_kojo = " + userInfoData.getCd_busho() );
				
//2009/11/7 TT.A.ISONO ADD END [原価試算：原価試算データの工場を確認]
				
			}

			//権限が「自工場分」以外の場合
			if (!arrCondition.get(16).equals("4")){
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
				//現状、キユーピーの方のみシサクイックを使用する為、下記条件をコメントアウト
//				if (!blAndOr){
//					blAndOr = true;
//				} else {
//					if (!strWhere.toString().equals("")) {
//						strWhere.append(" AND ");
//						blAndOr = true;
//					}
//				}
//				strWhere.append(" T1.cd_shain IN ");
//				strWhere.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end	
				
			}
			
//2009/10/20 TT.A.ISONO ADD START [原価試算：依頼データのみチェックがオンの場合の条件を追加]

			if (arrCondition.get(17).equals("1")){
				if (!blAndOr){
					strWhere.append(" ");
					blAndOr = true;
					
				} else {
					strWhere.append(" AND ");
					blAndOr = true;
					
				}
				strWhere.append("(ISNULL(T4.flg_shisanIrai,0) = 1)");
			}

//2009/10/20 TT.A.ISONO ADD END   [原価試算：依頼データのみチェックがオンの場合の条件を追加]
			
			//条件有りの場合Where句設定
			if (!strWhere.toString().equals("")){
				strSql.append("	WHERE ").append(strWhere);				
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + arrCondition.get(15));
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.no_shisaku ");
			
			strSql = strAllSql;
		} catch (Exception e) {
			
			em.ThrowException(e, "試作データ検索処理に失敗しました。");
			
		} finally {
			//変数の削除
			strWhere = null;
			strAllSql = null;
		}
		
		return strSql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
