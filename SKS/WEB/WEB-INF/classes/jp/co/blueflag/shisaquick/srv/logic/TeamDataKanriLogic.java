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
 * チームデータを管理（登録・更新・削除）するSQLを作成する。
 * M106_teamテーブルから、データの抽出を行う。
 * 取得情報を、レスポンスデータ保持「機能ID：SA090O」に設定する。
 * 
 * @author itou
 * @since 2009/04/16
 */
public class TeamDataKanriLogic extends LogicBase {

	/**
	 * チームデータ管理コンストラクタ ： チームデータの操作管理を行う。
	 */
	public TeamDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * チームデータ操作管理 ： チームデータの操作管理を行う。
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

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			// SQL文の作成
			if (strShotriKbn.equals("1")) {
				// 処理区分：登録　チームデータ登録SQL作成処理を呼出す。
				strSql = teamKanriInsertSQL(reqData);
			} else if (strShotriKbn.equals("2")) {
				// 処理区分：更新　チームデータ更新SQL作成処理を呼出す。
				strSql = teamKanriUpdateSQL(reqData, strSql);
			} else if (strShotriKbn.equals("3")) {
				// 処理区分：削除　チームデータ削除SQL作成処理を呼出す。
				strSql = teamKanriDeleteSQL(reqData, strSql);
			}

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			// SQLを実行
			execDB.execSQL(strSql.toString());

			// コミット
			execDB.Commit();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageLiteralDataKanri(resKind.getTableItem(strTableNm));

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
		}
		return resKind;
	}

	/**
	 * チームデータ登録SQL作成 : チームデータ登録用　SQLを作成し、各データをDBに登録する。
	 * 
	 * @param reqData
	 *            ：リクエストデータ
	 * @param strSql
	 *            ：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriInsertSQL(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {

			// グループCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// チームCD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");
			// チーム名
			String strTeamNm = requestData.getFieldVale(0, 0, "nm_team");

			// リテラルデータ登録SQL作成
			strSql.append("INSERT INTO ma_team (");
			strSql.append(" cd_group");
			strSql.append(" ,cd_team");
			strSql.append(" ,nm_team");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( '");
			strSql.append(strGroupCd + "'");
			if (!(strTeamCd.equals("") || strTeamCd == null)) {
				// 取得したチームコードを用い、チームデータ登録用SQLを作成する。
				strSql.append(" ,'" + strTeamCd + "'");
			} else {
				// 新規発行採番された、チームコード登録用SQLを作成する。
				/*** 採番処理 *******start **************************************/
				String strNoSeq = "";

				StringBuffer strSelSql = new StringBuffer();
				StringBuffer strInsSql = new StringBuffer();
				List<?> lstRecset = null;
				try {
					// トランザクション開始
					super.createSearchDB();
					searchDB.BeginTran();
					// リテラルコードの採番SQL作成
					strSelSql.append("SELECT (no_seq + 1) as no_seq");
					strSelSql.append(" FROM ma_saiban WITH(UPDLOCK, ROWLOCK)");
					strSelSql.append(" WHERE key1 = 'チームコード'");
					strSelSql.append(" AND key2 = '");
					strSelSql.append(strGroupCd + "'");
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
						strInsSql.append(" WHERE key1 = 'チームコード'");
						strInsSql.append(" AND key2 = '");
						strInsSql.append(strGroupCd + "'");

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
						strInsSql.append("  'チームコード'");
						strInsSql.append(" ,'" + strGroupCd + "'");
						strInsSql.append(" ," + strNoSeq);
						strInsSql.append(" ," + userInfoData.getId_user());
						strInsSql.append(" ,GETDATE()");
						strInsSql.append(" ," + userInfoData.getId_user());
						strInsSql.append(" ,GETDATE()");
						strInsSql.append(")");
					}
					//SQLを実行
					super.createExecDB();
					execDB.setSession(searchDB.getSession());
					execDB.execSQL(strInsSql.toString());
					
					//コミット
					searchDB.Commit();

					strTeamCd = strNoSeq;
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
				strSql.append(" ," + strTeamCd );
			}
			strSql.append(" ,'" + strTeamNm + "'");
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
	 * チームデータ更新SQL作成 : チームデータ更新用　SQLを作成し、各データをDBに更新する。
	 * 
	 * @param reqData
	 *            ：リクエストデータ
	 * @param strSql
	 *            ：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriUpdateSQL(
			RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// グループCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// チームCD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");
			// チーム名
			String strTeamNm = requestData.getFieldVale(0, 0, "nm_team");
			
			// SQL文の作成
			strSql.append("UPDATE ma_team SET");
			strSql.append(" nm_team = '");
			strSql.append(strTeamNm + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" AND cd_team = ");
			strSql.append(strTeamCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * チームデータ削除SQL作成 : チームデータ削除用　SQLを作成し、各データをDBに削除する。
	 * 
	 * @param reqData
	 *            ：リクエストデータ
	 * @param strSql
	 *            ：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriDeleteSQL(
			RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// グループCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// チームCD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");

			// SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_team");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" AND cd_team = ");
			strSql.append(strTeamCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 管理結果パラメーター格納 : チームデータの管理結果情報をレスポンスデータへ格納する。
	 * 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

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