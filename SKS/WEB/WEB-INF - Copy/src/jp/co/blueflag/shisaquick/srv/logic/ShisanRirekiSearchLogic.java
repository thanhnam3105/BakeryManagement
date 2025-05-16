package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * [SA840] JWS-試算確定履歴検索ＤＢ処理の実装
 *  
 * @author TT.k-katayama
 * @since  2009/06/10
 *
 */
public class ShisanRirekiSearchLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public ShisanRirekiSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * JWS-試算確定履歴検索ＤＢ処理ロジック管理
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
		
		//検索用SQL
		StringBuffer strSql = null;
		//検索結果データリスト
		List<?> lstRecset = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//① : 試算確定履歴検索用SQLを作成
			strSql = this.createRirekiSearchSQL(reqData);
			
			//② : SQLを実行
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
						
			//③ : 検索結果が無い場合
			if ( lstRecset.size() == 0 ) {
				this.em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}
			
			//④ : 試算履歴データパラメーター格納処理を呼び出し、レスポンスデータに設定する
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//パラメータ格納
			this.storageResponsData(lstRecset, resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "JWS-試算確定履歴検索ＤＢ処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			//セッションの解放
			if (this.searchDB != null) {
				this.searchDB.Close();
				this.searchDB = null;
				
			}
			
			//変数の削除
			strSql = null;

		}
		return resKind;

	}

	/**
	 * 試算履歴検索用SQLの作成
	 * @param reqData : 機能リクエストデータ
	 * @return strSql : 作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createRirekiSearchSQL(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		
		String strShainCd = null;
		String strNen = null;
		String strNoOi = null;		
		
		try { 

			//リクエストデータより検索用パラメータを取得する
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNoOi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQLを作成する
			ret.append(" SELECT DISTINCT ");
			ret.append("   T142.cd_shain AS cd_shain ");
			ret.append("  ,T142.nen AS nen ");
			ret.append("  ,T142.no_oi AS no_oi ");
			ret.append("  ,T142.seq_shisaku AS seq_shisaku ");
			ret.append("  ,T142.sort_rireki AS sort_rireki ");
			ret.append("  ,T142.nm_sample AS nm_sample ");
			ret.append("  ,CONVERT(VARCHAR,T142.dt_shisan,111) AS dt_shisan ");
			ret.append("  ,T142.id_toroku AS id_toroku ");
			ret.append("  ,T142.dt_toroku AS dt_toroku ");
			ret.append(" FROM tr_shisan_rireki T142 ");
			ret.append(" WHERE T142.cd_shain=");
			ret.append(strShainCd);
			ret.append("  AND T142.nen=");
			ret.append(strNen);
			ret.append("  AND T142.no_oi=");
			ret.append(strNoOi);
			ret.append("  ORDER BY T142.sort_rireki, T142.seq_shisaku ");
			
		} catch(Exception e) {
			this.em.ThrowException(e, "");			
			
		} finally {
			
		}
		return ret;
		
	}

	/**
	 * 試算履歴データパラメーター格納処理
	 * @param lstRecdata : 検索結果データリスト
	 * @param resTable : レスポンステーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageResponsData(List<?> lstRecdata, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try { 
			//①：レスポンスデータを形成する。
			
			for ( int i=0; i<lstRecdata.size(); i++ ) {
				
				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[])lstRecdata.get(i);
				
				resTable.addFieldVale(i, "cd_shain", toString(items[0]));
				resTable.addFieldVale(i, "nen", toString(items[1]));
				resTable.addFieldVale(i, "no_oi", toString(items[2]));
				resTable.addFieldVale(i, "seq_shisaku", toString(items[3]));
				resTable.addFieldVale(i, "sort_rireki", toString(items[4]));
				resTable.addFieldVale(i, "nm_sample", toString(items[5]));;
				resTable.addFieldVale(i, "dt_shisan", toString(items[6]));
				resTable.addFieldVale(i, "id_toroku", toString(items[7]));
				resTable.addFieldVale(i, "dt_toroku", toString(items[8]));
				
			}			
			
		} catch(Exception e) {
			this.em.ThrowException(e, "");			
			
		} finally {
			
		}
				
	}
	
}
