package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【QP@40404】
 *  デザインスペース情報：行削除
 *  機能ID：FGEN3270
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3270_Logic extends LogicBase{

	/**
	 * デザインスペース情報：行削除
	 * : インスタンス生成
	 */
	public FGEN3270_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * デザインスペース情報：行削除
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

		RequestResponsKindBean resKind = null;

		try {

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// トランザクション開始
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // テーブル名の設定
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // レスポンスデータの形成
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
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
	 * データ更新SQL作成
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
		StringBuffer strWhere = new StringBuffer();

		try {
			//ユーザ情報の会社コードを取得
			String strKaisha = userInfoData.getCd_kaisha();
			// 検索条件
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");
			// カテゴリコードを"_" で結合
			String cd_syurui      = reqData.getFieldVale("table", 0, "cd_syurui");
			String strTmp[]       = cd_syurui.split("_");
			String cd_literal     = strTmp[0];
			String cd_2nd_literal = strTmp.length > 1 ?  strTmp[1] : "";

			// 会社、工場、職場、ラインまでの検索条件
			strWhere.append(" WHERE ");
			strWhere.append("  cd_kaisha = '" + strKaisha + "'  AND  ");
			strWhere.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
			strWhere.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
			strWhere.append("  cd_line = '" +  strCdLine + "' ");

			// SQL文の作成
			strSql.append(" SELECT nm_syurui  ");
			strSql.append("       ,nm_file  ");
			strSql.append(" FROM  tr_shisan_designspace  ");
			strSql.append( strWhere + " AND ");
			strSql.append("  cd_literal = '" +  cd_literal + "'  AND  ");
			strSql.append("  cd_2nd_literal = '" +  cd_2nd_literal + "' ");

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			if(lstRecset.size() > 0) {
				// 更新 SQL文の作成：
				// 工場、職場、Line までのデータの更新者、更新日付をUPDATE
				strSql = new StringBuffer();

				strSql.append(" UPDATE  ");
				strSql.append("  tr_shisan_designspace  ");
				strSql.append("  SET dt_koshin = getdate() ");
				strSql.append("    , id_koshin = " + userInfoData.getId_user() );
				strSql.append( strWhere );


				execDB.execSQL(strSql.toString());

				strSql = new StringBuffer();

				// 削除SQL文の作成
				strSql.append(" DELETE  ");
				strSql.append(" FROM  tr_shisan_designspace  ");
				strSql.append( strWhere + " AND ");
				strSql.append("  cd_literal = '" +  cd_literal + "'  AND  ");
				strSql.append("  cd_2nd_literal = '" +  cd_2nd_literal + "' ");

				execDB.execSQL(strSql.toString());

			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
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

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
