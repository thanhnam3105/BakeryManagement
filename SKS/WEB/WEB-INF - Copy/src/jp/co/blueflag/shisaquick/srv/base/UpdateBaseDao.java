package jp.co.blueflag.shisaquick.srv.base;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * データベース管理
 *  : DBへ更新情報の管理を行う。
 *
 */
public class UpdateBaseDao extends BaseDao {

	//hibernateセッション
	private Session session;
	//hibernateトランザクション
	private Transaction transaction;

	/**
	 * コンストラクタ
	 * ：ハイバネートのセッションを生成する。
	 * @param DB_Category : 接続するDBの種類
	 */
	public UpdateBaseDao(DBCategory DB_Category) {

		super(DB_Category);

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
	 * DB更新処理
	 * @param sbSQL : 管理SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execUpdateSQL(String strSQL) throws  SQLServerException, Exception,  ExceptionSystem, ExceptionUser, ExceptionWaning {

			//SQLクエリの生成
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL更新実行
			sqlQuery.executeUpdate();

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
