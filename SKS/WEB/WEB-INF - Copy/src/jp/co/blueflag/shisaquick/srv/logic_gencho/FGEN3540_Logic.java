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
 *  ベース単価承認処理
 *  : ベース単価を承認登録する。
 *  機能ID：FGEN3540
 *
 * @author BRC Koizumi
 * @since  2016/09/07
 */
public class FGEN3540_Logic extends LogicBase{

    /**
     * ベース単価承認処理
     * : インスタンス生成
     */
    public FGEN3540_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * ベース単価承認処理
     *  : ベース単価を承認登録する。
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

        //レスポンスデータクラス
        RequestResponsKindBean resKind = null;

        try {

            //レスポンスデータ（機能）生成
            resKind = new RequestResponsKindBean();

            // トランザクション開始
            super.createExecDB();

            execDB.BeginTran();

            update(reqData);

            // コミット
            execDB.Commit();

            // 機能IDの設定
            String strKinoId = reqData.getID();

            resKind.setID(strKinoId);

            // テーブル名の設定
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // レスポンスデータの形成
            storageData(resKind.getTableItem(strTableNm));

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
            if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

        }
        return resKind;
    }

    /**
     * 承認更新処理
     * @param reqData：リクエストデータ
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void update(
            RequestResponsKindBean reqData
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        //SQL格納用
        StringBuffer strSql = new StringBuffer();

        try {

            String strCdMaker    = reqData.getFieldVale("base_price_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("base_price_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("base_price_list", 0, "no_hansu");
            String strChkShonin  = reqData.getFieldVale("base_price_list", 0, "chk_shonin");

            // 承認時は確認済みであること
            if (!strChkShonin.equals("")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    id_kakunin IS NOT NULL ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    no_hansu = " + strNoHansu );

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() <= 0) {
                    em.ThrowException(ExceptionKind.一般Exception,"E000337" ,"","","");
                }
            }

            // 確認者と承認者は異なること
            if (!strChkShonin.equals("")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    id_kakunin = " + userInfoData.getId_user() + "  ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    no_hansu = " + strNoHansu );

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.一般Exception,"E000339" ,"","","");
                }
            }

            strSql.append("UPDATE ma_base_price_list SET  ");

            if(!strChkShonin.equals("")){
                strSql.append("  id_shonin  = " + userInfoData.getId_user() + ",  ");
                strSql.append("  dt_shonin  = GETDATE(),  ");
            } else {
                strSql.append("  id_shonin  = NULL,  ");
                strSql.append("  dt_shonin  = NULL,  ");
            }

            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

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
