package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 【S2- SA420】 排他制御処理DBチェック
 * @author k-katayama
 * @since  2009/04/18
 */
public class HaitaSeigyoDataCheck extends DataCheck{

	/**
	 * 排他制御処理DBチェック処理用コンストラクタ 
	 */
	public HaitaSeigyoDataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 *排他制御処理DBチェック
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

		try {

			// 機能リクエストデータの取得
			String strReqZikoKbn = reqData.getFieldVale(0, 0, "kubun_ziko");
			String strReqShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			String strReqNen = reqData.getFieldVale(0, 0, "nen");
			String strReqOiNo = reqData.getFieldVale(0, 0, "no_oi");

			//SQL文の作成
			if (strReqZikoKbn.equals("1")) {
				//SQLを作成
				strSql = shisakuCheckSQL(strReqShainCd,strReqNen,strReqOiNo);
				
				//SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//検索結果がない場合
				if (lstRecset.size() == 0){
					String strMsg = strReqShainCd + "-" + strReqNen + "-" + strReqOiNo;
					em.ThrowException(ExceptionKind.一般Exception,"E000301", "試作コード", strMsg, "");
				}
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
		}
		
	}
	
	/**
	 * 試作コード存在チェックSQL作成
	 * @param strShainCd : 試作CD-社員コード
	 * @param strNen : 試作CD-年
	 * @param strOiNo : 試作CD-追番
	 * @return strSql：作成SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer shisakuCheckSQL(String strShainCd, String strNen, String strOiNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSQL = new StringBuffer(); 
		
		try {
			//SQL文の作成
			strRetSQL.append("SELECT cd_shain, nen, no_oi");
			strRetSQL.append(" FROM tr_shisakuhin");
			strRetSQL.append(" WHERE cd_shain = ");
			strRetSQL.append(strShainCd);
			strRetSQL.append(" AND nen = ");
			strRetSQL.append(strNen);
			strRetSQL.append(" AND no_oi = ");
			strRetSQL.append(strOiNo);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		
		return strRetSQL;
	}

}
