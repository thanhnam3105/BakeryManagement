package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 排他制御ＤＢ処理の実装
 *  : 排他制御処理のＤＢに対する業務ロジックの実装
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class HaitaSeigyoLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public HaitaSeigyoLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 排他制御ＤＢ処理ロジック管理
	 * @param reqData : 機能リクエストデータ
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

		String[] strReqShisakuCd = new String[3];		//リクエストデータ : 試作コード(社員コード, 年, 追番)
		String[] strResParam = new String[2];			//レスポンスパラメータ

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {

			String strReqZikoKbn = "";							//リクエストデータ : 実行区分
			String strReqHaitaKbn = "";							//リクエストデータ : 排他区分
			String strReqUserId = "";							//リクエストデータ : ユーザID
			
			String strHaitaKbn = "";								//試作品テーブル.排他区分
			strResParam[0] = "";
			strResParam[1] = "";

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));
			
			// 機能リクエストデータの取得
			strReqZikoKbn = reqData.getFieldVale(0, 0, "kubun_ziko");
			strReqHaitaKbn = reqData.getFieldVale(0, 0, "kubun_haita");
//			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqShisakuCd[0] = reqData.getFieldVale(0, 0, "cd_shain");
			strReqShisakuCd[1] = reqData.getFieldVale(0, 0, "nen");
			strReqShisakuCd[2] = reqData.getFieldVale(0, 0, "no_oi");
			
			//ユーザ情報のユーザIDを取得
			strReqUserId = userInfoData.getId_user();

			//①:機能リクエストデータの実行区分により、処理を行うかどうかを判定する (0：処理を行わない、1：処理する)
			if ( strReqZikoKbn.equals("0") ) {
				//処理を行わない
				
			} else if ( strReqZikoKbn.equals("1") ) {				
				//②:機能リクエストデータにより、排他制御検索SQL作成処理を呼び出し、試作品テーブル.排他区分を取得する
				strHaitaKbn = this.haitaSearchSQL(strReqShisakuCd);
				
				//③:機能リクエストデータの排他区分により、排他設定/排他解除処理を制御する
				if ( strReqHaitaKbn.equals("1") ) {
					//1:排他設定 ・・・ 排他制御設定処理を呼び出し、排他制御の設定を行う
					strResParam = this.haitaSet(strReqUserId, strReqShisakuCd, strHaitaKbn);
					
				} else if ( strReqHaitaKbn.equals("0") ) {
					//0:排他制御 ・・・ 排他制御解除処理を呼び出し、排他制御の解除を行う	
					strResParam = this.haitaKaijo(strReqUserId, strReqShisakuCd, strHaitaKbn);
					
				}	
			}

			//④:排他制御データパラメーター格納処理を呼び出し、レスポンスデータに設定する
			this.storageHaitaData(strResParam[0], strResParam[1], resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御ＤＢ処理ロジック管理処理が失敗しました。");

		} finally {
			//変数の削除
			strReqShisakuCd = null;
			strResParam = null;

		}
		return resKind;

	}

	/**
	 * 排他制御検索SQL作成
	 *  : 排他制御検索SQL作成 
	 * @param strShisakuCd : 試作コード
	 * @return : 試作品コード.排他区分
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String haitaSearchSQL(String[] strShisakuCd) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetHaitaKbn = "";
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;

		try {
			
			//①：引数より排他制御検索条件を抽出し、試作品テーブル.排他区分を取得するSQLを作成する。
			strSQL.append(" SELECT ");
			strSQL.append("     ISNULL(CONVERT(VARCHAR,T110.kbn_haita),'') AS kbn_haita ");
			strSQL.append(" FROM ");
			strSQL.append("     tr_shisakuhin AS T110 ");
			strSQL.append(" WHERE  ");
			strSQL.append("   T110.cd_shain=" + strShisakuCd[0]);
			strSQL.append("   AND T110.nen=" + strShisakuCd[1]);
			strSQL.append("   AND T110.no_oi=" + strShisakuCd[2]);
			
			//②：試作品テーブル検索を行い、試作品テーブル.排他区分を取得する
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());
			if ( lstSearchAry.size() >= 0 ) {
				strRetHaitaKbn = lstSearchAry.get(0).toString();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御検索SQL作成処理が失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstSearchAry);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			
			//変数の削除
			strSQL = null;
		}
		
		//③：試作品テーブル.排他区分を返却する。
		return strRetHaitaKbn;
	}

	/**
	 * 排他制御更新SQL作成
	 *  : 排他制御更新SQL作成
	 * @param strShisakuCd : 試作コード
	 * @param strHaitaKbn : 試作品コード.排他区分
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haitaUpdateSQL(String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();

		try {
			
			//①：引数のデータを用い、排他データ更新用SQLを作成する。
			strSQL.append(" UPDATE tr_shisakuhin ");
			strSQL.append("   SET kbn_haita=" + strHaitaKbn);
			strSQL.append(" WHERE cd_shain=" + strShisakuCd[0]);
			strSQL.append("   AND nen=" + strShisakuCd[1]);
			strSQL.append("   AND no_oi=" + strShisakuCd[2]);
			
			//②：データベース管理を用い、試作品テーブル.排他区分の更新を行う。
			super.createExecDB();							//DB更新の生成
			this.execDB.BeginTran();						//トランザクション開始
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL実行
				this.execDB.Commit();							//コミット

			}catch(Exception e){
				this.execDB.Rollback();							//ロールバック
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御更新SQL作成処理が失敗しました。");
		} finally {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}
			
			//変数の削除
			strSQL = null;
		}
		
	}
	
	/**
	 * 排他制御設定
	 *  : 排他制御を設定し、レスポンスデータに排他制御パラメータを格納する。
	 * @param strUserId : ユーザID
	 * @param strShisakuCd : 試作コード
	 * @param strHaitaKbn : 試作品コード.排他区分
	 * @return レスポンスパラメータ (0:排他結果, 1:ユーザID)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String[] haitaSet(String strUserId, String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String[] strRetParam = new String[2];
		strRetParam[0] = "";
		strRetParam[1] = "";

		try {
			
			//①：試作品テーブル.排他区分による判定（※試作品テーブル.排他区分にはユーザIDが格納されている）
			if ( strHaitaKbn.equals("") ) {
				//■　引数 試作品テーブル.排他区分がNULLの場合
				
				//排他制御更新SQL作成処理を呼出し、試作品テーブル.排他区分に機能リクエストデータのユーザIDを設定
				this.haitaUpdateSQL(strShisakuCd, strUserId);
				//レスポンスデータの排他結果にtrueを設定
				strRetParam[0] = "true";
				//レスポンスデータのユーザIDにリクエストデータのユーザIDを設定
				strRetParam[1] = strUserId;
				
			} else if ( strHaitaKbn.equals(strUserId) ) {
				//■　引数 試作品テーブル.排他区分がユーザIDと同一の場合

//				//レスポンスデータの排他結果にtrueを設定
				strRetParam[0] = "true";
				//レスポンスデータの排他結果にfalseを設定
//				strRetParam[0] = "false";
				//レスポンスデータのユーザIDにリクエストデータのユーザIDを設定
				strRetParam[1] = strUserId;
				
			} else if ( !strHaitaKbn.equals(strUserId) ) {
				//■　引数 試作品テーブル.排他区分がユーザIDと異なる場合

				//レスポンスデータの排他結果にfalseを設定
				strRetParam[0] = "false";
				//レスポンスデータのユーザIDに引数 試作品テーブル.排他区分を設定
				strRetParam[1] = strHaitaKbn;
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御設定処理が失敗しました。");
		} finally {
		}
		
		return strRetParam;
	}
	
	/**
	 * 排他制御解除
	 *  : 排他制御を解除し、 レスポンスデータに排他制御パラメータを格納する。
	 * @param strUserId : ユーザID
	 * @param strShisakuCd : 試作コード
	 * @param strHaitaKbn : 試作品コード.排他区分
	 * @return レスポンスパラメータ (0:排他結果, 1:ユーザID)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String[] haitaKaijo(String strUserId, String[] strShisakuCd, String strHaitaKbn ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String[] strRetParam = new String[2];
		strRetParam[0] = "";
		strRetParam[1] = "";

		try {
			
			//①：試作品テーブル.排他区分による判定
			if ( strHaitaKbn.equals(strUserId) ) {
				//■　引数 試作品テーブル.排他区分がユーザIDと同一の場合
				
				//排他制御更新SQL作成処理を呼出し、試作品テーブル.排他区分にNULLを設定
				this.haitaUpdateSQL(strShisakuCd, "NULL");
				//レスポンスデータの排他結果にtrueを設定
				strRetParam[0] = "true";
				//レスポンスデータのユーザIDに0を設定
				strRetParam[1] = "0";
				
			} else {
				//■　引数 試作品テーブル.排他区分がユーザIDと異なる場合。または、値が空の場合
				
				//処理を行わない。
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御解除処理が失敗しました。");
		} finally {
		}
		
		return strRetParam;		
	}
	
	/**
	 * 排他制御データパラメーター格納
	 *  : 排他制御データ情報をレスポンスデータへ格納する。 
	 * @param strHaitaKekka : 排他結果
	 * @param strUserId : ユーザID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageHaitaData(String strHaitaKekka, String strUserId, RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
			//結果をレスポンスデータに格納
			resTable.addFieldVale(0, "kekka_haita", strHaitaKekka);
			resTable.addFieldVale(0, "id_user", strUserId);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "排他制御データパラメーター格納処理が失敗しました。");

		} finally {

		}
		
	}
	
}
