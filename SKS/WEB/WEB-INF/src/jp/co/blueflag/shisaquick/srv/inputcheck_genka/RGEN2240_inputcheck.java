package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �������Z�i�c�Ɓj�@������߯����� :
 * 		����̲��߯�����
 *
 * @author TT.Kitazawa
 * @since 2015/03/03
 */
public class RGEN2240_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�i�c�Ɓj�@������߯������R���X�g���N�^
	 */
	public RGEN2240_inputcheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  execInputCheck�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

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


			//FGEN2240�̃C���v�b�g�`�F�b�N���s���B
			insertValueCheck(checkData);



		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                           insertValueCheck�i���߯������J�n�J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ������ڃ`�F�b�N
	 *  : FGEN2240�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�e�[�u���F[table]�̃C���v�b�g�`�F�b�N
			this.tableInsertValueCheck(checkData);



		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                              TableValueCheck�i�eð��ٲ��߯������j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �e�[�u���F[table]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tableInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN2240";
		String strTableNm = "table";

		try {
			//�񂪑I������Ă��邩���m�F
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   ����SEQ
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//�K�{�`�F�b�N
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"), "�����", "E000404");

			}

			//�V��ȏ�I������Ă��Ȃ������m�F
			if(checkData.GetRecCnt(strKinoNm, strTableNm) > 6){
				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000218", "6", "", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

}
