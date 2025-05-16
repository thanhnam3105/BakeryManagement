package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3330_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 */
	public RGEN3330_inputcheck() {
		// ���N���X�̃R���X�g���N�^
		super();
	}
	
	/**
     * �C���v�b�g�`�F�b�N�Ǘ�
     *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
     * @param requestData : ���N�G�X�g�f�[�^
     * @param userInfoData : ���[�U�[���
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
	public void execInputCheck(RequestData checkData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		super.userInfoData = _userInfoData;

		try {
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			// FGEN3330�̃C���v�b�g�`�F�b�N���s���B
			shizaiListSearchCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * ���������C���v�b�g�`�F�b�N : FGEN3330�̃C���v�b�g�`�F�b�N���s���B
	 * 
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiListSearchCheck(RequestData checkData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
// �yKPX@1602367�zadd mod start
			// ���ރR�[�h
			String strShizaiCd = checkData.GetValueStr("FGEN3330", 0, 0,
					"cd_shizai");
			if (strShizaiCd != null && !"".equals(strShizaiCd)) {
				// ���ރR�[�h��6����葽���ꍇ
				super.sizeCheckLen(strShizaiCd, "���ރR�[�h", 6);
			}

			// ���ޖ�
			String nm_shizai = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_shizai");
			if (nm_shizai != null && !"".equals(nm_shizai)) {
				 // ���ޖ��̓��͌����`�F�b�N���s��
				 super.sizeCheckLen(nm_shizai, "���ޖ�", 100);
			}

			 // �����ރR�[�h
			 String cd_shizai_old = checkData.GetValueStr("FGEN3330", 0, 0,
					 "cd_shizai_old");
			 if (cd_shizai_old != null && !"".equals(cd_shizai_old)) {
				 // �����ރR�[�h��6����葽���ꍇ
				 super.sizeCheckLen(cd_shizai_old, "�����ރR�[�h", 6);
			 }
			 
			 // ���i�i���i�j�R�[�h
			 String cd_shohin = checkData.GetValueStr("FGEN3330", 0, 0,
					 "cd_shohin");
			if (cd_shohin != null && !"".equals(cd_shohin)) {
				 // ���i�R�[�h��6����葽���ꍇ
				 super.sizeCheckLen(cd_shohin, "���i�R�[�h", 6);
			}
			
			// ���i�i���i�j��
			String strShohinNm = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_shohin");
			if (strShohinNm != null && !"".equals(strShohinNm)) {
				// ���i���̓��͌����`�F�b�N���s��
				super.sizeCheckLen(strShohinNm, "���i��", 100);
			}

			// �[����i�����H��j��
			String nmSeizoukojo = checkData.GetValueStr("FGEN3330", 0, 0,
					"nm_seizoukojo");
			if (nmSeizoukojo != null && !"".equals(nmSeizoukojo)) {
				// �����H�ꖼ�̓��͌����`�F�b�N���s��
				super.sizeCheckLen(nmSeizoukojo, "�����H��", 100);
			}

			// ������From
            String dt_hattyu_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_hattyu_from"));
            // ������From�̌`���`�F�b�N
            if (dt_hattyu_from != null && !"".equals(dt_hattyu_from)) {
            	super.dateCheck(dt_hattyu_from, "������From");
            }

			// ������To
            String dt_hattyu_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_hattyu_to"));
            // ������To�̌`���`�F�b�N
            if (dt_hattyu_to != null && !"".equals(dt_hattyu_to)) {
            	super.dateCheck(dt_hattyu_to, "������To");
            }

			// �[����From
            String dt_nonyu_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_nonyu_from"));
            // �[�����̌`���`�F�b�N
            if (dt_nonyu_from != null && !"".equals(dt_nonyu_from)) {
            	super.dateCheck(dt_nonyu_from, "�[����From");
            }

			// �[����To
            String dt_nonyu_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_nonyu_to"));
            // �[�����̌`���`�F�b�N
            if (dt_nonyu_to != null && !"".equals(dt_nonyu_to)) {
            	super.dateCheck(dt_nonyu_to, "�[����To");
            }

            // �ő�x����From
            String dt_han_payday_from = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_payday_from"));
            // �ő�x�����̌`���`�F�b�N
            if (dt_han_payday_from != null && !"".equals(dt_han_payday_from)) {
            	super.dateCheck(dt_han_payday_from, "�ő�x����From");
            }

            // �ő�x����To
            String dt_han_payday_to = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_payday_to"));
            // �ő�x�����̌`���`�F�b�N
            if (dt_han_payday_to != null && !"".equals(dt_han_payday_to)) {
            	super.dateCheck(dt_han_payday_to, "�ő�x����To");
            }

//            // ����
//            String dt_han_pay_due = toString(checkData.GetValueStr("FGEN3330", 0, 0, "dt_han_pay_due"));
//            // �����̌`���`�F�b�N
//            if (dt_han_pay_due != null && !"".equals(dt_han_pay_due)) {
//            	super.dateCheck(dt_han_pay_due, "����");
//            }
// �yKPX@1602367�zadd end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

}
