package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 原価試算共通表示検索DB処理
 *  : 原価試算画面のﾍｯﾀﾞｰ部分の情報を取得する。
 *  機能ID：FGEN0010
 *  
 * @author Nishigawa
 * @since  2009/10/20
 */
public class FGEN0010_Logic extends LogicBase{
	
	private String kengen_moto;
	
	/**
	 * 原価試算共通表示検索DB処理
	 * : インスタンス生成
	 */
	public FGEN0010_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 原価試算共通表示
	 *  : 原価試算画面のﾍｯﾀﾞｰ部分の情報を取得する。
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;
		
		try {
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			
			//試作コード設定（リクエストに試作コードがない場合、USERINFOより試作コードを取得）
			if( toString(reqData.getFieldVale(0, 0, "cd_shain")) == "" ){
				
				//試作コード_社員CD、年、追番　設定
				reqData.setFieldVale(0, 0, "cd_shain", toString(userInfoData.getMovement_condition().get(0)) );
				reqData.setFieldVale(0, 0, "nen", toString(userInfoData.getMovement_condition().get(1)) );
				reqData.setFieldVale(0, 0, "no_oi", toString(userInfoData.getMovement_condition().get(2)) );
				//【QP@00342】
				reqData.setFieldVale(0, 0, "no_eda", toString(userInfoData.getMovement_condition().get(3)) );
				
			}
			
			
			//原価試算[ kihon ]　レスポンスデータの形成
			this.genkaKihonSetting(resKind, reqData);
			
			
			//原価試算[ tr_shisan_shisaku ]　レスポンスデータの形成
			this.genkaTr_shisan_shisakuSetting(resKind, reqData);
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstRecset);
		
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			
		}
		return resKind;
		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting（レスポンスデータの形成） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * 原価試算共通情報[ kihon ]レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;	
		//【QP@00342】
		List<?> lstRecset2 = null;	
		
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			
			//テーブル名
			String strTblNm = "kihon";	

//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 START -----------------------------------															
			
			//排他ユーザ取得
			strSqlBuf = this.createGenkaHaitaSQL(reqData);
			super.createSearchDB();

			//【QP@00342】
			lstRecset2 = searchDB.dbSearch(strSqlBuf.toString());

			//【QP@00342】排他ID取得
			String haita_id = "";
			if(lstRecset2.size() > 0){
				Object[] items = (Object[])lstRecset2.get(0);
				haita_id = toString(items[8]);
			}
			
			//権限取得
			ArrayList aryGamen = userInfoData.getId_gamen();
			ArrayList aryKino = userInfoData.getId_kino();
			
			//権限ループ（原価試算画面の権限を探索）
			for(int i=0; i<aryGamen.size(); i++){
				
//				//原価試算画面ID
//				if(aryGamen.get(i).equals("170")){
//					
//					//本来もっている権限を退避
//					this.kengen_moto = toString(aryKino.get(i));
//					
//					//編集権限の場合
//					if( toString(aryKino.get(i)).equals("20") ){
//						
//						//排他区分がNULLの場合
//						if(haita_id.equals("")){
//							
//							//自身のIDにて排他をかける
//							updateHaitaKbn(reqData);
//							
//						}
//						//排他区分がログインユーザのIDと一致する場合
//						else if(haita_id.equals(userInfoData.getId_user())){
//							
//							//処理しない
//							
//						}
//						//排他区分がログインユーザのIDと一致しない場合
//						else{
//							
//							//原価試算権限を「閲覧」に変更
//							aryKino.set(i, "70");
//							
//						}
//						
//					}
//				}
				
				//【QP@00342】
				//画面ID取得
				String gamenId = toString(aryGamen.get(i));
				
				//原価試算画面ID or 原価試算画面（営業）ID
				if(aryGamen.get(i).equals("170") || aryGamen.get(i).equals("190") ){
					
					//本来もっている権限を退避
					this.kengen_moto = toString(aryKino.get(i));
					
						
					//排他区分がNULLの場合
					if(haita_id.equals("")){
							
						//自身のIDにて排他をかける
						updateHaitaKbn(reqData);
						
					}
					//排他区分がログインユーザのIDと一致する場合
					else if(haita_id.equals(userInfoData.getId_user())){
							
						//処理しない
							
					}
					//排他区分がログインユーザのIDと一致しない場合
					else{
							
						//原価試算権限を「排他」に変更
						this.kengen_moto = "999";
							
					}
				}
			}
			
