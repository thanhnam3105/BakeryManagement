package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yJW830�z �������Z�\�o�̓C���v�b�g�`�F�b�N
 *  : �������Z�\�o�͎��Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * @author k-katayama
 * @since 2009/05/21
 */
public class GenkaShisanHyoInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�\�o�̓C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GenkaShisanHyoInputCheck() {
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

			// �@�\ID�FSA800�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			outputKeyCheck(checkData);
			
			// �@�\ID�FSA870�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			shisanNoKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}

	/**
	 * �������Z�\�o�͏������ڃ`�F�b�N : �@�\ID�FSA800�̌������Z�\�̏o�͏����̍��ڃ`�F�b�N���s���B
	 * 
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s��

			// �@ �K�{���ڂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B(���b�Z�[�W�R�[�h:E000001)
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "cd_shain"),"����R�[�h�i�Ј��R�[�h�j");
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "nen"),"����R�[�h�i�N�j");
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "no_oi"),"����R�[�h�i�ǔԁj");
		    //�����1����I������Ă��Ȃ��ꍇ�A�`�F�b�N���|����B
		    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "seq_shisaku1"),"����SEQ");

		    
		    // �A �H�����u���̑������t�p�^�[���v�̏ꍇ�A�d�オ��d�ʂ̕K�{�`�F�b�N���s��
		    if ( checkData.GetValueStr("SA800", 0, 0, "kotei_value").equals("2") ) {
		    	
			    if ( !checkData.GetValueStr("SA800", 0, 0, "seq_shisaku1").isEmpty() ) {
				    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "juryo_shiagari1"),"�d�オ��d��");
				    
			    }
			    if ( !checkData.GetValueStr("SA800", 0, 0, "seq_shisaku2").isEmpty() ) {
				    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "juryo_shiagari2"),"�d�オ��d��");
				    
			    }
			    if ( !checkData.GetValueStr("SA800", 0, 0, "seq_shisaku3").isEmpty() ) {
				    super.hissuInputCheck(checkData.GetValueStr("SA800", 0, 0, "juryo_shiagari3"),"�d�オ��d��");
				    
			    }
		    
		    }
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}

	/**
	 * �������ZNo�X�V�������ڃ`�F�b�N
	 *  : �@�\ID�FSA870�̌������ZNo�̍X�V�����̍��ڃ`�F�b�N���s���B
	 * 
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisanNoKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s��

			// �@ �K�{���ڂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B(���b�Z�[�W�R�[�h:E000001)
		    super.hissuInputCheck(checkData.GetValueStr("SA870", 0, 0, "cd_shain"),"����R�[�h�i�Ј��R�[�h�j");
		    super.hissuInputCheck(checkData.GetValueStr("SA870", 0, 0, "nen"),"����R�[�h�i�N�j");
		    super.hissuInputCheck(checkData.GetValueStr("SA870", 0, 0, "no_oi"),"����R�[�h�i�ǔԁj");
		    //�����1����I������Ă��Ȃ��ꍇ�A�`�F�b�N���|����B
		    super.hissuInputCheck(checkData.GetValueStr("SA870", 0, 0, "seq_shisaku1"),"����SEQ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}

}
