package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【KPX@1602367】
 * 製品コードと製品名あいまい検索
 *  機能ID：FGEN3590
 *
 * @author BRC May Thu
 * @since  2016/09/13
 */
public class FGEN3590_Logic extends LogicBase{

	/**
	 * 製品コードと製品名検索
	 * : インスタンス生成
	 */
	public FGEN3590_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 製品コードと製品名あいまい検索
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
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void setData(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			// テーブル名
			String strTblNm = "table";

			//データ取得SQL作成
			strSqlBuf = this.createSQL(reqData);

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "製品コードと製品名検索処理が失敗しました。");

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

		try {
			String strNm_Seihin = reqData.getFieldVale("table", 0, "name_seihin");
			// SQL文の作成

    		strSql.append(" SELECT TOP 1000 cd_hin   ");
			strSql.append("       ,nm_seihin  ");
			strSql.append("       ,nisugata_hyoji  ");
			strSql.append(" FROM  vw_ma_seihin  ");
			if(strNm_Seihin != "" || strNm_Seihin != null){
				strSql.append(" WHERE cd_hin LIKE '%"+ strNm_Seihin + "%'");
				strSql.append(" OR");
				strSql.append(" nm_seihin LIKE '%"+ strNm_Seihin + "%'");
			}
			strSql.append(" order by cd_hin" );
			strSql.append("       ,nm_seihin  ");
			strSql.append("       ,nisugata_hyoji  ");
		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3590_Logic:createGenkaKihonSQL");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
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

				//処理結果
				resTable.addFieldVale(i, "cd_hin", toString(items[0],""));
				resTable.addFieldVale(i, "nm_seihin", toString(items[1],""));
				resTable.addFieldVale(i, "nisugata_hyoji", toString(items[2],""));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3590_Logic:createGenkaKihonSQL");

		} finally {

		}

	}

}
