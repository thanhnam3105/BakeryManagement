package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【KPX@1602367】
 * メインメニュー　発注作業中の情報を取得する
 *  機能ID：FGEN3490
 *
 * @author May Thu
 * @since  2016/09/01
 */
public class FGEN3490_Logic extends LogicBase{

	/**
	 * メインメニュー　注作業中の情報を取得する
	 * : インスタンス生成
	 */
	public FGEN3490_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * メインメニュー　注作業中の情報を取得する
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
			this.genkaKihonSetting(resKind, reqData);

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

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting（レスポンスデータの形成）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author May Thu
	 * @since  2016/09/01
	 */
	private void genkaKihonSetting(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;

		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			//テーブル名
			String strTblNm = "table";

			//データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);

			//共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			//追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "メインメニュー　注作業中の情報を取得処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}

			//変数の削除
			strSqlBuf = null;

		}

	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成）
	//
   //--------------------------------------------------------------------------------------------------------------
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSQL(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL文の作成
			strSql.append(" SELECT ");
			strSql.append(" M01.nm_user,count(*) AS ORDER_CNT  ");
			strSql.append(" FROM tr_shisan_shizai T01 ");
			strSql.append("inner join ma_user M01 on (");
			strSql.append("		   T01.cd_shain = M01.id_user");
			strSql.append("		)");
			strSql.append(" WHERE (T01.cd_hattyu IS NULL ");
			strSql.append(" OR ISNULL(T01.dt_hattyu,'') = '' )" );
			strSql.append(" AND ISNULL(T01.cd_shizai_new,'') <> '' " );
			strSql.append(" group by M01.nm_user" );
			strSql.append(" order by M01.nm_user" );
		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3490_Logic:createGenkaKihonSQL");
		} finally {

		}
		return strSql;
	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKihon(

			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstGenkaHeader.size(); i++) {
				Object[] items = (Object[]) lstGenkaHeader.get(i);
				//処理結果の
				resTable.addFieldVale(i, "nm_hattyu", toString(items[0],""));
				resTable.addFieldVale(i, "ORDER_CNT", toString(items[1],""));
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3490_Logic:storageGenkaKihon");
		} finally {

		}

	}

}
