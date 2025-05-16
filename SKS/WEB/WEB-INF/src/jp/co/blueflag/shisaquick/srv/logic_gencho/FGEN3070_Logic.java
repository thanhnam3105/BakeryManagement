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
 * 【QP@30297】
 *  コストテーブル承認処理
 *  : コストテーブルを承認登録する。
 *  機能ID：FGEN3060
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3070_Logic extends LogicBase{

    /**
     * コストテーブル承認処理
     * : インスタンス生成
     */
    public FGEN3070_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * コストテーブル承認処理
     *  : コストテーブルを承認登録する。
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

       List<?> lstRecset = null;

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
           //リストの破棄
           removeList(lstRecset);

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
     * @author TT.Nishigawa
     * @since  2009/10/21
     */
    private void setData(
             RequestResponsKindBean resKind
            ,RequestResponsKindBean reqData
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning {

        //レコード値格納リスト
        List<?> lstRecset = null;

        //レコード値格納リスト
        StringBuffer strSqlBuf = null;

        try {

            //テーブル名
            String strTblNm = "xmlFGEN3070";

            // レスポンスデータにテーブル追加
            resKind.addTableItem(strTblNm);


        	// ①データ取得SQL作成
            strSqlBuf = this.createSQL(reqData);

            // ②共通クラス　データベース検索を用いてSQL実行
            super.createSearchDB();

            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // 確認済みでない場合
			if (lstRecset.size() > 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000337", lstRecset.toString(), "", "");
			}

            // ④追加したテーブルへレコード格納
            this.storageData(lstRecset, resKind.getTableItem(strTblNm));


        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        	//リストの破棄
            removeList(lstRecset);

            if (searchDB != null) {
                //セッションの解放
                this.searchDB.Close();
                searchDB = null;

            }

            //変数の削除
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

            // 検索条件取得
            String strCdMaker    = toString(userInfoData.getMovement_condition().get(1));
            String strCdHouzai   = toString(userInfoData.getMovement_condition().get(2));
            String strNoHansu    = toString(userInfoData.getMovement_condition().get(3));

            strSql.append("SELECT ");
            strSql.append("   * ");
            strSql.append("FROM ma_cost_list   ");
            strSql.append("    ma_list   ");
            strSql.append("WHERE ");
            strSql.append("    id_kakunin IS NULL ");
            strSql.append("AND  ");
            strSql.append("    cd_maker = '" + strCdMaker + "'");
            strSql.append("AND  ");
            strSql.append("    cd_houzai = '" + strCdHouzai + "'");
            strSql.append("AND  ");
            strSql.append("    no_hansu = " + strNoHansu );

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }

        return strSql;
    }


	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstGenkaHeader
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

        } catch (Exception e) {

            this.em.ThrowException(e, "");

        } finally {

        }
    }
}
