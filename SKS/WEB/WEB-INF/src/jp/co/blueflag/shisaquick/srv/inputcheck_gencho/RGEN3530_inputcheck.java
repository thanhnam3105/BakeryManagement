package jp.co.blueflag.shisaquick.srv.inputcheck_gencho;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class RGEN3530_inputcheck extends InputCheck {

    /**
     *  : �x�[�X�P���o�^�E���F��� �o�^�{�^���������C���v�b�g�`�F�b�N�p�R���X�g���N�^
     */
    public RGEN3530_inputcheck() {
        //���N���X�̃R���X�g���N�^
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

            // FGEN3530�̃C���v�b�g�`�F�b�N���s���B
            basePriceTableSearchCheck(checkData);

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }
    }

    /**
     * �x�[�X�P���o�^�E���F��ʁ@�C���v�b�g�`�F�b�N
     *  : FGEN3530�̃C���v�b�g�`�F�b�N���s���B
     * @param requestData : ���N�G�X�g�f�[�^
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void basePriceTableSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

        try {

            // ���[�J�[��
            super.hissuCodeCheck(checkData.GetValueStr("FGEN3530", 0, 0, "cd_maker"), "���[�J�[��");

            // ���
//            super.hissuCodeCheck(checkData.GetValueStr("FGEN3530", 0, 0, "cd_houzai"), "���");

            // ��ޖ�
            super.hissuCodeCheck(checkData.GetValueStr("FGEN3530", 0, 0, "name_houzai"), "��ޖ�");

            // �Ő�
            super.hissuInputCheck(checkData.GetValueStr("FGEN3530", 0, 0, "no_hansu"), "�Ő�");

            // �L���J�n��
            super.hissuInputCheck(checkData.GetValueStr("FGEN3530", 0, 0, "dt_yuko"), "�L���J�n��");

            // �L���J�n��
            String dt_yuko = toString(checkData.GetValueStr("FGEN3530", 0, 0, "dt_yuko"));

            // �L���J�n���̌`���`�F�b�N
            super.dateCheck(dt_yuko, "�L���J�n��");

            // ��ޖ� ���͌����`�F�b�N
            super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "ma_literal", 0, "nm_2nd_literal"), "", ","),"��ޖ�",200);

            // ���l ���͌����`�F�b�N
            super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price_list", 0, "biko"), "", ","),"���l",200);

            for(int i = 0; i < 30; i++) {

            	if (i <= 0) {
            		basePriceTableInputCheck(checkData, i, "���b�g��");
            	} else {
            		basePriceTableInputCheck(checkData, i, "�x�[�X�P��");
            	}

            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }
    }

    /**
     * �x�[�X�P���o�^�E���F��ʁ@�C���v�b�g�`�F�b�N
     *  : FGEN3530�̃C���v�b�g�`�F�b�N���s���B
     * @param requestData : ���N�G�X�g�f�[�^
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void basePriceTableInputCheck(RequestData checkData, int row, String name) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value01").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value01"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value02").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value02"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value03").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value03"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value04").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value04"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value05").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value05"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value06").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value06"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value07").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value07"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value08").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value08"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value09").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value09"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value10").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value10"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value11").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value11"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value12").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value12"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value13").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value13"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value14").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value14"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value15").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value15"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value16").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value16"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value17").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value17"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value18").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value18"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value19").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value19"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value20").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value20"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value21").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value21"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value22").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value22"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value23").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value23"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value24").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value24"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value25").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value25"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value26").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value26"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value27").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value27"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value28").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value28"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value29").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value29"), name);
		}

    	if ( !(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value30").isEmpty()) ) {
			super.numberCheck(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value30"), name);
		}

        // �����`�F�b�N
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value01"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value02"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value03"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value04"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value05"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value06"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value07"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value08"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value09"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value10"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value11"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value12"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value13"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value14"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value15"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value16"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value17"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value18"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value19"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value20"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value21"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value22"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value23"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value24"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value25"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value26"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value27"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value28"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value29"), "", ","),name,10);
		super.sizeCheckLen(toString(checkData.GetValueStr("FGEN3530", "base_price", row, "no_value30"), "", ","),name,10);
    }
}
