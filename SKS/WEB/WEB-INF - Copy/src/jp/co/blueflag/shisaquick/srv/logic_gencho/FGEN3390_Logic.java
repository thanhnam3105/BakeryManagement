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
 * ��z�ς݃f�[�^����DB���� �@�\ID�FFGEN3390
 * @author shima.hs
 *
 */
public class FGEN3390_Logic extends LogicBase {

	/**
	 * ��z�ς݃f�[�^�����R���X�g���N�^�F�C���X�^���X����
	 */
	public FGEN3390_Logic() {
		super();
	}

	/**
	 * ��z�ς݃f�[�^�����F��z�ς݃f�[�^�������擾����B
	 *
	 * @param reqData
	 *            : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData
	 *            : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		List<?> lstRecset = null;
		StringBuffer strSql = new StringBuffer();

		// ���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean resKind = null;

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// SQL���̍쐬
			strSql = createSQL(reqData, strSql);

			// SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			createResponse(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			// �ϐ��̍폜
			strSql = null;
		}

		return resKind;
	}

	/**
	 * �擾SQL�쐬 : ��z�ς݃f�[�^�������擾����SQL���쐬�B
	 *
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		try {
			strSql.append(" SELECT ");
			strSql.append("   count(cd_shain) ");
			strSql.append(" FROM ");
			strSql.append("   tr_shizai_tehai ");
			strSql.append(" WHERE ");

			for(int i = 0; i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//���N�G�X�g�f�[�^��莎��R�[�h�擾
				String strReqShain           = toString(reqData.getFieldVale(0, i, "cd_shain"));
				String strReqNen             = toString(reqData.getFieldVale(0, i, "nen"));
				String strReqNoOi            = toString(reqData.getFieldVale(0, i, "no_oi"));
				String strReqShizai          = toString(reqData.getFieldVale(0, i, "seq_shizai"));
				String strReqEda             = toString(reqData.getFieldVale(0, i, "no_eda"));

				int intShain = Integer.parseInt(strReqShain);
				int intNen   = Integer.parseInt(strReqNen);
				int intNoOi  = Integer.parseInt(strReqNoOi);
				int intShizai= Integer.parseInt(strReqShizai);
				int intEda   = Integer.parseInt(strReqEda);

				if(i != 0){
					strSql.append(" OR ");
				}
				strSql.append("       (cd_shain = '" + intShain + "' ");
				strSql.append("    AND nen = '" + intNen + "' ");
				strSql.append("    AND no_oi = '" + intNoOi + "' ");
				strSql.append("    AND seq_shizai = '" + intShizai + "' ");
				strSql.append("    AND no_eda = '" + intEda + "' ");
				strSql.append("    AND flg_tehai_status = 3) ");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "��z�ς݃f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[ : ���X�|���X�f�[�^�֊i�[����B
	 *
	 * @param lstGroupCmb
	 *            : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void createResponse(List<?> lstHattyusakiCmb,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "count_tehaizumi",
						toString(lstHattyusakiCmb.get(0)));


		} catch (Exception e) {
			this.em.ThrowException(e, "��z�ς݃f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}
}
