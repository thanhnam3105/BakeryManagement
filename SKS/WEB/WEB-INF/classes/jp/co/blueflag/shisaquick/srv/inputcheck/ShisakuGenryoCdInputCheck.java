package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW110�z ����f�[�^��ʃ��N�G�X�g�����R�[�h���� �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuGenryoCdInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuGenryoCdInputCheck() {
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

			//SA�T�W0�̃C���v�b�g�`�F�b�N���s���B
			SearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^��ʃ��N�G�X�g�����R�[�h���� �C���v�b�g�`�F�b�N�����s���܂����B");
		} finally {
			
		}
	}

	/**
	 * �y����R�[�h���͌����z �������ڃ`�F�b�N
	 *  : SA580�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//��ЃR�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_kaisha"),"��ЃR�[�h");
			//�����R�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_genryo"),"�����R�[�h");
			//�����R�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA580", 0, 0, "cd_busho"),"�����R�[�h");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
