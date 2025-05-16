package jp.co.blueflag.shisaquick.srv.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * �Z�b�V�����`�F�b�N
 *  : �Z�b�V�������̑��݉ۃ`�F�b�N���s���B
 * @author TT.furuta
 * @since  2009/03/24
 */
public class SessionCheck {

	private HttpSession session = null;		//�Z�b�V�����N���X
	private ExceptionManager em = null;		//Exception�Ǘ��N���X

	/**
	 * �R���X�g���N�^
	 *  : �Z�b�V�����`�F�b�N�R���X�g���N�^
	 * @param session : �Z�b�V����
	 */
	public SessionCheck(HttpSession session) {
		//�C���X�^���X�ϐ��̏�����
//		session = null;
		em = null;

		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());
		//�Z�b�V�����i�[
		this.session = session;

	}

	/**
	 * �Z�b�V�������`�F�b�N
	 *  : �Z�b�V�������̑��݉ۂ��`�F�b�N����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void sessionInfoCheck()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning  {

		ArrayList<Object> arrSessionData = null;

		try{

			arrSessionData = new ArrayList<Object>();

			//���[�U�[�h�c
			arrSessionData.add(session.getAttribute("id_user"));
			//���[�U�[��
			arrSessionData.add(session.getAttribute("nm_user"));
			//��ЃR�[�h
			arrSessionData.add(session.getAttribute("cd_kaisha"));
			//��Ж�
			arrSessionData.add(session.getAttribute("nm_kaisha"));
			//�����R�[�h
			arrSessionData.add(session.getAttribute("cd_busho"));
			//������
			arrSessionData.add(session.getAttribute("nm_busho"));
			//�O���[�v�R�[�h
			arrSessionData.add(session.getAttribute("cd_group"));
			//�O���[�v��
			arrSessionData.add(session.getAttribute("nm_group"));
			//�`�[���R�[�h
			arrSessionData.add(session.getAttribute("cd_team"));
			//�`�[����
			arrSessionData.add(session.getAttribute("nm_team"));
			//��E�R�[�h
			arrSessionData.add(session.getAttribute("cd_literal"));
			//��E��
			arrSessionData.add(session.getAttribute("nm_literal"));

			//���X�g�T�C�Y�����[�v���ă`�F�b�N
			for (int i=0;i<arrSessionData.size();i++){

				//�Z�b�V������񂪎擾�o���Ă��邩����
//				if (arrSessionData.get(i).toString().equals(null)){
				if (arrSessionData.get(i) == null){
					//���o���Ă��Ȃ��ꍇ

					//���Exception�𐶐�����
					em.ThrowException(ExceptionKind.���Exception, "E000101", "", "", "");

				}

			}

		} catch (Exception e) {
			//��O����
			em.ThrowException(e, "�Z�b�V�������`�F�b�N�Ɏ��s���܂����B");

		} finally {
			removeList(arrSessionData);

		}

	}
	/**
	 * ���X�gObject�̔j��
	 * @param listObj : �j���Ώۂ̃��X�g
	 */
	private void removeList(Object Obj){

		List<?> tgList = null;
		try{
			tgList = (List<?>)Obj;

			for(int ix = 0; ix < tgList.size(); ix++){
				removeList(tgList.get(ix));
				tgList.remove(ix);

			}

		}catch(Exception e){

		} finally {
			Obj = null;
			tgList = null;
		}

	}

}
