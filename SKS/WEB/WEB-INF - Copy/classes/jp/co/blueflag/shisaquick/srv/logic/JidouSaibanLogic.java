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
 * 自動採番処理の実装
 *  : 自動採番処理のＤＢに対する業務ロジックの実装
 *  
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class JidouSaibanLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public JidouSaibanLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 自動採番データ管理
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

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String strReqShoriKbn = "";					//リクエストデータ : 処理区分
			String strNewCode = "";						//新規発行コード
			
			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(reqData.getTableID(0));
			
			// 機能リクエストデータの取得
			strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			
			//①：リクエストデータより、処理区分を抽出し、処理を振り分ける。
			
			if ( strReqShoriKbn.equals("cd_shisaku") ) {
				//処理区分：試作コード時(cd_shisaku)
				
				//試作コード用採番検索処理メソッドを呼出し、新規発行コードを取得する
				strNewCode = this.shisakuSelect();
				//新規発行コードを用いて、試作コード用採番更新処理メソッドにて自動採番処理を行う。
				this.shisakuUpdate(strNewCode);
				
			} else if ( strReqShoriKbn.equals("cd_genryo") ) {
				//処理区分：原料コード時(cd_genryo)

				//原料コード用採番検索処理メソッドを呼出し、新規発行コードを取得する
				strNewCode = this.genryoSelect();
				//新規発行コードを用いて、原料コード用採番更新処理メソッドにて自動採番処理を行う。
				this.genryoUpdate(strNewCode);
				
			}
			//②：①にて返却された新規発行コードを用い、自動採番データパラメーター格納を呼出し、リクエストデータを形成する。
			this.storageJidouSaibanData(strNewCode, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "自動採番データ管理処理が失敗しました。");
		
		} finally {
	
		}
		return resKind;

	}

	/**
	 * 原料コード用採番検索処理
	 *  : 採番マスタより、原料コード用の新規発行コードを取得する。
	 * @return 新規発行コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String genryoSelect() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetNewCode = "";
		
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;
		
		try {
			//①：下記条件を用いて、採番マスタ（ma_saiban）より、新規発行コード検索用SQLを作成する。

			strSQL.append(" SELECT MAX(M601.no_seq)+1 AS no_seq_max ");
			strSQL.append(" FROM ma_saiban M601 ");
			//採番マスタ.キー1 = 原料コード
			//採番マスタ.キー2 = N
			strSQL.append(" WHERE M601.key1='原料コード' AND M601.key2='N' ");
			
			//②：作成したSQLを用いて、検索処理を行い、新規発行コードを取得する。
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			if ( lstSearchAry.size() >= 0 ) {
				//新規発行コード　：　採番マスタ.採番の最大値+1
				strRetNewCode = lstSearchAry.get(0).toString();
				
			} else {
				//検索結果が存在しなかった場合
				strRetNewCode = "1";		//新規発行コードに1を格納する
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原料コード用採番検索処理が失敗しました。");
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
		
		//③:取得した新規発行コードを返却する。
		return strRetNewCode;
	}

	/**
	 * 試作コード用採番検索処理
	 *  : 試作採番マスタより、試作コード用の新規発行コードを取得する。
	 * @return 新規発行コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String shisakuSelect() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetNewCode = "";
		StringBuffer strSQL = new StringBuffer();
		List<?> lstSearchAry = null;
		
		try {
			String strUserId = userInfoData.getId_user();		//ユーザ情報.ユーザID
			
			//①：下記条件を用いて、試作採番マスタ（ma_shisakusaiban）より、新規発行試作コード検索用SQLを作成する。
			
			strSQL.append(" SELECT  ");
			strSQL.append("   RIGHT(YEAR(GETDATE()),2) AS nen ");
			strSQL.append("  ,ISNULL(MAX(M602.no_oi)+1,1) AS no_oi ");
			strSQL.append(" FROM ma_shisaku_saiban M602 ");
			//試作採番マスタ.試作CD-社員コード =  ユーザ情報.ユーザID
			strSQL.append(" WHERE M602.cd_shain =" + strUserId);
			//試作採番マスタ.試作CD-年 = システム.年の下二桁
			strSQL.append("   AND M602.nen = RIGHT(YEAR(GETDATE()),2) ");
			
			//②：作成したSQLを用いて、検索処理を行い、新規発行コードを取得する。
			super.createSearchDB();
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());
			
			//新規発行コード　：　"ユーザ情報.ユーザID" + "-" + "システム.年数の下二桁" + "-" + "試作採番マスタ.追番の最大値+1"
			if ( lstSearchAry.size() >= 0 ) {
				Object[] items = (Object[]) lstSearchAry.get(0);
				strRetNewCode += strUserId + "-";
				strRetNewCode += items[0].toString() + "-";
				strRetNewCode += items[1].toString();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード用採番検索処理が失敗しました。");
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

		//③:取得した新規発行コードを返却する。
		return strRetNewCode;
	}

	/**
	 * 原料コード用採番更新処理
	 *  : 原料コード用の採番マスタデータの更新処理を行う。
	 * @param strNewCode : 新規発行コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoUpdate(String strNewCode) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();

		try {
			String strUserId = "";							//ユーザ情報 : ユーザID
			
			//ユーザ情報のユーザIDを取得
			strUserId = userInfoData.getId_user();
			
			//①：下記条件を用いて、採番マスタ（ma_saiban）.採番の自動採番用SQLを作成する。

			strSQL.append(" UPDATE ma_saiban ");
			strSQL.append("   SET no_seq =" + strNewCode);
			strSQL.append("      ,id_koshin =" + strUserId);
			strSQL.append("      ,dt_koshin = GETDATE() ");
			//採番マスタ.キー1 = 原料コード
			//採番マスタ.キー2 = N
			strSQL.append(" WHERE key1='原料コード' AND key2='N' ");
			
			//②：作成したSQLを用いて、更新処理を行う。
			super.createExecDB();							//DB更新の生成
			this.execDB.BeginTran();						//トランザクション開始
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL実行
				this.execDB.Commit();							//コミット

			}catch(Exception e){
				this.execDB.Rollback();							//ロールバック
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原料コード用採番更新処理が失敗しました。");
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
	 * 試作コード用採番更新処理
	 *  : 試作採番マスタにデータが存在しない場合は、登録処理を行う。
	 *   存在する場合は、更新処理を行い、自動採番処理を行う。
	 * @param strNewCode : 新規発行コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuUpdate(String strNewCode) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSQL = new StringBuffer();
		
		try {
			//①：引数.新規発行コードを「-」ごとの文字列に分割し、社員コード・年・追番を取得する
			String strShainCd = strNewCode.split("-")[0];
			String strNen = strNewCode.split("-")[1];
			String strOiNo = strNewCode.split("-")[2];  
			
			//②:①の追番の値により、処理を振り分ける
			if ( strOiNo.equals("1") ) {
				//③：試作採番マスタ（ma_shisakusaiban）の登録処理を行う

				strSQL.append(" INSERT INTO ma_shisaku_saiban ");
				strSQL.append("  (cd_shain, nen, no_oi, id_toroku, dt_toroku, id_koshin, dt_koshin) ");
				strSQL.append("  VALUES ( ");
				strSQL.append(" " + strShainCd);
				strSQL.append(" ," + strNen);
				strSQL.append(" ," + strOiNo);
				strSQL.append("  ," + strShainCd + " ,GETDATE() ");		//更新者
				strSQL.append("  ," + strShainCd + " ,GETDATE() )");		//登録者
			
			} else {
				//④：試作採番マスタ（ma_shisakusaiban）.追番の更新処理を行う

				strSQL.append(" UPDATE ma_shisaku_saiban ");
				strSQL.append("   SET no_oi =" + strOiNo);
				strSQL.append("      ,id_koshin =" + strShainCd);
				strSQL.append("      ,dt_koshin = GETDATE() ");
				strSQL.append(" WHERE cd_shain =" + strShainCd);
				strSQL.append("   AND nen =" + strNen);
				
			}
			
			//⑤:作成したSQLを用いて、登録・更新処理を行う。
			super.createExecDB();							//DB更新の生成
			this.execDB.BeginTran();						//トランザクション開始
			
			try{
				this.execDB.execSQL(strSQL.toString());		//SQL実行
				this.execDB.Commit();							//コミット

			}catch(Exception e){
				this.execDB.Rollback();							//ロールバック
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード用採番更新処理が失敗しました。");
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
	 * 自動採番データパラメーター格納
	 *  : 自動採番データ情報をレスポンスデータへ格納する。
	 * @param strNewCode : 新規発行コード
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void storageJidouSaibanData(String strNewCode, RequestResponsTableBean resTable)
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
			resTable.addFieldVale(0, "new_code", strNewCode );
			
		} catch (Exception e) {
			this.em.ThrowException(e, "自動採番データパラメーター格納処理が失敗しました。");

		} finally {

		}
		
	}
		
}
