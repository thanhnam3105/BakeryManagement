package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ����f�[�^�ꗗJNLP�쐬�C���v�b�g�`�F�b�N : ����f�[�^�ꗗ��ʂŃO���[�v�h���b�v�_�E���I�����̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/09
 */
public class ShisakuSrvJnlpInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ����f�[�^�ꗗJNLP�쐬�C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ShisakuSrvJnlpInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : ���̓`�F�b�N�̊Ǘ����s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		super.userInfoData = _userInfoData;

		try {
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			// �@�\ID�FSA550�̃C���v�b�g�`�F�b�N�iJNLP�p�����[�^�`�F�b�N�j���s���B
			paramValueCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * JNLP�p�����[�^�`�F�b�N
	 *  : �@�\ID�FSA550��JNLP�쐬���ɐݒ肷��p�����[�^�l�̃`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void paramValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// ���[�UID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���[�UID 		���b�Z�[�W�p�����[�^�F"���[�UID"�@���b�Z�[�W�R�[�h�FE000300
			super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "id_user"), "���[�UID");
			//�V�K���[�h�ȊO
			if (!checkData.GetValueStr("SA550", 0, 0, "mode").equals("110")) {
				// ����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
				// ������1�F����R�[�h 		���b�Z�[�W�p�����[�^�F"����R�[�h"�@���b�Z�[�W�R�[�h�FE000300
				super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "no_shisaku"), "����R�[�h");
			}
			// �N�����[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�N�����[�h 		���b�Z�[�W�p�����[�^�F"�N�����[�h"�@���b�Z�[�W�R�[�h�FE000300
			super.hissuJNLPSetCheck(checkData.GetValueStr("SA550", 0, 0, "mode"), "�N�����[�h");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
