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
 * 手配済みデータ検索DB処理 機能ID：FGEN3390
 * @author shima.hs
 *
 */
public class FGEN3390_Logic extends LogicBase {

	/**
	 * 手配済みデータ検索コンストラクタ：インスタンス生成
	 */
	public FGEN3390_Logic() {
		super();
	}

	/**
	 * 手配済みデータ検索：手配済みデータ件数を取得する。
	 *
	 * @param reqData
	 *            : 機能リクエストデータ
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

		List<?> lstRecset = null;
		StringBuffer strSql = new StringBuffer();

		// レスポンスデータ（機能）
		RequestResponsKindBean resKind = null;

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// SQL文の作成
			strSql = createSQL(reqData, strSql);

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
			createResponse(lstRecset, resKind.getTableItem(0));

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
			strSql = null;
		}

		return resKind;
	}

	/**
	 * 取得SQL作成 : 手配済みデータ件数を取得するSQLを作成。
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
	private StringBuffer createSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		try {
			strSql.append(" SELECT ");
			strSql.append("   count(cd_shain) ");
			strSql.append(" FROM ");
			strSql.append("   tr_shizai_tehai ");
			strSql.append(" WHERE ");

			for(int i = 0; i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//リクエストデータより試作コード取得
				String strReqShain           = toString(reqData.getFieldVale(0, i, "cd_shain"));
				String strReqNen             = toString(reqData.getFieldVale(0, i, "nen"));
				String strReqNoOi            = toString(reqData.getFieldVale(0, i, "no_oi"));
				String strReqShizai          = toString(reqData.getFieldVale(0, i, "seq_shizai"));
				String strReqEda             = toString(reqData.getFieldVale(0, i, "no_eda"));

				int intShain = Integer.parseInt(strReqShain);
				int intNen   = Integer.parseInt(strReqNen);
				int intNoOi  = Integer.parseInt(strReqNoOi);
				int intShizai= Integer.parseInt(strReqShizai);
				int intEda   = Integer.parseInt(strReqEda);

				if(i != 0){
					strSql.append(" OR ");
				}
				strSql.append("       (cd_shain = '" + intShain + "' ");
				strSql.append("    AND nen = '" + intNen + "' ");
				strSql.append("    AND no_oi = '" + intNoOi + "' ");
				strSql.append("    AND seq_shizai = '" + intShizai + "' ");
				strSql.append("    AND no_eda = '" + intEda + "' ");
				strSql.append("    AND flg_tehai_status = 3) ");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "手配済みデータ検索処理に失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納 : レスポンスデータへ格納する。
	 *
	 * @param lstGroupCmb
	 *            : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void createResponse(List<?> lstHattyusakiCmb,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				// 結果をレスポンスデータに格納
				resTable.addFieldVale(0, "count_tehaizumi",
						toString(lstHattyusakiCmb.get(0)));


		} catch (Exception e) {
			this.em.ThrowException(e, "手配済みデータ検索処理に失敗しました。");

		} finally {

		}

	}
}
