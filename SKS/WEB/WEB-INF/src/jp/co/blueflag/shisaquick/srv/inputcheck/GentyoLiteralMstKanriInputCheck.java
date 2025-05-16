package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �e���e�����}�X�^�X�V�����C���v�b�g�`�F�b�N : �e���e�����}�X�^�����e��ʂœo�^�A�X�V�A�폜�{�^���������ɍX�V���e�̓��͒l�`�F�b�N���s���B
 *
 * @author hisahori
 * @since 2014/10/10
 */
public class GentyoLiteralMstKanriInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �e���e�����}�X�^�X�V�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GentyoLiteralMstKanriInputCheck() {
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

			// �@�\ID�FSA331�̃C���v�b�g�`�F�b�N�i�X�V���e�`�F�b�N�j���s���B
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �X�V���e�`�F�b�N : �o�^�A�X�V�A�폜���e�̓��͒l�`�F�b�N���s���B
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
			if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("1")) {
				// �����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				insertValueCheck(checkData);
			}else if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("2")) {
				// �����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				updateValueCheck(checkData);
			}else if (checkData.GetValueStr("SA331", 0, 0, "kbn_shori").equals("3")) {
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
			// �J�e�S���R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�J�e�S���R�[�h	 ���b�Z�[�W�p�����[�^�F"�J�e�S��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "�J�e�S��");
			// ���e�������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���e������	 ���b�Z�[�W�p�����[�^�F"���e������"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "���e������");
			// ���e�������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F���e�������A����2�F60		���b�Z�[�W�p�����[�^�F"���e������"�@���b�Z�[�W�R�[�h�FE000201�@����4�F60
			super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "���e������", 60);
			// �\�����̕K�{���̓`�F�b�N���s���B
			// ������1�F�\���� 		���b�Z�[�W�p�����[�^�F"�\����"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_sort"), "�\����");
			if (!checkData.GetValueStr("SA331", 0, 0, "biko").toString().equals("")) {
				// ���l�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
				// ������1�F���l�A����2�F60 		���b�Z�[�W�p�����[�^�F"���l"�@���b�Z�[�W�R�[�h�FE000200
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "biko"), "���l", 60);
			}
			// �ҏW�ۃt���O�̕K�{���̓`�F�b�N���s���B
			// ������1�F�ҏW�ۃt���O 	���b�Z�[�W�p�����[�^�F"�ҏW��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "flg_edit"), "�ҏW��");


			if(!checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal").toString().equals("")){
				// ��񃊃e�������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
				// ������1�F��Q���e������ 			���b�Z�[�W�p�����[�^�F"���e������"�@���b�Z�[�W�R�[�h�FE000200
				super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "��񃊃e������");
				// ��񃊃e�������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
				// ������1�F��Q���e�������A����2�F60		 ���b�Z�[�W�p�����[�^�F"���e������" ���b�Z�[�W�R�[�h�FE000201�@����4�F60
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "��񃊃e������", 60);
				// �\�����̕K�{���̓`�F�b�N���s���B
				// ������1�F��Q�\���� 		���b�Z�[�W�p�����[�^�F"��Q�\����"�@���b�Z�[�W�R�[�h�FE000200
				super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_2ndsort"), "��Q�\����");
			}
//			if(!checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal").toString().equals("")){
//			}

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
			// �J�e�S���R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�J�e�S���R�[�h 		���b�Z�[�W�p�����[�^�F"�J�e�S��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "�J�e�S��");
			// ���e�����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���e�����R�[�h 		���b�Z�[�W�p�����[�^�F"���e����"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_literal"), "���e����");
			// ���e�������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���e������ 			���b�Z�[�W�p�����[�^�F"���e������"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "���e������");
			// ���e�������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F���e�������A����2�F60		 ���b�Z�[�W�p�����[�^�F"���e������" ���b�Z�[�W�R�[�h�FE000201�@����4�F60
			super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_literal"), "���e������", 60);
			// �\�����̕K�{���̓`�F�b�N���s���B
			// ������1�F�\���� 			���b�Z�[�W�p�����[�^�F"�\����"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "no_sort"), "�\����");
			if (!checkData.GetValueStr("SA331", 0, 0, "biko").toString().equals("")) {
				// ���l�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
				// ������1�F���l�A����2�F60 		���b�Z�[�W�p�����[�^�F"���l"�@���b�Z�[�W�R�[�h�FE000200
				super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "biko"), "���l", 60);
			}
			// �ҏW�ۃt���O�̕K�{���̓`�F�b�N���s���B
			// ������1�F�ҏW�ۃt���O 		���b�Z�[�W�p�����[�^�F"�ҏW��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "flg_edit"), "�ҏW��");

			if(!checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal").toString().equals("")){

				// ��Q���e�����̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
				// ������1�F�J�e�S���R�[�h 		���b�Z�[�W�p�����[�^�F"�J�e�S��"�@���b�Z�[�W�R�[�h�FE000207
				super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal"), "��Q���e����");

				if(!checkData.GetValueStr("SA331", 0, 0, "cd_2nd_literal").toString().equals("")){
//					// ��񃊃e�������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//					// ������1�F���e������ 			���b�Z�[�W�p�����[�^�F"���e������"�@���b�Z�[�W�R�[�h�FE000200
//					super.hissuInputCheck(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "��񃊃e������");
					// ��񃊃e�������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
					// ������1�F���e�������A����2�F60		 ���b�Z�[�W�p�����[�^�F"���e������" ���b�Z�[�W�R�[�h�FE000201�@����4�F60
					super.sizeCheckLen(checkData.GetValueStr("SA331", 0, 0, "nm_2nd_literal"), "��񃊃e������", 60);
				}
			}

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
			// �J�e�S���R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F�J�e�S���R�[�h 		���b�Z�[�W�p�����[�^�F"�J�e�S��"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_category"), "�J�e�S��");
			// ���e�����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���e�����R�[�h 		���b�Z�[�W�p�����[�^�F"���e����"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA331", 0, 0, "cd_literal"), "���e����");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
