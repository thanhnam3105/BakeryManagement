package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@40404�z
 *  �Q�l�����Ǘ��c�a����
 *  �F�J�e�S���[�Ɉ�v����A�b�v���[�h���ꂽ�t�@�C�������擾����
 *  �@�\ID�FFGEN3220
 *
 * @author E.Kitazawa
 * @since  2014/09/04
 */
public class FGEN3210_Logic extends LogicBase{

	/**
	 * �Q�l�����Ǘ��c�a����
	 * : �C���X�^���X����
	 */
	public FGEN3210_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �Q�l�����Ǘ��c�a����
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

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

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
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			// �ϐ��̍폜

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
			//�e�[�u����
			String strTblNm = "table";

			// �f�[�^�擾SQL�쐬
			strSqlBuf = this.createSQL(reqData);

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�Q�l�����������������s���܂����B");

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

            String strCdLiteral    = reqData.getFieldVale("table", 0, "cd_literal");

			// SQL���̍쐬
			strSql.append(" SELECT cd_literal  ");
			strSql.append("       ,nm_literal  ");
			strSql.append("       ,nm_file  ");
			strSql.append(" FROM  tr_sankoshiryo  ");
			strSql.append(" WHERE ");
			strSql.append("    cd_literal = '" + strCdLiteral + "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstSiryo : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstSiryo
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			if (lstSiryo.size() > 0) {
				for (int i = 0; i < lstSiryo.size(); i++) {

					// �������ʂ̊i�[
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstSiryo.get(i);

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
					resTable.addFieldVale(i, "nm_literal", toString(items[1],""));
					resTable.addFieldVale(i, "nm_file", toString(items[2],""));
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
