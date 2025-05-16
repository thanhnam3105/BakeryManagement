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
 * 担当者マスタメンテ（営業）　会社検索（営業部署のみ）
 *  : 現在ステータス情報を取得する。
 *  機能ID：FGEN2050
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2050_Logic extends LogicBase{
	
	/**
	 * 担当者マスタメンテ（営業）　会社検索（営業部署のみ）
	 * : インスタンス生成
	 */
	public FGEN2050_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 担当者マスタメンテ（営業）　会社検索（営業部署のみ）
	 *  : 会社検索（営業部署のみ）情報を取得する。
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
			// ADD 2013/11/7 QP@30154 okano start
			//機能リクエストデータよりユーザIDと画面IDを取得
			String GamenId = "200";
			String KinoId = null;
			
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(GamenId)){
					//機能IDを設定
					KinoId = userInfoData.getId_kino().get(i).toString();
				}
			}
			// ADD 2013/11/7 QP@30154 okano end
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//レスポンスデータの形成
			// MOD 2013/11/7 QP@30154 okano start
//				this.genkaKihonSetting(resKind, reqData);
			this.genkaKihonSetting(resKind, reqData, KinoId, userInfoData);
			// MOD 2013/11/7 QP@30154 okano end
			
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
			// ADD 2013/11/7 QP@30154 okano start
			,String KinoId
			,UserInfoData userInfoData
			// ADD 2013/11/7 QP@30154 okano end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//レコード値格納リスト
		List<?> lstRecset = null;	
		
		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {
			//テーブル名
			String strTblNm = "table";	
			
			//データ取得SQL作成
			// MOD 2013/11/7 QP@30154 okano start
//				strSqlBuf = this.createGenkaKihonSQL();
			strSqlBuf = this.createGenkaKihonSQL(KinoId, userInfoData);
			// MOD 2013/11/7 QP@30154 okano end
			
			//共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者マスタメンテ（営業）　会社検索（営業部署のみ）処理が失敗しました。");
			
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
	// MOD 2013/11/7 QP@30154 okano start
//		private StringBuffer createGenkaKihonSQL()
	private StringBuffer createGenkaKihonSQL(String KinoId, UserInfoData userInfoData)
	// MOD 2013/11/7 QP@30154 okano send
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL文の作成
			strSql.append(" SELECT DISTINCT  ");
			strSql.append(" 	   cd_kaisha  ");
			strSql.append("       ,nm_kaisha  ");
			strSql.append(" FROM ma_busho  ");
			strSql.append(" where flg_eigyo=1  ");
			// ADD 2013/11/7 QP@30154 okano start
			if(KinoId.equals("100")){
				strSql.append(" and cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());
				
			} else if(KinoId.equals("102")){
				strSql.append(" and cd_kaisha = ");
				strSql.append(userInfoData.getCd_kaisha());
				
			} else {
				
			}
			// ADD 2013/11/7 QP@30154 okano end
			
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
	 *  : レスポンスデータへ格納する。
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
				resTable.addFieldVale(i, "cd_kaisha", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kaisha", toString(items[1],""));
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
