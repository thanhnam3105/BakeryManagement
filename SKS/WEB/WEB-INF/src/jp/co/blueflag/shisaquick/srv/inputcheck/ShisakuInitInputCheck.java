package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW010�z ����f�[�^��ʏ����\���C���v�b�g�`�F�b�N
 *
 */
public class ShisakuInitInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuInitInputCheck() {
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

			//SA480�̃C���v�b�g�`�F�b�N���s���B
			searchKeyCheck(checkData);
			
			//SA210�̃C���v�b�g�`�F�b�N���s���B
			tantokaishaSearchCheck(checkData);
			
			//SA600�`SA780�̃C���v�b�g�`�F�b�N���s��
			literalSearchCheck(checkData);
						
		} catch (Exception e) {
			this.em.ThrowException(e, "");
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

	/**
	 * ����f�[�^���������C���v�b�g�`�F�b�N
	 *  : SA480�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//����R�[�h�̕K�{�`�F�b�N���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "cd_shain"),"����CD-�Ј�CD");
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "nen"),"����CD-�N");
		    super.hissuInputCheck(checkData.GetValueStr("SA480", 0, 0, "no_oi"),"����CD-�ǔ�");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �����S����Ќ��������C���v�b�g�`�F�b�N
	 *  : SA210�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantokaishaSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//���[�UID�̕K�{�`�F�b�N���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA210", 0, 0, "id_user"),"���[�UID");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���e�������������C���v�b�g�`�F�b�N
	 *  : SA600�`SA780�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void literalSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//JWS���e��������
			literalHissuInputCheck(checkData,"SA600");		// �H������
			literalHissuInputCheck(checkData,"SA610");		// �ꊇ�\��
			literalHissuInputCheck(checkData,"SA620");		// �W������
			literalHissuInputCheck(checkData,"SA630");		// ���[�U
			literalHissuInputCheck(checkData,"SA640");		// ��������
			literalHissuInputCheck(checkData,"SA650");		// �p�r
			literalHissuInputCheck(checkData,"SA660");		// ���i��
			literalHissuInputCheck(checkData,"SA670");		// ���
			literalHissuInputCheck(checkData,"SA680");		// �����w��
			literalHissuInputCheck(checkData,"SA690");		// �S���c��
			literalHissuInputCheck(checkData,"SA700");		// �������@
			literalHissuInputCheck(checkData,"SA710");		// �[�U���@
			literalHissuInputCheck(checkData,"SA720");		// �E�ە��@
			literalHissuInputCheck(checkData,"SA730");		// �e����
			literalHissuInputCheck(checkData,"SA740");		// �e��
			literalHissuInputCheck(checkData,"SA750");		// �P��
			literalHissuInputCheck(checkData,"SA760");		// �׎p
			literalHissuInputCheck(checkData,"SA770");		// �戵���x
			literalHissuInputCheck(checkData,"SA780");		// �ܖ�����
			literalHissuInputCheck(checkData,"SA850");		// ���No
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	private void literalHissuInputCheck(RequestData checkData, String strKinoId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//���[�UID�̕K�{�`�F�b�N���s���B
//	    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "id_user"),"���[�UID");
	    //���ID�̕K�{�`�F�b�N���s���B
	    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "id_gamen"),"���ID");
	}
}
