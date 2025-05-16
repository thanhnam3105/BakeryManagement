package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【QP@30297】
 *  包材検索
 *  機能ID：FGEN3010
 *
 * @author Sakamoto
 * @since  2014/01/28
 */
public class FGEN3010_Logic extends LogicBase{

	/**
	 * 包材検索
	 * : インスタンス生成
	 */
	public FGEN3010_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 包材検索
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

		// ユーザー情報退避
		userInfoData = _userInfoData;

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// レスポンスデータの形成
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			// リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			// 変数の削除

		}
		return resKind;

	}

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
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			//テーブル名
			String strTblNm = "table";

			//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
			// データ取得SQL作成
//			strSqlBuf = this.createSQL();
			strSqlBuf = this.createSQL(reqData);
			//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "包材検索処理が失敗しました。");

		} finally {
			// リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				// セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}

			// 変数の削除
			strSqlBuf = null;

		}

	}

	//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//	private StringBuffer createSQL()
	private StringBuffer createSQL(
			RequestResponsKindBean reqData
			)
    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {

			// SQL文の作成
		    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
			// 検索条件取得
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");


/*			strSql.append(" SELECT cd_ｌiteral  ");
			strSql.append("       ,nm_ｌiteral  ");
			strSql.append(" FROM  ma_literal  ");
			strSql.append(" WHERE cd_category='houzai'  ");
			strSql.append(" ORDER BY  no_sort ");
*/
			strSql.append(" SELECT ");
			strSql.append("        cd_2nd_literal  ");
			strSql.append("       ,nm_2nd_literal  ");
			strSql.append("       ,cd_ｌiteral  ");
			strSql.append("       ,nm_ｌiteral  ");
			strSql.append("       ,ISNULL(flg_mishiyo,'0')  AS flg_mishiyo");									//未使用フラグ【KPX@1602367】add
			strSql.append(" FROM  ma_literal  ");
			strSql.append(" WHERE cd_category = 'maker_name'  ");
			strSql.append("   AND cd_ｌiteral = '" + strCdMaker + "'  ");
			strSql.append(" ORDER BY  no_2nd_sort");
			//【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				// 処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				// 結果をレスポンスデータに格納
			    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod start
//				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
//				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));
				resTable.addFieldVale(i, "cd_2nd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_2nd_literal", toString(items[1],""));
				resTable.addFieldVale(i, "cd_literal", toString(items[2],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[3],""));
			    //【QP@30297】No.19 E.kitazawa 課題対応 --------------------- mod end
				resTable.addFieldVale(i, "flg_mishiyo", toString(items[4],""));		//未使用フラグ【KPX@1602367】add
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
