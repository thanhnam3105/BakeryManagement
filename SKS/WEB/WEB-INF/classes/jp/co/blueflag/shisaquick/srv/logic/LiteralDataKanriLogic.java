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
 * リテラルデータ管理DB処理：リテラルデータを管理（登録・更新・削除）するSQLを作成する。
 * M302_ｌiteralテーブルデータの管理を行う。
 * 取得情報を、レスポンスデータ保持「機能ID：SA330O」に設定する。
 * @author itou
 * @since  2009/04/15
 */
public class LiteralDataKanriLogic extends LogicBase{
	
	/**
	 * リテラルデータ管理コンストラクタ
	 */
	public LiteralDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * リテラルデータ操作管理：リテラルデータの操作管理を行う。
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
				//処理区分：登録　リテラルデータ登録SQL作成処理を呼出す。
				strSql = literalKanriInsertSQL(reqData);
			} else if (strShotriKbn.equals("2")) {
				//処理区分：更新　リテラルデータ更新SQL作成処理を呼出す。
				strSql = literalKanriUpdateSQL(reqData, strSql);
			} else if (strShotriKbn.equals("3")) {
				//処理区分：削除　リテラルデータ削除SQL作成処理を呼出す。
				strSql = literalKanriDeleteSQL(reqData, strSql);
			}
			
			//トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

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
			storageLiteralDataKanri(resKind.getTableItem(0));
			
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
		}
		return resKind;
	}
	
	/**
	 * リテラルデータ登録SQL作成 : リテラルデータ登録用　SQLを作成し、各データをDBに登録する。
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
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			
			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// リテラル名
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// リテラル値1
			String strValue1 = requestData.getFieldVale(0, 0, "value1");
			// リテラル値2
			String strValue2 = requestData.getFieldVale(0, 0, "value2");
			// 表示値
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// 備考
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// 編集可否
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// グループ
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			// リテラルデータ登録SQL作成
			strSql.append("INSERT INTO ma_literal (");
			strSql.append(" cd_category");
			strSql.append(" ,cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,value1");
			strSql.append(" ,value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,cd_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");
			if (!(strLiteralCd.equals("") || strLiteralCd == null)) {
				// 取得したリテラルコードを用い、リテラルデータ登録用SQLを作成する。
				strSql.append(" ,'" + strLiteralCd + "'");
			} else {
				// 新規発行採番された、リテラルコード登録用SQLを作成する。
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
					strSelSql.append(" WHERE key1 = 'リテラルコード'");
					strSelSql.append(" AND key2 = '");
					strSelSql.append(strCategoryCd + "'");
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
						strInsSql.append(" WHERE key1 = 'リテラルコード'");
						strInsSql.append(" AND key2 = '");
						strInsSql.append(strCategoryCd + "'");

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
						strInsSql.append("  'リテラルコード'");
						strInsSql.append(" ,'" + strCategoryCd + "'");
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
					
					strLiteralCd = strNoSeq;
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
				strSql.append(" ,'" + ("000" + strLiteralCd).substring(("000" + strLiteralCd).length() - 3) + "'");
			}
			strSql.append(" ,'" + strLiteralNm + "'");
			if ( !strValue1.equals("") ) {
				strSql.append(" ," + strValue1);
			} else {
				strSql.append(" ,NULL");
			}
			if ( !strValue2.equals("") ) {
				strSql.append(" ," + strValue2);
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);
			if ( !strGroupCd.equals("") ) {
				strSql.append(" ," + strGroupCd);
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
	 * リテラルデータ更新SQL作成
	 *  : リテラルデータ更新用　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer literalKanriUpdateSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// リテラル名
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// リテラル値1
			String strValue1 = requestData.getFieldVale(0, 0, "value1");
			// リテラル値2
			String strValue2 = requestData.getFieldVale(0, 0, "value2");
			// 表示値
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// 備考
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// 編集可否
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// グループ
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL文の作成
			strSql.append("UPDATE ma_literal SET");
			strSql.append("  nm_literal = '");
			strSql.append(strLiteralNm + "'");
			strSql.append(" ,value1 = ");
			if ( !strValue1.equals("") ) {
				strSql.append(strValue1);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,value2 = ");
			if ( !strValue2.equals("") ) {
				strSql.append(strValue2);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,no_sort = ");
			strSql.append(strSortNo);
			strSql.append(" ,biko = ");
			if ( !strBiko.equals("") ) {
				strSql.append("'"+strBiko + "'");
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,flg_edit = ");
			strSql.append(strFlgEdit);
			strSql.append(" ,cd_group = ");
			if ( !strGroupCd.equals("") ) {
				strSql.append(strGroupCd);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * リテラルデータ削除SQL作成
	 *  : リテラルデータ削除用　SQLを作成し、各データをDBに削除する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd + "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 管理結果パラメーター格納
	 *  : リテラルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable) 
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
