package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * リテラルマスタメンテ：原資材調達部用 発注先一覧情報検索DB処理
 *
 *
 */
public class FGEN3630_Logic extends LogicBase {

	/**
	 * リテラルマスタメンテ：発注先一覧情報検索DB処理
	 *
	 */
	public FGEN3630_Logic() {
		// 基底クラスのコンストラクタ
		super();
	}

	/**
	 * リテラルマスタメンテ：発注先一覧情報取得SQL作成
	 *
	 * @param reqData
	 *            : リクエストデータ
	 * @param userInfoData
	 *            : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean requestData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ユーザー情報退避
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();

		// レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		// 明細番号
		String meisaiNum = "";

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//トランザクション開始
			super.createExecDB();
			//execDB.BeginTran();
			for (int i = 0; i < requestData.getCntRow(requestData.getTableID(0)); i++) {

				String shoriKbn = requestData.getFieldVale(0, i, "shoriKbn");
				execDB.BeginTran();

				// 登録処理
				if (shoriKbn.equals("1")) {

					strSql = literalKanriInsertSQL(requestData, i);
				// 更新処理
				} else if (shoriKbn.equals("2")) {
					meisaiNum = requestData.getFieldVale(0, i, "meisaiNum");
					strSql = literalKanriUpdateSQL(requestData, i);
				// 削除処理
				} else if  (shoriKbn.equals("3")) {

					strSql = literalKanriDeleteSQL(requestData, i);
				}

				//SQLを実行
				execDB.execUpdateSQL(strSql.toString());
				//コミット
				execDB.Commit();
			}


			//execDB.Commit();

			//機能IDの設定
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageLiteralDataKanri(resKind.getTableItem(0), 0, meisaiNum);

		} catch (ConstraintViolationException e) {

			if (execDB != null) {
				//ロールバック
				execDB.Rollback();
			}

			//機能IDの設定
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// 明細番号を取得
			//requestData.getFieldVale(TableNo, RowNo, itemNo)

			//レスポンスデータの形成
			storageLiteralDataKanri(resKind.getTableItem(0), 1, meisaiNum);

		} catch (Exception e) {

			if (execDB != null) {
				//ロールバック
				execDB.Rollback();
			}
			e.printStackTrace();
		} finally {

			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;

		}
		return resKind;

	}


	/**
	 * リテラルデータ登録SQL作成
	 *  : リテラルデータ登録　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// 登録用
		StringBuffer strSql = new StringBuffer();
		try {

			// 発注先コード
			String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
			String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);
			// 発注先名
			String strLiteralNm = requestData.getFieldVale(0, row, "nm_literal");
			// 発注先名表示値
			String strSortNo = requestData.getFieldVale(0, row, "no_sort");
			// 備考
			// String strBiko = requestData.getFieldVale(0, 0, "biko");
			// 編集可否
			short flgEdit = 0;
			// 第二リテラルコード
			String strLiteral2ndCd = "";
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, row, "nm_2nd_literal");
			// 第二リテラル使用フラグ値

			// 第２リテラル表示値
			String str2ndSortNo = requestData.getFieldVale(0, row, "no_2nd_sort");
			// メールアドレス
			String strMailAddress = requestData.getFieldVale(0, row, "mail_address");
			// 未使用
			String strFlgMishiyo = requestData.getFieldVale(0, row, "flg_mishiyo");

			String strCategoryCd = "C_hattyuusaki";

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
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ) VALUES ( '");
			strSql.append("C_hattyuusaki'");// カテゴリCD

			// 採番処理
			String strNoSeq = "";
			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			// try {
			// トランザクション開始
			// super.createSearchDB();
			// searchDB.BeginTran();

			/*** 第２リテラルコードの取得 start **************************************/
			// 採番処理
			int intNoSeq = 0;

			// リテラルコードの採番SQL作成
			strSelSql.append("SELECT MAX(CONVERT(int, cd_2nd_literal)) + 1 AS cd_2nd_literal");
			strSelSql.append(",cd_literal");
			strSelSql.append(" FROM ma_literal ");
			strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
			strSelSql.append(" AND cd_literal = '" + strLiteralCdFormatZero + "'");
			strSelSql.append("GROUP BY cd_literal");
			strSelSql.append(" ORDER BY cd_2nd_literal desc ");
			// SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSelSql.toString());

