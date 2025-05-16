package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yJW740�z ����\���̓f�[�^�m�F��ʉh�{�v�Z1�i�ܒ��j�o�̓C���v�b�g�`�F�b�N
 *  : ����\���̓f�[�^�m�F��ʂ̉h�{�v�Z�i�ܒ��j�o�͎��Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author k-katayama
 * @since 2009/05/22
 */
public class ShisakuBunsekiEiyouKeisan1InputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ����\���̓f�[�^�m�F��ʉh�{�v�Z�i�ܒ��j�o�̓C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public ShisakuBunsekiEiyouKeisan1InputCheck() {
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

			// �@�\ID�FSA430�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			outputKeyCheckEiyou1(checkData);

			// �@�\ID�FSA440�̃C���v�b�g�`�F�b�N�i�o�͍��ڃ`�F�b�N�j���s���B
			outputKeyCheckEiyou2(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �o�͏������ڃ`�F�b�N
	 *  : �@�\ID�FSA430�̉h�{�v�Z1�i�ܒ�����j�̏o�͏����̍��ڃ`�F�b�N���s���B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheckEiyou1(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s���B
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �o�͏������ڃ`�F�b�N
	 *  : �@�\ID�FSA440�̉h�{�v�Z2�i�ܒ��j�̏o�͏����̍��ڃ`�F�b�N���s���B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void outputKeyCheckEiyou2(RequestData checkData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		try {
			// ���[�̏o�͂ɕK�v�ȍ��ڂ̓��̓`�F�b�N���s���B
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
}
