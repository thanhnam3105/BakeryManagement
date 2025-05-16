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
 * ���N�G�X�gID�FRGEN1070�@�ގ��i����CSV�t�@�C�������C���v�b�g�`�F�b�N : 
 * 		�o�͎���ʏ��f�[�^���݃`�F�b�N
 * 
 * @author Nishigawa
 * @since 2009/11/10
 */
public class RGEN1070_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public RGEN1070_inputcheck() {
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
			
			//FGEN1050�̃C���v�b�g�`�F�b�N���s���B
			listExistenceCheck(checkData);
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
	
	
	/**
	 * ���N�G�X�gID�FRGEN1070�@�t�@���N�V����ID�FFGEN1050�̃��N�G�X�g�f�[�^��[seihin][shizai][sentaku]�e�[�u�����f�[�^���݃`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void listExistenceCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN1050";
		String strSeihinList = "seihin";
		String strSizaiList = "shizai";
		String strSentakuList = "sentaku";
		int recCnt = 0;
		
		try {
			
			//���i�ꗗ�̃��R�[�h�������Z
			recCnt += checkData.GetRecCnt(strKinoNm, strSeihinList);
			
			//���ވꗗ�̃��R�[�h�������Z
			recCnt += checkData.GetRecCnt(strKinoNm, strSizaiList);
			
			//�I���ꗗ�̃��R�[�h�������Z
			recCnt += checkData.GetRecCnt(strKinoNm, strSentakuList);
			
			//�ꗗ���R�[�h�̍��v��0�̏ꍇ
			if(recCnt == 0){
				
				//�s�����X���[����B
		    	em.ThrowException(
		    			ExceptionKind.���Exception, 
		    			"E000220", 
		    			"", 
		    			"", 
		    			"");
				
			}
			
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strSeihinList = null;
			strSizaiList = null;
			strSentakuList = null;
			
		}
	}
	
}
