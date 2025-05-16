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
 * [SA870] JWS-原価試算No更新ＤＢ処理の実装
 *  
 * @author TT.k-katayama
 * @since  2009/07/11
 *
 */
public class ShisanNoUpdateLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public ShisanNoUpdateLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * JWS-原価試算No更新ＤＢ処理ロジック管理
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

				//試作SEQ1が空ではない場合、原価試算No.更新処理を行う
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku1")).isEmpty() ) {
					
					//② : 原価試算No更新用SQLを作成
					strSql = this.createUpdateSQL(reqData, 1);
									
					//③ : 原価試算No更新用SQLを実行
					this.execDB.execSQL(strSql.toString());
				
				}

				//試作SEQ2が空ではない場合、原価試算No.更新処理を行う
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku2")).isEmpty() ) {

					//④ : 原価試算No更新用SQLを作成
					strSql = this.createUpdateSQL(reqData, 2);
									
					//⑤ : 原価試算No更新用SQLを実行
					this.execDB.execSQL(strSql.toString());
										
				}

				//試作SEQ3が空ではない場合、原価試算No.更新処理を行う
				if ( !toString(reqData.getFieldVale(0, 0, "seq_shisaku3")).isEmpty() ) {

					//⑥ : 原価試算No更新用SQLを作成
					strSql = this.createUpdateSQL(reqData, 3);
									
					//⑦ : 原価試算No更新用SQLを実行
					this.execDB.execSQL(strSql.toString());
										
				}
				
				//⑧ :コミット/ロールバック処理を実行する
				this.execDB.Commit();							//コミット
				
			} catch(Exception e) {
				this.execDB.Rollback();							//ロールバック
				this.em.ThrowException(e, "");
				
			} finally {
				
			}

			//⑨ : 登録結果パラメーター格納処理を呼び出し、レスポンスデータに設定する

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//パラメータ格納
			this.storageResponsData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-原価試算No更新ＤＢ処理が失敗しました。");

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
	 * 原価試算No更新用SQLの作成
	 * @param reqData : 機能リクエストデータ
	 * @param intShisakuNo : 1～3までの原価試算No
	 * @return strSql : 作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createUpdateSQL(RequestResponsKindBean reqData, int intShisanNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		String strSeqShisaku;
		
		try {
			
			//試作SEQを取得
			strSeqShisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + intShisanNo));
			
			//試作SEQがNULLまたは空では無い場合、更新用SQLを作成する
			if ( !strSeqShisaku.isEmpty() ) {
			
				//更新用SQL作成
				ret.append(" UPDATE tr_shisaku ");
				ret.append("    SET no_shisan = " + intShisanNo);
				ret.append("       ,id_koshin = " + userInfoData.getId_user());
				ret.append("       ,dt_koshin = GETDATE() ");
				ret.append("  WHERE cd_shain=" + reqData.getFieldVale(0, 0, "cd_shain"));
				ret.append("   AND nen=" + reqData.getFieldVale(0, 0, "nen"));
				ret.append("   AND no_oi=" + reqData.getFieldVale(0, 0, "no_oi"));
				ret.append("   AND seq_shisaku="+ strSeqShisaku);
				
			}
			
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
