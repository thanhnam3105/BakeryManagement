package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

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

// 20160607  KPX@1502111_No.5 ADD start
			// �@�\ID�FFGEN2250�̃C���v�b�g�`�F�b�N���s���B
			renkeiValueCheck(checkData);
// 20160607  KPX@1502111_No.5 ADD end


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
//20160819  KPX@1502111 MOD start
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10,5);
//20160819  KPX@1502111 MOD end
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5",10);
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai1")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						, 0.01
						, 999.99
						, "����1");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						,2
						,"����1");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai2")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						, 0.01
						, 999.99
						, "����2");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						,2
						,"����2");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai3")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						, 0.01
						, 999.99
						, "����3");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						,2
						,"����3");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai4")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						, 0.01
						, 999.99
						, "����4");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						,2
						,"����4");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai5")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						, 0.01
						, 999.99
						, "����5");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						,2
						,"����5");

			}

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
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),0,999.99,"�l�r�f�i%�j");
			}
			// ADD end 20121005 QP@20505 No.24

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
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),2,"�l�r�f�i%�j");
			}
			// ADD end 20121005 QP@20505 No.24

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
//20160819  KPX@1502111 MOD start
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"�h�{�v�Z�H�i�ԍ�1",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"�h�{�v�Z�H�i�ԍ�2",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"�h�{�v�Z�H�i�ԍ�3",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"�h�{�v�Z�H�i�ԍ�4",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"�h�{�v�Z�H�i�ԍ�5",10,5);
//20160819  KPX@1502111 MOD end
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5",10);
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai1")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"����1");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						, 0.01
						, 999.99
						, "����1");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						,2
						,"����1");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai2")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"����2");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						, 0.01
						, 999.99
						, "����2");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						,2
						,"����2");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai3")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"����3");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						, 0.01
						, 999.99
						, "����3");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						,2
						,"����3");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai4")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"����4");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						, 0.01
						, 999.99
						, "����4");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						,2
						,"����4");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai5")) != ""){
				//���l�`�F�b�N
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"����5");
				//���l�͈̓`�F�b�N
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						, 0.01
						, 999.99
						, "����5");
				//��������
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						,2
						,"����5");

			}

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
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),0,999.99,"�l�r�f�i%�j");
			}
			// ADD end 20121005 QP@20505 No.24

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
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),2,"�l�r�f�i%�j");
			}
			// ADD end 20121005 QP@20505 No.24

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}


// 20160607  KPX@1502111_No.5 ADD start
	/**
	 * �z�������N�X�V���e�`�F�b�N : �o�^�A�X�V���e�̓��͒l�`�F�b�N���s���B
	 *  : �@�\ID�FFGEN2250�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void renkeiValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		String strKinoNm = "FGEN2250";

		try {

			// �@�\ID�ݒ莞�̂݃`�F�b�N����
			if (checkData.GetItemNo(strKinoNm) > 0) {

				// �����敪
				String strKbn = checkData.GetValueStr(strKinoNm, 0, 0, "kbn_shori");
				if (strKbn.equals("3")) {
					//�������[�h�F�폜�̏ꍇ
					// ����No�S�Ă���ł��邱�Ɓu���͓��e���s���ł��B�v
					// ����m��-�Ј�CD�̖����̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					if (checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain").length() > 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000206", "����m��", "", "");
					}

					// ����m��-�N�̖����̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					if (checkData.GetValueStr(strKinoNm, 0, 0, "nen").length() > 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000206", "����m��", "", "");
					}

					// ����m��-�ǔԂ̖����̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					if (checkData.GetValueStr(strKinoNm, 0, 0, "no_oi").length() > 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000206", "����m��", "", "");
					}

					// �}�ԍ��̖����̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					if (checkData.GetValueStr(strKinoNm, 0, 0, "no_eda").length() > 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000206", "����m��", "", "");
					}

					// ����SEQ�̖����̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					if (checkData.GetValueStr(strKinoNm, 0, 0, "seq_shisaku").length() > 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000206", "�T���v���m��", "", "");
					}

				} else {
					//�����敪�F�o�^�E�X�V�̏ꍇ

					// ����m��-�Ј�CD�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"����m��_�Ј��R�[�h");
					// ����m��-�Ј�CD�̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"����m��_�Ј��R�[�h");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"����m��_�Ј��R�[�h", 10);


					// ����m��-�N�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"����m��_�N)");
					// ����m��-�N�̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"����m��_�N");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"����m��_�N", 2);

					// ����m��-�ǔԂ̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"����m��_�ǔ�");
					// ����m��-�ǔԂ̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"����m��_�ǔ�");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"����m��_�ǔ�", 3);

					// �}�ԍ��̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"����m��_�}��");
					// �}�ԍ��̓��̓`�F�b�N�i�X�[�p�[�N���X�̃`�F�b�N�j���s���B
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"����m��_�}��");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"����m��_�}��", 3);

					// ����SEQ�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, 0, 0, "seq_shisaku"),"�T���v���m��");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
// 20160607  KPX@1502111_No.5 ADD end

}
