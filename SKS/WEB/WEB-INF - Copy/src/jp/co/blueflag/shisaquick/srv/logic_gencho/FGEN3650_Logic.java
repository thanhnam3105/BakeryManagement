package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * リテラルマスタメンテ：原資材調達部用 発注先一覧情報検索DB処理
 *
 *
 */
public class FGEN3650_Logic extends LogicBase {

	/**
	 * リテラルマスタメンテ：発注先一覧情報検索DB処理
	 *
	 */
	public FGEN3650_Logic() {
		// 基底クラスのコンストラクタ
		super();
	}

	/**
	 * リテラルマスタメンテ：発注先一覧情報取得SQL作成
	 *
	 * @param reqData
	 *            : リクエストデータ
	 * @param userInfoData
	 *            : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ユーザー情報退避
		userInfoData = _userInfoData;

		//レコード値格納リスト
		List<?> lstRecset = null;

		StringBuffer strSql = new StringBuffer();

		// レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		// テーブル名
		String strTblNm = "table";
		try {

			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String cd_kaisha = userInfoData.getCd_kaisha();

			strSql = literalKanriSelectSQL(reqData, cd_kaisha);

			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			String strXmlID = reqData.getID();
//			//検索結果がない場合
//			if (lstRecset.size() == 0){
//				em.ThrowException(ExceptionKind.警告Exception, "W000408", "検索結果に該当するデータが有りません。", "", "");
//
//			}
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);
			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(0));


		} catch (Exception e) {
			em.ThrowException(e, "資材テーブル検索処理が失敗しました。");
		} finally {

			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			// 変数の削除

		}
		return resKind;

	}


	/**
	 * リテラルデータ登録SQL作成
	 *  : リテラルデータ登録　SQLを作成し、各データをDBに更新する。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriSelectSQL(RequestResponsKindBean requestData, String cd_kaisha)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// リテラルテーブルSQL
		StringBuffer strSql = new StringBuffer();

		String strCdLiteral = requestData.getFieldVale(0, 0, "cd_literal");
		//String StrCdLiteral = strFormat(beforeStrCdLiteral);
		//int cdLiteral = Integer.parseInt(strCdLiteral);

		try {

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append(" name_tenmei_kaisha");
			strSql.append(" FROM ma_temmei");
			strSql.append(" WHERE cd_kaisha = '");
			strSql.append(cd_kaisha);
			strSql.append("'");
			strSql.append(" AND cd_temmei = ");
			strSql.append(strCdLiteral);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

//	// 全角を半角にする
//	private String strFormat(String str) {
//		char[] chars = str.toCharArray();
//		StringBuilder sb = new StringBuilder(str);
//		for (int i = 0; i < chars.length; i++) {
//			if (String.valueOf(chars[i]).getBytes().length >= 2) {
//				int c = (int) sb.charAt(i);
//				if (c >= 0xFF10 && c <= 0xFF19) {
//					sb.setCharAt(i, (char) (c - 0xFEE0));
//				}
//			}
//		}
//		return  sb.toString();
//	}
	/**
	 * 管理結果パラメーター格納
	 *  : リテラルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(List<?> lstMstData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			// メニューからのロード時はlstMstDataがnullのため
			if (lstMstData == null|| lstMstData.size() == 0) {

				// データが取得できない時：エラーにしない（jsのメッセージを返したい）
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "検索結果に該当するデータが有りません。");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			} else {

				for (int i = 0; i < lstMstData.size(); i++) {
					//処理結果の格納
					resTable.addFieldVale(0, "flg_return", "true");
					resTable.addFieldVale(0, "msg_error", "");
					resTable.addFieldVale(0, "no_errmsg", "");
					resTable.addFieldVale(0, "nm_class", "");
					resTable.addFieldVale(0, "cd_error", "");
					resTable.addFieldVale(0, "msg_system", "");

					Object items = (Object) lstMstData.get(i);

					// 結果をレスポンスデータに格納
					resTable.addFieldVale(i, "nm_literal", items.toString());
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
}