//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 END -------------------------------------
			
			
			//①原価試算共通情報[ kihon ]データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//②共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//【QP@00342】
			//④追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset , lstRecset2 , resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算共通情報[ kihon ]データ検索処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;
				
			}

			//変数の削除
			strSqlBuf = null;

		}
		
	}
	
	
	/**
	 * 排他区分の更新
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void updateHaitaKbn(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();
			
			try {
				strSQL.append(" UPDATE tr_shisan_shisakuhin");
				strSQL.append("   SET ");
				strSQL.append("        haita_id_user = " + toString(userInfoData.getId_user()));
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain = " + 
						toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
				strSQL.append(" AND nen = " + 
						toString(reqData.getFieldVale(0, 0, "nen")) + " ");
				strSQL.append(" AND no_oi = " + 
						toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

				//【QP@00342】
				strSQL.append(" AND no_eda = " + 
						toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

				
				//共通クラス　データベース管理を用い、排他区分の更新を行う
				this.execDB.execSQL(strSQL.toString());
				
				// コミット
				execDB.Commit();
				
			} catch(Exception e) {
				// ロールバック
				execDB.Rollback();
				this.em.ThrowException(e, "");
				
			} finally {
				if (execDB != null) {
					execDB.Close();				//セッションのクローズ
					execDB = null;
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "排他区分の更新処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
	}
	
	
	
	/**
	 * 原価試算共通情報[ tr_shisan_shisaku ]　レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaTr_shisan_shisakuSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;	
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			
			//テーブル名
			String strTblNm = "tr_shisan_shisaku";	

			//①原価試算共通情報[ tr_shisan_shisaku ]データ取得SQL作成
			strSqlBuf = this.createGenkaTr_shisan_shisakuSQL(reqData);
			
			//②共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//④追加したテーブルへレコード格納
			this.storageGenkaTr_shisan_shisaku(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算共通情報[ tr_shisan_shisaku ]データ検索処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;
				
			}

			//変数の削除
			strSqlBuf = null;

		}
		
	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * 原価試算共通情報[ kihon ]データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaKihonSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL文の作成
			strSql.append(" SELECT ");
			strSql.append("  T310.cd_shain AS cd_shain ");
			strSql.append(" ,T310.nen AS nen ");
			strSql.append(" ,T310.no_oi AS no_oi ");
			strSql.append(" ,T310.no_eda AS no_eda ");
//MOD start 【H24年度対応】 20120416 hisahori
//			strSql.append(" ,T110.nm_hin AS hin ");
			strSql.append(" ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			strSql.append("  THEN ");
			strSql.append("	 CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			strSql.append("	 THEN T110.nm_hin ");
			strSql.append("	 ELSE T110.nm_hin + ' 【' + T310.nm_edaShisaku + '】' END ");
			strSql.append(" ELSE T110.nm_hin END AS hin ");
			//MOD end 【H24年度対応】 20120416 hisahori
			strSql.append(" ,M101_KEN.nm_user AS nm_user_KEN ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.ken_dt_koshin ");
			strSql.append(" 	,111) AS ken_dt_koshin ");
			strSql.append(" ,M101_SEI.nm_user AS nm_user_SEI ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.sei_dt_koshin ");
			strSql.append(" 	,111) AS sei_dt_koshin ");
			strSql.append(" ,M101_GEN.nm_user AS nm_user_GEN ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.gen_dt_koshin ");
			strSql.append(" 	,111) AS gen_dt_koshin ");
			strSql.append(" ,M101_KJO.nm_user AS nm_user_KJO ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.kojo_dt_koshin ");
			strSql.append(" 	,111) AS kojo_dt_koshin ");
			strSql.append(" ,T310.saiyo_sample AS saiyo_sample ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.sam_dt_koshin ");
			strSql.append(" 	,111) AS sam_dt_koshin ");
			strSql.append(" ,M101_SAM.nm_user AS nm_user_SAM ");
			strSql.append(" ,M104.nm_kaisha AS nm_kaisha_haita ");
			strSql.append(" ,M104.nm_busho AS nm_busho_haita ");
			strSql.append(" ,M101_HAITA.nm_user AS nm_user_haita ");
			strSql.append(" ,T310.haita_id_user AS haita_id_user ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T110.no_irai) AS no_irai ");
			strSql.append(" ,M105.nm_ｌiteral AS shurui_eda ");
			strSql.append(" ,CONVERT(VARCHAR,T310.dt_kizitu,111) AS dt_kizitu ");
			strSql.append(" ,CASE T310.saiyo_sample WHEN -1  ");
			strSql.append(" 	THEN '採用なし' ");
			strSql.append(" 	ELSE T131.nm_sample ");
			strSql.append("  END AS nm_sample ");
// ADD start 【H24年度対応】 20120420 hisahori
			strSql.append(" ,T110.nm_hin AS nm_motHin ");
// ADD end 【H24年度対応】 20120420 hisahori
			strSql.append(" FROM tr_shisan_shisakuhin AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain ");
			strSql.append(" AND T310.nen = T110.nen ");
			strSql.append(" AND T310.no_oi = T110.no_oi ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_KEN ");
			strSql.append(" ON T310.ken_id_koshin = M101_KEN.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_SEI ");
			strSql.append(" ON T310.sei_id_koshin = M101_SEI.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_GEN ");
			strSql.append(" ON T310.gen_id_koshin = M101_GEN.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_KJO ");
			strSql.append(" ON T310.kojo_id_koshin = M101_KJO.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_SAM ");
			strSql.append(" ON T310.sam_id_koshin = M101_SAM.id_user ");
			strSql.append(" LEFT JOIN ma_user AS M101_HAITA ");
			strSql.append(" ON T310.haita_id_user = M101_HAITA.id_user ");
			strSql.append(" LEFT JOIN ma_busho AS M104 ");
			strSql.append(" ON M101_HAITA.cd_kaisha = M104.cd_kaisha ");
			strSql.append(" AND M101_HAITA.cd_busho = M104.cd_busho ");
			strSql.append(" LEFT JOIN ma_literal AS M105 ");
			strSql.append(" ON M105.cd_category = 'shurui_eda' ");
			strSql.append(" AND M105.cd_ｌiteral = T310.shurui_eda ");
			strSql.append(" LEFT JOIN tr_shisaku AS T131 ");
			strSql.append(" ON T131.cd_shain = T310.cd_shain ");
			strSql.append(" AND T131.nen = T310.nen ");
			strSql.append(" AND T131.no_oi = T310.no_oi ");
			strSql.append(" AND T131.seq_shisaku = T310.saiyo_sample ");
			strSql.append(" WHERE ");
			strSql.append("     T310.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T310.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T310.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

			//【QP@00342】
			strSql.append(" AND T310.no_eda = " + 
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**【QP@00342】
	 * 排他データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaHaitaSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL文の作成
			strSql.append(" SELECT ");
			strSql.append("  T310.cd_shain AS cd_shain ");
			strSql.append(" ,T310.nen AS nen ");
			strSql.append(" ,T310.no_oi AS no_oi ");
			strSql.append(" ,T310.no_eda AS no_eda ");
// ADD start 【H24年度対応】 20120420 hisahori
//			strSql.append(" ,T110.nm_hin AS hin ");
			strSql.append(" ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			strSql.append("  THEN ");
			strSql.append("	 CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			strSql.append("	 THEN T110.nm_hin ");
			strSql.append("	 ELSE T110.nm_hin + ' 【' + T310.nm_edaShisaku + '】' END ");
			strSql.append(" ELSE T110.nm_hin END AS hin ");
// ADD end 【H24年度対応】 20120420 hisahori
			strSql.append(" ,M104.nm_kaisha AS nm_kaisha_haita ");
			strSql.append(" ,M104.nm_busho AS nm_busho_haita ");
			strSql.append(" ,M101_HAITA.nm_user AS nm_user_haita ");
			strSql.append(" ,T310.haita_id_user AS haita_id_user ");
			strSql.append(" FROM tr_shisan_shisakuhin AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain ");
			strSql.append(" AND T310.nen = T110.nen ");
			strSql.append(" AND T310.no_oi = T110.no_oi ");
			strSql.append(" LEFT JOIN ma_user AS M101_HAITA ");
			strSql.append(" ON T310.haita_id_user = M101_HAITA.id_user ");
			strSql.append(" LEFT JOIN ma_busho AS M104 ");
			strSql.append(" ON M101_HAITA.cd_kaisha = M104.cd_kaisha ");
			strSql.append(" AND M101_HAITA.cd_busho = M104.cd_busho ");
			strSql.append(" WHERE ");
			strSql.append("     T310.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T310.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T310.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			strSql.append(" AND T310.haita_id_user IS NOT NULL ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * 原価試算共通情報[ tr_shisan_shisaku ]データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaTr_shisan_shisakuSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL文の作成
			
			//-- テスト ----------------------------------------------------------+
//			strSql.append("SELECT TOP 3");
//			strSql.append(" nm_sample");
//			strSql.append(" ,seq_shisaku");
//			strSql.append(" FROM tr_shisaku");
			//-- テスト ----------------------------------------------------------+
			strSql.append(" SELECT DISTINCT ");
			strSql.append("  T131.nm_sample ");
			strSql.append(" ,T131.seq_shisaku ");
			
			//【QP@00342】
			strSql.append(" ,T131.sort_shisaku ");
			
			strSql.append(" FROM tr_shisan_shisaku AS T331 ");
			strSql.append(" LEFT JOIN tr_shisaku AS T131 ");
			strSql.append(" ON  T331.cd_shain = T131.cd_shain ");
			strSql.append(" AND T331.nen      = T131.nen ");
			strSql.append(" AND T331.no_oi    = T131.no_oi ");

			//【QP@00342】
			strSql.append(" AND T331.seq_shisaku    = T131.seq_shisaku ");

			strSql.append(" WHERE ");
			strSql.append("     T331.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T331.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T331.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			
			//【QP@00342】
			strSql.append(" AND T331.no_eda = " + 
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");
			strSql.append(" AND T331.fg_chusi IS NULL ");
			
			
//2010/01/21 ISONO ADD START ｼｻｸ依頼されている試作が採用サンプルNoの対象
			
			strSql.append(" AND T131.flg_shisanIrai = 1"); 
			
//2010/01/21 ISONO ADD END   ｼｻｸ依頼されている試作が採用サンプルNoの対象
			
			strSql.append(" order by T131.sort_shisaku");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strSql;
		
	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * 原価試算共通情報[ kihon ]パラメーター格納
	 *  : 原価試算画面のﾍｯﾀﾞｰ部情報をレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @param lstGenkaHaita  : 【QP@00342】検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
			  List<?> lstGenkaHeader
			,  List<?> lstGenkaHaita
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_shain", getRight("0000000000" + toString(items[0],""),10));
				resTable.addFieldVale(i, "nen", getRight("00" + toString(items[1],""), 2));
				resTable.addFieldVale(i, "no_oi", getRight("000" + toString(items[2],""), 3));
	
				//【QP@00342】
				resTable.addFieldVale(i, "no_eda", getRight("000" + toString(items[3],""), 3));

				resTable.addFieldVale(i, "hin", toString(items[4],""));
				resTable.addFieldVale(i, "kenkyu_tanto", toString(items[5],""));
				resTable.addFieldVale(i, "kenkyu_date", toString(items[6],""));
				resTable.addFieldVale(i, "seihon_tanto", toString(items[7],""));
				resTable.addFieldVale(i, "seihon_date", toString(items[8],""));
				resTable.addFieldVale(i, "genshizai_tanto", toString(items[9],""));
				resTable.addFieldVale(i, "genshizai_date", toString(items[10],""));
				resTable.addFieldVale(i, "kozyo_tanto",toString(items[11],""));
				resTable.addFieldVale(i, "kozyo_date", toString(items[12],""));
				resTable.addFieldVale(i, "saiyo_no", toString(items[13],""));
				resTable.addFieldVale(i, "saiyo_nm", toString(items[23],""));
				resTable.addFieldVale(i, "saiyo_date", toString(items[14],""));
				resTable.addFieldVale(i, "sam_id_koshin", toString(items[15],""));
// ADD start 【H24年度対応】 2012/4/18 hisahori
				resTable.addFieldVale(i, "nm_motHin", toString(items[24],""));				
// ADD end 【H24年度対応】 2012/4/18 hisahori

				//【QP@00342】
				if(lstGenkaHaita.size() == 0){
					resTable.addFieldVale(i, "cd_shisaku_haita", "");
					resTable.addFieldVale(i, "nm_shisaku_haita", "");
					resTable.addFieldVale(i, "nm_kaisha_haita","");
					resTable.addFieldVale(i, "nm_busho_haita", "");
					resTable.addFieldVale(i, "nm_user_haita", "");
					resTable.addFieldVale(i, "kengen_moto", "");
				}
				else{
					Object[] items2 = (Object[]) lstGenkaHaita.get(0);
					String cd_shisaku_haita = getRight("0000000000" + toString(items2[0],""),10);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("00" + toString(items2[1],""), 2);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("000" + toString(items2[2],""), 3);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("000" + toString(items2[3],""), 3);
					resTable.addFieldVale(i, "cd_shisaku_haita", cd_shisaku_haita);
					resTable.addFieldVale(i, "nm_shisaku_haita", toString(items2[4]));
					
					
					//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 START -----------------------------------															
					//DB取得値を設定
					resTable.addFieldVale(i, "nm_kaisha_haita", toString(items2[5],""));
					resTable.addFieldVale(i, "nm_busho_haita", toString(items2[6],""));
					resTable.addFieldVale(i, "nm_user_haita", toString(items2[7],""));
					resTable.addFieldVale(i, "kengen_moto", toString(this.kengen_moto,""));
					//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 END -------------------------------------
				}
				

				
				//【シサクイック（原価）要望】案件No12　ヘルプ表示　TT.Nishigawa 2010/5/12 START -----------------------------------															
				//DB取得値を設定
				resTable.addFieldVale(i, "help_file", toString(ConstManager.getConstValue(Category.設定, "HELPFILE_PATH"),""));
				//【シサクイック（原価）要望】案件No12　ヘルプ表示　TT.Nishigawa 2010/5/12 END -------------------------------------

				//【QP@00412_シサクイック改良　案件№27】 T.Arai 2010.10.01 START
				resTable.addFieldVale(i, "no_irai", toString(items[20],""));
				//【QP@00412_シサクイック改良　案件№27】 T.Arai 2010.10.01 END
				
				
				//【QP@00342】
				resTable.addFieldVale(i, "shurui_eda", toString(items[21],""));
				resTable.addFieldVale(i, "dt_kizitu", toString(items[22],""));
			
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	/**
	 * 原価試算共通情報[ tr_shisan_shisaku ]パラメーター格納
	 *  : 原価試算画面のサンプルNo情報をレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaTr_shisan_shisaku(
			
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "nm_sample",  toString(items[0],""));
				resTable.addFieldVale(i, "seq_shisaku",  toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
