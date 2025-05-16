package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2020_Logic;


/**
 * 原料単価洗い戻し実装クラス
 * @author isono
 * @create 2009/11/02
 *
 */
public class FGEN0070_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN0070_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原料単価洗い戻し操作
	 * @param reqData : 機能リクエストデータ
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
		//レスポンスデータ
		RequestResponsKindBean ret = null;
		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strWHERE = null;
		
		try {
			//レスポンスの生成
			ret = new RequestResponsKindBean();
			ret.setID("FGEN0070");

//【QP@10713】 TT.hisahori DEL 2011/11/4 start -----------------------------------
//			//SQL生成
//			strSQL.append(" UPDATE tr_shisan_haigo ");
//			strSQL.append(" SET ");
//			strSQL.append("  tanka_ins    = NULL ");
//			strSQL.append(" ,budomari_ins = NULL ");

//			//DBセッション生成
//			createExecDB();
//			//トランザクション開始
//			execDB.BeginTran();
			
//			for (int i = 0; i < reqData.getCntRow("genryo"); i++){

//				strWHERE = null;
//				strWHERE  = new StringBuffer();
				
//				//WHERE句生成
//				strWHERE.append(" WHERE ");
//				strWHERE.append("     cd_shain  = " + reqData.getFieldVale("genryo", i, "cd_shain") + " ");
//				strWHERE.append(" AND nen       = " + reqData.getFieldVale("genryo", i, "nen") + " ");
//				strWHERE.append(" AND no_oi     = " + reqData.getFieldVale("genryo", i, "no_oi") + " ");
//				strWHERE.append(" AND cd_kotei  = " + reqData.getFieldVale("genryo", i, "cd_kotei") + " ");
//				strWHERE.append(" AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") + " ");
				
//				//【QP@00342】
//				strWHERE.append(" AND no_eda = " + reqData.getFieldVale("genryo", i, "no_eda") + " ");
				
//				//DB更新（試算　試作品ﾃｰﾌﾞﾙ更新）
//				this.execDB.execSQL(strSQL.toString() + strWHERE.toString());
				
//			}

//			//DBコミット
//			this.execDB.Commit();
//【QP@10713】 TT.hisahori DEL 2011/11/4 end -----------------------------------
			
			//レスポンスセット
			makeRes(reqData, ret);
			
			
		} catch (Exception e) {

			//DBロールバック
			this.execDB.Rollback();
			
			this.em.ThrowException(e, "原料単価洗い戻し操作が失敗しました。");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
			}
			strSQL = null;
			strWHERE = null;
			
		}
		return ret;
		
	}
	private void makeRes(
			
			RequestResponsKindBean reqData
			,RequestResponsKindBean resData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = null;
		//検索結果バッファ
		List<?> lstResult;
		Object[] items = null;

		try{
			
			createSearchDB();
			
			for (int i = 0; i < reqData.getCntRow("genryo"); i++){
				
				strSQL = null;
				strSQL = new StringBuffer();
				
				strSQL.append(" SELECT  ");
				strSQL.append("  T320.cd_shain ");	//0
				strSQL.append(" ,T320.nen ");		//1
				strSQL.append(" ,T320.no_oi ");		//2
				strSQL.append(" ,T320.cd_kotei ");	//3
				strSQL.append(" ,T320.seq_kotei ");	//4
				strSQL.append(" ,T120.cd_genryo ");	//5
				strSQL.append(" ,T320.tanka_ma ");	//6
				strSQL.append(" ,T320.budomar_ma ");	//7
				strSQL.append(" ,T120.tanka ");		//8
				strSQL.append(" ,T120.budomari ");	//9
				
				
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------
				
				//単価　工場名
				strSQL.append(" ,M104_tanka.nm_busho as nm_kojo_tanka ");	//10
				//歩留り　工場名
				strSQL.append(" ,M104_budomari.nm_busho as nm_kojo_budomari ");	//11
				
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END     -----------------------------------
				
				
				strSQL.append(" FROM ");
				strSQL.append("           tr_shisan_haigo AS T320 ");
				strSQL.append(" LEFT JOIN tr_haigo AS T120 ");
				strSQL.append(" ON  T320.cd_shain  = T120.cd_shain ");
				strSQL.append(" AND T320.nen       = T120.nen ");
				strSQL.append(" AND T320.no_oi     = T120.no_oi ");
				strSQL.append(" AND T320.cd_kotei  = T120.cd_kotei ");
				strSQL.append(" AND T320.seq_kotei = T120.seq_kotei ");
				
				
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------			
				//単価　工場名
				strSQL.append(" LEFT JOIN ma_busho AS M104_tanka ");
				strSQL.append(" 	ON T320.cd_kaisha_tanka = M104_tanka.cd_kaisha ");
				strSQL.append(" 	AND T320.cd_kojo_tanka = M104_tanka.cd_busho ");
				//歩留り　工場名
				strSQL.append(" LEFT JOIN ma_busho AS M104_budomari ");
				strSQL.append(" 	ON T320.cd_kaisha_budomari = M104_budomari.cd_kaisha ");
				strSQL.append(" 	AND T320.cd_kojo_budomari = M104_budomari.cd_busho ");
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END --------------------------------------
				
				
				strSQL.append(" WHERE ");
				strSQL.append("     T320.cd_shain  = " + reqData.getFieldVale("genryo", i, "cd_shain") + " ");
				strSQL.append(" AND T320.nen       = " + reqData.getFieldVale("genryo", i, "nen") + " ");
				strSQL.append(" AND T320.no_oi     = " + reqData.getFieldVale("genryo", i, "no_oi") + " ");
				strSQL.append(" AND T320.cd_kotei  = " + reqData.getFieldVale("genryo", i, "cd_kotei") + " ");
				strSQL.append(" AND T320.seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") + " ");
				
				//【QP@00342】
				strSQL.append(" AND T320.no_eda     = " + reqData.getFieldVale("genryo", i, "no_eda") + " ");
				
				lstResult = searchDB.dbSearch(strSQL.toString());
				
				if ( lstResult.size() >= 0 ) {
					
					for (int ix = 0; ix < lstResult.size(); ix++) {

						items = (Object[]) lstResult.get(ix);
						
					}
					
				}

				if (items == null){
					
				}else{
					resData.addFieldVale("genryo", "rec" + toString(i), "flg_return", "true");
					resData.addFieldVale("genryo", "rec" + toString(i), "msg_error", "");
					resData.addFieldVale("genryo", "rec" + toString(i), "nm_class", "");
					resData.addFieldVale("genryo", "rec" + toString(i), "no_errmsg", "");
					resData.addFieldVale("genryo", "rec" + toString(i), "cd_error", "");
					resData.addFieldVale("genryo", "rec" + toString(i), "seq_gyo", reqData.getFieldVale("genryo", i, "seq_gyo"));
					resData.addFieldVale("genryo", "rec" + toString(i), "cd_kotei", toString(items[3]));
					resData.addFieldVale("genryo", "rec" + toString(i), "seq_kotei", toString(items[4]));
					resData.addFieldVale("genryo", "rec" + toString(i), "cd_genryo", toString(items[5]));
//					if (toDouble(items[6]) == toDouble(items[8])
//					&&	toDouble(items[7]) == toDouble(items[9])){
						resData.addFieldVale("genryo", "rec" + toString(i), "henko_renraku", "");
//						
//					}else{
//						resData.addFieldVale("genryo", "rec" + toString(i), "henko_renraku", "◎");
//						
//					}
					resData.addFieldVale("genryo", "rec" + toString(i), "tanka", toString(items[6]));
					resData.addFieldVale("genryo", "rec" + toString(i), "budomari", toString(items[7]));
					
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------
					
					resData.addFieldVale("genryo", "rec" + toString(i), "tanka_kojoNm", toString(items[10]));
					resData.addFieldVale("genryo", "rec" + toString(i), "budomari_kojoNm", toString(items[11]));
					
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END     -----------------------------------
					
				}
				
			}
			
		}catch (Exception e) {

			//DBロールバック
			this.searchDB.Rollback();
			
			this.em.ThrowException(e, "");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
			}
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
}
