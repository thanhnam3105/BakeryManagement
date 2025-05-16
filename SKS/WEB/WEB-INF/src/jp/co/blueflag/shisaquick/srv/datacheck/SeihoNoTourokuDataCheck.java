package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 【S2- SA500】 登録（製法コピー）DBチェック
 * @author k-katayama
 * @since  2009/05/21
 */
public class SeihoNoTourokuDataCheck extends DataCheck{

	private SearchBaseDao searchDB_Seiho = null;

	/**
	 *  登録（製法コピー）処理DBチェック処理用コンストラクタ 
	 */
	public SeihoNoTourokuDataCheck() {
		//基底クラスのコンストラクタ
		super();

		//インスタンス変数の初期化
		searchDB_Seiho = null;
	}

	/**
	 * 登録（製法コピー）処理DBチェック
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		try {
			//種別コードのチェック（製法支援に未登録の種別コードはエラー）
			checkShubetuCd(reqData);
			//原料コードのチェック	//2009/08/03 ADD 課題285の対応
			checkGenryoCd(reqData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
	}
	
	/**
	 * 種別コードのチェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void checkShubetuCd(
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();
		StringBuffer strSql3 = new StringBuffer();
		List<?> lstRecset = null;

		try {
			//リクエストデータ取得
			String strCd_shubetu = reqData.getFieldVale(0, 0, "cd_shubetu");
			String strNo_shubetu = reqData.getFieldVale(0, 0, "no_shubetu");
			
			//DBセッションの設定
			searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);
			super.createSearchDB();

			//種別コード（1桁目）チェック用SQLを作成
			strSql2 = createCheckShubetuCd1SQL(strCd_shubetu, strSql2);
			
			//種別コード（1桁目）チェック用SQLを実行
			lstRecset = searchDB_Seiho.dbSearch(strSql2.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000306", "種別コード", strCd_shubetu + strNo_shubetu, "");
			}
			
			//種別コード（2～3桁目）チェック用SQLを作成
			strSql3 = createCheckShubetuCd2SQL(strNo_shubetu, strSql3);
			//種別コード（2～3桁目）チェック用SQLを実行
			lstRecset = searchBD.dbSearch(strSql3.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000306", "種別番号", strCd_shubetu + strNo_shubetu, "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchBD != null) {
				//セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
			if (searchDB_Seiho != null) {
				//セッションのクローズ
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}
			
			//変数の削除
			strSql1 = null;
			strSql2 = null;
			strSql3 = null;
		}
		
	}

	/**
	 * 種別コード（1桁目）存在チェックSQL作成
	 * @param strShubetuCd：種別コード（1桁目）
	 * @param strSql：作成SQL
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckShubetuCd1SQL(String strShubetuCd, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_hin_syurui");
			strSql.append(" FROM");
			strSql.append("  ma_hin_syurui");
			strSql.append(" WHERE cd_hin_syurui = '" + strShubetuCd + "' COLLATE Japanese_CS_AS");

		} catch (Exception e) {
			this.em.ThrowException(e, "種別コード（1桁目）存在チェックSQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * 種別コード（2～3桁目）存在チェックSQL作成
	 * @param strShubetuCd：種別コード（2～3桁目）
	 * @param strSql：作成SQL
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckShubetuCd2SQL(String strShubetuCd, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  nm_literal");
			strSql.append(" FROM");
			strSql.append("  ma_literal");
			strSql.append(" WHERE cd_category = 'K_syubetuno'");
			strSql.append(" AND nm_literal =  '" + strShubetuCd + "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "種別コード（2～3桁目）存在チェックSQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * 2009/08/03 ADD 課題285の対応
	 * 原料コードのチェック
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void checkGenryoCd(
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();

		List<?> lstRecset = null;

		try {
			//試作リストデータ取得SQL作成メソッドを呼び出す。
			strSql = searchShisakuListSQL(reqData);

			//取得したSQL文を実行する。
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql.toString());

			//実行結果に該当データが存在する場合、エラーをスローする。
			if (lstRecset.size() != 0){
				em.ThrowException(ExceptionKind.一般Exception, "E000307", "試作データ画面 配合表 原料コード", "", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchBD != null) {
				//セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
			
			//変数の削除
			strSql = null;
		}
		
	}

	/**
	 * 2009/08/03 ADD 課題285の対応
	 * 試作リストデータ取得SQL作成
	 * @param reqData : リクエストデータ
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer searchShisakuListSQL(RequestResponsKindBean reqData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		String strGenryocd = "";
		
		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  TH.cd_shain");
			strSql.append(" ,TH.nen");
			strSql.append(" ,TH.no_oi");
			strSql.append(" ,TSL.seq_shisaku");
			strSql.append(" ,TSL.quantity");
			strSql.append(" FROM");
			strSql.append("  tr_haigo TH");
			strSql.append(" INNER JOIN");
			strSql.append("  tr_shisaku_list TSL");
			strSql.append(" ON  TH.cd_shain = TSL.cd_shain");
			strSql.append(" AND TH.nen = TSL.nen");
			strSql.append(" AND TH.no_oi = TSL.no_oi");
			strSql.append(" AND TH.cd_kotei = TSL.cd_kotei");
			strSql.append(" AND TH.seq_kotei = TSL.seq_kotei");
			strSql.append(" WHERE TH.cd_shain = " + reqData.getFieldVale(0, 0, "cd_shain") );
			strSql.append(" AND TH.nen = " + reqData.getFieldVale(0, 0, "nen") );
			strSql.append(" AND TH.no_oi = " + reqData.getFieldVale(0, 0, "no_oi") );
			strSql.append(" AND TSL.seq_shisaku = " + reqData.getFieldVale(0, 0, "seq_shisaku") );
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				if (!(strGenryocd.equals(""))) {
					strGenryocd = strGenryocd + ",";
				}
				strGenryocd = strGenryocd + "'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'";
			}
			strSql.append(" AND TH.cd_genryo IN (" + strGenryocd + ")" );
			strSql.append(" AND LEFT(TH.cd_genryo, 1) = 'N'");
			strSql.append(" AND ISNULL(TSL.quantity, 0) <> 0");

		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ取得SQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
	}

}
