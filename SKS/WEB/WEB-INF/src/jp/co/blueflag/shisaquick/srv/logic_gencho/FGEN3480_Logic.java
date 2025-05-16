package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * �yQP@40404�z
 *  ���ގ�z���́F���ގ�z�e�[�u���s�폜
 *  �@�\ID�FFGEN3480
 *
 * @author E.Kitazawa
 * @since  2014/09/24
 */
public class FGEN3480_Logic extends LogicBase{

	/**
	 * ���ގ�z���́F���ގ�z�e�[�u���s�폜
	 * : �C���X�^���X����
	 */
	public FGEN3480_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���ގ�z���́F���ގ�z�e�[�u���s�폜
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
	 * �f�[�^�X�VSQL�쐬
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
		StringBuffer strSql = new StringBuffer();

		try {
			// ���������i����No.�j
			String strCd_shain   = reqData.getFieldVale("table", 0, "cd_shain");
			String strNen        = reqData.getFieldVale("table", 0, "nen");
			String strNo_oi      = reqData.getFieldVale("table", 0, "no_oi");
			String strSeq_shizai = reqData.getFieldVale("table", 0, "seq_shizai");
			String strNo_eda     = reqData.getFieldVale("table", 0, "no_eda");
			// �������̍X�V����
			String dt_tehai_koshin = reqData.getFieldVale("table", 0, "dt_tehai_koshin");

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
			strSql.append(" SELECT dt_koshin  ");
			strSql.append(" FROM  tr_shizai_tehai  ");
			strSql.append(" WHERE cd_shain = "+ lngCd_shain );
			strSql.append("   AND nen = "     + intNen );
			strSql.append("   AND no_oi = "   + intNo_oi );
			strSql.append("   AND seq_shizai = " + intSeq_shizai );
			strSql.append("   AND no_eda = "  + intNo_eda );

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			// �f�[�^�����݂��鎞�A�f�[�^�폜
			if(lstRecset.size() > 0) {
				String strMsg = "���ގ�z�e�[�u��(Seq_shizai=" + strSeq_shizai + ") ";

				// ��������������X�V����Ă���ꍇ
				if (toString(lstRecset.get(0)).compareTo(dt_tehai_koshin) > 0) {

					// �X�V�������V�����Ȃ���
					em.ThrowException(ExceptionKind.���Exception,"E000341", strMsg, "","");
				}

				strSql = new StringBuffer();

				// �폜SQL���̍쐬
				strSql.append(" DELETE  ");
				strSql.append(" FROM  tr_shizai_tehai  ");
				strSql.append(" WHERE cd_shain = "+ lngCd_shain );
				strSql.append("   AND nen = "     + intNen );
				strSql.append("   AND no_oi = "   + intNo_oi );
				strSql.append("   AND seq_shizai = " + intSeq_shizai );
				strSql.append("   AND no_eda = "  + intNo_eda );

				execDB.execSQL(strSql.toString());
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
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
