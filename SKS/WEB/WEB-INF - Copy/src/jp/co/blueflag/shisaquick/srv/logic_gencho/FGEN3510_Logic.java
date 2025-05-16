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
 * �yKPX@1602367�z
 *  �x�[�X�P���擾�i�ꗗ�j
 *  : �x�[�X�P�������擾����B
 *  �@�\ID�FFGEN3510
 *
 * @author BRC Koizumi
 * @since  2016/09/05
 */
public class FGEN3510_Logic extends LogicBase{

	/**
	 * �x�[�X�P���擾����
	 * : �C���X�^���X����
	 */
	public FGEN3510_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �x�[�X�P���擾
	 *  : �x�[�X�P�������擾����B
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
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {

			// �e�[�u����
			String strTblNm = "xmlFGEN3510";

			// �@�f�[�^�擾SQL�쐬
			strSqlBuf = this.createSQL(reqData);

			// �A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// �������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
			}

			// �B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�x�[�X�P���ꗗ�@�f�[�^�������������s���܂����B");

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
		StringBuffer strWork = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {

			// ���������擾�i�S���ΏہF�m�F�E���F���`�F�b�N���Ȃ��j
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");
			String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");
			String strNoHansu = reqData.getFieldVale(0, 0, "no_hansu");

			// ���[�J�[��
			if(!strCdMaker.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.cd_maker = " + strCdMaker);
			}

			// ���
			if(!strCdHouzai.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.cd_houzai = " + strCdHouzai);
			}

			// �Ő�
			if(!strNoHansu.equals("")){
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" LST.no_hansu = " + strNoHansu);
			}

			// SQL���̍쐬
			strSql.append("SELECT ");
			strSql.append("    LST.cd_maker AS cd_maker,");
			strSql.append("    LST.cd_houzai AS cd_houzai,");
			strSql.append("    LST.no_hansu AS no_hansu,");
			strSql.append("    M1.nm_literal AS nm_maker,");
			strSql.append("    M1.nm_2nd_literal AS nm_houzai,");
			strSql.append("    M3.nm_user AS nm_kakunin,");
			strSql.append("    M4.nm_user AS nm_shonin,");
			strSql.append("    CONVERT(VARCHAR,LST.dt_yuko,111) AS dt_yuko,");
			strSql.append("    CONVERT(VARCHAR,LST.dt_koshin,111) AS dt_koshin, ");
			strSql.append("    LST.name_houzai AS nm_base_houzai ");
			strSql.append("FROM ");
			strSql.append("    ma_base_price_list LST    ");
			strSql.append("    LEFT JOIN ma_literal M1 ON M1.cd_category = 'maker_name' ");
			strSql.append("      AND M1.cd_literal = LST.cd_maker");
			strSql.append("      AND M1.cd_2nd_literal = LST.cd_houzai");
			strSql.append("    LEFT JOIN ma_user M3 ON M3.id_user = LST.id_kakunin");
			strSql.append("    LEFT JOIN ma_user M4 ON M4.id_user = LST.id_shonin");

			if(strWork.length() > 0){
				strWhere.append(" WHERE ");
				strWhere.append(strWork);
			}

			strSql.append(strWhere);

			strSql.append(" ORDER BY cd_maker, cd_houzai, no_hansu desc ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
	 * @param lstCostData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstCostData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstCostData.size(); i++) {

				// �������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstCostData.get(i);

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "no_row", toString(i + 1));

				resTable.addFieldVale(i, "cd_maker", toString(items[0],""));		// ���[�J�[�R�[�h
				resTable.addFieldVale(i, "cd_houzai", toString(items[1],""));		// ��ރR�[�h
				resTable.addFieldVale(i, "no_hansu", toString(items[2],""));		// �Ő�
				resTable.addFieldVale(i, "nm_maker", toString(items[3],""));		// ���[�J�[��
//				resTable.addFieldVale(i, "nm_houzai", toString(items[4],""));		// ��ޖ�
				resTable.addFieldVale(i, "nm_han_houzai", toString(items[4],""));	// �ł̕�ޖ��i��:��ޖ��j
				resTable.addFieldVale(i, "nm_kakunin", toString(items[5],""));		// �m�F��
				resTable.addFieldVale(i, "nm_shonin", toString(items[6],""));		// ���F��
				resTable.addFieldVale(i, "dt_yuko", toString(items[7],""));			// �L���J�n��
				resTable.addFieldVale(i, "dt_koshin", toString(items[8],""));		// �ύX��
				resTable.addFieldVale(i, "nm_base_houzai", toString(items[9],""));	// �x�[�X��ޖ�
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
