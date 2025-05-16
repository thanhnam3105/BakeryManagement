package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 権限データ管理DB処理 : 権限データを管理（登録・更新・削除）するSQLを作成する。
 * M102_Kengen　・　M103_kinouテーブルデータの管理を行う。 取得情報を、レスポンスデータ保持「機能ID：SA340O」に設定する。
 * 
 * @author itou
 * @since 2009/04/16
 */
public class KengenDataKanriLogic extends LogicBase {

	/**
	 * 権限データ管理コンストラクタ
	 */
	public KengenDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 権限データ操作管理 ： 権限データの操作管理を行う。
	 * 
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
		StringBuffer strSql3 = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale("ma_kengen", 0, "kbn_shori");

			
			// 機能リクエストデータより、処理区分を抽出し、各処理メソッドを呼出す。
			if (strShotriKbn.equals("1")) { // 処理区分：登録
				
				// 権限CD
				String strKengenCd = reqData.getFieldVale("ma_kengen", 0, "cd_kengen");
				// 権限CDの値がない場合、採番処理をする。
				if (strKengenCd.equals("") || strKengenCd == null) {
					strKengenCd = saibanKengenCd();
				}
				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				// 権限データ登録SQL作成処理を呼出す。
				strSql = kengenKanriInsertSQL(reqData, strKengenCd);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// 機能マスタ登録用SQL作成処理を呼出す。
				int intRecCount = reqData.getCntRow("ma_kinou");
				for (int i = 0; i < intRecCount; i++) {
					strSql2 = tantouKanriInsertSQL(reqData, i, strKengenCd);
					execDB.execSQL(strSql2.toString()); // SQLを実行
				}
			} else if (strShotriKbn.equals("2")) { // 処理区分：更新

				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				// 権限データ更新SQL作成処理を呼出す。
				strSql = kengenKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// 機能マスタ削除SQL作成処理を呼出す。
				strSql2 = tantouKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQLを実行
				// 機能マスタ登録SQL作成処理を呼出す。
				int intRecCount = reqData.getCntRow("ma_kinou");
				for (int i = 0; i < intRecCount; i++) {
					strSql3 = tantouKanriInsertSQL(reqData, i, reqData.getFieldVale("ma_kengen", 0, "cd_kengen"));
					execDB.execSQL(strSql3.toString()); // SQLを実行
				}

			} else if (strShotriKbn.equals("3")) { // 処理区分：削除

				// トランザクション開始
				super.createExecDB();
				execDB.BeginTran();
				// 担当者データ削除SQL作成処理を呼出す。
				strSql = kengenKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// 製造担当者データ削除SQL作成処理を呼出す。
				strSql2 = tantouKanriDeleteSQL(reqData);
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
			storageGroupDataKanri(resKind.getTableItem(0));

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
	 * 権限コード採番処理
	 * @return String
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String saibanKengenCd() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
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
			strSelSql.append(" WHERE key1 = '権限コード'");
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
				strInsSql.append(" WHERE key1 = '権限コード'");
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
				strInsSql.append("  '権限コード'");
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
	 * 権限データ登録SQL作成 : 権限データ登録用　SQLを作成し、各データをDBに登録する。
	 * 
	 * @param reqData ： リクエストデータ
	 * @param strSql ： 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriInsertSQL(RequestResponsKindBean requestData, String strKengenCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// 新規発行採番された、チームコード登録用SQLを作成する。
		StringBuffer strSql = new StringBuffer();
		try {
			// 権限名
			String strKengenNm = requestData.getFieldVale("ma_kengen", 0, "nm_kengen");
			
			// 権限データ登録用SQL作成
			strSql.append("INSERT INTO ma_kengen (");
			strSql.append(" cd_kengen");
			strSql.append(" ,nm_kengen");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( ");
			strSql.append(strKengenCd);
			strSql.append(" ,'" + strKengenNm + "'");
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
	 * 権限データ更新SQL作成 : 権限データ更新用　SQLを作成し、各データをDBに更新する。
	 * 
	 * @param reqData ： リクエストデータ
	 * @param strSql ： 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriUpdateSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// 権限CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			// 権限名
			String strKengenNm = requestData.getFieldVale("ma_kengen", 0, "nm_kengen");

			// SQL文の作成
			strSql.append("UPDATE ma_kengen SET");
			strSql.append(" nm_kengen = '");
			strSql.append(strKengenNm + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 権限データ削除SQL作成 : 権限データ削除用　SQLを作成し、各データをDBに削除する。
	 * 
	 * @param reqData ： リクエストデータ
	 * @param strSql ： 検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		try {
			// 権限CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			
			// SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_kengen");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 機能マスタ登録SQL作成 : 機能データ登録用　SQLを作成し、各データをDBに登録する。
	 * 
	 * @param reqData ： リクエストデータ
	 * @param strKengenCd ： 権限コード
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantouKanriInsertSQL(RequestResponsKindBean reqData, int i, String strKengenCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// 画面ID
			String strGamenId = reqData.getFieldVale("ma_kinou", i, "id_gamen");
			// 機能ID
			String strKinoNm = reqData.getFieldVale("ma_kinou", i, "nm_kino");
			// ﾃﾞｰﾀID
			String strKinoId = reqData.getFieldVale("ma_kinou", i, "id_kino");
			// 名称
			String strDataId = reqData.getFieldVale("ma_kinou", i, "id_data");
			// 備考
			String strBiko = reqData.getFieldVale("ma_kinou", i, "biko");
			// 機能マスタ登録用SQL作成
			strSql.append("INSERT INTO ma_kinou (");
			strSql.append(" cd_kengen");
			strSql.append(" ,id_gamen");
			strSql.append(" ,id_kino");
			strSql.append(" ,id_data");
			strSql.append(" ,nm_kino");
			strSql.append(" ,biko");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( ");
			strSql.append(strKengenCd);
			strSql.append(" ," + strGamenId);
			strSql.append(" ," + strKinoId);
			strSql.append(" ," + strDataId);
			strSql.append(" ,'" + strKinoNm + "'");
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");
			} else {
				strSql.append(" ,NULL");
			}
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
	 * 機能マスタ削除SQL作成 : 機能マスタ削除用　SQLを作成し、各データをDBに削除する。
	 * 
	 * @param reqData ： リクエストデータ
	 * @param strKengenCd ： 権限コード
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantouKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		try {
			// 権限CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			// SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_kinou");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * 管理結果パラメーター格納 : 担当者データの管理結果情報をレスポンスデータへ格納する。
	 * 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			// 処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}