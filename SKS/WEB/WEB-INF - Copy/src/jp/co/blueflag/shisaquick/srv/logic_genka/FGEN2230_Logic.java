package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *	原価試算一覧権限チェック処理
 *  機能ID：FGEN2230
 *
 *	@author okano
 *	@since  2013/10/22
 */
public class FGEN2230_Logic extends LogicBase{

	/**
	 * 権限確認ロジック用コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN2230_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 権限確認SQL作成
	 * : 権限不正チェックを行う為のSQLを作成。
	 * @param reqData：リクエストデータ
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

		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//テーブルBean取得
			reqTableBean = reqData.getTableItem(0);
			//行Bean取得
			reqRecBean = reqTableBean.getRowItem(0);

//			String strId_Gamen   = "";
			String strId_Kino   = "";
			String strId_Data   = "";
			String strId_User   = "";
			String strCd_Kaisha   = "";
			String strCd_Busho   = "";
			String strShain   = "";
			String strNen   = "";
			String strNo_oi   = "";
			String strNo_eda   = "";;
			String strKbnShori   = "";

			//リクエスト情報取得
			strId_Data = userInfoData.getID_data("180");
			strId_Kino = userInfoData.getId_kino("180");
			strId_User = userInfoData.getId_user();
			strCd_Kaisha = userInfoData.getCd_kaisha();
			strCd_Busho = userInfoData.getCd_busho();
			strShain = reqRecBean.GetItemValue("cd_shain");
			strNen = reqRecBean.GetItemValue("nen");
			strNo_oi = reqRecBean.GetItemValue("no_oi");
			strNo_eda = reqRecBean.GetItemValue("no_eda");
			strKbnShori = reqRecBean.GetItemValue("mode");

			//所属部署設定
			strSQL = new StringBuffer();
			strSQL.append(" SELECT ");
			strSQL.append(" CASE ");
			strSQL.append(" WHEN flg_kenkyu = 1 ");
			strSQL.append("  THEN 1 ");
			strSQL.append(" WHEN flg_seisan = 1 ");
			strSQL.append("  THEN 2 ");
			strSQL.append(" WHEN flg_genshizai = 1 ");
			strSQL.append("  THEN 3 ");
			strSQL.append(" WHEN flg_kojo = 1 ");
			strSQL.append("  THEN 4 ");
			strSQL.append(" WHEN flg_eigyo = 1 ");
			strSQL.append("  THEN 5 ");
			strSQL.append("  ELSE NULL ");
			strSQL.append(" END ");
			strSQL.append(" FROM ma_busho ");
			strSQL.append(" WHERE cd_kaisha = ");
			strSQL.append(strCd_Kaisha);
			strSQL.append(" AND cd_busho = ");
			strSQL.append(strCd_Busho);

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			if(strId_Data.equals("7")){
			} else {
				if (toString(lstRecset.get(0)).equals("1")){
					if(strKbnShori.equals("1")){
// 20160513  KPX@1600766 DEL start
// 研究所：原価試算画面表示後、単価表示制御
//						strSQL = new StringBuffer();
//						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha = ");
//						strSQL.append(strCd_Kaisha);
//						strSQL.append(" AND cd_shain = ");
//						strSQL.append(strShain);
//						strSQL.append(" AND nen = ");
//						strSQL.append(strNen);
//						strSQL.append(" AND no_oi = ");
//						strSQL.append(strNo_oi);
//						strSQL.append(" AND no_eda = ");
//						strSQL.append(strNo_eda);

//						createSearchDB();
//						lstRecset = searchDB.dbSearch(strSQL.toString());

//						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
//							em.ThrowException(ExceptionKind.一般Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
//						}

// 20160513  KPX@1600766 DEL end
						} else if(strKbnShori.equals("2")){

						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha <> ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_hanseki = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.一般Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
					}
				} else if (toString(lstRecset.get(0)).equals("2")){
					if(strKbnShori.equals("1")){

						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_kaisha = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.一般Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
					} else if(strKbnShori.equals("2")){

						// ADD 2013/12/5 okano【QP@30154】start
						strSQL = new StringBuffer();
						strSQL.append(" SELECT COUNT(*) FROM tr_shisan_shisakuhin WHERE cd_hanseki = ");
						strSQL.append(strCd_Kaisha);
						strSQL.append(" AND cd_shain = ");
						strSQL.append(strShain);
						strSQL.append(" AND nen = ");
						strSQL.append(strNen);
						strSQL.append(" AND no_oi = ");
						strSQL.append(strNo_oi);
						strSQL.append(" AND no_eda = ");
						strSQL.append(strNo_eda);

						createSearchDB();
						lstRecset = searchDB.dbSearch(strSQL.toString());

						if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
							em.ThrowException(ExceptionKind.一般Exception, "E000335", reqRecBean.GetItemValue("no_shisaku"), "", "");
						}
						// ADD 2013/12/5 okano【QP@30154】end
					}
				}
			}

			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			// ADD 2015/03/03 E.kitazawa【QP@40812】 javaエラー対応 --- start
			//処理結果を格納
			this.storageData(resKind.getTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID()));
			// ADD 2015/03/03 E.kitazawa【QP@40812】 javaエラー対応 --- end

		} catch (Exception e) {

			em.ThrowException(e,"");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSQL = null;
			//クラスの破棄
			reqTableBean = null;
			reqRecBean = null;
		}

		return resKind;
	}

	// ADD 2015/03/03 E.kitazawa【QP@40812】 javaエラー対応 --- start
	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param resTable :
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			// 処理結果の格納
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
	// ADD 2015/03/03 E.kitazawa【QP@40812】 javaエラー対応 --- end
}
