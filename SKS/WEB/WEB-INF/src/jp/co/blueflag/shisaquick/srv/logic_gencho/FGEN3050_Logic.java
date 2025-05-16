package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;

/**
 * 【QP@30297】
 *  コストテーブル登録
 *  : コストテーブル情報を登録する。
 *  機能ID：FGEN3040
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3050_Logic extends LogicBase{

	private String[] arryLot    = new String[30];
	private String[][] arryCost = new String[30][30];
	private String[] arrySort = new String[30];

	/**
     * コストテーブル登録処理
     * : インスタンス生成
     */
    public FGEN3050_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * コストテーブル登録
     *  : コストテーブル情報を登録する。
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

            sortData(reqData);

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

        //SQL格納用
        StringBuffer strSql = new StringBuffer();

        try {

            // 処理モードを取得する
            String strMode = toString(userInfoData.getMovement_condition().get(0));
            String strCdMaker    = reqData.getFieldVale("cost_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("cost_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("cost_list", 0, "no_hansu");
            String strDtYuko     = reqData.getFieldVale("cost_list", 0, "dt_yuko");
            String strBiko       = reqData.getFieldVale("cost_list", 0, "biko");
            String strChkKakunin = reqData.getFieldVale("cost_list", 0, "chk_kakunin");
            String strChkShonin  = reqData.getFieldVale("cost_list", 0, "chk_shonin");
            String strBiko_kojo       = reqData.getFieldVale("cost_list", 0, "biko_kojo");

// 【KPX@1602367】 add start
            String strNameHouzai   = reqData.getFieldVale("cost_list", 0, "name_houzai");
            String strNameHansu    = reqData.getFieldVale("cost_list", 0, "name_hansu");
            String strChkMishiyo = reqData.getFieldVale("cost_list", 0, "chk_mishiyo");		// 未使用
            String strCategoryCd = reqData.getFieldVale("cost_list", 0, "cd_category");	// カテゴリコード
            String strNoBaseHansu    = reqData.getFieldVale("cost_list", 0, "no_basehansu");	// ベース版数

            if (strMode.equals("2")) {
                // 登録（更新）時、リテラルマスタの未使用フラグを更新する
            	// 1:未使用 0:使用
                if (!strChkMishiyo.equals("")) {
                	StringBuffer strUpdSql = new StringBuffer();

                	//SQL文の作成
                	strUpdSql.append("UPDATE ma_literal SET");
                	strUpdSql.append(" flg_mishiyo = '");
                	strUpdSql.append(strChkMishiyo);				// 未使用
                	strUpdSql.append("' ,id_koshin = '");			// 更新者
                	strUpdSql.append(userInfoData.getId_user());
                	strUpdSql.append("' ,dt_koshin = GETDATE()");	// 更新日付
                	strUpdSql.append(" WHERE cd_category = '");
                	strUpdSql.append(strCategoryCd + "'");			// カテゴリコード
                	strUpdSql.append(" AND cd_literal = '");
                	strUpdSql.append(strCdMaker + "'");				// リテラルコード（メーカーコード）
                	strUpdSql.append(" AND cd_2nd_literal = '");
                	strUpdSql.append(strCdHouzai + "'");			// 第二リテラルコード（包材コード）

                	//SQLを実行
                	super.createExecDB();
                	execDB.BeginTran();
                	//SQLを実行
    				execDB.execSQL(strUpdSql.toString());
    				execDB.Commit();
                }
            }
// 【KPX@1602367】 add end

            // 新規またはコピーの場合は存在チェックを行う
            if (strMode.equals("1") || strMode.equals("3")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_cost_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    no_hansu = " + strNoHansu );

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.一般Exception,"E000336","","","");
                }
            }

            // 新規またはコピーの場合は有効日で存在チェックを行う
            if (strMode.equals("1") || strMode.equals("3")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_cost_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "' ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "' ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    CONVERT(VARCHAR, dt_yuko, 112) = '" + strDtYuko.replace("/", "") + "'");

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.一般Exception,"E000338","","","");
                }
            }

            // 【QP@40404】 TT.E Kitazawa 課題対応：一覧より登録時のみチェック --- add start
            if (strMode.equals("2")) {
                // 【QP@40404】 TT.E Kitazawa 課題対応 ------------------------ add end

                // 承認時は確認済みであること
                if (!strChkShonin.equals("")) {

                    StringBuffer strSqlBuf = new StringBuffer();

                    strSqlBuf.append("SELECT ");
                    strSqlBuf.append("   * ");
                    strSqlBuf.append("FROM ma_cost_list   ");
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
            }

            strSql.append("UPDATE ma_cost_list SET  ");
            strSql.append("  dt_yuko    = CONVERT(DateTime, '" + strDtYuko +  "',111),  ");
                strSql.append("  biko       = '" + strBiko + "',  ");
                strSql.append("  biko_kojo       = '" + strBiko_kojo + "',  ");

            // 更新
            if (strMode.equals("2")) {
                if(!strChkKakunin.equals("")){
	                strSql.append("  id_kakunin  = " + userInfoData.getId_user() + ",  ");
	                strSql.append("  dt_kakunin  = GETDATE(),  ");
	            } else {
	                strSql.append("  id_kakunin  = NULL,  ");
	                strSql.append("  dt_kakunin  = NULL,  ");
	            }
            }

            // 承認済みを解除する
            strSql.append("  id_shonin  = NULL,  ");
            strSql.append("  dt_shonin  = NULL,  ");

            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            strSql.append("IF @@ROWCOUNT = 0    ");

            strSql.append("INSERT INTO ma_cost_list (  ");
            strSql.append("  cd_maker,  ");
            strSql.append("  cd_houzai,  ");
            strSql.append("  no_hansu,  ");
            strSql.append("  dt_yuko,  ");

            if(!strBiko.equals("")){
                strSql.append("  biko,  ");
            }

            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
            if(!strBiko_kojo.equals("")){
                strSql.append("  biko_kojo,  ");
            }
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

            if(!strChkKakunin.equals("")){
                strSql.append("  id_kakunin,  ");
                strSql.append("  dt_kakunin,  ");
            }

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin,  ");
// 【KPX@1602367】 add start
            if(!strNameHouzai.equals("")){
                strSql.append("  name_houzai,  ");
            }
            strSql.append("  name_hansu,  ");
            strSql.append("  no_basehansu  ");
// 【KPX@1602367】 add end
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdMaker  + "',  ");
            strSql.append("  '" + strCdHouzai + "',  ");
            strSql.append("  "  + strNoHansu  + ",   ");
            strSql.append("  CONVERT(DateTime, '" + strDtYuko + "',111),  ");

            if(!strBiko.equals("")){
                strSql.append("  '" + strBiko + "',   ");
            }

            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
            if(!strBiko_kojo.equals("")){
                strSql.append("  '" + strBiko_kojo + "',   ");
            }
            // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end

            if(!strChkKakunin.equals("")){
                strSql.append("  " + userInfoData.getId_user() + ",  ");
                strSql.append("  GETDATE(),  ");
            }

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
// 【KPX@1602367】 add start
            if(!strNameHouzai.equals("")){
            	strSql.append(",  '" + strNameHouzai + "',   ");
            }
            strSql.append("  '" + strNameHansu + "' ,  ");
            strSql.append("  '" + strNoBaseHansu + "'   ");
