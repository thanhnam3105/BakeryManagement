package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 【S2-36 SA370】 原料データ管理処理DBチェック
 * @author k-katayama
 * @since  2009/04/17
 */
public class GenryoDataKanriDataCheck extends DataCheck{

	/**
	 * 原料データ管理処理DBチェック処理用コンストラクタ 
	 */
	public GenryoDataKanriDataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 *原料データ管理処理DBチェック
	 * : 原料データ削除時のDBチェック
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

		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		try {
			String strKaishaCd = "";							//リクエストデータ : 会社コード
			String strGenryoCd = "";							//リクエストデータ : 原料コード
//			String strHaishiFlg = "";							//リクエストデータ : 廃止フラグ

			// 機能リクエストデータの取得
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
//			strHaishiFlg = reqData.getFieldVale(0, 0, "flg_haishi");

			//①：原料データ管理DBチェックSQL作成処理メソッドを呼び出し、DBチェック用SQLを取得する
			strSQL = this.genryoCheckSQL(strKaishaCd,strGenryoCd);
			
			//②：取得したSQLより、検索処理を行う。
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSQL.toString());

			//③：検索結果が存在しない場合、一般Exceptionを発生させる。
			if (lstRecset.size() == 0){
				String strData = strKaishaCd + "," + strGenryoCd;
				em.ThrowException(ExceptionKind.一般Exception,"E000301", "会社コード,原料コード", strData, "");
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
			strSQL = null;
		}
		
	}

	/**
	 * 原料データ管理DBチェックSQL作成
	 *   ※ 対象テーブル : ma_genryo, ma_genryokojo
	 * @param strKaishaCd : 会社コード
	 * @param strGenryoCd : 原料コード
	 * @return 作成したSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoCheckSQL(String strKaishaCd, String strGenryoCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSQL = new StringBuffer();
		
		try {
			
			//①：リクエストデータより、原料データDBチェックを行う為のSQLを作成する。
			
			//検索対象 ： 原料分析情報マスタ、原料マスタ(新規原料のみ)
			strRetSQL.append(" SELECT M401.cd_kaisha, M401.cd_genryo, M402.cd_busho ");
			strRetSQL.append(" FROM ma_genryo M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("   ON   M402.cd_kaisha = M401.cd_kaisha " );
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo " );
			strRetSQL.append(" WHERE M401.cd_kaisha =" + strKaishaCd );
			strRetSQL.append("   AND  M401.cd_genryo ='" + strGenryoCd + "' ");
			strRetSQL.append("   AND  M402.cd_busho =" + ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSQL;
	}

}
