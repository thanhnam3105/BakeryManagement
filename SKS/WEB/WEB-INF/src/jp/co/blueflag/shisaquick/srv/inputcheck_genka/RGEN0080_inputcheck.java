package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �������Z�@���Z�R�[�h�������߯����� : 
 * 		���Z�R�[�h�����̲��߯�����
 * 
 * @author Nishigawa
 * @since 2009/11/10
 */
public class RGEN0080_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�@���Z�R�[�h�������߯������R���X�g���N�^
	 */
	public RGEN0080_inputcheck() {
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
			
			
			//FGEN0080�̃C���v�b�g�`�F�b�N���s���B
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
	 * FGEN0080�̃C���v�b�g�`�F�b�N
	 *  : FGEN0080�̃C���v�b�g�`�F�b�N���s���B
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

		String strKinoNm = "FGEN0080";
		String strTableNm = "table";
		
		try {
			
			
			
			//�񂪑I������Ă��邩���m�F
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �����H��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//�K�{�`�F�b�N
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojyo"), "�����H��");
					//���ރR�[�h���͒l�`�F�b�N�i���l�`�F�b�N�j
					super.numberCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shizai"),"���ރR�[�h");
					
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			
		}
	}
	
}
