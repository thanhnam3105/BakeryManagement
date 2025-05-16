package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
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
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0));

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
			strRetSQL.append("   ,M402.cd_busho AS cd_busho ");
			strRetSQL.append("   ,M401.cd_genryo AS cd_genryo ");
			
			strRetSQL.append("   ,M402.nm_genryo AS nm_genryo ");
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
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.dt_kakunin),'') AS dt_kakunin ");			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("  ,ISNULL(M402.nisugata,'') AS nisugata ");
			strRetSQL.append("  ,ISNULL(M402.kikakusho,'') AS kikakusho ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.dt_konyu,20),'') AS dt_konyu ");
			strRetSQL.append("  ,M401.id_toroku AS id_toroku ");
			strRetSQL.append("  ,M401.dt_toroku AS dt_toroku ");

			//総件数・明細表示件数（空を挿入）
			strRetSQL.append("  ,'' AS max_row ");
			strRetSQL.append("  ,'' AS list_max_row ");
			
			strRetSQL.append(" FROM ma_genryo AS M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402 ");
			strRetSQL.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
			
			//機能リクエストデータ数を取得
			intRecCount = reqData.getCntRow(0);
			
			//検索条件を設定
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
				
				//検索条件の追加
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");
					
				} else {
					strSQL_where.append(" OR ");
					
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  M401.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
				strSQL_where.append("  AND M401.cd_genryo ='" + strReqGenryoCd + "' ");
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
				
				data = new Object[35];
				
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {
					
					//検索結果を取得
					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//キー項目(会社コード、部署コード、原料コード)を比較する
					if ( (strReqKaishaCd.equals(items[0].toString())
							&& strReqBushoCd.equals(items[1].toString())
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
				lstChkBunsekiData.add(data);
				
			}
			
			//ページ分割処理(対象ページのリストを表示)
			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
				
				if ( i < lstChkBunsekiData.size() ) {
					//データを格納
					lstRetBunsekiData.add(lstChkBunsekiData.get(i));
					
				} else {
					//表示件数オーバー時は、ループから抜ける
					break;
					
				}
				
			}
			
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
	private void storageBunsekiHenko(List<?> lstGenryoData, RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//①：引数　検索結果情報リストに保持している各パラメーターを機能レスポンスデータへ格納する。
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
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale("rec" + i, "cd_busho", items[1].toString());
				resTable.addFieldVale("rec" + i, "cd_genryo", items[2].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[3].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[4].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[5].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[6].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[7].toString());
				resTable.addFieldVale("rec" + i, "hyojian", items[8].toString());
				resTable.addFieldVale("rec" + i, "tenkabutu", items[9].toString());
				resTable.addFieldVale("rec" + i, "memo", items[10].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo1", items[11].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo2", items[12].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo3", items[13].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo4", items[14].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo5", items[15].toString());
				resTable.addFieldVale("rec" + i, "wariai1", items[16].toString());
				resTable.addFieldVale("rec" + i, "wariai2", items[17].toString());
				resTable.addFieldVale("rec" + i, "wariai3", items[18].toString());
				resTable.addFieldVale("rec" + i, "wariai4", items[19].toString());
				resTable.addFieldVale("rec" + i, "wariai5", items[20].toString());
				resTable.addFieldVale("rec" + i, "kbn_haishi", items[21].toString());
				resTable.addFieldVale("rec" + i, "nm_haishi", items[22].toString());
				resTable.addFieldVale("rec" + i, "cd_kakutei", items[23].toString());
				resTable.addFieldVale("rec" + i, "id_kakunin", items[24].toString());
				resTable.addFieldVale("rec" + i, "dt_kakunin", items[25].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[26].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[27].toString());
				resTable.addFieldVale("rec" + i, "nisugata", items[28].toString());
				resTable.addFieldVale("rec" + i, "kikakusho", items[29].toString());
				resTable.addFieldVale("rec" + i, "dt_konyu", items[30].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[31].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[32].toString());
				resTable.addFieldVale("rec" + i, "max_row", items[33].toString());
				resTable.addFieldVale("rec" + i, "list_max_row", items[34].toString());
				
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "試作原料パラメーター格納処理が失敗しました。");
			
		} finally {
			//リスト開放
			removeList(lstGenryoData);

		}

	}
	
}
