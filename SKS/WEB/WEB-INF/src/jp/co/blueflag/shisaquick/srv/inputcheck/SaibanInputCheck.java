package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �����̔ԃC���v�b�g�`�F�b�N : �����̔Ԏ��̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/09
 */
public class SaibanInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �����̔ԃC���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public SaibanInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �e�f�[�^�`�F�b�N�������Ǘ�����B
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

			// �@�\ID�FSA410�̃C���v�b�g�`�F�b�N�i�̔ԍ��ڃ`�F�b�N�j���s���B
			saibanKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �̔ԍ��ڃ`�F�b�N
	 *  : �@�\ID�FSA410�̍̔ԃL�[�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void saibanKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �����敪�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�����敪 		���b�Z�[�W�p�����[�^�F"�����敪"�@���b�Z�[�W�R�[�hE000211
			super.hissuSaibanCheck(checkData.GetValueStr("SA410", 0, 0, "kbn_shori"), "�����敪");
//			// �L�[�P�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//			// ������1�F�L�[�P 		���b�Z�[�W�p�����[�^�F"�L�[�P"�@���b�Z�[�W�R�[�hE000211
//			super.hissuSaibanCheck(checkData.GetValueStr("SA410", 0, 0, "key1"), "�L�[�P");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
