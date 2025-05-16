package jp.co.blueflag.shisaquick.srv.jnlp;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 試作Noシークレットモードチェック処理
 *  : 試作コードのシークレットモードのチェックを行う。
 * @author shima
 * @since  2012/10/16
 */
public class ShisakuSecretCheck extends LogicBase {
	/**
	 * 試作No権限チェック処理用コンストラクタ
	 * : インスタンス生成
	 */
	public ShisakuSecretCheck(){
		//基底クラスのコンストラクタ
		super();
	}

	/**
	 * 試作Noシークレットモードチェック処理DBチェック
	 * :試作コードのシークレットモードのチェック処理の管理を行う。
	 * @param reqData : リクエストデータ
	 * @param _userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public boolean execDataCheck(
		RequestResponsKindBean reqData,
		UserInfoData _userInfoData
		)
	throws ExceptionSystem,ExceptionUser,ExceptionWaning{
		
		//JWS起動フラグ
		boolean flg_open = true;
		
		//ユーザー情報退避
		userInfoData = _userInfoData;
		
		//初期化
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql_hon = new StringBuffer();
		StringBuffer strSql_team = new StringBuffer();
		List<?> lstRecset = null;
		List<?> honRecset = null;
		List<?> teamRecset = null;
		
		try{
			strSql = createSQL(reqData);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//検索結果が無い時(シークレットモード)の場合
			if(lstRecset.size() == 0){
				
				//登録者か
				strSql_hon = createSQL_honnin(reqData);
				honRecset = searchDB.dbSearch(strSql_hon.toString());
				if(honRecset.size() == 0){
					
					//同じチームか
					strSql_team = createSQL_team(reqData);
					teamRecset = searchDB.dbSearch(strSql_team.toString());
					if(teamRecset.size() == 0){
					
						// 20160420 MOD KPX@1600766 Start
						//チームリーダー OR グループリーダーか
//						if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "GROUP_LEADER_CD"))
//							== Integer.parseInt(userInfoData.getCd_literal()) ||
//							Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
//							== Integer.parseInt(userInfoData.getCd_literal())){
						//グループリーダーか
						if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "GROUP_LEADER_CD"))
								== Integer.parseInt(userInfoData.getCd_literal())){
						// 20160420 MOD KPX@1600766 End
						}else{
							flg_open = false;
						}
					}
				}
			}
		}
		catch(Exception e){
			this.em.ThrowException(e,"試作コードのシークレットチェック処理に失敗しました。");
		}
		finally{
			//リストの破棄
			removeList(lstRecset);
			removeList(honRecset);
			removeList(teamRecset);
			if(searchDB != null){
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
			strSql_hon = null;
			strSql_team = null;
		}
		return flg_open;
	}
	
	/**
	 * 試作Noシークレット設定チェック処理SQL作成
	 *  : シークレット設定がある場合、データを取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @return strRetSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		String strNo_shisaku = null;
		String strCd_shain = null;
		String strNen = null;
		String strNo_oi = null;
		
		try {
			//機能リクエストデータの試作コードの値を取得する
			strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
			
			//試作コードを社員CD 年 追番に分割する
			strCd_shain = strNo_shisaku.split("-")[0];
			strNen = strNo_shisaku.split("-")[1];
			strNo_oi = strNo_shisaku.split("-")[2];
			
			//SQLの作成
			strRetSql.append(" SELECT T1.*");
			strRetSql.append(" FROM tr_shisakuhin T1 ");
			strRetSql.append(" LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strRetSql.append(" WHERE");
			strRetSql.append(" T1.cd_shain=" + strCd_shain);
			strRetSql.append(" AND");
			strRetSql.append(" T1.nen=" + strNen);
			strRetSql.append(" AND");
			strRetSql.append(" T1.no_oi=" + strNo_oi);
			strRetSql.append(" AND");
			strRetSql.append(" T1.flg_secret IS NULL");
		}
		catch (Exception e) {
			this.em.ThrowException(e, "シークレットチェックSQLの作成に失敗しました。");
		} finally {
		}
		
		return strRetSql;
	}
	
	/**
	 * 試作Noシークレット閲覧権限チェック処理SQL作成
	 *  : 本人が作成したものかどうかを判定
	 * @param reqData：リクエストデータ
	 * @return strRetSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_honnin(RequestResponsKindBean reqData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		String strNo_shisaku = null;
		String strCd_shain = null;
		String strNen = null;
		String strNo_oi = null;
		
		try {
			//機能リクエストデータの試作コードの値を取得する
			strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
			
			//試作コードを社員CD 年 追番に分割する
			strCd_shain = strNo_shisaku.split("-")[0];
			strNen = strNo_shisaku.split("-")[1];
			strNo_oi = strNo_shisaku.split("-")[2];
			
			//SQLの作成
			strRetSql.append(" SELECT T1.*");
			strRetSql.append(" FROM tr_shisakuhin T1 ");
			strRetSql.append(" Where");
			strRetSql.append(" T1.cd_shain=" + strCd_shain);
			strRetSql.append(" And");
			strRetSql.append(" T1.nen=" + strNen);
			strRetSql.append(" And");
			strRetSql.append(" T1.no_oi=" + strNo_oi);
			strRetSql.append(" And");
			strRetSql.append(" T1.cd_shain=" + userInfoData.getId_user());
		} catch (Exception e) {
			this.em.ThrowException(e, "シークレットチェックSQLの作成に失敗しました。");
		} finally {
		}	
		return strRetSql;
	}
	
	/**
	 * 試作Noシークレット閲覧権限チェック処理SQL作成
	 *  : 同チームの者が作成したものかどうかを判定
	 * @param reqData：リクエストデータ
	 * @return strRetSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_team(RequestResponsKindBean reqData) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{
			StringBuffer strRetSql = new StringBuffer();
			String strNo_shisaku = null;
			String strCd_shain = null;
			String strNen = null;
			String strNo_oi = null;
			
			try {
				//機能リクエストデータの試作コードの値を取得する
				strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
				
				//試作コードを社員CD 年 追番に分割する
				strCd_shain = strNo_shisaku.split("-")[0];
				strNen = strNo_shisaku.split("-")[1];
				strNo_oi = strNo_shisaku.split("-")[2];
				
				//SQLの作成
				strRetSql.append(" SELECT T1.*");
				strRetSql.append(" FROM tr_shisakuhin T1 ");
				strRetSql.append(" Where");
				strRetSql.append(" T1.cd_shain=" + strCd_shain);
				strRetSql.append(" And");
				strRetSql.append(" T1.nen=" + strNen);
				strRetSql.append(" And");
				strRetSql.append(" T1.no_oi=" + strNo_oi);
				strRetSql.append(" And");
				strRetSql.append(" T1.cd_group=" + userInfoData.getCd_group());
				strRetSql.append(" And");
				strRetSql.append(" T1.cd_team=" + userInfoData.getCd_team());
			} catch (Exception e) {
				this.em.ThrowException(e, "シークレットチェックSQLの作成に失敗しました。");
			} finally {
			}	
			return strRetSql;
		}
	
}
