package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 分析値変更確認ＤＢ処理DB処理
 *  : JWS試作表②画面の「分析値の変更確認」ボタンより呼び出す。
 * テーブルから、データの抽出を行う。
 * 
 * @author TT.katayama
 * @since 2009/04/10
 */
public class ChangeGenryoSearchLogic extends LogicBase {

	/**
	 * 分析値変更確認用コンストラクタ : インスタンス生成
	 */
	public ChangeGenryoSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 分析値変更確認情報取得SQL作成
	 * @param reqKind : 機能リクエストデータ
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

		StringBuffer strSql = null;
		List<?> lstGenryoSearchData = null;
		List<?> lstKaishaBushoData = null;
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
			
			//③： 部署マスタ 検索用SQL作成メソッド呼び出しSQLを取得する。
			strSql = new StringBuffer();
			strSql = createBushoMstSQL(reqData);

			//②：データベース検索を用い、部署マスタ 検索結果を取得する。
//			super.createSearchDB();
			lstKaishaBushoData = searchDB.dbSearch(strSql.toString());
			
			//④： リクエストデータと検索結果を比較し、分析値変更確認データを取得する
			lstRecset = setBunsekiHenkouData(reqData, lstGenryoSearchData);
			
			//⑤： 分析値変更確認データに会社名・部署名を設定する
			lstRecset = setKaishaBushoData(lstRecset, lstKaishaBushoData);
			
