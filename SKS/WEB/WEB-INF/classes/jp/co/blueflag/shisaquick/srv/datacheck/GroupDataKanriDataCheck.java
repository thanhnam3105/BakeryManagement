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
 * グループマスタメンテ：グループ情報データチェック処理
 *  : グループマスタメンテ：グループ情報のデータチェックを行う。
 * @author jinbo
 * @since  2009/04/17
 */
public class GroupDataKanriDataCheck extends DataCheck{

	/**
	 * グループマスタメンテ：グループ情報データチェック処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public GroupDataKanriDataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * グループマスタメンテ：グループ情報データチェック
	 *  : グループ情報のデータチェックを行う。
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
			String strGroupCd = null;

			//処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			//グループコードの取得
			strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
			//DBのグループコードがsmall int型のためif文によりSQLエラーを防ぐ
			if (!(strGroupCd.equals(""))) {
				// SQL文の作成
				strSql = groupKanriExistenceCheckSQL(reqData, strSql);

				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			//登録の場合
			if (strShotriKbn.equals("1")) {
				//グループコードが入力されている場合
				if (!strGroupCd.equals("")) {
					//データが存在する場合
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.一般Exception,"E000302", "グループコード", strGroupCd.toString(), "");
					}
				}
				
			//更新・削除の場合
			} else {
				//データが存在しない場合
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.一般Exception,"E000301", "グループコード", strGroupCd.toString(), "");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "グループ管理データチェック処理に失敗しました。");
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
	 * グループコード存在チェックSQL作成
	 *  : グループの存在チェックをするSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String strGroupCd = null;

			//グループコードの取得
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL文の作成
			strSql.append("SELECT cd_group");
			strSql.append(" FROM ma_group");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
