package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 【QP@00342】
 * 担当者マスタメンテ（営業）　ログインID取得（シングルサインオン時）
 *  : 現在ステータス情報を取得する。
 *  機能ID：FGEN2050
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2120_Logic extends LogicBase{
	
	/**
	 * 担当者マスタメンテ（営業）　ログインID取得（シングルサインオン時）
	 * : インスタンス生成
	 */
	public FGEN2120_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 担当者マスタメンテ（営業）　ログインID取得（シングルサインオン時）
	 *  : ログインID（シングルサインオン時）情報を取得する。
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
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;	
		
		try {
			//テーブル名
			String strTblNm = "table";	
			
			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//追加したテーブルへレコード格納
			this.storageGenkaKihon(resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者マスタメンテ（営業）　ログインID取得（シングルサインオン時）処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;
				
			}
		}
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
	private void storageGenkaKihon( RequestResponsTableBean resTable ) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//ユーザID取得
			String id_user = toString(userInfoData.getMovement_condition().get(0));
			
			//権限データID取得
			String strUKinoId = "";
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				// MOD 2013/9/25 okano【QP@30151】No.28 start
//					if (userInfoData.getId_gamen().get(i).toString().equals("200")){
				if (userInfoData.getId_gamen().get(i).toString().equals("200") || userInfoData.getId_gamen().get(i).toString().equals("90")){
				// MOD 2013/9/25 okano【QP@30151】No.28 end
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
				}
			}
			
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			//結果をレスポンスデータに格納
			resTable.addFieldVale(0, "id_user", id_user);
			
			//仮登録ユーザ区分設定
			// MOD 2013/9/25 okano【QP@30151】No.28 start
//				if(strUKinoId.equals("101")){
			if(strUKinoId.equals("101") || strUKinoId.equals("22") || strUKinoId.equals("104") || strUKinoId.equals("23") || strUKinoId.equals("105") || strUKinoId.equals("24") ){
			// MOD 2013/9/25 okano【QP@30151】No.28 end
				resTable.addFieldVale(0, "kbn_kari", "1");
			}
			else{
				resTable.addFieldVale(0, "kbn_kari", "0");
			}
			
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
