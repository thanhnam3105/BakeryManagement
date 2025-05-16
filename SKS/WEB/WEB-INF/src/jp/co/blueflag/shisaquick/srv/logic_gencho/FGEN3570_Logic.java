package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *  ベース単価削除処理
 *  : ベース単価情報を削除する。
 *  機能ID：FGEN3570
 *
 * @author BRC Koizumi
 * @since  2016/09/07
 */
public class FGEN3570_Logic extends LogicBase{

	/**
	 * ベース単価取得処理
	 * : インスタンス生成
	 */
	public FGEN3570_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
     * ベース単価削除
     *  : ベース単価情報を削除する。
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
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
        StringBuffer strSql = new StringBuffer();

        try {
			// トランザクション開始
            super.createExecDB();

            execDB.BeginTran();

			// ①データ取得SQL作成
            strSql = this.createSQL(reqData);
            // コミット
            execDB.Commit();

            // 機能IDの設定
            String strKinoId = reqData.getID();

            resKind.setID(strKinoId);

		} catch (Exception e) {
            //----------------
            if (execDB != null) {
                // ロールバック
                execDB.Rollback();
            }
            //----------------

			this.em.ThrowException(e, "ベース単価登録・承認　データ削除処理が失敗しました。");

		} finally {
            if (execDB != null) {
                // セッションのクローズ
                execDB.Close();
                execDB = null;
            }

            //変数の削除
            strSql = null;

			// リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				// セッションの解放
				this.searchDB.Close();
				searchDB = null;
			}

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

			// 検索条件取得
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");
			String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");
			String strNoHansu = reqData.getFieldVale(0, 0, "no_hansu");

            // ベース単価一覧の存在チェックを行う
            StringBuffer strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_base_price_list   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // データが存在しない場合
            if(lstRecset.size() == 0) {
            	// 対象データが存在しません。
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","ベース単価一覧","削除","");
             }

            // ベース単価の存在チェックを行う
            strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_base_price   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // データが存在しない場合
            if(lstRecset.size() == 0) {
            	// 対象データが存在しません。
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","ベース単価","削除","");
             }

            // ベース単価削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_base_price  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL実行
            execDB.execSQL(strSql.toString());

            // ベース単価一覧削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_base_price_list  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL実行
            execDB.execSQL(strSql.toString());

            // コストテーブル削除
            strSql = new StringBuffer();
            strSql.append("DELETE cost FROM ma_cost as cost ");
            strSql.append(" INNER JOIN ma_cost_list as cost_list ");
            strSql.append(" ON     ");
            strSql.append("  cost.cd_maker   = cost_list.cd_maker AND ");
            strSql.append("  cost.cd_houzai   = cost_list.cd_houzai AND ");
            strSql.append("  cost.no_hansu   = cost_list.no_hansu ");
            strSql.append("WHERE    ");
            strSql.append("  cost_list.cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cost_list.cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  cost_list.no_basehansu   = "  + strNoHansu  + "  ");

            // SQL実行
            execDB.execSQL(strSql.toString());

            // コストテーブルリスト一覧削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost_list  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_basehansu   = "  + strNoHansu  + "        ");

            // SQL実行
            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

/*	*//**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *//*
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
	}*/
}
