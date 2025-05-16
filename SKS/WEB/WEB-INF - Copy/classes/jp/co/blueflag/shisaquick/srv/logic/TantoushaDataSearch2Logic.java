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
		
		try {
			String strUserId = null;
			String dataId = null;
			
			//ユーザIDの取得
			strUserId = reqData.getFieldVale(0, 0, "id_user");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//担当者マスタメンテナンス画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成	
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
			
			//検索条件権限設定
			//自分のみ
			if(dataId.equals("1")) {
				strSql.append(" AND id_user = ");
				strSql.append(userInfoData.getId_user());

			//全て
			} else if (dataId.equals("9")) { 
				//なし
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");
		} finally {

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
				resTable.addFieldVale(0, "nm_user", items[0].toString());
				resTable.addFieldVale(0, "password", items[1].toString());
				resTable.addFieldVale(0, "cd_kengen", items[2].toString());
				resTable.addFieldVale(0, "cd_kaisha", items[3].toString());
				resTable.addFieldVale(0, "cd_busho", items[4].toString());
				resTable.addFieldVale(0, "cd_group", items[5].toString());
				resTable.addFieldVale(0, "cd_team", items[6].toString());
				resTable.addFieldVale(0, "cd_yakushoku", items[7].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}
