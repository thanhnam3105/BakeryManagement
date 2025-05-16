package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW820�z ���͒l���͉�ʃ��N�G�X�g�o�^ �C���v�b�g�`�F�b�N
 *
 */
public class BunsekichiTourokuInputCheck extends InputCheck {
	
	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public BunsekichiTourokuInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
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

			//SA380�̃C���v�b�g�`�F�b�N���s���B
			kanriValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �X�V���e�`�F�b�N
	 *  : SA380�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//
			if ( checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("0") ) {
				//�����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				this.insertValueCheck(checkData);
			} else if ( checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("1") )  {
				//�����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				this.updateValueCheck(checkData);
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �o�^�l �C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA380";
			
			///
			///�K�{���ڂ̓��̓`�F�b�N
			///
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kaisha"),"��ЃR�[�h");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_genryo"),"�����R�[�h");
	    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nm_genryo"),"������");
		    
		    ///
			///���͌����`�F�b�N
		    ///
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"������",60);
	    	super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"������",60,30);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"hyojian"),"�\����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"tenkabutu"),"�Y����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"memo"),"����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5",10);

			///
			/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
			///
		    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),0,999.99,"�|�_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),0,999.99,"�H���i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),0,999.99,"���_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),0,999.99,"���ܗL���i%�j");
			}

			///
			/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
			///
		    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),2,"�|�_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),2,"�H���i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),2,"���_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),2,"���ܗL���i%�j");
			}
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * �X�V�l�C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA380";
			
			///
			///�K�{���ڂ̓��̓`�F�b�N
			///
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kaisha"),"��ЃR�[�h");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_genryo"),"�����R�[�h");
	    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nm_genryo"),"������");
//		    if ( checkData.GetValueStr(strKinoNm, 0, 0, "kbn_haishi").equals("1") ) {
//			    //�p�~���`�F�b�N����Ă���ꍇ
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kakutei"),"�m��R�[�h");
//		    }

		    ///
			///���͌����`�F�b�N
		    ///
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"������",60);
	    	super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"������",60,30);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"hyojian"),"�\����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"tenkabutu"),"�Y����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"memo"),"����",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5",10);

			///
			/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
			///
		    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),0,999.99,"�|�_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),0,999.99,"�H���i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),0,999.99,"���_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),0,999.99,"���ܗL���i%�j");
			}

			///
			/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
			///
		    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),2,"�|�_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),2,"�H���i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),2,"���_�i%�j");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),2,"���ܗL���i%�j");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
