package jp.co.blueflag.shisaquick.srv.logic_gencho;

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
 * 【QP@40404】
 *  資材手配入力：品名マスタ検索
 *  機能ID：FGEN3430
 *
 * @author E.Kitazawa
 * @since  2014/09/24
 */
public class FGEN3430_Logic extends LogicBase{

	/**
	 * 資材手配入力：品名マスタ検索
	 * : インスタンス生成
	 */
	public FGEN3430_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 資材手配入力：品名マスタ検索
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

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "品名マスタ検索処理が失敗しました。");

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
	 * @return strSql：検索条件SQL
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

		try {
			//ユーザ情報の会社コードを取得
			String strKaisha = userInfoData.getCd_kaisha();
			// 製品コード
			String cdSeihin = reqData.getFieldVale("table", 0, "cd_seihin");
			if (cdSeihin.equals("")) {
				em.ThrowException(ExceptionKind.一般Exception,"E000001","製品コード","","");
			}

			// SQL文の作成
			strSql.append(" SELECT name_hinmei  ");
			strSql.append("       ,name_nisugata  ");
			strSql.append(" FROM  ma_Hinmei_Mst  ");
			strSql.append(" WHERE cd_kaisha = ");
			strSql.append(strKaisha);
			strSql.append("  AND CONVERT(int,cd_hinmei) = ");
			strSql.append(cdSeihin);

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
			if (lstMstData.size() > 0) {
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
					resTable.addFieldVale(i, "name_hinmei", toString(items[0],""));
					resTable.addFieldVale(i, "name_nisugata", toString(items[1],""));

				}

			} else {
					// データが取得できない時：エラーのしない為
					resTable.addFieldVale(0, "flg_return", "");
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
