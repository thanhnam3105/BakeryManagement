package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

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
public class FGEN3580_Logic extends LogicBase{

	/**
	 * リテラルマスタメンテ：発注先一覧情報検索DB処理
	 *
	 */
	public FGEN3580_Logic() {
		// 基底クラスのコンストラクタ
		super();
	}
	/**
	 * リテラルマスタメンテ：発注先一覧情報取得SQL作成
	 *
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		//ユーザー情報退避
		userInfoData = _userInfoData;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();


			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//レスポンスデータの形成
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除

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
	 * @author E.Kitazawa
	 * @since  2014/09/11
	 */
	private void setData(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			// テーブル名
			String strTblNm = "table";

			//データ取得SQL作成
			strSqlBuf = this.createSQL(reqData);

			// nullの時（メニューからのロード時）は検索処理を実行しない
			if (strSqlBuf != null) {

				// 共通クラス　データベース検索を用いてSQL実行
				super.createSearchDB();
				lstRecset = searchDB.dbSearch(strSqlBuf.toString());

				// 検索結果がない場合、ここでエラーとしない
				if (lstRecset.size() == 0){
//					em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
				}

			}
			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "発注先マスタ情報検索処理が失敗しました。");

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

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstMstData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(

			  List<?> lstMstData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//メニューからのロード時はlstMstDataが nullの為
			if ((lstMstData== null) || (lstMstData.size() == 0)) {
//				// データが取得できない時：エラーのしない為（jsのメッセージを返したい）
//				resTable.addFieldVale(0, "flg_return", "");
//				resTable.addFieldVale(0, "msg_error", "");
//				resTable.addFieldVale(0, "no_errmsg", "");
//				resTable.addFieldVale(0, "nm_class", "");
//				resTable.addFieldVale(0, "cd_error", "");
//				resTable.addFieldVale(0, "msg_system", "");

			} else {

				for (int i = 0; i < lstMstData.size(); i++) {

					// 処理結果の格納
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstMstData.get(i);

					// 結果をレスポンスデータに格納
					resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
					resTable.addFieldVale(i, "nm_literal", toString(items[1],""));
					resTable.addFieldVale(i, "no_sort", toString(items[2],""));
					resTable.addFieldVale(i, "cd_2nd_literal", toString(items[3],""));
					resTable.addFieldVale(i, "nm_2nd_literal", toString(items[4],""));
					resTable.addFieldVale(i, "no_2nd_sort", toString(items[5],""));
					resTable.addFieldVale(i, "mail_address", toString(items[6],""));
					resTable.addFieldVale(i, "flg_mishiyo", toString(items[7],""));
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return StringBuffer：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {

			String dataId = null;
			// カテゴりコード
			String strCategoryCd = reqData.getFieldVale("table", 0, "cd_category");
			// 発注先コード
			String strLiteralCd   = reqData.getFieldVale("table", 0, "cd_literal");
			// 発注先名
			String strLiteralNm      = reqData.getFieldVale("table", 0, "nm_literal");


			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,no_sort");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd);
			strSql.append("'");
			if (!strLiteralCd.equals("")) {
				strSql.append(" AND cd_literal LIKE '%");
				strSql.append(strLiteralCd);
				strSql.append("%'");
			}
			if (!strLiteralNm.equals("")) {
				strSql.append(" AND nm_literal LIKE '%");
				strSql.append(strLiteralNm);
				strSql.append("%'");
			}
			strSql.append("ORDER BY");
			strSql.append(" flg_mishiyo");
			strSql.append(" ,cast (cd_literal as int) ");
			strSql.append(" ,cast (cd_2nd_literal as int)");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

}
