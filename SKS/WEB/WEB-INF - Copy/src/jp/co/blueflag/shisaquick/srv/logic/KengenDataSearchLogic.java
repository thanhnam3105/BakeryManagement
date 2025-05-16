package jp.co.blueflag.shisaquick.srv.logic;

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
 * 
 * 権限マスタメンテ：権限情報検索DB処理
 *  : 権限マスタメンテ：権限情報を検索する。
 * @author jinbo
 * @since  2009/04/09
 */
public class KengenDataSearchLogic extends LogicBase{
	
	/**
	 * 権限マスタメンテ：権限報検索DB処理 
	 * : インスタンス生成
	 */
	public KengenDataSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 権限マスタメンテ：権限情報取得SQL作成
	 *  : 権限情報を取得するSQLを作成。
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
			strSql = genryoDataCreateSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageGenryouData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "権限データ検索処理に失敗しました。");
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
	 * 権限情報取得SQL作成
	 *  : 権限情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String strKengenCd = null;
			String dataId = null;
			
			//権限コードの取得
			strKengenCd = reqData.getFieldVale(0, 0, "cd_kengen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("80")){
					//権限マスタメンテナンス画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  M102.cd_kengen");
			strSql.append(" ,M102.nm_kengen");
			strSql.append(" ,ISNULL(M103.nm_kino,'') as nm_kino");
			strSql.append(" ,ISNULL(M103.id_gamen,'') as id_gamen");
			strSql.append(" ,ISNULL(M103.id_kino,'') as id_kino");
			strSql.append(" ,ISNULL(M103.id_data,'') as id_data");
			strSql.append(" ,ISNULL(M103.biko,'') as biko");
			strSql.append(" FROM ma_kengen M102");
			strSql.append("      LEFT JOIN ma_kinou M103");
			strSql.append("      ON M102.cd_kengen = M103.cd_kengen");
			strSql.append(" WHERE M102.cd_kengen = ");
			strSql.append(strKengenCd);
			strSql.append(" ORDER BY ");
			strSql.append(" M103.id_gamen ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "権限データ検索処理に失敗しました。");
		} finally {

		}
		return strSql;
	}

	/**
	 * 権限マスタメンテ：権限情報パラメーター格納
	 *  : 権限情報をレスポンスデータへ格納する。
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryouData(List<?> lstGenryouData, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenryouData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryouData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_kengen", items[0].toString());
				resTable.addFieldVale(i, "nm_kengen", items[1].toString());
				resTable.addFieldVale(i, "nm_kino", items[2].toString());
				resTable.addFieldVale(i, "id_gamen", items[3].toString());
				resTable.addFieldVale(i, "id_kino", items[4].toString());
				resTable.addFieldVale(i, "id_data", items[5].toString());
				resTable.addFieldVale(i, "biko", items[6].toString());
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "権限データ検索処理に失敗しました。");

		} finally {

		}

	}
	
}
