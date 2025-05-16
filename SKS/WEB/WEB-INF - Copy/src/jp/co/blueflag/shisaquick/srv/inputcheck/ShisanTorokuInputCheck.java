package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yJW860�z �������Z�o�^�C���v�b�g�`�F�b�N
 *  : �������Z�o�^���Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * @author k-katayama
 * @since 2009/06/24
 */
public class ShisanTorokuInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�o�^�C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ShisanTorokuInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * 
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
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			// �@�\ID�FSA820�̃C���v�b�g�`�F�b�N�i�o�^�L�[���ڃ`�F�b�N�j���s���B
			rirekiKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}

	/**
	 * ���Z�m�藚��o�^�������ڃ`�F�b�N : �@�\ID�FSA820�̍��ڃ`�F�b�N���s���B
	 * 
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void rirekiKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		
		String strKinoId = "SA820";
		
		try {
			// �o�^�p�L�[���ڂ̓��̓`�F�b�N���s��

			// �@ �K�{���ڂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B(���b�Z�[�W�R�[�h:E000001)
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "cd_shain"),"����R�[�h�i�Ј��R�[�h�j");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "nen"),"����R�[�h�i�N�j");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "no_oi"),"����R�[�h�i�ǔԁj");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "seq_shisaku"),"����SEQ");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoId, 0, 0, "sort_rireki"),"������");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoId = null;

		}
		
	}
	
}
