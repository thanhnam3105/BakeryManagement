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
 *  デザインスペース情報：登録処理
 *  機能ID：FGEN3280
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3280_Logic extends LogicBase{

	/**
	 * デザインスペース情報登録
	 * : インスタンス生成
	 */
	public FGEN3280_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * デザインスペース情報登録
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
		StringBuffer strSql = null;

		try {
			//ユーザ情報の会社コードを取得
			String strKaisha = userInfoData.getCd_kaisha();
			// 検索条件
			String strCdSeizokojo  = reqData.getFieldVale("table", 0, "cd_seizokojo");
			String strCdShokuba    = reqData.getFieldVale("table", 0, "cd_shokuba");
			String strCdLine       = reqData.getFieldVale("table", 0, "cd_line");

			// 登録情報（":::"で分割）
			String strCdSyurui     = reqData.getFieldVale("table", 0, "cd_syurui");
			String strNmSyurui     = reqData.getFieldVale("table", 0, "nm_syurui");
			String strNmfile       = reqData.getFieldVale("table", 0, "nm_file");

			String[] lstCdSyurui   = null;
			String[] lstNmSyurui   = null;
			String[] lstNmFile     = null;

            // 処理モードを取得する
            String strMode = reqData.getFieldVale("table", 0, "syoriMode");

            strSql = new StringBuffer();

			// SQL文の作成
            strSql.append(" SELECT nm_syurui  ");
            strSql.append("       ,nm_file  ");
            strSql.append(" FROM  tr_shisan_designspace  ");
            strSql.append(" WHERE ");
            strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
            strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
            strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
            strSql.append("  cd_line = '" +  strCdLine + "'");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSql.toString());

            if(lstRecset.size() > 0) {
            	strSql = new StringBuffer();

    			// 削除SQL文の作成
                strSql.append(" DELETE  ");
                strSql.append(" FROM  tr_shisan_designspace  ");
                strSql.append(" WHERE ");
                strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
                strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
                strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
                strSql.append("  cd_line = '" +  strCdLine + "'");

                execDB.execSQL(strSql.toString());
            }

            if (strMode.equals("ADD")) {
            	// 登録情報を分割
            	lstCdSyurui = strCdSyurui.split(":::");
            	lstNmSyurui = strNmSyurui.split(":::");
            	lstNmFile = strNmfile.split(":::");

            	for (int i = 0; i < lstNmSyurui.length; i++) {
            		if (!lstNmSyurui[i].equals("")) {

            			strSql = new StringBuffer();
            			String cd_syurui[] = null;
            			String cd_literal = "";
            			String cd_2nd_literal = "";
            			String nmFile = "";
            			// エラーを防ぐためのチェック（種類とファイル名の数は同じ）
            			if (i < lstCdSyurui.length) {
            				cd_syurui = lstCdSyurui[i].split("_");
            				cd_literal = cd_syurui[0];
            				cd_2nd_literal = cd_syurui.length > 1 ?  cd_syurui[1] : "";
            			}
            			if (i < lstNmFile.length)  nmFile = lstNmFile[i];

                        strSql.append("INSERT INTO tr_shisan_designspace (  ");
                        strSql.append("  cd_kaisha,  ");
                        strSql.append("  cd_seizokojo,  ");
                        strSql.append("  cd_shokuba,  ");
                        strSql.append("  cd_line,  ");
                        strSql.append("  no_hyoji,  ");
                        strSql.append("  cd_literal,  ");
                        strSql.append("  cd_2nd_literal,  ");
                        strSql.append("  nm_syurui,  ");
                        strSql.append("  nm_file,  ");
                        strSql.append("  id_toroku,  ");
                        strSql.append("  dt_toroku,  ");
                        strSql.append("  id_koshin,  ");
                        strSql.append("  dt_koshin  ");
                        strSql.append(")  ");
                        strSql.append("VALUES  ");
                        strSql.append("(  ");
                        strSql.append(      strKaisha  + ",  " );
                        strSql.append("  '" + strCdSeizokojo  + "',  ");
                        strSql.append("  '" + strCdShokuba + "',  ");
                        strSql.append("  '"  + strCdLine  + "',   ");
                        strSql.append("  '"  + i  + "',   ");
                        strSql.append("  '" + cd_literal + "',  ");
                        strSql.append("  '" + cd_2nd_literal + "',  ");
                        strSql.append("  '" + lstNmSyurui[i] + "',  ");
                        strSql.append("  '"  + nmFile  + "',   ");

                        strSql.append("  " + userInfoData.getId_user() + ",  ");
                        strSql.append("  GETDATE(),  ");
                        strSql.append("  " + userInfoData.getId_user() + ",  ");
                        strSql.append("  GETDATE()   ");
                        strSql.append(")  ");

                        execDB.execSQL(strSql.toString());
            		}
            	}
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
