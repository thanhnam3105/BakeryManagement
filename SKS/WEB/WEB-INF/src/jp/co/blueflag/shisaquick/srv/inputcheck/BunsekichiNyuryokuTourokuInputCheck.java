package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���͒l���͍X�V�����C���v�b�g�`�F�b�N : ����f�[�^��ʂ̎���\�o�͎��Ɋe���ڂ̓��͒l�`�F�b�N���s���B
 *
 * @author itou
 * @since 2009/04/06
 */
public class BunsekichiNyuryokuTourokuInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : ���͒l���͍X�V�����C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public BunsekichiNyuryokuTourokuInputCheck() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : ���̓`�F�b�N�̊Ǘ����s���B
	 *
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
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
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			// �@�\ID�FSA380�̃C���v�b�g�`�F�b�N�i�X�V���e�`�F�b�N�j���s���B
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �X�V���e�`�F�b�N : �o�^�A�X�V���e�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("0")) {
				// �����敪���o�^�̏ꍇ�A�o�^�l�`�F�b�N���s���B
				insertValueCheck(checkData);
			} else {
				// �����敪���X�V�̏ꍇ�A�X�V�l�`�F�b�N���s���B
				updateValueCheck(checkData);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �o�^�l�`�F�b�N : �o�^���e�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// ��ЃR�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
			// ������1�F��ЃR�[�h 				���b�Z�[�W�p�����[�^�F"���"�@���b�Z�[�W�R�[�h�FE000207
			super.hissuCodeCheck(checkData.GetValueStr("SA380", 0, 0, "cd_kaisha"), "���");

			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i�|�_,�H��,���_,���ܗL��,�\����,�Y����,�h�{�v�Z�H�i�ԍ�1�`5,����1�`5����у����̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �X�V�l�`�F�b�N : �X�V���e�̓��͒l�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �o�^�A�X�V���ʕ������̓`�F�b�N�B
			// �i�|�_,�H��,���_,���ܗL��,�\����,�Y����,�h�{�v�Z�H�i�ԍ�1�`5,����1�`5����у����̓��̓`�F�b�N�B�j
			comInsertUpdateValueCheck(checkData);

//			// �p�~���`�F�b�N����Ă���ꍇ�i0:�g�p�\�A1:�p�~�j�A�m��R�[�h�̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
//			// ������1�F�m��R�[�h ���b�Z�[�W�p�����[�^�F"�m��R�[�h"�@���b�Z�[�W�R�[�h�FE000200
//			if (checkData.GetValueStr("SA400", 0, 0, "kbn_haishi").equals("1")) {
//				super.hissuInputCheck(checkData.GetValueStr("SA380", 0, 0, "cd_kakutei"), "�m��R�[�h");
//			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �o�^�l�X�V�l���ʕ����`�F�b�N :
	 * �|�_,�H��,���_,���ܗL��,�\����,�Y����,�h�{�v�Z�H�i�ԍ�1�`5,����1�`5����у����̓��̓`�F�b�N�͓o�^�A�X�V���ʁB
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// �������̕K�{���̓`�F�b�N�i�X�[�p�[�N���X�̕K�{�`�F�b�N�j���s���B
		// ������1�F������ 		���b�Z�[�W�p�����[�^�F"������"�@���b�Z�[�W�R�[�h�FE000200
		super.hissuInputCheck(checkData.GetValueStr("SA380", 0, 0, "nm_genryo"), "������");
		// �������̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�������A����3�F60�A����4�F30 		���b�Z�[�W�p�����[�^�F"������"�@���b�Z�[�W�R�[�h�FE000201�@����3�F60�@����4�F30
		super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA380", 0, 0, "nm_genryo"), "������", 60, 30);
		String strRituSakusan = checkData.GetValueStr("SA380", 0, 0, "ritu_sakusan");
		if (!(strRituSakusan.equals("") || strRituSakusan == null)) {
			// �|�_�̐����͈̓`�F�b�N�i�X�[�p�[�N���X�̐����͈̓`�F�b�N�j���s���B
			// ������1�F�|�_�A����2�F0�A����3�F999.99 	 ���b�Z�[�W�p�����[�^�F"�|�_�i%�j"�@���b�Z�[�W�R�[�h�FE000203�@����4�F0�A����5�F999.99
			super.rangeNumCheck(Double.parseDouble(strRituSakusan), 0, 999.99, "�|�_�i%�j");
			// �|�_�̏��������`�F�b�N�i�X�[�p�[�N���X�̏��������`�F�b�N�j���s���B
			// ������1�F�|�_�A����2�F2	 ���b�Z�[�W�p�����[�^�F"�|�_�i%�j"�@���b�Z�[�W�R�[�h�FE000204�@����4�F3�A����5�F2
			super.shousuRangeCheck(Double.parseDouble(strRituSakusan), 3, 2, "�|�_�i%�j");
		}
		String strRituShokuen = checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen");
		if (!(strRituShokuen.equals("") || strRituShokuen == null)) {
			// �H���̐����͈̓`�F�b�N�i�X�[�p�[�N���X�̐����͈̓`�F�b�N�j���s���B
			// ������1�F�H���A����2�F0�A����3�F999.99 	 ���b�Z�[�W�p�����[�^�F"�H���i%�j"�@���b�Z�[�W�R�[�h�FE000203�@����4�F0�A����5�F999.99
			super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen")), 0, 999.99, "�H���i%�j");
			// �H���̏��������`�F�b�N�i�X�[�p�[�N���X�̏��������`�F�b�N�j���s���B
			// ������1�F�H���A����2�F2 ���b�Z�[�W�p�����[�^�F"�H���i%�j"�@���b�Z�[�W�R�[�h�FE000204�@����4�F3�A����5�F2
			super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen")), 3, 2, "�H���i%�j");
		}
		String strRituSousan = checkData.GetValueStr("SA380", 0, 0, "ritu_sousan");
		if (!(strRituSousan.equals("") || strRituSousan == null)) {
			// ���_�̐����͈̓`�F�b�N�i�X�[�p�[�N���X�̐����͈̓`�F�b�N�j���s���B
			// ������1�F���_�A����2�F0�A����3�F999.99 	 ���b�Z�[�W�p�����[�^�F"���_�i%�j"�@���b�Z�[�W�R�[�h�FE000203�@����4�F0�A����5�F999.99
			super.rangeNumCheck(Double.parseDouble(strRituSousan), 0, 999.99, "���_�i%�j");
			// ���_�̏��������`�F�b�N�i�X�[�p�[�N���X�̏��������`�F�b�N�j���s���B
			// ������1�F���_�A����2�F2 ���b�Z�[�W�p�����[�^�F"���_�i%�j"�@���b�Z�[�W�R�[�h�FE000204�@����4�F3�A����5�F2
			super.shousuRangeCheck(Double.parseDouble(strRituSousan), 3, 2, "���_�i%�j");
		}
		String strRituAbura = checkData.GetValueStr("SA380", 0, 0, "ritu_abura");
		if (!(strRituAbura.equals("") || strRituAbura == null)) {
			// ���ܗL���̐����͈̓`�F�b�N�i�X�[�p�[�N���X�̐����͈̓`�F�b�N�j���s���B
			// ������1�F���ܗL���A����2�F0�A����3�F999.99 	 ���b�Z�[�W�p�����[�^�F"���ܗL���i%�j"�@���b�Z�[�W�R�[�h�FE000203�@����4�F0�A����5�F999.99
			super.rangeNumCheck(Double.parseDouble(strRituAbura), 0, 999.99, "���ܗL��");
			// ���ܗL���̏��������`�F�b�N�i�X�[�p�[�N���X�̏��������`�F�b�N�j���s���B
			// ������1�F���ܗL���A����2�F2 ���b�Z�[�W�p�����[�^�F"���ܗL��"�@���b�Z�[�W�R�[�h�FE000204�@����4�F3�A����5�F2
			super.shousuRangeCheck(Double.parseDouble(strRituAbura), 3, 2, "���ܗL��");
		}
// ADD start 20121005 QP@20505 No.24
		String strRituMsg = checkData.GetValueStr("SA380", 0, 0, "ritu_msg");
		if (!(strRituMsg.equals("") || strRituMsg == null)) {
			super.rangeNumCheck(Double.parseDouble(strRituMsg), 0, 999.99, "�l�r�f");
			super.shousuRangeCheck(Double.parseDouble(strRituMsg), 3, 2, "�l�r�f");
		}
// ADD end 20121005 QP@20505 No.24
		String strHyojian = checkData.GetValueStr("SA380", 0, 0, "hyojian");
		if (!(strHyojian.equals("") || strHyojian == null)) {
			// �\���Ă̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�\���āA����2�F200 	 ���b�Z�[�W�p�����[�^�F"�\����"�@���b�Z�[�W�R�[�h�FE000212�@����4�F200�A����5�F�󕶎�
			super.sizeCheckLen(strHyojian,"�\����", 200);
		}
		String strTenkabutu = checkData.GetValueStr("SA380", 0, 0, "tenkabutu");
		if (!(strTenkabutu.equals("") || strTenkabutu == null)) {
			// �Y�����̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�Y�����A����2�F200 	 ���b�Z�[�W�p�����[�^�F"�Y����"�@���b�Z�[�W�R�[�h�FE000212�@����4�F200�A����5�F�󕶎�
			super.sizeCheckLen(strTenkabutu, "�Y����", 200);
		}
		String strEiyoNo1 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo1");
		String strEiyoNo2 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo2");
		String strEiyoNo3 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo3");
		String strEiyoNo4 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo4");
		String strEiyoNo5 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo5");
		if (!(strEiyoNo1.equals("") || strEiyoNo1 == null)) {
			// �h�{�v�Z�H�i�ԍ�1�`5�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
			// ������1�F�h�{�v�Z�H�i�ԍ��A����2�F10 ���b�Z�[�W�p�����[�^�F"�h�{�v�Z�H�i�ԍ�"�@���b�Z�[�W�R�[�h�F�s��
			super.sizeCheckLen(strEiyoNo1, "�h�{�v�Z�H�i�ԍ��P", 10);
		}
		if (!(strEiyoNo2.equals("") || strEiyoNo2 == null)) {
			super.sizeCheckLen(strEiyoNo2, "�h�{�v�Z�H�i�ԍ��Q", 10);
		}
		if (!(strEiyoNo3.equals("") || strEiyoNo3 == null)) {
			super.sizeCheckLen(strEiyoNo3, "�h�{�v�Z�H�i�ԍ��R", 10);
		}
		if (!(strEiyoNo4.equals("") || strEiyoNo4 == null)) {
			super.sizeCheckLen(strEiyoNo4, "�h�{�v�Z�H�i�ԍ��S", 10);
		}
		if (!(strEiyoNo5.equals("") || strEiyoNo5 == null)) {
			super.sizeCheckLen(strEiyoNo5, "�h�{�v�Z�H�i�ԍ��T", 10);
		}
		// ����1�`5�̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�����A����2�F10 ���b�Z�[�W�p�����[�^�F"�����i%�j"�@���b�Z�[�W�R�[�h�F�s��
		String strWariai1 = checkData.GetValueStr("SA380", 0, 0, "wariai1");
		String strWariai2 = checkData.GetValueStr("SA380", 0, 0, "wariai2");
		String strWariai3 = checkData.GetValueStr("SA380", 0, 0, "wariai3");
		String strWariai4 = checkData.GetValueStr("SA380", 0, 0, "wariai4");
		String strWariai5 = checkData.GetValueStr("SA380", 0, 0, "wariai5");
//		if (!(strWariai1.equals("") || strWariai1 == null)) {
//			super.sizeCheckLen(strWariai1, "�����P�i%�j", 10);
//		}
//		if (!(strWariai2.equals("") || strWariai2 == null)) {
//			super.sizeCheckLen(strWariai2, "�����Q�i%�j", 10);
//		}
//		if (!(strWariai3.equals("") || strWariai3 == null)) {
//			super.sizeCheckLen(strWariai3, "�����R�i%�j", 10);
//		}
//		if (!(strWariai4.equals("") || strWariai4 == null)) {
//			super.sizeCheckLen(strWariai4, "�����S�i%�j", 10);
//		}
//		if (!(strWariai5.equals("") || strWariai5 == null)) {
//			super.sizeCheckLen(strWariai5, "�����T�i%�j", 10);
//		}
		if(toString(strWariai1) != ""){
			//���l�`�F�b�N
			numberCheck(strWariai1,"����1");
			//���l�͈̓`�F�b�N
			rangeNumCheck(
					toDouble(strWariai1)
					, 0.01
					, 999.99
					, "����1");
			//��������
			shousuRangeCheck(toDouble(strWariai1),2,"����1");

		}
		if(toString(strWariai2) != ""){
			//���l�`�F�b�N
			numberCheck(strWariai2,"����2");
			//���l�͈̓`�F�b�N
			rangeNumCheck(
					toDouble(strWariai2)
					, 0.01
					, 999.99
					, "����2");
			//��������
			shousuRangeCheck(toDouble(strWariai2),2,"����2");

		}
		if(toString(strWariai3) != ""){
			//���l�`�F�b�N
			numberCheck(strWariai3,"����3");
			//���l�͈̓`�F�b�N
			rangeNumCheck(
					toDouble(strWariai3)
					, 0.01
					, 999.99
					, "����3");
			//��������
			shousuRangeCheck(toDouble(strWariai3),2,"����3");

		}
		if(toString(strWariai4) != ""){
			//���l�`�F�b�N
			numberCheck(strWariai4,"����4");
			//���l�͈̓`�F�b�N
			rangeNumCheck(
					toDouble(strWariai4)
					, 0.01
					, 999.99
					, "����4");
			//��������
			shousuRangeCheck(toDouble(strWariai4),2,"����4");

		}
		if(toString(strWariai5) != ""){
			//���l�`�F�b�N
			numberCheck(strWariai5,"����5");
			//���l�͈̓`�F�b�N
			rangeNumCheck(
					toDouble(strWariai5)
					, 0.01
					, 999.99
					, "����5");
			//��������
			shousuRangeCheck(toDouble(strWariai5),2,"����5");

		}

		// �����̓��͌����`�F�b�N�i�X�[�p�[�N���X�̓��͌����`�F�b�N�j���s���B
		// ������1�F�����A����2�F200 ���b�Z�[�W�p�����[�^�F"����"�@���b�Z�[�W�R�[�h�FE000212�@����4�F200�A����5�F�󕶎�
		String strMemo = checkData.GetValueStr("SA380", 0, 0, "memo");
		if (!(strMemo.equals("") || strMemo == null)) {
			super.sizeCheckLen(strMemo, "����", 200);
		}
	}

}