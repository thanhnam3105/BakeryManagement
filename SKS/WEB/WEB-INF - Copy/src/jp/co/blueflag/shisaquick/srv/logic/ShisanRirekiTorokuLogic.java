package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * [SA820] JWS-試算確定履歴登録ＤＢ処理の実装
 *  
 * @author TT.k-katayama
 * @since  2009/06/10
 *
 */
public class ShisanRirekiTorokuLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public ShisanRirekiTorokuLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * JWS-試算確定履歴登録ＤＢ処理ロジック管理
	 * @param reqData : 機能リクエストデータ
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

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		//登録用SQL
		StringBuffer strSql = null;

		try {
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			
			//①:トランザクションを開始する
			super.createExecDB();								//DB更新の生成
			this.execDB.BeginTran();							//トランザクション開始			

			try {

				//② : 試算履歴登録用SQLを作成
				strSql = this.createInsertSQL(reqData);
								
				//③ : 試算履歴登録用SQLを実行
				this.execDB.execSQL(strSql.toString());
				
				//④ : :コミット/ロールバック処理を実行する
				this.execDB.Commit();							//コミット
				
			} catch(Exception e) {
				this.execDB.Rollback();							//ロールバック
				this.em.ThrowException(e, "");
				
			} finally {
				
			}

			//⑤ : 登録結果パラメーター格納処理を呼び出し、レスポンスデータに設定する

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//パラメータ格納
			this.storageResponsData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-試算確定履歴登録ＤＢ処理が失敗しました。");

		} finally {			
			//セッションの解放
			if (this.execDB != null) {
				this.execDB.Close();
				this.execDB = null;
				
			}
			
			//変数の削除
			strSql = null;

		}
		return resKind;

	}

	/**
	 * 試算履歴登録用SQLの作成
	 * @param reqData : 機能リクエストデータ
	 * @return strSql : 作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createInsertSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		
		try {

			//登録用SQL作成
			ret.append(" INSERT INTO tr_shisan_rireki ");
			ret.append("  (cd_shain, nen, no_oi, seq_shisaku, sort_rireki, nm_sample, dt_shisan, id_toroku, dt_toroku) ");
			ret.append(" VALUES ( ");
			ret.append("  " + reqData.getFieldVale(0, 0, "cd_shain") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "nen") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "no_oi") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "seq_shisaku") );
			ret.append("  ," + reqData.getFieldVale(0, 0, "sort_rireki") );
			ret.append("  ,'" + reqData.getFieldVale(0, 0, "nm_sample") + "'");
			ret.append("  ,GETDATE()" );
//			ret.append("  ," + reqData.getFieldVale(0, 0, "cd_shain"));
			ret.append("  ," + userInfoData.getId_user());
			ret.append("  ,GETDATE()" );
			ret.append(" ) ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		return ret;
		
	}

	/**
	 * 登録結果パラメーター格納処理
	 * @param resTable : レスポンステーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageResponsData(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//①：レスポンスデータを形成する。

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
