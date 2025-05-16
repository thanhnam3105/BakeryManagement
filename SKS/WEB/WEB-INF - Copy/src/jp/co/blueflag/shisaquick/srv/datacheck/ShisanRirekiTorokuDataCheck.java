package jp.co.blueflag.shisaquick.srv.datacheck;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 【SA820】 JWS-試算確定履歴登録処理ＤＢチェック
 * @author k-katayama
 * @since  2009/06/11
 */
public class ShisanRirekiTorokuDataCheck extends DataCheck{

	/**
	 *  JWS-試算確定履歴登録処理ＤＢチェック処理用コンストラクタ 
	 */
	public ShisanRirekiTorokuDataCheck() {
		//基底クラスのコンストラクタ
		super();
		
	}

	/**
	 * JWS-試算確定履歴登録処理ＤＢチェック
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		String strShisakuRetu = "";
		String strSampleNo = "";

		try {
			
			//SQLを取得
			strSql  = createCheckSQL(reqData);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchBD.dbSearch(strSql.toString());

			//検索結果がある場合
			if ( lstRecset.size() != 0 ) {
				
				Object[] items = (Object[])lstRecset.get(0);
				
				//サンプルＮｏを取得し、エラーメッセージに表示
				strShisakuRetu = toString(items[5]);
				strSampleNo = toString(items[6]);
				
				em.ThrowException(ExceptionKind.一般Exception,"E000304", "試作列:" + strShisakuRetu, "サンプルNo:" + strSampleNo, "");
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchBD != null) {
				//セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
			
			//変数の削除
			strSql = null;
			strShisakuRetu = null;
			strSampleNo = null;
			
		}
		
	}

	/**
	 * 試算履歴存在チェック用SQL作成
	 * @param reqData : 機能リクエストデータ
	 * @return 作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createCheckSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		String strReqShainCd = "";
		String strReqNen = "";
		String strReqOiNo = "";
		String strReqRirekiJun = "";
		String strReqShisakuSeq = "";
		
		try {

			// 機能リクエストデータの取得
			strReqShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strReqNen = reqData.getFieldVale(0, 0, "nen");
			strReqOiNo = reqData.getFieldVale(0, 0, "no_oi");
			strReqRirekiJun = toString(reqData.getFieldVale(0, 0, "sort_rireki"));
			strReqShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku"));
			
			//SQLの生成
			ret.append("SELECT ");
			ret.append("   T142.cd_shain AS cd_shain ");
			ret.append("  ,T142.nen AS nen ");
			ret.append("  ,T142.no_oi AS no_oi ");
			ret.append("  ,T142.seq_shisaku AS seq_shisaku ");
			ret.append("  ,T142.sort_rireki AS sort_rireki ");
			ret.append("  ,ISNULL(T141.sort_shisaku,0) AS sort_shisaku ");
			ret.append("  ,ISNULL(T141.nm_sample,'') AS nm_sample ");
			ret.append(" FROM tr_shisan_rireki T142");
			ret.append(" LEFT JOIN tr_shisaku T141");
			ret.append(" ON   T141.cd_shain = T142.cd_shain ");
			ret.append(" AND T141.nen = T142.nen ");
			ret.append(" AND T141.no_oi = T142.no_oi ");
			ret.append(" AND T141.seq_shisaku = T142.seq_shisaku ");
			ret.append("WHERE T142.cd_shain = ");
			ret.append(strReqShainCd);
			ret.append(" AND T142.nen = ");
			ret.append(strReqNen);
			ret.append(" AND T142.no_oi = ");
			ret.append(strReqOiNo);
			ret.append(" AND T142.sort_rireki = ");
			ret.append(strReqRirekiJun);
			ret.append(" AND T142.seq_shisaku = ");
			ret.append(strReqShisakuSeq);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			 
		} finally {
			strReqShainCd = null;
			strReqNen = null;
			strReqOiNo = null;
			strReqRirekiJun = null;
			strReqShisakuSeq = null;
			
		}
		return ret;
		
	}
	
}
