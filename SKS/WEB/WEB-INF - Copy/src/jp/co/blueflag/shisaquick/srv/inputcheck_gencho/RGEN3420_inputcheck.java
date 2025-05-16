package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3420_inputcheck extends InputCheck {

	/**
	 *  : ���ގ�z���́F���i�R�[�h���� �C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public RGEN3420_inputcheck() {
		//���N���X�̃R���X�g���N�^
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

			// RGEN3420�̃C���v�b�g�`�F�b�N���s���B
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ���i�R�[�h���������C���v�b�g�`�F�b�N
	 *  : RGEN3420�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// ���i�R�[�h
			super.hissuInputCheck(checkData.GetValueStr("FGEN3420", 0, 0, "cd_seihin"), "���i�R�[�h");

			super.numberCheck(checkData.GetValueStr("FGEN3420", 0, 0, "cd_seihin"), "���i�R�[�h");


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
