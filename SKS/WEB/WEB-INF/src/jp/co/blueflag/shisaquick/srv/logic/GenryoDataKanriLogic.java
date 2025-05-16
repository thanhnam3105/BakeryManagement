package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * 原料データ管理DB処理
 *  : 原料データ削除処理のＤＢに対する業務ロジックの実装
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class GenryoDataKanriLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public GenryoDataKanriLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 原料データ削除管理
	 * @param reqKind : 機能リクエストデータ
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

			String strReqKaishaCd = "";							//リクエストデータ : 会社コード
			String strReqGenryoCd = "";							//リクエストデータ : 原料コード
//			String strReqHaishiFlg = "";							//リクエストデータ : 廃止フラグ

			// 機能リクエストデータの取得
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
//			strReqHaishiFlg = reqData.getFieldVale(0, 0, "flg_haishi");

			//①：原料データ削除SQL作成処理メソッドを呼び出し、原料データの削除処理を行う。
			this.genryoDeleteSQL(strReqKaishaCd,strReqGenryoCd);
			
			//②：正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));
			
			this.storageGenryoDataKanri(resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ削除管理処理が失敗しました。");
		} finally {
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}
		}

		return resKind;
	}

	/**
	 * 原料データ削除SQL作成
	 *  : 削除用SQLを作成し、原料分析情報マスタ及び原料マスタを削除する
	 *   ※ 対象テーブル : ma_genryo, ma_genryokojo
	 * @param strKaishaCd : 会社コード
	 * @param strGenryoCd : 原料コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoDeleteSQL(String strKaishaCd, String strGenryoCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = new StringBuffer();
		StringBuffer strSQL_M402 = new StringBuffer();

		try {
			
			//①：リクエストデータより、原料データ削除を行う為のSQLを作成する。
			
			//ma_genryo : 原料分析情報マスタ
			strSQL_M401.append(" DELETE FROM ma_genryo ");
			strSQL_M401.append("  WHERE cd_kaisha =" + strKaishaCd );
			strSQL_M401.append("   AND  cd_genryo ='" + strGenryoCd + "' ");
			
			//ma_genryokojo : 原料マスタ(新規原料のみ)
			strSQL_M402.append(" DELETE FROM ma_genryokojo ");
			strSQL_M402.append("  WHERE cd_kaisha =" + strKaishaCd );
			strSQL_M402.append("   AND  cd_busho =" + ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append("   AND  cd_genryo ='" + strGenryoCd + "' ");
			
			//②：データベース管理を用い、原料データの削除を行う。
			super.createExecDB();								//DB更新の生成
			this.execDB.BeginTran();							//トランザクション開始
			
			try{
				//M401 原料分析情報マスタ　削除用SQL実行
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 原料マスタ　削除用SQL実行			
				this.execDB.execSQL(strSQL_M402.toString());
				
				this.execDB.Commit();							//コミット

			}catch(Exception e){
				this.execDB.Rollback();							//ロールバック
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データ削除SQL作成処理が失敗しました。");
		} finally {
			//変数の削除
			strSQL_M401 = null;
			strSQL_M402 = null;
		}
		
	}
	
	/**
	 * 管理結果パラメーター格納
	 *  : 原料データの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryoDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "管理結果パラメーター格納処理が失敗しました。");

		} finally {

		}
		
	}
	
}
