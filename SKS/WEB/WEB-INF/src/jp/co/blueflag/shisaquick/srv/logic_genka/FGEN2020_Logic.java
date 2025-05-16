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
 * ステータスクリア　現在ステータス取得
 *  : 現在ステータス情報を取得する。
 *  機能ID：FGEN2010
 *  
 * @author Nishigawa
 * @since  2011/01/25
 */
public class FGEN2020_Logic extends LogicBase{
	
	/**
	 * ステータスクリア　現在ステータス取得処理
	 * : インスタンス生成
	 */
	public FGEN2020_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * ステータスクリア　現在ステータス取得
	 *  : 現在ステータス情報を取得する。
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
		
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			
			//テーブル名
			String strTblNm = "kihon";	
			
			//①データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//②共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//④追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "ステータス履歴　データ検索処理が失敗しました。");
			
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
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaKihonSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			//試作コード設定（リクエストに試作コードがない場合、USERINFOより試作コードを取得）
			if( toString(reqData.getFieldVale(0, 0, "cd_shain")) == "" ){
				
				//試作コード_社員CD、年、追番　設定
				reqData.setFieldVale(0, 0, "cd_shain", toString(userInfoData.getMovement_condition().get(0)) );
				reqData.setFieldVale(0, 0, "nen", toString(userInfoData.getMovement_condition().get(1)) );
				reqData.setFieldVale(0, 0, "no_oi", toString(userInfoData.getMovement_condition().get(2)) );
				reqData.setFieldVale(0, 0, "no_eda", toString(userInfoData.getMovement_condition().get(3)) );
				
			}
			String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
			String strNen = reqData.getFieldVale(0, 0, "nen");
			String strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			String strNoEda = reqData.getFieldVale(0, 0, "no_eda");
			
			//SQL文の作成
			strSql.append(" SELECT cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,no_eda  ");
			strSql.append("       ,st_kenkyu  ");
			strSql.append("       ,st_seisan  ");
			strSql.append("       ,st_gensizai  ");
			strSql.append("       ,st_kojo  ");
			strSql.append("       ,st_eigyo  ");
			strSql.append("       ,id_toroku  ");
			strSql.append("       ,dt_toroku  ");
			strSql.append("       ,id_koshin  ");
			strSql.append("       ,dt_koshin  ");
			strSql.append(" FROM tr_shisan_status  ");
			strSql.append(" WHERE   ");
			strSql.append(" 	cd_shain = " + strCdShain );
			strSql.append(" 	AND nen = " + strNen );
			strSql.append(" 	AND no_oi = " + strNoOi );
			strSql.append(" 	AND no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納） 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
				resTable.addFieldVale(i, "nen", toString(items[1],""));
				resTable.addFieldVale(i, "no_oi", toString(items[2],""));
				resTable.addFieldVale(i, "no_eda", toString(items[3],""));
				resTable.addFieldVale(i, "st_kenkyu", toString(items[4],""));
				resTable.addFieldVale(i, "st_seisan", toString(items[5],""));
				resTable.addFieldVale(i, "st_gensizai", toString(items[6],""));
				resTable.addFieldVale(i, "st_kojo", toString(items[7],""));
				resTable.addFieldVale(i, "st_eigyo", toString(items[8],""));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
