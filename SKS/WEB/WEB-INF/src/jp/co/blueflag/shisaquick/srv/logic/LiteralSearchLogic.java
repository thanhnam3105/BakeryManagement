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
 * コンボ用：リテラル情報検索DB処理
 *  : コンボ用：リテラル情報を検索する。
 * @author jinbo
 * @since  2009/04/07
 */
public class LiteralSearchLogic extends LogicBase{
	
	/**
	 * コンボ用：カテゴリ情報検索DB処理用コンストラクタ 
	 * : インスタンス生成
	 */
	public LiteralSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：リテラル情報取得SQL作成
	 *  : リテラルコンボボックス情報を取得するSQLを作成。
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
			strSql = createSQL(reqData, strSql);
			
			//SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageLiteralCmb(lstRecset, resKind.getTableItem(0));
			
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
			strSql = null;

		}
		return resKind;
	}
	
	/**
	 * リテラル取得SQL作成
	 *  : リテラルを取得するSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			String categoryCd = null;
			String userId = null;
			String gamenId = null;
			// ADD 2013/11/8 QP@30154 okano start
			String kinoId = null;
			// ADD 2013/11/8 QP@30154 okano end
			String dataId = null;

			//カテゴリコードの取得
			categoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//ユーザIDの取得
			userId = reqData.getFieldVale(0, 0, "id_user");
			//画面IDの取得
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					// ADD 2013/11/8 QP@30154 okano start
					//機能IDを設定
					kinoId = userInfoData.getId_kino().get(i).toString();
					// ADD 2013/11/8 QP@30154 okano end
					//データIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成
			strSql.append("SELECT cd_literal, nm_literal");
			strSql.append(" FROM ma_literal ");
			// DEL 2013/11/8 QP@30154 okano start
//				strSql.append(" WHERE cd_category = '");
//				strSql.append(categoryCd);
//				strSql.append("' ");
			// DEL 2013/11/8 QP@30154 okano end

			//リテラルマスタメンテナンス画面
			if (gamenId.equals("60") || gamenId.equals("65")) {
				if (dataId == null) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							// ADD 2013/11/8 QP@30154 okano start
							//機能IDを設定
							kinoId = userInfoData.getId_kino().get(i).toString();
							// ADD 2013/11/8 QP@30154 okano end
							//データIDを設定
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				
				// ADD 2013/11/8 QP@30154 okano start
				if(kinoId.equals("10") || kinoId.equals("20")){
				// ADD 2013/11/8 QP@30154 okano end
				if(dataId.equals("1")) {	//同一グループ
					// ADD 2013/11/8 QP@30154 okano start
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
					// ADD 2013/11/8 QP@30154 okano end
					strSql.append(" AND (cd_group IS NULL");
					strSql.append(" OR cd_group = ");
					strSql.append(userInfoData.getCd_group());
					strSql.append(" ) ");
				} else if (dataId.equals("9")) {	//全て
					//処理なし
					// ADD 2013/11/8 QP@30154 okano start
					strSql.append(" LEFT JOIN ma_group M1 ");
					strSql.append(" ON M1.cd_group = ma_literal.cd_group ");
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
					strSql.append(" AND (ma_literal.cd_group IS NULL");
					strSql.append(" OR M1.cd_kaisha = ");
					strSql.append(userInfoData.getCd_kaisha());
					strSql.append(" ) ");
					// ADD 2013/11/8 QP@30154 okano start
				}
				// ADD 2013/11/8 QP@30154 okano start
				} else {	//システム管理者
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
				}
				// ADD 2013/11/8 QP@30154 okano end
			}
			strSql.append(" ORDER BY no_sort");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * コンボ用：リテラルパラメーター格納
	 *  : リテラルコンボボックス情報をレスポンスデータへ格納する。
	 * @param lstLiteralCmb : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageLiteralCmb(List<?> lstLiteralCmb, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstLiteralCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstLiteralCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());

			}

			if (lstLiteralCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_literal", "");
				resTable.addFieldVale(0, "nm_literal", "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	
}
