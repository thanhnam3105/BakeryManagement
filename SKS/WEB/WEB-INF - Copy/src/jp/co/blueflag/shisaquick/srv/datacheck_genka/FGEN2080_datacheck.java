package jp.co.blueflag.shisaquick.srv.datacheck_genka;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 担当者データ（営業）管理処理ＤＢチェック ：担当者データ（営業）管理のデータチェックを行う。
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2080_datacheck extends DataCheck {

	/**
	 * 担当者データ（営業）管理 ： 担当者データチェック処理用コンストラクタ : インスタンス生成
	 */
	public FGEN2080_datacheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者データ（営業）管理 ：  担当者データ（営業）のデータチェックを行う。
	 * 
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
			String strShotriKbn = null;
			String strUserId = null;

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			
			// ユーザIDの取得
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			
			//ユーザIDが入力されている場合
			if (!(strUserId.equals(""))) {
				
				// SQL文の作成
				strSql = tantoushaDataKanriExistenceCheckSQL(reqData, strSql);
	
				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
			}
			// 登録の場合
			if (strShotriKbn.equals("1")) {
				//ユーザIDが入力されている場合
				if (!strUserId.equals("")) {
					// データが存在する場合
					if (lstRecset.size() != 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000302", "ユーザID", strUserId.toString(), "");
					}
				}
				
			}
			// 更新・削除の場合
			else {
				// データが存在しない場合
				if (lstRecset.size() == 0) {
					em.ThrowException(ExceptionKind.一般Exception, "E000301", "ユーザID", strUserId.toString(), "");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ管理データチェック処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchBD != null) {
				// セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
			
			//変数の削除
			strSql = null;
		}
	}

	

	/**
	 * ユーザID存在チェックSQL作成 :ユーザIDの存在チェックをするSQLを作成。
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
	private StringBuffer tantoushaDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strUserId = null;

			// ユーザIDの取得
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			// SQL文の作成
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
