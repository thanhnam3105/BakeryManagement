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
 *
 * コンボ用：発注先検索DB処理<br>
 * : コンボ用：発注先を検索する。 機能ID：FGEN3300　
 *
 * @author H.Shima
 * @since 2014/09/16
 */
public class FGEN3310_Logic extends LogicBase {

	/**
	 * コンボ用：発注先情報検索DB処理用コンストラクタ <br>
	 * : インスタンス生成
	 */
	public FGEN3310_Logic() {
		super();
	}

	/**
	 * コンボ用：発注先情報取得SQL作成<br>
	 * : グループコンボボックス情報を取得するSQLを作成。
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
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ユーザー情報退避
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		// レスポンスデータ（機能）
		RequestResponsKindBean resKind = null;

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// 検索条件取得
			String strFlgMishiyo = reqData.getFieldVale(0, 0, "flg_mishiyo");
			// 未使用
			if(strFlgMishiyo.equals("1")){
				// 使用可能な発注先リストを取得
				// SQL文の作成
				strSql = createNotMishiyoSQL(reqData, strSql, strFlgMishiyo);
			} else {
				// 発注先リストを全件取得
			// SQL文の作成
			strSql = createSQL(reqData, strSql);
			}
//
//			// SQL文の作成
//			strSql = createSQL(reqData, strSql);

			// SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageHattyusakiCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "発注先コンボボックス情報の取得に失敗しました。");

		} finally {
			// リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			// 変数の削除
			strSql = null;

		}
		return resKind;

	}


	/**
	 * 発注先取得SQL作成
	 *  : 対象資材を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		//cho 発注先１/2 のコンボに表示されたことをDistinctしてmergeする
		try {
			strSql.append(" SELECT distinct ");
			strSql.append("   cd_ｌiteral, ");
			strSql.append("   nm_literal, ");
			strSql.append("   no_sort ");
			strSql.append(" FROM ");
			strSql.append("   ma_literal ");
			strSql.append(" WHERE ");
			strSql.append("   cd_category = 'C_hattyuusaki' ");
			strSql.append(" ORDER BY ");
			strSql.append("   no_sort ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

//【KPX@1602367】add start
	/**
	 * 発注先取得SQL作成
	 *  : 発注先を取得するSQLを作成。
	 * @param strFlgMishiyo
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createNotMishiyoSQL(RequestResponsKindBean reqData, StringBuffer strSql, String strFlgMishiyo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		// 使用可能な発注先（未使用を除く）を取得する
		try {
			strSql.append(" SELECT distinct ");
			strSql.append("   cd_ｌiteral, ");
			strSql.append("   nm_literal, ");
			strSql.append("   no_sort ");
			strSql.append(" FROM ");
			strSql.append("   ma_literal ");
			strSql.append(" WHERE ");
			strSql.append("   cd_category = 'C_hattyuusaki' ");
			strSql.append("   AND flg_mishiyo = '" + strFlgMishiyo + "' ");
			strSql.append(" ORDER BY ");
			strSql.append("   no_sort ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
//【KPX@1602367】add end


	/**
	 * コンボ用：発注先パラメーター格納
	 *  : 発注先コンボボックス情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageHattyusakiCmb(List<?> lstHattyusakiCmb, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstHattyusakiCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstHattyusakiCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_hattyusaki", items[0].toString());
				resTable.addFieldVale(i, "nm_hattyusaki", items[1].toString());

			}

			if (lstHattyusakiCmb.size() == 0) {
				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_hattyusaki", "");
				resTable.addFieldVale(0, "nm_hattyusaki", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "発注先コンボボックスのレスポンスデータ生成に失敗しました。");

		} finally {

		}

	}

}
