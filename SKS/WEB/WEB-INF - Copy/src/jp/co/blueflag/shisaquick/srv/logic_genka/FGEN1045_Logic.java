package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 資材候補転送
 *  : 類似品検索：資材候補転送
 * @author nishigawa
 * @since  2009/11/06
 */
public class FGEN1045_Logic extends LogicBase{
	
	/**
	 * 類似品検索：資材候補転送を行う。
	 * : インスタンス生成
	 */
	public FGEN1045_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 類似品検索：資材候補転送を行う。
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
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			strSql = DataCreateSQL(reqData);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			resKind.addTableItem("shizai");

			//レスポンスデータの形成
			storageSeihinData(lstRecset, resKind.getTableItem("shizai"), reqData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材候補転送処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;

		}
		return resKind;

	}
	
	/**
	 * 資材候補転送情報取得SQL作成
	 *  : 資材候補転送情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer DataCreateSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strKaishaCd = null;
			String strKojoCd = null;
			String strShizaiCd = null;
			String strTanto_kaisya = null;
			String strTanto_kojyo = null;
			String strSeihinCd = null;

			//担当会社CD
			strTanto_kaisya = reqData.getFieldVale(0, 0, "cd_tanto_kaisya");
			//担当工場CD
			strTanto_kojyo = toString(reqData.getFieldVale(0, 0, "cd_tanto_kojyo"),"null");
			
			//SQL文の作成
			strSQL.append(" SELECT ");
			strSQL.append("  M_ALL.cd_kaisha AS cd_kaisha ");	//0
			strSQL.append(" ,M_ALL.cd_busho AS cd_busho ");		//1
			strSQL.append(" ,M_ALL.cd_seihin AS cd_seihin ");	//2
			strSQL.append(" ,M_ALL.cd_shizai AS cd_shizai ");	//3
			strSQL.append(" ,M_ALL.nm_shizai AS nm_shizai ");	//4
			strSQL.append(" ,M_ALL.tanka AS tanka ");			//5
			strSQL.append(" ,M_ALL.budomari AS budomari ");		//6
			strSQL.append(" ,M_ALL.siyoryo AS siyoryo ");		//7
			strSQL.append(" ,M_ALL.fg_seihin AS fg_seihin ");	//8
			strSQL.append(" ,M302_1.nm_literal AS nm_literal ");//9
			strSQL.append(" ,M_ALL.cd_kaisha2 AS cd_kaisha2 ");	//10
			strSQL.append(" ,M_ALL.cd_busho2 AS cd_busho2 ");	//11
			strSQL.append(" ,M_ALL.cd_seihin2 AS cd_seihin2 ");	//12
			strSQL.append(" ,M_ALL.cd_shizai2 AS cd_shizai2 ");	//13
			strSQL.append(" ,M_ALL.nm_shizai2 AS nm_shizai2 ");	//14
			strSQL.append(" ,M_ALL.tanka2 AS tanka2 ");			//15
			strSQL.append(" ,M_ALL.budomari2 AS budomari2 ");	//16
			strSQL.append(" ,M_ALL.siyoryo2 AS siyoryo2 ");		//17
			strSQL.append(" ,M_ALL.fg_seihin2 AS fg_seihin2 ");	//18
			strSQL.append(" ,M302_2.nm_literal AS nm_literal2 ");//19
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			//製品資材検索
			strSQL.append("  SELECT ");
			strSQL.append("   M801.cd_kaisha_seihin AS cd_kaisha ");
			strSQL.append("  ,M801.cd_busho_seihin AS cd_busho ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_seihin),ISNULL(M104.keta_genryo,6)) AS cd_seihin ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M801.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai  ");
			strSQL.append("  ,M801.nm_shizai AS nm_shizai ");
			strSQL.append("  ,M801.tanka AS tanka ");
			strSQL.append("  ,M801.budomari AS budomari ");
			strSQL.append("  ,M801.siyoryo AS siyoryo ");
			strSQL.append("  ,M801.fg_seihin AS fg_seihin ");
			strSQL.append("  ,NULL AS cd_kaisha2 ");
			strSQL.append("  ,NULL AS cd_busho2 ");
			strSQL.append("  ,NULL AS cd_seihin2 ");
			strSQL.append("  ,NULL AS cd_shizai2 ");
			strSQL.append("  ,NULL AS nm_shizai2 ");
			strSQL.append("  ,NULL AS tanka2 ");
			strSQL.append("  ,NULL AS budomari2 ");
			strSQL.append("  ,NULL AS siyoryo2 ");
			strSQL.append("  ,NULL AS fg_seihin2 ");
			strSQL.append("  FROM ");
			strSQL.append("           ma_seihin AS M801 ");
			
			strSQL.append("  LEFT JOIN ma_busho AS M104 ");
			strSQL.append("  ON M801.cd_kaisha_seihin = M104.cd_kaisha ");
			strSQL.append("  AND M801.cd_busho_seihin = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append("      1 > 1 ");
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
				
				if (reqData.getCntField(0, i) == 0){
					break;
				}

				strSQL.append(" OR (");
				
				//会社コード
				strKaishaCd = reqData.getFieldVale(0, i, "cd_kaisya");
				//工場コード
				strKojoCd = reqData.getFieldVale(0, i, "cd_kojyo");
				//製品コード
				strSeihinCd = toString(reqData.getFieldVale(0, i, "cd_seihin"),"null");
				//資材コード
				strShizaiCd = reqData.getFieldVale(0, i, "cd_shizai");
				
				strSQL.append("      M801.cd_kaisha_seihin = " + strKaishaCd + " ");
				strSQL.append("  AND M801.cd_busho_seihin = " + strKojoCd + " ");
				strSQL.append("  AND M801.cd_seihin = " + strSeihinCd + " ");
				strSQL.append("  AND M801.cd_shizai = " + strShizaiCd + " ");
				
				strSQL.append(" ) ");
				
			}
			strSQL.append("  ");
			strSQL.append(" UNION ALL ");
			strSQL.append("  ");
			//資材検索
			strSQL.append("  SELECT ");
			strSQL.append("   M901_1.cd_kaisha AS cd_kaisha ");
			strSQL.append("  ,M901_1.cd_busho AS cd_busho ");
			strSQL.append("  ,NULL AS cd_seihin ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M901_1.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai ");
			strSQL.append("  ,M901_1.nm_shizai AS nm_shizai ");
			strSQL.append("  ,M901_1.tanka AS tanka ");
			strSQL.append("  ,M901_1.budomari AS budomari ");
			strSQL.append("  ,NULL AS siyoryo ");
			strSQL.append("  ,NULL AS fg_seihin ");
			strSQL.append("  ,M901_2.cd_kaisha AS cd_kaisha2 ");
			strSQL.append("  ,M901_2.cd_busho AS cd_busho2 ");
			strSQL.append("  ,NULL AS cd_seihin2 ");
			strSQL.append("  ,RIGHT('0000000000' + CONVERT(varchar,M901_2.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai2 ");
			strSQL.append("  ,M901_2.nm_shizai AS nm_shizai2 ");
			strSQL.append("  ,M901_2.tanka AS tanka2 ");
			strSQL.append("  ,M901_2.budomari AS budomari2 ");
			strSQL.append("  ,NULL AS siyoryo2 ");
			strSQL.append("  ,NULL AS fg_seihin2 ");
			strSQL.append("  FROM ");
			strSQL.append("            ma_shizai AS M901_1 ");
			strSQL.append("  LEFT JOIN ma_shizai AS M901_2 ");
			strSQL.append("  ON  M901_2.cd_kaisha = " + strTanto_kaisya + " ");
			strSQL.append("  AND M901_2.cd_busho  = " + strTanto_kojyo + " ");
			strSQL.append("  AND M901_1.cd_shizai = M901_2.cd_shizai ");
			
			strSQL.append("  LEFT JOIN ma_busho AS M104 ");
			strSQL.append("  ON M901_1.cd_kaisha = M104.cd_kaisha ");
			strSQL.append("  AND M901_1.cd_busho = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append("      1 > 1 ");
			
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
				
				if (reqData.getCntField(0, i) == 0){
					break;
				}
				
				strSQL.append(" OR (");
				
				//会社コード
				strKaishaCd = reqData.getFieldVale(0, i, "cd_kaisya");
				//工場コード
				strKojoCd = reqData.getFieldVale(0, i, "cd_kojyo");
				//資材コード
				strShizaiCd = reqData.getFieldVale(0, i, "cd_shizai");
				
				strSQL.append("      M901_1.cd_kaisha = " + strKaishaCd + " ");
				strSQL.append("  AND M901_1.cd_busho  = " + strKojoCd + " ");
				strSQL.append("  AND M901_1.cd_shizai = " + strShizaiCd + " ");
				
				strSQL.append(" ) ");

			}
			
			strSQL.append(" ) AS M_ALL ");
			
			strSQL.append(" LEFT JOIN ma_literal AS M302_1 ");
			strSQL.append(" ON  M302_1.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302_1.cd_literal = CONVERT(varchar, M_ALL.cd_kaisha) + '-' + CONVERT(varchar, M_ALL.cd_busho) ");
			strSQL.append(" LEFT JOIN ma_literal AS M302_2 ");
			strSQL.append(" ON  M302_2.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302_2.cd_literal = CONVERT(varchar, M_ALL.cd_kaisha2) + '-' + CONVERT(varchar, M_ALL.cd_busho2) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材候補転送データ検索処理に失敗しました。");
		} finally {
			//変数の削除
		}
		return strSQL;
	}

	
	/**
	 * 類似品検索：資材候補転送パラメーター格納
	 *  : 資材候補転送情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihinData(
			
			List<?> lstGenryouData
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] items = null;		
		
		try {
			for (int i = 0 ; i < reqData.getCntRow(0) ; i++){
	
				items = getShizaiDBData(
						toString(reqData.getFieldVale(0, i, "cd_kaisya"), "")
						, toString(reqData.getFieldVale(0, i, "cd_kojyo"), "")
						, toString(reqData.getFieldVale(0, i, "cd_seihin"), "")
						, toString(reqData.getFieldVale(0, i, "cd_shizai"), "")
						, lstGenryouData);
				
				if (items == null ){
					//処理結果の格納
					resTable.addFieldVale("rec" + i, "flg_return", "false");
					resTable.addFieldVale("rec" + i, "msg_error", "対象資材、取得失敗");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
					
				}else{
					//処理結果の格納
					resTable.addFieldVale("rec" + i, "flg_return", "true");
					resTable.addFieldVale("rec" + i, "msg_error", "");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
					
					//結果をレスポンスデータに格納
					if(toString(items[10]).equals("")){
						//自社自工場に資材が無い場合
						resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[0]) );
						resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[1]));
						resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[9]));
						resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
						if(toString(items[8]).equals("1")){
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]) + "");
							
						}else{
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
							
						}
						resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[5]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[6]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[2]));
						if (toDouble(items[7], -1) > -1){
							resTable.addFieldVale("rec" + i, "ryo", toString(toDouble(items[7]),6,2,true,""));
							
						}else{
							resTable.addFieldVale("rec" + i, "ryo", "");
							
						}
						
					}else{
						//自社自工場に資材が有る場合
						resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[10]) );
						resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[11]));
						resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[19]));
						resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[13]));
						if(toString(items[18]).equals("1")){
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]) + "");
							
						}else{
							resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]));
							
						}
						resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[15]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[16]),2,2,true,""));
						resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[12]));
						resTable.addFieldVale("rec" + i, "ryo", "");
						
					}
					

				}
				
			}

//			for (int i = 0; i < lstGenryouData.size(); i++) {
//
//				//処理結果の格納
//				resTable.addFieldVale("rec" + i, "flg_return", "true");
//				resTable.addFieldVale("rec" + i, "msg_error", "");
//				resTable.addFieldVale("rec" + i, "no_errmsg", "");
//				resTable.addFieldVale("rec" + i, "nm_class", "");
//				resTable.addFieldVale("rec" + i, "cd_error", "");
//				resTable.addFieldVale("rec" + i, "msg_system", "");
//				
//				Object[] items = (Object[]) lstGenryouData.get(i);
//				
//				//結果をレスポンスデータに格納
//				if(toString(items[10]).equals("")){
//					//自社自工場に資材が無い場合
//					resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[0]) );
//					resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[1]));
//					resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[9]));
//					resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
//					if(toString(items[8]).equals("1")){
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]) + "");
//						
//					}else{
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
//						
//					}
//					resTable.addFieldVale("rec" + i, "tanka", toString(items[5]));
//					resTable.addFieldVale("rec" + i, "budomari", toString(items[6]));
//					resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[2]));
//					resTable.addFieldVale("rec" + i, "ryo", toString(items[7]));
//					
//				}else{
//					//自社自工場に資材が有る場合
//					resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[10]) );
//					resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[11]));
//					resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[19]));
//					resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[13]));
//					if(toString(items[18]).equals("1")){
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]) + "");
//						
//					}else{
//						resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[14]));
//						
//					}
//					resTable.addFieldVale("rec" + i, "tanka", toString(items[15]));
//					resTable.addFieldVale("rec" + i, "budomari", toString(items[16]));
//					resTable.addFieldVale("rec" + i, "cd_seihin", toString(items[12]));
//					resTable.addFieldVale("rec" + i, "ryo", toString(items[17]));
//					
//				}
//				
//			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材候補転送データ検索処理に失敗しました。");

		} finally {

		}

	}
	/**
	 * 対象データサーチ
	 * @param kaisya
	 * @param kojyo
	 * @param seihin
	 * @param shizai
	 * @param dataList
	 * @return Object[]
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] getShizaiDBData(
			
			String kaisya
			, String kojyo
			, String seihin
			, String shizai
			, List<?> dataList 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] ret = null;
		
		try {

			for (int i = 0; i < dataList.size(); i++) {
				
				Object[] items = (Object[]) dataList.get(i);
				
				if (toString(items[0],"").equals(kaisya) 
						&& toString(items[1],"").equals(kojyo)
						&& toString(items[2],"").equals(seihin)
						&& toString(items[3],"").equals(shizai)
						){
					
					ret = items;
					break;
					
				}

			}
				
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		
		return ret;
		
	}
	
}