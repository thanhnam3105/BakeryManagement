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
 *  ���ގ�z���́F����No����
 *  �@�\ID�FFGEN3420
 *
 * @author E.Kitazawa
 * @since  2014/09/24
 */
public class FGEN3420_Logic extends LogicBase{

	/**
	 * ���ގ�z���́F����No����
	 * : �C���X�^���X����
	 */
	public FGEN3420_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���ގ�z���́F����No����
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

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜

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
	 *
	 * @author E.Kitazawa
	 * @since  2014/09/11
	 */
	private void setData(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			// �e�[�u����
			String strTblNm = "table";

			//�f�[�^�擾SQL�쐬
			strSqlBuf = this.createSQL(reqData);

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "����No�������������s���܂����B");

		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);

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
		StringBuffer strSql = new StringBuffer();

		try {
            // ���i�R�[�h
			String cdSeihin = reqData.getFieldVale("table", 0, "cd_seihin");
			if (cdSeihin.equals("")) {
				em.ThrowException(ExceptionKind.���Exception,"E000001","���i�R�[�h","","");
			}

			// SQL���̍쐬
			strSql.append(" SELECT distinct  ");
			strSql.append("        T340.cd_shain  ");
			strSql.append("       ,T340.nen  ");
			strSql.append("       ,T340.no_oi  ");
			strSql.append("       ,T340.no_eda  ");
			strSql.append("       ,T340.cd_busho  ");
			strSql.append("       ,T441.st_kenkyu  ");
			strSql.append("       ,T441.st_seisan  ");
			strSql.append("       ,T441.st_gensizai  ");
			strSql.append("       ,T441.st_kojo  ");
			strSql.append("       ,T441.st_eigyo  ");
			strSql.append("       ,T310.saiyo_sample ");
			strSql.append(" FROM  (SELECT   ");
			strSql.append("        T131.cd_shain  ");
			strSql.append("       ,T131.nen  ");
			strSql.append("       ,T131.no_oi  ");
			strSql.append("     FROM  tr_shisaku AS T131 ");
			strSql.append("     WHERE no_seiho1 in (  ");
			strSql.append("       SELECT no_seiho  ");
			strSql.append("       FROM   vw_haigo_header  ");
			strSql.append("       WHERE cd_hin = ");
			strSql.append(  cdSeihin  );
			strSql.append("       )) AS LST  ");
			strSql.append(" LEFT JOIN tr_shisan_shizai AS T340 ");
			strSql.append(" ON (  T340.cd_shain = LST.cd_shain  ");
			strSql.append("  AND  T340.nen = LST.nen  ");
			strSql.append("  AND  T340.no_oi = LST.no_oi)  ");
			strSql.append(" LEFT JOIN tr_shisan_status AS T441 ");
			strSql.append(" ON (  T340.cd_shain = T441.cd_shain  ");
			strSql.append("  AND   T340.nen = T441.nen  ");
			strSql.append("  AND   T340.no_oi = T441.no_oi ");
			strSql.append("  AND   T340.no_eda = T441.no_eda)  ");
			strSql.append(" LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			strSql.append(" ON (  T340.cd_shain = T310.cd_shain  ");
			strSql.append("  AND   T340.nen = T310.nen  ");
			strSql.append("  AND   T340.no_oi = T310.no_oi ");
			strSql.append("  AND   T340.no_eda = T310.no_eda)  ");
			// �c�ƃX�e�[�^�X�F�̗p�̂��́ist_eigyo=4�j�𒊏o
			strSql.append(" WHERE  T441.st_eigyo IS NOT NULL AND  T441.st_eigyo = 4  ");
			// �s�̗p������
			strSql.append("   AND  T310.saiyo_sample IS NOT NULL AND  T310.saiyo_sample > 0  ");
			strSql.append(" ORDER BY T340.cd_shain, T340.nen, T340.no_oi, T340.no_eda");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstMstData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(

			  List<?> lstMstData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			if (lstMstData.size() > 0) {
				for (int i = 0; i < lstMstData.size(); i++) {

					// �������ʂ̊i�[
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstMstData.get(i);

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					// ����No�icd_shain�Anen�Ano_oi�Ano_eda ��O�[���t������"-"�Ō����j
					String strCdshain = "0000000000" + toString(items[0],"");
					strCdshain = strCdshain.substring(toString(items[0],"").length());
					String strNen = "00" + toString(items[1],"");
					strNen = strNen.substring(toString(items[1],"").length());
					String strOi = "000" + toString(items[2],"");
					strOi = strOi.substring(toString(items[2],"").length());
					String strEda = "000" + toString(items[3],"");
					strEda = strEda.substring(toString(items[3],"").length());

					resTable.addFieldVale(i, "no_shisaku", strCdshain + "-" + strNen + "-" + strOi + "-" + strEda);
					resTable.addFieldVale(i, "cd_busho", toString(items[4],""));
				}

			} else {
				// �f�[�^���擾�ł��Ȃ����F�G���[�̂��Ȃ���
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
