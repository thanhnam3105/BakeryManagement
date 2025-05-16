package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yKPX@1502111_No.5�z
 *  ���͒l���́F���ƌ����A�g��񌟍�
 *  �@�\FGEN2270
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2270_Logic extends LogicBaseJExcel {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2270_Logic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

	}
	/**
	 * ���͒l���́F���ƌ����A�g��񌟍�
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

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean resKind = null;

		try {

			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// ���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
		}
		return resKind;

	}

	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���R�[�h�l�i�[���X�g
		List<?> lstRec_nmHin = null;
		List<?> lstRec_sample = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			//�e�[�u����
			String strTblNm = "table";

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();

			// ���얼�擾SQL�쐬
			strSqlBuf = this.createSQL_nmHin(reqData);

			lstRec_nmHin = searchDB.dbSearch(strSqlBuf.toString());

			// �T���v���m���擾SQL�쐬
			strSqlBuf = this.createSQL_sampleNo(reqData);

			lstRec_sample = searchDB.dbSearch(strSqlBuf.toString());

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRec_nmHin, lstRec_sample, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�A�g��񌟍����������s���܂����B");

		} finally {
			// ���X�g�̔j��
			removeList(lstRec_nmHin);
			removeList(lstRec_sample);

			if (searchDB != null) {
				// �Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}

			// �ϐ��̍폜
			strSqlBuf = null;

		}

	}

	/**
	 * ���얼�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL_nmHin(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//����CD-�Ј�CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//�}�ԍ�
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL���̍쐬
			strSql.append(" SELECT CASE WHEN T310.nm_edaShisaku IS NOT NULL  ");
			strSql.append("        THEN  ");
			strSql.append("          CASE RTRIM(T310.nm_edaShisaku) WHEN ''  ");
			strSql.append("          THEN T110.nm_hin   ");
			strSql.append("          ELSE T110.nm_hin + ' �y' + T310.nm_edaShisaku + '�z' END   ");
			strSql.append("        ELSE T110.nm_hin END AS nm_hin  ");
			strSql.append("    ,T310.cd_shain  ");
			strSql.append("    ,T310.nen  ");
			strSql.append("    ,T310.no_oi  ");
			strSql.append("    ,T310.no_eda  ");
			strSql.append("    ,st_eigyo  ");
			strSql.append("    ,st_kenkyu  ");
			strSql.append("    ,st_seisan  ");
			strSql.append("    ,st_gensizai  ");
			strSql.append("    ,st_kojo  ");
			strSql.append(" FROM  tr_shisan_shisakuhin  AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110  ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain   ");
			strSql.append("   AND T310.nen = T110.nen   ");
			strSql.append("   AND T310.no_oi = T110.no_oi   ");
			strSql.append(" LEFT JOIN tr_shisan_status AS T441  ");
			strSql.append(" ON T310.cd_shain = T441.cd_shain   ");
			strSql.append("   AND T310.nen = T441.nen   ");
			strSql.append("   AND T310.no_oi = T441.no_oi   ");
			strSql.append("   AND T310.no_eda = T441.no_eda   ");
			strSql.append(" WHERE  T310.cd_shain = " + strShainCd );
			strSql.append("   AND  T310.nen = " + strNen );
			strSql.append("   AND  T310.no_oi = " + strNo_oi );
			strSql.append("   AND  T310.no_eda = " + strNoEda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}


	/**
	 * �T���v���m���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL_sampleNo(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//����CD-�Ј�CD
			String strShainCd  = reqData.getFieldVale("table", 0, "cd_shain");
			//����CD-�N
			String strNen    = reqData.getFieldVale("table", 0, "nen");
			//����CD-�ǔ�
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			//�}�ԍ�
			String strNoEda    = reqData.getFieldVale("table", 0, "no_eda");

			// SQL���̍쐬
			strSql.append(" SELECT T331.cd_shain  ");
			strSql.append("       ,T331.nen  ");
			strSql.append("       ,T331.no_oi  ");
			strSql.append("       ,T331.no_eda  ");
			strSql.append("       ,T331.seq_shisaku  ");
			strSql.append("       ,T331.fg_chusi   ");
			strSql.append("       ,T131.nm_sample  ");
			strSql.append(" FROM  tr_shisaku  AS T131  ");
			strSql.append(" LEFT JOIN tr_shisan_shisaku AS T331  ");
			strSql.append(" ON T331.cd_shain = T131.cd_shain  ");
			strSql.append("   AND T331.nen = T131.nen  ");
			strSql.append("   AND T331.no_oi = T131.no_oi  ");
			strSql.append("   AND T331.seq_shisaku = T131.seq_shisaku  ");
			strSql.append(" WHERE  T331.cd_shain = " + strShainCd );
			strSql.append("   AND  T331.nen = " + strNen );
			strSql.append("   AND  T331.no_oi = " + strNo_oi );
			strSql.append("   AND  T331.no_eda = " + strNoEda );
			strSql.append("   AND  T131.flg_shisanIrai = 1 "  );
			strSql.append(" ORDER BY "  );
			strSql.append("   T131.sort_shisaku "  );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstNmHin : �������ʏ�񃊃X�g�i���얼�j
	 * @param lstSampleNo : �������ʏ�񃊃X�g�i�T���v���m���j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstNmHin
			, List<?> lstSampleNo
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strHinmei  = "";
		String strStEigyo  = "";

		try {

			//���얼�擾����
			if (lstNmHin.size() > 0) {
				Object[] items = (Object[]) lstNmHin.get(0);
				//���얼
				strHinmei = toString(items[0],"");
				//�c�ƃX�e�[�^�X
				strStEigyo = toString(items[5],"");
			}

			//�T���v���m���擾����
			if (lstSampleNo.size() > 0) {
				for (int i = 0; i < lstSampleNo.size(); i++) {

					// �������ʂ̊i�[
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstSampleNo.get(i);

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
					resTable.addFieldVale(i, "nen", toString(items[1],""));
					resTable.addFieldVale(i, "no_oi", toString(items[2],""));
					resTable.addFieldVale(i, "no_eda", toString(items[3],""));
					resTable.addFieldVale(i, "seq_shisaku", toString(items[4],""));
					resTable.addFieldVale(i, "fg_chusi", toString(items[5],""));
					resTable.addFieldVale(i, "nm_sample", toString(items[6],""));
					resTable.addFieldVale(i, "nm_hin", strHinmei);
					resTable.addFieldVale(i, "st_eigyo", strStEigyo);
				}

			} else {
				// �T���v���m�����擾�ł��Ȃ���
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "nm_hin", strHinmei);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
