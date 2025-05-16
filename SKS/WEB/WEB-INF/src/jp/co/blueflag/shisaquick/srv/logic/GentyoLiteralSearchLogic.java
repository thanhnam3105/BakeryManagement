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
 * �R���{�p�F���e������񌟍�DB����
 *  : �R���{�p�F���e����������������B
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoLiteralSearchLogic extends LogicBase{

	/**
	 * �R���{�p�F�J�e�S����񌟍�DB�����p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public GentyoLiteralSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F���e�������擾SQL�쐬
	 *  : ���e�����R���{�{�b�N�X�����擾����SQL���쐬�B
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

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageLiteralCmb(lstRecset, resKind.getTableItem(0));

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
	 * ���e�����擾SQL�쐬
	 *  : ���e�������擾����SQL���쐬�B
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
			String categoryCd = null;
			String userId = null;
			String gamenId = null;
			String kinoId = null;
			String dataId = null;

			//�J�e�S���R�[�h�̎擾
			categoryCd = reqData.getFieldVale(0, 0, "cd_category");
			//���[�UID�̎擾
			userId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//�@�\ID��ݒ�
					kinoId = userInfoData.getId_kino().get(i).toString();
					//�f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL���̍쐬
			strSql.append("SELECT ");
			strSql.append("DISTINCT ");
			strSql.append("       cd_literal, ");
			strSql.append("       nm_literal, ");
			strSql.append("       no_sort ");
			strSql.append(" FROM ma_literal ");

			if(kinoId.equals("20")){
				if(dataId.equals("9")) {
					strSql.append(" WHERE cd_category = '");
					strSql.append(categoryCd);
					strSql.append("' ");
				}
			} else {	//�V�X�e���Ǘ���
				strSql.append(" WHERE cd_category = '");
				strSql.append(categoryCd);
				strSql.append("' ");
			}
			strSql.append(" ORDER BY no_sort");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F���e�����p�����[�^�[�i�[
	 *  : ���e�����R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstLiteralCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralCmb(List<?> lstLiteralCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstLiteralCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstLiteralCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", items[0].toString());
				resTable.addFieldVale(i, "nm_literal", items[1].toString());
				if (items[2] == null){
					resTable.addFieldVale(i, "flg_2ndedit", "0"); //null�Ȃ��񃊃e�������g�p�Ȃ̂ŁA0��Ԃ�
				}else{
					resTable.addFieldVale(i, "flg_2ndedit", items[2].toString());
				}

			}

			if (lstLiteralCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_literal", "");
				resTable.addFieldVale(0, "nm_literal", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
