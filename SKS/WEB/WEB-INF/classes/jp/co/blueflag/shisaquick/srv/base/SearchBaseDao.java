package jp.co.blueflag.shisaquick.srv.base;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 
 * Class���@: �f�[�^�x�[�X����
 * �����T�v : DB�̌������s���B
 * @author  TT.Furuta
 * @since   2009/03/25
 */
public class SearchBaseDao extends ObjectBase {

	private Session session;		//hibernate�Z�b�V����
	private Transaction transaction;//hibernate�g�����U�N�V����
	
	/**
	 * �R���X�g���N�^
	 *  : �f�[�^�x�[�X�����R���X�g���N�^
	 */
	public SearchBaseDao(String xmlPath) {
	    SessionFactory sessionFactory = new Configuration()
        .configure(xmlPath)
        .buildSessionFactory();
	    session = sessionFactory.openSession();

	}
	
	/**
	 * �f�[�^�x�[�X����
	 *  : �f�[�^�x�[�X�ւ̌����������s���B
	 * 
	 * @param  session  DB�R�l�N�V�������
	 * @param  strSQL   SQL�N�G��
	 * @return �������ʃ��X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * @throws ExceptionBase 
	 */
	public List<?> dbSearch(String strSQL) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		List<?> lstSearchKekka = null;
		
		try{
			
			//SQL����
			Query sqlQuery = session.createSQLQuery(strSQL);
			//SQL�������s
			lstSearchKekka = sqlQuery.list();
			//�������ʂ��Ȃ������ꍇ
			if (null == lstSearchKekka){
				//�Y���f�[�^�Ȃ����X���[����
				em.ThrowException(ExceptionKind.�x��Exception, "W000401", strSQL, "", "");
			}
		}catch (Exception e){
			em.ThrowException(e, "SQL�̎��s�Ɏ��s���܂���:" + strSQL);
		}finally{
			
		}
		
		return lstSearchKekka;

	}
	/**
	 * �g�����U�N�V�����̊J�n
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void BeginTran() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Commit() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Rollback() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

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
	public void Close() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{
			session.close();
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
