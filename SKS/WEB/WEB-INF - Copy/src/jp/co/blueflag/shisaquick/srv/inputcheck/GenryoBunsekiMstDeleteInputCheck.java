package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �������͏��}�X�^�폜�����C���v�b�g�`�F�b�N : �������͏��}�X�^�����e��ʂō폜�{�^���������̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/03
 */
public class GenryoBunsekiMstDeleteInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������͏��}�X�^�폜�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GenryoBunsekiMstDeleteInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
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

			// �@�\ID�FSA370�̃C���v�b�g�`�F�b�N�i�폜�L�[�l�`�F�b�N�j���s���B
			deleteKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �C���v�b�g�`�F�b�N�i�폜�L�[�l�`�F�b�N�j
	 *  : SA370�̃C���v�b�g�`�F�b�N�i�폜�L�[�l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void deleteKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			super.hissuInputCheck(checkData.GetValueStr("SA370", 0, 0, "cd_kaisha"), "��ЃR�[�h");

			// �����R�[�h�K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			String strGenryoCD = checkData.GetValueStr("SA370", 0, 0, "cd_genryo");
			super.noSelectGyo(strGenryoCD);
			
			// �����R�[�h�̐擪1������"N"�̏ꍇ�A�X���[����B
			if (strGenryoCD.charAt(0) != 'N') {
				super.noDeleteKizonGenryo();
			}

			// �p�~�t���O��1�̏ꍇ�A�X���[����B
			if (!checkData.GetValueStr("SA370", 0, 0, "flg_haishi").equals("1")) {
				super.noDeleteNoHaishiGenryo();
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}