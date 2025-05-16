package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW620�z �����ꗗ��ʃ��N�G�X�g���������i��ЃR���{�������j �C���v�b�g�`�F�b�N
 *
 */
public class GenryoIchiranSearchBushoInputCheck extends InputCheck {
	
	/**
	 * �R���X�g���N�^
	 */
	public GenryoIchiranSearchBushoInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
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

			//SA290�̃C���v�b�g�`�F�b�N���s���B
			BushoListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���������擾�L�[�`�F�b�N
	 *  : SA290�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void BushoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//��ЃR�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA290", 0, 0, "cd_kaisha"),"��ЃR�[�h");
			//��ʂh�c�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA290", 0, 0, "id_gamen"),"��ʂh�c");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
