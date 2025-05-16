package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �������͏��}�X�^��ЃR���{�I���C���v�b�g�`�F�b�N
 *  : �������͏��}�X�^�����e��ʂŉ�Ѓh���b�v�_�E���I�����̊e���ڂ̓��͒l�`�F�b�N���s���B
 * @author itou
 * @since  2009/04/03
 */
public class GenryouBunsekiMstKaishaInputCheck extends InputCheck {
	
	/**
	 * �R���X�g���N�^
	 *  : �������͏��}�X�^��ЃR���{�I���C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GenryouBunsekiMstKaishaInputCheck() {
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

			//SA180�̃C���v�b�g�`�F�b�N�i�H��ꗗ���擾�L�[�`�F�b�N�j���s���B
			kojoListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �H��ꗗ���R���{�擾���C���v�b�g�`�F�b�N
	 *  : SA180�̃h���b�v�_�E�����X�g�p�̌����L�[�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kojoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F��ЃR�[�h
			super.hissuInputCheck(checkData.GetValueStr("SA180", 0, 0, "cd_kaisha"),"���");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
