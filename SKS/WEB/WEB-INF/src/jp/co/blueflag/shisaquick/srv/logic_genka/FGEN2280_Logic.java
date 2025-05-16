package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.math.BigDecimal;
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
 *  原価試算：自家原料検索、自家原料単価設定
 *  機能FGEN2280
 *
 * @author TT.Kitazawa
 * @since 2016/06/17
 */
public class FGEN2280_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN2280_Logic() {
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
		// 試算配合の更新状況
		String[] chkKosin = null;

		try {
			//テーブル名
			String strTblNm = "table";

			//データ取得SQL作成
			//  自家原料に連携登録されている原料の連携先の情報を取得する
			strSqlBuf = this.createSQL(reqData);

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			if(lstRecset.size() > 0) {
				//自家原料の営業ステータス≧3(完了)の時、原料単価を設定する
				chkKosin = this.updateGenryoTanka(reqData, lstRecset);
			}

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm), chkKosin);

		} catch (Exception e) {
			this.em.ThrowException(e, "自家原料連携データ検索処理に失敗しました。");

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
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {

			//自家原料に連携登録されている原料の連携先の情報を取得する
			//試作CD-社員CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//試作CD-年
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//試作CD-追番
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//試作CD-枝番
			String strNo_eda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL文の作成
			strSql.append(" SELECT  ");
			strSql.append("       T120.cd_kotei  ");		//0:行程CD
			strSql.append("       ,T120.seq_kotei  ");		//1:行程SEQ
			strSql.append("       ,T120.cd_genryo  ");
			strSql.append("       ,T320.nm_genryo  ");
			strSql.append("       ,T320.tanka_ma  ");		//4:マスタ単価（置換前）
			strSql.append("       ,T320.budomar_ma  ");		//5:マスタ歩留（置換前）
			strSql.append("       ,T121.cd_shain  ");		//6:配合リンク（連携先）
			strSql.append("       ,T121.nen  ");
			strSql.append("       ,T121.no_oi  ");
			strSql.append("       ,T121.seq_shisaku  ");
			strSql.append("       ,T121.no_eda  ");
			strSql.append("       ,T332.kg_genka  ");		//11:原価計（円）/Kg
			strSql.append("       ,T441.st_eigyo  ");		//12:営業ステータス
			strSql.append("       ,T320.tanka_ins  ");		//13:手入力単価（置換前）
			strSql.append("       ,T320.budomari_ins  ");	//14:手入力歩留（置換前）
			strSql.append(" FROM  tr_haigo T120 ");
			strSql.append(" INNER JOIN   tr_haigo_link T121  ");
			strSql.append("   ON  T120.cd_genryo = T121.cd_genryo  ");
			strSql.append(" LEFT JOIN   tr_shisan_haigo T320  ");
			strSql.append("   ON   T320.cd_shain   = T120.cd_shain");
			strSql.append("   AND  T320.nen     = T120.nen" );
			strSql.append("   AND  T320.no_oi   = T120.no_oi" );
			strSql.append("   AND  T320.no_eda  = " + strNo_eda );
			strSql.append("   AND  T320.cd_kotei = T120.cd_kotei" );
			strSql.append("   AND  T320.seq_kotei = T120.seq_kotei" );
			strSql.append(" LEFT JOIN   tr_shisan_shisaku_kotei T332  ");
			strSql.append("   ON   T121.cd_shain   = T332.cd_shain");
			strSql.append("   AND  T121.nen     = T332.nen" );
			strSql.append("   AND  T121.no_oi   = T332.no_oi" );
			strSql.append("   AND  T121.no_eda  = T332.no_eda" );
			strSql.append("   AND  T121.seq_shisaku = T332.seq_shisaku" );
			strSql.append(" LEFT JOIN   tr_shisan_status T441  ");
			strSql.append("   ON   T121.cd_shain   = T441.cd_shain");
			strSql.append("   AND  T121.nen     = T441.nen" );
			strSql.append("   AND  T121.nen     = T441.nen" );
			strSql.append("   AND  T121.no_oi   = T441.no_oi" );
			strSql.append("   AND  T121.no_eda  = T441.no_eda" );
			strSql.append(" WHERE  T120.cd_shain = " + strShainCd );
			strSql.append("   AND  T120.nen = " + strNen );
			strSql.append("   AND  T120.no_oi = " + strNo_oi );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param reqData：リクエストデータ
	 * @param lstRenkei : 検索結果情報リスト
	 * @return String[] : 更新状況
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String[] updateGenryoTanka(
			RequestResponsKindBean reqData
			,List<?> lstRenkei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String[] retKosin = null;

		try {
			//試作CD-社員CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//試作CD-年
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//試作CD-追番
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//試作CD-枝番
			String strNo_eda    = reqData.getFieldVale("table", 0, "no_eda");

			// トランザクション開始
			super.createExecDB();

			execDB.BeginTran();

			// SQL格納用
			StringBuffer strSql = null;
			// 更新状況
			retKosin = new String[lstRenkei.size()];

			for (int i = 0; i < lstRenkei.size(); i++) {
				//データ取得
				Object[] items = (Object[]) lstRenkei.get(i);

				//営業ステータス
				String strStEigyo = toString(items[12],"");
				// マスタ単価設定値
				String strTanka_ma = "null";
				// 手入力単価設定値
				String strTanka_ins = "null";

				//変更チェック
				retKosin[i] = "";

				//営業ステータス：完了以上の時、原価計（円）/Kg をセット
				if (strStEigyo.equals("3") ||  strStEigyo.equals("4") ) {
					// マスタ単価（置換前）、原価計（円）/Kg を比較
					strTanka_ma = toString(items[11],"null");
					if (toDecimal(items[4]).compareTo(toDecimal(items[11])) != 0) {
						retKosin[i] = "1";
					}
					// 手入力単価：置き換え前手入力単価
					strTanka_ins = toString(items[13],"null");

				} else {
					// 営業ステータス：＜完了 の時、手入力単価
					strTanka_ins = toString(items[13],"null");

					//  マスタ単価に0をセット
					strTanka_ma = "0";
					// マスタ単価（置換前）と比較
					if (new BigDecimal("0").compareTo(toDecimal(items[4])) != 0) {
						retKosin[i] = "1";
					}
				}

				//変更時のみ更新処理を実行
				if (retKosin[i].equals("1")) {
					strSql = new StringBuffer();

					//単価・歩留を更新
					strSql.append(" UPDATE tr_shisan_haigo ");
					strSql.append(" SET tanka_ma = " + strTanka_ma); 			// マスタ単価
					strSql.append("   , tanka_ins = " + strTanka_ins);			// 手入力単価
					strSql.append("   , id_koshin = " + userInfoData.getId_user());
					strSql.append("   , dt_koshin = GETDATE()");
					strSql.append(" WHERE  cd_shain = " + strShainCd );
					strSql.append("   AND  nen = " + strNen );
					strSql.append("   AND  no_oi = " + strNo_oi );
					strSql.append("   AND  cd_kotei =  " + toString(items[0],""));		// 行程CD
					strSql.append("   AND  seq_kotei = " + toString(items[1],""));		// 行程SEQ
					strSql.append("   AND  no_eda = " + strNo_eda );

					execDB.execSQL(strSql.toString());
				}
			}

			// コミット
			execDB.Commit();

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}
			this.em.ThrowException(e, "");

		} finally {

		}

		return retKosin;
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
			, String[] chkKosin
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i=0; i<lstRenkei.size() ; i++) {
				//データ取得：1件のみ
				Object[] items = (Object[]) lstRenkei.get(i);

				//結果をレスポンスデータに格納
				// 行程CD
				resTable.addFieldVale(i, "cd_kotei", toString(items[0],""));
				// 行程SEQ
				resTable.addFieldVale(i, "seq_kotei", toString(items[1],""));
				// 原料CD
				resTable.addFieldVale(i, "cd_genryo", toString(items[2],""));
				// 原料名
				resTable.addFieldVale(i, "nm_genryo", toString(items[3],""));
				// 試算配合：マスタ単価
				resTable.addFieldVale(i, "tanka_ma", toString(items[4],""));
				// 試算配合：マスタ歩留
				resTable.addFieldVale(i, "budomar_ma", toString(items[5],""));
				// 連携先：試作CD-社員CD
				resTable.addFieldVale(i, "cd_shain", toString(items[6],""));
				// 連携先：試作CD-年
				resTable.addFieldVale(i, "nen", toString(items[7],""));
				// 連携先：試作CD-追番
				resTable.addFieldVale(i, "no_oi", toString(items[8],""));
				// 連携先：試作SEQ
				resTable.addFieldVale(i, "seq_shisaku", toString(items[9],""));
				// 連携先：試作CD-枝番
				resTable.addFieldVale(i, "no_eda", toString(items[10],""));
				// 連携先：単価（原価計（円）/Kg）
				resTable.addFieldVale(i, "kg_genka", toString(items[11],""));
				// 連携先：営業ステータス
				resTable.addFieldVale(i, "st_eigyo", toString(items[12],""));
				// 試算配合更新状況
				resTable.addFieldVale(i, "chkKosin", chkKosin[i]);

				// 処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
			}

			if (lstRenkei.size() == 0) {
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
