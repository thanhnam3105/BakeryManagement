package jp.co.blueflag.shisaquick.srv.logic_gencho;

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
 *  ベース単価取得
 *  : ベース単価情報を取得する。
 *  機能ID：FGEN3500
 *
 * @author BRC Koizumi
 * @since  2016/09/05
 */
public class FGEN3500_Logic extends LogicBase{

	/**
	 * ベース単価取得処理
	 * : インスタンス生成
	 */
	public FGEN3500_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * ベース単価取得
	 *  : ベース単価情報を取得する。
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//レスポンスデータの形成
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除

		}
		return resKind;

	}

	/**
	 * レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author BRC Koizumi
	 * @since  2016/09/05
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {

			// テーブル名
			String strTblNm = "xmlFGEN3500";

			// ①データ取得SQL作成
			strSqlBuf = this.createSQL(reqData);

			// ②共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// 検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
			}

			// ③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// ④追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "ベース単価一覧　データ検索処理が失敗しました。");

		} finally {

			// リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				// セッションの解放
				this.searchDB.Close();
				searchDB = null;
			}

			// 変数の削除
			strSqlBuf = null;
		}
	}

	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();
		StringBuffer strWork = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {

			// 検索条件取得
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");			// メーカーコード
			String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");		// 包材コード
			String strNoHansu = reqData.getFieldVale(0, 0, "no_hansu");			// 版数
			String strChkKakunin = reqData.getFieldVale(0, 0, "chk_kakunin");	// 登録チェック
			String strChkShonin = reqData.getFieldVale(0, 0, "chk_shonin");		// 承認チェック
			String strChkHanOnly = reqData.getFieldVale(0, 0, "chk_hanonly");	// 有効版のみチェック

			// メーカー名
			if(!strCdMaker.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.cd_maker = " + strCdMaker);
			}

			// 包材
			if(!strCdHouzai.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.cd_houzai = " + strCdHouzai);
			}

			// 版数
			if(!strNoHansu.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.no_hansu = " + strNoHansu);
			}

			// 登録（確認）済み
			if(!strChkKakunin.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.id_kakunin IS NOT NULL ");
			}
//			else{
//				if(strWork.length() > 0){
//					strWork.append(" AND ");
//				}
//				strWork.append(" LST.id_kakunin IS NULL ");
//			}

			// 承認済み
			if(!strChkShonin.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.id_shonin IS NOT NULL ");
			}
//			else{
//				if(strWork.length() > 0){
//					strWork.append(" AND ");
//				}
//				strWork.append(" LST.id_shonin IS NULL ");
//			}

			// 版のみ（有効データ）
			if(!strChkHanOnly.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.dt_yuko < GETDATE()  ");
			}

			// 未使用
			if(strWork.length() > 0){
				strWork.append(" AND ");
			}
			strWork.append(" ISNULL(M1.flg_mishiyo,'0') != '1' ");

			// SQL文の作成
			strSql.append("SELECT ");
			strSql.append("    LST.cd_maker AS cd_maker,");
			strSql.append("    LST.cd_houzai AS cd_houzai,");
			strSql.append("    LST.no_hansu AS no_hansu,");
			strSql.append("    M1.nm_literal AS nm_maker,");
			strSql.append("    M1.nm_2nd_literal AS nm_houzai,");
			strSql.append("    M3.nm_user AS nm_kakunin,");
			strSql.append("    M4.nm_user AS nm_shonin,");
			strSql.append("    CONVERT(VARCHAR,LST.dt_yuko,111) AS dt_yuko,");
			strSql.append("    CONVERT(VARCHAR,LST.dt_koshin,111) AS dt_koshin, ");
			strSql.append("    LST.name_houzai AS nm_base_houzai ");
			strSql.append("FROM ");
			strSql.append("    ma_base_price_list LST    ");
			strSql.append("    LEFT JOIN ma_literal M1 ON M1.cd_category = 'maker_name' ");
			strSql.append("      AND M1.cd_literal = LST.cd_maker");
			strSql.append("      AND M1.cd_2nd_literal = LST.cd_houzai");
			strSql.append("    LEFT JOIN ma_user M3 ON M3.id_user = LST.id_kakunin");
			strSql.append("    LEFT JOIN ma_user M4 ON M4.id_user = LST.id_shonin");

			if(strWork.length() > 0){
				strWhere.append(" WHERE ");
				strWhere.append(strWork);
			}

			strSql.append(strWhere);

			strSql.append(" ORDER BY cd_maker, cd_houzai, no_hansu desc ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				// 処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				// 結果をレスポンスデータに格納
				resTable.addFieldVale(i, "no_row", toString(i + 1));

				resTable.addFieldVale(i, "cd_maker", toString(items[0],""));		// メーカーコード
				resTable.addFieldVale(i, "cd_houzai", toString(items[1],""));		// 包材コード
				resTable.addFieldVale(i, "no_hansu", toString(items[2],""));		// 版数
				resTable.addFieldVale(i, "nm_maker", toString(items[3],""));		// メーカー名
//				resTable.addFieldVale(i, "nm_houzai", toString(items[4],""));		// 包材名
				resTable.addFieldVale(i, "nm_han_houzai", toString(items[4],""));		// 版の包材名（旧:包材名）
				resTable.addFieldVale(i, "nm_kakunin", toString(items[5],""));		// 確認者
				resTable.addFieldVale(i, "nm_shonin", toString(items[6],""));		// 承認者
				resTable.addFieldVale(i, "dt_yuko", toString(items[7],""));			// 有効開始日
				resTable.addFieldVale(i, "dt_koshin", toString(items[8],""));		// 変更日
				resTable.addFieldVale(i, "nm_base_houzai", toString(items[9],""));	// ベース包材名
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
