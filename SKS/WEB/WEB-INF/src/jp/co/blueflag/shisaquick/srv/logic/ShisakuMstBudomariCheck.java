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
 * マスタ歩留チェックＤＢ処理
 * @author nishigawa
 * @since  2010/10/15
 */
public class ShisakuMstBudomariCheck extends LogicBase{
	
	/**
	 * マスタ歩留チェックＤＢ処理
	 * : インスタンス生成
	 */
	public ShisakuMstBudomariCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作分析データ確認：原料分析情報取得SQL作成
	 *  : 原料分析情報を取得するSQLを作成。
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
			strSql = budomariDataCreateSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageResponseData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "マスタ歩留チェックＤＢ処理に失敗しました。");
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
	 * 原料工場検索SQL作成
	 *  : 原料工場情報を取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer budomariDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			
			//会社コード
			String strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//部署コード
			String strBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//原料コード
			String strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//歩留
			String strBudomari = reqData.getFieldVale(0, 0, "budomari");

			strAllSql.append("  select");
			strAllSql.append("   budomari");
			strAllSql.append("  from");
			strAllSql.append("   ma_genryokojo");
			strAllSql.append("  where");
			strAllSql.append("   cd_kaisha = " + strKaishaCd );
			strAllSql.append("   and cd_busho = " + strBushoCd );
			strAllSql.append("   and cd_genryo = '" + strGenryoCd + "'");
			strAllSql.append("   and budomari = " + strBudomari );
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "マスタ歩留チェックＤＢ処理に失敗しました。");
		} finally {
			//変数の削除
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * 原料工場情報パラメーター格納
	 * @param lstGenryouData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageResponseData(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
				
			//結果をレスポンスデータに格納
			//検索結果がない場合（歩留が相違）
			if (lstGenryouData.size() == 0){
				resTable.addFieldVale(0, "flg_hikaku", "false");
			}
			//検索結果がある場合（歩留が同一）
			else{
				resTable.addFieldVale(0, "flg_hikaku", "true");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "マスタ歩留チェックＤＢ処理に失敗しました。");

		} finally {

		}

	}
	
}