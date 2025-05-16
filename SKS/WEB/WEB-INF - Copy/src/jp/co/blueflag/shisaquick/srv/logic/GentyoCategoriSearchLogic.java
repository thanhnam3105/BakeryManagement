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
 * コンボ用：カテゴリ情報検索DB処理
 *  : コンボ用：カテゴリ情報を検索する。
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoCategoriSearchLogic extends LogicBase{

	/**
	 * コンボ用：カテゴリ情報検索DB処理用コンストラクタ
	 * : インスタンス生成
	 */
	public GentyoCategoriSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：ジャンル情報取得SQL作成
	 *  : ジャンルコンボボックス情報を取得するSQLを作成。
	 * @param reqKind : 機能リクエストデータ
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

		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			strSql = createSQL(reqData, strSql);

			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageCategoryCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");
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
	 * カテゴリ取得SQL作成
	 *  : カテゴリを取得するSQLを作成。
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
			String userId = null;
			String gamenId = null;
			String kinoId = null;
			String dataId = null;

			//ユーザIDの取得
			userId = reqData.getFieldVale(0, 0, "id_user");
			//画面IDの取得
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//データIDを設定
					kinoId = userInfoData.getId_kino().get(i).toString();
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL文の作成
			strSql.append("SELECT cd_category, nm_category");
			strSql.append(" FROM ma_category");

			//リテラルマスタメンテナンス画面
			if (gamenId.equals("300")) {
				if (kinoId == null) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("300")){
							//データIDを設定
							kinoId = userInfoData.getId_kino().get(i).toString();
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				if (kinoId.equals("10")) {	//編集（一般）
					strSql.append(" WHERE flg_edit = 1 AND flg_gencho = 1 AND cd_category <> 'C_hattyuusaki'");

				} else { //システム管理者
					strSql.append(" WHERE flg_gencho = 1 AND cd_category <> 'C_hattyuusaki'");
				}
			}
			strSql.append(" ORDER BY cd_category");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：カテゴリパラメーター格納
	 *  : カテゴリコンボボックス情報をレスポンスデータへ格納する。
	 * @param lstCategoryCmb : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageCategoryCmb(List<?> lstCategoryCmb, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstCategoryCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstCategoryCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_category", items[0].toString());
				resTable.addFieldVale(i, "nm_category", items[1].toString());

			}

			if (lstCategoryCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_category", "");
				resTable.addFieldVale(0, "nm_category", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

}
