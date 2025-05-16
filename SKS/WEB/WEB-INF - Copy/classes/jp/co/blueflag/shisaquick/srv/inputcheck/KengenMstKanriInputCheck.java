package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �����}�X�^�X�V�����C���v�b�g�`�F�b�N : �����}�X�^�����e��ʂœo�^�A�X�V�A�폜�{�^���������ɍX�V���e�̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/08
 */
public class KengenMstKanriInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �����}�X�^�X�V�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public KengenMstKanriInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
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

			// �@�\ID�FSA340�̃C���v�b�g�`�F�b�N�i�X�V���e�`�F�b�N�j���s���B
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �X�V���e�`�F�b�N : �@�\ID�FSA340�̓o�^�A�X�V�A�폜���e�̓��͒l�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("1")) {
				// �����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				insertValueCheck(checkData);
			} else if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("2")) {
				// �����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				updateValueCheck(checkData);
			} else if (checkData.GetValueStr("SA340", "ma_kengen", 0, "kbn_shori").equals("3")) {
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
			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i ������,�@�\��,���ID,�@�\ID,�Q�Ɖ\�f�[�^ID,���l�̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);
			
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
			// �����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�����R�[�h 			���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "cd_kengen"), "����");
			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i ������,�@�\��,���ID,�@�\ID,�Q�Ɖ\�f�[�^ID,���l�̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);
			
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
			// �����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�����R�[�h 			���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "cd_kengen"), "����");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	/**
	 * �o�^�l�X�V�l���ʕ����`�F�b�N : 
	 *  ������,�@�\��,���ID,�@�\ID,�Q�Ɖ\�f�[�^ID,���l�̓��̓`�F�b�N�͓o�^�A�X�V���ʁB
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		// �������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F������		 ���b�Z�[�W�p�����[�^�F"������"�@���b�Z�[�W�R�[�h�FE000200
		super.hissuInputCheck(checkData.GetValueStr("SA340", "ma_kengen", 0, "nm_kengen"), "������");
		// �������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�������A����2�F60 		���b�Z�[�W�p�����[�^�F"������"�@���b�Z�[�W�R�[�h�FE000201�@����4�F60
		super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kengen", 0, "nm_kengen"), "������", 60);
		
		int intRecCount = checkData.GetRecCnt("SA340", "ma_kinou");
		for (int i = 0; i < intRecCount; i++) {
			// �@�\���̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�@�\�� 		���b�Z�[�W�p�����[�^�F"�@�\��"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "nm_kino"), "�@�\��");
			// �@�\���̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�@�\���A����2�F60 		���b�Z�[�W�p�����[�^�F"�@�\��"�@���b�Z�[�W�R�[�h�FE000201�@����4�F60
			super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kinou", i, "nm_kino"), "�@�\��", 60);
			// ���ID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���ID 		���b�Z�[�W�p�����[�^�F"���"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_gamen"), "���");
			// �@�\ID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�@�\ID 		���b�Z�[�W�p�����[�^�F"�@�\"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_kino"), "�@�\");
			// �Q�Ɖ\�f�[�^ID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�Q�Ɖ\�f�[�^ID 		���b�Z�[�W�p�����[�^�F"�Q�Ɖ\�f�[�^"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA340", "ma_kinou", i, "id_data"), "�Q�Ɖ\�f�[�^");
			// ���l�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F���l�A����2�F60 		���b�Z�[�W�p�����[�^�F"���l"�@���b�Z�[�W�R�[�h�FE000201 ����4�F60
			super.sizeCheckLen(checkData.GetValueStr("SA340", "ma_kinou", i, "biko"), "���l", 60);
		}
		// ���ID�̏d���f�[�^�`�F�b�N���s���B
		super.diffValueCheck(checkData, "SA340", "ma_kinou", "id_gamen", "���");
	}
}