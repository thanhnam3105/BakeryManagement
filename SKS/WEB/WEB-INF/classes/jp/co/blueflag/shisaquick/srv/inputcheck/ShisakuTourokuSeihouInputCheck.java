package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW050�z ����f�[�^��ʃ��N�G�X�g�o�^�i���@�R�s�[�j �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuTourokuSeihouInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuTourokuSeihouInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * @param checkData : ���N�G�X�g�f�[�^
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

			//SA500�̃C���v�b�g�`�F�b�N���s���B
			SeihoNoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���@���o�^���ڃ`�F�b�N
	 *  : SA500�̃C���v�b�g�`�F�b�N���s���B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SeihoNoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�@ ����CD�|�Ј�CD�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "cd_shain"),"����R�[�h�i�Ј��R�[�h�j");
		    //�A ����CD�|�N�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "nen"),"����R�[�h�i�N�j");
		    //�B ����CD�|�ǔԂ̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_oi"),"����R�[�h�i�ǔԁj");
		    //�C ����SEQ�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuCodeCheck(checkData.GetValueStr("SA500", 0, 0, "seq_shisaku"),"�����");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
