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
 * �R���{�p�F�O���[�v��񌟍�DB����
 *  : �R���{�p�F�O���[�v������������B
 *  �@�\ID�FSA050�@
 *  
 * @author furuta
 * @since  2009/03/29
 */
public class GroupSearchLogic extends LogicBase{

	/**
	 * �R���{�p�F�O���[�v��񌟍�DB���� 
	 * : �C���X�^���X����
	 */
	public GroupSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�O���[�v���擾SQL�쐬
	 *  : �O���[�v�R���{�{�b�N�X�����擾����SQL���쐬�B
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
		
		//���X�|���X�f�[�^�i�@�\�j
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
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageGroupCmb(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�O���[�v�R���{�{�b�N�X���̎擾�Ɏ��s���܂����B");
			
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
	 * �O���[�v�擾SQL�쐬
	 *  : �O���[�v���擾����SQL���쐬�B
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
			String dataId = null;

			//���[�UID�̎擾
			userId = reqData.getFieldVale(0, 0, "id_user");
			//���ID�̎擾
			gamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(gamenId)){
					//�f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬
			strSql.append("SELECT ");
			strSql.append("  cd_group");
			strSql.append(" ,nm_group");
			strSql.append(" FROM ma_group");
			
			//����f�[�^�ꗗ���
			if (gamenId.equals("10")) {
				if(dataId.equals("1") || dataId.equals("2") || dataId.equals("3")) {	//����O���[�v���`
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("4") || dataId.equals("9")) {	//���H�ꕪ or �S��
					//�����Ȃ�
				}

			//���e�����}�X�^�����e���
			} else if (gamenId.equals("60") || gamenId.equals("65")) {
				if (dataId == null) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							//�f�[�^ID��ݒ�
							dataId = userInfoData.getId_data().get(i).toString();
						}
					}			
				}
				if(dataId.equals("1")) {	//����O���[�v
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("9")) {	//�S��
					//�����Ȃ�
				}

			//�O���[�v�}�X�^�����e���
			} else if (gamenId.equals("70")) {
				if(dataId.equals("9")) {	//�S��
					//�����Ȃ�
				}

			//�S���҃}�X�^�����e���
			} else if (gamenId.equals("90")) {
				if(dataId.equals("1")) {	//�����̂�
					strSql.append(" WHERE cd_group = ");
					strSql.append(userInfoData.getCd_group());
				} else if (dataId.equals("9")) {	//�S��
					//�����Ȃ�
				}
			}
			strSql.append(" ORDER BY cd_group");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �R���{�p�F�O���[�v�p�����[�^�[�i�[
	 *  : �O���[�v�R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGroupCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable) 
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
				resTable.addFieldVale(i, "cd_group", items[0].toString());
				resTable.addFieldVale(i, "nm_group", items[1].toString());

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
				resTable.addFieldVale(0, "cd_group", "");
				resTable.addFieldVale(0, "nm_group", "");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�O���[�v�R���{�{�b�N�X�̃��X�|���X�f�[�^�����Ɏ��s���܂����B");
			
		} finally {

		}
		
	}
	
}
