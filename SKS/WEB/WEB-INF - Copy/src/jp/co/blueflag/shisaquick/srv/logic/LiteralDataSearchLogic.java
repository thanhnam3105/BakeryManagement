package jp.co.blueflag.shisaquick.srv.logic;

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
 *
 * ���e�����}�X�^�����e�F���e������񌟍�DB����
 *  : ���e�����}�X�^�����e�F���e����������������B
 * @author jinbo
 * @since  2009/04/07
 */
public class LiteralDataSearchLogic extends LogicBase{

	/**
	 * ���e�����}�X�^�����e�F���e������񌟍�DB����
	 * : �C���X�^���X����
	 */
	public LiteralDataSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���e�����}�X�^�����e�F���e�������擾SQL�쐬
	 *  : ���e���������擾����SQL���쐬�B
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

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = createSQL(reqData, strSql);

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				// 20160513  KPX@1600766 MOD start
//				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				//�l��ݒ肵�Ă��鎞�G���[�Ƃ��Ȃ�
				if (reqData.getFieldVale(0, 0, "req_flg").equals("")) {
					em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				}
				// 20160513  KPX@1600766 MOD end

			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageLiteralData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����f�[�^���������Ɏ��s���܂����B");
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
	 * ���e�������擾SQL�쐬
	 *  : ���e���������擾����SQL���쐬�B
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
			String strCategoryCd = null;
			String strLiteralCd = null;
// 20160513  KPX@1600766 MOD start
//			String dataId = null;
			String dataId = "";
// 20160513  KPX@1600766 MOD end
			//�J�e�S���R�[�h�̎擾
			strCategoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//���e�����R�[�h�̎擾
			strLiteralCd = reqData.getFieldVale(0, 0, "cd_literal");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("60")) {
					//���e�����}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,ISNULL(CONVERT(VARCHAR,value1),'') as value1");
			strSql.append(" ,ISNULL(CONVERT(VARCHAR,value2),'') as value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,ISNULL(biko,'') as biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,ISNULL(cd_group,'') as cd_group");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd);
			strSql.append("'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");

			//�������������ݒ�
			//����O���[�v
			if(dataId.equals("1")) {
				strSql.append(" AND (cd_group = ");
				strSql.append(userInfoData.getCd_group());
				strSql.append(" OR cd_group IS NULL) ");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * ���e�����}�X�^�����e�F���e�������p�����[�^�[�i�[
	 *  : ���e�����������X�|���X�f�[�^�֊i�[����B
	 * @param lstLiteralData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralData(List<?> lstLiteralData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			// 20160513  KPX@1600766 ADD start
			if (lstLiteralData.size() == 0) {
				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}
			// 20160513  KPX@1600766 ADD end

			for (int i = 0; i < lstLiteralData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstLiteralData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				resTable.addFieldVale(i, "value1", items[2].toString());
				resTable.addFieldVale(i, "value2", items[3].toString());
				resTable.addFieldVale(i, "no_sort", items[4].toString());
				resTable.addFieldVale(i, "biko", items[5].toString());
				resTable.addFieldVale(i, "flg_edit", items[6].toString());
				resTable.addFieldVale(i, "cd_group", items[7].toString());

			}


		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}

}
