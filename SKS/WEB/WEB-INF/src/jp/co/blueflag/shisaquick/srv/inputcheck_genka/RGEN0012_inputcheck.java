package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �������Z�A�������\��ͯ�ް���߯����� : 
 * 		�������Z�A�������\��ͯ�ް���߯�����
 * 
 * @author Nishigawa
 * @since 2009/10/22
 */
public class RGEN0012_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�A�������\��ͯ�ް���߯������R���X�g���N�^
	 */
	public RGEN0012_inputcheck() {
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

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
