package jp.co.blueflag.shisaquick.srv.jnlp;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 
 * 試作No権限チェック処理
 *  : 試作コードの権限チェックを行う。
 * @author yoshida
 * @since  2009/08/03
 */
public class ShisakuNoKengenCheck extends LogicBase{ 
	/**
	 * 試作No権限チェック処理用コンストラクタ
	 * : インスタンス生成
	 */
	public ShisakuNoKengenCheck(){
		//基底クラスのコンストラクタ
		super();
	}
	/**
	 * 試作No権限チェック処理DBチェック
	 * :試作コードの権限チェック処理の管理を行う。
	 * @param reqData : リクエストデータ
	 * @param _userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public boolean execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//JWS起動フラグ
		boolean flg_open = true;
		
		//ユーザー情報退避
		userInfoData = _userInfoData;
		
		//初期化
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql_etr = new StringBuffer();
		StringBuffer strSql_hon = new StringBuffer();
		String strGamenId = null;
		String strKinoId = null;
		String strDataId = null;
		List<?> lstRecset = null;
		List<?> etrRecset = null;
		List<?> honRecset = null;
		
		try {
			
			//画面IDの取得
			strGamenId = reqData.getFieldVale(0, 0, "mode");
			
			//機能ID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
					//機能IDを設定
					strKinoId = userInfoData.getId_kino().get(i).toString();
				}
			}
			
			//データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
					//データIDを設定
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//データ編集(更新)の権限がある場合、データを取得するSQLを作成
			strSql = createSQL(reqData);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//試作データ画面(詳細)：閲覧　でなく、検索結果（データ編集権限）がない場合
			if (!(strGamenId.equals("100") && strKinoId.equals("10")) && lstRecset.size() == 0){
				
				//データ閲覧の権限がある場合、データを取得するSQLを作成
				strSql_etr = createSQL_etr(reqData);
				
				//SQLを実行
				super.createSearchDB();
				etrRecset = searchDB.dbSearch(strSql_etr.toString());
				
				//自グループのデータでない場合
				if(etrRecset.size() == 0){
					
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start						
//					//表示権限がない為、JWSを起動しない
//					flg_open = false;					
					
					//同一グループ且つ（本人＋ﾁｰﾑﾘｰﾀﾞ以上）【本人であればコピー可】 の場合
					if(strDataId.equals("5")){
						
						//本人作成確認用のSQL文作成
						strSql_hon = createSQL_honnin(reqData);
						
						//SQLを実行
						super.createSearchDB();
						honRecset = searchDB.dbSearch(strSql_hon.toString());
						
						//本人作成のデータでない場合
						if(honRecset.size() == 0){
							
							//表示権限がない為、JWSを起動しない
							flg_open = false;
							
						}
						//本人作成のデータの場合
						else{
							//試作コピーモードに変更（同グループ内であれば試作コピーのみ可能）
							reqData.setFieldVale(0, 0, "mode","130");					
						}
						
					}
					//上記以外の場合
					else{
						
						//表示権限がない為、JWSを起動しない
						flg_open = false;	
						
					}
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start						
				}
				//自グループのデータの場合
				else{
					
					//同一グループ且つ（本人＋ﾁｰﾑﾘｰﾀﾞ以上）【本人であればコピー可】 の場合
					if(strDataId.equals("5")){
						
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
						//試作コピーモードに変更（同グループ内であれば試作コピーのみ可能）
						reqData.setFieldVale(0, 0, "mode","130");					
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end	
						
					}
					//上記以外の場合
					else{
						//参照モードに変更（同グループ内であれば参照のみ可能）
						reqData.setFieldVale(0, 0, "mode","000");
					}
				}
			}
			//試作データ画面(詳細)：閲覧　で、、検索結果（データ編集権限）がない場合
			else if((strGamenId.equals("100") && strKinoId.equals("10"))&& lstRecset.size() == 0){
				
				flg_open = false;
			
			}
			//試作データ画面(詳細)：閲覧　で、、検索結果（データ編集権限）がある場合
			else if((strGamenId.equals("100") && strKinoId.equals("10"))&& lstRecset.size() != 0){
				
				//参照モードに変更
				reqData.setFieldVale(0, 0, "mode","000");
			}
			
		}
		catch (Exception e) {
			this.em.ThrowException(e, "試作コードの権限チェック処理に失敗しました。");
		}
		finally {
			//リストの破棄
			removeList(lstRecset);
			removeList(etrRecset);
			removeList(honRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
			strSql_etr = null;
			strSql_hon = null;
		}
		return flg_open;
	}
	/**
	 * 試作No権限チェック処理SQL作成
	 *  : データ編集(更新)の権限がある場合、データを取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @return strRetSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	StringBuffer strRetSql = new StringBuffer();
	String strDataId = null;
	String strGamenId = null;
	String strNo_shisaku = null;
	String strCd_shain = null;
	String strNen = null;
	String strNo_oi = null;
	
	try {
		//担当会社のSQL設定
		String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
		//画面IDの取得
		strGamenId = reqData.getFieldVale(0, 0, "mode");
		//機能リクエストデータの試作コードの値を取得する
		strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
		
		//試作コードを社員CD 年 追番に分割する
		strCd_shain = strNo_shisaku.split("-")[0];
		strNen = strNo_shisaku.split("-")[1];
		strNo_oi = strNo_shisaku.split("-")[2];
		
		//権限取得
		for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
			if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
				//データIDを設定
				strDataId = userInfoData.getId_data().get(i).toString();
			}
		}
		
		//SQLの作成
		strRetSql.append(" SELECT T1.*");
		strRetSql.append(" FROM tr_shisakuhin T1 ");
		strRetSql.append(" LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
		strRetSql.append(" Where");
		strRetSql.append(" T1.cd_shain=" + strCd_shain);
		strRetSql.append(" And");
		strRetSql.append(" T1.nen=" + strNen);
		strRetSql.append(" And");
		strRetSql.append(" T1.no_oi=" + strNo_oi);

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start						
		//検索条件権限設定
		//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
		//if (strDataId.equals("1")){
		//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上） or 同一グループ且つ（本人＋ﾁｰﾑﾘｰﾀﾞ以上）【本人であればコピー可】
	    if (strDataId.equals("1") || strDataId.equals("5")){
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end
	    	
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_team = ");
			strRetSql.append(userInfoData.getCd_team());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.id_toroku = ");
			strRetSql.append(userInfoData.getId_user());				
			strRetSql.append(" ) ");

			
		// 2010.10.20 Mod Arai Start
		// グループリーダーは、同一グループの全チームを編集可能
			//セッション情報．役職コード＝グループリーダーの場合、以下をOR条件で追加
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "GROUP_LEADER_CD"))
					== Integer.parseInt(userInfoData.getCd_literal())){
				strRetSql.append(" OR ");				
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				//strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(" ISNULL(M2.cd_yakushoku,'001') <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' ) ");
			}
			//セッション情報．役職コード＝チームリーダーの場合、以下をOR条件で追加
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
					== Integer.parseInt(userInfoData.getCd_literal())){
				strRetSql.append(" OR ");				
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" T1.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				//strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(" ISNULL(M2.cd_yakushoku,'001') <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' ) ");
			}
//			//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
//			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
//					<= Integer.parseInt(userInfoData.getCd_literal())){
//				strRetSql.append(" OR ");				
//				strRetSql.append(" ( ");
//				strRetSql.append(" T1.cd_group = ");
//				strRetSql.append(userInfoData.getCd_group());
//				strRetSql.append(" AND ");
//				strRetSql.append(" T1.cd_team = ");
//				strRetSql.append(userInfoData.getCd_team());
//				strRetSql.append(" AND ");
//				strRetSql.append(" M2.cd_yakushoku <= '");
//				strRetSql.append(userInfoData.getCd_literal());
//				strRetSql.append("' ) ");
//			}
		// 2010.10.20 Mod Arai End
			strRetSql.append(" ) ");
			
		//同一グループ且つ担当会社
		} else if (strDataId.equals("2")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kaisha in ( ");
			strRetSql.append(strSqlTanto + " ) ");
			strRetSql.append(" ) ");

		//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
		} else if (strDataId.equals("3")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_team = ");
			strRetSql.append(userInfoData.getCd_team());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.id_toroku = ");
			strRetSql.append(userInfoData.getId_user());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kaisha in ( ");
			strRetSql.append(strSqlTanto);
			strRetSql.append(" ) ");

			//セッション情報．役職コード≧チームリーダーの場合、以下をOR条件で追加
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.設定, "TEAM_LEADER_CD"))
					<= Integer.parseInt(userInfoData.getCd_literal())){

				strRetSql.append(" OR ");
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" T1.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' AND ");
				strRetSql.append(" T1.cd_kaisha in ( ");
				strRetSql.append(strSqlTanto);
				strRetSql.append(" ) ");
				strRetSql.append(" ) ");
			}
			
			strRetSql.append(" ) ");
		//自工場分
		} else if (strDataId.equals("4")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_kaisha = ");
			strRetSql.append(userInfoData.getCd_kaisha());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kojo = ");
			strRetSql.append(userInfoData.getCd_busho());
			strRetSql.append(" ) ");
		}

		//権限が「自工場分」以外の場合
		if (!strDataId.equals("4")){
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
			//現状、キユーピーの方のみシサクイックを使用する為、下記条件をコメントアウト
//			strRetSql.append(" AND");
//			strRetSql.append(" T1.cd_shain IN ");
//			strRetSql.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end
		}
		
	} catch (Exception e) {
		this.em.ThrowException(e, "権限チェックSQLの作成に失敗しました。");
	} finally {
	}	
		return strRetSql;
	}
	/**
	 * 試作No閲覧権限チェック処理SQL作成
	 *  : データ閲覧の権限がある場合、データを取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @return strRetSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_etr(RequestResponsKindBean reqData) 
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
			strRetSql.append(" T1.cd_group = " + userInfoData.getCd_group());
		} catch (Exception e) {
			this.em.ThrowException(e, "権限チェックSQLの作成に失敗しました。");
		} finally {
		}	
		return strRetSql;
	}
	
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
	/**
	 * 試作No閲覧権限チェック処理SQL作成
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
			this.em.ThrowException(e, "権限チェックSQLの作成に失敗しました。");
		} finally {
		}	
		return strRetSql;
	}
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end
}