			//⑥：分析値変更確認データパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			
			// 機能IDの設定
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// テーブル名の設定
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);
			
			// レスポンスデータの形成
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "分析値変更確認情報取得処理が失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstGenryoSearchData);
			removeList(lstKaishaBushoData);
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
			strKinoId = null;
			strTableNm = null;
		}
		
		//⑦：機能レスポンスデータを返却する。
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
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
			strRetSQL.append("   ,'' AS nm_kaisha ");
			strRetSQL.append("   ,'' AS nm_busho ");
			//ADD start 20121031 QP@20505
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
			//ADD end 20121031 QP@20505
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
//				String strReqGenryoNm = reqData.getFieldVale(0, i, "nm_genryo");
//				String strReqTanka = reqData.getFieldVale(0, i, "tanka");
//				String strReqBudomari = reqData.getFieldVale(0, i, "budomari");
//				String strReqAburaRitu = reqData.getFieldVale(0, i, "ritu_abura");
//				String strReqSakusanRitu = reqData.getFieldVale(0, i, "ritu_sakusan");
//				String strReqShokuenRitu = reqData.getFieldVale(0, i, "ritu_shokuen");
//				String strReqSousanRit = reqData.getFieldVale(0, i, "ritu_sousan");
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
//				strSQL_where.append("  AND M402.nm_genryo = '" + strReqGenryoNm + "' ");
//				strSQL_where.append("  AND M402.tanka =" + strReqTanka );
//				strSQL_where.append("  AND M402.budomari =" + strReqBudomari );
//				strSQL_where.append("  AND M401.ritu_abura =" + strReqAburaRitu );
//				strSQL_where.append("  AND M401.ritu_sakusan =" + strReqSakusanRitu );
//				strSQL_where.append("  AND M401.ritu_shokuen =" + strReqShokuenRitu );
//				strSQL_where.append("  AND M401.ritu_sousan =" + strReqSousanRit );
				strSQL_where.append("  )  ");
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "分析値変更確認情報検索用SQL作成処理が失敗しました。");
		} finally {
			//変数の削除
			strSQL_where = null;
		}
		
		//②:作成したSQLを返却
		return strRetSQL;
	}

	/**
	 * 部署マスタ 検索用SQL作成
	 * @param lstBunsekiHenkouData : 分析値変更確認データ 
	 * @return 作成SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createBushoMstSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSQL = new StringBuffer();
		StringBuffer strSQL_where = new StringBuffer();		//検索条件用
		int intRecCount = 0;

		//①：リクエストデータより、部署マスタ情報を取得するSQLを作成する。
		try {

			//M401[ma_busho]より取得
			strRetSQL.append(" SELECT DISTINCT ");
			strRetSQL.append("   M104.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("  ,M104.cd_busho AS cd_busho ");
			strRetSQL.append("  ,M104.nm_kaisha AS nm_kaisha ");
			strRetSQL.append("  ,M104.nm_busho AS nm_busho ");
			strRetSQL.append("  FROM ma_busho M104 ");

			//機能リクエストデータ数を取得
			intRecCount = reqData.getCntRow(0);
			
			//検索条件を設定
			for ( int i=0 ; i<intRecCount; i++ ) {
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				//検索条件の追加
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");	
				} else {
					strSQL_where.append(" OR ");
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  M104.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M104.cd_busho =" + strReqBushoCd);
				strSQL_where.append("  )  ");
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch(Exception e) {
			this.em.ThrowException(e, "会社名・部署名 検索用SQL作成処理が失敗しました。");
		} finally {
		}

		//②:作成したSQLを返却
		return strRetSQL;
	}
	
	/**
	 * 分析値変更確認データ設定
	 *  ： リクエストデータと原料検索結果を比較し、変更されている項目を設定する。
	 *  リクエストデータと会社名・部署名検索結果を比較し、会社名・部署名を設定する。
	 * @param reqData : リクエストデータ
	 * @param lstGenryoSearchData : 原料検索結果
	 * @param lstKaishaBushoData : 会社名・部署名検索結果
	 * @return 分析値変更確認データ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setBunsekiHenkouData(RequestResponsKindBean reqData, List<?> lstGenryoSearchData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ArrayList<Object[]> lstRetBunsekiData = new ArrayList<Object[]>();
		int intRecCount = 0;
		Object[] data = null;
	    
		try {
			intRecCount = reqData.getCntRow(0);			

			//リクエストデータと原料検索結果を比較し、設定
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				boolean blnSameChk = true;
				boolean blnDeleteChk = true;

				//MOD start 20121031 QP@20505
//				data = new Object[12];
				data = new Object[13];
				//MOD end 20121031 QP@20505
				
				//リクエストパラメータの取得
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
//				String strReqGenryoNm = reqData.getFieldVale(0, i, "nm_genryo");
//				String strReqTanka = reqData.getFieldVale(0, i, "tanka");
//				String strReqBudomari = reqData.getFieldVale(0, i, "budomari");
				String strReqAburaRitu = reqData.getFieldVale(0, i, "ritu_abura");
				String strReqSakusanRitu = reqData.getFieldVale(0, i, "ritu_sakusan");
				String strReqShokuenRitu = reqData.getFieldVale(0, i, "ritu_shokuen");
				String strReqSousanRit = reqData.getFieldVale(0, i, "ritu_sousan");
				//ADD start 20121031 QP@20505
				String strReqMSGRit = reqData.getFieldVale(0, i, "ritu_msg");
				//ADD end 20121031 QP@20505

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {

					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//リクエストされた原料とDBより検索した原料のマッチング
					
					//キー項目(会社コード、部署コード、原料コード)　比較
					if ( !(strReqKaishaCd.equals(items[0].toString())
							&& strReqBushoCd.equals(items[1].toString())
							&& strReqGenryoCd.equals(items[2].toString())) ) {
						
						items = null;
						blnDeleteChk = true;
						
						continue;
						
					}
					
					blnDeleteChk = false;

					//試作分析値とDB分析値の差を検出
					
//					//原料名
//					blnSameChk = strReqGenryoNm.equals(items[3].toString());
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
//					//単価
//					if ( (!strReqTanka.equals("NULL")) && (!items[4].equals("")) ) {
//						blnSameChk = (Double.parseDouble(strReqTanka) == Double.parseDouble(items[4].toString()));
//					} else {
//						blnSameChk = ((strReqTanka.equals("NULL")) && (items[4].equals("")));
//					}
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
//					//歩留
//					if ( (!strReqBudomari.equals("NULL")) && (!items[5].equals("")) ) {
//						blnSameChk = (Double.parseDouble(strReqBudomari) == Double.parseDouble(items[5].toString()));
//					} else {
//						blnSameChk = ((strReqBudomari.equals("NULL")) && (items[5].equals("")));
//					}
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
					//油含有率
					if ( (!strReqAburaRitu.equals("NULL")) && (!items[6].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqAburaRitu) == Double.parseDouble(items[6].toString()));
					} else {
						blnSameChk = ((strReqAburaRitu.equals("NULL")) && (items[6].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//酢酸
					if ( (!strReqSakusanRitu.equals("NULL")) && (!items[7].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqSakusanRitu) == Double.parseDouble(items[7].toString()));
					} else {
						blnSameChk = ((strReqSakusanRitu.equals("NULL")) && (items[7].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//食塩
					if ( (!strReqShokuenRitu.equals("NULL")) && (!items[8].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqShokuenRitu) == Double.parseDouble(items[8].toString()));
					} else {
						blnSameChk = ((strReqShokuenRitu.equals("NULL")) && (items[8].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//総酸
					if ( (!strReqSousanRit.equals("NULL")) && (!items[9].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqSousanRit) == Double.parseDouble(items[9].toString()));
					} else {
						blnSameChk = ((strReqSousanRit.equals("NULL")) && (items[9].equals("")));
					}
					//ADD start 20121031 QP@20505
					//MSG
					if ( (!strReqMSGRit.equals("NULL")) && (!items[12].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqMSGRit) == Double.parseDouble(items[12].toString()));
					} else {
						blnSameChk = ((strReqMSGRit.equals("NULL")) && (items[12].equals("")));
					}
					//MOD end 20121031 QP@20505
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					
					//変更箇所無しの場合、ループから抜ける
					blnSameChk = true;
					items = null;
					break;
					
				}
				
				//変更確認
				if ( blnDeleteChk ) {
					//存在しない場合					
					data[0] = strReqKaishaCd;
					data[1] = strReqBushoCd;
					data[2] = strReqGenryoCd;
					data[3] = "原料は存在しません";
					data[4] = "0";
					data[5] = "0";
					data[6] = "0";
					data[7] = "0";
					data[8] = "0";
					data[9] = "0";
					data[10] = "";
					data[11] = "";
					//ADD start 20121031 QP@20505
					data[12] = "";
					//ADD end 20121031 QP@20505
					lstRetBunsekiData.add(data);
				} else {
					if ( !blnSameChk ) {
						//データが異なる場合
						lstRetBunsekiData.add(data);
					}
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "分析値変更確認データ設定処理が失敗しました。");
		} finally {
			//リスト開放
			removeList(lstGenryoSearchData);
			
			intRecCount = 0;
			data = null;
		}
		
		return lstRetBunsekiData;
	}

	/**
	 * 分析値変更確認データ 会社名・部署名設定
	 *  ：分析値変更確認データと会社名・部署名検索結果を比較し、会社名・部署名を設定する。
	 * @param lstBunsekiHenkouData : 分析値変更確認データ
	 * @param lstKaishaBushoData : 会社名・部署名検索結果
	 * @return 分析値変更確認データ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setKaishaBushoData(List<?> lstBunsekiHenkouData, List<?> lstKaishaBushoData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {		
		List<Object[]> lstRetBunsekiData = (List<Object[]>) lstBunsekiHenkouData;
	    
		try {

			//分析値変更確認データと会社名・部署名検索結果を比較し、会社名・部署名を設定
			for (int i = 0; i < lstBunsekiHenkouData.size(); i++) {
				Object[] items = (Object[]) lstBunsekiHenkouData.get(i);
				
				for ( int j=0; j<lstKaishaBushoData.size(); j++ ) {
					Object[] nameItems = (Object[]) lstKaishaBushoData.get(j);
					
					//会社コード・部署コード　比較
					if ( !(items[0].toString().equals(nameItems[0].toString()) && items[1].toString().equals(nameItems[1].toString())) ) {
						continue;
					}
					//会社名を設定
					items[10] = nameItems[2];
					//部署名を設定
					items[11] = nameItems[3];
					break;
				}
				
				lstRetBunsekiData.set(i, items);
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "分析値変更確認データ 会社名・部署名設定処理が失敗しました。");
		} finally {
			//リスト開放
			removeList(lstKaishaBushoData);
		}
		
		return lstRetBunsekiData;
	}
	
	/**
	 * 分析値変更確認パラメーター格納
	 * 取得情報を、レスポンスデータ保持「機能ID：SA590O」に設定する。
	 * 
	 * @param lstBunsekiData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageBunsekiHenko(List<?> lstBunsekiData, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//①：引数　検索結果情報リストに保持している各パラメーターを機能レスポンスデータへ格納する。
		try {

			for (int i = 0; i < lstBunsekiData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstBunsekiData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "cd_busho", items[1].toString());
				resTable.addFieldVale(i, "cd_genryo", items[2].toString());
				resTable.addFieldVale(i, "nm_genryo", items[3].toString());
				resTable.addFieldVale(i, "tanka", items[4].toString());
				resTable.addFieldVale(i, "budomari", items[5].toString());
				resTable.addFieldVale(i, "ritu_abura", items[6].toString());
				resTable.addFieldVale(i, "ritu_sakusan", items[7].toString());
				resTable.addFieldVale(i, "ritu_shokuen", items[8].toString());
				resTable.addFieldVale(i, "ritu_sousan", items[9].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[10].toString());
				resTable.addFieldVale(i, "nm_busho", items[11].toString());
				//ADD start 20121031 QP@20505
				resTable.addFieldVale(i, "ritu_msg", items[12].toString());
				//ADD end 20121031 QP@20505
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "分析値変更確認パラメーター格納処理が失敗しました。");
	
		} finally {

		}

	}
	
}
