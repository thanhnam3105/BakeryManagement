package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �e���e�����}�X�^Excel�����C���v�b�g�`�F�b�N : �e���e�����}�X�^�����e��ʂ�Excel�{�^���������̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/08
 */
public class LiteralMstExcelInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �e���e�����}�X�^Excel�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public LiteralMstExcelInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �@�\ID�FSA320�̃C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j���s���B
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

			// �@�\ID�FSA320�̃C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j���s���B
			outputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j
	 *  : �o�͏����̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �J�e�S���R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�J�e�S���R�[�h 		���b�Z�[�W�p�����[�^�F"�J�e�S��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA320", 0, 0, "cd_category"), "�J�e�S��");
//			// ���e�����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//			// ������1�F���e�����R�[�h 		���b�Z�[�W�p�����[�^�F"���e����"�@���b�Z�[�W�R�[�h�FE000207
//			super.hissuCodeCheck(checkData.GetValueStr("SA320", 0, 0, "cd_literal"), "���e����");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
