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
 * 資材検索
 *  : 類似品検索：資材情報を検索する。
 * @author nishigawa
 * @since  2009/11/05
 */
public class FGEN1040_Logic extends LogicBase{
	
	/**
	 * 類似品検索：資材情報を検索する。
	 * : インスタンス生成
	 */
	public FGEN1040_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 類似品検索：資材情報を検索する。
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
		List<?> lstRecset_CNT = null;
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			super.createSearchDB();

			//SQL文の作成（件数）
			strSql = DataCreateSQL_CNT(reqData);
			//SQLを実行
			lstRecset_CNT = searchDB.dbSearch(strSql.toString());

			//SQL文の作成（データ）
			strSql = DataCreateSQL(reqData);
			//SQLを実行
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			resKind.addTableItem("kihon");
			resKind.addTableItem("shizai");

			//レスポンスデータの形成
			storageKihonData(lstRecset_CNT, reqData, resKind.getTableItem("kihon"));
			storageSeihinData(lstRecset, resKind.getTableItem("shizai"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材データ検索処理に失敗しました。");
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
	 * 資材検索情報（件数）取得SQL作成
	 *  : 資材情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer DataCreateSQL_CNT(
			
			RequestResponsKindBean reqData
			) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strShizaiCd = null;
			String strShizaiName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//工場コードの取得
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//資材コードの取得
			strShizaiCd = reqData.getFieldVale(0, 0, "cd_shizai");
			//資材名の取得
			strShizaiName = reqData.getFieldVale(0, 0, "nm_shizai");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			strSQL.append(" SELECT ");
			strSQL.append("  COUNT(cd_kaisha) AS CNT ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_shizai ");
			strSQL.append(" WHERE ");
			strSQL.append(" 1 = 1 ");
			if(!strKaishaCd.equals("")){
				
			}
			if(!strKaishaCd.equals("")){
				strSQL.append(" AND cd_kaisha = " + strKaishaCd + " ");
				
			}
			if(!strKojoCd.equals("")){
				strSQL.append(" AND cd_busho  = " + strKojoCd + " ");
				
			}
			if(!strShizaiCd.equals("")){
				strSQL.append(" AND cd_shizai = " + strShizaiCd + " ");
				
			}
			if(!strShizaiName.equals("")){
				strSQL.append(" AND nm_shizai LIKE '%" + strShizaiName + "%' ");
				
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "資材データ検索処理に失敗しました。");
		} finally {
			
		}
		return strSQL;
	}

	/**
	 * 資材検索情報取得SQL作成
	 *  : 資材情報を取得するSQLを作成。
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
			String strShizaiCd = null;
			String strShizaiName = null;
			String strKaishaCd = null;
			String strKojoCd = null;
			String strPageNo = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//会社コードの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			//工場コードの取得
			strKojoCd = reqData.getFieldVale(0, 0, "cd_kojyo");
			//資材コードの取得
			strShizaiCd = reqData.getFieldVale(0, 0, "cd_shizai");
			//資材名の取得
			strShizaiName = reqData.getFieldVale(0, 0, "nm_shizai");
			//選択ページNoの取得
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//SQL文の作成
			strSQL.append(" SELECT TOP " + strListRowMax + " ");
			strSQL.append("  * ");
			strSQL.append(" FROM ");
			strSQL.append(" ( ");
			strSQL.append("  SELECT ");
			strSQL.append("   ROW_NUMBER() OVER (ORDER BY M901.cd_kaisha, M901.cd_busho, M901.cd_shizai ) AS NUM ");
			strSQL.append("  ,M901.cd_kaisha ");	//1
			strSQL.append("  ,M901.cd_busho ");		//2
			//strSQL.append("  ,M901.cd_shizai ");	//3
			strSQL.append(" ,RIGHT('0000000000' + CONVERT(varchar,M901.cd_shizai),ISNULL(M104.keta_genryo,6)) AS cd_shizai ");
			strSQL.append("  ,M901.nm_shizai ");	//4
			strSQL.append("  ,M901.tanka ");		//5
			strSQL.append("  ,M901.budomari ");		//6
			strSQL.append("  ,M302.nm_literal ");	//7
			strSQL.append("  FROM ");
			strSQL.append("            ma_shizai AS M901 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M901.cd_kaisha) + '-' + CONVERT(varchar, M901.cd_busho) ");
			
			strSQL.append(" LEFT JOIN ma_busho AS M104 ");
			strSQL.append(" ON M901.cd_kaisha = M104.cd_kaisha ");
			strSQL.append(" AND M901.cd_busho = M104.cd_busho ");
			
			strSQL.append("  WHERE ");
			strSQL.append(" 1 = 1 ");
			if(!strKaishaCd.equals("")){
				
			}
			if(!strKaishaCd.equals("")){
				strSQL.append(" AND M901.cd_kaisha = " + strKaishaCd + " ");
				
			}
			if(!strKojoCd.equals("")){
				strSQL.append(" AND M901.cd_busho  = " + strKojoCd + " ");
				
			}
			if(!strShizaiCd.equals("")){
				strSQL.append(" AND M901.cd_shizai = " + strShizaiCd + " ");
				
			}
			if(!strShizaiName.equals("")){
				strSQL.append(" AND M901.nm_shizai LIKE '%" + strShizaiName + "%' ");
				
			}
			strSQL.append(" ) AS M901_1 ");
			strSQL.append(" WHERE ");
			strSQL.append("     M901_1.NUM >= " + 
					//先頭行番号の計算
					toString( (toInteger(strListRowMax) * (toInteger(strPageNo)-1) +1 ), 0, 1, false, "1")
					+ " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材データ検索処理に失敗しました。");
		} finally {
			//変数の削除
		}
		return strSQL;
	}
	
	/**
	 * 類似品検索：資材情報パラメーター格納
	 *  : 製品-資材情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageKihonData(
			List<?> lstGenryouData
			, RequestResponsKindBean reqData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//表示MAX行数
			String strListRowMax = toString(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"), "50");

			for (int i = 0; i < 1; i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object items = (Object) lstGenryouData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "titol", "”資材マスタより検索中”");
				resTable.addFieldVale(i, "max_cnt", toString(items));
				resTable.addFieldVale(i, "no_page", reqData.getFieldVale(0, 0, "no_page"));
				resTable.addFieldVale(i, "max_page",  toString(
						(toInteger(items) / toInteger(strListRowMax))
						, 0
						, 2
						, false
						, "1"));
				resTable.addFieldVale(i, "disp_cnt", toString(ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX"), "50"));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材データ検索処理に失敗しました。");

		} finally {

		}

	}
	
	/**
	 * 類似品検索：資材情報パラメーター格納
	 *  : 資材情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihinData(
			List<?> lstGenryouData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec" + i, "cd_kaisya", toString(items[1]));
				resTable.addFieldVale("rec" + i, "cd_kojyyo", toString(items[2]));
				resTable.addFieldVale("rec" + i, "kigo_kojyo", toString(items[7]));
				resTable.addFieldVale("rec" + i, "cd_shizai", toString(items[3]));
				resTable.addFieldVale("rec" + i, "nm_shizai", toString(items[4]));
				resTable.addFieldVale("rec" + i, "tanka", toString(toDouble(items[5]),2,2,true,""));
				resTable.addFieldVale("rec" + i, "budomari", toString(toDouble(items[6]),2,2,true,""));
				resTable.addFieldVale("rec" + i, "cd_seihin", "");
				resTable.addFieldVale("rec" + i, "ryo", "");
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "資材データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}