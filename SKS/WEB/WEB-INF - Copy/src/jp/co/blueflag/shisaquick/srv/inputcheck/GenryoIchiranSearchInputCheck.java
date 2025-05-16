package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW630�z �����ꗗ��ʃ��N�G�X�g���� �C���v�b�g�`�F�b�N
 *
 */
public class GenryoIchiranSearchInputCheck extends InputCheck {
	
	/**
	 * �R���X�g���N�^
	 */
	public GenryoIchiranSearchInputCheck() {
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

			//SA510�̃C���v�b�g�`�F�b�N���s���B
			genryoSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���͌������������l�`�F�b�N
	 *  : SA510�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//��ЃR�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "cd_kaisha"),"��ЃR�[�h");
			//�����R�[�h�̕K�{���̓`�F�b�N
		    //super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "cd_busho"),"�����R�[�h");
			//�����R�[�h�̓��͌����`�F�b�N
		    super.sizeCheckLen(checkData.GetValueStr("SA510", 0, 0, "cd_genryo"),"�R�[�h",11);
			//�������̓��͌����`�F�b�N
		    super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA510", 0, 0, "nm_genryo"),"���O",30,60);
			//�I���y�[�W�ԍ��̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA510", 0, 0, "num_selectRow"),"�I���y�[�W�ԍ�");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
