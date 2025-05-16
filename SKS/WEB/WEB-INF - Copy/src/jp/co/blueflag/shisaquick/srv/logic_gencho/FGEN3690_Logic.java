package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yQP@40404�z
 *  ���ގ�z���́F���ރe�[�u���X�V
 *  �@�\ID�FFGEN3460
 *
 * @author E.Kitazawa
 * @since  2014/10/03
 */
public class FGEN3690_Logic extends LogicBase{

	/**
	 * ���ގ�z���́F���ރe�[�u���X�V
	 * : �C���X�^���X����
	 */
	public FGEN3690_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���ގ�z���́F���ރe�[�u���X�V
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();


			// �g�����U�N�V�����J�n
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			// �R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // �e�[�u�����̐ݒ�
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // ���X�|���X�f�[�^�̌`��
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = null;

		try {
			// �Ј��R�[�h
			String strCd_shain   = reqData.getFieldVale("table", 0, "cd_shain");
			// �N
			String strNen        = reqData.getFieldVale("table", 0, "nen");
			// �ǔ�
			String strNo_oi      = reqData.getFieldVale("table", 0, "no_oi");
			// seq
			String strSeq_shizai = reqData.getFieldVale("table", 0, "seq_shizai");
			// �}��
			String strNo_eda     = reqData.getFieldVale("table", 0, "no_eda");

			// ���i�R�[�h
			String strCdShohin = reqData.getFieldVale("table", 0, "cd_shohin");

			// �X�V�f�[�^
			// �[����
			String strNounyuDay   = reqData.getFieldVale("table", 0, "nounyu_day");
			// ���ގ�z�e�[�u���X�V
			// �ő�
			String strHanPay   = reqData.getFieldVale("table", 0, "han_pay");
			// �ő�x����
			String strHanPayDay    = reqData.getFieldVale("table", 0, "han_payday");
			// �ő�x�����̌`��
			StringBuffer strbuf = new StringBuffer();
			strbuf = getHidukeFormat(strHanPayDay, strbuf);

			// �t�@�C����
			String strInputName      = reqData.getFieldVale("table", 0, "inputName");
			// �t�@�C���p�X
			String strFileName      = reqData.getFieldVale("table", 0, "filename");

			StringBuffer strShisanShizai = new StringBuffer();


			long lngCd_shain = 0;
			int intNen = 0;
			int intNo_oi = 0;
			int intSeq_shizai = 0;
			int intNo_eda = 0;

			// �����L�[�ɋ󔒂͋����Ȃ�
			if (strCd_shain.equals("") || strNen.equals("") || strNo_oi.equals("") || strSeq_shizai.equals("") || strNo_eda.equals("")) {
				em.ThrowException(ExceptionKind.���Exception,"E000001","����No","","");
			}

			// ���l �ɕϊ�
			lngCd_shain =  Long.parseLong(strCd_shain);
			intNen      =  Integer.parseInt(strNen);
			intNo_oi    =  Integer.parseInt(strNo_oi);
			intSeq_shizai =  Integer.parseInt(strSeq_shizai);
			intNo_eda   =  Integer.parseInt(strNo_eda);

            strSql = new StringBuffer();

            // SQL���̍쐬
            strSql.append(" SELECT cd_shain  ");
            strSql.append(" FROM  tr_shizai_tehai  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );
           //strSql.append("   AND cd_shohin = '"  + strCdShohin + "'");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSql.toString());

            // �X�V�f�[�^�����݂��Ȃ����A�G���[
            if(lstRecset.size() < 1) {
            	em.ThrowException(ExceptionKind.���Exception,"E000301","���ގ�z�e�[�u��","����No.","");
            }

            strSql = new StringBuffer();

            // SQL���̍쐬
            strSql.append("UPDATE  tr_shizai_tehai SET  ");
            strSql.append("   han_pay = '" + strHanPay + "'");				// �ő�

            strSql.append("   , dt_han_payday = CONVERT(DateTime, '" + strbuf.toString() + "',111) ");		// �ő�x����
            strSql.append("   ,nm_file_aoyaki    = '" + strInputName + "'" );	// �t�@�C����
            // �t�@�C��������΃t�@�C���p�X���A�b�v�f�[�g����
            if (!strInputName.equals("")) {
            	strSql.append("   ,file_path_aoyaki   = '" + strFileName + "'");	// �t�@�C���p�X
            } else {
            	strSql.append("   ,file_path_aoyaki   = ''");	// �t�@�C���p�X
            }
            strSql.append("   ,id_koshin  = ");
            strSql.append(   userInfoData.getId_user());
            strSql.append("   ,dt_koshin  = ");
            strSql.append("  GETDATE()  ");
            strSql.append(" WHERE cd_shain = "+ lngCd_shain );
            strSql.append("   AND nen = "     + intNen );
            strSql.append("   AND no_oi = "   + intNo_oi );
            strSql.append("   AND seq_shizai = " + intSeq_shizai );
            strSql.append("   AND no_eda = "  + intNo_eda );
            //strSql.append("   AND cd_shohin = '"  + strCdShohin + "'");

            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	private StringBuffer getHidukeFormat(String date, StringBuffer strbuf) {
		String strMM;
		String strDD;
		String[] strTemps;
		if (!date.equals("")) {


			if (date.indexOf("/") != -1) {

				strTemps = date.split("/");

				// �N���o��
				strbuf.append(strTemps[0].toString());
				if (strTemps[1].toString().length() == 1) {
					strMM = strTemps[1].toString();
					strbuf.append("0" + strMM);

				} else {
					strbuf.append(strTemps[1].toString());
				}
				if (strTemps[2].toString().length() == 1) {

					strDD = strTemps[2].toString();
					strbuf.append("0" + strDD);
				} else {
					strbuf.append(strTemps[2].toString());
				}

			} else {
				strbuf.append(date);
			}
		} else {
			strbuf.append("");
		}

		return strbuf;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
            // �������ʂ̊i�[
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
