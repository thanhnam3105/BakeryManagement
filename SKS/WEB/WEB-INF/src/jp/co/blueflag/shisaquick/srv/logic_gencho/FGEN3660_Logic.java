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
 *  コスト版数MAX値取得
 *  : コスト版数MAX値を取得する。
 *  機能ID：FGEN3660
 *
 * @author BRC Koizumi
 * @since  2016/10/21
 */
public class FGEN3660_Logic extends LogicBase{

    private String strCdMaker = "";
    private String strCdHouzai = "";

	/**
     * コスト版数MAX値取得処理
     * : インスタンス生成
     */
    public FGEN3660_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * コスト版数MAX値取得
     *  : コスト版数MAX値を取得する。
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
     * @author BRC Koizumi
     * @since  2016/09/07
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
            String strTblNm = "xmlFGEN3660";

            // レスポンスデータにテーブル追加
            resKind.addTableItem(strTblNm);

            	this.strCdMaker = toString(userInfoData.getMovement_condition().get(1));		// メーカーコード
            	this.strCdHouzai = toString(userInfoData.getMovement_condition().get(2));		// 版の包材コード

            	// ①データ取得SQL作成
                strSqlBuf = this.createSQL(reqData);

                // ②共通クラス　データベース検索を用いてSQL実行
                super.createSearchDB();
                lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                // 検索結果がない場合
    			if (lstRecset.size() == 0){
    				this.emptyData(resKind.getTableItem(strTblNm));
    			}

                // ④追加したテーブルへレコード格納
                this.storageData(lstRecset, resKind.getTableItem(strTblNm));


        } catch (Exception e) {
            this.em.ThrowException(e, "ベース単価　データ検索処理が失敗しました。");

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
        StringBuffer strWork = new StringBuffer();
        StringBuffer strWhere = new StringBuffer();

        try {

            // メーカーコード
            if(!this.strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" cd_maker = " + this.strCdMaker);
            }

            // ベース包材コード
            if(!this.strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" cd_houzai = " + this.strCdHouzai);
            }

            // 有効開始日
//            if(strWork.length() > 0){
//				strWork.append(" AND ");
//			}
//			strWork.append(" dt_yuko < GETDATE()  ");

            // SQL文の作成
            strSql.append("SELECT ");
            strSql.append("    MAX(no_hansu) as no_hansu ");	// 版数（最大値）
            strSql.append("FROM ");
            strSql.append("    ma_cost_list ");

            if(strWork.length() > 0){
                strWhere.append(" WHERE ");
                strWhere.append(strWork);
            }

            strSql.append(strWhere);

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }
        return strSql;
    }

    /**
     * パラメーター格納
     *  : ステータス履歴画面へのレスポンスデータへ格納する。
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void emptyData(
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

            // 結果をレスポンスデータに格納
            resTable.addFieldVale(0, "no_hansu",  "1");					// 版数

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

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

            Object items = (Object) lstGenkaHeader.get(0);

            // 結果をレスポンスデータに格納
            resTable.addFieldVale(0, "no_hansu", toString(items,""));		// 版数

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

    }

}
