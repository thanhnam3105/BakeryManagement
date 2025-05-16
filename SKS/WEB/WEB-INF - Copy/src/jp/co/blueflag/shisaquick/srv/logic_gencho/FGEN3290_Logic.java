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
 * 発注先顧客名検索DB処理<br>
 * : 発注先顧客名を検索する。 機能ID：FGEN3290　
 *
 * @author H.Shima
 * @since 2014/09/17
 */
public class FGEN3290_Logic extends LogicBase {

	/**
	 * 発注先顧客名DB処理用コンストラクタ <br>
	 * : インスタンス生成
	 */
	public FGEN3290_Logic() {
		super();
	}

	/**
	 * 発注先顧客名取得SQL作成<br>
	 * : 発注先顧客名情報を取得するSQLを作成。
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

			// SQL文の作成
			strSql = createSQL(reqData, strSql);

			if (strSql.length() != 0) {

				// SQLを実行
				super.createSearchDB();
				lstRecset = searchDB.dbSearch(strSql.toString());
			}

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageTantosyaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "発注先顧客名情報の取得に失敗しました。");

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
	 * 取得SQL作成
	 *  : 発注先顧客名を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strCdHattyusaki = toString(reqData.getFieldVale(0, 0, "cd_hattyusaki"));
			int intHattyusaki = 0;
			if (!strCdHattyusaki.equals("")) {
				intHattyusaki = Integer.parseInt(strCdHattyusaki);

				strSql.append(" SELECT ");
				strSql.append("   cd_2nd_literal, ");
				strSql.append("   nm_2nd_literal ");
				strSql.append(" FROM ");
				strSql.append("   ma_literal ");
				strSql.append(" WHERE ");
				strSql.append("     cd_category = 'C_hattyuusaki' ");
				strSql.append(" AND cd_literal =  " + intHattyusaki + " ");
			}



		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 発注先顧客名パラメーター格納
	 *  : 発注先顧客名情報をレスポンスデータへ格納する。
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageTantosyaCmb(List<?> lstHattyusakiCmb, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			if (lstHattyusakiCmb != null) {
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
					resTable.addFieldVale(i, "cd_tantosha", toString(items[0]));
					resTable.addFieldVale(i, "nm_tantosha", toString(items[1]));

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
					resTable.addFieldVale(0, "cd_tantosha", "");
					resTable.addFieldVale(0, "nm_tantosha", "");
				}
			} else {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_tantosha", "");
				resTable.addFieldVale(0, "nm_tantosha", "");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "発注先顧客名のレスポンスデータ生成に失敗しました。");

		} finally {

		}

	}
}
