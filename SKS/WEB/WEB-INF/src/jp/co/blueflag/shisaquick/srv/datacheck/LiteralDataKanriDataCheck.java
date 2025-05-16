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
 * リテラルデータ管理データチェック処理 : リテラルデータ管理のデータチェックを行う。
 * 
 * @author itou
 * @since 2009/04/20
 */
public class LiteralDataKanriDataCheck extends DataCheck{
	
	/**
	 * リテラルマスタメンテ ： リテラルデータ管理のチェック処理用コンストラクタ : インスタンス生成
	 */
	public LiteralDataKanriDataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * リテラルマスタメンテ : リテラルデータ情報のデータチェックを行う。
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

			//処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			// カテゴリコード
			String strCategoryCd = reqData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = reqData.getFieldVale(0, 0, "cd_literal");
			if (!(strLiteralCd.equals(""))) {
				//SQL文の作成
				strSql = literalDataKanriExistenceCheckSQL(reqData, strSql);
				
				//SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			//登録の場合
			if (strShotriKbn.equals("1")) {
				//チームコードが入力されている場合
				if (!(strLiteralCd.equals(""))) {
					//データが存在する場合
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.一般Exception,"E000304", "カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString(), "");
					}
				}
			//更新・削除の場合
			} else {
				//データが存在しない場合
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.一般Exception,"E000305","カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString(), "");
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ管理データチェック処理に失敗しました。");
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
	 * リテラルコード存在チェックSQL作成
	 *  : リテラルの存在チェックをするSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer literalDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");

			//SQL文の作成
			strSql.append("SELECT cd_category");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
