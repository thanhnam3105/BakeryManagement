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
 *  資材手配入力：試作No検索
 *  機能ID：FGEN3420
 *
 * @author E.Kitazawa
 * @since  2014/09/24
 */
public class FGEN3420_Logic extends LogicBase{

	/**
	 * 資材手配入力：試作No検索
	 * : インスタンス生成
	 */
	public FGEN3420_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 資材手配入力：試作No検索
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
			this.em.ThrowException(e, "試作No検索処理が失敗しました。");

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
            // 製品コード
			String cdSeihin = reqData.getFieldVale("table", 0, "cd_seihin");
			if (cdSeihin.equals("")) {
				em.ThrowException(ExceptionKind.一般Exception,"E000001","製品コード","","");
			}

			// SQL文の作成
			strSql.append(" SELECT distinct  ");
			strSql.append("        T340.cd_shain  ");
			strSql.append("       ,T340.nen  ");
			strSql.append("       ,T340.no_oi  ");
			strSql.append("       ,T340.no_eda  ");
			strSql.append("       ,T340.cd_busho  ");
			strSql.append("       ,T441.st_kenkyu  ");
			strSql.append("       ,T441.st_seisan  ");
			strSql.append("       ,T441.st_gensizai  ");
			strSql.append("       ,T441.st_kojo  ");
			strSql.append("       ,T441.st_eigyo  ");
			strSql.append("       ,T310.saiyo_sample ");
			strSql.append(" FROM  (SELECT   ");
			strSql.append("        T131.cd_shain  ");
			strSql.append("       ,T131.nen  ");
			strSql.append("       ,T131.no_oi  ");
			strSql.append("     FROM  tr_shisaku AS T131 ");
			strSql.append("     WHERE no_seiho1 in (  ");
			strSql.append("       SELECT no_seiho  ");
			strSql.append("       FROM   vw_haigo_header  ");
			strSql.append("       WHERE cd_hin = ");
			strSql.append(  cdSeihin  );
			strSql.append("       )) AS LST  ");
			strSql.append(" LEFT JOIN tr_shisan_shizai AS T340 ");
			strSql.append(" ON (  T340.cd_shain = LST.cd_shain  ");
			strSql.append("  AND  T340.nen = LST.nen  ");
			strSql.append("  AND  T340.no_oi = LST.no_oi)  ");
			strSql.append(" LEFT JOIN tr_shisan_status AS T441 ");
			strSql.append(" ON (  T340.cd_shain = T441.cd_shain  ");
			strSql.append("  AND   T340.nen = T441.nen  ");
			strSql.append("  AND   T340.no_oi = T441.no_oi ");
			strSql.append("  AND   T340.no_eda = T441.no_eda)  ");
			strSql.append(" LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			strSql.append(" ON (  T340.cd_shain = T310.cd_shain  ");
			strSql.append("  AND   T340.nen = T310.nen  ");
			strSql.append("  AND   T340.no_oi = T310.no_oi ");
			strSql.append("  AND   T340.no_eda = T310.no_eda)  ");
			// 営業ステータス：採用のもの（st_eigyo=4）を抽出
			strSql.append(" WHERE  T441.st_eigyo IS NOT NULL AND  T441.st_eigyo = 4  ");
			// 不採用を除く
			strSql.append("   AND  T310.saiyo_sample IS NOT NULL AND  T310.saiyo_sample > 0  ");
			strSql.append(" ORDER BY T340.cd_shain, T340.nen, T340.no_oi, T340.no_eda");

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
					// 試作No（cd_shain、nen、no_oi、no_eda を前ゼロ付加して"-"で結合）
					String strCdshain = "0000000000" + toString(items[0],"");
					strCdshain = strCdshain.substring(toString(items[0],"").length());
					String strNen = "00" + toString(items[1],"");
					strNen = strNen.substring(toString(items[1],"").length());
					String strOi = "000" + toString(items[2],"");
					strOi = strOi.substring(toString(items[2],"").length());
					String strEda = "000" + toString(items[3],"");
					strEda = strEda.substring(toString(items[3],"").length());

					resTable.addFieldVale(i, "no_shisaku", strCdshain + "-" + strNen + "-" + strOi + "-" + strEda);
					resTable.addFieldVale(i, "cd_busho", toString(items[4],""));
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
