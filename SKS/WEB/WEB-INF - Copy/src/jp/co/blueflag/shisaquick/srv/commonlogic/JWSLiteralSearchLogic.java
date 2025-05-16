package jp.co.blueflag.shisaquick.srv.commonlogic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * JWS用リテラル情報検索DB処理
 *  : JWS用リテラル情報情報を検索する。
 *  
 * @author TT.katayama
 * @since  2009/04/07
 * 
 */
public class JWSLiteralSearchLogic extends LogicBase{
	
	/**
	 * JWS用リテラル情報検索DB処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public JWSLiteralSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * リテラル情報取得ロジック実行
	 * @param reqData : 機能リクエストデータ
	 * @param strCd_Categori : カテゴリコード
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			, String strCd_Categori
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;

		//リクエストデータ
		String strReqKinoId = null;
		String strReqTableNm = null;
//		String strReqUserId = null;
		String strReqGamenId = null;
		//ユーザ情報
		String strGroupCd = null;
		String strDataId = null;
		String strKinoId = null;
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①：機能リクエストデータクラスを用い、リテラルマスタ情報を取得するSQLを作成する。
			
			//機能IDの設定
			strReqKinoId = reqData.getID();
			resKind.setID(strReqKinoId);

			//テーブル名の設定
			strReqTableNm = reqData.getTableID(0);
			resKind.addTableItem(strReqTableNm);
			
			//機能リクエストデータよりユーザIDと画面IDを取得
//			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//ユーザ情報のユーザIDを取得
//			strReqUserId = UserInfoData.getId_user();			
			//ユーザ情報のグループコードを取得
			strGroupCd = userInfoData.getCd_group();
			
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//機能IDを設定
					strKinoId = userInfoData.getId_kino().get(i).toString(); 
					//データIDを設定
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//画面ID及び機能IDの判定
			if ( strReqGamenId == null ) {
				//処理無し
			} else if ( strReqGamenId.equals("100") ) {
				//画面ID:試作データ画面(詳細)
				if ( !(strKinoId.equals("10") || strKinoId.equals("20") ) ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("110") ) {
				//画面ID:試作データ画面(新規)
				if ( !strKinoId.equals("30") ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("120") ) {
				//画面ID:試作データ画面(製法コピー)
				if ( !strKinoId.equals("20") ) {
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			}
			
			//SQL文の作成
			strSql.append("SELECT cd_category, cd_literal, nm_literal,");
			strSql.append(" ISNULL(value1,0) as value1, ISNULL(value2,0) as value2,");
			strSql.append(" no_sort, ISNULL(cd_group,0) as cd_group");
			
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			strSql.append(" ,biko");
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			strSql.append(" FROM ma_literal");			
			//引数　カテゴリコードを検索条件に設定
			strSql.append(" WHERE cd_category = '" + strCd_Categori + "'");
			strSql.append(" AND ( cd_group IS NULL ");
			
			//権限判定
			if ( strDataId == null ) {
				
				//処理無し
				
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start	
			//} else if ( strDataId.equals("1") ) {
			} else if ( strDataId.equals("1") || strDataId.equals("5") ) {
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
				
				//データID = 1 : 同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("2") ) {
				
				//データID = 2 : 同一グループ且つ担当会社
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("3") ) {
				
				//データID = 3 : 同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
				strSql.append(" OR cd_group = '" + strGroupCd + "'");
				
			} else if ( strDataId.equals("4") ) {
				
				//データID = 4 : 自工場分
				//処理無し
				
			} else if ( strDataId.equals("9") ) {
				//データID = 9 : 全て
				strSql.append(" OR cd_group IS NOT NULL");
				
			}
			strSql.append(" ) ");
			strSql.append(" ORDER BY no_sort");
			
			//②：データベース検索を用い、リテラルマスタデータを取得する。
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//③：リテラルパラメーター格納メソッドを呼出し、機能レスポンスデータを形成する。
			storageLiteralInfo(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JWS用リテラル情報取得処理が失敗しました。");
			
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
		//④：機能レスポンスデータを返却する。
		return resKind;
	}

	/**
	 * リテラルパラメーター格納
	 *  : リテラルマスタ情報格納
	 * @param lstSearchData : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void storageLiteralInfo(List<?> lstSearchData, RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//①：引数　検索結果情報リストに保持している各パラメーターを機能レスポンスデータへ格納する。
		try {

			if ( lstSearchData.size() != 0 ) {
				for (int i = 0; i < lstSearchData.size(); i++) {

					//処理結果の格納
					resTable.addFieldVale("rec" + i, "flg_return", "true");
					resTable.addFieldVale("rec" + i, "msg_error", "");
					resTable.addFieldVale("rec" + i, "no_errmsg", "");
					resTable.addFieldVale("rec" + i, "nm_class", "");
					resTable.addFieldVale("rec" + i, "cd_error", "");
					resTable.addFieldVale("rec" + i, "msg_system", "");
	
					Object[] items = (Object[]) lstSearchData.get(i);
	
					//結果をレスポンスデータに格納
					resTable.addFieldVale("rec" + i, "cd_category", items[0].toString());
					resTable.addFieldVale("rec" + i, "cd_literal", items[1].toString());
					resTable.addFieldVale("rec" + i, "nm_literal", items[2].toString());
					resTable.addFieldVale("rec" + i, "value1", items[3].toString());
					resTable.addFieldVale("rec" + i, "value2", items[4].toString());
					resTable.addFieldVale("rec" + i, "no_sort", items[5].toString());
					resTable.addFieldVale("rec" + i, "cd_group", items[6].toString());
					
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					resTable.addFieldVale("rec" + i, "biko", toString(items[7]));
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	
				}
			} else {

				//処理結果の格納
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec0", "cd_category", "");
				resTable.addFieldVale("rec0", "cd_literal", "");
				resTable.addFieldVale("rec0", "nm_literal", "");
				resTable.addFieldVale("rec0", "value1", "");
				resTable.addFieldVale("rec0", "value2", "");
				resTable.addFieldVale("rec0", "no_sort", "");
				resTable.addFieldVale("rec0", "cd_group", "");
				resTable.addFieldVale("rec0", "biko", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルマスタ検索結果格納処理が失敗しました。");

		} finally {

		}

	}
	
}
