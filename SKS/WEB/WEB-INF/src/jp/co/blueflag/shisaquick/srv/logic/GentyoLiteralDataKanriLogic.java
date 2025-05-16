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
 * 取得情報を、レスポンスデータ保持「機能ID：SA331O」に設定する。
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoLiteralDataKanriLogic extends LogicBase{

	/**
	 * リテラルデータ管理コンストラクタ
	 */
	public GentyoLiteralDataKanriLogic() {
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
			// 表示値
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// 備考
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// 編集可否
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// 第二リテラルコード
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");
			// 第二リテラル使用フラグ値
			String strFlg2ndEdit = requestData.getFieldVale(0, 0, "flg_2ndedit");
			// 第２リテラル表示値
			String str2ndSortNo = requestData.getFieldVale(0, 0, "no_2ndsort");

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
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");

			// 採番処理
			String strNoSeq = "";
			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			try {
				// トランザクション開始
				super.createSearchDB();
				searchDB.BeginTran();

				//第２リテラル名が入力されていない場合、（第１）リテラルの登録
				if ( strLiteral2ndNm.equals("") ){
					/*** リテラルコード start **************************************/
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

					strSql.append(" ,'" + ("000" + strLiteralCd).substring(("000" + strLiteralCd).length() - 3) + "'");
					/*** リテラルコード end **************************************/

				//第２リテラル名が入力されている場合、第２リテラルの登録
				}else{
					/*** 第２リテラルコードの取得 start **************************************/
					// 採番処理
					int intNoSeq = 0;

					// リテラルコードの採番SQL作成
					strSelSql.append("SELECT top 1 cd_2nd_literal");
					strSelSql.append(" FROM ma_literal ");
					strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
					strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
					strSelSql.append(" ORDER BY cd_2nd_literal desc ");
					//SQLを実行
					lstRecset = searchDB.dbSearch(strSelSql.toString());
					if (lstRecset.size() > 0) {
						// 採番結果を退避
						Object items = (Object) lstRecset.get(0);
						if (items.toString().equals("")){
							intNoSeq = 0;
						}else{
							intNoSeq = Integer.parseInt(items.toString()); //001 → 1
						}
						strLiteral2ndCd = String.valueOf(intNoSeq + 1);  //＋1して文字列に
					} else {
						strLiteral2ndCd = "1";
					}

					strSql.append(" ,'" + strLiteralCd + "'");  //リテラルコード
					/*** 第２リテラルコードの取得 end **************************************/


					// 第２リテラルを使用開始するまで、使っていた（第１）リテラル用のレコードを削除
					strUpdSql.append("DELETE ma_literal");
					strUpdSql.append(" WHERE cd_category = '");
					strUpdSql.append(strCategoryCd + "'");
					strUpdSql.append(" AND cd_literal = '");
					strUpdSql.append(strLiteralCd + "'");
					strUpdSql.append(" AND cd_2nd_literal = ''"); //第２リテラルが空白のものが対象

					//SQLを実行
					super.createExecDB();
					execDB.setSession(searchDB.getSession());
					execDB.execSQL(strUpdSql.toString());

					//コミット
					searchDB.Commit();
				}
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

			/*** リテラル名以下 **************************************/
			strSql.append(" ,'" + strLiteralNm + "'");  //リテラル名
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");  // 備考
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);  // 編集フラグ
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");

			//第２リテラル名が
			if ( !strLiteral2ndNm.equals("") ){
				//入力されていない場合、（第１）リテラルの登録
				strSql.append(" ,'" + ("000" + strLiteral2ndCd).substring(("000" + strLiteral2ndCd).length() - 3) + "'");  //第２リテラルコード
				strSql.append(" ,'" + strLiteral2ndNm + "'");  //第２リテラル名
				strSql.append(" ,'" + str2ndSortNo + "'");  //第２リテラル表示順
				strSql.append(" ,'1'");  //第２リテラル使用フラグ
			}else{
				//入力されている場合、第２リテラルの登録
				strSql.append(" ,''");  //第２リテラルコード
				strSql.append(" ,NULL");  //第２リテラル名
				strSql.append(" ,NULL");  //第２リテラル表示順
				strSql.append(" ,NULL");  //第２リテラル使用フラグ
			}

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
			// 表示値
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// 備考
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// 編集可否
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// 第二リテラルコード
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");
			// 第２表示値
			String str2ndSortNo = requestData.getFieldVale(0, 0, "no_2ndsort");
			// 第二リテラル使用フラグ値
			String strFlg2ndEdit = requestData.getFieldVale(0, 0, "flg_2ndedit");

			//SQL文の作成
			strSql.append("UPDATE ma_literal SET");
			if ( strLiteral2ndNm.equals("") ){
				//第２リテラル名が入力されていなければ、（第１）リテラルの更新
				strSql.append("  nm_literal = '");
				strSql.append(strLiteralNm + "'");
				strSql.append(" ,no_sort = ");
				strSql.append(strSortNo);
				strSql.append(" ,biko = ");
				if ( !strBiko.equals("") ) {
					strSql.append("'"+ strBiko + "'");
				} else {
					strSql.append("NULL");
				}
				strSql.append(" ,flg_edit = ");
				strSql.append(strFlgEdit);
			}else{
				//第２リテラル名が入力されていれば、第２リテラルの更新
				strSql.append(" cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd + "'");
				strSql.append(" ,nm_2nd_literal = '");
				strSql.append(strLiteral2ndNm + "'");
				strSql.append(" ,no_2nd_sort = ");
				strSql.append(str2ndSortNo);
				strSql.append(" ,flg_2ndedit = ");
				strSql.append(strFlg2ndEdit);
			}
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			if ( !strLiteral2ndNm.equals("") ){
				strSql.append(" AND cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd+ "'");
			}

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
			// リテラル名
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// 第二リテラルコード
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");

			//SQL文の作成
			if ( strLiteral2ndNm.equals("") ){
				//第２リテラル名が入力されていなければ、（第１）リテラルの削除。第２リテラル使用で、複数行あっても全て削除
				strSql.append("DELETE");
				strSql.append(" FROM ma_literal");
				strSql.append(" WHERE cd_category = '");
				strSql.append(strCategoryCd + "'");
				strSql.append(" AND cd_literal = '");
				strSql.append(strLiteralCd + "'");
			}else{
				//第２リテラル名が入力されていれば、第２リテラルの削除
				strSql.append("DELETE");
				strSql.append(" FROM ma_literal");
				strSql.append(" WHERE cd_category = '");
				strSql.append(strCategoryCd + "'");
				strSql.append(" AND cd_literal = '");
				strSql.append(strLiteralCd + "'");
				strSql.append(" AND cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd + "'");
			}

			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			List<?> lstRecset = null;

			try {
				// トランザクション開始
				super.createSearchDB();
				searchDB.BeginTran();

				// 対象データが何レコードあるか
				strSelSql.append("SELECT cd_literal");
				strSelSql.append(" FROM ma_literal ");
				strSelSql.append(" WHERE cd_category = '");
				strSelSql.append(strCategoryCd + "'");
				strSelSql.append(" AND cd_literal = '");
				strSelSql.append(strLiteralCd + "'");

				lstRecset = searchDB.dbSearch(strSelSql.toString());

				if ( strLiteral2ndNm.equals("") ){
					// 第１リテラルしかない場合は気にしない
				}else{
					// 第２リテラルの最後の一つが削除される場合、（第１）リテラル用のレコードを作成
					if (lstRecset.size() <= 1) {
						strInsSql.append("INSERT INTO ma_literal (");
						strInsSql.append(" cd_category");
						strInsSql.append(" ,cd_literal");
						strInsSql.append(" ,nm_literal");
						strInsSql.append(" ,value1");
						strInsSql.append(" ,value2");
						strInsSql.append(" ,no_sort");
						strInsSql.append(" ,biko");
						strInsSql.append(" ,flg_edit");
						strInsSql.append(" ,cd_group");
						strInsSql.append(" ,id_toroku");
						strInsSql.append(" ,dt_toroku");
						strInsSql.append(" ,id_koshin");
						strInsSql.append(" ,dt_koshin");
						strInsSql.append(" ,cd_2nd_literal");
						strInsSql.append(" ,nm_2nd_literal");
						strInsSql.append(" ,no_2nd_sort");
						strInsSql.append(" ,flg_2ndedit");
						strInsSql.append(" )");
						strInsSql.append(" SELECT");
						strInsSql.append(" cd_category");
						strInsSql.append(" ,cd_literal");
						strInsSql.append(" ,nm_literal");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,no_sort");
						strInsSql.append(" ,biko");
						strInsSql.append(" ,flg_edit");
						strInsSql.append(" ,cd_group");
						strInsSql.append(" ,id_toroku");
						strInsSql.append(" ,dt_toroku");
						strInsSql.append(" ,id_koshin");
						strInsSql.append(" ,dt_koshin");
						strInsSql.append(" ,''"); // キーなのでNULLではなく空白
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" FROM ma_literal ");
						strInsSql.append(" WHERE cd_category = '");
						strInsSql.append(strCategoryCd + "'");
						strInsSql.append(" AND cd_literal = '");
						strInsSql.append(strLiteralCd + "'");
						strInsSql.append(" AND cd_2nd_literal = '");
						strInsSql.append(strLiteral2ndCd + "'");

						//SQLを実行
						super.createExecDB();
						execDB.setSession(searchDB.getSession());
						execDB.execSQL(strInsSql.toString());
						//コミット
						searchDB.Commit();
					}

				}

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
				strInsSql = null;
			}
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
