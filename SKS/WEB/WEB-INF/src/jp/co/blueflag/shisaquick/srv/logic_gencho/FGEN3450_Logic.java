package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【QP@40404】
 *  資材手配入力：資材テーブル検索
 *  機能ID：FGEN3450
 *
 * @author E.Kitazawa
 * @since  2014/09/11
 */
public class FGEN3450_Logic extends LogicBase{

	/**
	 * 資材手配入力：資材テーブル検索
	 * : インスタンス生成
	 */
	public FGEN3450_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 資材手配入力：資材テーブル検索
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
	 * @author E.Kitazawa
	 * @since  2014/09/11
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
			// nullの時（メニューからのロード時）は検索処理を実行しない
			if (strSqlBuf != null) {
			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// 検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
			}
			}

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm), reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "資材テーブル検索処理が失敗しました。");

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
			// デザインスペースアップロード後の画面再ロード用
			String strMovement_condition = toString(userInfoData.getMovement_condition());
			String strCd_seihin_tmp = "";
			String strCd_sisakuNo_tmp = "";
			String strCd_shain_tmp = "";
			String strNen_tmp = "";
			String strNo_oi_tmp    = "";
			String strNo_eda_tmp    = "";

			if (strMovement_condition.length() > 6) {
				strCd_seihin_tmp    = toString(userInfoData.getMovement_condition().get(0));
				strCd_sisakuNo_tmp    = toString(userInfoData.getMovement_condition().get(1));
				strCd_shain_tmp    = toString(userInfoData.getMovement_condition().get(2));
				strNen_tmp = toString(userInfoData.getMovement_condition().get(3));
				strNo_oi_tmp = toString(userInfoData.getMovement_condition().get(4));
				strNo_eda_tmp    = toString(userInfoData.getMovement_condition().get(5));
			}

			// 試作No.
			String strCd_shain = reqData.getFieldVale("table", 0, "cd_shain");
			String strNen      = reqData.getFieldVale("table", 0, "nen");
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			String strNo_eda   = reqData.getFieldVale("table", 0, "no_eda");

			long lngCd_shain = 0;
			int intNen = 0;
			int intNo_oi = 0;
			int intNo_eda = 0;

			// 検索キーに空白は許可しない
			if (strCd_shain.equals("") || strNen.equals("") || strNo_oi.equals("") || strNo_eda.equals("")) {
				// アップロード後の画面再ロード時、前回の検索キーで再取得
				if (!strCd_sisakuNo_tmp.equals("")) {
					strCd_shain = strCd_shain_tmp;
					strNen      = strNen_tmp;
					strNo_oi    = strNo_oi_tmp;
					strNo_eda    = strNo_eda_tmp;
				} else {
					// メニューからのロード時は空白
					return null;
				}
//				em.ThrowException(ExceptionKind.一般Exception,"E000001","試作No","","");
			}

			// 数値 に変換
			lngCd_shain =  Long.parseLong(strCd_shain);
			intNen      =  Integer.parseInt(strNen);
			intNo_oi    =  Integer.parseInt(strNo_oi);
			intNo_eda   =  Integer.parseInt(strNo_eda);

			// SQL文の作成
			strSql.append(" SELECT T340.cd_shain  ");
			strSql.append("       ,T340.nen  ");
			strSql.append("       ,T340.no_oi  ");
			strSql.append("       ,T340.no_eda  ");
			strSql.append("       ,T340.seq_shizai  ");
			strSql.append("       ,T340.cd_kaisha  ");
			strSql.append("       ,T340.cd_busho  ");
			strSql.append("       ,T340.cd_shizai  ");
			strSql.append("       ,T340.nm_shizai  ");
			strSql.append("       ,T340.tanka  ");
			strSql.append("       ,T340.cd_shizai_new  ");
			strSql.append("       ,T340.nm_shizai_new  ");
			strSql.append("       ,T340.cd_seizokojo  ");
			strSql.append("       ,T340.cd_shokuba  ");
			strSql.append("       ,T340.cd_line  ");
			strSql.append("       ,T340.cd_seihin  ");
			strSql.append("       ,T340.cd_taisyoshizai  ");
			strSql.append("       ,T340.cd_hattyusaki  ");
			strSql.append("       ,T340.chk_kanryo  ");
			strSql.append("       ,M101.nm_user AS nm_koshin  ");
			strSql.append("       ,CONVERT(VARCHAR,T340.dt_koshin,111) AS dt_koshin  ");
//			strSql.append("       ,CONVERT(VARCHAR,T340.dt_koshin,120) AS dt_koshin  ");
			// 資材手配テーブルの更新日と手配ステータスを取得する
			strSql.append("       ,T401.dt_koshin AS dt_tehai_koshin  ");
			strSql.append("       ,T401.flg_tehai_status  ");
			//発注先のデータ取得 【KPX@1602367】May Thu 2016/09/20 add start
			strSql.append("       ,ISNULL(M102.nm_user,'') AS nm_hattyu  ");
			strSql.append("       ,CONVERT(VARCHAR,T340.dt_hattyu,111) AS dt_hattyu  ");
