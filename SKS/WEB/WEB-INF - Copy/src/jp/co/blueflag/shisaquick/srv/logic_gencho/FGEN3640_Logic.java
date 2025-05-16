package jp.co.blueflag.shisaquick.srv.logic_gencho;

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
public class FGEN3640_Logic extends LogicBase {

	/**
	 * ���e�����}�X�^�����e�F������ꗗ��񌟍�DB����
	 *
	 */
	public FGEN3640_Logic() {
		// ���N���X�̃R���X�g���N�^
		super();
	}

	/**
	 * ���e�����}�X�^�����e�F������ꗗ���擾SQL�쐬
	 *
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
	 * @param userInfoData
	 *            : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean requestData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();

		// ���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			strSql = literalKanriDeleteSQL(requestData);

			//�g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			//SQL�����s
			execDB.execSQL(strSql.toString());

			//�R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageLiteralDataKanri(resKind.getTableItem(0));

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
	 * ���e�����f�[�^�o�^SQL�쐬
	 *  : ���e�����f�[�^�o�^�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// �o�^�p
		StringBuffer strSql = new StringBuffer();

			// ������R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");

		if ( strLiteral2ndNm.equals("") ){
			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append("C_hattyuusaki'");
			strSql.append(" AND cd_literal = '");
			strSql.append("'");
			strSql.append(strLiteralCd);
			strSql.append("'");
		} else {

			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append("C_hattyuusaki'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");
			strSql.append(" AND nm_2nd_literal = '");
			strSql.append(strLiteral2ndNm);
			strSql.append("'");
		}
		return strSql;
	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ���e�����f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������ʂ̊i�[
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
