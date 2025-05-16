package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 　担当者データ管理DB：担当者データを管理（登録・更新・削除）するSQLを作成する。
 * M101_user　・　M107_tantokaisyaテーブルから、データの抽出を行う。
 * 取得情報を、レスポンスデータ保持「機能ID：SA270O」に設定する。																
 * 
 * @author itou
 * @since 2009/04/13
 */
public class TantoushaDataKanriLogic extends LogicBase {

	/**
	 * 担当者データ管理コンストラクタ ： インスタンス生成
	 */
	public TantoushaDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者データ操作管理 ： 担当者データの操作管理を行う。
	 * 
	 * @param reqData
	 *            : リクエストデータ
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
		StringBuffer strSql3 = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			// 機能リクエストデータより、処理区分を抽出し、各処理メソッドを呼出す。
			if (strShotriKbn.equals("1")) { // 処理区分：登録

				// 担当者データ登録SQL作成処理を呼出す。
				strSql = tantoushaKanriInsertSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				if (!reqData.getFieldVale("ma_tantokaisya", 0, "cd_tantokaisha").equals("")) {
					// 製造担当者データ登録SQL作成処理を呼出す。
					String strUserId = reqData.getFieldVale("ma_tantokaisya", 0, "id_user");
					int intRecCount = reqData.getCntRow("ma_tantokaisya");
					for (int i = 0; i < intRecCount; i++) {
						strSql2 = seizouTantoushaKanriInsertSQL(strUserId,reqData.getFieldVale("ma_tantokaisya", i, "cd_tantokaisha"));
						execDB.execSQL(strSql2.toString()); // SQLを実行
					}
				}
			} else if (strShotriKbn.equals("2")) { // 処理区分：更新
				
				//ADD 2013/9/25 okano【QP@30151】No.28 start
				String strUKinoId = userInfoData.getId_kino().get(0).toString();
				if(strUKinoId.equals("23")){
					String strPassId = reqData.getFieldVale( 0, 0, "id_user");
					StringBuffer strSqlPass = new StringBuffer();
					strSqlPass.append("SELECT password FROM ma_user ");
					strSqlPass.append("WHERE id_user=");
					strSqlPass.append(strPassId);
					
					createSearchDB();
					List<?> lstRecset = searchDB.dbSearch(strSqlPass.toString());
					
					if(lstRecset.get(0).toString().equals(reqData.getFieldVale( 0, 0, "password"))){
						em.ThrowException(ExceptionKind.一般Exception, "E000228", "パスワード", "", "");
					}
					//リストの破棄
					removeList(lstRecset);
					if (searchDB != null) {
						//セッションのクローズ
						searchDB.Close();
						searchDB = null;
					}
					strUKinoId = null;
					strPassId = null;
					strSqlPass = null;
				}
				//ADD 2013/9/25 okano【QP@30151】No.28 end
				
				// 担当者データ更新SQL作成処理を呼出す。
				strSql = tantoushaKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
				
				// 製造担当者データ削除SQL作成処理を呼出す。
				strSql2 = seizouTantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQLを実行
				if (!reqData.getFieldVale("ma_tantokaisya", 0, "cd_tantokaisha").equals("")) {
					// 製造担当者データ登録SQL作成処理を呼出す。
					String strUserId = reqData.getFieldVale("ma_tantokaisya", 0, "id_user");
					int intRecCount = reqData.getCntRow("ma_tantokaisya");
					for (int i = 0; i < intRecCount; i++) {	
						strSql3 = seizouTantoushaKanriInsertSQL(strUserId,reqData.getFieldVale("ma_tantokaisya", i, "cd_tantokaisha"));
						execDB.execSQL(strSql3.toString()); // SQLを実行
					}
				}
				
			} else if (strShotriKbn.equals("3")) { // 処理区分：削除
				
				// 担当者データ削除SQL作成処理を呼出す。
				strSql = tantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
				
				// 製造担当者データ削除SQL作成処理を呼出す。
				strSql2 = seizouTantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQLを実行
				
			}

			// コミット
			execDB.Commit();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageGroupDataKanri(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				// セッションのクローズ
				execDB.Close();
				execDB = null;
			}
			