			if (lstRecset.size() > 0) {

				Object[] items = (Object[]) lstRecset.get(0);
				intNoSeq = Integer.parseInt(items[0].toString());

				strLiteral2ndCd = String.format("%06d", intNoSeq);
			} else {
				strLiteral2ndCd = "000001";
			}

			strSql.append(" ,'" + strLiteralCdFormatZero + "'"); // 発注先コード（リテラルCD）
			/*** 第２リテラルコードの取得 end **************************************/


			/*** リテラル名以下 **************************************/
			strSql.append(" ,'" + strLiteralNm + "'"); // 発注先名
			strSql.append(" ,NULL"); // val1リテラル値1
			strSql.append(" ,NULL"); // val2 リテラル値2
			strSql.append(" ," + strSortNo); // 表示順
			strSql.append(" ,NULL");// 備考

			strSql.append(" ," + flgEdit); // 編集フラグ
			strSql.append(" ,NULL"); // グループCD
			strSql.append(" ," + userInfoData.getId_user()); // 登録者ID
			strSql.append(" ,GETDATE()"); // 登録日付
			strSql.append(" ," + userInfoData.getId_user()); // 更新ID
			strSql.append(" ,GETDATE()"); // 更新日付
			strSql.append(" ,'" + strLiteral2ndCd + "'");// 第２リテラルコード
			strSql.append(" ,'" + strLiteral2ndNm + "'"); // 第２リテラル名
			strSql.append(" ,'" + str2ndSortNo + "'"); // 第２リテラル表示順
			strSql.append(" ,1"); // 第２リテラル使用フラグ
			// メールアドレス
			strSql.append(",'" + strMailAddress + "'");
			// 未使用
			strSql.append(",'" + strFlgMishiyo + "'");

