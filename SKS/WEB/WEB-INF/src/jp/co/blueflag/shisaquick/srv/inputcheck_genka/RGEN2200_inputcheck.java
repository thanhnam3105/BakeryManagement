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
 * �������Z�@�}�ԍ쐬���̲��߯����� : 
 * 		�������Z�A�}�ԍ쐬���̲��߯�����
 * 
 * @author Hisahori
 * @since 2012/4/10
 */
public class RGEN2200_inputcheck extends InputCheck {
	
	//�yQP@00342�z
	String nm_edaShisan = "";

	/**
	 * �R���X�g���N�^ : �������Z�A���ޏ��\��ͯ�ް���߯������R���X�g���N�^
	 */
	public RGEN2200_inputcheck() {
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
			
			//�yQP@00342�z���N�G�X�g�f�[�^���畔���t���O�擾
			nm_edaShisan = toString(checkData.GetValueStr("FGEN2180", "kihon", 0, "eda_nm_shisaku"));

			//FGEN2180�̃C���v�b�g�`�F�b�N���s���B
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
	 * �o�^���ڃ`�F�b�N
	 *  : FGEN2180�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�C���v�b�g�`�F�b�N
			this.kihonInsertValueCheck(checkData);
			
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
	 * ���Z���얼 ���̓e�L�X�g�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN2180";

		String strTest = "";
		
		try {
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �}�Ԏ��얼
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

			strTest = checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku");
			
				if( !toString( checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku")).equals("") ){
					
					
					//�����`�F�b�N�i20���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "eda_nm_shisaku"),"�}�Ԏ��얼",20);
					
				}
		
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			
		}
	}
}
