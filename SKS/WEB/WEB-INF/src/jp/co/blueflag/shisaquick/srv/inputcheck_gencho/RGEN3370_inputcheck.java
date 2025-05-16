package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3370_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public RGEN3370_inputcheck() {
		// ���N���X�̃R���X�g���N�^
		super();
	}

	/**
     * �C���v�b�g�`�F�b�N�Ǘ�
     *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
     * @param requestData : ���N�G�X�g�f�[�^
     * @param userInfoData : ���[�U�[���
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
	public void execInputCheck(RequestData checkData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		super.userInfoData = _userInfoData;

		try {
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			// FGEN3370�̃C���v�b�g�`�F�b�N���s���B
			tehaiIraiOutputCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ���ގ�z���EXCEL�o�̓C���v�b�g�`�F�b�N : FGEN3370�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tehaiIraiOutputCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �Ώێ���
			String strTaisyosizai = checkData.GetValueStr("FGEN3370", 0, 0,
					"taisyosizai");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strTaisyosizai, "�Ώێ���");

			// ������R�[�h�P
			String strHattyuusakiCd = checkData.GetValueStr("FGEN3370", 0, 0,
					"hattyusaki_com1");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strHattyuusakiCd, "������R�[�h�P");

			// �S���҃R�[�h�P
			String strTantousyaCd = checkData.GetValueStr("FGEN3370", 0, 0,
					"hattyusaki_user1");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strTantousyaCd, "�S���҃R�[�h�P");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
