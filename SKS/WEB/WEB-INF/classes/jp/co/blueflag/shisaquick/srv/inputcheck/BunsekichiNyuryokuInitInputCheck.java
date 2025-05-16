package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���͒l���͏����\���C���v�b�g�`�F�b�N : ���͒l���͉�ʂ̏����\�����Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/06
 */
public class BunsekichiNyuryokuInitInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ���͒l���͏����\���C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public BunsekichiNyuryokuInitInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �@�\ID�FSA360�̃C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j���s���B
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

			// �@�\ID�FSA400�̃C���v�b�g�`�F�b�N�i���������l�`�F�b�N�j���s���B
			searchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �C���v�b�g�`�F�b�N�i���������̓��͒l�`�F�b�N�j
	 *  : �@�\ID�FSA400�̃C���v�b�g�`�F�b�N�i���������l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�����敪��2�i�ڍׁj�̏ꍇ
			if (checkData.GetValueStr("SA400", 0, 0, "kbn_shori").equals("2")) {
				// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr("SA400", 0, 0, "cd_kaisha"),"���");
				
				// �H��R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr("SA400", 0, 0, "cd_busho"),"�H��");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
