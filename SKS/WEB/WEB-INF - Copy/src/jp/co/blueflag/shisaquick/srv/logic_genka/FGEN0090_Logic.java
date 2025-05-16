package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 排他解除
 *  : 排他解除を行う
 *  機能ID：FGEN0090
 *  
 * @author Nishigawa
 * @since  2010/02/17
 */
public class FGEN0090_Logic extends LogicBase{
	
	/**
	 * 排他解除
	 * : インスタンス生成
	 */
	public FGEN0090_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 排他解除
	 *  : 排他解除を行う
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
			
			//排他解除[ table ]　レスポンスデータの形成
			this.haitaTableSetting(resKind, reqData);
			
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
	
	/**
	 * 排他解除[ table ]レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2010/02/17
	 */
	private void haitaTableSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;	
		
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			
			//テーブル名
			String strTblNm = "table";

			//排他解除
			updateHaitaKbn(reqData);
			
			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//追加したテーブルへレコード格納
			this.storageHaitakaizyo(resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他解除[ table ]レスポンスデータの形成処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;
				
			}

			//変数の削除
			strSqlBuf = null;

		}
		
	}
	
	
	/**
	 * 排他区分の更新
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void updateHaitaKbn(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();
			
			try {
				strSQL.append(" UPDATE tr_shisan_shisakuhin");
				strSQL.append("   SET ");
				strSQL.append("        haita_id_user = NULL ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain = " + 
						toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
				strSQL.append(" AND nen = " + 
						toString(reqData.getFieldVale(0, 0, "nen")) + " ");
				strSQL.append(" AND no_oi = " + 
						toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

				//【QP@00342】
				strSQL.append(" AND no_eda = " + 
						toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

				
				//共通クラス　データベース管理を用い、排他区分の更新を行う
				this.execDB.execSQL(strSQL.toString());
				
				// コミット
				execDB.Commit();
				
			} catch(Exception e) {
				// ロールバック
				execDB.Rollback();
				this.em.ThrowException(e, "");
				
			} finally {
				if (execDB != null) {
					execDB.Close();				//セッションのクローズ
					execDB = null;
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "排他解除処理が失敗しました。");
			
		} finally {
			//変数の削除
			strSQL = null;
			
		}
	}
	
	
	
	/**
	 * 排他解除[ table ]パラメーター格納
	 * @param resTable : レスポンスデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHaitakaizyo(
				RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
				//処理結果の格納
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
	
}
