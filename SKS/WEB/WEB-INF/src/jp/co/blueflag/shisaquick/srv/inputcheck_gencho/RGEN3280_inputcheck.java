package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class RGEN3280_inputcheck extends InputCheck {

	/**
	 *  : �f�U�C���X�y�[�X�o�^�F�o�^�{�^���������C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public RGEN3280_inputcheck() {
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

			// FGEN3280�̃C���v�b�g�`�F�b�N���s���B
			costTableSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 *  �f�U�C���X�y�[�X�o�^�����C���v�b�g�`�F�b�N
	 *  : FGEN3280�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void costTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strNmSyurui = "";
			String strNmfile   = "";
			String[] lstNmSyurui = null;
			String[] lstNmFile = null;

			// �����H��
			super.hissuInputCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_seizokojo"), "�����H��");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_seizokojo"), "�����H��");

			// �E��
			super.hissuCodeCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_shokuba"), "�E��");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_shokuba"), "�E��");

			// �������C��
		    super.hissuCodeCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_line"), "�������C��");
			super.numberCheck(checkData.GetValueStr("FGEN3280", 0, 0, "cd_line"), "�������C��");

			// �������[�h�F�o�^�̎�
			if (checkData.GetValueStr("FGEN3280", 0, 0, "syoriMode").equals("ADD")) {
				// ���
				strNmSyurui = checkData.GetValueStr("FGEN3280", 0, 0, "nm_syurui");
				// �t�@�C��
				strNmfile = checkData.GetValueStr("FGEN3280", 0, 0, "nm_file");

            	// �o�^���𕪊�
            	lstNmSyurui = strNmSyurui.split(":::");
            	lstNmFile = strNmfile.split(":::");

				// ��ނƃt�@�C�����̃��X�g���͓���
            	//�iPG�Ő�������̂ł����ł̓`�F�b�N���Ȃ��j
            	// ���
				for (int i=0; i<lstNmSyurui.length; i++ ) {
					super.hissuCodeCheck(lstNmSyurui[i], "���");
				}

				// �t�@�C����
				for (int i=0; i<lstNmFile.length; i++ ) {
					super.hissuCodeCheck(lstNmFile[i], "�t�@�C����");

					// �t�@�C��������̕s���`�F�b�N�i�����Ń`�F�b�N���Ă��A�b�v���[�h����Ă��܂��j
					super.checkStringURL(lstNmFile[i], "�t�@�C����");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
