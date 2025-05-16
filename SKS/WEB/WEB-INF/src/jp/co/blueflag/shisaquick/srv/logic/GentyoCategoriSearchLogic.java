package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 *
 * �R���{�p�F�J�e�S����񌟍�DB����
 *  : �R���{�p�F�J�e�S��������������B
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoCategoriSearchLogic extends LogicBase{

	/**
	 * �R���{�p�F�J�e�S����񌟍�DB�����p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public GentyoCategoriSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�W���������擾SQL�쐬
	 *  : �W�������R���{�{�b�N�X�����擾����SQL���쐬�B
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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
		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = createSQL(reqData, strSql);

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageCategoryCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
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
	 * �J�e�S���擾SQL�쐬
	 *  : �J�e�S�����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String userId = null;
			String gamenId = null;
			String kinoId = null;
			String dataId = null;

			//���[�UID�̎擾
			userId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//�f�[�^ID��ݒ�
					kinoId = userInfoData.getId_kino().get(i).toString();
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL���̍쐬
			strSql.append("SELECT cd_category, nm_category");
			strSql.append(" FROM ma_category");

			//���e�����}�X�^�����e�i���X���
			if (gamenId.equals("300")) {
				if (kinoId == null) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("300")){
							//�f�[�^ID��ݒ�
							kinoId = userInfoData.getId_kino().get(i).toString();
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				if (kinoId.equals("10")) {	//�ҏW�i��ʁj
					strSql.append(" WHERE flg_edit = 1 AND flg_gencho = 1 AND cd_category <> 'C_hattyuusaki'");

				} else { //�V�X�e���Ǘ���
					strSql.append(" WHERE flg_gencho = 1 AND cd_category <> 'C_hattyuusaki'");
				}
			}
			strSql.append(" ORDER BY cd_category");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�J�e�S���p�����[�^�[�i�[
	 *  : �J�e�S���R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstCategoryCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageCategoryCmb(List<?> lstCategoryCmb, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstCategoryCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstCategoryCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_category", items[0].toString());
				resTable.addFieldVale(i, "nm_category", items[1].toString());

			}

			if (lstCategoryCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_category", "");
				resTable.addFieldVale(0, "nm_category", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

}
