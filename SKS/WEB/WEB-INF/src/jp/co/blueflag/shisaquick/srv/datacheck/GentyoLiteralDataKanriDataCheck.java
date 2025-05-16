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
 * @author hisahori
 * @since 2014/10/10
 */
public class GentyoLiteralDataKanriDataCheck extends DataCheck{

	/**
	 * リテラルマスタメンテ ： リテラルデータ管理のチェック処理用コンストラクタ : インスタンス生成
	 */
	public GentyoLiteralDataKanriDataCheck() {
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
			// 第２リテラルコード
			String str2ndLiteralCd = reqData.getFieldVale(0, 0, "cd_2nd_literal");
			// 第２リテラル名
			String str2ndLiteralNm = reqData.getFieldVale(0, 0, "nm_2nd_literal");

			// リテラルコードが入力されていて
			if (!(strLiteralCd.equals(""))) {
				// 第２リテラル名が空白の場合
				if ((str2ndLiteralNm.equals(""))) {
					//SQL文の作成
					strSql = literalDataKanriExistenceCheckSQL(reqData, strSql);

					//SQLを実行
					super.createSearchDB();
					lstRecset = searchBD.dbSearch(strSql.toString());
				// 第２リテラルコードが空白ではない場合
				}else{
					//SQL文の作成
					strSql = literalDataKanriExistenceCheckSQL2nd(reqData, strSql);

					//SQLを実行
					super.createSearchDB();
					lstRecset = searchBD.dbSearch(strSql.toString());
				}
			}

			//新規登録の場合
			if (strShotriKbn.equals("1")) {
				// 第２リテラル名が空白の場合
				if ((str2ndLiteralNm.equals(""))) {
					//リテラル（コンボボックス）が選択されている場合
					if (!(strLiteralCd.equals(""))) {
						//
						if (lstRecset.size() != 0){
							em.ThrowException(ExceptionKind.一般Exception,"E000304", "カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString(), "");
						}
					}
				}else{
					//第２リテラル（コンボボックス）が選択されている場合
					if (!(str2ndLiteralCd.equals(""))) {
						if (lstRecset.size() != 0){
							em.ThrowException(ExceptionKind.一般Exception,"E000304", "カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString() + "第２リテラルコード : " + str2ndLiteralCd.toString(), "");
						}
					}

				}

			//更新・削除の場合
			} else {
				// 第２リテラル名が空白の場合
				if ((str2ndLiteralNm.equals(""))) {
					//データが存在しない場合
					if (lstRecset.size() == 0){
						em.ThrowException(ExceptionKind.一般Exception,"E000305","カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString(), "");
					}
				}else{
					//データが存在しない場合
					if (lstRecset.size() == 0){
						em.ThrowException(ExceptionKind.一般Exception,"E000305","カテゴリコード : " + strCategoryCd.toString(), "リテラルコード : " + strLiteralCd.toString() + "第２リテラルコード : " + str2ndLiteralCd.toString(), "");
					}
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
//			strSql.append(" AND cd_2nd_literal = ''"); // 第2リテラルコードが空白ではない ＝ 第2リテラルを使用していない

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
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
	private StringBuffer literalDataKanriExistenceCheckSQL2nd(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			// カテゴリコード
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// リテラルコード
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// リテラルコード
			String str2ndLiteralCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");

			//SQL文の作成
			strSql.append("SELECT cd_category");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			strSql.append(" AND cd_2nd_literal = '");
			strSql.append(str2ndLiteralCd+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}