package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * 試作原料検索DB処理
 *  : リクエストデータを用い、試作原料検索抽出SQLを作成する。
 * 取得情報を、機能レスポンスデータ保持「機能ID：SA570」に設定する。
 * 
 * @author TT.katayama
 * @since 2009/04/13
 */
public class ListGenryoSearchLogic extends LogicBase {

	/**
	 * 試作原料検索コンストラクタ : インスタンス生成
	 */
	public ListGenryoSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作原料データ取得
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

		//リクエストデータ
		String strKinoId = "";
		String strTableNm = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstGenryoSearchData = null;
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①：原料マスタ 検索用SQL作成メソッド呼び出しSQLを取得する。
			strSql = new StringBuffer();
			strSql = this.createGenryoMstSQL(reqData);
			
			//②：データベース検索を用い、原料マスタ 検索結果を取得する。
			super.createSearchDB();
			lstGenryoSearchData = searchDB.dbSearch(strSql.toString());

			//③： リクエストデータと検索結果を比較し、分析値変更確認データを取得する
			lstRecset = setGenryoBunsekiData(reqData, lstGenryoSearchData);
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}

			//④：試作原料パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			
			// 機能IDの設定
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// テーブル名の設定
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0),reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作原料データ取得処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
				
			}

			//変数の削除
			strKinoId = null;
			strTableNm = null;
			strSql = null;
			lstRecset = null;
			
		}
		//⑤：機能レスポンスデータを返却する。
		return resKind;
		
	}

	
	/**
	 * 原料マスタ 検索用SQL作成
	 * @param reqData : 機能リクエストデータ
	 * @return 作成SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createGenryoMstSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strRetSQL = new StringBuffer();
		StringBuffer strSQL_where = new StringBuffer();		//検索条件用
		int intRecCount = 0;
		
		//①：リクエストデータより、原料マスタ情報を取得するSQLを作成する。		
		try {

			//M401[ma_genryo, ma_genryokojo]より取得
			strRetSQL.append(" SELECT DISTINCT ");
			strRetSQL.append("   M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("   ,ISNULL(M402.cd_busho,'') AS cd_busho ");
			strRetSQL.append("   ,M401.cd_genryo AS cd_genryo ");
			
//			strRetSQL.append("   ,ISNULL(M402.nm_genryo,'原料が存在しません') AS nm_genryo ");
			strRetSQL.append("   ,ISNULL(M402_geryoname.nm_genryo,'原料が存在しません') AS nm_genryo ");
			
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
			strRetSQL.append("   ,ISNULL(M401.hyojian,'') AS hyojian ");
			strRetSQL.append("   ,ISNULL(M401.tenkabutu,'') AS tenkabutu ");
			strRetSQL.append("   ,ISNULL(M401.memo,'') AS memo ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo1,'') AS no_eiyo1 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo2,'') AS no_eiyo2 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo3,'') AS no_eiyo3 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo4,'') AS no_eiyo4 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo5,'') AS no_eiyo5 ");
			strRetSQL.append("   ,ISNULL(M401.wariai1,'') AS wariai1 ");
			strRetSQL.append("   ,ISNULL(M401.wariai2,'') AS wariai2 ");
			strRetSQL.append("   ,ISNULL(M401.wariai3,'') AS wariai3 ");
			strRetSQL.append("   ,ISNULL(M401.wariai4,'') AS wariai4 ");
			strRetSQL.append("   ,ISNULL(M401.wariai5,'') AS wariai5 ");
			
			//廃止区分名称
			strRetSQL.append("  ,M401.kbn_haishi AS kbn_haishi ");
			strRetSQL.append("  ,CASE M401.kbn_haishi ");
			strRetSQL.append("    WHEN 0 THEN '使用可能' ");
			strRetSQL.append("    WHEN 1 THEN '廃止' END as nm_haishi ");
			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.cd_kakutei),'') AS cd_kakutei ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.id_kakunin),'') AS id_kakunin ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.dt_kakunin,111),'') AS dt_kakunin ");			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("  ,ISNULL(M402.nisugata,'') AS nisugata ");
			strRetSQL.append("  ,ISNULL(M402.kikakusho,'') AS kikakusho ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.dt_konyu,111),'') AS dt_konyu ");
			
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
//			strRetSQL.append("  ,M401.id_toroku AS id_toroku ");
//			strRetSQL.append("  ,CONVERT(VARCHAR,M401.dt_toroku,111) AS dt_toroku ");
			strRetSQL.append("  ,M401.id_koshin AS id_koshin ");
			strRetSQL.append("  ,CONVERT(VARCHAR,M401.dt_koshin,111) AS dt_koshin ");
//mod end --------------------------------------------------------------------------------------

			//総件数・明細表示件数（空を挿入）
			strRetSQL.append("  ,'' AS max_row ");
			strRetSQL.append("  ,'' AS list_max_row ");
			
			//確認者・入力者
			strRetSQL.append("  ,ISNULL( M101_kakunin.nm_user, '') AS nm_kakunin ");
			strRetSQL.append("  ,ISNULL( M101_nyuryoku.nm_user, '') AS nm_koshin ");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//使用実績・未使用
			strRetSQL.append("  ,ISNULL( M402.flg_shiyo, '') AS flg_shiyo ");
			strRetSQL.append("  ,ISNULL( M402.flg_mishiyo, '') AS flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			
			// ADD start 20121025 QP@20505 No.24
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
			// ADD end 20121025 QP@20505 No.24
			
			strRetSQL.append(" FROM ma_genryo AS M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402 ");
			strRetSQL.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
			
			
			strRetSQL.append("  AND M402.cd_busho =" + reqData.getFieldVale(0, 0, "cd_busho"));
			//strRetSQL.append("  OR M402.cd_busho = 0");
			
			
			strRetSQL.append("  LEFT JOIN ma_user AS M101_kakunin ");
			strRetSQL.append("   ON M101_kakunin.id_user = M401.id_kakunin ");
			strRetSQL.append("  LEFT JOIN ma_user AS M101_nyuryoku ");
			//mod start --------------------------------------------------------------------------------------
			//QP@00412_シサクイック改良 No.13
//						strRetSQL.append("   ON M101_nyuryoku.id_user = M401.id_toroku ");
						strRetSQL.append("   ON M101_nyuryoku.id_user = M401.id_koshin ");
			//mod end --------------------------------------------------------------------------------------			
			
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402_geryoname ");
			strRetSQL.append("   ON  M402_geryoname.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402_geryoname.cd_genryo = M401.cd_genryo ");
			
			

			
			
			//機能リクエストデータ数を取得
			intRecCount = reqData.getCntRow(0);
			
			//検索条件を設定
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
//				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
//mod end --------------------------------------------------------------------------------------
				
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
				
				//検索条件の追加
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");
					
				} else {
					strSQL_where.append(" OR ");
					
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  (M401.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M401.cd_genryo ='" + strReqGenryoCd + "' ) ");
				
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				//strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
				//strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
//				strSQL_where.append("  OR ( M402.cd_kaisha = 1 AND M402.cd_busho = 0 AND M401.cd_genryo ='" + strReqGenryoCd + "')");
							
//mod end --------------------------------------------------------------------------------------
				
				strSQL_where.append("  )  ");
				
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作原料データ情報検索用SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL_where = null;
			
		}
		//②:作成したSQLを返却
		return strRetSQL;
		
	}
	
	/**
	 * 試作原料データ設定
	 *  ： リクエストデータと原料検索結果を比較し、検索結果が存在するかどうか確認する。
	 * @param reqData : リクエストデータ
	 * @param lstGenryoSearchData : 原料検索結果
	 * @return 試作原料データ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setGenryoBunsekiData(RequestResponsKindBean reqData, List<?> lstGenryoSearchData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {		
		
		ArrayList<Object[]> lstChkBunsekiData = new ArrayList<Object[]>();
		ArrayList<Object[]> lstRetBunsekiData = new ArrayList<Object[]>();
		int intRecCount = 0;
		int intListRowMax = 0;
		int intReqSelectRow = 0;
		Object[] data = null;
		boolean blnDeleteChk = true;
	    
		try {

			//最大表示項目数を設定
			intListRowMax = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"));

			//選択ページ番号の取得
			intReqSelectRow = Integer.parseInt(reqData.getFieldVale(0, 0, "num_selectRow"));
			
			//リクエストデータ数の取得
			intRecCount = reqData.getCntRow(0);	

			//リクエストデータと原料検索結果を比較し、設定
			for ( int i=0 ; i<intRecCount; i++ ) {

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//				data = new Object[37];
//QP@20505 MOD start 20121030 No.24
//				data = new Object[39];
				data = new Object[40];
//QP@20505 MOD end 20121030 No.24
//add end --------------------------------------------------------------------------------------
				
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {
					
					//検索結果を取得
					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//キー項目(会社コード、部署コード、原料コード)を比較する
					if ( (strReqKaishaCd.equals(items[0].toString())
							//&& strReqBushoCd.equals(items[1].toString())
							&& strReqGenryoCd.equals(items[2].toString())) ) {

						//原料データが存在する場合
						blnDeleteChk = false;
						data = items;
						break;
						
					} else {
						//原料データが存在しない場合
						blnDeleteChk = true;
						
					}
					
				}
				
				//存在チェック
				if ( blnDeleteChk ) {
					//存在しない場合					
					data[0] = strReqKaishaCd;
					data[1] = strReqBushoCd;
					data[2] = strReqGenryoCd;
					data[3] = "原料が存在しません";
					for ( int j=4; j<data.length; j++ ) {
						data[j] = "";	
						
					}
					
				}
				
				//総件数を設定
				data[33] = intRecCount;

				//明細表示件数を設定
				data[34] = intListRowMax;
				
				//データを格納
				lstRetBunsekiData.add(data);
				
			}
			
//			//ページ分割処理(対象ページのリストを表示)
//			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
//				
//				if ( i < lstChkBunsekiData.size() ) {
//					//データを格納
//					lstRetBunsekiData.add(lstChkBunsekiData.get(i));
//					
//				} else {
//					//表示件数オーバー時は、ループから抜ける
//					break;
//					
//				}
//				
//			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作原料データ設定処理が失敗しました。");
			
		} finally {
			//リスト開放
			removeList(lstGenryoSearchData);
			removeList(lstChkBunsekiData);
			
			//変数削除
			intRecCount = 0;
			intListRowMax = 0;
			intReqSelectRow = 0;
			data = null;
			
		}
		return lstRetBunsekiData;
		
	}

	/**
	 * 試作原料パラメーター格納
	 * 取得情報を、レスポンスデータ保持「機能ID：SA570O」に設定する。
	 * @param lstGenryoData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageBunsekiHenko(List<?> lstGenryoData
			,RequestResponsTableBean resTable
			,RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Object[] items = null;
		Object[] tgItems = null;
		
		//①：引数　検索結果情報リストに保持している各パラメーターを機能レスポンスデータへ格納する。

		try {
			
			//リクエストデータを行番号でソートする
			List<Object> tglist = reqData.getTableItem(0).getItemList();

			Collections.sort(tglist, new Comparator<Object>() {
				public int compare(Object tc1, Object tc2) {
					int tc1Name = 0;
					int tc2Name = 0;
					try {

						tc1Name = Integer.parseInt(((RequestResponsRowBean) tc1).getFieldVale("gyo_no"));
						tc2Name =  Integer.parseInt(((RequestResponsRowBean) tc2).getFieldVale("gyo_no"));

					} catch (Exception e) {

					}
					if (tc1Name < tc2Name) {
						return -1;
					} else if (tc1Name > tc2Name) {
						return 1;
					}
					return 0;
				}
			});
			
			//ページ分割処理(対象ページのリストを表示)
			//最大表示項目数を設定
			int intListRowMax = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"));
			//選択ページ番号の取得
			int intReqSelectRow = Integer.parseInt(reqData.getFieldVale(0, 0, "num_selectRow"));
			ArrayList<RequestResponsRowBean> rettglist = new ArrayList<RequestResponsRowBean>();
			
			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
				
				if ( i < tglist.size() ) {
					//データを格納
					rettglist.add((RequestResponsRowBean)tglist.get(i));
					
				} else {
					//表示件数オーバー時は、ループから抜ける
					break;
					
				}
				
			}
			
			//リクエストの原料分、レスポンスを生成する
			for(int ix = 0; ix < rettglist.size(); ix++){

				//リクエストされた原料に該当するデータを検索する
				for (int i = 0; i < lstGenryoData.size(); i++) {
					
					items = (Object[]) lstGenryoData.get(i);
					tgItems = null;

					if (toString(((RequestResponsRowBean) rettglist.get(ix)).getFieldVale("cd_genryo")).equals(toString(items[2]))){
						//該当するデータがある場合
						tgItems = items;
						break;
						
					}
					
				}
				
				//
				if ( tgItems != null ) {
					//処理結果の格納
					resTable.addFieldVale("rec" + ix, "flg_return", "true");
					resTable.addFieldVale("rec" + ix, "msg_error", "");
					resTable.addFieldVale("rec" + ix, "no_errmsg", "");
					resTable.addFieldVale("rec" + ix, "nm_class", "");
					resTable.addFieldVale("rec" + ix, "cd_error", "");
					resTable.addFieldVale("rec" + ix, "msg_system", "");
					
					//結果をレスポンスデータに格納
					resTable.addFieldVale("rec" + ix, "cd_kaisha", tgItems[0].toString());
					resTable.addFieldVale("rec" + ix, "cd_busho", tgItems[1].toString());
					resTable.addFieldVale("rec" + ix, "cd_genryo", tgItems[2].toString());
					resTable.addFieldVale("rec" + ix, "nm_genryo", tgItems[3].toString());
					resTable.addFieldVale("rec" + ix, "ritu_sakusan", tgItems[4].toString());
					resTable.addFieldVale("rec" + ix, "ritu_shokuen", tgItems[5].toString());
					resTable.addFieldVale("rec" + ix, "ritu_sousan", tgItems[6].toString());
					resTable.addFieldVale("rec" + ix, "ritu_abura", tgItems[7].toString());
					resTable.addFieldVale("rec" + ix, "hyojian", tgItems[8].toString());
					resTable.addFieldVale("rec" + ix, "tenkabutu", tgItems[9].toString());
					resTable.addFieldVale("rec" + ix, "memo", tgItems[10].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo1", tgItems[11].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo2", tgItems[12].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo3", tgItems[13].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo4", tgItems[14].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo5", tgItems[15].toString());
					resTable.addFieldVale("rec" + ix, "wariai1", tgItems[16].toString());
					resTable.addFieldVale("rec" + ix, "wariai2", tgItems[17].toString());
					resTable.addFieldVale("rec" + ix, "wariai3", tgItems[18].toString());
					resTable.addFieldVale("rec" + ix, "wariai4", tgItems[19].toString());
					resTable.addFieldVale("rec" + ix, "wariai5", tgItems[20].toString());
					resTable.addFieldVale("rec" + ix, "kbn_haishi", tgItems[21].toString());
					resTable.addFieldVale("rec" + ix, "nm_haishi", tgItems[22].toString());
					resTable.addFieldVale("rec" + ix, "cd_kakutei", tgItems[23].toString());
					resTable.addFieldVale("rec" + ix, "id_kakunin", tgItems[24].toString());
					resTable.addFieldVale("rec" + ix, "nm_kakunin", tgItems[35].toString());
					resTable.addFieldVale("rec" + ix, "dt_kakunin", tgItems[25].toString());
					resTable.addFieldVale("rec" + ix, "tanka", tgItems[26].toString());
					resTable.addFieldVale("rec" + ix, "budomari", tgItems[27].toString());
					resTable.addFieldVale("rec" + ix, "nisugata", tgItems[28].toString());
					resTable.addFieldVale("rec" + ix, "kikakusho", tgItems[29].toString());
					resTable.addFieldVale("rec" + ix, "dt_konyu", tgItems[30].toString());
					resTable.addFieldVale("rec" + ix, "id_koshin", tgItems[31].toString());
					resTable.addFieldVale("rec" + ix, "nm_koshin", tgItems[36].toString());
					resTable.addFieldVale("rec" + ix, "dt_koshin", tgItems[32].toString());
					resTable.addFieldVale("rec" + ix, "max_row", tgItems[33].toString());
					resTable.addFieldVale("rec" + ix, "list_max_row", tgItems[34].toString());
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
					resTable.addFieldVale("rec" + ix, "flg_shiyo", tgItems[37].toString());
					resTable.addFieldVale("rec" + ix, "flg_mishiyo", tgItems[38].toString());
//add end --------------------------------------------------------------------------------------
					
					// ADD start 20121025 QP@20505 No.24
					resTable.addFieldVale("rec" + ix, "ritu_msg", tgItems[39].toString());
					// ADD end 20121025 QP@20505 No.24
				}
				
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "試作原料パラメーター格納処理が失敗しました。");
			
		} finally {
			//リスト開放
			removeList(lstGenryoData);
			
			tgItems = null;

		}

	}
	
}
