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
 * 【QP@40404】
 *  デザインスペース情報：一覧検索
 *  機能ID：FGEN3260
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3260_Logic extends LogicBase{

	/**
	 * デザインスペース情報一覧検索
	 * : インスタンス生成
	 */
	public FGEN3260_Logic() {
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
			// デザインスペースアップロード後の画面再ロード用
			String strMovement_condition = toString(userInfoData.getMovement_condition());

			String strkeyKojo = "";
			String strkeyShokuba = "";
			String strkeyLine    = "";

			if (strMovement_condition.length() > 3) {
				strkeyKojo    = toString(userInfoData.getMovement_condition().get(1));
				strkeyShokuba = toString(userInfoData.getMovement_condition().get(2));
				strkeyLine    = toString(userInfoData.getMovement_condition().get(3));
			}

			//ユーザ情報の会社コードを取得
			String strKaisha = userInfoData.getCd_kaisha();

			// 製造工場
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			// 職場
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			// 製造ライン
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");

			// 種類：カテゴリコードを"_" で結合
			String cd_syurui      = reqData.getFieldVale("table", 0, "cd_syurui");
			String cd_literal     = "";
			String cd_2nd_literal = "";
			String strTmp[]       = null;

			// 検索キーにすべて空白は許可しない
			if (strCdSeizokojo.equals("") && strCdShokuba.equals("") && strCdLine.equals("") && cd_syurui.equals("")) {
				// アップロード後の画面再ロード時、前回の検索キーで再取得
				if (!strkeyKojo.equals("")) {
					strCdSeizokojo = strkeyKojo;
					strCdShokuba   = strkeyShokuba;
					strCdLine      = strkeyLine;
				} else {
					// メニューからのロード時は空白
					return null;

				}
			}

			// 検索条件：会社
			strWhere.append(" T402.cd_kaisha = " + strKaisha );

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
			if(!cd_syurui.equals("")){
				strTmp         = cd_syurui.split("_");
				cd_literal     = strTmp[0];
				cd_2nd_literal = strTmp.length > 1 ?  strTmp[1] : "";

				strWhere.append(" AND  T402.cd_literal = '" + cd_literal + "'");
				strWhere.append(" AND  T402.cd_2nd_literal = '" + cd_2nd_literal + "'");
			}

			// SQL文の作成
			strSql.append(" SELECT  ");
			strSql.append("    T402.cd_seizokojo  ");
			strSql.append("   ,T402.cd_shokuba  ");
			strSql.append("   ,T402.cd_line  ");
			strSql.append("   ,T402.cd_literal  ");
			strSql.append("   ,T402.cd_2nd_literal  ");
			strSql.append("   ,T402.nm_syurui  ");
			strSql.append("   ,T402.nm_file  ");
			strSql.append("   ,M101.nm_user AS nm_koshin  ");
			strSql.append("   ,CONVERT(VARCHAR,T402.dt_koshin,120) AS dt_koshin  ");
			strSql.append("   ,LST.nm_busho   ");
			strSql.append("   ,LST.nm_shokuba   ");
			strSql.append("   ,LST.nm_line   ");
			strSql.append(" FROM  tr_shisan_designspace  AS T402 ");
			strSql.append(" LEFT JOIN  ");
			strSql.append("  (SELECT M405.cd_kaisha ");
			strSql.append("        , M405.cd_seizokojo ");
			strSql.append("        , M405.cd_shokuba ");
			strSql.append("        , M405.cd_line ");
			strSql.append("        , M104.nm_busho ");
			strSql.append("        , M405.nm_line ");
			strSql.append("        , M404.nm_shokuba ");
			strSql.append("   FROM ma_line M405 ");
			strSql.append("   LEFT JOIN ma_shokuba M404 ");
			strSql.append("   ON   (M405.cd_kaisha = M404.cd_kaisha ");
			strSql.append("    AND  M405.cd_seizokojo = M404.cd_seizokojo ");
			strSql.append("    AND  M405.cd_shokuba = M404.cd_shokuba) ");
			strSql.append("   LEFT JOIN ma_busho M104 ");
			strSql.append("   ON  (M404.cd_kaisha = M104.cd_kaisha ");
			strSql.append("    AND  M404.cd_seizokojo = M104.cd_busho) ");
			strSql.append("   WHERE M104.cd_kaisha = " + strKaisha );
			strSql.append("   GROUP BY M405.cd_kaisha ");
			strSql.append("          , M405.cd_seizokojo   ");
			strSql.append("          , M405.cd_shokuba   ");
			strSql.append("          , M405.cd_line  ");
			strSql.append("          , M104.nm_busho ");
			strSql.append("          ,M405.nm_line ");
			strSql.append("          ,M404.nm_shokuba)   AS LST ");
			strSql.append("   ON (T402.cd_kaisha = LST.cd_kaisha ");
			strSql.append("    AND T402.cd_seizokojo = LST.cd_seizokojo ");
			strSql.append("    AND T402.cd_shokuba = LST.cd_shokuba ");
			strSql.append("    AND T402.cd_line = LST.cd_line) ");
			strSql.append("   LEFT JOIN ma_user M101 ");
			strSql.append("   ON   M101.id_user = T402.id_koshin ");

			strSql.append(" WHERE  " + strWhere);
			// 種類名称順
			strSql.append(" ORDER BY   ");
			strSql.append("    T402.cd_seizokojo  ");
			strSql.append("   ,T402.cd_shokuba  ");
			strSql.append("   ,T402.cd_line  ");
			strSql.append("   ,T402.nm_syurui  ");

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
			//メニューからのロード時はlstMstDataが nullの為
			if ((lstMstData== null) || (lstMstData.size() == 0)) {
				// データが取得できない時：エラーのしない為（jsのメッセージを返したい）
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

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
					resTable.addFieldVale(i, "cd_seizokojo", toString(items[0],""));
					resTable.addFieldVale(i, "cd_shokuba", toString(items[1],""));
					resTable.addFieldVale(i, "cd_line", toString(items[2],""));
					resTable.addFieldVale(i, "cd_literal", toString(items[3],""));
					resTable.addFieldVale(i, "cd_2nd_literal", toString(items[4],""));
					resTable.addFieldVale(i, "nm_syurui", toString(items[5],""));
					resTable.addFieldVale(i, "nm_file", toString(items[6],""));
					resTable.addFieldVale(i, "nm_koshin", toString(items[7],""));
					resTable.addFieldVale(i, "dt_koshin", toString(items[8],""));
					resTable.addFieldVale(i, "nm_seizokojo", toString(items[9],""));
					resTable.addFieldVale(i, "nm_shokuba", toString(items[10],""));
					resTable.addFieldVale(i, "nm_line", toString(items[11],""));
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