//			strSql.append("       ,CONVERT(VARCHAR,T340.dt_hattyu,120) AS dt_hattyu  ");
			//発注先のデータ取得 【KPX@1602367】May Thu 2016/09/20 add end
			//返下ファイル名とパス取得 【KPX@1602367】May Thu 2016/09/28 add start
			strSql.append("       ,T340.nm_file_henshita  ");
			strSql.append("       ,T340.file_path_henshita  ");
			//返下ファイル名とパス取得 【KPX@1602367】May Thu 2016/09/28 add end
			//発注先のデータ取得 【KPX@1602367】May Thu 2016/09/28 add end
			strSql.append(" FROM  tr_shisan_shizai T340 ");
			strSql.append(" LEFT JOIN  tr_shizai_tehai T401 ");
			strSql.append(" ON   ( T340.cd_shain   = T401.cd_shain ");
			strSql.append("   AND  T340.nen        = T401.nen");
			strSql.append("   AND  T340.no_oi      = T401.no_oi");
			strSql.append("   AND  T340.no_eda     = T401.no_eda ");
			strSql.append("   AND  T340.seq_shizai = T401.seq_shizai) ");
			strSql.append("   LEFT JOIN ma_user M101 ");
			strSql.append("   ON   M101.id_user = T340.id_koshin ");
			strSql.append("   LEFT JOIN ma_user M102 ");
			strSql.append("   ON   T340.cd_hattyu = M102.id_user ");
			strSql.append(" WHERE T340.cd_shain = "+ lngCd_shain );
			strSql.append("   AND T340.nen = "     + intNen );
			strSql.append("   AND T340.no_oi = "   + intNo_oi );
			strSql.append("   AND T340.no_eda = "  + intNo_eda );
			//発注先のデータ取得 【KPX@1602367】May Thu 2016/11/01 update start
			//strSql.append(" ORDER BY  T340.no_sort");
			strSql.append(" ORDER BY ");
			strSql.append("   T340.dt_hattyu, ");
			strSql.append("   case T401.flg_tehai_status when 2 then 1 else 2 end, ");
			strSql.append("   case T401.flg_tehai_status when 3 then 1 else 2 end, ");
			strSql.append("   case T401.flg_tehai_status when 1 then 1 else 2 end ");
			//発注先のデータ取得 【KPX@1602367】May Thu 2016/11/01 update end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param lstSizaiData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(

			  List<?> lstSizaiData
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//メニューからのロード時はlstMstDataが nullの為
			if ((lstSizaiData== null) || (lstSizaiData.size() == 0)) {
				// データが取得できない時：エラーのしない為（jsのメッセージを返したい）
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "sisakuNo", "");
			} else {
			    String strMovement_condition = toString(userInfoData.getMovement_condition());
 		        String sisakuNo   = reqData.getFieldVale("table", 0, "sisakuNo");
 		        if(sisakuNo == null || sisakuNo.equals("")) {
 		            // デザインスペースアップロード後の画面再ロード用
 		            sisakuNo    = toString(userInfoData.getMovement_condition().get(1));
 		        }
			for (int i = 0; i < lstSizaiData.size(); i++) {

				// 処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				resTable.addFieldVale(i, "sisakuNo", sisakuNo);

				Object[] items = (Object[]) lstSizaiData.get(i);

				// 結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
				resTable.addFieldVale(i, "nen", toString(items[1],""));
				resTable.addFieldVale(i, "no_oi", toString(items[2],""));
				resTable.addFieldVale(i, "no_eda", toString(items[3],""));
				resTable.addFieldVale(i, "seq_shizai", toString(items[4],""));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[5],""));
				resTable.addFieldVale(i, "cd_busho", toString(items[6],""));
				resTable.addFieldVale(i, "cd_shizai", toString(items[7],""));
				resTable.addFieldVale(i, "nm_shizai", toString(items[8],""));
				resTable.addFieldVale(i, "tanka", toString(items[9],""));
				resTable.addFieldVale(i, "cd_shizai_new", toString(items[10],""));
				resTable.addFieldVale(i, "nm_shizai_new", toString(items[11],""));
				resTable.addFieldVale(i, "cd_seizokojo", toString(items[12],""));
				resTable.addFieldVale(i, "cd_shokuba", toString(items[13],""));
				resTable.addFieldVale(i, "cd_line", toString(items[14],""));
				resTable.addFieldVale(i, "cd_seihin", toString(items[15],""));
				resTable.addFieldVale(i, "cd_taisyoshizai", toString(items[16],""));
				resTable.addFieldVale(i, "cd_hattyusaki", toString(items[17],""));
				resTable.addFieldVale(i, "chk_kanryo", toString(items[18],""));
				resTable.addFieldVale(i, "nm_koshin", toString(items[19],""));
				resTable.addFieldVale(i, "dt_koshin", toString(items[20],""));
				// 資材手配テーブルの更新日と手配ステータス
				resTable.addFieldVale(i, "dt_tehai_koshin", toString(items[21],""));
				resTable.addFieldVale(i, "flg_tehai_status", toString(items[22],""));
				//発注先のデータ取得 【KPX@1602367】May Thu 2016/09/20 add start
				resTable.addFieldVale(i, "nm_hattyu", toString(items[23],""));
				resTable.addFieldVale(i, "dt_hattyu", toString(items[24],""));
				resTable.addFieldVale(i, "nm_file_henshita", toString(items[25],""));
				resTable.addFieldVale(i, "file_path_henshita", toString(items[26],""));
				//発注先のデータ取得 【KPX@1602367】May Thu 2016/09/20 add end
			}
            }
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
