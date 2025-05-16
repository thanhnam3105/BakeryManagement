package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �C���v�b�g�`�F�b�N
 *  : ���N�G�X�g�p�����[�^�[�iInput�j�̃`�F�b�N���s���B�@�����N�G�X�g�P�ʂŔh��
 * @author TT.furuta
 * @since  2009/03/25
 */
public class InputCheck extends ObjectBase{

	//���[�U�[���Ǘ�
	protected UserInfoData userInfoData = null;

	/**
	 * �R���X�g���N�^
	 *  : �C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public InputCheck() {
		super();
		
	}

	/**
	 * �C���v�b�g�`�F�b�N�̎���
	 *  : �h����ŃI�[�o�[���C�h���āA�C���v�b�g�`�F�b�N�̎������L�q����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 */
	public void execInputCheck(
			RequestData reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//���[�U�[���ޔ�
		this.userInfoData = _userInfoData;

		//�h����ɂĎ���
	}
	
	/**
	 * �K�{���̓`�F�b�N : ���̓p�����[�^�[���I������Ă��邩�`�F�b�N���s���B
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuInputCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuInputCheck(strChkPrm,strChkName,"");
	}
	protected void hissuInputCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);
			
			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if (msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000200", strChkName, "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * �K�{���̓`�F�b�N : ���̓p�����[�^�[���I������Ă��邩�`�F�b�N���s���B
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuCodeCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuCodeCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuCodeCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);
			
			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if (msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000207", strChkName, "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * �K�{���̓`�F�b�N : �̔ԏ������ݒ肢�邩�`�F�b�N���s���B
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuSaibanCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuSaibanCheck( strChkPrm,  strChkName , "");
	}
	protected void hissuSaibanCheck(String strChkPrm, String strChkName , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				
				if (msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000211", strChkName, "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}

			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * �K�{���̓`�F�b�N : JNLP�t�@�C���̍쐬�ɐݒ肳��Ă��邩�`�F�b�N���s���B
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuJNLPSetCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuJNLPSetCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuJNLPSetCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				
				if (msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000300", strChkName, "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}

			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}

	/**
	 * �K�{���̓`�F�b�N : ���̓p�����[�^�[���I������Ă��邩�`�F�b�N���s���B
	 * @param lstChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuInputCheck(ArrayList<Object> lstChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuInputCheck( lstChkPrm,  strChkName, "");
	}
	protected void hissuInputCheck(ArrayList<Object> lstChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		boolean blnFlg = false;
		
		try {

			for (int i = 0; i < lstChkPrm.size(); i++) {
				//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
				checkSingleQuotation(lstChkPrm.get(i), strChkName);

				// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
				if (!(lstChkPrm.get(i).toString().equals(null) || lstChkPrm.get(i).toString().equals(""))) {

					blnFlg = true;
					break;
				}
			}

			if (blnFlg == false) {
				if(msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000200", strChkName, "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	
	/**
	 * �K�{���̓`�F�b�N : �r�����䂪�ݒ肳��Ă��邩�`�F�b�N���s���B
	 * �iE000213:"�r�����䎸�s�F$1���ݒ�"�j
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void hissuExclusiveControlCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		hissuExclusiveControlCheck( strChkPrm,  strChkName, "");
	}
	protected void hissuExclusiveControlCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if(msgNo.equals("")){
					
				}else{
					
				}
				
				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000213", strChkName, "", "");
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	/**
	 * ���͌����`�F�b�N
	 *  : ���̓p�����[�^�[�����̃`�F�b�N���s���B(�S�p�A���p����ʂ��Ȃ��B�j
	 * @param strChkPrm	: �`�F�b�N�p�����[�^�[
	 * @param strParam : ���b�Z�[�W�p�����[�^
	 * @param iMaxLen : �ő包��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeCheckLen(String strChkPrm, String strParam, int iMaxLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeCheckLen( strChkPrm,  strParam,  iMaxLen, "");
	}
	protected void sizeCheckLen(String strChkPrm, String strParam, int iMaxLen, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {					
	 
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.length();
			// �@:�ő包����p���A���͌����`�F�b�N���s���B
			if (iMaxLen < chkLen) {
				

				if(msgNo.equals("")){
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							"E000212",
							strParam,
							Integer.toString(iMaxLen),
							"");
					
				}else{
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							msgNo,
							strParam,
							Integer.toString(iMaxLen),
							"");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * ���͌����`�F�b�N
	 *  : ���̓p�����[�^�[�����̃`�F�b�N���s���B
	 * @param strChkPrm	: �`�F�b�N�p�����[�^�[
	 * @param strParam : ���b�Z�[�W�p�����[�^
	 * @param iHalfLen : ���p�ő包��
	 * @param iFullLen : �S�p�ő包��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeHalfFullLengthCheck(String strChkPrm, String strParam, int iHalfLen, int iFullLen ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeHalfFullLengthCheck( strChkPrm,  strParam,  iHalfLen,  iFullLen , "");
	}
	protected void sizeHalfFullLengthCheck(String strChkPrm, String strParam, int iHalfLen, int iFullLen , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {		

		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// �@:�ő包����p���A���͌����`�F�b�N���s���B
			if (iHalfLen < chkLen) {

				if(msgNo.equals("")){
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							"E000201",
							strParam,
							Integer.toString(iHalfLen),
							Integer.toString(iFullLen));
					
				}else{
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							msgNo,
							strParam,
							Integer.toString(iHalfLen),
							Integer.toString(iFullLen));
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * ���͌����`�F�b�N
	 *  : ���̓p�����[�^�[�����̃`�F�b�N���s���B
	 * @param strChkPrm	: �`�F�b�N�p�����[�^�[
	 * @param strParam : ���b�Z�[�W�p�����[�^
	 * @param iHalfLen : ���p�ő包��
	 * @param iFullLen : �S�p�ő包��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeHalfFullLengthCheck_hankaku(String strChkPrm, String strParam, int iHalfLen, int iFullLen ) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeHalfFullLengthCheck_hankaku( strChkPrm,  strParam,  iHalfLen,  iFullLen , "");
	}
	protected void sizeHalfFullLengthCheck_hankaku(String strChkPrm, String strParam, int iHalfLen, int iFullLen , String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {		

		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// �@:�ő包����p���A���͌����`�F�b�N���s���B
			if (iHalfLen < chkLen) {

				if(msgNo.equals("")){
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							"E000411",
							strParam,
							Integer.toString(iHalfLen),
							"");
					
				}else{
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							msgNo,
							strParam,
							Integer.toString(iHalfLen),
							"");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}
	
	/**
	 * ���t�`�F�b�N
	 *  : ���̓p�����[�^�[�̓��t�̐������`�F�b�N���s���B
	 * @param strChkPrm : �`�F�b�N�p�����[�^�[
	 * @param strChckName	: ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void dateCheck(String strChkPrm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		dateCheck( strChkPrm,  strChkName, "");
	}
	protected void dateCheck(String strChkPrm, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
				
		try {
			String[] strTemps = null;
			
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

		    //strChkPrm = strChkPrm.replace('-', '/');
		    
			strChkPrm = cnvDateFormat(strChkPrm);
			strTemps = strChkPrm.split("/");
			
		    DateFormat format = DateFormat.getDateInstance();

		    // ���t/������͂������ɍs�����ǂ�����ݒ肷��B
		    format.setLenient(false);

		    //���t�̐������`�F�b�N
		    try {
		        format.parse(strChkPrm);
		        
		    } catch (Exception e) {
		    	

				if(msgNo.equals("")){
			    	//���͓��t�s�����X���[����B
		    		em.ThrowException(ExceptionKind.���Exception, "E000202", strChkName, "", "");

				}else{
			    	//���͓��t�s�����X���[����B
			    	em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");

				}
				
		    }

		    //���t�͈̓`�F�b�N�iSQLServer�̓��t�^�́A9999�N�܂ł����Ή����Ă��Ȃ����߁j
	    	if(strTemps[0].length() > 4){
				//���͓��t�s�����X���[����B
	    		em.ThrowException(ExceptionKind.���Exception, "E000203", strChkName, "2000�N01��01��", "9999�N12��31��");

	    	}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}

	/**
	 * �����ő�l�`�F�b�N
	 *  : �����̍ő�l�`�F�b�N���s���B
	 * @param intRowValue		: �����
	 * @param intMaxRowValue	: �ő�s��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void maxRowCheck(int intRowValue, int intMaxRowValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		maxRowCheck(intRowValue, intMaxRowValue, strChkName, "");
	}
	protected void maxRowCheck(int intRowValue, int intMaxRowValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {

			if (intRowValue > intMaxRowValue){

				if(msgNo.equals("")){
			    	//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			"E000219", 
			    			Integer.toString(intMaxRowValue),
			    			"",
			    			"");
					
				}else{
			    	//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Double.toString(intMaxRowValue),
			    			"");
					
				}
			}
			
		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
		
	}
	
	/**
	 * �����͈̓`�F�b�N
	 *  : �������ڂ͈̔̓`�F�b�N���s���B(���[�s��)
	 * @param dblChkPrm	   : �`�F�b�N�p�����[�^�[
	 * @param dblMinValue : �����l
	 * @param dblMaxValue : ����l
	 * @param strChkName  : ���ږ� 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void rangeNumCheck(double dblChkPrm, double dblMinValue, double dblMaxValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		rangeNumCheck( dblChkPrm,  dblMinValue,  dblMaxValue,  strChkName, "");
	}
	protected void rangeNumCheck(double dblChkPrm, double dblMinValue, double dblMaxValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
				
		try {
			
			//�@�F����l�A�����l��p���A�`�F�b�N�p�����[�^�[�͈̔̓`�F�b�N���s���B
			if (dblMaxValue < dblChkPrm || dblChkPrm < dblMinValue){
			

				if(msgNo.equals("")){
			    	//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			"E000203", 
			    			strChkName, 
			    			Double.toString(dblMinValue), 
			    			Double.toString(dblMaxValue));
					
				}else{
			    	//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Double.toString(dblMinValue), 
			    			Double.toString(dblMaxValue));
					
				}
				

			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * �����͈̓`�F�b�N
	 *  : �������ڂ͈̔̓`�F�b�N���s���B(���[�s��)
	 * @param dblChkPrm	   : �`�F�b�N�p�����[�^�[
	 * @param dblMinValue : �����l
	 * @param dblMaxValue : ����l
	 * @param strChkName  : ���ږ� 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void rangeNumCheck(BigDecimal dblChkPrm, BigDecimal dblMinValue, BigDecimal dblMaxValue, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		rangeNumCheck( dblChkPrm,  dblMinValue,  dblMaxValue,  strChkName, "");
	}
	protected void rangeNumCheck(BigDecimal dblChkPrm, BigDecimal dblMinValue, BigDecimal dblMaxValue, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
			
		boolean chkflg = false;
		
		try {
			
			
			//�����l���`�F�b�N�p�����[�^�[���傫���@���́@������
			if(dblChkPrm.compareTo(dblMinValue) > -1){
				
				//����l���`�F�b�N�p�����[�^�[���������@���́@������
				if(dblChkPrm.compareTo(dblMaxValue) < 1){
					
					chkflg = true;
				}
				
			}
			
			//���ʔ���
			if(chkflg){
				
			}else{

				if(msgNo.equals("")){
					//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			"E000203", 
			    			strChkName, 
			    			dblMinValue.toString(), 
			    			dblMaxValue.toString());
					
				}else{
					//�����͈͕s�����X���[����B
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			msgNo, 
			    			strChkName, 
			    			dblMinValue.toString(), 
			    			dblMaxValue.toString());
					
				}
				
			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * ���������`�F�b�N
	 *  : ���������̓��̓`�F�b�N���s���B�i�������̌����w�肪�Ȃ��̂�ErrorMessage�p�����[�^�ɂ͐��������͌������w�肷��̂��j
	 * @param dblChkPrm  : �`�F�b�N�p�����[�^�[
	 * @param iKetasu    : �����w�茅��
	 * @param strChkName : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void shousuRangeCheck(double dblChkPrm, int iKetasu, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iKetasu,  strChkName, "");
	}
	protected void shousuRangeCheck(double dblChkPrm, int iKetasu, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			String strDblData = Double.toString(dblChkPrm);
			
			// ���������`�F�b�N
			shousuRangeCheck(strDblData, iKetasu, strChkName, msgNo);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * ���������`�F�b�N
	 *  : ���������̓��̓`�F�b�N���s���B�i�������̌����w�肪�Ȃ��̂�ErrorMessage�p�����[�^�ɂ͐��������͌������w�肷��̂��j
	 * @param dblChkPrm  : �`�F�b�N�p�����[�^�[
	 * @param iKetasu    : �����w�茅��
	 * @param strChkName : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void shousuRangeCheck(String dblChkPrm, int iKetasu, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iKetasu,  strChkName, "");
	}
	protected void shousuRangeCheck(String dblChkPrm, int iKetasu, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			String strDblData = dblChkPrm;
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iKetasu < iSize){
			

				if(msgNo.equals("")){
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			"E000217", 
			    			strChkName, 
			    			Integer.toString(iKetasu),
			    			"");
					
				}else{
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			msgNo, 
			    			strChkName, 
			    			Integer.toString(iKetasu),
			    			"");
					
				}

			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * ���������`�F�b�N
	 *  : ���������̓��̓`�F�b�N���s���B
	 * @param dblChkPrm : �`�F�b�N�p�����[�^�[
	 * @param iIntegeruPartLen : �����w�茅��
	 * @param iDecimalPartLen : �����w�茅��
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iIntegeruPartLen,  iDecimalPartLen, "");
	}
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			String strDblData = Double.toString(dblChkPrm);

			//���������`�F�b�N
			shousuRangeCheck(strDblData, iIntegeruPartLen, iDecimalPartLen, strChkName);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * ���������`�F�b�N
	 *  : ���������̓��̓`�F�b�N���s���B
	 * @param dblChkPrm : �`�F�b�N�p�����[�^�[
	 * @param iIntegeruPartLen : �����w�茅��
	 * @param iDecimalPartLen : �����w�茅��
	 * @param strChkName : ���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void shousuRangeCheck(String dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		shousuRangeCheck( dblChkPrm,  iIntegeruPartLen,  iDecimalPartLen,  strChkName, "");
	}
	protected void shousuRangeCheck(String dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName, String msgNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			int iSize = dblChkPrm.substring(dblChkPrm.indexOf(".") + 1).length();
			
			if (iSize != 0 && iDecimalPartLen < iSize){
			

				if(msgNo.equals("")){
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			"E000204", 
			    			strChkName, 
			    			String.valueOf(iIntegeruPartLen), 
			    			String.valueOf(iDecimalPartLen));
					
				}else{
			    	em.ThrowException(
			    			ExceptionKind.���Exception, 
			    			msgNo, 
			    			strChkName, 
			    			String.valueOf(iIntegeruPartLen), 
			    			String.valueOf(iDecimalPartLen));
					
				}

			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
		
	/**
	 * ���[�U���擾ID�K�{�`�F�b�N
	 *  : ���[�U���擾�p�̃��[�UID�K�{���̓`�F�b�N���s���B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void userInfoCheck(RequestData reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		userInfoCheck( reqData, "");
	}
	protected void userInfoCheck(RequestData reqData, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�����敪���P�ȊO�̏ꍇ
			if (!reqData.GetValueStr("USERINFO", 0, 0, "kbn_shori").equals("1")) {
				//���[�UID�̎擾
				String strUserId = reqData.GetValueStr("USERINFO", 0, 0, "id_user");
				
				//���[�UID�̕K�{���̓`�F�b�N���s���B
				if (strUserId.equals(null) || strUserId.equals("")){

					if(msgNo.equals("")){
						em.ThrowException(ExceptionKind.���Exception,"E000205","","","");
						
					}else{
						em.ThrowException(ExceptionKind.���Exception,msgNo,"","","");
						
					}
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {
			
		}
	}
	
	/**
	 * ���̓`�F�b�N : �G���[���b�Z�[�W�\��:"�p�~����Ă��Ȃ������͍폜�ł��܂���B"
	 * (code="E000208")
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noDeleteNoHaishiGenryo()
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

			// ���͕s�����X���[����B
			noParamErrMsgOnlyDisp("E000208");

	}
	/**
	 * ���̓`�F�b�N : �G���[���b�Z�[�W�\��:"���������͍폜�ł��܂���B"
	 * (code="E000209")
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noDeleteKizonGenryo()
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

			// ���͕s�����X���[����B
			noParamErrMsgOnlyDisp("E000209");

	}
	/**
	 * ���̓`�F�b�N : �G���[���b�Z�[�W�\��:"�f�[�^�����I���ł��B\n�Ώۍs��I�����ĉ������B"
	 * (code="E000210")
	 * @param strGenryoCD 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noSelectGyo(String strChkPrm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		noSelectGyo( strChkPrm, "E000210");
	}
	protected void noSelectGyo(String strChkPrm, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				if(msgNo.equals("")){
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000210", "", "", "");
					
				}else{
					// �K�{���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, "", "", "");
					
				}
				
			}
		} catch (Exception e) {
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
		} finally {

		}
	}
	/**
	 * ���̓`�F�b�N : �p�����[�^�[���Ȃ����̓G���[���b�Z�[�W�̂ݕ\�����鋤�ʃ��\�b�h�B
	 * 
	 * @param strErrMsgCD
	 *            : �G���[���b�Z�[�W�R�[�h�iConst_Msg.xml���j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void noParamErrMsgOnlyDisp(String strErrMsgCD) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			// ���͕s�����X���[����B
			em.ThrowException(ExceptionKind.���Exception, strErrMsgCD, "", "", "");

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}

	/**
	 * ���͒l�`�F�b�N�@�F�@���l�`�F�b�N
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @param strChkName : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void numberCheck(Object obj,String strChkName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		numberCheck( obj, strChkName, "");
	}
	protected void numberCheck(Object obj,String strChkName, String msgNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{			
			//�yQP@10713�z2011/11/21 TT H.SHIMA ADD Start �󔒃X�y�[�X�`�F�b�N
			if(toString(obj).indexOf(" ") != -1){
				throw new NumberFormatException();
			}
			//�yQP@10713�z2011/11/21 TT H.SHIMA ADD End   �󔒃X�y�[�X�`�F�b�N
						
			Double.parseDouble(toString(obj));
			
		}catch(NumberFormatException e){

			if(msgNo.equals("")){
		    	em.ThrowException(
		    			ExceptionKind.���Exception,
		    			//�yQP@10713�z2011/11/15 TT H.SHIMA ADD Start
		    			//"E000216", 
		    			"E000221", 
		    			//�yQP@10713�z2011/11/15 TT H.SHIMA ADD End
		    			strChkName, 
		    			"", 
		    			"");
				
			}else{
		    	em.ThrowException(
		    			ExceptionKind.���Exception, 
		    			msgNo, 
		    			strChkName, 
		    			"", 
		    			"");
				
			}
			
			
		} catch (Exception ex) {
			em.ThrowException(ex, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
		
	}

	/**
	 * ���̓f�[�^�̏d���f�[�^�`�F�b�N���s��
	 * @param checkData
	 * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
	 * @param ValueNm : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		diffValueCheck( checkData,  KindId,  TableNm,  ValueNm,  strChkName, "");
	}
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName, String msgNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int intRecCount = checkData.GetRecCnt(KindId, TableNm);
		ArrayList<String> lstTantokaishaCd = new ArrayList<String>();
		// ��ʂ���擾�����I�����X�g���d�����Ă���ꍇ�A�X���[����B
		for (int i = 0; i < intRecCount; i++) {
			if (!lstTantokaishaCd.contains(prefixZeroCut(checkData.GetValueStr(KindId, TableNm, i, ValueNm)))) {
				lstTantokaishaCd.add(prefixZeroCut(checkData.GetValueStr(KindId,TableNm, i, ValueNm)));
			}else{
				if (msgNo.equals("")){
					// ���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000214", strChkName, "", "");
					
				}else{
					// ���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
			}
		}
		lstTantokaishaCd = null;
	}
	
	/**
	 * �z��̒����`�F�b�N
	 * @param checkData : �`�F�b�N�f�[�^
	 * @param iIndex : �z��̒����i�ŏ��l�F1�j
	 * @param strChkName : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void arrayCheck(String checkData, int iIndex, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		arrayCheck( checkData,  iIndex,  strChkName, "");
	}
	protected void arrayCheck(String checkData, int iIndex, String strChkName, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {

			String[] aryChkData = checkData.split("-");
			
			//�z��̒����`�F�b�N���s��
			if (aryChkData.length != iIndex){

				if(msgNo.equals("")){
					//�G���[���X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000206", strChkName, "", "");
					
				}else{
					//�G���[���X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}

	/**
	 * �������r�Ɏg�����߂ɕ�����̓������O�i�[���j���폜����
	 * @param String
	 * @return String
	 */
	private String prefixZeroCut(String fieldVale) {
		String strRet = fieldVale;
		for (int i = 0; i < fieldVale.length(); i++) {
			if (strRet.startsWith("0")) {
				strRet = strRet.replaceFirst("0", "");
			}
		}
		return strRet;
	}
	