			strSql.append(")");

		}  catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strSql;
	}

	/**
	 * リテラルデータ登録SQL作成
	 *  : リテラルデータ登録　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriUpdateSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// 登録用
		StringBuffer strSql = new StringBuffer();

		try {

			// 発注先コード
			String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
			String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);

			// 編集する前の発注先コード
			String strBeforeLiteralCd = requestData.getFieldVale(0, row, "hiddenCd_literal");
			// 発注先名
			String strLiteralNm = requestData.getFieldVale(0, row, "nm_literal");
			// 発注先名表示値
			String strSortNo = requestData.getFieldVale(0, row, "no_sort");
			// 第二リテラル名
			String strLiteral2ndCd = requestData.getFieldVale(0, row, "cd_2nd_literal");
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, row, "nm_2nd_literal");

			// 第２リテラル表示値
			String str2ndSortNo = requestData.getFieldVale(0, row, "no_2nd_sort");
			// メールアドレス
			String strMailAddress = requestData.getFieldVale(0, row, "mail_address");
			// 未使用
			String strFlgMishiyo = requestData.getFieldVale(0, row, "flg_mishiyo");

			String updateCd2ndLiteral = "";

			String strCategoryCd = "C_hattyuusaki";
			List<?> lstRecset = null;


//			//----------重複チェック------------
//			StringBuffer strSql2 = new StringBuffer();
//			strSql2 = new StringBuffer();
//            // SQL文の作成
//            strSql2.append(" SELECT cd_literal  ");
//            strSql2.append(" FROM  ma_literal  ");
//            strSql2.append(" WHERE cd_category = '"+ strCategoryCd );
//            strSql2.append("'");
//            strSql2.append("   AND cd_literal = '"     + strLiteralCd );
//            strSql2.append("'");
//            strSql2.append(" AND cd_2nd_literal = '"   + strLiteral2ndCd );
//            strSql2.append("'");
//            super.createSearchDB();
//            List<?> lstRecset2 = searchDB.dbSearch(strSql2.toString());
//
//            // 更新データが存在しない時、エラー
//            if(lstRecset2.size() >= 1) {
//            	em.ThrowException(ExceptionKind.一般Exception,"E000301","","","");
//            }

			//SQL文の作成
			strSql.append("UPDATE ma_literal SET");
			strSql.append("  cd_literal = '");
			strSql.append(strLiteralCdFormatZero + "'");// 発注先コード
			strSql.append("  ,nm_literal = '");
			strSql.append(strLiteralNm + "'");// 発注先名
			strSql.append(" ,no_sort = ");
			strSql.append(strSortNo);		 // 発注先表示順

//			strSql.append(" ,cd_2nd_literal = ");
//
//			/*** 第２リテラルコードの取得 start **************************************/
//			// 採番処理
//			int intNoSeq = 0;
//			StringBuffer strSelSql = new StringBuffer();
//			// リテラルコードの採番SQL作成
//			strSelSql.append("SELECT MAX(cd_2nd_literal) AS cd_2nd_literal");
//			strSelSql.append(",cd_literal");
//			strSelSql.append(" FROM ma_literal ");
//			strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
//			strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
//			strSelSql.append("GROUP BY cd_literal");
//			strSelSql.append(" ORDER BY cd_2nd_literal desc ");
//			// SQLを実行
//			super.createSearchDB();
//			lstRecset = searchDB.dbSearch(strSelSql.toString());
//
//			if (lstRecset.size() > 0) {
//				// 採番結果を退避
//				Object items = (Object) lstRecset.get(0);
//				if (items.toString().equals("")) {
//					intNoSeq = 0;
//				} else {
//					intNoSeq = Integer.parseInt(items.toString()); // 001 →
//																	// 1
//				}
//				updateCd2ndLiteral = String.valueOf(intNoSeq + 1); // ＋1して文字列に
//			} else {
//				updateCd2ndLiteral = "1";
//			}
//
//			strSql.append(" '00" + updateCd2ndLiteral + "'"); // 発注先コード（リテラルCD）
//			/*** 第２リテラルコードの取得 end **************************************/


			strSql.append(" ,nm_2nd_literal = '");
			strSql.append(strLiteral2ndNm + "'");	// 第二リテラル
			strSql.append(" ,no_2nd_sort = '");
			strSql.append(str2ndSortNo + "'");	// 第二リテラル表示順
			strSql.append(" ,mail_address = '");
			strSql.append(strMailAddress + "'");	// メールアドレス
			strSql.append(" ,flg_mishiyo = ");
			strSql.append(strFlgMishiyo);	// 未使用
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strBeforeLiteralCd+ "'");
			strSql.append(" AND cd_2nd_literal = '");
			strSql.append(strLiteral2ndCd+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}


	/**
	 * リテラルデータ登録SQL作成
	 *  : リテラルデータ登録　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// 登録用
		StringBuffer strSql = new StringBuffer();

		// 発注先コード
		String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
		String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);

		// 第二リテラル名
		String strLiteral2ndCd = requestData.getFieldVale(0, row, "cd_2nd_literal");


		strSql.append("DELETE");
		strSql.append(" FROM ma_literal");
		strSql.append(" WHERE cd_category = '");
		strSql.append("C_hattyuusaki'");
		strSql.append(" AND cd_literal = '");
		strSql.append(strLiteralCdFormatZero);
		strSql.append("'");
		strSql.append(" AND cd_2nd_literal = '");
		strSql.append(strLiteral2ndCd);
		strSql.append("'");

		return strSql;
	}
	/**
	 * 0サプレスする
	 * @param strCdShohin
	 * @return
	 */
	private String  get0SupressCode(String strCdShohin) {
		int intCdShohin = 0;
		if (!strCdShohin.equals("")) {
			 intCdShohin = Integer.parseInt(strCdShohin);
		}
		return String.valueOf(intCdShohin);
	}
	/**
	 * 管理結果パラメーター格納
	 *  : リテラルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable, int num, String meisaiNum)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			if (num == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "updateErrorFlg_return", "false");
				resTable.addFieldVale(0, "meisaiNum", meisaiNum);
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			} else {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "updateErrorFlg_return", "true");
				resTable.addFieldVale(0, "meisaiNum", meisaiNum);
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