			//変数の削除
			strSql = null;
			strSql2 = null;
			strSql3 = null;
		}
		return resKind;
	}

	/**
	 * 担当者データ登録SQL作成
	 *  : 担当者データ登録用　SQLを作成し、各データをDBに登録する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriInsertSQL(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {
			
			// ユーザID
			String strUserId = "";
			// パスワード
			String strPassword = "";
			// 権限
			String strKengenCd = "";
			// 氏名
			String strNmUser = "";
			// 所属会社
			String strKaishaCd = "";
			// 所属部署
			String strBushoCd = "";
			// 所属グループ
			String strGroupCd = "";
			// 所属チーム
			String strTeamCd = "";
			// 役職
			String strYakushokuCd = "";
			
			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			strPassword = requestData.getFieldVale("ma_user", 0, "password");
			strKengenCd = requestData.getFieldVale("ma_user", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("ma_user", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("ma_user", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("ma_user", 0, "cd_busho");
			strGroupCd = requestData.getFieldVale("ma_user", 0, "cd_group");
			strTeamCd = requestData.getFieldVale("ma_user", 0, "cd_team");
			strYakushokuCd = requestData.getFieldVale("ma_user", 0, "cd_yakushoku");
			
			// SQL文の作成
			//レコードを追加
			strInsSql.append("INSERT INTO ma_user (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,password");
			strInsSql.append(" ,cd_kengen");
			strInsSql.append(" ,nm_user");
			strInsSql.append(" ,cd_kaisha");
			strInsSql.append(" ,cd_busho");
			strInsSql.append(" ,cd_group");
			strInsSql.append(" ,cd_team");
			strInsSql.append(" ,cd_yakushoku");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strInsSql.append(" ,dt_password");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			strInsSql.append(" ) VALUES (");
			strInsSql.append(strUserId);
			strInsSql.append(" ,'" + strPassword + "'");
			strInsSql.append(" ," + strKengenCd);
			strInsSql.append(" ,'" + strNmUser + "'");
			strInsSql.append(" ," + strKaishaCd);
			strInsSql.append(" ," + strBushoCd);
			strInsSql.append(" ," + strGroupCd);
			strInsSql.append(" ," + strTeamCd);
			strInsSql.append(" ,'" + strYakushokuCd + "'");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 担当者データ更新SQL作成
	 *  : 担当者データ更新用　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriUpdateSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";
			// パスワード
			String strPassword = "";
			// 権限
			String strKengenCd = "";
			// 氏名
			String strNmUser = "";
			// 所属会社
			String strKaishaCd = "";
			// 所属部署
			String strBushoCd = "";
			// 所属グループ
			String strGroupCd = "";
			// 所属チーム
			String strTeamCd = "";
			// 役職
			String strYakushokuCd = "";

			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			strPassword = requestData.getFieldVale("ma_user", 0, "password");
			strKengenCd = requestData.getFieldVale("ma_user", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("ma_user", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("ma_user", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("ma_user", 0, "cd_busho");
			strGroupCd = requestData.getFieldVale("ma_user", 0, "cd_group");
			strTeamCd = requestData.getFieldVale("ma_user", 0, "cd_team");
			strYakushokuCd = requestData.getFieldVale("ma_user", 0, "cd_yakushoku");

			//SQL文の作成
			strSql.append("UPDATE ma_user SET");
			strSql.append(" password = '");
			strSql.append(strPassword + "'");
			strSql.append(" ,cd_kengen = ");
			strSql.append(strKengenCd);
			strSql.append(" ,nm_user = '");
			strSql.append(strNmUser + "'");
			strSql.append(" ,cd_kaisha = ");
			strSql.append(strKaishaCd);
			strSql.append(" ,cd_busho = ");
			strSql.append(strBushoCd);
			strSql.append(" ,cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" ,cd_team = ");
			strSql.append(strTeamCd);
			strSql.append(" ,cd_yakushoku = '");
			strSql.append(strYakushokuCd + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			//MOD 2016/7/14 shima start
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strSql.append(" ,dt_password = GETDATE() ");
//			strSql.append(" ,dt_password = CASE ");
//			strSql.append(" WHEN password = '");
//			strSql.append(strPassword);
//			strSql.append("' THEN dt_password ");
//			strSql.append(" ELSE GETDATE() END");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			//MOD 2016/7/14 shima end
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 担当者データ削除SQL作成
	 *  : 担当者データ削除用　SQLを作成し、各データをDBに削除する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";
			
			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			
			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * 製造担当者データ登録SQL作成
	 *  : 製造担当者データ登録用　SQLを作成し、各データをDBに登録する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantoushaKanriInsertSQL(String strUserId,String strTantokaishaCd) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strInsSql = new StringBuffer();
		try {
			
			// SQL文の作成
			//レコードを追加
			strInsSql.append("INSERT INTO ma_tantokaisya (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,cd_tantokaisha");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			strInsSql.append(" ) VALUES (");
			strInsSql.append(strUserId);
			strInsSql.append(" ," + strTantokaishaCd);
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 製造担当者データ削除SQL作成
	 *  : 製造担当者データ削除用　SQLを作成し、各データをDBに削除する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantoushaKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";
			
			strUserId = requestData.getFieldVale("ma_tantokaisya", 0, "id_user");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_tantokaisya");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * 管理結果パラメーター格納
	 *  : 担当者データの管理結果情報をレスポンスデータへ格納する。
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
