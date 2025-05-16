package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * JWS-試作テーブル管理DB処理
 *  : JWS-試作テーブル管理DB処理のＤＢに対する業務ロジックの実装
 *  
 * @author TT.k-katayama
 * @since  2009/04/15
 *
 */
public class ShisakuTblKanriDataChekck extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public ShisakuTblKanriDataChekck() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 試作テーブル管理操作
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
		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①:トランザクションを開始する
			super.createExecDB();								//DB更新の生成
			this.execDB.BeginTran();							//トランザクション開始			
			
			try {

				//②:T110 試作品テーブル(tr_shisakuhin)の削除・登録処理を行う
				this.shisakuhinKanriDeleteSQL(reqData);
				this.shisakuhinKanriInsertSQL(reqData);
				//③:T120 配合テーブル(tr_haigo)の削除・登録処理を行う
				this.haigoKanriDeleteSQL(reqData);
				this.haigoKanriInsertSQL(reqData);
				//④:T131 試作テーブル(tr_shisaku)の削除・登録処理を行う
				this.shisakuKanriDeleteSQL(reqData);
				this.shisakuKanriInsertSQL(reqData);
				//⑤:T132 試作リストテーブル(tr_shisaku_list)の削除・登録処理を行う
				this.shisakuListKanriDeleteSQL(reqData);
				this.shisakuListKanriInsertSQL(reqData);
				//⑥:T133 製造工程テーブル(tr_cyuui)の削除・登録処理を行う
				this.seizoKouteiKanriDeleteSQL(reqData);
				this.seizoKouteiKanriInsertSQL(reqData);
				
//				//⑦:T140 原価資材テーブル(tr_shizai)の削除・登録処理を行う(二次分)
//				this.genkaShizaiKanriDeleteSQL();
//				this.genkaShizaiKanriInsertSQL();
//				//⑧:T141 原価原料テーブル(tr_genryo)の削除・登録処理を行う(二次分)
//				this.genkaGenryoKanriDeleteSQL();
//				this.genkaGenryoKanriInsertSQL();

				//⑨:コミット/ロールバック処理を実行する
				this.execDB.Commit();							//コミット
				
			} catch(Exception e) {
				this.execDB.Rollback();							//ロールバック
				this.em.ThrowException(e, "");
				
			} finally {
				
			}
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//⑩：正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			this.storageKengenDataKanri(resKind.getTableItem(strTableNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作テーブル管理操作処理が失敗しました。");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
				
			}
			
		}
		return resKind;

	}

	/**
	 * 試作品データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、試作品データ削除用SQLを作成する。
			
			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");
			
			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisakuhin ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
						
			//②：共通クラス　データベース管理を用い、試作品データの削除を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ削除SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
	}
	/**
	 * 試作品データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、試作品データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisakuhin", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "no_oi") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "no_irai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "nm_hin") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kojo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_shubetu") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "no_shubetu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_group")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_team")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ikatu") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_genre") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_user") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "tokuchogenryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youto") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_kakaku") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_eigyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_hoho") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_juten") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "hoho_sakin") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youki") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "yoryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_tani") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "su_iri") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "shomikikan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "genka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "baika") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "buturyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_hatubai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_nisugata") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "keta_shosu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haishi")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haita")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "seq_shisaku")) );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_koshin") + "'" );

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisakuhin ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
						
			//②：共通クラス　データベース管理を用い、試作品データの登録を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ登録SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * 配合データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、配合データ削除用SQLを作成する。
			
			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_haigo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_haigo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_haigo", 0, "no_oi");
			
			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_haigo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//②：共通クラス　データベース管理を用い、配合データの削除を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ削除SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
	}
	/**
	 * 配合データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、配合データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_kotei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "zoku_kotei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_kotei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_genryo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_busho")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_genryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "tanka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "budomari") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_abura") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sakusan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_shokuen") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sousan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_haigo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//②：共通クラス　データベース管理を用い、配合データの登録を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ登録SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * 試作データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、試作データ削除用SQLを作成する。
			
			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisaku", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku", 0, "no_oi");
			
			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//②：共通クラス　データベース管理を用い、試作データの削除を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ削除SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
	}
	/**
	 * 試作データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {

			//①：リクエストデータを用い、試作データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "seq_shisaku") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sort_shisaku")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_chui")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nm_sample") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_print")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_auto")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_shisan")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "toudo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_toudo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_nendo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ondo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ondo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ph")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ph")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "hiju")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "suibun_kasei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_suibun_kasei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "alcohol")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_alcohol")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_sakusei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_memo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hyoka") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hyoka")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value1") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free1")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value2") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free2")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value3") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free3")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "dt_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "juryo_shiagari_g")));
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisaku ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//②：共通クラス　データベース管理を用い、試作データの登録を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ登録SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * 試作リストデータ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//①：リクエストデータを用い、試作リストデータ削除用SQLを作成する。
			
			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisaku_list", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku_list", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku_list", 0, "no_oi");
			
			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku_list ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//②：共通クラス　データベース管理を用い、試作リストデータの削除を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ削除SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
		
	}
	/**
	 * 試作リストデータ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {
			
			//①：リクエストデータを用い、試作リストデータ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
				}
				strSQL_values.append(" SELECT ");
				
				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_shisaku") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "quantity")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku_list", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisaku_list ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//②：共通クラス　データベース管理を用い、試作リストデータの登録を行う。
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ登録SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * 製造工程データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
						
			//製造工程のテーブル件数が0件ではない場合、処理を続行する
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//①：リクエストデータを用い、製造工程データ削除用SQLを作成する。
				
				//リクエストデータ取得
				String strReqShainCd = reqData.getFieldVale("tr_cyuui", 0, "cd_shain");
				String strReqNen = reqData.getFieldVale("tr_cyuui", 0, "nen");
				String strReqOiNo = reqData.getFieldVale("tr_cyuui", 0, "no_oi");
				
				//削除用SQL作成
				strSQL.append(" DELETE ");
				strSQL.append("  FROM tr_cyuui ");
				strSQL.append("  WHERE ");
				strSQL.append("   cd_shain= " + strReqShainCd);
				strSQL.append("   AND nen= " + strReqNen);
				strSQL.append("   AND no_oi= " + strReqOiNo);
				strSQL.append("  ");
				
				//②：共通クラス　データベース管理を用い、製造工程データの削除を行う。
				this.execDB.execSQL(strSQL.toString());
				
			}			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ削除SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
		
	}
	/**
	 * 製造工程データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//製造工程のテーブル件数が0件ではない場合、処理を続行する
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {
			
				//①：リクエストデータを用い、製造工程データ登録用SQLを作成する。
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					if ( strSQL_values.length() != 0 ) {
						strSQL_values.append(" UNION ALL ");
					}
					strSQL_values.append(" SELECT ");
					
					//値をSQLに設定していく
					strSQL_values.append(" " + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "nen") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_oi") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "seq_shisaku") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_chui") );
					strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_cyuui", i, "chuijiko") + "'") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_toroku") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_toroku") + "'" );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ,GETDATE()" );
	
				}
	
				//登録用SQL作成
				strSQL.append(" INSERT INTO tr_cyuui ");
				strSQL.append(strSQL_values.toString());
				strSQL.append("");			
				
				//②：共通クラス　データベース管理を用い、製造工程データの登録を行う。
				this.execDB.execSQL(strSQL.toString());
			
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ登録SQL作成処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

//	/**
//	 * 原価資材データ削除SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//①：リクエストデータを用い、原価資材データ削除用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//②：共通クラス　データベース管理を用い、原価資材データの削除を行う。
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価資材データ削除SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}
//	/**
//	 * 原価資材データ登録SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//①：リクエストデータを用い、原価資材データ登録用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//②：共通クラス　データベース管理を用い、原価資材データの登録を行う。
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価資材データ登録SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}

//	/**
//	 * 原価原料データ削除SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaGenryoKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//①：リクエストデータを用い、原価原料データ削除用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//②：共通クラス　データベース管理を用い、原価原料データの削除を行う。
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価原料データ削除SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}
//	/**
//	 * 原価原料データ登録SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaGenryoKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//①：リクエストデータを用い、原価原料データ登録用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//②：共通クラス　データベース管理を用い、原価原料データの登録を行う。
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価原料データ登録SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}
	
	/**
	 * 管理結果パラメーター格納
	 *  : 試作テーブルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageKengenDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {			
			//①：レスポンスデータを形成する。

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "管理結果パラメーター格納処理が失敗しました。");
			
		} finally {

		}
		
	}
	
	/**
	 * Nullチェック
	 * @param strValue ： チェック値
	 * @return 結果(値が空の場合、NULLを返す)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String checkNull(String strChkValue) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetValue = strChkValue;
		
		try {
			if ( strRetValue.equals("") ) {
				strRetValue = "NULL";
			} else if ( strRetValue.equals("''")) {
				strRetValue = "NULL";
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "Nullチェック処理が失敗しました。");
		} finally {
		}
		
		return strRetValue; 
	}

}
