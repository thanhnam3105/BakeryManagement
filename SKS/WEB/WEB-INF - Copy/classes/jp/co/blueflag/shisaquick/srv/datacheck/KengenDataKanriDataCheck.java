package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * 
 * 権限マスタメンテ ： 権限データ管理のデータチェックを行う。
 * 
 * @author itou
 * @since 2009/04/20
 */
public class KengenDataKanriDataCheck extends DataCheck{
	
	/**
	 * 権限マスタメンテ ： 権限データ管理データチェック処理用コンストラクタ : インスタンス生成
	 */
	public KengenDataKanriDataCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 権限マスタメンテ ： 権限データ管理のデータチェックを行う。
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			String strShotriKbn = null;

			// 処理区分の取得
			strShotriKbn = reqData.getFieldVale("ma_kengen", 0, "kbn_shori");
			// 権限コードの取得
			String strKengenCd = reqData.getFieldVale("ma_kengen", 0, "cd_kengen");
			if (!(strKengenCd.equals(""))) {
				// SQL文の作成
				strSql = kengenDataKanriExistenceCheckSQL(reqData, strSql);
				
				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			// 登録の場合
			if (strShotriKbn.equals("1")) {
				// 権限コードが入力されている場合
				if (!strKengenCd.equals("")) {
					// データが存在する場合
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.一般Exception,"E000302", "権限コード", strKengenCd.toString(), "");
					}
				}
				
			// 更新・削除の場合
			} else {
				// データが存在しない場合
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.一般Exception,"E000301", "権限コード", strKengenCd.toString(), "");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "グループ管理データチェック処理に失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchBD != null) {
				//セッションのクローズ
				searchBD.Close();
				searchBD = null;
			}
			
			//変数の削除
			strSql = null;
		}
		
	}
	
	/**
	 * 権限コード存在チェックSQL作成
	 *  : 権限の存在チェックをするSQLを作成。
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer kengenDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			// 権限コードの取得
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");

			//SQL文の作成
			strSql.append("SELECT cd_kengen");
			strSql.append(" FROM ma_kengen");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
