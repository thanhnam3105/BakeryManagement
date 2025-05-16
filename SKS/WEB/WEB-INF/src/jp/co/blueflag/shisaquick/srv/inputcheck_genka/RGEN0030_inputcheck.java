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
 * �������Z�@�Čv�Z���߯����� : 
 * 		�������Z�A�Čv�Z�̲��߯�����
 * 
 * @author Nishigawa
 * @since 2009/11/19
 */
public class RGEN0030_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �Čv�Z���߯������R���X�g���N�^
	 */
	public RGEN0030_inputcheck() {
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
			
			
			//FGEN0030�̃C���v�b�g�`�F�b�N���s���B
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
	 * �Čv�Z���ڃ`�F�b�N
	 *  : FGEN0030�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�e�[�u���F[kihon]�̃C���v�b�g�`�F�b�N
			this.kihonInsertValueCheck(checkData);
			
			//�e�[�u���F[genryo]�̃C���v�b�g�`�F�b�N
			this.genryoInsertValueCheck(checkData);
			
			//�e�[�u���F[keisan]�̃C���v�b�g�`�F�b�N
			this.keisanInsertValueCheck(checkData);
			
			//�e�[�u���F[shizai]�̃C���v�b�g�`�F�b�N
			this.shizaiInsertValueCheck(checkData);

			
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
	 * �e�[�u���F[kihon]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "kihon";
		
		try {
			
			
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			
		}
	}
	
	/**
	 * �e�[�u���F[genryo]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void genryoInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "genryo";
		String strAddMsg = "";
		
		try {
			
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	/**
	 * �e�[�u���F[keisan]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void keisanInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "keisan";
		String strAddMsg = "";
		
		try {
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "�y���Z���ځz [ �񐔁F" + ( i + 1 ) + "]";
				
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	/**
	 * �e�[�u���F[shizai]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shizaiInsertValueCheck(RequestData checkData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "shizai";
		String strAddMsg = "";
		
		try {
			
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "�y���ޏ��z [ �s���F" + ( i + 1 ) + "]";
				
				
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;
			
		}
		
	}
	
	
}
