package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �����S����Ќ���DB����
 *  : �����S����Џ�����������B
 * @author furuta
 * @since  2009/03/29
 */
public class SeizouTantouKaishaSearchLogic extends LogicBase{

	/**
	 * �����S����Ќ����R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public SeizouTantouKaishaSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �����S����Ѓf�[�^�擾SQL�쐬
	 *  : �����S����Ѓf�[�^���擾����SQL���쐬�B
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

			String strUserId = "";
			
			//�e�[�u��Bean�擾
			RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//�sBean�擾
			RequestResponsRowBean reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);
			//���[�U�[ID�擾
			strUserId = reqRecBean.GetItemValue(0);
			
			//SQL���̍쐬
			strSql.append("SELECT ");
			strSql.append("B.cd_kaisha, B.nm_kaisha ");
			strSql.append("FROM ");
			strSql.append("ma_tantokaisya A JOIN ma_busho B ON A.cd_tantokaisha = B.cd_kaisha  ");
			strSql.append("WHERE A.id_user = ");
			strSql.append(strUserId);
			strSql.append(" GROUP BY B.cd_kaisha, B.nm_kaisha");
			strSql.append(" ORDER BY B.cd_kaisha");
			
			//DB�C���X�^���X����
			createSearchDB();
			//�������s
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//���X�|���X�f�[�^�̌`��
			storageSeizouTantouKaishaData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����S����Ќ���DB�����Ɏ��s���܂����B");
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
	 * �����S����Ѓf�[�^�p�����[�^�[�i�[
	 *  : �����S����Ѓf�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeizouTantouKaishaData(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[1].toString());

			}
			
			if (lstGroupCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_kaisha", "");
				resTable.addFieldVale(0, "nm_kaisha", "");

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����S����Ќ���DB�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
