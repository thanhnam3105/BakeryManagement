package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 【KPX@1502111_No.5】
 *  分析値入力：配合リンクＤＢ検索
 *  機能FGEN2260
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2260_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN2260_Logic() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}
	/**
	 * 分析値入力：配合リンクＤＢ検索
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

		//レスポンスデータ（機能）
		RequestResponsKindBean resKind = null;

		try {

			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// レスポンスデータの形成
			this.setData(resKind, reqData);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				// セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
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
			//テーブル名
			String strTblNm = "table";

            //検索モード
			String strMode  = reqData.getFieldVale("table", 0, "syoriMode");

			if (strMode.equals("0")) {
				// データ取得SQL作成（原料コードより検索）
				strSqlBuf = this.createSQLgenryo(reqData);
			} else {
				// データ取得SQL作成（試作Ｎｏより検索）
				strSqlBuf = this.createSQLshisaku(reqData);
			}

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "配合リンク検索処理が失敗しました。");

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
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQLgenryo(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {

            //会社コード
			String strKaishaCd   = reqData.getFieldVale("table", 0, "cd_kaisha");
			//原料コード
			String strGenryoCd   = reqData.getFieldVale("table", 0, "cd_genryo");
			//部署コード
			String strBushoCd    = reqData.getFieldVale("table", 0, "cd_busho");
			if (strBushoCd.equals("")) {
				strBushoCd = ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD");
			}

			// SQL文の作成
			strSql.append(" SELECT cd_kaisha  ");
			strSql.append("       ,cd_genryo  ");
			strSql.append("       ,cd_busho  ");
			strSql.append("       ,cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
			strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
			strSql.append("   AND  cd_busho = " + strBushoCd );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQLshisaku(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {

			//試作CD-社員CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//試作CD-年
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//試作CD-追番
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//試作CD-枝番
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL文の作成
			strSql.append(" SELECT cd_kaisha  ");
			strSql.append("       ,cd_genryo  ");
			strSql.append("       ,cd_busho  ");
			strSql.append("       ,cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_shain = " + strShainCd );
			strSql.append("   AND  nen = " + strNen );
			strSql.append("   AND  no_oi = " + strNo_oi );
			strSql.append("   AND  no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstRenkei : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstRenkei
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			if (lstRenkei.size() > 0) {
				//データ取得：1件のみ
				Object[] items = (Object[]) lstRenkei.get(0);

				// 結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_kaisha", toString(items[0],""));
				resTable.addFieldVale(0, "cd_genryo", toString(items[1],""));
				resTable.addFieldVale(0, "cd_busho", toString(items[2],""));
				resTable.addFieldVale(0, "cd_shain", toString(items[3],""));
				resTable.addFieldVale(0, "nen", toString(items[4],""));
				resTable.addFieldVale(0, "no_oi", toString(items[5],""));
				resTable.addFieldVale(0, "no_eda", toString(items[6],""));
				resTable.addFieldVale(0, "seq_shisaku", toString(items[7],""));

				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

			} else {
				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
