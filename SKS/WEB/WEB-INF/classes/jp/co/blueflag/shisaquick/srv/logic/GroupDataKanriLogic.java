package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * グループマスタメンテ：グループ情報登録、更新、削除DB処理
 *  : グループマスタメンテ：グループ情報を登録、更新、削除する。
 * @author jinbo
 * @since  2009/04/09
 */
public class GroupDataKanriLogic extends LogicBase{
	
	/**
	 * グループマスタメンテ：グループ情報登録、更新、削除DB処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public GroupDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * グループマスタメンテ：グループ情報更新SQL作成
	 *  : グループ情報を更新するSQLを作成。
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
		StringBuffer strSql2 = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			//処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			//SQL文の作成
			if (strShotriKbn.equals("1")) {
				// グループコードの取得
				String strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
				// グループコードの値がない場合、採番処理をする。
				if (strGroupCd.equals("") || strGroupCd == null) {
					strGroupCd = saibanGroupCd();
				}
				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				//登録
				strSql = groupKanriInsertSQL(reqData, strGroupCd);
			} else if (strShotriKbn.equals("2")) {
				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				//更新
				strSql = groupKanriUpdateSQL(reqData);
			} else if (strShotriKbn.equals("3")) {
				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				// グループ削除
				strSql = groupKanriDeleteSQL(reqData);
				// チーム削除と実行
				strSql2 = teamDataKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString());
			}
			
			//SQLを実行
			execDB.execSQL(strSql.toString());
			
			//コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageGroupDataKanri(resKind.getTableItem(0));
			
		} catch (Exception e) {
			if (execDB != null) {
				//ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}
			
			//変数の削除
			strSql = null;
			strSql2 = null;

		}
		return resKind;
		
	}
	
	/**
	 * グループコード採番処理
	 * @return String
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String saibanGroupCd() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		/*** 採番処理 *******start **************************************/
		String strNoSeq = "";

		StringBuffer strSelSql = new StringBuffer();
		StringBuffer strInsSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			// トランザクション開始
			super.createSearchDB();
			searchDB.BeginTran();
			// グループコードの採番SQL作成
			strSelSql.append("SELECT (no_seq + 1) as no_seq");
			strSelSql.append(" FROM ma_saiban WITH(UPDLOCK, ROWLOCK)");
			strSelSql.append(" WHERE key1 = 'グループコード'");
			strSelSql.append(" AND key2 = ''");
			// SQLを実行
			lstRecset = searchDB.dbSearch(strSelSql.toString());
			if (lstRecset.size() > 0) {
				// 採番結果を退避
				Object items = (Object) lstRecset.get(0);
				strNoSeq = items.toString();

				// レコードを更新
				strInsSql.append("UPDATE ma_saiban SET");
				strInsSql.append("  no_seq = ");
				strInsSql.append(strNoSeq);
				strInsSql.append(" ,id_koshin = ");
				strInsSql.append(userInfoData.getId_user());
				strInsSql.append(" ,dt_koshin = GETDATE()");
				strInsSql.append(" WHERE key1 = 'グループコード'");
				strInsSql.append(" AND KEY2 = ''");

			} else {
				strNoSeq = "1";

				// レコードを追加
				strInsSql.append("INSERT INTO ma_saiban (");
				strInsSql.append("  key1");
				strInsSql.append(" ,key2");
				strInsSql.append(" ,no_seq");
				strInsSql.append(" ,id_toroku");
				strInsSql.append(" ,dt_toroku");
				strInsSql.append(" ,id_koshin");
				strInsSql.append(" ,dt_koshin");
				strInsSql.append(" ) VALUES (");
				strInsSql.append("  'グループコード'");
				strInsSql.append(" ,''");
				strInsSql.append(" ," + strNoSeq);
				strInsSql.append(" ," + userInfoData.getId_user());
				strInsSql.append(" ,GETDATE()");
				strInsSql.append(" ," + userInfoData.getId_user());
				strInsSql.append(" ,GETDATE()");
				strInsSql.append(")");
			}
			// SQLを実行
			super.createExecDB();
			execDB.setSession(searchDB.getSession());
			execDB.execSQL(strInsSql.toString());

			// コミット
			searchDB.Commit();

		} catch (Exception e) {
			if (searchDB != null) {
				// ロールバック
				searchDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSelSql = null;
			strInsSql = null;
		}
		/*** 採番処理 *******end **************************************/
		return strNoSeq;
	}
	
	/**
	 * グループ登録SQL作成
	 *  : グループを登録するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriInsertSQL(RequestResponsKindBean requestData, String strGroupCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// グループ名の取得
			String strGroupName = requestData.getFieldVale(0, 0, "nm_group");

			// SQL文の作成
			strSql.append("INSERT INTO ma_group (");
			strSql.append(" cd_group");
			strSql.append(" ,nm_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES (");
			strSql.append(" " + strGroupCd);
			strSql.append(" ,'" + strGroupName + "'");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * グループ更新SQL作成
	 *  : グループを更新するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriUpdateSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			String strGroupCd = null;
			String strGroupName = null;

			//グループコードの取得
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			//グループ名の取得
			strGroupName = requestData.getFieldVale(0, 0, "nm_group");

			//SQL文の作成
			strSql.append("UPDATE ma_group SET");
			strSql.append(" nm_group = '");
			strSql.append(strGroupName + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * グループ削除SQL作成
	 *  : グループを削除するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			String strGroupCd = null;

			//グループコードの取得
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_group");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * チーム削除SQL作成
	 *  : チームを削除するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer teamDataKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {

			// グループCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			// SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_team");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * グループマスタメンテ：グループパラメーター格納
	 *  : グループ更新結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}

	}
	
}
