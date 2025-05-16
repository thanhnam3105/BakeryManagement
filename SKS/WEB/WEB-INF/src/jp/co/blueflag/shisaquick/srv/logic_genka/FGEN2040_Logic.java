package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 【QP@00342】
 * 担当者マスタメンテ（営業）　権限検索
 *  : 現在ステータス情報を取得する。
 *  機能ID：FGEN2040
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2040_Logic extends LogicBase{
	
	/**
	 * 担当者マスタメンテ（営業）　権限検索
	 * : インスタンス生成
	 */
	public FGEN2040_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 担当者マスタメンテ（営業）　権限検索
	 *  : 権限検索情報を取得する。
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
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//レスポンスデータの形成
			this.genkaKihonSetting(resKind, reqData);
			
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
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);
			
			//追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "担当者マスタメンテ（営業）　権限検索処理が失敗しました。");
			
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
	private StringBuffer createGenkaKihonSQL(RequestResponsKindBean reqData )
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL格納用
		StringBuffer strSql = new StringBuffer();
		String strUKinoId  = "";
		
		try {
			
			//SQL文の作成
			strSql.append(" SELECT cd_kengen  ");
			strSql.append("       ,nm_kengen  ");
			strSql.append(" FROM ma_kengen  ");
			
			//営業（一般）権限コード取得
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_IPPAN");
			//営業（本部権限）権限コード取得
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");
			//営業（システム管理者）権限コード取得
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_SYSTEM");
			
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
				}
			}
			
			//権限が「編集（一般）」の場合
			if(strUKinoId.equals("100")){
				//WHERE句設定
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				
			}
			//権限が「編集（仮登録ユーザ）」の場合
			else if(strUKinoId.equals("101")){
				//WHERE句設定
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				
			}
			//権限が「編集（本部権限）」の場合
			else if(strUKinoId.equals("102")){
				//リクエストからデータ抽出
				String id_user = reqData.getFieldVale(0, 0, "id_user");
				if(id_user.equals(userInfoData.getId_user())){
					//WHERE句設定
					strSql.append(" WHERE cd_kengen = " + strEigyoHonbu);
				}
				else{
					//WHERE句設定
					strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				}
			}
			//権限が「編集（システム管理者）」の場合
			else if(strUKinoId.equals("103")){
				//WHERE句設定
				strSql.append(" WHERE cd_kengen = " + strEigyoIppan);
				strSql.append(" OR cd_kengen = " + strEigyoHonbu);
				strSql.append(" OR cd_kengen = " + strEigyoSystem);
				
			}
			
			
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
			//営業（一般）権限コード取得
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_IPPAN");
			//営業（本部権限）権限コード取得
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");
			//営業（システム管理者）権限コード取得
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_SYSTEM");
			
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
				resTable.addFieldVale(i, "cd_kengen", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kengen", toString(items[1],""));
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));
				resTable.addFieldVale(i, "cd_kengen_ippan", strEigyoIppan);
				resTable.addFieldVale(i, "cd_kengen_honbu", strEigyoHonbu);
				resTable.addFieldVale(i, "cd_kengen_system", strEigyoSystem);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
