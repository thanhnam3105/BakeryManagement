package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW020�z ����f�[�^��ʃ��N�G�X�g����R�s�[ �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuCopyInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuCopyInputCheck() {
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
			//USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			//SA420�̃C���v�b�g�`�F�b�N���s���B
			exclusiveControlKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^��ʃ��N�G�X�g����R�s�[�C���v�b�g�`�F�b�N���������s���܂����B");
		} finally {
			
		}
	}

	/**
	 * �r�����䍀�ڃC���v�b�g�`�F�b�N
	 *  : SA420�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//���s�敪��1�i��������j�̏ꍇ
			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
				//���[�UID�̕K�{�`�F�b�N���s���B
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"���[�UID");
	
			    //�r���敪�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"�r���敪");
				//����R�[�h�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"����CD-�Ј�CD");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"����CD-�N");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"����CD-�ǔ�");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
