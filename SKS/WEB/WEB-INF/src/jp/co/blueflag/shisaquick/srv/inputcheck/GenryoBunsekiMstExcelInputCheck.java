package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �������͏��}�X�^Excel�����C���v�b�g�`�F�b�N : �������͏��}�X�^�����e��ʂ�Excel�{�^���������̊e���ڂ̓��͒l�`�F�b�N���s���B
 * 
 * @author itou
 * @since 2009/04/03
 */
public class GenryoBunsekiMstExcelInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������͏��}�X�^Excel�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public GenryoBunsekiMstExcelInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �@�\ID�FSA360�̃C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j���s���B
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

			// �@�\ID�FSA360�̃C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j���s���B
			outputKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �C���v�b�g�`�F�b�N�i�o�͏����l�`�F�b�N�j
	 *  : CSV�t�@�C���o�͏����̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void outputKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ArrayList<Object> lstParam = null;
		
		try {
			lstParam = new ArrayList<Object>();

			//�@�Ώی����̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			lstParam.add(checkData.GetValueStr("SA360", 0, 0, "kbn_joken1"));
			lstParam.add(checkData.GetValueStr("SA360", 0, 0, "kbn_joken2"));
			super.hissuInputCheck(lstParam, "�Ώی���");
			
			// �����R�[�h�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			super.sizeCheckLen(checkData.GetValueStr("SA360", 0, 0, "cd_genryo"),"�����R�[�h",11);
			
			// �������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA360", 0, 0, "nm_genryo"),"������",30,60);
			
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			super.hissuInputCheck(checkData.GetValueStr("SA360", 0, 0, "cd_kaisha"),"���");
			
			// �H��R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//			super.hissuInputCheck(checkData.GetValueStr("SA360", 0, 0, "cd_kojo"),"�H��");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
			lstParam = null;
			
		}
	}
}
