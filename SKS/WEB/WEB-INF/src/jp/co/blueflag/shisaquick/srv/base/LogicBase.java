package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ロジックベース
 *  : 業務ロジックの実装基底クラス
 * @author TT.furuta
 * @since  2009/03/25
 */
public class LogicBase extends ObjectBase{

	//レスポンスデータ
	protected RequestResponsKindBean responsData;
	//DBの検索を実施する
	protected SearchBaseDao searchDB;
	//DBの更新を実施する
	protected UpdateBaseDao execDB;
	//ユーザー情報管理
	protected UserInfoData userInfoData = null;
	
	/**
	 * ロジックベースコンストラクタ : インスタンス生成
	 */
	public LogicBase() {
		super();

	}
	
	/**
	 * ロジック実装
	 * ：派生先でオーバーライドして、業務ロジックの実装を記述する
	 * @param reqKind : 機能リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqKind
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//基底（LogicBase）のExecLogicが実行されました
		//ユーザー情報退避
		this.userInfoData = _userInfoData;
		
		return responsData;
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
		
		createSearchDB(DBCategory.DB1);
		
	}
	/**
	 * DBの検索の生成
	 * ：DBの検索ｸﾗｽを生成する
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createSearchDB(DBCategory DB_Category) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//DBセッションを生成
			searchDB = new SearchBaseDao(DB_Category);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB検索の生成に失敗しました。");
			
		} finally {
			
		}
	}
	/**
	 * DB更新の生成
	 * ：DB更新ｸﾗｽを生成する
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createExecDB() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		createExecDB(DBCategory.DB1);

	}

	/**
	 * DB更新の生成
	 * ：DB更新ｸﾗｽを生成する
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createExecDB(DBCategory DB_Category) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		try {
			//DBセッションを生成
			execDB = new UpdateBaseDao(DB_Category);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB管理の生成に失敗しました。");
			
		} finally {
			
		}

	}

}
