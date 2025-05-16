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
 * QP@10181_No.42_49_72
 * 【SA930】 JWSリテラル検索（調味料１液タイプ　工程属性）ＤＢ処理の実装
 * 
 * @author TT.Nishigawa
 * @since 2011/05/30
 *
 */
public class SA930 extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public SA930() {
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
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;

		//リクエストデータ
		String strReqKinoId = null;
		String strReqTableNm = null;
		
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
			
			//SQL文の作成
			strSql.append(" SELECT ");
			strSql.append(" 	biko ");
			strSql.append(" FROM ");
			strSql.append(" 	ma_literal ");
			strSql.append(" WHERE ");
			strSql.append(" 	cd_category = 'K_kote_ptn' ");
			strSql.append(" 	and value1 = 1 ");
			
			//②：データベース検索を用い、リテラルマスタデータを取得する。
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//データが取得できなかった場合
			if(lstRecset.size() == 0){
				//リテラルマスタの設定が正しくない
				em.ThrowException(ExceptionKind.一般Exception, "E000408", "", "", "");
			}
			
			//備考レコード取得
			String biko = toString(lstRecset.get(0));
			
			//備考に何も設定されていない場合
			if(biko.length() == 0){
				//リテラルマスタの設定が正しくない
				em.ThrowException(ExceptionKind.一般Exception, "E000408", "", "", "");
			}
			
			//備考に設定されたリテラルコード取得
			String[] aryBiko = biko.split(",");
			
			//調味料１液タイプの工程属性取得用SQL生成
			strSql = new StringBuffer();
			//SQL文の作成
			strSql.append("SELECT cd_category, cd_literal, nm_literal,");
			strSql.append(" ISNULL(value1,0) as value1, ISNULL(value2,0) as value2,");
			strSql.append(" no_sort, ISNULL(cd_group,0) as cd_group");
			strSql.append(" ,biko");
			strSql.append(" FROM ma_literal");			
			strSql.append(" WHERE cd_category = 'K_kote'");
			strSql.append(" AND cd_ｌiteral IN( ");
			for(int i = 0; i < aryBiko.length; i++){
				if(i == 0){
					strSql.append("'" + aryBiko[i] + "'");
				}
				else{
					strSql.append(",'" + aryBiko[i] + "'");
				}
			}
			strSql.append(" )");
			strSql.append(" ORDER BY no_sort");
			
			//データベース検索を用い、リテラルマスタデータを取得する。
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
					resTable.addFieldVale("rec" + i, "biko", toString(items[7]));
	
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