	/**
	 * �V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkSingleQuotation(Object objValue, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkSingleQuotation( objValue,  strChkName, "");
	}
	private void checkSingleQuotation(Object objValue, String strChkName, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (objValue.toString().length() > 0) {
				// �V���O���N�H�[�e�[�V�����̃`�F�b�N
				if (objValue.toString().indexOf("'") != -1) {

					if(msgNo.equals("")){
						// ���͕s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception, "E000215", strChkName, "", "");
						
					}else{
						// ���͕s�����X���[����B
						em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
						
					}
					
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}

	}
	
	/**
	 * �����̑��݃`�F�b�N�i�V�K�����j
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void checkExistString(Object objValue, String strChk, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkExistString( objValue,  strChk,  strChkName, "");
	}
	protected void checkExistString(Object objValue, String strChk, String strChkName, String msgNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if(objValue.toString().indexOf(strChk) != -1){

				if(msgNo.equals("")){
					// ���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000307", strChkName, "", "");
					
				}else{
					// ���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, msgNo, strChkName, "", "");
					
				}
				
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}

	}
	
	/**
	 * �f�[�^�����`�F�b�N�i�z���ʃZ�����F1500�Z���`�F�b�N�j
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void DataCellCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "SA490";
		String strTableHaigo = "tr_haigo";
		String strTableShisaku = "tr_shisaku";
		
		try{
			//�ő�H�����擾
			int intSort1 = 0;
			for(int i=0; i < checkData.GetRecCnt(strKinoNm, strTableHaigo); i++){
				int strSort2 = Integer.parseInt(checkData.GetValueStr(strKinoNm, strTableHaigo, i, "sort_kotei"));
				if(strSort2 > intSort1){
					intSort1 = strSort2;
				}
			}
			
			//�e�f�[�^�����擾
			int intHaigoCnt = checkData.GetRecCnt(strKinoNm, strTableHaigo) + intSort1;
			int intShisakuCnt = checkData.GetRecCnt(strKinoNm, strTableShisaku);
			
			//�Z�������Z�o
			int intSumCnt = intHaigoCnt * intShisakuCnt;
			//�Z���`�F�b�N���擾
			int intCheckCnt = Integer.parseInt(ConstManager.getConstValue(Category.�ݒ�, "CHECK_MAX_CELL"));
			
			//�Z���`�F�b�N�i�w��Z�����ȉ��̏ꍇ�j
			if(intSumCnt <= intCheckCnt){
				//�������Ȃ�
			}
			else{
				// ���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000220", ConstManager.getConstValue(Category.�ݒ�, "CHECK_MAX_CELL"), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/*�yQP@00342�z
	 * URL�\���̑��݃`�F�b�N
	 * @param objValue
	 * @param strChkName
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	protected void checkStringURL(Object objValue, String strChkName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		checkStringURL( objValue,  strChkName, "");
	}
	private void checkStringURL(Object objValue, String strChkName, String msgNo)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (objValue.toString().length() > 0) {
				if(toString(objValue).indexOf(";") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000319", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("/") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000320", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("?") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000321", strChkName, "", "");
				}
				else if(toString(objValue).indexOf(":") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000322", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("@") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000323", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("&") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000324", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("=") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000325", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("+") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000326", strChkName, "", "");
				}
				else if(toString(objValue).indexOf("$") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000327", strChkName, "", "");
				}
				else if(toString(objValue).indexOf(",") >= 0){
					em.ThrowException(ExceptionKind.���Exception, "E000328", strChkName, "", "");
				}
				// 2012/02/15 TT H.Shima add ���s�`�F�b�N Start
				else if(toString(objValue).indexOf('\n') >= 0 || toString(objValue).indexOf('\r') >= 0 ){
					em.ThrowException(ExceptionKind.���Exception, "E000332", strChkName, "", "");
				}
				// 2012/02/15 TT H.Shima add ���s�`�F�b�N End
			}
		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}

	}
	
	// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
	
	/**
	 * ���͌����`�F�b�N
	 *  : ���̓p�����[�^�[�����̃`�F�b�N���s���B(�S�p�A���p����ʂ��Ȃ��B�j
	 * @param strChkPrm	: �`�F�b�N�p�����[�^�[
	 * @param strParam : ���b�Z�[�W�p�����[�^
	 * @param iMaxLen : �ŏ�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void sizeCheckLenMin(String strChkPrm, String strParam, int iMinLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		sizeCheckLenMin( strChkPrm,  strParam,  iMinLen, "");
	}
	protected void sizeCheckLenMin(String strChkPrm, String strParam, int iMinLen, String msgNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			int chkLen = strChkPrm.length();
			// �@:�ŏ�������p���A���͌����`�F�b�N���s���B
			if (iMinLen > chkLen) {
				if(msgNo.equals("")){
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							"E000226",
							strParam,
							Integer.toString(iMinLen),
							"");
					
				}else{
					// ���͌����s�����X���[����B
					em.ThrowException(
							ExceptionKind.���Exception,
							msgNo,
							strParam,
							Integer.toString(iMinLen),
							"");
					
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {

		}
	}

	/**
	 * ���͕�����`�F�b�N
	 *  : ���̓p�����[�^�[�̉p�������݃`�F�b�N���s���B(�S�p�A���p����ʂ��Ȃ��B�j
	 * @param strChkPrm	: �`�F�b�N�p�����[�^�[
	 * @param strParam : ���b�Z�[�W�p�����[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void strCheckPrm(String strChkPrm, String strParam) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//�󔒃X�y�[�X�`�F�b�N
			if(strChkPrm.indexOf(" ") != -1){

				em.ThrowException(ExceptionKind.���Exception,
						"E000227",
						strParam,
						"",
						"");
			}
			
			//���p�p�������݃`�F�b�N
			if(!strChkPrm.matches("^[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*$|^[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*$")){

				em.ThrowException(ExceptionKind.���Exception,
						"E000227",
						strParam,
						"",
						"");
			}

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[�F�؂Ɏ��s���܂����B");

		} finally {

		}
	}
	// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
}