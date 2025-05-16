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
 * 【QP@30297】No.22
 *  コストテーブル削除処理
 *  : コストテーブル情報を削除する。
 *  機能ID：FGEN3110
 *
 * @author E.Kitazawa
 * @since  2014/08/21
 */
public class FGEN3110_Logic extends LogicBase{

	/**
	 * コストテーブル取得処理
	 * : インスタンス生成
	 */
	public FGEN3110_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
     * コストテーブル削除
     *  : コストテーブル情報を削除する。
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

        StringBuffer strSql = new StringBuffer();

        try {

			// テーブル名
			String strTblNm = "xmlFGEN3110";

            //----------------
			// トランザクション開始
            super.createExecDB();
            execDB.BeginTran();
            //----------------
			// ①データ取得SQL作成
            strSql = this.createSQL(reqData);
            //----------------
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
			this.em.ThrowException(e, "コストテーブル登録・承認　データ削除処理が失敗しました。");
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

            // コストテーブル一覧の存在チェックを行う
            StringBuffer strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_cost_list   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // データが存在しない場合
            if(lstRecset.size() == 0) {
            	// 対象データが存在しません。
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","コストテーブル一覧","削除","");
             }

            // 承認済みデータのチェック？


            // コストテーブルの存在チェックを行う
            strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_cost   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // データが存在しない場合
            if(lstRecset.size() == 0) {
            	// 対象データが存在しません。
            	em.ThrowException(ExceptionKind.一般Exception,"E000301","コストテーブル","削除","");
             }

            // コストデータ削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL実行
            execDB.execSQL(strSql.toString());

            // コスト一覧削除　×
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost_list  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL実行
            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
