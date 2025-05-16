package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 【QP@40404】
 *  デザインスペース情報：対象カテゴリ内のMAX更新日時の取得
 *  機能ID：FGEN3410
 *
 * @author E.Kitazawa
 * @since  2014/12/03
 */
public class FGEN3410_Logic extends LogicBase{

	/**
	 * デザインスペース情報一覧検索
	 * : インスタンス生成
	 */
	public FGEN3410_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * デザインスペース情報一覧検索
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

		RequestResponsKindBean resKind = null;

		try {

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

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// 検索結果がない場合（ここには来ない）
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
			}

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "デザインスペース情報検索処理が失敗しました。");

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
			//ユーザ情報の会社コードを取得
			String strKaisha = userInfoData.getCd_kaisha();

			// 製造工場
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			// 職場
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			// 製造ライン
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");


			// 検索条件：会社
			strWhere.append(" cd_kaisha = " + strKaisha );

			// 検索条件：設定値
			if (!strCdSeizokojo.equals("")) {
				strWhere.append(" AND  T402.cd_seizokojo = '" + strCdSeizokojo + "'");
			}
			if(!strCdShokuba.equals("")){
				strWhere.append(" AND  T402.cd_shokuba = '" + strCdShokuba + "'");
			}
			if(!strCdLine.equals("")){
				strWhere.append(" AND  T402.cd_line = '" + strCdLine + "'");
			}

			// SQL文の作成
			strSql.append(" SELECT  ");
			strSql.append("   MAX(CONVERT(VARCHAR,dt_koshin,120)) AS max_koshin  ");
			strSql.append(" FROM  tr_shisan_designspace ");
            strSql.append(" WHERE ");
            strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
            strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
            strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
            strSql.append("  cd_line = '" +  strCdLine + "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
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
			// 処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			// データが存在した時
			if (lstMstData.size() > 0) {
				// MAX 更新日時 を取得
				Object item = (Object) lstMstData.get(0);

				// 結果をレスポンスデータに格納
				// データなしの時、nullが返り ""がセットされる
				resTable.addFieldVale(0, "max_koshin", toString(item,""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
