package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * 担当者マスタメンテ：担当者情報検索DB処理
 *  : 担当者マスタメンテ：担当者情報を検索する。
 * @author jinbo
 * @since  2009/04/07
 */
public class TantoushaDataSearch2Logic extends LogicBase{
	
	// ADD 2013/11/20 QP@30154 okano start
	String EditMode = "";
	// ADD 2013/11/20 QP@30154 okano end
	/**
	 * 担当者マスタメンテナンス：担当者情報検索DB処理 
	 * : インスタンス生成
	 */
	public TantoushaDataSearch2Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 担当者マスタメンテ：担当者情報取得SQL作成
	 *  : 担当者情報を取得するSQLを作成。
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
			strSql = genryoData2CreateSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				//em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageTantoushaData(lstRecset, resKind.getTableItem(strTableNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");
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
	 * 担当者情報取得SQL作成
	 *  : 担当者情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// ADD 2013/9/25 okano【QP@30151】No.28 start
		List<?> lstRecset = null;
		// ADD 2013/9/25 okano【QP@30151】No.28 end
		
		try {
			String strUserId = null;
			// ADD 2013/11/20 QP@30154 okano start
			String kinoId = null;
			// ADD 2013/11/20 QP@30154 okano end
			String dataId = null;
			
			//ユーザIDの取得
			strUserId = reqData.getFieldVale(0, 0, "id_user");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//担当者マスタメンテナンス画面のデータIDを設定
					// ADD 2013/11/20 QP@30154 okano start
					kinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/11/20 QP@30154 okano end
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			// MOD 2013/9/25 okano【QP@30151】No.28 start
			//SQL文の作成	
//			strSql.append("SELECT");
//			strSql.append("  nm_user");
//			strSql.append(" ,password");
//			strSql.append(" ,cd_kengen");
//			strSql.append(" ,cd_kaisha");
//			strSql.append(" ,cd_busho");
//			strSql.append(" ,cd_group");
//			strSql.append(" ,cd_team");
//			strSql.append(" ,cd_yakushoku");
//			strSql.append(" FROM ma_user");
//			strSql.append(" WHERE id_user = ");
//			strSql.append(strUserId);
			
			strSql.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSql.append(strUserId);
			
			createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			strSql = new StringBuffer();
			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSql.append("SELECT");
				strSql.append("  nm_user");
				strSql.append(" ,password");
				strSql.append(" ,cd_kengen");
				strSql.append(" ,cd_kaisha");
				strSql.append(" ,cd_busho");
				strSql.append(" ,cd_group");
				strSql.append(" ,cd_team");
				strSql.append(" ,null");
				strSql.append(" FROM ma_user_new");
				strSql.append(" WHERE id_user = ");
				strSql.append(strUserId);
				// ADD 2013/11/20 QP@30154 okano start
				EditMode = "Cash";
				// ADD 2013/11/20 QP@30154 okano end
			} else {
				strSql.append("SELECT");
				strSql.append("  nm_user");
				strSql.append(" ,password");
				strSql.append(" ,cd_kengen");
				strSql.append(" ,cd_kaisha");
				strSql.append(" ,cd_busho");
				strSql.append(" ,cd_group");
				strSql.append(" ,cd_team");
				strSql.append(" ,cd_yakushoku");
				strSql.append(" FROM ma_user");
				strSql.append(" WHERE id_user = ");
				strSql.append(strUserId);
			}
			// MOD 2013/9/25 okano【QP@30151】No.28 end
			//検索条件権限設定
			//自分のみ
			if(dataId.equals("1")) {
				strSql.append(" AND id_user = ");
				strSql.append(userInfoData.getId_user());

			// ADD 2013/11/7 QP@30154 okano start
			//所属会社のみ
			} else if (dataId.equals("2")) { 
				strSql.append(" AND cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());

			// ADD 2013/11/7 QP@30154 okano end
			//全て
			} else if (dataId.equals("9")) { 
				//なし
				// ADD 2013/11/20 QP@30154 okano start
				if(kinoId.equals("20") && !EditMode.equals("Cash")){
					EditMode = "System";
				}
				// ADD 2013/11/20 QP@30154 okano end
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");
		} finally {
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			// ADD 2013/9/25 okano【QP@30151】No.28 end

		}
		return strSql;
	}

	/**
	 * 担当者マスタメンテ：担当者情報パラメーター格納
	 *  : 担当者情報をレスポンスデータへ格納する。
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantoushaData(List<?> lstTantouShaData, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			// ADD 2013/11/20 QP@30154 okano start
			if(EditMode.equals("Cash")){
				resTable.addFieldVale(0, "md_edit", "cash");
			} else if(EditMode.equals("System")) {
				resTable.addFieldVale(0, "md_edit", "system");
			} else {
				resTable.addFieldVale(0, "md_edit", "ippan");
			}
			// ADD 2013/11/20 QP@30154 okano end
			
			if (lstTantouShaData.size() == 0){
				//対象データが無い場合
				
				//空レコードを生成
				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "nm_user", "");
				resTable.addFieldVale(0, "password", "");
				resTable.addFieldVale(0, "cd_kengen", "");
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "cd_team", "");
				resTable.addFieldVale(0, "cd_yakushoku", "");

			}else{
				//対象データが有る場合

				Object[] items = (Object[]) lstTantouShaData.get(0);
				
				//結果をレスポンスデータに格納
				// MOD 2013/9/25 okano【QP@30151】No.28 start
//				resTable.addFieldVale(0, "nm_user", items[0].toString());
//				resTable.addFieldVale(0, "password", items[1].toString());
//				resTable.addFieldVale(0, "cd_kengen", items[2].toString());
//				resTable.addFieldVale(0, "cd_kaisha", items[3].toString());
//				resTable.addFieldVale(0, "cd_busho", items[4].toString());
				
				resTable.addFieldVale(0, "nm_user", toString(items[0]));
				resTable.addFieldVale(0, "password", toString(items[1]));
				resTable.addFieldVale(0, "cd_kengen", toString(items[2]));
				resTable.addFieldVale(0, "cd_kaisha", toString(items[3]));
				resTable.addFieldVale(0, "cd_busho", toString(items[4]));
				// MOD 2013/9/25 okano【QP@30151】No.28 start
				
				//【QP@00342】 グループ、チーム、役職がNULLの場合があるので修正
				//resTable.addFieldVale(0, "cd_group", items[5].toString());
				//resTable.addFieldVale(0, "cd_team", items[6].toString());
				//resTable.addFieldVale(0, "cd_yakushoku", items[7].toString());
				resTable.addFieldVale(0, "cd_group", toString(items[5]));
				resTable.addFieldVale(0, "cd_team", toString(items[6]));
				resTable.addFieldVale(0, "cd_yakushoku", toString(items[7]));
				//ここまで
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}
