package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 
 * データベース管理
 *  : DBへ更新情報の管理を行う。
 *
 */
public class UpdateBaseDao extends ObjectBase {

	private Session session;		//hibernateセッション
	private Transaction transaction;//hibernateトランザクション
	
	/**
	 * コンストラクタ
	 *  : データベース管理コンストラクタ
	 * @param xmlPath　:DBセッションXML名
	 */
	public UpdateBaseDao(String xmlPath) {
		super();
		
	    SessionFactory sessionFactory = new Configuration()
        .configure(xmlPath)
        .buildSessionFactory();
	    session = sessionFactory.openSession();
	}
	/**
	 * DB更新処理
	 * @param sbSQL : 管理SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execSQL(String strSQL) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			//SQLクエリの生成
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL更新実行
			sqlQuery.executeUpdate();

		}catch (Exception e){
			
			em.ThrowException(e, "SQLが失敗しました:" + strSQL);
			
		}finally{
			
		}
	}
	/**
	 * トランザクションの開始
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void BeginTran() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Commit() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Rollback() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Close() throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			if (session != null) {
				//セッションのクローズ
				session.close();
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
