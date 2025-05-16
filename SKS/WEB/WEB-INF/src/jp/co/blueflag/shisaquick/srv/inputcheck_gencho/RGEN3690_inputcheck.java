package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3690_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public RGEN3690_inputcheck() {
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

			// FGEN3690�̃C���v�b�g�`�F�b�N���s���B
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ���������C���v�b�g�`�F�b�N : FGEN3330�̃C���v�b�g�`�F�b�N���s���B
	 *
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// �ő�
			String strHanPay = checkData.GetValueStr("FGEN3690", 0, 0, "han_pay");
			if (strHanPay != null && !"".equals(strHanPay)) {
				// �ő㐔�l�`�F�b�N
				super.numberCheck(strHanPay, "�ő�");
			}

            // �ő�x����
            String dt_han_payday = toString(checkData.GetValueStr("FGEN3690", 0, 0, "han_payday"));
            // �ő�x�����̌`���`�F�b�N
            if (dt_han_payday != null && !"".equals(dt_han_payday)) {
            	super.dateCheck(dt_han_payday, "�ő�x����");
            }



		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
