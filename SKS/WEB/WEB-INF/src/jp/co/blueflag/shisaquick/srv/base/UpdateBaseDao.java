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
 * �f�[�^�x�[�X�Ǘ�
 *  : DB�֍X�V���̊Ǘ����s���B
 *
 */
public class UpdateBaseDao extends BaseDao {

	//hibernate�Z�b�V����
	private Session session;
	//hibernate�g�����U�N�V����
	private Transaction transaction;

	/**
	 * �R���X�g���N�^
	 * �F�n�C�o�l�[�g�̃Z�b�V�����𐶐�����B
	 * @param DB_Category : �ڑ�����DB�̎��
	 */
	public UpdateBaseDao(DBCategory DB_Category) {

		super(DB_Category);

	    session = sessionFactory.openSession();

	}
	/**
	 * DB�X�V����
	 * @param sbSQL : �Ǘ�SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execSQL(String strSQL) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			//SQL�N�G���̐���
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL�X�V���s
			sqlQuery.executeUpdate();

		}catch (Exception e){

			em.ThrowException(e, "SQL�����s���܂���:" + strSQL);

		}finally{

		}
	}
	/**
	 * DB�X�V����
	 * @param sbSQL : �Ǘ�SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execUpdateSQL(String strSQL) throws  SQLServerException, Exception,  ExceptionSystem, ExceptionUser, ExceptionWaning {

			//SQL�N�G���̐���
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL�X�V���s
			sqlQuery.executeUpdate();

	}
	/**
	 * �g�����U�N�V�����̊J�n
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void BeginTran() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction = session.beginTransaction();
		}catch (Exception e){
			em.ThrowException(e, "�g�����U�N�V�����̊J�n�Ɏ��s���܂����B");
		}finally{

		}

	}
	/**
	 * �X�V�̃R�~�b�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Commit() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction.commit();
		}catch (Exception e){
			em.ThrowException(e, "�R�~�b�g�Ɏ��s���܂����B");
		}finally{

		}

	}
	/**
	 * �X�V�̃��[���o�b�N
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Rollback() throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			transaction.rollback();
		}catch (Exception e){
			em.ThrowException(e, "���[���o�b�N�Ɏ��s���܂����B");
		}finally{

		}

	}
	/**
	 * �Z�b�V�����̃N���[�Y
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void Close() throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			if (session != null) {
				//�Z�b�V�����̃N���[�Y
				session.close();
			}

		}catch (Exception e){
			em.ThrowException(e, "�Z�b�V�����̃N���[�Y�Ɏ��s���܂����B");
		}finally{

		}
	}
	/**
	 * �Z�b�V���� �Z�b�^�[
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setSession(Session _session) {
		session = _session;
	}
	/**
	 * �Z�b�V���� �Q�b�^�[
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public Session getSession() {
		return session;
	}
}
