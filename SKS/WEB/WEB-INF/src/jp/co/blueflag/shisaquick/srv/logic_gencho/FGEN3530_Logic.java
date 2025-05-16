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
 *  ベース単価登録
 *  : ベース単価情報を登録する。
 *  機能ID：FGEN3530
 *
 * @author BRC Koizumi
 * @since  2016/09/07
 */
public class FGEN3530_Logic extends LogicBase{

	private String[] arryLot    = new String[30];
	private String[][] arryCost = new String[30][30];
	private String[] arrySort = new String[30];
	private String strLiteral2ndCd = "";	// 第二リテラルコード

	/**
     * ベース単価登録処理
     * : インスタンス生成
     */
    public FGEN3530_Logic() {
        //基底クラスのコンストラクタ
        super();

    }

    /**
     * ベース単価登録
     *  : ベース単価情報を登録する。
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
        StringBuffer strSqlBuf = new StringBuffer();

        try {

            // 処理モードを取得する
            String strMode = toString(userInfoData.getMovement_condition().get(0));
            String strCdMaker    = reqData.getFieldVale("base_price_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("base_price_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("base_price_list", 0, "no_hansu");
            String strDtYuko     = reqData.getFieldVale("base_price_list", 0, "dt_yuko");
            String strChkKakunin = reqData.getFieldVale("base_price_list", 0, "chk_kakunin");
            String strChkShonin  = reqData.getFieldVale("base_price_list", 0, "chk_shonin");
            String strNameHansu    = reqData.getFieldVale("base_price_list", 0, "name_hansu");
            String strNameHouzai    = reqData.getFieldVale("base_price_list", 0, "name_houzai");
            String strChkMishiyo = reqData.getFieldVale("base_price_list", 0, "chk_mishiyo");		// 未使用
            String strCategoryCd = reqData.getFieldVale("ma_literal", 0, "cd_category");	// カテゴリコード

            String strBiko       = reqData.getFieldVale("base_price_list", 0, "biko");
            String strBiko_kojo       = "";

            // 新規またはコピーの場合は包材名登録処理を行う
            if ((strMode.equals("1") || strMode.equals("3")) &&
            		strCdHouzai.equals("") && !strNameHouzai.equals("")) {
            	//処理区分：登録　リテラルデータ登録SQL作成処理を呼出す。
            	strSqlBuf = literalKanriInsertSQL(reqData);

				strCdHouzai = strLiteral2ndCd;
            }

            if (strMode.equals("2")) {

                // 未使用チェック有の場合、リテラルマスタの未使用フラグを更新する
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

                	super.createExecDB();
                	execDB.BeginTran();
                	//SQLを実行
    				execDB.execSQL(strUpdSql.toString());
    				execDB.Commit();
                }
            }


            // 新規またはコピーの場合は存在チェックを行う
            if (strMode.equals("1") || strMode.equals("3")) {

                strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
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

                strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
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

            if (strMode.equals("2")) {

                // 承認時は確認済みであること
                if (!strChkShonin.equals("")) {

                    strSqlBuf = new StringBuffer();

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
            }

            strSql.append("UPDATE ma_base_price_list SET  ");
            strSql.append("  dt_yuko    = CONVERT(DateTime, '" + strDtYuko +  "',111),  ");
            strSql.append("  biko       = '" + strBiko + "',  ");
            strSql.append("  biko_kojo       = '" + strBiko_kojo + "',  ");
            strSql.append("  name_houzai       = '" + strNameHouzai + "',  ");
            strSql.append("  name_hansu       = '" + strNameHansu + "',  ");

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

            strSql.append("INSERT INTO ma_base_price_list (  ");
            strSql.append("  cd_maker,  ");
            strSql.append("  cd_houzai,  ");
            strSql.append("  no_hansu,  ");
            strSql.append("  dt_yuko,  ");

            if(!strNameHouzai.equals("")){
                strSql.append("  name_houzai,  ");
            }

            if(!strNameHansu.equals("")){
                strSql.append("  name_hansu,  ");
            }

            if(!strBiko.equals("")){
                strSql.append("  biko,  ");
            }

            if(!strBiko_kojo.equals("")){
                strSql.append("  biko_kojo,  ");
            }

            if(!strChkKakunin.equals("")){
                strSql.append("  id_kakunin,  ");
                strSql.append("  dt_kakunin,  ");
            }

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin  ");
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdMaker  + "',  ");
            strSql.append("  '" + strCdHouzai + "',  ");
            strSql.append("  "  + strNoHansu  + ",   ");
            strSql.append("  CONVERT(DateTime, '" + strDtYuko + "',111),  ");

            if(!strNameHouzai.equals("")){
            	strSql.append("  '" + strNameHouzai + "',   ");
            }

            if(!strNameHansu.equals("")){
            	strSql.append("  '" + strNameHansu + "',   ");
            }

            if(!strBiko.equals("")){
                strSql.append("  '" + strBiko + "',   ");
            }

            if(!strBiko_kojo.equals("")){
                strSql.append("  '" + strBiko_kojo + "',   ");
            }

            if(!strChkKakunin.equals("")){
                strSql.append("  " + userInfoData.getId_user() + ",  ");
                strSql.append("  GETDATE(),  ");
            }

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
            strSql.append(")  ");

            super.createExecDB();
            execDB.BeginTran();
            execDB.execSQL(strSql.toString());

            // 削除
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_base_price  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

            for (int i = 0; i < 31; i++ ) {

                strSql = new StringBuffer();

                strSql.append("INSERT INTO ma_base_price (  ");
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

                String strTitle = reqData.getFieldVale("base_price", i, "nm_title");

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
     * ベース単価値をソートする
     *  : ベース単価値を行と列でソートする。
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
	    		String strValue = reqData.getFieldVale("base_price", 0, strKey);
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

	        		String strValue = reqData.getFieldVale("base_price", row + 1, s.getKey());

	        		if(strValue.equals("")){
	        			strValue = "@";
	        		} else {
	        			strValue = String.format("%.3f", new BigDecimal(strValue));
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

    /**
	 * リテラルデータ登録SQL作成 : リテラルデータ登録用　SQLを作成し、各データをDBに登録する。
	 *
	 * @param reqData
	 *            ：リクエストデータ
	 * @param strSql
	 *            ：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {

			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale("ma_literal", 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale("ma_literal", 0, "cd_literal");
			// リテラル名
			String strLiteralNm = requestData.getFieldVale("ma_literal", 0, "nm_literal");
			// 表示値
			String strSortNo = "";
			// 備考
			String strBiko = "";
			// 編集可否
			String strFlgEdit = "1";

			// 第二リテラル名
			String strLiteral2ndNm = requestData.getFieldVale("ma_literal", 0, "nm_2nd_literal");
			// 第２リテラル表示値
			String str2ndSortNo = "";

			// リテラルデータ登録SQL作成
			strSql.append("INSERT INTO ma_literal (");
			strSql.append(" cd_category");
			strSql.append(" ,cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,value1");
			strSql.append(" ,value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,cd_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");

			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			try {

				 super.createExecDB();
		         execDB.BeginTran();

				// トランザクション開始
				super.createSearchDB();

				//第２リテラル名が入力されている場合、第２リテラルの登録

				/*** 第２リテラルコードの取得 start **************************************/
				// 採番処理
				int intNoSeq = 0;

