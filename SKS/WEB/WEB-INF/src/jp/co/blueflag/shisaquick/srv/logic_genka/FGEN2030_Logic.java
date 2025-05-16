package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【QP@00342】
 * ステータスクリア　ステータスクリア実行
 *  : ステータスクリアを実行する。
 *  機能ID：FGEN2030
 *  
 * @author Nishigawa
 * @since  2011/01/25
 */
public class FGEN2030_Logic extends LogicBase{
	
	/**
	 * ステータスクリア　現在ステータス取得処理
	 * : インスタンス生成
	 */
	public FGEN2030_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * ステータスクリア　現在ステータス取得
	 *  : 現在ステータス情報を取得する。
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
			
			//レスポンスデータの形成
			this.genkaKihonSetting(resKind, reqData);
			
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
	 * レスポンスデータの形成
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
		
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			
			//テーブル名
			String strTblNm = "kihon";	
			
			//データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//DBコネクション生成
			super.createExecDB();		
			//トランザクション開始
			this.searchDB.BeginTran();
			//トランザクションをexecDBと共有する。
			execDB.setSession(searchDB.getSession());
			
			//DB更新
			//SQL生成（ステータス履歴ﾃｰﾌﾞﾙ更新）
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_StatusRireki(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//SQL生成（原価試算ステータスﾃｰﾌﾞﾙ更新）
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_StatusGenka(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//SQL生成（試算　試作品テーブル更新）（採用ｻﾝﾌﾟﾙNO初期化）
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_sisanShisakuhin(reqData,lstRecset);
			this.execDB.execSQL(strSqlBuf.toString());
			
			//【H24年度対応】2012/05/29 TT H.SHIMA ADD Start
			//SQL生成（試算　試作テーブル更新）（試算日初期化）
			strSqlBuf = null;
			strSqlBuf = makeExecSQL_sisanShisaku(reqData,lstRecset);
			if(strSqlBuf != null && strSqlBuf.length() > 0){
				this.execDB.execSQL(strSqlBuf.toString());
			}
			//【H24年度対応】2012/05/29 TT H.SHIMA ADD Start
			
			//DBコミット
			this.searchDB.Commit();
			
			//③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//④追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			//DBロールバック
			this.searchDB.Rollback();
			this.em.ThrowException(e, "ステータスクリア　クリア実行処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
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
	 * データ取得SQL作成
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
			//リクエストからデータ抽出
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			//SQL文の作成
			strSql.append(" SELECT  ");
			strSql.append("   st_kenkyu  ");
			strSql.append("   ,st_seisan  ");
			strSql.append("   ,st_gensizai  ");
			strSql.append("   ,st_kojo  ");
			strSql.append("   ,st_eigyo  ");
			strSql.append(" FROM ma_status_clear  ");
			strSql.append(" WHERE cd_clear='" + cd_clear + "'  ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * ステータス履歴ﾃｰﾌﾞﾙ更新
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_StatusRireki(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			//リクエストからデータ抽出
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			//レコードセットからデータ抽出
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kenkyu = toString(items[0],"");
			String st_seikan = toString(items[1],"");
			String st_gentyo = toString(items[2],"");
			String st_kojo = toString(items[3],"");
			String st_eigyo = toString(items[4],"");
			
			//部署業務　リテラルCD設定
			String cd_li = "";
			if(cd_clear.equals("1")){
				cd_li = "90";
			}
			else if(cd_clear.equals("2")){
				cd_li = "91";
			}
			else if(cd_clear.equals("3")){
				cd_li = "92";
			}
			else if(cd_clear.equals("4")){
				cd_li = "93";
			}
			else if(cd_clear.equals("5")){
				cd_li = "94";
			}
			else if(cd_clear.equals("6")){
				cd_li = "95";
			}
			else if(cd_clear.equals("7")){
				cd_li = "98";
			}
			else if(cd_clear.equals("8")){
				cd_li = "99";
			}
			else if(cd_clear.equals("9")){
				cd_li = "96";
			}
			else if(cd_clear.equals("10")){
				cd_li = "97";
			}
			
			//SQL文の作成
			strSql.append(" INSERT INTO tr_shisan_status_rireki  ");
			strSql.append("            (cd_shain  ");
			strSql.append("            ,nen  ");
			strSql.append("            ,no_oi  ");
			strSql.append("            ,no_eda  ");
			strSql.append("            ,dt_henkou  ");
			strSql.append("            ,cd_kaisha  ");
			strSql.append("            ,cd_busho  ");
			strSql.append("            ,id_henkou  ");
			strSql.append("            ,cd_zikko_ca  ");
			strSql.append("            ,cd_zikko_li  ");
			strSql.append("            ,st_kenkyu  ");
			strSql.append("            ,st_seisan  ");
			strSql.append("            ,st_gensizai  ");
			strSql.append("            ,st_kojo  ");
			strSql.append("            ,st_eigyo  ");
			strSql.append("            ,id_toroku  ");
			strSql.append("            ,dt_toroku)  ");
			strSql.append("      VALUES  ");
			strSql.append("            (" + strCdShain);
			strSql.append("            ," + strNen);
			strSql.append("            ," + strNoOi);
			strSql.append("            ," + strNoEda);
			strSql.append("            ,GETDATE() ");
			strSql.append("            ,"  + userInfoData.getCd_kaisha());
			strSql.append("            ,"  + userInfoData.getCd_busho());
			strSql.append("            ,"  + userInfoData.getId_user());
			strSql.append("            ,'wk_seisan' ");
			strSql.append("            ," + cd_li);
			strSql.append("            ," + st_kenkyu);
			strSql.append("            ," + st_seikan);
			strSql.append("            ," + st_gentyo);
			strSql.append("            ," + st_kojo);
			strSql.append("            ," + st_eigyo);
			strSql.append("            ," + userInfoData.getId_user());
			strSql.append("            ,GETDATE() )  ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * 原価試算ステータスﾃｰﾌﾞﾙ更新
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_StatusGenka(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			//リクエストからデータ抽出
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			
			//レコードセットからデータ抽出
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kenkyu = toString(items[0],"");
			String st_seikan = toString(items[1],"");
			String st_gentyo = toString(items[2],"");
			String st_kojo = toString(items[3],"");
			String st_eigyo = toString(items[4],"");
			
			//SQL文の作成
			strSql.append(" UPDATE tr_shisan_status  ");
			strSql.append(" SET   ");
			strSql.append("        st_kenkyu = " + st_kenkyu);
			strSql.append("       ,st_seisan = " + st_seikan);
			strSql.append("       ,st_gensizai = " + st_gentyo);
			strSql.append("       ,st_kojo = " + st_kojo);
			strSql.append("       ,st_eigyo = " + st_eigyo);
			strSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strSql.append("       ,dt_koshin = GETDATE()  ");
			strSql.append(" WHERE   ");
			strSql.append("       cd_shain = " + strCdShain );
			strSql.append("       AND nen = " + strNen );
			strSql.append("       AND no_oi = " + strNoOi );
			strSql.append("       AND no_eda = " + strNoEda );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * 試算　試作品ﾃｰﾌﾞﾙ更新
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_sisanShisakuhin(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			//リクエストからデータ抽出
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			String cd_clear = reqData.getFieldVale(0, 0, "cd_clear");
			
			
			//SQL文の作成
			strSql.append(" UPDATE tr_shisan_shisakuhin  ");
			strSql.append(" SET   ");
			strSql.append("        saiyo_sample = NULL");
			
			//営業ステータスが１になるものは試算期日を初期化
			if(cd_clear.equals("1") || cd_clear.equals("2")){
				strSql.append("       ,dt_kizitu = NULL");
			}
			
			strSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strSql.append("       ,dt_koshin = GETDATE()  ");
			strSql.append(" WHERE   ");
			strSql.append("       cd_shain = " + strCdShain );
			strSql.append("       AND nen = " + strNen );
			strSql.append("       AND no_oi = " + strNoOi );
			strSql.append("       AND no_eda = " + strNoEda );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
//【H24年度対応】2012/05/29 TT H.SHIMA ADD Start
	/**
	 * 試算　試作ﾃｰﾌﾞﾙ更新
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer makeExecSQL_sisanShisaku(
			RequestResponsKindBean reqData,
			List<?>  lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {

			//リクエストからデータ抽出
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			Object[] items = (Object[]) lstRecset.get(0);
			String st_kojo = toString(items[3],"");
// add start 20120620 hisahori
			String no_clearcheck = reqData.getFieldVale(0, 0, "no_clearcheck");
			String arrClearNo[] = no_clearcheck.split("chk");
			int cntClearNo = arrClearNo.length;
// add end 20120620 hisahori
				if(st_kojo.equals("1")){
					//SQL文の作成
	// mod start 20120620 hisahori
	//				strSql.append("UPDATE tr_shisan_shisaku ");
	//				strSql.append("SET ");
	//				strSql.append("       dt_shisan = NULL ");
	//				strSql.append("WHERE ");
	//				strSql.append("       cd_shain = " + strCdShain );
	//				strSql.append("       AND nen = " + strNen );
	//				strSql.append("       AND no_oi = " + strNoOi );
	//				strSql.append("       AND no_eda = " + strNoEda );
	//				strSql.append("       AND seq_shisaku BETWEEN 1 AND ");
	//				strSql.append("              (SELECT COUNT(*) ");
	//				strSql.append("               FROM tr_shisan_shisaku ");
	//				strSql.append("               WHERE");
	//				strSql.append("                      cd_shain = " + strCdShain );
	//				strSql.append("                      AND nen = " + strNen );
	//				strSql.append("                      AND no_oi = " + strNoOi );
	//				strSql.append("                      AND no_eda = " + strNoEda + ")");
					for (int i = 0; i < cntClearNo; i++) {
						strSql.append("UPDATE tr_shisan_shisaku ");
						strSql.append("SET ");
						strSql.append("       dt_shisan = NULL ");
						strSql.append("WHERE ");
						strSql.append("       cd_shain = " + strCdShain );
						strSql.append("       AND nen = " + strNen );
						strSql.append("       AND no_oi = " + strNoOi );
						strSql.append("       AND no_eda = " + strNoEda );
						if (arrClearNo[i] == ""){
							arrClearNo[i] = "''";
						}
						strSql.append("       AND seq_shisaku = " + arrClearNo[i] );
						strSql.append(" ; ");
					}
	// mod end 20120620 hisahori
				}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
//【H24年度対応】2012/05/29 TT H.SHIMA ADD End
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
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
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
