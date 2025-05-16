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
 *  承認版取得処理
 *  機能ID：FGEN3090
 *
 * @author TT.nishigawa
 * @since  2014/05/08
 */
public class FGEN3090_Logic extends LogicBase{

    /**
     * コストテーブル取得処理
     * : インスタンス生成
     */
    public FGEN3090_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * コストテーブル取得
     *  : コストテーブル情報を取得する。
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
            String strTblNm = "xmlFGEN3090";

            // レスポンスデータにテーブル追加
            resKind.addTableItem(strTblNm);

            // ①データ取得SQL作成
            strSqlBuf = this.createSQL(reqData);

            // ②共通クラス　データベース検索を用いてSQL実行
            super.createSearchDB();
            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // 検索結果がない場合
//			if (lstRecset.size() == 0){
//				em.ThrowException(ExceptionKind.警告Exception,"W000420", lstRecset.toString(), "", "");
//			}

            // ④追加したテーブルへレコード格納
            this.storageData(lstRecset, resKind.getTableItem(strTblNm));

        } catch (Exception e) {
            this.em.ThrowException(e, "コストテーブル参照　データ検索処理が失敗しました。");

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

            // 検索条件を取得する

        	// メーカー名
        	String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");

        	// 包材
        	String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");


            // メーカー名
            if(!strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append("  LST.cd_maker = " + strCdMaker);
            }

            // 包材
            if(!strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" LST.cd_houzai = " + strCdHouzai);
            }

            // 確認者（確認済み）
            if(strWork.length() > 0){
                strWork.append(" AND ");
            }
            strWork.append(" LST.id_kakunin IS NOT NULL");

            // 承認者（承認済み）
            if(strWork.length() > 0){
                strWork.append(" AND ");
            }
            strWork.append(" LST.id_shonin IS NOT NULL");

            // 有効開始日
            if(strWork.length() > 0){
                strWork.append(" AND ");
            }
            strWork.append(" LST.dt_yuko <= GETDATE()");

            // SQL文の作成
            strSql.append("SELECT ");
            strSql.append("     LST.no_hansu ");
            strSql.append("FROM ");
            strSql.append("    ma_cost_list LST  ");

            if(strWork.length() > 0){
                strWhere.append(" WHERE ");
                strWhere.append(strWork);
            }

            strSql.append(strWhere);
            strSql.append("  ORDER BY no_hansu ASC");

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

            for (int i = 0; i < lstGenkaHeader.size(); i++) {

                // 処理結果の格納
                resTable.addFieldVale(i, "flg_return", "true");
                resTable.addFieldVale(i, "msg_error", "");
                resTable.addFieldVale(i, "no_errmsg", "");
                resTable.addFieldVale(i, "nm_class", "");
                resTable.addFieldVale(i, "cd_error", "");
                resTable.addFieldVale(i, "msg_system", "");

                Object items = (Object) lstGenkaHeader.get(i);

                // 結果をレスポンスデータに格納
                resTable.addFieldVale(i, "no_hansu", toString(items,""));

            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

    }
}
