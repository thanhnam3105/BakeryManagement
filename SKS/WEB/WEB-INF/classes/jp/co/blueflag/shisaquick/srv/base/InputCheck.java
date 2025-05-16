package jp.co.blueflag.shisaquick.srv.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
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
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);
			
			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000200", strChkName, "", "");
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
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);
			
			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000207", strChkName, "", "");
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
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000211", strChkName, "", "");
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
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000300", strChkName, "", "");
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
				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000200", strChkName, "", "");
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
		
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {

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
	 
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.length();
			// �@:�ő包����p���A���͌����`�F�b�N���s���B
			if (iMaxLen < chkLen) {
				// ���͌����s�����X���[����B
				em.ThrowException(
						ExceptionKind.���Exception,
						"E000212",
						strParam,
						Integer.toString(iMaxLen),
						"");
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

		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strParam);

			int chkLen = strChkPrm.getBytes().length;
			// �@:�ő包����p���A���͌����`�F�b�N���s���B
			if (iHalfLen < chkLen) {
				// ���͌����s�����X���[����B
				em.ThrowException(
						ExceptionKind.���Exception,
						"E000201",
						strParam,
						Integer.toString(iHalfLen),
						Integer.toString(iFullLen));
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
				
		try {
			//�V���O���N�H�[�e�[�V�����̑��݃`�F�b�N
			checkSingleQuotation(strChkPrm, strChkName);
			
			//�@�`�F�b�N�p�����[�^�[�̓��t�̐������`�F�b�N���s���B
			//�f�t�H���g�̓��t�^���擾
		    SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance();
		    
		    //�t�H�[�}�b�g�p�^���ݒ�
		    df.applyPattern("YYYY/MM/DD"); 

		    //�`�F�b�N�p�����[�^�[����t�^�ɕϊ�
	        df.parse(strChkPrm);

	    //�ϊ����s���̗�O���L���b�`
	    } catch(ParseException ex) {

	    	//���͓��t�s�����X���[����B
	    	em.ThrowException(ExceptionKind.���Exception, "E000202", strChkName, "", "");
	    	
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
				
		try {
			
			//�@�F����l�A�����l��p���A�`�F�b�N�p�����[�^�[�͈̔̓`�F�b�N���s���B
			if (dblMaxValue < dblChkPrm || dblChkPrm < dblMinValue){
			
		    	//�����͈͕s�����X���[����B
		    	em.ThrowException(
		    			ExceptionKind.���Exception, 
		    			"E000203", 
		    			strChkName, 
		    			Double.toString(dblMinValue), 
		    			Double.toString(dblMaxValue));

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
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			String strDblData = Double.toString(dblChkPrm);
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iKetasu < iSize){
			
		    	em.ThrowException(
		    			ExceptionKind.���Exception, 
		    			"E000204", 
		    			strChkName, 
		    			String.valueOf(strDblData.substring(strDblData.indexOf(".")).length()), 
		    			Integer.toString(iSize));

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
	protected void shousuRangeCheck(double dblChkPrm, int iIntegeruPartLen, int iDecimalPartLen, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
					
		try {

	    	// �����w�茅����p���A�`�F�b�N�p�����[�^�[�̏��������`�F�b�N���s���B
			String strDblData = Double.toString(dblChkPrm);
			int iSize = strDblData.substring(strDblData.indexOf(".") + 1).length();
			
			if (iSize != 0 && iDecimalPartLen < iSize){
			
		    	em.ThrowException(
		    			ExceptionKind.���Exception, 
		    			"E000204", 
		    			strChkName, 
		    			String.valueOf(iIntegeruPartLen), 
		    			String.valueOf(iDecimalPartLen));

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

		try {

			//�����敪���P�ȊO�̏ꍇ
			if (!reqData.GetValueStr("USERINFO", 0, 0, "kbn_shori").equals("1")) {
				//���[�UID�̎擾
				String strUserId = reqData.GetValueStr("USERINFO", 0, 0, "id_user");
				
				//���[�UID�̕K�{���̓`�F�b�N���s���B
				if (strUserId.equals(null) || strUserId.equals("")){
					em.ThrowException(ExceptionKind.���Exception,"E000205","","","");
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
		try {

			// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
			if (strChkPrm.equals(null) || strChkPrm.equals("")) {
				// �K�{���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000210", "", "", "");
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
	 * ���̓f�[�^�̏d���f�[�^�`�F�b�N���s��
	 * @param checkData
	 * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
	 * @param ValueNm : ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void diffValueCheck(RequestData checkData, String KindId, String TableNm, String ValueNm, String strChkName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int intRecCount = checkData.GetRecCnt(KindId, TableNm);
		ArrayList<String> lstTantokaishaCd = new ArrayList<String>();
		// ��ʂ���擾�����I�����X�g���d�����Ă���ꍇ�A�X���[����B
		for (int i = 0; i < intRecCount; i++) {
			if (!lstTantokaishaCd.contains(prefixZeroCut(checkData.GetValueStr(KindId, TableNm, i, ValueNm)))) {
				lstTantokaishaCd.add(prefixZeroCut(checkData.GetValueStr(KindId,TableNm, i, ValueNm)));
			}else{
				// ���͕s�����X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000214", strChkName, "", "");
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
		
		try {

			String[] aryChkData = checkData.split("-");
			
			//�z��̒����`�F�b�N���s��
			if (aryChkData.length != iIndex){
				//�G���[���X���[����B
				em.ThrowException(ExceptionKind.���Exception, "E000206", strChkName, "", "");
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

		try {
			if (objValue.toString().length() > 0) {
				// �V���O���N�H�[�e�[�V�����̃`�F�b�N
				if (objValue.toString().indexOf("'") != -1) {
					// ���͕s�����X���[����B
					em.ThrowException(ExceptionKind.���Exception, "E000215", strChkName, "", "");
				}
			}

		} catch (Exception e) {

			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}

	}

}
