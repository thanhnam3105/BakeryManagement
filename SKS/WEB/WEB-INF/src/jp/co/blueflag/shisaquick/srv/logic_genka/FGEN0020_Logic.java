package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 変更確認
 * 入力内容とDBを比較し変更の有無を確認する
 * @author isono
 * @create 2009/11/03
 */
public class FGEN0020_Logic extends LogicBase {

	//対象データ特定情報
	//試作No-社員ID
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	//試作No-年
	int cd_shisaku_nen = -1;
	//試作No-追番
	int cd_shisaku_oi = -1;
	
	//【QP@00342】試作No-枝番
	int cd_shisaku_eda = -1;
	
	//試作情報
	List<?> listShisaku = null;	
	//ｻﾝﾌﾟﾙ情報
	List<?> listSanpuru = null;	
	//原料情報
	List<?> listGenryo = null;	
	//資材情報
	List<?> listShizai = null;
	
	// ADD 2013/7/2 shima【QP@30151】No.37 start
	//基本情報サブ
	List<?> listKihonSub = null;
	// ADD 2013/7/2 shima【QP@30151】No.37 end

	/**
	 * コンストラクタ
	 */
	public FGEN0020_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;
		//レスポンスデータクラス
		RequestResponsKindBean ret = null;
		
		try {

			//試作NO退避	
			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"),"-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"),-1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"),-1);

			//【QP@00342】
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"),-1);
			
			//DB取得項目の取得
			getDBKomoku();
			
			//レスポンス生成
			ret = new RequestResponsKindBean();
			ret.addFieldVale("table", "rec", "flg_return", "true");
			ret.addFieldVale("table", "rec", "msg_error", "");
			ret.addFieldVale("table", "rec", "nm_class", "");
			ret.addFieldVale("table", "rec", "no_errmsg", "");
			ret.addFieldVale("table", "rec", "cd_error", "");
			ret.addFieldVale("table", "rec", "msg_system", "");
			
			ret.setID("FGEN0020");
			
			//変更確認
			if (Comparison(reqData)){
				//変更なし
				ret.addFieldVale("table", "rec", "hantei", "true");
				
			}else{
				//変更あり
				ret.addFieldVale("table", "rec", "hantei", "false");
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算　変更確認が失敗しました。");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
			}
		}
		return ret;
		
	}
	private boolean Comparison(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		boolean ret = true;
		Object[] items = null;
		int ix = -1;
		
		try {

			//試作情報の比較
			
			items = (Object[]) listShisaku.get(0);

			//【QP@00342】
//			//採用サンプルＮＯ	no_sanpuru
//			if (!toString(reqData.getFieldVale("kihon", "rec", "no_sanpuru")).equals(
//					toString(items[3]))){
//				ret = false;
//			}
			
			//工場　担当会社	cd_kaisya
			if (!toString(reqData.getFieldVale("kihon", "rec", "cd_kaisya")).equals(
					toString(items[4]))){
				ret = false;
			}
			//工場　担当工場	cd_kojyo
			if (!toString(reqData.getFieldVale("kihon", "rec", "cd_kojyo")).equals(
					toString(items[5]))){
				ret = false;
			}
			
			//【QP@00342】
//			//入り数	irisu
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "irisu"))
//					!= toDouble(items[6])){
//				ret = false;
//			}
//			//荷姿	nisugata
//			if (!toString(reqData.getFieldVale("kihon", "rec", "nisugata")).equals(
//					toString(items[7]))){
//				ret = false;
//			}
//			//原価希望	kibo_genka
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "kibo_genka"))
//					!= toDouble(items[8])){
//				ret = false;
//			}
//			//原価希望単位CD	kibo_genka_cd_tani
//			if (!toString(reqData.getFieldVale("kihon", "rec", "kibo_genka_cd_tani")).equals(
//					toString(items[9]))){
//				ret = false;
//			}
//			//売価希望	kibo_baika
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "kibo_baika"))
//					!= toDouble(items[10])){
//				ret = false;
//			}
//			
//			//想定物量	butu_sotei
//			if (!toString(reqData.getFieldVale("kihon", "rec", "butu_sotei")).equals(
//					toString(items[19]))){
//				ret = false;
//			}
//			
//			//販売時期	ziki_hanbai
//			if (!toString(reqData.getFieldVale("kihon", "rec", "ziki_hanbai")).equals(
//					toString(items[11]))){
//				ret = false;
//			}
//			//計画売上	keikaku_uriage
//			if (!toString(reqData.getFieldVale("kihon", "rec", "keikaku_uriage")).equals(
//					toString(items[12]))){
//				ret = false;
//			}
//			//計画利益	keikaku_rieki
//			if (!toString(reqData.getFieldVale("kihon", "rec", "keikaku_rieki")).equals(
//					toString(items[13]))){
//				ret = false;
//			}
//			//販売後売上	hanbaigo_uriage
//			if (!toString(reqData.getFieldVale("kihon", "rec", "hanbaigo_uriage")).equals(
//					toString(items[14]))){
//				ret = false;
//			}
//			//販売後利益	hanbaigo_rieki
//			if (!toString(reqData.getFieldVale("kihon", "rec", "hanbaigo_rieki")).equals(
//					toString(items[15]))){
//				ret = false;
//			}
			
			// DEL 2013/7/2 shima【QP@30151】No.37 start
			//製造ロット	seizo_roto