				// リテラルコードの採番SQL作成
				strSelSql.append("SELECT top 1 cd_2nd_literal, no_2nd_sort, no_sort");
				strSelSql.append(" FROM ma_literal ");
				strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
				strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
				strSelSql.append(" ORDER BY cd_2nd_literal desc ");
				//SQLを実行
				lstRecset = searchDB.dbSearch(strSelSql.toString());
				if (lstRecset.size() > 0) {
					// 採番結果を退避

					Object[] items = (Object[]) lstRecset.get(0);
					// 第２リテラルコード
					if (items[0].toString().equals("")){
						intNoSeq = 0;
					}else{
						intNoSeq = Integer.parseInt(items[0].toString()); //001 → 1
					}
					strLiteral2ndCd = String.valueOf(intNoSeq + 1);  //＋1して文字列に

					// 第２リテラル表示順
					if(items[1] != null){
						if (items[1].toString().equals("")){
							intNoSeq = 0;
						}else{
							intNoSeq = Integer.parseInt(items[1].toString()); //001 → 1
						}
					} else {
						intNoSeq = 0;
					}
					str2ndSortNo = String.valueOf(intNoSeq + 1);  //＋1して文字列に
					strSortNo = items[2].toString();
				} else {
					strLiteral2ndCd = "1";
					str2ndSortNo = "1";
				}

				strSql.append(" ,'" + strLiteralCd + "'");  //リテラルコード
				/*** 第２リテラルコードの取得 end **************************************/

				// 第２リテラルを使用開始するまで、使っていた（第１）リテラル用のレコードを削除
				strUpdSql.append("DELETE ma_literal");
				strUpdSql.append(" WHERE cd_category = '");
				strUpdSql.append(strCategoryCd + "'");
				strUpdSql.append(" AND cd_literal = '");
				strUpdSql.append(strLiteralCd + "'");
				strUpdSql.append(" AND cd_2nd_literal = ''"); //第２リテラルが空白のものが対象

				execDB.execSQL(strUpdSql.toString());


			/*** リテラル名以下 **************************************/
			strSql.append(" ,'" + strLiteralNm + "'");  //リテラル名
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");  // 備考
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);  // 編集フラグ
			strSql.append(" ,NULL");  // 原資材調達部用カテゴリマスタでは編集しない項目
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");

			strLiteral2ndCd = ("000" + strLiteral2ndCd).substring(("000" + strLiteral2ndCd).length() - 3);
			//第２リテラル名が入力されている場合、第２リテラルの登録
			strSql.append(" ,'" + strLiteral2ndCd + "'");  //第２リテラルコード
			strSql.append(" ,'" + strLiteral2ndNm + "'");  //第２リテラル名
			strSql.append(" ,'" + str2ndSortNo + "'");  //第２リテラル表示順
			strSql.append(" ,'1'");  //第２リテラル使用フラグ
				strSql.append(" ,NULL");  //メールアドレス
				strSql.append(" ,'0'");  //未使用フラグ

			strSql.append(")");

				execDB.execSQL(strSql.toString());		// SQL実行

				//コミット
				execDB.Commit();

			} catch (Exception e) {
				if (execDB != null) {
					// ロールバック
					execDB.Rollback();
				}

				this.em.ThrowException(e, "");
			} finally {
				//リストの破棄
				removeList(lstRecset);
				if (searchDB != null) {
					// セッションのクローズ
					searchDB.Close();
					searchDB = null;
				}
				if (execDB != null) {
					// セッションのクローズ
					execDB.Close();
					execDB = null;
				}
				//変数の削除
				strSelSql = null;
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
