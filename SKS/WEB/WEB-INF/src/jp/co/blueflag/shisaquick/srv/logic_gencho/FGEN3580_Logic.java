package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���e�����}�X�^�����e�F�����ޒ��B���p ������ꗗ��񌟍�DB����
 *
 *
 */
public class FGEN3580_Logic extends LogicBase{

	/**
	 * ���e�����}�X�^�����e�F������ꗗ��񌟍�DB����
	 *
	 */
	public FGEN3580_Logic() {
		// ���N���X�̃R���X�g���N�^
		super();
	}
	/**
	 * ���e�����}�X�^�����e�F������ꗗ���擾SQL�쐬
	 *
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();


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

			// null�̎��i���j���[����̃��[�h���j�͌������������s���Ȃ�
			if (strSqlBuf != null) {

				// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
				super.createSearchDB();
				lstRecset = searchDB.dbSearch(strSqlBuf.toString());

				// �������ʂ��Ȃ��ꍇ�A�����ŃG���[�Ƃ��Ȃ�
				if (lstRecset.size() == 0){
//					em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
				}

			}
			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "������}�X�^��񌟍����������s���܂����B");

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
			//���j���[����̃��[�h����lstMstData�� null�̈�
			if ((lstMstData== null) || (lstMstData.size() == 0)) {
//				// �f�[�^���擾�ł��Ȃ����F�G���[�̂��Ȃ��ׁijs�̃��b�Z�[�W��Ԃ������j
//				resTable.addFieldVale(0, "flg_return", "");
//				resTable.addFieldVale(0, "msg_error", "");
//				resTable.addFieldVale(0, "no_errmsg", "");
//				resTable.addFieldVale(0, "nm_class", "");
//				resTable.addFieldVale(0, "cd_error", "");
//				resTable.addFieldVale(0, "msg_system", "");

			} else {

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
					resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
					resTable.addFieldVale(i, "nm_literal", toString(items[1],""));
					resTable.addFieldVale(i, "no_sort", toString(items[2],""));
					resTable.addFieldVale(i, "cd_2nd_literal", toString(items[3],""));
					resTable.addFieldVale(i, "nm_2nd_literal", toString(items[4],""));
					resTable.addFieldVale(i, "no_2nd_sort", toString(items[5],""));
					resTable.addFieldVale(i, "mail_address", toString(items[6],""));
					resTable.addFieldVale(i, "flg_mishiyo", toString(items[7],""));
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

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

			String dataId = null;
			// �J�e�S��R�[�h
			String strCategoryCd = reqData.getFieldVale("table", 0, "cd_category");
			// ������R�[�h
			String strLiteralCd   = reqData.getFieldVale("table", 0, "cd_literal");
			// �����於
			String strLiteralNm      = reqData.getFieldVale("table", 0, "nm_literal");


			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,no_sort");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd);
			strSql.append("'");
			if (!strLiteralCd.equals("")) {
				strSql.append(" AND cd_literal LIKE '%");
				strSql.append(strLiteralCd);
				strSql.append("%'");
			}
			if (!strLiteralNm.equals("")) {
				strSql.append(" AND nm_literal LIKE '%");
				strSql.append(strLiteralNm);
				strSql.append("%'");
			}
			strSql.append("ORDER BY");
			strSql.append(" flg_mishiyo");
			strSql.append(" ,cast (cd_literal as int) ");
			strSql.append(" ,cast (cd_2nd_literal as int)");
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

}
