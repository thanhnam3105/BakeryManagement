package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �S���Ҍ����i�c�Ɓj���������C���v�b�g�`�F�b�N : �S���Ҍ����i�c�Ɓj��ʂŌ����{�^�������A�y�[�W�����N�N���b�N���̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class JW900_InputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �S���Ҍ������������C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public JW900_InputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : ���̓`�F�b�N�̊Ǘ����s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
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

			//FGEN2110�̃C���v�b�g�`�F�b�N���s���B
			tantoshaSearchCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �S���Ҍ����������ڃ`�F�b�N
	 *  : FGEN2110�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantoshaSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�S���Җ��̓��͌����`�F�b�N���s���B
		    super.sizeCheckLen(checkData.GetValueStr("FGEN2110", 0, 0, "nm_user"),"�S���Җ�",60);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}

