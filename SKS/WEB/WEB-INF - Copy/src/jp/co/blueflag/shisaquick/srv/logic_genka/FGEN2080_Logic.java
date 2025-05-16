package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * 担当者（営業）データ管理DB：担当者データを管理（登録・更新・削除）するSQLを作成する。
 * M101_userテーブルから、データの抽出を行う。
 * 取得情報を、レスポンスデータ保持「機能ID：FGEN2080O」に設定する。
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2080_Logic extends LogicBase {

	/**
	 * 担当者データ（営業）管理コンストラクタ ： インスタンス生成
	 */
	public FGEN2080_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者データ（営業）操作管理 ： 担当者データ（営業）の操作管理を行う。
	 *
	 * @param reqData
	 *            : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();
//		StringBuffer strSql3 = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			// 機能リクエストデータより、処理区分を抽出し、各処理メソッドを呼出す。
			// 処理区分：登録
			if (strShotriKbn.equals("1")) {

				// 担当者データ登録SQL作成処理を呼出す。
				strSql = tantoushaKanriInsertSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
				// テーブルBean取得
				RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
				// データ件数分共有メンバーを登録
				for (int i = 0; i < reqTableBean.GetCnt(); i++) {
					// 共有メンバー登録SQL作成処理
					strSql = tantoushaMemberInsertSQL(reqTableBean, i);
					// 共有メンバーがない時は処理不要
					if (strSql != null) {
						execDB.execSQL(strSql.toString()); // SQLを実行
					}
				}
				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end

			}
			// 処理区分：更新
			else if (strShotriKbn.equals("2")) {

				//ADD 2013/9/25 okano【QP@30151】No.28 start
				String strUKinoId = userInfoData.getId_kino().get(0).toString();
				if(strUKinoId.equals("23")){
					String strPassId = reqData.getFieldVale( 0, 0, "id_user");
					StringBuffer strSqlPass = new StringBuffer();
					strSqlPass.append("SELECT password FROM ma_user ");
					strSqlPass.append("WHERE id_user=");
					strSqlPass.append(strPassId);

					createSearchDB();
					List<?> lstRecset = searchDB.dbSearch(strSqlPass.toString());

					if(lstRecset.get(0).toString().equals(reqData.getFieldVale( 0, 0, "password"))){
						em.ThrowException(ExceptionKind.一般Exception, "E000228", "パスワード", "", "");
					}
					//リストの破棄
					removeList(lstRecset);
					if (searchDB != null) {
						//セッションのクローズ
						searchDB.Close();
						searchDB = null;
					}
					strSqlPass = null;
				}
				//ADD 2013/9/25 okano【QP@30151】No.28 end

				// 担当者データ更新SQL作成処理を呼出す。
				strSql = tantoushaKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
				// 共有メンバー削除（削除担当者 = id_user）
				strSql = tantoushaMemberDeleteSQL(reqData, false);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//テーブルBean取得
				RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
				// データ件数分共有メンバーを登録
				for (int i = 0; i < reqTableBean.GetCnt(); i++) {
					// 共有メンバー登録SQL作成処理
					strSql = tantoushaMemberInsertSQL(reqTableBean, i);
					// 共有メンバーがない時は処理不要
					if (strSql != null) {
						execDB.execSQL(strSql.toString()); // SQLを実行
					}
				}
				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end

			}
			// 処理区分：削除
			else if (strShotriKbn.equals("3")) {

				// 担当者データ削除SQL作成処理を呼出す。
				strSql = tantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行

				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
				// 共有メンバー削除（削除担当者 = id_user OR id_member）
				strSql = tantoushaMemberDeleteSQL(reqData, true);
				execDB.execSQL(strSql.toString()); // SQLを実行
				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end

				// 製造担当者データ削除SQL作成処理を呼出す。
				strSql2 = seizouTantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQLを実行

			}

			// コミット
			execDB.Commit();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageGroupDataKanri(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			// MOD 2013/9/25 okano【QP@30151】No.28 end
//			em.ThrowException(ExceptionKind.一般Exception, "E000329", "", "", "");
			this.em.ThrowException(e, "");
			// MOD 2013/9/25 okano【QP@30151】No.28 end

			//this.em.ThrowException(e, "担当者データ（営業）の登録・更新・削除処理が失敗しました。");
		} finally {
			if (execDB != null) {
				// セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//変数の削除
			strSql = null;
			strSql2 = null;
//			strSql3 = null;
		}
		return resKind;
	}

	/**
	 * 担当者データ（営業）登録SQL作成
	 *  : 担当者データ（営業）登録用　SQLを作成し、各データをDBに登録する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaKanriInsertSQL(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			// ユーザID
			String strUserId = "";
			// パスワード
			String strPassword = "";
			// 権限
			String strKengenCd = "";
			// 氏名
			String strNmUser = "";
			// 所属会社
			String strKaishaCd = "";
			// 所属部署
			String strBushoCd = "";
			// 役職
			String strJosiCd = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");
			strPassword = requestData.getFieldVale("table", 0, "password");
			strKengenCd = requestData.getFieldVale("table", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("table", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("table", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("table", 0, "cd_busho");
			strJosiCd = requestData.getFieldVale("table", 0, "id_josi");

			if(strJosiCd.equals("")){
				strJosiCd = "NULL";
			}

			// SQL文の作成
			//レコードを追加
			strInsSql.append("INSERT INTO ma_user (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,password");
			strInsSql.append(" ,cd_kengen");
			strInsSql.append(" ,nm_user");
			strInsSql.append(" ,cd_kaisha");
			strInsSql.append(" ,cd_busho");
			strInsSql.append(" ,cd_group");
			strInsSql.append(" ,cd_team");
			strInsSql.append(" ,cd_yakushoku");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			strInsSql.append(" ,id_josi");
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strInsSql.append(" ,dt_password");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			strInsSql.append(" ) VALUES (");
			strInsSql.append("  " + strUserId);
			strInsSql.append(" ,'" + strPassword + "'");
			strInsSql.append(" ," + strKengenCd);
			strInsSql.append(" ,'" + strNmUser + "'");
			strInsSql.append(" ," + strKaishaCd);
			strInsSql.append(" ," + strBushoCd);
			strInsSql.append(" ,NULL");
			strInsSql.append(" ,NULL");
			strInsSql.append(" ,NULL");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + strJosiCd);
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ（営業）登録SQL作成処理が失敗しました。");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 担当者データ（営業）更新SQL作成
	 *  : 担当者データ（営業）更新用　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaKanriUpdateSQL(RequestResponsKindBean requestData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";
			// パスワード
			String strPassword = "";
			// 権限
			String strKengenCd = "";
			// 氏名
			String strNmUser = "";
			// 所属会社
			String strKaishaCd = "";
			// 所属部署
			String strBushoCd = "";
		//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//			// 役職
//			String strJosiCd = "";
		//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

			strUserId = requestData.getFieldVale("table", 0, "id_user");
			strPassword = requestData.getFieldVale("table", 0, "password");
			strKengenCd = requestData.getFieldVale("table", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("table", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("table", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("table", 0, "cd_busho");
		//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//			strJosiCd = requestData.getFieldVale("table", 0, "id_josi");
//
//			if(strJosiCd.equals("")){
//				strJosiCd = "NULL";
//			}
		//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19

			//SQL文の作成
			strSql.append("UPDATE ma_user SET");
			strSql.append(" password = '");
			strSql.append(strPassword + "'");
			strSql.append(" ,cd_kengen = ");
			strSql.append(strKengenCd);
			strSql.append(" ,nm_user = '");
			strSql.append(strNmUser + "'");
			strSql.append(" ,cd_kaisha = ");
			strSql.append(strKaishaCd);
			strSql.append(" ,cd_busho = ");
			strSql.append(strBushoCd);
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			//  2015/03/03 DEL start TT.Kitazawa【QP@40812】No.19
//			strSql.append(" ,id_josi = ");
//			strSql.append(strJosiCd);
			//  2015/03/03 DEL end TT.Kitazawa【QP@40812】No.19
			//MOD 2016/7/14 shima start
			//ADD 2013/10/2 okano【QP@30151】No.28 start
			strSql.append(" ,dt_password = GETDATE() ");
//			strSql.append(" ,dt_password = CASE ");
//			strSql.append(" WHEN password = '");
//			strSql.append(strPassword);
//			strSql.append("' THEN dt_password ");
//			strSql.append(" ELSE GETDATE() END");
			//ADD 2013/10/2 okano【QP@30151】No.28 end
			//MOD 2016/7/14 shima start
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ（営業）更新SQL作成処理が失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * 担当者データ削除（営業）SQL作成
	 *  : 担当者データ（営業）削除用　SQLを作成し、各データをDBに削除する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaKanriDeleteSQL(RequestResponsKindBean requestData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ削除（営業）SQL作成処理が失敗しました。");
		} finally {

		}
		return strSql;
	}


	/**
	 * 製造担当者データ（営業）削除SQL作成
	 *  : 製造担当者データ（営業）削除用　SQLを作成し、各データをDBに削除する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer seizouTantoushaKanriDeleteSQL(RequestResponsKindBean requestData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_tantokaisya");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "製造担当者データ（営業）削除SQL作成処理が失敗しました。");
		} finally {

		}
		return strSql;
	}
	/**
	 * 管理結果パラメーター格納
	 *  : 担当者データの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 start
	/**
	 * 担当者（営業）共有メンバー削除SQL作成
	 *  : 担当者（営業）共有メンバー削除用　SQLを作成する。
	 * @param reqData：リクエストデータ
	 * @param blMember：true ユーザ削除、false 更新時
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaMemberDeleteSQL(RequestResponsKindBean requestData, Boolean blMember)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		try {
			// ユーザID
			String strUserId = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM ma_member");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			if (blMember) {
				strSql.append(" OR id_member = ");
				strSql.append(strUserId);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）共有メンバー削除SQL作成処理が失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * 担当者（営業）共有メンバー登録SQL作成
	 *  : 担当者（営業）共有メンバー登録用　SQLを作成する。
	 * @param reqData：リクエストデータ RequestResponsTableBean
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaMemberInsertSQL(RequestResponsTableBean reqTableData, int cnt)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strInsSql = new StringBuffer();
		try {
			// ユーザID（１件目のみに設定）
			String strUserId = reqTableData.getFieldVale( 0, "id_user");
			// 共有メンバー
			String strMemberId = reqTableData.getFieldVale( cnt, "id_member");
			if (strMemberId.equals("")) {
				// 登録不要
				return null;
			}

			//SQL文の作成
			strInsSql.append("INSERT INTO ma_member (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,id_member");
			strInsSql.append(" ,no_sort");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			strInsSql.append(" ) VALUES (");
			strInsSql.append("  " + strUserId );
			strInsSql.append(" ," + strMemberId );
			strInsSql.append(" ," + (cnt+1));
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "担当者（営業）共有メンバー登録SQL作成処理が失敗しました。");
		} finally {

		}
		return strInsSql;
	}
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.19 end

}

