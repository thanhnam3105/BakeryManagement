package jp.co.blueflag.shisaquick.srv.logic;

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
 * 
 * ユーザー認証SQL作成
 *  : ユーザー認証情報を検索する。
 * @author furuta
 * @since  2009/03/29
 */
public class UserNinshoLogic extends LogicBase{
	
	/**
	 * ユーザー認証ロジック用コンストラクタ 
	 * : インスタンス生成
	 */
	public UserNinshoLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * ユーザー認証SQL作成 
	 * : ユーザー認証を行う為のSQLを作成。
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
			
			String strId_User   = "";
			String strKbn_Login = "";
			String strPassword  = "";
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			boolean flg_Eigyo = false;
			boolean flg_Sign = false;
			// ADD 2013/9/25 okano【QP@30151】No.28 end
			
			//行データよりリクエスト情報取得
			//ユーザーID設定
			strId_User = reqRecBean.GetItemValue("id_user");
			//ログイン区分設定
			strKbn_Login = reqRecBean.GetItemValue("kbn_login");
			//パスワード設定
			strPassword = reqRecBean.GetItemValue("password");
					
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			
			strSQL = new StringBuffer();
			strSQL.append("SELECT COUNT(*) FROM ma_user ");
			strSQL.append(" LEFT JOIN ma_busho ");
			strSQL.append(" ON ma_busho.cd_busho = ma_user.cd_busho ");
			strSQL.append(" AND ma_busho.cd_kaisha = ma_user.cd_kaisha ");
			strSQL.append(" WHERE id_user = ");
			strSQL.append(strId_User);
			strSQL.append(" AND flg_eigyo = 1 ");
			
			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSQL = new StringBuffer();
				strSQL.append("SELECT COUNT(*) FROM ma_user_new ");
				strSQL.append(" LEFT JOIN ma_busho ");
				strSQL.append(" ON ma_busho.cd_busho = ma_user_new.cd_busho ");
				strSQL.append(" AND ma_busho.cd_kaisha = ma_user_new.cd_kaisha ");
				strSQL.append(" WHERE id_user = ");
				strSQL.append(strId_User);
				strSQL.append(" AND flg_eigyo = 1 ");
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
				
				if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
					flg_Eigyo = false;
				} else {
					flg_Eigyo = true;
				}
			} else {
				flg_Eigyo = true;
			}
			strSQL = new StringBuffer();
			// ADD 2013/9/25 okano【QP@30151】No.28 end
			
			// MOD 2013/9/25 okano【QP@30151】No.28 start
//				//シングルサインオン用SQL作成
//				if (strKbn_Login.equals("1")){
//					
//					strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
//					strSQL.append(strId_User);
//					
//				//ログイン用SQL作成
//				} else if (strKbn_Login.equals("2")){
//					
//					strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
//					strSQL.append(strId_User);
//					strSQL.append(" AND password = '");
//					strSQL.append(strPassword + "' COLLATE Japanese_CS_AS");
//				}
//				
//				createSearchDB();
//				lstRecset = searchDB.dbSearch(strSQL.toString());
			//シングルサインオン用SQL作成
			if (strKbn_Login.equals("1")){
				
				strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
				
				if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
					
					strSQL = new StringBuffer();
					strSQL.append("SELECT COUNT(*) FROM ma_user_new WHERE id_user = ");
					strSQL.append(strId_User);
					
					createSearchDB();
					lstRecset = searchDB.dbSearch(strSQL.toString());
					
					flg_Sign = true;
					
				}
				
			//ログイン用SQL作成
			} else if (strKbn_Login.equals("2")){
				
				strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);
				strSQL.append(" AND password = '");
				strSQL.append(strPassword + "' COLLATE Japanese_CS_AS");
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
			}
			// MOD 2013/9/25 okano【QP@30151】No.28 end
							
			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//認証結果格納パラメーター呼出
			// MOD 2013/9/25 okano【QP@30151】No.28 start
//				storageUserNinsho(lstRecset, resKind.getTableItem(0));
			storageUserNinsho(lstRecset, resKind.getTableItem(0), flg_Eigyo, flg_Sign);
			// MOD 2013/9/25 okano【QP@30151】No.28 end
			
		} catch (Exception e) {
			
			em.ThrowException(e,"ユーザー認証に失敗しました。");
			
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

	/**
	 * 認証結果パラメーター格納 
	 * : ユーザー認証の認証結果情報を機能レスポンスデータへ格納する。
	 * @param lstNinshoKekka：認証結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	// MOD 2013/9/25 okano【QP@30151】No.28 start
//		private void storageUserNinsho(List<?> lstNinshoKekka, RequestResponsTableBean resTable) 
	private void storageUserNinsho(List<?> lstNinshoKekka, RequestResponsTableBean resTable, boolean flg_Eigyo, boolean flg_Sign) 
	// MOD 2013/9/25 okano【QP@30151】No.28 end
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			// ADD 2013/9/25 okano【QP@30151】No.28 start
			if(flg_Eigyo){
				resTable.addFieldVale(0, "flg_eigyo", "true");
			} else {
				resTable.addFieldVale(0, "flg_eigyo", "false");
			}
			if(flg_Sign){
				resTable.addFieldVale(0, "flg_sign", "true");
			} else {
				resTable.addFieldVale(0, "flg_sign", "false");
			}
			// ADD 2013/9/25 okano【QP@30151】No.28 end

			//ユーザー情報が存在しない場合
			if (Integer.parseInt(lstNinshoKekka.get(0).toString()) == 0){

				//警告例外をThrowする。
				em.ThrowException(ExceptionKind.一般Exception, "W000401", "", "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e,"ユーザー認証に失敗しました。");
			
		} finally {

		}

	}

}
