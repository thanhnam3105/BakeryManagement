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
 * 【QP@30297】
 *  コストテーブル取得
 *  : コストテーブル情報を取得する。
 *  機能ID：FGEN3030
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3030_Logic extends LogicBase{

    private String strCdMaker = "";
    private String strCdHouzai = "";
    private String strNoHansu = "";
    private String strChkMishiyo = ""; //【KPX@1602367】add

	/**
     * コストテーブル取得処理
     * : インスタンス生成
     */
    public FGEN3030_Logic() {
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
            String strTblNm = "xmlFGEN3030";

            // レスポンスデータにテーブル追加
            resKind.addTableItem(strTblNm);

            // 処理モードを取得する
            String strMode = toString(userInfoData.getMovement_condition().get(0));

            String strBack = toString(userInfoData.getMovement_condition().get(4));

            // 新規
            if (strMode.equals("1")) {

            	// 追加したテーブルへレコード格納
                this.emptyData(resKind.getTableItem(strTblNm));


            }
            // 登録・承認
            else {

            	this.strCdMaker = toString(userInfoData.getMovement_condition().get(1));
            	this.strCdHouzai = toString(userInfoData.getMovement_condition().get(2));
            	this.strNoHansu = toString(userInfoData.getMovement_condition().get(3));
            	this.strChkMishiyo = toString(userInfoData.getMovement_condition().get(5));	// 未使用チェック

            	// ①データ取得SQL作成
                strSqlBuf = this.createSQL(reqData);

                // ②共通クラス　データベース検索を用いてSQL実行
                super.createSearchDB();
                lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                // 検索結果がない場合
    			if (lstRecset.size() == 0){
    				em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
    			}

                // ④追加したテーブルへレコード格納
                this.storageData(lstRecset, resKind.getTableItem(strTblNm));

            }

            resKind.getTableItem(strTblNm).addFieldVale(0, "mode", strMode);
            resKind.getTableItem(strTblNm).addFieldVale(0, "back", strBack);
            resKind.getTableItem(strTblNm).addFieldVale(0, "chk_mishiyo", this.strChkMishiyo);

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

            // メーカー名
            if(!this.strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.cd_maker = " + this.strCdMaker);
            }

            // 包材
            if(!this.strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.cd_houzai = " + this.strCdHouzai);
            }

            // 版数
            if(!this.strNoHansu.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.no_hansu = " + this.strNoHansu);
            }

            // 未使用
            if(strChkMishiyo.equals("1")){
            	if(strWork.length() > 0){
            		strWork.append(" AND ");
            	}
            	strWork.append(" ISNULL(M4.flg_mishiyo,'0') = " + this.strChkMishiyo);
            } else{
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" ISNULL(M4.flg_mishiyo,'0') != '1' ");
			}

            // SQL文の作成
            strSql.append("SELECT ");
            strSql.append("    M1.cd_maker, ");
            strSql.append("    M1.cd_houzai, ");
            strSql.append("    M1.no_hansu, ");
			strSql.append("    CONVERT(VARCHAR,LST.dt_yuko,111) AS dt_yuko,");
			strSql.append("    LST.biko,");
//			strSql.append("    LST.id_toroku,");
			strSql.append("    LST.id_kakunin,");
			strSql.append("    M2.nm_user AS nm_kakunin,");
			strSql.append("    LST.id_shonin,");
			strSql.append("    M3.nm_user AS nm_shonin,");
            strSql.append("    M1.no_row, ");
            strSql.append("    M1.nm_title, ");
            strSql.append("    M1.no_value01, ");
            strSql.append("    M1.no_value02, ");
            strSql.append("    M1.no_value03, ");
            strSql.append("    M1.no_value04, ");
            strSql.append("    M1.no_value05, ");
            strSql.append("    M1.no_value06, ");
            strSql.append("    M1.no_value07, ");
            strSql.append("    M1.no_value08, ");
            strSql.append("    M1.no_value09, ");
            strSql.append("    M1.no_value10, ");
            strSql.append("    M1.no_value11, ");
            strSql.append("    M1.no_value12, ");
            strSql.append("    M1.no_value13, ");
            strSql.append("    M1.no_value14, ");
            strSql.append("    M1.no_value15, ");
            strSql.append("    M1.no_value16, ");
            strSql.append("    M1.no_value17, ");
            strSql.append("    M1.no_value18, ");
            strSql.append("    M1.no_value19, ");
            strSql.append("    M1.no_value20, ");
            strSql.append("    M1.no_value21, ");
            strSql.append("    M1.no_value22, ");
            strSql.append("    M1.no_value23, ");
            strSql.append("    M1.no_value24, ");
            strSql.append("    M1.no_value25, ");
            strSql.append("    M1.no_value26, ");
            strSql.append("    M1.no_value27, ");
            strSql.append("    M1.no_value28, ");
            strSql.append("    M1.no_value29, ");
            strSql.append("    M1.no_value30  ");
            strSql.append("    ,LST.biko_kojo  ");

//【KPX@1602367】add start
//            strSql.append("    ,LST.name_houzai  ");								// ベース包材名
            strSql.append("    ,LST.name_hansu  ");								// 版の包材名
            strSql.append("    ,LST.no_basehansu  ");							// ベース版数
            strSql.append("    ,ISNULL(M4.flg_mishiyo,'0') AS  flg_mishiyo ");								// 未使用フラグ
//【KPX@1602367】add end

            strSql.append("FROM ");
            strSql.append("    ma_cost M1");
			strSql.append("    LEFT JOIN ma_cost_list LST ON  ");
			strSql.append("    M1.cd_maker  = LST.cd_maker AND ");
			strSql.append("    M1.cd_houzai = LST.cd_houzai AND ");
			strSql.append("    M1.no_hansu  = LST.no_hansu ");
//			strSql.append("    LEFT JOIN ma_user M2 ON M2.id_user = LST.id_toroku");
			strSql.append("    LEFT JOIN ma_user M2 ON M2.id_user = LST.id_kakunin");
			strSql.append("    LEFT JOIN ma_user M3 ON M3.id_user = LST.id_shonin");
			strSql.append("    LEFT JOIN ma_literal M4 ON M4.cd_category = 'maker_name' ");
			strSql.append("      AND M4.cd_literal = LST.cd_maker");
			strSql.append("      AND M4.cd_2nd_literal = LST.cd_houzai");

            if(strWork.length() > 0){
                strWhere.append(" WHERE ");
                strWhere.append(strWork);
            }

            strSql.append(strWhere);
            strSql.append("  ORDER BY no_row ASC");

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

            for (int i = 0; i < 31; i++) {

                // 処理結果の格納
                resTable.addFieldVale(i, "flg_return", "true");
                resTable.addFieldVale(i, "msg_error", "");
                resTable.addFieldVale(i, "no_errmsg", "");
                resTable.addFieldVale(i, "nm_class", "");
                resTable.addFieldVale(i, "cd_error", "");
                resTable.addFieldVale(i, "msg_system", "");

                // 結果をレスポンスデータに格納
                resTable.addFieldVale(i, "cd_maker", "");
                resTable.addFieldVale(i, "cd_houzai", "");
                resTable.addFieldVale(i, "no_hansu",  "");
                resTable.addFieldVale(i, "dt_yuko",  "");
                resTable.addFieldVale(i, "biko",  "");

                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                resTable.addFieldVale(i, "biko_kojo",  "");
                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

                resTable.addFieldVale(i, "flg_mishiyo",  "");		//未使用フラグ【KPX@1602367】mod

                resTable.addFieldVale(i, "id_kakunin",  "");
                resTable.addFieldVale(i, "id_shonin",  "");
                resTable.addFieldVale(i, "no_row", "");
                resTable.addFieldVale(i, "nm_title", "");
                resTable.addFieldVale(i, "no_row",Integer.toString(i));
                resTable.addFieldVale(i, "nm_title", "");

                for (int j = 0; j < 30; j++) {
                    resTable.addFieldVale(i, String.format("no_value%02d", j + 1), "");
                }
            }

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

        	for (int i = 0; i < lstGenkaHeader.size(); i++) {

                // 処理結果の格納
                resTable.addFieldVale(i, "flg_return", "true");
                resTable.addFieldVale(i, "msg_error", "");
                resTable.addFieldVale(i, "no_errmsg", "");
                resTable.addFieldVale(i, "nm_class", "");
                resTable.addFieldVale(i, "cd_error", "");
                resTable.addFieldVale(i, "msg_system", "");

                Object[] items = (Object[]) lstGenkaHeader.get(i);

                // 結果をレスポンスデータに格納
                resTable.addFieldVale(i, "cd_maker", toString(items[0],""));
                resTable.addFieldVale(i, "cd_houzai", toString(items[1],""));
                resTable.addFieldVale(i, "no_hansu", toString(items[2],""));
                resTable.addFieldVale(i, "dt_yuko", toString(items[3],""));
                resTable.addFieldVale(i, "biko", toString(items[4],""));

            	// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
                resTable.addFieldVale(i, "biko_kojo", toString(items[41],""));
                // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

//                resTable.addFieldVale(i, "name_houzai", toString(items[42],""));	// ベース包材名【KPX@1602367】mod
                resTable.addFieldVale(i, "name_hansu", toString(items[42],""));		//コスト包材名【KPX@1602367】mod
                resTable.addFieldVale(i, "no_basehansu", toString(items[43],""));	//ベース版数【KPX@1602367】mod
                resTable.addFieldVale(i, "flg_mishiyo", toString(items[44],""));	//未使用フラグ【KPX@1602367】mod

                resTable.addFieldVale(i, "id_kakunin", toString(items[5],""));
                resTable.addFieldVale(i, "nm_kakunin", toString(items[6],""));
                resTable.addFieldVale(i, "id_shonin", toString(items[7],""));
                resTable.addFieldVale(i, "nm_shonin", toString(items[8],""));
                resTable.addFieldVale(i, "no_row", toString(items[9],""));
                resTable.addFieldVale(i, "nm_title", toString(items[10],""));

                for (int j = 0; j < 30; j++) {
                    resTable.addFieldVale(i, String.format("no_value%02d", j + 1), toString(items[j + 11], ""));
                }
            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

    }

}