// 【KPX@1602367】 add end
            strSql.append(")  ");

            // トランザクション開始
            super.createExecDB();
            execDB.BeginTran();
            execDB.execSQL(strSql.toString());

            // 削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

            for (int i = 0; i < 31; i++ ) {

                strSql = new StringBuffer();

                strSql.append("INSERT INTO ma_cost (  ");
                strSql.append("  cd_maker,  ");
                strSql.append("  cd_houzai, ");
                strSql.append("  no_hansu,  ");
                strSql.append("  no_row,    ");
                strSql.append("  nm_title, ");
                strSql.append("  no_value01, ");
                strSql.append("  no_value02, ");
                strSql.append("  no_value03, ");
                strSql.append("  no_value04, ");
                strSql.append("  no_value05, ");
                strSql.append("  no_value06, ");
                strSql.append("  no_value07, ");
                strSql.append("  no_value08, ");
                strSql.append("  no_value09, ");
                strSql.append("  no_value10, ");
                strSql.append("  no_value11, ");
                strSql.append("  no_value12, ");
                strSql.append("  no_value13, ");
                strSql.append("  no_value14, ");
                strSql.append("  no_value15, ");
                strSql.append("  no_value16, ");
                strSql.append("  no_value17, ");
                strSql.append("  no_value18, ");
                strSql.append("  no_value19, ");
                strSql.append("  no_value20, ");
                strSql.append("  no_value21, ");
                strSql.append("  no_value22, ");
                strSql.append("  no_value23, ");
                strSql.append("  no_value24, ");
                strSql.append("  no_value25, ");
                strSql.append("  no_value26, ");
                strSql.append("  no_value27, ");
                strSql.append("  no_value28, ");
                strSql.append("  no_value29, ");
                strSql.append("  no_value30  ");
                strSql.append(")  ");
                strSql.append("VALUES  ");
                strSql.append("(  ");
                strSql.append("  '" + strCdMaker  + "',  ");
                strSql.append("  '" + strCdHouzai + "',  ");
                strSql.append("  "  + strNoHansu  + ",   ");
                strSql.append("  "  + toString(i) + ",   ");

                String strTitle = reqData.getFieldVale("cost", i, "nm_title");

                if(strTitle.equals("")){
                    strSql.append("  NULL,    ");
                } else {
                    strSql.append("  '"  + strTitle + "',   ");
                }

                String strValue;

                for(int j = 0; j < 30; j++){

                	if (i == 0) {
                		strValue = this.arryLot[j];
                	} else {
                		strValue = this.arryCost[i - 1][j];
                	}

                	if(strValue.equals("@")){
                        strSql.append("  NULL    ");
                    } else {
                    	//strValue = String.format("%d", Integer.parseInt(strValue));
                    	strSql.append("  "  + strValue);
                    }

                    if(j != 29) {
                        strSql.append(" ,  ");
                    }
                }

                strSql.append(")  ");

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

    /**
     * コスト値をソートする
     *  : コスト値を行と列でソートする。
     * @param reqData : リクエストデータ
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void sortData(RequestResponsKindBean reqData)
    	    throws ExceptionSystem, ExceptionUser, ExceptionWaning
   {

        try {

        	// ロット数を格納する変数を初期化する
        	Arrays.fill(this.arryLot, "@");

        	// コストを格納する変数を初期化する
        	for (int x = 0; x < 30; x++) {
        		for (int y = 0; y < 30; y++) {
        			this.arryCost[x][y] = "@";
        		}
        	}

        	// ロット数をソートする列のハッシュを生成する
        	Map<String, Integer> hashMap = new HashMap<String, Integer>();

        	// ロット数をハッシュに格納する
        	for(int i = 0; i < 30; i++){
	    		String strKey = String.format("no_value%02d", (i + 1));
	    		String strValue = reqData.getFieldVale("cost", 0, strKey);
	    		if (strValue.equals("")) {
	    			continue;
	    		} else {
	    			hashMap.put(strKey, Integer.parseInt(strValue));
	    		}
	    	}

	    	// List 生成 (ソート用)
	        List<Map.Entry<String,Integer>> entries =
	              new ArrayList<Map.Entry<String,Integer>>(hashMap.entrySet());

	        Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {

	            public int compare(
	                  Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
	                return ((Integer)entry1.getValue()).compareTo((Integer)entry2.getValue());
	            }
	        });

	        // ハッシュに入れソートしたロット数を配列に格納する
	        int col = 0;

	        for (Entry<String,Integer> s : entries) {
	        	this.arryLot[col] = s.getValue().toString();
	        	col++;
	        }

	        col = 0;

	        for (Entry<String,Integer> s : entries) {

	        	for(int row = 0; row < 30; row++){

	        		String strValue = reqData.getFieldVale("cost", row + 1, s.getKey());

	        		if(strValue.equals("")){
	        			strValue = "@";
	        		} else {
	        			// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start
	        			strValue = String.format("%.3f", new BigDecimal(strValue));
	        			// 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end
	        		}

	        		arryCost[row][col] = strValue;
	        	}

	            col++;
	        }

	        for(col = 0; col < 30; col++) {

	        	for (int y = 0; y < 30; y++) {
	    			this.arrySort[y] = "@";
	        	}

		        for(int row = 0, idx = 0; row < 30; row++) {
		        	if(!arryCost[row][col].equals("@")) {
		        		arrySort[idx++] = arryCost[row][col];
		        	}
		        }

		        for(int row = 0; row < 30; row++) {
		        	arryCost[row][col] = arrySort[row];
		        }
	        }

        } catch (Exception e) {

            this.em.ThrowException(e, "");

        } finally {

        }
    }
}
