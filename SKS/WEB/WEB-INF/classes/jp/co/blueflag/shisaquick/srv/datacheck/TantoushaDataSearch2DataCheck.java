package jp.co.blueflag.shisaquick.srv.datacheck;

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
 * 担当者マスタメンテ：担当者情報データチェック処理
 *  : 担当者マスタメンテ：担当者情報のデータチェックを行う。
 * @author jinbo
 * @since  2009/04/18
 */
public class TantoushaDataSearch2DataCheck extends DataCheck{

	/**
	 * 担当者マスタメンテ：担当者情報データチェック処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public TantoushaDataSearch2DataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者マスタメンテ：担当者情報データチェック
	 *  : 担当者情報のデータチェックを行う。
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
			String strUserId = null;
			String strDataId = null;

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//担当者マスタメンテナンス画面のデータIDを設定
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//権限データが「自分のみ」の場合
			if (strDataId.equals("1")) {
				//ユーザIDの取得
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//SQL文の作成
				strSql = TantoushaDataSearch2ExistenceCheckSQL(reqData, strSql);
				
				//SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//データが存在しない場合
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.一般Exception,"E000303", "ユーザID", "", "");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者管理②データチェック処理に失敗しました。");
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
	 * ユーザID存在チェックSQL作成
	 *  : 担当者の存在チェックをするSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String strUserId = null;

			//ユーザIDの取得
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL文の作成
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			strSql.append(" AND id_user = ");
			strSql.append(userInfoData.getId_user());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

}
