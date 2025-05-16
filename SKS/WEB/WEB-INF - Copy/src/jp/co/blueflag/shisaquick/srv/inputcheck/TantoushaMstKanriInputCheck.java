package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �S���҃}�X�^�X�V�����C���v�b�g�`�F�b�N : �S���҃}�X�^�����e��ʂœo�^�A�X�V�A�폜�{�^���������ɍX�V���e�̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/08
 */
public class TantoushaMstKanriInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �S���҃}�X�^�X�V�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public TantoushaMstKanriInputCheck() {
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

			// �@�\ID�FSA270�̃C���v�b�g�`�F�b�N�i�X�V���e�`�F�b�N�j���s���B
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �X�V���e�`�F�b�N : �@�\ID�FSA270�̓o�^�A�X�V�A�폜���e�̓��͒l�`�F�b�N���s���B
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
			if (checkData.GetValueStr("SA270", "ma_user", 0, "kbn_shori").equals("1")) {
				// �����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				insertValueCheck(checkData);
			}else if (checkData.GetValueStr("SA270", "ma_user", 0, "kbn_shori").equals("2")) {
				// �����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				updateValueCheck(checkData);
			}else if (checkData.GetValueStr("SA270", "ma_user", 0, "kbn_shori").equals("3")) {
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
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i ���[�UID,�p�X���[�h,�����R�[�h,����,������ЃR�[�h,���������R�[�h,�����`�[���R�[�h,��E�R�[�h����ѐ����S����ЃR�[�h�̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * �X�V�l�`�F�b�N : �X�V���e�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i ���[�UID,�p�X���[�h,�����R�[�h,����,������ЃR�[�h,���������R�[�h,�����`�[���R�[�h,��E�R�[�h����ѐ����S����ЃR�[�h�̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * �o�^�l�X�V�l���ʕ����`�F�b�N : 
	 * ���[�UID,�p�X���[�h,�����R�[�h,����,������ЃR�[�h,���������R�[�h,�����`�[���R�[�h,��E�R�[�h����ѐ����S����ЃR�[�h�̓��̓`�F�b�N�͓o�^�A�X�V���ʁB
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		// ���[�UID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F���[�UID 		���b�Z�[�W�p�����[�^�F"���[�UID"�@���b�Z�[�W�R�[�h�FE000200
		super.hissuInputCheck(checkData.GetValueStr("SA270", "ma_user", 0, "id_user"), "���[�UID");
		// �p�X���[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F�p�X���[�h 		���b�Z�[�W�p�����[�^�F"�p�X���[�h"�@���b�Z�[�W�R�[�h�FE000200
		super.hissuInputCheck(checkData.GetValueStr("SA270", "ma_user", 0, "password"), "�p�X���[�h");
		// �p�X���[�h�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�p�X���[�h�A����2�F30 		���b�Z�[�W�p�����[�^�F"�p�X���[�h"�@���b�Z�[�W�R�[�h�FE000212�@����4�F30
		super.sizeCheckLen(checkData.GetValueStr("SA270", "ma_user", 0, "password"), "�p�X���[�h", 30);
		// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
		// �p�X���[�h�̍ŏ����͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		super.sizeCheckLenMin(checkData.GetValueStr("SA270", "ma_user", 0, "password"), "�p�X���[�h", 6);
		// �p�X���[�h�̉p�������݃`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		super.strCheckPrm(checkData.GetValueStr("SA270", "ma_user", 0, "password"), "�p�X���[�h");
		// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
		// �����R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F�����R�[�h 		���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_kengen"), "����");
		// �����̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F���� 		���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000200
		super.hissuInputCheck(checkData.GetValueStr("SA270", "ma_user", 0, "nm_user"), "����");
		// �����̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�����A����2�F60 		���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000201 ����4�F60
		super.sizeCheckLen(checkData.GetValueStr("SA270", "ma_user", 0, "nm_user"), "����", 60);
		// ������ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F������ЃR�[�h 		���b�Z�[�W�p�����[�^�F"�������"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_kaisha"), "�������");
		// ���������R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F���������R�[�h 		���b�Z�[�W�p�����[�^�F"��������"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_busho"), "��������");
		// �����O���[�v�R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F�����O���[�v�R�[�h 		���b�Z�[�W�p�����[�^�F"�����O���[�v"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_group"), "�����O���[�v");
		// �����`�[���R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F�����`�[���R�[�h 		���b�Z�[�W�p�����[�^�F"�����`�[��"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_team"), "�����`�[��");
		// ��E�R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F��E�R�[�h 		���b�Z�[�W�p�����[�^�F"��E"�@���b�Z�[�W�R�[�h�FE000207
		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_user", 0, "cd_yakushoku"), "��E");
//		// �����S����ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//		// ������1�F�����S����ЃR�[�h		 ���b�Z�[�W�p�����[�^�F"�����S�����"�@���b�Z�[�W�R�[�h�FE000207
//		super.hissuCodeCheck(checkData.GetValueStr("SA270", "ma_tantokaisya", 0, "cd_tantokaisha"), "�����S�����");
		if (!checkData.GetValueStr("SA270", "ma_tantokaisya", 0, "cd_tantokaisha").equals("")) {
			// �����S����ЃR�[�h�̏d���f�[�^�`�F�b�N���s���B
			super.diffValueCheck(checkData, "SA270", "ma_tantokaisya", "cd_tantokaisha", "�S���������");
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
			
			// ���[�UID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F���[�UID 		���b�Z�[�W�p�����[�^�F"���[�UID"�@���b�Z�[�W�R�[�h�FE000200
			super.hissuInputCheck(checkData.GetValueStr("SA270", "ma_user", 0, "id_user"), "���[�UID");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}