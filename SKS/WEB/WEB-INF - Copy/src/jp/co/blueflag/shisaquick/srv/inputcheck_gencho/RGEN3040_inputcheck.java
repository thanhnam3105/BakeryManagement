package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3040_inputcheck extends InputCheck {

	/**
	 *  : �R�X�g�e�[�u���Q�Ɖ�� �����{�^���������C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public RGEN3040_inputcheck() {
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

			// FGEN3040�̃C���v�b�g�`�F�b�N���s���B
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �R�X�g�e�[�u���Q�ƌ��������C���v�b�g�`�F�b�N
	 *  : FGEN3040�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			// ���[�J�[��
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3040", 0, 0, "cd_maker"), "���[�J�[��");

			// ���
		    super.hissuCodeCheck(checkData.GetValueStr("FGEN3040", 0, 0, "cd_houzai"), "���");

			// �Ő�
		    //�yQP@30297�zNo.18 E.kitazawa �ۑ�Ή� --------------------- mod start
//		    super.hissuInputCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "�Ő�");
		    super.hissuInputCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "�Ő�", "E000340");
		    //�yQP@30297�zNo.18 E.kitazawa �ۑ�Ή� --------------------- mod end

		    //�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
		    super.numberCheck(checkData.GetValueStr("FGEN3040", 0, 0, "no_hansu"), "�Ő�");
		    //�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
