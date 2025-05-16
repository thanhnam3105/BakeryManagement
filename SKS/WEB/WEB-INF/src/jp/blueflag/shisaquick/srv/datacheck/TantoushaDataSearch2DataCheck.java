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
			// ADD 2013/12/18 QP@30154 okano start
			String strKinoId = null;
			// ADD 2013/12/18 QP@30154 okano end
			String strDataId = null;

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					// ADD 2013/12/18 QP@30154 okano start
					strKinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/12/18 QP@30154 okano end
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
			// ADD 2013/11/7 QP@30154 okano start
			//権限データが「所属会社のみ」の場合
			else if (strDataId.equals("2")) {
				//ユーザIDの取得
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//SQL文の作成
				strSql = TantoushaDataSearch2ExistenceCheckSQL2(reqData, strSql);

				//SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());

				//データが存在しない場合
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.一般Exception,"E000303", "ユーザID", "", "");
				}
				// ADD 2013/12/18 QP@30154 okano start
				//編集権限が「氏名、パスワードのみ」のとき、キャッシュレスユーザを検索しない
				if(strKinoId.equals("21") == false && strKinoId.equals("20") == false){
					strSql = new StringBuffer();
					strSql = TantoushaDataSearch2ExistenceCheckSQLNew(reqData, strSql);

					// SQLを実行
					super.createSearchDB();
					lstRecset = searchBD.dbSearch(strSql.toString());
					//データが存在しない場合はそのまま
					if (lstRecset.size() == 0){

					//データが存在する場合にエラーチェック
					} else {
						em.ThrowException(ExceptionKind.一般Exception,"E000303", "ユーザID", "", "");
					}
				}
				// ADD 2013/12/18 QP@30154 okano end
			}
			// ADD 2013/11/7 QP@30154 okano end
//【QP@10713】2011/10/26 TT H.SHIMA ADD START 「担当者マスタ(営業)の登録内容は表示しない。」
			else{
				//ユーザIDの取得
				strUserId = reqData.getFieldVale(0, 0, "id_user");

				//ユーザ検索
				strSql.append(" SELECT cd_kengen,flg_eigyo	");
				strSql.append("	FROM ma_user ");
				strSql.append("	JOIN ma_busho ");
				strSql.append(" 	ON ma_user.cd_busho = ma_busho.cd_busho ");
				strSql.append("		AND ma_user.cd_kaisha = ma_busho.cd_kaisha ");
				strSql.append(" WHERE id_user= "+strUserId);

				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());

				//データが存在しない場合はそのまま
				if (lstRecset.size() == 0){
					// ADD 2013/12/18 QP@30154 okano start
					//編集権限が「氏名、パスワードのみ」のとき、キャッシュレスユーザを検索しない
					if(strKinoId.equals("21") == false && strKinoId.equals("20") == false){
						strSql = new StringBuffer();
						strSql = TantoushaDataSearch2ExistenceCheckSQLNew(reqData, strSql);

						// SQLを実行
						super.createSearchDB();
						lstRecset = searchBD.dbSearch(strSql.toString());
						//データが存在しない場合はそのまま
						if (lstRecset.size() == 0){

						//データが存在する場合にエラーチェック
						} else {
							em.ThrowException(ExceptionKind.一般Exception,"E000303", "ユーザID", "", "");
						}
					}
					// ADD 2013/12/18 QP@30154 okano end

				}
				//データが存在する場合にエラーチェック
				else{
					//検索結果取得
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[1],"");

					//営業はエラー
					if( cd_kengen.equals("1")){
						em.ThrowException(ExceptionKind.一般Exception,"E000330","ユーザ ID", "", "");
					}
				}
			}
//【QP@10713】2011/10/26 TT H.SHIMA ADD END   「担当者マスタ(営業)の登録内容は表示しない。」

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
	// ADD 2013/11/7 QP@30154 okano start
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
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQL2(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strUserId = null;

			//ユーザIDの取得
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL文の作成
			// MOD 2013/12/18 QP@30154 okano start
//				strSql.append("SELECT id_user");
//				strSql.append(" FROM ma_user");
//				strSql.append(" WHERE id_user = ");
//				strSql.append(strUserId);
//				strSql.append(" AND cd_kaisha = ");
//				strSql.append(userInfoData.getCd_kaisha());
			strSql.append("SELECT id_user ");
			strSql.append(" FROM ( ");
			strSql.append(" SELECT id_user,cd_kaisha,cd_busho ");
			strSql.append(" FROM ma_user ");
			strSql.append(" UNION ");
			strSql.append(" SELECT id_user,cd_kaisha,cd_busho ");
			strSql.append(" FROM ma_user_new ");
			strSql.append(" ) alluser ");
			strSql.append(" JOIN ma_busho ");
			strSql.append(" 	ON alluser.cd_busho = ma_busho.cd_busho ");
			strSql.append(" 	AND alluser.cd_kaisha = ma_busho.cd_kaisha ");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			strSql.append(" AND alluser.cd_kaisha = ");
			strSql.append(userInfoData.getCd_kaisha());
			strSql.append(" AND flg_eigyo IS NULL ");
			// MOD 2013/12/18 QP@30154 okano end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/11/7 QP@30154 okano end
	// ADD 2013/12/18 QP@30154 okano start
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
	private StringBuffer TantoushaDataSearch2ExistenceCheckSQLNew(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strUserId = null;

			//ユーザIDの取得
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			//SQL文の作成
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user_new");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/12/18 QP@30154 okano end

}
