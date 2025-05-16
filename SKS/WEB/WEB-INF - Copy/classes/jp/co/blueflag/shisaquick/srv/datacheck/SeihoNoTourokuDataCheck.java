package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
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
			//原料コードのチェック（既存原料以外はエラー）
			checkGenryoCd(reqData);

			//種別コードのチェック（製法支援に未登録の種別コードはエラー）
			checkShubetuCd(reqData);
			
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
			//試作品データ取得用SQLを作成
			strSql1 = createShisakuSQL(reqData, strSql1);
			
			//試作品データ取得用SQLを実行
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql1.toString());
			Object[] items = (Object[]) lstRecset.get(0);
			
			//DBセッションの設定
			searchDB_Seiho = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB2);

			//種別コード（1桁目）チェック用SQLを作成
			strSql2 = createCheckShubetuCd1SQL(items[0].toString(), strSql2);
			
			//種別コード（1桁目）チェック用SQLを実行
			lstRecset = searchDB_Seiho.dbSearch(strSql2.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000306", "種別コード", items[0].toString() + items[1].toString(), "");
			}

			//種別コード（2～3桁目）チェック用SQLを作成
			strSql3 = createCheckShubetuCd2SQL(items[1].toString(), strSql3);
			
			//種別コード（2～3桁目）チェック用SQLを実行
			lstRecset = searchDB_Seiho.dbSearch(strSql3.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.一般Exception,"E000306", "種別コード", items[0].toString() + items[1].toString(), "");
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
			//配合データ取得用SQLを作成
			strSql = createCheckGenryoCdSQL(reqData, strSql);
			
			//配合データ取得用SQLを実行
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql.toString());

			//新規原料が存在する場合
			if (lstRecset.size() > 0) {
				em.ThrowException(ExceptionKind.一般Exception,"E000307", "", "", "");
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
	 * 試作品データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：作成SQL
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// 機能リクエストデータの取得
			String strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_shubetu");
			strSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,no_shubetu),2) as no_shubetu");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ取得SQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
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
			strSql.append("  cd_code");
			strSql.append(" FROM");
			strSql.append("  ma_name");
			strSql.append(" WHERE cd_bunrui = 25");
			strSql.append(" AND cd_code = RIGHT('00' + '" + strShubetuCd + "',2)");

		} catch (Exception e) {
			this.em.ThrowException(e, "種別コード（2～3桁目）存在チェックSQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
	}

	/**
	 * 新規原料コード存在チェックSQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：作成SQL
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckGenryoCdSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// 機能リクエストデータの取得
			String strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_genryo");
			strSql.append(" FROM");
			strSql.append("  tr_haigo");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			strSql.append(" AND LEFT(cd_genryo,1) = 'N'");

		} catch (Exception e) {
			this.em.ThrowException(e, "新規原料コード存在チェックSQL作成処理が失敗しました。");
		} finally {
		}
		
		return strSql;
	}

}
