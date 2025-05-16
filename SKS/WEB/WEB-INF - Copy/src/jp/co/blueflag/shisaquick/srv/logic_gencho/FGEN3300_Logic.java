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
 * �R���{�p�F�S���Ҍ���DB����<br>
 * : �R���{�p�F�S���҂���������B �@�\ID�FFGEN3300�@
 * 
 * @author H.Shima
 * @since 2014/09/17
 */
public class FGEN3300_Logic extends LogicBase {
	
	/**
	 * �R���{�p�F�S���Ҍ���DB�����p�R���X�g���N�^ <br>
	 * : �C���X�^���X����
	 */
	public FGEN3300_Logic() {
		super();
	}
	
	/**
	 * �R���{�p�F�S���҃R�[�h�擾SQL�쐬<br>
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
			storageTantosyaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃R���{�{�b�N�X���̎擾�Ɏ��s���܂����B");

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
	 * �擾SQL�쐬
	 *  : �S���҃R�[�h���擾����SQL���쐬�B
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
			strSql.append(" SELECT DISTINCT ");
			strSql.append("   id_user, ");
			strSql.append("   nm_user ");
			strSql.append(" FROM ");
			strSql.append("   tr_shizai_tehai T350 LEFT JOIN ma_user M101 ");
			strSql.append("     ON T350.id_toroku = M101.id_user ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �R���{�p�F�S���҃R�[�h�p�����[�^�[�i�[
	 *  : �S���҃R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageTantosyaCmb(List<?> lstHattyusakiCmb, RequestResponsTableBean resTable) 
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
				resTable.addFieldVale(i, "id_user", items[0].toString());
				resTable.addFieldVale(i, "nm_user", items[1].toString());

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
				resTable.addFieldVale(0, "id_user", "");
				resTable.addFieldVale(0, "nm_user", "");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃R���{�{�b�N�X�̃��X�|���X�f�[�^�����Ɏ��s���܂����B");
			
		} finally {

		}
		
	}
}
