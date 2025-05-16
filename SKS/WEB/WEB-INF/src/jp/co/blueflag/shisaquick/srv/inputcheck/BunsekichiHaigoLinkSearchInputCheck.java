package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���͒l���̓C���v�b�g�`�F�b�N : ���͒l���͉�ʂ̘A�g�ݒ�i�z�������N�����j�̓��͒l�`�F�b�N���s���B
 *
 * @author TT Kitazawa
 * @since 2016/06/07
 */
public class BunsekichiHaigoLinkSearchInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ���͒l���́F�z�������N�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public BunsekichiHaigoLinkSearchInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �@�\ID�FFGEN2260�̃C���v�b�g�`�F�b�N���s���B
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

			// �@�\ID�FFGEN2260�̃C���v�b�g�`�F�b�N�i���������l�`�F�b�N�j���s���B
			searchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �C���v�b�g�`�F�b�N�i���������̓��͒l�`�F�b�N�j
	 *  : �@�\ID�FFGEN2260�̃C���v�b�g�`�F�b�N�i���������l�`�F�b�N�j���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void searchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�������[�h��0�i�������j�̏ꍇ
			if (checkData.GetValueStr(1, 0, 0, "syoriMode").equals("0")) {
				//��ЃR�[�h�A�����R�[�h�A�����R�[�h�i���͍��ڂłȂ��j

			} else {
				//�������[�h�i���캰�ނ��j�̏ꍇ
				// ����CD-�Ј�CD�̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "cd_shain"),"����m��_�Ј��R�[�h");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "cd_shain"),"����m��_�Ј��R�[�h");

				// ����CD-�N�̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "nen"),"����m��_�N");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "nen"),"����m��_�N");

				// ����CD-�ǔԂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "no_oi"),"����m��_�ǔ�");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "no_oi"),"����m��_�ǔ�");

				// �}�ԍ��̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
				super.hissuInputCheck(checkData.GetValueStr(1, 0, 0, "no_eda"),"����m��_�}��");
				super.numberCheck(checkData.GetValueStr(1, 0, 0, "no_eda"),"����m��_�}��");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
