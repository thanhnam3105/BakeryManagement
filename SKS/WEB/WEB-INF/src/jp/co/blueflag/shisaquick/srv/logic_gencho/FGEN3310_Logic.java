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
 *
 * �R���{�p�F�����挟��DB����<br>
 * : �R���{�p�F���������������B �@�\ID�FFGEN3300�@
 *
 * @author H.Shima
 * @since 2014/09/16
 */
public class FGEN3310_Logic extends LogicBase {

	/**
	 * �R���{�p�F�������񌟍�DB�����p�R���X�g���N�^ <br>
	 * : �C���X�^���X����
	 */
	public FGEN3310_Logic() {
		super();
	}

	/**
	 * �R���{�p�F��������擾SQL�쐬<br>
	 * : �O���[�v�R���{�{�b�N�X�����擾����SQL���쐬�B
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
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		// ���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean resKind = null;

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// ���������擾
			String strFlgMishiyo = reqData.getFieldVale(0, 0, "flg_mishiyo");
			// ���g�p
			if(strFlgMishiyo.equals("1")){
				// �g�p�\�Ȕ����惊�X�g���擾
				// SQL���̍쐬
				strSql = createNotMishiyoSQL(reqData, strSql, strFlgMishiyo);
			} else {
				// �����惊�X�g��S���擾
			// SQL���̍쐬
			strSql = createSQL(reqData, strSql);
			}
//
//			// SQL���̍쐬
//			strSql = createSQL(reqData, strSql);

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
			storageHattyusakiCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "������R���{�{�b�N�X���̎擾�Ɏ��s���܂����B");

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
	 * ������擾SQL�쐬
	 *  : �Ώێ��ނ��擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		//cho ������P/2 �̃R���{�ɕ\�����ꂽ���Ƃ�Distinct����merge����
		try {
			strSql.append(" SELECT distinct ");
			strSql.append("   cd_��iteral, ");
			strSql.append("   nm_literal, ");
			strSql.append("   no_sort ");
			strSql.append(" FROM ");
			strSql.append("   ma_literal ");
			strSql.append(" WHERE ");
			strSql.append("   cd_category = 'C_hattyuusaki' ");
			strSql.append(" ORDER BY ");
			strSql.append("   no_sort ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

//�yKPX@1602367�zadd start
	/**
	 * ������擾SQL�쐬
	 *  : ��������擾����SQL���쐬�B
	 * @param strFlgMishiyo
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createNotMishiyoSQL(RequestResponsKindBean reqData, StringBuffer strSql, String strFlgMishiyo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		// �g�p�\�Ȕ�����i���g�p�������j���擾����
		try {
			strSql.append(" SELECT distinct ");
			strSql.append("   cd_��iteral, ");
			strSql.append("   nm_literal, ");
			strSql.append("   no_sort ");
			strSql.append(" FROM ");
			strSql.append("   ma_literal ");
			strSql.append(" WHERE ");
			strSql.append("   cd_category = 'C_hattyuusaki' ");
			strSql.append("   AND flg_mishiyo = '" + strFlgMishiyo + "' ");
			strSql.append(" ORDER BY ");
			strSql.append("   no_sort ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
//�yKPX@1602367�zadd end


	/**
	 * �R���{�p�F������p�����[�^�[�i�[
	 *  : ������R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageHattyusakiCmb(List<?> lstHattyusakiCmb, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstHattyusakiCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstHattyusakiCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_hattyusaki", items[0].toString());
				resTable.addFieldVale(i, "nm_hattyusaki", items[1].toString());

			}

			if (lstHattyusakiCmb.size() == 0) {
				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_hattyusaki", "");
				resTable.addFieldVale(0, "nm_hattyusaki", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "������R���{�{�b�N�X�̃��X�|���X�f�[�^�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
