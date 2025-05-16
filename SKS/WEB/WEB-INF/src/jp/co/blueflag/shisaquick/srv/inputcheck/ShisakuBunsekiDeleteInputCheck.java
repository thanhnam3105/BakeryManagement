package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �yJW730�z ����\���̓f�[�^�m�F��ʃ��N�G�X�g�폜 �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuBunsekiDeleteInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuBunsekiDeleteInputCheck() {
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

			//SA370�̃C���v�b�g�`�F�b�N���s���B
			DeleteKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �폜�L�[�l�`�F�b�N
	 *  : SA370�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void DeleteKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//��ЃR�[�h�̕K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_kaisha"),"��ЃR�[�h");
		    //�����R�[�h�K�{���̓`�F�b�N
		    super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_genryo"),"�����R�[�h");
		    //�����R�[�h�̐擪1������"N"�̏ꍇ
		    if ( checkData.GetValueStr("SA370", 0, 0, "cd_genryo").charAt(0) != 'N' ) {
		    	this.em.ThrowException(ExceptionKind.���Exception, "E000005", "", "", "");
		    }
		    //�p�~�t���O��1�̏ꍇ
		    if ( !checkData.GetValueStr("SA370", 0, 0, "flg_haishi").equals("1") ) {
		    	this.em.ThrowException(ExceptionKind.���Exception, "E000004", "", "", "");
		    }
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
