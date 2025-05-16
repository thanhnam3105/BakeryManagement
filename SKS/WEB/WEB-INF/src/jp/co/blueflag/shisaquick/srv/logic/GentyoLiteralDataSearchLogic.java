package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 *
 * リテラルマスタメンテ：原資材調達部用リテラル情報検索DB処理
 *  : リテラルマスタメンテ：リテラル情報を検索する。（第二カテゴリも取得）
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoLiteralDataSearchLogic extends LogicBase{

	/**
	 * リテラルマスタメンテ：リテラル情報検索DB処理
	 * : インスタンス生成
	 */
	public GentyoLiteralDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * リテラルマスタメンテ：リテラル情報取得SQL作成
	 *  : リテラル情報を取得するSQLを作成。
	 * @param reqData : リクエストデータ
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
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//SQL文の作成
			strSql = createSQL(reqData, strSql);

			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageLiteralData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}
		return resKind;
	}

	/**
	 * リテラル情報取得SQL作成
	 *  : リテラル情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strCategoryCd = null;
			String strLiteralCd = null;
//			String dataId = null;

			//カテゴリコードの取得
			strCategoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//リテラルコードの取得
			strLiteralCd = reqData.getFieldVale(0, 0, "cd_literal");

//			//権限データID取得
//			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
//				if (userInfoData.getId_gamen().get(i).toString().equals("300")) {
//					//原調用リテラルマスタメンテナンス画面のデータIDを設定
//					dataId = userInfoData.getId_data().get(i).toString();
//				}
//			}

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,no_sort");
			strSql.append(" ,ISNULL(biko,'') as biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,ISNULL(cd_group,'') as cd_group");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd);
			strSql.append("'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");

//			//検索条件権限設定
//			//同一グループ
//			if(dataId.equals("1")) {
//				strSql.append(" AND (cd_group = ");
//				strSql.append(userInfoData.getCd_group());
//				strSql.append(" OR cd_group IS NULL) ");
//			}

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * リテラルマスタメンテ：リテラル情報パラメーター格納
	 *  : リテラル情報をレスポンスデータへ格納する。
	 * @param lstLiteralData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralData(List<?> lstLiteralData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstLiteralData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstLiteralData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				resTable.addFieldVale(i, "no_sort", items[2].toString());
				resTable.addFieldVale(i, "biko", items[3].toString());
				resTable.addFieldVale(i, "flg_edit", items[4].toString());
				resTable.addFieldVale(i, "cd_group", items[5].toString());
				 //第二リテラル使用フラグがnullなら、第二リテラル未使用のため、0or空白を返す
				if (items[8] == null){
					resTable.addFieldVale(i, "cd_2nd_literal", "");
					resTable.addFieldVale(i, "nm_2nd_literal", "");
					resTable.addFieldVale(i, "flg_2ndedit", "0");
				}else{
					resTable.addFieldVale(i, "cd_2nd_literal", items[6].toString());
					resTable.addFieldVale(i, "nm_2nd_literal", items[7].toString());
					resTable.addFieldVale(i, "flg_2ndedit", items[8].toString());
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータ検索処理に失敗しました。");

		} finally {

		}

	}

}
