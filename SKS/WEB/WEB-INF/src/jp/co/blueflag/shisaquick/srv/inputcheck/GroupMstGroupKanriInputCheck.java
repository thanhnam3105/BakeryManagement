package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �O���[�v�}�X�^�O���[�v�X�V�����C���v�b�g�`�F�b�N : �O���[�v�}�X�^�����e��ʂœo�^�A�X�V�A�폜�{�^���������ɃO���[�v�X�V���e�̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/08
 */
public class GroupMstGroupKanriInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �O���[�v�}�X�^�O���[�v�X�V�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GroupMstGroupKanriInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
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

			// �@�\ID�FSA060�̃C���v�b�g�`�F�b�N�i�O���[�v�X�V���e�`�F�b�N�j���s���B
			groupKanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �O���[�v�X�V���e�`�F�b�N : �O���[�v�̓o�^�A�X�V�A�폜���e�̓��͒l�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void groupKanriValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("1")) {
				// �����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				insertValueCheck(checkData);
			}else if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("2")) {
				// �����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				updateValueCheck(checkData);
			}else if (checkData.GetValueStr("SA060", 0, 0, "kbn_shori").equals("3")) {
				// �����敪���폜�̏ꍇ�A�폜�l�`�F�b�N���s���B
				deleteValueCheck(checkData);
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * �o�^�l�`�F�b�N : �o�^���e�̓��͒l�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// �O���[�v���̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�O���[�v��		 ���b�Z�[�W�p�����[�^�F"�O���[�v��"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "�O���[�v��");
			// �O���[�v���̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�O���[�v���A����2�F100 	���b�Z�[�W�p�����[�^�F"�O���[�v��"�@���b�Z�[�W�R�[�h�FE000201 ����3�F"�O���[�v��"�A����4�F100
			super.sizeCheckLen(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "�O���[�v��", 100);
			// ADD 2013/10/24 QP@30154 okano start
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F��ЃR�[�h 		���b�Z�[�W�p�����[�^�F"���"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_kaisha"), "���");
			// ADD 2013/10/24 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	/**
	 * �X�V���e�`�F�b�N : �X�V���e�̓��͒l�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// �O���[�v�R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�O���[�v�R�[�h 		���b�Z�[�W�p�����[�^�F"�O���[�v"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_group"), "�O���[�v");
			// �O���[�v���̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�O���[�v�� 		���b�Z�[�W�p�����[�^�F"�O���[�v��"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "�O���[�v��");
			// �O���[�v���̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�O���[�v���A����2�F100 		���b�Z�[�W�p�����[�^�F"�O���[�v��"�@���b�Z�[�W�R�[�h�FE000201�@ ����3�F"�O���[�v��"�A����4�F100
			super.sizeCheckLen(checkData.GetValueStr("SA060", 0, 0, "nm_group"), "�O���[�v��", 100);
			// ADD 2013/10/24 QP@30154 okano start
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F��ЃR�[�h 		���b�Z�[�W�p�����[�^�F"���"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_kaisha"), "���");
			// ADD 2013/10/24 QP@30154 okano end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	/**
	 * �폜�l�`�F�b�N : �폜���e�̓��͒l�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void deleteValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {

			// �O���[�v�R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�O���[�v�R�[�h 		���b�Z�[�W�p�����[�^�F"�O���[�v"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA060", 0, 0, "cd_group"), "�O���[�v");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
