package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class ShisakuSrvTeamInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^�ꗗ��� �`�[���R���{�ύX���C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuSrvTeamInputCheck() {
		//���N���X�̃R���X�g���N�^
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

			//SA250�̃C���v�b�g�`�F�b�N���s���B
			tantoListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �S���҃R���{�擾���C���v�b�g�`�F�b�N
	 *  : SA250�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void tantoListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
		    //�����敪��1�i�p�����[�^�g�p�j�̏ꍇ
			if (checkData.GetValueStr("SA250", 0, 0, "kbn_shori").equals("1")) {
				//�O���[�v�R�[�h�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA250", 0, 0, "cd_group"),"�����O���[�v");

				//�`�[���R�[�h�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA250", 0, 0, "cd_team"),"�����`�[��");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
