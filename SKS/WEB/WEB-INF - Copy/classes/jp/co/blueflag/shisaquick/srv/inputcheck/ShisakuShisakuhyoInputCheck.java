package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yJW120�z ����f�[�^��ʎ���\�o�̓C���v�b�g�`�F�b�N : ����f�[�^��ʂ̎���\�o�͎��Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author k-katayama
 * @since 2009/05/20
 */
public class ShisakuShisakuhyoInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ����f�[�^��� ����f�[�^��ʎ���\�o�̓C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ShisakuShisakuhyoInputCheck() {
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

			// �@�\ID�FSA460�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			OutputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �o�͏������ڃ`�F�b�N : �@�\ID�FSA460 ����\�̏o�͏����̍��ڃ`�F�b�N���s���B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void OutputKeyCheck(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s���B
			String strKinoNm = "SA460";
			
			// �@ �K�{���ڂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B(���b�Z�[�W�R�[�h:E000001)
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, 0); i++ ) {	
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "cd_shain"),"����R�[�h(�Ј��R�[�h)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "nen"),"����R�[�h(�N)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "no_oi"),"����R�[�h(�ǔ�)");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "seq_shisaku"),"����SEQ");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "cd_kotei"),"�H��CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, i, "seq_kotei"),"�H��SEQ");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
}
