package jp.co.blueflag.shisaquick.srv.base;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 
 * Class名　: データベース検索
 * 処理概要 : DBの検索を行う。
 * @author  TT.Furuta
 * @since   2009/03/25
 */
public class SearchBaseDao extends BaseDao {

	//hibernateセッション
	private Session session;
	//hibernateトランザクション
	private Transaction transaction;
	
	/**
	 * コンストラクタ
	 * ：ハイバネートのセッションを生成する。
	 * @param DB_Category : 接続するDBの種類
	 */
	public SearchBaseDao(DBCategory DB_Category) {
		
		super(DB_Category);
		
	    session = sessionFactory.openSession();

	}
	
	/**
	 * データベース検索
	 *  : データベースへの検索処理を行う。
	 * 
	 * @param  session  DBコネクション情報
	 * @param  strSQL   SQLクエリ
	 * @return 検索結果リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * @throws ExceptionBase 
	 */
	public List<?> dbSearch(String strSQL) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		List<?> lstSearchKekka = null;
		
		try{
			
			//SQL生成
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL検索実行
			lstSearchKekka = sqlQuery.list();
			//検索結果がなかった場合
			if (null == lstSearchKekka){
				//該当データなしをスローする
				em.ThrowException(ExceptionKind.警告Exception, "W000401", strSQL, "", "");
			}
		}catch (Exception e){
			em.ThrowException(e, "SQLの実行に失敗しました:" + strSQL);
		}finally{
			
		}
		
		return lstSearchKekka;

	}
	/**【QP@00342】
	 * データベース検索
	 *  : データベースへの検索処理を行う。
	 * 
	 * @param  session  DBコネクション情報
	 * @param  strSQL   SQLクエリ
	 * @return 検索結果リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * @throws ExceptionBase 
	 */
	public List<?> dbSearch_notError(String strSQL) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		List<?> lstSearchKekka = null;
		
		try{
			
			//SQL生成
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL検索実行
			lstSearchKekka = sqlQuery.list();
			
		}catch (Exception e){
			em.ThrowException(e, "SQLの実行に失敗しました:" + strSQL);
		}finally{
			
		}
		
		return lstSearchKekka;

	}
	/**
	 * トランザクションの開始
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void BeginTran() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction = session.beginTransaction();
		}catch (Exception e){
			em.ThrowException(e, "トランザクションの開始に失敗しました。");
		}finally{
			
		}
		
	}
	/**
	 * 更新のコミット
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Commit() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction.commit();
		}catch (Exception e){
			em.ThrowException(e, "コミットに失敗しました。");
		}finally{
			
		}
		
	}
	/**
	 * 更新のロールバック
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Rollback() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction.rollback();
		}catch (Exception e){
			em.ThrowException(e, "ロールバックに失敗しました。");
		}finally{
			
		}

	}
	/**
	 * セッションのクローズ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Close() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			if (session != null){
				session.clear();
				session.close();
				session = null;
			}
		}catch (Exception e){
			em.ThrowException(e, "セッションのクローズに失敗しました。");
		}finally{
			
		}
	}
	/**
	 * セッション セッター
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setSession(Session _session) {
		session = _session;

	}
	/**
	 * セッション ゲッター
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public Session getSession() {
		return session;
		
	}
	
}
