package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �yJW050�z ����f�[�^��ʃ��N�G�X�g�o�^�i���@�R�s�[�j �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuTourokuSeihouInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuTourokuSeihouInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
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
			//USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			//SA500�̃C���v�b�g�`�F�b�N���s���B
			SeihoNoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ���@���o�^���ڃ`�F�b�N
	 *  : SA500�̃C���v�b�g�`�F�b�N���s���B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SeihoNoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�@ ����CD�|�Ј�CD�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "cd_shain"),"����R�[�h�i�Ј��R�[�h�j");
		    //�A ����CD�|�N�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "nen"),"����R�[�h�i�N�j");
		    //�B ����CD�|�ǔԂ̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_oi"),"����R�[�h�i�ǔԁj");
		    //�C ����SEQ�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuCodeCheck(checkData.GetValueStr("SA500", 0, 0, "seq_shisaku"),"�����");
		    //�D ��ʔԍ��̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_shubetu"),"��ʔԍ�");
			super.sizeCheckLen(checkData.GetValueStr("SA500", 0, 0, "no_shubetu"),"��ʔԍ�",2);
		    //�E �z���f�[�^�̓��̓`�F�b�N
		    haigoInsertValueCheck(checkData);
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * �z���e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haigoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA500";
			String strTableNm = "tr_haigo";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";

				///
				/// �K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"����f�[�^��� �z���\ �H��CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"����f�[�^��� �z���\ �H��SEQ");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo"),"����f�[�^��� �z���\ �����R�[�h");
			    
				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� �z���\ [sort_kotei=" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "] :";

//2009/08/03 DEL �ۑ臂285�̑Ή�
//				///
//				/// �V�K�����`�F�b�N
//				///
//				super.checkExistString(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo"),"N","����f�[�^��� ����\�@ �����R�[�h");
			
				
				///
				/// ���͌����`�F�b�N
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"�H����",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"�H����",60,30);
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60,30);

				///
				/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			    if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(
							new BigDecimal(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")))
							, new BigDecimal("0")
							, new BigDecimal("99999999.99")
							, strAddMsg+"�P��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),0,999.99,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),0,999.99,strAddMsg+"���ܗL��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),0,999.99,strAddMsg+"�|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg+"�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg+"���_");
				}

				///
				/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"),2,strAddMsg+"�P��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),2,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),2,strAddMsg+"���ܗL��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),2,strAddMsg+"�|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg+"�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg+"���_");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
