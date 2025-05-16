package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.util.List;
import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 原料の製造会社、製造工場の洗い替え
 *  : 試作毎の洗い替え
 * @author isono
 * @since  2009/10/21
 * 
 */
public class CGEN0010_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public CGEN0010_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試算ﾃﾞｰﾀ原料洗い替えの実装
	 * @param cd_shain	：試作CD社員
	 * @param nen		：試作CD年
	 * @param no_oi		：試作CD追番
	 * @param cd_kaisha	：新会社CD
	 * @param cd_kojo	：新工場CD
	 * @param no_eda	：【QP@00342】枝番
	 * @param userInfo	：ユーザー情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ExecLogic(

			BigDecimal          cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			,UserInfoData       userInfo
			
			) 

	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = userInfo;
		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		//検索結果バッファ
		List<?> lstSearchAry = null;
		
		try{

			//DBコネクション生成
			super.createExecDB();								
			super.createSearchDB();
			
			//トランザクション開始
			this.searchDB.BeginTran();
			execDB.setSession(searchDB.getSession());
			
			//【QP@00342】検索SQL生成
			strSQL = makeSearchSQL(cd_shain, nen, no_oi, cd_kaisha, cd_kojo, no_eda);
			
			//DB検索
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			strSQL = null;
			//【QP@00342】更新SQL生成（試算　試作品ﾃｰﾌﾞﾙ更新）
			strSQL = makeExecSQL_T310(cd_shain, nen, no_oi, cd_kaisha, cd_kojo,no_eda);

			//DB更新（試算　試作品ﾃｰﾌﾞﾙ更新）
			this.execDB.execSQL(strSQL.toString());
			
			if ( lstSearchAry.size() >= 0 ) {
				
				for (int i = 0; i < lstSearchAry.size(); i++) {

					strSQL = null;
					Object[] items = (Object[]) lstSearchAry.get(i);
					
					//【QP@00342】更新SQL生成
					strSQL = makeExecSQL(items,no_eda);
					
					if (strSQL == null){
						//洗い替え対象外の場合は、strSQLがnullで帰ってきます。
						//SKIP！
						
					}else{
						//DB更新
						this.execDB.execSQL(strSQL.toString());
						
					}
					
				}
				
			}
			
			//DBコミット
			this.searchDB.Commit();
			
		}catch(Exception e){
			//DBロールバック
			this.searchDB.Rollback();

			//例外のスロー
			this.em.ThrowException(e, "原料の製造会社、製造工場の洗い替えに失敗しました。");
			
		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			
			//ローカル変数の開放
			removeList(lstSearchAry);
			strSQL = null;
			
		}
		
	}

	/**
	 * 洗い替え諸原データ検索用SQLを生成する。
	 * @param cd_shain	：試作CD社員
	 * @param nen		：試作CD年
	 * @param no_oi		：試作CD追番
	 * @param no_eda	：【QP@00342】試作CD枝番
	 * @param cd_kaisha	：新会社CD
	 * @param cd_kojo	：新工場CD
	 * @return　StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL(

			BigDecimal			cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQLバッファ
		StringBuffer ret = new StringBuffer();
		
		try{
			ret.append(" SELECT ");
			ret.append("  T310.cd_shain AS cd_shain ");
			ret.append(" ,T310.nen AS nen ");
			ret.append(" ,T310.no_oi AS no_oi ");
			ret.append(" ,T310.cd_kaisha AS cd_kaisha_SHISAN ");
			ret.append(" ,T310.cd_kojo AS cd_kojo_SHISAN ");
			ret.append(" ," + toString(cd_kaisha) + " AS cd_kaisha_NEW ");
			ret.append(" ," + toString(cd_kojo) + " AS cd_kojo_NEW ");
			ret.append(" ,T120.cd_genryo AS cd_genryo ");
			
			
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　START
//			ret.append(" ,T120.nm_genryo AS nm_genryo_SHISAKU ");
//			ret.append(" ,ISNULL(M993.name_hinmei,T120.nm_genryo) AS nm_genryo_SHISAKU ");
			ret.append(" ,M993.name_hinmei AS nm_genryo_SHISAKU ");
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　END
			
			
			ret.append(" ,T320.tanka_ins AS tanka_SHISAN_ins ");
			ret.append(" ,T320.budomari_ins AS budomari_SHISAN_ins ");
			ret.append(" ,T320.tanka_ma AS tanka_SHISAN_ma ");
			ret.append(" ,T320.budomar_ma AS budomar_SHISAN_ma ");
			ret.append(" ,T402.tanka AS tanka_MASTA ");
			ret.append(" ,T402.budomari AS budomari_MASTA ");
			ret.append(" ,T402_2.tanka AS tanka_MAX ");
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　START	
//			ret.append(" ,T402_2.budomari AS budomari_MIN ");
			ret.append(" ,T402_3.budomari AS budomari_MIN ");
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　END
			ret.append(" ,T120.tanka AS tanka_SHISAKU ");
			ret.append(" ,T120.budomari AS budomari_SHISAKU ");
			ret.append(" ,T320.cd_kotei AS cd_kotei ");
			ret.append(" ,T320.seq_kotei AS seq_kotei ");
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　START
			ret.append(" ,T402_2.cd_kaisha AS cd_kaisha_MAX_TANKA ");
			ret.append(" ,T402_2.cd_busho AS cd_busho_MAX_TANKA ");
			ret.append(" ,T402_3.cd_kaisha AS cd_kaisha_MIN_BUDOMARI ");
			ret.append(" ,T402_3.cd_busho AS cd_busho_MIN_BUDOMARI ");
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　END
			
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　START			
			//新規原料用に取得
			ret.append(" ,T120.nm_genryo AS nm_genryo_N ");
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　END
			
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
			ret.append(" ,genryokojo.flg_shiyo AS flg_shiyo "); //26
//add end   -------------------------------------------------------------------------------
			
			ret.append(" FROM tr_shisan_shisakuhin AS T310 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("        ON T310.cd_shain = T320.cd_shain  ");
			ret.append("       AND T310.nen      = T320.nen  ");
			ret.append("       AND T310.no_oi    = T320.no_oi  ");
			
			//【QP@00342】
			ret.append("       AND T310.no_eda    = T320.no_eda  ");
			
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append("        ON T320.cd_shain = T120.cd_shain  ");
			ret.append("       AND T320.nen      = T120.nen  ");
			ret.append("       AND T320.no_oi    = T120.no_oi  ");
			ret.append("       AND T320.cd_kotei = T120.cd_kotei ");
			ret.append("       AND T320.seq_kotei= T120.seq_kotei ");
			
			
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　START
			ret.append(" LEFT JOIN ma_Hinmei_Mst AS M993 ");
			ret.append("       ON M993.cd_kaisha = " +toString(cd_kaisha) );
			ret.append("       AND M993.cd_hinmei = T120.cd_genryo ");
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　END			
			
			
			
//2010/05/12　シサクイック（原価）要望【案件No3】単価・歩留の取得方法変更　TT.NISHIGAWA　START
//			ret.append(" LEFT JOIN ma_genryokojo AS T402 ");
//			ret.append("        ON " + toString(cd_kaisha) + " = T402.cd_kaisha  ");
//			ret.append("       AND " + toString(cd_kojo) + " = T402.cd_busho   ");
//			ret.append("       AND T120.cd_genryo = T402.cd_genryo  ");
			ret.append(" LEFT JOIN ma_HinTB_Mst AS T402 ");
			ret.append("        ON " + toString(cd_kaisha) + " = T402.cd_kaisha  ");
			ret.append("       AND " + toString(cd_kojo) + " = T402.cd_busho   ");
			ret.append("       AND RIGHT('00000000000'+T402.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo  ");
//2010/05/12　シサクイック（原価）要望【案件No3】単価・歩留の取得方法変更　TT.NISHIGAWA　END
			
			
			
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　START	
//			ret.append(" LEFT JOIN ( ");
//			ret.append("      SELECT  ");
//			ret.append("               T402.cd_kaisha AS cd_kaisha ");
//			ret.append("              ,T402.cd_genryo AS cd_genryo ");
//			ret.append("              ,MAX(T402.tanka) AS tanka ");
//			ret.append("              ,MIN(T402.budomari) AS budomari ");
//			ret.append("      FROM ma_genryokojo AS T402 ");
//			ret.append("      GROUP BY T402.cd_kaisha ");
//			ret.append("              ,T402.cd_genryo  ");
//			ret.append(" ) AS T402_2 ");
//			ret.append("        ON " + toString(cd_kaisha) + " = T402_2.cd_kaisha ");
//			ret.append("       AND T120.cd_genryo = T402_2.cd_genryo ");

			ret.append(" OUTER APPLY  ");
			ret.append("   (SELECT TOP 1 ");
			ret.append("    T402_T.cd_kaisha AS cd_kaisha ");
			ret.append("   ,T402_T.cd_busho AS cd_busho  ");
			ret.append("   ,T402_T.cd_hinmei AS cd_genryo ");
			ret.append("   ,T402_T.tanka AS tanka ");
			ret.append("   FROM ma_HinTB_Mst AS T402_T ");
			ret.append("   WHERE  ");
			ret.append("        " + toString(cd_kaisha) + " = T402_T.cd_kaisha ");
			ret.append("   AND RIGHT('00000000000'+T402_T.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo ");
			ret.append("   ORDER BY T402_T.cd_kaisha ");
			ret.append("         ,  T402_T.cd_hinmei ");
			ret.append("         ,  T402_T.tanka desc ");
			ret.append("    ) AS T402_2 ");
			
			ret.append(" OUTER APPLY  ");
			ret.append("   (SELECT TOP 1 ");
			ret.append("    T402_B.cd_kaisha AS cd_kaisha ");
			ret.append("   ,T402_B.cd_busho AS cd_busho  ");
			ret.append("   ,T402_B.cd_hinmei AS cd_genryo ");
			ret.append("   ,T402_B.budomari AS budomari ");
			ret.append("   FROM ma_HinTB_Mst AS T402_B ");
			ret.append("   WHERE  ");
			ret.append("        " + toString(cd_kaisha) + " = T402_B.cd_kaisha ");
			ret.append("   AND RIGHT('00000000000'+T402_B.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo ");
			ret.append("   ORDER BY T402_B.cd_kaisha ");
			ret.append("         ,  T402_B.cd_hinmei ");
			ret.append("         ,  T402_B.budomari ");
			ret.append("   ) AS T402_3 ");
//2010/05/12　シサクイック（原価）要望【案件No14】単価・歩留の会社・工場コード取得　TT.NISHIGAWA　END
			
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
			ret.append(" LEFT JOIN ma_genryokojo AS genryokojo ");
			ret.append(" ON " + toString(cd_kaisha) + " = genryokojo.cd_kaisha ");
			ret.append(" AND " + toString(cd_kojo) + " = genryokojo.cd_busho ");
			ret.append(" AND T120.cd_genryo = genryokojo.cd_genryo ");
//add end   -------------------------------------------------------------------------------
			
			ret.append(" WHERE ");
			ret.append("       T310.cd_shain = " + toString(cd_shain) + " ");
			ret.append("   AND T310.nen      = " + toString(nen) + " ");
			ret.append("   AND T310.no_oi    = " + toString(no_oi) + " ");
			
			//【QP@00342】
			ret.append("   AND T310.no_eda    = " + toString(no_eda) + " ");
			
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "検索SQLの生成に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * 試算　試作品ﾃｰﾌﾞﾙの会社/工場を更新するSQLを生成
	 * @param cd_shain	：試作CD社員
	 * @param nen		：試作CD年
	 * @param no_oi		：試作CD追番
	 * @param no_eda	：【QP@00342】試作CD枝番
	 * @param cd_kaisha	：新会社CD
	 * @param cd_kojo	：新工場CD
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T310(
			
			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();
		
		try{
			
			ret.append(" UPDATE tr_shisan_shisakuhin ");
			ret.append(" SET ");
			ret.append("     cd_kaisha = " + cd_kaisha + " ");
			ret.append("    ,cd_kojo   = " + cd_kojo + " ");
			ret.append("    ,id_koshin = " + userInfoData.getId_user() + " ");
			ret.append("    ,dt_koshin = GETDATE() ");
			ret.append(" WHERE ");
			ret.append("     cd_shain = " + cd_shain + " ");
			ret.append(" AND nen      = " + nen + " ");
			ret.append(" AND no_oi    = " + no_oi + " ");
			
			//【QP@00342】
			ret.append(" AND no_eda    = " + no_eda + " ");
			
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * 洗い替え実行SQLを生成する
	 * @param items		：洗い替え諸原データ1件分
	 * @param no_eda	：【QP@00342】試作CD枝番
	 * @return　StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL(
			
			Object[] items
			,int no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();
		
		try{
			
			//諸原の収集
			//原料CD
			String strGenryoCD = toString(items[7]);
			//原料名
			String strGenryoNm = toString(items[8]);
			
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　START
			//N原料名
			String strNGenryoNm = toString(items[25]);
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　END
			

//2010/02/15 ADD TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
			//会社CD MAX単価
			String cd_kaisha_MAX_TANKA = toString(items[5]);
			//工場CD MAX単価
			String cd_kojo_MAX_TANKA = toString(items[6]);
			//会社CD MIN歩留
			String cd_kaisha_MIN_BUDOMARI = toString(items[5]);
			//工場CD MIN歩留
			String cd_kojo_MIN_BUDOMARI = toString(items[6]);
//2010/02/15 ADD TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			
			
			
			//原料名が空の場合
			if(strGenryoNm.equals("")){
				
			}
			//原料名が空でない場合
			else{
				//★☆記号削除
				if(strGenryoNm.substring(0, 1).equals("★") || 
						strGenryoNm.substring(0, 1).equals("☆")){
					
					//星記号削除
					strGenryoNm = strGenryoNm.substring(1);
				}
				
			}
			
// 2009/11/10 破棄 isono
//			//単価　原価試算（入力）
//			String strTanka_Ins = toString(items[9],"null");
//			//歩留　原価試算（入力）
//			String strBudomari_Ins = toString(items[10],"null");
			//単価　原価試算（マスタ）
			//String strTanka_Mas = toString(items[11]);
			//歩留　原価試算（マスタ）
			//String strBudomari_Mas = toString(items[12]);
			//単価　試作入力
			String strTanka_Shisaku = toString(items[17],"null");
			//歩留　試作入力
			String strBudomari_Shisaku = toString(items[18],"null");
			//単価　工場原料マスタ
			String strTanka_KOUZYO_MAS = toString(items[13],"null");
			//歩留　工場原料マスタ
			String strBudomari_KOUZYO_MAS = toString(items[14],"null");
			//単価　会社内MAX
			String strTanka_MAX = toString(items[15],"null");
			//歩留　会社内MIN
			String strBudomari_MIN = toString(items[16],"null");
			//試作CD　社員
			String strCd_shain = toString(items[0]);
			//試作CD　年
			String strNen = toString(items[1]);
			//試作CD　追番
			String strNo_oi = toString(items[2]);
			//工程CD
			String strCd_kotei = toString(items[19]);
			//工程SEQ
			String strSeq_kotei = toString(items[20]);
			
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//使用実績
			String strFg_Shiyo = toString(items[26]);
//add end   -------------------------------------------------------------------------------
			
			//編集項目用バッファ
			//原料名
			String strGenryo = "";
			//単価
			String strTanka = "";
			//歩留
			String strBudomari = "";
			
			//編集内容判定
// 2009/11/10 破棄 isono
//			//6　試算　配合テーブルの単価（入力）、歩留（入力）が設定されている場合
//			if(strTanka_Ins.equals("null") && strBudomari_Ins.equals("null")){
//				
//			}else{
//				ret = null;
//				return ret;
//			}
			
			if (!strGenryoCD.equals("") && strGenryoCD.substring(0, 1).equals( "N")){
				//5 原料CDの先頭に”N”（新規原料ｼﾝﾎﾞﾙ）が設定されている場合

//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　START
				//原料名
				//strGenryo = strGenryoNm;
				
				//原料名
				strGenryo = strNGenryoNm;
//2010/05/12　シサクイック（原価）要望【案件No3】原料名の取得方法変更　TT.NISHIGAWA　END
				
				//単価
				strTanka = strTanka_Shisaku;
				//歩留
				strBudomari = strBudomari_Shisaku;

//2010/02/15 UPDATE TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
				//会社CD MAX単価
				cd_kaisha_MAX_TANKA = toString("null");
				//工場CD MAX単価
				cd_kojo_MAX_TANKA = toString("null");
				//会社CD MIN歩留
				cd_kaisha_MIN_BUDOMARI = toString("null");
				//工場CD MIN歩留
				cd_kojo_MIN_BUDOMARI = toString("null");
//2010/02/15 UPDATE TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			
				
			}else if(!strBudomari_KOUZYO_MAS.equals("null") || !strBudomari_KOUZYO_MAS.equals("null")){
				//1 新会社CD、新工場CD、原料CDに該当するマスタ（工場原料マスタ）が存在する場合
				
				//単価
				strTanka = strTanka_KOUZYO_MAS;
				//歩留
				strBudomari = strBudomari_KOUZYO_MAS;

//2010/02/15 UPDATE TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
				//会社CD MAX単価
				cd_kaisha_MAX_TANKA = toString(items[5]);
				//工場CD MAX単価
				cd_kojo_MAX_TANKA = toString(items[6]);
				//会社CD MIN歩留
				cd_kaisha_MIN_BUDOMARI = toString(items[5]);
				//工場CD MIN歩留
				cd_kojo_MIN_BUDOMARI = toString(items[6]);
//2010/02/15 UPDATE TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			
								
			}else if(strBudomari_KOUZYO_MAS.equals("null") && strBudomari_KOUZYO_MAS.equals("null")){
				//2 新会社CD、新工場CD、原料CDに該当するマスタ（工場原料マスタ）が存在しない場合
				
				if (!strTanka_MAX.equals("null") || !strBudomari_MIN.equals("null")){
					//3 新会社CD、原料CDに該当するマスタ（工場原料マスタ）が存在する場合
					
					//原料名
					strGenryoNm = "☆" + strGenryoNm;
					//単価
					strTanka = strTanka_MAX;
					//歩留
					strBudomari = strBudomari_MIN;
					
//2010/02/15 UPDATE TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
					//会社CD MAX単価
					cd_kaisha_MAX_TANKA = toString(items[21]);
					//工場CD MAX単価
					cd_kojo_MAX_TANKA = toString(items[22]);
					//会社CD MIN歩留
					cd_kaisha_MIN_BUDOMARI = toString(items[23]);
					//工場CD MIN歩留
					cd_kojo_MIN_BUDOMARI = toString(items[24]);
//2010/02/15 UPDATE TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			

				}else {
					//4 新会社CD、原料CDに該当するマスタ（工場原料マスタ）が存在しない場合
					
					//原料名
					//strGenryo = "★" + "";
					strGenryo = toString("");
					
					//単価
					strTanka = toString("null");
					//歩留
					strBudomari = toString("null");
					
//2010/02/15 UPDATE TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
					//会社CD MAX単価
					cd_kaisha_MAX_TANKA = toString("null");
					//工場CD MAX単価
					cd_kojo_MAX_TANKA = toString("null");
					//会社CD MIN歩留
					cd_kaisha_MIN_BUDOMARI = toString("null");
					//工場CD MIN歩留
					cd_kojo_MIN_BUDOMARI = toString("null");
//2010/02/15 UPDATE TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			

				}
				
			}
			
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			
			if (!strGenryoCD.equals("") && strGenryoCD.substring(0, 1).equals( "N")){
				
			}
			else{
				
				//3ヶ月使用実績があるもの=1
				if(strFg_Shiyo.equals("1")){
					strGenryo = strGenryoNm;
				}
				//3ヶ月使用実績がないもの=0
				else if(strFg_Shiyo.equals("0")){
					//★☆記号削除
					if(!strGenryoNm.equals("")){
						if(strGenryoNm.substring(0, 1).equals("★") || 
								strGenryoNm.substring(0, 1).equals("☆")){
							//星記号削除
							strGenryoNm = strGenryoNm.substring(1);
						}
					}
					strGenryo ="★" + strGenryoNm;
				}
				//上記以外=null
				else{
					if (strGenryoNm.equals("")){
						strGenryo = strGenryoNm;
					}
					else{
						//★☆記号削除
						if(strGenryoNm.substring(0, 1).equals("★") || 
								strGenryoNm.substring(0, 1).equals("☆")){
							//星記号削除
							strGenryoNm = strGenryoNm.substring(1);
						}
						strGenryo = "★" + strGenryoNm;
					}
				}	
			}
//add end ---------------------------------------------------------------------------------
			
			//試算　配合ﾃｰﾌﾞﾙ（T320）更新用SQL生成
			ret.append(" UPDATE tr_shisan_haigo ");
			ret.append(" SET ");
			ret.append("     nm_genryo = '" + strGenryo + "' ");
//			ret.append("    ,tanka_ins = null ");
//			ret.append("    ,budomari_ins= null ");
			ret.append("    ,tanka_ma  = " + strTanka + " ");
			ret.append("    ,budomar_ma= " + strBudomari + " ");
//2010/02/15 UPDATE TT.ISONO START MAX単価、MIN歩留　会社、工場の追加			
			ret.append("    ,cd_kaisha_tanka = " + cd_kaisha_MAX_TANKA + " ");
			ret.append("    ,cd_kojo_tanka = " + cd_kojo_MAX_TANKA + " ");
			ret.append("    ,cd_kaisha_budomari = " + cd_kaisha_MIN_BUDOMARI + " ");
			ret.append("    ,cd_kojo_budomari = " + cd_kojo_MIN_BUDOMARI + " ");
//2010/02/15 UPDATE TT.ISONO END   MAX単価、MIN歩留　会社、工場の追加			
			ret.append("    ,id_koshin = " + userInfoData.getId_user() + " ");
			ret.append("    ,dt_koshin = getdate() ");
			ret.append(" WHERE ");
			ret.append("      cd_shain  = " + strCd_shain + " ");
			ret.append("  AND nen       = " + strNen + " ");
			ret.append("  AND no_oi     = " + strNo_oi + " ");
			
			//【QP@00342】
			ret.append("  AND no_eda     = " + no_eda + " ");
			
			ret.append("  AND cd_kotei  = " + strCd_kotei + " ");
			ret.append("  AND seq_kotei = " + strSeq_kotei + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "洗い替え実行SQLを生成に失敗しました。");
			
		}finally{
			
		}
		return ret;

	}
	
}
