package jp.co.blueflag.shisaquick.srv.logic_gencho;

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
public class FGEN3640_Logic extends LogicBase {

	/**
	 * リテラルマスタメンテ：発注先一覧情報検索DB処理
	 *
	 */
	public FGEN3640_Logic() {
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

		try {
			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			strSql = literalKanriDeleteSQL(requestData);

			//トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			//SQLを実行
			execDB.execSQL(strSql.toString());

			//コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageLiteralDataKanri(resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

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
	 * リテラルデータ登録SQL作成
	 *  : リテラルデータ登録　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// 登録用
		StringBuffer strSql = new StringBuffer();

			// 発注先コード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");

		if ( strLiteral2ndNm.equals("") ){
			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append("C_hattyuusaki'");
			strSql.append(" AND cd_literal = '");
			strSql.append("'");
			strSql.append(strLiteralCd);
			strSql.append("'");
		} else {

			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append("C_hattyuusaki'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");
			strSql.append(" AND nm_2nd_literal = '");
			strSql.append(strLiteral2ndNm);
			strSql.append("'");
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
