package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

import java.util.List;

/**
 * 
 * 工場検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）ＤＢ処理 : ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用　工場検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）データ抽出SQLを作成する。
 * M104_bushoテーブルから、データの抽出を行う。
 * 取得情報を、レスポンスデータ保持「機能ID：SA180O」に設定する。
 * @author itou
 * @since 2009/04/06
 */
public class KoujouSearchLogic extends LogicBase {

	/**
	 * 工場検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）コンストラクタ : インスタンス生成
	 */
	public KoujouSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：工場情報取得SQL作成 M104_bushoテーブルから、データの抽出を行う。
	 * 
	 * @param reqData
	 *            : リクエストデータ
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

			String kaishaCd = "";
			String UserId = "";
			String GamenId = "";
			String DataId = "";
			
			//会社コードの取得
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//ユーザIDの取得
			UserId = reqData.getFieldVale(0, 0, "id_user");
			//画面IDの取得
			GamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(GamenId)){
					//データIDを設定
					DataId = userInfoData.getId_data().get(i).toString();
				}
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
			//strSql.append("SELECT cd_kaisha,cd_busho,nm_busho");
			strSql.append("SELECT cd_kaisha,cd_busho");
			strSql.append(",CASE cd_kaisha WHEN " + ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA") + " THEN");
			strSql.append(" 	CASE cd_busho WHEN " + ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KOJO") + " THEN '新規登録原料'");
			strSql.append(" 	ELSE nm_busho");
			strSql.append("  	END");
			strSql.append(" ELSE nm_busho");
			strSql.append(" END AS nm_busho");
//mod end   -------------------------------------------------------------------------------
			
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");

			//原料分析情報（JSP）
			if ( GamenId.equals("20") || GamenId.equals("25") ) {
				if (DataId.equals("")) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("25")){
							//データIDを設定
							DataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				if ( DataId.equals("1") ) {
					//担当会社
					//会社コード取得
				    // MOD 2013/11/6 QP@30154 okano start
//						if (kaishaCd.equals("")) {
//							for (int i = 0; i < userInfoData.getCd_tantokaisha().size(); i++) {
//								if (userInfoData.getCd_tantokaisha().get(i).toString().equals(userInfoData.getCd_kaisha().toString())){
//									kaishaCd = userInfoData.getCd_tantokaisha().get(i).toString();
//									break;
//								}
//							}
//						}
//						if (kaishaCd.equals("")) {
//							kaishaCd = userInfoData.getCd_tantokaisha().get(0).toString();
//						}
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
				    // MOD 2013/11/6 QP@30154 okano end
					strSql.append(kaishaCd);
					
				} else if ( DataId.equals("2") ) {
					//自工場分
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
					strSql.append(kaishaCd);
					strSql.append(" AND cd_busho = ");
					strSql.append(userInfoData.getCd_busho());
					
				} else if ( DataId.equals("9") ) {
					//全て
					if (kaishaCd.equals("")) {
						kaishaCd = userInfoData.getCd_kaisha();
					}
					strSql.append(kaishaCd);
				}
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
			strSql.append("  AND fg_hyoji = 1");
//mod end   -------------------------------------------------------------------------------
			
			strSql.append(" ORDER BY cd_kaisha,cd_busho");
			
			// SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageKoujouCmb(lstRecset, resKind.getTableItem(0));

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
	 * コンボ用：会社パラメーター格納 : 会社コンボボックス情報をレスポンスデータへ格納する。
	 * 取得情報を、レスポンスデータ保持「機能ID：SA140O」に設定する。
	 * 
	 * @param lstGroupCmb
	 *            : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKoujouCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_busho", items[1].toString());
				resTable.addFieldVale(i, "nm_busho", items[2].toString());

			}

			if (lstGroupCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "nm_busho", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}

	}
	
}
