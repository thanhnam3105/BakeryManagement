package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3360_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public RGEN3360_inputcheck() {
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

			// FGEN3360�̃C���v�b�g�`�F�b�N���s���B
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ���ގ�z���o�^�X�V�C���v�b�g�`�F�b�N : FGEN3360�̃C���v�b�g�`�F�b�N���s���B<br>
	 * �X�V�f�[�^�͕��������邪�A���e�͑S�ē���̈׃`�F�b�N�͍ŏ���1�������s���B
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "FGEN3360";
		try {
			// �݌v�P
			String strSekkei1 = checkData.GetValueStr(strKinoNm, 0, 0,
					"sekkei1");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strSekkei1, "�݌v�@");
			// �݌v�P��250����葽�������̏ꍇ
			super.sizeCheckLen(strSekkei1, "�݌v�@", 250);
			
			// �ގ�
			String strZaishitsu = checkData.GetValueStr(strKinoNm, 0, 0,
					"zaishitsu");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strZaishitsu, "�ގ�");
			// �ގ���250����葽�������̏ꍇ
			super.sizeCheckLen(strZaishitsu, "�ގ�", 250);
			
			// ����F
			String strPrintColor = checkData.GetValueStr(strKinoNm, 0, 0,
					"printcolor");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strPrintColor, "����F");
			// ����F��250����葽�������̏ꍇ
			super.sizeCheckLen(strPrintColor, "����F", 250);
			
			// �F�ԍ�
			String strNoColor = checkData.GetValueStr(strKinoNm, 0, 0,
					"no_color");
			// �K�{���̓`�F�b�N
			super.hissuInputCheck(strNoColor, "�F�ԍ�");
			// �F�ԍ���250����葽�������̏ꍇ
			super.sizeCheckLen(strNoColor, "�F�ԍ�", 250);
				
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
