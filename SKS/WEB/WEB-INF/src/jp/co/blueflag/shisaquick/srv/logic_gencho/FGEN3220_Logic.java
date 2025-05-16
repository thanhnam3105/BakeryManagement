package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【QP@40404】
 *  参考資料管理ＤＢ登録
 *  機能ID：FGEN3220
 *
 * @author E.Kitazawa
 * @since  2014/09/04
 */
public class FGEN3220_Logic extends LogicBase{

	/**
     * 参考資料管理ＤＢ登録処理
     * : インスタンス生成
     */
    public FGEN3220_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

    /**
     * 参考資料管理ＤＢ登録
     *  : 参考資料情報を登録する。
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

        //レスポンスデータクラス
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

            // 機能IDの設定
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
            if (execDB != null) {
                // セッションのクローズ
                execDB.Close();
                execDB = null;
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

        //SQL格納用
        StringBuffer strSql = new StringBuffer();

        try {

            String strCdLiteral   = reqData.getFieldVale("table", 0, "cd_literal");
            String strNmLiteral   = reqData.getFieldVale("table", 0, "nm_literal");
            String strFileNm      = reqData.getFieldVale("table", 0, "nm_file");

            strSql.append("UPDATE tr_sankoshiryo SET  ");
            strSql.append("  nm_literal  = '" + strNmLiteral + "',  ");
            strSql.append("  nm_file  = '" + strFileNm + "',  ");
            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_literal  = '" + strCdLiteral + "' ");

            strSql.append("IF @@ROWCOUNT = 0    ");

            strSql.append("INSERT INTO tr_sankoshiryo (  ");
            strSql.append("  cd_literal,  ");
            strSql.append("  nm_literal,  ");
            strSql.append("  nm_file,  ");

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin  ");
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdLiteral + "',  ");
            strSql.append("  '"  + strNmLiteral  + "',   ");
            strSql.append("  '"  + strFileNm  + "',   ");

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
            strSql.append(")  ");

            execDB.execSQL(strSql.toString());


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
