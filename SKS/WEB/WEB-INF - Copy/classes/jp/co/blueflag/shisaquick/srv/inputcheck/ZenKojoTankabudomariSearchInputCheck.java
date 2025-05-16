package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �S�H��P���������������C���v�b�g�`�F�b�N : 
 * 		�S�H��P��������ʂŌ����{�^���������̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author jinbo
 * @since 2009/05/20
 */
public class ZenKojoTankabudomariSearchInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �S�H��P���������������C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ZenKojoTankabudomariSearchInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �e�f�[�^�`�F�b�N�������Ǘ�����B
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

			// �@�\ID�FSA790�̃C���v�b�g�`�F�b�N�i�S�H��P���������������l�`�F�b�N�j���s���B
			genryoSearchKeyCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �S�H��P���������̌��������̓��͒l�`�F�b�N���s���B
	 *  : �@�\ID�FSA790�̃C���v�b�g�`�F�b�N�i�S�H��P���������������l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "cd_kaisha"), "���");
			//���O�����͂���Ă���ꍇ
			if (!checkData.GetValueStr("SA790", 0, 0, "nm_genryo").equals("")) {
				// ���O�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
				super.sizeCheckLen(checkData.GetValueStr("SA790", 0, 0, "nm_genryo"), "���O", 60);
			}
			// �Ώۃf�[�^�敪�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "kbn_data"), "�Ώۃf�[�^");
			// �o�͍��ڂ̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			super.hissuInputCheck(checkData.GetValueStr("SA790", 0, 0, "item_output"), "�o�͍���");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
