package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yJW130�z ����f�[�^��ʃT���v���������̓C���v�b�g�`�F�b�N : ����f�[�^��ʂ̃T���v���������o�͎��Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author k-katayama
 * @since 2009/05/20
 */
public class ShisakuSampleSetumeiInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ����f�[�^��ʃT���v���������̓C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ShisakuSampleSetumeiInputCheck() {
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

			// �@�\ID�FSA450�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			OutputKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * �o�͏������ڃ`�F�b�N : �@�\ID�FSA450�̃T���v���������̏o�͏����̍��ڃ`�F�b�N���s���B
	 * 
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void OutputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s���B
			
			// �@�F�K�{���ڂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "cd_shain"),"����CD-�Ј�CD");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "nen"),"����CD-�N");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "no_oi"),"����CD-�ǔ�");
		    super.hissuInputCheck(checkData.GetValueStr("SA450", 0, 0, "seq_shisaku1"),"����SEQ");
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
