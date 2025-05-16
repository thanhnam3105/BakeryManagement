package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 *
 * リテラルマスタメンテ：リテラル情報検索DB処理
 *  : リテラルマスタメンテ：リテラル情報を検索する。
 * @author jinbo
 * @since  2009/04/07
 */
public class LiteralDataSearchLogic extends LogicBase{

	/**
	 * リテラルマスタメンテ：リテラル情報検索DB処理
	 * : インスタンス生成
	 */
	public LiteralDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * リテラルマスタメンテ：リテラル情報取得SQL作成
	 *  : リテラル情報を取得するSQLを作成。
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
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			strSql = createSQL(reqData, strSql);

			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (lstRecset.size() == 0){
				// 20160513  KPX@1600766 MOD start
//				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				//値を設定している時エラーとしない
				if (reqData.getFieldVale(0, 0, "req_flg").equals("")) {
					em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				}
				// 20160513  KPX@1600766 MOD end

			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageLiteralData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}
		return resKind;
	}

	/**
	 * リテラル情報取得SQL作成
	 *  : リテラル情報を取得するSQLを作成。
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
			String strCategoryCd = null;
			String strLiteralCd = null;
// 20160513  KPX@1600766 MOD start
//			String dataId = null;
			String dataId = "";
// 20160513  KPX@1600766 MOD end
			//カテゴリコードの取得
			strCategoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//リテラルコードの取得
			strLiteralCd = reqData.getFieldVale(0, 0, "cd_literal");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("60")) {
					//リテラルマスタメンテナンス画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,ISNULL(CONVERT(VARCHAR,value1),'') as value1");
			strSql.append(" ,ISNULL(CONVERT(VARCHAR,value2),'') as value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,ISNULL(biko,'') as biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,ISNULL(cd_group,'') as cd_group");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd);
			strSql.append("'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");

			//検索条件権限設定
			//同一グループ
			if(dataId.equals("1")) {
				strSql.append(" AND (cd_group = ");
				strSql.append(userInfoData.getCd_group());
				strSql.append(" OR cd_group IS NULL) ");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * リテラルマスタメンテ：リテラル情報パラメーター格納
	 *  : リテラル情報をレスポンスデータへ格納する。
	 * @param lstLiteralData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralData(List<?> lstLiteralData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			// 20160513  KPX@1600766 ADD start
			if (lstLiteralData.size() == 0) {
				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}
			// 20160513  KPX@1600766 ADD end

			for (int i = 0; i < lstLiteralData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstLiteralData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				resTable.addFieldVale(i, "value1", items[2].toString());
				resTable.addFieldVale(i, "value2", items[3].toString());
				resTable.addFieldVale(i, "no_sort", items[4].toString());
				resTable.addFieldVale(i, "biko", items[5].toString());
				resTable.addFieldVale(i, "flg_edit", items[6].toString());
				resTable.addFieldVale(i, "cd_group", items[7].toString());

			}


		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");

		} finally {

		}

	}

}
