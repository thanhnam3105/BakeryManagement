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
 * 【S2-37 SA380】 原料データ管理②処理DBチェック
 *  : 原料データの更新・登録時DBチェック
 * @author k-katayama
 * @since  2009/04/17
 */
public class GenryoDataKanri2DataCheck extends DataCheck{

	/**
	 * 原料データ管理処理DBチェック処理用コンストラクタ 
	 */
	public GenryoDataKanri2DataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 *原料データ管理処理DBチェック
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

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			
			//機能リクエストデータの取得
			String strShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			String strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");

			//検索DB管理を生成
			super.createSearchDB();
			
			//①:処理区分による、処理分岐
			if ( strShoriKbn.equals("0") ) {				//登録時

				//②:原料データ管理②DBチェックSQL作成処理メソッドを呼び出し、DBチェック用SQLを取得する
				strSql = genryoKanriCheckSQL(strKaishaCd,strGenryoCd,strShoriKbn);

				//③:取得したSQLより、検索処理を行う。
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//④:検索結果が重複している場合、一般Exceptionを発生させる。
				if (lstRecset.size() != 0){
					String strData = strKaishaCd + "," + strGenryoCd;
					em.ThrowException(ExceptionKind.一般Exception,"E000302", "会社コード,原料コード", strData, "");
				}
				
				//処理を終える
			} else if ( strShoriKbn.equals("1") ) {	//更新時
				//⑤:【更新用】原料データ管理②DBチェックSQL作成処理メソッドを呼び出し、DBチェック用SQLを取得する
				strSql = genryoKanriCheckSQL(strKaishaCd,strGenryoCd,strShoriKbn);
				
				//⑥:【更新用】取得したSQLより、検索処理を行う。
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//⑦:【更新用】検索結果が存在しない場合、一般Exceptionを発生させる。
				if (lstRecset.size() == 0){
					String strData = strKaishaCd + "," + strGenryoCd;
					em.ThrowException(ExceptionKind.一般Exception,"E000301", "会社コード,原料コード", strData, "");
				}
				
				//⑧：確定コード入力済み時の存在チェック
				if ( !(strKakuteiCd.equals(null) || strKakuteiCd.equals("")) ) {
					//⑨:【登録(確定コード)用】原料データ管理②DBチェックSQL作成処理メソッドを呼び出し、DBチェック用SQLを取得する
					strSql = genryoKanriCheckSQL(strKaishaCd,strKakuteiCd,strShoriKbn);

					//⑩:【登録(確定コード)用】取得したSQLより、検索処理を行う。
					lstRecset = searchBD.dbSearch(strSql.toString());
						
					//⑪:【登録(確定コード)用】検索結果が存在していない場合、一般Exceptionを発生させる。
					if (lstRecset.size() == 0){
						String strData = strKaishaCd + "," + strKakuteiCd;
						em.ThrowException(ExceptionKind.一般Exception,"E000305", "会社コード,確定コード", strData, "");
					}
				}
				
				//処理を終える
				
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
	 * 原料データ管理②DBチェック用SQL作成
	 * @param strKaishaCd : 会社コード
	 * @param strGenryoCd : 原料コード
	 * @param strShoriKbn : 処理区分
	 * @return 作成したSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoKanriCheckSQL(String strKaishaCd, String strGenryoCd, String strShoriKbn) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		
		try {
			
			//①：リクエストデータより、原料データDBチェックを行う為のSQLを作成する。
			
			//検索対象 ： 原料分析情報マスタ、原料マスタ
			strRetSql.append(" SELECT M401.cd_kaisha, M401.cd_genryo, M402.cd_busho ");
			strRetSql.append(" FROM ma_genryo M401 ");
			strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
			strRetSql.append("   ON   M402.cd_kaisha = M401.cd_kaisha " );
			strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo " );
			strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd );
			strRetSql.append("   AND  M401.cd_genryo ='" + strGenryoCd + "' ");
			if ( strShoriKbn.equals("0") ) {
				strRetSql.append("   AND  M402.cd_busho =" + ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD"));
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSql;
	}

}
