package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;


/**
 * 
 * データチェック
 *  : 各ロジック、DB処理実行前データチェックを行う。
 * @author TT.furuta
 * @since  2009/03/25
 */
public class DataCheck extends ObjectBase{

	//データベース検索
	protected SearchBaseDao searchBD;
	//ユーザー情報管理
	protected UserInfoData userInfoData = null;
	
	/**
	 * コンストラクタ
	 *  : データチェックコンストラクタ
	 * @param checkData : 処理データ
	 */
	public DataCheck() {
		super();

	}
	
	/**
	 * データチェック
	 *  : 各ロジック　・　DB操作実行前のデータチェックを行う。
	 * @param checkData : チェック対象データ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		//各クラスにてオーバーライドして使用

		//ユーザー情報退避
		userInfoData = _userInfoData;

	}
	/**
	 * DBの検索の生成
	 * ：DBの検索ｸﾗｽを生成する
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createSearchDB() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//DBセッションを生成
			searchBD = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB1);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB検索の生成に失敗しました。");
			
		} finally {
			
		}
	}
	
}
