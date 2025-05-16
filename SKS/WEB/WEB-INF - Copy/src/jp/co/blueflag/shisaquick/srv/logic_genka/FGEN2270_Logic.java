package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【KPX@1502111_No.5】
 *  分析値入力：自家原料連携情報検索
 *  機能FGEN2270
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2270_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN2270_Logic() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}
	/**
	 * 分析値入力：自家原料連携情報検索
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
		List<?> lstRec_nmHin = null;
		List<?> lstRec_sample = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			//テーブル名
			String strTblNm = "table";

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();

			// 試作名取得SQL作成
			strSqlBuf = this.createSQL_nmHin(reqData);

			lstRec_nmHin = searchDB.dbSearch(strSqlBuf.toString());

			// サンプルＮｏ取得SQL作成
			strSqlBuf = this.createSQL_sampleNo(reqData);

			lstRec_sample = searchDB.dbSearch(strSqlBuf.toString());

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRec_nmHin, lstRec_sample, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "連携情報検索処理が失敗しました。");

		} finally {
			// リストの破棄
			removeList(lstRec_nmHin);
			removeList(lstRec_sample);

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
	 * 試作名取得SQL作成
	 * @param reqData：リクエストデータ
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL_nmHin(
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
			//枝番号
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL文の作成
			strSql.append(" SELECT CASE WHEN T310.nm_edaShisaku IS NOT NULL  ");
			strSql.append("        THEN  ");
			strSql.append("          CASE RTRIM(T310.nm_edaShisaku) WHEN ''  ");
			strSql.append("          THEN T110.nm_hin   ");
			strSql.append("          ELSE T110.nm_hin + ' 【' + T310.nm_edaShisaku + '】' END   ");
			strSql.append("        ELSE T110.nm_hin END AS nm_hin  ");
			strSql.append("    ,T310.cd_shain  ");
			strSql.append("    ,T310.nen  ");
			strSql.append("    ,T310.no_oi  ");
			strSql.append("    ,T310.no_eda  ");
			strSql.append("    ,st_eigyo  ");
			strSql.append("    ,st_kenkyu  ");
			strSql.append("    ,st_seisan  ");
			strSql.append("    ,st_gensizai  ");
			strSql.append("    ,st_kojo  ");
			strSql.append(" FROM  tr_shisan_shisakuhin  AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110  ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain   ");
			strSql.append("   AND T310.nen = T110.nen   ");
			strSql.append("   AND T310.no_oi = T110.no_oi   ");
			strSql.append(" LEFT JOIN tr_shisan_status AS T441  ");
			strSql.append(" ON T310.cd_shain = T441.cd_shain   ");
			strSql.append("   AND T310.nen = T441.nen   ");
			strSql.append("   AND T310.no_oi = T441.no_oi   ");
			strSql.append("   AND T310.no_eda = T441.no_eda   ");
			strSql.append(" WHERE  T310.cd_shain = " + strShainCd );
			strSql.append("   AND  T310.nen = " + strNen );
			strSql.append("   AND  T310.no_oi = " + strNo_oi );
			strSql.append("   AND  T310.no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}


	/**
	 * サンプルＮｏ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL_sampleNo(
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
			//枝番号
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL文の作成
			strSql.append(" SELECT T331.cd_shain  ");
			strSql.append("       ,T331.nen  ");
			strSql.append("       ,T331.no_oi  ");
			strSql.append("       ,T331.no_eda  ");
			strSql.append("       ,T331.seq_shisaku  ");
			strSql.append("       ,T331.fg_chusi   ");
			strSql.append("       ,T131.nm_sample  ");
			strSql.append(" FROM  tr_shisaku  AS T131  ");
			strSql.append(" LEFT JOIN tr_shisan_shisaku AS T331  ");
			strSql.append(" ON T331.cd_shain = T131.cd_shain  ");
			strSql.append("   AND T331.nen = T131.nen  ");
			strSql.append("   AND T331.no_oi = T131.no_oi  ");
			strSql.append("   AND T331.seq_shisaku = T131.seq_shisaku  ");
			strSql.append(" WHERE  T331.cd_shain = " + strShainCd );
			strSql.append("   AND  T331.nen = " + strNen );
			strSql.append("   AND  T331.no_oi = " + strNo_oi );
			strSql.append("   AND  T331.no_eda = " + strNoEda );
			strSql.append("   AND  T131.flg_shisanIrai = 1 "  );
			strSql.append(" ORDER BY "  );
			strSql.append("   T131.sort_shisaku "  );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstNmHin : 検索結果情報リスト（試作名）
	 * @param lstSampleNo : 検索結果情報リスト（サンプルＮｏ）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstNmHin
			, List<?> lstSampleNo
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strHinmei  = "";
		String strStEigyo  = "";

		try {

			//試作名取得結果
			if (lstNmHin.size() > 0) {
				Object[] items = (Object[]) lstNmHin.get(0);
				//試作名
				strHinmei = toString(items[0],"");
				//営業ステータス
				strStEigyo = toString(items[5],"");
			}

			//サンプルＮｏ取得結果
			if (lstSampleNo.size() > 0) {
				for (int i = 0; i < lstSampleNo.size(); i++) {

					// 処理結果の格納
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstSampleNo.get(i);

					// 結果をレスポンスデータに格納
					resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
					resTable.addFieldVale(i, "nen", toString(items[1],""));
					resTable.addFieldVale(i, "no_oi", toString(items[2],""));
					resTable.addFieldVale(i, "no_eda", toString(items[3],""));
					resTable.addFieldVale(i, "seq_shisaku", toString(items[4],""));
					resTable.addFieldVale(i, "fg_chusi", toString(items[5],""));
					resTable.addFieldVale(i, "nm_sample", toString(items[6],""));
					resTable.addFieldVale(i, "nm_hin", strHinmei);
					resTable.addFieldVale(i, "st_eigyo", strStEigyo);
				}

			} else {
				// サンプルＮｏが取得できない時
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "nm_hin", strHinmei);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
