package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �S���҃}�X�^���������C���v�b�g�`�F�b�N : �S���҃}�X�^�����e��ʂŃ��[�UID���X�g�t�H�[�J�X���̊e���ڂ̓��͒l�`�F�b�N���s���B
 *
 * @author itou
 * @since 2009/04/08
 */
public class TantoushaMstUserIDInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �S���҃}�X�^���������C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public TantoushaMstUserIDInputCheck() {
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

			// �@�\ID�FSA260�̃C���v�b�g�`�F�b�N�i�S���Ҍ��������l�`�F�b�N�j���s���B
			tantoSearchKeyCheck(checkData);
			// �@�\ID�FSA210�̃C���v�b�g�`�F�b�N�i������Ќ��������l�`�F�b�N�j���s���B
			kaishaSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �S���ҏ��̌��������̓��͒l�`�F�b�N���s���B
	 *  : �@�\ID�FSA260�̃C���v�b�g�`�F�b�N�i�S���Ҍ��������l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tantoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ���[�UID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// MOD start 2015/07/30 TT.Kitazawa�yQP@40812�zNo.5
//			super.hissuInputCheck(checkData.GetValueStr("SA260", 0, 0, "id_user"), "���[�UID");
			super.hissuInputCheck(checkData.GetValueStr("SA260", 0, 0, "id_user"), "�Ј��ԍ�");
			// MOD end 2015/07/30 TT.Kitazawa�yQP@40812�zNo.5

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ������Ќ��������l�`�F�b�N
	 *  : �@�\ID�FSA210�̃C���v�b�g�`�F�b�N�i������Ќ��������l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kaishaSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �����敪��"1"�̏ꍇ�A���[�UID�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			if (checkData.GetValueStr("SA210", 0, 0, "kbn_shori").equals("1")) {
				super.hissuInputCheck(checkData.GetValueStr("SA210", 0, 0, "id_user"), "���[�UID");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