//			if (!toString(reqData.getFieldVale("kihon", "rec", "seizo_roto")).equals(
//					toString(items[16]))){
//				ret = false;
//			}
			// DEL 2013/7/2 shima【QP@30151】No.37 end
			
			//原価試算メモ	memo_genkashisan
			if (!toString(reqData.getFieldVale("kihon", "rec", "memo_genkashisan")).equals(
					toString(items[17]))){
				ret = false;
			}
			//計算項目（固定費/ケースor固定費/kg）	ragio_kesu_kg
			if (!toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg")).equals(
					toString(items[18]))){
				ret = false;
			}
			
			//【QP@00342】
			//原価試算メモ（営業連絡用）	memo_genkashisan_eigyo
			if (!toString(reqData.getFieldVale("kihon", "rec", "memo_genkashisan_eigyo")).equals(
					toString(items[20]))){
				ret = false;
			}
			

			//原料比較

			for (int i = 0 ; i < reqData.getCntRow("genryo") ; i++){
				
				ix = seachGenryoIndex(
						reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei"));
				if (ix > -1){
					
				}else{
					ret = false;
					break;
				}
				items = (Object[]) listGenryo.get(ix);

				//工程CD	cd_kotei
				if (!toString(reqData.getFieldVale("genryo", i, "cd_kotei")).equals(
						toString(items[3]))){
					ret = false;
				}
				//工程SEQ	seq_kotei
				if (!toString(reqData.getFieldVale("genryo", i, "seq_kotei")).equals(
						toString(items[4]))){
					ret = false;
				}
				//原料CD	cd_genryo
				if (!toString(reqData.getFieldVale("genryo", i, "cd_genryo")).equals(
						toString(items[5]))){
					ret = false;
				}
				//単価	tanka
				if (toDouble(reqData.getFieldVale("genryo", i, "tanka"))
						!= toDouble(items[6])){
					ret = false;
				}
				//歩留	budomari
				if (toDouble(reqData.getFieldVale("genryo", i, "budomari"))
						!= toDouble(items[7])){
					ret = false;
				}
				
			}

			//原価計算（サンプル）比較

			for (int i = 0 ; i < reqData.getCntRow("keisan") ; i++){
				
				ix = seachSanpuruIndex(
						reqData.getFieldVale("keisan", i, "seq_shisaku"));
				if (ix > -1){
					
				}else{
					ret = false;
					break;
				}
				items = (Object[]) listSanpuru.get(ix);
				
				//【QP@00342】試算中止の比較
				if( !toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals( toString( items[11] ) ) ){
					ret = false;
				}
				
				//【QP@00342】試算中止の場合はチェックしない
				if( toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("1") ){
					
				}
				else{
					
					//試作SEQ	seq_shisaku
					if (!toString(reqData.getFieldVale("keisan", i, "seq_shisaku")).equals(
							toString(items[3]))){
						ret = false;
					}
		
	//【シサクイック（原価）要望】試算日の入力変更確認を規定フォーマットで判断　TT.Nishigawa 2010/2/16 START -----------------------------------												
					//試算日	shisan_date
					//DB値がNULLの場合
					if( toString(items[4]).equals("") ){
						//文字列比較
						if (!toString(reqData.getFieldVale("keisan", i, "shisan_date")).equals(
								toString(items[4]))){
							ret = false;
						}
					}
					//DB値が日付の場合
					else{
						//日付比較
						String sisanDate = toString(reqData.getFieldVale("keisan", i, "shisan_date"));
						DateFormat format = DateFormat.getDateInstance();
					    format.setLenient(false);
					    try {
					    	Date dtDb = format.parse(toString(items[4]));
					    	Date dtInput = format.parse(cnvDateFormat(sisanDate));
					    	
					        //日付が同じ場合
					    	if(dtDb.compareTo(dtInput) == 0){
					    		
					    	}
					    	//日付が異なる場合
					    	else{
					    		ret = false;
					    	}
					        
					    } catch (Exception e) {
					    	ret = false;
					    }
					}
	//【シサクイック（原価）要望】試算日の入力変更確認を規定フォーマットで判断　TT.Nishigawa 2010/2/16 END -----------------------------------																
					
					
					//有効歩留（％）	yuuko_budomari
					if (toDouble(reqData.getFieldVale("keisan", i, "yuuko_budomari"))
							!= toDouble(items[5])){
						ret = false;
					}
					//平均充填量（ｇ）	heikinjyutenryo
					if (toDouble(reqData.getFieldVale("keisan", i, "heikinjyutenryo"))
							!= toDouble(items[6])){
						ret = false;
					}
					
					String sen_koteihi = toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg"));
					if(sen_koteihi.equals("1")){
						//固定費/ケース	kesu_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kesu_kotehi"))
								!= toDouble(items[7])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano start
						//固定費/ケース	kesu_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kesu_rieki"))
								!= toDouble(items[9])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano end
					}
					else{
						//固定費/KG	kg_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kg_kotehi"))
								!= toDouble(items[8])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano start
						//固定費/KG	kg_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kg_rieki"))
								!= toDouble(items[10])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano end
					}
//ADD 2013/07/12 ogawa 【QP@30151】No.13 start
					//項目固定チェック	fg_koumokuchk
					if (toDouble(reqData.getFieldVale("keisan", i, "fg_koumokuchk"))
							!= toDouble(items[12])){
						ret = false;
					}
//ADD 2013/07/12 ogawa 【QP@30151】No.13 end
				}
			}
			

			//資材比較

			for (int i = 0 ; i < reqData.getCntRow("shizai") ; i++){
				
				ix = seachShizaiIndex(
						reqData.getFieldVale("shizai", i, "seq_shizai"));
				if (ix > -1){
					
				}else{
					if (!toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals("")){
						ret = false;
					}else{
						
					}
					break;
				}
				items = (Object[]) listShizai.get(ix);

				//資材SEQ	seq_shizai
				if (!toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals(
						toString(items[3]))){
					ret = false;
				}
				//会社CD	cd_kaisya
				if (!toString(reqData.getFieldVale("shizai", i, "cd_kaisya")).equals(
						toString(items[4]))){
					ret = false;
				}
				//工場CD	cd_kojyo
				if (!toString(reqData.getFieldVale("shizai", i, "cd_kojyo")).equals(
						toString(items[5]))){
					ret = false;
				}
				//資材CD	cd_shizai
				if (toDouble(reqData.getFieldVale("shizai", i, "cd_shizai"))
						!= toDouble(items[6])){
					ret = false;
				}
				//資材名	nm_shizai
				if (!toString(reqData.getFieldVale("shizai", i, "nm_shizai")).equals(
						toString(items[7]))){
					ret = false;
				}
				//単価	tanka
				if (toDouble(reqData.getFieldVale("shizai", i, "tanka"))
						!= toDouble(items[8])){
					ret = false;
				}
				//歩留（％）	budomari
				if (toDouble(reqData.getFieldVale("shizai", i, "budomari"))
						!= toDouble(items[9])){
					ret = false;
				}
				//使用量/ｹｰｽ	shiyouryo
				if (toDouble(reqData.getFieldVale("shizai", i, "shiyouryo"))
						!= toDouble(items[10])){
					ret = false;
				}
				
			}
			
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			//基本情報サブ
			
			for (int i = 0 ; i < reqData.getCntRow("kihonsub") ; i++){
				
				ix = seachKihonSubIndex(
						reqData.getFieldVale("kihonsub", i, "seq_shisaku"));
				if (ix > -1){
					
				}else{
					if (!toString(reqData.getFieldVale("kihonsub", i, "seq_shisaku")).equals("")){
						ret = false;
					}else{
						
					}
					break;
				}
				items = (Object[]) listKihonSub.get(ix);
				
				//試算中止の場合はチェックしない
				if(toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("1")){
				}else{
					
					//試作SEQ	seq_shisaku
					if (!toString(reqData.getFieldVale("kihonsub", i, "seq_shisaku")).equals(
							toString(items[3]))){
						ret = false;
					}
					//製造ロット	seizo_roto
					if (toDouble(reqData.getFieldVale("kihonsub", i, "seizo_roto"))
							!= toDouble(items[4])){
						ret = false;
					}
				
				}
				
			}
			// ADD 2013/7/2 shima【QP@30151】No.37 end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
		return ret;
	}
	/**
	 * DB情報取得
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		
		try{

			//DBコネクション
			createSearchDB();
			
			//試作情報検索

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T310.cd_shain ");		//0
			strSQL.append(" ,T310.nen ");			//1
			strSQL.append(" ,T310.no_oi ");			//2
			strSQL.append(" ,T310.saiyo_sample ");	//3
			strSQL.append(" ,T310.cd_kaisha ");		//4
			strSQL.append(" ,T310.cd_kojo ");		//5
			strSQL.append(" ,T310.su_iri ");		//6
			strSQL.append(" ,T310.cd_nisugata ");	//7
			strSQL.append(" ,T310.genka ");			//8
			strSQL.append(" ,T310.cd_genka_tani ");	//9
			strSQL.append(" ,T310.baika ");			//10
			strSQL.append(" ,T310.dt_hatubai ");	//11
			strSQL.append(" ,T310.uriage_k ");		//12
			strSQL.append(" ,T310.rieki_k ");		//13
			strSQL.append(" ,T310.uriage_h ");		//14
			strSQL.append(" ,T310.rieki_h ");		//15
			strSQL.append(" ,T310.lot ");			//16

			//【QP@00342】
			strSQL.append(" ,T311.memo ");			//17

			strSQL.append(" ,T310.fg_keisan ");		//18
			strSQL.append(" ,T310.buturyo ");		//19
			//【QP@00342】
			strSQL.append(" ,T312.memo_eigyo ");		//20
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisakuhin AS T310 ");
			
			//【QP@00342】
			strSQL.append(" LEFT JOIN tr_shisan_memo AS T311 ");
			strSQL.append(" ON T310.cd_shain = T311.cd_shain ");
			strSQL.append(" AND T310.nen = T311.nen ");
			strSQL.append(" AND T310.no_oi = T311.no_oi ");
			strSQL.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			strSQL.append(" ON T310.cd_shain = T312.cd_shain ");
			strSQL.append(" AND T310.nen = T312.nen ");
			strSQL.append(" AND T310.no_oi = T312.no_oi ");
			
			
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//【QP@00342】
			strSQL.append(" AND T310.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB検索
			listShisaku = this.searchDB.dbSearch(strSQL.toString());
			
			//ｻﾝﾌﾟﾙ情報検索
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T331.cd_shain ");		//0
			strSQL.append(" ,T331.nen ");			//1
			strSQL.append(" ,T331.no_oi ");			//2
			strSQL.append(" ,T331.seq_shisaku ");	//3
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) ");	//4
			strSQL.append(" ,T331.budomari ");		//5
			strSQL.append(" ,T331.heikinjuten ");	//6
			strSQL.append(" ,T331.cs_kotei ");		//7
			strSQL.append(" ,T331.kg_kotei ");		//8
			// ADD 2013/11/1 QP@30154 okano start
			strSQL.append(" ,T331.cs_rieki ");		//9
			strSQL.append(" ,T331.kg_rieki ");		//10
			// ADD 2013/11/1 QP@30154 okano end
			
			//【QP@00342】
			strSQL.append(" ,T331.fg_chusi ");		//11
//ADD 2013/07/12 ogawa 【QP@30151】No.13 start
			strSQL.append(" ,T331.fg_koumokuchk ");	//12
//ADD 2013/07/12 ogawa 【QP@30151】No.13 end
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisaku AS T331 ");
			strSQL.append(" WHERE ");
			strSQL.append("     T331.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T331.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T331.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//【QP@00342】
			strSQL.append(" AND T331.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB検索
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());
			
			//原料情報
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T320.cd_shain ");		//0
			strSQL.append(" ,T320.nen ");			//1
			strSQL.append(" ,T320.no_oi ");			//2
			strSQL.append(" ,T320.cd_kotei ");		//3
			strSQL.append(" ,T320.seq_kotei ");		//4
			strSQL.append(" ,T120.cd_genryo ");		//5
			strSQL.append(" ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tanka ");			//6
			strSQL.append(" ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomari ");	//7
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_haigo AS T320 ");
			strSQL.append(" LEFT JOIN tr_haigo AS T120 ");
			strSQL.append(" ON  T320.cd_shain  = T120.cd_shain ");
			strSQL.append(" AND T320.nen       = T120.nen ");
			strSQL.append(" AND T320.no_oi     = T120.no_oi ");
			strSQL.append(" AND T320.cd_kotei  = T120.cd_kotei ");
			strSQL.append(" AND T320.seq_kotei = T120.seq_kotei ");
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//【QP@00342】
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB検索
			listGenryo = this.searchDB.dbSearch(strSQL.toString());

			//資材情報
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T340.cd_shain ");		//0
			strSQL.append(" ,T340.nen ");			//1
			strSQL.append(" ,T340.no_oi ");			//2
			strSQL.append(" ,T340.seq_shizai ");	//3
			strSQL.append(" ,T340.cd_kaisha ");		//4
			strSQL.append(" ,T340.cd_busho  ");		//5
			strSQL.append(" ,T340.cd_shizai ");		//6
			strSQL.append(" ,T340.nm_shizai ");		//7
			strSQL.append(" ,T340.tanka ");			//8
			strSQL.append(" ,T340.budomari ");		//9
			strSQL.append(" ,T340.cs_siyou ");		//10
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai AS T340 ");
			strSQL.append(" WHERE ");
			strSQL.append("     T340.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T340.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T340.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//【QP@00342】
			strSQL.append(" AND T340.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB検索
			listShizai = this.searchDB.dbSearch(strSQL.toString());
			
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			//基本情報サブ
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append(" T313.cd_shain, ");
			strSQL.append(" T313.nen, ");
			strSQL.append(" T313.no_oi, ");
			strSQL.append(" T313.seq_shisaku, ");
			strSQL.append(" T313.lot ");
			strSQL.append(" FROM ");
			strSQL.append(" tr_shisan_kihonjoho AS T313 ");
			strSQL.append(" WHERE ");
			strSQL.append(" T313.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T313.nen = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T313.no_oi = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T313.no_eda = " + toString(cd_shisaku_eda) + " ");
			
			//DB検索
			listKihonSub = this.searchDB.dbSearch(strSQL.toString());
			// ADD 2013/7/2 shima【QP@30151】No.37 end
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 \nSQL:"
					+ strSQL.toString());
			
		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			//ローカル変数の開放
			strSQL = null;

		}
		
	}
	/**
	 * サンプル情報（listSanpuru）より、条件に合致するインデックスを検出する
	 * @param seq_shisaku	：試作SEQ
	 * @param listGenryo	：検索結果（サンプル情報）
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachSanpuruIndex(
			
			  String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listSanpuru.size(); i++){
				
				Object[] items = (Object[]) listSanpuru.get(i);
				
				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
						){
					//該当した場合、終了
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	/**
	 * 原料情報（listGenryo）より、条件に合致するインデックスを検出する
	 * @param cd_kotei		：工程CD
	 * @param seq_kotei		：工程SEQ
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachGenryoIndex(
			
			  String cd_kotei 
			, String seq_kotei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listGenryo.size(); i++){
				
				Object[] items = (Object[]) listGenryo.get(i);
				
				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(cd_kotei) == toInteger(items[3])
					&&	toInteger(seq_kotei) == toInteger(items[4])
						){
					//該当した場合、終了
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}

	/**
	 * 資材情報（listShizai）より、条件に合致するインデックスを検出する
	 * @param seq_Shizai	：資材SEQ
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachShizaiIndex(
			
			  String seq_Shizai
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listShizai.size(); i++){
				
				Object[] items = (Object[]) listShizai.get(i);
				
				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_Shizai) == toInteger(items[3])
						){
					//該当した場合、終了
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	/**
	 * 基本情報情報（listKihonSub）より、条件に合致するインデックスを検出する
	 * @param seq_KihonSub	：基本情報SEQ
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachKihonSubIndex(
			
			  String seq_KihonSub
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listKihonSub.size(); i++){
				
				Object[] items = (Object[]) listKihonSub.get(i);
				
				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_KihonSub) == toInteger(items[3])
						){
					//該当した場合、終了
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	
}
