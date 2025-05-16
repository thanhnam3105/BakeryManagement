package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �yQP@40404�z
 *  �f�U�C���X�y�[�X���F�ΏۃJ�e�S������MAX�X�V�����̎擾
 *  �@�\ID�FFGEN3410
 *
 * @author E.Kitazawa
 * @since  2014/12/03
 */
public class FGEN3410_Logic extends LogicBase{

	/**
	 * �f�U�C���X�y�[�X���ꗗ����
	 * : �C���X�^���X����
	 */
	public FGEN3410_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �f�U�C���X�y�[�X���ꗗ����
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

			// �������ʂ��Ȃ��ꍇ�i�����ɂ͗��Ȃ��j
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
			}

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�f�U�C���X�y�[�X��񌟍����������s���܂����B");

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
	 * @return StringBuffer�F��������SQL
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
		StringBuffer strWhere = new StringBuffer();

		try {
			//���[�U���̉�ЃR�[�h���擾
			String strKaisha = userInfoData.getCd_kaisha();

			// �����H��
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			// �E��
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			// �������C��
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");


			// ���������F���
			strWhere.append(" cd_kaisha = " + strKaisha );

			// ���������F�ݒ�l
			if (!strCdSeizokojo.equals("")) {
				strWhere.append(" AND  T402.cd_seizokojo = '" + strCdSeizokojo + "'");
			}
			if(!strCdShokuba.equals("")){
				strWhere.append(" AND  T402.cd_shokuba = '" + strCdShokuba + "'");
			}
			if(!strCdLine.equals("")){
				strWhere.append(" AND  T402.cd_line = '" + strCdLine + "'");
			}

			// SQL���̍쐬
			strSql.append(" SELECT  ");
			strSql.append("   MAX(CONVERT(VARCHAR,dt_koshin,120)) AS max_koshin  ");
			strSql.append(" FROM  tr_shisan_designspace ");
            strSql.append(" WHERE ");
            strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
            strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
            strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
            strSql.append("  cd_line = '" +  strCdLine + "'");

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
			// �������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			// �f�[�^�����݂�����
			if (lstMstData.size() > 0) {
				// MAX �X�V���� ���擾
				Object item = (Object) lstMstData.get(0);

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
				// �f�[�^�Ȃ��̎��Anull���Ԃ� ""���Z�b�g�����
				resTable.addFieldVale(0, "max_koshin", toString(item,""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
